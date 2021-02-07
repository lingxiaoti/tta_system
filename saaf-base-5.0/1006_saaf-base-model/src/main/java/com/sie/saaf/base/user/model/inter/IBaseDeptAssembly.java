package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBaseDeptAssembly {
    /**
     * 查询部门控件
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination findDeptList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询人员控件
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination findPersonAssList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询职位控件
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination findPersonAssListByPerson(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
