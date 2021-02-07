package com.sie.watsons.base.withdrawal.model.inter.server;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;
import javax.transaction.SystemException;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.EnumBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.sie.saaf.common.util.BigDecimalUtils;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineEntity_HI_RO;
import com.sie.watsons.base.contract.model.entities.readonly.TtaContractLineHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.*;
import com.sie.watsons.base.proposal.model.entities.readonly.*;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnL;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;
import com.sie.watsons.base.proposal.model.inter.server.TtaBrandplnHServer;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.TtaSplitBrandDetailEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSplitBrandDetailEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemRelSupplierEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSplitBrandDetail;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.utils.*;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.dao.CommonDAO_HI_DY;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemHeaderEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.entities.readonly.TtaSupplierItemMidEntity_HI_RO;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemMid;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;

import redis.clients.jedis.JedisCluster;

@Component("ttaSupplierItemMidServer")
public class TtaSupplierItemMidServer extends BaseCommonServer<TtaSupplierItemMidEntity_HI> implements ITtaSupplierItemMid {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemMidServer.class);

    @Autowired
    private ITtaSupplierItemRelSupplier iTtaSupplierItemRelSupplier;
    @Autowired
    BaseCommonDAO_HI<TtaSupplierItemMidEntity_HI> baseCommonDAO;
    @Autowired
    private GenerateCodeServer generateCodeServer;
    private ExecutorService customerExecutorService = new ThreadPoolExecutor(4, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    //使用排队策略 LinkedBlockingQueue
    private static ExecutorService threadPoolExecutorService = new ThreadPoolExecutor(20, 100, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    @Autowired
    private BaseCommonDAO_HI<TtaSupplierItemMidEntity_HI> ttaSupplierItemMidDAO_HI;
    @Autowired
    private BaseViewObject<TtaSupplierItemMidEntity_HI_RO> ttaSupplierItemMidDAO_HI_RO;
    @Autowired
    private ITtaProposalHeader ttaProposalHeaderServer;
    @Autowired
    private ITtaBrandplnL ttaBrandplnLServer;
    @Autowired
    private TtaBrandplnHServer ttaBrandplnHServer;
    @Autowired
    private ITtaSupplierItemHeader ttaSupplierItemHeaderServer;
    @Autowired
    private ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer;
    @Autowired
    private ViewObject<TtaSplitBrandDetailEntity_HI> ttaSplitBrandDetailDAO_HI;
    @Autowired
    private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;
    @Autowired
    private ViewObject<TtaBrandplnLEntity_HI> ttaBrandplnLDAO_HI;
    @Autowired
    private BaseCommonDAO_HI<TtaBrandplnHEntity_HI> ttaBrandplnHDAO_HI;
    @Autowired
    private ViewObject<TtaTermsHEntity_HI> ttaTermsHDAO_HI;
    @Autowired
    private ITtaSplitBrandDetail ttaSplitBrandDetailServer;
    @Autowired
    private BaseViewObject<TtaSupplierEntity_HI_RO> ttaSupplierDAO_HI_RO;
    @Autowired
    private ViewObject<TtaTermsLEntity_HI> ttaTermsLDAO_HI;
    @Autowired
    private ViewObject<TtaContractLineEntity_HI> ttaContractLineDAO_HI;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ITtaBrandCountCRecord ttaBrandCountCRecordServer;


    public TtaSupplierItemMidServer() {
        super();
    }

    public static final Map<String, String> UP_CONDITION = new HashMap<>();

    static {
        UP_CONDITION.put("Group", "tsim.group_code = tai.group_code");
        UP_CONDITION.put("Group+Dept", "tsim.group_code = tai.group_code \n" +
                "         and tsim.dept_code = tai.dept_code");
        UP_CONDITION.put("Group+Dept+Brand", "tsim.group_code = tai.group_code \n" +
                "         and tsim.dept_code = tai.dept_code and tsim.brand_name = tai.brand_cn and tsim.brand_name_en = tai.brand_en");
        UP_CONDITION.put("Group+Dept+Brand+Item", "tsim.group_code = tai.group_code \n" +
                "         and tsim.dept_code = tai.dept_code and tsim.brand_name = tai.brand_cn and tsim.brand_name_en = tai.brand_en and tsim.item_code = tai.item_nbr");
    }

    /**
     * 保存purchase数据
     *
     * @param splitDetailListBySplitAndDate
     * @param instance
     * @param userId
     */
    @Override
    public List<TtaSupplierItemMidEntity_HI> savePurchaseInfoList(List<Map<String, Object>> splitDetailListBySplitAndDate, TtaSupplierItemHeaderEntity_HI instance, int userId) throws Exception {
        TtaProposalHeaderEntity_HI_RO proposalHearder = getProposalHearder(instance.getProposalCode(),instance.getVersionCode());
        List<TtaBrandplnLEntity_HI_RO> brandplnLEntityHiRoList = this.findBrandplnDataByProposalSplit(proposalHearder.getProposalId());
        Map<String, Map<String, BigDecimal>> sumBrandFcsAmount = setSumBrandFcsAmount(brandplnLEntityHiRoList, instance.getSplitCondition());
        List<TtaSupplierItemMidEntity_HI> list = new ArrayList<>();
        String contrast = "";
        String splitCondition = instance.getSplitCondition();
        LOGGER.info("Proposal拆分与合并头信息拆分条件: {}", splitCondition);
        for (int i = 0; i < splitDetailListBySplitAndDate.size(); i++) {
            Object object = splitDetailListBySplitAndDate.get(i);
            JSONObject queryObject = JSONObject.parseObject(JSON.toJSONString(object));
            TtaSupplierItemMidEntity_HI entity = SaafToolUtils.setEntity(TtaSupplierItemMidEntity_HI.class, queryObject, ttaSupplierItemMidDAO_HI, userId);
            StringBuffer sb = new StringBuffer();
            String groupString = getGroupStr(entity, instance.getSplitCondition());
            //String groupString =sb.append(entity.getGroupName()).append("-").append(entity.getDeptCode()).append("-").append(entity.getBrandName()).toString();
            entity.setSupplierItemHId(instance.getSupplierItemId());
            entity.setSplitSupplierCode(contrast);
            entity.setSplitSupplierName(contrast);
            entity.setCreationDate(new Date());
            entity.setCreatedBy(userId);
            if ("Group".equals(splitCondition)) {
                entity.setDeptCode(contrast);
                entity.setDeptName(contrast);
                entity.setBrandCode(contrast);
                entity.setBrandName(contrast);
                entity.setBrandNameEn(contrast);
                entity.setItemCode(contrast);
                entity.setItemName(contrast);
            } else if ("Group+Dept".equals(splitCondition)) {
                entity.setBrandCode(contrast);
                entity.setBrandName(contrast);
                entity.setBrandNameEn(contrast);
                entity.setItemCode(contrast);
                entity.setItemName(contrast);
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                entity.setItemCode(contrast);
                entity.setItemName(contrast);
            }

            if (sumBrandFcsAmount.containsKey(groupString)) {
                Map<String, BigDecimal> decimalMap = sumBrandFcsAmount.get(groupString);
                entity.setBrandFcsPurchase(decimalMap.get("brandFcsPurchase"));
                entity.setBrandFcsSales(decimalMap.get("brandFcsSales"));
            } else {
                entity.setBrandFcsPurchase(new BigDecimal("0"));
                entity.setBrandFcsSales(new BigDecimal("0"));
            }
            list.add(entity);
        }
        ttaSupplierItemMidDAO_HI.saveOrUpdateAll(list);
        return list;
    }

    /**
     * 汇总group + dept + brand等等维度的数据
     *
     * @param brandplnLEntityHiRoList
     * @return
     */
    private Map<String, Map<String, BigDecimal>> setSumBrandFcsAmount(List<TtaBrandplnLEntity_HI_RO> brandplnLEntityHiRoList, String splitCondition) {
        Map<String, Map<String, BigDecimal>> sumMap = new HashMap<>();
        for (TtaBrandplnLEntity_HI_RO entityHiRo : brandplnLEntityHiRoList) {
            StringBuffer sb = new StringBuffer();
            //String groupString = sb.append(entityHiRo.getGroupDesc()).append("-").append(entityHiRo.getDeptCode()).append("-").append(entityHiRo.getBrandCn()).toString();
            String groupString = getGroupStr(entityHiRo, splitCondition);
            if (sumMap.containsKey(groupString)) {
                Map<String, BigDecimal> stringBigDecimalMap = sumMap.get(groupString);
                stringBigDecimalMap.put("brandFcsPurchase", stringBigDecimalMap.get("brandFcsPurchase").add(entityHiRo.getFcsPurchase()));
                stringBigDecimalMap.put("brandFcsSales", stringBigDecimalMap.get("brandFcsSales").add(entityHiRo.getFcsSales()));
            } else {
                Map<String, BigDecimal> map = new HashMap<>();
                map.put("brandFcsPurchase", entityHiRo.getFcsPurchase() == null ? BigDecimal.ZERO : entityHiRo.getFcsPurchase());
                map.put("brandFcsSales", entityHiRo.getFcsSales() == null ? BigDecimal.ZERO : entityHiRo.getFcsSales());
                sumMap.put(groupString, map);
            }
        }
        return sumMap;
    }

    @Override
    public void saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        LOGGER.info("*************第一步:指定供应商操作开始*************************");
        JSONObject supplierItemHeaderInfo = paramsJSON.getJSONObject("supplierItemHeaderInfo");
        Assert.notNull(supplierItemHeaderInfo, "未填写单据信息,请先填写");
        Integer supplierItemId = supplierItemHeaderInfo.getInteger("supplierItemId");
        Assert.notNull(supplierItemId, "单据信息未保存,无法指定供应商");
        String supplierCode = supplierItemHeaderInfo.getString("supplierCode");
        String startDateStr = supplierItemHeaderInfo.getString("startDate");
        String endDateStr = supplierItemHeaderInfo.getString("endDate");
        String splitCondition = supplierItemHeaderInfo.getString("splitCondition");

        //根据单据头信息的供应商查询关联供应商的数据
        Pagination<TtaSupplierItemRelSupplierEntity_HI_RO> findList = ttaSupplierItemRelSupplierServer.findSupplierItemRelSupplierListBySupplierItemId(supplierItemId);
        List<TtaSupplierItemRelSupplierEntity_HI_RO> relSupplierData = findList.getData();
        //查询关联供应商的数据
        String relSupplierCodeSql = "select trs.rel_supplier_code from tta_rel_supplier trs \n" +
                "join tta_supplier ts on trs.rel_id = ts.supplier_id \n" +
                "where ts.supplier_code ='" + supplierCode + "'";
        List<Map<String, Object>> sqlForList = ttaSupplierItemRelSupplierServer.queryNamedSQLForList(relSupplierCodeSql, new HashMap<>());

        List<String> supplierList = new ArrayList<>();
        supplierList.add(supplierCode);
        for (TtaSupplierItemRelSupplierEntity_HI_RO datum : relSupplierData) {
            supplierList.add(datum.getRelSupplierCode());
        }
        for (Map<String, Object> stringObjectMap : sqlForList) {
            String relSupplierCode = (String) stringObjectMap.get("REL_SUPPLIER_CODE");
            supplierList.add(relSupplierCode);
        }
        String joinSupplierStr = "'" + StringUtils.join(supplierList, "','") + "'";

        //JSONArray list = paramsJSON.getJSONArray("list");
        //Assert.notEmpty(list, "请先选择需要指定供应商的数据!");
        List<TtaSupplierItemMidEntity_HI> list = ttaSupplierItemMidDAO_HI.findByProperty(new JSONObject().fluentPut("supplierItemHId", supplierItemId));
        Assert.notEmpty(list, "符合拆分条件明细数据为空,提交失败!");

        Date startDate = SaafDateUtils.string2UtilDate(startDateStr, "yyyy-MM");
        Date endDate = SaafDateUtils.string2UtilDate(endDateStr, "yyyy-MM");
        String format = SaafDateUtils.convertDateToString(startDate, "yyyyMM");
        String format1 = SaafDateUtils.convertDateToString(endDate, "yyyyMM");
        List<String> from2MonthList = WDatesUtils.getFrom2Month(format, format1);
        List<Integer> monthBetween = WDatesUtils.getMonthBetween(startDate, endDate);

        String splitString = "";
        if (UP_CONDITION.containsKey(splitCondition.trim())) {
            splitString += UP_CONDITION.get(splitCondition.trim());
        }
        if (StringUtils.isBlank(splitString)) {
            throw new IllegalArgumentException("拆分条件未选择,请重新保存单据");
        }

        LOGGER.info("*************第二步:检查符合条件数据和保存拆分操作*************************");
        //进行检查符合条件拆分明细数据和保存拆分之后的数据
        this.saveAndCheckBrandSplit(paramsJSON, userId);

        Long startTime = System.currentTimeMillis();
        List<Future<String>> futureList = new ArrayList<>();

        //监控主线程
        CountDownLatch mainLatch = new CountDownLatch(1);
        //监控子线程
        CountDownLatch threadLatch = new CountDownLatch(from2MonthList.size() + monthBetween.size() + 6);
        //根据子线程执行结果判断是否需要回滚
        BlockingDeque<Boolean> resultList = new LinkedBlockingDeque<>(from2MonthList.size() + monthBetween.size() + 6);
        //必须要使用对象，如果使用变量会造成线程之间不可共享变量值
        RollBack rollBack = new RollBack(false);

        String finalSplitString = splitString;//拆分条件
        List<String> itemList = new ArrayList<>();
        //List<TtaSupplierItemMidEntity_HI> entityHiList = list.toJavaList(TtaSupplierItemMidEntity_HI.class);
        //List<TtaSupplierItemMidEntity_HI> entityHiList = list;
        List<Integer> midIdList = new ArrayList<>();
        list.forEach(entity -> {
            Integer mid = entity.getMid();
            midIdList.add(mid);
        });
        String joinMidId = "'" + StringUtils.join(midIdList, "','") + "'";

        LOGGER.info("*************第三步:更新TTA_SALE_SUM_XX表*************************");
        //更新tta_sale_sum_xx表
        from2MonthList.forEach(toMonth -> {
            SelectUpdateSupplierCallable selectUpdateSupplierCallable = new SelectUpdateSupplierCallable(mainLatch, threadLatch, rollBack, resultList,
                    supplierItemId, joinSupplierStr, toMonth, splitCondition, itemList, finalSplitString, joinMidId);

            Future<String> stringFuture = threadPoolExecutorService.submit(selectUpdateSupplierCallable);
            futureList.add(stringFuture);
        });

        LOGGER.info("*************第四步:更新TTA_PURCHASE_IN_XX表*************************");
        //更新tta_purchase_in_xx表
        monthBetween.forEach(yearItem -> {
            SelectUpdatePurchaseSupplierCallable selectUpdatePurchaseSupplierCallable
                    = new SelectUpdatePurchaseSupplierCallable(mainLatch, threadLatch, rollBack, resultList, ttaSupplierItemHeaderServer,
                    supplierItemId, joinSupplierStr, yearItem, splitCondition, itemList, finalSplitString, joinMidId);
            Future<String> stringFuture = threadPoolExecutorService.submit(selectUpdatePurchaseSupplierCallable);
            futureList.add(stringFuture);
        });

        //----------------------业务: 如下为OI账单相关的处理逻辑  start--------------------------------------------------------
        LOGGER.info("*************第五步:更新oi六种场景数据*************************");
        //拆分oi六种场景数据
        for (int oi = 1; oi <= 6; oi++) {
            UpdateSelectTotalOiSceneCallable updateSelectTotalOiSceneCallable = new UpdateSelectTotalOiSceneCallable(mainLatch, threadLatch, rollBack, resultList,
                    joinSupplierStr, splitString, ttaSupplierItemHeaderServer, joinMidId, format, format1, oi);
            Future<String> stringFuture = threadPoolExecutorService.submit(updateSelectTotalOiSceneCallable);
            futureList.add(stringFuture);
        }
        //----------------------业务: 如下为OI账单相关的处理逻辑  end-------------------------------------------------------------


        /** 存放子线程返回结果. */
        List<Boolean> backUpResult = Lists.newArrayList();
        try {
            //等待所有子线程执行完毕()
            boolean await = threadLatch.await(1800, TimeUnit.SECONDS);
            //threadLatch.await();

            //如果超时，直接回滚
            if (!await) {
                rollBack.setRollBack(true);
            } else {
                //查看执行情况，如果有存在需要回滚的线程，则全部回滚
                for (int i = 0; i < from2MonthList.size() + monthBetween.size() + 6; i++) {
                    Boolean result = resultList.take();
                    backUpResult.add(result);
                    LOGGER.debug("子线程返回结果result: {}", result);
                    if (result) {
                        /** 有线程执行异常，需要回滚子线程. */
                        rollBack.setRollBack(true);
                    }
                }
            }
        } catch (InterruptedException e) {
            jedisCluster.setex(paramsJSON.getString("createKey"), 3600, "{}");
            ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, e);
            LOGGER.error("等待所有子线程执行完毕时，出现异常");
            throw new SystemException("等待所有子线程执行完毕时，出现异常，整体回滚");
        } finally {
            mainLatch.countDown();
        }

        /** 检查子线程是否有异常，有异常整体回滚. */
        for (int i = 0; i < from2MonthList.size() + monthBetween.size() + 6; i++) {
            if (CollectionUtils.isNotEmpty(backUpResult)) {
                Boolean result = backUpResult.get(i);
                if (result) {
                    jedisCluster.setex(paramsJSON.getString("createKey"), 3600, "{}");
                    ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, new IllegalArgumentException("指定供应商失败"));
                    LOGGER.info("指定供应商失败，整体回滚1");
                    throw new SystemException("指定供应商失败");
                }
            } else {
                jedisCluster.setex(paramsJSON.getString("createKey"), 3600, "{}");
                ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, new IllegalArgumentException("指定供应商失败"));
                LOGGER.info("指定供应商失败，整体回滚2");
                throw new SystemException("指定供应商失败");
            }
        }

        //拼接结果
        try {
            for (Future<String> future : futureList) {
                String returnS = future.get();
                System.out.println("子线程返回结果:" + returnS);
                if ("fail".equals(returnS)) {
                    jedisCluster.setex(paramsJSON.getString("createKey"), 3600, "{}");
                    ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, new IllegalArgumentException("指定供应商子线程返回的结果:" + returnS));
                    throw new RuntimeException("指定供应商失败!,子线程返回的结果:" + returnS);
                }
            }
        } catch (Exception e) {
            jedisCluster.setex(paramsJSON.getString("createKey"), 3600, "{}");
            ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, e);
            LOGGER.info("获取子线程操作结果出现异常，异常信息： {}", e);
            throw new SystemException("指定供应商子线程正常更新成功，主线程出现异常，回滚失败");
        }
        jedisCluster.setex(paramsJSON.getString("createKey"), 3600, JSON.toJSONString(paramsJSON));
        ttaBrandCountCRecordServer.updateProposalSplitRecordStatus(paramsJSON, null);
        Long endTime = System.currentTimeMillis();
        LOGGER.info("线程执行总时间:{}", (endTime - startTime) / 1000 + "m");
    }

    /**
     * 选中数据更新指定供应商Callable对象
     */
    public class SelectUpdateSupplierCallable implements Callable<String> {
        /**
         * 主线程监控
         */
        private CountDownLatch mainLatch;
        /**
         * 子线程监控
         */
        private CountDownLatch threadLatch;
        /**
         * 是否回滚
         */
        private RollBack rollBack;
        private BlockingDeque<Boolean> resultList;

        private Integer supplierItemId;//头id
        private String joinSupplierStr;//拼接多个供应商
        private String toMonth;//年月份
        private String splitCondition;//拆分条件

        private List<String> itemList;//item集合
        private String finalSplitString;
        private String joinMidId;

        public SelectUpdateSupplierCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList,
                                            Integer supplierItemId, String joinSupplierStr, String toMonth, String splitCondition, List<String> itemList, String finalSplitString, String joinMidId) {
            this.mainLatch = mainLatch;
            this.threadLatch = threadLatch;
            this.rollBack = rollBack;
            this.resultList = resultList;
            this.supplierItemId = supplierItemId;
            this.joinSupplierStr = joinSupplierStr;
            this.toMonth = toMonth;
            this.splitCondition = splitCondition;
            this.itemList = itemList;
            this.finalSplitString = finalSplitString;
            this.joinMidId = joinMidId;
        }

        @Override
        public String call() throws Exception {
            //为了保证事务不提交，此处只能调用一个有事务的方法，spring 中事务的颗粒度是方法，只有方法不退出，事务才不会提交
            return ttaSupplierItemHeaderServer.updateSplitCodeBySelectData(mainLatch, threadLatch, rollBack, resultList, supplierItemId, joinSupplierStr, toMonth, splitCondition, itemList, finalSplitString, joinMidId);
        }

    }

    @Override
    public Pagination<TtaSupplierItemMidEntity_HI> findSplitDetailList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
        Integer supplierItemHId = queryParamJSON.getInteger("supplierItemHId");
        StringBuffer sb = new StringBuffer("from TtaSupplierItemMidEntity_HI ttaSupplierItemM where 1=1 and ttaSupplierItemM.supplierItemHId =:supplierItemHId ");
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("supplierItemHId", supplierItemHId);
        //SaafToolUtils.parperParam(queryParamJSON,"ttaSupplierItemM.supplier_item_h_id","supplierItemHId",sb,queryParamMap,"=",true);
        SaafToolUtils.parperParam(queryParamJSON, "ttaSupplierItemM.group_name", "groupName", sb, queryParamMap, "like", true);
        SaafToolUtils.parperParam(queryParamJSON, "to_number(ttaSupplierItemM.dept_code)", "deptCode", sb, queryParamMap, "=", true);
        SaafToolUtils.parperParam(queryParamJSON, "ttaSupplierItemM.dept_name", "deptName", sb, queryParamMap, "like", true);
        SaafToolUtils.parperParam(queryParamJSON, "ttaSupplierItemM.brand_name", "brandName", sb, queryParamMap, "like", true);
        SaafToolUtils.parperParam(queryParamJSON, "to_number(ttaSupplierItemM.item_code)", "itemCode", sb, queryParamMap, "=", true);
        SaafToolUtils.changeQuerySort(queryParamJSON, sb, "MID asc", true);
        Pagination<TtaSupplierItemMidEntity_HI> pagination = ttaSupplierItemMidDAO_HI.findPagination(sb, queryParamMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public Set<String> findSupplierItemMidList(Integer supplierItemId) throws Exception {
        StringBuffer sql = new StringBuffer(" select tsim.mid as mid,\n" +
                "          tsim.supplier_item_h_id  as supplierItemHId,\n" +
                "          tsim.supplier_code       as supplierCode,\n" +
                "          tsim.split_supplier_code as splitSupplierCode\n" +
                "     from tta_supplier_item_mid tsim\n" +
                "    where tsim.supplier_item_h_id = :supplierItemHId");
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("supplierItemHId", supplierItemId);
        //去重集合
        Set<String> supplierCodeList = new HashSet<>();
        List<TtaSupplierItemMidEntity_HI_RO> list = ttaSupplierItemMidDAO_HI_RO.findList(sql, queryParamMap);
        if (list != null && list.size() > 0) {
            for (TtaSupplierItemMidEntity_HI_RO midEntity_hi : list) {
                if (StringUtils.isBlank(midEntity_hi.getSplitSupplierCode())) {
                    midEntity_hi.setSplitSupplierCode("-1");
                }
                supplierCodeList.add(midEntity_hi.getSupplierCode());
                supplierCodeList.add(midEntity_hi.getSplitSupplierCode());
            }
        }
        return supplierCodeList;
    }

    @Override
    public List<Map<String, Object>> saveSpliteIo(JSONObject paramJson) throws Exception {
        String uuid = generateCodeServer.generateUUIDString();
        String startMonth = paramJson.getString("startMonth");
        String endMonth = paramJson.getString("endMonth");
        String vendorNbrListStr = paramJson.getString("vendorNbrList");//供应商信息
        vendorNbrListStr = "1015733105,1065246101";
        LOGGER.info(".saveSpliteIo 调用io拆分参数信息:{}, uuid:{}", paramJson, uuid);
        if (StringUtils.isBlank(vendorNbrListStr)) {
            return null;
        }
        String[] split = vendorNbrListStr.split(","); //供应
        List<String> vendorNbrList = Arrays.asList(split);
        ArrayList<Map<String, Object>> resultList = new ArrayList<>();
        ArrayList<Future<List<Map<String, Object>>>> futures = new ArrayList<>();
        List<String> from2MonthList = WDatesUtils.getFrom2Month(startMonth, endMonth);
        from2MonthList.forEach(yearMonth -> {
            futures.add(customerExecutorService.submit(new TaskSplitIoCallable(vendorNbrList, yearMonth)));
        });
        for (Future<List<Map<String, Object>>> future : futures) {
            List<Map<String, Object>> list = future.get();
            if (list == null || list.isEmpty()) {
                continue;
            }
            list.forEach(item -> {
                item.put("r_id", uuid);
                item.put("start_month", startMonth);
                item.put("end_month", endMonth);
            });
            resultList.addAll(list);
        }
        LOGGER.info(".saveSpliteIo 调用oi拆分入参信息:{}, uuid:{}，出参信息:{}", paramJson, uuid, resultList);
        baseCommonDAO.executeSqlUpdate("delete from tta_sale_oi_mid_record t where t.r_id='" + uuid + "'");
        baseCommonDAO.executeSqlUpdate("delete from tta_sale_oi_mid_rate_record t where t.r_id='" + uuid + "'");
        baseCommonDAO.saveBatchJDBC("tta_sale_oi_mid_record", resultList);
        String querySql = TtaSupplierItemMidEntity_HI_RO.getQuerySql(uuid);//拆分多个供应商占原供应商比值
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("rId", uuid);
        List<Map<String, Object>> queryList = baseCommonDAO.queryNamedSQLForList(querySql, queryMap);
        baseCommonDAO.saveBatchJDBC("tta_sale_io_mid_rate_record", queryList);
        //


        return queryList;
    }

    /**
     * 检查符合条件的拆分明细数据
     *
     * @param jsonObject
     * @param userId
     * @return
     */
    @Override
    public JSONObject checkSplitConditionDetail(JSONObject jsonObject, int userId) {
        JSONObject headerInfo = jsonObject.getJSONObject("headerInfo");
        Integer supplierItemId = headerInfo.getInteger("supplierItemId");
        Assert.notNull(supplierItemId, "Proposal拆分与合并头信息未保存,请先保存,再保存拆分条件明细数据!");
        String splitCondition = headerInfo.getString("splitCondition");
        String proposalCode = headerInfo.getString("proposalCode");//proposal单据号
        String versionCode = headerInfo.getString("versionCode");
        JSONArray splitData = jsonObject.getJSONArray("saveAll");
        JSONObject result = new JSONObject();

        //查询proposal信息
        TtaProposalHeaderEntity_HI_RO hiRo = checkProposal(proposalCode,versionCode);
        //查询品牌明细数据
        List<TtaBrandplnLEntity_HI_RO> dataByProposalSplit = findBrandplnDataByProposalSplit(hiRo.getProposalId());
        if (null == dataByProposalSplit || dataByProposalSplit.isEmpty()) {
            throw new IllegalArgumentException(hiRo.getProposalYear() + "年度的" + "Proposal单号【" + proposalCode + "】不存在品牌计划明细数据,保存失败,请检查!");
        }
        if ("Group+Dept+Brand+Item".equals(splitCondition)) {
            result = checkTotalFcsPurchaseAndFcsSales(proposalCode, splitCondition, splitData, dataByProposalSplit);
        }
        return result;
        /*else {
            //检查其他分组维度的数据
            result = checkOtherGroupDemins(proposalCode, splitCondition, splitData, dataByProposalSplit);
        }*/
    }

    /**
     * @date 2019.12.24
     * 保存符合拆分明细数据
     */
    @Override
    public void saveSplitConditionDetail(JSONObject jsonObject, int userId) throws Exception {
        JSONObject headerInfo = jsonObject.getJSONObject("headerInfo");
        Integer supplierItemId = headerInfo.getInteger("supplierItemId");
        Assert.notNull(supplierItemId, "Proposal拆分与合并头信息未保存,请先保存,再保存拆分条件明细数据!");
        JSONArray splitData = jsonObject.getJSONArray("saveAll");
        List<TtaSupplierItemMidEntity_HI> itemMidEntity_his = new ArrayList<>();
        for (int i = 0; i < splitData.size(); i++) {
            TtaSupplierItemMidEntity_HI entity = SaafToolUtils.setEntity(TtaSupplierItemMidEntity_HI.class, splitData.getJSONObject(i), ttaSupplierItemMidDAO_HI, userId);
            if (entity.getFcsPurchase() == null) {
                entity.setFcsPurchase(new BigDecimal("0"));
            }
            if (entity.getFcsSales() == null) {
                entity.setFcsSales(new BigDecimal("0"));
            }
            if (entity.getGpAmt() == null) {
                entity.setGpAmt(BigDecimal.ZERO);
            }
            entity.setLastUpdateDate(new Date());
            entity.setLastUpdatedBy(userId);
            entity.setLastUpdateLogin(userId);
            entity.setOperatorUserId(userId);
            itemMidEntity_his.add(entity);
        }
        ttaSupplierItemMidDAO_HI.saveOrUpdateAll(itemMidEntity_his);
    }

    /**
     * 检查其他分组维度的数据
     *
     * @param proposalCode
     * @param splitCondition
     * @param splitData
     * @return
     */
    public JSONObject checkOtherGroupDemins(String proposalCode, String splitCondition, JSONArray splitData, List<TtaBrandplnLEntity_HI_RO> dataByProposalSplit) {
        JSONObject result = new JSONObject();
        result.put("method", "2");
        //查询proposal信息和品牌计划明细数据
        Set<String> set = new HashSet<>();
        Set<String> set1 = new HashSet<>();

        for (int j = 0; j < splitData.size(); j++) {
            JSONObject jsonObject = splitData.getJSONObject(j);
            TtaSupplierItemMidEntity_HI midEntityHi = JSONObject.parseObject(JSON.toJSONString(jsonObject), TtaSupplierItemMidEntity_HI.class);
            String groupName = midEntityHi.getGroupName();
            String deptCode = midEntityHi.getDeptCode();
            String brandName = midEntityHi.getBrandName();
            String itemNbr = midEntityHi.getItemCode();
            StringBuffer sb = new StringBuffer();
            if ("Group".equals(splitCondition)) {
                sb.append(groupName);
            } else if ("Group+Dept".equals(splitCondition)) {
                sb.append(groupName).append("-").append(deptCode);
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                sb.append(groupName).append("-").append(deptCode).append("-").append(brandName);
            }
            set.add(sb.toString());
        }

        for (TtaBrandplnLEntity_HI_RO entityHiRo : dataByProposalSplit) {
            String groupName = entityHiRo.getGroupDesc();
            String deptCode = entityHiRo.getDeptCode();
            String brandName = entityHiRo.getBrandCn();
            StringBuffer sbf = new StringBuffer();
            if ("Group".equals(splitCondition)) {
                sbf.append(groupName);
            } else if ("Group+Dept".equals(splitCondition)) {
                sbf.append(groupName).append("-").append(deptCode);
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                sbf.append(groupName).append("-").append(deptCode).append("-").append(brandName);
            }
            set1.add(sbf.toString());
        }
        //取差值(取出的是符合条件明细数据不在品牌明细中的数据)
        //Collection subtract = CollectionUtils.subtract(set, set1);
        //取差值(取出的是品牌计划明细不在符合条件中的数据)
        //Collection subtract1 = CollectionUtils.subtract(set1, set);
        return result;
    }

    /**
     * 校验拆分前金额和拆分后的金额是否相等,如不等,抛异常,针对group desc brand item 分组维度
     *
     * @param proposalCode proposal单号
     * @param splitData    拆分条件明细数据
     */
    public JSONObject checkTotalFcsPurchaseAndFcsSales(String proposalCode, String splitCondition, JSONArray splitData, List<TtaBrandplnLEntity_HI_RO> dataByProposalSplit) {
        Map<String, Map<String, Object>> sumMap = this.sumTotalBySplitCondition(splitCondition, splitData);
        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("method", "1");
        //校验F'cs purchase 和 F'cs sales金额
        for (TtaBrandplnLEntity_HI_RO hi_ro : dataByProposalSplit) {
            StringBuffer sbf = new StringBuffer();
            //拼接group dept brand等字符串
            String dimensionStr = sbf.append(hi_ro.getGroupDesc()).append("-").append(hi_ro.getDeptCode()).append("-").append(hi_ro.getBrandCn()).append("-").append(hi_ro.getBrandEn()).toString();
            //品牌计划明细的Fcs金额,拆前金额
            BigDecimal fcsPurchase = hi_ro.getFcsPurchase() == null ? BigDecimal.ZERO : hi_ro.getFcsPurchase();
            BigDecimal fcsSales = hi_ro.getFcsSales() == null ? BigDecimal.ZERO : hi_ro.getFcsSales();
            if (sumMap.containsKey(dimensionStr)) {
                Map<String, Object> objectMap = sumMap.get(dimensionStr);
                //拆后金额
                BigDecimal totalFcsPurchase = (BigDecimal) objectMap.get("totalFcsPurchase");
                BigDecimal totalFcsSales = (BigDecimal) objectMap.get("totalFcsSales");
                boolean isCheckBrandPl = (boolean) objectMap.get("isCheckBrandPl");
                if (isCheckBrandPl) {//需要检查
                    //检查品牌计划明细数据,比较拆前和拆后金额大小
                    if (totalFcsPurchase.compareTo(fcsPurchase) != 0) {
                        Assert.isTrue(false, "请检查Proposal:" + proposalCode + "的品牌计划同一纬度【" + dimensionStr + "】的F’cs Purchase值:" + fcsPurchase + "和当前填写的F’cs Purchase值:" +
                                totalFcsPurchase + "是否相同");
                    }
                    if (totalFcsSales.compareTo(fcsSales) != 0) {
                        Assert.isTrue(false, "请检查Proposal:" + proposalCode + "的请检查品牌计划同一纬度【" + dimensionStr + "】的F’cs Sales值:" + fcsSales + "和当前填写的F’cs Sales:" +
                                totalFcsSales + "是否相同");
                    }
                }
            }
        }
        return jsonObject;
    }

    /**
     * 根据不同的条件汇总同一份组维度的 FcsPurchase FcsSales金额
     *
     * @param splitCondition
     * @param splitData
     * @return
     */
    public Map<String, Map<String, Object>> sumTotalBySplitCondition(String splitCondition, JSONArray splitData) {
        //统计同一分组维度的数据
        Map<String, Object> totalRowMap = null;
        Map<String, Map<String, Object>> sumMap = new HashMap<>();

        //拆分条件明细数据
        for (int i = 0; i < splitData.size(); i++) {
            JSONObject jsonObject = splitData.getJSONObject(i);
            StringBuffer sb = new StringBuffer();
            String groupCode = jsonObject.getString("groupCode");
            String groupName = jsonObject.getString("groupName");
            String deptCode = jsonObject.getString("deptCode");
            String brandName = jsonObject.getString("brandName");
            String brandNameEn = jsonObject.getString("brandNameEn");
            String itemCode = jsonObject.getString("itemCode");
            BigDecimal fcsPurchase = jsonObject.getBigDecimal("fcsPurchase") == null ? new BigDecimal("0") : jsonObject.getBigDecimal("fcsPurchase");
            BigDecimal fcsSales = jsonObject.getBigDecimal("fcsSales") == null ? new BigDecimal("0") : jsonObject.getBigDecimal("fcsSales");
            String supplierCode = jsonObject.getString("supplierCode");
            String splitSupplierCode = jsonObject.getString("splitSupplierCode");
            //拼接分组维度,作为唯一
            String str = sb.append(groupName).append("-").append(deptCode).append("-").append(brandName).append("-").append(brandNameEn).toString();
            if ("Group+Dept+Brand+Item".equals(splitCondition)) {
                if (sumMap.containsKey(str)) {
                    Map<String, Object> objectMap = sumMap.get(str);
                    BigDecimal totalFcsPurchase = (BigDecimal) objectMap.get("totalFcsPurchase");
                    BigDecimal totalFcsSales = (BigDecimal) objectMap.get("totalFcsSales");
                    objectMap.put("totalFcsPurchase", totalFcsPurchase.add(fcsPurchase));
                    objectMap.put("totalFcsSales", totalFcsSales.add(fcsSales));
                    setIsCheckBrandPl(supplierCode, splitSupplierCode, objectMap);
                } else {
                    totalRowMap = new HashMap<>();
                    totalRowMap.put("totalFcsPurchase", fcsPurchase);
                    totalRowMap.put("totalFcsSales", fcsSales);
                    totalRowMap.put("isCheckBrandPl", false);//默认不检查
                    setIsCheckBrandPl(supplierCode, splitSupplierCode, totalRowMap);
                    sumMap.put(str, totalRowMap);
                }
            }
        }
        return sumMap;
    }

    /**
     * 逻辑:
     * 1.指定供应商为空或者指定供应商和原供应商相同,不需要检查拆分明细和品牌计划明细的同一纬度的信息
     * 2.指定供应商不为空并且指定供应商和原供应商不相同,那么就需要检查数据
     *
     * @param supplierCode      原供应商编号
     * @param splitSupplierCode 指定供应商编号
     * @param objectMap
     */
    public void setIsCheckBrandPl(String supplierCode, String splitSupplierCode, Map<String, Object> objectMap) {
        if (!((boolean) objectMap.get("isCheckBrandPl")) && (StringUtils.isBlank(splitSupplierCode) || splitSupplierCode.equals(supplierCode))) {
            objectMap.put("isCheckBrandPl", false);//不检查
        } else if (StringUtils.isNotBlank(splitSupplierCode) && !splitSupplierCode.equals(supplierCode)) {
            objectMap.put("isCheckBrandPl", true);//检查
        }
    }

    //查询proposal信息
    public TtaProposalHeaderEntity_HI_RO checkProposal(String proposalCode,String versionCode) {
        TtaProposalHeaderEntity_HI_RO proposalHearder = this.getProposalHearder(proposalCode,new BigDecimal(versionCode));
        Assert.notNull(proposalHearder, "TTA系统中,不存在Proposal单据:" + proposalCode + ",请联系管理员!");
        return proposalHearder;
    }

    /**
     * 获取Proposal信息
     *
     * @param proposalCode Proposal单据号
     * @return
     */
    public TtaProposalHeaderEntity_HI_RO getProposalHearder(String proposalCode,BigDecimal versionCode) {
        if (StringUtils.isBlank(proposalCode)) {
            return null;
        }
        return ttaProposalHeaderServer.getProposalHearder(proposalCode,versionCode);
    }

    /**
     * 获取品牌明细数据
     *
     * @param proposalId
     * @return
     */
    public List<TtaBrandplnLEntity_HI_RO> findBrandplnDataByProposalSplit(Integer proposalId) {
        if (null == proposalId) {
            return null;
        }
        return ttaBrandplnLServer.findBrandplnDataByProposalSplit(proposalId);
    }

    /**
     * 保存和检查拆分后的品牌明细数据(对选择的Proposal单据进行保存)
     *
     * @param paramsJSON
     * @date 2019.12.26
     */
    public void saveAndCheckBrandSplit(JSONObject paramsJSON, int userId) throws Exception {
        JSONObject supplierItemHeaderInfo = paramsJSON.getJSONObject("supplierItemHeaderInfo");
        Integer supplierItemId = supplierItemHeaderInfo.getInteger("supplierItemId");
        String supplierCode = supplierItemHeaderInfo.getString("supplierCode");
        String proposalCode = supplierItemHeaderInfo.getString("proposalCode");
        String versionCode = supplierItemHeaderInfo.getString("versionCode");
        String splitCondition = supplierItemHeaderInfo.getString("splitCondition");
        //JSONArray jsonArray = paramsJSON.getJSONArray("list");
        //所有的符合拆分条件明细数据
        List<TtaSupplierItemMidEntity_HI> list = ttaSupplierItemMidDAO_HI.findByProperty(new JSONObject().fluentPut("supplierItemHId", supplierItemId));
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(list));

        TtaProposalHeaderEntity_HI_RO hiRo = checkProposal(proposalCode,versionCode);
        //查询品牌明细数据,汇总金额,Proposal单号,ProposalId
        JSONObject jsonObject = ttaBrandplnLServer.findBrandTotalAndProposalCode(hiRo);
        Map<String, Object> brandHeader = (Map<String, Object>) jsonObject.get("brandHeader");
        List<TtaBrandplnLEntity_HI_RO> brandLine = (List<TtaBrandplnLEntity_HI_RO>) jsonObject.get("brandLine");
        Assert.notNull(brandHeader, "Proposal单据[#]的品牌明细数据不存在,请检查!".replace("#", hiRo.getOrderNbr()));
        Assert.notNull(brandLine, "Proposal单据[#]的品牌明细数据不存在,请检查!".replace("#", hiRo.getOrderNbr()));
        // <1.品牌计划明细Fcs_Purchase总金额
        // <2.品牌计划明细Fcs Sales 总金额
        BigDecimal brandFcsPurchase = (BigDecimal) brandHeader.get("FCS_PURCHASE");
        BigDecimal brandFcsSales = (BigDecimal) brandHeader.get("FCS_SALES");
        LOGGER.info("Proposal单号:{},品牌明细数据汇总Fcs Purchase金额:{},Fcs Sales金额:{}", proposalCode, brandFcsPurchase, brandFcsSales);
        ttaSplitBrandDetailDAO_HI.executeSqlUpdate("delete from tta_split_brand_detail tsbd where tsbd.supplier_item_id =" + supplierItemId);

        //拆分条件为:Group+Dept+Brand+Item
        List<TtaSplitBrandDetailEntity_HI> brandDetailEntity_his = new ArrayList<>();
        if ("Group+Dept+Brand+Item".equals(splitCondition)) {
            //得到拆分条件明细数据中同一分组的fcspurchase,fcsSales金额
            Map<String, Map<String, Object>> sumMap = sumTotalBySplitCondition(splitCondition, jsonArray);
            if (CollectionUtils.isNotEmpty(brandLine)) {
                for (TtaBrandplnLEntity_HI_RO brandplnLEntityHiRo : brandLine) {
                    String dimensionStr = new StringBuffer().append(brandplnLEntityHiRo.getGroupDesc()).append("-").
                            append(brandplnLEntityHiRo.getDeptCode()).append("-").append(brandplnLEntityHiRo.getBrandCn()).append("-").append(brandplnLEntityHiRo.getBrandEn()).toString();
                    BigDecimal fcsPurchase = brandplnLEntityHiRo.getFcsPurchase() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsPurchase();
                    BigDecimal fcsSales = brandplnLEntityHiRo.getFcsSales() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsSales();
                    boolean isFristInto = true;
                    //如果品牌计划明细中的分组维度不在符合拆分条件明细中,那么就认为没有被拆分,并且指定的供应商还是原来的供应商;
                    // 否则认为被拆分,指定的供应商为填写的供应商
                    if (sumMap.containsKey(dimensionStr)) {
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            StringBuffer stringBuffer = new StringBuffer();
                            String groupName = object.getString("groupName");
                            String deptCode = object.getString("deptCode");
                            String brandName = object.getString("brandName");
                            String brandNameEn = object.getString("brandNameEn");
                            String str = stringBuffer.append(groupName).append("-").append(deptCode).append("-").append(brandName).append("-").append(brandNameEn).toString();
                            Map<String, Object> objectMap = sumMap.get(str);
                            BigDecimal totalFcsPurchase = (BigDecimal) objectMap.get("totalFcsPurchase");
                            BigDecimal totalFcsSales = (BigDecimal) objectMap.get("totalFcsSales");
                            boolean isCheckBrandPl = (boolean) objectMap.get("isCheckBrandPl");
                            //如果原供应商等于指定供应商或者指定供应商为空,不需要检查金额
                            if (dimensionStr.equals(str) && !isCheckBrandPl && isFristInto) {
                                isFristInto = false;
                                //保存品牌计划相关的数据 0:不拆分,1:拆分
                                setObjectAttribute(object, brandFcsPurchase, brandFcsSales, "0", hiRo, brandplnLEntityHiRo, brandDetailEntity_his, supplierItemId, splitCondition, userId);
                                break;
                            } else if (dimensionStr.equals(str) && isCheckBrandPl) {
                                //检查品牌计划明细数据,比较拆前和拆后金额大小
                                if (totalFcsPurchase.compareTo(fcsPurchase) != 0) {
                                    Assert.isTrue(false, "请检查拆分明细数据和Proposal单号:" + proposalCode + "的品牌计划明细数据,分组维度为【" + str + "】的" +
                                            "品牌计划F’cs Purchase值:" + fcsPurchase + ",拆分明细数据的F’cs Purchase值为:" + totalFcsPurchase + ",是否相同");
                                }
                                if (totalFcsSales.compareTo(fcsSales) != 0) {
                                    Assert.isTrue(false, "请检查拆分明细数据和Proposal单号:" + proposalCode + "的品牌计划明细数据,分组维度为【" + str + "】的" +
                                            "品牌计划F’cs Sales值:" + fcsSales + ",拆分明细数据的F’cs Sales值为:" + totalFcsSales + ",是否相同");
                                }
                                setObjectAttribute(object, brandFcsPurchase, brandFcsSales, "1", hiRo, brandplnLEntityHiRo, brandDetailEntity_his, supplierItemId, splitCondition, userId);
                            }
                        }
                    } else {
                        //不包含在符合拆分明细表,保存到品牌拆分明细表
                        setObjectAttribute(null, brandFcsPurchase, brandFcsSales, "0", hiRo, brandplnLEntityHiRo, brandDetailEntity_his, supplierItemId, splitCondition, userId);
                    }
                }
            }
        } else {
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject midJsonObject = jsonArray.getJSONObject(j);
                TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(midJsonObject), TtaSupplierItemMidEntity_HI.class);
                String midGroupDeminStr = getGroupStr(midEntityHi, splitCondition);
                boolean isFrist = false;
                for (TtaBrandplnLEntity_HI_RO ttaBrandplnLEntityHiRo : brandLine) {
                    String brandGroupDemineStr = getGroupStr(ttaBrandplnLEntityHiRo, splitCondition);
                    //如果找到,就认为是拆分的,找不到,就不是拆分的,品牌计划明细数据中的指定供应商还是原来的供应商
                    if (midGroupDeminStr.equals(brandGroupDemineStr) && !isFrist) {
                        isFrist = true;
                        TtaSplitBrandDetailEntity_HI entityHi = new TtaSplitBrandDetailEntity_HI();
                        entityHi.setSupplierCode(midEntityHi.getSupplierCode());
                        entityHi.setSupplierName(midEntityHi.getSupplierName());
                        entityHi.setGroupCode(midEntityHi.getGroupCode());
                        entityHi.setGroupName(midEntityHi.getGroupName());
                        entityHi.setDeptCode(midEntityHi.getDeptCode());
                        entityHi.setDeptName(midEntityHi.getDeptName());
                        entityHi.setBrandCode(midEntityHi.getBrandCode());
                        entityHi.setBrandName(midEntityHi.getBrandName());
                        entityHi.setBrandNameEn(midEntityHi.getBrandNameEn());
                        entityHi.setItemNbr(midEntityHi.getItemCode());
                        entityHi.setItemName(midEntityHi.getItemName());
                        //查询拆分条件的品牌明细数据(汇总同一纬度的FCSPurchase,Fcs Sales金额)
                        Map<String, BigDecimal> bigDecimalMap = sumDifferenDemin(brandGroupDemineStr, brandLine, splitCondition);
                        entityHi.setFcsPurchase(bigDecimalMap.get("totalFcsPurchase"));
                        entityHi.setFcsSales(bigDecimalMap.get("totalFcsSales"));
                        BigDecimal divideFcsPurchase = entityHi.getFcsPurchase().divide(brandFcsPurchase, 4, BigDecimal.ROUND_HALF_UP);
                        BigDecimal divideFcsSales = entityHi.getFcsSales().divide(brandFcsSales, 4, BigDecimal.ROUND_HALF_UP);
                        entityHi.setAmoutRateFcspur(divideFcsPurchase);
                        entityHi.setAmoutRateFcssal(divideFcsSales);
                        entityHi.setTotalFcsPurchase(brandFcsPurchase);
                        entityHi.setTotalFcsSales(brandFcsSales);
                        String splitSupplierCode = StringUtils.isBlank(midEntityHi.getSplitSupplierCode()) ? midEntityHi.getSupplierCode() : midEntityHi.getSplitSupplierCode();
                        String splitSupplierName = StringUtils.isBlank(midEntityHi.getSplitSupplierName()) ? midEntityHi.getSupplierName() : midEntityHi.getSplitSupplierName();
                        entityHi.setSplitSupplierCode(splitSupplierCode);
                        entityHi.setSplitSupplierName(splitSupplierName);
                        //entityHi.setBrandplnLId();
                        entityHi.setProposalCode(hiRo.getOrderNbr());
                        entityHi.setProposalId(hiRo.getProposalId());
                        entityHi.setProposalYear(hiRo.getProposalYear());
                        entityHi.setMid(midEntityHi.getMid());
                        entityHi.setSupplierItemId(supplierItemId);
                        entityHi.setSplitCondition(splitCondition);
                        entityHi.setCreationDate(new Date());
                        entityHi.setCreatedBy(userId);
                        entityHi.setLastUpdateDate(new Date());
                        entityHi.setLastUpdatedBy(userId);
                        entityHi.setOperatorUserId(userId);
                        entityHi.setSplitStatus("1");//拆分
                        brandDetailEntity_his.add(entityHi);
                        break;
                    }
                }
            }
        }
        ttaSplitBrandDetailDAO_HI.saveOrUpdateAll(brandDetailEntity_his);
        //创建虚拟Proposal相关的信息
        saveVirtualProposalInfo(jsonArray, hiRo, jsonObject, splitCondition, userId, supplierItemId);
        //处理拆前供应商逻辑
        saveDealWithBeforeSplitSupplier(jsonArray, jsonObject, splitCondition, hiRo, supplierItemId, userId);
        //处理续签供应商逻辑
        saveDealRenewLastYearVendor(jsonArray, hiRo, jsonObject, splitCondition, userId, supplierItemId);
    }

    public void saveDealRenewLastYearVendor(JSONArray jsonArray, TtaProposalHeaderEntity_HI_RO hiRo, JSONObject jsonObject, String splitCondition, int userId, Integer supplierItemId) throws Exception {
        //拆前供应商品牌计划信息
        List<TtaBrandplnLEntity_HI_RO> brandLine = (List<TtaBrandplnLEntity_HI_RO>) jsonObject.get("brandLine");
        //查询指定供应商编号为续签的品牌计划数据
        ArrayList<String> vendorList = new ArrayList<>();
        HashMap<String,List<TtaBrandplnLEntity_HI_RO>> listMap = new HashMap<>();
        HashMap<String,Integer> proposalList = new HashMap<>();
        HashMap<String,Integer> brandHeaderIdList = new HashMap<>();
        for (int j = 0; j < jsonArray.size(); j++) {
            TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(jsonArray.getJSONObject(j)), TtaSupplierItemMidEntity_HI.class);
            if (StringUtils.isNotBlank(midEntityHi.getSplitSupplierCode()) && !vendorList.contains(midEntityHi.getSplitSupplierCode())
                    && !midEntityHi.getSupplierCode().equals(midEntityHi.getSplitSupplierCode()) && !midEntityHi.getSplitSupplierCode().contains("P")) {
                vendorList.add(midEntityHi.getSplitSupplierCode());
                Map<String,Object> queryParam = new HashMap<>();
                queryParam.put("vendorNbr",midEntityHi.getSplitSupplierCode());
                queryParam.put("proposalYear",hiRo.getProposalYear());
                List<TtaProposalHeaderEntity_HI> byProperty = ttaProposalHeaderDAO_HI.findByProperty(queryParam);
                if (CollectionUtils.isNotEmpty(byProperty)) {
                    Integer proposalId = byProperty.get(0).getProposalId();
                    //把原FcsPurchase,FcsSales,GP字段值更新到FcsSplitPurchase,FcsSplitSales,SplitGp字段中
                    int fcsCount = ttaBrandplnLDAO_HI.executeSqlUpdate(TtaBrandplnLEntity_HI_RO.getUpdateFcsAmt(proposalId));
                    LOGGER.info("续签供应商Proposal单号:{},ProposalId:{},更新FcsSplitPurchase,FcsSplitSales,SplitGp字段条数为:{}",
                            byProperty.get(0).getOrderNbr(),proposalId,fcsCount);

                    List<TtaBrandplnLEntity_HI_RO> data = ttaBrandplnLServer.findBrandplnDataByProposalSplit(proposalId);
                    listMap.put(midEntityHi.getSplitSupplierCode(),data);
                    proposalList.put(midEntityHi.getSplitSupplierCode(),proposalId);
                    List<TtaBrandplnHEntity_HI> entityList = ttaBrandplnHServer.findList(new JSONObject().fluentPut("proposalId", proposalId));
                    brandHeaderIdList.put(midEntityHi.getSplitSupplierCode(),entityList.get(0).getBrandplnHId());
                }
            }
        }
        List<TtaBrandplnLEntity_HI> entityHiList = new ArrayList<>();
        if ("Group+Dept+Brand+Item".equals(splitCondition)) {
            //汇总同一个品牌维度的拆分条件明细数据
            Map<String, Map<String, BigDecimal>> stringMapMap = countEqualBrandDeminats(jsonArray);
            for (Map.Entry<String, List<TtaBrandplnLEntity_HI_RO>> entry : listMap.entrySet()) {
                String splitSupplierKey = entry.getKey();
                Set<String> keys = stringMapMap.keySet();
                for (String key : keys) {
                    String[] split = key.split("-");
                    String splitSupplier = split[0];
                    String itemGroupStr = split[1] + "-" + split[2] + "-" + split[3] + "-" + split[4];//GROUP + DEPT + ITEM
                    if (splitSupplierKey.equals(splitSupplier)) {
                        //某个拆分供应商的FcsPurchase,FcsSales,FcsGp
                        Map<String, BigDecimal> bigDecimalMap = stringMapMap.get(key);
                        //某个拆分供应商的品牌明细数据
                        List<TtaBrandplnLEntity_HI_RO> brandListValue = entry.getValue();
                        for (TtaBrandplnLEntity_HI_RO entityHiRo : brandListValue) {
                            String groupStr = getGroupStr(entityHiRo, "Group+Dept+Brand");
                            BigDecimal brandFcsPurchase = BigDecimalUtils.dealNull(entityHiRo.getFcsPurchase());
                            BigDecimal brandFcsSales = BigDecimalUtils.dealNull(entityHiRo.getFcsSales());
                            BigDecimal gp = BigDecimalUtils.dealNull(entityHiRo.getGp());
                            BigDecimal brandGp$ = brandFcsSales.multiply(gp).divide(new BigDecimal("100"));//GP金额
                            if (Objects.equals(itemGroupStr,groupStr)) {//品牌维度都匹配上,累加
                                BigDecimal fcsPurchase = bigDecimalMap.get("fcsPurchase");
                                BigDecimal fcsSales = bigDecimalMap.get("fcsSales");
                                BigDecimal fcsGpAmt = bigDecimalMap.get("fcsGpAmt");
                                brandFcsPurchase = brandFcsPurchase.add(fcsPurchase);
                                brandFcsSales = brandFcsSales.add(fcsSales);
                                brandGp$ = brandGp$.add(fcsGpAmt);
                                //设置拆后相关数据
                                entityHiRo.setSplitGp(brandGp$.divide(brandFcsSales.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : brandFcsSales));
                                entityHiRo.setFcsSplitPurchse(brandFcsPurchase);
                                entityHiRo.setFcsSplitSales(brandFcsSales);
                                TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
                                BeanUtils.copyProperties(entityHi, entityHiRo);
                                entityHi.setIsSplit("Y");
                                entityHiList.add(entityHi);
                            }
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < jsonArray.size(); i++) {
                TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(jsonArray.getJSONObject(i)), TtaSupplierItemMidEntity_HI.class);
                if (StringUtils.isNotBlank(midEntityHi.getSplitSupplierCode())
                        && !midEntityHi.getSplitSupplierCode().contains("P")
                        && !midEntityHi.getSupplierCode().equals(midEntityHi.getSplitSupplierCode())) {
                    String groupStr = getGroupStr(midEntityHi, splitCondition);//分组维度
                    ArrayList<TtaBrandplnLEntity_HI_RO> brandplnList = new ArrayList<>();
                    for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
                        String brandGroupStr = getGroupStr(entityHiRo, splitCondition);
                        if (Objects.equals(groupStr,brandGroupStr)) {
                            brandplnList.add(entityHiRo);
                        }
                    }
                    if (brandplnList.size() > 0) {
                        if (!listMap.isEmpty()){
                            List<TtaBrandplnLEntity_HI_RO> hiRoList = listMap.get(midEntityHi.getSplitSupplierCode());
                            Integer proposalId = proposalList.get(midEntityHi.getSplitSupplierCode());
                            //品牌计划头信息
                            List<TtaBrandplnHEntity_HI> entityList = ttaBrandplnHServer.findList(new JSONObject().fluentPut("proposalId", proposalId));
                            //ttaBrandplnHDAO_HI.queryNamedSQLForList()
                            if (CollectionUtils.isNotEmpty(hiRoList)) {
                                //拆前供应商相关信息
                                for (TtaBrandplnLEntity_HI_RO entityHiRo : brandplnList) {
                                    String beforeGroup = getGroupStr(entityHiRo, "Group+Dept+Brand");
                                    boolean flag = false;
                                    //拆后供应商相关信息
                                    for (TtaBrandplnLEntity_HI_RO hEntityHi : hiRoList) {
                                        String targetGroup = getGroupStr(hEntityHi, "Group+Dept+Brand");
                                        //匹配上,做更新操作
                                        if (Objects.equals(beforeGroup,targetGroup)) {
                                            flag = true;
                                            TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
                                            BeanUtils.copyProperties(entityHi,hEntityHi);
                                            //拆后供应商fcsPurchase,fcsSales
                                            BigDecimal fcsPurchase = BigDecimalUtils.dealNull(entityHi.getFcsPurchase());
                                            BigDecimal fcsSales = BigDecimalUtils.dealNull(entityHi.getFcsSales());
                                            BigDecimal gp = BigDecimalUtils.dealNull(entityHi.getGp());
                                            BigDecimal gp$ = fcsSales.multiply(gp).divide(new BigDecimal("100"));
                                            //拆前供应商fcsPurchase,fcsSales
                                            BigDecimal beforeFcsPurchase = BigDecimalUtils.dealNull(entityHiRo.getFcsPurchase());
                                            BigDecimal beforeFcsSales = BigDecimalUtils.dealNull(entityHiRo.getFcsSales());
                                            BigDecimal beforeGp = BigDecimalUtils.dealNull(entityHiRo.getGp());
                                            BigDecimal beforeGp$ = beforeFcsSales.multiply(beforeGp).divide(new BigDecimal("100"));

                                            entityHi.setFcsSplitPurchse(fcsPurchase.add(beforeFcsPurchase));
                                            entityHi.setFcsSplitSales(fcsSales.add(beforeFcsSales));
                                            BigDecimal totalGp$ = gp$.add(beforeGp$);
                                            entityHi.setSplitGp(totalGp$.divide(entityHi.getFcsSplitSales(),4,BigDecimal.ROUND_HALF_UP));
                                            entityHiList.add(entityHi);
                                            break;
                                        }
                                    }
                                    //如果找不到,插入拆后的品牌计划行数据
                                    if (!flag){
                                        TtaBrandplnLEntity_HI entity = setBrandPlnAttributes(entityHiRo,entityList,proposalId);
                                        entityHiList.add(entity);
                                    }
                                }
                            } else {
                                for (TtaBrandplnLEntity_HI_RO entity_hi_ro : brandplnList) {
                                    TtaBrandplnLEntity_HI entityHi = setBrandPlnAttributes(entity_hi_ro, entityList, proposalId);
                                    entityHiList.add(entityHi);
                                }
                            }
                        }
                    }
                }
            }
        }
        String isSplit = "N";
        if (entityHiList.size() > 0) {
            ttaBrandplnLDAO_HI.saveOrUpdateAll(entityHiList);
            ttaBrandplnLDAO_HI.fluch();
            isSplit = "Y";
        }
        //更新续签供应商的品牌头信息
        if (brandHeaderIdList.size() > 0) {
            for (Map.Entry<String, Integer> entry : brandHeaderIdList.entrySet()) {
                String vendorKey = entry.getKey();
                Integer brandPlhId = entry.getValue();
                Integer proposalId = proposalList.get(vendorKey);
                //基于品牌明细数据更新品牌汇总数据
                String sql = TtaBrandplnHEntity_HI_RO.getUpdateFcsSplitSql(brandPlhId);
                int brandCout = ttaBrandplnHDAO_HI.executeSqlUpdate(sql);
                LOGGER.info("ProposalId:{},brandplnHId:{},更新拆后的品牌计划汇总数:{}",proposalId, brandPlhId , brandCout);
                ttaBrandplnLDAO_HI.executeSqlUpdate("update tta_brandpln_line tbl set tbl.is_split = '" + isSplit + "' where tbl.proposal_id = " + proposalId);
                ttaBrandplnHDAO_HI.executeSqlUpdate("update tta_brandpln_header tbh set tbh.is_split = '" + isSplit + "' where tbh.proposal_id = " + proposalId);
                //Proposal信息
                TtaProposalHeaderEntity_HI proposalH = ttaProposalHeaderDAO_HI.getById(proposalId);
                //List<TtaBrandplnHEntity_HI> byProperty = ttaBrandplnHDAO_HI.findByProperty(new JSONObject().fluentPut("brandplnHId", brandPlhId));
                List<Map<String, Object>> mapList = ttaBrandplnHDAO_HI.queryNamedSQLForList(TtaBrandplnHEntity_HI_RO.getBrandphSingal(brandPlhId),new HashMap<>());
                TtaBrandplnHEntity_HI byProperty = JSONObject.parseObject(JSON.toJSONString(mapList.get(0)), TtaBrandplnHEntity_HI.class);

                //操作一  更新TTA TERMS头信息
                List<TtaTermsHEntity_HI> beforeTtaTerms = ttaTermsHDAO_HI.findByProperty("proposalId", proposalId);
                TtaSplitBrandDetailEntity_HI_RO ttaSplitBrandDetail = ttaSplitBrandDetailServer.findSplitBrandByFcsPurchaseAndFcsSales(
                        String.valueOf(Integer.parseInt(proposalH.getProposalYear())), proposalH.getVendorNbr());
                if (!SaafToolUtils.isNullOrEmpty(ttaSplitBrandDetail) && !SaafToolUtils.isNullOrEmpty(ttaSplitBrandDetail.getSplitBrandId())) {
                    beforeTtaTerms.get(0).setIsSplit("1");//已拆分
                    beforeTtaTerms.get(0).setFcsSplitPurchse(BigDecimalUtils.dealNull(byProperty.getFcsPurchase()).add(BigDecimalUtils.dealNull(ttaSplitBrandDetail.getFcsSplitPurchase())));
                    beforeTtaTerms.get(0).setFcsSplitSales(BigDecimalUtils.dealNull(byProperty.getFcsSales()).add(BigDecimalUtils.dealNull(ttaSplitBrandDetail.getFcsSplitSales())));
                    beforeTtaTerms.get(0).setSplitGp(BigDecimalUtils.dealNull(byProperty.getSplitGp()));
                } else {
                    beforeTtaTerms.get(0).setIsSplit("0");
                    beforeTtaTerms.get(0).setFcsSplitPurchse(BigDecimalUtils.dealNull(beforeTtaTerms.get(0).getFcsPurchse()));
                    beforeTtaTerms.get(0).setFcsSplitSales(BigDecimalUtils.dealNull(beforeTtaTerms.get(0).getFcsSales()));
                    beforeTtaTerms.get(0).setSplitGp(BigDecimalUtils.dealNull(byProperty.getGp()));
                }
                ttaTermsHDAO_HI.saveOrUpdate(beforeTtaTerms.get(0));
                ttaTermsHDAO_HI.fluch();

                //操作二  更新TTA TERMS行信息
                int count = ttaTermsLDAO_HI.executeSqlUpdate(TtaTermsLEntity_HI_RO.updateOldTtaTermSplitFeeAmt(proposalH.getProposalId(), proposalH.getProposalYear(), "1", proposalH.getVendorNbr()));
                LOGGER.info("拆前供应商更新TTA TERM条数:{}", count);
                TtaTermsHEntity_HI termHI = ttaTermsHDAO_HI.getById(beforeTtaTerms.get(0).getTermsHId());
                termHI.setIsSplitFlag(1);
                ttaTermsHDAO_HI.saveOrUpdate(termHI);

                //操作三 更新(tta_contract_line)合同表的信息
                //1. 更新拆后的费用预估
                int updateContractCount = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.updateContractLineFeeAmt(proposalH.getProposalId(), proposalH.getProposalYear(), "1", proposalH.getVendorNbr(), "contract"));
                LOGGER.info("拆前供应商更新TTA_CONTRACT_LIN费用预估条数:{}", updateContractCount);
                //2. 更新拆后的purchase,sales
                int updateFcsCount = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.UpdateFcs(userId, proposalH.getProposalId(), beforeTtaTerms.get(0).getFcsSplitPurchse(), beforeTtaTerms.get(0).getFcsSplitSales()));
                LOGGER.info("拆前供应商更新TTA_CONTRACT_LIN预估purchase,sales条数:{}", updateFcsCount);
            }
        }

    }

    private TtaBrandplnLEntity_HI setBrandPlnAttributes(TtaBrandplnLEntity_HI_RO entityHiRo, List<TtaBrandplnHEntity_HI> entityList, Integer proposalId) throws Exception {
        TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
        SaafBeanUtils.copyUnionProperties(entityHiRo,entityHi);
        entityHi.setBrandDetails("New_Brand");
        entityHi.setPoRecord(BigDecimal.ZERO);
        entityHi.setSales(BigDecimal.ZERO);
        entityHi.setActualGp(BigDecimal.ZERO);
        entityHi.setVersionNum(0);
        entityHi.setBrandplnHId(entityList.get(0).getBrandplnHId());
        entityHi.setProposalId(proposalId);
        entityHi.setAnnualPurchase(BigDecimal.ZERO);
        entityHi.setAnnualSales(BigDecimal.ZERO);
        entityHi.setFcsSalesCon("");
        entityHi.setSalesGrowthCon("");
        entityHi.setFcsPurchaseCon("");
        entityHi.setPurchaseGrowthCon("");
        entityHi.setIsSplit("Y");
        entityHi.setFcsSplitPurchse(entityHi.getFcsPurchase());
        entityHi.setFcsSplitSales(entityHi.getFcsSales());
        entityHi.setSplitGp(entityHi.getGp());
        return entityHi;
    }

    /**
     * 功能:创建虚拟Proposal信息
     *
     * @param splitDataArray 符合条件拆分数据
     * @param hiRo           Proposal信息
     * @param brandData      品牌计划相关信息
     * @param splitCondition 拆分条件
     */
    @Override
    public void saveVirtualProposalInfo(JSONArray splitDataArray, TtaProposalHeaderEntity_HI_RO hiRo, JSONObject brandData, String splitCondition, int userId, Integer supplierItemId) throws Exception {
        JSONObject sumObject = null;
        if ("Group+Dept+Brand+Item".equals(splitCondition)) {
            sumObject = sumSupplierDimensions(splitDataArray);
        } else {
            sumObject = classSupplierMidData(splitDataArray);
        }

        //处理拆后供应商逻辑
        List<String> supplierList = new ArrayList<>();
        for (int i = 0; i < splitDataArray.size(); i++) {
            TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(splitDataArray.getJSONObject(i)), TtaSupplierItemMidEntity_HI.class);
            //指定供应商不为空并且是P开头的供应商
            if (StringUtils.isNotBlank(midEntityHi.getSplitSupplierCode()) && midEntityHi.getSplitSupplierCode().contains("P")
                    && !supplierList.contains(midEntityHi.getSplitSupplierCode())) {
                //先查询P开头的供应商是否已经生成了新的虚拟Proposal单,如生成,直接取查询之后的Proposal单据
                //List<TtaProposalHeaderEntity_HI> proposalList = ttaProposalHeaderDAO_HI.findByProperty(new JSONObject().fluentPut("vendorNbr", midEntityHi.getSplitSupplierCode()).fluentPut("proposalYear", hiRo.getProposalYear()));
                String hql = "from TtaProposalHeaderEntity_HI h where " +
                        "h.status not in('D') and " +
                        "h.proposalYear='" + hiRo.getProposalYear() + "' and " +
                        "h.vendorNbr = '" + midEntityHi.getSplitSupplierCode() + "'";
                List<TtaProposalHeaderEntity_HI> proposalList = ttaProposalHeaderDAO_HI.findList(hql);
                TtaProposalHeaderEntity_HI instance = null;
                if (CollectionUtils.isNotEmpty(proposalList)) {
                    instance = proposalList.get(0);
                } else {
                    //<1>插入Proposal信息
                    instance = this.saveVirtualProposal(midEntityHi.getSplitSupplierCode(), midEntityHi.getSplitSupplierName(), hiRo, userId);
                }
                if ("Group+Dept+Brand+Item".equals(splitCondition)) {
                    //<2>插入品牌计划相关的信息
                    TtaBrandplnHEntity_HI brandplnH = saveVirtualBrandpln(midEntityHi, instance, sumObject.get("create_brand_data"), brandData, userId);
                    //<3>插入TTA TERM相关数据
                    saveVirtualTtaTerm(midEntityHi, instance, hiRo, brandplnH, supplierItemId, userId);
                    //<4>拆入TTA_CONTRACT_LINE相关数据
                    saveVirtualContract(midEntityHi, instance, hiRo, userId);
                } else {
                    TtaBrandplnHEntity_HI brandplnH = saveBrandplnByOtherDimension(midEntityHi, instance, sumObject.get("class_supplier_data"), splitCondition, brandData, userId);
                    saveVirtualTtaTerm(midEntityHi, instance, hiRo, brandplnH, supplierItemId, userId);
                    saveVirtualContract(midEntityHi, instance, hiRo, userId);
                }
                supplierList.add(midEntityHi.getSplitSupplierCode());
            }
        }

    }

    @Override
    public TtaBrandplnHEntity_HI saveBrandplnByOtherDimension(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, Object class_supplier_data, String splitCondition, JSONObject brandData, int userId) throws Exception {
        Map<String, List<TtaSupplierItemMidEntity_HI>> classSupplierData = (Map<String, List<TtaSupplierItemMidEntity_HI>>) class_supplier_data;
        List<TtaSupplierItemMidEntity_HI> entityHiList = classSupplierData.get(midEntityHi.getSplitSupplierCode());
        //往年的Proposal的品牌信息
        List<TtaBrandplnLEntity_HI_RO> brandLine = (List<TtaBrandplnLEntity_HI_RO>) brandData.get("brandLine");
        //虚拟品牌计划头信息
        List<TtaBrandplnHEntity_HI> brandplnHeader = ttaBrandplnHDAO_HI.findByProperty("proposalId", instance.getProposalId());
        TtaBrandplnHEntity_HI brandplnH = null;
        if (CollectionUtils.isNotEmpty(brandplnHeader)) {
            brandplnH = brandplnHeader.get(0);
        } else {
            //插入品牌头信息
            brandplnH = getBrandplnH(instance, userId);
            ttaBrandplnHDAO_HI.saveOrUpdate(brandplnH);
            ttaBrandplnLDAO_HI.fluch();
        }
        //虚拟品牌计划行信息
        List<TtaBrandplnLEntity_HI> virtualBrandplnLine = ttaBrandplnLDAO_HI.findByProperty("proposalId", instance.getProposalId());
        if (CollectionUtils.isNotEmpty(entityHiList)) {
            for (TtaSupplierItemMidEntity_HI entityHi : entityHiList) {//拆分条件明细数据
                String midGroupDeminStr = getGroupStr(entityHi, splitCondition);
                for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
                    String brandGroupStr = getGroupStr(entityHiRo, splitCondition);
                    if (Objects.equals(midGroupDeminStr, brandGroupStr)) {//拆分条件明细数据维度和品牌计划行数据的维度一样
                        //如果虚拟品牌计划信息已存在就更新,不存在插入
                        if (CollectionUtils.isNotEmpty(virtualBrandplnLine)) {
                            StringBuffer sb = new StringBuffer();
                            String entityStr = sb.append(entityHiRo.getGroupCode()).append("-").append(entityHiRo.getDeptCode()).
                                    append("-").append(entityHiRo.getBrandCn()).append("-").append(entityHiRo.getBrandEn()).toString();
                            boolean flag = false;
                            for (TtaBrandplnLEntity_HI virtualBrandEntity : virtualBrandplnLine) {
                                StringBuffer existGroupSb = new StringBuffer();
                                String existGroupStr = existGroupSb.append(virtualBrandEntity.getGroupCode()).append("-").append(virtualBrandEntity.getDeptCode())
                                        .append("-").append(virtualBrandEntity.getBrandCn()).append("-").append(virtualBrandEntity.getBrandEn()).toString();
                                if (Objects.equals(entityStr, existGroupStr)) {//如果找到,就更新,否则新增
                                    flag = true;
                                    virtualBrandEntity.setFcsPurchase(BigDecimalUtils.dealNull(virtualBrandEntity.getFcsPurchase()).add(BigDecimalUtils.dealNull(entityHiRo.getFcsPurchase())));
                                    virtualBrandEntity.setFcsSales(BigDecimalUtils.dealNull(virtualBrandEntity.getFcsSales()).add(BigDecimalUtils.dealNull(entityHiRo.getFcsSales())));
                                    //*****已存在的虚拟品牌行相关金额(fcsPurchase,fcsSalse,fcsGP$)****
                                    BigDecimal fcsSales = BigDecimalUtils.dealNull(virtualBrandEntity.getFcsSales());
                                    BigDecimal gp = BigDecimalUtils.dealNull(virtualBrandEntity.getGp());
                                    BigDecimal gp$ = fcsSales.multiply(gp).divide(new BigDecimal("100"));//GP$ = Sales$ * GP%

                                    //*****未插入品牌行数据相关金额(fcsPurchase,fcsSalse,fcsGP$)****
                                    BigDecimal noExistFcsSales = BigDecimalUtils.dealNull(entityHiRo.getFcsSales());
                                    BigDecimal noExistFcsGp = BigDecimalUtils.dealNull(entityHiRo.getGp());
                                    BigDecimal noExistFcsGp$ = noExistFcsSales.multiply(noExistFcsGp).divide(new BigDecimal("100"));
                                    BigDecimal totalGP$ = gp$.add(noExistFcsGp$);
                                    virtualBrandEntity.setGp(totalGP$.divide(virtualBrandEntity.getFcsSales(), 4, BigDecimal.ROUND_HALF_UP));
                                    virtualBrandEntity.setSplitGp(virtualBrandEntity.getGp());
                                    virtualBrandEntity.setFcsSplitPurchse(virtualBrandEntity.getFcsPurchase());
                                    virtualBrandEntity.setFcsSplitSales(virtualBrandEntity.getFcsSales());
                                    ttaBrandplnLDAO_HI.saveOrUpdate(virtualBrandEntity);
                                }
                            }
                            if (!flag) {
                                //新增虚拟品牌计划信息
                                TtaBrandplnLEntity_HI brandplnLEntity_hi = setTtaBrandplnLEntityProperties(instance, entityHiRo, brandplnH, userId);
                                ttaBrandplnLDAO_HI.saveOrUpdate(brandplnLEntity_hi);
                            }
                        } else {
                            //新增虚拟品牌计划信息
                            TtaBrandplnLEntity_HI brandplnLEntity_hi = setTtaBrandplnLEntityProperties(instance, entityHiRo, brandplnH, userId);
                            ttaBrandplnLDAO_HI.saveOrUpdate(brandplnLEntity_hi);
                        }
                    }
                }
            }
        }
        ttaBrandplnLDAO_HI.fluch();

        //品牌明细数据插入之后更新品牌头信息
        TtaBrandplnHEntity_HI brandplnHId = ttaBrandplnHDAO_HI.getById(brandplnH.getBrandplnHId());
        LOGGER.info("新增的品牌虚拟单信息:\n" + JSON.toJSONString(brandplnHId));
        String sql = TtaBrandplnHEntity_HI_RO.getUpdateBrandplnH(brandplnH.getBrandplnHId());
        int i = ttaBrandplnHDAO_HI.executeSqlUpdate(sql);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("更新基于虚拟品牌行数据汇总虚拟品牌头条数:{}", i);
        }
        int i1 = ttaBrandplnHDAO_HI.executeSqlUpdate("update tta_brandpln_header tbh set tbh.split_gp = tbh.gp,tbh.fcs_split_purchse = tbh.fcs_purchase,tbh.fcs_split_sales = tbh.fcs_sales where tbh.brandpln_h_id = " + brandplnH.getBrandplnHId());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("更新SPLIT_GP字段值的条数:{}", i1);
        }
        //ttaBrandplnHDAO_HI.fluch();
        List<Map<String, Object>> mapList = ttaBrandplnHDAO_HI.queryNamedSQLForList(TtaBrandplnHEntity_HI_RO.getBrandphSingal(brandplnH.getBrandplnHId()), new HashMap<String, Object>());
        TtaBrandplnHEntity_HI brandplnHEntity_hi = JSONObject.parseObject(JSON.toJSONString(mapList.get(0)), TtaBrandplnHEntity_HI.class);
        return brandplnHEntity_hi;
    }

    private TtaBrandplnLEntity_HI setTtaBrandplnLEntityProperties(TtaProposalHeaderEntity_HI instance, TtaBrandplnLEntity_HI_RO entityHiRo, TtaBrandplnHEntity_HI brandplnH, int userId) throws Exception {
        TtaBrandplnLEntity_HI newEntity = new TtaBrandplnLEntity_HI();
        SaafBeanUtils.copyUnionProperties(entityHiRo, newEntity);
        newEntity.setProposalId(instance.getProposalId());
        newEntity.setBrandplnHId(brandplnH.getBrandplnHId());
        //设置实际费用为0
        newEntity.setPoRecord(BigDecimal.ZERO);
        newEntity.setSales(BigDecimal.ZERO);
        newEntity.setActualGp(BigDecimal.ZERO);
        newEntity.setAnnualPurchase(BigDecimal.ZERO);
        newEntity.setFcsSplitPurchse(newEntity.getFcsPurchase());
        newEntity.setFcsSplitSales(newEntity.getFcsSales());
        newEntity.setSplitGp(newEntity.getGp());
        newEntity.setIsSplit("Y");
        //newEntity.setPurchaseGrowth(BigDecimal.ZERO);
        //newEntity.setSalesGrowth(BigDecimal.ZERO);

        newEntity.setCreationDate(new Date());
        newEntity.setCreatedBy(userId);
        newEntity.setLastUpdateDate(new Date());
        newEntity.setLastUpdateLogin(userId);
        newEntity.setLastUpdatedBy(userId);
        newEntity.setOperatorUserId(userId);
        return newEntity;
    }

    /**
     * @param midEntityHi
     * @param instance    新建虚拟Proposal
     * @param hiRo        历史Proposal
     * @param userId
     */
    @Override
    public void saveVirtualContract(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, TtaProposalHeaderEntity_HI_RO hiRo, int userId) throws Exception {
        List<TtaTermsHEntity_HI> termHDomain = ttaTermsHDAO_HI.findByProperty("proposalId", instance.getProposalId());
        List<TtaContractLineEntity_HI> virtualContractLine = ttaContractLineDAO_HI.findByProperty("proposalId", instance.getProposalId());
        List<TtaContractLineEntity_HI> historyContractLine = ttaContractLineDAO_HI.findByProperty("proposalId", hiRo.getProposalId());
        if (CollectionUtils.isEmpty(virtualContractLine)) {
            virtualContractLine = new ArrayList<>();
            for (TtaContractLineEntity_HI contractLineEntityHi : historyContractLine) {
                TtaContractLineEntity_HI ttaContractLineEntity_hi = new TtaContractLineEntity_HI();
                SaafBeanUtils.copyUnionProperties(contractLineEntityHi, ttaContractLineEntity_hi);
                ttaContractLineEntity_hi.setVendorCode(instance.getVendorNbr());
                ttaContractLineEntity_hi.setVendorName(instance.getVendorName());
                ttaContractLineEntity_hi.setProposalId(instance.getProposalId());
                ttaContractLineEntity_hi.setSales(termHDomain.get(0).getFcsSales());
                ttaContractLineEntity_hi.setPurchase(termHDomain.get(0).getFcsPurchse());
                ttaContractLineEntity_hi.setSplitPurchase(termHDomain.get(0).getFcsSplitPurchse());
                ttaContractLineEntity_hi.setSplitSales(termHDomain.get(0).getFcsSplitSales());
                ttaContractLineEntity_hi.setFeeIntas(BigDecimal.ZERO);
                ttaContractLineEntity_hi.setFeeNotax(BigDecimal.ZERO);
                ttaContractLineEntity_hi.setIsSplitMoney("1");
                ttaContractLineEntity_hi.setCreatedBy(userId);
                ttaContractLineEntity_hi.setCreationDate(new Date());
                ttaContractLineEntity_hi.setLastUpdatedBy(userId);
                ttaContractLineEntity_hi.setLastUpdateDate(new Date());
                ttaContractLineEntity_hi.setLastUpdateLogin(userId);
                ttaContractLineEntity_hi.setOperatorUserId(userId);
                virtualContractLine.add(ttaContractLineEntity_hi);
            }
            ttaContractLineDAO_HI.saveOrUpdateAll(virtualContractLine);
            ttaContractLineDAO_HI.fluch();
            //更新新建虚拟的合同表预估费用
            int i = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.updateContractLineFeeAmt(instance.getProposalId(), instance.getProposalYear(), "1", instance.getVendorNbr(), "terms"));
            LOGGER.info("更新虚拟合同行数据更新条数:{}", i);
        } else {
            for (TtaContractLineEntity_HI entityHi : virtualContractLine) {
                entityHi.setSplitPurchase(termHDomain.get(0).getFcsSplitPurchse());
                entityHi.setSplitSales(termHDomain.get(0).getFcsSales());
                entityHi.setPurchase(entityHi.getSplitPurchase());
                entityHi.setSales(entityHi.getSplitSales());
                entityHi.setLastUpdatedBy(userId);
                entityHi.setLastUpdateDate(new Date());
                entityHi.setLastUpdateLogin(userId);
                entityHi.setOperatorUserId(userId);
                ttaContractLineDAO_HI.saveOrUpdate(entityHi);
                ttaContractLineDAO_HI.fluch();
            }
            int i = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.updateContractLineFeeAmt(instance.getProposalId(), instance.getProposalYear(), "1", instance.getVendorNbr(), "terms"));
            LOGGER.info("更新虚拟合同行数据更新条数:{}", i);
        }
    }

    /**
     * @param beforeProposal 拆前的Proposal
     */
    @Override
    public void saveDealWithBeforeSplitSupplier(JSONArray splitDataArray, JSONObject brandData, String splitCondition, TtaProposalHeaderEntity_HI_RO beforeProposal, Integer supplierItemId, int userId) throws Exception {
        //获取拆前供应商的品牌计划相关信息
        String vendorNbr = beforeProposal.getVendorNbr();//拆前供应商
        //List<TtaBrandplnLEntity_HI_RO> brandLine = (List<TtaBrandplnLEntity_HI_RO>) brandData.get("brandLine");
/*        for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
            TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
            BeanUtils.copyProperties(entityHi, entityHiRo);
            entityHi.setFcsSplitPurchse(entityHi.getFcsPurchase());
            entityHi.setFcsSplitSales(entityHi.getFcsSales());
            entityHi.setSplitGp(entityHi.getGp());
            ttaBrandplnLDAO_HI.saveOrUpdate(entityHi);
            ttaBrandplnLDAO_HI.fluch();
        }*/
        int fcsCount = ttaBrandplnLDAO_HI.executeSqlUpdate(TtaBrandplnLEntity_HI_RO.getUpdateFcsAmt(beforeProposal.getProposalId()));
        LOGGER.info("拆前供应商操作更新预估Purchase,Sales条数:{}",fcsCount);
        List<TtaBrandplnLEntity_HI_RO> brandLine = ttaBrandplnLServer.findBrandplnDataByProposalSplit(beforeProposal.getProposalId());

        //操作 0 更新TTA Brand信息
        List<TtaBrandplnLEntity_HI> brandplnLList = new ArrayList<>();
        //Group+Dept+Brand+Item
        if ("Group+Dept+Brand+Item".equals(splitCondition)) {
            //汇总相同品牌维度的用户填写的fcsSales,fcsPurchase
            Map<String, Map<String, BigDecimal>> stringMapMap = countEqualBrandDeminats(splitDataArray);
            for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
                String groupStr = getGroupStr(entityHiRo, "Group+Dept+Brand");
                BigDecimal brandFcsPurchase = BigDecimalUtils.dealNull(entityHiRo.getFcsPurchase());
                BigDecimal brandFcsSales = BigDecimalUtils.dealNull(entityHiRo.getFcsSales());
                BigDecimal gp = BigDecimalUtils.dealNull(entityHiRo.getGp());
                BigDecimal brandGp$ = brandFcsSales.multiply(gp).divide(new BigDecimal("100"));
                boolean flag = false;
                for (Map.Entry<String, Map<String, BigDecimal>> next : stringMapMap.entrySet()) {
                    String nextKey = next.getKey();
                    String[] split = nextKey.split("-");
                    //GROUP + DEPT + BRAND
                    String key = split[1] + "-" + split[2] + "-" + split[3] + "-" + split[4];
                    if (groupStr.equals(key)) {
                        flag = true;
                        Map<String, BigDecimal> value = next.getValue();
                        BigDecimal fcsPurchase = value.get("fcsPurchase");
                        BigDecimal fcsSales = value.get("fcsSales");
                        BigDecimal fcsGpAmt = value.get("fcsGpAmt");
                        brandFcsPurchase = brandFcsPurchase.subtract(fcsPurchase);
                        brandFcsSales = brandFcsSales.subtract(fcsSales);
                        brandGp$ = brandGp$.subtract(fcsGpAmt);
                    }
                }
                if (flag) {
                    //entityHiRo.setGp(brandGp$.divide(brandFcsSales.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : brandFcsSales));
                    entityHiRo.setSplitGp(brandGp$.divide(brandFcsSales.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : brandFcsSales));
                    entityHiRo.setFcsSplitPurchse(brandFcsPurchase);
                    entityHiRo.setFcsSplitSales(brandFcsSales);
                    TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
                    BeanUtils.copyProperties(entityHi, entityHiRo);
                    entityHi.setIsSplit("Y");
                    brandplnLList.add(entityHi);
                }
            }
        } else {
            for (int i = 0; i < splitDataArray.size(); i++) {
                JSONObject arrayJSONObject = splitDataArray.getJSONObject(i);
                TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(arrayJSONObject), TtaSupplierItemMidEntity_HI.class);
                //当前供应商指定给其他供应商
                if (StringUtils.isNotBlank(midEntityHi.getSplitSupplierCode()) && !vendorNbr.equals(midEntityHi.getSplitSupplierCode())) {
                    String splitGroupStr = getGroupStr(midEntityHi, splitCondition);
                    for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
                        String brandplnLGroupStr = getGroupStr(entityHiRo, splitCondition);
                        //Proposal拆分明细与Proposal brand分组维度一样
                        if (Objects.equals(splitGroupStr, brandplnLGroupStr)) {
                            TtaBrandplnLEntity_HI entityHi = new TtaBrandplnLEntity_HI();
                            BeanUtils.copyProperties(entityHi, entityHiRo);
                            entityHi.setIsSplit("Y");
                            entityHi.setFcsSplitPurchse(BigDecimal.ZERO);
                            entityHi.setFcsSplitSales(BigDecimal.ZERO);
                            entityHi.setSplitGp(BigDecimal.ZERO);
                            //entityHi.setGp(BigDecimal.ZERO);//未增加拆后GP%字段,使用原GP字段计算拆后的总GP%字段的值
                            entityHi.setSplitGp(BigDecimal.ZERO);
                            brandplnLList.add(entityHi);
                        }
                    }
                }
            }
        }
        String isSplit = "N";
        if (brandplnLList.size() > 0) {
            //更新品牌计划信息
            ttaBrandplnLDAO_HI.saveOrUpdateAll(brandplnLList);
            ttaBrandplnLDAO_HI.fluch();
            isSplit = "Y";
        }
        List<TtaBrandplnHEntity_HI> beforeBrandPlnH = ttaBrandplnHDAO_HI.findByProperty("proposalId", beforeProposal.getProposalId());
        //基于品牌明细数据更新品牌汇总数据
        String sql = TtaBrandplnHEntity_HI_RO.getUpdateFcsSplitSql(beforeBrandPlnH.get(0).getBrandplnHId());
        int brandCout = ttaBrandplnHDAO_HI.executeSqlUpdate(sql);
        LOGGER.info("更新拆后的品牌计划汇总数:{}", brandCout);

        ttaBrandplnLDAO_HI.executeSqlUpdate("update tta_brandpln_line tbl set tbl.is_split = '" + isSplit + "' where tbl.proposal_id = " + beforeProposal.getProposalId());
        ttaBrandplnHDAO_HI.executeSqlUpdate("update tta_brandpln_header tbh set tbh.is_split = '" + isSplit + "' where tbh.proposal_id = " + beforeProposal.getProposalId());
        List<TtaBrandplnHEntity_HI> byProperty = ttaBrandplnHDAO_HI.findByProperty(new JSONObject().fluentPut("brandplnHId", beforeBrandPlnH.get(0).getBrandplnHId()));

        //操作一  更新TTA TERMS头信息
        List<TtaTermsHEntity_HI> beforeTtaTerms = ttaTermsHDAO_HI.findByProperty("proposalId", beforeProposal.getProposalId());
        TtaSplitBrandDetailEntity_HI_RO ttaSplitBrandDetail = ttaSplitBrandDetailServer.findSplitBrandByFcsPurchaseAndFcsSales(String.valueOf(Integer.parseInt(beforeProposal.getProposalYear())), beforeProposal.getVendorNbr());
        if (!SaafToolUtils.isNullOrEmpty(ttaSplitBrandDetail) && !SaafToolUtils.isNullOrEmpty(ttaSplitBrandDetail.getSplitBrandId())) {
            beforeTtaTerms.get(0).setIsSplit("1");//已拆分
            //得到的数据是包含了当前供应商拆分给其他供应商和其他供应商拆分给当前供应商的数据
            beforeTtaTerms.get(0).setFcsSplitPurchse(beforeBrandPlnH.get(0).getFcsPurchase().add(ttaSplitBrandDetail.getFcsSplitPurchase()));
            beforeTtaTerms.get(0).setFcsSplitSales(beforeBrandPlnH.get(0).getFcsSales().add(ttaSplitBrandDetail.getFcsSplitSales()));
            beforeTtaTerms.get(0).setSplitGp(byProperty.get(0).getSplitGp());
        } else {
            beforeTtaTerms.get(0).setIsSplit("0");
            beforeTtaTerms.get(0).setFcsSplitPurchse(beforeTtaTerms.get(0).getFcsPurchse());
            beforeTtaTerms.get(0).setFcsSplitSales(beforeTtaTerms.get(0).getFcsSales());
            beforeTtaTerms.get(0).setSplitGp(byProperty.get(0).getGp());
        }
        ttaTermsHDAO_HI.saveOrUpdate(beforeTtaTerms.get(0));
        ttaTermsHDAO_HI.fluch();

        //操作二  更新TTA TERMS行信息
        int count = ttaTermsLDAO_HI.executeSqlUpdate(TtaTermsLEntity_HI_RO.updateOldTtaTermSplitFeeAmt(beforeProposal.getProposalId(), beforeProposal.getProposalYear(), "1", beforeProposal.getVendorNbr()));
        LOGGER.info("拆前供应商更新TTA TERM条数:{}", count);
        TtaTermsHEntity_HI termHI = ttaTermsHDAO_HI.getById(beforeTtaTerms.get(0).getTermsHId());
        termHI.setIsSplitFlag(1);
        ttaTermsHDAO_HI.saveOrUpdate(termHI);

        //操作三 更新(tta_contract_line)合同表的信息
        //1. 更新拆后的费用预估
        int updateContractCount = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.updateContractLineFeeAmt(beforeProposal.getProposalId(), beforeProposal.getProposalYear(), "1", beforeProposal.getVendorNbr(), "contract"));
        LOGGER.info("拆前供应商更新TTA_CONTRACT_LIN费用预估条数:{}", updateContractCount);
        //2. 更新拆后的purchase,sales
        int updateFcsCount = ttaContractLineDAO_HI.executeSqlUpdate(TtaContractLineHEntity_HI_RO.UpdateFcs(userId, beforeProposal.getProposalId(), beforeTtaTerms.get(0).getFcsSplitPurchse(), beforeTtaTerms.get(0).getFcsSplitSales()));
        LOGGER.info("拆前供应商更新TTA_CONTRACT_LIN预估purchase,sales条数:{}", updateFcsCount);
    }

    private Map<String, Map<String, BigDecimal>> countEqualBrandDeminats(JSONArray splitDataArray) {
        HashMap<String, Map<String, BigDecimal>> sumMap = new HashMap<>();
        for (int i = 0; i < splitDataArray.size(); i++) {
            TtaSupplierItemMidEntity_HI midEntityHi = JSONObject.parseObject(JSONObject.toJSONString(splitDataArray.getJSONObject(i)),
                    TtaSupplierItemMidEntity_HI.class);
            String supplierCode = midEntityHi.getSupplierCode();
            //拆后供应商不等于拆前供应商
            if (StringUtils.isNotBlank(midEntityHi.getSplitSupplierCode()) && !supplierCode.equals(midEntityHi.getSplitSupplierCode())) {
                String groupDeptBrandStr = midEntityHi.getSplitSupplierCode() + "-" + midEntityHi.getGroupName() + "-" +
                        midEntityHi.getDeptCode() + "-" + midEntityHi.getBrandName() + "-" + midEntityHi.getBrandNameEn();
                if (!sumMap.containsKey(groupDeptBrandStr)) {
                    Map<String, BigDecimal> bigDecimalMap = new HashMap<>();
                    bigDecimalMap.put("fcsPurchase", BigDecimalUtils.dealNull(midEntityHi.getFcsPurchase()));
                    bigDecimalMap.put("fcsSales", BigDecimalUtils.dealNull(midEntityHi.getFcsSales()));
                    bigDecimalMap.put("fcsGpAmt", BigDecimalUtils.dealNull(midEntityHi.getGpAmt()));
                    sumMap.put(groupDeptBrandStr, bigDecimalMap);
                } else {
                    Map<String, BigDecimal> bigDecimalMap = sumMap.get(groupDeptBrandStr);
                    bigDecimalMap.put("fcsPurchase", bigDecimalMap.get("fcsPurchase").add(BigDecimalUtils.dealNull(midEntityHi.getFcsPurchase())));
                    bigDecimalMap.put("fcsSales", bigDecimalMap.get("fcsSales").add(BigDecimalUtils.dealNull(midEntityHi.getFcsSales())));
                    bigDecimalMap.put("fcsGpAmt", bigDecimalMap.get("fcsGpAmt").add(BigDecimalUtils.dealNull(midEntityHi.getGpAmt())));
                    sumMap.put(groupDeptBrandStr, bigDecimalMap);
                }
            }
        }
        return sumMap;
    }

    private TtaSupplierEntity_HI_RO getSupplierInfo(TtaSupplierItemMidEntity_HI midEntityHi) {
        StringBuffer sb = new StringBuffer(TtaSupplierEntity_HI_RO.TTA_SUPPLIER_V);
        sb.append(" and v.supplier_code =:vendorNbr");
        return ttaSupplierDAO_HI_RO.get(sb, new JSONObject().fluentPut("vendorNbr", midEntityHi.getSplitSupplierCode()));
    }

    /**
     * 保存虚拟TTA TERMS的数据
     *
     * @param newProposalDomain
     * @param oldProposalDomain
     * @param supplierItemId
     * @param userId
     */
    @Override
    public void saveVirtualTtaTerm(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI newProposalDomain, TtaProposalHeaderEntity_HI_RO oldProposalDomain, TtaBrandplnHEntity_HI newBrandplnHEntity, Integer supplierItemId, int userId) throws Exception {
        //虚拟TTA TERMS
        List<TtaTermsHEntity_HI> newTtaTermHeaderList = ttaTermsHDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", newProposalDomain.getProposalId()));
        //被拆分的供应商的TTA TERMS
        List<TtaTermsHEntity_HI> oldTtaTermHeaderList = ttaTermsHDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", oldProposalDomain.getProposalId()));
        Assert.notNull(oldTtaTermHeaderList.get(0), "需要拆分的Proposal单据[#]上的TTA TERMS模块数据不存在,请检查!".replace("#", oldProposalDomain.getOrderNbr()));
        //TtaBrandplnHEntity_HI newBrandplnHEntity = ttaBrandplnHDAO_HI.getById(brandplnH.);
        TtaSupplierEntity_HI_RO supplierEntity = getSupplierInfo(midEntityHi);
        //插入TTA TERMS头信息
        TtaTermsHEntity_HI hEntityHi = null;
        //新增的虚拟TTA TERMS数据,增加拆分标识和拆前供应商
        if (CollectionUtils.isEmpty(newTtaTermHeaderList)) {
            hEntityHi = new TtaTermsHEntity_HI();
            SaafBeanUtils.copyUnionProperties(oldTtaTermHeaderList.get(0), hEntityHi);
            hEntityHi.setProposalId(newProposalDomain.getProposalId());
            hEntityHi.setVendorNbr(newProposalDomain.getVendorNbr());
            hEntityHi.setVendorDesc(newProposalDomain.getVendorName());
            hEntityHi.setSplitVendorNbr(oldProposalDomain.getVendorNbr());//拆分供应商:第一次新增的时候插入,后面不做更新
            hEntityHi.setDeptCode(supplierEntity.getOwnerGroup());
            hEntityHi.setDeptDesc(supplierEntity.getOwnerGroupName());
            hEntityHi.setFcsPurchse(newBrandplnHEntity.getFcsPurchase());
            hEntityHi.setFcsSales(newBrandplnHEntity.getFcsSales());
            hEntityHi.setGp(newBrandplnHEntity.getGp());
            hEntityHi.setFcsSplitPurchse(newBrandplnHEntity.getFcsPurchase());//拆后Purchase
            hEntityHi.setFcsSplitSales(newBrandplnHEntity.getFcsSales());//拆后sales
            hEntityHi.setSplitGp(newBrandplnHEntity.getSplitGp());//拆后GP%
            hEntityHi.setIsSplit("1");//是否拆分,1:是0:否
            hEntityHi.setCreatedBy(userId);
            hEntityHi.setCreationDate(new Date());
            hEntityHi.setLastUpdateDate(new Date());
            hEntityHi.setLastUpdatedBy(userId);
            hEntityHi.setLastUpdateLogin(userId);
            hEntityHi.setOperatorUserId(userId);
            hEntityHi.setVersionNum(0);
            hEntityHi.setRecordRunVendor(oldProposalDomain.getVendorNbr());
        } else {
            hEntityHi = newTtaTermHeaderList.get(0);
            hEntityHi.setFcsPurchse(newBrandplnHEntity.getFcsPurchase());
            hEntityHi.setFcsSales(newBrandplnHEntity.getFcsSales());
            hEntityHi.setGp(newBrandplnHEntity.getGp());
            hEntityHi.setFcsSplitPurchse(newBrandplnHEntity.getFcsPurchase());
            hEntityHi.setFcsSplitSales(newBrandplnHEntity.getFcsSales());
            hEntityHi.setSplitGp(newBrandplnHEntity.getSplitGp());
            //记录重组供应商
            hEntityHi.setRecordRunVendor(hEntityHi.getRecordRunVendor() + "," + oldProposalDomain.getVendorNbr());
            hEntityHi.setLastUpdatedBy(userId);
            hEntityHi.setLastUpdateLogin(userId);
            hEntityHi.setLastUpdateDate(new Date());
        }
        ttaTermsHDAO_HI.saveOrUpdate(hEntityHi);
        //插入虚拟TTA TERMS行信息
        List<TtaTermsLEntity_HI> virtualTtaTermLList = ttaTermsLDAO_HI.findByProperty("proposalId", newProposalDomain.getProposalId());
        //Proposal拆分与合并头信息上的往年Proposal
        List<TtaTermsLEntity_HI> oldTtaTermLList = ttaTermsLDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", oldProposalDomain.getProposalId()));

        if (CollectionUtils.isNotEmpty(virtualTtaTermLList)) {
            ttaTermsHDAO_HI.executeSqlUpdate(TtaTermsLEntity_HI_RO.updateOldTtaTermSplitFeeAmt(newProposalDomain.getProposalId(), newProposalDomain.getProposalYear(), "1", newProposalDomain.getVendorNbr()));
        } else {
            virtualTtaTermLList = new ArrayList<>();
            for (TtaTermsLEntity_HI entityHi : oldTtaTermLList) {
                TtaTermsLEntity_HI termLDomain = new TtaTermsLEntity_HI();
                SaafBeanUtils.copyUnionProperties(entityHi, termLDomain);
                termLDomain.setTermsHId(hEntityHi.getTermsHId());
                termLDomain.setFeeIntas(BigDecimal.ZERO);//费用预估含税
                termLDomain.setFeeNotax(BigDecimal.ZERO);//费用预估不含税
                termLDomain.setCreatedBy(userId);
                termLDomain.setCreationDate(new Date());
                termLDomain.setLastUpdatedBy(userId);
                termLDomain.setLastUpdateDate(new Date());
                termLDomain.setLastUpdateLogin(userId);
                termLDomain.setOperatorUserId(userId);
                termLDomain.setVersionNum(0);
                termLDomain.setProposalId(newProposalDomain.getProposalId());
                termLDomain.setFeeAcIntas(BigDecimal.ZERO);
                termLDomain.setFeeAcNotax(BigDecimal.ZERO);
                termLDomain.setFeeSplitIntas(BigDecimal.ZERO);
                termLDomain.setFeeSplitNotax(BigDecimal.ZERO);
                virtualTtaTermLList.add(termLDomain);
            }
            ttaTermsLDAO_HI.saveOrUpdateAll(virtualTtaTermLList);
            List<TtaTermsLEntity_HI> proposalIdSize = ttaTermsLDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", newProposalDomain.getProposalId()));
            LOGGER.info("生成的虚拟TTA TERMS行数量:{}", SaafToolUtils.isNullOrEmpty(proposalIdSize) ? 0 : proposalIdSize.size());
            //更新新建的虚拟TTA TERMS行数据
            int i = ttaTermsHDAO_HI.executeSqlUpdate(TtaTermsLEntity_HI_RO.updateOldTtaTermSplitFeeAmt(newProposalDomain.getProposalId(), newProposalDomain.getProposalYear(), "1", newProposalDomain.getVendorNbr()));
            LOGGER.info("更新虚拟TTA TERMS费用预估条数为:{}", i);
            TtaTermsHEntity_HI hdaoHiById = ttaTermsHDAO_HI.getById(hEntityHi.getTermsHId());
            hdaoHiById.setIsSplitFlag(1);//统计当前单据是否拆分过
            ttaTermsHDAO_HI.saveOrUpdate(hdaoHiById);
            int sqlUpdateCount = ttaTermsLDAO_HI.executeSqlUpdate("update tta_proposal_terms_line tptl set tptl.fee_intas = tptl.fee_split_intas,tptl.fee_notax = tptl.fee_split_notax where tptl.proposal_id =" + newProposalDomain.getProposalId());
            LOGGER.info("更新原费用预估条数:" + sqlUpdateCount);
        }
    }

    /**
     * 保存虚拟Proposal的品牌数据
     *
     * @param midEntityHi       Proposal拆分与合并的拆分条件明细数据
     * @param instance          新生成的Proposal虚拟单
     * @param create_brand_data
     * @param brandData
     * @param userId
     * @throws Exception
     */
    @Override
    public TtaBrandplnHEntity_HI saveVirtualBrandpln(TtaSupplierItemMidEntity_HI midEntityHi, TtaProposalHeaderEntity_HI instance, Object create_brand_data, JSONObject brandData, int userId) throws Exception {
        Map<String, Map<String, Map<String, Object>>> createBrandData = (Map<String, Map<String, Map<String, Object>>>) create_brand_data;
        List<TtaBrandplnLEntity_HI_RO> brandLine = (List<TtaBrandplnLEntity_HI_RO>) brandData.get("brandLine");
        Map<String, Map<String, Object>> stringMapMap = createBrandData.get(midEntityHi.getSplitSupplierCode());
        //if (stringMapMap == null) {
        //	throw new IllegalArgumentException("");
        //}
        //插入品牌计划相关信息之前先插入头信息,等品牌明细数据插入之后再更新品牌头信息
        List<TtaBrandplnHEntity_HI> brandHearList = ttaBrandplnHDAO_HI.findByProperty(new JSONObject().fluentPut("proposalId", instance.getProposalId()));
        TtaBrandplnHEntity_HI brandplnH = null;
        if (CollectionUtils.isNotEmpty(brandHearList)) {
            brandplnH = brandHearList.get(0);
        } else {
            brandplnH = getBrandplnH(instance, userId);
            ttaBrandplnHDAO_HI.saveOrUpdate(brandplnH);
        }
        List<TtaBrandplnLEntity_HI> existBrandplnList = ttaBrandplnLDAO_HI.findByProperty(new JSONObject().fluentPut("brandplnHId", brandplnH.getBrandplnHId()));
        for (TtaBrandplnLEntity_HI_RO brandplnLEntityHiRo : brandLine) {
            //GROUP_DEPT_BRAND
            String brandGroupStr = brandplnLEntityHiRo.getGroupDesc() + "-" + brandplnLEntityHiRo.getDeptCode() +
                    "-" + brandplnLEntityHiRo.getBrandCn() + "-" + brandplnLEntityHiRo.getBrandEn();
            for (Map.Entry<String, Map<String, Object>> mapEntry : stringMapMap.entrySet()) {
                String key = mapEntry.getKey();
                String[] groupSplit = key.split("-");
                String concatStr = groupSplit[1] + "-" + groupSplit[2] + "-" + groupSplit[3] + "-" + groupSplit[4];//group-dept-brand
                Map<String, Object> value = mapEntry.getValue();
                //品牌明细的品牌维度等于拆分明细的品牌维度
                if (brandGroupStr.equals(concatStr)) {
                    if (CollectionUtils.isNotEmpty(existBrandplnList)) {
                        boolean isExixs = false;
                        for (TtaBrandplnLEntity_HI existEntity : existBrandplnList) {
                            String existGroupStr = existEntity.getGroupDesc() + "-" + existEntity.getDeptCode() +
                                    "-" + existEntity.getBrandCn() + "-" +existEntity.getBrandEn() ;
                            if (existGroupStr.contains(brandGroupStr)) {
                                isExixs = true;
                                existEntity.setFcsPurchase(existEntity.getFcsPurchase().add((BigDecimal) value.get("groupfcsPurchase")));
                                existEntity.setFcsSales(existEntity.getFcsSales().add((BigDecimal) value.get("groupfcsSales")));
                                existEntity.setGp(existEntity.getGp().add((BigDecimal) value.get("groupGpAmt")));
                                ttaBrandplnLDAO_HI.saveOrUpdate(existEntity);
                            }
                        }
                        if (!isExixs) {
                            TtaBrandplnLEntity_HI brandplnLEntity = setTtaBrandplnLEntityProperties(instance, brandplnLEntityHiRo, brandplnH, value, userId);
                            ttaBrandplnLDAO_HI.saveOrUpdate(brandplnLEntity);
                        }
                    } else {
                        TtaBrandplnLEntity_HI brandplnLEntity = setTtaBrandplnLEntityProperties(instance, brandplnLEntityHiRo, brandplnH, value, userId);
                        ttaBrandplnLDAO_HI.saveOrUpdate(brandplnLEntity);
                    }
                }
            }
        }
        ttaBrandplnLDAO_HI.fluch();
        //品牌明细数据插入之后更新品牌头信息
        //ttaBrandplnHServer.brandplnHUpdate(new JSONObject().fluentPut("brandplnHId", brandplnH.getBrandplnHId()), userId);
        String sql = TtaBrandplnHEntity_HI_RO.getUpdateBrandplnH(brandplnH.getBrandplnHId());
        int i = ttaBrandplnHDAO_HI.executeSqlUpdate(sql);
        //ttaBrandplnHDAO_HI.fluch();
        List<Map<String, Object>> mapList = ttaBrandplnHDAO_HI.queryNamedSQLForList("SELECT * FROM tta_brandpln_header T WHERE Brandpln_H_Id=" + brandplnH.getBrandplnHId(), new HashMap<String, Object>());
        TtaBrandplnHEntity_HI brandplnHEntity_hi = JSONObject.parseObject(JSON.toJSONString(mapList.get(0)), TtaBrandplnHEntity_HI.class);
        //TtaBrandplnHEntity_HI hdaoHiById = ttaBrandplnHDAO_HI.getById(brandplnH.getBrandplnHId());
        return brandplnHEntity_hi;
    }

    private TtaBrandplnLEntity_HI setTtaBrandplnLEntityProperties(TtaProposalHeaderEntity_HI instance, TtaBrandplnLEntity_HI_RO brandplnLEntity, TtaBrandplnHEntity_HI brandplnH, Map<String, Object> value, int userId) throws Exception {
        TtaBrandplnLEntity_HI newEntity = new TtaBrandplnLEntity_HI();
        SaafBeanUtils.copyUnionProperties(brandplnLEntity, newEntity);
        newEntity.setProposalId(instance.getProposalId());
        newEntity.setBrandDetails("Existing_Brand");
        newEntity.setBrandplnHId(brandplnH.getBrandplnHId());
        newEntity.setPoRecord(BigDecimal.ZERO);
        newEntity.setSales(BigDecimal.ZERO);
        newEntity.setActualGp(BigDecimal.ZERO);
        newEntity.setAnnualPurchase(BigDecimal.ZERO);
        newEntity.setFcsPurchase((BigDecimal) value.get("groupfcsPurchase"));
        newEntity.setFcsSales((BigDecimal) value.get("groupfcsSales"));
        //GP值需要公式计算出来 4, BigDecimal.ROUND_HALF_UP
        BigDecimal gpPercent = ((BigDecimal) value.get("groupGpAmt")).divide(((BigDecimal) value.get("groupfcsSales")), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
        newEntity.setGp(gpPercent);
        newEntity.setPurchaseGrowth(BigDecimal.ZERO);
        newEntity.setSalesGrowth(BigDecimal.ZERO);

        newEntity.setCreationDate(new Date());
        newEntity.setCreatedBy(userId);
        newEntity.setLastUpdateDate(new Date());
        newEntity.setLastUpdateLogin(userId);
        newEntity.setLastUpdatedBy(userId);
        newEntity.setOperatorUserId(userId);
        return newEntity;
    }

    private TtaBrandplnHEntity_HI getBrandplnH(TtaProposalHeaderEntity_HI instance, int userId) {
        TtaBrandplnHEntity_HI brandplnHEntity_hi = new TtaBrandplnHEntity_HI();
        brandplnHEntity_hi.setProposalId(instance.getProposalId());
        brandplnHEntity_hi.setVendorNbr(instance.getVendorNbr());
        brandplnHEntity_hi.setVendorName(instance.getVendorName());
        brandplnHEntity_hi.setNewOrExisting(instance.getNewOrExisting());
        brandplnHEntity_hi.setYearCode(instance.getProposalYear());
        brandplnHEntity_hi.setIsSplit("Y");
        brandplnHEntity_hi.setCreatedBy(userId);
        brandplnHEntity_hi.setCreationDate(new Date());
        brandplnHEntity_hi.setLastUpdatedBy(userId);
        brandplnHEntity_hi.setLastUpdateDate(new Date());
        brandplnHEntity_hi.setLastUpdateLogin(userId);
        return brandplnHEntity_hi;
    }

    private JSONObject sumSupplierDimensions(JSONArray splitDataArray) {
        Map<String, Map<String, Map<String, Object>>> supplierMap = new HashMap<>();
        Map<String, Map<String, Object>> sumMap = null;
        Map<String, Object> countMap = null;
        Map<String, List<TtaSupplierItemMidEntity_HI>> newSupplierSplitDataMap = new HashMap<>();
        JSONObject returnObject = new JSONObject();
        for (int i = 0; i < splitDataArray.size(); i++) {
            JSONObject jsonObject = splitDataArray.getJSONObject(i);
            TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(jsonObject), TtaSupplierItemMidEntity_HI.class);
            if (midEntityHi.getSplitSupplierCode().contains("P")) {//指定的供应商为P开头的供应商
                String splitSupplierCode = midEntityHi.getSplitSupplierCode();
                StringBuffer sb = new StringBuffer();
                //供应商-group-dept-brand
                String string = sb.append(splitSupplierCode).append("-").append(midEntityHi.getGroupName()).append("-")
                        .append(midEntityHi.getDeptCode()).append("-").append(midEntityHi.getBrandName())
                        .append("-").append(midEntityHi.getBrandNameEn()).toString();
                BigDecimal fcsPurchase = BigDecimalUtils.dealNull(midEntityHi.getFcsPurchase());
                BigDecimal fcsSales = BigDecimalUtils.dealNull(midEntityHi.getFcsSales());
                BigDecimal gpAmt = BigDecimalUtils.dealNull(midEntityHi.getGpAmt());
                if (!supplierMap.containsKey(splitSupplierCode)) {
                    sumMap = new HashMap<>();
                    supplierMap.put(splitSupplierCode, sumMap);
                } else {
                    sumMap = supplierMap.get(splitSupplierCode);
                }
                if (!sumMap.containsKey(string)) {
                    countMap = new HashMap<>();
                    countMap.put("groupfcsPurchase", fcsPurchase);
                    countMap.put("groupfcsSales", fcsSales);
                    countMap.put("groupGpAmt", gpAmt);
                    sumMap.put(string, countMap);
                } else {
                    Map<String, Object> objectMap = sumMap.get(string);
                    BigDecimal groupfcsPurchase = BigDecimalUtils.dealNull((BigDecimal) objectMap.get("groupfcsPurchase"));
                    BigDecimal groupfcsSales = BigDecimalUtils.dealNull((BigDecimal) objectMap.get("groupfcsSales"));
                    BigDecimal groupGpAmt = BigDecimalUtils.dealNull((BigDecimal) objectMap.get("groupGpAmt"));
                    groupfcsPurchase = groupfcsPurchase.add(fcsPurchase);
                    groupfcsSales = groupfcsSales.add(fcsSales);
                    groupGpAmt = groupGpAmt.add(gpAmt);
                    objectMap.put("groupfcsPurchase", groupfcsPurchase);
                    objectMap.put("groupfcsSales", groupfcsSales);
                    objectMap.put("groupGpAmt", groupGpAmt);
                }
                if (!newSupplierSplitDataMap.containsKey(splitSupplierCode)) {
                    List<TtaSupplierItemMidEntity_HI> entityHiList = new ArrayList<>();
                    entityHiList.add(midEntityHi);
                    newSupplierSplitDataMap.put(splitSupplierCode, entityHiList);
                } else {
                    List<TtaSupplierItemMidEntity_HI> entityHiList = newSupplierSplitDataMap.get(splitSupplierCode);
                    entityHiList.add(midEntityHi);
                }
            }
        }
        returnObject.put("create_brand_data", supplierMap);
        returnObject.put("create_term_data", newSupplierSplitDataMap);
        return returnObject;
    }

    private JSONObject classSupplierMidData(JSONArray splitDataArray) {
        Map<String, List<TtaSupplierItemMidEntity_HI>> classData = new HashMap<>();
        for (int i = 0; i < splitDataArray.size(); i++) {
            JSONObject jsonObject = splitDataArray.getJSONObject(i);
            TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(jsonObject), TtaSupplierItemMidEntity_HI.class);
            String splitSupplierCode = midEntityHi.getSplitSupplierCode();
            if (splitSupplierCode.contains("P")) {
                if (!classData.containsKey(splitSupplierCode)) {
                    List<TtaSupplierItemMidEntity_HI> entityHiList = new ArrayList<>();
                    entityHiList.add(midEntityHi);
                    classData.put(splitSupplierCode, entityHiList);
                } else {
                    List<TtaSupplierItemMidEntity_HI> entityHiList = classData.get(splitSupplierCode);
                    entityHiList.add(midEntityHi);
                }
            }
        }
        JSONObject returnObject = new JSONObject();
        returnObject.put("class_supplier_data", classData);
        return returnObject;
    }

    @Override
    public TtaProposalHeaderEntity_HI saveVirtualProposal(String splitSupplierCode, String splitSupplierName, TtaProposalHeaderEntity_HI_RO proposalHeader, int userId) throws Exception {
        TtaProposalHeaderEntity_HI entity_hi = ttaProposalHeaderServer.getById(proposalHeader.getProposalId());
        TtaProposalHeaderEntity_HI newProposal = new TtaProposalHeaderEntity_HI();
        SaafBeanUtils.copyUnionProperties(entity_hi, newProposal);
        setEntityProperties(splitSupplierCode, splitSupplierName, newProposal, userId);
        ttaProposalHeaderDAO_HI.saveOrUpdate(newProposal);
        return newProposal;
    }

    private void setEntityProperties(String splitSupplierCode, String splitSupplierName, TtaProposalHeaderEntity_HI entityHi, int userId) {
        entityHi.setProposalId(null);
        entityHi.setOrderNbr(generateCodeServer.getGenerateCodeByHistory("", 4, true, true));
        entityHi.setCreatedBy(userId);
        entityHi.setCreationDate(new Date());
        entityHi.setLastUpdateLogin(userId);
        entityHi.setLastUpdatedBy(userId);
        entityHi.setLastUpdateDate(new Date());
        entityHi.setOperatorUserId(userId);
        entityHi.setVersionNum(1);
        entityHi.setLastYearProposalId(null);
        entityHi.setVendorNbr(splitSupplierCode);
        entityHi.setProposalNbr(entityHi.getProposalNbr() + "@" + "拆分");
        entityHi.setVendorName(splitSupplierName);
        entityHi.setVersionCode(new BigDecimal("1"));
        entityHi.setVersionStatus("1");//生效
        entityHi.setIsSplit("Y");//表示是从Proposal拆分与合并模块生成Proposal虚拟单
    }

    /**
     * 汇总不同纬度的Fcs Purchase Fcs Sales金额
     *
     * @param brandGroupDemineStr 拆分条件
     * @param brandLine           品牌明细数据
     * @param splitCondition      拆分条件
     */
    private Map<String, BigDecimal> sumDifferenDemin(String brandGroupDemineStr, List<TtaBrandplnLEntity_HI_RO> brandLine, String splitCondition) {
        Map<String, BigDecimal> sumMap = new HashMap<>();
        BigDecimal totalFcsPurchase = new BigDecimal("0");
        BigDecimal totalFcsSales = new BigDecimal("0");
        for (TtaBrandplnLEntity_HI_RO entityHiRo : brandLine) {
            StringBuffer sb = new StringBuffer();
            if ("Group".equals(splitCondition)) {
                sb.append(entityHiRo.getGroupDesc());
            } else if ("Group+Dept".equals(splitCondition)) {
                sb.append(entityHiRo.getGroupDesc()).append("-").append(entityHiRo.getDeptCode());
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                sb.append(entityHiRo.getGroupDesc()).append("-").append(entityHiRo.getDeptCode()).append("-")
                        .append(entityHiRo.getBrandCn()).append("-").append(entityHiRo.getBrandEn());
            }
            if (brandGroupDemineStr.equals(sb.toString())) {
                totalFcsPurchase = totalFcsPurchase.add(entityHiRo.getFcsPurchase() == null ? new BigDecimal("0") : entityHiRo.getFcsPurchase());
                totalFcsSales = totalFcsSales.add(entityHiRo.getFcsSales() == null ? new BigDecimal("0") : entityHiRo.getFcsSales());
            }
        }
        sumMap.put("totalFcsPurchase", totalFcsPurchase);
        sumMap.put("totalFcsSales", totalFcsSales);
        return sumMap;
    }

    /**
     * @param object
     * @param splitCondition 拆分条件
     * @return
     */
    public String getGroupStr(Object object, String splitCondition) {
        StringBuffer sb = new StringBuffer();
        if (object instanceof TtaSupplierItemMidEntity_HI) {
            TtaSupplierItemMidEntity_HI midEntityHi = (TtaSupplierItemMidEntity_HI) object;
            if ("Group".equals(splitCondition)) {
                sb.append(midEntityHi.getGroupName());
            } else if ("Group+Dept".equals(splitCondition)) {
                sb.append(midEntityHi.getGroupName()).append("-").append(midEntityHi.getDeptCode());
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                sb.append(midEntityHi.getGroupName()).append("-").append(midEntityHi.getDeptCode()).append("-").append(midEntityHi.getBrandName())
                        .append("-").append(midEntityHi.getBrandNameEn());
            } else {
                sb.append(midEntityHi.getGroupName()).append("-").append(midEntityHi.getDeptCode()).append("-").append(midEntityHi.getBrandName())
                        .append("-").append(midEntityHi.getBrandNameEn());
            }
        } else if (object instanceof TtaBrandplnLEntity_HI_RO) {
            TtaBrandplnLEntity_HI_RO brandplnLEntity_hi_ro = (TtaBrandplnLEntity_HI_RO) object;
            if ("Group".equals(splitCondition)) {
                sb.append(brandplnLEntity_hi_ro.getGroupDesc());
            } else if ("Group+Dept".equals(splitCondition)) {
                sb.append(brandplnLEntity_hi_ro.getGroupDesc()).append("-").append(brandplnLEntity_hi_ro.getDeptCode());
            } else if ("Group+Dept+Brand".equals(splitCondition)) {
                sb.append(brandplnLEntity_hi_ro.getGroupDesc()).append("-").append(brandplnLEntity_hi_ro.getDeptCode()).
                        append("-").append(brandplnLEntity_hi_ro.getBrandCn()).append("-").append(brandplnLEntity_hi_ro.getBrandEn());
            } else {
                sb.append(brandplnLEntity_hi_ro.getGroupDesc()).append("-").append(brandplnLEntity_hi_ro.getDeptCode())
                        .append("-").append(brandplnLEntity_hi_ro.getBrandCn()).append("-").append(brandplnLEntity_hi_ro.getBrandEn());
            }
        }
        return sb.toString();
    }

    private void setObjectAttribute(JSONObject jsonObject, BigDecimal brandFcsPurchase, BigDecimal brandFcsSales, String splitStatus,
                                    TtaProposalHeaderEntity_HI_RO proposalHeaderEntity_hi_ro, TtaBrandplnLEntity_HI_RO brandplnLEntityHiRo,
                                    List<TtaSplitBrandDetailEntity_HI> brandDetailEntity_hiList, Integer supplierItemHId, String splitCondition, int userId) throws Exception {
        TtaSplitBrandDetailEntity_HI entityHi = new TtaSplitBrandDetailEntity_HI();
        String groupCode = "";
        String groupName = "";
        String deptCode = "";
        String deptName = "";
        String brandName = "";
        String brandNameEn = "";
        String brandCode = "";
        String itemCode = "";
        String itemName = "";
        BigDecimal fcsPurchase = new BigDecimal("0");
        BigDecimal fcsSales = new BigDecimal("0");
        //原供应商
        String supplierCode = "";
        String supplierName = "";
        //指定供应商
        String appointVendorNbr = "";
        String appointVendorNbrName = "";
        Integer brandplnLId = null;
        Integer mid = -1;
        Integer supplierItemId = -1;
        String splitWhere = "";

        if ("0".equals(splitStatus)) {//不拆分
            if (null == jsonObject) {//拆分条件明细对象不存在,把品牌计划明细数据保存
                groupCode = brandplnLEntityHiRo.getGroupCode();
                groupName = brandplnLEntityHiRo.getGroupDesc();
                deptCode = brandplnLEntityHiRo.getDeptCode();
                deptName = brandplnLEntityHiRo.getDeptDesc();
                brandName = brandplnLEntityHiRo.getBrandCn();
                brandNameEn = brandplnLEntityHiRo.getBrandEn();
                brandCode = brandplnLEntityHiRo.getBrandCode();
                supplierCode = proposalHeaderEntity_hi_ro.getVendorNbr();
                supplierName = proposalHeaderEntity_hi_ro.getVendorName();

                fcsPurchase = brandplnLEntityHiRo.getFcsPurchase() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsPurchase();
                fcsSales = brandplnLEntityHiRo.getFcsSales() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsSales();
                appointVendorNbr = proposalHeaderEntity_hi_ro.getVendorNbr();
                appointVendorNbrName = proposalHeaderEntity_hi_ro.getVendorName();
                brandplnLId = brandplnLEntityHiRo.getBrandplnLId();//品牌明细行id
                supplierItemId = supplierItemHId;
            } else {//不需要检查金额 ,指定供应商等于原来供应商
                groupCode = jsonObject.getString("groupCode");
                groupName = jsonObject.getString("groupName");
                deptCode = jsonObject.getString("deptCode");
                deptName = jsonObject.getString("deptName");
                brandName = jsonObject.getString("brandName");
                brandNameEn = jsonObject.getString("brandNameEn");
                brandCode = jsonObject.getString("brandCode");
                itemCode = jsonObject.getString("itemCode");
                itemName = jsonObject.getString("itemName");
                supplierCode = proposalHeaderEntity_hi_ro.getVendorNbr();
                supplierName = proposalHeaderEntity_hi_ro.getVendorName();
                //品牌计划表上的fcspurchase,fcsSales
                fcsPurchase = brandplnLEntityHiRo.getFcsPurchase() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsPurchase();
                fcsSales = brandplnLEntityHiRo.getFcsSales() == null ? BigDecimal.ZERO : brandplnLEntityHiRo.getFcsSales();
                appointVendorNbr = proposalHeaderEntity_hi_ro.getVendorNbr();//Proposal上的供应商编号
                appointVendorNbrName = proposalHeaderEntity_hi_ro.getVendorName();
                brandplnLId = brandplnLEntityHiRo.getBrandplnLId();//品牌明细行id
                mid = jsonObject.getInteger("mid");//与品牌计划明细数据分组维度相同的拆分明细数据的第一条数据的主键
                supplierItemId = jsonObject.getInteger("supplierItemHId");
            }
        } else {//拆分
            TtaSupplierItemMidEntity_HI midEntityHi = JSON.parseObject(JSONObject.toJSONString(jsonObject), TtaSupplierItemMidEntity_HI.class);
            groupCode = midEntityHi.getGroupCode();
            groupName = midEntityHi.getGroupName();
            deptCode = midEntityHi.getDeptCode();
            deptName = midEntityHi.getDeptName();
            brandName = midEntityHi.getBrandName();
            brandNameEn = midEntityHi.getBrandNameEn();
            brandCode = midEntityHi.getBrandCode();
            itemCode = midEntityHi.getItemCode();
            itemName = midEntityHi.getItemName();
            supplierCode = midEntityHi.getSupplierCode();
            supplierName = midEntityHi.getSupplierName();

            //TtaSupplierItemMidEntity_HI midEntityHi = SaafToolUtils.setEntity(TtaSupplierItemMidEntity_HI.class, jsonObject, ttaSupplierItemMidDAO_HI, userId);
            fcsPurchase = midEntityHi.getFcsPurchase() == null ? BigDecimal.ZERO : midEntityHi.getFcsPurchase();//用户手动填写的值
            fcsSales = midEntityHi.getFcsSales() == null ? BigDecimal.ZERO : midEntityHi.getFcsSales();
            appointVendorNbr = midEntityHi.getSplitSupplierCode();
            appointVendorNbrName = midEntityHi.getSplitSupplierName();
            if (StringUtils.isBlank(appointVendorNbr) || StringUtils.isBlank(appointVendorNbrName)) {
                appointVendorNbr = midEntityHi.getSupplierCode();
                appointVendorNbrName = midEntityHi.getSupplierName();
            }
            brandplnLId = brandplnLEntityHiRo.getBrandplnLId();//品牌明细行id
            mid = midEntityHi.getMid();//拆分明细表的主键
            supplierItemId = midEntityHi.getSupplierItemHId();
            splitWhere = splitCondition;
        }
        entityHi.setSupplierCode(supplierCode);
        entityHi.setSupplierName(supplierName);
        entityHi.setGroupCode(groupCode);
        entityHi.setGroupName(groupName);
        entityHi.setDeptCode(deptCode);
        entityHi.setDeptName(deptName);
        entityHi.setBrandCode(brandCode);
        entityHi.setBrandName(brandName);
        entityHi.setBrandNameEn(brandNameEn);
        entityHi.setItemNbr(itemCode);
        entityHi.setItemName(itemName);

        //设置Fcs purchase Fcs Sales
        entityHi.setFcsPurchase(fcsPurchase);
        entityHi.setFcsSales(fcsSales);
        //保留4位小数,四舍五入
        BigDecimal divideFcsPurchase = fcsPurchase.divide(brandFcsPurchase, 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal divideFcsSales = fcsSales.divide(brandFcsSales, 4, BigDecimal.ROUND_HALF_UP);
        entityHi.setAmoutRateFcspur(divideFcsPurchase);//purchase比例
        entityHi.setAmoutRateFcssal(divideFcsSales);//sales比例
        //ProposalId 对应的品牌明细汇总Fcs Purchase Fcs Sales金额
        entityHi.setTotalFcsPurchase(brandFcsPurchase);
        entityHi.setTotalFcsSales(brandFcsSales);
        entityHi.setSplitSupplierCode(appointVendorNbr);
        entityHi.setSplitSupplierName(appointVendorNbrName);
        entityHi.setBrandplnLId(brandplnLId);
        entityHi.setProposalCode(proposalHeaderEntity_hi_ro.getOrderNbr());
        entityHi.setProposalId(proposalHeaderEntity_hi_ro.getProposalId());
        entityHi.setProposalYear(proposalHeaderEntity_hi_ro.getProposalYear());
        entityHi.setMid(mid);
        entityHi.setSupplierItemId(supplierItemId);
        entityHi.setSplitCondition(splitWhere);//当是拆分的时候,存入拆分条件,不是,不存入拆分条件
        entityHi.setSplitStatus(splitStatus);
        entityHi.setCreationDate(new Date());
        entityHi.setCreatedBy(userId);
        entityHi.setLastUpdateDate(new Date());
        entityHi.setLastUpdatedBy(userId);
        entityHi.setOperatorUserId(userId);
        brandDetailEntity_hiList.add(entityHi);
    }

    @Override
    public List<TtaSupplierItemMidEntity_HI> findSplitDetailDataBySupplierItemHId(JSONObject jsonObject) {
        JSONObject headerInfo = jsonObject.getJSONObject("info");
        JSONObject date = headerInfo.getJSONObject("date");
        if (SaafToolUtils.isNullOrEmpty(date) || SaafToolUtils.isNullOrEmpty(date.getInteger("supplierItemId"))) {
            JSONArray errorList = new JSONArray();
            JSONObject errJson = new JSONObject();
            String errorMsg = "头信息未保存,请先保存,导出模板,再重新导入";
            errJson.put("ROW_NUM", 0);
            errJson.put("ERR_MESSAGE", errorMsg);
            errorList.add(errJson);
            throw new IllegalArgumentException(errorList.toJSONString());
        }
        //查询拆分明细数据
        Integer supplierItemId = date.getInteger("supplierItemId");
        StringBuffer sb = new StringBuffer("from TtaSupplierItemMidEntity_HI ttaSupplierItemM where 1=1 and ttaSupplierItemM.supplierItemHId =:supplierItemId ");
        Map<String, Object> queryParamMap = new HashMap<>();
        queryParamMap.put("supplierItemId", supplierItemId);
        List<TtaSupplierItemMidEntity_HI> daoList = ttaSupplierItemMidDAO_HI.findList(sb, queryParamMap);
        return daoList;
    }

    /**
     * Excel导入
     *
     * @param queryParamJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public int saveOrUpdateWithdrawalSplitDataImport(JSONObject queryParamJSON, int userId, List<TtaSupplierItemMidEntity_HI> midEntityHiList) throws Exception {
        JSONArray jsonArray = queryParamJSON.getJSONArray("data");//获取导入的数据
        JSONObject headerInfo = queryParamJSON.getJSONObject("info");
        JSONObject date = headerInfo.getJSONObject("date");

        if (SaafToolUtils.isNullOrEmpty(jsonArray) || jsonArray.size() == 0) {
            JSONArray errorList = new JSONArray();
            JSONObject errJson = new JSONObject();
            String errorMsg = "导入的数据集为空,请重新导出模板再导入";
            errJson.put("ROW_NUM", 0);
            errJson.put("ERR_MESSAGE", errorMsg);
            errorList.add(errJson);
            throw new IllegalArgumentException(errorList.toJSONString());
        }

        Integer supplierItemId = date.getInteger("supplierItemId");
        TtaSupplierItemHeaderEntity_HI byId = ttaSupplierItemHeaderServer.getById(supplierItemId);
        String condition = byId.getSplitCondition();

        JSONArray errList = new JSONArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);//代表Excel每一行数据
            TtaSupplierItemMidEntity_HI ttaSupplierItemMidEntityHi = SaafToolUtils.setEntity(TtaSupplierItemMidEntity_HI.class, json, baseCommonDAO, userId);
            Integer mid = json.getInteger("mid");
            JSONObject errJson = new JSONObject();
            boolean isFlag = true;//默认不相同
            String msgStr = "";
            try {
                if (SaafToolUtils.isNullOrEmpty(mid)) {
                    msgStr += "导入的唯一标识列的值不能为空;";
                }
                for (TtaSupplierItemMidEntity_HI itemMidEntityHi : midEntityHiList) {
                    if (itemMidEntityHi.getMid().equals(mid)) {
                        msgStr = this.checkExcelTemplateData(condition, json, itemMidEntityHi, msgStr);
                        isFlag = false;
                        break;
                    }
                    ;
                }

                if (isFlag) {
                    msgStr += "导入的唯一标识列的值(" + mid + ")与原数据不相符;";
                }

                if (!"".equals(msgStr)) {
                    errJson.put("ROW_NUM", json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE", msgStr);
                    errList.add(errJson);
                } else {
                    //json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
                    //json.put("versionNum",ttaSupplierItemMidEntityHi.getVersionNum());
                    //json.put("lastUpdateDate",new Date());
                    //json.put("lastUpdatedBy",userId);
                    //json.put("creationDate",new Date());
                    //json.put("createdBy",userId);
                    //json.put("supplierItemHId",ttaSupplierItemMidEntityHi.getSupplierItemHId());//tta_sole_item表中的外键
                    ttaSupplierItemMidEntityHi.setLastUpdateDate(new Date());
                    ttaSupplierItemMidEntityHi.setLastUpdatedBy(userId);
                    ttaSupplierItemMidEntityHi.setLastUpdateLogin(userId);
                    super.saveOrUpdate(ttaSupplierItemMidEntityHi);

                }
            } catch (Exception e) {
                msgStr += ("有异常,数据有误.");
                errJson.put("ROW_NUM", json.get("ROW_NUM"));
                errJson.put("ERR_MESSAGE", msgStr);
                errList.add(errJson);
                e.printStackTrace();
            }
        }
        if (!errList.isEmpty()) {
            throw new IllegalArgumentException(errList.toJSONString());
        }
        return jsonArray.size();
    }

    /**
     * 检查Excel表格中的每一行的数据正确性
     *
     * @param condition       拆分条件
     * @param jsonObject      Excel 行数据
     * @param itemMidEntityHi 拆分明细行数据
     * @param msgStr          提示信息
     * @throws Exception
     */
    private String checkExcelTemplateData(String condition, JSONObject jsonObject, TtaSupplierItemMidEntity_HI itemMidEntityHi, String msgStr) throws Exception {
        String supplierCode = jsonObject.getString("supplierCode");
        String supplierName = jsonObject.getString("supplierName");
        if (!StringUtils.equals(supplierCode, itemMidEntityHi.getSupplierCode())) {
            msgStr += "导入的原供应商编号(" + supplierCode + ")与原数据的供应商编号(" + itemMidEntityHi.getSupplierCode() + ")不相符;";
        }
        if (!StringUtils.equals(supplierName, itemMidEntityHi.getSupplierName())) {
            msgStr += "导入的原供应商名称(" + supplierName + ")与原数据的供应商名称(" + itemMidEntityHi.getSupplierName() + ")不相符;";
        }

        switch (StringUtils.trim(condition)) {
            case ITtaSupplierItemMid.GROUP:
                msgStr = checkExcelGroup(jsonObject, itemMidEntityHi, msgStr);
                break;
            case ITtaSupplierItemMid.GROUP_DEPT:
                msgStr = checkExcelDept(jsonObject, itemMidEntityHi, msgStr);
                break;
            case ITtaSupplierItemMid.GROUP_DEPT_BRAND:
                msgStr = checkExcelBrand(jsonObject, itemMidEntityHi, msgStr);
                break;
            case ITtaSupplierItemMid.GROUP_DEPT_BRAND_ITEM:
                msgStr = checkExcelItem(jsonObject, itemMidEntityHi, msgStr);
                break;
        }

        return msgStr;
    }

    private String checkExcelGroup(JSONObject jsonObject, TtaSupplierItemMidEntity_HI midEntityHi, String msgStr) throws Exception {
        String groupCode = jsonObject.getString("groupCode");
        String groupName = jsonObject.getString("groupName");

        if (!StringUtils.equals(groupCode, midEntityHi.getGroupCode())) {
            msgStr += "导入的GROUP(" + groupCode + ")与原数据的GROUP(" + midEntityHi.getGroupCode() + ")不相符;";
        }
        if (!StringUtils.equals(groupName, midEntityHi.getGroupName())) {
            msgStr += "导入的GROUP_DESC(" + groupName + ")与原数据的GROUP_DESC(" + midEntityHi.getGroupName() + ")不相符;";
        }

        return msgStr;
    }

    private String checkExcelDept(JSONObject jsonObject, TtaSupplierItemMidEntity_HI midEntityHi, String msgStr) throws Exception {
        msgStr = checkExcelGroup(jsonObject, midEntityHi, msgStr);
        String deptCode = jsonObject.getString("deptCode");
        String deptName = jsonObject.getString("deptName");

        if (!StringUtils.equals(deptCode, midEntityHi.getDeptCode())) {
            msgStr += "导入的DEPT_CODE(" + deptCode + ")与原数据的DEPT_CODE(" + midEntityHi.getDeptCode() + ")不相符;";
        }
        if (!StringUtils.equals(deptName, midEntityHi.getDeptName())) {
            msgStr += "导入的DEPT_DESC(" + deptName + ")与原数据的DEPT_DESC(" + midEntityHi.getDeptName() + ")不相符;";
        }
        return msgStr;
    }

    private String checkExcelBrand(JSONObject jsonObject, TtaSupplierItemMidEntity_HI midEntityHi, String msgStr) throws Exception {
        msgStr = checkExcelDept(jsonObject, midEntityHi, msgStr);
        String brandName = jsonObject.getString("brandName");

        if (!StringUtils.equals(brandName, midEntityHi.getBrandName())) {
            msgStr += "导入的BRAND_CN(" + brandName + ")与原数据的BRAND_CN(" + midEntityHi.getBrandName() + ")不相符;";
        }
        return msgStr;
    }

    private String checkExcelItem(JSONObject jsonObject, TtaSupplierItemMidEntity_HI midEntityHi, String msgStr) throws Exception {
        msgStr = checkExcelBrand(jsonObject, midEntityHi, msgStr);
        String itemCode = jsonObject.getString("itemCode");
        String itemName = jsonObject.getString("itemName");

        if (!StringUtils.equals(itemCode, midEntityHi.getItemCode())) {
            msgStr += "导入的ITEM(" + itemCode + ")与原数据的ITEM(" + midEntityHi.getItemCode() + ")不相符;";
        }
        if (!StringUtils.equals(itemName, midEntityHi.getItemName())) {
            msgStr += "导入的ITEM名称(" + itemName + ")与原数据的ITEM名称(" + midEntityHi.getItemName() + ")不相符;";
        }
        return msgStr;
    }


    class TaskSplitIoCallable implements Callable<List<Map<String, Object>>> {

        private String yearMonth;

        private String venderNbrStr;

        public TaskSplitIoCallable() {

        }

        public TaskSplitIoCallable(List<String> vendorNbrList, String yearMonth) {
            this.venderNbrStr = String.join(",", vendorNbrList);
            this.yearMonth = yearMonth;
        }

        @Override
        public List<Map<String, Object>> call() throws Exception {
            String querySql = " select " +
                    "    t.vendor_nbr,\n" +
                    "    t.split_supplier_code,\n" +
                    "    sum(t.sales_amt) as sales_amt,\n" +
                    "    sum(t.po_amt) as po_amt\n" +
                    "    from tta_sale_sum_" + yearMonth + "\t t\n" +
                    "    where t.vendor_nbr in ( " + venderNbrStr + ")\n" +
                    "    group by t.vendor_nbr, t.split_supplier_code";
            return iTtaSupplierItemRelSupplier.queryNamedSQLForList(querySql, new HashMap<>());
        }
    }

    public class RollBack {
        private Boolean isRollBack;

        public Boolean getRollBack() {
            return isRollBack;
        }

        public void setRollBack(Boolean rollBack) {
            isRollBack = rollBack;
        }

        public RollBack(Boolean isRollBack) {
            this.isRollBack = isRollBack;
        }
    }

}

