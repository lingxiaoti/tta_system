package com.sie.watsons.base.exclusive.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.item.model.entities.TtaItemEntity_HI;
import com.sie.watsons.base.item.model.entities.readonly.TtaItemEntity_HI_RO;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaBrandplnLEntity_HI_RO;
import com.sie.watsons.base.supplier.model.entities.readonly.TtaSupplierEntity_HI_RO;
import com.sie.watsons.base.supplier.model.inter.ITtaSupplier;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrtInfo;

@RestController
@RequestMapping("/ttaSoleAgrtInfoService")
public class TtaSoleAgrtInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtInfoService.class);

	@Autowired
	private ITtaSoleAgrtInfo ttaSoleAgrtInfoServer;

	@Autowired
	private ITtaSupplier ttaSupplierServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSoleAgrtInfoServer;
	}

	/**
	 * 保存或者更新独家协议信息
	 * @param params JSON参数，对象属性的JSON格式
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaSoleAgrtInfoEntity_HI instance = ttaSoleAgrtInfoServer.saveOrUpdate(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 删除
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "delete")
	public String deleteSoleAgrtInfoById(@RequestParam(required = true) String params){
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONArray ids = jsonObject.getJSONArray("ids");
			Assert.notNull(ids, "未选择数据,删除失败");
			for (int i = 0; i < ids.size(); i++) {
				Integer id = (Integer)ids.get(i);
				ttaSoleAgrtInfoServer.deleteTtaSoleItem(id);
				ttaSoleAgrtInfoServer.deleteSoleAgrtInfoById(id);
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
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
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			LOGGER.info("参数params:{}",params);
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSoleAgrtInfoEntity_HI_RO> findList = ttaSoleAgrtInfoServer.findSoleAgrtInfo(jsonObject, pageIndex, pageRows);
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
	 * 批量更新保存soleAgrtInfo
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveBatchSoleAgrtInfo")
	public String saveBatchSoleAgrtInfo(@RequestParam(required = true) String params) {
		try {
			Integer userId = this.getSessionUserId();
			JSONObject paramsJson = parseObject(params);
			ttaSoleAgrtInfoServer.saveBatchSoleAgrtInfo(paramsJson, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:"+ e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询供应商列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplier")
	public String findSupplier(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaSupplierEntity_HI_RO> result = ttaSupplierServer.findTtaSuppliers(jsonObject, pageIndex, pageRows);
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
	 * 独家协议信息新增弹窗soleAgrtInfo中的部门按钮数据
	 * @param params 参数
	 * @param pageIndex 页码
	 * @param pageRows 页容量
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findItemDept")
	public String findItemDept(@RequestParam(required = false) String params,
							   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
							   @RequestParam(required = false, defaultValue = "10") Integer pageRows){
		try {
			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<TtaItemEntity_HI_RO> result= ttaSoleAgrtInfoServer.findItemDept(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
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
	 * 批量导入ITEM信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "saveImportItemDetail")
	public String saveImportOIInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			int userId = getSessionUserId();
			int size = ttaSoleAgrtInfoServer.saveImportItemDetail(jsonObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "总共"+size+"条数据导入成功", size, null).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findProposalBrand")
	public String findProposalVendor(@RequestParam(required = false) String params,
									 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaBrandplnLEntity_HI_RO> pagination = ttaSoleAgrtInfoServer.findProposalBrand(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findProposalDept")
	public String findProposalDept(@RequestParam(required = false) String params,
									 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaBrandplnLEntity_HI_RO> pagination = ttaSoleAgrtInfoServer.findProposalDept(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

}