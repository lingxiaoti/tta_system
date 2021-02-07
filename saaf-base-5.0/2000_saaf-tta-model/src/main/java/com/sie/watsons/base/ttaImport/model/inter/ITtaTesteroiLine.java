package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ttaImport.model.entities.TtaTesteroiLineEntity_HI;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTesteroiLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2019/10/23/023.
 */
public interface ITtaTesteroiLine extends IBaseCommon<TtaTesteroiLineEntity_HI> {
    int saveImportInfo(JSONObject jsonObject, MultipartFile file) throws Exception;

    Pagination<TtaTesteroiLineEntity_HI_RO> findInfo(JSONObject jsonObject, Integer pageIndex, Integer pageRows) throws Exception;

    JSONObject deleteImportInfo(JSONObject jsonObject) throws Exception;
}
