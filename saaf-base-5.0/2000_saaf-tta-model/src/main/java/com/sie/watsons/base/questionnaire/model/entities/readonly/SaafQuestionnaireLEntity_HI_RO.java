package com.sie.watsons.base.questionnaire.model.entities.readonly;

import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.Transient;

/**
 * SaafQuestionnaireLEntity_HI_RO Entity Object
 * Mon Nov 12 09:28:33 CST 2018  Auto Generate
 */
//tta_questionnaire_line

public class SaafQuestionnaireLEntity_HI_RO {

	public static final String QUERY_LINE_SQL = "select a.qn_line_id,\n" +
			"       a.serial_number,\n" +
			"       a.project_type,\n" +
			"       a.project_title,\n" +
			"       a.project_title_alt,\n" +
			"       a.require_flag,\n" +
			"       a.display_flag,\n" +
			"       a.description,\n" +
			"       a.calc_rule,\n" +
			"       a.version_num,\n" +
			"       a.creation_date,\n" +
			"       a.created_by,\n" +
			"       a.last_updated_by,\n" +
			"       a.last_update_date,\n" +
			"       a.last_update_login,\n" +
			"       b.rule_name as calc_rule_name," +
            "       a.project_category\n" +
			"  from tta_questionnaire_line a\n" +
			"  left join tta_base_rule b\n" +
			"    on a.calc_rule = b.rule_id where 1 = 1 ";

	/**
	 * 功能描述： 查询第二级未被关联的题目
	 * @author xiaoga
	 * @date 2019/6/11
	 * @param  
	 * @return 
	 */
	public static final  String  QUERY_SEC_QUS_SQL = "select\n" +
			"      qn_line_id,\n" +
			"      serial_number,\n" +
			"      project_type,\n" +
			"      project_title,\n" +
			"      project_title_alt,\n" +
			"      require_flag,\n" +
			"      display_flag,\n" +
			"      description,\n" +
			"      version_num,\n" +
			"      creation_date,\n" +
			"      created_by,\n" +
			"      last_updated_by,\n" +
			"      last_update_date,\n" +
			"      last_update_login from tta_questionnaire_line a where display_flag = 'N' \n" +
			"      and not exists (select 1 from tta_subject_choice_sec_mid b where b.qn_line_id = a.qn_line_id) \n";

    private Integer qnLineId; //表ID，主键，供其他表做外键
    private Integer qnHeadId; //头ID
    private Integer serialNumber; //题目序号
    private String projectType; //题目类型(单选/多选/文本)
    private String projectTitle; //题目标题
    private String projectTitleAlt; //题目标题别名
    private String requireFlag; //是否必需标记
    private String displayFlag; //是否显示标志
    private String description; //说明、备注
    private Integer versionNum;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
	private String calcRule; //计算规则值
	private String calcRuleName;//计算规则名称
    private String projectCategory; //题目类别 1:一级题目，2:二级题目 3:规则题型

	List<SaafQuestionnaireChoiceEntity_HI_RO> qnChoiceData = new ArrayList<>();

	public void setQnLineId(Integer qnLineId) {
		this.qnLineId = qnLineId;
	}

	
	public Integer getQnLineId() {
		return qnLineId;
	}

	public void setQnHeadId(Integer qnHeadId) {
		this.qnHeadId = qnHeadId;
	}

	
	public Integer getQnHeadId() {
		return qnHeadId;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	
	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	
	public String getProjectType() {
		return projectType;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitleAlt(String projectTitleAlt) {
		this.projectTitleAlt = projectTitleAlt;
	}

	
	public String getProjectTitleAlt() {
		return projectTitleAlt;
	}

	public void setRequireFlag(String requireFlag) {
		this.requireFlag = requireFlag;
	}

	
	public String getRequireFlag() {
		return requireFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	
	public String getDisplayFlag() {
		return displayFlag;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getDescription() {
		return description;
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

	public void setCalcRule(String calcRule) {
		this.calcRule = calcRule;
	}

	public String getCalcRule() {
		return calcRule;
	}

	public String getCalcRuleName() {
		return calcRuleName;
	}

	public void setCalcRuleName(String calcRuleName) {
		this.calcRuleName = calcRuleName;
	}

	public void setQnChoiceData(List<SaafQuestionnaireChoiceEntity_HI_RO> qnChoiceData) {
		this.qnChoiceData = qnChoiceData;
	}

	public List<SaafQuestionnaireChoiceEntity_HI_RO> getQnChoiceData() {
		return qnChoiceData;
	}

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

}
