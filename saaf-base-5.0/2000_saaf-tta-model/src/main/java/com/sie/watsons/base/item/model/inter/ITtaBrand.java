package com.sie.watsons.base.item.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.item.model.entities.readonly.TtaBrandEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.item.model.entities.TtaBrandEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface ITtaBrand extends IBaseCommon<TtaBrandEntity_HI>{
    Pagination<TtaBrandEntity_HI_RO> find(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

}
