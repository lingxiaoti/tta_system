package com.sie.saaf.rule.model.beans;

import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2017/8/2.
 *
 *
 * eg:
 *  td:{'name':'姓名','sex':'性别'}
 *  tb [{'name':'张萨拉','sex':'女'},{'name':'李四','sex':'男'}]
 *
 *
 */



public class PageModelTalbelResult {

    /**
     * 弹出框标题
     */
    private String title;
    /**
     * 表头
     */
    private Map<String,String> th;
    /**
     * 表格内容
     */
    private List<?> tb;

    /**
     * 约定保存时将哪个变量传给后台
     */
    private String idKey;


    public Map<String, String> getTh() {
        return th;
    }

    public void setTh(Map<String, String> th) {
        this.th = th;
    }

    public List<?> getTb() {
        return tb;
    }

    public void setTb(List<?> tb) {
        this.tb = tb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }
}
