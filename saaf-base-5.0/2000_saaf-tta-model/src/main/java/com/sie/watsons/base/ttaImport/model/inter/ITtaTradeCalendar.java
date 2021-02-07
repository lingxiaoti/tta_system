package com.sie.watsons.base.ttaImport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTradeCalendarEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.ttaImport.model.entities.TtaTradeCalendarEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaTradeCalendar extends IBaseCommon<TtaTradeCalendarEntity_HI>{
    int saveImportTCInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception ;

    Pagination<TtaTradeCalendarEntity_HI_RO> findTCInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception ;

    JSONObject deleteImportTCInfo(JSONObject queryParamJSON) throws Exception;
}
