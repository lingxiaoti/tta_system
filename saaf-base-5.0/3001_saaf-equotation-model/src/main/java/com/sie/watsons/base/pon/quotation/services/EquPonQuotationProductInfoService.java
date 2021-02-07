package com.sie.watsons.base.pon.quotation.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pon.quotation.model.entities.readonly.EquPonQuotationProductInfoEntity_HI_RO;
import com.sie.watsons.base.pon.quotation.model.inter.IEquPonQuotationProductInfo;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equPonQuotationProductInfoService")
public class EquPonQuotationProductInfoService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquPonQuotationProductInfoService.class);

    @Autowired
    private IEquPonQuotationProductInfo equPonQuotationProductInfoServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.equPonQuotationProductInfoServer;
    }

    @PostMapping("/saveImportForQuotationProduct")
    public String saveImportForQuotationProduct(@RequestParam(required = true) String params) {
        try {
            Integer userId = getSessionUserId();
            int size = equPonQuotationProductInfoServer.saveImportForQuotationProduct(params, userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "导入成功", size, null).toString();
        } catch (IllegalArgumentException e) {
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        }
    }

    @PostMapping("/findQuoProductInfo")
    public String findQuoProductInfo(@RequestParam("params") String params) {
        try {
            JSONObject jsonObject = parseObject(params);
            List<EquPonQuotationProductInfoEntity_HI_RO> quoProductInfo = equPonQuotationProductInfoServer.findQuoProductInfo(jsonObject);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, quoProductInfo).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @PostMapping("/deleteQuotationProductInfo")
    public String deleteQuotationProductInfo(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            return equPonQuotationProductInfoServer.deleteQuotationProductInfo(jsonObject, userId);
        } catch (IllegalArgumentException e) {
            LOGGER.info(e.getMessage());
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, null).toString();
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    /**
     * 产品模版导出
     */
    @RequestMapping(method = RequestMethod.POST, value = "productTemplateExport")
    public String productTemplateExport(@RequestParam("params") String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        String accessPath = null;
        try {
            ResultFileEntity result = equPonQuotationProductInfoServer.productTemplateExport(jsonObject);
            accessPath = result.getAccessPath() + "?attname=" + result.getFileName();
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, accessPath).toString();
        } catch (Exception e) {
            LOGGER.info("产品明细导出失败" + e.getMessage());
        }
        return SToolUtils.convertResultJSONObj(ERROR_STATUS, ERROR_MSG, 0, "产品明细导出失败.请联系管理员").toString();
    }
}