package com.sie.watsons.base.pos.category.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * EquPosSupplierCategoryEntity_HI Entity Object
 * Thu Sep 05 09:25:56 CST 2019  Auto Generate
 */
@Entity
@Table(name = "EQU_POS_SUPPLIER_CATEGORY")
public class EquPosSupplierCategoryEntity_HI {
    private Integer categoryMaintainId;
    private Integer departmentId;
    private String categoryCodeFirst;
    private String categoryCodeSecond;
    private String categoryCodeThird;
    private String factoryCategoryCode;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date invalidDate;
    private Integer versionNum;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private Integer createdBy;
    private Integer lastUpdatedBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate;
    private Integer lastUpdateLogin;
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
    private Integer operatorUserId;
    private String categoryGroup;
    private String categoryFirstMeaning;
    private String categorySecondMeaning;
    private String categoryThirdMeaning;
    private String categoryFirstDescription;
    private String categorySecondDescription;
    private String categoryThirdDescription;
    private String deptCode;

    public void setCategoryMaintainId(Integer categoryMaintainId) {
        this.categoryMaintainId = categoryMaintainId;
    }

    @Id
    @SequenceGenerator(name = "EQU_POS_SUPPLIER_CATEGORY_SEQ", sequenceName = "EQU_POS_SUPPLIER_CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "EQU_POS_SUPPLIER_CATEGORY_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "category_maintain_id", nullable = false, length = 22)
    public Integer getCategoryMaintainId() {
        return categoryMaintainId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "department_id", nullable = false, length = 22)
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setCategoryCodeFirst(String categoryCodeFirst) {
        this.categoryCodeFirst = categoryCodeFirst;
    }

    @Column(name = "category_code_first", nullable = false, length = 30)
    public String getCategoryCodeFirst() {
        return categoryCodeFirst;
    }

    public void setCategoryCodeSecond(String categoryCodeSecond) {
        this.categoryCodeSecond = categoryCodeSecond;
    }

    @Column(name = "category_code_second", nullable = false, length = 30)
    public String getCategoryCodeSecond() {
        return categoryCodeSecond;
    }

    public void setCategoryCodeThird(String categoryCodeThird) {
        this.categoryCodeThird = categoryCodeThird;
    }

    @Column(name = "category_code_third", nullable = false, length = 30)
    public String getCategoryCodeThird() {
        return categoryCodeThird;
    }

    public void setFactoryCategoryCode(String factoryCategoryCode) {
        this.factoryCategoryCode = factoryCategoryCode;
    }

    @Column(name = "factory_category_code", nullable = false, length = 30)
    public String getFactoryCategoryCode() {
        return factoryCategoryCode;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    @Column(name = "invalid_date", nullable = true, length = 7)
    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    @Version
    @Column(name = "version_num", nullable = true, length = 22)
    public Integer getVersionNum() {
        return versionNum;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(name = "creation_date", nullable = false, length = 7)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by", nullable = false, length = 22)
    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "last_updated_by", nullable = false, length = 22)
    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Column(name = "last_update_date", nullable = false, length = 7)
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    @Column(name = "last_update_login", nullable = true, length = 22)
    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    @Column(name = "attribute1", nullable = true, length = 240)
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    @Column(name = "attribute2", nullable = true, length = 240)
    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    @Column(name = "attribute3", nullable = true, length = 240)
    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    @Column(name = "attribute4", nullable = true, length = 240)
    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    @Column(name = "attribute5", nullable = true, length = 240)
    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    @Column(name = "attribute6", nullable = true, length = 240)
    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    @Column(name = "attribute7", nullable = true, length = 240)
    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    @Column(name = "attribute8", nullable = true, length = 240)
    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    @Column(name = "attribute9", nullable = true, length = 240)
    public String getAttribute9() {
        return attribute9;
    }

    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }

    @Column(name = "attribute10", nullable = true, length = 240)
    public String getAttribute10() {
        return attribute10;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    @Transient
    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    @Column(name = "category_group", nullable = true, length = 240)
    public String getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    @Column(name = "category_first_meaning", nullable = true, length = 50)
    public String getCategoryFirstMeaning() {
        return categoryFirstMeaning;
    }

    public void setCategoryFirstMeaning(String categoryFirstMeaning) {
        this.categoryFirstMeaning = categoryFirstMeaning;
    }

    @Column(name = "category_second_meaning", nullable = true, length = 50)
    public String getCategorySecondMeaning() {
        return categorySecondMeaning;
    }

    public void setCategorySecondMeaning(String categorySecondMeaning) {
        this.categorySecondMeaning = categorySecondMeaning;
    }

    @Column(name = "category_third_meaning", nullable = true, length = 50)
    public String getCategoryThirdMeaning() {
        return categoryThirdMeaning;
    }

    public void setCategoryThirdMeaning(String categoryThirdMeaning) {
        this.categoryThirdMeaning = categoryThirdMeaning;
    }
    @Column(name = "category_first_description", nullable = true, length = 50)
    public String getCategoryFirstDescription() {
        return categoryFirstDescription;
    }

    public void setCategoryFirstDescription(String categoryFirstDescription) {
        this.categoryFirstDescription = categoryFirstDescription;
    }
    @Column(name = "category_second_description", nullable = true, length = 50)
    public String getCategorySecondDescription() {
        return categorySecondDescription;
    }

    public void setCategorySecondDescription(String categorySecondDescription) {
        this.categorySecondDescription = categorySecondDescription;
    }
    @Column(name = "category_third_description", nullable = true, length = 50)
    public String getCategoryThirdDescription() {
        return categoryThirdDescription;
    }

    public void setCategoryThirdDescription(String categoryThirdDescription) {
        this.categoryThirdDescription = categoryThirdDescription;
    }
    @Column(name = "dept_code", nullable = true, length = 50)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}
