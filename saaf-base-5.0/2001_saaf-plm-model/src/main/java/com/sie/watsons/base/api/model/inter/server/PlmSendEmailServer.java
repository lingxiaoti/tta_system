package com.sie.watsons.base.api.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.enums.lookupCodeValusEnum;
import com.sie.watsons.base.api.model.inter.IPlmRmsMap;
import com.sie.watsons.base.api.model.inter.IPlmSendEmail;
import com.sie.watsons.base.api.services.PlmSendEmailService;
import com.sie.watsons.base.redisUtil.EmailUtil;
import com.sie.watsons.base.redisUtil.ResultConstant;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementHeadEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component("plmSendEmailServer")
public class PlmSendEmailServer implements IPlmSendEmail {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSendEmailServer.class);


    @Autowired
    private BaseViewObject<PlmSupplementHeadEntity_HI_RO> plmSupplementHeadVo;
    @Value("${api.attachment.orderListURL:}")
    private String orderListURL;

    @Override
    @Async
    public void timingSendEmail(Map<String, String> timeInterval) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        if (timeInterval == null || timeInterval.isEmpty()) {
            return;
        }

        if (timeInterval == null || timeInterval.isEmpty()) {
            return;
        }
        // 采购分货纸邮件
        List<String> paramList = new ArrayList<>();
        paramList.add("VMI_NOTICE_EMAIL");
//      List<JSONObject> jsonObjectList =  ResultUtils.getReturnJson(paramList);
        ResultUtils.getLookUpValue("PLM_NOTICE_EMAIL");
        JSONObject jsonObject = ResultConstant.PLM_NOTICE_EMAIL;


        Map<String, String> emails=new HashMap<>();
        if(jsonObject==null || jsonObject.isEmpty()){
            LOGGER.info("没有获取的块码中的邮箱！");
            emails.put("1","1198415652@qq.com");
            emails.put("2","daijie1@chinasie.com");
            if (emails == null || emails.isEmpty()) {
                return;
            }
        }else {
            for(  String key:jsonObject.keySet()){
                emails.put(jsonObject.getString(key),key);
            }
        }
        //        Map<String, String> emails = ttaDataClient.getLookUpValuesMap("VMI_NOTICE_EMAIL");
        LOGGER.info("生成附件:{}", emails);
        // 生成采购分货纸邮件
        emailAttachment("supplement", emails, timeInterval);

//        // 额外分货纸邮件
//        Map<String, String> opsEmails = ttaDataClient.getLookUpValuesMap("VMI_OPS_NOTICE_EMAIL");
//        if (opsEmails == null || opsEmails.isEmpty()) {
//            return;
//        }
//        // 生成额外分货纸邮件
//        emailAttachment("1", opsEmails, timeInterval);
//        LOGGER.info("生成额外分货纸邮件:{}", opsEmails);
    }

//    private Map<String, String> lookupToMap(List<JSONObject> jsonObjectList) {
//        Map<String, String> map = new HashMap<>();
//        if(!CollectionUtils.isEmpty(jsonObjectList)){
//            for (JSONObject object:jsonObjectList) {
//                map.put(object.getString("MEANING").toString(), map.getString("LOOKUP_CODE").toString());
//            }
//        }plmErrorResultToExcel
//        return map;
//    }

    /**
     * 生成邮件附件
     *
     * @return
     */
    public void emailAttachment(String attachmentType, Map<String, String> email, Map<String, String> timeInterval) {
        // 判断生成的邮件类型
        String emailName = "supplement".equals(attachmentType) ? "补货申请" : "补货清单";

        // 生成邮件正文内容
        String content = "补货清单-邮件反馈(" + emailName + "): " + timeInterval.get("startTime") + "至 " + timeInterval.get("endTime") + "生成的补货清单如下，请知悉，谢谢！";

        // 生成订货清单头
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFCellStyle cellStyle = workBook.createCellStyle();
        XSSFDataFormat format = workBook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        // 创建Excel的工作sheet,对应到一个excel文档的tab
        XSSFSheet sheet = workBook.createSheet("补货清单");
        // 设置默认列宽
        sheet.setDefaultColumnWidth(20);
//        String[] headers = new String[]{"序号", "" + emailName + "单号", "申请日期", "补货类型", "单据状态"};
        String[] headers = new String[]{"序号", "" + emailName + "单号","货号","品牌名称","生效地点","补货状态","停补原因","生效时间","失效时间","创建日期", };
        XSSFRow row = sheet.createRow(0);
        for (int k = 0; k < headers.length; k++) {
            XSSFCell cell = row.createCell(k);
            cell.setCellValue(headers[k]);
        }

        // 序号
        int i = 1;

        // 查询该时间区间内生成的所有订货清单
        List<PlmSupplementHeadEntity_HI_RO> orderList = plmSupplementHeadVo.findList(
                "SELECT psh.sup_code AS supcode ,psh.plm_supplement_head_id ,psl.plm_supplement_line_id\n" +
                "      ,psh.creation_date AS creationDate\n" +
                "      ,psh.order_type ordertype\n" +
                "      ,pph.brandname_cn  brandnameCn\n" +
                "      ,psl.rms_code  as rmsCode --货号\n" +
                "      ,psl.product_name as productName --产品名称\n" +
                "      ,psl.meter \n" +
                "      ,psl.supplement_status as supplementStatus\n" +
                "      ,psl.stop_reason as stopReason\n" +
                "      ,psl.exe_date  exeDate\n" +
                "      ,psl.expire_date expireDate\n" +
                "      ,decode(psh.order_status,'1','审批中','2','已审批') AS orderstatus \n" +
                "  FROM plm_supplement_head psh\n" +
                "  JOIN plm_supplement_line psl\n" +
                "    ON psl.head_id = psh.plm_supplement_head_id\n" +
                "  left join plm_product_head pph on pph.rms_code = psl.rms_code \n" +
                " WHERE 1 = 1\n" +
                "   AND psh.order_status = '2' \n" +
                "AND psh.creation_date >= TO_DATE ('" + timeInterval.get("startTime") + "','yyyy-MM-dd HH24:MI:SS')" +
                "AND psh.creation_date <= TO_DATE ('" + timeInterval.get("endTime") + "','yyyy-MM-dd HH24:MI:SS')");
        // 如果当前时间段未生成订单则不发生邮件;
        if(orderList == null || orderList.isEmpty()){
            return;
        }

        for (PlmSupplementHeadEntity_HI_RO order : orderList) {
            if (order == null) {
                continue;
            }

            Map<Integer, Object> orderListDetailed = new HashMap<Integer, Object>();
            // 序号
            orderListDetailed.put(0, i++);
            // 补货单号
            orderListDetailed.put(1, order.getSupCode());
            // 货号
            orderListDetailed.put(2, order.getRmsCode());
            // 品牌名称
            orderListDetailed.put(3, order.getBrandnameCn());
            // 生效地点
            orderListDetailed.put(4, order.getMeter());
            // 补货状态
            orderListDetailed.put(5, getMeaningForValue("PLM_SUP_STATUS_ALL",order.getSupplementStatus()));
            // 停补原因
            orderListDetailed.put(6, getMeaningForValue("PLM_SUP_STOP_REASON",order.getStopReason()));

            // 生效时间
            orderListDetailed.put(7, DateFormatUtils.format(order.getExeDate(), "yyyy-MM-dd") );
            // 失效时间
            orderListDetailed.put(8,order.getExpireDate()!=null? DateFormatUtils.format(order.getExpireDate(), "yyyy-MM-dd"):"");
            // 创建日期
            orderListDetailed.put(9, DateFormatUtils.format(order.getCreationDate(), "yyyy-MM-dd HH:mm:ss"));
//            // 状态
//            orderListDetailed.put(9, order.getOrderStatus());

            // 生成订货清单明细
            row = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int j = 0; j < headers.length; j++) {
                XSSFCell cell = row.createCell(j);
                // 设置单元格为文本格式
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                if (orderListDetailed.get(j) != null) {
                    cell.setCellValue(orderListDetailed.get(j).toString());
                }

            }
        }

        String startTimeStr = timeInterval.get("startTime").substring(0, timeInterval.get("startTime").indexOf(":")).replace(" ", "").replace("-", "");
        String endTimeStr = timeInterval.get("endTime").substring(0, timeInterval.get("endTime").indexOf(":")).replace(" ", "").replace("-", "");
        String url = (StringUtils.isEmpty(orderListURL)?"/home/sieuat/file/attachment/":orderListURL )+ "补货清单" + startTimeStr + "-" + endTimeStr + ".xlsx";
//        String url = (StringUtils.isEmpty(orderListURL)?"E:\\home\\sieuat\\file\\attachement\\":orderListURL )+ "订货清单" + startTimeStr + "-" + endTimeStr + ".xlsx";

        // 如果该文件存在，生成该文件再生成
        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }
        LOGGER.info("附件参数："+"sendTo："+email.values().toArray(new String[email.size()]).toString()
                +"  subjuct" +"(UATTEST)补货:" + timeInterval.get("startTime") + "至 " + timeInterval.get("endTime")
                + "  content::: "+content+"url:: "+url);
        OutputStream output = null;
        try {
            output = new FileOutputStream(url);
            workBook.write(output);//写入磁盘
            LOGGER.info("生成订货清单excel成功:{}", url);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        EmailUtil.sendMailFromWatsons(email.values().toArray(new String[email.size()]),"(UATTEST)补货:" + timeInterval.get("startTime") + "至 " + timeInterval.get("endTime"),content,url);
//        EmailUtil.SendEmail("(UATTEST)补货:" + timeInterval.get("startTime") + "至 " + timeInterval.get("endTime"), content, url, email.keySet().toArray(new String[email.size()]));
    }

    String getMeaningForValue(String type ,String values) {
        EnumSet<lookupCodeValusEnum> enumSet = EnumSet.allOf(lookupCodeValusEnum.class);
        for ( lookupCodeValusEnum e:enumSet){
            if (e.getType().equals(type) && e.getValues().equals(values)){
                return e.getMeaning();
            }
        }
        return "";
    }
}
