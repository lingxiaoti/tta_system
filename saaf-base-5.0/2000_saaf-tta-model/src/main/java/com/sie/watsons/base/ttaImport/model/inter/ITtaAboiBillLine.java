package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiBillLineEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2019/10/22/022.
 */
public interface ITtaAboiBillLine extends IBaseCommon<TtaAboiBillLineEntity_HI> {
    int saveImportABOIInfo(JSONObject jsonObject, MultipartFile file) throws Exception;

    Pagination<TtaAboiBillLineEntity_HI_RO> findABOIInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportABOIInfo(JSONObject jsonObject) throws Exception;
}
