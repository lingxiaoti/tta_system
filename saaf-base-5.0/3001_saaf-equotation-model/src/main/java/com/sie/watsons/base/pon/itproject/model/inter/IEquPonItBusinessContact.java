package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItBusinessContactEntity_HI;
import com.sie.watsons.base.pon.itproject.model.entities.readonly.EquPonItBusinessContactEntity_HI_RO;

import java.util.List;

public interface IEquPonItBusinessContact extends IBaseCommon<EquPonItBusinessContactEntity_HI>{

    /**
     * 查询业务项目联系人
     */
    List<EquPonItBusinessContactEntity_HI_RO> findItBusinessContact(JSONObject jsonObject);

    //IT报价管理-业务项目联系人查询，分页查询
    JSONObject findItBusinessContactInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-业务项目联系人删除
    void deleteItBusinessContact(JSONObject params) throws Exception;
}
