package com.sie.saaf.base.user.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.readonly.BaseUserPDAProOrgChannel_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseUserPDAProOrgChannel;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangyipo
 * @date 2018/2/25
 */

@RestController
@RequestMapping("/baseUserPDAProOrgChannelService")
public class BaseUserPDAProOrgChannelService extends CommonAbstractService {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserPDAProOrgChannelService.class);

    @Autowired
    IBaseUserPDAProOrgChannel baseUserPDAProOrgChannelServer;


    @Override
    public IBaseCommon getBaseCommonServer() { return null; }
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
    @RequestMapping(method = RequestMethod.POST,value = "findBaseUserPDAChannelProductSyn")
    public String findBaseUserPDAChannelProductSyn(@RequestParam(required = false) String params ){
        try {
            JSONObject queryParamJSON = parseObject(params);
            BaseUserPDAProOrgChannel_HI_RO baseUserPDAChannelProductSyn = baseUserPDAProOrgChannelServer.findBaseUserPDAChannelProductSyn(queryParamJSON);

            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,"",1,new JSONArray().fluentAdd(JSON.toJSON(baseUserPDAChannelProductSyn))).toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


}
