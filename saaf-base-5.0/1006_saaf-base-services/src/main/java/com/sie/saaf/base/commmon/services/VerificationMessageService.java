package com.sie.saaf.base.commmon.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangtao
 */
@RestController
@RequestMapping("/verificationMessageService")
public class VerificationMessageService extends CommonAbstractService {
    private static final Logger log= LoggerFactory.getLogger(VerificationMessageService.class);

    /**
     *
     * @param params
     * {
     *     phoneNumber:手机号
     * }
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String upload(@RequestParam String params) throws Exception {
        try {
            JSONObject jsonObject= JSON.parseObject(params);
            SaafToolUtils.validateJsonParms(jsonObject,"phoneNumber");
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS,SUCCESS_MSG,0,null).toJSONString();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }


    }


    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
