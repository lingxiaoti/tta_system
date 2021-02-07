package com.sie.saaf.rule.services;


import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.constant.Constant;
import com.sie.saaf.rule.model.entities.RuleDimEntity_HI;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Admin on 2017/6/28.
 */
@RestController
@RequestMapping("/constantServices")
public class ConstantServices extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBusinessLineService.class);

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }

    /**
     * 业务线匹配类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getBusinessLineMapptype")
    public String getBusinessLineMapptype() {
        try {
            return SToolUtils.convertResultJSONObj("S", "success", 0, Constant.BUSINESSLINE_MAPPTYPE).toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return SToolUtils.convertResultJSONObj("E",e.getMessage(),0,null).toString();
        }
    }


    /**
     * 业务线匹配类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getruleViewType")
    public String getruleViewType() {
        try {
            return SToolUtils.convertResultJSONObj("S", "success", 0, Constant.RULEVIEW_MAPPTYPE).toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return SToolUtils.convertResultJSONObj("E",e.getMessage(),0,null).toString();
        }

    }

    /**
     *  维度的值来源
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getDimValueFrom")
    public String getDimValueFrom() {
        try {
            return SToolUtils.convertResultJSONObj("S", "success", 0, Constant.DIMVALUEFROM_MAPPTYPE).toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return SToolUtils.convertResultJSONObj("E",e.getMessage(),0,null).toString();
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "getOpreateType")
    public String getOpreateType() {
        RuleDimEntity_HI temp = new RuleDimEntity_HI();
        return SToolUtils.convertResultJSONObj("S", "success", 0, temp.getOperatorBeans()).toString();
    }

    /**
     *  数据类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getDimDataType")
    public String getDimDataType() {
        try {
            return SToolUtils.convertResultJSONObj("S", "success", 0, Constant.DIMDATA_MAPPTYPE).toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return SToolUtils.convertResultJSONObj("E",e.getMessage(),0,null).toString();
        }

    }


    /**
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "getTargetType")
    public String getTargetType() {
        try {
            return SToolUtils.convertResultJSONObj("S", "success", 0, Constant.TARGET_MAPPTYPE).toString();
        } catch (Exception e) {
            LOGGER.error("", e);
            return SToolUtils.convertResultJSONObj("E",e.getMessage(),0,null).toString();
        }
    }

}
