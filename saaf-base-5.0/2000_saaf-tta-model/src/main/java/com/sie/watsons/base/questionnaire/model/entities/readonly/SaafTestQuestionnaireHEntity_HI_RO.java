package com.sie.watsons.base.questionnaire.model.entities.readonly;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.watsons.base.questionnaire.model.entities.SaafTestQuestionnaireChoiceEntity_HI;

import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * SaafTestQuestionnaireHEntity_HI_RO Entity Object
 * Sat Feb 16 14:17:41 CST 2019  Auto Generate
 */

public class SaafTestQuestionnaireHEntity_HI_RO {

	public static String QUERY_SQL="SELECT\n" +
			"  stqh.test_qn_head_id testQnHeadId,\n" +
			"  stqh.qn_title qnTitle,\n" +
			"  stqh.qn_code qnCode,\n" +
			"  stqh.qn_platform qnPlatform,\n" +
			"  stqh.qn_type qnType,\n" +
			"  stqh.org_id instId,\n" +
			"  stqh.investigate_object investigateObject,\n" +
			"  stqh.qn_status qnStatus,\n" +
			"  stqh.STATUS,\n" +
			"  stqh.break_point_answer breakPointAnswer,\n" +
			"  stqh.process_instance_id processInstanceId,\n" +
			"  stqh.start_date_active startDateActive,\n" +
			"  stqh.end_date_active endDateActive,\n" +
			"  stqh.bg_color bgColor,\n" +
			"  stqh.bg_image_path bgImagePath,\n" +
			"  stqh.description,\n" +
			"  BU.user_full_name userFullName,\n" +
			"  BLV.meaning qnTypeName\n" +
			"FROM tta_test_questionnaire_header stqh\n" +
			"  INNER JOIN base_lookup_values blv\n" +
			"    ON blv.lookup_code = stqh.qn_type\n" +
			"    AND blv.lookup_type = 'SAAF_QN_TYPE'\n" +
			"  INNER JOIN base_users bu\n" +
			"    ON bu.user_id = stqh.CREATED_BY\n" +
			"WHERE 1 = 1 ";

	public static String QUERY_LINE_AND_CHOICES = "select\n" +
			"\tqtql.TEST_QN_LINE_ID testQnLineId,\n" +
			"\tqtql.TEST_QN_HEAD_ID testQnHeadId,\n" +
			"\tqtql.PROJECT_TYPE projectType,\n" +
			"\tqtql.PROJECT_TITLE projectTitle,\n" +
			"\tqtql.PROJECT_TITLE_ALT projectTitleAlt,\n" +
			"\tqtql.REQUIRE_FLAG requireFlag,\n" +
			"\tqtql.DISPLAY_FLAG displayFlag,\n" +
			"\tqtql.DESCRIPTION description\n" +
			"from tta_test_questionnaire_line qtql\n" +
			"\twhere 1=1 ";

	public static String QUERY_LIST_LOV = "SELECT\n" +
			"  sqh.test_qn_head_id testQnHeadId,\n" +
			"  sqh.qn_title qnTitle,\n" +
			"  sqh.qn_code qnCode,\n" +
			"  sqh.qn_platform qnPlatform,\n" +
			"  sqh.qn_type qnType,\n" +
			"  sqh.org_id instId,\n" +
			"  sqh.investigate_object investigateObject,\n" +
			"  sqh.qn_status qnStatus,\n" +
			"  sqh.STATUS,\n" +
			"  sqh.break_point_answer breakPointAnswer,\n" +
			"  sqh.process_instance_id processInstanceId,\n" +
			"  sqh.start_date_active startDateActive,\n" +
			"  sqh.end_date_active endDateActive,\n" +
			"  sqh.bg_color bgColor,\n" +
			"  sqh.bg_image_path bgImagePath,\n" +
			"  sqh.description,\n" +
			"  BU.user_full_name userFullName,\n" +
			"  BLV.meaning qnTypeName\n" +
			"FROM tta_test_questionnaire_header sqh\n" +
			"  INNER JOIN base_lookup_values blv\n" +
			"    ON blv.lookup_code = sqh.qn_type\n" +
			"    AND blv.lookup_type = 'SAAF_QN_TYPE'\n" +
			"  INNER JOIN base_users bu\n" +
			"    ON bu.user_id = sqh.CREATED_BY\n" +
			"WHERE 1 = 1 \n" +
			"\tand sqh.test_qn_head_id not in (select sqp.test_qn_head_id from tta_questionnaire_publish sqp) ";

	public static void main(String[] args) {
		System.out.println(QUERY_LINE_AND_CHOICES);
	}


    private Integer testQnHeadId; //表ID，主键，供其他表做外键
    private Integer testQnLineId; //表ID，主键，供其他表做外键
    private String qnTitle; //问卷标题
    private String qnCode; //问卷编码
    private String qnPlatform; //问卷应用平台
    private String qnType; //问卷类型
    private Integer orgId; //组织ID
    private String investigateObject; //调查对象
    private String qnStatus; //问卷状态
    private String status; //状态
    private String breakPointAnswer; //断点续传
    private String processInstanceId; //流程ID
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date startDateActive; //起始日期
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date endDateActive; //终止日期
    private String description; //说明、备注
    private String bgColor; //背景颜色
    private String bgImagePath; //背景图片
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;


	private Integer qnHeadId; //表ID，主键，供其他表做外键
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private String selectQnLineId;
	//拓展字段
	private String qnChoice;
	private String qnAnswer;
	private String qnChoiceContent;
	//	private Integer instThisLeaderid;
	private String qnChoiceId;
	private String qnChoiceResult;
	private Integer qnLineId;
	private Integer serialNumber;
	private String projectTitle;
	private String projectType;
	private String requireFlag;
	private String displayFlag;
	private Integer resultNum;
	private String userFullName;
	private String qnStatusName;
	private String statusName;
	private String qnPlatformName;
	private String qnTypeName;
	private String orgName;
	private Integer maxResultNum;
	private Integer publishId;
	private String orgsId;
	private String usersId;
	private Integer userId;
	private String userName;
	private String publishCode;
	private Integer resultChoiceId;
	@JSONField(format = "yyyy-MM-dd")
	private Date publishDate;
	private List<SaafTestQuestionnaireChoiceEntity_HI> choiceList;

	public void setTestQnHeadId(Integer testQnHeadId) {
		this.testQnHeadId = testQnHeadId;
	}

	
	public Integer getTestQnHeadId() {
		return testQnHeadId;
	}

	public void setQnTitle(String qnTitle) {
		this.qnTitle = qnTitle;
	}

	
	public String getQnTitle() {
		return qnTitle;
	}

	public void setQnCode(String qnCode) {
		this.qnCode = qnCode;
	}

	
	public String getQnCode() {
		return qnCode;
	}

	public void setQnPlatform(String qnPlatform) {
		this.qnPlatform = qnPlatform;
	}

	
	public String getQnPlatform() {
		return qnPlatform;
	}

	public void setQnType(String qnType) {
		this.qnType = qnType;
	}

	
	public String getQnType() {
		return qnType;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	
	public Integer getOrgId() {
		return orgId;
	}

	public void setInvestigateObject(String investigateObject) {
		this.investigateObject = investigateObject;
	}

	
	public String getInvestigateObject() {
		return investigateObject;
	}

	public void setQnStatus(String qnStatus) {
		this.qnStatus = qnStatus;
	}

	
	public String getQnStatus() {
		return qnStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getStatus() {
		return status;
	}

	public void setBreakPointAnswer(String breakPointAnswer) {
		this.breakPointAnswer = breakPointAnswer;
	}

	
	public String getBreakPointAnswer() {
		return breakPointAnswer;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setStartDateActive(Date startDateActive) {
		this.startDateActive = startDateActive;
	}

	
	public Date getStartDateActive() {
		return startDateActive;
	}

	public void setEndDateActive(Date endDateActive) {
		this.endDateActive = endDateActive;
	}

	
	public Date getEndDateActive() {
		return endDateActive;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	
	public String getBgColor() {
		return bgColor;
	}

	public void setBgImagePath(String bgImagePath) {
		this.bgImagePath = bgImagePath;
	}

	
	public String getBgImagePath() {
		return bgImagePath;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}

	public static String getQuerySql() {
		return QUERY_SQL;
	}

	public static void setQuerySql(String querySql) {
		QUERY_SQL = querySql;
	}

	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	public String getSelectQnLineId() {
		return selectQnLineId;
	}

	public void setSelectQnLineId(String selectQnLineId) {
		this.selectQnLineId = selectQnLineId;
	}

	public String getQnChoice() {
		return qnChoice;
	}

	public void setQnChoice(String qnChoice) {
		this.qnChoice = qnChoice;
	}

	public String getQnAnswer() {
		return qnAnswer;
	}

	public void setQnAnswer(String qnAnswer) {
		this.qnAnswer = qnAnswer;
	}

	public String getQnChoiceContent() {
		return qnChoiceContent;
	}

	public void setQnChoiceContent(String qnChoiceContent) {
		this.qnChoiceContent = qnChoiceContent;
	}

	public String getQnChoiceId() {
		return qnChoiceId;
	}

	public void setQnChoiceId(String qnChoiceId) {
		this.qnChoiceId = qnChoiceId;
	}

	public String getQnChoiceResult() {
		return qnChoiceResult;
	}

	public void setQnChoiceResult(String qnChoiceResult) {
		this.qnChoiceResult = qnChoiceResult;
	}

	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getRequireFlag() {
		return requireFlag;
	}

	public void setRequireFlag(String requireFlag) {
		this.requireFlag = requireFlag;
	}

	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public Integer getResultNum() {
		return resultNum;
	}

	public void setResultNum(Integer resultNum) {
		this.resultNum = resultNum;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getQnStatusName() {
		return qnStatusName;
	}

	public void setQnStatusName(String qnStatusName) {
		this.qnStatusName = qnStatusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getQnPlatformName() {
		return qnPlatformName;
	}

	public void setQnPlatformName(String qnPlatformName) {
		this.qnPlatformName = qnPlatformName;
	}

	public String getQnTypeName() {
		return qnTypeName;
	}

	public void setQnTypeName(String qnTypeName) {
		this.qnTypeName = qnTypeName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getMaxResultNum() {
		return maxResultNum;
	}

	public void setMaxResultNum(Integer maxResultNum) {
		this.maxResultNum = maxResultNum;
	}

	public Integer getPublishId() {
		return publishId;
	}

	public void setPublishId(Integer publishId) {
		this.publishId = publishId;
	}

	public String getOrgsId() {
		return orgsId;
	}

	public void setOrgsId(String orgsId) {
		this.orgsId = orgsId;
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPublishCode() {
		return publishCode;
	}

	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}

	public Integer getResultChoiceId() {
		return resultChoiceId;
	}

	public void setResultChoiceId(Integer resultChoiceId) {
		this.resultChoiceId = resultChoiceId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getTestQnLineId() {
		return testQnLineId;
	}

	public void setTestQnLineId(Integer testQnLineId) {
		this.testQnLineId = testQnLineId;
	}

	public List<SaafTestQuestionnaireChoiceEntity_HI> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<SaafTestQuestionnaireChoiceEntity_HI> choiceList) {
		this.choiceList = choiceList;
	}
}
