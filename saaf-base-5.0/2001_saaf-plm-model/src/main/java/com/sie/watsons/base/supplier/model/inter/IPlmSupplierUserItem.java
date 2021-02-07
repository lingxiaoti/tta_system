package com.sie.watsons.base.supplier.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserItemEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserItemEntity_HI_RO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 张韧炼
 * @create 2020-09-25 上午9:49
 **/
public interface IPlmSupplierUserItem extends IBaseCommon<PlmSupplierUserItemEntity_HI> {

    /**
     * 缓存RMI数据到PLM中间表
     *
     * @return
     * @throws Exception
     */
    int saveOrUpdateVmiToPlm() throws Exception;


    /**
     * 缓存用户商品信息到中间表
     *
     * @param supplierNumbers
     * @param supplierUserId
     * @param supplierUserName
     * @return
     * @throws Exception
     */
    int saveUserItems(String supplierNumbers, Integer supplierUserId, String supplierUserName) throws Exception;

    /**
     * 在中间表根据规则过滤出有用的数据给到权限表
     *
     * @param ruleInfos
     * @param supplierUserId
     * @param ruleId
     * @return
     * @throws Exception
     */
    List<PlmSupplierUserItemEntity_HI_RO> saveFilterData(List<Map<String, Object>> ruleInfos, Integer supplierUserId, BigDecimal ruleId) throws Exception;

    /**
     * 清空表
     *
     * @throws Exception
     */
    void deleteClearData() throws Exception;

    /**
     * 通过供应商ID删除
     *
     * @return 成功数量
     * @throws Exception 异常
     */
    int deleteBySupplierUserId(Integer supplierUserId) throws Exception;

    /**
     * 通过供应商ID删除商家数据权限列表
     * 这个方法写在中间表的类里面是为了在,权限表类里面调用时,能支持独立的事务
     *
     * @param supplierUserId 供应商ID
     * @return 成功数量
     * @throws Exception 删除异常
     */
    int deletePlmSupplierUserBrandAclBySupplierUserId(Integer supplierUserId) throws Exception;
}
