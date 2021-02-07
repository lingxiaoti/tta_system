package com.sie.watsons.base.exclusive.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRespRoleProfile_HI_RO;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleSupplierEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
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
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleSupplier;

import java.util.List;

@RestController
@RequestMapping("/ttaSoleSupplierService")
public class TtaSoleSupplierService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleSupplierService.class);

	@Autowired
	private ITtaSoleSupplier ttaSoleSupplierServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSoleSupplierServer;
	}

	/**
	 *  查找数据(已不用,2020.4.13)
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSoleSupplierEntity_HI_RO> pagination = ttaSoleSupplierServer.findSoleSupplierPagination(jsonObject, pageIndex, pageRows);
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(pagination));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *  查找数据
	 * @param params JSON参数，查询条件的JSON格式
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
								 @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
								 @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject jsonObject = parseObject(params);
			Pagination<TtaSoleSupplierEntity_HI_RO> findList = ttaSoleSupplierServer.find(jsonObject, pageIndex, pageRows);
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
	 * 保存proposal供应商的数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "save")
	public String saveProposalSupplier(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			TtaSoleSupplierEntity_HI instance= ttaSoleSupplierServer.saveProposalSupplier(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,instance).toString();
		} catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *删除proposal供应商数据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteById")
	public String deleteProposalSupplierById(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = this.parseObject(params);
			JSONArray ids = jsonObject.getJSONArray("ids");
			Assert.notNull(ids, "选择的数据为空,删除失败");
			for (Object id : ids) {
				Integer deleteId = (Integer) id;
				TtaSoleSupplierEntity_HI ttaSoleSupplierEntity_hi = ttaSoleSupplierServer.deleteProposalSupplierById(deleteId);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,0,null).toString();
		} catch (IllegalArgumentException ar) {
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, ar.getMessage(), 0,null).toString();
		} catch (Exception e) {
			LOGGER.error(".deleteById:{}" , e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常:" + e.getMessage(), 0,null).toString();
		}
	}

	/**
	 * 保存proposal供应商数据,废弃,已不用
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "ttaProposalSupplierSaveForSplitMerge")
	public String ttaProposalSupplierSaveForSplitMerge(@RequestParam(required = true) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			//proposal供应商
			List<TtaSoleSupplierEntity_HI> list= ttaSoleSupplierServer.ttaProposalSupplierSaveForSplitMerge(queryParamJSON);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1,list).toString();
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


}