package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.rule.model.entities.PageModelGroupDetailEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.PageModelGroupDetailEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IPageModelGroupDetailServer;
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
 * Created by Administrator on Fri Jul 07 17:28:51 CST 2017
 */
@Component("pageModelGroupDetailServer")
public class PageModelGroupDetailServer extends BaseCommonServer<PageModelGroupDetailEntity_HI> implements IPageModelGroupDetailServer {

    private ViewObject pageModelGroupDetailDao;
    private BaseViewObject pageModelGroupDetailRoDao;

    @Resource(name = "pageModelGroupDetailDAO_HI")
    public void setPageModelGroupDetailDao(ViewObject pageModelGroupDetailDao) {
        this.pageModelGroupDetailDao = pageModelGroupDetailDao;
    }

    @Resource(name = "pageModelGroupDetailDAO_HI_RO")
    public void setPageModelGroupDetailRoDao(BaseViewObject pageModelGroupDetailRoDao) {
        this.pageModelGroupDetailRoDao = pageModelGroupDetailRoDao;
    }

    @Override
    public PageModelGroupDetailEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                               IllegalAccessException {
        PageModelGroupDetailEntity_HI instance = Util.setEntity(new PageModelGroupDetailEntity_HI(), parameter, pageModelGroupDetailDao, parameter.get("groupDetId"), userId);
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        pageModelGroupDetailDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        PageModelGroupDetailEntity_HI instance = (PageModelGroupDetailEntity_HI)pageModelGroupDetailDao.getById(id);
        if (instance == null)
            return false;
        pageModelGroupDetailDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<PageModelGroupDetailEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(pageModelGroupDetailRoDao, PageModelGroupDetailEntity_HI_RO.query, params, nowPage, pageSize);
    }

    @Override
    public List<PageModelGroupDetailEntity_HI> findByProperty(String name, Object value) {
        return pageModelGroupDetailDao.findByProperty(name, value);
    }

    @Override
    public List<PageModelGroupDetailEntity_HI> findByProperty(Map<String, Object> map) {
        return pageModelGroupDetailDao.findByProperty(map);
    }

    @Override
    public PageModelGroupDetailEntity_HI findById(Integer id) {
        return (PageModelGroupDetailEntity_HI)pageModelGroupDetailDao.getById(id);
    }

}
