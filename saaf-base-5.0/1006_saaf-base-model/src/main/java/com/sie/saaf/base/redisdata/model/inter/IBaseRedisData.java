package com.sie.saaf.base.redisdata.model.inter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.redisdata.model.entities.BaseRedisDataEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IBaseRedisData extends IBaseCommon<BaseRedisDataEntity_HI> {

	/**
	 * 根据redisKey查询数据
	 * @param redisKey redis缓存Key
	 * @return {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/24
	 * @description 根据redisKey查询数据
	 */
	BaseRedisDataEntity_HI findBaseRedisDataEntityByRedisKey(String redisKey);

	/**
	 * 根据redisType，redisKey查询唯一数据
	 * @param redisType 缓存类型
	 * @param redisKey 缓存Key
	 * @return {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 根据redisType，redisKey查询唯一数据
	 */
	BaseRedisDataEntity_HI findUnionEntity(String redisType, String redisKey);

	/**
	 * 刷新redis缓存数据
	 * @param queryParamJSON
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 刷新redis缓存数据
	 */
	void flushRedisCache(JSONObject queryParamJSON);


	JSONArray findRedis(JSONObject queryParamJSON);

	JSONObject saveRedis(JSONObject queryParamJSON);

	void deleteRedis(JSONObject queryParamJSON);

	String getRedisByKey(String redisType, String redisKey);

	JSONArray filtrationRedis(JSONArray result, JSONObject queryParamJSON);
}
