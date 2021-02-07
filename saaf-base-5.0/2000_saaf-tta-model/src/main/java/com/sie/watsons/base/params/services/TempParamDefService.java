package com.sie.watsons.base.params.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.HtmlUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.params.model.entities.TempParamDefEntity_HI;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.params.model.inter.ITempParamDef;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tempParamDefService")
public class TempParamDefService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempParamDefService.class);

    @Autowired
    private ITempParamDef tempParamDefServer;

    @Autowired
    private ITempRuleDef tempRuleDefServer;


    @Override
    public IBaseCommon getBaseCommonServer() {
        return this.tempParamDefServer;
    }


    @RequestMapping(value = "find", method = RequestMethod.POST)
    public String find(@RequestParam(required = false) String params,
                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                       @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotBlank(params)) {
                jsonObject = JSON.parseObject(params);
            }
            Pagination<TempParamDefEntity_HI_RO> pagination = tempParamDefServer.findPage(jsonObject, pageIndex, pageRows);
            jsonObject = (JSONObject) JSON.toJSON(pagination);
            jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();

        }
    }


    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            JSONObject jsonParams = this.parseObject(params);
            TempParamDefEntity_HI entityHi = JSONObject.parseObject(jsonParams.toJSONString(), TempParamDefEntity_HI.class);
            /*if (!entityHi.getParamKey().contains("#{")) {
                entityHi.setParamKey("#{" + entityHi.getParamKey().trim() + "}");
            }*/
            if (!entityHi.getParamKey().contains("start")) {
                entityHi.setParamKey("start" + entityHi.getParamKey().trim() + "End");
            }
            List<TempParamDefEntity_HI> entityList = tempParamDefServer.findList(new JSONObject().fluentPut("paramKey", entityHi.getParamKey()));
            if (entityList != null && !entityList.isEmpty()) {
                if (entityHi.getParamId() == null && entityHi.getParamKey().equalsIgnoreCase(entityList.get(0).getParamKey())) {
                    return SToolUtils.convertResultJSONObj("E", "参数名称不能重复", 0, null).toString();

                }
            }
            //entityHi.setParamContent(HtmlUtils.clearStyle(HtmlUtils.clearHTMLTag(entityHi.getParamContent())));//2020.4.16注释,清除span等其他html元素
            entityHi.setParamContent(HtmlUtils.clearStyleExculdeSpan(HtmlUtils.clearHTMLTagExculdeSpan(entityHi.getParamContent())));//不去除span等html并且它的style的样式
            tempParamDefServer.saveOrUpdate(entityHi);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, entityHi).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常", 0, null).toString();
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteById(String params) {
        String result =  SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 0, null).toString();
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer id = jsonObject.getInteger("id");
            List<TempRuleDefEntity_HI_RO> ruleList = tempRuleDefServer.findRuleNameByParam(id);
            if (ruleList != null &&  !ruleList.isEmpty() && StringUtils.isNotBlank(ruleList.get(0).getRuleName())) {
               return SToolUtils.convertResultJSONObj("E","该参数被引用的规则名称有：" + ruleList.get(0).getRuleName() + ", 不可删除！", 0, null).toString();
            }
            tempParamDefServer.deleteById(id);
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();

        }
    }


    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public String findById(String params) {
        try {
            JSONObject jsonObject = this.parseObject(params);
            Integer id = jsonObject.getInteger("id");
            TempParamDefEntity_HI entity = tempParamDefServer.getById(id);
            //保存或更新数据后可注释改代码
            //entity.setParamContent(HtmlUtils.clearStyle(HtmlUtils.clearHTMLTag(entity.getParamContent()))); //2020.4.16注释
            entity.setParamContent(HtmlUtils.clearStyleExculdeSpan(HtmlUtils.clearHTMLTagExculdeSpan(entity.getParamContent())));
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(entity)).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();

        }
    }


}