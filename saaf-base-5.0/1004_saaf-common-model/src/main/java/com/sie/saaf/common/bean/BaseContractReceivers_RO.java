package com.sie.saaf.common.bean;

public class BaseContractReceivers_RO {

    /**
     * 1.如果签署人为个人，
     * 则不填。 企业名称需
     * 与工商局注册的企业
     * 全称完全一致。 注意
     * ： 如果
     * ifProxyClaimer 为
     * false， 需填写
     * userAccount， 不填写
     * proxyClaimerAccount
     * s； 如果
     * ifProxyClaimer 为
     * true， 则不填
     * userAccount， 合同可
     * 由 pr
     * oxyClaimerAccount
     * s 指定的上上签账号
     * 中的一个认领合同。
     *
     * 2.是否必填： 是
     * 3.名称：企 业 名 称
     */
    private String enterpriseName;



    /**
     * 1.须使用中国大陆手
     * 机。 该手机可以收到
     * 合同签署通知， 但不
     * 能用于签署校验。 假
     * 定用户使用邮箱 a 注
     * 册账号， 设手机 b 为
     * 通知手机； 开发者在
     * 接口中添加了手机 c
     * 为临时通知手机， 那
     * 么邮箱 a、 手机 b、
     * 手机 c 都能收到签署
     * 通知， 但必须使用邮
     * 箱 a 或手机 b 获取签
     * 约校验验证码
     *
     * 2.是否必填： 否
     * 3.名称：临 时 通 知 手 机
     */
    private String notification;

    private String companyFlag; //企业标识， 1为甲方，2为乙方，3为丙方


    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setCompanyFlag(String companyFlag) {
        this.companyFlag = companyFlag;
    }

    public String getCompanyFlag() {
        return companyFlag;
    }
}
