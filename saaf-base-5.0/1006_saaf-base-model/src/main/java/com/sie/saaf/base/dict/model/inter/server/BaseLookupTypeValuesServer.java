package com.sie.saaf.base.dict.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import com.sie.saaf.base.dict.model.inter.IBaseLookupTypeValues;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("baseLookupTypeValuesServer")
public class BaseLookupTypeValuesServer implements IBaseLookupTypeValues {
    @Autowired
    private BaseViewObject<BaseLookupValuesEntity_HI_RO> baseLookupValuesDAO_HI_RO;

    public List<BaseLookupValuesEntity_HI_RO> findLookupValuesEntities(JSONObject queryParamJSON){
        StringBuffer lookupTypeValueSQL = new StringBuffer(BaseLookupValuesEntity_HI_RO.QUERY_LOOK_DIC_SQL);
        Map<String, Object> paramMap = SToolUtils.fastJsonObj2Map(queryParamJSON);


        if (queryParamJSON!=null && StringUtils.isNotBlank(queryParamJSON.getString("description"))){
            lookupTypeValueSQL.append(" AND B.description = :description ");
        }
        List<BaseLookupValuesEntity_HI_RO> baseLookupValuesEntities = baseLookupValuesDAO_HI_RO.findList(lookupTypeValueSQL, paramMap);
        return baseLookupValuesEntities;
    }

    @Override
    public List<BaseLookupValuesEntity_HI_RO> findByParent(JSONObject queryParamJSON) {
        StringBuffer sb = new StringBuffer(BaseLookupValuesEntity_HI_RO.QUERY_PARENT_DIC_SQL);
        Map<String, Object> paramMap = new HashMap<>();
        SaafToolUtils.parperParam(queryParamJSON,"t1.lookup_type_id", "lookupTypeId", sb, paramMap, "=");
        List<BaseLookupValuesEntity_HI_RO> list = baseLookupValuesDAO_HI_RO.findList(sb, paramMap);
        return list;
    }
}
