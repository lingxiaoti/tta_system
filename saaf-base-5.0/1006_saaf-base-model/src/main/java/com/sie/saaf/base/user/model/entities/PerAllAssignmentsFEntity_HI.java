package com.sie.saaf.base.user.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

import javax.persistence.Transient;

/**
 * PerAllAssignmentsFEntity_HI Entity Object
 * Mon Apr 23 21:28:24 CST 2018  Auto Generate
 */
@Entity
@Table(name = "per_all_assignments_f")
public class PerAllAssignmentsFEntity_HI {
    private Integer assignmentId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartDatetime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndDatetime;
    private Integer businessGroupId;
    private Integer recruiterId;
    private Integer gradeId;
    private Integer positionId;
    private Integer jobId;
    private Integer assignmentStatusTypeId;
    private Integer payrollId;
    private Integer locationId;
    private Integer personReferredById;
    private Integer supervisorId;
    private Integer specialCeilingStepId;
    private Integer personId;
    private Integer recruitmentActivityId;
    private Integer sourceOrganizationId;
    private Integer organizationId;
    private Integer peopleGroupId;
    private Integer softCodingKeyflexId;
    private Integer vacancyId;
    private Integer payBasisId;
    private Integer assignmentSequence;
    private String assignmentType;
    private String primaryFlag;
    private Integer applicationId;
    private String assignmentInt;
    private String changeReason;
    private Integer commentId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date datetimeProbationEnd;
    private Integer defaultCodeCombId;
    private String employmentCategory;
    private String frequency;
    private String internalAddressLine;
    private String managerFlag;
    private BigDecimal normalHours;
    private Integer perfReviewPeriod;
    private String perfReviewPeriodFrequency;
    private Integer periodOfServiceId;
    private BigDecimal probationPeriod;
    private String probationUnit;
    private Integer salReviewPeriod;
    private String salReviewPeriodFrequency;
    private Integer setOfBooksId;
    private String sourceType;
    private String timeNormalFinish;
    private String timeNormalStart;
    private String bargainingUnitCode;
    private String labourUnionMemberFlag;
    private String hourlySalariedCode;
    private Integer contractId;
    private Integer collectiveAgreementId;
    private Integer cagrIdFlexNum;
    private Integer cagrGradeDefId;
    private Integer establishmentId;
    private Integer requestId;
    private Integer programApplicationId;
    private Integer programId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date programUpdatetimeDatetime;
    private String assAttributeCategory;
    private String assAttribute1;
    private String assAttribute2;
    private String assAttribute3;
    private String assAttribute4;
    private String assAttribute5;
    private String assAttribute6;
    private String assAttribute7;
    private String assAttribute8;
    private String assAttribute9;
    private String assAttribute10;
    private String assAttribute11;
    private String assAttribute12;
    private String assAttribute13;
    private String assAttribute14;
    private String assAttribute15;
    private String assAttribute16;
    private String assAttribute17;
    private String assAttribute18;
    private String assAttribute19;
    private String assAttribute20;
    private String assAttribute21;
    private String assAttribute22;
    private String assAttribute23;
    private String assAttribute24;
    private String assAttribute25;
    private String assAttribute26;
    private String assAttribute27;
    private String assAttribute28;
    private String assAttribute29;
    private String assAttribute30;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatetimeDatetime;
    private Integer lastUpdatetimedBy;
    private Integer lastUpdatetimeLogin;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDatetime;
    private String title;
    private Integer objectVersionInt;
    private Integer noticePeriod;
    private String noticePeriodUom;
    private String employeeCategory;
    private String workAtHome;
    private String jobPostSourceName;
    private Integer postingContentId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date periodOfPlacementDatetimeStart;
    private Integer vendorId;
    private String vendorEmployeeInt;
    private String vendorAssignmentInt;
    private String assignmentCategory;
    private String projectTitle;
    private Integer applicantRank;
    private Integer vendorSiteId;
    private Integer poHeaderId;
    private Integer poLineId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date projectedAssignmentEnd;
    private Integer gradeLadderPgmId;
    private Integer supervisorAssignmentId;
    private Integer operatorUserId;

	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_PER_ALL_ASSIGNMENTS_F", sequenceName = "SEQ_PER_ALL_ASSIGNMENTS_F", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_PER_ALL_ASSIGNMENTS_F", strategy = GenerationType.SEQUENCE)	
	@Column(name = "assignment_id", nullable = false, length = 11)	
	public Integer getAssignmentId() {
		return assignmentId;
	}

	public void setEffectiveStartDatetime(Date effectiveStartDatetime) {
		this.effectiveStartDatetime = effectiveStartDatetime;
	}

	@Column(name = "effective_start_datetime", nullable = false, length = 0)	
	public Date getEffectiveStartDatetime() {
		return effectiveStartDatetime;
	}

	public void setEffectiveEndDatetime(Date effectiveEndDatetime) {
		this.effectiveEndDatetime = effectiveEndDatetime;
	}

	@Column(name = "effective_end_datetime", nullable = false, length = 0)	
	public Date getEffectiveEndDatetime() {
		return effectiveEndDatetime;
	}

	public void setBusinessGroupId(Integer businessGroupId) {
		this.businessGroupId = businessGroupId;
	}

	@Column(name = "business_group_id", nullable = false, length = 11)	
	public Integer getBusinessGroupId() {
		return businessGroupId;
	}

	public void setRecruiterId(Integer recruiterId) {
		this.recruiterId = recruiterId;
	}

	@Column(name = "recruiter_id", nullable = true, length = 11)	
	public Integer getRecruiterId() {
		return recruiterId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "grade_id", nullable = true, length = 11)	
	public Integer getGradeId() {
		return gradeId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	@Column(name = "position_id", nullable = true, length = 11)	
	public Integer getPositionId() {
		return positionId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	@Column(name = "job_id", nullable = true, length = 11)	
	public Integer getJobId() {
		return jobId;
	}

	public void setAssignmentStatusTypeId(Integer assignmentStatusTypeId) {
		this.assignmentStatusTypeId = assignmentStatusTypeId;
	}

	@Column(name = "assignment_status_type_id", nullable = false, length = 11)	
	public Integer getAssignmentStatusTypeId() {
		return assignmentStatusTypeId;
	}

	public void setPayrollId(Integer payrollId) {
		this.payrollId = payrollId;
	}

	@Column(name = "payroll_id", nullable = true, length = 11)	
	public Integer getPayrollId() {
		return payrollId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	@Column(name = "location_id", nullable = true, length = 11)	
	public Integer getLocationId() {
		return locationId;
	}

	public void setPersonReferredById(Integer personReferredById) {
		this.personReferredById = personReferredById;
	}

	@Column(name = "person_referred_by_id", nullable = true, length = 11)	
	public Integer getPersonReferredById() {
		return personReferredById;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	@Column(name = "supervisor_id", nullable = true, length = 11)	
	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSpecialCeilingStepId(Integer specialCeilingStepId) {
		this.specialCeilingStepId = specialCeilingStepId;
	}

	@Column(name = "special_ceiling_step_id", nullable = true, length = 11)	
	public Integer getSpecialCeilingStepId() {
		return specialCeilingStepId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Column(name = "person_id", nullable = false, length = 11)	
	public Integer getPersonId() {
		return personId;
	}

	public void setRecruitmentActivityId(Integer recruitmentActivityId) {
		this.recruitmentActivityId = recruitmentActivityId;
	}

	@Column(name = "recruitment_activity_id", nullable = true, length = 11)	
	public Integer getRecruitmentActivityId() {
		return recruitmentActivityId;
	}

	public void setSourceOrganizationId(Integer sourceOrganizationId) {
		this.sourceOrganizationId = sourceOrganizationId;
	}

	@Column(name = "source_organization_id", nullable = true, length = 11)	
	public Integer getSourceOrganizationId() {
		return sourceOrganizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "organization_id", nullable = false, length = 11)	
	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setPeopleGroupId(Integer peopleGroupId) {
		this.peopleGroupId = peopleGroupId;
	}

	@Column(name = "people_group_id", nullable = true, length = 11)	
	public Integer getPeopleGroupId() {
		return peopleGroupId;
	}

	public void setSoftCodingKeyflexId(Integer softCodingKeyflexId) {
		this.softCodingKeyflexId = softCodingKeyflexId;
	}

	@Column(name = "soft_coding_keyflex_id", nullable = true, length = 11)	
	public Integer getSoftCodingKeyflexId() {
		return softCodingKeyflexId;
	}

	public void setVacancyId(Integer vacancyId) {
		this.vacancyId = vacancyId;
	}

	@Column(name = "vacancy_id", nullable = true, length = 11)	
	public Integer getVacancyId() {
		return vacancyId;
	}

	public void setPayBasisId(Integer payBasisId) {
		this.payBasisId = payBasisId;
	}

	@Column(name = "pay_basis_id", nullable = true, length = 9)	
	public Integer getPayBasisId() {
		return payBasisId;
	}

	public void setAssignmentSequence(Integer assignmentSequence) {
		this.assignmentSequence = assignmentSequence;
	}

	@Column(name = "assignment_sequence", nullable = false, length = 11)	
	public Integer getAssignmentSequence() {
		return assignmentSequence;
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	@Column(name = "assignment_type", nullable = false, length = 1)	
	public String getAssignmentType() {
		return assignmentType;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	@Column(name = "primary_flag", nullable = false, length = 30)	
	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "application_id", nullable = true, length = 11)	
	public Integer getApplicationId() {
		return applicationId;
	}

	public void setAssignmentInt(String assignmentInt) {
		this.assignmentInt = assignmentInt;
	}

	@Column(name = "assignment_int", nullable = true, length = 30)	
	public String getAssignmentInt() {
		return assignmentInt;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	@Column(name = "change_reason", nullable = true, length = 30)	
	public String getChangeReason() {
		return changeReason;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	@Column(name = "comment_id", nullable = true, length = 11)	
	public Integer getCommentId() {
		return commentId;
	}

	public void setDatetimeProbationEnd(Date datetimeProbationEnd) {
		this.datetimeProbationEnd = datetimeProbationEnd;
	}

	@Column(name = "datetime_probation_end", nullable = true, length = 0)	
	public Date getDatetimeProbationEnd() {
		return datetimeProbationEnd;
	}

	public void setDefaultCodeCombId(Integer defaultCodeCombId) {
		this.defaultCodeCombId = defaultCodeCombId;
	}

	@Column(name = "default_code_comb_id", nullable = true, length = 11)	
	public Integer getDefaultCodeCombId() {
		return defaultCodeCombId;
	}

	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}

	@Column(name = "employment_category", nullable = true, length = 30)	
	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Column(name = "frequency", nullable = true, length = 30)	
	public String getFrequency() {
		return frequency;
	}

	public void setInternalAddressLine(String internalAddressLine) {
		this.internalAddressLine = internalAddressLine;
	}

	@Column(name = "internal_address_line", nullable = true, length = 80)	
	public String getInternalAddressLine() {
		return internalAddressLine;
	}

	public void setManagerFlag(String managerFlag) {
		this.managerFlag = managerFlag;
	}

	@Column(name = "manager_flag", nullable = true, length = 30)	
	public String getManagerFlag() {
		return managerFlag;
	}

	public void setNormalHours(BigDecimal normalHours) {
		this.normalHours = normalHours;
	}

	@Column(name = "normal_hours", precision = 22, scale = 3)	
	public BigDecimal getNormalHours() {
		return normalHours;
	}

	public void setPerfReviewPeriod(Integer perfReviewPeriod) {
		this.perfReviewPeriod = perfReviewPeriod;
	}

	@Column(name = "perf_review_period", nullable = true, length = 11)	
	public Integer getPerfReviewPeriod() {
		return perfReviewPeriod;
	}

	public void setPerfReviewPeriodFrequency(String perfReviewPeriodFrequency) {
		this.perfReviewPeriodFrequency = perfReviewPeriodFrequency;
	}

	@Column(name = "perf_review_period_frequency", nullable = true, length = 30)	
	public String getPerfReviewPeriodFrequency() {
		return perfReviewPeriodFrequency;
	}

	public void setPeriodOfServiceId(Integer periodOfServiceId) {
		this.periodOfServiceId = periodOfServiceId;
	}

	@Column(name = "period_of_service_id", nullable = true, length = 11)	
	public Integer getPeriodOfServiceId() {
		return periodOfServiceId;
	}

	public void setProbationPeriod(BigDecimal probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	@Column(name = "probation_period", precision = 22, scale = 2)	
	public BigDecimal getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationUnit(String probationUnit) {
		this.probationUnit = probationUnit;
	}

	@Column(name = "probation_unit", nullable = true, length = 30)	
	public String getProbationUnit() {
		return probationUnit;
	}

	public void setSalReviewPeriod(Integer salReviewPeriod) {
		this.salReviewPeriod = salReviewPeriod;
	}

	@Column(name = "sal_review_period", nullable = true, length = 11)	
	public Integer getSalReviewPeriod() {
		return salReviewPeriod;
	}

	public void setSalReviewPeriodFrequency(String salReviewPeriodFrequency) {
		this.salReviewPeriodFrequency = salReviewPeriodFrequency;
	}

	@Column(name = "sal_review_period_frequency", nullable = true, length = 30)	
	public String getSalReviewPeriodFrequency() {
		return salReviewPeriodFrequency;
	}

	public void setSetOfBooksId(Integer setOfBooksId) {
		this.setOfBooksId = setOfBooksId;
	}

	@Column(name = "set_of_books_id", nullable = true, length = 11)	
	public Integer getSetOfBooksId() {
		return setOfBooksId;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "source_type", nullable = true, length = 30)	
	public String getSourceType() {
		return sourceType;
	}

	public void setTimeNormalFinish(String timeNormalFinish) {
		this.timeNormalFinish = timeNormalFinish;
	}

	@Column(name = "time_normal_finish", nullable = true, length = 5)	
	public String getTimeNormalFinish() {
		return timeNormalFinish;
	}

	public void setTimeNormalStart(String timeNormalStart) {
		this.timeNormalStart = timeNormalStart;
	}

	@Column(name = "time_normal_start", nullable = true, length = 5)	
	public String getTimeNormalStart() {
		return timeNormalStart;
	}

	public void setBargainingUnitCode(String bargainingUnitCode) {
		this.bargainingUnitCode = bargainingUnitCode;
	}

	@Column(name = "bargaining_unit_code", nullable = true, length = 30)	
	public String getBargainingUnitCode() {
		return bargainingUnitCode;
	}

	public void setLabourUnionMemberFlag(String labourUnionMemberFlag) {
		this.labourUnionMemberFlag = labourUnionMemberFlag;
	}

	@Column(name = "labour_union_member_flag", nullable = true, length = 30)	
	public String getLabourUnionMemberFlag() {
		return labourUnionMemberFlag;
	}

	public void setHourlySalariedCode(String hourlySalariedCode) {
		this.hourlySalariedCode = hourlySalariedCode;
	}

	@Column(name = "hourly_salaried_code", nullable = true, length = 30)	
	public String getHourlySalariedCode() {
		return hourlySalariedCode;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	@Column(name = "contract_id", nullable = true, length = 11)	
	public Integer getContractId() {
		return contractId;
	}

	public void setCollectiveAgreementId(Integer collectiveAgreementId) {
		this.collectiveAgreementId = collectiveAgreementId;
	}

	@Column(name = "collective_agreement_id", nullable = true, length = 11)	
	public Integer getCollectiveAgreementId() {
		return collectiveAgreementId;
	}

	public void setCagrIdFlexNum(Integer cagrIdFlexNum) {
		this.cagrIdFlexNum = cagrIdFlexNum;
	}

	@Column(name = "cagr_id_flex_num", nullable = true, length = 11)	
	public Integer getCagrIdFlexNum() {
		return cagrIdFlexNum;
	}

	public void setCagrGradeDefId(Integer cagrGradeDefId) {
		this.cagrGradeDefId = cagrGradeDefId;
	}

	@Column(name = "cagr_grade_def_id", nullable = true, length = 11)	
	public Integer getCagrGradeDefId() {
		return cagrGradeDefId;
	}

	public void setEstablishmentId(Integer establishmentId) {
		this.establishmentId = establishmentId;
	}

	@Column(name = "establishment_id", nullable = true, length = 11)	
	public Integer getEstablishmentId() {
		return establishmentId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	@Column(name = "request_id", nullable = true, length = 11)	
	public Integer getRequestId() {
		return requestId;
	}

	public void setProgramApplicationId(Integer programApplicationId) {
		this.programApplicationId = programApplicationId;
	}

	@Column(name = "program_application_id", nullable = true, length = 11)	
	public Integer getProgramApplicationId() {
		return programApplicationId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	@Column(name = "program_id", nullable = true, length = 11)	
	public Integer getProgramId() {
		return programId;
	}

	public void setProgramUpdatetimeDatetime(Date programUpdatetimeDatetime) {
		this.programUpdatetimeDatetime = programUpdatetimeDatetime;
	}

	@Column(name = "program_updatetime_datetime", nullable = true, length = 0)	
	public Date getProgramUpdatetimeDatetime() {
		return programUpdatetimeDatetime;
	}

	public void setAssAttributeCategory(String assAttributeCategory) {
		this.assAttributeCategory = assAttributeCategory;
	}

	@Column(name = "ass_attribute_category", nullable = true, length = 30)	
	public String getAssAttributeCategory() {
		return assAttributeCategory;
	}

	public void setAssAttribute1(String assAttribute1) {
		this.assAttribute1 = assAttribute1;
	}

	@Column(name = "ass_attribute1", nullable = true, length = 150)	
	public String getAssAttribute1() {
		return assAttribute1;
	}

	public void setAssAttribute2(String assAttribute2) {
		this.assAttribute2 = assAttribute2;
	}

	@Column(name = "ass_attribute2", nullable = true, length = 150)	
	public String getAssAttribute2() {
		return assAttribute2;
	}

	public void setAssAttribute3(String assAttribute3) {
		this.assAttribute3 = assAttribute3;
	}

	@Column(name = "ass_attribute3", nullable = true, length = 150)	
	public String getAssAttribute3() {
		return assAttribute3;
	}

	public void setAssAttribute4(String assAttribute4) {
		this.assAttribute4 = assAttribute4;
	}

	@Column(name = "ass_attribute4", nullable = true, length = 150)	
	public String getAssAttribute4() {
		return assAttribute4;
	}

	public void setAssAttribute5(String assAttribute5) {
		this.assAttribute5 = assAttribute5;
	}

	@Column(name = "ass_attribute5", nullable = true, length = 150)	
	public String getAssAttribute5() {
		return assAttribute5;
	}

	public void setAssAttribute6(String assAttribute6) {
		this.assAttribute6 = assAttribute6;
	}

	@Column(name = "ass_attribute6", nullable = true, length = 150)	
	public String getAssAttribute6() {
		return assAttribute6;
	}

	public void setAssAttribute7(String assAttribute7) {
		this.assAttribute7 = assAttribute7;
	}

	@Column(name = "ass_attribute7", nullable = true, length = 150)	
	public String getAssAttribute7() {
		return assAttribute7;
	}

	public void setAssAttribute8(String assAttribute8) {
		this.assAttribute8 = assAttribute8;
	}

	@Column(name = "ass_attribute8", nullable = true, length = 150)	
	public String getAssAttribute8() {
		return assAttribute8;
	}

	public void setAssAttribute9(String assAttribute9) {
		this.assAttribute9 = assAttribute9;
	}

	@Column(name = "ass_attribute9", nullable = true, length = 150)	
	public String getAssAttribute9() {
		return assAttribute9;
	}

	public void setAssAttribute10(String assAttribute10) {
		this.assAttribute10 = assAttribute10;
	}

	@Column(name = "ass_attribute10", nullable = true, length = 150)	
	public String getAssAttribute10() {
		return assAttribute10;
	}

	public void setAssAttribute11(String assAttribute11) {
		this.assAttribute11 = assAttribute11;
	}

	@Column(name = "ass_attribute11", nullable = true, length = 150)	
	public String getAssAttribute11() {
		return assAttribute11;
	}

	public void setAssAttribute12(String assAttribute12) {
		this.assAttribute12 = assAttribute12;
	}

	@Column(name = "ass_attribute12", nullable = true, length = 150)	
	public String getAssAttribute12() {
		return assAttribute12;
	}

	public void setAssAttribute13(String assAttribute13) {
		this.assAttribute13 = assAttribute13;
	}

	@Column(name = "ass_attribute13", nullable = true, length = 150)	
	public String getAssAttribute13() {
		return assAttribute13;
	}

	public void setAssAttribute14(String assAttribute14) {
		this.assAttribute14 = assAttribute14;
	}

	@Column(name = "ass_attribute14", nullable = true, length = 150)	
	public String getAssAttribute14() {
		return assAttribute14;
	}

	public void setAssAttribute15(String assAttribute15) {
		this.assAttribute15 = assAttribute15;
	}

	@Column(name = "ass_attribute15", nullable = true, length = 150)	
	public String getAssAttribute15() {
		return assAttribute15;
	}

	public void setAssAttribute16(String assAttribute16) {
		this.assAttribute16 = assAttribute16;
	}

	@Column(name = "ass_attribute16", nullable = true, length = 150)	
	public String getAssAttribute16() {
		return assAttribute16;
	}

	public void setAssAttribute17(String assAttribute17) {
		this.assAttribute17 = assAttribute17;
	}

	@Column(name = "ass_attribute17", nullable = true, length = 150)	
	public String getAssAttribute17() {
		return assAttribute17;
	}

	public void setAssAttribute18(String assAttribute18) {
		this.assAttribute18 = assAttribute18;
	}

	@Column(name = "ass_attribute18", nullable = true, length = 150)	
	public String getAssAttribute18() {
		return assAttribute18;
	}

	public void setAssAttribute19(String assAttribute19) {
		this.assAttribute19 = assAttribute19;
	}

	@Column(name = "ass_attribute19", nullable = true, length = 150)	
	public String getAssAttribute19() {
		return assAttribute19;
	}

	public void setAssAttribute20(String assAttribute20) {
		this.assAttribute20 = assAttribute20;
	}

	@Column(name = "ass_attribute20", nullable = true, length = 150)	
	public String getAssAttribute20() {
		return assAttribute20;
	}

	public void setAssAttribute21(String assAttribute21) {
		this.assAttribute21 = assAttribute21;
	}

	@Column(name = "ass_attribute21", nullable = true, length = 150)	
	public String getAssAttribute21() {
		return assAttribute21;
	}

	public void setAssAttribute22(String assAttribute22) {
		this.assAttribute22 = assAttribute22;
	}

	@Column(name = "ass_attribute22", nullable = true, length = 150)	
	public String getAssAttribute22() {
		return assAttribute22;
	}

	public void setAssAttribute23(String assAttribute23) {
		this.assAttribute23 = assAttribute23;
	}

	@Column(name = "ass_attribute23", nullable = true, length = 150)	
	public String getAssAttribute23() {
		return assAttribute23;
	}

	public void setAssAttribute24(String assAttribute24) {
		this.assAttribute24 = assAttribute24;
	}

	@Column(name = "ass_attribute24", nullable = true, length = 150)	
	public String getAssAttribute24() {
		return assAttribute24;
	}

	public void setAssAttribute25(String assAttribute25) {
		this.assAttribute25 = assAttribute25;
	}

	@Column(name = "ass_attribute25", nullable = true, length = 150)	
	public String getAssAttribute25() {
		return assAttribute25;
	}

	public void setAssAttribute26(String assAttribute26) {
		this.assAttribute26 = assAttribute26;
	}

	@Column(name = "ass_attribute26", nullable = true, length = 150)	
	public String getAssAttribute26() {
		return assAttribute26;
	}

	public void setAssAttribute27(String assAttribute27) {
		this.assAttribute27 = assAttribute27;
	}

	@Column(name = "ass_attribute27", nullable = true, length = 150)	
	public String getAssAttribute27() {
		return assAttribute27;
	}

	public void setAssAttribute28(String assAttribute28) {
		this.assAttribute28 = assAttribute28;
	}

	@Column(name = "ass_attribute28", nullable = true, length = 150)	
	public String getAssAttribute28() {
		return assAttribute28;
	}

	public void setAssAttribute29(String assAttribute29) {
		this.assAttribute29 = assAttribute29;
	}

	@Column(name = "ass_attribute29", nullable = true, length = 150)	
	public String getAssAttribute29() {
		return assAttribute29;
	}

	public void setAssAttribute30(String assAttribute30) {
		this.assAttribute30 = assAttribute30;
	}

	@Column(name = "ass_attribute30", nullable = true, length = 150)	
	public String getAssAttribute30() {
		return assAttribute30;
	}

	public void setLastUpdatetimeDatetime(Date lastUpdatetimeDatetime) {
		this.lastUpdatetimeDatetime = lastUpdatetimeDatetime;
	}

	@Column(name = "last_updatetime_datetime", nullable = true, length = 0)	
	public Date getLastUpdatetimeDatetime() {
		return lastUpdatetimeDatetime;
	}

	public void setLastUpdatetimedBy(Integer lastUpdatetimedBy) {
		this.lastUpdatetimedBy = lastUpdatetimedBy;
	}

	@Column(name = "last_updatetimed_by", nullable = true, length = 11)	
	public Integer getLastUpdatetimedBy() {
		return lastUpdatetimedBy;
	}

	public void setLastUpdatetimeLogin(Integer lastUpdatetimeLogin) {
		this.lastUpdatetimeLogin = lastUpdatetimeLogin;
	}

	@Column(name = "last_updatetime_login", nullable = true, length = 11)	
	public Integer getLastUpdatetimeLogin() {
		return lastUpdatetimeLogin;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 11)	
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	@Column(name = "creation_datetime", nullable = true, length = 0)	
	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "title", nullable = true, length = 30)	
	public String getTitle() {
		return title;
	}

	public void setObjectVersionInt(Integer objectVersionInt) {
		this.objectVersionInt = objectVersionInt;
	}

	@Column(name = "object_version_int", nullable = true, length = 9)	
	public Integer getObjectVersionInt() {
		return objectVersionInt;
	}

	public void setNoticePeriod(Integer noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	@Column(name = "notice_period", nullable = true, length = 11)	
	public Integer getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriodUom(String noticePeriodUom) {
		this.noticePeriodUom = noticePeriodUom;
	}

	@Column(name = "notice_period_uom", nullable = true, length = 30)	
	public String getNoticePeriodUom() {
		return noticePeriodUom;
	}

	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}

	@Column(name = "employee_category", nullable = true, length = 30)	
	public String getEmployeeCategory() {
		return employeeCategory;
	}

	public void setWorkAtHome(String workAtHome) {
		this.workAtHome = workAtHome;
	}

	@Column(name = "work_at_home", nullable = true, length = 30)	
	public String getWorkAtHome() {
		return workAtHome;
	}

	public void setJobPostSourceName(String jobPostSourceName) {
		this.jobPostSourceName = jobPostSourceName;
	}

	@Column(name = "job_post_source_name", nullable = true, length = 240)	
	public String getJobPostSourceName() {
		return jobPostSourceName;
	}

	public void setPostingContentId(Integer postingContentId) {
		this.postingContentId = postingContentId;
	}

	@Column(name = "posting_content_id", nullable = true, length = 11)	
	public Integer getPostingContentId() {
		return postingContentId;
	}

	public void setPeriodOfPlacementDatetimeStart(Date periodOfPlacementDatetimeStart) {
		this.periodOfPlacementDatetimeStart = periodOfPlacementDatetimeStart;
	}

	@Column(name = "period_of_placement_datetime_start", nullable = true, length = 0)	
	public Date getPeriodOfPlacementDatetimeStart() {
		return periodOfPlacementDatetimeStart;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "vendor_id", nullable = true, length = 11)	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorEmployeeInt(String vendorEmployeeInt) {
		this.vendorEmployeeInt = vendorEmployeeInt;
	}

	@Column(name = "vendor_employee_int", nullable = true, length = 30)	
	public String getVendorEmployeeInt() {
		return vendorEmployeeInt;
	}

	public void setVendorAssignmentInt(String vendorAssignmentInt) {
		this.vendorAssignmentInt = vendorAssignmentInt;
	}

	@Column(name = "vendor_assignment_int", nullable = true, length = 30)	
	public String getVendorAssignmentInt() {
		return vendorAssignmentInt;
	}

	public void setAssignmentCategory(String assignmentCategory) {
		this.assignmentCategory = assignmentCategory;
	}

	@Column(name = "assignment_category", nullable = true, length = 30)	
	public String getAssignmentCategory() {
		return assignmentCategory;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	@Column(name = "project_title", nullable = true, length = 30)	
	public String getProjectTitle() {
		return projectTitle;
	}

	public void setApplicantRank(Integer applicantRank) {
		this.applicantRank = applicantRank;
	}

	@Column(name = "applicant_rank", nullable = true, length = 11)	
	public Integer getApplicantRank() {
		return applicantRank;
	}

	public void setVendorSiteId(Integer vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}

	@Column(name = "vendor_site_id", nullable = true, length = 11)	
	public Integer getVendorSiteId() {
		return vendorSiteId;
	}

	public void setPoHeaderId(Integer poHeaderId) {
		this.poHeaderId = poHeaderId;
	}

	@Column(name = "po_header_id", nullable = true, length = 11)	
	public Integer getPoHeaderId() {
		return poHeaderId;
	}

	public void setPoLineId(Integer poLineId) {
		this.poLineId = poLineId;
	}

	@Column(name = "po_line_id", nullable = true, length = 11)	
	public Integer getPoLineId() {
		return poLineId;
	}

	public void setProjectedAssignmentEnd(Date projectedAssignmentEnd) {
		this.projectedAssignmentEnd = projectedAssignmentEnd;
	}

	@Column(name = "projected_assignment_end", nullable = true, length = 0)	
	public Date getProjectedAssignmentEnd() {
		return projectedAssignmentEnd;
	}

	public void setGradeLadderPgmId(Integer gradeLadderPgmId) {
		this.gradeLadderPgmId = gradeLadderPgmId;
	}

	@Column(name = "grade_ladder_pgm_id", nullable = true, length = 11)	
	public Integer getGradeLadderPgmId() {
		return gradeLadderPgmId;
	}

	public void setSupervisorAssignmentId(Integer supervisorAssignmentId) {
		this.supervisorAssignmentId = supervisorAssignmentId;
	}

	@Column(name = "supervisor_assignment_id", nullable = true, length = 11)	
	public Integer getSupervisorAssignmentId() {
		return supervisorAssignmentId;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
