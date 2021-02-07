package com.sie.watsons.base.pos.scoreUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.scoreUpdate.model.entities.EquPosScoreUpdateLineEntity_HI;

import java.util.List;

public interface IEquPosScoreUpdateLine extends IBaseCommon<EquPosScoreUpdateLineEntity_HI> {
    /**
     * 分页查询评分审核详情行
     */
    JSONObject findScoreUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    /**
     * 保存评分审核详情行
     */
    void saveScoreUpdateLineList(Integer updateHeadId, List<EquPosScoreUpdateLineEntity_HI> lineData, Integer userId) throws Exception;

    /**
     * 导入行
     * @param params
     * @param userId
     * @return
     */
    int saveImportForScoreUpdate(String params, Integer userId) throws Exception;

    /**
     * 删除行数据
     */
    String deleteScoreUpdateLine(JSONObject jsonObject, int userId);
}
