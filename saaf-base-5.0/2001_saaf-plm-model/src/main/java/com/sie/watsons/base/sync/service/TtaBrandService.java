package com.sie.watsons.base.sync.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.sync.model.inter.ITtaBrand;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

@RestController
@RequestMapping("/ttaBrandService")
public class TtaBrandService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaBrandService.class);

	@Autowired
	private ITtaBrand ttaBrandServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaBrandServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "syncVMI")
	public String syncVMI(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
            ttaBrandServer.find(jsonObject, pageIndex, pageRows);
			//jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}