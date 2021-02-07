package com.sie.watsons.base.questionnaire.model.entities.readonly;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Version;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireHEntity_HI_RO Entity Object
 * Tue Nov 13 15:56:38 CST 2018  Auto Generate
 */

public class SaafQuestionnaireHEntity_HI_RO {

	public static String QUERY_SQL="SELECT\n" +
			"  sqh.qn_head_id qnHeadId,\n" +
			"  sqh.qn_title qnTitle,\n" +
			"  sqh.qn_code qnCode,\n" +
			"  sqh.qn_platform qnPlatform,\n" +
			"  sqh.qn_type qnType,\n" +
			"  sqh.org_id instId,\n" +
			"  sqh.investigate_object investigateObject,\n" +
			"  sqh.qn_status qnStatus,\n" +
			"  sqh.status,\n" +
			"  sqh.break_point_answer breakPointAnswer,\n" +
			"  sqh.process_instance_id processInstanceId,\n" +
			"  sqh.start_date_active startDateActive,\n" +
			"  sqh.end_date_active endDateActive,\n" +
			"  sqh.bg_color bgColor,\n" +
			"  sqh.bg_image_path bgImagePath,\n" +
			"  sqh.description,\n" +
			"  sqh.creation_date,\n" +
			"  BU.user_full_name userFullName,\n" +
			"  BLV.meaning qnTypeName\n" +
			"FROM tta_questionnaire_header sqh\n" +
			"  LEFT JOIN base_lookup_values blv\n" +
			"    ON blv.lookup_code = sqh.qn_type\n" +
			"    AND blv.lookup_type = 'SAAF_QN_TYPE'\n" +
			"  INNER JOIN base_users bu\n" +
			"    ON bu.user_id = sqh.CREATED_BY\n" +
			"WHERE 1 = 1 ";

	public static String QUERY_SQL_USER_ID="SELECT\n" +
			"  bu.user_id userId\n" +
			"FROM base_users bu\n" +
			"WHERE 1 = 1 ";

	public static String QUERY_SQL_PUBLISH_INFO="SELECT\n" +
			"  sqh.test_qn_HEAD_ID testQnHeadId,\n" +
			"  sqh.qn_CODE qnCode,\n" +
			"  sqh.qn_TITLE qnTitle,\n" +
			"  sqp.PUBLISH_ID publishId,\n" +
			"  sqp.LAST_UPDATE_DATE publishDate,\n" +
			"  sqp.ORGS_ID orgsId,\n" +
			"  sqp.START_DATE_ACTIVE startDateActive,\n" +
			"  sqp.END_DATE_ACTIVE endDateActive,\n" +
			"  sqp.USERS_ID usersId,\n" +
			"  sqp.STATUS status,\n" +
			"  sqp.publish_code publishCode,\n" +
			"  sqp.flow_status flowStatus,\n" +
			"  sqp.process_instance_id processInstanceId,\n" +
			"  sqh.bg_color bgColor,\n" +
			"  sqh.bg_image_path bgImagePath,\n" +
			"  sqh.description,\n" +
			"  bu.user_full_name userFullName,\n" +
			"  sqp.qn_type qnType,\n" +
			"  blv.meaning statusName,\n" +
			"  blv1.meaning qnTypeName\n" +
			"FROM tta_questionnaire_publish sqp,\n" +
			"     tta_test_questionnaire_header sqh,\n" +
			"     base_users bu,\n" +
			"     base_lookup_values blv,\n" +
			"     base_lookup_values blv1\n" +
			"WHERE 1 = 1\n" +
			"AND sqp.test_qn_HEAD_ID = sqh.test_qn_HEAD_ID\n" +
			"AND sqp.created_by = bu.user_id\n" +
			"AND blv.lookup_type = 'SAAF_QN_STATUS'\n" +
			"AND blv.lookup_code = sqp.STATUS\n" +
			"AND blv1.lookup_type = 'SAAF_QN_TYPE'\n" +
			"AND blv1.lookup_code = sqp.qn_type ";

	public static String QUERY_USER_NAME_BY_CITY_ID="SELECT\n" +
			"  bu.user_id userId,\n" +
			"  bu.user_name userName,\n" +
			"  bu.user_full_name userFullName,bpo.org_id\n" +
			"FROM base_users bu\n" +
			"  INNER JOIN base_person_organization bpo\n" +
			"    ON CONCAT(bpo.person_id, '') = bu.person_id ";

	public static String QUERY_USERS_NAME="SELECT\n" +
			"  bu.user_id userId,\n" +
			"  bu.user_name userName,\n" +
			"  bu.user_full_name userFullName\n" +
			"FROM base_users bu\n" +
			"WHERE 1 = 1 ";

	public static String QUERY_MAX_RESULT_NUM="SELECT\n" +
			"  NVL(MAX(sqr.RESULT_NUM), 0) maxResultNum\n" +
			"FROM tta_questionnaire_result sqr\n" +
			"WHERE 1 = 1 ";

//	public static String QUERY_RESULT_PERSON="SELECT DISTINCT\n" +
//			"  sqr.RESULT_NUM resultNum,\n" +
//			"  sqr.PUBLISH_ID publishId,\n" +
//			"  sqr.qn_HEAD_ID qnHeadId,\n" +
//			"  sqh.qn_CODE qnCode,\n" +
//			"  sqh.qn_TITLE qnTitle,\n" +
//			"  sqh.qn_TYPE qnType,\n" +
//			"  bu.user_id userId,\n" +
//			"  bu.user_full_name userFullName\n" +
//			"FROM tta_questionnaire_result sqr\n" +
//			"  INNER JOIN tta_questionnaire_header sqh\n" +
//			"    ON sqr.qn_HEAD_ID = sqh.qn_HEAD_ID\n" +
//			"  LEFT JOIN base_users bu\n" +
//			"    ON sqr.CREATED_BY = bu.user_id\n" +
//			"WHERE 1 = 1 ";

	//update on 2018-08-23增加几个字段用于报表那里
	public static String QUERY_RESULT_PERSON="select\n" +
			"  sqr.RESULT_NUM resultNum,\n" +
			"  sqr.PUBLISH_ID publishId,\n" +
			"  sqr.qn_HEAD_ID qnHeadId,\n" +
			"  sqr.RESULT_CHOICE_ID resultChoiceId,\n" +
			"  sqr.QN_CHOICE_ID qnChoiceId,\n" +
			"  sqr.QN_CHOICE_RESULT qnChoiceResult,\n" +
			"  sqr.QN_LINE_ID qnLineId,\n" +
			"  sqh.qn_CODE qnCode,\n" +
			"  sqh.qn_TITLE qnTitle,\n" +
			"  sqh.qn_TYPE qnType,\n" +
			"  bu.user_id userId,\n" +
			"  bu.user_full_name userFullName\n" +
			"from tta_questionnaire_result sqr,\n" +
			"\t tta_test_questionnaire_header sqh,\n" +
			"\tbase_users bu\n" +
			"where sqr.qn_HEAD_ID = sqh.test_qn_HEAD_ID\n" +
			"\tand sqr.CREATED_BY = bu.user_id ";

	public static String CHECK_RESULT_PERSON="SELECT\n" +
			"  sqr.PUBLISH_ID publishId,\n" +
			"  sqr.RESULT_NUM resultNum,\n" +
			"  sqr.QN_CHOICE_ID qnChoiceId,\n" +
			"  sqr.QN_CHOICE_RESULT qnChoiceResult,\n" +
			"  sqr.QN_LINE_ID qnLineId,\n" +
			"  hsql.SERIAL_NUMBER serialNumber,\n" +
			"  hsql.PROJECT_TITLE projectTitle,\n" +
			"  hsql.PROJECT_TYPE projectType,\n" +
			"  hsql.REQUIRE_FLAG requireFlag,\n" +
			"  hsql.DISPLAY_FLAG displayFlag\n" +
			"FROM tta_questionnaire_result sqr\n" +
			"  INNER JOIN tta_test_questionnaire_line hsql\n" +
			"    ON hsql.TEST_QN_LINE_ID = sqr.QN_LINE_ID\n" +
			"WHERE 1 = 1 ";

	//获取标题
	public static String QUERY_PROJECT_SQL="SELECT\n" +
			"  hsql.QN_LINE_ID qnLineId,\n" +
			"  hsql.SERIAL_NUMBER serialNumber,\n" +
			"  hsql.PROJECT_TITLE projectTitle\n" +
			"FROM tta_questionnaire_line hsql\n" +
			"WHERE 1 = 1  ";

	public static String QUERY_QnChoice_SQL="SELECT\n" +
			"  sqp.PUBLISH_ID publishId,\n" +
			"  sqp.publish_code publishCode,\n" +
			"  sqc.TEST_QN_CHOICE_ID qnChoiceId,\n" +
			"  sqc.TEST_QN_HEAD_ID testQnHeadId,\n" +
			"  sqc.SELECT_QN_LINE_ID selectQnLineId,\n" +
			"  sqc.QN_ANSWER qnAnswer,\n" +
			"  sqc.QN_CHOICE qnChoice,\n" +
			"  sqc.QN_CHOICE_CONTENT qnChoiceContent,\n" +
			"  sqc.TEST_QN_LINE_ID testQnLineId,\n" +
			"  sqc.DISPLAY_FLAG displayFlag\n" +
			"FROM tta_test_questionnaire_choice sqc,\n" +
			"     tta_questionnaire_publish sqp\n" +
			"WHERE sqc.TEST_QN_HEAD_ID = sqp.TEST_QN_HEAD_ID ";

	public static String QUERY_LINE = "select\n" +
			"\tsqll.QN_LINE_ID qnLineId,\n" +
			"\tsqll.QN_HEAD_ID qnHeadId,\n" +
			"\tsqll.SERIAL_NUMBER serialNumber,\n" +
			"\tsqll.PROJECT_TYPE projectType,\n" +
			"\tsqll.PROJECT_TITLE projectTitle,\n" +
			"\tsqll.PROJECT_TITLE_ALT projectTitleAlt,\n" +
			"\tsqll.REQUIRE_FLAG requireFlag,\n" +
			"\tsqll.REQUIRE_FLAG checked,\n" +
			"\tsqll.DISPLAY_FLAG displayFlag,\n" +
			"\tsqll.DESCRIPTION descripiton\n" +
			"from tta_questionnaire_header sqh,\n" +
			"\t tta_questionnaire_line sqll\n" +
			"where sqh.QN_HEAD_ID = sqll.QN_HEAD_ID\n" +
			"\tand sqh.QN_TYPE = :qnType ";

	public static void main(String[] args) {
		System.out.println(QUERY_RESULT_PERSON);
	}

    private Integer qnHeadId; //表ID，主键，供其他表做外键
    private Integer testQnHeadId;
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
	private String projectTitleAlt;
	private String checked;
	private List choiceArr;

	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	
	public Integer getQnHeadId() {
		return qnHeadId;
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

	public String getProjectTitleAlt() {
		return projectTitleAlt;
	}

	public void setProjectTitleAlt(String projectTitleAlt) {
		projectTitleAlt = projectTitleAlt;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public List getChoiceArr() {
		return choiceArr;
	}

	public void setChoiceArr(List choiceArr) {
		this.choiceArr = choiceArr;
	}

	public Integer getTestQnHeadId() {
		return testQnHeadId;
	}

	public void setTestQnHeadId(Integer testQnHeadId) {
		this.testQnHeadId = testQnHeadId;
	}
}
