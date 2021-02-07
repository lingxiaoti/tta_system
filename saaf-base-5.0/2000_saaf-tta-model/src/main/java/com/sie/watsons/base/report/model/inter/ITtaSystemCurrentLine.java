package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaSystemCurrentLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaSystemCurrentLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaSystemCurrentLine extends IBaseCommon<TtaSystemCurrentLineEntity_HI>{

    int saveImportInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaSystemCurrentLineEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception;

}
