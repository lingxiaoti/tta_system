package com.sie.saaf.base.shiro.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseWarehouseMenu_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseWarehouseMenu;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangJun
 * @creteTime 2017-12-18
 */
@RestController
@RequestMapping("/baseWarehouseMenuService")
public class BaseWarehouseMenuService extends CommonAbstractService {

    private static final Logger log= LoggerFactory.getLogger(BaseWarehouseMenuService.class);

    @Autowired
    private IBaseWarehouseMenu baseWarehouseMenuServer;



    @RequestMapping(method = RequestMethod.POST, value = "find")
    public String find(@RequestParam String params) {
       try {
           JSONObject jsonObject = new JSONObject();
           if (StringUtils.isNotBlank(params))
               jsonObject = JSON.parseObject(params);
           List<BaseWarehouseMenu_HI_RO> list = baseWarehouseMenuServer.query(jsonObject);
           return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, list.size(), list).toString();
       }catch (Exception e){
           log.error(e.getMessage(),e);
       }
        return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
    }


    @Override
    public IBaseCommon<?> getBaseCommonServer() {
        return null;
    }
}
