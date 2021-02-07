package com.sie.saaf.common.model.inter;

import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.inter.server.AliOssServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhangJun
 * @createTime 2018-11-05 5:40 PM
 * @description
 */
public interface IAliOss {
	ResultFileEntity imageUploadAndCloseStream(InputStream inputStream, String fileName);

	ResultFileEntity imageUpload(InputStream inputStream, String fileName) throws IOException;

	ResultFileEntity fileUpload(InputStream inputStream, String fileName, AliOssServer.RemoteDirectory remoteDirectory) throws IOException;

	ResultFileEntity imageUpload(File file);

	void delete(String filePath);

	void download(Long fileId, HttpServletRequest request, HttpServletResponse response) throws Exception;

	void delete(Long fileId, UserSessionBean userSessionBean);

	InputStream getObjectInputStream(String ossfile);

}
