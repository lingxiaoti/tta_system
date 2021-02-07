package com.sie.saaf.base.message.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.message.model.entities.BaseMessageInstationEntity_HI;
import com.sie.saaf.base.message.model.entities.readonly.BaseMessageInstationEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseMessageInstation extends IBaseCommon<BaseMessageInstationEntity_HI> {
    /**
     * 查询站内消息列表
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseMessageInstationEntity_HI_RO> findMessInstationPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
    /**
     * 查询站内消息列表(含图片)
     *
     * @param queryParamJSON
     * @param pageIndex
     * @param pageRows
     * @return
     */
    Pagination<BaseMessageInstationEntity_HI_RO> findPaginationIncludeImg(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    /**
     * 新增站内接收消息
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    BaseMessageInstationEntity_HI saveMessageInstation(JSONObject paramsJSON) throws Exception;

    /**
     * 更新消息状态
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    JSONObject updateMessageStatus(JSONObject paramsJSON) throws Exception;

    /**
     * 批量更新消息状态
     *
     * @param paramsJSON
     * @return
     * @throws Exception
     */
    JSONObject updateMessageStatusByBatch(JSONObject paramsJSON) throws Exception;

    /**
     * 撤回站内消息：查询需要撤回的所有消息记录
     *
     * @param conMessId
     * @return
     * @throws Exception
     */
    List<BaseMessageInstationEntity_HI_RO> findNeedToRevokeMess(Integer conMessId);

    /**
     * 获取用户新
     * @param paramJSON
     * @return
     */
    JSONObject findUserInfo(JSONObject paramJSON);

    /**
     * 查看消息
     * @param paramJSON
     * @return
     */
    JSONObject findMessInstationDetail(JSONObject paramJSON);
}
