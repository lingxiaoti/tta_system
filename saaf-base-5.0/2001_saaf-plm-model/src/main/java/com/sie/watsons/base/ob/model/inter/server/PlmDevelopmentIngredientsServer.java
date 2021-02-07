package com.sie.watsons.base.ob.model.inter.server;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentIngredientsEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentIngredientsEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentIngredients;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmDevelopmentIngredientsServer")
public class PlmDevelopmentIngredientsServer extends
        BaseCommonServer<PlmDevelopmentIngredientsEntity_HI> implements
        IPlmDevelopmentIngredients {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(PlmDevelopmentIngredientsServer.class);
    @Autowired
    private ViewObject<PlmDevelopmentIngredientsEntity_HI> plmDevelopmentIngredientsDAO_HI;
    @Autowired
    private BaseViewObject<PlmDevelopmentIngredientsEntity_HI_RO> plmDevelopmentIngredientsDAO_HI_RO;

    public PlmDevelopmentIngredientsServer() {
        super();
    }

    @Override
    public Pagination<PlmDevelopmentIngredientsEntity_HI_RO> findPlmDevelopmentIngredientsInfo(
            JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(
                PlmDevelopmentIngredientsEntity_HI_RO.QUERY_SQL);
        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
            sql = new StringBuffer(
                    PlmDevelopmentIngredientsEntity_HI_RO.REPORT_SQL);
        }
        Map<String, Object> paramsMap = new HashMap<>();
        if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("inciName_like"))){
            sql.append(" and UPPER(pdi.INCI_NAME) like '%"+queryParamJSON.getString("inciName_like").toUpperCase()+"%' ");
            queryParamJSON.remove("inciName_like");
        }
        SaafToolUtils.parperHbmParam(
                PlmDevelopmentIngredientsEntity_HI_RO.class, queryParamJSON,
                sql, paramsMap);
        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("report"))) {
            sql.append(" order by pdi2.LAST_UPDATE_DATE desc ");
        }
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,
                "count(*)");
        Pagination<PlmDevelopmentIngredientsEntity_HI_RO> pagination = plmDevelopmentIngredientsDAO_HI_RO
                .findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public List<PlmDevelopmentIngredientsEntity_HI> savePlmDevelopmentIngredientsInfo(
            JSONObject queryParamJSON) throws Exception{
        if (!SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("data"))) {
            queryParamJSON = this
                    .saveAndImportProductIngredients(queryParamJSON);
        }
        List<PlmDevelopmentIngredientsEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmDevelopmentIngredientsList")
                        .toString(), PlmDevelopmentIngredientsEntity_HI.class);
        dataList = this.changeStatusByCommand(dataList,
                queryParamJSON.getString("commandStatus"),
                queryParamJSON.getInteger("varUserId"),
                queryParamJSON.getInteger("plmDevelopmentInfoId"),
                queryParamJSON.getInteger("plmProjectId"));
        plmDevelopmentIngredientsDAO_HI.saveOrUpdateAll(dataList);
        return dataList;
    }

    // 导入
    public JSONObject saveAndImportProductIngredients(JSONObject queryParamJSON) throws Exception {
        JSONArray dataArray = queryParamJSON.getJSONArray("data");
//        for (int i = 0; i < dataArray.size(); i++) {
//            dataArray.getJSONObject(i).put("w3", dataArray.getJSONObject(i).getString("w3").trim());
//            System.out.println("total = total.add(new BigDecimal(\"" + dataArray.getJSONObject(i).getString("w3").trim() + "\"));");
//            System.out.print(dataArray.getJSONObject(i).getString("w3").trim() + ", ");
//        }
        JSONObject infoObject = queryParamJSON.getJSONObject("info");
        SaafToolUtils.validateJsonParms(infoObject, "plmProjectId",
                "plmDevelopmentInfoId");
        Integer plmProjectId = infoObject.getInteger("plmProjectId");
        Integer plmDevelopmentInfoId = infoObject
                .getInteger("plmDevelopmentInfoId");
        BigDecimal total = BigDecimal.ZERO;

        JSONArray returnArray = new JSONArray();
        JSONArray errArray = new JSONArray();

        for (int i = 0; i < dataArray.size(); i++) {
            String errMsg = "";
            JSONObject data = dataArray.getJSONObject(i);
            if (SaafToolUtils.isNullOrEmpty(data.getString("w3"))) {
                throw new IllegalArgumentException("w3未输入！");
            } else {
                data.put("w3", data.getString("w3").trim());
                data.put("w2", data.getString("w2").trim());
                data.put("w1", data.getString("w1").trim());

                if(data.getString("w1").indexOf(".")+7!=data.getString("w1").length()){
                    errMsg += "W1未保留六位小数！";
                }
                if(data.getString("w2").indexOf(".")+7!=data.getString("w2").length()){
                    errMsg += "W2未保留六位小数！";
                }
                if(data.getString("w3").indexOf(".")+7!=data.getString("w3").length()){
                    errMsg += "W3未保留六位小数！";
                }
                total = total.add(data.getBigDecimal("w3"));
            }
            data.put("plmDevelopmentInfoId", plmDevelopmentInfoId);
            data.put("plmProjectId", plmProjectId);
            data.put("rawMaterialName", data.getString("rawMaterialName")
                    .trim());
            data.put("rawMaterialProducer",
                    data.getString("rawMaterialProducer").trim());
            data.put("rawMaterialSourceArea",
                    data.getString("rawMaterialSourceArea").trim());
            data.put("standardChineseName",
                    data.getString("standardChineseName").trim());
            data.put("inciName", data.getString("inciName").trim());
            data.put("inciOrCiSource", data.getString("inciOrCiSource").trim());
            data.put("casNumber", data.getString("casNumber").trim());
            data.put("einecsNumber", data.getString("einecsNumber").trim());
            data.put("function", data.getString("function").trim());
            returnArray.add(data);
            if(!errMsg.equals("")){
                JSONObject errRow = new JSONObject();
                errRow.put("ERR_MESSAGE", errMsg);
                errRow.put("ROW_NUM",data.get("ROW_NUM"));
                errArray.add(errRow);
            }
        }
        if(errArray.size()!=0){
            throw new Exception(errArray.toJSONString());
        }
        if (total.compareTo(new BigDecimal(100)) != 0) {
            throw new IllegalArgumentException("w3总和不为100，请检查数据！");
        }

        List<PlmDevelopmentIngredientsEntity_HI> deleteArray = plmDevelopmentIngredientsDAO_HI
                .findByProperty("plmDevelopmentInfoId", plmDevelopmentInfoId);
        plmDevelopmentIngredientsDAO_HI.deleteAll(deleteArray);

        queryParamJSON.put("plmDevelopmentIngredientsList", returnArray);
        return queryParamJSON;
    }

    public List<PlmDevelopmentIngredientsEntity_HI> changeStatusByCommand(
            List<PlmDevelopmentIngredientsEntity_HI> dataList,
            String commandStatus, Integer userId, Integer plmDevelopmentInfoId,
            Integer plmProjectId) {
        for (PlmDevelopmentIngredientsEntity_HI data : dataList) {

            data.setOperatorUserId(userId);
            if (plmProjectId != null)
                data.setPlmProjectId(plmProjectId);
            if (plmDevelopmentInfoId != null) {
                data.setPlmDevelopmentInfoId(plmDevelopmentInfoId);
            }
        }
        return dataList;
    }

    @Override
    public Integer deletePlmDevelopmentIngredientsInfo(JSONObject queryParamJSON) {
        if (SaafToolUtils.isNullOrEmpty(queryParamJSON
                .getJSONArray("plmDevelopmentIngredientsList"))) {
            PlmDevelopmentIngredientsEntity_HI entity = JSON.parseObject(
                    queryParamJSON.toString(),
                    PlmDevelopmentIngredientsEntity_HI.class);
            plmDevelopmentIngredientsDAO_HI.delete(entity);
            return 1;
        }
        List<PlmDevelopmentIngredientsEntity_HI> dataList = JSON.parseArray(
                queryParamJSON.getJSONArray("plmDevelopmentIngredientsList")
                        .toString(), PlmDevelopmentIngredientsEntity_HI.class);
        plmDevelopmentIngredientsDAO_HI.deleteAll(dataList);
        return dataList.size();
    }

}
