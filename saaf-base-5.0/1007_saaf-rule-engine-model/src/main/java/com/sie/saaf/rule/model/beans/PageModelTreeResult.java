package com.sie.saaf.rule.model.beans;

import java.util.List;

/**
 * Created by Admin on 2017/8/2.
 */
public class PageModelTreeResult {

    /**
     * 弹出框标题
     */
    private String title;

    /**
     * 树结构内容
     */
    private List<?> trees;

    /**
     *  约定保存时将哪个变量传给后台
     */
    private String idKey;

    /**
     * 约定页面展示哪个字段
     */
    private String nameKey;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getTrees() {
        return trees;
    }

    public void setTrees(List<?> trees) {
        this.trees = trees;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;

    }
}
