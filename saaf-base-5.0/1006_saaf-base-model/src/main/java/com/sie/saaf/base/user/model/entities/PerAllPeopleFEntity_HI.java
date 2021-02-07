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
 * PerAllPeopleFEntity_HI Entity Object
 * Mon Apr 23 21:28:26 CST 2018  Auto Generate
 */
@Entity
@Table(name = "per_all_people_f")
public class PerAllPeopleFEntity_HI {
    private Integer personId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveStartDatetime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveEndDatetime;
    private Integer businessGroupId;
    private Integer personTypeId;
    private String lastName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startDatetime;
    private String applicantInt;
    private String backgroundCheckStatus;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date backgroundDatetimeCheck;
    private String bloodType;
    private Integer commentId;
    private String correspondenceLanguage;
    private String currentApplicantFlag;
    private String currentEmpOrAplFlag;
    private String currentEmployeeFlag;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date datetimeEmployeeDataVerified;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date datetimeOfBirth;
    private String emailAddress;
    private String employeeInt;
    private String expenseCheckSendToAddress;
    private String fastPathEmployee;
    private String firstName;
    private BigDecimal fteCapacity;
    private String fullName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date holdApplicantDatetimeUntil;
    private String honors;
    private String internalLocation;
    private String knownAs;
    private String lastMedicalTestBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastMedicalTestDatetime;
    private String mailstop;
    private String maritalStatus;
    private String middleNames;
    private String nationality;
    private String nationalIdentifier;
    private String officeInt;
    private String onMilitaryService;
    private String orderName;
    private String preNameAdjunct;
    private String previousLastName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date projectedStartDatetime;
    private String rehireAuthorizor;
    private String rehireReason;
    private String rehireRecommendation;
    private String resumeExists;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date resumeLastUpdatetimed;
    private String registeredDisabledFlag;
    private String secondPassportExists;
    private String sex;
    private String studentStatus;
    private String suffix;
    private String title;
    private Integer vendorId;
    private String workSchedule;
    private String workTelephone;
    private String coordBenMedPlnNo;
    private String coordBenNoCvgFlag;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dpdntAdoptionDatetime;
    private String dpdntVlntrySvceFlag;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date receiptOfDeathCertDatetime;
    private String usesTobaccoFlag;
    private Integer benefitGroupId;
    private Integer requestId;
    private Integer programApplicationId;
    private Integer programId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date programUpdatetimeDatetime;
    private String attributeCategory;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String attribute16;
    private String attribute17;
    private String attribute18;
    private String attribute19;
    private String attribute20;
    private String attribute21;
    private String attribute22;
    private String attribute23;
    private String attribute24;
    private String attribute25;
    private String attribute26;
    private String attribute27;
    private String attribute28;
    private String attribute29;
    private String attribute30;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdatetimeDatetime;
    private Integer lastUpdatetimedBy;
    private Integer lastUpdatetimeLogin;
    private Integer createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDatetime;
    private String perInformationCategory;
    private String perInformation1;
    private String perInformation2;
    private String perInformation3;
    private String perInformation4;
    private String perInformation5;
    private String perInformation6;
    private String perInformation7;
    private String perInformation8;
    private String perInformation9;
    private String perInformation10;
    private String perInformation11;
    private String perInformation12;
    private String perInformation13;
    private String perInformation14;
    private String perInformation15;
    private String perInformation16;
    private String perInformation17;
    private String perInformation18;
    private String perInformation19;
    private String perInformation20;
    private String perInformation21;
    private String perInformation22;
    private String perInformation23;
    private String perInformation24;
    private String perInformation25;
    private String perInformation26;
    private String perInformation27;
    private String perInformation28;
    private String perInformation29;
    private String perInformation30;
    private Integer objectVersionInt;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date datetimeOfDeath;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date originalDatetimeOfHire;
    private String townOfBirth;
    private String regionOfBirth;
    private String countryOfBirth;
    private String globalPersonId;
    private String coordBenMedPlName;
    private String coordBenMedInsrCrrName;
    private String coordBenMedInsrCrrIdent;
    private String coordBenMedExtEr;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date coordBenMedCvgStrtDt;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date coordBenMedCvgEndDt;
    private Integer partyId;
    private String npwInt;
    private String currentNpwFlag;
    private String globalName;
    private String localName;
    private Integer operatorUserId;

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	@Id	
	@SequenceGenerator(name = "SEQ_PER_ALL_PEOPLE_F", sequenceName = "SEQ_PER_ALL_PEOPLE_F", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_PER_ALL_PEOPLE_F", strategy = GenerationType.SEQUENCE)	
	@Column(name = "person_id", nullable = false, length = 11)	
	public Integer getPersonId() {
		return personId;
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

	public void setPersonTypeId(Integer personTypeId) {
		this.personTypeId = personTypeId;
	}

	@Column(name = "person_type_id", nullable = false, length = 11)	
	public Integer getPersonTypeId() {
		return personTypeId;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "last_name", nullable = false, length = 150)	
	public String getLastName() {
		return lastName;
	}

	public void setStartDatetime(Date startDatetime) {
		this.startDatetime = startDatetime;
	}

	@Column(name = "start_datetime", nullable = false, length = 0)	
	public Date getStartDatetime() {
		return startDatetime;
	}

	public void setApplicantInt(String applicantInt) {
		this.applicantInt = applicantInt;
	}

	@Column(name = "applicant_int", nullable = true, length = 30)	
	public String getApplicantInt() {
		return applicantInt;
	}

	public void setBackgroundCheckStatus(String backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}

	@Column(name = "background_check_status", nullable = true, length = 30)	
	public String getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}

	public void setBackgroundDatetimeCheck(Date backgroundDatetimeCheck) {
		this.backgroundDatetimeCheck = backgroundDatetimeCheck;
	}

	@Column(name = "background_datetime_check", nullable = true, length = 0)	
	public Date getBackgroundDatetimeCheck() {
		return backgroundDatetimeCheck;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	@Column(name = "blood_type", nullable = true, length = 30)	
	public String getBloodType() {
		return bloodType;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	@Column(name = "comment_id", nullable = true, length = 11)	
	public Integer getCommentId() {
		return commentId;
	}

	public void setCorrespondenceLanguage(String correspondenceLanguage) {
		this.correspondenceLanguage = correspondenceLanguage;
	}

	@Column(name = "correspondence_language", nullable = true, length = 30)	
	public String getCorrespondenceLanguage() {
		return correspondenceLanguage;
	}

	public void setCurrentApplicantFlag(String currentApplicantFlag) {
		this.currentApplicantFlag = currentApplicantFlag;
	}

	@Column(name = "current_applicant_flag", nullable = true, length = 30)	
	public String getCurrentApplicantFlag() {
		return currentApplicantFlag;
	}

	public void setCurrentEmpOrAplFlag(String currentEmpOrAplFlag) {
		this.currentEmpOrAplFlag = currentEmpOrAplFlag;
	}

	@Column(name = "current_emp_or_apl_flag", nullable = true, length = 30)	
	public String getCurrentEmpOrAplFlag() {
		return currentEmpOrAplFlag;
	}

	public void setCurrentEmployeeFlag(String currentEmployeeFlag) {
		this.currentEmployeeFlag = currentEmployeeFlag;
	}

	@Column(name = "current_employee_flag", nullable = true, length = 30)	
	public String getCurrentEmployeeFlag() {
		return currentEmployeeFlag;
	}

	public void setDatetimeEmployeeDataVerified(Date datetimeEmployeeDataVerified) {
		this.datetimeEmployeeDataVerified = datetimeEmployeeDataVerified;
	}

	@Column(name = "datetime_employee_data_verified", nullable = true, length = 0)	
	public Date getDatetimeEmployeeDataVerified() {
		return datetimeEmployeeDataVerified;
	}

	public void setDatetimeOfBirth(Date datetimeOfBirth) {
		this.datetimeOfBirth = datetimeOfBirth;
	}

	@Column(name = "datetime_of_birth", nullable = true, length = 0)	
	public Date getDatetimeOfBirth() {
		return datetimeOfBirth;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "email_address", nullable = true, length = 240)	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmployeeInt(String employeeInt) {
		this.employeeInt = employeeInt;
	}

	@Column(name = "employee_int", nullable = true, length = 30)	
	public String getEmployeeInt() {
		return employeeInt;
	}

	public void setExpenseCheckSendToAddress(String expenseCheckSendToAddress) {
		this.expenseCheckSendToAddress = expenseCheckSendToAddress;
	}

	@Column(name = "expense_check_send_to_address", nullable = true, length = 30)	
	public String getExpenseCheckSendToAddress() {
		return expenseCheckSendToAddress;
	}

	public void setFastPathEmployee(String fastPathEmployee) {
		this.fastPathEmployee = fastPathEmployee;
	}

	@Column(name = "fast_path_employee", nullable = true, length = 30)	
	public String getFastPathEmployee() {
		return fastPathEmployee;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "first_name", nullable = true, length = 150)	
	public String getFirstName() {
		return firstName;
	}

	public void setFteCapacity(BigDecimal fteCapacity) {
		this.fteCapacity = fteCapacity;
	}

	@Column(name = "fte_capacity", precision = 5, scale = 2)	
	public BigDecimal getFteCapacity() {
		return fteCapacity;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "full_name", nullable = true, length = 240)	
	public String getFullName() {
		return fullName;
	}

	public void setHoldApplicantDatetimeUntil(Date holdApplicantDatetimeUntil) {
		this.holdApplicantDatetimeUntil = holdApplicantDatetimeUntil;
	}

	@Column(name = "hold_applicant_datetime_until", nullable = true, length = 0)	
	public Date getHoldApplicantDatetimeUntil() {
		return holdApplicantDatetimeUntil;
	}

	public void setHonors(String honors) {
		this.honors = honors;
	}

	@Column(name = "honors", nullable = true, length = 45)	
	public String getHonors() {
		return honors;
	}

	public void setInternalLocation(String internalLocation) {
		this.internalLocation = internalLocation;
	}

	@Column(name = "internal_location", nullable = true, length = 45)	
	public String getInternalLocation() {
		return internalLocation;
	}

	public void setKnownAs(String knownAs) {
		this.knownAs = knownAs;
	}

	@Column(name = "known_as", nullable = true, length = 80)	
	public String getKnownAs() {
		return knownAs;
	}

	public void setLastMedicalTestBy(String lastMedicalTestBy) {
		this.lastMedicalTestBy = lastMedicalTestBy;
	}

	@Column(name = "last_medical_test_by", nullable = true, length = 60)	
	public String getLastMedicalTestBy() {
		return lastMedicalTestBy;
	}

	public void setLastMedicalTestDatetime(Date lastMedicalTestDatetime) {
		this.lastMedicalTestDatetime = lastMedicalTestDatetime;
	}

	@Column(name = "last_medical_test_datetime", nullable = true, length = 0)	
	public Date getLastMedicalTestDatetime() {
		return lastMedicalTestDatetime;
	}

	public void setMailstop(String mailstop) {
		this.mailstop = mailstop;
	}

	@Column(name = "mailstop", nullable = true, length = 45)	
	public String getMailstop() {
		return mailstop;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "marital_status", nullable = true, length = 30)	
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	@Column(name = "middle_names", nullable = true, length = 60)	
	public String getMiddleNames() {
		return middleNames;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Column(name = "nationality", nullable = true, length = 30)	
	public String getNationality() {
		return nationality;
	}

	public void setNationalIdentifier(String nationalIdentifier) {
		this.nationalIdentifier = nationalIdentifier;
	}

	@Column(name = "national_identifier", nullable = true, length = 30)	
	public String getNationalIdentifier() {
		return nationalIdentifier;
	}

	public void setOfficeInt(String officeInt) {
		this.officeInt = officeInt;
	}

	@Column(name = "office_int", nullable = true, length = 45)	
	public String getOfficeInt() {
		return officeInt;
	}

	public void setOnMilitaryService(String onMilitaryService) {
		this.onMilitaryService = onMilitaryService;
	}

	@Column(name = "on_military_service", nullable = true, length = 30)	
	public String getOnMilitaryService() {
		return onMilitaryService;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@Column(name = "order_name", nullable = true, length = 240)	
	public String getOrderName() {
		return orderName;
	}

	public void setPreNameAdjunct(String preNameAdjunct) {
		this.preNameAdjunct = preNameAdjunct;
	}

	@Column(name = "pre_name_adjunct", nullable = true, length = 30)	
	public String getPreNameAdjunct() {
		return preNameAdjunct;
	}

	public void setPreviousLastName(String previousLastName) {
		this.previousLastName = previousLastName;
	}

	@Column(name = "previous_last_name", nullable = true, length = 150)	
	public String getPreviousLastName() {
		return previousLastName;
	}

	public void setProjectedStartDatetime(Date projectedStartDatetime) {
		this.projectedStartDatetime = projectedStartDatetime;
	}

	@Column(name = "projected_start_datetime", nullable = true, length = 0)	
	public Date getProjectedStartDatetime() {
		return projectedStartDatetime;
	}

	public void setRehireAuthorizor(String rehireAuthorizor) {
		this.rehireAuthorizor = rehireAuthorizor;
	}

	@Column(name = "rehire_authorizor", nullable = true, length = 60)	
	public String getRehireAuthorizor() {
		return rehireAuthorizor;
	}

	public void setRehireReason(String rehireReason) {
		this.rehireReason = rehireReason;
	}

	@Column(name = "rehire_reason", nullable = true, length = 60)	
	public String getRehireReason() {
		return rehireReason;
	}

	public void setRehireRecommendation(String rehireRecommendation) {
		this.rehireRecommendation = rehireRecommendation;
	}

	@Column(name = "rehire_recommendation", nullable = true, length = 30)	
	public String getRehireRecommendation() {
		return rehireRecommendation;
	}

	public void setResumeExists(String resumeExists) {
		this.resumeExists = resumeExists;
	}

	@Column(name = "resume_exists", nullable = true, length = 30)	
	public String getResumeExists() {
		return resumeExists;
	}

	public void setResumeLastUpdatetimed(Date resumeLastUpdatetimed) {
		this.resumeLastUpdatetimed = resumeLastUpdatetimed;
	}

	@Column(name = "resume_last_updatetimed", nullable = true, length = 0)	
	public Date getResumeLastUpdatetimed() {
		return resumeLastUpdatetimed;
	}

	public void setRegisteredDisabledFlag(String registeredDisabledFlag) {
		this.registeredDisabledFlag = registeredDisabledFlag;
	}

	@Column(name = "registered_disabled_flag", nullable = true, length = 30)	
	public String getRegisteredDisabledFlag() {
		return registeredDisabledFlag;
	}

	public void setSecondPassportExists(String secondPassportExists) {
		this.secondPassportExists = secondPassportExists;
	}

	@Column(name = "second_passport_exists", nullable = true, length = 30)	
	public String getSecondPassportExists() {
		return secondPassportExists;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "sex", nullable = true, length = 30)	
	public String getSex() {
		return sex;
	}

	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	@Column(name = "student_status", nullable = true, length = 30)	
	public String getStudentStatus() {
		return studentStatus;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Column(name = "suffix", nullable = true, length = 30)	
	public String getSuffix() {
		return suffix;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "title", nullable = true, length = 30)	
	public String getTitle() {
		return title;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Column(name = "vendor_id", nullable = true, length = 11)	
	public Integer getVendorId() {
		return vendorId;
	}

	public void setWorkSchedule(String workSchedule) {
		this.workSchedule = workSchedule;
	}

	@Column(name = "work_schedule", nullable = true, length = 30)	
	public String getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkTelephone(String workTelephone) {
		this.workTelephone = workTelephone;
	}

	@Column(name = "work_telephone", nullable = true, length = 60)	
	public String getWorkTelephone() {
		return workTelephone;
	}

	public void setCoordBenMedPlnNo(String coordBenMedPlnNo) {
		this.coordBenMedPlnNo = coordBenMedPlnNo;
	}

	@Column(name = "coord_ben_med_pln_no", nullable = true, length = 30)	
	public String getCoordBenMedPlnNo() {
		return coordBenMedPlnNo;
	}

	public void setCoordBenNoCvgFlag(String coordBenNoCvgFlag) {
		this.coordBenNoCvgFlag = coordBenNoCvgFlag;
	}

	@Column(name = "coord_ben_no_cvg_flag", nullable = true, length = 30)	
	public String getCoordBenNoCvgFlag() {
		return coordBenNoCvgFlag;
	}

	public void setDpdntAdoptionDatetime(Date dpdntAdoptionDatetime) {
		this.dpdntAdoptionDatetime = dpdntAdoptionDatetime;
	}

	@Column(name = "dpdnt_adoption_datetime", nullable = true, length = 0)	
	public Date getDpdntAdoptionDatetime() {
		return dpdntAdoptionDatetime;
	}

	public void setDpdntVlntrySvceFlag(String dpdntVlntrySvceFlag) {
		this.dpdntVlntrySvceFlag = dpdntVlntrySvceFlag;
	}

	@Column(name = "dpdnt_vlntry_svce_flag", nullable = true, length = 30)	
	public String getDpdntVlntrySvceFlag() {
		return dpdntVlntrySvceFlag;
	}

	public void setReceiptOfDeathCertDatetime(Date receiptOfDeathCertDatetime) {
		this.receiptOfDeathCertDatetime = receiptOfDeathCertDatetime;
	}

	@Column(name = "receipt_of_death_cert_datetime", nullable = true, length = 0)	
	public Date getReceiptOfDeathCertDatetime() {
		return receiptOfDeathCertDatetime;
	}

	public void setUsesTobaccoFlag(String usesTobaccoFlag) {
		this.usesTobaccoFlag = usesTobaccoFlag;
	}

	@Column(name = "uses_tobacco_flag", nullable = true, length = 30)	
	public String getUsesTobaccoFlag() {
		return usesTobaccoFlag;
	}

	public void setBenefitGroupId(Integer benefitGroupId) {
		this.benefitGroupId = benefitGroupId;
	}

	@Column(name = "benefit_group_id", nullable = true, length = 11)	
	public Integer getBenefitGroupId() {
		return benefitGroupId;
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

	public void setAttributeCategory(String attributeCategory) {
		this.attributeCategory = attributeCategory;
	}

	@Column(name = "attribute_category", nullable = true, length = 30)	
	public String getAttributeCategory() {
		return attributeCategory;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	@Column(name = "attribute1", nullable = true, length = 150)	
	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	@Column(name = "attribute2", nullable = true, length = 150)	
	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	@Column(name = "attribute3", nullable = true, length = 150)	
	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	@Column(name = "attribute4", nullable = true, length = 150)	
	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	@Column(name = "attribute5", nullable = true, length = 150)	
	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}

	@Column(name = "attribute6", nullable = true, length = 150)	
	public String getAttribute6() {
		return attribute6;
	}

	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}

	@Column(name = "attribute7", nullable = true, length = 150)	
	public String getAttribute7() {
		return attribute7;
	}

	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}

	@Column(name = "attribute8", nullable = true, length = 150)	
	public String getAttribute8() {
		return attribute8;
	}

	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}

	@Column(name = "attribute9", nullable = true, length = 150)	
	public String getAttribute9() {
		return attribute9;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	@Column(name = "attribute10", nullable = true, length = 150)	
	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}

	@Column(name = "attribute11", nullable = true, length = 150)	
	public String getAttribute11() {
		return attribute11;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	@Column(name = "attribute12", nullable = true, length = 150)	
	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	@Column(name = "attribute13", nullable = true, length = 150)	
	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}

	@Column(name = "attribute14", nullable = true, length = 150)	
	public String getAttribute14() {
		return attribute14;
	}

	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}

	@Column(name = "attribute15", nullable = true, length = 150)	
	public String getAttribute15() {
		return attribute15;
	}

	public void setAttribute16(String attribute16) {
		this.attribute16 = attribute16;
	}

	@Column(name = "attribute16", nullable = true, length = 150)	
	public String getAttribute16() {
		return attribute16;
	}

	public void setAttribute17(String attribute17) {
		this.attribute17 = attribute17;
	}

	@Column(name = "attribute17", nullable = true, length = 150)	
	public String getAttribute17() {
		return attribute17;
	}

	public void setAttribute18(String attribute18) {
		this.attribute18 = attribute18;
	}

	@Column(name = "attribute18", nullable = true, length = 150)	
	public String getAttribute18() {
		return attribute18;
	}

	public void setAttribute19(String attribute19) {
		this.attribute19 = attribute19;
	}

	@Column(name = "attribute19", nullable = true, length = 150)	
	public String getAttribute19() {
		return attribute19;
	}

	public void setAttribute20(String attribute20) {
		this.attribute20 = attribute20;
	}

	@Column(name = "attribute20", nullable = true, length = 150)	
	public String getAttribute20() {
		return attribute20;
	}

	public void setAttribute21(String attribute21) {
		this.attribute21 = attribute21;
	}

	@Column(name = "attribute21", nullable = true, length = 150)	
	public String getAttribute21() {
		return attribute21;
	}

	public void setAttribute22(String attribute22) {
		this.attribute22 = attribute22;
	}

	@Column(name = "attribute22", nullable = true, length = 150)	
	public String getAttribute22() {
		return attribute22;
	}

	public void setAttribute23(String attribute23) {
		this.attribute23 = attribute23;
	}

	@Column(name = "attribute23", nullable = true, length = 150)	
	public String getAttribute23() {
		return attribute23;
	}

	public void setAttribute24(String attribute24) {
		this.attribute24 = attribute24;
	}

	@Column(name = "attribute24", nullable = true, length = 150)	
	public String getAttribute24() {
		return attribute24;
	}

	public void setAttribute25(String attribute25) {
		this.attribute25 = attribute25;
	}

	@Column(name = "attribute25", nullable = true, length = 150)	
	public String getAttribute25() {
		return attribute25;
	}

	public void setAttribute26(String attribute26) {
		this.attribute26 = attribute26;
	}

	@Column(name = "attribute26", nullable = true, length = 150)	
	public String getAttribute26() {
		return attribute26;
	}

	public void setAttribute27(String attribute27) {
		this.attribute27 = attribute27;
	}

	@Column(name = "attribute27", nullable = true, length = 150)	
	public String getAttribute27() {
		return attribute27;
	}

	public void setAttribute28(String attribute28) {
		this.attribute28 = attribute28;
	}

	@Column(name = "attribute28", nullable = true, length = 150)	
	public String getAttribute28() {
		return attribute28;
	}

	public void setAttribute29(String attribute29) {
		this.attribute29 = attribute29;
	}

	@Column(name = "attribute29", nullable = true, length = 150)	
	public String getAttribute29() {
		return attribute29;
	}

	public void setAttribute30(String attribute30) {
		this.attribute30 = attribute30;
	}

	@Column(name = "attribute30", nullable = true, length = 150)	
	public String getAttribute30() {
		return attribute30;
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

	public void setPerInformationCategory(String perInformationCategory) {
		this.perInformationCategory = perInformationCategory;
	}

	@Column(name = "per_information_category", nullable = true, length = 30)	
	public String getPerInformationCategory() {
		return perInformationCategory;
	}

	public void setPerInformation1(String perInformation1) {
		this.perInformation1 = perInformation1;
	}

	@Column(name = "per_information1", nullable = true, length = 150)	
	public String getPerInformation1() {
		return perInformation1;
	}

	public void setPerInformation2(String perInformation2) {
		this.perInformation2 = perInformation2;
	}

	@Column(name = "per_information2", nullable = true, length = 150)	
	public String getPerInformation2() {
		return perInformation2;
	}

	public void setPerInformation3(String perInformation3) {
		this.perInformation3 = perInformation3;
	}

	@Column(name = "per_information3", nullable = true, length = 150)	
	public String getPerInformation3() {
		return perInformation3;
	}

	public void setPerInformation4(String perInformation4) {
		this.perInformation4 = perInformation4;
	}

	@Column(name = "per_information4", nullable = true, length = 150)	
	public String getPerInformation4() {
		return perInformation4;
	}

	public void setPerInformation5(String perInformation5) {
		this.perInformation5 = perInformation5;
	}

	@Column(name = "per_information5", nullable = true, length = 150)	
	public String getPerInformation5() {
		return perInformation5;
	}

	public void setPerInformation6(String perInformation6) {
		this.perInformation6 = perInformation6;
	}

	@Column(name = "per_information6", nullable = true, length = 150)	
	public String getPerInformation6() {
		return perInformation6;
	}

	public void setPerInformation7(String perInformation7) {
		this.perInformation7 = perInformation7;
	}

	@Column(name = "per_information7", nullable = true, length = 150)	
	public String getPerInformation7() {
		return perInformation7;
	}

	public void setPerInformation8(String perInformation8) {
		this.perInformation8 = perInformation8;
	}

	@Column(name = "per_information8", nullable = true, length = 150)	
	public String getPerInformation8() {
		return perInformation8;
	}

	public void setPerInformation9(String perInformation9) {
		this.perInformation9 = perInformation9;
	}

	@Column(name = "per_information9", nullable = true, length = 150)	
	public String getPerInformation9() {
		return perInformation9;
	}

	public void setPerInformation10(String perInformation10) {
		this.perInformation10 = perInformation10;
	}

	@Column(name = "per_information10", nullable = true, length = 150)	
	public String getPerInformation10() {
		return perInformation10;
	}

	public void setPerInformation11(String perInformation11) {
		this.perInformation11 = perInformation11;
	}

	@Column(name = "per_information11", nullable = true, length = 150)	
	public String getPerInformation11() {
		return perInformation11;
	}

	public void setPerInformation12(String perInformation12) {
		this.perInformation12 = perInformation12;
	}

	@Column(name = "per_information12", nullable = true, length = 150)	
	public String getPerInformation12() {
		return perInformation12;
	}

	public void setPerInformation13(String perInformation13) {
		this.perInformation13 = perInformation13;
	}

	@Column(name = "per_information13", nullable = true, length = 150)	
	public String getPerInformation13() {
		return perInformation13;
	}

	public void setPerInformation14(String perInformation14) {
		this.perInformation14 = perInformation14;
	}

	@Column(name = "per_information14", nullable = true, length = 150)	
	public String getPerInformation14() {
		return perInformation14;
	}

	public void setPerInformation15(String perInformation15) {
		this.perInformation15 = perInformation15;
	}

	@Column(name = "per_information15", nullable = true, length = 150)	
	public String getPerInformation15() {
		return perInformation15;
	}

	public void setPerInformation16(String perInformation16) {
		this.perInformation16 = perInformation16;
	}

	@Column(name = "per_information16", nullable = true, length = 150)	
	public String getPerInformation16() {
		return perInformation16;
	}

	public void setPerInformation17(String perInformation17) {
		this.perInformation17 = perInformation17;
	}

	@Column(name = "per_information17", nullable = true, length = 150)	
	public String getPerInformation17() {
		return perInformation17;
	}

	public void setPerInformation18(String perInformation18) {
		this.perInformation18 = perInformation18;
	}

	@Column(name = "per_information18", nullable = true, length = 150)	
	public String getPerInformation18() {
		return perInformation18;
	}

	public void setPerInformation19(String perInformation19) {
		this.perInformation19 = perInformation19;
	}

	@Column(name = "per_information19", nullable = true, length = 150)	
	public String getPerInformation19() {
		return perInformation19;
	}

	public void setPerInformation20(String perInformation20) {
		this.perInformation20 = perInformation20;
	}

	@Column(name = "per_information20", nullable = true, length = 150)	
	public String getPerInformation20() {
		return perInformation20;
	}

	public void setPerInformation21(String perInformation21) {
		this.perInformation21 = perInformation21;
	}

	@Column(name = "per_information21", nullable = true, length = 150)	
	public String getPerInformation21() {
		return perInformation21;
	}

	public void setPerInformation22(String perInformation22) {
		this.perInformation22 = perInformation22;
	}

	@Column(name = "per_information22", nullable = true, length = 150)	
	public String getPerInformation22() {
		return perInformation22;
	}

	public void setPerInformation23(String perInformation23) {
		this.perInformation23 = perInformation23;
	}

	@Column(name = "per_information23", nullable = true, length = 150)	
	public String getPerInformation23() {
		return perInformation23;
	}

	public void setPerInformation24(String perInformation24) {
		this.perInformation24 = perInformation24;
	}

	@Column(name = "per_information24", nullable = true, length = 150)	
	public String getPerInformation24() {
		return perInformation24;
	}

	public void setPerInformation25(String perInformation25) {
		this.perInformation25 = perInformation25;
	}

	@Column(name = "per_information25", nullable = true, length = 150)	
	public String getPerInformation25() {
		return perInformation25;
	}

	public void setPerInformation26(String perInformation26) {
		this.perInformation26 = perInformation26;
	}

	@Column(name = "per_information26", nullable = true, length = 150)	
	public String getPerInformation26() {
		return perInformation26;
	}

	public void setPerInformation27(String perInformation27) {
		this.perInformation27 = perInformation27;
	}

	@Column(name = "per_information27", nullable = true, length = 150)	
	public String getPerInformation27() {
		return perInformation27;
	}

	public void setPerInformation28(String perInformation28) {
		this.perInformation28 = perInformation28;
	}

	@Column(name = "per_information28", nullable = true, length = 150)	
	public String getPerInformation28() {
		return perInformation28;
	}

	public void setPerInformation29(String perInformation29) {
		this.perInformation29 = perInformation29;
	}

	@Column(name = "per_information29", nullable = true, length = 150)	
	public String getPerInformation29() {
		return perInformation29;
	}

	public void setPerInformation30(String perInformation30) {
		this.perInformation30 = perInformation30;
	}

	@Column(name = "per_information30", nullable = true, length = 150)	
	public String getPerInformation30() {
		return perInformation30;
	}

	public void setObjectVersionInt(Integer objectVersionInt) {
		this.objectVersionInt = objectVersionInt;
	}

	@Column(name = "object_version_int", nullable = true, length = 11)	
	public Integer getObjectVersionInt() {
		return objectVersionInt;
	}

	public void setDatetimeOfDeath(Date datetimeOfDeath) {
		this.datetimeOfDeath = datetimeOfDeath;
	}

	@Column(name = "datetime_of_death", nullable = true, length = 0)	
	public Date getDatetimeOfDeath() {
		return datetimeOfDeath;
	}

	public void setOriginalDatetimeOfHire(Date originalDatetimeOfHire) {
		this.originalDatetimeOfHire = originalDatetimeOfHire;
	}

	@Column(name = "original_datetime_of_hire", nullable = true, length = 0)	
	public Date getOriginalDatetimeOfHire() {
		return originalDatetimeOfHire;
	}

	public void setTownOfBirth(String townOfBirth) {
		this.townOfBirth = townOfBirth;
	}

	@Column(name = "town_of_birth", nullable = true, length = 90)	
	public String getTownOfBirth() {
		return townOfBirth;
	}

	public void setRegionOfBirth(String regionOfBirth) {
		this.regionOfBirth = regionOfBirth;
	}

	@Column(name = "region_of_birth", nullable = true, length = 90)	
	public String getRegionOfBirth() {
		return regionOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	@Column(name = "country_of_birth", nullable = true, length = 90)	
	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setGlobalPersonId(String globalPersonId) {
		this.globalPersonId = globalPersonId;
	}

	@Column(name = "global_person_id", nullable = true, length = 30)	
	public String getGlobalPersonId() {
		return globalPersonId;
	}

	public void setCoordBenMedPlName(String coordBenMedPlName) {
		this.coordBenMedPlName = coordBenMedPlName;
	}

	@Column(name = "coord_ben_med_pl_name", nullable = true, length = 80)	
	public String getCoordBenMedPlName() {
		return coordBenMedPlName;
	}

	public void setCoordBenMedInsrCrrName(String coordBenMedInsrCrrName) {
		this.coordBenMedInsrCrrName = coordBenMedInsrCrrName;
	}

	@Column(name = "coord_ben_med_insr_crr_name", nullable = true, length = 80)	
	public String getCoordBenMedInsrCrrName() {
		return coordBenMedInsrCrrName;
	}

	public void setCoordBenMedInsrCrrIdent(String coordBenMedInsrCrrIdent) {
		this.coordBenMedInsrCrrIdent = coordBenMedInsrCrrIdent;
	}

	@Column(name = "coord_ben_med_insr_crr_ident", nullable = true, length = 80)	
	public String getCoordBenMedInsrCrrIdent() {
		return coordBenMedInsrCrrIdent;
	}

	public void setCoordBenMedExtEr(String coordBenMedExtEr) {
		this.coordBenMedExtEr = coordBenMedExtEr;
	}

	@Column(name = "coord_ben_med_ext_er", nullable = true, length = 80)	
	public String getCoordBenMedExtEr() {
		return coordBenMedExtEr;
	}

	public void setCoordBenMedCvgStrtDt(Date coordBenMedCvgStrtDt) {
		this.coordBenMedCvgStrtDt = coordBenMedCvgStrtDt;
	}

	@Column(name = "coord_ben_med_cvg_strt_dt", nullable = true, length = 0)	
	public Date getCoordBenMedCvgStrtDt() {
		return coordBenMedCvgStrtDt;
	}

	public void setCoordBenMedCvgEndDt(Date coordBenMedCvgEndDt) {
		this.coordBenMedCvgEndDt = coordBenMedCvgEndDt;
	}

	@Column(name = "coord_ben_med_cvg_end_dt", nullable = true, length = 0)	
	public Date getCoordBenMedCvgEndDt() {
		return coordBenMedCvgEndDt;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	@Column(name = "party_id", nullable = true, length = 11)	
	public Integer getPartyId() {
		return partyId;
	}

	public void setNpwInt(String npwInt) {
		this.npwInt = npwInt;
	}

	@Column(name = "npw_int", nullable = true, length = 30)	
	public String getNpwInt() {
		return npwInt;
	}

	public void setCurrentNpwFlag(String currentNpwFlag) {
		this.currentNpwFlag = currentNpwFlag;
	}

	@Column(name = "current_npw_flag", nullable = true, length = 30)	
	public String getCurrentNpwFlag() {
		return currentNpwFlag;
	}

	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}

	@Column(name = "global_name", nullable = true, length = 240)	
	public String getGlobalName() {
		return globalName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	@Column(name = "local_name", nullable = true, length = 240)	
	public String getLocalName() {
		return localName;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient	
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
