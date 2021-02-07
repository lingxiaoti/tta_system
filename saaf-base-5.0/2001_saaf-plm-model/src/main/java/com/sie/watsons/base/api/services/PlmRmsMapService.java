package com.sie.watsons.base.api.services;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.api.model.entities.readonly.PlmRmsMapEntity_HI_RO;
import com.sie.watsons.base.api.model.inter.IPlmRmsMap;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
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
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/plmRmsMapService")
public class PlmRmsMapService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmRmsMapService.class);

	@Autowired
	private IPlmRmsMap plmRmsMapServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.plmRmsMapServer;
	}

    @RequestMapping(method = RequestMethod.POST, value = "update")
    public String updateMaster(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            plmRmsMapServer.update(paramJSON);
            return SToolUtils.convertResultJSONObj("S", "更新成功", 0, null).toJSONString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "save")
    public String save(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            plmRmsMapServer.save(paramJSON);
            return SToolUtils.convertResultJSONObj("S", "保存成功", 0, null).toJSONString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "delete")
    public String delete(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = JSON.parseObject(params);
            plmRmsMapServer.delete(paramJSON);
            return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
        } catch (Exception e) {
            return SToolUtils.convertResultJSONObj("ERROR", "操作失败", 1,
                    e.getMessage()).toString();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "findRmsData")
    public String findPlmLineInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmRmsMapEntity_HI_RO> results = plmRmsMapServer.findRmsData(queryParamJSON, pageIndex, pageRows);
            queryParamJSON = (JSONObject) JSON.toJSON(results);
            queryParamJSON.put(SToolUtils.STATUS, "S");
            queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}