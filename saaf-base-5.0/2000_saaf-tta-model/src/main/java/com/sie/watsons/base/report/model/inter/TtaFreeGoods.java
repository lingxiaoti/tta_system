package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/30/030.
 */
public interface TtaFreeGoods extends IBaseCommon<TtaFreeGoodsEntity_HI> {

    Pagination<TtaFreeGoodsEntity_HI_RO> saveFind(JSONObject queryParamJSON, UserSessionBean userSessionBean,Integer pageIndex, Integer pageRows) throws Exception;

    TtaFreeGoodsEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    void delete(Integer osdId);

    List<TtaFreeGoodsEntity_HI_RO> findfFreeGoodList(String batchId, Integer userId) throws Exception;

    JSONObject saveFreeGoodsByPoList(JSONObject jsonObject,UserSessionBean userSessionBean, int userId) throws Exception;

    Pagination<TtaFreeGoodsEntity_HI_RO> findProcessSummaryInfo(JSONObject jsonObject, UserSessionBean sessionBean);

    void saveOrUpdateAll(JSONObject jsonObject, int userId) throws Exception;
}