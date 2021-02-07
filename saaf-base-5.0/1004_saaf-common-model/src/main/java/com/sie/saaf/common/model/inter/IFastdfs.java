package com.sie.saaf.common.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhangJun
 * @createTime 2018-11-05 5:44 PM
 * @description
 */
public interface IFastdfs {
	ResultFileEntity fileUpload(InputStream inputStream, String fileName) throws IOException;

	ResultFileEntity fileUpload(InputStream inputStream, String fileName,String functionId,Long bussnessId, Integer userId) throws IOException;

	ResultFileEntity delete(String filePath);

	ResultFileEntity delete(String bucketName, String remoteFileName);

	void delete(Long fileId, UserSessionBean userSessionBean);

	InputStream getInputStream(String bucketName, String remoteFileName);

	void download(Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception;

	InputStream plmErrorResultToExcel(JSONArray array);

	/**
	 * 补货基础导出错误格式
	 * @param array
	 * @return
	 */
    InputStream plmSupErrorResultToExcel(JSONArray array);

    //BaseAttachmentEntity_HI download_file(Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
