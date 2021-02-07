package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;

import java.util.Date;

/**
 * BasePersonEntity_HI Entity Object Wed Dec 06 10:57:20 CST 2017 Auto Generate
 */
@Entity
@Table(name = "base_person")
public class BasePersonEntity_HI {
    private Integer personId; // 人员Id
    private String employeeNumber; // 员工号
    private String personName; // 人员姓名
    private String personType; // IN:内部员工，OUT：经销商（财务、商务、仓管）、门店、兼职导购
    private String sex; // 性别
    @JSONField(format = "yyyy-MM-dd")
    private Date birthDay; // 生日
    private String cardNo; // 身份证号
    private String enabled; // 启用标识
    private String telPhone; // 电话号码
    private String mobilePhone; // 手机号码
    private String email; // 邮箱地址
    private String postalAddress; // 通信地址
    private String postcode; // 邮编
    private Integer sourceId;//源系统ID
    private String sourceCode; // 系统来源
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; // 创建日期
    private Integer createdBy; // 创建人
    private Integer lastUpdatedBy; // 更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; // 更新日期
    private Integer versionNum; // 版本号
    private Integer lastUpdateLogin;
    private Integer operatorUserId;

    @Id
    @SequenceGenerator(name = "SEQ_BASE_PERSON", sequenceName = "SEQ_BASE_PERSON", allocationSize = 1)
  	@GeneratedValue(generator = "SEQ_BASE_PERSON", strategy = GenerationType.SEQUENCE)
    @Column(name = "person_id", nullable = false, length = 11)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Column(name = "employee_number", nullable = true, length = 30)
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Column(name = "person_name", nullable = true, length = 150)
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Column(name = "person_type", nullable = true, length = 10)
    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    @Column(name = "sex", nullable = true, length = 10)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "birth_day", nullable = true, length = 0)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Column(name = "card_no", nullable = true, length = 20)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Column(name = "enabled", nullable = true, length = 1)
    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Column(name = "tel_phone", nullable = true, length = 30)
    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    @Column(name = "mobile_phone", nullable = true, length = 30)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Column(name = "email", nullable = true, length = 60)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "postal_address", nullable = true, length = 240)
    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Column(name = "postcode", nullable = true, length = 10)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Column(name = "source_id", nullable = true, length = 11)
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "source_code", nullable = true, length = 30)
    public String getSourceCode() { return sourceCode; }

    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    @Column(name = "creation_date", nullable = true, length = 0)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "created_by", nullable = true, length = 11)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "last_updated_by", nullable = true, length = 11)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_update_date", nullable = true, length = 0)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 11)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Column(name = "last_update_login", nullable = true, length = 11)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }
}
