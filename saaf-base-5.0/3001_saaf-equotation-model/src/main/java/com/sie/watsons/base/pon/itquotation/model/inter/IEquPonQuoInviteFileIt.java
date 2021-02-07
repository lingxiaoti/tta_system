package com.sie.watsons.base.pon.itquotation.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.pon.itquotation.model.entities.readonly.EquPonQuoInviteFileItEntity_HI_RO;

import java.util.List;

public interface IEquPonQuoInviteFileIt {
    // 查询邀请文件
    List<EquPonQuoInviteFileItEntity_HI_RO> findQuoInviteFileIt(JSONObject jsonObject);
}
