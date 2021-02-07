package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.rule.model.entities.SaafWebserviceParamInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.SaafWebserviceParamInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.ISaafWebserviceParamInfoServer;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Component("saafWebserviceParamInfoServer")
public class SaafWebserviceParamInfoServer extends BaseCommonServer<SaafWebserviceParamInfoEntity_HI> implements ISaafWebserviceParamInfoServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SaafWebserviceParamInfoServer.class);
    private ViewObject saafWebserviceParamInfoDao;
    private BaseViewObject saafWebserviceParamInfoRoDao;

    @Resource(name = "saafWebserviceParamInfoDAO_HI")
    public void setSaafWebserviceParamInfoDao(ViewObject saafWebserviceParamInfoDao) {
        this.saafWebserviceParamInfoDao = saafWebserviceParamInfoDao;
    }

    @Resource(name = "saafWebserviceParamInfoDAO_HI_RO")
    public void setSaafWebserviceParamInfoDao(BaseViewObject saafWebserviceParamInfoRoDao) {
        this.saafWebserviceParamInfoRoDao = saafWebserviceParamInfoRoDao;
    }

    @Override
    public SaafWebserviceParamInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                  IllegalAccessException {
        SaafWebserviceParamInfoEntity_HI instance =
            Util.setEntity(new SaafWebserviceParamInfoEntity_HI(), parameter, saafWebserviceParamInfoDao, parameter.get("paramId"), userId);

        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        saafWebserviceParamInfoDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        SaafWebserviceParamInfoEntity_HI instance = (SaafWebserviceParamInfoEntity_HI)saafWebserviceParamInfoDao.getById(id);
        if (instance == null)
            return false;
        saafWebserviceParamInfoDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<SaafWebserviceParamInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(saafWebserviceParamInfoRoDao, SaafWebserviceParamInfoEntity_HI_RO.query, params, nowPage, pageSize);
    }

    @Override
    public List<SaafWebserviceParamInfoEntity_HI> findByProperty(String name, Object value) {
        return saafWebserviceParamInfoDao.findByProperty(name, value);
    }

    @Override
    public List<SaafWebserviceParamInfoEntity_HI> findByProperty(Map<String, Object> map) {
        return saafWebserviceParamInfoDao.findByProperty(map);
    }

    @Override
    public SaafWebserviceParamInfoEntity_HI findById(Integer id) {
        return (SaafWebserviceParamInfoEntity_HI)saafWebserviceParamInfoDao.getById(id);
    }


}

