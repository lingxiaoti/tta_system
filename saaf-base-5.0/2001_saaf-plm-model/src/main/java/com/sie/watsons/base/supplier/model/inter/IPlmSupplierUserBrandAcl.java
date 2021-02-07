package com.sie.watsons.base.supplier.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserBrandAclEntity_HI;

import java.util.List;
import java.util.Map;

public interface IPlmSupplierUserBrandAcl extends IBaseCommon<PlmSupplierUserBrandAclEntity_HI> {
    /**
     * 生成或修改供应商品牌数据
     *
     * @param parameter
     * @return
     */
    Integer saveOrUpdateSupplierUserBrand(JSONObject parameter) throws Exception;

    /**
     * 获取供应商信息
     *
     * @return
     */
    List<Map<String, Object>> findSupplierInfo() throws Exception;

    /**
     * 获取供应商信息,供应商编号已经拼接好了
     *
     * @return
     */
    List<Map<String, Object>> findSupplierInfos() throws Exception;

    /**
     * 通过AccountId查询规则信息
     *
     * @param accountId
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> ruleInformationByAccountId(Integer accountId) throws Exception;


    /**
     * 通过PLM_PRODUCT_HEAD和VMI_DATA_PRIVILEGE_LINE查询对应供应商和商品之间的关系
     *
     * @param accountId
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findProductHeadByPrivilegeLine(Integer accountId,
                                                             String startDate, String endDate) throws Exception;
}
