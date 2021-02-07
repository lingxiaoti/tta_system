package com.sie.watsons.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    /**
     * 生产账户随机密码
     */
    public static String generateRandomPassword(int len) {
        // 1、定义基本字符串baseStr和出参password
        String password = null;
        String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%";
        boolean flag = false;
        // 2、使用循环来判断是否是正确的密码
        while (!flag) {
            // 密码重置
            password = "";
            // 个数计数
            int a = 0, b = 0, c = 0, d = 0;
            for (int i = 0; i < len; i++) {
                int rand = (int) (Math.random() * baseStr.length());
                password += baseStr.charAt(rand);
                if (0 <= rand && rand < 10) {
                    a++;
                }
                if (10 <= rand && rand < 36) {
                    b++;
                }
                if (36 <= rand && rand < 62) {
                    c++;
                }
                if (62 <= rand && rand < baseStr.length()) {
                    d++;
                }
            }
            flag = (a * b * c * d != 0);
        }
        return password;
    }

    /**
     * owner驳回供应商资质信息填写，发送邮件到供应商内容
     */
    public static String generateZZSCRejectMailContent(String supplierName){
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(supplierName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "您提交资质信息已被驳回，驳回原因详见表单内“驳回原因”，请及时登录屈臣氏电子采购系统完善信息并重新提交。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 供应商资质信息填写，发送邮件到owner内容
     */
    public static String generateZZSCSupplierMailContent(String ownerName,String supplierName){
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(ownerName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "由您发起新增的供应商");
        buff.append(supplierName);
        buff.append("已把资质信息填写完毕，请登陆屈臣氏电子采购系统审阅。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        return buff.toString();
    }

    /**
     * 供应商CSR审核结束，发送邮件到owner内容
     */
    public static String generateCSRCompleteMailContent(String ownerName,String supplierName){
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(ownerName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "新增供应商");
        buff.append(supplierName);
        buff.append("的CSR审核信息已由QA同事填写完毕，请登陆屈臣氏电子采购系统审阅，并进行后续供应商入库流程。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        return buff.toString();
    }

    /**
     * 供应商质量审核结束，发送邮件到owner内容
     */
    public static String generateQualityCompleteMailContent(String ownerName,String supplierName){
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(ownerName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "新增供应商");
        buff.append(supplierName);
        buff.append("的质量审核信息已由QA同事填写完毕，请登陆屈臣氏电子采购系统审阅，并进行后续供应商入库流程。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        return buff.toString();
    }

    /**
     * 封装发送邮件内容
     */
    public static String generateMailContent(String supplierName,String contactName, String userName, String userPwd) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("<p class=\"MsoNormal\">\n" +
                "\t尊敬的");
        buff.append(contactName);
        buff.append("，您好！ \n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t恭喜您，贵司(" + supplierName + ")已成功注册屈臣氏电子采购系统！ \n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t您注册的登录账号为：");
        buff.append(userName);
        buff.append("，登录密码为：");
        buff.append(userPwd);
        buff.append("\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t&nbsp;\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t请尽快登录系统，完善公司资料，谢谢！\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t&nbsp;\n" +
                "</p>\n" +
                "链接：");
        buff.append("https://supplierportal.watsonsvip.com.cn/portal/#/entrance");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 封装发送邮件内容
     */
    public static String generateMailContent2(String supplierName,String contactName, String userName, String userPwd) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("<p class=\"MsoNormal\">\n" +
                "\t尊敬的");
        buff.append(contactName);
        buff.append("，您好！ \n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t恭喜您，贵司(" + supplierName + ")已成功注册屈臣氏电子采购系统！ \n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t您注册的登录账号为：");
        buff.append(userName);
        buff.append("，登录密码为：");
        buff.append(userPwd);
        buff.append("\n" +
                "</p>\n" +
                "<p class=\"MsoNormal\" style=\"text-indent:21.0pt;\">\n" +
                "\t&nbsp;\n" +
                "</p>\n" +
                "链接：");
        buff.append("https://supplierportal.watsonsvip.com.cn/portal/#/entrance");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 发送邮件.确认立项信息不修改提交至见证小组
     */
    public static String witnessMailContent(String contactName, String projectName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(contactName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "竞价项目"+projectName+"立项信息及相关资料已被上传至屈臣氏电子采购系统，请及时登录系统审阅。<br />\n" +
                "见证小组对此项目的立项信息及相关资料全部确认后，供应商的非价格报价文件将于截标日期后自动开启。<br />\n" +
                "若您对审批单中的信息存疑，可使用“驳回”功能：使用“驳回”功能，表单将被直接驳回至发起人。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 发送邮件.见证人员驳回通知owner
     */
    public static String rejectWitnessMailContent(String contactName, String projectName,String option) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(contactName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "您" + option + "的竞价项目"+projectName+"立项信息确认已被见证小组驳回，驳回原因详见表单，请及时登录屈臣氏电子采购系统完善信息并重新提交。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 发送邮件.见证人员驳回通知owner
     */
    public static String openWitnessMailContent(String contactName, String projectName, String openName,String option) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(contactName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "您" + option + "的竞价项目"+projectName+"立项信息被见证小组成员"+openName+"审核通过，详情请登陆屈臣氏电子采购系统查阅"+"。<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 发送邮件.见证人员驳回通知owner
     */
    public static String submitWitnessMailContent(String contactName, String projectName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("尊敬的");
        buff.append(contactName);
        buff.append("，<br />\n" +
                "<br />\n" +
                "竞价项目"+projectName+"的所有供应商非报价资料已自动开启，请及时登录屈臣氏电子采购系统审阅并填写审阅意见。"+"<br />\n" +
                "<br />\n" +
                "感谢您的合作。<br />\n" +
                "<br />\n" +
                "屈臣氏电子采购系统<br />\n" +
                "<br />\n" +
                "（本邮件由系统自动发送，请不要回复）<br />");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 发送邮件.立项审批通过后通知owner
     */
    public static String projectAllowMailContent(String projectNumber) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("立项单据：");
        buff.append(projectNumber);
        buff.append("已审批完成，请核实变更内容，确认是否登录系统重新发送竞价邀请！");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 终止项目完成后，需要通知到witness panel
     */
    public static String projectTerminateMailContent(String projectName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<html><body>");
        buff.append("Dear all,<br />");
        buff.append("请知悉").append(projectName).append("项目经审批，终止报价。");
        buff.append("</body></html>");
        return buff.toString();
    }

    /**
     * 流程待办审批通过后，邮件通知owner
     */
    public static String processApprovalMailContent(String submitDate,String bpmTitle,String ownerName,String approvalName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<p>尊敬的").append(ownerName).append(",<br />");
        buff.append(submitDate).append("发起的").append(bpmTitle).append("已通过").append(approvalName).append("的审批，谢谢。");
        buff.append("</p>");
        return buff.toString();
    }

    /**
     * 去除首尾指定字符
     * @param str   字符串
     * @param element   指定字符
     * @return
     */
    public static String trimFirstAndLastChar(String str, String element){
        boolean beginIndexFlag = true;
        boolean endIndexFlag = true;
        do{
            int beginIndex = str.indexOf(element) == 0 ? 1 : 0;
            int endIndex = str.lastIndexOf(element) + 1 == str.length() ? str.lastIndexOf(element) : str.length();
            str = str.substring(beginIndex, endIndex);
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) + 1 == str.length());
        } while (beginIndexFlag || endIndexFlag);
        return str;
    }

    public static void processApprovalEmailToOwner(JSONObject parseObject,Integer ownerId,String billNumber){
        try{
            if("APPROVAL".equals(parseObject.getString("status")) || "ALLOW".equals(parseObject.getString("status"))){

                JSONObject reqParams = new JSONObject();
                JSONObject paramsObj = new JSONObject();
                paramsObj.put("billNo",billNumber);
                paramsObj.put("searchAll","true");
                reqParams.put("params", paramsObj);
                JSONObject resultJson = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/bpmServer/bpmListService/findBpmLists", reqParams);
                JSONArray bpmList = resultJson.getJSONArray("data");
                JSONObject bpmJson = bpmList.getJSONObject(0);
                Date startTime = bpmJson.getDate("startTime");
                String bpmTitle = bpmJson.getString("title");

                //查询提交流程日期
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String submitDate = format.format(startTime);

                //发送邮件通知owner
                Integer approvalId = parseObject.getIntValue("userId"); //审批人id

                if(approvalId.intValue() != ownerId.intValue()){
                    //查询待办人姓名
                    JSONObject paramsJson1 = new JSONObject();
                    paramsJson1.put("userId", approvalId);
                    JSONObject resultJson1 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson1);
                    String approvalName = resultJson1.getString("personNameCn");

                    //查询owner姓名和邮箱
                    JSONObject paramsJson2 = new JSONObject();
                    paramsJson2.put("userId", ownerId);
                    JSONObject resultJson2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/getUserInfo", paramsJson2);
                    String ownerName = resultJson2.getString("personNameCn");
                    String sendTo = resultJson2.getString("email");

                    String subject = "待办审批通过通知";
                    String content = CommonUtils.processApprovalMailContent(submitDate,bpmTitle,ownerName,approvalName);
                    EmailUtil.sendInMail(subject,content,sendTo);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    //涉及第三方感官测试的项目，评分时感官测试分数因为必须要等非价格评分审批完之后再填，所以需要增加一个提示邮件给QA
    public static String scoreAllowMailContent(String ownerName,String projectName) throws Exception {
        StringBuffer buff = new StringBuffer();
        buff.append("<p>尊敬的").append(ownerName).append(",<br />");
        buff.append(projectName).append("项目非价格部分评分已通过见证小组审核，请登录系统录入感官测试结果，谢谢。");
        buff.append("</p>");
        return buff.toString();
    }

    public static void interfaceAccessControl(Integer operationRespId,String menuCode) throws Exception{
        JSONObject paramObj = new JSONObject();
        JSONObject param = new JSONObject();
        param.put("responsibilityId", operationRespId);
        param.put("menuCode", menuCode);
        paramObj.put("params", param);
        JSONObject result2 = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/ttadataServer" + "/ttadata/ttaBaseDataService/v2/findInterfaceAccessControl", paramObj);
        JSONObject obj = result2.getJSONArray("data").getJSONObject(0);
        Integer result3 = obj.getInteger("result");
        if(result3 == 0){
            throw new Exception("抱歉,当前职责没有访问该接口的权限！");
        }
    }
}
