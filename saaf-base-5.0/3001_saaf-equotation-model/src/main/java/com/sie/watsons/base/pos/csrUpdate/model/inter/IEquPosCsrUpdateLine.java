package com.sie.watsons.base.pos.csrUpdate.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pos.csrUpdate.model.entities.EquPosCsrUpdateLineEntity_HI;

import java.util.List;

public interface IEquPosCsrUpdateLine extends IBaseCommon<EquPosCsrUpdateLineEntity_HI>{
    /**
     * 分页查询质量审核详情行
     */
    JSONObject findCsrUpdateLinePagination(JSONObject jsonObject , Integer pageIndex, Integer pageRows);

    /**
     * 保存质量审核详情行
     */
    void saveCsrUpdateLineList(Integer updateHeadId, List<EquPosCsrUpdateLineEntity_HI> lineData,Integer userId) throws Exception;

    /**
     * 导入行
     * @param params
     * @return
     */
    int saveImportForCsrUpdate(String params,Integer userId) throws Exception;

    /**
     * 删除行数据
     */
    String deleteCsrUpdateLine(JSONObject jsonObject, int userId);
}
