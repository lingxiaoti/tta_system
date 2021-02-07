package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.rule.constant.Constant;
import com.sie.saaf.rule.model.entities.SaafWebserviceInfoEntity_HI;
import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.ISaafWebserviceInfoServer;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Component("saafWebserviceInfoServer")
public class SaafWebserviceInfoServer extends BaseCommonServer<SaafWebserviceInfoEntity_HI> implements ISaafWebserviceInfoServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaafWebserviceInfoServer.class);
    private ViewObject saafWebserviceInfoDao;
    private BaseViewObject saafWebserviceInfoRoDao;
    private ViewObject webserviceParamDao;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Resource(name = "saafWebserviceParamInfoDAO_HI")
    public void setWebserviceParamDao(ViewObject webserviceParamDao) {
        this.webserviceParamDao = webserviceParamDao;
    }

    @Resource(name = "saafWebserviceInfoDAO_HI")
    public void setSaafWebserviceInfoDao(ViewObject saafWebserviceInfoDao) {
        this.saafWebserviceInfoDao = saafWebserviceInfoDao;
    }

    @Resource(name = "saafWebserviceInfoDAO_HI_RO")
    public void setSaafWebserviceInfoRoDao(BaseViewObject saafWebserviceInfoRoDao) {
        this.saafWebserviceInfoRoDao = saafWebserviceInfoRoDao;
    }

    @Override
    public SaafWebserviceInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                             IllegalAccessException {
        SaafWebserviceInfoEntity_HI instance = Util.setEntity(new SaafWebserviceInfoEntity_HI(), parameter, saafWebserviceInfoDao, parameter.get("webserviceId"), userId);
        if (StringUtils.isBlank(instance.getWebserviceCode()))
            instance.setWebserviceCode(generateCodeServer.generateCode(Constant.WEBSERVICE_CODE, 5));

        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        saafWebserviceInfoDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        SaafWebserviceInfoEntity_HI instance = (SaafWebserviceInfoEntity_HI)saafWebserviceInfoDao.getById(id);
        if (instance == null)
            return false;
        List<SaafWebserviceParamInfoEntity_HI> list = webserviceParamDao.findByProperty("webserviceCode", instance.getWebserviceCode());
        webserviceParamDao.delete(list);
        saafWebserviceInfoDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<SaafWebserviceInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(saafWebserviceInfoRoDao, SaafWebserviceInfoEntity_HI_RO.query, params, nowPage, pageSize);
    }

    @Override
    public List<SaafWebserviceInfoEntity_HI> findByProperty(String name, Object value) {
        return saafWebserviceInfoDao.findByProperty(name, value);
    }

    @Override
    public List<SaafWebserviceInfoEntity_HI> findByProperty(Map<String, Object> map) {
        return saafWebserviceInfoDao.findByProperty(map);
    }

    @Override
    public SaafWebserviceInfoEntity_HI findById(Integer id) {
        return (SaafWebserviceInfoEntity_HI)saafWebserviceInfoDao.getById(id);
    }


}

