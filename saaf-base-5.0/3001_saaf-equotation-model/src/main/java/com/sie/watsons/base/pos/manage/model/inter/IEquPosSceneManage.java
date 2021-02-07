package com.sie.watsons.base.pos.manage.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pos.manage.model.entities.readonly.EquPosSceneManageEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

public interface IEquPosSceneManage  {

    JSONObject saveDemo(String params);

    Pagination<EquPosSceneManageEntity_HI_RO> findEquPosSceneManageInfo(String params, Integer pageIndex, Integer pageRows);

    void saveSceneManage(JSONObject jsonObject, int userId);

    String deleteSceneManage(JSONObject jsonObject, int userId);

    String sumbitSceneManage(JSONObject jsonObject, int userId);

    String findSceneManageLine(JSONObject jsonObject, int userId);
}
