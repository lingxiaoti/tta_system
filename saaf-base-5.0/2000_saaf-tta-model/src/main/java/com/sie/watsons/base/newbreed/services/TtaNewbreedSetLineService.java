package com.sie.watsons.base.newbreed.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;

import com.sie.watsons.base.newbreed.model.entities.TtaNewbreedSetLineEntity_HI;
import com.sie.watsons.base.newbreed.model.inter.ITtaNewbreedSetLine;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ttaNewbreedSetLineService")
public class TtaNewbreedSetLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaNewbreedSetLineService.class);

	@Autowired
	private ITtaNewbreedSetLine ttaNewbreedSetLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaNewbreedSetLineServer;
	}

	/**
	 * 新增&修改新品种信息
	 * @param params 对象属性的JSON格式
	 * {
	 *     breedName:新品种名称,
	 *     range:取值范围,
	 * }
	 * @return 新增&修改结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdateTtaNewbreedSetLIne")
	public String saveOrUpdateTtaNewbreedSetLIne(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONObject nbLine = paramsJSON.getJSONObject("nbLine");
			JSONArray objects = new JSONArray();
			objects.add(nbLine);
			List<TtaNewbreedSetLineEntity_HI> obj = ttaNewbreedSetLineServer.saveOrUpdateTtaNewbreedSetLineInfo(objects, paramsJSON.getIntValue("varUserId"), null, paramsJSON.getInteger("deleteFlag"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, obj).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("新增&修改新品种宣传推广服务费项目设置信息参数异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("新增&修改新品种宣传推广服务费项目设置信息异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常", 0, null).toString();
		}
	}

}