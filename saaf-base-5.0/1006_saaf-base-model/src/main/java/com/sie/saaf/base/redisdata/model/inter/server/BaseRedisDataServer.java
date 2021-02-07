package com.sie.saaf.base.redisdata.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.dict.model.inter.server.BaseLookupValuesServer;
import com.sie.saaf.base.redisdata.model.entities.BaseRedisDataEntity_HI;
import com.sie.saaf.base.redisdata.model.inter.IBaseRedisData;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;
import java.util.*;

@Component("baseRedisDataServer")
public class BaseRedisDataServer extends BaseCommonServer<BaseRedisDataEntity_HI> implements IBaseRedisData {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRedisDataServer.class);
	@Autowired
	private ViewObject<BaseRedisDataEntity_HI> baseRedisDataDAO_HI;
	@Autowired
	private JedisCluster jedisCluster;
//	@Autowired
//	private GenerateCodeServer generateCodeServer;
	@Autowired
	private BaseLookupValuesServer baseLookupValuesServer;

	//物料事务处理来源
	public static final String MTL_TXN_SOURCE_TYPES = "MTL_TXN_SOURCE_TYPES";
	//物料事务处理类型
	public static final String MTL_TRANSACTION_TYPES = "MTL_TRANSACTION_TYPES";
	//全局缓存
	public static final String GLOBAL_REDIS_CACHE = "GLOBAL_REDIS_CACHE";

	public BaseRedisDataServer() {
		super();
	}

	/**
	 * 根据redisKey查询数据
	 * @param redisKey redis缓存Key
	 * @return {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/24
	 * @description 根据redisKey查询数据
	 */
	@Override
	public BaseRedisDataEntity_HI findBaseRedisDataEntityByRedisKey(String redisKey){
		List<BaseRedisDataEntity_HI> list = baseRedisDataDAO_HI.findByProperty("redisKey",redisKey);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据redisType，redisKey查询唯一数据
	 * @param redisType 缓存类型
	 * @param redisKey 缓存Key
	 * @return {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 根据redisType，redisKey查询唯一数据
	 */
	@Override
	public BaseRedisDataEntity_HI findUnionEntity(String redisType, String redisKey) {
		String hql = "from BaseRedisDataEntity_HI where redisType=:redisType and redisKey=:redisKey";
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("redisType",redisType);
		paramsMap.put("redisKey",redisKey);
		List<BaseRedisDataEntity_HI> list = baseRedisDataDAO_HI.findList(hql,paramsMap);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 保存或更新数据时，同时将数据放到redis中
	 * @param entity redis数据对象 {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/24
	 * @description 保存或更新数据时，同时将数据放到redis中
	 */
	@Override
	public void saveOrUpdate(BaseRedisDataEntity_HI entity) {
		super.saveOrUpdate(entity);
		flushRedis(entity);
	}

	/**
	 * 保存或更新数据时，同时将数据放到redis中
	 * @param queryParamJSON {@link BaseRedisDataEntity_HI }
	 * @reutrn {@link BaseRedisDataEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/2/24
	 * @description 保存或更新数据时，同时将数据放到redis中
	 */
	@Override
	public BaseRedisDataEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		BaseRedisDataEntity_HI entity = super.saveOrUpdate(queryParamJSON);
		flushRedis(entity);
		return entity;
	}

	/**
	 * 刷新redis缓存
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 刷新redis缓存，如果enabled为Y时，更新redis值，如果为N时，删除redis值
	 */
	private void flushRedis(BaseRedisDataEntity_HI entity){
		if(StringUtils.equals(CommonConstants.ENABLED_TRUE,entity.getEnabled())) {
			jedisCluster.hset(entity.getRedisType(),entity.getRedisKey(),entity.getRedisValue());
		}else{
			if(jedisCluster.exists(entity.getRedisType())){
				//存在Key
				if(jedisCluster.hexists(entity.getRedisType(),entity.getRedisKey())){
					//Key中存在redisKey为键的值
					jedisCluster.hdel(entity.getRedisType(),entity.getRedisKey());
				}
				if(jedisCluster.hkeys(entity.getRedisType()).isEmpty()){
					//如果redisType为Key的hash不存在键值对时，删除redisType对应数据
					jedisCluster.del(entity.getRedisType());
				}
			}
		}
	}

	/**
	 * 刷新redis缓存数据
	 * @param queryParamJSON
	 * @author ZhangJun
	 * @createTime 2018/2/25
	 * @description 刷新redis缓存数据
	 */
	@Override
	public void flushRedisCache(JSONObject queryParamJSON) {
		List<BaseRedisDataEntity_HI> list = this.findList(queryParamJSON);
		if(list!=null && !list.isEmpty()){
			for(BaseRedisDataEntity_HI entity : list){
				flushRedis(entity);
			}
		}
	}

	/**
	 * 删除数据时，同时将数据从redis中移除
	 * @param ids 需要删除的数据ID集合
	 * @author ZhangJun
	 * @createTime 2018/2/24
	 * @description 删除数据时，同时将数据从redis中移除
	 */
	@Override
	public void deleteAll(List<Serializable> ids) {

		List<Integer> idList = new ArrayList<>();
		for(Serializable id : ids){
			idList.add(Integer.valueOf(id.toString()));
		}

		String hql = " from BaseRedisDataEntity_HI where redisDataId in (:ids)";
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("ids",idList);
		List<BaseRedisDataEntity_HI> entities = baseRedisDataDAO_HI.findList(hql,paramsMap);

		if(!entities.isEmpty()){
			for (BaseRedisDataEntity_HI entity:entities) {
				baseRedisDataDAO_HI.delete(entity);
				jedisCluster.hdel(entity.getRedisType(),entity.getRedisKey());
			}
		}
	}

	/**
	 * 查询Redis
	 * @param queryParamJSON {
	 *     redisType:类型,
	 *     redisKey:键
	 * }
	 * @author ZhangJun
	 * @createTime 2018/2/26
	 * @description 查询Redis
	 */
	@Override
	public JSONArray findRedis(JSONObject queryParamJSON){
		String redisType = queryParamJSON.getString("redisType");
		String redisKey = queryParamJSON.getString("redisKey");

		if(StringUtils.isBlank(redisType)){
			redisType = GLOBAL_REDIS_CACHE;
		}


		JSONArray result = new JSONArray();

		if(StringUtils.isNotBlank(redisKey)){
			//根据redisKey查询时，直接取得数据返回
			String d = jedisCluster.hget(redisType,redisKey);

			if(StringUtils.isNotBlank(d)) {
				if (StringUtils.startsWith(d, "[") && StringUtils.endsWith(d, "]")) {
					result.addAll(JSONArray.parseArray(d));
				} else if (StringUtils.startsWith(d, "{") && StringUtils.endsWith(d, "}")) {
					result.add(JSONObject.parseObject(d));
				} else {
					JSONObject obj = new JSONObject();
					obj.put("redisType", redisType);
					obj.put("redisKey", redisKey);
					obj.put("redisValue", d);
					result.add(obj);
				}
			}
		}else {

			if (!StringUtils.equals(redisType, GLOBAL_REDIS_CACHE)) {
				List<String> datas = jedisCluster.hvals(redisType);
				if (datas != null && !datas.isEmpty()) {
					for (String d : datas ) {
						if (StringUtils.startsWith(d, "[") && StringUtils.endsWith(d, "]")) {
							result.addAll(JSONArray.parseArray(d));
						} else if(StringUtils.startsWith(d, "{") && StringUtils.endsWith(d, "}")){
							result.add(JSONObject.parseObject(d));
						}else{
							JSONObject obj = new JSONObject();
							obj.put("redisType", redisType);
							obj.put("redisKey", redisKey);
							obj.put("redisValue", d);
							result.add(obj);
						}
					}
				}
				//result = JSONArray.parseArray(JSON.toJSONString(datas));
			} else {
				//全局缓存

				Map<String, String> datas = jedisCluster.hgetAll(GLOBAL_REDIS_CACHE);
				Set<String> keys = datas.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					String redisValue = datas.get(key);

					JSONObject obj = new JSONObject();
					obj.put("redisType", GLOBAL_REDIS_CACHE);
					obj.put("redisKey", key);
					obj.put("redisValue", redisValue);
					result.add(obj);
				}
			}
		}

		Map<String,JSONObject> lookupValusMap = new HashMap<>();
		JSONObject queryJSON = new JSONObject();
		queryJSON.put("lookupType",CommonConstants.REDIS_CACHE_TYPE);
		List findList  = baseLookupValuesServer.findCacheDic(queryJSON);
		if(findList!=null && !findList.isEmpty()){
			for (int i = 0; i < findList.size(); i++) {

				JSONObject objJSON = (JSONObject)JSON.toJSON(findList.get(i));

				lookupValusMap.put(objJSON.getString("lookupCode"),objJSON);

			}
		}

		if(result!=null && !result.isEmpty()){
			for (int i = 0; i < result.size(); i++) {
				JSONObject jsonObject = result.getJSONObject(i);

				String key = jsonObject.getString("redisType");
				if(lookupValusMap.containsKey(key)){
					JSONObject entity = lookupValusMap.get(key);
					jsonObject.put("redisTypeName",entity.getString("meaning"));
				}
			}
		}

		return this.filtrationRedis(result,queryParamJSON);
	}

	/**
	 * 保存Redis数据
	 * @param queryParamJSON {
	 *     redisType:类型,
	 *     redisKey:键,
	 *     redisValue:值
	 * }
	 * @author ZhangJun
	 * @createTime 2018/2/26
	 * @description 保存Redis数据
	 */
	@Override
	public JSONObject saveRedis(JSONObject queryParamJSON) {
		String redisType = queryParamJSON.getString("redisType");

		if(StringUtils.isBlank(redisType)){
			redisType = GLOBAL_REDIS_CACHE;
			queryParamJSON.put("redisType",redisType);
		}

		if(!StringUtils.equals(redisType,GLOBAL_REDIS_CACHE)){
			//long id = queryParamJSON.getIntValue("redisKey");
			//if(id==0) {
			//	id = generateCodeServer.getGenerateId(redisType);
			//	queryParamJSON.put("redisKey", id);
			//}
			String redisKey = queryParamJSON.getString("redisKey");
			jedisCluster.hset(redisType,redisKey,queryParamJSON.toJSONString());
		}else{
			//全局缓存
			String redisKey = queryParamJSON.getString("redisKey");
			String redisValue = queryParamJSON.getString("redisValue");
			jedisCluster.hset(GLOBAL_REDIS_CACHE,redisKey,redisValue);
		}
		return queryParamJSON;
	}

	/**
	 * 删除Redis缓存数据
	 * @param queryParamJSON {
	 *     redisType:缓存类型,
	 *     redisKey:缓存Key
	 * }
	 * @author ZhangJun
	 * @createTime 2018/2/26
	 * @description 删除Redis缓存数据
	 */
	@Override
	public void deleteRedis(JSONObject queryParamJSON){
		String redisType = queryParamJSON.getString("redisType");
		String redisKey = queryParamJSON.getString("redisKey");

		if(StringUtils.isBlank(redisType)){
			redisType = GLOBAL_REDIS_CACHE;
		}

		if(jedisCluster.hexists(redisType,redisKey)) {
			jedisCluster.hdel(redisType, redisKey);
		}

		if(jedisCluster.hkeys(redisType).isEmpty()){
			jedisCluster.del(redisType);
		}
	}

	/**
	 * 获取Redis值
	 * @param redisType redis分类，默认为全局缓存
	 * @param redisKey
	 * @author ZhangJun
	 * @createTime 2018/3/7
	 * @description 获取Redis值
	 */
	@Override
	public String getRedisByKey(String redisType, String redisKey){
		if(StringUtils.isBlank(redisType)){
			redisType = GLOBAL_REDIS_CACHE;
		}
		if(jedisCluster.hexists(redisType,redisKey)){
			return jedisCluster.hget(redisType,redisKey);
		}
		return null;
	}
	/**
	 *
	 * @author YangXiaowei
	 * @creteTime 2018/3/22
	 * @param result 需筛选的对象
	 * @param queryParamJSON 筛选条件
	 * @description 筛选Redis
	 */
	@Override
	public JSONArray filtrationRedis(JSONArray result, JSONObject queryParamJSON) {
		String redisKey = queryParamJSON.getString("redisKey");
		String transactionSourceTypeName = queryParamJSON.getString("transactionSourceTypeName");
		String description = queryParamJSON.getString("description");
		String transactionTypeName = queryParamJSON.getString("transactionTypeName");
		String transactionActionId = queryParamJSON.getString("transactionActionId");
		String transactionSourceTypeId = queryParamJSON.getString("transactionSourceTypeId");

		// 事务来源类型的筛选条件
		boolean key = StringUtils.isBlank(redisKey);
		boolean ts = StringUtils.isBlank(transactionSourceTypeName);
		boolean d = StringUtils.isBlank(description);
		// 事务处理类型的筛选条件
		boolean t = StringUtils.isBlank(transactionTypeName);
		boolean actionId = StringUtils.isBlank(transactionActionId);
		boolean typeId = StringUtils.isBlank(transactionSourceTypeId);

		if((d && ts || !key) && (t && actionId && typeId)){
			// 若值都为空,则不进行筛选
			return result;
		}

		for(int i = 0; i < result.size(); ) {
			JSONObject jsonObject = result.getJSONObject(i);
			if (!ts) {
				String typeName = jsonObject.getString("transactionSourceTypeName");
				if(!typeName.contains(transactionSourceTypeName)){
					result.remove(jsonObject);
					continue;
				}
			}
			if (!d) {
				String des = jsonObject.getString("description");
				if(!des.contains(description)){
					result.remove(jsonObject);
					continue;
				}
			}
			if (!t) {
				String tTypeName = jsonObject.getString("transactionTypeName");
				if(!tTypeName.contains(transactionTypeName)){
					result.remove(jsonObject);
					continue;
				}
			}
			if (!actionId) {
				String tActionId = jsonObject.getString("transactionActionId");
				if(!StringUtils.equals(tActionId,transactionActionId)){
					result.remove(jsonObject);
					continue;
				}
			}
			if (!typeId) {
				String sourceTypeId = jsonObject.getString("transactionSourceTypeId");
				if(!StringUtils.equals(sourceTypeId,transactionSourceTypeId)){
					result.remove(jsonObject);
					continue;
				}
			}
			i++;
		}
		return result;
	}

}
