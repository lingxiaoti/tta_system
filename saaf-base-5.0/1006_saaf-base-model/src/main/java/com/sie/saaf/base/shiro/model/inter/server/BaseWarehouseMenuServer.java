package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.entities.BaseReportDatasourceEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseWarehouseMenu_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseWarehouseMenu;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangtao
 */
@Component
public class BaseWarehouseMenuServer implements IBaseWarehouseMenu {

    @Autowired
    private BaseViewObject<BaseWarehouseMenu_HI_RO> baseWarehouseMenuDAO_HI_RO;


    @Override
    public List<BaseWarehouseMenu_HI_RO> query(JSONObject queryParamJSON) {
        StringBuffer sql = new StringBuffer(BaseWarehouseMenu_HI_RO.SQL_WAREHOUSE_MENU);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperHbmParam(BaseReportDatasourceEntity_HI.class, queryParamJSON, sql, paramsMap);
        return baseWarehouseMenuDAO_HI_RO.findList(sql, paramsMap);
    }

}
