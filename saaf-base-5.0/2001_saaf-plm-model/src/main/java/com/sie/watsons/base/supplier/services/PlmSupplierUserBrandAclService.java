package com.sie.watsons.base.supplier.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplier.model.inter.IPlmSupplierUserBrandAcl;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plmSupplierUserBrandAclService")
public class PlmSupplierUserBrandAclService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierUserBrandAclService.class);

    @Autowired
    private IPlmSupplierUserBrandAcl plmSupplierUserBrandAclServer;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.plmSupplierUserBrandAclServer;
    }


    /**
     * 生成或修改供应商品牌数据
     *
     * @param params JSON参数，对象属性的JSON格式
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateSupplierUserBrand")
    public String saveOrUpdateSupplierUserBrand(@RequestParam(required = false) String params) {
        try {
            JSONObject parameters = JSON.parseObject(params);
            Integer resultCount = plmSupplierUserBrandAclServer.saveOrUpdateSupplierUserBrand(parameters);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, resultCount, null).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}