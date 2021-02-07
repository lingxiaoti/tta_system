package com.sie.watsons.base.exclusive.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleItemEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.item.model.inter.ITtaItem;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleItem;

import java.util.List;

@RestController
@RequestMapping("/ttaSoleItemService")
public class TtaSoleItemService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleItemService.class);

	@Autowired
	private ITtaSoleItem ttaSoleItemServer;

	@Autowired
	private ITtaItem ttaItemServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSoleItemServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询item列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItemList")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaItemEntity_HI_RO> result = ttaItemServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
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

	/**
	 * 保存item明细信息
	 * @param params JSON参数，对象属性的JSON格式
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			LOGGER.info("用户id :{}",userId);
			JSONObject jsonObject = JSON.parseObject(params);
			List<TtaSoleItemEntity_HI> list = ttaSoleItemServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 单条保存ITEM信息
	 * @param params JSON参数，对象属性的JSON格式
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdteBySingle")
	public String saveOrUpdteBySingle(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			LOGGER.info("用户id :{}",userId);
			JSONObject jsonObject = this.parseObject(params);
			ttaSoleItemServer.saveOrUpdteBySingle(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "delete")
	public String deleteTtaSoleItemById(@RequestParam(required = true) String params){
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONArray ids = jsonObject.getJSONArray("ids");
			Assert.notNull(ids, "未选择数据,请选择需要删除的数据");
			for (int i = 0; i < ids.size(); i++) {
				Integer id = ids.getInteger(i);
				ttaSoleItemServer.deleteSoleItemById(id);
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  查询数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String findSoleItemList(@RequestParam(required = false) String params,
					   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			LOGGER.info("参数params:{}",params);
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSoleItemEntity_HI_RO> findList = ttaSoleItemServer.findSoleItem(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(findList));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findSoleItemSingal")
	public String findSoleItemSingal(@RequestParam(required = true) String params) {
		try {
			Integer userId = this.getSessionUserId();
			JSONObject paramsJson = parseObject(params);
			int size = ttaSoleItemServer.findSoleItemSingal(paramsJson,userId);
			return SToolUtils.convertResultJSONObj("S", "成功", 0, size).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:"+ e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 批量保存
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBatchSoleItem")
	public String saveBatchSoleAgrtInfo(@RequestParam(required = true) String params) {
		try {
			Integer userId = this.getSessionUserId();
			JSONObject paramsJson = parseObject(params);
			List<TtaSoleItemEntity_HI> list= ttaSoleItemServer.saveBatchSoleItem(paramsJson,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, list).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:"+ e.getMessage(), 0, null).toString();
		}
	}

/*	*//**
	 * item信息批量导入(tta_sole_item)
	 * @param params
	 * @return
	 *//*
	@RequestMapping(method = RequestMethod.POST, value = "saveImportItemDetail")
	public String saveImportItemDetail(@RequestParam(required = false) String params) {
		try {
			Integer userId = this.getSessionUserId();
			JSONObject paramsJson = parseObject(params);
			JSONObject jsonObject= ttaSoleItemServer.saveBatchSoleItem(paramsJson.getInteger("soleAgrtInfoId"), paramsJson.getJSONArray("soleItemList"), userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:"+ e.getMessage(), 0, null).toString();
		}
	}*/

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询item列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findItemListByBrand")
	public String findItemListByBrand(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaItemEntity_HI_RO> result = new Pagination<>();
			Integer soleAgrtInfoId = jsonObject.getInteger("soleAgrtInfoId");
			if (!SaafToolUtils.isNullOrEmpty(soleAgrtInfoId)) {
				result = ttaSoleItemServer.findItem(jsonObject, pageIndex, pageRows);
			}
			jsonObject = (JSONObject) JSON.toJSON(result);
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

	@RequestMapping(method = RequestMethod.POST, value = "addAllItemData")
	public String addAllItemData(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			LOGGER.info("用户id :{}",userId);
			JSONObject jsonObject = this.parseObject(params);
			ttaSoleItemServer.saveAllItemData(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping("findExclusiveItemReport")
	public String findTtaSoleAgrtPagination(@RequestParam(required = false) String params,
											@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false, defaultValue = "5") Integer pageRows) {
		try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSoleItemEntity_HI_RO> page = ttaSoleItemServer.findExclusiveItemReport(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		}
	}

}