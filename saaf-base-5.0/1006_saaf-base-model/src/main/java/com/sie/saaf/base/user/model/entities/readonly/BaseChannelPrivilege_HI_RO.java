package com.sie.saaf.base.user.model.entities.readonly;

public class BaseChannelPrivilege_HI_RO {

    public static final String QUERY="SELECT ACCESS_TYPE AS accessType,TRANSACTION_TYPE_ID AS transactionTypeId,COUNT(ACCESS_TYPE) AS qty  FROM base_channel_privilege GROUP BY ACCESS_TYPE,TRANSACTION_TYPE_ID ";


    private String accessType;
    private Integer transactionTypeId;
    private Integer qty;

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
