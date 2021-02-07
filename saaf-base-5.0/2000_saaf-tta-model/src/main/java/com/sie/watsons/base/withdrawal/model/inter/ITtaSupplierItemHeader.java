package com.sie.watsons.base.withdrawal.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;

public interface ITtaSupplierItemHeader extends IBaseCommon<TtaSupplierItemHeaderEntity_HI>{

	JSONObject saveTtaSupplierItemHeaderInfo(JSONObject jsonObject,int userId) throws Exception;
	
	Pagination<TtaSupplierItemHeaderEntity_HI_RO> findTtaSupplierItemHeaderEntityHIPage(JSONObject parameters,Integer pageIndex, Integer pageRows) throws Exception;

	Pagination<TtaSupplierEntity_HI_RO> findTtaSupplierEntity_HI_RO(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

	List<Map<String, Object>> saveSplitDetailListBySplitAndDate(JSONObject parameters) throws Exception;

    TtaSupplierItemHeaderEntity_HI saveSubmitBill(JSONObject parameters, int userId) throws Exception;

    TtaSupplierItemHeaderEntity_HI saveSupplierItemDiscard(JSONObject parameters, int userId) throws Exception;

    List<TtaSupplierItemMidEntity_HI> saveTtaSupplierItemSplitConditionDetail(JSONObject parameters, int userId) throws Exception;


    String updateSplitSupplitInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String monthDay, String splitString, String splitSupplierCode, String splitSupplierName) throws Exception;

    List<Object> insertNewCompanyUsers(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, List<Object> taskList) throws Exception;

    /**
     * 更新选中的数据:指定供应商
     * @param mainLatch
     * @param threadLatch
     * @param rollBack
     * @param resultList
     * @param supplierItemId
     * @param joinSupplierStr
     * @param toMonth
     * @param splitCondition
     * @param itemList
     * @return
     */
    String updateSplitCodeBySelectData(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String toMonth, String splitCondition,List<String> itemList,String finalSplitString,String joinMidId) throws Exception;

    /**
     * 更新purchase的供应商数据
     * @param mainLatch
     * @param threadLatch
     * @param rollBack
     * @param resultList
     * @param joinSupplierStr
     * @param yearItem
     * @param finalSplitString
     * @param splitSupplierCode
     * @param splitSupplierName
     * @return
     */
    String updatePurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList,Integer supplierItemId,String joinSupplierStr, Integer yearItem, String finalSplitString, String splitSupplierCode, String splitSupplierName) throws Exception;

    /**
     * Proposal拆分与合并根据选中的数据去更新tta_purchase_in等表的数据
     * @param mainLatch
     * @param threadLatch
     * @param rollBack
     * @param resultList
     * @param supplierItemId
     * @param joinSupplierStr
     * @param yearItem
     * @param splitCondition
     * @param itemList
     * @return
     */
    String updateSelectPurchaseSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, Integer yearItem, String splitCondition, List<String> itemList,String finalSplitString,String joinMidId) throws Exception;

    //所有OI场景拆分
    String updateOiSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String finalSplitString, String splitSupplierCode, String startDate, String endDate, Integer oiType) throws Exception;

    String updateSelectTotalOiSaleSceneSupplierInfo(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, String joinSupplierStr, String finalSplitString, String joinMidId, String startDate, String endDate, Integer oiType) throws Exception;

    void callCommon(JSONObject querJSONParam,Integer userId) throws Exception;

    JSONObject appointVendorNbrStatus(String createKey);
}