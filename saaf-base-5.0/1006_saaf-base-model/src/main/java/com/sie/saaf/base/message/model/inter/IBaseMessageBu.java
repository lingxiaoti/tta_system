package com.sie.saaf.base.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageBuEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageBuEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.List;

public interface IBaseMessageBu extends IBaseCommon<BaseMessageBuEntity_HI> {
    /**
     * 新增&修改站内消息和接收对象BU组合信息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    JSONObject saveOrUpdateMessContentAndBu(JSONObject paramsJSON, int userId) throws Exception;

    /**
     * 新增&修改接收对象BU组合信息
     *
     * @param paramsJSON
     * @param userId
     * @return
     * @throws Exception
     */
    BaseMessageBuEntity_HI saveOrUpdateMessageBu(JSONObject paramsJSON, int userId) throws Exception;

    /**
     * 查询接收对象信息
     * @param conMessId 消息ID
     * @return 接收对象信息列表
     */
    List<BaseMessageBuEntity_HI_RO> findBuInfoList(Integer conMessId);
}
