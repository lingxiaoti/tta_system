package com.sie.watsons.base.supplier.model.inter.server;

import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserBrandAclEntity_HI;
import com.sie.watsons.base.supplier.model.entities.PlmSupplierUserItemEntity_HI;
import com.sie.watsons.base.supplier.model.entities.readonly.PlmSupplierUserItemEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserBrandAcl;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserItem;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import jdk.nashorn.internal.ir.IfNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 张韧炼
 * @create 2020-09-25 上午9:50
 **/
@Component("plmSupplierUserItemServer")
public class PlmSupplierUserItemServer extends BaseCommonServer<PlmSupplierUserItemEntity_HI> implements IPlmSupplierUserItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserItemServer.class);
    @Autowired
    private ViewObject<PlmSupplierUserItemEntity_HI> plmSupplierUserItemDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupplierUserItemEntity_HI_RO> plmSupplierUserItemDAO_HI_RO;

    @Autowired
    private IPlmSupplierUserBrandAcl plmSupplierUserBrandAclServer;

    @Autowired
    private ViewObject<PlmSupplierUserBrandAclEntity_HI> plmSupplierUserBrandAclDAO_HI;

    public PlmSupplierUserItemServer() {
        super();
    }

    /**
     * 包含
     */
    public static final String INCLUDE = "I";
    /**
     * 排除
     */
    public static final String EXCLUDE = "E";
    /**
     * 总排除
     */
    public static final String EXCLUDE_ALL = "EX";

    @Override
    public int saveOrUpdateVmiToPlm() throws Exception {
        List<Map<String, Object>> supplierInfos = plmSupplierUserBrandAclServer.findSupplierInfo();
        AtomicInteger successCount = new AtomicInteger();
        if (null != supplierInfos && supplierInfos.size() > 0) {
            StringBuilder queryInCacheData = new StringBuilder(PlmSupplierUserItemEntity_HI_RO.QUERY_IN_CACHE_DATA);
            queryInCacheData.append(" and ppsi.supplier_code = :supplierCode");
            Map<String, Object> queryMap = new HashMap<>(16);
            supplierInfos.forEach(item -> {
                String supplierCode = (String) item.get("supplierCode");
                if (StringUtils.isNotBlank(supplierCode)) {
                    queryMap.put("supplierCode", supplierCode);
                    List<PlmSupplierUserItemEntity_HI_RO> rowList = plmSupplierUserItemDAO_HI_RO.findList(queryInCacheData, queryMap);
                    List<PlmSupplierUserItemEntity_HI> updateList = new ArrayList<>();
                    rowList.forEach(oldRow -> {
                        PlmSupplierUserItemEntity_HI newRow = new PlmSupplierUserItemEntity_HI();
                        BeanUtils.copyProperties(oldRow, newRow);
                        updateList.add(newRow);
                    });
                    if (updateList.size() > 0) {
                        plmSupplierUserItemDAO_HI.saveOrUpdateAll(updateList);
                        successCount.addAndGet(updateList.size());
                    }
                }
            });
        }
        return successCount.get();
    }

    /**
     * 缓存用户商品信息到中间表
     *
     * @param supplierNumbers
     * @param supplierUserId
     * @param supplierUserName
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public int saveUserItems(String supplierNumbers, Integer supplierUserId, String supplierUserName) throws Exception {
        StringBuilder queryInCacheData = new StringBuilder(PlmSupplierUserItemEntity_HI_RO.QUERY_IN_CACHE_DATA);
        queryInCacheData.append("and ppsi.supplier_code in(").append(supplierNumbers).append(")");
        List<PlmSupplierUserItemEntity_HI_RO> rowList = plmSupplierUserItemDAO_HI_RO.findList(queryInCacheData, new HashMap<>(16));
        List<PlmSupplierUserItemEntity_HI> updateList = new ArrayList<>();
        rowList.forEach(oldRow -> {
            oldRow.setSupplierUserId(supplierUserId);
            oldRow.setSupplierUserName(supplierUserName);
            PlmSupplierUserItemEntity_HI newRow = new PlmSupplierUserItemEntity_HI();
            BeanUtils.copyProperties(oldRow, newRow);
            newRow.setOperatorUserId(-1);
            updateList.add(newRow);
        });
        if (updateList.size() > 0) {
            plmSupplierUserItemDAO_HI.saveOrUpdateAll(updateList);
        }
        return updateList.size();
    }

    /**
     * 在中间表根据规则过滤出有用的数据给到权限表
     *
     * @param ruleInfos
     * @param supplierUserId
     * @param ruleId
     * @return
     * @throws Exception
     */
    @Override
    public List<PlmSupplierUserItemEntity_HI_RO> saveFilterData(List<Map<String, Object>> ruleInfos, Integer supplierUserId, BigDecimal ruleId) throws Exception {
        StringBuilder queryString = new StringBuilder(PlmSupplierUserItemEntity_HI_RO.QUERY_FILTER_PREFIX_NEW);
        StringBuilder iAndE = new StringBuilder();
        StringBuilder ex = new StringBuilder();
        Map<String, Object> queryMap = new HashMap<>(16);
        queryMap.put("supplierUserId", supplierUserId);
        // 动态拼接SQL,ruleId=2时,要去规则表里面匹配
        if (BigDecimal.valueOf(Long.valueOf("2")).equals(ruleId)) {
            dynamicStitchingSQL(ruleInfos, queryString, iAndE, ex, queryMap);
        }
        List<PlmSupplierUserItemEntity_HI_RO> resultList = plmSupplierUserItemDAO_HI_RO.findList(queryString, queryMap);
        // 补偿机制,规则表里面类型为I的,有中文或英文的,如果上面的结果里面没有也要补偿进去
        saveCompensationMechanism(ruleInfos, supplierUserId, resultList);
        return resultList;
    }

    /**
     * 补偿机制
     *
     * @param ruleInfos
     * @param supplierUserId
     * @param resultList
     */
    private void saveCompensationMechanism(List<Map<String, Object>> ruleInfos, Integer supplierUserId, List<PlmSupplierUserItemEntity_HI_RO> resultList) {
        String supplierUserName = "";
        if (CollectionUtils.isNotEmpty(resultList)) {
            supplierUserName = resultList.get(0).getSupplierUserName();
        }
        Map<String, String> checkMap = new HashMap<>(16);
        resultList.forEach(row -> checkMap.put(row.getBrandnameEn(), row.getBrandnameCn()));
        String finalSupplierUserName = supplierUserName;
        ruleInfos.forEach(rule -> {
            String type = (String) rule.get("type");
            String brandEn = (String) rule.get("brandEn");
            String brandCn = (String) rule.get("brandCn");
            PlmSupplierUserItemEntity_HI_RO newRow = null;
            PlmSupplierUserItemEntity_HI_RO enRow = null;
            PlmSupplierUserItemEntity_HI_RO cnRow = null;
            if (StringUtils.isNotBlank(type) && INCLUDE.equals(type)) {
                // 按英文名补偿
                if (StringUtils.isNotBlank(brandEn) && !checkMap.containsValue(brandEn)) {
                    StringBuffer queryEn = new StringBuffer(PlmSupplierUserItemEntity_HI_RO.QUERY_BRAND_EN);
                    Map<String, Object> enMap = new HashMap<>(16);
                    enMap.put("plmBrandEn", brandEn);
                    List<PlmSupplierUserItemEntity_HI_RO> enList = plmSupplierUserItemDAO_HI_RO.findList(queryEn, enMap);
                    if (CollectionUtils.isNotEmpty(enList)) {
                        enRow = enList.get(0);
                    }
                }
                // 按中文名补偿
                if (StringUtils.isNotBlank(brandCn) && !checkMap.containsKey(brandCn)) {
                    StringBuffer queryCn = new StringBuffer(PlmSupplierUserItemEntity_HI_RO.QUERY_BRAND_CN);
                    Map<String, Object> cnMap = new HashMap<>(16);
                    cnMap.put("plmBrandCn", brandCn);
                    List<PlmSupplierUserItemEntity_HI_RO> cnList = plmSupplierUserItemDAO_HI_RO.findList(queryCn, cnMap);
                    if (CollectionUtils.isNotEmpty(cnList)) {
                        cnRow = cnList.get(0);
                    }
                }
            }
            if (null != enRow) {
                newRow = new PlmSupplierUserItemEntity_HI_RO();
                newRow.setBrandnameEn(enRow.getBrandnameEn());
                newRow.setBrandEnUdaId(enRow.getBrandEnUdaId());
                newRow.setBrandEnUdaValue(enRow.getBrandEnUdaValue());
            }
            if (null != cnRow) {
                if (null == newRow) {
                    newRow = new PlmSupplierUserItemEntity_HI_RO();
                }
                newRow.setBrandnameCn(cnRow.getBrandnameCn());
                newRow.setBrandCnUdaId(cnRow.getBrandCnUdaId());
                newRow.setBrandCnUdaValue(cnRow.getBrandCnUdaValue());
            }
            if (null != newRow) {
                newRow.setSupplierUserId(supplierUserId);
                newRow.setSupplierUserName(finalSupplierUserName);
                resultList.add(newRow);
            }
        });
    }

    /**
     * 动态拼接SQL
     *
     * @param ruleInfos
     * @param queryString
     * @param iAndE
     * @param ex
     * @param queryMap
     */
    private void dynamicStitchingSQL(List<Map<String, Object>> ruleInfos, StringBuilder queryString, StringBuilder iAndE, StringBuilder ex, Map<String, Object> queryMap) {
        // 1.用来存放规则组,以组为单位进行循环,方便拼接SQL
        List<BigDecimal> seqNos = new ArrayList<>();
        ruleInfos.forEach(rule -> {
            BigDecimal newSeqNo = (BigDecimal) rule.get("seqNo");
            if (null != newSeqNo && !seqNos.contains(newSeqNo)) {
                seqNos.add(newSeqNo);
            }
        });

        // 用来拼接参数命名
        AtomicInteger argsNo = new AtomicInteger(1);
        // 2.循环规则组,拼接SQL
        seqNos.forEach(seqNo -> {
            // 子SQL,只拼接
            StringBuilder subSql = new StringBuilder();
            ruleInfos.forEach(rule -> {
                // 临时SQL
                StringBuffer snapSql = new StringBuffer();
                BigDecimal newSeqNo = (BigDecimal) rule.get("seqNo");
                // 类型,I,E,EX三种
                String type = (String) rule.get("type");
                String itemType = (String) rule.get("itemType");
                String brandCn = (String) rule.get("brandCn");
                String brandEn = (String) rule.get("brandEn");
                String dept = (String) rule.get("dept");
                String exClass = (String) rule.get("exClass");
                String subclass = (String) rule.get("subclass");
                String item = (String) rule.get("item");
                // 只拼接同一组的数据SQL
                if (seqNo.equals(newSeqNo)) {
                    if (StringUtils.isNotBlank(itemType)) {
                        String args = "itemType" + argsNo.get();
                        snapSql.append("\n and t2.PRODUCT_TYPE = :").append(args);
                        queryMap.put(args, itemType);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(brandCn)) {
                        String args = "brandCn" + argsNo.get();
                        snapSql.append("\n and t2.BRANDNAME_CN = :").append(args);
                        queryMap.put(args, brandCn);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(brandEn)) {
                        String args = "brandEn" + argsNo.get();
                        snapSql.append("\n and t2.BRANDNAME_EN = :").append(args);
                        queryMap.put(args, brandEn);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(dept)) {
                        String args = "dept" + argsNo.get();
                        snapSql.append("\n and t2.department = :").append(args);
                        queryMap.put(args, dept);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(exClass)) {
                        String args = "exClass" + argsNo.get();
                        snapSql.append("\n and t2.CLASSES = :").append(args);
                        queryMap.put(args, exClass);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(subclass)) {
                        String args = "subclass" + argsNo.get();
                        snapSql.append("\n and t2.SUB_CLASS = :").append(args);
                        queryMap.put(args, subclass);
                        argsNo.addAndGet(1);
                    }

                    if (StringUtils.isNotBlank(item)) {
                        String args = "item" + argsNo.get();
                        snapSql.append("\n and t2.RMS_CODE = :").append(args);
                        queryMap.put(args, item);
                        argsNo.addAndGet(1);
                    }
                    String queryFilterSplicing = PlmSupplierUserItemEntity_HI_RO.QUERY_FILTER_SPLICING.replace("--#--", snapSql.toString());

                    if (StringUtils.isNotBlank(type) && INCLUDE.equals(type)) {
                        if (StringUtils.isBlank(subSql.toString())) {
                            subSql.append("( EXISTS").append(queryFilterSplicing);
                        } else {
                            subSql.append(" AND EXISTS").append(queryFilterSplicing);
                        }
                    }
                    if (StringUtils.isNotBlank(type) && EXCLUDE.equals(type)) {
                        if (StringUtils.isBlank(subSql.toString())) {
                            subSql.append("( NOT EXISTS").append(queryFilterSplicing);
                        } else {
                            subSql.append(" AND NOT EXISTS").append(queryFilterSplicing);
                        }
                    }
                    if (StringUtils.isNotBlank(type) && EXCLUDE_ALL.equals(type)) {
                        ex.append("\n AND NOT EXISTS").append(queryFilterSplicing);
                    }
                }
            });
            if (StringUtils.isNotBlank(subSql.toString())) {
                subSql.append(")");
            }
            if (StringUtils.isBlank(iAndE.toString())) {
                iAndE.append("\n AND (").append(subSql);
            } else {
                iAndE.append("\n OR ").append(subSql);
            }
        });
        // 拼接I,E过滤
        if (StringUtils.isNotBlank(iAndE.toString())) {
            iAndE.append(")");
            queryString.append(iAndE);
        }
        // 拼接EX过滤
        if (StringUtils.isNotBlank(ex.toString())) {
            queryString.append(ex);
        }
    }

    /**
     * 清空表
     *
     * @throws Exception
     */
    @Override
    public void deleteClearData() throws Exception {
        List<PlmSupplierUserItemEntity_HI> allList = plmSupplierUserItemDAO_HI.findList("from  PlmSupplierUserItemEntity_HI where 1 = 1");
        plmSupplierUserItemDAO_HI.delete(allList);
        LOGGER.info("删除了{}条数据", allList.size());
    }

    /**
     * 通过供应商ID删除
     *
     * @param supplierUserId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public int deleteBySupplierUserId(Integer supplierUserId) throws Exception {
        List<PlmSupplierUserItemEntity_HI> deleteList = plmSupplierUserItemDAO_HI.findByProperty("supplierUserId", supplierUserId);
        if (CollectionUtils.isNotEmpty(deleteList)) {
            plmSupplierUserItemDAO_HI.delete(deleteList);
            LOGGER.info("删除了{}条商家权限中间表数据", deleteList.size());
            return deleteList.size();
        }
        LOGGER.info("删除了{}条商家权限中间表数据", 0);
        return 0;
    }

    /**
     * 通过供应商ID删除商家数据权限列表
     * 这个方法写在中间表的类里面是为了在,权限表类里面调用时,能支持独立的事务
     *
     * @param supplierUserId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public int deletePlmSupplierUserBrandAclBySupplierUserId(Integer supplierUserId) throws Exception {
        List<PlmSupplierUserBrandAclEntity_HI> deleteList = plmSupplierUserBrandAclDAO_HI.findByProperty("supplierUserId", supplierUserId);
        if (CollectionUtils.isNotEmpty(deleteList)) {
            plmSupplierUserBrandAclDAO_HI.delete(deleteList);
            LOGGER.info("删除了{}条商家权限表数据", deleteList.size());
            return deleteList.size();
        }
        LOGGER.info("删除了{}条商家权限表数据", 0);
        return 0;
    }
}
