package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaBeoiBillLineEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttaImport.model.entities.TtaBeoiBillLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaBeoiBillLine extends IBaseCommon<TtaBeoiBillLineEntity_HI>{

    int saveImportBEOIInfo(JSONObject jsonObject, MultipartFile file) throws Exception;

    Pagination<TtaBeoiBillLineEntity_HI_RO> findBEOIInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportBEOIInfo(JSONObject jsonObject) throws Exception;
}
