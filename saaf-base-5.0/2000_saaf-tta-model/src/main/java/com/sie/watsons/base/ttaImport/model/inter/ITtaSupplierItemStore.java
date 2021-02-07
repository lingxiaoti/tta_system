package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSupplierItemStoreEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTesteroiLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttaImport.model.entities.TtaSupplierItemStoreEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaSupplierItemStore extends IBaseCommon<TtaSupplierItemStoreEntity_HI>{
    int saveImportInfo(JSONObject jsonObject, MultipartFile file) throws Exception;

    Pagination<TtaSupplierItemStoreEntity_HI_RO> findInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportInfo(JSONObject jsonObject) throws Exception;
}
