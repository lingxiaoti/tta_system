package com.sie.watsons.base.supplier.services;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserItem;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张韧炼
 * @create 2020-09-27 上午9:42
 **/
@RestController
@RequestMapping("/plmSupplierUserItemService")
public class PlmSupplierUserItemService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserBrandAclService.class);
    @Autowired
    private IPlmSupplierUserItem plmSupplierUserItemServer;

    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return plmSupplierUserItemServer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "saveUserItems")
    public String saveUserItems(@RequestParam(value = "supplierNumbers") String supplierNumbers,
                                @RequestParam(value = "supplierUserId") Integer supplierUserId,
                                @RequestParam(value = "supplierUserName") String supplierUserName) {
        try {
            int resultCount = plmSupplierUserItemServer.saveUserItems(supplierNumbers, supplierUserId, supplierUserName);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, resultCount, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "deleteClearData")
    public String deleteClearData() {
        try {
            plmSupplierUserItemServer.deleteClearData();
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
