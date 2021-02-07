package com.sie.saaf.dataexport.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.dataexport.bean.XlsExportBean;
import com.sie.saaf.dataexport.model.entities.BaseExportRecordEntity_HI;
import com.sie.saaf.dataexport.model.inter.IBaseExportRecord;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.sie.saaf.sso.model.inter.server.SsoServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("baseExportRecordServer")
public class BaseExportRecordServer implements IBaseExportRecord {
    @Autowired
    private ViewObject<BaseExportRecordEntity_HI> baseExportRecordDAO_HI;

    @Autowired
    private ISsoServer ssoServer;

    @Override
    public List<BaseExportRecordEntity_HI> findBaseExportRecordInfo(JSONObject queryParamJSON) {
        return null;
    }

    @Override
    public BaseExportRecordEntity_HI saveBaseExportRecordInfo(XlsExportBean xlsExportBean) {
        UserSessionBean userSessionBean = ssoServer.getUserSession(xlsExportBean.getToken());
        BaseExportRecordEntity_HI instance = new BaseExportRecordEntity_HI();
        if (userSessionBean != null){
            instance.setOperatorUserId(userSessionBean.getUserId());
            instance.setUserId(userSessionBean.getUserId());
        }
        instance.setFunctionName(xlsExportBean.getEmailSubject());
        instance.setExportId(xlsExportBean.getExportId());
        instance.setRequestParams(xlsExportBean.getRequestParam());
        instance.setRequestUrl(xlsExportBean.getRequestUrl());
        instance.setStatus("1");
        baseExportRecordDAO_HI.save(instance);
        return instance;
    }

    @Override
    public BaseExportRecordEntity_HI updateRecordStatus(XlsExportBean xlsExportBean) {
        if (xlsExportBean==null || StringUtils.isBlank(xlsExportBean.getExportId()))
            return null;
        UserSessionBean userSessionBean = ssoServer.getUserSession(xlsExportBean.getToken());
        List<BaseExportRecordEntity_HI> list=baseExportRecordDAO_HI.findByProperty("exportId",xlsExportBean.getExportId());
        if (list.size()==0)
            return null;
        if (userSessionBean != null)
            list.get(0).setOperatorUserId(userSessionBean.getUserId());
        list.get(0).setStatus("2");
        list.get(0).setCompleteDate(new Date());
        list.get(0).setFileUrl(xlsExportBean.getFileUrl());
        baseExportRecordDAO_HI.update(list.get(0));
        return list.get(0);
    }


}
