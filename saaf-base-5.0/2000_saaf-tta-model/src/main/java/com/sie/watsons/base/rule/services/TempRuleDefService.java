package com.sie.watsons.base.rule.services;

import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.rowset.serial.SerialClob;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.sie.saaf.common.util.HtmlUtils;
import com.yhg.base.utils.FileUtil;
import com.yhg.base.utils.StringUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.params.model.inter.ITempParamDef;
import com.sie.watsons.base.rule.model.entities.RuleFileTemplateEntity_HI;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/tempRuleDefService")
public class TempRuleDefService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempRuleDefService.class);

    @Autowired
    private ITempRuleDef tempRuleDefServer;

    @Autowired
    private ITempParamDef tempParamDefServer;

    @Autowired
    private IRuleFileTemplate ruleFileTemplate;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.tempRuleDefServer;
    }


    /**
     * POST /saafsso/baseSystemSsoService/findSysList
     * POST /saafsso/ssoUserService/findById
     * POST /saafsso/ssoUserService/findUserPagination
     */
    /**
     * 分页查询规则信息
     *
     * @param params
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, value = "findRulePagination")
    public String findRulePagination(@RequestParam(required = false) String params,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageRows) throws Exception {
        String result = null;
        try {
            JSONObject queryParamJSON = JSONObject.parseObject(params);
            queryParamJSON = SaafToolUtils.cleanNull(queryParamJSON, "isEnable", "ruleName", "soleResouceType", "soleProductType", "isIncludeEc", "isIncludeSpecial");
            Pagination<TempRuleDefEntity_HI_RO> findList = tempRuleDefServer.findRulePagination(queryParamJSON, pageIndex, pageRows);
            result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, findList);
        } catch (Exception e) {
            LOGGER.error("findRulePagination error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "查询失败", null);
        }
        LOGGER.info("分页查询规则入参信息:{},出参信息:{}", new Object[]{params, result});
        return result;
    }


    /**
     * 功能说明：查询用户及系统信息详情
     *
     * @return
     * @createTime 2018/12/28 14:03
     * @author xiaoga
     * @params 用户id
     */
    @RequestMapping(method = RequestMethod.POST, value = "findById")
    public String findById(@RequestParam String params) {
        String result = "";
        JSONObject paramJson = JSON.parseObject(params);
        try {
            Integer ruleId = paramJson.getInteger("ruleId");
            TempRuleDefEntity_HI_RO ruleDefEntity = tempRuleDefServer.findRuleById(ruleId);
            result = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, ruleDefEntity).toString();
        } catch (Exception e) {
            LOGGER.error("findById error:{}", e);
            result = SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败", 1, null).toString();
        }
        LOGGER.info(".findById 入参:{}, 出参:{}", new Object[]{paramJson, result});
        return result;
    }


    /**
     * 功能描述： 通过规则查询参数信息
     * @author xiaoga
     * @date 2019/5/31
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "findParams")
    public String findParams(@RequestParam String params, @RequestParam(required = false, defaultValue = "1") Integer pageIndex, @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        String result = "";
        JSONObject paramJson = JSON.parseObject(params);
        try {
            Integer ruleId = paramJson.getInteger("ruleId");
            if (ruleId == null) {
                return this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, null);
            }
            Pagination<TempParamDefEntity_HI_RO> pageination = tempRuleDefServer.findParams(paramJson, pageIndex, pageRows);
            result = this.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, pageination);
        } catch (Exception e) {
            LOGGER.error("findParams error:{}", e);
            result = this.convertResultJSONObj(ERROR_STATUS, "操作失败", null).toString();
        }
        LOGGER.info(".findParams 入参:{}, 出参:{}", new Object[]{paramJson, result});
        return result;
    }


    /**
     * 功能描述： 查询规则已排除的列表项
     * @author xiaoga
     * @date 2019/5/31
     * @param
     * @return
     */
	@RequestMapping(method = RequestMethod.POST, value = "findNotExistsList")
    public String findNotExistsList(@RequestParam String params,
    		@RequestParam(required = false, defaultValue = "1")  Integer pageIndex,
            @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
    	String result = null;
        try {
            JSONObject paramJson = this.parseObject(params);
            Pagination<TempParamDefEntity_HI_RO> paramDefPage = tempParamDefServer.findNotExistsList(paramJson, pageIndex, pageRows);
            result = convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, paramDefPage);
        } catch (Exception e) {
            LOGGER.error("findAll error:{}", e);
            result = convertResultJSONObj(ERROR_STATUS,"查询失败",null);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam String params) {
        JSONObject paramJson = this.parseObject(params);
        String result = null;
        TempRuleDefEntity_HI tempRuleDefEntity_hi = null;
        try {
            tempRuleDefEntity_hi = JSON.parseObject(paramJson.toJSONString(), TempRuleDefEntity_HI.class);
            tempRuleDefServer.saveOrUpdate(tempRuleDefEntity_hi);
            result = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, tempRuleDefEntity_hi).toString();
        } catch (Exception e) {
            LOGGER.error("saveOrUpdate error:{}", e);
            result = SToolUtils.convertResultJSONObj(ERROR_STATUS,"操作失败,请检查规则项是否重复", 0,null).toString();
        }
        LOGGER.info(".saveOrUpdate 入参:{}, 出参:{}", new Object[]{paramJson, result});
        return result;
    }

    /*
    @SuppressWarnings("all")
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="download")
    public String download(@RequestParam(required = false) Integer ruleId){
        String fileName = "";
        OutputStream outputStream = null;
        try{
            RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
            if (entity == null || entity.getFileContent() == null) {
                LOGGER.info(this.getClass() + ".download 还没有上传文件!");
                return  convertResultJSONObj(ERROR_STATUS,"还没有上传文件!",null);
            }
            Clob fileContent = entity.getFileContent();
            String content = fileContent.getSubString(1, (int) fileContent.length());
            if (ruleId != null) {
                HashSet<String> wordList = new HashSet<String>();
                Pattern pattern = Pattern.compile("start\\w+end",Pattern.CASE_INSENSITIVE);
                Matcher mc = pattern.matcher(content);
                while (mc.find()) {
                    String strCt = mc.group();
                    wordList.add(strCt);
                }
                //1.通过规则查询参数信息
                HashSet<String> setList = new HashSet<String>();
                List<TempParamDefEntity_HI_RO> paramsList = tempParamDefServer.findParamsByRuleId(ruleId);
                paramsList.forEach(item->{
                    setList.add(item.getParamKey());
                });
                wordList.removeAll(setList);
                LOGGER.info("差值后的结果集:{}", wordList);
                Iterator<String> iterator = wordList.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    String keyContent = HtmlUtils.getContentKey(content, next);
                    if (StringUtils.isNotEmpty(keyContent)) {
                        content = content.replaceAll(keyContent, "");
                    }
                }
                if (paramsList != null) {
                    for (TempParamDefEntity_HI_RO paramEntity : paramsList) {
                        LOGGER.info("需要替换的KEY:{}, 需要替换的内容:{}", paramEntity.getParamKey(), paramEntity.getParamContent());
                        if (content.contains(paramEntity.getParamKey())){
                            String ct = HtmlUtils.stringFilter(paramEntity.getParamContent());
                            content = content.replace(paramEntity.getParamKey(), ct);
                        }
                    }
                }
                TempRuleDefEntity_HI ruleEntity = tempRuleDefServer.getById(ruleId);
                fileName = new String(ruleEntity.getRuleName().getBytes(), "ISO8859-1") + ".doc";
                //content = content.replaceAll("[#][{]\\w+[}]", "");
            } else {
                  fileName = new String(entity.getFileTempName().getBytes(), "ISO8859-1");
                  fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".doc";
            }

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            //response.addHeader("Content-Length", String.valueOf(file.length()));
            outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/msword");
            //response.setContentType("application/octet-stream");
            outputStream.write(new String(content).getBytes("gb2312"));// 输出文件
            outputStream.flush();
        }catch(Exception e){
            LOGGER.error("文件下载异常", e);
        } finally{
            try {
                if(null != outputStream){
                    outputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("文件下载关闭文件流异常", e);
            }
        }
        return null;
    }*/


    @SuppressWarnings("all")
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="fileDownload")
    public String fileDownload(@RequestParam(required = false) Integer ruleId){
        OutputStream out =  null;
        InputStream in = null;
        String fileName = "";
        try{
            response.reset();
            //response.setContentType("application/msword");
            response.setContentType("application/x-download");// 设置response内容的类型为附件类型
            out = response.getOutputStream();
            RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
            if (entity == null || entity.getFileContent() == null) {
                LOGGER.info(this.getClass() + ".download 还没有上传文件!");
                return  convertResultJSONObj(ERROR_STATUS,"还没有上传文件!",null);
            }
            in = entity.getFileContent().getBinaryStream();
            if (ruleId != null) {
                Map<String, Object> resultMap = generateDocx(ruleId, false);
                in = (InputStream)resultMap.get("fis");
                fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".docx";
            } else {
                fileName = new String(entity.getFileTempName().getBytes(), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }catch(Exception e){
            LOGGER.error("文件下载异常", e);
        } finally{
            try{
                if (in != null) {
                    in.close();
                }
            }catch (Exception e){
                LOGGER.error("文件下载关闭文件in流异常", e);
            }
            try {
                if(null != out){
                    out.close();
                }
            } catch (Exception e){
                LOGGER.error("文件下载关闭文件out流异常", e);
            }
        }
        return null;
    }



    public JSONObject generateDocx(Integer ruleId, boolean needUnderLineFlag) throws Exception{
        JSONObject result = new JSONObject();
        RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
        if (entity == null || entity.getFileContent() == null) {
            LOGGER.info(this.getClass() + ".download 还没有上传文件!");
             throw  new Exception(".download 还没有上传文件!");
        }
        InputStream in = entity.getFileContent().getBinaryStream();
        XWPFDocument doc = null;
        File tempFile = null;
        FileOutputStream fos = null;
        if (ruleId != null) {
            HashMap<String, String> contentMap = new HashMap<>();
            TempRuleDefEntity_HI ruleEntity = null;
            Map<String, Object> underLineMap = new HashMap<>();
            try {
                ruleEntity = tempRuleDefServer.getById(ruleId);
                LinkedHashSet<String> wordList = new LinkedHashSet<String>();//文档里面所有的单词
                LinkedHashSet<String> setList = new LinkedHashSet<String>();//定义参数里面的内容
                ArrayList<String> contentList = new ArrayList<>();//内容索引
                List<TempParamDefEntity_HI_RO> paramsList = tempParamDefServer.findParamsByRuleId(ruleId);
                paramsList.forEach(item->{
                    setList.add("{{"+ item.getParamKey() + "}}");
                    String html = HtmlUtils.removeTag(item.getParamContent()).replace("&nbsp;","").replace("&quot;","");
                    if (needUnderLineFlag) {
                        List<String> list = HtmlUtils.getTextParamsList(html);
                        TextRenderData data = null;
                        Style style = null;
                        for (String str : list) {
                            data = new TextRenderData();
                            style = new Style();
                            style.setUnderLine(true);
                            data.setStyle(style);
                            data.setText("   ");
                            underLineMap.put(str, data);
                        }
                    }
                    contentMap.put(item.getParamKey(), html);
                });
                doc = new XWPFDocument(in);
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    String text = (paragraph.getText() + "").trim();
                    String lowerStr = text.toLowerCase();
                    if (lowerStr.contains("start") && lowerStr.contains("end")) {
                        wordList.add(text);
                    }
                    contentList.add(text);
                }
                wordList.removeAll(setList);
                LOGGER.info("取差值之后：" + wordList);
                int removeIdx = 0;
                for (String word : wordList) {
                    if (word.contains("\t")) {
                        continue;
                    }
                    int contentIdx = contentList.indexOf(word);
                    if (contentIdx != -1) {
                        doc.removeBodyElement(contentIdx - removeIdx);
                        removeIdx ++;
                    }
                }
                tempFile = File.createTempFile(UUID.randomUUID().toString(), ".docx");
                fos = new FileOutputStream(tempFile);
                doc.write(fos);
            } catch (Exception e){
                LOGGER.error("操作docx 异常:{}", e);
            } finally {
                if (doc != null) {
                    doc.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
            ByteArrayOutputStream out = null;
            File file = null;
            XWPFTemplate xwpfTemplate = null;
            try {
                FileInputStream fileInputStream = null;
                XWPFTemplate template = null;
               try {
                   fileInputStream = new FileInputStream(tempFile);
                   template = XWPFTemplate.compile(fileInputStream).render(contentMap);
                   file = File.createTempFile(UUID.randomUUID().toString(), ".docx");
                   template.writeToFile(file.getPath());
               } catch (Exception e) {
                   e.getStackTrace();
               } finally {
                   if (template != null) {
                       template.close();
                   }
                   if (fileInputStream != null) {
                       fileInputStream.close();
                   }
               }
                InputStream fis = null;
                if (needUnderLineFlag) {
                    out = new ByteArrayOutputStream();
                    xwpfTemplate = XWPFTemplate.compile(file.getPath()).render(underLineMap);
                    xwpfTemplate.write(out);
                    fis = new ByteArrayInputStream(out.toByteArray());
                } else {
                    fis = new FileInputStream(file.getPath());
                }
                result.put("fileName",ruleEntity.getRuleName());
                result.put("underLineMap", underLineMap);
                result.put("fis", fis);
            } catch (Exception e) {
                LOGGER.error("写入文件异常：{}", e);
            } finally {
                if (tempFile != null) {
                    tempFile.delete();
                }
                if (out != null) {
                    out.close();
                }
                if (xwpfTemplate != null) {
                    xwpfTemplate.close();
                }
                if (file != null) {
                    file.deleteOnExit();
                }
            }
            return result;
        }
        return  null;
    }



    /**
     * 功能描述：规则文件上传
     * @author xiaoga
     * @date 2019/6/3
     * @param
     * @return
     */
    @RequestMapping(value = "/ruleFileUpload", method = RequestMethod.POST)
    public String fileUpload(@RequestParam("file") MultipartFile file){
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            String fileName = request.getParameter("fileName") + "";
            List<ResultFileEntity> list = new ArrayList<>();
            Assert.notNull(file, "上传内容为空");
            inputStream = file.getInputStream();
            JSONObject jsonObject = this.parseObject(null);
            RuleFileTemplateEntity_HI ruleEntity = JSON.parseObject(jsonObject.toJSONString(), RuleFileTemplateEntity_HI.class);
            ruleEntity.setFileTempName(fileName);
            ruleEntity.setFileType(file.getContentType());
            ruleEntity.setBusinessType("1");
            Blob blob = Hibernate.createBlob(inputStream);
            ruleEntity.setFileContent(blob);
            ruleFileTemplate.updateRuleFile(ruleEntity);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}