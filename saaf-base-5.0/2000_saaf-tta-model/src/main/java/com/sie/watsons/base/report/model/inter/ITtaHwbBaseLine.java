package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbBaseLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaHwbBaseLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaHwbBaseLine extends IBaseCommon<TtaHwbBaseLineEntity_HI>{

    int saveImportHwbBaseInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaHwbBaseLineEntity_HI_RO> findHwbBaseInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportHwbBaseInfo(JSONObject queryParamJSON) throws Exception;

    List<TtaHwbBaseLineEntity_HI_RO> findHwbBaseInfoOne(JSONObject queryParamJSON) throws Exception;
}
