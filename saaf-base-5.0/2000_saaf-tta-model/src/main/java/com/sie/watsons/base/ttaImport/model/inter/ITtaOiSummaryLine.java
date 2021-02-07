package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ttaImport.model.entities.TtaOiSummaryLineEntity_HI;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaOiSummaryLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaOiSummaryLine extends IBaseCommon<TtaOiSummaryLineEntity_HI> {
    int saveImportOISInfo(JSONObject jsonObject, MultipartFile file, UserSessionBean sessionBean) throws Exception;

    Pagination<TtaOiSummaryLineEntity_HI_RO> findOISInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportOISInfo(JSONObject jsonObject) throws Exception;
}
