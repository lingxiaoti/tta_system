package com.sie.watsons.base.pos.spendUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.spendUpdate.model.entities.EquPosSpendUpdateLineEntity_HI;

import java.util.List;

public interface IEquPosSpendUpdateLine extends IBaseCommon<EquPosSpendUpdateLineEntity_HI> {
    /**
     * 分页查询概要文件审核详情行
     */
    JSONObject findSpendUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    /**
     * 保存概要文件审核详情行
     */
    void saveSpendUpdateLineList(Integer updateHeadId, List<EquPosSpendUpdateLineEntity_HI> lineData, Integer userId) throws Exception;

    /**
     * 导入行
     * @param params
     * @param userId
     * @return
     */
    int saveImportForSpendUpdate(String params, Integer userId) throws Exception;

    /**
     * 删除行数据
     */
    String deleteSpendUpdateLine(JSONObject jsonObject, int userId);
}
