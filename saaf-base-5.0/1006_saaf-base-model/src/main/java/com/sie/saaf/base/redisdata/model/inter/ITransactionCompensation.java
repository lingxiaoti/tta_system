package com.sie.saaf.base.redisdata.model.inter;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;
import com.yhg.transaction.compensation.beans.RedisMessageContentBean;

import java.util.List;

/**
 * @author ZhangJun
 * @createTime 2018-03-12 10:14
 * @description
 */
public interface ITransactionCompensation extends IBaseCommon<BaseManualSupplementEntity_HI> {

    Pagination<RedisMessageContentBean> findMQMessageList(int pageIndex, int pageSize);

    JSONObject retryCompensation(JSONObject queryParamJSON);

    JSONObject deleteList(List<String> messageIndexs);

    /**
     * 获取MQ中需要补偿的所有队列列表
     * @return
     */
    List<RedisMessageContentBean> findMQMessageList();

    /**
     * 重新进行事务补偿
     * @param queryParamJSON
     * @return
     */
    boolean automaticCompensation(JSONObject queryParamJSON);

    /**
     * 分批
     * @param redisCursor 游标
     * @return
     */
    JSONObject redisTransactionDatasByBatch(String redisCursor);
}
