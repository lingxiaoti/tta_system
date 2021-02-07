package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.rule.model.entities.RuleMappingBusinessEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleMappingBusinessEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleMappingBusiness;
import com.sie.saaf.rule.utils.Util;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@Component("ruleMappingBusinessServer")
public class RuleMappingBusinessServer extends BaseCommonServer<RuleMappingBusinessEntity_HI> implements IRuleMappingBusiness {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleMappingBusinessServer.class);

    /*@Autowired
    private RuleMappingBusinessDAO_HI ruleMappingBusinessDao;
    @Autowired
    private RuleMappingBusinessDAO_HI_RO ruleMappingBusinessRoDao;*/

    @Resource(name = "ruleMappingBusinessDAO_HI")
    private ViewObject ruleMappingBusinessDao;
    @Resource(name = "ruleMappingBusinessDAO_HI_RO")
    private BaseViewObject ruleMappingBusinessRoDao;


    @Override
    public RuleMappingBusinessEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException,
                                                                                                          IllegalAccessException {
        RuleMappingBusinessEntity_HI instance = Util.setEntity(new RuleMappingBusinessEntity_HI(), parameter, ruleMappingBusinessDao, parameter.get("ruleMappBusId"), userId);
        if (StringUtils.isBlank(parameter.getString("effectEndDate")))
            instance.setEffectEndDate(null);

        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        ruleMappingBusinessDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null)
            return false;
        RuleMappingBusinessEntity_HI instance = (RuleMappingBusinessEntity_HI)ruleMappingBusinessDao.getById(id);
        if (instance == null)
            return false;
        ruleMappingBusinessDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<RuleMappingBusinessEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        return Util.autoSearchPagination(ruleMappingBusinessRoDao, RuleMappingBusinessEntity_HI_RO.query, params, nowPage, pageSize, "order by rmb.RULE_MAPP_BUS_ID");
    }

    @Override
    public List<RuleMappingBusinessEntity_HI> findByProperty(String name, Object value) {
        return ruleMappingBusinessDao.findByProperty(name, value);
    }

    @Override
    public List<RuleMappingBusinessEntity_HI> findByProperty(Map<String, Object> map) {
        return ruleMappingBusinessDao.findByProperty(map);
    }

    @Override
    public RuleMappingBusinessEntity_HI findById(Integer id) {
        return (RuleMappingBusinessEntity_HI)ruleMappingBusinessDao.getById(id);
    }
}
