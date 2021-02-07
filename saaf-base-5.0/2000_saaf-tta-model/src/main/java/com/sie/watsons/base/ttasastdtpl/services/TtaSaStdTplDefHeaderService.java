package com.sie.watsons.base.ttasastdtpl.services;

import com.alibaba.fastjson.JSON;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaSaStdTplDefHeaderEntity_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaSaStdTplDefHeaderEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaSaStdTplDefHeader;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
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
@RequestMapping("/ttaSaStdTplDefHeaderService")
public class TtaSaStdTplDefHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSaStdTplDefHeaderService.class);

	@Autowired
	private ITtaSaStdTplDefHeader ttaSaStdTplDefHeaderServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSaStdTplDefHeaderServer;
	}


	@RequestMapping(method = RequestMethod.POST, value = "findTtaSaStdTplDefHeaderTree")
	public String findTtaSaStdTplDefHeaderTree(@RequestParam(required = false) String params){
		JSONObject queryParamJSON = parseObject(params);
		List<TtaSaStdTplDefHeaderEntity_HI_RO> treeList = ttaSaStdTplDefHeaderServer.findTtaSaStdTplDefHeaderTree(queryParamJSON);
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", treeList.size(), treeList).toString();
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = parseObject(params);
			TtaSaStdTplDefHeaderEntity_HI instance = ttaSaStdTplDefHeaderServer.saveOrUpdate(jsonObject,getUserSessionBean(), userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaSaStdTplDefHeaderServer.delete(Integer.parseInt(pkId));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}
}