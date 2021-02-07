package com.sie.watsons.base.fieldconfig.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.fieldconfig.model.inter.ITtaOiFieldMapping;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
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
@RequestMapping("/ttaOiFieldMappingService")
public class TtaOiFieldMappingService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiFieldMappingService.class);

	@Autowired
	private ITtaOiFieldMapping ttaOiFieldMappingServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaOiFieldMappingServer;
	}

	@RequestMapping("findFieldPagination")
	public String findFieldPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			queryParamJSON= SaafToolUtils.cleanNull(queryParamJSON,"tradeYear","businessType","isEnable","sourceFieldName","sourceFieldRemark","targetFieldName","targetFieldRemark");
			Pagination findList = ttaOiFieldMappingServer.findFieldPagination(queryParamJSON,pageIndex,pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询供应商列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findResourceField")
	public String findLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = this.parseObject(params);
			queryParamJSON= SaafToolUtils.cleanNull(queryParamJSON,"columnName","columnComment");
			Pagination findList = ttaOiFieldMappingServer.findResourceField(queryParamJSON, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常！", 0, null).toString();
		}
	}

    @RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateField")
    public String saveOrUpdateField(@RequestParam(required = false) String params) {
        try {
            JSONObject paramJSON = this.parseObject(params);
            ttaOiFieldMappingServer.saveOrUpdateField(paramJSON);
            return SToolUtils.convertResultJSONObj("S", "更新成功！", 0, null).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj("E", "服务异常！", 0, null).toString();
        }
    }

}