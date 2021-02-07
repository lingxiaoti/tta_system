package com.sie.saaf.common.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.entities.BaseCommonMessageConfirmEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommonMessageConfirm;
import com.sie.saaf.common.util.SaafDateUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther: huqitao 2018/7/25
 */
@Component("baseCommonMessageConfirmServer")
public class BaseCommonMessageConfirmServer implements IBaseCommonMessageConfirm {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BaseCommonMessageConfirmServer.class);
    @Autowired
    private ViewObject<BaseCommonMessageConfirmEntity_HI> baseCommonMessageConfirmDAO_HI;

    @Override
    public List<BaseCommonMessageConfirmEntity_HI> findByProperty(Map<String, Object> queryMap) {
        return baseCommonMessageConfirmDAO_HI.findByProperty(queryMap);
    }

    @Override
    public List<BaseCommonMessageConfirmEntity_HI> findByProperty(String var1, Object var2) {
        return baseCommonMessageConfirmDAO_HI.findByProperty(var1, var2);
    }

    @Override
    public List<BaseCommonMessageConfirmEntity_HI> findList(JSONObject queryParamsJSON) {
        Map<String, Object> queryMap = new HashMap<>();
        StringBuffer querySQL = new StringBuffer(" from BaseCommonMessageConfirmEntity_HI where 1 = 1");
        if (StringUtils.isNotBlank(queryParamsJSON.getString("creationDate"))) {
            querySQL.append(" and creationDate <= :creationDate ");
            queryMap.put("creationDate", SaafDateUtils.string2UtilDate(queryParamsJSON.getString("creationDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotBlank(queryParamsJSON.getString("messageIndex"))) {
            querySQL.append(" and messageIndex = :messageIndex ");
            queryMap.put("messageIndex", queryParamsJSON.getInteger("messageIndex"));
        }
        return baseCommonMessageConfirmDAO_HI.findList(querySQL, queryMap);
    }

    @Override
    public void delete(BaseCommonMessageConfirmEntity_HI messageConfirmEntity) {
        if (messageConfirmEntity != null) {
            baseCommonMessageConfirmDAO_HI.delete(messageConfirmEntity);
        }
    }

    @Override
    public void deleteAll(List<BaseCommonMessageConfirmEntity_HI> messageConfirmList) {
        if (messageConfirmList != null && messageConfirmList.size() > 0) {
            baseCommonMessageConfirmDAO_HI.delete(messageConfirmList);
        }
    }
}
