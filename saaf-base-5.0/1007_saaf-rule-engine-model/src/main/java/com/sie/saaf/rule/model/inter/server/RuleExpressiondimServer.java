package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.rule.model.entities.RuleExpressiondimEntity_HI;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressiondimEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpressiondim;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("ruleExpressiondimServer")
public class RuleExpressiondimServer extends BaseCommonServer<RuleExpressiondimEntity_HI> implements IRuleExpressiondim {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressiondimServer.class);

    private ViewObject ruleExpressiondimDao;
    private BaseViewObject ruleExpressiondimRoDao;

    @Autowired
    private BaseViewObject<RuleExpressiondimEntity_HI_RO> ruleExpressiondimDao_HI_RO;

    @Resource(name = "ruleExpressiondimDAO_HI")
    public void setRuleExpressiondimDao(ViewObject ruleExpressiondimDao) {
        this.ruleExpressiondimDao = ruleExpressiondimDao;
    }

    @Resource(name = "ruleExpressiondimDAO_HI_RO")
    public void setRuleExpressiondimRoDao(BaseViewObject ruleExpressiondimRoDao) {
        this.ruleExpressiondimRoDao = ruleExpressiondimRoDao;
    }

    @Override
    public RuleExpressiondimEntity_HI saveOrUpdate(JSONObject parameter, int userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        RuleExpressiondimEntity_HI instance = Util.setEntity(new RuleExpressiondimEntity_HI(), parameter,ruleExpressiondimDao,parameter.get("ruleExpDimId"), userId);
        if (StringUtils.isBlank(parameter.getString("effectEndDate")))
            instance.setEffectEndDate(null);
        if(instance.getEffectDate()==null)
            instance.setEffectDate(new Date());
        instance.setOperatorUserId(parameter.getIntValue("operatorUserId"));
        ruleExpressiondimDao.saveOrUpdate(instance);
        return instance;
    }

    @Override
    public boolean delete(Integer id){
        if (id==null)
            return false;
        RuleExpressiondimEntity_HI instance= (RuleExpressiondimEntity_HI) ruleExpressiondimDao.getById(id);
        if (instance==null)
            return false;
        ruleExpressiondimDao.delete(instance);
        return true;
    }

    @Override
    public Pagination<RuleExpressiondimEntity_HI_RO> find(JSONObject params, int nowPage, int pageSize) {
        String ruleExpCode = params.getString("var_equal_ruleExpCode");
        String ruleBussinessLineCode = params.getString("var_equal_ruleBusinessLineCode");
        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("ruleExpCode",ruleExpCode);
        conditionMap.put("ruleBussinessLineCode",ruleBussinessLineCode);
        StringBuffer sql = new StringBuffer(RuleExpressiondimEntity_HI_RO.QUERY_RULE_EXP_DIM);
        StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(1)");
        return ruleExpressiondimDao_HI_RO.findPagination(sql,countSql,conditionMap,nowPage,pageSize);
//        return Util.autoSearchPagination(ruleExpressiondimRoDao, RuleExpressiondimEntity_HI_RO.query, params, nowPage, pageSize,"order by re.RULE_EXP_DIM_ID");
    }

    @Override
    public Pagination<RuleExpressiondimEntity_HI_RO> findBySql(JSONObject params, String sql , int nowPage, int pageSize) {
        return Util.autoSearchPagination(ruleExpressiondimRoDao,sql, params, nowPage, pageSize,"order by re.RULE_EXP_DIM_ID");
    }




    @Override
    public List<RuleExpressiondimEntity_HI> findByProperty(String name, Object value){
        return ruleExpressiondimDao.findByProperty(name,value);
    }

    @Override
    public List<RuleExpressiondimEntity_HI> findByProperty(Map<String, Object> map){
        return ruleExpressiondimDao.findByProperty(map);
    }

    @Override
    public RuleExpressiondimEntity_HI findById(Integer id){
        return (RuleExpressiondimEntity_HI) ruleExpressiondimDao.getById(id);
    }

}
