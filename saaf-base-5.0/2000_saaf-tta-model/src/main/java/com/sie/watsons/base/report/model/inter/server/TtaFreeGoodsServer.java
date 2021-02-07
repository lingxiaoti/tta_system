package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaFreeGoodsDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaFreeGoodsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaIrTermsEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaFreeGoodsEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.TtaFreeGoods;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Administrator on 2019/7/30/030.
 */
@Component("ttaFreeGoodsServer")
public class TtaFreeGoodsServer extends BaseCommonServer<TtaFreeGoodsEntity_HI> implements TtaFreeGoods {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaFreeGoodsServer.class);

    @Autowired
    private BaseCommonDAO_HI<TtaFreeGoodsEntity_HI> ttaFreeGoodsDAO_HI;
    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;

    @Autowired
    private TtaFreeGoodsDAO_HI ttaFreeGoodsDAOHi;

    @Autowired
    private BaseViewObject<TtaFreeGoodsEntity_HI_RO> TtaFreeGoodsDAO_HI_RO;

    @Autowired
    private BaseViewObject<TtaUserGroupDeptBrandEntity_HI_RO> ttaUserGroupDeptBrandDAO_HI_RO;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    public HttpServletRequest request;

    public TtaFreeGoodsServer() {
        super();
    }

    @Override
    public Pagination<TtaFreeGoodsEntity_HI_RO> saveFind(JSONObject queryParamJSON,UserSessionBean userSessionBean, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer  sql = new StringBuffer();
        Map<String, Object> freeParamsMap = new HashMap<String, Object>();
        //非 BIC用户
        if (!("45".equals(userSessionBean.getUserType()))) {
            sql.append(TtaFreeGoodsEntity_HI_RO.NO_BIC_FREEGOODS_QUERY);
            //queryParamJSON.put("proposalUserId", userSessionBean.getUserId());
            //SaafToolUtils.parperParam(queryParamJSON, "tfg.proposal_user_id", "proposalUserId", sql, freeParamsMap, "=");
            String version = queryParamJSON.getString("batchId");
            freeParamsMap.put("batchCode",version);
            freeParamsMap.put("userId",userSessionBean.getUserId());
        } else {
            sql.append(TtaFreeGoodsEntity_HI_RO.FREEGOODS_QUERY);
            SaafToolUtils.parperParam(queryParamJSON, "tfg.times_version", "batchId", sql, freeParamsMap, "=");
        }

        SaafToolUtils.parperParam(queryParamJSON, "tfg.vendor_nbr", "vendorNbr", sql, freeParamsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "tfg.vendor_name", "vendorName", sql, freeParamsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tfg.TTA_FREE_GOODS_ID desc", false);
        return TtaFreeGoodsDAO_HI_RO.findPagination(sql, freeParamsMap, pageIndex, pageRows);
    }

    /**
     * 获取月份
     * @return
     */
    public String getYearMonth(Date inputDate,String synbol) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(inputDate);
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        String date = "";

        if ("start".equals(synbol)) {
            date = year + "01";
        } else if ("end".equals(synbol)){
            if(month<10){
                date = year + "0" + month;
            }else{
                date = year + "" + month;
            }
        } else {
            date = String.valueOf(month);
        }
        return date;
    }

    @Override
    public TtaFreeGoodsEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        return null;
    }

    @Override
    public void saveOrUpdateAll(JSONObject paramsJSON, int userId) throws Exception {
        JSONArray saveAll = paramsJSON.getJSONArray("saveAll");
        List<TtaFreeGoodsEntity_HI> allData = new ArrayList<>();
        for (int i = 0; i < saveAll.size(); i++) {
            JSONObject jsonObject = saveAll.getJSONObject(i);
            TtaFreeGoodsEntity_HI instance = SaafToolUtils.setEntity(TtaFreeGoodsEntity_HI.class, jsonObject, ttaFreeGoodsDAO_HI, userId);
            instance.setLastUpdatedBy(userId);
            instance.setLastUpdateDate(new Date());
            instance.setLastUpdateLogin(userId);
            allData.add(instance);
        }
        ttaFreeGoodsDAO_HI.saveOrUpdateAll(allData);
    }

    @Override
    public void delete(Integer osdId) {

    }

    @Override
    public List<TtaFreeGoodsEntity_HI_RO> findfFreeGoodList(String batchId, Integer userId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(TtaFreeGoodsEntity_HI_RO.TTA_FREE_GOODS_QUERY);
        JSONObject freeParamObjet = new JSONObject();
        Map<String, Object> freeParamsMap = new HashMap<String, Object>();
        freeParamObjet.put("timesVersion", batchId);
        SaafToolUtils.parperParam(freeParamObjet, "t.TIMES_VERSION", "timesVersion", sql, freeParamsMap, "=");
        SaafToolUtils.changeQuerySort(freeParamObjet, sql , " t.TTA_FREE_GOODS_ID desc", false);
        return TtaFreeGoodsDAO_HI_RO.findList(sql.toString(),freeParamsMap);
    }

    @Override
    public JSONObject saveFreeGoodsByPoList(JSONObject queryParamJSON,UserSessionBean userSessionBean, int userId) throws Exception {
        LOGGER.info("----------------开始新增FG数据------------------");
        SaafToolUtils.validateJsonParms(queryParamJSON,"yearMonth");
        String yearMonth = queryParamJSON.getString("yearMonth");
        Assert.notNull(yearMonth,"年份不能为空,请先选择年份!");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date parse = sdf.parse(yearMonth);
        JSONObject jsonObject  = new JSONObject();

        int selectYear = Integer.parseInt(yearMonth);
        int nowYear = LocalDate.now().getYear();
        String startAccountMonth = "";
        String endAccountMonth = "";
        String month = "";
        if (selectYear < nowYear) {
            startAccountMonth = getAllYear(selectYear,"start");
            endAccountMonth = getAllYear(selectYear,"end");
            month = "12";
        } else if (selectYear == nowYear){
            startAccountMonth = getYearMonth(new Date(),"start");
            endAccountMonth = getYearMonth(new Date(),"end");
            month = getYearMonth(new Date(), "month");
        } else {
            throw new RuntimeException("选择的年份不能大于当年的年份噢!");
        }

        //获取开始年月
        //String startAccountMonth = getYearMonth(parse,"start");
        //获取结束年月
        //String endAccountMonth = getYearMonth(parse,"end");
        String version = codeService.getTtaFGReport("FG");
        LOGGER.info("Free Goods报表批次号:{}",version);

        StringBuffer sql = null;
        //查找历史的Free Goods数据
        Map<String,Object> freeGoodsParamMap = new HashMap<>();
        sql = new StringBuffer(TtaFreeGoodsEntity_HI_RO.TTA_FREE_GOODS_HISTORY);
        queryParamJSON.put("timesVersion", version);
        SaafToolUtils.parperParam(queryParamJSON, "t.TIMES_VERSION", "timesVersion", sql, freeGoodsParamMap, "=");
        List<TtaFreeGoodsEntity_HI_RO> ttaFreeGoodsEntityHiRos = TtaFreeGoodsDAO_HI_RO.findList(sql,freeGoodsParamMap);
        if (null != ttaFreeGoodsEntityHiRos && !ttaFreeGoodsEntityHiRos.isEmpty()) {
            throw new IllegalArgumentException("系统已存在相同批次" + version + "的免费货品数据,请刷新重试,如再次失败,请联系管理员!");
        }

        sql = new StringBuffer(TtaFreeGoodsEntity_HI_RO.getFreeGoodsSql(startAccountMonth,endAccountMonth,month,selectYear));
        //查询需要生成的FG报表数据
        List<Map<String, Object>> mapList = ttaFreeGoodsDAO_HI.queryNamedSQLForList(sql.toString(), new HashMap<>());

        if (null != mapList && !mapList.isEmpty()) {
            for (Map<String, Object> map : mapList) {
                map.put("STATUS","1");//生效
                map.put("TIMES_VERSION",version);
                map.put("CREATED_BY",userSessionBean.getUserId()) ;
                map.put("CREATION_DATE",new Date()) ;
                map.put("LAST_UPDATED_BY",userSessionBean.getUserId()) ;
                map.put("LAST_UPDATE_DATE",new Date()) ;
                map.put("LAST_UPDATE_LOGIN",userSessionBean.getUserId()) ;
                map.put("VERSION_NUM",0) ;
                map.put("TTA_FREE_GOODS_ID",null);
            }

            ttaFreeGoodsDAOHi.saveSeqBatchJDBC("TTA_FREE_GOODS",mapList,"TTA_FREE_GOODS_ID","SEQ_TTA_FREE_GOODS.NEXTVAL");

            //创建头信息单据,默认为制作中
            TtaReportHeaderEntity_HI rh = new TtaReportHeaderEntity_HI();
            rh.setPromotionSection(yearMonth);//年月
            rh.setStatus("DS01");
            rh.setBatchId(version);
            rh.setCreatedBy(userSessionBean.getUserId());
            rh.setCreationDate(new Date());
            rh.setReportType("FG");
            rh.setIsPublish("N");//未发布
            rh.setLastUpdateDate(new Date());
            rh.setLastUpdatedBy(userSessionBean.getUserId());
            ttaReportHeaderDAO_HI.save(rh);
            jsonObject.put("fgReport",rh);
        } else {
            throw new IllegalArgumentException("未查找到相关的免费货品数据,生成FG数据失败");
        }
        return jsonObject;
    }

    private String getAllYear(int selectYear, String flag) {
        String month = "";
        if ("start".equals(flag)) {
            month = selectYear + "01";
        } else {
            month = selectYear + "12";
        }
        return month;
    }

    @Override
    public Pagination<TtaFreeGoodsEntity_HI_RO> findProcessSummaryInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) {
        Pagination<TtaFreeGoodsEntity_HI_RO> pagination = TtaFreeGoodsDAO_HI_RO.findPagination(TtaFreeGoodsEntity_HI_RO.getProcessSummary(queryParamJSON.getString("batchCode"),sessionBean.getUserName(), sessionBean.getUserId()), new HashMap<String, Object>(), 0, Integer.MAX_VALUE);
        return pagination;
    }
}
