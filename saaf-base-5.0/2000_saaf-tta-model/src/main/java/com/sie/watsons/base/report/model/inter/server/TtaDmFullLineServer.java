package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaDmFullLineEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmFullLineEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaDmFullLine;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.base.utils.DateUtil;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Component("ttaDmFullLineServer")
public class TtaDmFullLineServer extends BaseCommonServer<TtaDmFullLineEntity_HI> implements ITtaDmFullLine {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaDmFullLineServer.class);

    @Autowired
    private BaseCommonDAO_HI<TtaDmFullLineEntity_HI> ttaDmFullLineDAO_HI;

    @Autowired
    private BaseViewObject<TtaDmFullLineEntity_HI_RO> ttaDmFullLineDAO_HI_RO;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;

    public TtaDmFullLineServer() {
        super();
    }


    /**
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaDmFullLineEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
        StringBuffer sql = new StringBuffer(TtaDmFullLineEntity_HI_RO.QUERY_SQL);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(TtaDmFullLineEntity_HI_RO.class, queryParamJSON, "td.duration_period", "durationPeriod", sql, queryMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaDmFullLineEntity_HI_RO.class, queryParamJSON, "td.effective_area", "effectiveArea", sql, queryMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaDmFullLineEntity_HI_RO.class, queryParamJSON, "td.item_nbr", "itemNbr", sql, queryMap, "fulllike");
        SaafToolUtils.parperHbmParam(TtaDmFullLineEntity_HI_RO.class, queryParamJSON, "td.is_create", "isCreate", sql, queryMap, "=");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql, "count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " dm_full_line_id desc", false);
        Pagination<TtaDmFullLineEntity_HI_RO> resultList = ttaDmFullLineDAO_HI_RO.findPagination(sql, countSql, queryMap, pageIndex, pageRows);
        return resultList;
    }

    /**
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public void deleteImportInfo(JSONObject queryParamJSON) throws Exception {
        String dmFullLineIds = queryParamJSON.getString("dmFullLineIds");
        if (StringUtils.isNotEmpty(dmFullLineIds)) {
            String[] ids = dmFullLineIds.split(",");
            for (String id : ids) {
                ttaDmFullLineDAO_HI.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public Pagination<TtaDmFullLineEntity_HI_RO> findDmCreateInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
       /* String  CREATE_QUERY = " select * from\n" +
                "(select tpslv.dm_full_line_id,tpslv.duration_period,nvl(tpslv.is_create,'N') is_create, row_number() \n" +
                " over(partition by tpslv.duration_period order by nvl(tpslv.is_create,'N') desc ) rn\n" +
                " from tta_dm_full_line tpslv ) t1 where t1.rn=1 ";*/
        sql.append(TtaDmFullLineEntity_HI_RO.CREATE_QUERY);
        SaafToolUtils.parperParam(queryParamJSON, "t1.is_create", "isCreate", sql, map, "=");
        SaafToolUtils.parperParam(queryParamJSON, "t1.duration_period", "durationPeriod", sql, map, "fulllike"); //促销档期
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " t1.dm_full_line_id desc", false);
        Pagination<TtaDmFullLineEntity_HI_RO> resultList = ttaDmFullLineDAO_HI_RO.findPagination(sql, map, pageIndex, pageRows);
        return resultList;
    }

    @SuppressWarnings("all")
    @Override
    public JSONObject saveOrUpdateDmOrder(JSONObject paramsJSON, int userId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        //校验数据
        SaafToolUtils.validateJsonParms(paramsJSON, "durationPeriod");//促销档期
        String durationPeriod = paramsJSON.getString("durationPeriod");
        List<TtaDmFullLineEntity_HI> list = ttaDmFullLineDAO_HI.findByProperty(new JSONObject().fluentPut("durationPeriod", durationPeriod));
        if (list.size() > 0) {
            Date date = new Date();
            String dateString = DateUtil.date2Str(date,"yyyy-MM-dd HH:mm:ss");
            String batchCode = codeService.getTtaOiCw("DM");
            TtaDmFullLineEntity_HI  entity_hi = list.get(0);
            String yearMonth = (entity_hi.getYearMonth() + "").substring(0, 4);
            //插入基础数据
            ttaDmFullLineDAO_HI.executeSqlUpdate(TtaDmCheckingEntity_HI_RO.getInsertReportBase(batchCode, userId, durationPeriod, Integer.parseInt(yearMonth), dateString));
            //更新Own
            ttaDmFullLineDAO_HI.executeSqlUpdate(TtaDmCheckingEntity_HI_RO.getUpdateReportOwn(userId, durationPeriod, batchCode)) ;
            //更新基础表
            ttaDmFullLineDAO_HI.executeSqlUpdate(TtaDmCheckingEntity_HI_RO.getUpdateReportBaseDm(userId, durationPeriod));
            //头表插入一条数据
            TtaReportHeaderEntity_HI ttaReportHeaderEntity_hi = new TtaReportHeaderEntity_HI();
            ttaReportHeaderEntity_hi.setOperatorUserId(userId);
            ttaReportHeaderEntity_hi.setStatus("DS01"); //制单中
            ttaReportHeaderEntity_hi.setIsPublish("N");
            ttaReportHeaderEntity_hi.setBatchId(batchCode);
            ttaReportHeaderEntity_hi.setReportType("DM");
            if (ttaReportHeaderEntity_hi.getCreationDate() == null) {
                ttaReportHeaderEntity_hi.setCreationDate(date);
            }
            ttaReportHeaderEntity_hi.setLastUpdateDate(date);
            ttaReportHeaderEntity_hi.setPromotionSection(durationPeriod);
            ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntity_hi);
           // String promotionStartDate = DateUtil.date2Str(entity_hi.getPromotionStartDate(),"yyyy-MM-dd HH:mm:ss");
           // String promotionEndDate =  DateUtil.date2Str(entity_hi.getPromotionEndDate(),"yyyy-MM-dd HH:mm:ss");
            //插入调整应收金额数据：proposal变更或者制作通过的单据信息，差异调整金额插入业务表
            ttaDmFullLineDAO_HI.executeSqlUpdate(TtaDmCheckingEntity_HI_RO.getAdjustAmountSlq(durationPeriod, userId, batchCode, dateString));
            //插入上个月的将收取
            ttaDmFullLineDAO_HI.executeSqlUpdate(TtaDmCheckingEntity_HI_RO.getUpdateReportBaseLastTimes(batchCode, userId,durationPeriod,dateString));
            jsonObject.put("report", ttaReportHeaderEntity_hi);
        } else {
            throw new IllegalArgumentException("无法获取导入数据的年份");
        }
        return jsonObject;
    }

    @Override
    public void saveImportDMInfo(JSONObject jsonObject, MultipartFile file)  throws  Exception {
        try {
            Set<String> hashSet = new HashSet<>();
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaDmFullLineEntity_HI.class,0);
            List<Map<String, Object>> datas = (List<Map<String, Object>>)result.get("datas");
            if (datas != null && !datas.isEmpty()) {
                datas.forEach(item->{
                    item.put("CREATED_BY",jsonObject.getInteger("userId"));
                    item.put("LAST_UPDATED_BY",jsonObject.getInteger("userId"));
                    item.put("LAST_UPDATE_LOGIN",jsonObject.getInteger("userId"));
                    item.put("IS_CREATE","N");
                    item.put("LAST_UPDATE_DATE", new Date());
                    item.put("CREATION_DATE", new Date());
                    item.put("VERSION_NUM",0);
                    item.remove("OPERATOR_USER_ID");
                    Assert.notNull(item.get("DURATION_PERIOD"), "请检查档期不能为空!");
                    Assert.notNull(item.get("DM_PAGE"), "请检查页码不能为空");
                    Assert.notNull(item.get("LOCATION_CODE"), "请检查位置不能为空!");
                    Assert.notNull(item.get("ITEM_NBR"), "请检查ITEM_NBR不能为空!");
                    Assert.notNull(item.get("ACCEPT_UNIT"), "请检查单位不能为空!");
                    Assert.notNull(item.get("EFFECTIVE_AREA_CNT"), "请检查生效区域数不能为空!");
                    Assert.notNull(item.get("EFFECTIVE_AREA"), "请检查生效区域不能为空!");
                    if ((item.get("EFFECTIVE_AREA") + "").length() !=  Integer.parseInt(item.get("EFFECTIVE_AREA_CNT") + "")){
                        throw  new IllegalArgumentException("当前生效区域与生效区域数量不一致，请检查生效区域:" + item.get("EFFECTIVE_AREA") + "，数量：" + item.get("EFFECTIVE_AREA_CNT"));
                    }
                    String key = "档期:" + item.get("DURATION_PERIOD") + ",ITEM:" + item.get("ITEM_NBR") + ",页码:" + item.get("DM_PAGE");
                    if (!hashSet.add(key)){
                        throw  new IllegalArgumentException("出现重复数据，如" + key);
                    }
                });
            }
            ttaDmFullLineDAO_HI.executeSqlUpdate("truncate table tta_dm_full_line_check");
            ttaDmFullLineDAO_HI.saveSeqBatchJDBC("TTA_DM_FULL_LINE_CHECK",datas,"DM_FULL_LINE_ID","SEQ_TTA_DM_FULL_LINE_ID_CHECK.NEXTVAL");
            List<Map<String, Object>> checkList = ttaDmFullLineDAO_HI.queryNamedSQLForList(TtaDmFullLineEntity_HI_RO.GET_CHECK_SQL, new HashMap<String, Object>());
            if (checkList != null && checkList.size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("请勿导入重复数据:\n产品编号/DM页码/位置/促销档期\n");
                checkList.forEach(item->{
                    String info = item.get("ITEM_NBR") + "/" + item.get("DM_PAGE") + "/" + item.get("LOCATION_CODE") + "/" + item.get("DURATION_PERIOD") + "\n";
                    sb.append(info);
                });
                Assert.isTrue(false, sb.toString());
            }
            ttaDmFullLineDAO_HI.saveSeqBatchJDBC("TTA_DM_FULL_LINE",datas,"DM_FULL_LINE_ID","SEQ_DM_FULL_LINE_ID.NEXTVAL");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw  new Exception(e);
        }
    }

}
