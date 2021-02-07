package com.sie.saaf.base.user.model.entities;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by chenzg on 2018/3/6.
 */
@Entity
@Table(name = "wx_user")
public class WxUserEntity_HI {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "userid", nullable = true, length = 300)
    private String userid;

    @Column(name = "name", nullable = true, length = 300)
    private String name;

    @Column(name = "department", nullable = true, length = 300)
    private String department;

    @Column(name = "position", nullable = true, length = 300)
    private String position;

    @Column(name = "mobile", nullable = true, length = 300)
    private String mobile;

    @Column(name = "gender", nullable = true, length = 30)
    private String gender;

    @Column(name = "email", nullable = true, length = 300)
    private String email;

    @Column(name = "avatar", nullable = true, length = 300)
    private String avatar;

    @Column(name = "status", nullable = true, length = 30)
    private String status;

    @Column(name = "enable", nullable = true, length = 30)
    private String enable;

    @Column(name = "isleader", nullable = true, length = 30)
    private String isleader;

    @Column(name = "extattr", nullable = true, length = 300)
    private String extattr;

    @Column(name = "hide_mobile", nullable = true, length = 300)
    private String hide_mobile;

    @Column(name = "english_name", nullable = true, length = 300)
    private String english_name;

    @Column(name = "telephone", nullable = true, length = 300)
    private String telephone;

    @Column(name = "order_", nullable = true, length = 300)
    private String order;

    @Transient
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; // 创建日期

    @Transient
    private Integer createdBy;

    @Transient
    private Integer LastUpdatedBy;

    @Transient
    private Integer LastUpdateLogin;

    @Transient
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date LastUpdateDate;

    @Transient
    private Integer operatorUserId;

    @Transient
    private String password;//密码，明文，不在数据库保存

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getIsleader() {
        return isleader;
    }

    public void setIsleader(String isleader) {
        this.isleader = isleader;
    }

    public String getExtattr() {
        return extattr;
    }

    public void setExtattr(String extattr) {
        this.extattr = extattr;
    }

    public String getHide_mobile() {
        return hide_mobile;
    }

    public void setHide_mobile(String hide_mobile) {
        this.hide_mobile = hide_mobile;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getLastUpdatedBy() {
        return LastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        LastUpdatedBy = lastUpdatedBy;
    }

    public Integer getLastUpdateLogin() {
        return LastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        LastUpdateLogin = lastUpdateLogin;
    }

    public Date getLastUpdateDate() {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }
}
