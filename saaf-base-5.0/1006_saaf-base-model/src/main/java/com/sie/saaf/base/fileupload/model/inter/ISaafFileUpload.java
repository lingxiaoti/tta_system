package com.sie.saaf.base.fileupload.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.fileupload.model.entities.SaafFileUploadEntity_HI;
import com.sie.saaf.base.fileupload.model.entities.readonly.SaafFileUploadEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

public interface ISaafFileUpload extends IBaseCommon<SaafFileUploadEntity_HI> {


	Pagination<SaafFileUploadEntity_HI_RO> findSaafFileUploadList(JSONObject parameters, Integer pageIndex,
                                                                  Integer pageRows) throws Exception;
	JSONObject saveSaafFileUpload(JSONObject parameters) throws Exception;

	JSONObject saveSaafFileUpload(JSONObject parameters, Integer sourceId) throws Exception;

	JSONObject deleteSaafFileUpload(JSONObject parameters) throws Exception;


}
