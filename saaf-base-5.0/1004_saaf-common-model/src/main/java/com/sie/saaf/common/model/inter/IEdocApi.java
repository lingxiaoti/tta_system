package com.sie.saaf.common.model.inter;

import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;

import java.io.IOException;
import java.io.InputStream;

public interface IEdocApi {
    String getAccessToken() throws IOException;

    String  getUserToken(String employeeNumber) throws IOException;

    ResultFileEntity upload(InputStream inputStream, String fileName, UserSessionBean userSessionBean) throws IOException;

    void delete(Long fileId, UserSessionBean userSessionBean) throws IOException;

    InputStream getFileInputStream(String fileId, UserSessionBean userSessionBean) throws IOException;

    String UserLogin(String employeeNumber) throws IOException;
}
