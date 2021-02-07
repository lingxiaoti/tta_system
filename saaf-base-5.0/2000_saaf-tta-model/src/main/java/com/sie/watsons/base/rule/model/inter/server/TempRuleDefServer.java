package com.sie.watsons.base.rule.model.inter.server;

import java.io.InputStream;
import java.util.*;
import com.sie.watsons.base.params.model.dao.readonly.TempParamDefDAO_HI_RO;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.params.model.inter.ITempParamDef;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import com.sie.watsons.base.rule.services.TempRuleDefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.rule.model.dao.readonly.TempRuleDefDAO_HI_RO;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("tempRuleDefServer")
public class TempRuleDefServer extends BaseCommonServer<TempRuleDefEntity_HI> implements ITempRuleDef {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempRuleDefServer.class);

    @Autowired
    private ViewObject<TempRuleDefEntity_HI> tempRuleDefDAO_HI;

    @Autowired
    private TempRuleDefDAO_HI_RO tempRuleDefDAO_HI_RO;

    @Autowired
    private TempParamDefDAO_HI_RO tempParamDefDAO_HI_RO;

    @Autowired
    private ITempParamDef tempParamDefServer;

    @Autowired
    private IRuleFileTemplate ruleFileTemplate;

    @Autowired
    private TempRuleDefService tempRuleDefService;

    public TempRuleDefServer() {
        super();
    }

    @Override
    public Pagination<TempRuleDefEntity_HI_RO> findRulePagination(JSONObject queryParamJSON, Integer pageIndex,
                                                                  Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TempRuleDefEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(queryParamJSON, "t.is_enable", "isEnable", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "t.rule_name", "ruleName", sql, paramsMap, "like");
        SaafToolUtils.parperParam(queryParamJSON, "t.sole_resouce_type", "soleResouceType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "t.sole_product_type", "soleProductType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "t.is_include_ec", "isIncludeEc", sql, paramsMap, "=");
        SaafToolUtils.parperParam(queryParamJSON, "t.is_include_special", "isIncludeSpecial", sql, paramsMap, "=");
        SaafToolUtils.changeQuerySort(queryParamJSON, sql, "  t.sole_resouce_type, t.rule_name asc ", false);
        return tempRuleDefDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
    }


    @Override
    public TempRuleDefEntity_HI_RO findRuleById(Integer ruleId) {
        StringBuffer sql = new StringBuffer();
        sql.append(TempRuleDefEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(new JSONObject().fluentPut("ruleId", ruleId), "t.rul_id", "ruleId", sql, paramsMap, "=");
        List<TempRuleDefEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql, paramsMap);
        TempRuleDefEntity_HI_RO ruleDefEntity = null;
        if (list != null && !list.isEmpty()) {
            ruleDefEntity = list.get(0);
        }
        return ruleDefEntity;
    }

    @Override
    public Pagination<TempParamDefEntity_HI_RO> findParams(JSONObject jsonObject, Integer pageIndex, Integer pageRows) {
        StringBuffer sql = new StringBuffer();
        sql.append(TempParamDefEntity_HI_RO.QUERY_PARAM_BY_RULE);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonObject,"t.param_key","paramKey",sql,paramsMap,"fulllike");
        SaafToolUtils.parperParam(jsonObject,"t.param_content","paramContent",sql,paramsMap,"fulllike");
        SaafToolUtils.parperParam(jsonObject,"t.remark","remark",sql,paramsMap,"fulllike");
        SaafToolUtils.parperParam(new JSONObject().fluentPut("ruleId", jsonObject.getInteger("ruleId")), "b.rule_id", "ruleId", sql, paramsMap, "=");
        sql.append(" order by  t.param_key asc ");
        Pagination<TempParamDefEntity_HI_RO> pagination = tempParamDefDAO_HI_RO.findPagination(sql, paramsMap, pageIndex, pageRows);
        return pagination;
    }


    @Override
    public void saveOrupdate(TempRuleDefEntity_HI entity_hi) {
        tempRuleDefDAO_HI.save(entity_hi);
    }

    @Override
    public TempRuleDefEntity_HI_RO findRuleOne(JSONObject jsonObject) {
        StringBuffer sql = new StringBuffer();
        sql.append(TempRuleDefEntity_HI_RO.QUERY_SQL);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        SaafToolUtils.parperParam(jsonObject, "t.is_enable", "isEnable", sql, paramsMap, "=");
        SaafToolUtils.parperParam(jsonObject, "t.sole_resouce_type", "soleResouceType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(jsonObject, "t.sole_product_type", "soleProductType", sql, paramsMap, "=");
        SaafToolUtils.parperParam(jsonObject, "t.is_include_ec", "isIncludeEc", sql, paramsMap, "=");
        SaafToolUtils.parperParam(jsonObject, "t.is_include_special", "isIncludeSpecial", sql, paramsMap, "=");
        List<TempRuleDefEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql, paramsMap);
        TempRuleDefEntity_HI_RO ruleDefEntity = null;
        if (list != null && !list.isEmpty()) {
            ruleDefEntity = list.get(0);
        }
        return ruleDefEntity;
    }

    @Override
    public List<TempRuleDefEntity_HI_RO> findRuleNameByParam(Integer paramId) {
        HashMap<String, Object> queryMap = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(TempRuleDefEntity_HI_RO.QUERY_RULE_BY_PARAM).append(" and a.param_id=:paramId");
        queryMap.put("paramId", paramId);
        List<TempRuleDefEntity_HI_RO> list = tempRuleDefDAO_HI_RO.findList(sql, queryMap);
        return list;
    }

    @Override
    public JSONObject getDocInputStream(Integer ruleId) throws Exception{
        JSONObject result = new JSONObject();
        String fileName = "";
        RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
        if (entity == null || entity.getFileContent() == null) {
            throw new IllegalArgumentException("还没有上传文件");
        }
        InputStream in = null;
        if (ruleId != null) {
            Map<String, Object> resultMap = tempRuleDefService.generateDocx(ruleId, true);
            in = (InputStream)resultMap.get("fis");
            fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".docx";
        } else {
            fileName = new String(entity.getFileTempName().getBytes(), "ISO8859-1");
            fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".docx";
        };
       /* if (!SaafToolUtils.isNullOrEmpty(content)) {
           // content = content.replaceAll("${\w+}","    ");
            content = content.replaceAll("\\$\\{\\w+\\}", "_________");
        }*/
        result.put("in",in);
        result.put("fileName",fileName);
        return result;
    }

}
