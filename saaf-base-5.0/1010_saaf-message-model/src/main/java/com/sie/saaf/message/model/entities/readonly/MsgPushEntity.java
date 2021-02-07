package com.sie.saaf.message.model.entities.readonly;

import java.util.List;

/**
 * @author huangminglin
 * @version v1.0
 * @description:
 * @Name:MsgPushEntity
 * @date 2018/10/12 16:11
 */
public class MsgPushEntity {

    private List<String> alias;   //发送人别名
    private String alert;           //发送内容
    private String title;           //发送标题

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
