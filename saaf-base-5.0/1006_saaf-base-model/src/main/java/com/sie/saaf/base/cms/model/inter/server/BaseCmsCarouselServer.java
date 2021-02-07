package com.sie.saaf.base.cms.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsCarouselEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsCarouselEntity_HI_RO;
import com.sie.saaf.base.cms.model.inter.IBaseCmsCarousel;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseCmsCarouselServer")
public class BaseCmsCarouselServer extends BaseCommonServer<BaseCmsCarouselEntity_HI> implements IBaseCmsCarousel {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCmsCarouselServer.class);
    @Autowired
    private ViewObject<BaseCmsCarouselEntity_HI> baseCmsCarouselDAO_HI;
    @Autowired
    private BaseViewObject<BaseCmsCarouselEntity_HI_RO> baseCmsCarouselDAO_HI_RO;

    public BaseCmsCarouselServer() {
        super();
    }

    @Override
    public List<BaseCmsCarouselEntity_HI> findList(JSONObject queryParamJSON) {
        String varOrgId = queryParamJSON.getString("varOrgId")+",0";//BU过滤==当前域名对应OU+0全局
        StringBuffer hql = new StringBuffer();
        hql.append(" FROM BaseCmsCarouselEntity_HI WHERE 1=1 ");
        hql.append(" and orgId in (" + varOrgId + ")");
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        changeQuerySql(queryParamJSON, paramsMap, hql, true);
        SaafToolUtils.changeQuerySort(queryParamJSON, hql, "order_sequence asc", false);
        List<BaseCmsCarouselEntity_HI> findList = baseCmsCarouselDAO_HI.findList(hql, paramsMap);
        return findList;
    }

    @Override
    public Pagination<BaseCmsCarouselEntity_HI_RO> findAllCarousel(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseCmsCarouselEntity_HI_RO.QUERY_CAROUSEL_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "carousel.display_flag", "displayFlag", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "carousel.carousel_type", "carouselType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "carousel.carousel_position", "carouselPosition", sql, paramsMap, "=");
        if (!"Y".equals(queryParamJSON.getString("varIsadmin")) && StringUtils.isNotBlank(queryParamJSON.getString("orgId"))) {
            sql.append(" and carousel.org_id in (" + queryParamJSON.getString("orgId") + ",0 ) \n");
        }
        sql.append(" and carousel.carousel_type !='APP_LOADING' ");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "carousel.creation_date", false);
        Pagination<BaseCmsCarouselEntity_HI_RO> findList = baseCmsCarouselDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql) ,paramsMap, pageIndex, pageRows);
        return findList;
    }


    @Override
    public Pagination<BaseCmsCarouselEntity_HI_RO> findAPPCarousel(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(BaseCmsCarouselEntity_HI_RO.QUERY_CAROUSEL_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "carousel.display_flag", "displayFlag", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "carousel.carousel_type", "carouselType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "carousel.carousel_position", "carouselPosition", sql, paramsMap, "=");
        sql.append(" and carousel.carousel_type ='APP_LOADING' ");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "carousel.creation_date", false);
        Pagination<BaseCmsCarouselEntity_HI_RO> findList = baseCmsCarouselDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql),paramsMap, pageIndex, pageRows);
        return findList;
    }
}
