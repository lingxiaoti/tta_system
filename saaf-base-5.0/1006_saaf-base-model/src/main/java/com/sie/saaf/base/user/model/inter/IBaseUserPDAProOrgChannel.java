package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAProOrgChannel_HI_RO;

/**
 * @author xiangyipo
 * @date 2018/2/8
 */


public interface IBaseUserPDAProOrgChannel {
    /** 用于产品信息的同步
     *
     * @param {
     *     isUpdate :是否更新
     *     userId  : 用户Id
     *     lastDate :最后请求时间
     * }
     * @return
     * @author xiangyipo
     * @date 2018/2/24
     */
    BaseUserPDAProOrgChannel_HI_RO findBaseUserPDAChannelProductSyn(JSONObject queryParamJSON);
    }
