package com.sie.watsons.base.api.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.api.model.entities.PlmRmsMapEntity_HI;
import com.sie.watsons.base.api.model.entities.readonly.PlmRmsMapEntity_HI_RO;
import com.sie.watsons.base.api.model.inter.IPlmRmsMap;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmRmsMapServer")
public class PlmRmsMapServer extends BaseCommonServer<PlmRmsMapEntity_HI> implements IPlmRmsMap {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsMapServer.class);

	@Autowired
	private ViewObject<PlmRmsMapEntity_HI> plmRmsMapDAO_HI;
    @Autowired
    private BaseViewObject<PlmRmsMapEntity_HI_RO> plmRmsMapDAO_HI_RO;

	public PlmRmsMapServer() {
		super();
	}

    @Override
    public void update(JSONObject queryJSON) throws Exception {
        JSONArray arr = queryJSON.getJSONArray("data");
        for(int i=0;i<arr.size();i++) {
            JSONObject queryParamJSON = arr.getJSONObject(i);
            String sql = new String();
            sql= "from PlmRmsMapEntity_HI s where id ='"+queryParamJSON.get("id").toString()+"' " ;
            Map<String,Object> map = new HashMap<String,Object>();
            List<PlmRmsMapEntity_HI> lineList = plmRmsMapDAO_HI.findList(sql, map);
            PlmRmsMapEntity_HI entity = null;
            if(lineList.size()>0){
                entity = lineList.get(0);
            }
            if (entity != null) {
                if(queryParamJSON.get("dataName")!=null){
                    entity.setDataName(queryParamJSON.get("dataName").toString());
                }
                if(queryParamJSON.get("dataValue")!=null){
                    entity.setDataValue(queryParamJSON.get("dataValue").toString());
                }
                if(queryParamJSON.get("status")!=null){
                    entity.setStatus(queryParamJSON.get("status").toString());
                }

                plmRmsMapDAO_HI.saveOrUpdate(entity);
            }
        }
    }

    @Override
    public void save(JSONObject queryJSON) throws Exception {
        JSONArray arr = queryJSON.getJSONArray("data");
        for(int i=0;i<arr.size();i++) {
            JSONObject queryParamJSON = arr.getJSONObject(i);
            PlmRmsMapEntity_HI entity = new PlmRmsMapEntity_HI();

                if(queryParamJSON.get("dataName")!=null){
                    entity.setDataName(queryParamJSON.get("dataName").toString());
                }
                if(queryParamJSON.get("dataValue")!=null){
                    entity.setDataValue(queryParamJSON.get("dataValue").toString());
                }
                if(queryParamJSON.get("status")!=null){
                    entity.setStatus(queryParamJSON.get("status").toString());
                }

                plmRmsMapDAO_HI.saveOrUpdate(entity);

        }
    }

    @Override
    public void delete(JSONObject queryJSON) throws Exception {
        JSONArray arr = queryJSON.getJSONArray("data");
        for(int i=0;i<arr.size();i++) {
            JSONObject queryParamJSON = arr.getJSONObject(i);
            String sql = new String();
            sql= "delete from PLM_RMS_MAP s where ID ='"+queryParamJSON.get("id").toString()+"' " ;
            plmRmsMapDAO_HI.executeSqlUpdate(sql);
        }
    }

    @Override
    public Pagination<PlmRmsMapEntity_HI_RO> findRmsData(JSONObject queryParamJSON, Integer pageIndex,Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmRmsMapEntity_HI_RO.SQL);
        Map<String, Object> queryParamMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(PlmRmsMapEntity_HI_RO.class, queryParamJSON, sql, queryParamMap);
        Pagination<PlmRmsMapEntity_HI_RO> findListResult = plmRmsMapDAO_HI_RO.findPagination(sql, queryParamMap, pageIndex, pageRows);
        return findListResult;
    }
}
