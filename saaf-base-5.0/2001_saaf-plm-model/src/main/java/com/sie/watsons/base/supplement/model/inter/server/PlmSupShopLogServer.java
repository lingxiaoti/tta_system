package com.sie.watsons.base.supplement.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopLogEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupShopLog;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("plmSupShopLogServer")
public class PlmSupShopLogServer extends BaseCommonServer<PlmSupShopLogEntity_HI> implements IPlmSupShopLog{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupShopLogServer.class);
    @Autowired
    private ViewObject<PlmSupShopLogEntity_HI> plmSupShopLogDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupShopLogEntity_HI_RO> plmSupShopLogEntity_HI_RO;
    public PlmSupShopLogServer() {
        super();
    }

    public List<PlmSupShopLogEntity_HI> findPlmSupShopLogInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        List<PlmSupShopLogEntity_HI> findListResult = plmSupShopLogDAO_HI.findList("from PlmSupShopLogEntity_HI", queryParamMap);
        return findListResult;
    }
    public Object savePlmSupShopLogInfo(JSONObject queryParamJSON) {
        PlmSupShopLogEntity_HI plmSupShopLogEntity_HI = JSON.parseObject(queryParamJSON.toString(), PlmSupShopLogEntity_HI.class);
        Object resultData = plmSupShopLogDAO_HI.save(plmSupShopLogEntity_HI);
        return resultData;
    }

    @Override
    public List<PlmSupShopLogEntity_HI_RO> findShopList(JSONObject queryParamJSON) {
        StringBuffer sb = new StringBuffer(PlmSupShopLogEntity_HI_RO.QUERY);
        Map<String,Object> map = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON,"s.product_code","productCode",sb,map,"=",false);
        SaafToolUtils.parperParam(queryParamJSON,"s.shop_code","shopCode",sb,map,"=",false);
        List<PlmSupShopLogEntity_HI_RO> list = plmSupShopLogEntity_HI_RO.findList(sb, map);
        return list;
    }
}
