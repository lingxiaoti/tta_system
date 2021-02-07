package com.sie.watsons.base.pos.contractUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.contractUpdate.model.entities.EquPosContractUpdateLineEntity_HI;

import java.util.List;

public interface IEquPosContractUpdateLine extends IBaseCommon<EquPosContractUpdateLineEntity_HI> {
    /**
     * 分页查询质量审核详情行
     */
    JSONObject findContractUpdateLinePagination(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    /**
     * 保存质量审核详情行
     */
    void saveContractUpdateLineList(Integer updateHeadId, List<EquPosContractUpdateLineEntity_HI> lineData, Integer userId) throws Exception;

    /**
     * 导入行
     * @param params
     * @param userId
     * @return
     */
    int saveImportForContractUpdate(String params, Integer userId) throws Exception;

    /**
     * 删除行数据
     */
    String deleteContractUpdateLine(JSONObject jsonObject, int userId);
}
