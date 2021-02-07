package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.rule.constant.Constant;
import com.sie.saaf.rule.model.entities.PageModelInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IPageModelInfoServer;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on Thu Jul 06 17:24:57 CST 2017
 */
@Component("pageModelInfoServer")
public class PageModelInfoServer extends BaseCommonServer<PageModelInfoEntity_HI> implements IPageModelInfoServer {

    private ViewObject pageModelInfoDao;
    private BaseViewObject pageModelInfoRoDao;

    @Autowired
    private GenerateCodeServer generateCodeServer;

    @Resource(name = "pageModelInfoDAO_HI")
    public void setPageModelInfoDao(ViewObject pageModelInfoDao) {
        this.pageModelInfoDao = pageModelInfoDao;
    }

    @Resource(name = "pageModelInfoDAO_HI_RO")
    public void setPageModelInfoRoDao(BaseViewObject pageModelInfoRoDao) {
        this.pageModelInfoRoDao = pageModelInfoRoDao;
    }

    @Override
    public PageModelInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                        IllegalAccessException {

        PageModelInfoEntity_HI instance = Util.setEntity(new PageModelInfoEntity_HI(), parameter, pageModelInfoDao, parameter.get("modelId"), userId);
        if (StringUtils.isBlank(instance.getModelCode())) {
            instance.setModelCode(generateCodeServer.generateCode(Constant.MODELCODE_SEQ, Constant.DATEFORMAT_DATE_SEQ, 4));
        }
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        pageModelInfoDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        PageModelInfoEntity_HI instance = (PageModelInfoEntity_HI)pageModelInfoDao.getById(id);
        if (instance == null)
            return false;
        pageModelInfoDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<PageModelInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(pageModelInfoRoDao, PageModelInfoEntity_HI_RO.query, params, nowPage, pageSize);
    }

    @Override
    public List<PageModelInfoEntity_HI> findByProperty(String name, Object value) {
        return pageModelInfoDao.findByProperty(name, value);
    }

    @Override
    public List<PageModelInfoEntity_HI> findByProperty(Map<String, Object> map) {
        return pageModelInfoDao.findByProperty(map);
    }

    @Override
    public PageModelInfoEntity_HI findById(Integer id) {
        return (PageModelInfoEntity_HI)pageModelInfoDao.getById(id);
    }

}
