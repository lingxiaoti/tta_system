package com.sie.saaf.rule.model.inter.server;


import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressionFullViewEntity_HI_RO;
import com.sie.saaf.rule.model.entities.readonly.RuleExpressiondimViewEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleExpressiondimView;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("ruleExpressiondimViewServer")
public class RuleExpressiondimViewServer implements IRuleExpressiondimView {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExpressiondimViewServer.class);

    private BaseViewObject ruleExpressiondimViewDAO_HI_RO;

    @Resource(name = "ruleExpressiondimViewDAO_HI_RO")
    public void setRuleExpressiondimViewDAO_HI_RO(BaseViewObject ruleExpressiondimViewDAO_HI_RO) {
        this.ruleExpressiondimViewDAO_HI_RO = ruleExpressiondimViewDAO_HI_RO;
    }

    public RuleExpressiondimViewServer() {
        super();
    }

    public String findRuleExpressiondimViewInfo(JSONObject queryParamJSON) {
        Map<String, Object> queryParamMap = SToolUtils.fastJsonObj2Map(queryParamJSON);
        queryParamMap.put("currentDate", new Date());
        List<RuleExpressiondimViewEntity_HI_RO> findListResult = ruleExpressiondimViewDAO_HI_RO.findList(RuleExpressionFullViewEntity_HI_RO.QUERY_SQL, queryParamMap);
        //        String resultData = JSON.toJSONString(findListResult);
        JSONObject resultStr = SToolUtils.convertResultJSONObj("S", "", findListResult.size(), findListResult);
        return resultStr.toString();
    }

}
