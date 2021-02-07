package com.sie.saaf.base.redisdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.redisdata.model.entities.BaseRedisDataEntity_HI;
import com.sie.saaf.base.redisdata.model.inter.IBaseRedisData;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baseRedisDataService")
public class BaseRedisDataService extends CommonAbstractService {
private static final Logger logger = LoggerFactory.getLogger(BaseRedisDataService.class);
	@Autowired
	private IBaseRedisData baseRedisDataServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return baseRedisDataServer;
	}

	/**
	 * 根据Id查询数据
	 * @param params 参数id
	 * {
	 *     id:主键Id
	 * }
	 * @return
	 * @author ZhangJun
	 * @creteTime 2018/2/24
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	public String findById(String params) {
		return super.findById(params);
	}

	/**
	 * 查找数据
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @author ZhangJun
	 * @创建时间 2018/2/24
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findPagination(@RequestParam(required = false) String params,
			@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		return super.findPagination(params,pageIndex,pageRows);
	}

	/**
	 * 保存或更新数据
	 * @param params
	 * @return
	 * @author ZhangJun
	 * @创建时间 2018/2/24
	 */
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public String saveOrUpdate(@RequestParam(required = true) String params){
		JSONObject queryParamJSON = parseObject(params);
		SaafToolUtils.validateJsonParms(queryParamJSON,"redisType","redisKey");
		if(queryParamJSON.getInteger("redisDataId")==null) {
			//如果没有redisDataId值，则表示新增数据，新增数据需要判断Key值是否已存在
			String redisType = queryParamJSON.getString("redisType");
			String redisKey = queryParamJSON.getString("redisKey");
			BaseRedisDataEntity_HI entity = baseRedisDataServer.findUnionEntity(redisType,redisKey);
			if (entity != null) {
				return SToolUtils.convertResultJSONObj(ERROR_STATUS, "分类["+redisType+"]中已存在键为[" + entity.getRedisKey() + "]的数据", 0, null).toString();
			}
		}
		return super.saveOrUpdate(params);
	}

	/**
	 * 删除数据
	 * @param params 参数id
	 * {
	 *     id:需要删除的数据Id，如果需要删除多个，则用;分隔
	 * }
	 * @return
	 * @author ZhangJun
	 * @创建时间 2018/2/24
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		return super.delete(params);
	}

	/**
	 * 刷新redis缓存数据
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 刷新redis缓存数据
	 */
	@RequestMapping(method = RequestMethod.POST,value="flushRedisCache")
	public String flushRedisCache(@RequestParam(required=false) String params){
	    try{
			JSONObject queryParamJSON = parseObject(params);
			baseRedisDataServer.flushRedisCache(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}


	@RequestMapping(method = RequestMethod.POST,value="saveRedis")
	public String saveRedis(@RequestParam(required=false) String params){
	    try{
	    	logger.info("请求参数params:"+params);
			JSONObject queryParamJSON = JSON.parseObject(params);
			JSONObject redis = baseRedisDataServer.saveRedis(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 1, new JSONArray().fluentAdd(redis)).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
	
	@RequestMapping(method = RequestMethod.POST,value="deleteRedis")
	public String deleteRedis(@RequestParam(required=false) String params){
	    try{
			logger.info("请求参数params:"+params);
			JSONObject queryParamJSON = JSON.parseObject(params);
			SaafToolUtils.validateJsonParms(queryParamJSON,"redisType","redisKey");
	        baseRedisDataServer.deleteRedis(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", 0, null).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
	
	@RequestMapping(method = RequestMethod.POST,value="findRedis")
	public String findRedis(@RequestParam(required=false) String params){
	    try{
			logger.info("请求参数params:"+params);
			JSONObject queryParamJSON = JSON.parseObject(params);
			JSONArray result = baseRedisDataServer.findRedis(queryParamJSON);
	        return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "操作成功", result.size(), result).toString();
	    }catch(Exception e){
	        logger.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
	    }
	}
}
