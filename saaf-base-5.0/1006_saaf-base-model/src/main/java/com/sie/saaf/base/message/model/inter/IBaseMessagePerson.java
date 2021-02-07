package com.sie.saaf.base.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessagePersonEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessagePersonEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

public interface IBaseMessagePerson extends IBaseCommon<BaseMessagePersonEntity_HI> {
    /**
     * 查看发送对象：查询包含接收人员信息
     * @param queryParamJSON
     * * {
     *     conMessId：消息ID，
     *     depMessId：包含部门记录ID，
     *     deptId：部门ID
     * }
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseMessagePersonEntity_HI_RO> findReceiverPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
