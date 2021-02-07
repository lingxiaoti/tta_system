package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.report.model.entities.readonly.TtaPogSpaceLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaPogSpaceLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaPogSpaceLine extends IBaseCommon<TtaPogSpaceLineEntity_HI>{

    int saveImportPogInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaPogSpaceLineEntity_HI_RO> findPogInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportPogInfo(JSONObject queryParamJSON) throws Exception;

    List<TtaPogSpaceLineEntity_HI_RO> findPogInfoOne(JSONObject queryParamJSON) throws Exception;

}
