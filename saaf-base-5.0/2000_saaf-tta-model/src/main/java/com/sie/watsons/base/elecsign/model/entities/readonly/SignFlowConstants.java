package com.sie.watsons.base.elecsign.model.entities.readonly;

public class SignFlowConstants {

    /**
     * 用户类型
     */
    public static enum UserType {
        PERSON,       // 个人
        ENTERPRISE;   // 企业
    }

    /**
     * 合同参与者类型
     */
    public static enum ReceiverType {
        SENDER,      // 发送者
        SIGNER,      // 签署者
        CC_USER,     // 抄送方
        APPROVER,    // 审批人
        VIEWER,      // 查阅者
        DISTRIBUTOR, // 分配者
        UNDEFINER;   // 未知
    }

    /**
     * 签署标签类型
     */
    public static enum SignLabelType {
        SIGNATURE,          // 签名
        DATE,               // 日期
        SEAL,               // 签章（公章）
        RIDING_SEAL,        // 骑缝章（工作）
        RIDING_SIGNATURE,   // 骑缝章（签名）
        RIDING_PART,        // 骑缝章派生
        GROUP_SIGN,         // 团签
        BESTSIGN_SIGNATURE, // 上上签签名
        QR_CODE;            // 二维码
    }
}
