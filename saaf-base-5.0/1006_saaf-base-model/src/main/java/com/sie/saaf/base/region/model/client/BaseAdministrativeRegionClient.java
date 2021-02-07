package com.sie.saaf.base.region.model.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.region.model.entities.BaseAdminstrativeRegionEntity_HI;
import com.sie.saaf.base.region.model.inter.IBaseAdminstrativeRegion;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.util.HttpClientUtil;
import com.yhg.base.utils.SToolUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;

/**
 * @author ZhangJun
 * @createTime 2018-11-20 9:47 PM
 * @description
 */
@Component
public class BaseAdministrativeRegionClient {

	@Autowired
	private IBaseAdminstrativeRegion baseAdminstrativeRegionServer;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 初始化行政区域
	 * @param queryParamJSON {
	 *     keywords:规则：只支持单个关键词语搜索关键词支持
	 *     				行政区名称、citycode、adcode
	 *     				例如，在subdistrict=2，搜索省份（例如山东），能够显示市（例如济南），区（例如历下区）
	 *     subdistrict:规则：设置显示下级行政区级数（行政区级别包括：国家、省/直辖市、市、区/县4个级别）
	 *                   可选值：0、1、2、3、4
	 *                   0：不返回下级行政区；
	 *                   1：返回下一级行政区；
	 *                   2：返回下两级行政区；
	 *                   3：返回下三级行政区；
	 *                   以此类推
	 * }
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 初始化行政区域
	 */
	public JSONObject initialize(JSONObject queryParamJSON) throws IOException {
		String key = jedisCluster.get("INITIALIZE_KEY");

		//key存放在redis缓存中
		if (StringUtils.isBlank(key)){
			key = "";
		}

		Object keywords = queryParamJSON.getOrDefault("keywords","中国");
		Object subdistrict = queryParamJSON.getOrDefault("subdistrict","4");
		String url = "https://restapi.amap.com/v3/config/district?offset=100&subdistrict="+subdistrict.toString()+"&extensions=base&key="+key+"&keywords="+keywords.toString();

		String result = HttpClientUtil.send(url);
		JSONObject resultJSON = JSONObject.parseObject(result);
		if(StringUtils.equalsIgnoreCase(resultJSON.getString("status"),"1")){
			//数据返回成功，插入数据
			JSONArray regions = resultJSON.getJSONArray("districts");
			this.recursiveRegion(regions,null);

			return new JSONObject().fluentPut(SToolUtils.STATUS,"S").fluentPut(SToolUtils.MSG,resultJSON.getString("info"));
		}else{
			return new JSONObject().fluentPut(SToolUtils.STATUS,"E").fluentPut(SToolUtils.MSG,resultJSON.getString("info"));
		}
	}

	/**
	 * 递归行政区域数据
	 * @author ZhangJun
	 * @createTime 2018/11/20
	 * @description 递归行政区域数据
	 */
	private void recursiveRegion(JSONArray regions,Long parentRegionId){
		for(int i=0,size=regions.size();i<size;i++){
			JSONObject region = regions.getJSONObject(i);
			BaseAdminstrativeRegionEntity_HI entity = new BaseAdminstrativeRegionEntity_HI();

			if(StringUtils.equalsIgnoreCase(region.getString("level"),"country")) {
				parentRegionId = 0L;
			}
			entity.setParentRegionId(parentRegionId);
			entity.setCityCode(StringUtils.equalsIgnoreCase(region.getString("citycode"),"[]")?null:region.getString("citycode"));
			entity.setAdCode(region.getString("adcode"));
			entity.setRegionName(region.getString("name"));
			entity.setRegionCenter(region.getString("center"));
			entity.setRegionLevel(region.getString("level"));
			entity.setRegionDescription(region.getString("name"));
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
			entity.setOperatorUserId(1);

			baseAdminstrativeRegionServer.saveOrUpdate(entity);

			JSONArray districts = region.getJSONArray("districts");
			if(districts.size() > 0){
				this.recursiveRegion(districts,entity.getRegionId());
			}
		}
	}
}
