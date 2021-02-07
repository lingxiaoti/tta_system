package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.redisUtil.ResultUtils;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupWarehouseEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupWarehouse;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@RestController
@RequestMapping("/plmSupWarehouseService")
public class PlmSupWarehouseService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupWarehouseService.class);
	@Autowired
	private IPlmSupWarehouse plmSupWarehouseServer;

	private ExecutorService concurrentExportPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

	private LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(20);

	public PlmSupWarehouseService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	/**
	 * 保存或更新数据
	 * @param params JSON参数 <br>
	 *     {<br>
	 *                      apihId:主键，（更新时必填）<br>
	 *                      centerName:项目/中心名称<br>
	 *                      centerCode:项目/中心编码<br>
	 *                      versionNum:版本号（更新时必填）<br>
	 *     }
	 * @return
	 * @author your name
	 * @creteTime Tue Oct 15 13:46:14 CST 2019
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmSupWarehouseInfo")
	public String findPlmSupWarehouseInfo(@RequestParam(required = true) String params, @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmSupWarehouseEntity_HI_RO> results = plmSupWarehouseServer.findPlmSupWarehouseInfo(queryParamJSON, pageIndex, pageRows);
			ResultUtils.getLookUpValue("PLM_PRODUCT_RETURNPRO");
            ResultUtils.getLookUpValue("PLM_PRODUCT_SESION");
            ResultUtils.getLookUpValue("PLM_SUP_STORE_TYPE");
            ResultUtils.getLookUpValue("PLM_SUP_STOP_REASON");
            ResultUtils.getLookUpValue("PLM_SUP_STATUS");
            ResultUtils.getLookUpValue("PLM_PRODUCT_TRANS");
            ResultUtils.getLookUpValue("PLM_SUP_STATUS_ALL");
            ResultUtils.getLookUpValue("PLM_SUP_ORDER_STATUS");
            ResultUtils.getLookUpValue("PLM_SUP_ORDER_REASON");
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

	@RequestMapping(method = RequestMethod.POST, value = "importWarehouse")
	public String importWarehouse(@RequestParam(required = true) String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			plmSupWarehouseServer.saveWarehouseByExcel(queryParamJSON);
//			queryParamJSON = (JSONObject) JSON.toJSON(results);
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

	@RequestMapping(method = RequestMethod.POST, value = "importWarehouse2")
	public String importWarehouse2(@RequestParam(required = true) String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			Integer deptId = this.getUserSessionBean().getDeptId();
			queryParamJSON.put("userDept", deptId);
			Object object = plmSupWarehouseServer.saveAndImportWarehouse2(queryParamJSON);
			queue.offer(queryParamJSON);
			Runnable runnable= () -> {
				try {
					LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),queue.size());
					JSONObject top = queue.take();
					plmSupWarehouseServer.saveAndImportWarehouse(top);
				}catch (Exception e){
					LOGGER.error(e.getMessage(),e);
				}
			};
			concurrentExportPool.submit(runnable);
			queryParamJSON = (JSONObject) JSON.toJSON(object);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
//            return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();

		}
	}

	@PostMapping("deletePlmSupForList")
	public String deleteForList(@RequestParam(required = false) String params) {
		try {
			JSONObject parameters = this.parseObject(params);
			SaafToolUtils.validateJsonParms(parameters,"ids");
			JSONArray ids = parameters.getJSONArray("ids");
			List<Integer> headerIds= ids.toJavaList(Integer.class);
			for (Integer headerId: headerIds) {
				plmSupWarehouseServer.deletePlmSupForList(parameters);
			}


			return SToolUtils.convertResultJSONObj("S", "删除成功", 0, null).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "删除失败", 0, e).toJSONString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmSupWarehouseInfoById")
	public String findPlmSupWarehouseInfo(@RequestParam(required = true) String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			PlmSupWarehouseEntity_HI_RO results = plmSupWarehouseServer.findPlmSupWarehouseInfoById(queryParamJSON);
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
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdatePlmSupWarehouse")
	public String saveOrUpdatePlmSupWarehouse(@RequestParam(required = true) String params) {
		try{
			JSONObject queryParamJSON = parseObject(params);
			String results = plmSupWarehouseServer.saveOrUpdatePlmSupWarehouse(queryParamJSON);
			JSONObject jsonObject = JSONObject.parseObject(results);
			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, jsonObject).toJSONString();

		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}
