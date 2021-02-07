package com.sie.watsons.base.usergroupdeptbrand.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserDataTypeEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaUserDataType extends IBaseCommon<TtaUserDataTypeEntity_HI>{
    int saveImportInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception;
}
