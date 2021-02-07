package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.dao.BaseProfileValueDAO_HI;
import com.sie.saaf.base.shiro.model.dao.readonly.BaseProfileValueDAO_HI_RO;
import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseProfileValue_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseProfileValue;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * 对表base_profile_value进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
@Component("baseProfileValueServer")
public class BaseProfileValueServer extends BaseCommonServer<BaseProfileValueEntity_HI> implements IBaseProfileValue {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseProfileValueServer.class);
	@Autowired
	private BaseProfileValueDAO_HI baseProfileValueDAO_HI;
	@Autowired
	private BaseProfileValueDAO_HI_RO baseProfileValueDAO_HI_RO;

	public BaseProfileValueServer() {
		super();
	}

	/**
	 * 分页查询数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * profileId:profileId<br>
	 * keyTableName:业务表名<br>
	 * systemCode:系统编码，默认为All<br>
	 * deleteFlag:是否删除；0:否;1:删除<br>
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页数据列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * profileValueId:主键ID<br>
	 * profileId:profile表Id<br>
	 * businessKey:业务表对应的主键<br>
	 * systemCode:profile值关联的页面的Code<br>
	 * keyTableName:business_key来源的表的名字<br>
	 * profileValue:profile_value<br>
	 * deleteFlag;//是否删除；0：否；1：删除<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }],<br>
	 * firstIndex: 首页索引,<br>
	 * lastIndex: 尾页索引,<br>
	 * nextIndex: 下一页索引,<br>
	 * pageSize: 每页记录数,<br>
	 * pagesCount: 总页数,<br>
	 * preIndex: 上一页索引<br>
	 * }
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@Override
	public Pagination<BaseProfileValueEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON,pageIndex,pageRows);
	}

	/**
	 * 保存一条数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * profileValueId:主键ID（更新时必填）<br>
	 * profileId:profile表Id<br>
	 * businessKey:业务表对应的主键<br>
	 * systemCode:profile值关联的页面的Code<br>
	 * keyTableName:business_key来源的表的名字<br>
	 * profileValue:profile_value<br>
	 * deleteFlag;//是否删除；0：否；1：删除<br>
	 * versionNum:版本号（更新时必填）<br>
	 * operatorUserId:操作者<br>
	 * }
	 *
	 * @return BaseProfileValueEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@Override
	public BaseProfileValueEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	@Override
	protected void setEntityDefaultValue(BaseProfileValueEntity_HI entity) {
		if (entity.getDeleteFlag()==null) {
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
		}
	}

	/**
	 * 设置查询条件
	 *
	 * @param queryParamJSON 入参
	 * @param paramsMap sql或hql参数
	 * @param sql sql或hql
	 * @param isHql 是否HQL查询，如果是HQL查询，自动将查询字段转换为对象属性
	 */
	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		boolean isValid = false;
		if(queryParamJSON.containsKey("isValid")){
			isValid = queryParamJSON.getBooleanValue("isValid");
		}
		if (isValid) {
			if (isHql) {
				sql.append(" and baseProfileValue.deleteFlag=:deleteFlag ");
			} else {
				sql.append(" and baseProfileValue.delete_flag=:deleteFlag ");
			}
			paramsMap.put("deleteFlag", CommonConstants.DELETE_FALSE);
		} else {
			SaafToolUtils.parperParam(queryParamJSON, "baseProfileValue.delete_flag", "deleteFlag", sql, paramsMap, "=", isHql);
		}
		SaafToolUtils.parperParam(queryParamJSON, "baseProfileValue.profile_id", "profileId", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseProfileValue.system_code", "systemCode", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseProfileValue.key_table_name", "keyTableName", sql, paramsMap, "like", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseProfileValue.business_key", "businessKey", sql, paramsMap, "=", isHql);
	}

	/**
	 * 更新删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:07
	 * @description 此方法覆盖deleteAll，Service调用此方法时，更新Profile删除标记，不直接删除
	 */
	@Override
	public void deleteAll(List<Serializable> ids) {
		List<Integer> profileValueIds = new ArrayList<>();
		Iterator<Serializable> it = ids.iterator();
		while(it.hasNext()){
			profileValueIds.add(Integer.parseInt(it.next().toString()));
		}
		baseProfileValueDAO_HI.updateDeleteFlag(profileValueIds,CommonConstants.DELETE_TRUE);
	}

	/**
	 * 查询列表，根据tableName,业务主键
	 * @param tableName 表名
	 * @param businessKey 业务主键
	 * @return {@link BaseProfileValueEntity_HI}
	 * @author ZhangJun
	 * @createTime 2018/1/12 15:55
	 * @description 查询列表，根据tableName,业务主键
	 */
	@Override
	public List<BaseProfileValueEntity_HI> findList(String tableName, Integer businessKey){
		JSONObject queryJSON = new JSONObject();
		queryJSON.put("keyTableName",tableName);
		queryJSON.put("businessKey",businessKey);
		return this.findList(queryJSON);
	}

	@Override
	public List<BaseProfileValue_HI_RO> findList(String tableName, String profileCode, List<Integer> businessKey){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from base_profile_value where delete_flag=0 and key_table_name=:tableName and profile_id=(select profile_id from base_profile where profile_code=:profileCode)");

		Map<String,Object> params = new HashMap<>();
		params.put("tableName",tableName);
		params.put("profileCode",profileCode);

		if(businessKey!=null && !businessKey.isEmpty()){
			sb.append(" and ").append(SaafToolUtils.buildLogicIN("business_key",businessKey));
		}
		List<BaseProfileValue_HI_RO> findList = baseProfileValueDAO_HI_RO.findList(sb,params);

		return findList;
	}

	@Override
	public String getUserIds(String profileCodes, String tableName) {
		if(StringUtils.isNotBlank(profileCodes)) {
			profileCodes = profileCodes.replace(",","','");
			profileCodes = "'"+profileCodes+"'";

			StringBuffer sb = new StringBuffer();
			sb.append("select * from base_profile_value where delete_flag=0 and key_table_name=:tableName and \n" +
					" delete_flag = 0 and profile_id in (select profile_id from base_profile where profile_code in (:profileCodes)) " );

			Map<String,Object> params = new HashMap<>();
			params.put("profileCodes",profileCodes);
			params.put("tableName",tableName);

			List<BaseProfileValue_HI_RO> findList = baseProfileValueDAO_HI_RO.findList(sb,params);
			if(CollectionUtils.isNotEmpty(findList)) {
				List<Integer> userIds = new ArrayList<Integer>();
				for(BaseProfileValue_HI_RO profileValue : findList) {
					if(null != profileValue.getBusinessKey() && !userIds.contains(profileValue.getBusinessKey())) {
						userIds.add(profileValue.getBusinessKey());
					}
				}
				return StringUtils.join(userIds,",");
			}
		}
		return "";
	}
}
