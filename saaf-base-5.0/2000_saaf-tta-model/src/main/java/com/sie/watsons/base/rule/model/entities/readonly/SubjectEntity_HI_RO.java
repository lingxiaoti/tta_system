package com.sie.watsons.base.rule.model.entities.readonly;

import java.util.Date;


/**
 * TempParamRuleMidleEntity_HI_RO Entity Object
 * Wed May 29 16:22:30 CST 2019  Auto Generate
 */

public class SubjectEntity_HI_RO {

	    public  static  final String QUERY_SUBJECT_BY_RULE = "select qn_head_id from tta_base_subject t where t.rule_id in (:ruleIds) ";
		private Integer subjectId;
	    private Integer qnHeadId;
	    private Integer ruleId;
	    private String projectTitle;
	    private Date creationDate;
	    private Integer createdBy;
	    private Date lastUpdateDate;
	    private Integer lastUpdateLogin;
	    private Integer lastUpdatedBy;
	    private Integer versionNum;
		public Integer getSubjectId() {
			return subjectId;
		}
		public void setSubjectId(Integer subjectId) {
			this.subjectId = subjectId;
		}
		public Integer getQnHeadId() {
			return qnHeadId;
		}
		public void setQnHeadId(Integer qnHeadId) {
			this.qnHeadId = qnHeadId;
		}
		public Integer getRuleId() {
			return ruleId;
		}
		public void setRuleId(Integer ruleId) {
			this.ruleId = ruleId;
		}
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public Integer getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(Integer createdBy) {
			this.createdBy = createdBy;
		}
		public Date getLastUpdateDate() {
			return lastUpdateDate;
		}
		public void setLastUpdateDate(Date lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}
		public Integer getLastUpdateLogin() {
			return lastUpdateLogin;
		}
		public void setLastUpdateLogin(Integer lastUpdateLogin) {
			this.lastUpdateLogin = lastUpdateLogin;
		}
		public Integer getLastUpdatedBy() {
			return lastUpdatedBy;
		}
		public void setLastUpdatedBy(Integer lastUpdatedBy) {
			this.lastUpdatedBy = lastUpdatedBy;
		}
		public Integer getVersionNum() {
			return versionNum;
		}
		public void setVersionNum(Integer versionNum) {
			this.versionNum = versionNum;
		}
		public String getProjectTitle() {
			return projectTitle;
		}
		public void setProjectTitle(String projectTitle) {
			this.projectTitle = projectTitle;
		}
		
}
