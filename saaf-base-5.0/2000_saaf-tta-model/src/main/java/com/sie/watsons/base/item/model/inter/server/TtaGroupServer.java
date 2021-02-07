package com.sie.watsons.base.item.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.item.model.entities.readonly.TtaGroupEntity_HI_RO;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.item.model.entities.TtaGroupEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.item.model.inter.ITtaGroup;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaGroupServer")
public class TtaGroupServer extends BaseCommonServer<TtaGroupEntity_HI> implements ITtaGroup {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaGroupServer.class);

    @Autowired
    private ViewObject<TtaGroupEntity_HI> ttaGroupDAO_HI;
    @Autowired
    private BaseViewObject<TtaGroupEntity_HI_RO> ttaGroupDAO_HI_RO;

    @Autowired
    private BaseViewObject<BaseDepartmentEntity_HI_RO> baseDepartmentDAO_HI_RO;

    public TtaGroupServer() {
        super();
    }


    @Override
    public Pagination<BaseDepartmentEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        String departmentType = queryParamJSON.getString("departmentType");
        String parentDepartmentId = queryParamJSON.getString("parentDepartmentId");
        LOGGER.info("传入参数parentDepartmentId :{},departmentType : {}", new Object[]{departmentType, parentDepartmentId});
        System.out.println("参数 parentDepartmentId : " + parentDepartmentId);
        if (StringUtils.isBlank(parentDepartmentId)) {
            queryParamJSON.put("parentDepartmentId", -1000);
        }

        StringBuffer sql = new StringBuffer();
        sql.append(TtaGroupEntity_HI_RO.TTA_ITEM_V);//2019.8.1这个视图改成是基于base_department生成的
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        //SaafToolUtils.parperParam(queryParamJSON, "v.PARENT_DEPARTMENT_ID", "parentDepartmentId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_TYPE", "departmentType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_CODE", "departmentCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_FULL_NAME", "departmentFullName", sql, paramsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.DEPARTMENT_ID asc", false);
        //Pagination<TtaGroupEntity_HI_RO> findList = ttaGroupDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        Pagination<BaseDepartmentEntity_HI_RO> findList = baseDepartmentDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return findList;
    }

    @Override
    public List<BaseDepartmentEntity_HI_RO> findGroup(JSONObject queryParamJSON) {
        String departmentType = queryParamJSON.getString("departmentType");
        if (StringUtils.isBlank(departmentType)) {
            queryParamJSON.put("departmentType","30");
        }
        StringBuffer sql = new StringBuffer();
        sql.append(TtaGroupEntity_HI_RO.TTA_ITEM_V);//2019.8.1这个视图改成是基于base_department生成的
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_TYPE", "departmentType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_CODE", "departmentCode", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "v.DEPARTMENT_FULL_NAME", "departmentFullName", sql, paramsMap, "like");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "v.department_code asc", false);
        List<BaseDepartmentEntity_HI_RO> findList = baseDepartmentDAO_HI_RO.findList(sql, paramsMap);
        return findList;
    }
}
