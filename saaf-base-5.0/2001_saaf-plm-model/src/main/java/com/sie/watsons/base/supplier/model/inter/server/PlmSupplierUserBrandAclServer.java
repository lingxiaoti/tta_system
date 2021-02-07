package com.sie.watsons.base.supplier.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.product.model.entities.readonly.PlmProductHeadEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserBrandAclEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserBrandAclEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserItemEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserBrandAcl;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserItem;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component("plmSupplierUserBrandAclServer")
public class PlmSupplierUserBrandAclServer extends BaseCommonServer<PlmSupplierUserBrandAclEntity_HI> implements IPlmSupplierUserBrandAcl {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserBrandAclServer.class);

    @Autowired
    private ViewObject<PlmSupplierUserBrandAclEntity_HI> plmSupplierUserBrandAclDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupplierUserBrandAclEntity_HI_RO> plmSupplierUserBrandAclDAO_HI_RO;
    @Autowired
    private IPlmSupplierUserItem plmSupplierUserItemServer;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    public PlmSupplierUserBrandAclServer() {
        super();
    }

    /**
     * vmi 库
     */
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate vmiJdbcTemplate;

    /**
     * plm 库
     */
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate plmJdbcTemplate;

    /**
     * 生成或修改供应商品牌数据
     *
     * @param parameter
     * @return
     */
    @Override
    public Integer saveOrUpdateSupplierUserBrand(JSONObject parameter) throws Exception {
        List<Map<String, Object>> supplierInfos = findSupplierInfos();
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger aclSuccessCount = new AtomicInteger();

        // 1 循环插入中间表
        supplierInfos.forEach(account ->
                // 多线程操作
                taskExecutor.execute(() -> {
                    int i = 0;
                    try {
                        String supplierNumbers = (String) account.get("supplierNumbers");
                        Integer supplierUserId = Integer.valueOf((String) account.get("supplierUserId"));
                        String supplierUserName = (String) account.get("supplierUserName");
                        // 删除中间表
                        plmSupplierUserItemServer.deleteBySupplierUserId(supplierUserId);
                        // 插入中间表
                        i = plmSupplierUserItemServer.saveUserItems(supplierNumbers, supplierUserId, supplierUserName);
                        // 插入供应商和数据权限表
                        saveSupplierUserBrandAclBySupplierUserId(aclSuccessCount, account, supplierUserId);
                    } catch (Exception e) {
                        LOGGER.error("插入中间表失败", e);
                    }
                    successCount.addAndGet(i);
                })
        );
        LOGGER.info("成功插入中间表{}条数据", successCount.get());

        /// 单线程的逻辑还要了
        /*// 2 循环处理中间表插入供应商和数据权限表,
        supplierInfos.forEach(account -> {
            // 2.1 通过ACOUNT_ID查询规则信息
            Integer supplierUserId = Integer.valueOf((String) account.get("supplierUserId"));
            saveSupplierUserBrandAclBySupplierUserId(aclSuccessCount, account, supplierUserId);
            saveSupplierUserBrandAclBySupplierUserId(aclSuccessCount, account, supplierUserId);
        });*/
        return aclSuccessCount.get();
    }

    /**
     * 插入供应商和数据权限表
     *
     * @param aclSuccessCount
     * @param account
     * @param supplierUserId
     */
    private void saveSupplierUserBrandAclBySupplierUserId(AtomicInteger aclSuccessCount, Map<String, Object> account, Integer supplierUserId) {
        List<Map<String, Object>> ruleInfos = ruleInformationByAccountId(supplierUserId);
        // 2.2 去临时表里面筛选出可以插入权限表的数据
        BigDecimal ruleId = (BigDecimal) account.get("ruleId");
        List<PlmSupplierUserItemEntity_HI_RO> ruleList = null;
        if (ruleInfos.size() > 0) {
            try {
                ruleList = plmSupplierUserItemServer.saveFilterData(ruleInfos, supplierUserId, ruleId);
            } catch (Exception e) {
                LOGGER.error("去临时表里面筛选失败", e);
            }
        }
        // 2.3.1删除权限表
        try {
            plmSupplierUserItemServer.deletePlmSupplierUserBrandAclBySupplierUserId(supplierUserId);
        } catch (Exception e) {
            LOGGER.error("删除权限表失败", e);
        }
        // 2.3.2 插入权限表
        List<PlmSupplierUserBrandAclEntity_HI> insertList = new ArrayList<>();
        ruleList.forEach(oldRow -> {
            PlmSupplierUserBrandAclEntity_HI newRow = new PlmSupplierUserBrandAclEntity_HI();
            BeanUtils.copyProperties(oldRow, newRow);
            newRow.setOperatorUserId(-1);
            insertList.add(newRow);
        });
        if (insertList.size() > 0) {
            plmSupplierUserBrandAclDAO_HI.saveOrUpdateAll(insertList);
            aclSuccessCount.addAndGet(insertList.size());
        }
    }

    /**
     * 获取供应商信息
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findSupplierInfo() {
        List<Map<String, Object>> result = vmiJdbcTemplate.queryForList(PlmSupplierUserBrandAclEntity_HI_RO.QUERY_SUPPLIER_INFO);
        LOGGER.info(JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 获取供应商信息,供应商编号已经拼接好了
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findSupplierInfos() {
        return vmiJdbcTemplate.queryForList(PlmSupplierUserBrandAclEntity_HI_RO.QUERY_SUPPLIER_INFOS);
    }

    /**
     * 通过AccountId查询规则信息
     *
     * @param accountId
     * @return
     */
    @Override
    public List<Map<String, Object>> ruleInformationByAccountId(Integer accountId) {
        StringBuffer queryString = new StringBuffer(PlmSupplierUserBrandAclEntity_HI_RO.QUERY_RULE_INFORMATION_BY_ACCOUNT_ID);
        queryString.append(" WHERE VDPIL.ACCOUNT_ID= ").append(accountId).append("\n order by seq_no");
        return vmiJdbcTemplate.queryForList(queryString.toString());
    }

    @Override
    public List<Map<String, Object>> findProductHeadByPrivilegeLine(Integer accountId, String startDate, String endDate) {
        StringBuffer queryString = new StringBuffer(PlmProductHeadEntity_HI_RO.QUERYP_RODUCT_HEAD_RMS);
        if(StringUtils.isNotBlank(startDate)) {
            queryString.append(" AND vd.LAST_UPDATE_DATE>=to_date('").append(startDate).append("','yyyy-MM-dd') ");
        }
        if(StringUtils.isNotBlank(endDate)) {
            queryString.append(" AND vd.LAST_UPDATE_DATE<to_date('").append(endDate).append("','yyyy-MM-dd') ");
        }
        return plmJdbcTemplate.queryForList(queryString.toString());
    }



}
