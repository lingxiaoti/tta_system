package com.sie.watsons.base.pos.qualityUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.qualityUpdate.model.entities.EquPosQualityUpdateLineEntity_HI;

import java.util.List;

public interface IEquPosQualityUpdateLine extends IBaseCommon<EquPosQualityUpdateLineEntity_HI> {
    /**
     * 分页查询质量审核详情行
     */
    JSONObject findQualityUpdateLinePagination(JSONObject jsonObject , Integer pageIndex, Integer pageRows);

    /**
     * 保存质量审核详情行
     */
    void saveQualityUpdateLineList(Integer updateHeadId,List<EquPosQualityUpdateLineEntity_HI> lineData,Integer userId) throws Exception;

    /**
     * 导入行
     * @param params
     * @param userId
     * @return
     */
    int saveImportForQualityUpdate(String params,Integer userId) throws Exception;

    /**
     * 删除行数据
     */
    String deleteQualityUpdateLine(JSONObject jsonObject, int userId);
}
