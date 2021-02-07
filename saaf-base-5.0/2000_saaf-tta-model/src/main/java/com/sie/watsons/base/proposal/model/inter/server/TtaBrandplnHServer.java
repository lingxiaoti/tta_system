package com.sie.watsons.base.proposal.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaContractLineEntity_HI;
import com.sie.watsons.base.contract.model.entities.TtaContractLineHEntity_HI;
import com.sie.watsons.base.newbreedextend.model.inter.server.TtaNewbreedExtendHeaderServer;
import com.sie.watsons.base.proposal.model.dao.TtaBrandplnHDAO_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnHEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaBrandplnLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.TtaTermsLEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnHEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaTermsLEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandCountCRecord;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnL;
import com.sie.watsons.base.questionnaire.model.dao.TtaQuestionHeaderDAO_HI;
import com.sie.watsons.base.questionnaire.model.inter.ITtaQuestionChoiceLine;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.proposal.model.inter.ITtaBrandplnH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Component("ttaBrandplnHServer")
public class TtaBrandplnHServer extends BaseCommonServer<TtaBrandplnHEntity_HI> implements ITtaBrandplnH {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandplnHServer.class);

    @Autowired
    private ViewObject<TtaBrandplnHEntity_HI> ttaBrandplnHDAO_HI;
    @Autowired
    private ViewObject<TtaProposalHeaderEntity_HI> ttaProposalHeaderDAO_HI;
    @Autowired
    private TtaBrandplnHDAO_HI ttaBrandplnHDAO;
    @Autowired
    private BaseViewObject<TtaBrandplnHEntity_HI_RO> ttaBrandplnHDAO_HI_RO;
    @Autowired
    private ViewObject<TtaBrandplnLEntity_HI> ttaBrandplnLDAO_HI;
    @Autowired
    private BaseCommonDAO_HI<TtaBrandplnLEntity_HI> ttaBrandplnLEntity_hiBaseCommonDAO_hi;
    @Autowired
    private ViewObject<TtaContractLineEntity_HI> ttaContractLineDAO_HI;
    @Autowired
    private ViewObject<TtaContractLineHEntity_HI> ttaContractLineHDAO_HI;
    @Autowired
    private DynamicBaseViewObject commonDAO_HI_DY;
    @Autowired
    private ITtaBrandplnL ttaBrandplnL;
    @Autowired
    private GenerateCodeService codeService;
    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    private ITtaBrandCountCRecord ttaBrandCountCRecordServer;

    //查询品牌计划明细数据线程池
    //private static ExecutorService executorService2 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    //使用排队策略 LinkedBlockingQueue
    private static ExecutorService executorService = new ThreadPoolExecutor(3, 20, 0,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());


    public TtaBrandplnHServer() {
    }

    public static final Map<String, String> PURCH_TYPE = new HashMap<>();

    static {
        PURCH_TYPE.put("B01", "PURCHASE");//购销模式
        PURCH_TYPE.put("B02", "PURCHASE");//购销模式
        PURCH_TYPE.put("B04", "CONSIGNMENT");//寄售
        PURCH_TYPE.put("A01", "CVW");//寄售经仓
        PURCH_TYPE.put("B03", "CVW");//寄售经仓
        PURCH_TYPE.put("A02", "CVW");//寄售经仓
    }


    @Override
    public Pagination<TtaBrandplnHEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBrandplnHEntity_HI_RO.TTA_ITEM_V);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_h_id", "brandplnHId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.year_Code", "yearCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");

        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.brandpln_h_id desc", false);
        Pagination<TtaBrandplnHEntity_HI_RO> findList = ttaBrandplnHDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public TtaBrandplnHEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaBrandplnHEntity_HI instance = SaafToolUtils.setEntity(TtaBrandplnHEntity_HI.class, paramsJSON, ttaBrandplnHDAO_HI, userId);
        ttaBrandplnHDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public void delete(Integer pkId) {
        if (pkId == null) {
            return;
        }
        TtaBrandplnHEntity_HI instance = ttaBrandplnHDAO_HI.getById(pkId);
        if (instance == null) {
            return;
        }
        ttaBrandplnHDAO_HI.delete(instance);
    }


    @Override
    public TtaBrandplnHEntity_HI_RO callfindByRoId(JSONObject queryParamJSON, int userId) throws Exception {
        SaafToolUtils.validateJsonParms(queryParamJSON, "proposalId");
        Integer proposalId = queryParamJSON.getInteger("proposalId");
        StringBuffer sql = new StringBuffer();
        sql.append(TtaBrandplnHEntity_HI_RO.TTA_ITEM_V);

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.brandpln_h_id", "brandplnHId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.vendor_Nbr", "vendorNbr", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.year_Code", "yearCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.proposal_Id", "proposalId", sql, paramsMap, "=");
        TtaBrandplnHEntity_HI_RO instance = (TtaBrandplnHEntity_HI_RO) ttaBrandplnHDAO_HI_RO.get(sql, paramsMap);
        //如果品牌计划头信息为空,新建一条品牌头信息
        if (instance == null) {
            Map<String, Object> paramsMap2 = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            //try {
            paramsMap2.put("pkId", proposalId);
            paramsMap2.put("userId", userId);
            resultMap = ttaBrandplnHDAO.callBrandPlnGen(paramsMap2);
            Integer xFlag = (Integer) resultMap.get("xFlag");
            String xMsg = (String) resultMap.get("xMsg");
            if (xFlag != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //
                throw new Exception(xMsg);
            }
            instance = (TtaBrandplnHEntity_HI_RO) ttaBrandplnHDAO_HI_RO.get(sql, paramsMap);
        }
        return instance;
    }


    @Override
    public TtaBrandplnHEntity_HI updateconfirm(JSONObject paramsJSON, int userId) throws Exception {
        //TtaBrandplnHEntity_HI instance = SaafToolUtils.setEntity(TtaBrandplnHEntity_HI.class, paramsJSON, ttaBrandplnHDAO_HI, userId);
        Integer proposalId = paramsJSON.getInteger("proposalId");
        TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
        if (instance.getStatus().equals("B") || instance.getStatus().equals("C")) {
            throw new IllegalArgumentException("Proposal单据状态为审批通过、待审批，不允许编辑单据");
        }
        instance.setIsPlnConfirm("Y");
        ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
        return null;
    }

    @Override
    public String checkConfirm(JSONObject jsonObject, int userId) {
        Integer proposalId = jsonObject.getInteger("proposalId");
        TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
        if (instance.getStatus().equals("B") || instance.getStatus().equals("C")) {
            throw new IllegalArgumentException("Proposal单据状态为审批通过、待审批，不能取消确认");
        }

        if("N".equals(instance.getIsPlnConfirm())){
            throw new IllegalArgumentException("当前品牌计划表已经是非确认状态,请刷新重试,如多次重试失败,请联系管理员");
        }

        String msg = "";
        List<String> tipList = new ArrayList<>();

        //Terms
        if ("Y".equals(instance.getIsTermsConfirm())) {
            tipList.add("TTA Terms");
        }

        //部门协定标准
        if ("Y".equals(instance.getIsDepartConfirm())) {
            tipList.add("部门协定标准");
        }

        if("Y".equals(instance.getIsNewConfirm())){
            tipList.add("NPP服务费");
        }

        //5.取消问卷调查
        if("Y".equals(instance.getIsQuestConfirm())){
            tipList.add("问卷调查");
        }

        if (null != tipList && tipList.size() > 0) {
            msg += "【"+StringUtils.join(tipList,",")+"】模块为确认状态";
        }

        return msg;
    }

    /**
     * 品牌计划取消确认,其他模块只是取消确认状态,不需要更新数据
     *
     * 1.Terms数据不需要更新
     * 2.部门协定标准数据不需要更新
     * 3.NPP服务费不需要更新
     * 4.问卷调查
     * 5.TTA_Summary中的数据需要更新
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public TtaProposalHeaderEntity_HI updatecancelConfirm(JSONObject paramsJSON, int userId) throws Exception {
        Integer proposalId = paramsJSON.getInteger("proposalId");
        TtaProposalHeaderEntity_HI instance = ttaProposalHeaderDAO_HI.getById(proposalId);
        if (instance.getStatus().equals("B") || instance.getStatus().equals("C")) {
            throw new IllegalArgumentException("Proposal单据状态为审批通过、待审批，不能取消确认");
        }

        if("N".equals(instance.getIsPlnConfirm())){
            throw new IllegalArgumentException("当前品牌计划表已经是非确认状态,请刷新重试,如多次重试失败,请联系管理员");
        }

        //1.取消品牌计划确认
        instance.setIsPlnConfirm("N");

        Map<String,Object> brandParamMap = new HashMap<>();
        brandParamMap.put("proposalId",proposalId);
        List<TtaBrandplnLEntity_HI> byProperty1 = ttaBrandplnLDAO_HI.findByProperty(brandParamMap);
        for (TtaBrandplnLEntity_HI entity_hi : byProperty1) {
            entity_hi.setFcsPurchaseCon("N");
            entity_hi.setPurchaseGrowthCon("N");
            entity_hi.setFcsSalesCon("N");
            entity_hi.setSalesGrowthCon("N");
        }


        //2.取消Terms确认
        if ("Y".equals(instance.getIsTermsConfirm())) {
            instance.setIsTermsConfirm("N");
        }

        //3.取消部门协定标准确认
        if ("Y".equals(instance.getIsDepartConfirm())) {
            instance.setIsDepartConfirm("N");
        }

        //4.取消NPP 服务费
        if("Y".equals(instance.getIsNewConfirm())){
            instance.setIsNewConfirm("N");
        }

        //5.取消问卷调查
        if("Y".equals(instance.getIsQuestConfirm())){
            instance.setIsQuestConfirm("N");
		}

        List<TtaContractLineHEntity_HI> objects = new ArrayList<TtaContractLineHEntity_HI>();
        //6.删除TTA_Summary 中的TTA_CONTRACT_LIEN的数据之前,先把数据推送到历史表:TTA_CONTRACT_LIEN_H ,然后再做删除TTA_CONTRACT_LIEN的数据
        // 流向:TTA_CONTRACT_LIEN --------> TTA_CONTRACT_LIEN_H
        List<TtaContractLineEntity_HI> byProperty = ttaContractLineDAO_HI.findByProperty("proposalId", instance.getProposalId());
        for(TtaContractLineEntity_HI t:byProperty){
            TtaContractLineHEntity_HI ttaContractLineHEntity_hi = new TtaContractLineHEntity_HI();
            SaafBeanUtils.copyUnionProperties(t,ttaContractLineHEntity_hi);
            ttaContractLineHEntity_hi.setOperatorUserId(userId);
            objects.add(ttaContractLineHEntity_hi);
        }
        
        ttaContractLineHDAO_HI.saveOrUpdateAll(objects);
        ttaContractLineDAO_HI.deleteAll(byProperty);

        ttaBrandplnLDAO_HI.save(byProperty1);
        ttaProposalHeaderDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public JSONObject getBrandCreateResult(String createKey) {
        String str= jedisCluster.get(createKey);
        if (StringUtils.isBlank(str))
            return null;
        return JSONObject.parseObject(str);
    }

    /**
     * 计算逻辑(拉取品牌计划行明细数据
     * )
     *
     * @param queryJSONParam
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaBrandplnLEntity_HI> callCount(JSONObject queryJSONParam, int userId) throws Exception {
        JSONObject project = queryJSONParam.getJSONObject("project");//proposal单据的参数
        StringBuffer hql = new StringBuffer("from TtaBrandplnLEntity_HI h where h.proposalId =:proposalId");
        Map<String,Object> map=new HashMap<>();
        map.put("proposalId",project.getInteger("proposalId"));

        //1.拉取数据前先删除相对应的proposal Id的数据
        List<TtaBrandplnLEntity_HI> brandListByProposalId = ttaBrandplnLDAO_HI.findList(hql,map);
        ttaBrandplnLDAO_HI.delete(brandListByProposalId);

        //2.获取品牌明细列表的数组
        JSONArray paramsArray = queryJSONParam.getJSONArray("brandPlnLDataTable");
        JSONObject brandparams = queryJSONParam.getJSONObject("brandparams");//头信息

        //3.获取参数
        String proposalYear = project.getString("proposalYear");//制作年度
        String saleType = project.getString("saleType");//销售方式
        String creationDate = project.getString("creationDate");//proposal制单日期
        String newOrExisting = project.getString("newOrExisting");//new_or_existing
        String vendorNbr = brandparams.getString("vendorNbr");//品牌计划单据上的供应商编号
        String vendorName = brandparams.getString("vendorName");//品牌计划单据上的供应商名称
        Assert.notNull(vendorNbr,"供应商参数为空,请先选择供应商");
        Assert.notNull(proposalYear,"Proposal制作年度为空,请先选择Proposal制作年度");
        Assert.notNull(saleType,"Proposal头信息的销售方式为空,请先选择销售方式");

        if ("Existing_Vendor".equals(newOrExisting)) {//存在的供应商
            if (PURCH_TYPE.containsKey(saleType)) {
                String purchType = PURCH_TYPE.get(saleType);
                //4.获取系统时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                String date = sdf.format(new Date());
                //5.格式化制单日期得到年份
                //int yearByString = WDatesUtils.getYearByString(creationDate, "yyyy-MM-dd");
                String sqlSalesStr = "";
                String sqlPurchaseStr = "";
                List<Integer> dateList = new ArrayList<>();
                int saleIndex = 0;
                int purschaseYear = 0;
                if (StringUtils.isBlank(proposalYear)) {
                    throw new IllegalArgumentException("Proposal制作年度没有选择,不允许制作品牌计划!");
                }
                if (Integer.parseInt(proposalYear) <= 2018) {
                    throw new IllegalArgumentException("当前[" + proposalYear + "]年度,没有历史数据!");
                } else if (Integer.parseInt(proposalYear) >= Integer.parseInt(date)) {//proposal制作年度大于当前年度
                    int oiYear = Integer.parseInt(proposalYear) - 1;
                    String sqlMonth = "select to_char(max(tl.account_month),'yyyy-MM-dd') maxM from  " +
                            "tta_oi_summary_line tl where to_char(tl.account_month,'yyyy') = '"+oiYear+"'";
                    List<Map<String, Object>> mapList = ttaBrandplnLEntity_hiBaseCommonDAO_hi.queryNamedSQLForList(sqlMonth, new HashMap<>());
                    if (mapList == null || mapList.size() == 0) {
                        throw new IllegalArgumentException("当前年度:" + oiYear + ",没有往年数据");
                    }

                    Map<String, Object> map1 = mapList.get(0);
                    String monthParam = (String)map1.get("MAXM");
                    LOGGER.info("品牌拉取Actual数据日期:" + monthParam);
                    //8.获取月份(从制单日期中获取)
                    //int month = WDatesUtils.getMonth(creationDate, "yyyy-MM-dd");
                    //saleIndex = month;
                    //传入11,实际上得到点的是10
                    int month = WDatesUtils.getMonth(monthParam, "yyyy-MM-dd");
                    saleIndex = month + 1;
                    purschaseYear = Integer.parseInt(proposalYear) - 1;//获取年份
                    for (int i = 1; i <= month + 1; i++) {
                        String periodMonth = "";
                        if (i >= 10) {
                            periodMonth = purschaseYear + "" + i;
                        } else {
                            periodMonth = purschaseYear + "0" + i;
                        }
                        dateList.add(Integer.valueOf(periodMonth));
                    }

                    sqlSalesStr = "(t.TOTAL / "+saleIndex+") * 12";
                    sqlPurchaseStr = "((t.po_Record - nvl(t.cancel_receiving_amt,0)) / " + saleIndex +") * 12";

                    //sqlSalesStr = "(t.total_intax/"+saleIndex+")*12";
                    //sqlPurchaseStr = "(t.each_brand_netpurchase/" + saleIndex +") *12";
                } else if ((2019 <= Integer.parseInt(proposalYear)) && (Integer.parseInt(proposalYear) < Integer.parseInt(date)) ){
                    purschaseYear = Integer.parseInt(proposalYear) - 1;//获取年份
                    //当前年度为2019 时
                    if (Integer.parseInt(proposalYear) == 2019) {
                        //取往年2018年度的
                        dateList.add(201812);
                    }else {
                        //7.去年度的月份
                        for (int a = 1; a <= 12; a++) {
                            String monthStr = "";
                            if (a >= 10) {
                                monthStr = (purschaseYear - 1) + "" + a;
                            } else {
                                monthStr = (purschaseYear - 1) + "0" + a;
                            }
                            dateList.add(Integer.valueOf(monthStr));
                        }
                    }

                    //sqlSalesStr = "t.total_notax";//不含税销售总额
                    sqlPurchaseStr = "t.po_Record - nvl(t.cancel_receiving_amt,0)";
                    sqlSalesStr = "t.TOTAL";//含税销售总额
                }
                
                //10.获取唯一标识,作为删除数据的唯一标识
                String deleteSymbol = codeService.getPurchaseOrderNumber();
                List<Future<String>> futureList = new ArrayList<>();
                Long startTime  = System.currentTimeMillis();
                for (int th = 0; th < dateList.size(); th++) {
                    int finalTh = th;
                    Future<String> submit = executorService.submit(new Callable<String>() {
                        @Override
                        public String call() throws Exception{
                            String msg = "fail";
                                try {
                                    ttaBrandplnL.insertBrandPlanData(vendorNbr,purchType,dateList.get(finalTh),deleteSymbol);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    jedisCluster.setex(queryJSONParam.getString("createKey"),3600,"{}");
                                    ttaBrandCountCRecordServer.updateBrandRecordStatus(queryJSONParam,e);
                                    return "fail";
                                }
                            return "success";
                        }
                    });
                    futureList.add(submit);
                }

                for (Future<String> future : futureList) {
                    String returnS = future.get();
                    System.out.println("子线程返回结果:" + returnS);
                    if ("fail".equals(returnS)) {
                        throw new RuntimeException("拉取品牌计划明细数据失败!");
                    }
                }
                Long endTime = System.currentTimeMillis();
                LOGGER.info("所有线程汇总数据插入临时表总花费时间:" + (endTime - startTime) + "ms");

                String readSql = getBrandSql(sqlPurchaseStr,sqlSalesStr,purchType,deleteSymbol,vendorNbr,purschaseYear,dateList.get(0),dateList.get(dateList.size() - 1));
                List<Map<String, Object>> list  = commonDAO_HI_DY.findList(readSql);
                LOGGER.info("生成品牌明细计划行信息条数:{}",list.size());
                List<TtaBrandplnLEntity_HI> ttaBrandplnLEntity_his = JSON.parseArray(JSON.toJSONString(list), TtaBrandplnLEntity_HI.class);
                for (TtaBrandplnLEntity_HI entity_hi : ttaBrandplnLEntity_his) {
                    entity_hi.setCreatedBy(userId);
                    entity_hi.setCreationDate(new Date());
                    entity_hi.setLastUpdateDate(new Date());
                    entity_hi.setLastUpdatedBy(userId);
                    entity_hi.setLastUpdateLogin(userId);
                    entity_hi.setBrandplnHId(brandparams.getInteger("brandplnHId"));//品牌计划头信息的id
                    entity_hi.setProposalId(project.getInteger("proposalId"));
                    entity_hi.setFcsPurchaseCon("N");
                    entity_hi.setPurchaseGrowthCon("N");
                    entity_hi.setFcsSalesCon("N");
                    entity_hi.setFcsSalesCon("N");
                    entity_hi.setIsSplit("N");
                    entity_hi.setFcsSplitPurchse(entity_hi.getFcsPurchase());
                    entity_hi.setFcsSplitSales(entity_hi.getFcsSales());
                }
                ttaBrandplnLDAO_HI.saveOrUpdateAll(ttaBrandplnLEntity_his);

                //11.删除临时表
                String deletSql = "delete from tta_collect_sales_temp tcst where  tcst.delete_symbol = '"+deleteSymbol+"'";
                //更新品牌汇总数据
                commonDAO_HI_DY.executeUpdate(deletSql);
                this.brandplnHUpdate(brandparams,userId);
                jedisCluster.setex(queryJSONParam.getString("createKey"),3600,JSON.toJSONString(queryJSONParam));
                ttaBrandCountCRecordServer.updateBrandRecordStatus(queryJSONParam,null);
                return ttaBrandplnLEntity_his;
            } else {
                throw new IllegalArgumentException("请先在Proposal信息页签栏选择销售方式,再重新点计算操作拉取品牌计划明细列表的数据!");
            }
        } else {
            throw new IllegalArgumentException("New_Vendor 不需要计算操作!");
        }
    }

    /**
     * 根据销售方式拼接sql
     * @param sqlPurchaseStr 拼接采购成本sql
     * @param sqlSalesStr 拼接销售sql
     * @param purchType 采购模式
     * @param vendorNbr 供应商
     * @param purchaseYear 年份
     * @return
     */
    public String getBrandSql(String sqlPurchaseStr,String sqlSalesStr,String purchType,String deleteSymbol,String vendorNbr,int purchaseYear,Integer startMonth,Integer endMonth) {
        String readSql = "select \n" +
                "       tlls.brandDetails as brandDetails,\n" +
                "       tlls.tranDate as tranDate,\n" +
                "       tlls.purchType as purchType,\n" +
                "       tlls.groupCode as groupCode,\n" +
                "       tlls.groupDesc as groupDesc,\n" +
                "       tlls.deptCode as deptCode,\n" +
                "       tlls.deptDesc as deptDesc,\n" +
                "       tlls.brandCn as brandCn,\n" +
                "       tlls.brandEn as brandEn,\n" +
                "       tlls.brandCode as brandCode,\n" +
                "       tlls.salesAmt as salesAmt,\n" +
                "       tlls.cancel_receiving_amt,\n" +
                "       tlls.total_netpurchase,\n" +
                "       tlls.total_brand_po_amt,\n" +
                "       decode(tlls.totol_poRecord,0,0,round((tlls.poRecord/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as poRecord, -- 采购额(含税),\n" +
                "       decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as fcsPurchase, --预估未来的采购额(含税),\n" +
                "       tlls.sales as sales,\n" +
                "       tlls.fcsSales as fcsSales,\n" +
                "       decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as annualPurchase,\n" +
                "       tlls.fcsSales as annualSales,\n" +
                "       tlls.actualGp as actualGp,\n" +
                "       tlls.gp as gp\n" +
                " from ( \n" +
                " select \n" +
                "    mid.brandDetails,\n" +
                "    mid.tranDate,\n" +
                "    mid.purchType,\n" +
                "    mid.groupCode,\n" +
                "    mid.groupDesc,\n" +
                "    mid.deptCode,\n" +
                "    mid.deptDesc,\n" +
                "    mid.brandCn,\n" +
                "    mid.brandEn,\n" +
                "    mid.brandCode,\n" +
                "    mid.salesAmt,\n" +
                "    mid.cancel_receiving_amt,\n" +
                "    mid.total_netpurchase,\n" +
                "    mid.total_brand_po_amt,\n" +
                "    mid.fcsPurchase,\n" +
                "    mid.poRecord,\n" +
                "    mid.fcsSales,\n" +
                "    mid.sales,\n" +
                "    mid.actualGp,\n" +
                "    mid.gp,\n" +
                "    sum(mid.poRecord) over() as totol_poRecord,\n" +
                "    mid.brandPo_Record \n" +
                "  from \n" +
                " (\n" +
                "   select \n" +
                "       t.brand_Details as brandDetails,\n" +
                "       t.tran_date as tranDate,\n" +
                "       t.purch_type as purchType,\n" +
                "       t.group_code as groupCode,\n" +
                "       t.group_desc as groupDesc,\n" +
                "       t.dept_code as deptCode,\n" +
                "       t.dept_desc as deptDesc,\n" +
                "       t.brand_cn as brandCn,\n" +
                "       t.brand_en as brandEn,\n" +
                "       t.brand_code as brandCode,\n" +
                "       t.sales_amt as salesAmt,\n" +
                "       t.cancel_receiving_amt,\n" +
                "       t.total_netpurchase,\n" +
                "       t.total_brand_po_amt,\n" +
                "       t.po_Record as brandPo_Record,\n" +
                "       round(" + sqlPurchaseStr + ",0) as fcsPurchase,--预测未来采购成本\n" +
                "       round(t.po_Record-nvl(t.cancel_receiving_amt,0),0) as poRecord, --采购成本 \n" +
                "       round(" + sqlSalesStr + ",0) as fcsSales, --预测未来销售总金额\n" +
                "       round(t.TOTAL,0) as sales,--含税销售总金额\n" +
                "       round(t.total_notax,0) as notax_sales,--不含税销售总金额\n" +
                "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as actualGp, --计算不含税\n" +
                "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as gp --    \n" +
                "  from (\n" +
                "        select \n" +
                "               'Existing_Brand' as brand_Details,\n" +
                "               tcst.tran_date,\n" +
                "               tcst.vendor_nbr,\n" +
                "               tcst.purch_type,--采购类型\n" +
                "               tcst.group_code,\n" +
                "               tcst.group_desc,\n" +
                "               tcst.dept_code,\n" +
                "               tcst.dept_desc,\n" +
                "               tcst.brand_cn,\n" +
                "               tcst.brand_en,\n" +
                "               tcst.brand_code,\n" +
                "               tcst.sales_amt, --最先交易日期的销售金额\n" +
                "               nvl(tpur.cancel_receiving_amt,0) cancel_receiving_amt,\n" +
                "               nvl(rms.total_netpurchase,0) total_netpurchase, --某个供应商和关联供应商的净采购额,\n" +
                "               sum(case when tcst.purch_type = 'PURCHASE' then tcst.po_amt else tcst.cost end) over() total_brand_po_amt, --供应商所有品牌采购总额,\n" +
                "               sum(case when tcst.purch_type = 'PURCHASE' then tcst.po_amt else tcst.cost end) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as po_Record, --采购成本\n" +
                "               sum(tcst.gp_supplier_popt_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as gpr, --不含税销售毛利额\n" +
                "               sum(tcst.sales_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total, --含税销售总额\n" +
                "               sum(tcst.sales_exclude_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total_notax, --不含税销售总额\n" +
                "               row_number() over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en order by tcst.tran_date) flag\n" +
                "          from tta_collect_sales_temp tcst \n" +
                "          left join\n" +
                "          (\n" +
                "               select * from (\n" +
                "                select \n" +
                "                     tit.group_code,\n" +
                "                     tit.group_desc,\n" +
                "                     tit.dept_code,\n" +
                "                     tit.dept_desc,\n" +
                "                     tit.brand_code,\n" +
                "                     tit.brand_cn,\n" +
                "                     tit.brand_en,\n" +
                "                     tss.receiving_amount,\n" +
                "                     tss.vendor_nbr, \n" +
                "                     '" + vendorNbr + "' as split_supplier_code, \n" +
                "                     tss.purch_type,\n" +
                "                     tss.po_type,\n" +
                "                     sum(tss.receiving_amount) over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en) AS cancel_receiving_amt, --退货总金额\n" +
                "                     row_number() over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en order by tss.vendor_nbr) purchase_flag\n" +
                "                     from  tta_purchase_in_" + purchaseYear + " tss left join (\n" +
                "                                 select *\n" +
                "                                   from (SELECT t1.item_nbr,\n" +
                "                                                t1.group_code,\n" +
                "                                                t1.group_desc,\n" +
                "                                                t1.dept_code,\n" +
                "                                                t1.dept_desc, \n" +
                "                                                t1.brand_code,                               \n" +
                "                                                t1.brand_cn,\n" +
                "                                                t1.brand_en,\n" +
                "                                                ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.last_update_date desc) row_id\n" +
                "                                           FROM tta_item t1\n" +
                "                                          order by t1.last_update_date desc) t2\n" +
                "                                  where t2.row_id = 1\n" +
                "                            ) tit\n" +
                "                      on tss.item_nbr = tit.item_nbr \n" +
                "                      where tss.po_type = 'RETRUN' --and tss.purch_type = 'PURCHASE' \n" +
                "                      and tss.split_supplier_code in (\n" +
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) + "\n" +
                "                     ) \n" +
                "                      ) tpt  where tpt.purchase_flag = 1\n" +
                "                                 \n" +
                "          ) tpur \n" +
                "          on tcst.group_code =tpur.group_code and  tcst.dept_code =tpur.dept_code and  tcst.brand_cn =tpur.brand_cn\n" +
                "          and   tcst.brand_en = tpur.brand_en and tcst.split_supplier_code = tpur.split_supplier_code \n" +
                "         left join ( --根据供应商查询净采购额\n" +
                "   select \n" +
                "      t.supplierCode,\n" +
                "      sum(t.total_netpurchase) total_netpurchase\n" +
                " from (\n" +
                "select '" + vendorNbr + "' as supplierCode,\n" +
                "       sum(nvl(tosl.netpurchase, 0)) as total_netpurchase\n" +
                "  from tta_oi_summary_line tosl\n" +
                " where tosl.rms_code in (\n" +
                TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) + "\n" +
                "                         )\n" +
                "   and to_char(tosl.account_month, 'yyyyMM') >= '" + startMonth + "'\n" +
                "   and to_char(tosl.account_month, 'yyyyMM') <= '" + endMonth + "'\n" +
                "union all\n" +
                "select '" + vendorNbr + "' as supplierCode,\n" +
                "       (0 - tsim.purchase) as total_netpurchase\n" +
                "  from tta_supplier_item_header tsih\n" +
                "  join tta_supplier_item_mid tsim\n" +
                "    on tsih.supplier_item_id = tsim.supplier_item_h_id\n" +
                " where tsih.bill_status = 'affirm' -- 当前供应商拆分出去\n" +
                "   and tsim.supplier_code = '" + vendorNbr + "'\n" +
                "   and nvl(tsim.split_supplier_code,tsim.supplier_code) <> '" + vendorNbr + "'\n" +
                "   and tsih.proposal_year = '" + purchaseYear + "'\n" +
                "union all\n" +
                "select '" + vendorNbr + "' as supplierCode,\n" +
                "       tsim.purchase as total_netpurchase -- 其他供应商拆分给当前供应商(当前供应商是传入的条件)\n" +
                "  from tta_supplier_item_header tsih  \n" +
                "  join tta_supplier_item_mid tsim\n" +
                "    on tsih.supplier_item_id = tsim.supplier_item_h_id\n" +
                " where tsih.bill_status = 'affirm' --已确认\n" +
                "   and tsim.supplier_code <> '" + vendorNbr + "'\n" +
                "   and tsim.split_supplier_code = '" + vendorNbr + "'\n" +
                "   and tsih.proposal_year = '" + purchaseYear + "'\n" +
                "   ) t group by t.supplierCode \n" +
                "          ) rms \n" +
                "          on tcst.split_supplier_code = rms.supplierCode  \n" +
                "          where tcst.delete_symbol = '" + deleteSymbol + "' \n" +
                "          ) t\n" +
                " where t.flag = 1 and nvl(t.group_code,'-1') != '-1'\n" +
                " ) mid\n" +
                " ) tlls ";


        //从总账单查询净采购额
/*        "         left join ( --根据供应商查询净采购额\n" +
                "             select '" + vendorNbr + "' as supplierCode,\n" +
                "                    sum(nvl(tosl.netpurchase, 0)) as total_netpurchase\n" +
                "               from tta_oi_summary_line tosl\n" +
                "              where tosl.rms_code in (\n" +
                TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) + "\n" +
                "               )\n" +*/



/*        //1.判断销售方式是何种方式
        if ("PURCHASE".equals(purchType)) {//购销模式PURCHASE
            readSql ="select \n" +
                    "       tlls.brandDetails as brandDetails,\n" +
                    "       tlls.tranDate as tranDate,\n" +
                    "       tlls.purchType as purchType,\n" +
                    "       tlls.groupCode as groupCode,\n" +
                    "       tlls.groupDesc as groupDesc,\n" +
                    "       tlls.deptCode as deptCode,\n" +
                    "       tlls.deptDesc as deptDesc,\n" +
                    "       tlls.brandCn as brandCn,\n" +
                    "       tlls.brandEn as brandEn,\n" +
                    "       tlls.brandCode as brandCode,\n" +
                    "       tlls.salesAmt as salesAmt,\n" +
                    "       tlls.cancel_receiving_amt,\n" +
                    "       tlls.total_netpurchase,\n" +
                    "       tlls.total_brand_po_amt,\n" +
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.poRecord/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as poRecord, -- 采购额(不使用),\n" +
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.fcsPurchase/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as fcsPurchase, --预估未来的采购额(不使用),\n" +
                    "      decode(tlls.totol_poRecord,0,0,round((tlls.poRecord/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as poRecord, -- 采购额(含税),\n" +
                    "      decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as fcsPurchase, --预估未来的采购额(含税),\n" +
                    "       tlls.sales as sales,\n"+
                    "       tlls.fcsSales as fcsSales,\n"+
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.fcsPurchase/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as annualPurchase,\n"+
                    "       decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as annualPurchase,\n"+
                    "       tlls.fcsSales as annualSales,\n"+
                    "       tlls.actualGp as actualGp,\n" +
                    "       tlls.gp as gp\n" +
                    "       --tlls.gpa\n" +
                    " from (" +
                    " select \n" +
                    "    mid.brandDetails,\n" +
                    "    mid.tranDate,\n" +
                    "    mid.purchType,\n" +
                    "    mid.groupCode,\n" +
                    "    mid.groupDesc,\n" +
                    "    mid.deptCode,\n" +
                    "    mid.deptDesc,\n" +
                    "    mid.brandCn,\n" +
                    "    mid.brandEn,\n" +
                    "    mid.brandCode,\n" +
                    "    mid.salesAmt,\n" +
                    "    mid.cancel_receiving_amt,\n" +
                    "    mid.total_netpurchase,\n" +
                    "    mid.total_brand_po_amt,\n" +
                    "    mid.fcsPurchase,\n" +
                    "    mid.poRecord,\n" +
                    "    mid.fcsSales,\n" +
                    "    mid.sales,\n" +
                    "    mid.actualGp,\n" +
                    "    mid.gp,\n" +
                    "    --mid.gpa,\n" +
                    "    sum(mid.poRecord) over() as totol_poRecord ,\n" +
                    "    mid.brandPo_Record \n" +
                    "  from \n" +
                    " (\n" +
                    "   select \n" +
                    "       t.brand_Details as brandDetails,\n" +
                    "       t.tran_date as tranDate,\n" +
                    "       t.purch_type as purchType,\n" +
                    "       t.group_code as groupCode,\n" +
                    "       t.group_desc as groupDesc,\n" +
                    "       t.dept_code as deptCode,\n" +
                    "       t.dept_desc as deptDesc,\n" +
                    "       t.brand_cn as brandCn,\n" +
                    "       t.brand_en as brandEn,\n" +
                    "       t.brand_code as brandCode,\n" +
                    "       t.sales_amt as salesAmt,\n" +
                    "       t.cancel_receiving_amt,\n"+
                    "       t.total_netpurchase,\n" +
                    "       t.total_brand_po_amt,\n"+
                    "       t.po_Record as brandPo_Record,\n" +
                    "       round(" + sqlPurchaseStr + ",0) as fcsPurchase,--预测未来采购成本\n" +
                    "       round(t.po_Record-nvl(t.cancel_receiving_amt,0),0) as poRecord, --采购成本 \n" +
                    "       round(" + sqlSalesStr + ",0) as fcsSales, --预测未来销售总金额\n " +
                    "       round(t.TOTAL,0) as sales,--含税销售总金额\n " +
                    "       round(t.total_notax,0) as notax_sales,--不含税销售总金额\n " +
                    "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as actualGp, --计算不含税\n" +
                    "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as gp --\n" +
                    "       --case when t.TOTAL is null or t.total = 0 then 0 else round(t.gpr / t.TOTAL * 100 , 2) end as gpa\n" +
                    "  from (\n" +
                    "        select \n" +
                    "               'Existing_Brand' as brand_Details,\n" +
                    "               tcst.tran_date,\n" +
                    "               tcst.vendor_nbr,\n" +
                    "               tcst.purch_type,--采购类型\n" +
                    "               tcst.group_code,\n" +
                    "               tcst.group_desc,\n" +
                    "               tcst.dept_code,\n" +
                    "               tcst.dept_desc,\n" +
                    "               tcst.brand_cn,\n" +
                    "               tcst.brand_en,\n" +
                    "               tcst.brand_code,\n" +
                    "               tcst.sales_amt, --最先交易日期的销售金额\n" +
                    "               nvl(tpur.cancel_receiving_amt,0) cancel_receiving_amt,\n"+
                    "               nvl(rms.total_netpurchase,0) total_netpurchase, --某个供应商和关联供应商的净采购额,\n" +
                    "               sum(tcst.po_amt) over() total_brand_po_amt, --供应商所以品牌采购总额,\n" +
                    "               sum(tcst.po_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as po_Record, --采购成本\n" +
                    "               sum(tcst.gp_supplier_popt_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as gpr, --不含税销售毛利额\n" +
                    "               sum(tcst.sales_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total, --含税销售总额\n" +
                    "               sum(tcst.sales_exclude_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total_notax, --不含税销售总额\n" +
                    "               row_number() over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en order by tcst.tran_date) flag\n" +
                    "          from tta_collect_sales_temp tcst \n" +
                    "       left join\n" +
                    "          (\n" +
                    "               select * from (\n" +
                    "                select \n" +
                    "                     tit.group_code,\n" +
                    "                     tit.group_desc,\n" +
                    "                     tit.dept_code,\n" +
                    "                     tit.dept_desc,\n" +
                    "                     tit.brand_code,\n" +
                    "                     tit.brand_cn,\n" +
                    "                     tit.brand_en,\n" +
                    "                     tss.receiving_amount,\n" +
                    "                     tss.vendor_nbr, \n" +
                    "                     '"+vendorNbr+"' as split_supplier_code, \n" +
                    "                     tss.purch_type,\n" +
                    "                     tss.po_type,\n" +
                    "                     sum(tss.receiving_amount) over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en) AS cancel_receiving_amt, --退货总金额\n" +
                    "                     row_number() over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en order by tss.vendor_nbr) purchase_flag\n" +
                    "                     from  tta_purchase_in_"+purchaseYear+" tss left join (\n" +
                    "                                 select *\n" +
                    "                                   from (SELECT t1.item_nbr,\n" +
                    "                                                t1.group_code,\n" +
                    "                                                t1.group_desc,\n" +
                    "                                                t1.dept_code,\n" +
                    "                                                t1.dept_desc, \n" +
                    "                                                t1.brand_code,                               \n" +
                    "                                                t1.brand_cn,\n" +
                    "                                                t1.brand_en,\n" +
                    "                                                ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.last_update_date) row_id\n" +
                    "                                           FROM tta_item t1\n" +
                    "                                          order by t1.last_update_date desc) t2\n" +
                    "                                  where t2.row_id = 1\n" +
                    "                            ) tit\n" +
                    "                      on tss.item_nbr = tit.item_nbr \n" +
                    //注释,改为拆分供应商"                      where tss.po_type = 'RETRUN' and tss.purch_type = 'PURCHASE' and tss.vendor_nbr = '"+vendorNbr+"'\n" +
                    "                      where tss.po_type = 'RETRUN' and tss.purch_type = '"+purchType+"' and tss.split_supplier_code in (\n" +
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) +
                    ") \n" +
                    "                      ) tpt  where tpt.purchase_flag = 1\n" +
                    "                                 \n" +
                    "          ) tpur on tcst.group_code =tpur.group_code and  tcst.dept_code =tpur.dept_code and  tcst.brand_cn =tpur.brand_cn\n" +
                    // 注释,改为拆分供应商"          and   tcst.brand_en = tpur.brand_en and tcst.vendor_nbr = tpur.vendor_nbr " +
                    "          and   tcst.brand_en = tpur.brand_en and tcst.split_supplier_code = tpur.split_supplier_code \n" +
                    "left join ( --根据供应商查询净采购额\n" +
                    "             select '"+vendorNbr+"' as supplierCode,\n" +
                    "                    sum(nvl(tosl.netpurchase, 0)) as total_netpurchase\n" +
                    "               from tta_oi_summary_line tosl\n" +
                    "              where tosl.rms_code in (\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) +
                    "               )\n" +
                    "                and to_char(tosl.account_month, 'yyyyMM') >= '"+startMonth+"'\n" +
                    "                and to_char(tosl.account_month, 'yyyyMM') <= '"+endMonth+"'\n" +
                    "          ) rms on tcst.split_supplier_code = rms.supplierCode " +
                    " where tcst.delete_symbol = '"+deleteSymbol+"' \n" +
                    "          ) t\n" +
                    " where t.flag = 1 and t.group_code is not null\n" +
                    " ) mid\n"+
                    " ) tlls\n";


        } else {//CVW,CONSIGN 模式
            if ("CONSIGN".equals(purchType)){
                purchType = "CONSIGNMENT";
            }
            readSql = "select \n" +
                    "       tlls.brandDetails as brandDetails,\n" +
                    "       tlls.tranDate as tranDate,\n" +
                    "       tlls.purchType as purchType,\n" +
                    "       tlls.groupCode as groupCode,\n" +
                    "       tlls.groupDesc as groupDesc,\n" +
                    "       tlls.deptCode as deptCode,\n" +
                    "       tlls.deptDesc as deptDesc,\n" +
                    "       tlls.brandCn as brandCn,\n" +
                    "       tlls.brandEn as brandEn,\n" +
                    "       tlls.brandCode as brandCode,\n" +
                    "       tlls.salesAmt as salesAmt,\n" +
                    "       tlls.cancel_receiving_amt,\n" +
                    "       tlls.total_netpurchase,\n" +
                    "       tlls.total_brand_po_amt,\n" +
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.poRecord/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as poRecord, -- 采购额,\n" +
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.fcsPurchase/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as fcsPurchase, --预估未来的采购额,\n" +
                    "      decode(tlls.totol_poRecord,0,0,round((tlls.poRecord/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as poRecord, -- 采购额,\n" +
                    "      decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as fcsPurchase, --预估未来的采购额,\n" +
                    "       tlls.sales as sales,\n" +
                    "       tlls.fcsSales as fcsSales,\n" +
                    "       --decode(tlls.total_brand_po_amt,0,0,round((tlls.fcsPurchase/tlls.total_brand_po_amt) * tlls.total_netpurchase,0)) as annualPurchase,\n"+
                    "      decode(tlls.totol_poRecord,0,0,round((tlls.fcsPurchase/tlls.totol_poRecord) * tlls.total_netpurchase,0)) as annualPurchase,\n"+
                    "       tlls.fcsSales as annualSales,\n"+
                    "       tlls.actualGp,\n" +
                    "       tlls.gp\n" +
                    "       --tlls.gpa\n" +
                    " from (" +
                    " select \n" +
                    "    mid.brandDetails,\n" +
                    "    mid.tranDate,\n" +
                    "    mid.purchType,\n" +
                    "    mid.groupCode,\n" +
                    "    mid.groupDesc,\n" +
                    "    mid.deptCode,\n" +
                    "    mid.deptDesc,\n" +
                    "    mid.brandCn,\n" +
                    "    mid.brandEn,\n" +
                    "    mid.brandCode,\n" +
                    "    mid.salesAmt,\n" +
                    "    mid.cancel_receiving_amt,\n" +
                    "    mid.total_netpurchase,\n" +
                    "    mid.total_brand_po_amt,\n" +
                    "    mid.fcsPurchase,\n" +
                    "    mid.poRecord,\n" +
                    "    mid.fcsSales,\n" +
                    "    mid.sales,\n" +
                    "    mid.actualGp,\n" +
                    "    mid.gp,\n" +
                    "    --mid.gpa,\n" +
                    "    sum(mid.poRecord) over() as totol_poRecord ,\n" +
                    "    mid.brandPo_Record \n" +
                    "  from \n" +
                    " (\n" +
                    "    select \n" +
                    "       t.brand_Details as brandDetails,\n" +
                    "       t.tran_date as tranDate,\n" +
                    "       t.purch_type as purchType,\n" +
                    "       t.group_code as groupCode,\n" +
                    "       t.group_desc as groupDesc,\n" +
                    "       t.dept_code as deptCode,\n" +
                    "       t.dept_desc as deptDesc,\n" +
                    "       t.brand_cn as brandCn,\n" +
                    "       t.brand_en as brandEn,\n" +
                    "       t.brand_code as brandCode,\n" +
                    "       t.sales_amt as salesAmt,\n" +
                    "       t.cancel_receiving_amt,\n"+
                    "       t.total_netpurchase,\n" +
                    "       t.total_brand_po_amt,\n"+
                    "       t.po_Record as brandPo_Record,\n" +
                    "       round(" + sqlPurchaseStr + ",0) as fcsPurchase,--预测未来采购成本\n" +
                    "       round(t.po_Record-nvl(t.cancel_receiving_amt,0),0) as poRecord, --采购成本 \n" +
                    "       round(" + sqlSalesStr + ",0) as fcsSales, --预测未来销售总金额\n " +
                    "       round(t.TOTAL,0) as sales,--销售总金额\n " +
                    "       round(t.total_notax,0) as notax_sales,--不含税销售总金额\n " +
                    "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as actualGp, --计算不含税\n" +
                    "       case when nvl(t.total_notax,-1) = -1 or t.total_notax = 0 then 0 else round(t.gpr / t.total_notax * 100 , 2) end as gp --\n" +
                    "       --case when t.TOTAL is null or t.total = 0 then 0 else round(t.gpr / t.TOTAL * 100 , 2) end as gpa\n" +
                    "  from (\n" +
                    "        select \n" +
                    "               'Existing_Brand' as brand_Details,\n" +
                    "               tcst.tran_date,\n" +
                    "               tcst.vendor_nbr,\n" +
                    "               tcst.purch_type,--采购类型\n" +
                    "               tcst.group_code,\n" +
                    "               tcst.group_desc,\n" +
                    "               tcst.dept_code,\n" +
                    "               tcst.dept_desc,\n" +
                    "               tcst.brand_cn,\n" +
                    "               tcst.brand_en,\n" +
                    "               tcst.brand_code,\n" +
                    "               tcst.sales_amt, --最先交易日期的销售金额\n" +
                    "               nvl(tpur.cancel_receiving_amt,0) cancel_receiving_amt,\n"+
                    "               nvl(rms.total_netpurchase,0) total_netpurchase, --某个供应商和关联供应商的净采购额,\n" +
                    "               sum(tcst.cost) over() total_brand_po_amt, --供应商所以品牌采购总额,\n" +
                    "               sum(tcst.cost) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as po_Record, --CVW,CONSIGN模式的采购成本\n" +
                    "               -- sum(tcst.po_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as po_Record, --采购成本\n" +
                    "               sum(tcst.gp_supplier_popt_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) as gpr, --不含税销售毛利额\n" +
                    "               sum(tcst.sales_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total, --销售总额\n" +
                    "               sum(tcst.sales_exclude_amt) over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en) AS total_notax, --不含税销售总额\n" +
                    "               row_number() over(partition by tcst.group_code, tcst.dept_code, tcst.brand_cn, tcst.brand_en order by tcst.tran_date) flag\n" +
                    "          from tta_collect_sales_temp tcst " +
                    "       left join\n" +
                    "          (\n" +
                    "               select * from (\n" +
                    "                select \n" +
                    "                     tit.group_code,\n" +
                    "                     tit.group_desc,\n" +
                    "                     tit.dept_code,\n" +
                    "                     tit.dept_desc,\n" +
                    "                     tit.brand_code,\n" +
                    "                     tit.brand_cn,\n" +
                    "                     tit.brand_en,\n" +
                    "                     tss.receiving_amount,\n" +
                    "                     tss.vendor_nbr, \n" +
                    "                     '"+vendorNbr+"' as split_supplier_code, \n" +
                    "                     tss.purch_type,\n" +
                    "                     tss.po_type,\n" +
                    "                     sum(tss.receiving_amount) over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en) AS cancel_receiving_amt, --退货总金额\n" +
                    "                     row_number() over(partition by tit.group_code, tit.dept_code, tit.brand_cn, tit.brand_en order by tss.vendor_nbr) purchase_flag\n" +
                    "                     from  tta_purchase_in_"+purchaseYear+" tss left join (\n" +
                    "                                 select *\n" +
                    "                                   from (SELECT t1.item_nbr,\n" +
                    "                                                t1.group_code,\n" +
                    "                                                t1.group_desc,\n" +
                    "                                                t1.dept_code,\n" +
                    "                                                t1.dept_desc, \n" +
                    "                                                t1.brand_code,                               \n" +
                    "                                                t1.brand_cn,\n" +
                    "                                                t1.brand_en,\n" +
                    "                                                ROW_NUMBER() OVER(PARTITION BY t1.item_nbr ORDER BY t1.item_nbr) row_id\n" +
                    "                                           FROM tta_item t1\n" +
                    "                                          order by t1.last_update_date desc) t2\n" +
                    "                                  where t2.row_id = 1\n" +
                    "                            ) tit\n" +
                    "                      on tss.item_nbr = tit.item_nbr \n" +
                    //"                      where tss.po_type = 'RETRUN' and tss.purch_type = 'PURCHASE' and tss.vendor_nbr = '"+vendorNbr+"'\n" +
                    "                      where tss.po_type = 'RETRUN' and tss.purch_type = '"+purchType+"' and tss.split_supplier_code in (\n" +
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr) +
                    ") \n" +
                    "                      ) tpt  where tpt.purchase_flag = 1\n" +
                    "                                 \n" +
                    "          ) tpur on tcst.group_code =tpur.group_code and  tcst.dept_code =tpur.dept_code and  tcst.brand_cn =tpur.brand_cn\n" +
                    // 注释,改为拆分供应商"          and   tcst.brand_en = tpur.brand_en and tcst.vendor_nbr = tpur.vendor_nbr " +
                    "          and   tcst.brand_en = tpur.brand_en and tcst.split_supplier_code = tpur.split_supplier_code \n" +
                    "left join ( --根据供应商查询净采购额\n" +
                    "             select '"+vendorNbr+"' as supplierCode,\n" +
                    "                    sum(nvl(tosl.netpurchase, 0)) as total_netpurchase\n" +
                    "               from tta_oi_summary_line tosl\n" +
                    "              where tosl.rms_code in (\n"+
                    TtaBrandplnHEntity_HI_RO.getAllVendorNbrByVendorNbrWhere(vendorNbr)
                    +              ")\n" +
                    "                and to_char(tosl.account_month, 'yyyyMM') >= '"+startMonth+"'\n" +
                    "                and to_char(tosl.account_month, 'yyyyMM') <= '"+endMonth+"'\n" +
                    "          ) rms on tcst.split_supplier_code = rms.supplierCode " +
                    " where tcst.delete_symbol = '"+deleteSymbol+"' \n" +
                    "          ) t\n" +
                    " where t.flag = 1 and t.group_code is not null\n" +
                    " ) mid\n"+
                    " ) tlls\n";
        }*/
		LOGGER.info("获取品牌计划的sql:\n{}",readSql);
        return readSql;
    }

    //更新品牌计划汇总数据
    @Override
    public int brandplnHUpdate(JSONObject jsonObject, int userId) {
        Integer brandplnHId = jsonObject.getInteger("brandplnHId");
        int updateCount = 0;
        //当查找不到数据,为空时默认为0
        String brandpSql = "update tta_brandpln_header T\n" +
                "           SET \n" +
                "           (\n" +
                "             T.Po_Record_Sum,\n" +
                "             T.Sales_Sum,\n" +
                "             T.Actual_Gp,\n" +
                "             T.Po_Record_Sum2,\n" +
                "             T.Sales_Sum2,\n" +
                "             T.Actual_Gp2,\n" +
                "             T.Fcs_Purchase, \n" +
                "             T.Purchase_Growth,\n" +
                "             T.Fcs_Sales,\n" +
                "             T.Sales_Growth,\n" +
                "             T.Gp,            \n" +
                "             T.Is_Split,\n" +
                "             T.Fcs_Split_Purchse,\n" +
                "             t.fcs_split_sales,\n" +
                "             t.split_gp\n" +
                "           ) = (\n" +
                "             select              --sum(nvl(t1.po_record,0)),\n" +
                "             --sum(nvl(t1.sales,0)),\n" +
                "             nvl(sum(nvl(t1.annual_purchase,0)),0),\n" +
                "             nvl(sum(nvl(t1.annual_sales,0)),0),\n" +
                "             nvl(round(decode(sum(nvl(t1.sales, 0)),\n" +
                "                    0,\n" +
                "                    0,\n" +
                "                    sum(nvl(t1.sales, 0) * nvl(t1.actual_gp, 0)) /\n" +
                "                    sum(t1.sales)),\n" +
                "             2),0) as actual_gp,\n" +
                "             nvl(sum(nvl(t1.po_record2,0)),0),\n" +
                "             nvl(sum(nvl(t1.sales2,0)),0),\n" +
                "             nvl(avg(nvl(t1.actual_gp2,0)),0),\n" +
                "             nvl(sum(nvl(t1.Fcs_Purchase,0)),0),\n" +
                "             nvl(decode(sum(nvl(t1.annual_purchase,0)),0,0,(sum(nvl(t1.Fcs_Purchase,0)) / sum(nvl(t1.annual_purchase,0)) - 1 ) * 100),0) as purchase_growth,\n" +
                "             nvl(sum(nvl(t1.Fcs_Sales,0)),0), \n" +
                "             nvl(decode(sum(nvl(t1.annual_sales,0)),0,0,( sum(nvl(t1.Fcs_Sales,0)) / sum(nvl(t1.annual_sales,0)) - 1) * 100 ),0) as Sales_Growth,\n" +
                "             nvl(round(decode(sum(nvl(t1.fcs_sales, 0)),\n" +
                "                    0,\n" +
                "                    0,\n" +
                "                    sum(nvl(t1.fcs_sales, 0) * nvl(t1.gp, 0)) /\n" +
                "                    sum(t1.fcs_sales)),\n" +
                "             2),0) as gp,\n" +
                "             'N',\n" +
                "             nvl(sum(nvl(t1.Fcs_Purchase,0)),0),\n" +
                "             nvl(sum(nvl(t1.Fcs_Sales,0)),0),\n" +
                "             nvl(round(decode(sum(nvl(t1.fcs_sales, 0)),\n" +
                "                    0,\n" +
                "                    0,\n" +
                "                    sum(nvl(t1.fcs_sales, 0) * nvl(t1.gp, 0)) /\n" +
                "                    sum(t1.fcs_sales)),\n" +
                "             2),0) as split_gp\n" +
                "             from tta_brandpln_line t1 where t1.brandpln_h_id =" + brandplnHId + "\n" +
                "           )  WHERE T.BRANDPLN_H_ID =" + brandplnHId;
        updateCount = ttaBrandplnHDAO_HI.executeSqlUpdate(brandpSql);
        return updateCount;
    }

}
