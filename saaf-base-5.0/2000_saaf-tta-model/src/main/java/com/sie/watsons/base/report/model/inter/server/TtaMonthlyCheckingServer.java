package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaMonthlyCheckingDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaMonthlyCheckingEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.TtaOsdBaseLineEntity_HI;
import com.sie.watsons.base.report.model.entities.TtaReportHeaderEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaMonthlyCheckingEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaMonthlyChecking;
import com.sie.watsons.base.report.model.inter.ITtaOsdBaseLine;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserGroupDeptBrandEntity_HI_RO;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.DynamicBaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2019/7/17/017.
 */
@Component("ttaMonthlyCheckingServer")
public class TtaMonthlyCheckingServer extends BaseCommonServer<TtaMonthlyCheckingEntity_HI> implements ITtaMonthlyChecking {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaMonthlyCheckingServer.class);

    @Autowired
    private BaseCommonDAO_HI<TtaMonthlyCheckingEntity_HI> ttaMonthlyCheckingDAO_HI;

    @Autowired
    private BaseViewObject<TtaMonthlyCheckingEntity_HI_RO> ttaMonthlyCheckingDAO_HI_RO;

    @Autowired
    private GenerateCodeService codeService;

    @Autowired
    private ViewObject<TtaReportHeaderEntity_HI> ttaReportHeaderDAO_HI;

    @Autowired
    private TtaMonthlyCheckingDAO_HI ttaMonthlyCheckingDAO ;

    @Autowired
    private ITtaOsdBaseLine ttaOsdBaseLineServer;

    @Autowired
    private JedisCluster jedisCluster;
    @Autowired
    public HttpServletRequest request;

    public TtaMonthlyCheckingServer() {
        super();
    }



    /**
     * OI批量导入
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public int saveImportOsdMajorInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
        jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
        JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
        if(file != null ){
            Map<String,Object> result = null ;
            Boolean flag = (Boolean) result.get("flag");
            if(flag){
                list = (List<Map<String,Object>>) result.get("datas");
            }else{
                JSONObject errJson2 = new JSONObject();
                errJson2.put("ROW_NUM",'0');
                errJson2.put("ERR_MESSAGE","表头信息错误");
                errList.add(errJson2) ;
            }
        }

        for(int i=0;i<list.size();i++){
            Map<String,Object> json = list.get(i);
            JSONObject errJson = new JSONObject();
            json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
            json.put("CREATION_DATE",new Date()) ;
            json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
            json.put("LAST_UPDATE_DATE",new Date()) ;
            json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
            json.put("VERSION_NUM",0) ;
            String msgStr = "";
            try {
                if(!"".equals(msgStr)){
                    errJson.put("ROW_NUM",json.get("ROW_NUM"));
                    errJson.put("ERR_MESSAGE",msgStr);
                    errList.add(errJson);
                }else{
                    //	json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
                    //	super.saveOrUpdate(json);
                }
            }catch (Exception e){
                msgStr += ("有异常,数据有误.");
                errJson.put("ROW_NUM",json.get("ROW_NUM"));
                errJson.put("ERR_MESSAGE",msgStr);
                errList.add(errJson);
                e.printStackTrace();
            }
        }
        if (!errList.isEmpty()){
            throw new Exception(errList.toJSONString());
        }else{
            ttaMonthlyCheckingDAO.saveSeqBatchJDBC("TTA_OSD_MONTHLY_CHECKING",list,"OSD_ID","SEQ_TTA_OSD_MONTHLY_CHECKING.NEXTVAL",jedisCluster,sessionBean);
            jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

        }
        return list.size();
    }

    /**
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaMonthlyCheckingEntity_HI_RO> findOsdMajorInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows,UserSessionBean sessionBean) throws Exception{
        StringBuffer sql = new StringBuffer();
        StringBuffer countSql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        String flag = queryParamJSON.getString("flag");
        //流程单据查询
        if("process".equals(flag)){
            SaafToolUtils.validateJsonParms(queryParamJSON, "reportProcessHeaderId");
            sql.append(TtaMonthlyCheckingEntity_HI_RO.QUERY) ;
            SaafToolUtils.parperParam(queryParamJSON, "tom.process_id", "reportProcessHeaderId", sql, map, "=");
        }else{
            //校验数据
            //SaafToolUtils.validateJsonParms(queryParamJSON, "batchCode");
            //  非 BIC用户
            if( !("45".equals(sessionBean.getUserType()))  && !(Integer.valueOf(1).equals(queryParamJSON.getInteger("varUserId")) )){
                sql.append(TtaMonthlyCheckingEntity_HI_RO.QUERY_NO_BIC) ;
                map.put("userId",sessionBean.getUserId()) ;
                queryParamJSON.remove("orderBy");
                getSearch(queryParamJSON,sql,map);
                SaafToolUtils.parperParam(queryParamJSON, "tom.batch_code", "batchCode", sql, map, "=");
                SaafToolUtils.parperParam(queryParamJSON, "tom.process_Status", "processStatus", sql, map, "=");
                SaafToolUtils.parperParam(queryParamJSON, "to_char(tom.creation_date,'yyyy')", "substrYear", sql, map, "=");
                countSql = SaafToolUtils.getSqlCountString(sql) ;

                SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tom.osd_id desc", false);
            }else{
                sql.append(TtaMonthlyCheckingEntity_HI_RO.QUERY) ;
                getSearch(queryParamJSON,sql,map);
                String batchCode = queryParamJSON.getString("batchCode");
                if (StringUtils.isNotEmpty(batchCode)) {
                    sql.append(" and tom.batch_code in ('" + org.apache.commons.lang3.StringUtils.join(batchCode.split(","),"','") + "')\n");
                }
                //SaafToolUtils.parperParam(queryParamJSON, "tom.batch_code", "batchCode", sql, map, "=");
                SaafToolUtils.parperParam(queryParamJSON, "TRPH.status", "processStatus", sql, map, "=");
                SaafToolUtils.parperParam(queryParamJSON, "to_char(tom.creation_date,'yyyy')", "substrYear", sql, map, "=");
                countSql = SaafToolUtils.getSqlCountString(sql) ;
                if("receiveAmount".equals(queryParamJSON.getString("orderBy"))){
                    sql.append(" order by  nvl(tom.receive_amount,0) desc,tom.osd_id desc") ;
                }else{
                    sql.append(" order by  tom.prior_vendor_code desc,     nvl(tom.receive_amount,0) desc,tom.osd_id desc ") ;
                }

            }
           // map.put("batchCode",queryParamJSON.getString("batchCode")) ;
        }

        Pagination<TtaMonthlyCheckingEntity_HI_RO> resultList =ttaMonthlyCheckingDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    @Override
    public Pagination<TtaMonthlyCheckingEntity_HI_RO> findNotExist(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append(TtaMonthlyCheckingEntity_HI_RO.QUERY_EXISTS);
        StringBuffer countSql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("batchCode",queryParamJSON.getString("batchCode"));
        SaafToolUtils.parperParam(queryParamJSON, "tom.group_code", "groupCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
        sql.append(" group by     tom.group_code,tom.group_desc,tom.dept_code,tom.dept_desc,tom.brand_cn ");
        countSql = SaafToolUtils.getSqlCountString(sql) ;
        Pagination<TtaMonthlyCheckingEntity_HI_RO> resultList =ttaMonthlyCheckingDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    private  void getSearch(JSONObject queryParamJSON,StringBuffer sql,Map<String,Object> map){
        SaafToolUtils.parperParam(queryParamJSON, "tom.promotion_section", "promotionSection", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.item_code", "itemCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.prior_vendor_code", "priorVendorCode", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.prior_vendor_name", "priorVendorName", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.brand_cn", "brandCn", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.group_desc", "groupDesc", sql, map, "fulllike");
        SaafToolUtils.parperParam(queryParamJSON, "tom.dept_code", "deptCode", sql, map, "in");


    }
    /**
     *
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public JSONObject deleteImportOsdMajorInfo(JSONObject queryParamJSON) throws Exception{
        JSONObject result = new JSONObject();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
            if("pl".equals(queryParamJSON.getString("flag"))){
                if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
                    List<TtaMonthlyCheckingEntity_HI> ttaPogSpaceLineEntityList = ttaMonthlyCheckingDAO_HI.findByProperty("promotion_section", queryParamJSON.getString("month"));
                    if(ttaPogSpaceLineEntityList.size() == 0){
                        throw new IllegalArgumentException("当前促销区间不存在,删除失败");
                    }
                    ttaMonthlyCheckingDAO_HI.executeSqlUpdate("delete from tta_pog_space_line t where t.promotion_section ='" + queryParamJSON.getString("month")+"'");
                }
            }else{
                if(queryParamJSON.getInteger("pogSpaceLineId")!=null || !"".equals(queryParamJSON.getInteger("pogSpaceLineId"))){
                    ttaMonthlyCheckingDAO_HI.delete(queryParamJSON.getInteger("pogSpaceLineId"));
                }
            }
        }
        return result;
    }

    @Override
    public JSONObject saveOrUpdateFind(JSONObject paramsJSON, int userId) throws Exception {
        JSONObject jsonObject = new JSONObject();

        //校验数据
        SaafToolUtils.validateJsonParms(paramsJSON, "promotionSection","osdBaseLineId");
        TtaOsdBaseLineEntity_HI ttaOsdBaseLineEntityHi = ttaOsdBaseLineServer.getById(paramsJSON.getInteger("osdBaseLineId")) ;
        if(!SaafToolUtils.isNullOrEmpty(ttaOsdBaseLineEntityHi)){
            String countKey = "TTA_OSD_YTD";
            Date date = new Date();
            Calendar ca = Calendar.getInstance();
            ca.setTime(date);
            String defaultDateString = new StringBuffer().append(ca.get(Calendar.YEAR)).append("-01-01 00:00:00").toString();
            String startDateString = "";

            String dateString = SaafDateUtils.convertDateToString(date);
            String batchCode = codeService.getTtaOiCw("OSD") ;
            int ytdId = jedisCluster.incrBy(countKey, 1).intValue();
            //查询最后的创建时间
            TtaMonthlyCheckingEntity_HI_RO ttaOsdLastCreateDate = ttaMonthlyCheckingDAO_HI_RO.get(TtaMonthlyCheckingEntity_HI_RO.SELECT_LAST_CREATE_DATE, (new HashMap<>()));
            if (SaafToolUtils.isNullOrEmpty(ttaOsdLastCreateDate) || null ==ttaOsdLastCreateDate.getCreationDate()) {
                startDateString = defaultDateString ;
            } else if (ttaOsdLastCreateDate.getCreationDate().getYear() < date.getYear()){
                startDateString = defaultDateString;
            } else {
                startDateString = SaafDateUtils.convertDateToString(ttaOsdLastCreateDate.getCreationDate());
            }
            //插入基础数据
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getInsertReportBase(batchCode,userId,paramsJSON.getString("promotionSection"),ttaOsdBaseLineEntityHi.getOsdYear(),dateString)) ;

            //更新组别
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportVendorCod(userId,paramsJSON.getString("promotionSection"),batchCode)) ;


            //更新基础数据合并表
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportBase(userId,paramsJSON.getString("promotionSection"),batchCode)) ;

            //更新Own
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportOwn(userId,paramsJSON.getString("promotionSection"),batchCode)) ;

            //插入上个月的将收取
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportBaseLastTimes(batchCode,userId,paramsJSON.getString("promotionSection"),dateString)) ;
            //更新基础表
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportBasePog(userId,paramsJSON.getString("promotionSection"))) ;
            //插入YTD 数据
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getInsertOsdYtd(userId,paramsJSON.getString("promotionSection"),ytdId,dateString,startDateString,defaultDateString)) ;
            //更新ytd 标准
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateOsdytdStandard(userId,paramsJSON.getString("promotionSection"),ytdId)) ;
            //插入 ytd 的数据 TO OSD
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getInsertYtdNotInOsd(batchCode,userId,paramsJSON.getString("promotionSection"),ttaOsdBaseLineEntityHi.getOsdYear(),ytdId,dateString)) ;

            //ytd插入历史记录
            ttaMonthlyCheckingDAO_HI.executeSql(TtaMonthlyCheckingEntity_HI_RO.insertRecord(userId,ytdId)) ;

            //删除 当前 ytd
            ttaMonthlyCheckingDAO_HI.executeSql(TtaMonthlyCheckingEntity_HI_RO.deleteCurYtd(userId,ytdId)) ;
            //备份数据
            ttaMonthlyCheckingDAO_HI.executeSqlUpdate(TtaMonthlyCheckingEntity_HI_RO.getUpdateReportBaseHistory(userId,paramsJSON.getString("promotionSection"))) ;
            //头表插入一条数据
            TtaReportHeaderEntity_HI ttaReportHeaderEntity_hi = new TtaReportHeaderEntity_HI();
            ttaReportHeaderEntity_hi.setOperatorUserId(userId);
            ttaReportHeaderEntity_hi.setStatus("DS01");
            ttaReportHeaderEntity_hi.setIsPublish("N");
            ttaReportHeaderEntity_hi.setBatchId(batchCode);
            ttaReportHeaderEntity_hi.setReportType("OSD");
            ttaReportHeaderEntity_hi.setPromotionSection(paramsJSON.getString("promotionSection"));
            ttaReportHeaderDAO_HI.saveOrUpdate(ttaReportHeaderEntity_hi);
            jsonObject.put("report",ttaReportHeaderEntity_hi);
        }else{
            throw new IllegalArgumentException("无法获取导入数据的年份");
        }


        return jsonObject;
    }

    @Override
    public List<TtaMonthlyCheckingEntity_HI> saveOrUpdateALL(JSONObject paramsJSON, int userId) throws Exception {
        ArrayList<TtaMonthlyCheckingEntity_HI> objects = new ArrayList<>();
        JSONArray save = paramsJSON.getJSONArray("save");
        for(int i = 0 ;i<save.size();i++){
            JSONObject  json = (JSONObject)save.get(i) ;
            TtaMonthlyCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaMonthlyCheckingEntity_HI.class, json, ttaMonthlyCheckingDAO_HI, userId);
            objects.add(instance);
        }
        ttaMonthlyCheckingDAO_HI.saveOrUpdateAll(objects);
        return objects;
    }

    /**
     *转办
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaMonthlyCheckingEntity_HI> saveOrUpdateTransferALL(JSONObject paramsJSON, int userId) throws Exception {
        ArrayList<TtaMonthlyCheckingEntity_HI> objects = new ArrayList<>();
        JSONArray jsonArrayLine = paramsJSON.getJSONArray("lines");
        Integer personIds = paramsJSON.getInteger("personId");
        Date date = new Date();
        for(int i = 0 ;i<jsonArrayLine.size();i++){
            JSONObject  json = (JSONObject)jsonArrayLine.get(i) ;
            TtaMonthlyCheckingEntity_HI instance = SaafToolUtils.setEntity(TtaMonthlyCheckingEntity_HI.class, json, ttaMonthlyCheckingDAO_HI, userId);
            instance.setTransferInDate(date);
            instance.setTransferOutDate(instance.getTransferOutDate() == null ?date:instance.getTransferOutDate());
            instance.setTransferLastDate(date);
            instance.setTransferInPerson(personIds);
            instance.setTransferOutPerson(instance.getTransferOutPerson() == null ? userId:instance.getTransferOutPerson());
            instance.setTransferLastPerson(userId);
            instance.setOperatorUserId(userId);
            objects.add(instance);
        }
        ttaMonthlyCheckingDAO_HI.saveOrUpdateAll(objects);
        return objects;
    }

    @Override
    public List<TtaMonthlyCheckingEntity_HI> saveOrUpdateSplitALL(JSONArray paramsJSON, int userId,JSONObject currentRow) throws Exception {
        ArrayList<TtaMonthlyCheckingEntity_HI> objects = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        BigDecimal totalBigD = new BigDecimal("0");
        BigDecimal basezZero = new BigDecimal("0");
        BigDecimal totalBigDNoIncludeLast = new BigDecimal("0");
        BigDecimal totalBigDIncludeLast = new BigDecimal("0");
        BigDecimal baseBig = new BigDecimal("1");

        boolean isExistCurrentVendorCode = false ;

        TtaMonthlyCheckingEntity_HI ttaMonthlyCheckingEntityHi = SaafToolUtils.setEntity(TtaMonthlyCheckingEntity_HI.class, currentRow, ttaMonthlyCheckingDAO_HI, userId);
        //校验数据
        if(null == ttaMonthlyCheckingEntityHi ){
            throw new IllegalArgumentException("当前行不存在,无法拆分，请重新尝试");
        }
        TtaMonthlyCheckingEntity_HI baseInstance = new TtaMonthlyCheckingEntity_HI();
        SaafBeanUtils.copyUnionProperties(ttaMonthlyCheckingEntityHi, baseInstance);


        for (int i = 0 ;i<paramsJSON.size();i++){
            JSONObject  json = (JSONObject)paramsJSON.get(i) ;
            totalBigD = totalBigD.add(json.getBigDecimal("noUnconfirmAmount"));
        }
        if(1 == paramsJSON.size() ){
            JSONObject  json = (JSONObject)paramsJSON.get(0) ;
            ttaMonthlyCheckingEntityHi.setPriorVendorCode(json.getString("priorVendorCode"));
            ttaMonthlyCheckingEntityHi.setPriorVendorName(json.getString("priorVendorName"));
            //采购确认金额
            ttaMonthlyCheckingEntityHi.setNoUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
            stringBuffer.append("OSD-").append(ttaMonthlyCheckingEntityHi.getPromotionSection())
                    .append("-").append(ttaMonthlyCheckingEntityHi.getPromotionLocation()).append("-").append(ttaMonthlyCheckingEntityHi.getGroupDesc())
                    .append("-").append(ttaMonthlyCheckingEntityHi.getBrandCn()).append("-").append(json.getString("priorVendorCode")) ;

            if(SaafToolUtils.isNullOrEmpty(ttaMonthlyCheckingEntityHi.getCollect())){
                ttaMonthlyCheckingEntityHi.setDifference(basezZero);
            }else if("TTA".equals(ttaMonthlyCheckingEntityHi.getCollect())){
                ttaMonthlyCheckingEntityHi.setUnconfirmAmount(
                        json.getBigDecimal("noUnconfirmAmount").multiply( (new BigDecimal("100")).add(ttaMonthlyCheckingEntityHi.getAdditionRate()))
                        .divide(new BigDecimal("100"),0,BigDecimal.ROUND_HALF_UP)
                );
            }else{
                ttaMonthlyCheckingEntityHi.setUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
            }
            int compareInt = ttaMonthlyCheckingEntityHi.getUnconfirmAmount()
                                                       .subtract(ttaMonthlyCheckingEntityHi.getReceiveAmount()==null?basezZero:ttaMonthlyCheckingEntityHi.getReceiveAmount())
                                                       .subtract(ttaMonthlyCheckingEntityHi.getAdjustReceiveAmount()==null?basezZero:ttaMonthlyCheckingEntityHi.getAdjustReceiveAmount())
                                                       .compareTo(BigDecimal.ZERO);
            if(0 > compareInt){
                ttaMonthlyCheckingEntityHi.setDifference(ttaMonthlyCheckingEntityHi.getUnconfirmAmount()
                                                        .subtract(ttaMonthlyCheckingEntityHi.getReceiveAmount()==null?basezZero:ttaMonthlyCheckingEntityHi.getReceiveAmount())
                                                        .subtract(ttaMonthlyCheckingEntityHi.getAdjustReceiveAmount()==null?basezZero:ttaMonthlyCheckingEntityHi.getAdjustReceiveAmount()));
            }else{
                ttaMonthlyCheckingEntityHi.setDifference(new BigDecimal("0"));
            }
            ttaMonthlyCheckingEntityHi.setContent(stringBuffer.toString());
            ttaMonthlyCheckingEntityHi.setOperatorUserId(userId);
            objects.add(ttaMonthlyCheckingEntityHi);
        }else{
            for(int i = 0 ;i<paramsJSON.size();i++){
                JSONObject  json = (JSONObject)paramsJSON.get(i) ;
                stringBuffer.setLength(0);
                TtaMonthlyCheckingEntity_HI instance = new TtaMonthlyCheckingEntity_HI();
                //是否是第一次拆分
                if(SaafToolUtils.isNullOrEmpty(ttaMonthlyCheckingEntityHi.getParentId())){
                    ttaMonthlyCheckingEntityHi.setStatus(0);
                    SaafBeanUtils.copyUnionProperties(ttaMonthlyCheckingEntityHi, instance);
                    instance.setAdditionRate(json.getBigDecimal("additionRate")==null?basezZero:json.getBigDecimal("additionRate"));
                }else{
                    if( ( ttaMonthlyCheckingEntityHi.getPriorVendorCode().equals(json.getString("priorVendorCode")) || paramsJSON.size()-1 == i)
                            && !isExistCurrentVendorCode){

                        instance = ttaMonthlyCheckingEntityHi ;
                        isExistCurrentVendorCode = true ;
                    }else{
                        SaafBeanUtils.copyUnionProperties(ttaMonthlyCheckingEntityHi, instance);
                        instance.setAdditionRate(json.getBigDecimal("additionRate")==null?basezZero:json.getBigDecimal("additionRate"));
                    }
                }
                stringBuffer.append("OSD-").append(instance.getPromotionSection())
                        .append("-").append(instance.getPromotionLocation()).append("-").append(instance.getGroupDesc())
                        .append("-").append(instance.getBrandCn()).append("-").append(json.getString("priorVendorCode")) ;

                instance.setParentId(SaafToolUtils.isNullOrEmpty(ttaMonthlyCheckingEntityHi.getParentId())?ttaMonthlyCheckingEntityHi.getOsdId():ttaMonthlyCheckingEntityHi.getParentId());
                instance.setParentVendorCode(SaafToolUtils.isNullOrEmpty(ttaMonthlyCheckingEntityHi.getParentId())?ttaMonthlyCheckingEntityHi.getPriorVendorCode():ttaMonthlyCheckingEntityHi.getParentVendorCode());
                instance.setParentVendorName(SaafToolUtils.isNullOrEmpty(ttaMonthlyCheckingEntityHi.getParentId())?ttaMonthlyCheckingEntityHi.getPriorVendorName():ttaMonthlyCheckingEntityHi.getParentVendorName());

                instance.setPriorVendorCode(json.getString("priorVendorCode"));
                instance.setPriorVendorName(json.getString("priorVendorName"));
                //采购确认金额
                instance.setNoUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));

                //拆分后的应收金额
                BigDecimal currentNoReceiveAmount = (baseInstance.getNoReceiveAmount()==null?basezZero:baseInstance.getNoReceiveAmount()).multiply(json.getBigDecimal("noUnconfirmAmount")).divide(totalBigD,0,BigDecimal.ROUND_HALF_UP);
                BigDecimal currentReceiveAmount = (baseInstance.getReceiveAmount()==null?basezZero:baseInstance.getReceiveAmount()).multiply(json.getBigDecimal("noUnconfirmAmount")).divide(totalBigD,0,BigDecimal.ROUND_HALF_UP);

                //应收金额

                if(paramsJSON.size()-1 == i){
                    instance.setNoReceiveAmount((baseInstance.getNoReceiveAmount()==null?basezZero:baseInstance.getNoReceiveAmount()).subtract(totalBigDNoIncludeLast));
                    //应收金额(含加成)
                    instance.setReceiveAmount((baseInstance.getReceiveAmount()==null?basezZero:baseInstance.getReceiveAmount()).subtract(totalBigDIncludeLast));

                }else{
                    //应收金额(不含加成)
                    instance.setNoReceiveAmount(currentNoReceiveAmount);
                    //应收金额(含加成)
                    instance.setReceiveAmount(currentReceiveAmount);
                    totalBigDNoIncludeLast = totalBigDNoIncludeLast.add(currentNoReceiveAmount) ;
                    totalBigDIncludeLast = totalBigDIncludeLast.add(currentReceiveAmount) ;

                }
                if("TTA".equals(instance.getCollect())){
                    instance.setUnconfirmAmount(
                            json.getBigDecimal("noUnconfirmAmount").multiply( (new BigDecimal("100")).add(instance.getAdditionRate()))
                                    .divide(new BigDecimal("100"),0,BigDecimal.ROUND_HALF_UP)
                    );
                }else{
                    instance.setUnconfirmAmount(json.getBigDecimal("noUnconfirmAmount"));
                }
                int compareInt = instance.getUnconfirmAmount().subtract(instance.getReceiveAmount()==null ?basezZero:instance.getReceiveAmount())
                        .subtract(instance.getAdjustReceiveAmount()==null?basezZero:instance.getAdjustReceiveAmount())
                        .compareTo(BigDecimal.ZERO);
                if(0 > compareInt){
                    //设置差异
                    instance.setDifference(instance.getUnconfirmAmount()
                            .subtract(instance.getReceiveAmount()==null?basezZero:instance.getReceiveAmount())
                            .subtract(instance.getAdjustReceiveAmount()==null?basezZero:instance.getAdjustReceiveAmount()));
                }else{
                    instance.setDifference(new BigDecimal("0"));
                }

                instance.setContent(stringBuffer.toString());
                instance.setOperatorUserId(userId);
                instance.setStatus(1);
                objects.add(instance);
            }
        }

        ttaMonthlyCheckingDAO_HI.saveOrUpdateAll(objects);
        ttaMonthlyCheckingDAO_HI.saveOrUpdate(ttaMonthlyCheckingEntityHi);
        return objects;
    }

    /**
     * 查询CW 报表的SUNMARY
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    @Override
    public List<TtaMonthlyCheckingEntity_HI_RO> findOsdMajorSummaryList(JSONObject queryParamJSON) throws Exception{
        StringBuffer sql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        SaafToolUtils.validateJsonParms(queryParamJSON,"batchCode");
        sql.append(TtaMonthlyCheckingEntity_HI_RO.SUMMARY_QUERY) ;
        SaafToolUtils.parperParam(queryParamJSON, "tcc.batch_code", "batchCode", sql, map, "in");
        sql.append(" group by tcc.group_desc") ;
        List<TtaMonthlyCheckingEntity_HI_RO> list = ttaMonthlyCheckingDAO_HI_RO.findList(sql, map);
        return list;
    }

    @Override
    public List<TtaMonthlyCheckingEntity_HI_RO> findAmountList(JSONObject queryParamJSON) throws Exception{
        StringBuffer sql = new StringBuffer();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("groupCode",queryParamJSON.getString("groupCode"));
        map.put("year",queryParamJSON.getString("year"));
        map.put("oldYear",queryParamJSON.getString("oldYear"));
        map.put("supplierCode",queryParamJSON.getString("supplierCode"));//采购确认供应商
        map.put("promotionLocation",queryParamJSON.getString("promotionLocation"));//促销位置
        map.put("deptDesc",queryParamJSON.getString("deptDesc"));
        map.put("storesNum",queryParamJSON.getInteger("storesNum"));

        sql.append(TtaMonthlyCheckingEntity_HI_RO.QUERY_UNIT_AMOUNT) ;
        List<TtaMonthlyCheckingEntity_HI_RO> list = ttaMonthlyCheckingDAO_HI_RO.findList(sql, map);
        return list;
    }

    /**
     * 报表导出  ABOI模板
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     * @throws Exception
     */
    @Override
    public Pagination<TtaMonthlyCheckingEntity_HI_RO> findExportPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        SaafToolUtils.validateJsonParms(queryParamJSON,"batchCode");
        StringBuffer sql = new StringBuffer();
        StringBuffer countSql = new StringBuffer();
        sql.append(TtaMonthlyCheckingEntity_HI_RO.ABOI_TEMPLATES_QUERY) ;
        Map<String,Object> map = new HashMap<String,Object>();
        SaafToolUtils.parperParam(queryParamJSON, "tom.batch_code", "batchCode", sql, map, "in");
        Pagination<TtaMonthlyCheckingEntity_HI_RO> resultList = ttaMonthlyCheckingDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), map, pageIndex, pageRows);
        return resultList;
    }

    @SuppressWarnings("all")
    @Override
    public void updateImportOSDInfo(JSONObject jsonObject, MultipartFile file) throws Exception {
        try {
            Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaMonthlyCheckingEntity_HI_MODEL.class,0);
            List<Map<String, Object>> datas = (List<Map<String, Object>>)result.get("datas");
            if (datas != null && !datas.isEmpty()) {
                datas.forEach(item->{
                    item.put("CREATED_BY",jsonObject.getInteger("userId"));
                    item.put("LAST_UPDATED_BY",jsonObject.getInteger("userId"));
                    item.put("LAST_UPDATE_LOGIN",jsonObject.getInteger("userId"));
                    item.put("LAST_UPDATE_DATE", new Date());
                    item.put("CREATION_DATE", new Date());
                });
            }
            ttaMonthlyCheckingDAO.updateBatchJDBC("tta_osd_monthly_checking", TtaMonthlyCheckingEntity_HI_MODEL.class,  datas);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw  new Exception(e);
        }
    }

    @Override
    public Pagination<TtaMonthlyCheckingEntity_HI_RO>  findProcessSummaryOsdInfo(JSONObject queryParamJSON, UserSessionBean sessionBean) throws Exception {
        Pagination<TtaMonthlyCheckingEntity_HI_RO> pagination = ttaMonthlyCheckingDAO_HI_RO.findPagination(TtaMonthlyCheckingEntity_HI_RO.getOsdProcessSummary(queryParamJSON.getString("batchCode"),sessionBean.getUserName(), sessionBean.getUserId()), new HashMap<String, Object>(), 0, Integer.MAX_VALUE);
        return pagination;
    }

    @Override
    public Pagination<TtaMonthlyCheckingEntity_HI_RO>  findProcessOsdInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows, UserSessionBean sessionBean) throws Exception {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        sql.append(TtaMonthlyCheckingEntity_HI_RO.getQueryProcess(sessionBean.getUserName(), sessionBean.getUserId()));
        Pagination<TtaMonthlyCheckingEntity_HI_RO> resultList = ttaMonthlyCheckingDAO_HI_RO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), queryMap, pageIndex, pageRows);
        return resultList;
    }


    @Override
    public void findCheckGroupCount(JSONObject paramsJson, UserSessionBean sessionBean) {
        LOGGER.info("当前登录的用户信息：{}" , SaafToolUtils.toJson(sessionBean));
        String isCheckProcess = jedisCluster.hget("GLOBAL_REDIS_CACHE","IS_CHECK_PROCESS");
        LOGGER.info("流程校验开关参数值：" + isCheckProcess);
        if ("N".equalsIgnoreCase(isCheckProcess)) {
            return;
        }
        /* 用户需求：
        Trading Manager审批节点提示当前部门仍有单据未审批。
        建议：仅在ATD审批节点需要校验部门是否提交全部单据（Food部门需要在Senior Manager节点校验,Health在TC节点）。
        后续优化：各审批节点配置校验不同的单据批量审批完成情况。*/
        //用户类型： Senior Trading Manager 高级经理（userType:25），TC(userType:30), ATD(userType:40)
        //部门信息：1:Health and Fitness, 5： Food and Sundries
        LOGGER.info("当前登录的用户信息：{}" , SaafToolUtils.toJson(sessionBean));
        String userType = sessionBean.getUserType();
        String deptCode = sessionBean.getGroupCode();
        if (StringUtils.isBlank(userType) || StringUtils.isBlank(deptCode)) {
            Assert.isTrue(false, "sorry，不能审批该单据，请联系管理员配置你的用户类型或小部门信息！");
        }
        if ("40".equalsIgnoreCase(userType)) {
            //ATD审批节点需要校验部门是否提交全部单据
            checkProcesss(paramsJson);
        }
        if ("5".equalsIgnoreCase(deptCode) && "25".equalsIgnoreCase(userType)) {
            //Food部门需要在Senior Manager节点校验
            checkProcesss(paramsJson);
        }
        if ("1".equalsIgnoreCase(deptCode) && "30".equalsIgnoreCase(userType)) {
            //Health在TC节点校验
            checkProcesss(paramsJson);
        }
    }


    public void checkProcesss(JSONObject paramsJson) {
        String batchCode = paramsJson.getString("batchId");
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("batchCode", batchCode);
        List<Map<String, Object>> listMap = ttaMonthlyCheckingDAO_HI.queryNamedSQLForList(TtaMonthlyCheckingEntity_HI_RO.getCheckGroupOsd(), queryMap);
        JSONArray paramsList = paramsJson.getJSONArray("paramsList");
        for (int idx = 0; idx < paramsList.size(); idx ++) {
            JSONObject jsonObject = paramsList.getJSONObject(idx);
            String paramGroupCode = jsonObject.getString("groupCode");
            int paramGroupDmCnt = jsonObject.getInteger("groupOsdCnt");
            String  groupDesc = jsonObject.getString("groupDesc");

            for (Map<String, Object> map: listMap) {
                String groupCode = map.get("groupCode") + "";
                if (!paramGroupCode.equals(groupCode)) {
                    continue;
                }
                int cnt = Integer.parseInt(map.get("cnt") + "");
                Assert.isTrue(paramGroupDmCnt == cnt,  groupDesc + "部门还有" + (cnt - paramGroupDmCnt) + "条单据未到此节点，暂不能审批/驳回！");
            }
        }
    }
}


