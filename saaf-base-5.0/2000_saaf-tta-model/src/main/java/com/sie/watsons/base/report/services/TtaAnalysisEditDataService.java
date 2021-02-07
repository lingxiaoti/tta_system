package com.sie.watsons.base.report.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.report.model.entities.TtaAnalysisEditDataEntity_HI;
import com.sie.watsons.base.report.model.entities.readonly.TtaAnalysisEditDataEntity_HI_RO;
import com.sie.watsons.base.report.model.inter.ITtaAnalysisEditData;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ttaAnalysisEditDataService")
public class TtaAnalysisEditDataService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAnalysisEditDataService.class);

	@Autowired
	private ITtaAnalysisEditData ttaAnalysisEditDataServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaAnalysisEditDataServer;
	}


    /**
     *
     * @param params JSON参数，查询条件的JSON格式
     * @param pageIndex 页码
     * @param pageRows 每页查询记录数
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "findPagination")
    public String findPagination(@RequestParam(required = false) String params,
                                 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                 @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject jsonObject = parseObject(params);
            Pagination<TtaAnalysisEditDataEntity_HI_RO> result = ttaAnalysisEditDataServer.findInfo(jsonObject,pageIndex,pageRows);
            jsonObject = (JSONObject) JSON.toJSON(result);
            jsonObject.put(SToolUtils.STATUS,"S");
            jsonObject.put(SToolUtils.MSG,SUCCESS_MSG);
            return jsonObject.toString();
        }catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }


    /**
     * @param params
     * @description 保存/修改
     */
    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
    public String saveOrUpdate(@RequestParam(required = true) String params) {
        try {
            int userId = getSessionUserId();
            JSONObject jsonObject = JSON.parseObject(params);
            TtaAnalysisEditDataEntity_HI instance = ttaAnalysisEditDataServer.saveOrUpdate(jsonObject, userId);
            return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
        }
    }

}