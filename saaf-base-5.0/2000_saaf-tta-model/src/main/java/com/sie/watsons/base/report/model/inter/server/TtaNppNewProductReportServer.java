package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaNppNewProductReportDAO_HI;
import com.sie.watsons.base.report.model.dao.TtaSystemCurrentLineDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaCwCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaSystemCurrentLineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.*;
import com.sie.watsons.base.report.utils.JdbcUtils;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaNppNewProductReportEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaNppNewProductReport;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

@Component("ttaNppNewProductReportServer")
public class TtaNppNewProductReportServer extends BaseCommonServer<TtaNppNewProductReportEntity_HI> implements ITtaNppNewProductReport {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaNppNewProductReportServer.class);

    @Autowired
    private ViewObject<TtaNppNewProductReportEntity_HI> ttaNppNewProductReportDAO_HI;
    @Autowired
    private TtaNppNewProductReportDAO_HI ttaNppNewProductReportDAOHi;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;

    @Autowired
    private BaseViewObject<TtaNppNewProductReportEntity_HI_RO> ttaNppNewProductReportDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaSystemCurrentLineEntity_HI_RO> ttaSystemCurrentLineDAO_HI_RO;

    @Autowired
    private TtaSystemCurrentLineDAO_HI ttaSystemCurrentLineDAOHi;

    @Autowired
    private ViewObject<TtaSystemCurrentLineEntity_HI> ttaSystemCurrentLineDAO_HI;

    @Autowired
    private JedisCluster jedisCluster;

    public TtaNppNewProductReportServer() {
        super();
    }

    @Override
    public JSONObject saveOrUpdateByNppQuery(JSONObject paramsJSON, UserSessionBean userSessionBean) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Integer userId = userSessionBean.getUserId();
        String batchCode = codeService.getTtaNppReport("NPP");
        String yearMonth = paramsJSON.getString("yearMonth");
        yearMonth = WDatesUtils.tranferDateFormat(yearMonth);
        Assert.notNull(yearMonth, "必须选择年月");
        Integer month = Integer.valueOf(yearMonth);
        String year = yearMonth.substring(0, 4);
        //校验数据
        this.checkData(month);
        try {
            LOGGER.info("******************第一步:查询导入的历史数据和NPP已存在的数据*************************");
            List<TtaSystemCurrentLineEntity_HI_RO> allSystemCurrentList = ttaSystemCurrentLineDAO_HI_RO.findList(TtaSystemCurrentLineEntity_HI_RO.QUERY, new HashMap<>());
            List<TtaNppNewProductReportEntity_HI_RO> nppAllData = ttaNppNewProductReportDAO_HI_RO.findList(TtaNppNewProductReportEntity_HI_RO.NPP_ALLDATA_BY_QUERE, new HashMap<>());
            //当月导入数
            List<TtaSystemCurrentLineEntity_HI> currentImportList = ttaSystemCurrentLineDAOHi.findByProperty("monthN", month);

            LOGGER.info("******************第二步:找出条件为有首次上图时间并且是生效状态的首次上图的数据******");
            //查询已经存在的导入的数据,条件为:1.有首次上图时间 2.生效状态
            StringBuffer sql = new StringBuffer(TtaSystemCurrentLineEntity_HI_RO.QUERY);
            sql.append(" and nvl(tsc.frist_rec_pog_time,-1) != -1 and tsc.status = 'Y'");
            Map<String, Object> paramMap = new HashMap<>();
            List<TtaSystemCurrentLineEntity_HI_RO> daoHiRoList = ttaSystemCurrentLineDAO_HI_RO.findList(sql, paramMap);
            //Map<String,Map<String,Object>> countStoreByItem = new HashMap<>();//存放需要计算的上图数量
            List<Map<String, Object>> updateSCEntityList = new ArrayList<>();
            List<Map<String, Object>> noExistsList = new ArrayList<>();
            List<Map<String, Object>> maxStoreZeroList = new ArrayList<>();
            List<Map<String, Object>> updateMaxOrCographStoreList = new ArrayList<>();
            List<Map<String, Object>> efficacyItemList = new ArrayList<>();

            LOGGER.info("******************第三步:更新最大店铺数量****************************************");
            //如不存在历史数据,更新首次上图时间,状态,最大店铺数量
            if (CollectionUtils.isNotEmpty(daoHiRoList)) {
                for (TtaSystemCurrentLineEntity_HI currentEntity : currentImportList) {
                    boolean isExists = false;
                    for (TtaSystemCurrentLineEntity_HI_RO entityHiRo : daoHiRoList) {
                        if (currentEntity.getItem().equals(entityHiRo.getItem())) {
                            isExists = true;
                            Integer monthN = currentEntity.getMonthN();//当前导入月
                            Integer fristRecPogTime = entityHiRo.getFristRecPogTime();//首次上图时间
                            //当前导入月与首次上图月份相差值
                            int monthsBetween = WDatesUtils.getMonthsBetween(monthN.toString(), fristRecPogTime.toString());
                            if (monthsBetween <= 6) {
                                //筛选出大于首次上图时间并且小于当前导入月的最大店铺数量
                                TtaSystemCurrentLineEntity_HI_RO maxSystemCurrentEntity = allSystemCurrentList.stream().filter(item -> item.getMonthN().compareTo(fristRecPogTime) >= 0
                                        && item.getMonthN().compareTo(monthN) <= 0 && item.getItem().equals(entityHiRo.getItem())).max((o1, o2) -> Integer.compare(o1.getStoreCoun(), o2.getStoreCoun())).get();
                                currentEntity.setMaxStoreCoun(maxSystemCurrentEntity.getStoreCoun());//设置到当前导入月的item所对应的最大店铺数
                                currentEntity.setLastUpdateDate(new Date());
                                //对大于首次上图时间小于当前导入月之间的采购确认TOTAL店铺数求和
                                /*BigDecimal totalDecimal = nppAllData.stream().filter(nppItem -> nppItem.getMonthN() >= fristRecPogTime && nppItem.getMonthN() < monthN
                                        && nppItem.getItemCode().equals(entityHiRo.getItem())).map(nppMap -> nppMap.getAffirmTotStoreCount() == null ? BigDecimal.ZERO : nppMap.getAffirmTotStoreCount())
                                        .reduce(BigDecimal::add).get();*/
                                Optional<BigDecimal> reduce = nppAllData.stream().filter(nppItem -> nppItem.getMonthN() >= fristRecPogTime && nppItem.getMonthN() < monthN
                                        && nppItem.getItemCode().equals(entityHiRo.getItem())).map(nppMap -> nppMap.getAffirmTotStoreCount() == null ? BigDecimal.ZERO : nppMap.getAffirmTotStoreCount())
                                        .reduce(BigDecimal::add);
                                //BigDecimal totalDecimal = reduce.isPresent() ? reduce.get() : BigDecimal.ZERO;
                                BigDecimal totalDecimal = reduce.orElse(BigDecimal.ZERO);
                                currentEntity.setCographStoteNum(new BigDecimal(currentEntity.getMaxStoreCoun()).subtract(totalDecimal).intValue());
                                Map<String, Object> map = SaafBeanUtils.object2Map(currentEntity);
                                updateSCEntityList.add(map);
                            } else {
                                //当前月份与首次上图时间是否是相同的年度,如果是,更新当前月份的最大店铺数为0
                                if (WDatesUtils.isSameDate(String.valueOf(monthN), String.valueOf(fristRecPogTime))) {
                                    currentEntity.setMaxStoreCoun(0);
                                    currentEntity.setCographStoteNum(0);
                                    currentEntity.setLastUpdateDate(new Date());
                                } else {
                                    //这部分,等NPP数据生成了,再做更新操作
                                    entityHiRo.setStatus("N");//设置首次上图时间的item为失效状态
                                    TtaSystemCurrentLineEntity_HI systemCurrentLineEntity = new TtaSystemCurrentLineEntity_HI();
                                    BeanUtils.copyProperties(entityHiRo, systemCurrentLineEntity);
                                    systemCurrentLineEntity.setLastUpdateDate(new Date());
                                    Map<String, Object> objectMap = SaafBeanUtils.object2Map(systemCurrentLineEntity);
                                    efficacyItemList.add(objectMap);

                                    currentEntity.setMaxStoreCoun(0);
                                    currentEntity.setCographStoteNum(0);
                                    currentEntity.setLastUpdateDate(new Date());
                                }
                                Map<String, Object> object2Map = SaafBeanUtils.object2Map(currentEntity);
                                maxStoreZeroList.add(object2Map);
                            }
                        }
                    }
                    if (!isExists) {
                        currentEntity.setFristRecPogTime(currentEntity.getMonthN());//记录首次上图时间
                        currentEntity.setStatus("Y");//生效
                        currentEntity.setMaxStoreCoun(currentEntity.getStoreCoun());//最大店铺数量
                        currentEntity.setCographStoteNum(currentEntity.getStoreCoun());//上图数量
                        currentEntity.setLastUpdateDate(new Date());
                        Map<String, Object> objectMap = SaafBeanUtils.object2Map(currentEntity);
                        noExistsList.add(objectMap);
                    }
                }
            } else {
                for (TtaSystemCurrentLineEntity_HI entity_hi : currentImportList) {
                    entity_hi.setFristRecPogTime(entity_hi.getMonthN());//首次上图时间
                    entity_hi.setMaxStoreCoun(entity_hi.getStoreCoun());//最大的店铺数量
                    entity_hi.setCographStoteNum(entity_hi.getStoreCoun());//上图数量
                    entity_hi.setStatus("Y");//生效
                    entity_hi.setLastUpdateDate(new Date());
                    Map<String, Object> map = SaafBeanUtils.object2Map(entity_hi);
                    updateMaxOrCographStoreList.add(map);
                }
                // 不用此方式更新
                //ttaSystemCurrentLineDAO_HI.executeSqlUpdate(TtaSystemCurrentLineEntity_HI_RO.getUpdateFrirstCographTime(month));
            }
            JdbcUtils.beginTransaction();//开启事务
            if (noExistsList.size() > 0) {
                ttaSystemCurrentLineDAOHi.updateBatchJDBC("tta_system_current_line", TtaSystemCurrentLineEntity_HI.class, noExistsList);
            }
            if (updateSCEntityList.size() > 0) {
                ttaSystemCurrentLineDAOHi.updateBatchJDBC("tta_system_current_line", TtaSystemCurrentLineEntity_HI.class, updateSCEntityList);
            }
            if (maxStoreZeroList.size() > 0) {
                ttaSystemCurrentLineDAOHi.updateBatchJDBC("tta_system_current_line", TtaSystemCurrentLineEntity_HI.class, maxStoreZeroList);
            }
            if (updateMaxOrCographStoreList.size() > 0) {
                ttaSystemCurrentLineDAOHi.updateBatchJDBC("tta_system_current_line", TtaSystemCurrentLineEntity_HI.class, updateMaxOrCographStoreList);
            }

            LOGGER.info("******************第四步:插入[#]月份的NPP数据******".replace("#", month.toString()));
            String countKey = "TTA_NPP_YTD";
            int ytdId = jedisCluster.incrBy(countKey, 1).intValue();

            conn = JdbcUtils.getConnection();
            //插入基础表数据(包含了应收金额(不含加成,含加成)和追加应收金额的计算)
            String insertNppReportSql = TtaNppNewProductReportEntity_HI_RO.getInsertNppReportSql(batchCode, userId, Integer.valueOf(year), month, userSessionBean.getDeptCode());
            LOGGER.info("插入基础表SQL:\n" + insertNppReportSql);
            //Own Brand部门逻辑处理
            String  updateNppReportOwn = TtaNppNewProductReportEntity_HI_RO.getUpdateReportOwn(batchCode, userId, month) ;
            //插入上个月的将收取
            String insertWillChargeSql = TtaNppNewProductReportEntity_HI_RO.getInsertWillChargeNppReportByLastMonth(batchCode, userId, month);
            //插入YTD 数据
            String insertNppAdjuctRecieveNppSql = TtaNppNewProductReportEntity_HI_RO.getInsertNppAdjuctRecieveAmt(batchCode, userId, month, ytdId);
            //插入 ytd 的数据 TO NPP
            String adjuctRecieveAmtByYtd = TtaNppNewProductReportEntity_HI_RO.getInsertNppAdjuctRecieveAmtByYtd(batchCode, userId, month, ytdId);

            LOGGER.info("******************第五步:更新数据为生成状态*************************");
            //更新是否生成状态
            String updateNppIsCreateSql = TtaNppNewProductReportEntity_HI_RO.getUpdateNppIsCreateSql(batchCode, userId, month);
            st = conn.createStatement();
            st.addBatch(insertNppReportSql);
            st.addBatch(updateNppReportOwn);
            st.addBatch(updateNppIsCreateSql);
            st.addBatch(insertWillChargeSql);
            st.addBatch(insertNppAdjuctRecieveNppSql);
            st.addBatch(adjuctRecieveAmtByYtd);
            st.executeBatch();
            st.clearBatch();

            //插入基础表数据
            //ttaNppNewProductReportDAO_HI.executeSqlUpdate(TtaNppNewProductReportEntity_HI_RO.getInsertNppReportSql(batchCode, userId, Integer.valueOf(year), month, userSessionBean.getDeptCode()));
            //更新应收金额(不含加成,含加成)等值
            //ttaNppNewProductReportDAO_HI.executeSqlUpdate(TtaNppNewProductReportEntity_HI_RO.getUpdateNppReportSql(batchCode, userId, month));
            //更新是否生成状态
            //ttaNppNewProductReportDAO_HI.executeSqlUpdate(TtaNppNewProductReportEntity_HI_RO.getUpdateNppIsCreateSql(batchCode, userId, month));

            //更新首次上图时间为失效状态
            if (CollectionUtils.isNotEmpty(efficacyItemList)) {
                //ttaSystemCurrentLineDAO_HI.saveOrUpdateAll(efficacyItemList);
                ttaSystemCurrentLineDAOHi.updateBatchJDBC("tta_system_current_line", TtaSystemCurrentLineEntity_HI.class, efficacyItemList);
            }
            JdbcUtils.commitTransaction();//提交事务

            LOGGER.info("******************第六步:往头表插入数据start*************************");
            //5.头表插入一条数据
            TtaReportHeaderEntity_HI ttaReportHeaderEntity_hi = new TtaReportHeaderEntity_HI();
            ttaReportHeaderEntity_hi.setPromotionSection(month.toString());
            ttaReportHeaderEntity_hi.setOperatorUserId(userId);
            ttaReportHeaderEntity_hi.setStatus("DS01");//制作中
            ttaReportHeaderEntity_hi.setIsPublish("N");//未发布
            ttaReportHeaderEntity_hi.setBatchId(batchCode);
            ttaReportHeaderEntity_hi.setReportType("NPP");//NPP类型
            ttaReportHeaderEntity_hi.setCreatedBy(userId);
            ttaReportHeaderEntity_hi.setCreationDate(new Date());
            ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntity_hi);
            jsonObject.put("report", ttaReportHeaderEntity_hi);
            LOGGER.info("******************第七步:往头表插入数据成功 end*************************");
        } catch (Exception e) {
            e.printStackTrace();
            JdbcUtils.rollbackTransaction();
            throw new IllegalArgumentException(e.getMessage());
        } finally {
            JdbcUtils.closeConnection();
            JdbcUtils.release(st, rs);
        }
        return jsonObject;
    }

    private void checkData(Integer month) throws Exception {
        List<Map<String, Object>> mapList = ttaNppNewProductReportDAOHi.queryNamedSQLForList(TtaSystemCurrentLineEntity_HI_RO.getCountByIsCreate(month), new HashMap<>());
        if (CollectionUtils.isNotEmpty(mapList)) {
            BigDecimal cou = (BigDecimal) mapList.get(0).get("COU");
            if (cou.intValue() == 0) {
                throw new IllegalArgumentException("系统中不存在[#]月份的数据,请先导入数据再进行操作!".replace("#", month.toString()));
            }
        }
    }

    @Override
    public Pagination<TtaNppNewProductReportEntity_HI_RO> findNppInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception {
        StringBuffer sql = new StringBuffer();
        StringBuffer countSql = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        String flag = queryParamJSON.getString("flag");//判断是否是流程单据

        //流程单据查询
        if ("process".equals(flag)) {
            SaafToolUtils.validateJsonParms(queryParamJSON, "reportProcessHeaderId");
            sql.append(TtaNppNewProductReportEntity_HI_RO.NPP_QUERY);
            SaafToolUtils.parperParam(queryParamJSON, "tcc.process_id", "reportProcessHeaderId", sql, map, "=");
        } else {
            //校验数据
            SaafToolUtils.validateJsonParms(queryParamJSON, "batchCode");

            //非 BIC用户
            if (!("45".equals(sessionBean.getUserType()))) {
                sql.append(TtaNppNewProductReportEntity_HI_RO.NPP_QUERY_NO_BIC);
                map.put("userId", sessionBean.getUserId());
                //拼接查询条件
                //this.setJoinSql(queryParamJSON, sql, map);
                //countSql = SaafToolUtils.getSqlCountString(sql);
                //SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tcc.NPP_ID desc", false);
            } else {//BIC用户
                sql.append(TtaNppNewProductReportEntity_HI_RO.NPP_QUERY);
                sql.append(" and nvl(tcc.batch_code, '-1') = :batchCode ");
                //拼接查询条件
                //this.setJoinSql(queryParamJSON, sql, map);
                //countSql = SaafToolUtils.getSqlCountString(sql);
                //SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tcc.NPP_ID desc", false);
            }

            map.put("batchCode", queryParamJSON.getString("batchCode"));
            String groupCode = queryParamJSON.getString("groupCode");
            if (StringUtils.isNotEmpty(groupCode)) {
                sql.append(" and tcc.group_code in ('").append(String.join("','", groupCode.split(","))).append("') ");
            }
            this.setJoinSql(queryParamJSON, sql, map);
            countSql = SaafToolUtils.getSqlCountString(sql);
            SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tcc.NPP_ID desc", false);
        }
        Pagination<TtaNppNewProductReportEntity_HI_RO> resultList = ttaNppNewProductReportDAO_HI_RO.findPagination(sql, countSql, map, pageIndex, pageRows);
        return resultList;
    }

    private void setJoinSql(JSONObject queryParamJSON, StringBuffer sql, Map<String, Object> map) {
        //查询条件
        SaafToolUtils.parperParam(queryParamJSON, "tcc.VENDOR_NBR", "vendorNbr", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.VENDOR_NAME", "vendorName", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.group_code", "groupCode", sql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.dept_code", "deptCode", sql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.dept_desc", "deptDesc", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.brand_cn", "brandCn", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.item_code", "itemCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.collect_way", "collectWay", sql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.purchase_act", "purchaseAct", sql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "tcc.group_desc", "groupDesc", sql, map, "fulllike");
    }

    /**
     * 供应商拆分操作
     *
     * @param save
     * @param userId
     * @param currentRow
     * @return
     * @throws Exception
     */
    @Override
    @SuppressWarnings("all")
    public List<TtaNppNewProductReportEntity_HI> saveOrUpdateSplitALL(JSONArray paramJSON, int userId, JSONObject currentRow) throws Exception {
        StringBuffer sb = new StringBuffer();
        List<TtaNppNewProductReportEntity_HI> objects = new ArrayList<>();

        //被拆分记录(选中的数据)
        TtaNppNewProductReportEntity_HI parentEntity = SaafToolUtils.setEntity(TtaNppNewProductReportEntity_HI.class, currentRow, ttaNppNewProductReportDAO_HI, userId);

        //检验数据
        if (null == parentEntity) {
            throw new IllegalArgumentException("当前行不存在,无法拆分,请重试");
        }

        BigDecimal totalBigD = new BigDecimal("0");//采购确认总金额，用于计算总比例值
        //采购确认总金额
        for (int i = 0; i < paramJSON.size(); i++) {
            JSONObject json = (JSONObject) paramJSON.get(i);
            totalBigD = totalBigD.add(json.getBigDecimal("unconfirmAmount")); //采购确认金额
        }

        //需要拆分的供应商列表
        for (int i = 0; i < paramJSON.size(); i++) {
            JSONObject json = (JSONObject) paramJSON.get(i);
            TtaNppNewProductReportEntity_HI instance = new TtaNppNewProductReportEntity_HI();

            //记录最上级拆分父级
            if (SaafToolUtils.isNullOrEmpty(parentEntity.getParentId())) {
                //第一次拆分
                instance.setParentId(parentEntity.getNppId());
            } else {
                //N次拆分
                instance.setParentId(parentEntity.getParentId());
            }

            SaafBeanUtils.copyUnionProperties(parentEntity, instance);
            parentEntity.setStatus(0);
            parentEntity.setLastUpdatedBy(userId);
            parentEntity.setLastUpdateDate(new Date());

            instance.setStatus(1);

            //置空字段
            instance.setAboiReceipts(null);//实收金额
            instance.setDebitOrderCode(null);//借记单编号
            instance.setPurchaseAct(null);// 采购行动
            instance.setExemptionReason(null); //豁免原因_1
            instance.setExemptionReason2(null); //豁免原因_2
            instance.setTransferInPerson(null);//转办人(转入)
            instance.setTransferInDate(null);//转办时间(转入)
            instance.setTransferOutPerson(null);//转办人(转出)
            instance.setTransferOutDate(null);//转办时间(转出)
            instance.setTransferLastPerson(null);//转办人(LAST_转出)
            instance.setTransferLastDate(null);// 转办时间(LAST_转出)

            //拼接活动内容:"NPP" - "Month" - "Department" - "Item code" - "Brand (CN)" - "供应商编号"
            sb.append("NPP-" + instance.getMonth() + "-" + instance.getGroupDesc() + "-" + instance.getItemCode() +
                    "-" + instance.getBrandCn() + "-" + json.getString("vendorNbr"));

            instance.setParentId(SaafToolUtils.isNullOrEmpty(parentEntity.getParentId()) ? parentEntity.getNppId() : parentEntity.getParentId());

            //拆分前的供应商编码和供应商名称
            instance.setParentVendorCode(SaafToolUtils.isNullOrEmpty(parentEntity.getParentId()) ? parentEntity.getVendorNbr() : parentEntity.getParentVendorCode());
            instance.setParentVendorName(SaafToolUtils.isNullOrEmpty(parentEntity.getParentId()) ? parentEntity.getVendorName() : parentEntity.getParentVendorName());
            instance.setVendorNbr(json.getString("vendorNbr"));//当前拆分的供应商编码
            instance.setVendorName(json.getString("vendorName"));//当前拆分的供应商名称
            instance.setUnconfirmAmount(json.getBigDecimal("unconfirmAmount")); ////采购确认金额

            //应收金额处理：按照确认收取金额拆分 应收金额*(当行确认收取金额/确认收取总金额)
            BigDecimal parentReceiveAmount = parentEntity.getReceiveAmount();//父级应收金额
            Double rateAmount = BigDecimalUtils.divide(instance.getUnconfirmAmount().doubleValue(), totalBigD.doubleValue());
            BigDecimal childUnconfirmAmount = new BigDecimal(BigDecimalUtils.multiply(parentReceiveAmount.doubleValue(), rateAmount)).setScale(0, BigDecimal.ROUND_HALF_UP);
            instance.setReceiveAmount(childUnconfirmAmount);//通过比例计算下级的应收金额
            instance.setActivity(sb.toString());
            instance.setOperatorUserId(userId);
            instance.setCreatedBy(userId);
            instance.setCreationDate(new Date());
            instance.setLastUpdatedBy(userId);
            instance.setLastUpdateDate(new Date());
            objects.add(instance);
        }

        ttaNppNewProductReportDAO_HI.saveOrUpdateAll(objects);
        ttaNppNewProductReportDAO_HI.saveOrUpdate(parentEntity);
        return objects;
    }

    /**
     * 保存或者更新操作
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaNppNewProductReportEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception {
        List<TtaNppNewProductReportEntity_HI> reportEntityHiList = new ArrayList<>();
        JSONArray save = paramsJSON.getJSONArray("save");
        for (int i = 0; i < save.size(); i++) {
            JSONObject json = (JSONObject) save.get(i);
            TtaNppNewProductReportEntity_HI instance = SaafToolUtils.setEntity(TtaNppNewProductReportEntity_HI.class, json, ttaNppNewProductReportDAO_HI, userId);
            reportEntityHiList.add(instance);
        }
        ttaNppNewProductReportDAO_HI.saveOrUpdateAll(reportEntityHiList);
        return reportEntityHiList;
    }

    /**
     * 转办人保存
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaNppNewProductReportEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception {
        List<TtaNppNewProductReportEntity_HI> objects = new ArrayList<>();
        JSONArray jsonArrayLine = paramsJSON.getJSONArray("lines");
        Integer personIds = paramsJSON.getInteger("personId");
        Date date = new Date();
        for (int i = 0; i < jsonArrayLine.size(); i++) {
            JSONObject json = (JSONObject) jsonArrayLine.get(i);
            TtaNppNewProductReportEntity_HI instance = SaafToolUtils.setEntity(TtaNppNewProductReportEntity_HI.class, json, ttaNppNewProductReportDAO_HI, userId);
            instance.setTransferInDate(date);
            //instance.setTransferOutDate(instance.getTransferOutDate() == null ? date : instance.getTransferOutDate());
            instance.setTransferLastDate(date);
            instance.setTransferInPerson(personIds);//被转办人
            //instance.setTransferOutPerson(instance.getTransferOutPerson() == null ? userId : instance.getTransferOutPerson());
            instance.setTransferLastPerson(userId);//转办人
            instance.setOperatorUserId(userId);
            objects.add(instance);
        }
        ttaNppNewProductReportDAO_HI.saveOrUpdateAll(objects);
        return objects;
    }

    @Override
    public Pagination<TtaNppNewProductReportEntity_HI_RO> findProcessSummaryInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) {
        Pagination<TtaNppNewProductReportEntity_HI_RO> pagination = ttaNppNewProductReportDAO_HI_RO.findPagination(TtaNppNewProductReportEntity_HI_RO.getProcessSummary(queryParamJSON.getString("batchCode"), sessionBean.getUserName(), sessionBean.getUserId()), new HashMap<String, Object>(), 0, Integer.MAX_VALUE);
        return pagination;
    }

    @Override
    public Pagination<TtaNppNewProductReportEntity_HI_RO> findProcessNppInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        sql.append(TtaNppNewProductReportEntity_HI_RO.getQueryProcess(sessionBean.getUserName(), sessionBean.getUserId()));
        Pagination<TtaNppNewProductReportEntity_HI_RO> resultList = ttaNppNewProductReportDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), queryMap, pageIndex, pageRows);
        return resultList;
    }

    @Override
    public Pagination<TtaNppNewProductReportEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaNppNewProductReportEntity_HI_RO.QUERY_EXISTS);
        StringBuffer countSql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("batchCode",queryParamJSON.getString("batchCode"));
        SaafToolUtils.parperParam(queryParamJSON, "tom.group_code", "groupCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
        sql.append(" group by     tom.group_code,tom.group_desc,tom.dept_code,tom.dept_desc,tom.brand_cn ");
        countSql = SaafToolUtils.getSqlCountString(sql) ;
        Pagination<TtaNppNewProductReportEntity_HI_RO> resultList =ttaNppNewProductReportDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    @Override
    public TtaNppNewProductReportEntity_HI_RO findChangeVender(JSONObject queryParamJSON) {
        //获取加成比例,收费方式,应收金额(含加成),应收金额（不含加成），rateCard 年份，合同条款
        TtaNppNewProductReportEntity_HI_RO entityHiRo = JSON.parseObject(queryParamJSON.toJSONString(), TtaNppNewProductReportEntity_HI_RO.class);
        String priorVendorCode = entityHiRo.getPriorVendorCode();//采购确认供应商
        String ctw = entityHiRo.getCtw();
        String eb = entityHiRo.getEb();
        Integer storeCoun = entityHiRo.getStoreCoun();//店铺数量
        String groupCode = entityHiRo.getGroupCode();
        //获取年份
        Integer monthN = entityHiRo.getMonthN();
        String year = monthN.toString().substring(0,4);
        TtaNppNewProductReportEntity_HI_RO result = ttaNppNewProductReportDAO_HI_RO.get(TtaNppNewProductReportEntity_HI_RO.getChangeVenderSql(priorVendorCode,
                ctw,eb,storeCoun,groupCode,year));
        Assert.notNull(result,"没有找到该供应商的主从供应商信息！");
        entityHiRo.setReceiveAmount(result.getReceiveAmount());//应收金额（不含加成）
        entityHiRo.setReceiveAmountAdd(result.getReceiveAmountAdd());//应收金额（含加成）
        entityHiRo.setContractTerms(result.getContractTerms());//条款
        entityHiRo.setAdditionRate(result.getAdditionRate());//加成比例
        entityHiRo.setChargeStandards(result.getChargeStandards());//收费标准*/
        entityHiRo.setChargeMethod(result.getChargeMethod());//收费方式
        return entityHiRo;
    }

}
