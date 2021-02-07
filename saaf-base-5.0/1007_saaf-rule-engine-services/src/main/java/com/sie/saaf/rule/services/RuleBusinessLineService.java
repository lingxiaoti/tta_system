package com.sie.saaf.rule.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.rule.constant.Constant;
import com.sie.saaf.rule.model.entities.readonly.RuleBusinessLineEntity_HI_RO;
import com.sie.saaf.rule.model.inter.IRuleBusinessLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ruleBusinessLineService")
public class RuleBusinessLineService extends CommonAbstractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleBusinessLineService.class);
    @Autowired
    private IRuleBusinessLine ruleBusinessLineServer ;

    @Override
    public IBaseCommon getBaseCommonServer() {
        return ruleBusinessLineServer;
    }
    
    @RequestMapping(method = RequestMethod.POST,value="query")
    public String query(@RequestParam(required=false) String params,
    		@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
    		@RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            Pagination<RuleBusinessLineEntity_HI_RO> pagination = ruleBusinessLineServer.find(queryParamJSON,pageIndex,pageRows);
    		JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));

            JSONArray array = results.getJSONArray("data");
            JSONArray resultArray = new JSONArray();
            for (int i = 0; i < array.size(); i++) {
                JSONObject json = array.getJSONObject(i);
                json.put("ruleBusinessLineMapptypeMeaning", Constant.BUSINESSLINE_MAPPTYPE.get(json.getString("ruleBusinessLineMapptype")));
                resultArray.add(json);
            }
            results.put(SToolUtils.DATA, resultArray);

    		results.put(SToolUtils.STATUS, SUCCESS_STATUS);
    		results.put(SToolUtils.MSG, "成功");
    		return results.toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="delete")
    public String delete(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            if(queryParamJSON==null || !queryParamJSON.containsKey("ruleBusinessLineId")){
                return SToolUtils.convertResultJSONObj(ERROR_STATUS, "缺少参数 ruleBusinessLineId ", 0, null).toString();
            }
            List<Serializable> ids = new ArrayList<Serializable>();
            String[] idArr = queryParamJSON.getString("ruleBusinessLineId").split(",");
            for (String id : idArr) {
                ids.add(id);
            }
            ruleBusinessLineServer.deleteAll(ids);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", ids.size(), ids).toString();
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST,value="findRuleBusinessLineInfo")
    public String findRuleBusinessLineInfo(@RequestParam(required=false) String params){
        try{
    		JSONObject queryParamJSON = parseObject(params);
            String resultStr = ruleBusinessLineServer.findRuleBusinessLineInfo(queryParamJSON);
            return resultStr;
        }catch(Exception e){
            LOGGER.error(e.getMessage(),e);
    		return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    //业务线分页查询，重写，以前的分页查询count居然返回都是0，醉了
    @PostMapping("/pageRuleBusinessLine")
    public String pageRuleBusinessLine(@RequestParam(required=false) String params,
                                       @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false,defaultValue = "10") Integer pageRows) {

        try {
            JSONObject parameters = parseObject(params);
            Pagination<RuleBusinessLineEntity_HI_RO> page = ruleBusinessLineServer.pageRuleBusinessLine(parameters, pageIndex, pageRows);
            JSONObject results = JSONObject.parseObject(JSON.toJSONString(page));
            results.put(SToolUtils.STATUS, SUCCESS_STATUS);
            results.put(SToolUtils.MSG, "成功");
            return results.toString();
        } catch (Exception e) {
            LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e).toJSONString();
        }
    }

}
