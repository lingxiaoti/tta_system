package com.sie.wastons.ttadata.model.entities.readyonly;

import com.alibaba.fastjson.annotation.JSONField;
import com.sie.wastons.sql.annotation.SqlBinder;

import java.math.BigDecimal;
import java.util.Date;

public class VmiPersonInfoEntity_HI_RO {
    public static String personInfo_sql = "select bu.user_id         userId,\n" +
            "       bu.phone_number    phoneNumber,\n" +
            "       bu.isadmin         isadmin,\n" +
            "       bu.user_name       userName,\n" +
            "       bu.user_desc       userDesc,\n" +
            "       bu.user_full_name  userFullName,\n" +
            "       bp.employee_number employeeNumber,\n" +
            "       bp.person_id       personId,\n" +
            "       bp.person_name     personName,\n" +
            "       bp.sex             sex,\n" +
            "       bp.birth_day       birthDay,\n" +
            "       bp.card_no         cardNo,\n" +
            "       bp.tel_phone       telPhone,\n" +
            "       bp.mobile_phone    mobilePhone,\n" +
            "       bp.email           email,\n" +
            "       bp.postal_address  postalAddress,\n" +
            "       bp.postcode        postcode,\n" +
            "       bp.grand           grand,\n" +
            "       bp.person_name_en  personNameEn,\n" +
            "       bp.dept_no         deptNo,\n" +
            "       bp.post_name       postName,\n" +
            "       bp.area            area,\n" +
            "       bp.market          market,\n" +
            "       bp.org_code        orgCode\n" +
            "  from base_users bu\n" +
            "  left join base_person bp\n" +
            "    on bu.person_id = bp.person_id";

    @SqlBinder(sqlColumn ="bu.user_id",opreation = SqlBinder.OPR.EQ)
    private BigDecimal userId;
    private String phoneNumber;
    private String isadmin;
    @SqlBinder(sqlColumn ="bu.user_name",opreation = SqlBinder.OPR.CONTAIN)
    private String userName;
    private String userDesc;
    @SqlBinder(sqlColumn ="bu.user_full_name",opreation = SqlBinder.OPR.CONTAIN)
    private String userFullName;
    @SqlBinder(sqlColumn ="bp.employee_number",opreation = SqlBinder.OPR.CONTAIN)
    private String employeeNumber;
    private BigDecimal personId;
    @SqlBinder(sqlColumn ="bp.person_name",opreation = SqlBinder.OPR.CONTAIN)
    private String personName;
    private String sex;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date birthDay;
    private String cardNo;
    private String telPhone;
    private String mobilePhone;
    private String email;
    private String postalAddress;
    private String postcode;
    private BigDecimal grand;
    @SqlBinder(sqlColumn ="bp.person_name_en",opreation = SqlBinder.OPR.CONTAIN)
    private String personNameEn;
    @SqlBinder(sqlColumn ="bp.dept_no",opreation = SqlBinder.OPR.CONTAIN)
    private String deptNo;
    @SqlBinder(sqlColumn ="bp.post_name",opreation = SqlBinder.OPR.CONTAIN)
    private String postName;
    private String area;
    private String market;
    private String orgCode;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(String isadmin) {
        this.isadmin = isadmin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public BigDecimal getPersonId() {
        return personId;
    }

    public void setPersonId(BigDecimal personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public BigDecimal getGrand() {
        return grand;
    }

    public void setGrand(BigDecimal grand) {
        this.grand = grand;
    }

    public String getPersonNameEn() {
        return personNameEn;
    }

    public void setPersonNameEn(String personNameEn) {
        this.personNameEn = personNameEn;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
