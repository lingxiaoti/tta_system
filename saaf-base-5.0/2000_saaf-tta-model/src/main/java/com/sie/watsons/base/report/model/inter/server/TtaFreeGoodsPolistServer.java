package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

import com.aspose.words.Run;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.report.model.dao.TtaFreeGoodsPolistDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsPolistEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsPolistEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaFreeGoodsPolist;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.report.utils.ExcelImportUtils;
import com.sie.watsons.base.report.utils.JdbcUtils;
import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import static com.sie.watsons.base.report.utils.JdbcUtils.rollbackTransaction;

@Component("ttaFreeGoodsPolistServer")
public class TtaFreeGoodsPolistServer extends BaseCommonServer<TtaFreeGoodsPolistEntity_HI> implements ITtaFreeGoodsPolist {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsPolistServer.class);
    @Autowired
    private ViewObject<TtaFreeGoodsPolistEntity_HI> ttaFreeGoodsPolistDAO_HI;

    @Autowired
    private BaseViewObject<TtaFreeGoodsPolistEntity_HI_RO> ttaFreeGoodsPolistDAO_HI_RO;

    @Autowired
    private TtaFreeGoodsPolistDAO_HI ttaFreeGoodsPolistDAOHi;

    @Autowired
    private BaseViewObject<TtaItemEntity_HI_RO> ttaItemDAO_HI_RO;

    @Autowired
    BaseCommonDAO_HI<TtaFreeGoodsPolistEntity_HI> baseCommonDAO;

    @Autowired
    GenerateCodeService codeService;

    @Autowired
    private JedisCluster jedisCluster;

    private JSONObject importParam = new JSONObject();

    private UserSessionBean userSessionBean = null;

    private List<TtaItemEntity_HI_RO> itemEntity_hi_ros = null;

    public TtaFreeGoodsPolistServer() {
        super();
    }

    /**
     * 批量导入po_list数据
     *
     * @param queryParamJSON
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public int saveImportInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception {
        LOGGER.info("*********************导入FreeGoodsPolist数据开始****************");
        Integer[] insetNum = {0};
        try {
            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'U'}");
            importParam = queryParamJSON;
            userSessionBean = sessionBean;
            String polist = jedisCluster.get("freegoodsPolist");
            if (StringUtils.isBlank(polist)) {
                //先查询TTA_ITEM的数据
                StringBuffer sql = new StringBuffer();
                sql.append(TtaItemEntity_HI_RO.TTA_ITEM_QUERY);
                Map<String, Object> paramsMap = new HashMap<String, Object>();
                sql.append(" order by ti.item_nbr asc");
                long startTime = System.currentTimeMillis();
                itemEntity_hi_ros = ttaItemDAO_HI_RO.findList(sql, paramsMap);
                long endTime = System.currentTimeMillis();
                LOGGER.info("导入freegoodPolist查询TTA_ITEM表花费的时间====>{}s",(endTime - startTime) / 1000);
                jedisCluster.setex("freegoodsPolist",86400,JSON.toJSONString(itemEntity_hi_ros));//失效时间为一天
            } else {
                itemEntity_hi_ros = JSONObject.parseArray(polist,TtaItemEntity_HI_RO.class);
            }
            if (file != null) {
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("<----------导入数据开始------------>");
                }
                long startTime = System.currentTimeMillis();
                JdbcUtils.beginTransaction();//开启事务
                AnalysisEventListener<Map<String,Object>> userAnalysisEventListener = ExcelImportUtils.getListener(this.insertBatchData(), 15000,jedisCluster,sessionBean,insetNum);
                EasyExcelUtil.readExcel(file, TtaFreeGoodsPolistEntity_HI_MODEL.class, 0, userAnalysisEventListener);
                JdbcUtils.commitTransaction();//提交事务
                jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");
                long endTime = System.currentTimeMillis();
                LOGGER.info("freeGoods导入数据总花费时间:{}s,",(endTime - startTime) / 1000);
            }
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("<----------导入数据完成------------>");
            }
        } catch (Exception e) {
            try {
                JdbcUtils.rollbackTransaction();//回滚事务
            } catch (SQLException e1) {
                throw new RuntimeException(e1.getMessage());
            }
            //e.printStackTrace();
            LOGGER.error("freegoods导入错误：{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            JdbcUtils.closeConnection();//关闭连接
        }
        LOGGER.info("*********************导入FreeGoodsPolist数据结束****************");
        return insetNum[0];
    }

    /**
     * 批量保存
     * 使用示例:ExcelImportUtils.getListener(insertBatchData(), 15000,jedisCluster,sessionBean)
     * Consumer函数接口接受一个参数,不返回结果
     * @return 没有返回值
     * @throws Exception
     */
   private Consumer<List<Map<String,Object>>> batchInsert() throws Exception {
        Consumer<List<Map<String,Object>>> listConsumer = datas -> {
            try {
                saveData(datas);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e.getMessage());
            }
        };
        return listConsumer;
    }

    /**
     * 批量插入
     * 使用Function函数接口,接受一个参数对象,返回一个参数对象,有返回值
     * @return 有返回值,返回值是数量
     * @throws Exception
     */
    private Function<List<Map<String,Object>>,Integer> insertBatchData() throws Exception{
        return datas -> {
            try {
                return saveFreeGoodsData(datas);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
                throw new IllegalArgumentException(e.getMessage());
            }
        };
        //return this::saveFreeGoodsData;//第二种调用方式
    }

    private Integer saveFreeGoodsData(List<Map<String,Object>> datas) throws Exception {
        Assert.notNull(importParam,"参数转换异常");
        Assert.notNull(userSessionBean,"用户信息缺失,导入失败");
        JSONArray errList = new JSONArray();
        List<Map<String, Object>> poList = null;
        if (datas != null && datas.size() > 0) {
            poList = new ArrayList<>();
            String batchCode = codeService.getFeeGoodBatchCode();
            Integer year = LocalDate.now().getYear();//当前年度
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> json = datas.get(i);
                json.put("CREATED_BY", importParam.getInteger("varUserId"));
                json.put("CREATION_DATE", new Date());
                json.put("LAST_UPDATED_BY", importParam.getInteger("varUserId"));
                json.put("LAST_UPDATE_DATE", new Date());
                json.put("LAST_UPDATE_LOGIN", importParam.getInteger("varUserId"));
                json.put("VERSION_NUM", 0);
                json.put("EXPORT_BATCH",batchCode);//导入批次号
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.putAll(json);
                objectMap.remove("EXPORT_BATCH");
                objectMap.put("REL_WEEK", objectMap.get("WEEK"));
                objectMap.put("SUPPLIER_CODE", objectMap.get("SUPPLIER"));
                objectMap.put("SUPPLIER_NAME", objectMap.get("SUP_NAME"));
                objectMap.put("REL_PO", objectMap.get("ORDER_NO"));
                objectMap.put("REL_COMMENTS", objectMap.get("COMMENTS"));
                objectMap.put("ITEM_CODE", objectMap.get("ITEM"));
                String itemDescCn = "";
                String brandCn = "";
                String groupDesc = "";
                String deptDesc = "";
                String groupCode = "";
                String brandEn = "";
                String deptCode = "";
                String item = (String) objectMap.get("ITEM");
                TtaItemEntity_HI_RO itemEntityHiRo = binarySearch(item);
                if (null != itemEntityHiRo) {
                    groupDesc = itemEntityHiRo.getGroupDesc();
                    deptDesc = itemEntityHiRo.getDeptDesc();
                    itemDescCn = itemEntityHiRo.getItemDescCn();
                    brandCn = itemEntityHiRo.getBrandCn();
                    groupCode = itemEntityHiRo.getGroupCode() + "";
                    brandEn = itemEntityHiRo.getBrandEn();
                    deptCode = itemEntityHiRo.getDeptCode() + "";
                }
                objectMap.put("ITEM_NAME", itemDescCn);
                objectMap.put("BRAND", brandCn);
                objectMap.put("GROUP_DESC", groupDesc);
                objectMap.put("DEPT_DESC", deptDesc);
                objectMap.put("BRAND_EN", brandEn);
                objectMap.put("GROUP_CODE", groupCode);
                objectMap.put("DEPT_CODE", deptCode);

                objectMap.put("REL_UNIT_COST", objectMap.get("UNIT_COST"));
                objectMap.put("REL_QTY_RECEIVED", objectMap.get("QTY_RECEIVED"));
                //计算实收金额
                BigDecimal num1 = new BigDecimal((SaafToolUtils.isNullOrEmpty(objectMap.get("UNIT_COST_INIT")) || (objectMap.get("UNIT_COST_INIT") + "").equals("NULL"))  ?  "0" : objectMap.get("UNIT_COST_INIT") + "");
                BigDecimal num2 = new BigDecimal((SaafToolUtils.isNullOrEmpty(objectMap.get("QTY_RECEIVED")) || (objectMap.get("QTY_RECEIVED") + "").equals("NULL")) ? "0" : objectMap.get("QTY_RECEIVED") + "");
                BigDecimal multiply = num1.multiply(num2);
                objectMap.put("ACTUAL_MONEY", String.valueOf(multiply));
                String comments = (String) objectMap.get("COMMENTS");
                if (comments.indexOf("合同") > -1 || comments.indexOf("TTA") > -1) {
                    objectMap.put("REL_RDER_TYPE", "合同免费货");
                } else if (comments.indexOf("试用") > -1) {
                    objectMap.put("REL_RDER_TYPE", "试用装免费货");
                } else {
                    objectMap.put("REL_RDER_TYPE", "促销免费货");
                }
                String chargeYear = (String) objectMap.get("CHARGE_YEAR");//获取费用年度
                if (StringUtils.isBlank(chargeYear)) {
                    objectMap.put("IS_CALCULATE", "N");//不加入计算
                    json.put("IS_CALCULATE", "N");
                    objectMap.put("OPEN_SELECT", "Y");//不加入计算就开发给采购选择
                } else {
                    objectMap.put("IS_CALCULATE","Y");//加入计算
                    json.put("IS_CALCULATE","Y");
                    objectMap.put("OPEN_SELECT","N");
                }
                if ("合同免费货".equals(objectMap.get("REL_RDER_TYPE"))) {
                    objectMap.put("IS_CALCULATE", "Y");
                    json.put("IS_CALCULATE", "Y");
                    objectMap.put("OPEN_SELECT", "N");//不开放给采购选择
                    objectMap.put("CHARGE_YEAR",(StringUtils.isNotEmpty(chargeYear) ? Integer.parseInt(chargeYear) : year ) );
                    json.put("CHARGE_YEAR",(StringUtils.isNotEmpty(chargeYear) ? Integer.parseInt(chargeYear) : year ) );
                }
                objectMap.put("TIMES_VERSION", batchCode);//时间版本
                poList.add(objectMap);

                JSONObject errJson = new JSONObject();
                String msgStr = "";
                try {
                    if (SaafToolUtils.isNullOrEmpty(json.get("WEEK"))) {
                        msgStr += "导入WEEK字段不能为空";
                    }
                    if (!"".equals(msgStr)) {
                        errJson.put("ROW_NUM", json.get("ROW_NUM"));
                        errJson.put("ERR_MESSAGE", msgStr);
                        errList.add(errJson);
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
                throw new Exception(errList.toJSONString());
            } else {
                //保存数据之前,先校验是否重复导入数据
                StringBuffer checkSql = new StringBuffer(TtaFreeGoodsPolistEntity_HI_RO.getNoEqualSql(batchCode));
                List<TtaFreeGoodsPolistEntity_HI_RO> noExitsPolist = ttaFreeGoodsPolistDAO_HI_RO.findList(checkSql, new HashMap<>());

                ArrayList<String> repeatList = new ArrayList<>();
                for (int j = 0; j < datas.size(); j++) {
                    Map<String, Object> map = datas.get(j);
                    Integer week = Integer.valueOf(map.get("WEEK") + "");//周期
                    String supplier = map.get("SUPPLIER") + "";//供应商编号
                    String item = map.get("ITEM") + "";
                    String orderNo = map.get("ORDER_NO") + "";//po单号
                    for (TtaFreeGoodsPolistEntity_HI_RO polistEntityHiRo : noExitsPolist) {
                        /*if (week.equals(polistEntityHiRo.getWeek()) && supplier.equals(polistEntityHiRo.getSupplier())
                            && item.equals(polistEntityHiRo.getItem())) {
                            String msg = "\nWEEK[" + week + "],SUPPLIER CODE[" + supplier + "],ITEM CODE[" + item + "]\n";
                            repeatList.add(msg);
                        }*/
                        if (orderNo.equals(polistEntityHiRo.getOrderNo()) && item.equals(polistEntityHiRo.getItem())) {
                            String msg = "PO[" + orderNo + "]--ITEM CODE[" + item + "]";
                            repeatList.add(msg);
                        }
                    }
                }

                if (!repeatList.isEmpty()) {
                    String msgRemark = "系统中已存在相同的,\n" + org.apache.commons.lang3.StringUtils.join(repeatList,";\n") + "的数据,不允许重复导入";
                    throw new RuntimeException(msgRemark);
                }

                List<Map<String, Object>> batchList = new ArrayList<>();
                List<Map<String, Object>> batchPoList = new ArrayList<>();
                int count = 0;
                int poCount = 0;
                int rownum = 5000;
                for (int a = 0; a < datas.size(); a++) {
                    batchList.add(datas.get(a));
                    if (batchList.size() % rownum == 0) {
                        count++;
                        ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_POLIST", batchList, "ID", "seq_tta_free_goods_polist.NEXTVAL","导入步骤",jedisCluster,userSessionBean);
                        LOGGER.info("操作的表:{},批量保存数:{}", "TTA_FREE_GOODS_POLIST", rownum * count);
                        batchList = new ArrayList<>();
                    }
                }

                for (int i = 0; i < poList.size(); i++) {
                    batchPoList.add(poList.get(i));
                    if (batchPoList.size() % rownum == 0) {
                        poCount++;
                        ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_ORDER_DETAIL", batchPoList, "ID", "seq_tta_free_goods_o_d.NEXTVAL","导入步骤");
                        LOGGER.info("操作的表:{},批量保存数:{}", "TTA_FREE_GOODS_ORDER_DETAIL", rownum * poCount);
                        batchPoList = new ArrayList<>();
                    }
                }

                ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_POLIST", batchList, "ID", "seq_tta_free_goods_polist.NEXTVAL","导入步骤",jedisCluster,userSessionBean);
                ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_ORDER_DETAIL", batchPoList, "ID", "seq_tta_free_goods_o_d.NEXTVAL","导入步骤");
            }
        }
        return Objects.requireNonNull(datas).size();
    }

    private void saveData(List<Map<String,Object>> datas) throws Exception {
        Assert.notNull(importParam,"参数转换异常");
        Assert.notNull(userSessionBean,"用户信息缺失,导入失败");
        JSONArray errList = new JSONArray();
        List<Map<String, Object>> poList = null;
        if (datas != null && datas.size() > 0) {
            poList = new ArrayList<>();
            String batchCode = codeService.getFeeGoodBatchCode();
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> json = datas.get(i);
                json.put("CREATED_BY", importParam.getInteger("varUserId"));
                json.put("CREATION_DATE", new Date());
                json.put("LAST_UPDATED_BY", importParam.getInteger("varUserId"));
                json.put("LAST_UPDATE_DATE", new Date());
                json.put("LAST_UPDATE_LOGIN", importParam.getInteger("varUserId"));
                json.put("VERSION_NUM", 0);
                json.put("EXPORT_BATCH",batchCode);//导入批次号
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.putAll(json);
                objectMap.remove("EXPORT_BATCH");
                objectMap.put("REL_WEEK", objectMap.get("WEEK"));
                objectMap.put("SUPPLIER_CODE", objectMap.get("SUPPLIER"));
                objectMap.put("SUPPLIER_NAME", objectMap.get("SUP_NAME"));
                objectMap.put("REL_PO", objectMap.get("ORDER_NO"));
                objectMap.put("REL_COMMENTS", objectMap.get("COMMENTS"));
                objectMap.put("ITEM_CODE", objectMap.get("ITEM"));
                String itemDescCn = "";
                String brandCn = "";
                String groupDesc = "";
                String deptDesc = "";
                String item = (String) objectMap.get("ITEM");
                TtaItemEntity_HI_RO itemEntityHiRo = binarySearch(item);
                if (null != itemEntityHiRo) {
                    groupDesc = itemEntityHiRo.getGroupDesc();
                    deptDesc = itemEntityHiRo.getDeptDesc();
                    itemDescCn = itemEntityHiRo.getItemDescCn();
                    brandCn = itemEntityHiRo.getBrandCn();
                }
                objectMap.put("ITEM_NAME", itemDescCn);
                objectMap.put("BRAND", brandCn);
                objectMap.put("GROUP_DESC", groupDesc);
                objectMap.put("DEPT_DESC", deptDesc);
                objectMap.put("REL_UNIT_COST", objectMap.get("UNIT_COST"));
                objectMap.put("REL_QTY_RECEIVED", objectMap.get("QTY_RECEIVED"));
                //计算实收金额
                BigDecimal num1 = new BigDecimal((String) objectMap.get("UNIT_COST_INIT"));
                BigDecimal num2 = new BigDecimal((String) objectMap.get("QTY_RECEIVED"));
                BigDecimal multiply = num1.multiply(num2);
                objectMap.put("ACTUAL_MONEY", String.valueOf(multiply));
                String comments = (String) objectMap.get("COMMENTS");
                if (comments.indexOf("合同") > -1 || comments.indexOf("TTA") > -1) {
                    objectMap.put("REL_RDER_TYPE", "合同免费货");
                } else if (comments.indexOf("试用") > -1) {
                    objectMap.put("REL_RDER_TYPE", "试用装免费货");
                } else {
                    objectMap.put("REL_RDER_TYPE", "促销免费货");
                }
                String chargeYear = (String) objectMap.get("CHARGE_YEAR");//获取费用年度
                if (StringUtils.isBlank(chargeYear)) {
                    objectMap.put("IS_CALCULATE", "N");//不加入计算
                    json.put("IS_CALCULATE", "N");
                    objectMap.put("OPEN_SELECT", "Y");//不加入计算就开发给采购选择
                }
                if ("合同免费货".equals(objectMap.get("REL_RDER_TYPE"))) {
                    objectMap.put("IS_CALCULATE", "Y");
                    json.put("IS_CALCULATE", "Y");
                    objectMap.put("OPEN_SELECT", "N");
                }
                objectMap.put("TIMES_VERSION", batchCode);//时间版本
                poList.add(objectMap);

                JSONObject errJson = new JSONObject();
                String msgStr = "";
                try {
                    if (SaafToolUtils.isNullOrEmpty(json.get("WEEK"))) {
                        msgStr += "导入WEEK字段不能为空";
                    }
                    if (!"".equals(msgStr)) {
                        errJson.put("ROW_NUM", json.get("ROW_NUM"));
                        errJson.put("ERR_MESSAGE", msgStr);
                        errList.add(errJson);
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
                throw new Exception(errList.toJSONString());
            } else {
                List<Map<String, Object>> batchList = new ArrayList<>();
                List<Map<String, Object>> batchPoList = new ArrayList<>();
                int count = 0;
                int poCount = 0;
                int rownum = 5000;
                for (int a = 0; a < datas.size(); a++) {
                    batchList.add(datas.get(a));
                    if (batchList.size() % rownum == 0) {
                        count++;
                        ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_POLIST", batchList, "ID", "seq_tta_free_goods_polist.NEXTVAL","导入步骤",jedisCluster,userSessionBean);
                        LOGGER.info("操作的表:{},批量保存数:{}", "TTA_FREE_GOODS_POLIST", rownum * count);
                        batchList = new ArrayList<>();
                    }
                }

                for (int i = 0; i < poList.size(); i++) {
                    batchPoList.add(poList.get(i));
                    if (batchPoList.size() % rownum == 0) {
                        poCount++;
                        ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_ORDER_DETAIL", batchPoList, "ID", "seq_tta_free_goods_o_d.NEXTVAL","导入步骤");
                        LOGGER.info("操作的表:{},批量保存数:{}", "TTA_FREE_GOODS_ORDER_DETAIL", rownum * poCount);
                        batchPoList = new ArrayList<>();
                    }
                }

                ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_POLIST", batchList, "ID", "seq_tta_free_goods_polist.NEXTVAL","导入步骤",jedisCluster,userSessionBean);
                ttaFreeGoodsPolistDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS_ORDER_DETAIL", batchPoList, "ID", "seq_tta_free_goods_o_d.NEXTVAL","导入步骤");
            }
        }
    }

    /**
     * 二分查找法查找对象
     * @param key 要查找的key
     * @return
     */
    private  TtaItemEntity_HI_RO binarySearch(String key){
        if (itemEntity_hi_ros == null || StringUtils.isBlank(key)) {
            return null;
        }
        int headIdx = 0 ;
        int endIdx = itemEntity_hi_ros.size() - 1;
        while (headIdx <= endIdx) {
            int midIdx = (headIdx + endIdx) >> 1;
            String itemNbr = itemEntity_hi_ros.get(midIdx).getItemNbr();
            if (key.equals(itemNbr)) {
                return itemEntity_hi_ros.get(midIdx);
            } else if (key.compareTo(itemNbr) > 0) {//查找的值大于中间值
                headIdx = midIdx + 1;
            } else {//查找的值小于中间值
                endIdx = midIdx - 1;
            }
        }
        return null;
    }

    /**
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaFreeGoodsPolistEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer
            pageIndex, Integer pageRows) throws Exception {
        StringBuffer sql = new StringBuffer(TtaFreeGoodsPolistEntity_HI_RO.TTA_FREE_GOODS_POLIST);
        Map<String, Object> map = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(TtaFreeGoodsPolistEntity_HI_RO.class, queryParamJSON, "s.TIMES_VERSION", "timesVersion", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql, "count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " id desc", false);
        Pagination<TtaFreeGoodsPolistEntity_HI_RO> resultList = ttaFreeGoodsPolistDAO_HI_RO.findPagination(sql, countSql, map, pageIndex, pageRows);
        return resultList;
    }

    /**
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception {
        JSONObject result = new JSONObject();
        if (queryParamJSON.getInteger("timesVersion") != null || !"".equals(queryParamJSON.getInteger("timesVersion"))) {
            //ttaFreeGoodsPolistDAO_HI.delete(queryParamJSON.getInteger("timesVersion"));
            ttaFreeGoodsPolistDAO_HI.executeSql("delete from TTA_FREE_GOODS_POLIST where TIMES_VERSION = '" + queryParamJSON.getInteger("timesVersion") + "'");
        }
        return result;
    }

    @Override
    public TtaFreeGoodsPolistEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaFreeGoodsPolistEntity_HI instance = SaafToolUtils.setEntity(TtaFreeGoodsPolistEntity_HI.class, paramsJSON, ttaFreeGoodsPolistDAO_HI, userId);
        instance.setLastUpdatedBy(userId);
        instance.setLastUpdateDate(new Date());
        instance.setLastUpdateLogin(userId);
        ttaFreeGoodsPolistDAO_HI.saveOrUpdate(instance);
        return instance;
    }
}
