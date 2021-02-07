package com.sie.watsons.base.pos.utils;

public enum CommonEnum {
    /**
     * 编码方式
     */
    UTF8("utf-8"),


    /**========================================表名============================**/
    /**
     * contract审核更新头
     */
    EQU_POS_CONTRACT_UPDATE_HEAD("EQU_POS_CONTRACT_UPDATE_HEAD"),
    /**
     * contract审核更新行
     */
    EQU_POS_CONTRACT_UPDATE_LINE("EQU_POS_CONTRACT_UPDATE_LINE"),
    /**
     * spend审核更新头
     */
    EQU_POS_SPEND_UPDATE_HEAD("EQU_POS_SPEND_UPDATE_HEAD"),
    /**
     * spend审核更新行
     */
    EQU_POS_SPEND_UPDATE_LINE("EQU_POS_SPEND_UPDATE_LINE"),
    /**
     * score审核更新头
     */
    EQU_POS_SCORE_UPDATE_HEAD("EQU_POS_SCORE_UPDATE_HEAD"),
    /**
     * score审核更新行
     */
    EQU_POS_SCORE_UPDATE_LINE("EQU_POS_SCORE_UPDATE_LINE"),
    /**
     * CSR审核更新头
     */
    EQU_POS_CSR_UPDATE_HEAD("EQU_POS_CSR_UPDATE_HEAD"),
    /**
     * CSR审核更新头
     */
    EQU_POS_CSR_UPDATE_LINE("EQU_POS_CSR_UPDATE_LINE"),
    /**
     * 质量审核更新头
     */
    EQU_POS_QUALITY_UPDATE_HEAD("EQU_POS_QUALITY_UPDATE_HEAD"),
    /**
     * 质量审核更新行
     */
    EQU_POS_QUALITY_UPDATE_LINE("EQU_POS_QUALITY_UPDATE_LINE");
    /**
     * ========================================表名============================
     **/
    private String value;

    public String getValue() {
        return value;
    }

    CommonEnum(String value) {
        this.value = value;
    }
}
