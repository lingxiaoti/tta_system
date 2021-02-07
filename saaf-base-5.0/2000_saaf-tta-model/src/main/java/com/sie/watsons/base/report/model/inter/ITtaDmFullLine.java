package com.sie.watsons.base.report.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.report.model.entities.TtaDmFullLineEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaDmFullLineEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaDmFullLine extends IBaseCommon<TtaDmFullLineEntity_HI>{


    Pagination<TtaDmFullLineEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    void deleteImportInfo(JSONObject queryParamJSON) throws Exception;
    
    /**
     * 功能描述： 查詢需要生成的单据
     */
    Pagination<TtaDmFullLineEntity_HI_RO> findDmCreateInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);


    /**
     * 功能描述：生成单据信息
     */
    public JSONObject saveOrUpdateDmOrder(JSONObject paramsJSON, int userId) throws Exception;

    void saveImportDMInfo(JSONObject jsonObject, MultipartFile file) throws Exception;
}
