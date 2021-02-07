package com.yhg.transaction.compensation.beans;

public class IdempotentBean {
    private String queueName;
    private String tableName;
    private String entitiyName;
    private String daoName;
    private String serverName;
    public IdempotentBean() {
        super();
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setEntitiyName(String entitiyName) {
        this.entitiyName = entitiyName;
    }

    public String getEntitiyName() {
        return entitiyName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }
}
