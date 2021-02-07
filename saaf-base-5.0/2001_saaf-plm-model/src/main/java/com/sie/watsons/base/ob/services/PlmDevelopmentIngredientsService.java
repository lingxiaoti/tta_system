package com.sie.watsons.base.ob.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.ob.model.entities.PlmDevelopmentIngredientsEntity_HI;
import com.sie.watsons.base.ob.model.entities.readonly.PlmDevelopmentIngredientsEntity_HI_RO;
import com.sie.watsons.base.ob.model.inter.IPlmDevelopmentIngredients;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plmDevelopmentIngredientsService")
public class PlmDevelopmentIngredientsService extends CommonAbstractService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PlmDevelopmentIngredientsService.class);
    @Autowired
    private IPlmDevelopmentIngredients plmDevelopmentIngredientsServer;

    @Override
    public IBaseCommon getBaseCommonServer(){
        return this.plmDevelopmentIngredientsServer;
    }

    /**
     * 查询产品成分表
     *
     * @param params
     * {
     *     teamFrameworkId:框架ID
     * }
     * @param pageIndex 当前页码
     * @param pageRows 页面行数
     * @return Pagination
     */
    @RequestMapping(method = RequestMethod.POST, value = "findPlmDevelopmentIngredientsInfo")
    public String findPlmDevelopmentIngredientsInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                                  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        JSONObject queryParamJSON = parseObject(params);
        Pagination<PlmDevelopmentIngredientsEntity_HI_RO> plmDevelopmentIngredientsList = plmDevelopmentIngredientsServer.findPlmDevelopmentIngredientsInfo(queryParamJSON,pageIndex,pageRows);
        queryParamJSON = (JSONObject) JSON.toJSON(plmDevelopmentIngredientsList);
        queryParamJSON.put(SToolUtils.STATUS, "S");
        queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
        return queryParamJSON.toString();
    }

    /**
     * @param params
     * @return java.lang.String
     * @description   批量保存产品成分表数据
     **/
    @RequestMapping(method = RequestMethod.POST,value = "savePlmDevelopmentIngredientsInfo")
    public String savePlmDevelopmentIngredientsInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            JSONArray jsonArray = (JSONArray) JSONArray.toJSON(plmDevelopmentIngredientsServer.savePlmDevelopmentIngredientsInfo(parseObject));
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonArray).toString();

        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
//            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();

        }
    }

    /**
     * @param params
     * @return java.lang.String
     * @description   批量删除产品成分表数据
     **/
    @RequestMapping(method = RequestMethod.POST,value = "deletePlmDevelopmentIngredientsInfo")
    public String deletePlmDevelopmentIngredientsInfo(@RequestParam(required = true) String params){
        try{
            JSONObject parseObject = parseObject(params);
            int size = plmDevelopmentIngredientsServer.deletePlmDevelopmentIngredientsInfo(parseObject);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, size).toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }
}