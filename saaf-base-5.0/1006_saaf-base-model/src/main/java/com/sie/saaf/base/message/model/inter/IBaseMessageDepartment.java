package com.sie.saaf.base.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageDepartmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IBaseMessageDepartment extends IBaseCommon<BaseMessageDepartmentEntity_HI> {
    /**
     * 新增&修改站内消息待发送对象-部门和部门中的人员
     *
     * @param paramsJSON
     * @param userId
     * @throws Exception
     */
    void saveOrUpdateMessDeptAndPerson(JSONObject paramsJSON, int userId) throws Exception;
}
