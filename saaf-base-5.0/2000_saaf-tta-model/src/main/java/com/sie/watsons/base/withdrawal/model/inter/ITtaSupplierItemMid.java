package com.sie.watsons.base.withdrawal.model.inter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.yhg.hibernate.core.paging.Pagination;

public interface ITtaSupplierItemMid extends IBaseCommon<TtaSupplierItemMidEntity_HI>{

    public static final String GROUP = "Group";
    public static final String GROUP_DEPT = "Group+Dept";
    public static final String GROUP_DEPT_BRAND = "Group+Dept+Brand";
    public static final String GROUP_DEPT_BRAND_ITEM = "Group+Dept+Brand+Item";

    String PURCHASE_GROUP_STRING = " and tsmdt.group_code =tpur.group_code \n";
    String PURCHASE_GROUP_DEPT_STRING = " and tsmdt.group_code =tpur.group_code and  tsmdt.dept_code =tpur.dept_code \n";
    String PURCHASE_GROUP__DEPT_BRAND_STRING = " and tsmdt.group_code =tpur.group_code and  tsmdt.dept_code =tpur.dept_code and  tsmdt.brand_cn =tpur.brand_cn and tsmdt.brand_en = tpur.brand_en \n" ;
    String PURCHASE_GROUP__DEPT_BRAND_ITEM_STRING = " and tsmdt.group_code =tpur.group_code and  tsmdt.dept_code =tpur.dept_code and  tsmdt.brand_cn =tpur.brand_cn and tsmdt.brand_en = tpur.brand_en \n" +
            " and tsmdt.item_nbr = tpur.item_nbr ";

    String PURCHASETIT_GROUP_STRING = " tit.group_code ";
    String PURCHASETIT_GROUP_DEPT_STRING = " tit.group_code,tit.dept_code ";
    String PURCHASETIT_GROUP_DEPT_BRAND_STRING = " tit.group_code,tit.dept_code,tit.brand_cn ,tit.brand_en ";
    String PURCHASETIT_GROUP_DEPT_BRAND_ITEM_STRING = " tit.group_code,tit.dept_code,tit.brand_cn,tit.brand_en,tit.item_nbr ";

    String OI_PO_GROUP_STR = "ts.group_code\n";
    String OI_PO_GROUP_DEPT_STR = "ts.group_code,\n" +
            "                    ts.dept_code\n";
    String OI_PO_GROUP_DEPT_BRAND_STR = "ts.group_code,\n" +
            "                    ts.dept_code,\n" +
            "                    ts.brand_cn\n" ;
    String OI_PO_GROUP_DEPT_BRAND_ITEM_STR = "ts.group_code,\n" +
            "                    ts.dept_code,\n" +
            "                    ts.brand_cn,\n" +
            "                    ts.item_nbr";

    String OI_POR_REMS_GROUP = " and tsmdt.group_code =rms.group_code \n";
    String OI_POR_REMS_GROUP_DEPT = " and tsmdt.group_code =rms.group_code and  tsmdt.dept_code =rms.dept_code\n";
    String OI_POR_REMS_GROUP_DEPT_BRAND = " and tsmdt.group_code =rms.group_code and  tsmdt.dept_code =rms.dept_code and  tsmdt.brand_cn = rms.brand_cn\n";
    String OI_POR_REMS_GROUP_DEPT_BRAND_ITEM = " and tsmdt.group_code =rms.group_code and  tsmdt.dept_code =rms.dept_code and  tsmdt.brand_cn = rms.brand_cn\n" +
            "           and tsmdt.item_nbr = rms.item_nbr ";


    List<TtaSupplierItemMidEntity_HI>  savePurchaseInfoList(List<Map<String,Object>> splitDetailListBySplitAndDate, TtaSupplierItemHeaderEntity_HI instance, int userId) throws Exception;
    void saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

    Pagination<TtaSupplierItemMidEntity_HI> findSplitDetailList(JSONObject parameters, Integer pageIndex, Integer pageRows) throws Exception;

    Set<String> findSupplierItemMidList(Integer supplierItemId) throws Exception;

    /**
     * 功能描述： IO 拆分
     * @author xiaoga
     * @date 2019/8/27
     * @param
     * @return
     */
    public List<Map<String, Object>> saveSpliteIo(JSONObject paramJson) throws  Exception;

    void saveSplitConditionDetail(JSONObject jsonObject, int userId) throws Exception;

    JSONObject checkSplitConditionDetail(JSONObject jsonObject, int userId);

    int saveOrUpdateWithdrawalSplitDataImport(JSONObject jsonObject, int userId,List<TtaSupplierItemMidEntity_HI> midEntityHiList) throws Exception;

    List<TtaSupplierItemMidEntity_HI> findSplitDetailDataBySupplierItemHId(JSONObject jsonObject);

    void saveVirtualProposalInfo(JSONArray splitDataArray, TtaProposalHeaderEntity_HI_RO hiRo, JSONObject brandData, String splitCondition, int userId, Integer supplierItemId) throws Exception;

    TtaBrandplnHEntity_HI saveVirtualBrandpln(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, Object create_brand_data, JSONObject brandData, int userId) throws Exception;

    void saveVirtualTtaTerm(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI newProposalDomain, TtaProposalHeaderEntity_HI_RO oldProposalDomain, TtaBrandplnHEntity_HI newBrandplnHEntity, Integer supplierItemId, int userId) throws Exception;

    void saveVirtualContract(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, TtaProposalHeaderEntity_HI_RO hiRo, int userId) throws Exception;

    TtaBrandplnHEntity_HI saveBrandplnByOtherDimension(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, Object class_supplier_data, String splitCondition, JSONObject brandData, int userId) throws Exception;

    void saveDealWithBeforeSplitSupplier(JSONArray splitDataArray,JSONObject brandData,String splitCondition ,TtaProposalHeaderEntity_HI_RO beforeProposal,Integer supplierItemId, int userId) throws Exception;

    TtaProposalHeaderEntity_HI saveVirtualProposal(String splitSupplierCode, String splitSupplierName, TtaProposalHeaderEntity_HI_RO proposalHeader, int userId) throws Exception;

    void saveAndCheckBrandSplit(JSONObject paramsJSON, int userId) throws Exception;

    void saveDealRenewLastYearVendor(JSONArray jsonArray, TtaProposalHeaderEntity_HI_RO hiRo, JSONObject jsonObject, String splitCondition, int userId, Integer supplierItemId) throws Exception;

}
