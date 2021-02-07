package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsOrderDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsOrderDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaFreeGoodsOrderDetail extends IBaseCommon<TtaFreeGoodsOrderDetailEntity_HI>{

    Pagination<TtaFreeGoodsOrderDetailEntity_HI_RO> findInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows, UserSessionBean userSessionBean) throws Exception;

    TtaFreeGoodsOrderDetailEntity_HI saveOrUpdate(JSONObject jsonObject, int userId) throws Exception;

    List<TtaFreeGoodsOrderDetailEntity_HI> saveOrUpdateAll(JSONObject jsonObject, int userId) throws Exception;

    void batchJoinCount(JSONObject jsonObject, int userId) throws Exception;

    void batchJoinFeeYear(JSONObject jsonObject, int userId) throws Exception;

    void batchDelete(JSONObject jsonObject, int userId);
}
