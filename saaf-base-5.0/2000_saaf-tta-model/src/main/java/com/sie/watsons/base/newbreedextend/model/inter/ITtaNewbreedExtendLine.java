package com.sie.watsons.base.newbreedextend.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.newbreedextend.model.entities.readonly.TtaNewbreedExtendLineEntity_HI_RO;

import java.util.List;
import com.sie.watsons.base.newbreedextend.model.entities.TtaNewbreedExtendLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaNewbreedExtendLine extends IBaseCommon<TtaNewbreedExtendLineEntity_HI>{


    List<TtaNewbreedExtendLineEntity_HI_RO> findById(JSONObject queryParamJSON,Integer userId) throws Exception;

}
