package com.sie.watsons.base.exclusive.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleItemEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface ITtaSoleItem extends IBaseCommon<TtaSoleItemEntity_HI>{

    List<TtaSoleItemEntity_HI> saveOrUpdate(JSONObject jsonObject, int userId) throws Exception;

    TtaSoleItemEntity_HI deleteSoleItemById(Integer id) throws Exception;

    List<TtaSoleItemEntity_HI> saveBatchSoleItem(JSONObject paramJson,Integer userId) throws Exception;

    Pagination<TtaItemEntity_HI_RO> findItem(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    Pagination<TtaSoleItemEntity_HI_RO> findSoleItem(JSONObject jsonObject, Integer pageIndex, Integer pageRows);

    void saveOrUpdteBySingle(JSONObject jsonObject, int userId) throws Exception;

    int findSoleItemSingal(JSONObject paramsJson, Integer userId);

    void saveAllItemData(JSONObject jsonObject, int userId) throws Exception;

    Pagination<TtaSoleItemEntity_HI_RO> findExclusiveItemReport(JSONObject parameters, Integer pageIndex, Integer pageRows);

}
