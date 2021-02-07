package com.sie.watsons.base.newbreed.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetLineEntity_HI;

import java.util.List;

public interface ITtaNewbreedSetLine extends IBaseCommon<TtaNewbreedSetLineEntity_HI>{

    /**
     * 新增&修改新品种行信息
     *
     * @param paramsJSON 对象属性的JSON格式
     * @param userId     当前用户ID
     * @return 返回实体行
     * @throws Exception 抛出异常
     */

     List<TtaNewbreedSetLineEntity_HI> saveOrUpdateTtaNewbreedSetLineInfo(JSONArray paramsJSON, Integer userId, Integer newbreedSetId, Integer deleteFlag) throws Exception;
}
