package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.rule.model.entities.PageModelGroupInfoEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelGroupInfoEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IPageModelGroupInfoServer;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on Fri Jul 07 17:27:23 CST 2017
 */
@Component("pageModelGroupInfoServer")
public class PageModelGroupInfoServer extends BaseCommonServer<PageModelGroupInfoEntity_HI> implements IPageModelGroupInfoServer {

    private ViewObject pageModelGroupInfoDao;
    private BaseViewObject pageModelGroupInfoRoDao;

    @Resource(name = "pageModelGroupInfoDAO_HI")
    public void setPageModelGroupInfoDao(ViewObject pageModelGroupInfoDao) {
        this.pageModelGroupInfoDao = pageModelGroupInfoDao;
    }

    @Resource(name = "pageModelGroupInfoDAO_HI_RO")
    public void setPageModelGroupInfoRoDao(BaseViewObject pageModelGroupInfoRoDao) {
        this.pageModelGroupInfoRoDao = pageModelGroupInfoRoDao;
    }

    @Override
    public PageModelGroupInfoEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                             IllegalAccessException {
        PageModelGroupInfoEntity_HI instance = Util.setEntity(new PageModelGroupInfoEntity_HI(), parameter, pageModelGroupInfoDao, parameter.get("groupId"), userId);
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        pageModelGroupInfoDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        PageModelGroupInfoEntity_HI instance = (PageModelGroupInfoEntity_HI)pageModelGroupInfoDao.getById(id);
        if (instance == null)
            return false;
        pageModelGroupInfoDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<PageModelGroupInfoEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(pageModelGroupInfoRoDao, PageModelGroupInfoEntity_HI_RO.query, params, nowPage, pageSize);
    }

    @Override
    public List<PageModelGroupInfoEntity_HI> findByProperty(String name, Object value) {
        return pageModelGroupInfoDao.findByProperty(name, value);
    }

    @Override
    public List<PageModelGroupInfoEntity_HI> findByProperty(Map<String, Object> map) {
        return pageModelGroupInfoDao.findByProperty(map);
    }

    @Override
    public PageModelGroupInfoEntity_HI findById(Integer id) {
        return (PageModelGroupInfoEntity_HI)pageModelGroupInfoDao.getById(id);
    }

}
