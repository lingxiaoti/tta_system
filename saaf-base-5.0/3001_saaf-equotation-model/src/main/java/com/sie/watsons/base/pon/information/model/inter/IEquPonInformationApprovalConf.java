package com.sie.watsons.base.pon.information.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.information.model.entities.EquPonInformationApprovalConfEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonInformationApprovalConf extends IBaseCommon<EquPonInformationApprovalConfEntity_HI>{
    //资质审查-查询供应商地址信息
    JSONObject findInformationApprovalConf(JSONObject queryParamJSON, Integer pageIndex,
                                           Integer pageRows);

    //资质审查-供应商地址信息保存
    EquPonInformationApprovalConfEntity_HI saveInformationApprovalConf(JSONObject params) throws Exception;
}
