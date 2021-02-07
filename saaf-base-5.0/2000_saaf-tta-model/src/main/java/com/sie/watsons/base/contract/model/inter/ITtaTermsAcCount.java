package com.sie.watsons.base.contract.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.contract.model.entities.readonly.TtaTermsAcCountEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.contract.model.entities.TtaTermsAcCountEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaTermsAcCount extends IBaseCommon<TtaTermsAcCountEntity_HI>{
    int saveImportACInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaTermsAcCountEntity_HI_RO> findACInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportACInfo(JSONObject queryParamJSON) throws Exception;
}
