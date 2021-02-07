package com.sie.watsons.base.pon.itquotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoContentItEntity_HI_RO;

import java.util.List;

public interface IEquPonQuoContentIt {

    /**
     * 查询报价内容
     */
    List<EquPonQuoContentItEntity_HI_RO> findQuoContentIt(JSONObject jsonObject);
}
