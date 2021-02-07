package com.sie.saaf.common.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseManualSupplementEntity_HI;

import java.util.List;

public interface IBaseManualSupplement {

	List<BaseManualSupplementEntity_HI> findBaseManualSupplementInfo(JSONObject queryParamJSON);

	Object saveBaseManualSupplementInfo(JSONObject queryParamJSON);

    void deleteByMessageIndex(Integer messageIndex);

    BaseManualSupplementEntity_HI save(BaseManualSupplementEntity_HI instance);

    List<BaseManualSupplementEntity_HI> findAllManualSupplementList();
}
