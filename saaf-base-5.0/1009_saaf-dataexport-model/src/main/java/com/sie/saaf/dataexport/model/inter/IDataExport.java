package com.sie.saaf.dataexport.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.dataexport.bean.XlsExportBean;

/**
 * @author huangtao
 * @creationDate 2018年5月22日 16:56:53
 */
public interface IDataExport {


    void startExport(XlsExportBean xlsExportBean) throws Exception;

    JSONObject getExportResult(String exportId);
}
