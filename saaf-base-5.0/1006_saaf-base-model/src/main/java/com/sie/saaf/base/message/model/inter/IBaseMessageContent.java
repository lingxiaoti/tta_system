package com.sie.saaf.base.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageContentEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageContentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseMessageContent extends IBaseCommon<BaseMessageContentEntity_HI> {
    /**
     * 查询站内消息列表
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseMessageContentEntity_HI_RO> findBaseConMessPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 查询站内消息已读未读数量
     *
     * @param conMessId
     * @return
     */
    List<BaseMessageContentEntity_HI_RO> findIsOrNotConsulted(Integer conMessId);

    /**
     * 新增&修改站内消息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    BaseMessageContentEntity_HI saveOrUpdateMessContent(JSONObject paramsJSON, int userId) throws Exception;

    /**
     * 获取待发送消息分组
     * @param queryParamJSON
     * {
     *      conMessId：消息ID
     * }
     * @return
     */
    List<BaseMessageContentEntity_HI_RO> findWaitingSendList(JSONObject queryParamJSON);
}
