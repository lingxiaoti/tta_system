package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseChannelPrivilegeEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseChannelPrivilege_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseProductInfoChannelOrg_HI_RO;
import com.sie.saaf.base.user.model.inter.IBaseChannelPrivilege;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafDateUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.sql.SQLException;
import java.util.*;

@Component("baseChannelPrivilegeServer")
public class BaseChannelPrivilegeServer extends BaseCommonServer<BaseChannelPrivilegeEntity_HI> implements IBaseChannelPrivilege {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseChannelPrivilegeServer.class);
	@Autowired
	private ViewObject<BaseChannelPrivilegeEntity_HI> baseChannelPrivilegeDAO_HI;

	@Autowired
	private BaseViewObject<BaseProductInfoChannelOrg_HI_RO> baseProductInfoChannelOrg_HI_RO;
//
//	@Autowired
//	private OracleTemplateServer oracleTemplateServer;
//	@Autowired
//	private JedisCluster jedisCluster;

//	@Autowired
//	private BaseViewObject<BaseChannelPrivilege_HI_RO> baseChannelPrivilegeDAO_HI_RO;


	public BaseChannelPrivilegeServer() {
		super();
	}


	/**
	 * 同步产品渠道授权信息
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 同步产品渠道授权信息
	 */
	@Override
	public JSONObject saveSyncBaseChannelPrivilege(JSONObject queryParamJSON){
		final String BASE_CHANNEL_PRIVILIGE_LAST_SYNC_TIME = "BASE_CHANNEL_PRIVILIGE_LAST_SYNC_TIME";
		JSONObject result = new JSONObject();
//		try {
//			String lastSyncTime = jedisCluster.hget(CommonConstants.RedisCacheKey.LAST_SYNC_TIME,BASE_CHANNEL_PRIVILIGE_LAST_SYNC_TIME);
//			if(StringUtils.isBlank(lastSyncTime)){
//				lastSyncTime = "2000-01-01 00:00:00";
//			}
////			String lastSyncTime = "2018-06-13 00:00:00";
//			StringBuffer sb = new StringBuffer();
//			sb.append("SELECT * FROM CUX.CUX_CDM_CHANNEL_PRIVILEGE CP WHERE LAST_UPDATE_DATE >=TO_DATE('"+lastSyncTime+"','yyyy-mm-dd HH24:mi:ss') ");
//			LOGGER.info("{}",sb.toString());
//			List<JSONObject> privilegeList = oracleTemplateServer.findList(sb.toString());
//
//			int count = 0;
//			if(privilegeList!=null && !privilegeList.isEmpty()) {
//				for (int i = 0; i < privilegeList.size(); i++) {
//					JSONObject privilege = privilegeList.get(i);
//
//					BaseChannelPrivilegeEntity_HI entity = JSON.toJavaObject(privilege, BaseChannelPrivilegeEntity_HI.class);
//					Integer orgId = privilege.getInteger("ORG_ID");
//					String accessType = privilege.getString("ACCESS_TYPE");
//					String channelType = privilege.getString("CHANNEL_TYPE");
//					Integer transactionTypeId = privilege.getInteger("TRANSACTION_TYPE_ID");
//					List<BaseChannelPrivilegeEntity_HI> BaseChannelPrivilegeList = findBaseChannelPrivilegeList(orgId, accessType, channelType, transactionTypeId);
//					if(BaseChannelPrivilegeList.size() > 0){
//						//删除多余数据
//						for (int j=1;j<BaseChannelPrivilegeList.size();j++){
//							baseChannelPrivilegeDAO_HI.delete(BaseChannelPrivilegeList.get(j));
//						}
//
//						BaseChannelPrivilegeEntity_HI entityNew = BaseChannelPrivilegeList.get(0);
//
//						Integer channelPrivilegeId = entityNew.getChannelPrivilegeId();
//						Integer versionNumOld = entityNew.getVersionNum();
//						Date creationDateOld = entityNew.getCreationDate();
//						Integer creationBy = entityNew.getCreatedBy();
//
//						BeanUtils.copyProperties(entity, entityNew);
//						entityNew.setChannelPrivilegeId(channelPrivilegeId);
//						entityNew.setCreatedBy(creationBy);
//						entityNew.setCreationDate(creationDateOld);
//						entityNew.setLastUpdateDate(new Date());
//						entityNew.setVersionNum(versionNumOld);
//						LOGGER.info("已存在同步过的数据，本次为更新，参数:{}", JSON.toJSONString(entityNew));
//						saveOrUpdate(entityNew);
//						count++;
//					}else{
//						entity.setOperatorUserId(1);
//						saveOrUpdate(entity);
//						count++;
//					}
//				}
//				LOGGER.info("保存渠道授权数据:{}条", count);
//			}else{
//				result.put("syncStatus","success");
//				result.put("syncMsg","当前未取得渠道授权数据");
//				LOGGER.info("产品同步结果：{}",result.toJSONString());
//			}
//			//删除多余数据
//			deleteIneffectiveData();
//
//			//同步完成，更新redis最后更新时间
//			jedisCluster.hset(CommonConstants.RedisCacheKey.LAST_SYNC_TIME, BASE_CHANNEL_PRIVILIGE_LAST_SYNC_TIME, SaafDateUtils.convertDateToString(new Date()));
//			result.put("syncStatus","success");
//			result.put("syncMsg","渠道授权同步完成");
//			result.put("updateCount",count);
//		} catch (Exception e) {
//			LOGGER.error("",e);
//			result.put("syncStatus","fail");
//			result.put("syncMsg","渠道授权同步出现异常:"+e.getMessage());
//		}
		return result;

	}

	@Override
	public List<String> findListByOrgId(JSONObject paramJSON) {
		String OrgIds = StringUtils.join(paramJSON.getJSONArray("operationOrgIds"), ",");
		if(StringUtils.isEmpty(OrgIds)){
			OrgIds = "0";
		}
		paramJSON.put("orgId_in",OrgIds);
		StringBuffer sb = new StringBuffer(BaseProductInfoChannelOrg_HI_RO.QUERY_CHANNEL_SQL);
		HashMap<String, Object> map = new HashMap<>();
		SaafToolUtils.parperParam(paramJSON, "CP.ORG_ID","orgId_in",sb, map, "in");
		List<BaseProductInfoChannelOrg_HI_RO> list = baseProductInfoChannelOrg_HI_RO.findList(sb, map);
		List<String> itemCodes= new ArrayList<>();
		for(BaseProductInfoChannelOrg_HI_RO entity : list){
			itemCodes.add(entity.getItemCode());
		}
		return itemCodes;
	}

	public List<BaseChannelPrivilegeEntity_HI> findBaseChannelPrivilegeList(Integer orgId, String accessType, String channelType, Integer transactionTypeId){
		String hql = "from BaseChannelPrivilegeEntity_HI where orgId = :orgId and accessType = :accessType and channelType = :channelType and transactionTypeId = :transactionTypeId";
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("orgId", orgId);
		paramsMap.put("accessType", accessType);
		paramsMap.put("channelType", channelType);
		paramsMap.put("transactionTypeId", transactionTypeId);
		return baseChannelPrivilegeDAO_HI.findList(hql, paramsMap);
	}

	public void deleteIneffectiveData() throws SQLException {
//		List<BaseChannelPrivilege_HI_RO> tidbList=baseChannelPrivilegeDAO_HI_RO.findList(BaseChannelPrivilege_HI_RO.QUERY);
//		for (BaseChannelPrivilege_HI_RO item:tidbList){
//			StringBuffer sql=new StringBuffer("SELECT count(1) FROM CUX.CUX_CDM_CHANNEL_PRIVILEGE where ")
//					.append(" TRANSACTION_TYPE_ID='").append(item.getTransactionTypeId()).append("'")
//					.append(" and ACCESS_TYPE='").append(item.getAccessType()).append("' ");
//			int count= oracleTemplateServer.findCount(sql.toString());
//			if (item.getQty()==null || item.getQty().intValue()==count)
//				continue;
//			LOGGER.warn("清除数据:{},oracle数量:{}",JSON.toJSONString(item),count);
//			deleteIneffectiveData(item.getAccessType(),item.getTransactionTypeId());
//		}
	}


//	private void deleteIneffectiveData(String accessType, Integer transactionTypeId) throws SQLException {
//		List<BaseChannelPrivilegeEntity_HI> list=baseChannelPrivilegeDAO_HI.findList("from BaseChannelPrivilegeEntity_HI where accessType=? and transactionTypeId=?",accessType,transactionTypeId);
//		for (BaseChannelPrivilegeEntity_HI item:list){
//			StringBuffer sql=new StringBuffer("SELECT * FROM CUX.CUX_CDM_CHANNEL_PRIVILEGE where TRANSACTION_TYPE_ID=:transactionTypeId and ACCESS_TYPE=:accessType ");
//			Map<String,Object> paramMap=new LinkedHashMap<>();
//			paramMap.put("transactionTypeId",transactionTypeId);
//			paramMap.put("accessType",accessType);
//			if (item.getOrgId()==null)
//				sql.append(" and ORG_ID is null ");
//			else{
//				sql.append(" and ORG_ID=:orgId");
//				paramMap.put("orgId",item.getOrgId());
//			}
//
//			if (item.getChannelType()==null)
//				sql.append(" and CHANNEL_TYPE is null");
//			else {
//				sql.append(" and CHANNEL_TYPE =:channelType");
//				paramMap.put("channelType",item.getChannelType());
//			}
//			List jsonlist= oracleTemplateServer.findList(sql.toString(),paramMap);
//			if (jsonlist.size()==0){
//				LOGGER.warn("base_channel_privilege 数据同步,删除数据：{}",JSON.toJSONString(item));
//				baseChannelPrivilegeDAO_HI.delete(item);
//			}
//		}
//	}

}
