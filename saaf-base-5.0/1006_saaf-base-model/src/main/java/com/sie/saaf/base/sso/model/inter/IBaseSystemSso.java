package com.sie.saaf.base.sso.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.BaseSystemSsoEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * @author huangtao
 * @createTime 2017年12月12日 11:07:42
 */
public interface IBaseSystemSso extends IBaseCommon<BaseSystemSsoEntity_HI> {


    List<BaseSystemSsoEntity_HI> findBaseSystemSsoInfo(JSONObject queryParamJSON);


    BaseSystemSsoEntity_HI saveOrUpdateSystemSsoInfo(JSONObject params, int userId) throws Exception;


    Pagination<BaseSystemSsoEntity_HI> findSystemSsoInfoWithPage(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Integer getOrderNo();

    void delete(Integer id);

    void delete(String[] ids);
}
