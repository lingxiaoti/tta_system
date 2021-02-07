package com.sie.watsons.base.ob.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.ob.model.entities.readonly.PlmObHistoryListEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.ob.model.entities.PlmObHistoryListEntity_HI;

public interface IPlmObHistoryList extends IBaseCommon<PlmObHistoryListEntity_HI> {

	Pagination<PlmObHistoryListEntity_HI_RO> findPlmObHistoryListInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	PlmObHistoryListEntity_HI savePlmObHistoryListInfo(JSONObject queryParamJSON) throws Exception;

	JSONObject importAndTransferObHistoryList() throws Exception;
}
