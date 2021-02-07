package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbSittingPlanEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbSittingPlanEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbSittingPlanEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaHwbSittingPlan;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaHwbSittingPlanServer")
public class TtaHwbSittingPlanServer extends BaseCommonServer<TtaHwbSittingPlanEntity_HI> implements ITtaHwbSittingPlan{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbSittingPlanServer.class);
    @Autowired
    private ViewObject<TtaHwbSittingPlanEntity_HI> ttaHwbSittingPlanDAO_HI;

    @Autowired
    private BaseViewObject<TtaHwbSittingPlanEntity_HI_RO> ttaHwbSittingPlanDAO_HI_RO;

    public TtaHwbSittingPlanServer() {
        super();
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
        return date;
    }

    /**
     * 批量导入
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public int saveImportInfo(JSONObject queryParamJSON) throws Exception{
        JSONArray jsonArray = queryParamJSON.getJSONArray("data");
        JSONArray errList = new JSONArray();
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        TtaHwbSittingPlanEntity_HI ttaHwbSittingPlanEntity_hi = null ;
        List<TtaHwbSittingPlanEntity_HI> byProperty = new ArrayList<>();
        //每次导入前删除同一批次历史数据
        ttaHwbSittingPlanDAO_HI.executeSql("delete from TTA_HWB_SITTING_PLAN where TIMES_VERSION = '"+jsonArray.getJSONObject(0).get("timesVersion")+"'");
        for(int i=0;i<jsonArray.size();i++){
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject errJson = new JSONObject();
            String msgStr = "";
            try {
                json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
                json.put("timesVersion",getdate());
                json.put("status","1");
                super.saveOrUpdate(json);
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
        }
        return jsonArray.size();
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
    public Pagination<TtaHwbSittingPlanEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaHwbSittingPlanEntity_HI_RO.TTA_HWB_SITTING_PLAN);
        Map<String,Object> map = new HashMap<String,Object>();
        SaafToolUtils.parperHbmParam(TtaHwbSittingPlanEntity_HI_RO.class, queryParamJSON, "s.TIMES_VERSION", "timesVersion", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " id desc", false);
        Pagination<TtaHwbSittingPlanEntity_HI_RO> resultList =ttaHwbSittingPlanDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
        return resultList;
    }

    /**
     *
     * @param queryParamJSON
     * @return
     * @throws Exception
     */
    public JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception{
        JSONObject result = new JSONObject();
        if(queryParamJSON.getInteger("timesVersion")!=null || !"".equals(queryParamJSON.getInteger("timesVersion"))){
            //ttaHwbSittingPlanDAO_HI.delete(queryParamJSON.getInteger("timesVersion"));
            ttaHwbSittingPlanDAO_HI.executeSql("delete from TTA_HWB_SITTING_PLAN where TIMES_VERSION = '"+queryParamJSON.getInteger("timesVersion")+"'");
        }
        return result;
    }

}
