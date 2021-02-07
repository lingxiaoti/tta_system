package com.sie.saaf.base.cms.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsArticleEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsArticleEntity_HI_RO;
import com.sie.saaf.base.cms.model.inter.IBaseCmsArticle;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component("baseCmsArticleServer")
public class BaseCmsArticleServer extends BaseCommonServer<BaseCmsArticleEntity_HI> implements IBaseCmsArticle {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCmsArticleServer.class);
    @Autowired
    private ViewObject<BaseCmsArticleEntity_HI> baseCmsArticleDAO_HI;
    @Autowired
    private BaseViewObject<BaseCmsArticleEntity_HI_RO> baseCmsArticleDAO_HI_RO;

    public BaseCmsArticleServer() {
        super();
    }


    @Override
    public Pagination<BaseCmsArticleEntity_HI_RO> findBaseCmsArticles(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseCmsArticleEntity_HI_RO.QUERY_ALL_ARTICLE_SQL);
        String isPortal=queryParamJSON.getString("isPortal");
        if ("Y".equals(isPortal)){
            sql.append(" AND article.enabled_flag='Y' ");
            sql.append(" AND article.article_publish_date <sysdate ");
        }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "article.article_type", "articleType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "article.system_code", "systemCode_in", sql, paramsMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "article.system_code", "systemCodeadd", sql, paramsMap, "in");
        SaafToolUtils.parperParam(queryParamJSON, "article.article_title", "articleTitle", sql, paramsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "article.article_publish_date desc", false);
        Pagination<BaseCmsArticleEntity_HI_RO> findList = baseCmsArticleDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public BaseCmsArticleEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        BaseCmsArticleEntity_HI instance = SaafToolUtils.setEntity(BaseCmsArticleEntity_HI.class, paramsJSON, baseCmsArticleDAO_HI, userId);
        if ("N".equals(instance.getTopFlag())){
            instance.setTopToDate(null);
        }
        baseCmsArticleDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public void delete(Integer articleId) {
        if (articleId == null)
            return;
        BaseCmsArticleEntity_HI instance = baseCmsArticleDAO_HI.getById(articleId);
        if (instance == null)
            return;
        instance.setArticleStatus(CommonConstants.ENABLED_FALSE);
        baseCmsArticleDAO_HI.update(instance);
    }

    @Override
    public List<BaseCmsArticleEntity_HI_RO> findNews(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseCmsArticleEntity_HI_RO.QUERY_NEWS);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "notice.systemCode", "systemCode_in", sql, paramsMap, "in");
        sql.append(" ORDER BY topFlag DESC ");
        List<BaseCmsArticleEntity_HI_RO> findList = baseCmsArticleDAO_HI_RO.findList(sql, paramsMap);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-3);
        for (BaseCmsArticleEntity_HI_RO news:findList){
            //是否置顶
            Boolean newFlag=(news.getArticlePublishDate()).before(cal.getTime());
//            Boolean topFlag="Y".equals(news.getTopFlag());
            if (!newFlag){
                news.setNewFlag("Y");
            }
        }
        return findList;
    }

    @Override
    public List<BaseCmsArticleEntity_HI_RO> findNotice(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseCmsArticleEntity_HI_RO.QUERY_NOTICE);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "notice.systemCode", "systemCode_in", sql, paramsMap, "in");
        sql.append(" ORDER BY topFlag DESC ");
        List<BaseCmsArticleEntity_HI_RO> findList = baseCmsArticleDAO_HI_RO.findList(sql, paramsMap);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,-3);
        for (BaseCmsArticleEntity_HI_RO notice:findList){
            Boolean newFlag=(notice.getArticlePublishDate()).before(cal.getTime());
//            Boolean topFlag="Y".equals(notice.getTopFlag());
            if (!newFlag){
                notice.setNewFlag("Y");
            }
        }
        return findList;
    }
}
