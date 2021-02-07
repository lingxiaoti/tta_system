package com.sie.watsons.base.supplement.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementHead;

import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineRegionEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineWareEntity_HI_RO;

import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLine;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/plmSupplementLineService")
public class PlmSupplementLineService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmSupplementLineService.class);
	@Autowired
	private IPlmSupplementLine plmSupplementLineServer;

	@Autowired
	private IPlmSupplementHead plmSupplementHeadServer;

	private LinkedBlockingQueue<JSONObject> queue = new LinkedBlockingQueue<JSONObject>(20);

	private ExecutorService concurrentExportPool = Executors.newFixedThreadPool(4);

	public PlmSupplementLineService() {
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
	 * @creteTime Mon Sep 23 17:32:30 CST 2019
	 */
//	@RequestMapping(method = RequestMethod.POST, value = "findPlmSupplementLineInfo")
	//	/restServer/plmSupplementLineService/findPlmSupplementLineInfo
//	public String findPlmSupplementLineInfo(@RequestParam(required = true) String params) {
//		LOGGER.info(params);
//		JSONObject paramJSON = JSON.parseObject(params);
//		String resultStr = JSONObject.toJSONString(plmSupplementLineServer.findPlmSupplementLineInfo(paramJSON));
//		LOGGER.info(resultStr);
//		return resultStr;
//	}

	@RequestMapping(method = RequestMethod.POST, value = "findPlmLineInfo")
	public String findPlmLineInfo(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
												  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Pagination<PlmSupplementLineEntity_HI_RO> results = plmSupplementLineServer.findPlmSupplementLineInfo(queryParamJSON, pageIndex, pageRows);
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

    @RequestMapping(method = RequestMethod.POST, value = "findPlmLineRegion")
    public String findPlmLineRegion(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                  @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmSupplementLineRegionEntity_HI_RO> results = plmSupplementLineServer.findPlmSupplementLineRegion(queryParamJSON, pageIndex, pageRows);
            //queryParamJSON = JSONObject.parseObject(JSON.toJSONString(results));
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

    @RequestMapping(method = RequestMethod.POST, value = "findPlmLineWare")
    public String findPlmLineWare(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                                    @RequestParam(required = false,defaultValue = "10") Integer pageRows){
        try{
            JSONObject queryParamJSON = parseObject(params);
            Pagination<PlmSupplementLineWareEntity_HI_RO> results = plmSupplementLineServer.findPlmSupplementLineWare(queryParamJSON, pageIndex, pageRows);
            //queryParamJSON = JSONObject.parseObject(JSON.toJSONString(results));
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

	@RequestMapping(method = RequestMethod.POST, value = "findPlmLineDetail")
	public String findPlmLineDetail(@RequestParam(required = false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Object result = plmSupplementLineServer.findPlmSupplementLineDetail(queryParamJSON);
			queryParamJSON = (JSONObject) JSON.toJSON(result);
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

	@RequestMapping(method = RequestMethod.POST, value = "removePlmLine")
	public String removePlmLine(@RequestParam(required = false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			plmSupplementLineServer.removePlmLine(queryParamJSON);
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

	@RequestMapping(method = RequestMethod.POST, value = "findMeter")
	public String findMeter(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
							@RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Object results = plmSupplementLineServer.findMeter(queryParamJSON, pageIndex, pageRows);
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

	@RequestMapping(method = RequestMethod.POST, value = "importSupLines")
	public String importSupLines(@RequestParam(required = false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Integer deptId = this.getUserSessionBean().getDeptId();
			queryParamJSON.put("userDept", deptId);
			Object object = plmSupplementLineServer.importSupLine(queryParamJSON);
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

    @RequestMapping(method = RequestMethod.POST, value = "importSupLines2")
    public String importSupLines2(@RequestParam(required = false) String params){
        try{
            JSONObject queryParamJSON = parseObject(params);
            JSONObject object = plmSupplementLineServer.importSupLine3(queryParamJSON);
            queue.offer(object);
			Runnable runnable= () -> {
				try {
					LOGGER.info("{}fist step finished:{}",Thread.currentThread().getName(),queue.size());
					JSONObject top = queue.take();
					importSupLinesMulTh(top);
				}catch (Exception e){
					LOGGER.error(e.getMessage(),e);
				}
			};
			concurrentExportPool.submit(runnable);
//			importSupLinesMulTh(queryParamJSON);
//			JSONArray array = object.getJSONArray("lines");
//			int per = 500;
//			int total = array.size();
//			int pageCount = total / per;
//			for (int i = 0; i < pageCount; i++) {
//				int start = i * per;
//				int limit = per * (i + 1);
//				JSONArray arr = new JSONArray();
//				for (int j = start; j < limit; j++) {
//					JSONObject obj = array.getJSONObject(j);
//					arr.add(obj);
//				}
//				JSONObject tmp = (JSONObject) queryParamJSON.clone();
//				tmp.put("lines", arr);
//				p.offer(tmp);
//			}
//			int remains = total - pageCount * per;
//			if (remains > 0) {
//				JSONArray arr = new JSONArray();
//				for (int i = pageCount * per; i < total; i++) {
//					JSONObject obj = array.getJSONObject(i);
//					arr.add(obj);
//				}
//				JSONObject tmp = (JSONObject) queryParamJSON.clone();
//				tmp.put("lines", arr);
//				p.offer(tmp);
//			}
//			while (!p.isEmpty()) {
//				Runnable runnable= () -> {
//					try {
//						LOGGER.info("{}开始处理，当前任务数:{}",Thread.currentThread().getName(),p.size());
//						JSONObject top = p.take();
//						plmSupplementLineServer.saveImportSupLine2(top);
//					}catch (Exception e){
//						LOGGER.error(e.getMessage(),e);
//					}
//				};
//				concurrentExportPool.submit(runnable);
//			}
//			if (object.getString("uploadfilepath") == null) {
//				queryParamJSON = (JSONObject) JSON.toJSON(object);
//				queryParamJSON.put(SToolUtils.STATUS, "S");
//				queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
//			} else {
//				queryParamJSON = (JSONObject) JSON.toJSON(object);
//				queryParamJSON.put(SToolUtils.STATUS, "ERR_IMPORT");
//				queryParamJSON.put(SToolUtils.MSG, ERROR_MSG);
//			}
			queryParamJSON = (JSONObject) JSON.toJSON(object);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
            return queryParamJSON.toString();
        } catch (IllegalArgumentException e){
            LOGGER.warn(e.getMessage());
            return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
        } catch (Exception e){
                LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
        }
    }

	public void importSupLinesMulTh(JSONObject object) throws ExecutionException, InterruptedException {
		List<JSONArray> list = new ArrayList<>();
		Collections.synchronizedList(list);
		LinkedBlockingQueue<JSONObject> p = new LinkedBlockingQueue<JSONObject>(20);
		JSONArray array = object.getJSONArray("lines");
		int per = 200;
		int total = array.size();
		LOGGER.info("second step starting, total data:{}",total);
		int pageCount = total / per;
		int remains = total - pageCount * per;
		CountDownLatch latch;
		if (remains > 0) {
			latch = new CountDownLatch(pageCount + 1);
		} else {
			latch = new CountDownLatch(pageCount);
		}
		for (int i = 0; i < pageCount; i++) {
			int start = i * per;
			int limit = per * (i + 1);
			JSONArray arr = new JSONArray();
			for (int j = start; j < limit; j++) {
				JSONObject obj = array.getJSONObject(j);
				arr.add(obj);
			}
			JSONObject tmp = (JSONObject) object.clone();
			tmp.put("lines", arr);
			p.offer(tmp);
		}
		if (remains > 0) {
			JSONArray arr = new JSONArray();
			for (int i = pageCount * per; i < total; i++) {
				JSONObject obj = array.getJSONObject(i);
				arr.add(obj);
			}
			JSONObject tmp = (JSONObject) object.clone();
			tmp.put("lines", arr);
			p.offer(tmp);
		}
		while (!p.isEmpty()) {
			Callable<JSONArray> runnable= () -> {
				LOGGER.info("{}subroutine starting:{}",Thread.currentThread().getName(),p.size());
				JSONObject top = p.take();
				latch.countDown();
				return plmSupplementLineServer.saveImportSupLine2(top);
			};
			Future<JSONArray> future = concurrentExportPool.submit(runnable);
			JSONArray arr = future.get();
			list.add(arr);
		}
		latch.await();
		JSONArray errArr = new JSONArray();
		for (JSONArray j: list) {
			if (j != null) {
				for (int i = 0; i < j.size(); i++) {
					errArr.add(j.getJSONObject(i));
				}
			}
		}
		object.put("err", errArr);
		plmSupplementLineServer.saveImportSupLineMulTh(object);

	}

	@RequestMapping(method = RequestMethod.POST, value = "getUploadPath")
	public String getUploadPath(@RequestParam(required = false) String params){
		try{
			JSONObject queryParamJSON = parseObject(params);
			JSONObject object = plmSupplementLineServer.getUploadPath(queryParamJSON);
			queryParamJSON = (JSONObject) JSON.toJSON(object);
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
			return queryParamJSON.toString();
		} catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("M", e.getMessage(), 0, null).toString();
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E", e.getMessage(), 0, JSONArray.parseArray(e.getMessage())).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "getShops")
	public String getShops(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
						   @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
			Object object = plmSupplementHeadServer.getItems(queryParamJSON, pageIndex, pageRows);
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

	@RequestMapping(method = RequestMethod.POST, value = "findLines")
	public String findLines(@RequestParam(required = false) String params,@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
                            @RequestParam(required = false,defaultValue = "10") Integer pageRows){
		try{
			JSONObject queryParamJSON = parseObject(params);
            String headId = queryParamJSON.getString("headId");
            if (headId == null || "".equals(headId)) {
                queryParamJSON.put("data", null);
                queryParamJSON.put("count", 0);
                queryParamJSON.put(SToolUtils.STATUS, "S");
                queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);
                return queryParamJSON.toString();
            }
			Object results = plmSupplementLineServer.getLines(queryParamJSON, pageIndex, pageRows);
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



	public static void main(String arg[]){
		String a = "广东";
		String b = "" + a +"";
		JSONArray arr = new JSONArray();

		System.out.println(b);
		System.out.println(arr.size());
//		System.out.println(c);
	}

}
