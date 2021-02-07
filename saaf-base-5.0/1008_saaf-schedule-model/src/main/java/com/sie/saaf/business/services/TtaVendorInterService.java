package com.sie.saaf.business.services;

import com.sie.saaf.business.model.entities.TtaVendorInterEntity_HI;
import com.sie.saaf.business.model.inter.ITtaVendorInter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaVendorInterService")
public class TtaVendorInterService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TtaVendorInterService.class);

    @Autowired
    private ITtaVendorInter ttaVendorInterServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.ttaVendorInterServer;
    }

    /**
     * 批量保存供应商信息
     */
    @RequestMapping("/saveBatchVendor")
    public String saveBatchVendor() {
        try {
            ITtaVendorInter iTtaVendorInter = (ITtaVendorInter) SpringBeanUtil.getBean("ttaVendorInterServer");

            iTtaVendorInter.saveBatchVendor(new ArrayList<TtaVendorInterEntity_HI>());
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}