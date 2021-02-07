package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbAwardPolistEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbAwardPolistEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbAwardPolistEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaHwbAwardPolist;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaHwbAwardPolistServer")
public class TtaHwbAwardPolistServer extends BaseCommonServer<TtaHwbAwardPolistEntity_HI> implements ITtaHwbAwardPolist{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbAwardPolistServer.class);
    @Autowired
    private ViewObject<TtaHwbAwardPolistEntity_HI> ttaHwbAwardPolistDAO_HI;

    @Autowired
    private BaseViewObject<TtaHwbAwardPolistEntity_HI_RO> ttaHwbAwardPolistDAO_HI_RO;

    public TtaHwbAwardPolistServer() {
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
        TtaHwbAwardPolistEntity_HI ttaHwbAwardPolistEntity_hi = null ;
        List<TtaHwbAwardPolistEntity_HI> byProperty = new ArrayList<>();
        //每次导入前删除同一批次历史数据
        ttaHwbAwardPolistDAO_HI.executeSql("delete from TTA_HWB_AWARD_POLIST where TIMES_VERSION = '"+jsonArray.getJSONObject(0).get("timesVersion")+"'");
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
    public Pagination<TtaHwbAwardPolistEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
        StringBuffer sql = new StringBuffer(TtaHwbAwardPolistEntity_HI_RO.TTA_HWB_AWARD_POLIST);
        Map<String,Object> map = new HashMap<String,Object>();
        SaafToolUtils.parperHbmParam(TtaHwbAwardPolistEntity_HI_RO.class, queryParamJSON, "s.TIMES_VERSION", "timesVersion", sql, map, "fulllike");
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, " id desc", false);
        Pagination<TtaHwbAwardPolistEntity_HI_RO> resultList =ttaHwbAwardPolistDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
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
            //ttaHwbAwardPolistDAO_HI.delete(queryParamJSON.getInteger("timesVersion"));
            ttaHwbAwardPolistDAO_HI.executeSql("delete from TTA_HWB_AWARD_POLIST where TIMES_VERSION = '"+queryParamJSON.getInteger("timesVersion")+"'");
        }
        return result;
    }

}
