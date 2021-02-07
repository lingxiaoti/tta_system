package com.sie.watsons.base.withdrawal.model.inter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemRelSupplierEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.RollBack;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaSupplierItemRelSupplier extends IBaseCommon<TtaSupplierItemRelSupplierEntity_HI>{

    Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findSupplierItemRelSupplierList(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    Pagination<TtaRelSupplierEntity_HI_RO> selectSupplierItemRelSupplierList(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    List<TtaSupplierItemRelSupplierEntity_HI> ttaSupplierItemRelSupplierSave(JSONObject queryParamJSON) throws Exception;

    TtaSupplierItemRelSupplierEntity_HI ttaSupplierItemRelSupplierDelete(Integer id) throws Exception;

    Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findSupplierItemRelSupplierListBySupplierItemId(Integer supplierItemId) throws Exception;

    public List<Map<String, Object>> queryNamedSQLForList(String sql, Map<String, Object> queryMap);

    String updateRollbackSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String monthDay, String finalSplitString,List<String> supplierList) throws Exception;

    String updateRollbackPurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, Integer yearItem, String finalSplitString,List<String> supplierList) throws Exception;

    /**
     * OI账单场景一
     * @param mainLatch
     * @param threadLatch
     * @param rollBack
     * @param resultList
     * @param supplierItemId
     * @param joinSupplierStr
     * @param startDate
     * @param endDate
     * @param finalSplitString
     * @param supplierList
     * @return
     * @throws Exception
     */
    String updateRollbackUpdateTotalOiSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String startDate, String endDate, String finalSplitString, List<String> supplierList,Integer oiType) throws Exception;

}
