package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiTaxEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.ttaImport.model.entities.TtaOiTaxEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaOiTax extends IBaseCommon<TtaOiTaxEntity_HI>{
    int saveImportTaxInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaOiTaxEntity_HI_RO> findTaxInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportTaxInfo(JSONObject queryParamJSON) throws Exception;
}
