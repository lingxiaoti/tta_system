package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUsersPerson_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbAttendanceFeeEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAttendanceFeeEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaIrTermsEntity_HI_RO;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbAttendanceFeeEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaHwbAttendanceFee;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;

@Component("ttaHwbAttendanceFeeServer")
public class TtaHwbAttendanceFeeServer extends BaseCommonServer<TtaHwbAttendanceFeeEntity_HI> implements ITtaHwbAttendanceFee{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbAttendanceFeeServer.class);
    @Autowired
    private ViewObject<TtaHwbAttendanceFeeEntity_HI> ttaHwbAttendanceFeeDAO_HI;
    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;
    @Autowired
    public HttpServletRequest request;
    @Autowired
    private BaseViewObject<BaseUsersPerson_HI_RO> baseUsersPersonDAO_HI_RO;
    @Autowired
    private BaseViewObject<TtaUserGroupDeptBrandEntity_HI_RO> ttaUserGroupDeptBrandDAO_HI_RO;
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private BaseViewObject<TtaHwbAttendanceFeeEntity_HI_RO> TtaHwbAttendanceFeeDAO_HI_RO;

    public TtaHwbAttendanceFeeServer() {
        super();
    }

    @Override
    public Pagination<TtaHwbAttendanceFeeEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        String startDate = (String)queryParamJSON.get("startDate");
        String endDate = (String)queryParamJSON.get("endDate");
        //如果时间版本为空则默认查询当月数据，如果当月数据也为空则sql查询并插入
        String version = (String)queryParamJSON.get("batchId");

        Pagination<TtaHwbAttendanceFeeEntity_HI_RO> findList = null;

        //如果为空则是新增，否则查询历史表
        if(null==version||"".equals(version)){
            version =getdate();
            queryParamJSON.put("timesVersion",version);

            //把sql字符串2019年替换成当前年份
            sql =new StringBuffer(TtaHwbAttendanceFeeEntity_HI_RO.TTA_HWB_ATTENDANCE_FEE.replace("2019",year+""));
            findList = TtaHwbAttendanceFeeDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
            //分页的数据集只有有限10行，要重新查询再保存
            Pagination<TtaHwbAttendanceFeeEntity_HI_RO> findList2 = TtaHwbAttendanceFeeDAO_HI_RO.findPagination(sql, paramsMap, 0, 999999);
            if(findList2.getData().size()>0) {
                ttaHwbAttendanceFeeDAO_HI.saveAll(change(findList2.getData()));
                TtaReportHeaderEntity_HI rh = new TtaReportHeaderEntity_HI();
                List<TtaReportHeaderEntity_HI> list = ttaReportHeaderDAO_HI.findByProperty("batchId", findList.getData().get(0).getTimesVersion());
                if (list.size() <= 0) {
                    rh = list.get(0);
                }
                //添加头表批次数据
                rh.setStatus("1");
                rh.setBatchId(findList.getData().get(0).getTimesVersion());
                rh.setCreatedBy(findList.getData().get(0).getCreatedBy());
                rh.setCreationDate(findList.getData().get(0).getCreationDate());
                rh.setReportType("HWB");
                rh.setLastUpdateDate(findList.getData().get(0).getLastUpdateDate());
                rh.setLastUpdatedBy(findList.getData().get(0).getLastUpdatedBy());
                ttaReportHeaderDAO_HI.saveOrUpdate(rh);
            }
        }


        //以上逻辑为无权限数据处理，为部分权限下的所有数据处理
        //以下查询根据权限展示

        UserSessionBean userSessionBean= getUserSessionBean();
        String dataType = userSessionBean.getDataType();
        //根据userID获取其对应的GROUP，DEPT，BRAND
        StringBuffer sql2 = new StringBuffer();
        Map<String, Object> params = new HashMap<String, Object>();
        sql2.append(TtaUserGroupDeptBrandEntity_HI_RO.TTA_GROUP_LIST);
        queryParamJSON.put("userId",userSessionBean.getUserId());
        SaafToolUtils.parperParam(queryParamJSON, "tug.user_id", "userId", sql2, paramsMap, "=");
        paramsMap.remove("timesVersion");
        Pagination<TtaUserGroupDeptBrandEntity_HI_RO> findList2 = ttaUserGroupDeptBrandDAO_HI_RO.findPagination(sql2, paramsMap, pageIndex, pageRows);
        paramsMap.remove("userId");
        queryParamJSON.put("timesVersion",version);
        //获取当前user信息到history表做权限过滤
        if(findList2.getData().size()>0){
            if("1".equals(dataType)){
                queryParamJSON.put("groupCode",findList2.getData().get(0).getGroup());
                SaafToolUtils.parperParam(queryParamJSON, "t.GROUP_CODE", "groupCode", sql, paramsMap, "=");
            }
            if("2".equals(dataType)){
                queryParamJSON.put("groupCode",findList2.getData().get(0).getGroup());
                queryParamJSON.put("deptCode",findList2.getData().get(0).getDept());
                SaafToolUtils.parperParam(queryParamJSON, "t.GROUP_CODE", "groupCode", sql, paramsMap, "=");
                SaafToolUtils.parperParam(queryParamJSON, "t.DEPT_CODE", "deptCode", sql, paramsMap, "=");
            }
            if("3".equals(dataType)){
                queryParamJSON.put("groupCode",findList2.getData().get(0).getGroup());
                queryParamJSON.put("deptCode",findList2.getData().get(0).getDept());
                queryParamJSON.put("brandCn",findList2.getData().get(0).getBrandCn());
                SaafToolUtils.parperParam(queryParamJSON, "t.GROUP_CODE", "groupCode", sql, paramsMap, "=");
                SaafToolUtils.parperParam(queryParamJSON, "t.DEPT_CODE", "deptCode", sql, paramsMap, "=");
                SaafToolUtils.parperParam(queryParamJSON, "t.BRAND_CN", "brandCn", sql, paramsMap, "=");
            }

        }

        //查询历史
        sql = new StringBuffer(TtaHwbAttendanceFeeEntity_HI_RO.TTA_HWB_ATTENDANCE_FEE_HISTORY);
        queryParamJSON.put("timesVersion",version);
        SaafToolUtils.parperParam(queryParamJSON, "t.TIMES_VERSION", "timesVersion", sql, paramsMap, "=");
        //   SaafToolUtils.parperParam(queryParamJSON, "t.TIMES_VERSION", "timesVersion", sql, paramsMap, "=");
        // SaafToolUtils.parperParam(queryParamJSON, "t.TIMES_VERSION", "timesVersion", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "usr.USER_FULL_NAME", "createdName", sql, paramsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " t.ID desc", false);
        findList = TtaHwbAttendanceFeeDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);

        return findList;
    }

    @Override
    public Pagination<BaseUsersPerson_HI_RO> findUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {

        String userId = (String)queryParamJSON.get("userId");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        sql.append(TtaHwbAttendanceFeeEntity_HI_RO.TTA_HWB_USER_LIST);
        SaafToolUtils.parperParam(queryParamJSON, "u.user_full_name", "userFullName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "u.user_id", "userId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "u.user_name", "userName", sql, paramsMap, "like");

        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "u.user_id desc", false);

        Pagination<BaseUsersPerson_HI_RO> findList = baseUsersPersonDAO_HI_RO.findPagination(sql,SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return findList;
    }

    public List<TtaHwbAttendanceFeeEntity_HI> change(List<TtaHwbAttendanceFeeEntity_HI_RO> list){
        List<TtaHwbAttendanceFeeEntity_HI> hlist = new ArrayList<TtaHwbAttendanceFeeEntity_HI>();
        for(TtaHwbAttendanceFeeEntity_HI_RO h:list){
            TtaHwbAttendanceFeeEntity_HI hi = new TtaHwbAttendanceFeeEntity_HI();
            hi.setDept(h.getDept());
            hi.setBrand(h.getBrand());
            hi.setBrandEnorcn(h.getBrandEnorcn());
            hi.setNum(h.getNum());
            hi.setFeeType(h.getFeeType());
            hi.setRateCard(h.getRateCard());
            hi.setFeeGroup(h.getFeeGroup());
            hi.setCategory(h.getCategory());
            hi.setContent(h.getContent());
            hi.setConfirmAmount(h.getConfirmAmount());
            hi.setFg(h.getFg());
            hi.setVendorCode(h.getVendorCode());
            hi.setVendorName(h.getVendorName());
            hi.setDifferent(h.getDifferent());
            hi.setProjectType(h.getProjectType());
            hi.setActive(h.getActive());
            hi.setAmount(h.getAmount());
            hi.setRemark(h.getRemark());
            hi.setTimesVersion(getdate());
            hi.setStatus("0");
            hlist.add(hi);
        }
        return hlist;
    }

    public String getdate() {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        String date = year+"";
        if(month<10){
            date = date + "0"+month;
        }else{
            date = date + ""+month;
        }
        date = "HWB"+year+"01-"+date;
        return date;
    }

    @Override
    public TtaHwbAttendanceFeeEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception {
        TtaHwbAttendanceFeeEntity_HI instance = SaafToolUtils.setEntity(TtaHwbAttendanceFeeEntity_HI.class, paramsJSON, ttaHwbAttendanceFeeDAO_HI, userId);

        ttaHwbAttendanceFeeDAO_HI.saveOrUpdate(instance);
        return instance;
    }

    /**
     * 从redis缓存中获取UserSessionBean对象
     * @return UserSessionBean对象
     * @author ZhangJun
     * @creteTime 2017/12/15
     */
    public UserSessionBean getUserSessionBean() {

        try {
            String certificate = request.getHeader("Certificate");
            if (StringUtils.isBlank(certificate)) {
                return null;
            }
            String key = "cookie_" + certificate;
            String result = jedisCluster.hget(key,"sessionInfo");
            if (StringUtils.isBlank(result))
                return null;
            UserSessionBean userSessionBean= JSON.parseObject(result,UserSessionBean.class);
            String expire = jedisCluster.hget(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire");
            if (StringUtils.isBlank(expire) && !StringUtils.isNumeric(expire)) {
                jedisCluster.hset(CommonConstants.RedisCacheKey.GLOBAL_REDIS_CACHE, "appSessionExprire", "48");
                expire = "48";
            }
            jedisCluster.expire(key, userSessionBean.isFromApp() ? Integer.valueOf(expire) * 3600 : CommonConstants.COOKIE_EXPIRED);
            return userSessionBean;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}