package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseDept_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePersonAss_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionAss_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseDeptAssembly;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("baseDeptAssemblyServer")
public class BaseDeptAssemblyServer implements IBaseDeptAssembly {
    @Autowired
    private BaseViewObject<BaseDept_HI_RO> baseDept_HI_RO;

    @Autowired
    private BaseViewObject<BasePersonAss_HI_RO> basePersonAss_HI_RO;

    @Autowired
    private BaseViewObject<BasePositionAss_HI_RO> basePositionAss_HI_RO;

    @Override
    public Pagination findDeptList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BaseDept_HI_RO.QUERY_DEPT_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "dept.ou_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "dept.parent_org_id", "parentDeptId", sql, paramsMap, "=");
        String endDate = queryParamJSON.getString("endDate");
        if (StringUtils.isEmpty(endDate)){
            queryParamJSON.put("endDate", SaafDateUtils.convertDateToString(new Date(), "yyyy-MM-dd"));
        }
        SaafToolUtils.parperParam(queryParamJSON, "dept.end_date", "endDate", sql, paramsMap, ">=");
        SaafToolUtils.parperParam(queryParamJSON, "dept.org_level", "deptLevel", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "dept.org_name", "deptName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "dept2.org_name", "parentDeptName", sql, paramsMap, "like");
        sql.append(" ORDER BY dept.org_level,dept.org_id");
        Pagination<BaseDept_HI_RO> pagination = baseDept_HI_RO.findPagination(sql.toString(),SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public Pagination findPersonAssList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BasePersonAss_HI_RO.QUERY_PERSON_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "assign.ou_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "department.department_id", "deptId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "department.department_name", "deptName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "assign.person_id", "personId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "person.person_name", "personName", sql, paramsMap, "like");
        Pagination<BasePersonAss_HI_RO> pagination = basePersonAss_HI_RO.findPagination(sql.toString(),SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public Pagination findPersonAssListByPerson(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(BasePositionAss_HI_RO.QUERY_POSITION_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "assign.ou_id", "orgId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "assign.person_id", "personId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "organization.org_id", "deptId", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "organization.org_name", "deptName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "person.person_name", "personName", sql, paramsMap, "like");
        Pagination<BasePositionAss_HI_RO> pagination = basePositionAss_HI_RO.findPagination(sql.toString(),SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        sql.append(" ORDER BY assign.primary_flag DESC");
        return pagination;
    }
}
