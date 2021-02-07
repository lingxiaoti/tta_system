package com.sie.saaf.base.cms.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseCmsArticleEntity_HI_RO Entity Object
 * Fri Feb 09 09:56:03 CST 2018  Auto Generate
 */

public class BaseCmsArticleEntity_HI_RO {
    public static final String QUERY_ALL_ARTICLE_SQL = "SELECT DISTINCT \n" +
            "\t article.article_id AS articleId,\n" +
            "\t article.article_title AS articleTitle,\n" +
            "\t to_char(article.article_content) AS articleContent,\n" +
            "\t article.article_description AS articleDescription,\n" +
            "\t article.article_publish_date AS articlePublishDate,\n" +
            "\t article.article_type AS articleType, \n" +
            "\t u.user_desc AS userDesc,\n" +
            "\t blv.meaning AS articleTypeDesc, \n" +
            "\t article.system_code systemCode, \n" +
            "\t blv1.meaning AS systemCodeDesc, \n" +
            "\t article.enabled_flag AS enabledFlag, \n" +
            "\t article.article_status AS articleStatus \n" +
            " FROM \n" +
            "\t base_cms_article article \n" +
            "\tLEFT JOIN base_users u ON article.created_by = u.user_id \n" +
            "\tLEFT JOIN base_lookup_values blv ON article.article_type=blv.lookup_code\n" +
            "\tLEFT JOIN base_lookup_values blv1 ON article.system_code=blv1.lookup_code\n" +
            "\tWHERE\n" +
            "\t nvl(article.article_status,'Y') <> 'N'\n";
    public static final String QUERY_NEWS ="SELECT * FROM( \n" +
            "( SELECT DISTINCT \n" +
            "\t article.article_id AS articleId, \n" +
            "\t article.article_title AS articleTitle, \n" +
            "\t article.ARTICLE_CONTENT AS articleContent, \n" +
            "\t article.article_description AS articleDescription, \n" +
            "\t article.top_to_date AS topToDate, \n" +
            "\t article.article_publish_date AS articlePublishDate, \n" +
            "\t article.article_type AS articleType, \n" +
            "\t u.user_desc AS userDesc, \n" +
            "\t blv.meaning AS articleTypeDesc, \n" +
            "\t 'Y' AS topFlag, \n" +
            "\t article.system_code AS systemCode \n" +
            "FROM \n" +
            "\t base_cms_article article \n" +
            "\t LEFT JOIN base_users u ON article.created_by = u.user_id \n" +
            "\t LEFT JOIN base_lookup_values blv ON article.article_type = blv.lookup_code \n" +
            "\t WHERE \n" +
            "\t nvl(article.article_status,'Y') <> 'N' \n" +
            "\t AND nvl(article.enabled_flag,'Y') <> 'N' \n" +
            "   AND article.top_to_date>=sysdate\n" +
            "\t AND article.article_publish_date<=sysdate \n" +
            "\t AND article.article_type = 'NEWS' \n" +
            "\t ORDER BY article.article_publish_date DESC LIMIT 3 )\n" +
            " UNION ALL \n" +
            "\t ( SELECT DISTINCT\n" +
            "\t article.article_id AS articleId, \n" +
            "\t article.article_title AS articleTitle,\n" +
            "\t article.ARTICLE_CONTENT AS articleContent,\n" +
            "\t article.article_description AS articleDescription,\n" +
            "\t article.top_to_date AS topToDate, \n" +
            "\t article.article_publish_date AS articlePublishDate, \n" +
            "\t article.article_type AS articleType, \n" +
            "\t u.user_desc AS userDesc, \n" +
            "\t blv.meaning AS articleTypeDesc, \n" +
            "\t 'N' AS topFlag, \n" +
            "\t article.system_code AS systemCode \n" +
            " FROM \n" +
            "\t base_cms_article article \n" +
            "\t LEFT JOIN base_users u ON article.created_by = u.user_id \n" +
            "\t LEFT JOIN base_lookup_values blv ON article.article_type = blv.lookup_code \n" +
            "\t WHERE \n" +
            "\t nvl(article.article_status,'Y') <> 'N' \n" +
            "\t AND nvl(article.enabled_flag,'Y') <> 'N' \n" +
            "   AND(article.top_to_date<sysdate OR ISNULL(article.top_to_date) OR article.top_flag ='N') \n" +
            "\t AND article.article_publish_date<=sysdate \n" +
            "\t AND article.article_type = 'NEWS' \n" +
            "\t ORDER BY article.article_publish_date DESC LIMIT 8 ) \n" +
            "\t LIMIT 8 ) notice \n" +
            "\t WHERE 1=1 \n";
    public static final String QUERY_NOTICE ="SELECT * FROM( \n" +
            "( SELECT DISTINCT \n" +
            "\t article.article_id AS articleId, \n" +
            "\t article.article_title AS articleTitle, \n" +
            "\t article.ARTICLE_CONTENT AS articleContent, \n" +
            "\t article.article_description AS articleDescription, \n" +
            "\t article.top_to_date AS topToDate, \n" +
            "\t article.article_publish_date AS articlePublishDate, \n" +
            "\t article.article_type AS articleType, \n" +
            "\t u.user_desc AS userDesc, \n" +
            "\t blv.meaning AS articleTypeDesc, \n" +
            "\t 'Y' AS topFlag, \n" +
            "\t article.system_code AS systemCode \n" +
            "FROM \n" +
            "\t base_cms_article article \n" +
            "\t LEFT JOIN base_users u ON article.created_by = u.user_id \n" +
            "\t LEFT JOIN base_lookup_values blv ON article.article_type = blv.lookup_code \n" +
            "\t WHERE \n" +
            "\t nvl(article.article_status,'Y') <> 'N' \n" +
            "\t AND nvl(article.enabled_flag,'Y') <> 'N' \n" +
            "   AND article.top_to_date>=sysdate\n" +
            "\t AND article.article_publish_date<=sysdate \n" +
            "\t AND article.article_type = 'NOTICE' \n" +
            "\t ORDER BY article.article_publish_date DESC LIMIT 3 )\n" +
            " UNION ALL \n" +
            "\t ( SELECT DISTINCT \n" +
            "\t article.article_id AS articleId, \n" +
            "\t article.article_title AS articleTitle,\n" +
            "\t article.ARTICLE_CONTENT AS articleContent,\n" +
            "\t article.article_description AS articleDescription,\n" +
            "\t article.top_to_date AS topToDate, \n" +
            "\t article.article_publish_date AS articlePublishDate, \n" +
            "\t article.article_type AS articleType, \n" +
            "\t u.user_desc AS userDesc, \n" +
            "\t blv.meaning AS articleTypeDesc, \n" +
            "\t 'N' AS topFlag, \n" +
            "\t article.system_code AS systemCode \n" +
            " FROM \n" +
            "\t base_cms_article article \n" +
            "\t LEFT JOIN base_users u ON article.created_by = u.user_id \n" +
            "\t LEFT JOIN base_lookup_values blv ON article.article_type = blv.lookup_code \n" +
            "\t WHERE \n" +
            "\t nvl(article.article_status,'Y') <> 'N' \n" +
            "\t AND nvl(article.enabled_flag,'Y') <> 'N' \n" +
            "   AND(article.top_to_date<sysdate OR ISNULL(article.top_to_date) OR article.top_flag ='N') \n" +
            "\t AND article.article_publish_date<=sysdate \n" +
            "\t AND article.article_type = 'NOTICE' \n" +
            "\t ORDER BY article.article_publish_date DESC LIMIT 8 ) \n" +
            "\t LIMIT 8 ) notice \n" +
            "\t WHERE 1=1 \n";
    private Integer articleId; //主键ID
    private String articleType; //文章类型
    private String articleTypeDesc;//文章类型描述
    private String enArticleTitle; //En文章标题
    private String articleTitle; //文章标题
    private String enArticleDescription; //En文章简述
    private String articleDescription; //文章简述
    private String enabledFlag; //是否首页显示
    private String pictureWeb; //web端缩略图url
    private String picturePc; //pc端缩略图url
    private String articleContentUrl; //文章链接
    private String articleStatus; //文章状态
    private Integer visited; //浏览量
    @JSONField(format = "yyyy-MM-dd  HH:mm:ss")
    private Date articlePublishDate; //文章发布时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date articleInvalidDate; //文章失效时间
    private String enArticleContent; //En文章内容
    private String articleContent; //文章内容
    private String systemCode; //系统编码
    private String systemCodeDesc;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建人
    private Integer versionNum; //版本号
    private Integer lastUpdatedBy; //最后更新人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date topToDate; //置顶有效期
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //最后更新时间
    private Integer lastUpdateLogin;
    private Integer operatorUserId;
    private String userDesc;//发布人
    private String topFlag;//置顶标识 Y 置顶 NEW 最新
    private String newFlag;//最新标识

    public String getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(String newFlag) {
        this.newFlag = newFlag;
    }

    public String getTopFlag() {
        return topFlag;
    }

    public void setTopFlag(String topFlag) {
        this.topFlag = topFlag;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }


    public String getArticleType() {
        return articleType;
    }

    public String getArticleTypeDesc() {
        return articleTypeDesc;
    }

    public void setArticleTypeDesc(String articleTypeDesc) {
        this.articleTypeDesc = articleTypeDesc;
    }

    public void setEnArticleTitle(String enArticleTitle) {
        this.enArticleTitle = enArticleTitle;
    }


    public String getEnArticleTitle() {
        return enArticleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }


    public String getArticleTitle() {
        return articleTitle;
    }

    public void setEnArticleDescription(String enArticleDescription) {
        this.enArticleDescription = enArticleDescription;
    }


    public String getEnArticleDescription() {
        return enArticleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }


    public String getArticleDescription() {
        return articleDescription;
    }

    public void setEnabledFlag(String enabledFlag) {
        this.enabledFlag = enabledFlag;
    }


    public String getEnabledFlag() {
        return enabledFlag;
    }

    public void setPictureWeb(String pictureWeb) {
        this.pictureWeb = pictureWeb;
    }


    public String getPictureWeb() {
        return pictureWeb;
    }

    public void setPicturePc(String picturePc) {
        this.picturePc = picturePc;
    }


    public String getPicturePc() {
        return picturePc;
    }

    public void setArticleContentUrl(String articleContentUrl) {
        this.articleContentUrl = articleContentUrl;
    }


    public String getArticleContentUrl() {
        return articleContentUrl;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemCodeDesc() {
        return systemCodeDesc;
    }

    public void setSystemCodeDesc(String systemCodeDesc) {
        this.systemCodeDesc = systemCodeDesc;
    }

    public void setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
    }


    public String getArticleStatus() {
        return articleStatus;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }


    public Integer getVisited() {
        return visited;
    }

    public void setArticlePublishDate(Date articlePublishDate) {
        this.articlePublishDate = articlePublishDate;
    }


    public Date getArticlePublishDate() {
        return articlePublishDate;
    }

    public void setArticleInvalidDate(Date articleInvalidDate) {
        this.articleInvalidDate = articleInvalidDate;
    }


    public Date getArticleInvalidDate() {
        return articleInvalidDate;
    }

    public void setEnArticleContent(String enArticleContent) {
        this.enArticleContent = enArticleContent;
    }


    public String getEnArticleContent() {
        return enArticleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }


    public String getArticleContent() {
        return articleContent;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }


    public Integer getVersionNum() {
        return versionNum;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Date getTopToDate() {
        return topToDate;
    }

    public void setTopToDate(Date topToDate) {
        this.topToDate = topToDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }


    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }


    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }


    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }
}
