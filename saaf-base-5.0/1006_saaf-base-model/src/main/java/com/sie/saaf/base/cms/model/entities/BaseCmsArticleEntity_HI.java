package com.sie.saaf.base.cms.model.entities;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * BaseCmsArticleEntity_HI Entity Object
 * Fri Mar 02 19:33:15 CST 2018  Auto Generate
 */
@Entity
@Table(name = "base_cms_article")
public class BaseCmsArticleEntity_HI {
	private Integer articleId; //主键ID
	private String articleType; //文章类型
	private String topFlag; //置顶标识
	private String articleTitle; //文章标题
	private String enArticleDescription; //En文章简述
	private String articleDescription; //文章简述
	private String enabledFlag; //是否首页显示
	private String pictureWeb; //web端缩略图url
	private String picturePc; //pc端缩略图url
	private String articleContentUrl; //文章链接
	private String articleStatus; //文章状态
	private Integer visited; //浏览量
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date topToDate; //置顶有效期
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date articlePublishDate; //文章发布时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date articleInvalidDate; //文章失效时间
	private String enArticleContent; //En文章内容
	private String articleContent; //文章内容
	private String systemCode; //系统编码
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date creationDate; //创建时间
	private Integer createdBy; //创建人
	private Integer versionNum; //版本号
	private Integer lastUpdatedBy; //最后更新人
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateDate; //最后更新时间
	private Integer lastUpdateLogin;
	private Integer operatorUserId;

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	@Id
	@SequenceGenerator(name = "SEQ_BASE_CMS_ARTICLE", sequenceName = "SEQ_BASE_CMS_ARTICLE", allocationSize = 1)
	@GeneratedValue(generator = "SEQ_BASE_CMS_ARTICLE", strategy = GenerationType.SEQUENCE)
	@Column(name = "article_id", nullable = false, length = 20)
	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}

	@Column(name = "article_type", nullable = true, length = 50)
	public String getArticleType() {
		return articleType;
	}

	public void setTopFlag(String topFlag) {
		this.topFlag = topFlag;
	}

	@Column(name = "top_flag", nullable = true, length = 50)
	public String getTopFlag() {
		return topFlag;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	@Column(name = "article_title", nullable = true, length = 50)
	public String getArticleTitle() {
		return articleTitle;
	}

	public void setEnArticleDescription(String enArticleDescription) {
		this.enArticleDescription = enArticleDescription;
	}

	@Column(name = "en_article_description", nullable = true, length = 150)
	public String getEnArticleDescription() {
		return enArticleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	@Column(name = "article_description", nullable = true, length = 150)
	public String getArticleDescription() {
		return articleDescription;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Column(name = "enabled_flag", nullable = true, length = 2)
	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setPictureWeb(String pictureWeb) {
		this.pictureWeb = pictureWeb;
	}

	@Column(name = "picture_web", nullable = true, length = 150)
	public String getPictureWeb() {
		return pictureWeb;
	}

	public void setPicturePc(String picturePc) {
		this.picturePc = picturePc;
	}

	@Column(name = "picture_pc", nullable = true, length = 150)
	public String getPicturePc() {
		return picturePc;
	}

	public void setArticleContentUrl(String articleContentUrl) {
		this.articleContentUrl = articleContentUrl;
	}

	@Column(name = "article_content_url", nullable = true, length = 150)
	public String getArticleContentUrl() {
		return articleContentUrl;
	}

	public void setArticleStatus(String articleStatus) {
		this.articleStatus = articleStatus;
	}

	@Column(name = "article_status", nullable = true, length = 2)
	public String getArticleStatus() {
		return articleStatus;
	}

	public void setVisited(Integer visited) {
		this.visited = visited;
	}

	@Column(name = "visited", nullable = true, length = 11)
	public Integer getVisited() {
		return visited;
	}

	@Column(name = "top_to_date", nullable = true, length = 0)
	public Date getTopToDate() {
		return topToDate;
	}

	public void setTopToDate(Date topToDate) {
		this.topToDate = topToDate;
	}

	public void setArticlePublishDate(Date articlePublishDate) {
		this.articlePublishDate = articlePublishDate;
	}

	@Column(name = "article_publish_date", nullable = true, length = 0)
	public Date getArticlePublishDate() {
		return articlePublishDate;
	}

	public void setArticleInvalidDate(Date articleInvalidDate) {
		this.articleInvalidDate = articleInvalidDate;
	}

	@Column(name = "article_invalid_date", nullable = true, length = 0)
	public Date getArticleInvalidDate() {
		return articleInvalidDate;
	}

	public void setEnArticleContent(String enArticleContent) {
		this.enArticleContent = enArticleContent;
	}

	@Column(name = "en_article_content", nullable = true, length = 0)
	public String getEnArticleContent() {
		return enArticleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	@Column(name = "article_content", nullable = true, length = 0)
	public String getArticleContent() {
		return articleContent;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "system_code", nullable = true, length = 150)
	public String getSystemCode() {
		return systemCode;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "creation_date", nullable = true, length = 0)
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "created_by", nullable = true, length = 20)
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}

	@Version
	@Column(name = "version_num", nullable = true, length = 11)
	public Integer getVersionNum() {
		return versionNum;
	}

	public void setLastUpdatedBy(Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "last_updated_by", nullable = true, length = 20)
	public Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name = "last_update_date", nullable = true, length = 0)
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateLogin(Integer lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}

	@Column(name = "last_update_login", nullable = true, length = 11)
	public Integer getLastUpdateLogin() {
		return lastUpdateLogin;
	}

	public void setOperatorUserId(Integer operatorUserId) {
		this.operatorUserId = operatorUserId;
	}

	@Transient
	public Integer getOperatorUserId() {
		return operatorUserId;
	}
}
