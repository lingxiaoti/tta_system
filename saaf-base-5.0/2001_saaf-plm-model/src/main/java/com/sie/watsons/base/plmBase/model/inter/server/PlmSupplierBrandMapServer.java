package com.sie.watsons.base.plmBase.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.plmBase.model.entities.PlmSupplierBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmSupplierBrandMapEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.IPlmSupplierBrandMap;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("plmSupplierBrandMapServer")
public class PlmSupplierBrandMapServer extends BaseCommonServer<PlmSupplierBrandMapEntity_HI> implements IPlmSupplierBrandMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplierBrandMapServer.class);

    @Autowired
    private ViewObject<PlmSupplierBrandMapEntity_HI> plmSupplierBrandMapDAO_HI;
    @Autowired
    private BaseViewObject<PlmSupplierBrandMapEntity_HI_RO> plmSupplierBrandMapEntityRO;

    public PlmSupplierBrandMapServer() {
        super();
    }

    @Override
    public Pagination<PlmSupplierBrandMapEntity_HI_RO> findSupplierBrandMap(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmSupplierBrandMapEntity_HI_RO.query_sql);

        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils
                .parperHbmParam(PlmSupplierBrandMapEntity_HI_RO.class,
                        queryParamJSON, sql, paramsMap);
        sql.append(" order by pbm.LAST_UPDATE_DATE desc");
        Pagination<PlmSupplierBrandMapEntity_HI_RO> pagination = plmSupplierBrandMapEntityRO
                .findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
        return pagination;
    }

    @Override
    public Pagination<PlmSupplierBrandMapEntity_HI_RO> findDistinctSupplierBrandMap(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer(PlmSupplierBrandMapEntity_HI_RO.QUERY_BRAND_MAP);
        Map<String, Object> paramsMap = new HashMap<>();
        com.sie.saaf.common.util.SaafToolUtils.parperHbmParam(PlmSupplierBrandMapEntity_HI_RO.class, queryParamJSON, sql, paramsMap);
        JSONArray vendorCodeList = queryParamJSON.getJSONArray("vendorCodeList");
        // 从session中获取供应商编码
        if (vendorCodeList != null && !vendorCodeList.isEmpty()) {
            String vendorCode = "";
            for (Object object : vendorCodeList) {
                vendorCode += "'" + object.toString() + "',";
            }
            vendorCode = vendorCode.substring(0, vendorCode.length() - 1);
            String sqls = " AND pbm.SUPPLIER_CODE  IN (" + vendorCode + ")";
            sql.append(sqls);
        }
        sql.append(" ORDER BY pbm.brand_cn_uda_id, pbm.brand_cn_uda_value");
        return plmSupplierBrandMapEntityRO.findPagination(sql, SaafToolUtils.getSqlCountString(sql), paramsMap, pageIndex, pageRows);
    }
}
