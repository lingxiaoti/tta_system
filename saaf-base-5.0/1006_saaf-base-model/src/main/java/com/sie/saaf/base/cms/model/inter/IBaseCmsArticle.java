package com.sie.saaf.base.cms.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.cms.model.entities.BaseCmsArticleEntity_HI;
import com.sie.saaf.base.cms.model.entities.readonly.BaseCmsArticleEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseCmsArticle extends IBaseCommon<BaseCmsArticleEntity_HI> {

    Pagination<BaseCmsArticleEntity_HI_RO> findBaseCmsArticles(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    BaseCmsArticleEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer articleId);

    /**
     * portal
     * @description 公司新闻列表*/

    List<BaseCmsArticleEntity_HI_RO> findNews(JSONObject queryParamJSON);
    /**
     * portal
     * @description 通知公告列表*/

    List<BaseCmsArticleEntity_HI_RO> findNotice(JSONObject queryParamJSON);
}
