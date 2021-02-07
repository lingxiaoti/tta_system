package com.sie.wastons.ttadata.model.entities.readyonly;

import com.sie.wastons.sql.annotation.SqlBinder;

import java.math.BigDecimal;

public class VmiPersonLevelInfoEntity_HI_RO {
    public static String personLevel_sql = "select bu.user_id         userId,\n" +
            "       bp.person_id       personId,\n" +
            "       bp.employee_number employeeNumber,\n" +
            "       bp.person_name     personName,\n" +
            "       bp.sex             sex,\n" +
            "       bp.email           email,\n" +
            "       bp.mobile_phone    mobilePhone,\n" +
            "       bpl.mgr_person_id  mgrPersonId\n" +
            "  from base_users bu\n" +
            "  left join base_person bp\n" +
            "    on bu.person_id = bp.person_id\n" +
            "  left join base_person_level bpl\n" +
            "    on bp.person_id = bpl.person_id\n" +
            " where 1 = 1";

    @SqlBinder(sqlColumn ="bu.user_id",opreation = SqlBinder.OPR.EQ)
    private BigDecimal userId;
    @SqlBinder(sqlColumn ="bp.person_id",opreation = SqlBinder.OPR.EQ)
    private BigDecimal personId;
    @SqlBinder(sqlColumn ="bp.employee_number",opreation = SqlBinder.OPR.EQ)
    private String employeeNumber;
    private String personName;
    private String sex;
    private String email;
    @SqlBinder(sqlColumn ="bpl.mgr_person_id",opreation = SqlBinder.OPR.EQ)
    private BigDecimal mgrPersonId;
    private String mobilePhone;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public BigDecimal getPersonId() {
        return personId;
    }

    public void setPersonId(BigDecimal personId) {
        this.personId = personId;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getMgrPersonId() {
        return mgrPersonId;
    }

    public void setMgrPersonId(BigDecimal mgrPersonId) {
        this.mgrPersonId = mgrPersonId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
