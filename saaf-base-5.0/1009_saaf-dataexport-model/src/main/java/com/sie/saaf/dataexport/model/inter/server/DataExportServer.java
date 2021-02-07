package com.sie.saaf.dataexport.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.HttpClientUtil;
import com.sie.saaf.dataexport.bean.XlsExportBean;
import com.sie.saaf.dataexport.model.inter.IBaseExportRecord;
import com.sie.saaf.dataexport.model.inter.IDataExport;
import com.sie.saaf.dataexport.utils.ExcelUtil;
import com.sie.saaf.sso.model.inter.ISsoServer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 1、阻塞队列控制并发导出任务数量
 * 2、多线程分批查询导出
 * 3、
 *
 * @author huangtao
 * @creationDate 2018年5月22日 16:56:53
 */
@Component
public class DataExportServer implements IDataExport {
    private static final Logger log = LoggerFactory.getLogger(DataExportServer.class);

    //用于数据的分批查询写入excel
    private  ExecutorService batchExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    //用于控制并发导出的任务数
    private  ExecutorService concurrentExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //用于流转换处理
    private  ExecutorService ioStreamPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //阻塞队列
    private  LinkedBlockingQueue<XlsExportBean> queue = new LinkedBlockingQueue<XlsExportBean>(2000);

    private Map<String, ExcelUtil<JSONObject>> workbookMap = new ConcurrentHashMap<>();

    @Autowired
    private redis.clients.jedis.JedisCluster jedisCluster;

    @Autowired
    private ISsoServer ssoServer;

    @Autowired
    private IFastdfs fastdfsServer;

//    @Autowired
//    private JavaMailSender mailSender;

//    @Autowired
//    private AliOssServer aliOssServer;

    @Autowired
    private IBaseExportRecord baseExportRecordServer;

    private byte[] _byte = new byte[2048];

    private final String DATA_COUNT_KEY = "EXPORT_COUNT_";

    public DataExportServer() {
        log.info("导出类初始化");
        for (int i=0;i<8;i++){
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            log.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
                            XlsExportBean xlsExportBean= queue.take();
                            exportAction(xlsExportBean);
                        }catch (Exception e){
                            log.error(e.getMessage(),e);
                        }
                    }
                }
            };
            concurrentExportPool.submit(runnable);
        }
    }

    /**
     *  多线程分批查询导出
     * @param xlsExportBean
     * @throws Exception
     */
    @Override
    public void startExport(XlsExportBean xlsExportBean)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYMMddHHmmss");
        String seqStr = simpleDateFormat.format(new Date()) + RandomUtils.nextInt(100);
        UserSessionBean userSessionBean= ssoServer.getUserSession(xlsExportBean.getToken());
        if (userSessionBean!=null){
            seqStr+=userSessionBean.getUserId();
        }
        xlsExportBean.setExportId(seqStr);
        boolean result=queue.offer(xlsExportBean);
        Assert.isTrue(result,"导个毛线，服务器不干了");
        baseExportRecordServer.saveBaseExportRecordInfo(xlsExportBean);
    }


    /**
     * 多线程查询导出
     * @param xlsExportBean
     * @throws Exception
     */
    private void exportAction(XlsExportBean xlsExportBean) throws Exception {
        try {
            Map<String, String> header = new HashMap<>();
            header.put(xlsExportBean.getToakenAttributeName(), xlsExportBean.getToken());
            JSONObject params = new JSONObject();
            if (StringUtils.isNotBlank(xlsExportBean.getRequestParam()))
                params = JSON.parseObject(xlsExportBean.getRequestParam());
            params.put(xlsExportBean.getPageIndexParamName(), 1);
            params.put(xlsExportBean.getPageRowsParamName(), 1);
            //查询总数
            String result = xlsExportBean.getRequestUrl().contains("/v2/") ?
                    HttpClientUtil.sendJsonRequest(xlsExportBean.getRequestUrl(), params, header, xlsExportBean.isPost(),"utf-8")
                    : HttpClientUtil.send(xlsExportBean.getRequestUrl(), params, header, xlsExportBean.isPost());
            if (StringUtils.isBlank(result))
                throw new Exception("查询数据总数量异常");
            JSONObject jsonResult = JSON.parseObject(result);
            Assert.notNull(jsonResult.getString(xlsExportBean.getDataCount()), "服务返回结果无数据总数量属性");
            Integer count = jsonResult.getInteger(xlsExportBean.getDataCount());
            if (Objects.equals(xlsExportBean.getExportNum(), -1) || xlsExportBean.getExportNum() > count)
                xlsExportBean.setExportNum(count);
            if (count<=0){
                xlsExportBean.setFileUrl("数据查询服务无返回数据");
                baseExportRecordServer.updateRecordStatus(xlsExportBean);
                jedisCluster.setex(xlsExportBean.getExportId(), 3600, "{}");
                return;
            }
            int pageSize = getPageSize();
            String dataKey =xlsExportBean.getExportId();
            String countKey = DATA_COUNT_KEY + xlsExportBean.getExportId();

            int pageCount = xlsExportBean.getExportNum() / pageSize;
            if (xlsExportBean.getExportNum() % pageSize > 0)
                pageCount++;
            JSONObject finalParams = params;
            //要导出的数据总量
            for (int i = 1; i <= pageCount; i++) {


                int finalI = i;
                int finalPageCount = pageCount;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {


                        String result = null;
                        boolean success = false;
                        int dataCount = pageSize;
                        //最后一页数据量可能小于pageSize
                        if (finalI == finalPageCount) {
                            dataCount = xlsExportBean.getExportNum() - finalI * pageSize;
                        }
                        try {
                            //构建服务请求分页参数
                            JSONObject requestParams = JSON.parseObject(finalParams.toJSONString());
                            requestParams.fluentPut(xlsExportBean.getPageRowsParamName(), pageSize)
                                    .put(xlsExportBean.getPageIndexParamName(), finalI);
                            result = getRequestResult(xlsExportBean, requestParams, header);
                            int n = 0;
                            while (StringUtils.isBlank(result)) {
                                log.info("服务请求失败,重试第{}次,url:{},param:{}", n, xlsExportBean.getRequestUrl(), requestParams);
                                if (n++ > 20)
                                    break;
                                Thread.sleep(100);
                                result = getRequestResult(xlsExportBean, requestParams, header);
                            }
                            if (StringUtils.isBlank(result)) {
                                throw new Exception("请求服务失败:" + xlsExportBean.getRequestUrl());
                            }
                            //解析服务返回数据
                            JSONArray dataArry = parseServiceResult(result,xlsExportBean);
                            if (dataArry == null || dataArry.size() == 0) {
                                throw new Exception("解析服务返回数据为空");
                            }
                            appendXlsContent(dataKey,dataArry,xlsExportBean);
                            dataCount = dataArry.size();
                            success = true;
                        } catch (Exception e) {
                            log.error("请求{}返回{},params:{}", xlsExportBean.getRequestUrl(), result, xlsExportBean.getRequestParam());
                            log.error(e.getMessage(), e);
                        } finally {
                            //查询异常时，导出数量减去当前页数据大小
                            if (!success) {
                                synchronized (xlsExportBean.getExportNum()) {
                                    xlsExportBean.setExportNum(xlsExportBean.getExportNum() - dataCount);
                                }
                            }
                            int number = jedisCluster.incrBy(countKey, dataCount).intValue();
                            log.info("{}第{}->{}次查询:{}->{}:{}", dataKey, finalPageCount, finalI, xlsExportBean.getExportNum(), number, dataCount);
                            if (number >= xlsExportBean.getExportNum()) {
                                jedisCluster.expire(countKey, 3600 * 6);
                                log.info("{}开始导出", dataKey);
                                jedisCluster.del(countKey);
                                try(ByteArrayOutputStream outputStream=new ByteArrayOutputStream()) {
                                    Long start = System.currentTimeMillis();
                                    ExcelUtil<JSONObject> excelUtil = workbookMap.get(xlsExportBean.getExportId());
                                    excelUtil.getWorkbook().write(outputStream);
                                    start = System.currentTimeMillis() - start;
                                    log.info("写入文件结束用时{}秒", Double.parseDouble(start + "") / 1000);

                                    sendIOstream2Server(xlsExportBean,outputStream);
                                } catch (Exception e) {
                                    jedisCluster.setex(xlsExportBean.getExportId(),3600,"{}");
                                    log.error(e.getMessage(),e);
                                }
                                workbookMap.remove(dataKey);
                                baseExportRecordServer.updateRecordStatus(xlsExportBean);
                            }
                        }
                    }
                };
                batchExportPool.submit(runnable);
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            jedisCluster.setex(xlsExportBean.getExportId(),3600,"{}");
        }
    }

    public String getRequestResult(XlsExportBean xlsExportBean, JSONObject requestParams, Map<String, String> header) {
        try {
            String result = xlsExportBean.getRequestUrl().contains("/v2/") ?
                    HttpClientUtil.sendJsonRequest(xlsExportBean.getRequestUrl(), requestParams, header, xlsExportBean.isPost(),"utf-8")
                    : HttpClientUtil.send(xlsExportBean.getRequestUrl(), requestParams, header, xlsExportBean.isPost());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 同步锁 初始化 excel导出类,避免多线程时多次初始化
     *
     * @param key
     * @param xlsExportBean
     * @return
     */
    private synchronized ExcelUtil<JSONObject> initExcelUtil(String key, XlsExportBean xlsExportBean) {
        if (workbookMap.get(key) != null)
            return workbookMap.get(key);
        JSONArray labelArray=JSONArray.parseArray(xlsExportBean.getLabelName());
        JSONArray attributeArray=JSONArray.parseArray(xlsExportBean.getAttributeNames());
        String[] labels=new String[labelArray.size()];
        String[] attribute=new String[attributeArray.size()];
        labelArray.toArray(labels);
        attributeArray.toArray(attribute);
        ExcelUtil<JSONObject> excelUtil = new ExcelUtil<>(labels,attribute);
        workbookMap.put(key, excelUtil);
        return excelUtil;
    }


    /**
     *  将查询数据追加至excel中
     * @param key
     * @param dataArray
     * @param xlsExportBean
     */
    private void appendXlsContent(String key, JSONArray dataArray, XlsExportBean xlsExportBean) {
        try {
            if (dataArray == null || dataArray.size() == 0)
                return;
            ExcelUtil<JSONObject> excelUtil = workbookMap.get(key);
            if (excelUtil == null) {
                excelUtil = initExcelUtil(key, xlsExportBean);
            }
            excelUtil.createExcel2007Doc(dataArray.toJavaList(JSONObject.class));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 解析服务返回数据
     * @param result
     * @param xlsExportBean
     * @return
     * @throws Exception
     */
    public JSONArray parseServiceResult(String result,XlsExportBean xlsExportBean) throws Exception {
        if (StringUtils.isBlank(result)) {
            throw new Exception("请求服务失败:" + xlsExportBean.getRequestUrl());
        }
        //解析服务返回数据
        JSONObject jsonObject = JSON.parseObject(result);
        String[] strs = xlsExportBean.getStructure().split("\\.");
        JSONObject dataJson = jsonObject;
        for (int j = 0; j < strs.length - 1; j++) {
            if (StringUtils.isBlank(strs[j]))
                continue;
            dataJson = jsonObject.getJSONObject(strs[j]);
            if (dataJson == null) {
                throw new Exception("数据结构解析失败");
            }
        }
        JSONArray dataArry = dataJson.getJSONArray(strs[strs.length - 1]);
        if (dataArry == null || dataArry.size() == 0) {
            throw new Exception("解析服务返回数据为空");
        }
        return dataArry;
    }



    /**
     *  1、将文件流上传至服务器（阿里OSS或FastDFS）
     *  2、将文件上传结果保存至redis
     * @param xlsExportBean
     */
    private void sendIOstream2Server(XlsExportBean xlsExportBean, ByteArrayOutputStream outputStream){
        try(InputStream inputStream=new ByteArrayInputStream(outputStream.toByteArray()) )  {
            String filename = xlsExportBean.getEmailSubject()+System.currentTimeMillis()+".xlsx";
            ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream,filename);
            String accessPath = resultFileEntity.getAccessPath();
            //ResultFileEntity resultFileEntity = aliOssServer.fileUpload(inputStream, filename, "file/updata/");
            //String accessPath = resultFileEntity.getAccessPath();
            jedisCluster.setex(xlsExportBean.getExportId(),3600,JSON.toJSONString(resultFileEntity));
            xlsExportBean.setFileUrl(accessPath);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            jedisCluster.setex(xlsExportBean.getExportId(),3600,"{}");
        }
    }

    @Override
    public JSONObject getExportResult(String exportId){
        String str= jedisCluster.get(exportId);
        if (StringUtils.isBlank(str))
            return null;
        return JSONObject.parseObject(str);
    }


//    /**
//     *  压缩excel文件流
//     * @param inputStream
//     * @throws IOException
//     */
//    private void createZipISStream(ByteArrayInputStream inputStream,XlsExportBean xlsExportBean) throws IOException {
//        log.info("excel文件流大小:{}",inputStream.available());
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
//        zipOutputStream.putNextEntry(new ZipEntry( System.currentTimeMillis() + ".xlsx"));
//        int len = 0 ;
//        while( (len = inputStream.read(_byte)) > 0  ){
//            zipOutputStream.write(_byte, 0, len);
//        }
//        zipOutputStream.closeEntry();
//        zipOutputStream.close();
////        sendEmail(outputStream,xlsExportBean);
//        inputStream.close();
//
//
//    }

    /**
     *  将文件压缩流作为附件发送邮件
     * @param outputStream
     * @throws IOException
     */
//    private void sendEmail(ByteArrayOutputStream outputStream,XlsExportBean xlsExportBean) throws MessagingException, IOException {
//        byte[] bytes=outputStream.toByteArray();
//        log.info("字节大小:{}",bytes.length);
//        InputStreamSource inputStreamSource=new ByteArrayResource(bytes);
//        MimeMessage message = null;
//        message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setFrom("notice@ausnutria.com");
//        helper.setTo(xlsExportBean.getEmail());
//        helper.setSubject(xlsExportBean.getEmailSubject());
//        helper.addAttachment(System.currentTimeMillis() + ".zip",inputStreamSource);
//        String text ="";
//        if (StringUtils.isNotBlank(xlsExportBean.getRequestParamConten()))
//            text="数据导出筛选条件:"+xlsExportBean.getRequestParamConten();
//        helper.setText(text, true);
//        mailSender.send(message);
//        outputStream.close();
//    }

//    /**
//     * 解析json字符数组大小，减少字符转json对象的内存消耗
//     *
//     * @param str
//     * @return
//     */
//    private static int getJsonArraySize(String str) {
//        char[] chars = str.toCharArray();
//        int n = 0;
//        Stack<String> stack = new Stack<>();
//        boolean escape = false;
//        for (char c : chars) {
//            // {:123
//            // }:125
//            // ':39
//            //\:92
//            //":34
//            //标记前一个字符为转义字符
//            if (c == 92) {
//                escape = true;
//                continue;
//            }
//            //前一个字符为转义字符
//            if (c != 92 && escape) {
//                escape = false;
//                continue;
//            }
//            if (c == 123) {
//                stack.push(c + "");
//                continue;
//            }
//            if (stack.size() >= 1 && (c == 39 || c == 34)) {
//                String symbol = stack.peek();
//                if (symbol.equals(c + ""))
//                    stack.pop();
//                else
//                    stack.push(c + "");
//            } else if (stack.size() == 1 && c == 125) {
//                n++;
//                stack.pop();
//            }
//        }
//        return n;
//    }


    private int getPageSize() {
        try {
            String str = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "exportPageSize");
            if (StringUtils.isBlank(str)) {
                jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "exportPageSize", "500");
                str = "500";
            }
            return Integer.valueOf(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 500;
        }
    }

}
