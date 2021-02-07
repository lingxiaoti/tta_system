package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSpecialProductTypeEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSpecialProductTypeEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSpecialProductType;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmSpecialProductTypeServer")
public class PlmSpecialProductTypeServer extends BaseCommonServer<PlmSpecialProductTypeEntity_HI> implements IPlmSpecialProductType {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSpecialProductTypeServer.class);
    @Autowired
    private ViewObject<PlmSpecialProductTypeEntity_HI> plmSpecialProductTypeDAO_HI;
    @Autowired
    private BaseViewObject<PlmSpecialProductTypeEntity_HI_RO> plmSpecialProductTypeDAO_HI_RO;

    public PlmSpecialProductTypeServer() {
        super();
    }

    @Override
    public Pagination<PlmSpecialProductTypeEntity_HI_RO> findPlmSpecialProductTypeInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmSpecialProductTypeEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmSpecialProductTypeEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
        Pagination<PlmSpecialProductTypeEntity_HI_RO> pagination = plmSpecialProductTypeDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public PlmSpecialProductTypeEntity_HI savePlmSpecialProductTypeInfo(JSONObject queryParamJSON) {
        PlmSpecialProductTypeEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmSpecialProductTypeEntity_HI.class);
        entity.setOperatorUserId(queryParamJSON.getInteger("varUserId"));
        plmSpecialProductTypeDAO_HI.saveOrUpdate(entity);
        return entity;
    }

    @Override
    public Integer deletePlmSpecialProductTypeInfo(JSONObject queryParamJSON) {
        if(SaafToolUtils.isNullOrEmpty(queryParamJSON.getJSONArray("specialProductTypeInfoList"))){
            PlmSpecialProductTypeEntity_HI entity = JSON.parseObject(queryParamJSON.toString(), PlmSpecialProductTypeEntity_HI.class);
            plmSpecialProductTypeDAO_HI.delete(entity);
            return 1;
        }
        List<PlmSpecialProductTypeEntity_HI> dataList = JSON.parseArray(queryParamJSON.getJSONArray("specialProductTypeInfoList").toString(),PlmSpecialProductTypeEntity_HI.class);
        plmSpecialProductTypeDAO_HI.deleteAll(dataList);
        return dataList.size();
    }

}
