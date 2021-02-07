package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiSummaryEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaBeoiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiSummaryEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaAboiSummary extends IBaseCommon<TtaAboiSummaryEntity_HI>{

    int saveImportAboiSummaryInfo(JSONObject jsonObject, MultipartFile file) throws Exception;

    Pagination<TtaAboiSummaryEntity_HI_RO> findAboiSummaryInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportAboiSummaryInfo(JSONObject jsonObject) throws Exception;
}
