package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.report.model.entities.TtaOiBillLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaOiBillLine extends IBaseCommon<TtaOiBillLineEntity_HI>{

    int saveImportOIInfo(JSONObject queryParamJSON, MultipartFile  file) throws Exception ;

    Pagination<TtaOiBillLineEntity_HI_RO> findOIBInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportOIInfo(JSONObject queryParamJSON) throws Exception;

}
