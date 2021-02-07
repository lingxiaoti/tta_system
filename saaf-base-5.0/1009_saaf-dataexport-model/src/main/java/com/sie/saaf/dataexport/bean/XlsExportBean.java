package com.sie.saaf.dataexport.bean;

public class XlsExportBean {

    //excel 表头名
    private String labelName;
    //参数名
    private String attributeNames;
    //excle sheet名
    private String sheetName="sheet1";
    //导出数量
    private Integer exportNum=5000;
    //数据请求服务地址
    private String requestUrl;
    //请求参数
    private String requestParam;
    //查询条件  当作邮件发送内容
    private String requestParamConten;
    //邮件主题
    private String emailSubject="数据导出";
    //是否post请求
    private boolean post=true;
    //当前页参数名
    private String pageIndexParamName="pageIndex";
    //分页大小参数名
    private String pageRowsParamName="pageRows";
    //数据总数
    private String dataCount="count";
    //数据结构
    private String structure="data";
    //token参数名
    private String toakenAttributeName="certificate";
    //token ，请求认证信息
    private String token;
    //收件人邮箱
    private String email;

    private String exportId;

    private String fileUrl;


    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getAttributeNames() {
        return attributeNames;
    }

    public void setAttributeNames(String attributeNames) {
        this.attributeNames = attributeNames;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getExportNum() {
        return exportNum;
    }

    public void setExportNum(Integer exportNum) {
        this.exportNum = exportNum;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }

    public String getPageIndexParamName() {
        return pageIndexParamName;
    }

    public void setPageIndexParamName(String pageIndexParamName) {
        this.pageIndexParamName = pageIndexParamName;
    }

    public String getPageRowsParamName() {
        return pageRowsParamName;
    }

    public void setPageRowsParamName(String pageRowsParamName) {
        this.pageRowsParamName = pageRowsParamName;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getDataCount() {
        return dataCount;
    }

    public void setDataCount(String dataCount) {
        this.dataCount = dataCount;
    }

    public String getRequestParamConten() {
        return requestParamConten;
    }

    public void setRequestParamConten(String requestParamConten) {
        this.requestParamConten = requestParamConten;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getToakenAttributeName() {
        return toakenAttributeName;
    }

    public void setToakenAttributeName(String toakenAttributeName) {
        this.toakenAttributeName = toakenAttributeName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
