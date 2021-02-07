package com.sie.watsons.base.item.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.orgStructure.model.entities.readonly.BaseDepartmentEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.readonly.TtaGroupEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.item.model.entities.TtaGroupEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaGroup extends IBaseCommon<TtaGroupEntity_HI>{
    Pagination<BaseDepartmentEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    List<BaseDepartmentEntity_HI_RO> findGroup(JSONObject jsonObject);
}
