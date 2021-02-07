package com.sie.saaf.dataexport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.dataexport.bean.XlsExportBean;
import com.sie.saaf.dataexport.model.entities.BaseExportRecordEntity_HI;

import java.util.List;

public interface IBaseExportRecord {

	List<BaseExportRecordEntity_HI> findBaseExportRecordInfo(JSONObject queryParamJSON);

	BaseExportRecordEntity_HI saveBaseExportRecordInfo(XlsExportBean xlsExportBean);

	BaseExportRecordEntity_HI updateRecordStatus(XlsExportBean xlsExportBean);
}
