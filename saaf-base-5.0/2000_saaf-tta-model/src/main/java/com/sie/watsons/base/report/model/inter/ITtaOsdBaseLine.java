package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaOsdBaseLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaOsdBaseLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaOsdBaseLine extends IBaseCommon<TtaOsdBaseLineEntity_HI>{

    int saveImportOsdBaseInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaOsdBaseLineEntity_HI_RO> findOsdBaseInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportOsdBaseInfo(JSONObject queryParamJSON) throws Exception;

    List<TtaOsdBaseLineEntity_HI_RO> findOsdBaseInfoOne(JSONObject queryParamJSON) throws Exception;
}
