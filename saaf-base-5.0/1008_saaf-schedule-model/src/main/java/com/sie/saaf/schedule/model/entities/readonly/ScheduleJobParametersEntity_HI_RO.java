package com.sie.saaf.schedule.model.entities.readonly;

public class ScheduleJobParametersEntity_HI_RO {
    public ScheduleJobParametersEntity_HI_RO() {
        super();
    }
    
    public static final String QUERY_SQL = 
    " select " +
    " sjp.PARAM_ID paramId, " +
    " sjp.JOB_ID jobId, " +
    " sjp.PARAM_SEQ_NUM paramSeqNum, " +
    " sjp.PARAM_NAME paramName, " +
    " sjp.PARAM_TYPE paramType, " +
    " lv1.MEANING paramTypeMeaning, " +
    " sjp.DESCRIPTION description, " +
    " sjp.IS_REQUIRED isRequired, " +
    " sjp.IS_ENABLED isEnabled, " +
    " sjp.DEFAULT_VALUE defaultValue, " +
    " sjp.PARAM_REGION paramRegion " +
    " from schedule_job_parameters sjp, " +
    " base_lookup_values lv1 " +
    " where lv1.LOOKUP_TYPE = 'PARAM_TYPE' " +
    " and sjp.PARAM_TYPE = lv1.LOOKUP_CODE ";

    private Integer paramId;
    private Integer jobId;
    private Integer paramSeqNum;
    private String paramName;
    private String paramType;
    private String paramTypeMeaning;
    private String description;
    private String isRequired;
    private String isEnabled;
    private String defaultValue;
    private String paramRegion;

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getParamSeqNum() {
        return paramSeqNum;
    }

    public void setParamSeqNum(Integer paramSeqNum) {
        this.paramSeqNum = paramSeqNum;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamTypeMeaning() {
        return paramTypeMeaning;
    }

    public void setParamTypeMeaning(String paramTypeMeaning) {
        this.paramTypeMeaning = paramTypeMeaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getParamRegion() {
        return paramRegion;
    }

    public void setParamRegion(String paramRegion) {
        this.paramRegion = paramRegion;
    }


}
