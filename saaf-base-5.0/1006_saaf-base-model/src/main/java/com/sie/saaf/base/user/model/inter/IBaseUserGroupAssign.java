package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.BaseLookupValuesEntity_HI;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.BaseUserGroupAssignEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserGroupAssignEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseUserGroupAssign extends IBaseCommon<BaseUserGroupAssignEntity_HI> {

    /**
     * 指定用户查询关联群组
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForUser(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 指定用户查询关联群组
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForGroup(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    Pagination<BaseUserGroupAssignEntity_HI_RO> findUserGroupAssignsForGroup2(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 指定用户新增一个或多个群组
     * @param queryParamJSON
     * @return
     */
    String saveUserGroupAssignsForUser(JSONObject queryParamJSON);

    /**
     * 指定群组关联一个或多个用户
     * @param queryParamJSON
     * @return
     */
    String saveUserGroupAssignsForGroup(JSONObject queryParamJSON);

    /**
     * 指定用户删除一个或多个群组
     * @param queryParamJSON
     * @return
     */
    String deleteUserGroupAssignsForUser(JSONObject queryParamJSON);

    /**
     * 指定群组删除一个或多个用户
     * @param queryParamJSON
     * @return
     */
    String deleteUserGroupAssignsForGroup(JSONObject queryParamJSON);

    /**
     * 查询未分配指定用户的 群组
     * @param queryParamJSON
     * @return
     */
    List<BaseLookupValuesEntity_HI_RO> findNoAssignsForUser(JSONObject queryParamJSON);
}
