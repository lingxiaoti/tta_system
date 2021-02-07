package com.sie.saaf.base.dict.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.entities.readonly.BaseLookupValuesEntity_HI_RO;
import java.util.List;

public interface IBaseLookupTypeValues {
    List<BaseLookupValuesEntity_HI_RO> findLookupValuesEntities(JSONObject queryParamJSON);

    List<BaseLookupValuesEntity_HI_RO> findByParent(JSONObject queryParamJSON);
}
