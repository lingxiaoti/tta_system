package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLinePogEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineRegionEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineWareEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineEntity_HI;

import java.io.IOException;

public interface IPlmSupplementLine extends IBaseCommon<PlmSupplementLineEntity_HI> {

	Pagination<PlmSupplementLineEntity_HI_RO> findPlmSupplementLineInfo(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex);
    Pagination<PlmSupplementLineRegionEntity_HI_RO> findPlmSupplementLineRegion(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex);
    Pagination<PlmSupplementLineWareEntity_HI_RO> findPlmSupplementLineWare(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex);
    Pagination<PlmSupplementLinePogEntity_HI_RO> findPlmSupplementLinePog(JSONObject queryParamJSON, Integer pageRows, Integer pageIndex);
    void removePlmLine(JSONObject queryParamJSON) throws Exception;

	Object findPlmSupplementLineDetail(JSONObject queryParamJSON);

	Object findMeter(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	Object importSupLine(JSONObject queryParamJSON) throws Exception;
	JSONArray saveImportSupLine2(JSONObject queryParamJSON) throws Exception;

	Object getLines(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	JSONObject getUploadPath(JSONObject queryParamJSON);

	JSONObject importSupLine3(JSONObject queryParamJSON) throws IOException, Exception;

	void saveImportSupLineMulTh(JSONObject object);
}
