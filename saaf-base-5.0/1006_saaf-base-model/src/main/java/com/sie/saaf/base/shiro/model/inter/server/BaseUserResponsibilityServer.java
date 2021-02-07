package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.app.event.PermissionCacheUpdateEvent;
import com.sie.saaf.base.shiro.model.entities.BaseUserResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.base.shiro.model.inter.IBaseUserResponsibility;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.base.user.model.inter.server.BaseUsersServer;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 对用户与职责关系表base_user_responsibility操作
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
@Component("baseUserResponsibilityServer")
public class BaseUserResponsibilityServer extends BaseCommonServer<BaseUserResponsibilityEntity_HI> implements IBaseUserResponsibility {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseUserResponsibilityServer.class);
	@Autowired
	private ViewObject<BaseUserResponsibilityEntity_HI> baseUserResponsibilityDAO_HI;
	@Autowired
	private BaseViewObject<BaseUserResponsibility_HI_RO> baseUserResponsibilityDAO_HI_RO;

    @Autowired
    private BaseUsersServer baseUsersServer;

//    @Autowired
//    private ApplicationContext applicationContext;

	public BaseUserResponsibilityServer() {
		super();
	}



	/**
	 * 保存一条记录
	 * @param queryParamJSON JSON参数<br>
	 *     {<br>
	 *         respUserId:主键<br>
	 *         responsibilityId:职责标识<br>
	 *         userId:用户标识<br>
	 *         startDateActive:生效时间<br>
	 *         endDateActive:失效时间<br>
	 *         versionNum:版本号<br>
	 *         operatorUserId:操作用户<br>
	 *     }
	 * @return BaseUserResponsibilityEntity_HI对象
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	@Override
	public BaseUserResponsibilityEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 保存用户职责<br>
	 * 当actionType=0时：responsibilityIds只有一个数据<br>
	 * 当actionType=1时：userIds只有一个数据
	 * @param queryParamJSON 参数<br>
	 *     {<br>
	 *         responsibilityIds:[1,2,3,4]职责标识,<br>
	 *         userIds:[1,2,3,4]用户标识数组,<br>
	 *         actionType:操作类型,0:职责添加用户，1：用户添加职责<br>
	 *     }
	 * @author ZhangJun
	 * @createTime 2018/1/8 12:39
	 * @description 保存用户职责
	 */
	@Override
	public void saveUserResp(JSONObject queryParamJSON){
		Set<Integer> userIdSet=new HashSet<>();

		int actionType = queryParamJSON.getIntValue("actionType");
		if(actionType == 0){
			//职责添加用户
			JSONArray responsibilityIds = queryParamJSON.getJSONArray("responsibilityIds");
			JSONArray userIds = queryParamJSON.getJSONArray("userIds");

			Integer responsibilityId = responsibilityIds.getIntValue(0);
			JSONObject queryParam = new JSONObject();
			queryParam.put("responsibilityId",responsibilityId);
//			queryParam.put("userId_in", StringUtils.join(userIds,","));
			List<BaseUserResponsibilityEntity_HI> baseUserResponsibilityEntitys = findList(queryParam);

			if(userIds.isEmpty()){
				//没有传入userIds，则删除该职责的所有用户
				if(baseUserResponsibilityEntitys!=null && !baseUserResponsibilityEntitys.isEmpty()) {
					for(BaseUserResponsibilityEntity_HI entity : baseUserResponsibilityEntitys) {
						entity.setEndDateActive(new Date());
						baseUserResponsibilityDAO_HI.update(entity);
						userIdSet.add(entity.getUserId());
					}
				}
			}else{
				/*-------删除不在userIds中的原始数据-------*/
				List<Integer> insertUserIds = new ArrayList<>();
				insertUserIds.addAll(userIds.toJavaList(Integer.class));

				for(BaseUserResponsibilityEntity_HI userResp : baseUserResponsibilityEntitys){
					boolean deleteFlag = true;
					for(int i=0;i<userIds.size();i++){
						Integer userId = userIds.getInteger(i);

						if(userResp.getUserId().intValue()==userId.intValue()){
							deleteFlag = false;
							insertUserIds.remove(userId);
							break;
						}
					}
					if(deleteFlag){
						userResp.setEndDateActive(new Date());
						baseUserResponsibilityDAO_HI.update(userResp);
					}else{
					    userResp.setStartDateActive(new Date());
					    userResp.setEndDateActive(null);
                        baseUserResponsibilityDAO_HI.update(userResp);
                    }
					userIdSet.add(userResp.getUserId());
				}
				/*-------删除不在userIds中的原始数据end-------*/

				/*-------插入新数据------*/
				for(int i=0;i<insertUserIds.size();i++){
					Integer userId = insertUserIds.get(i);
					this.save(userId,responsibilityId,queryParamJSON.getInteger("operatorUserId"));
					userIdSet.add(userId);
				}
				/*-------插入新数据end------*/
			}
		}else{
			//用户添加职责
			JSONArray userIds = queryParamJSON.getJSONArray("userIds");
			JSONArray responsibilityIds = queryParamJSON.getJSONArray("responsibilityIds");

			Integer userId = userIds.getInteger(0);

			JSONObject queryJSONParam = new JSONObject();
			queryJSONParam.put("userId",userId);
			List<BaseUserResponsibilityEntity_HI> userResps = findList(queryJSONParam);

            List<Integer> responsibilityIdList = new ArrayList<>();
            responsibilityIdList.addAll(responsibilityIds.toJavaList(Integer.class));

            if(userResps!=null && !userResps.isEmpty()) {
                for (BaseUserResponsibilityEntity_HI entity : userResps) {
                    for(int i=0;i<responsibilityIds.size();i++){
                        Integer responsibilityId = responsibilityIds.getInteger(i);
                        if(entity.getResponsibilityId().intValue()==responsibilityId.intValue()){
                            responsibilityIdList.remove(responsibilityId);
                            entity.setStartDateActive(new Date());
                            entity.setEndDateActive(null);
                            baseUserResponsibilityDAO_HI.update(entity);
                            userIdSet.add(entity.getUserId());
                            break;
                        }
                    }
                }
            }

            /*-------插入新数据------*/
            for(int i=0;i<responsibilityIdList.size();i++){
                Integer responsibilityId = responsibilityIdList.get(i);
                this.save(userId,responsibilityId,queryParamJSON.getInteger("operatorUserId"));
				userIdSet.add(userId);
            }
            /*-------插入新数据end------*/

        }
        //发布更新缓存事件
		PermissionCacheUpdateEvent event=new PermissionCacheUpdateEvent("update",userIdSet);
		SpringBeanUtil.applicationContext.publishEvent(event);

	}

	public List<BaseUserResponsibility_HI_RO> findBaseUsersResponsibilitys(JSONObject queryJSONParam){
		Map<String,Object> paramsMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(BaseUserResponsibility_HI_RO.QUERY_RESPONSIBILITY_BY_USERID_SQL);
		sb.append(" and (baseUserResponsibility.start_date_active- interval 1 day)<=sysdate and (baseUserResponsibility.end_date_active is null or baseUserResponsibility.end_date_active>=sysdate )");
		changeQuerySql(queryJSONParam, paramsMap, sb,false);
		List<BaseUserResponsibility_HI_RO> list = baseUserResponsibilityDAO_HI_RO.findList(sb, queryJSONParam);
		return list;
	}

	/**
	 * 保存
	 * @param userId 用户Id
	 * @param responsibilityId 职责Id
	 * @param operatorUserId 操作用户Id
	 * @author ZhangJun
	 * @createTime 2018/1/8 19:09
	 * @description 保存
	 */
	private void save(Integer userId,Integer responsibilityId,Integer operatorUserId){
		BaseUserResponsibilityEntity_HI entity = new BaseUserResponsibilityEntity_HI();
		entity.setUserId(userId);
		entity.setResponsibilityId(responsibilityId);
		entity.setOperatorUserId(operatorUserId);

		if(entity.getStartDateActive()==null){
			entity.setStartDateActive(new Date());
		}

		this.saveOrUpdate(entity);
	}
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseUserResponsibility.user_id", "userId", sql, paramsMap, "=", isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseUserResponsibility.responsibility_Id", "responsibilityId", sql, paramsMap, "=", isHql);
	}

    //用户职责同步
    @Override
    public void baseUserResponsibilitySyn(JSONObject queryParamJSON) {

        JSONObject paramJSON = new JSONObject();
        paramJSON.fluentPut("userType","20");
        List<BaseUsersEntity_HI> userList = baseUsersServer.findList(paramJSON);

        for (BaseUsersEntity_HI user : userList){
            Integer userId = user.getUserId();
            BaseUserResponsibilityEntity_HI baseUserResponsibilityEntityHi = new BaseUserResponsibilityEntity_HI();
            baseUserResponsibilityEntityHi.setUserId(userId);
            baseUserResponsibilityEntityHi.setResponsibilityId(120018);
            baseUserResponsibilityEntityHi.setStartDateActive(new Date());
            baseUserResponsibilityEntityHi.setCreationDate(new Date());
            baseUserResponsibilityEntityHi.setCreatedBy(-1);
            baseUserResponsibilityEntityHi.setVersionNum(0);
            baseUserResponsibilityEntityHi.setOperatorUserId(-1);
            baseUserResponsibilityEntityHi.setLastUpdateDate(new Date());

            baseUserResponsibilityDAO_HI.saveOrUpdate(baseUserResponsibilityEntityHi);
        }

        paramJSON.fluentPut("userDesc","CRM");
        List<BaseUsersEntity_HI> userList2 = baseUsersServer.findList(paramJSON);

        for(BaseUsersEntity_HI user : userList2){
            Integer userId = user.getUserId();
            BaseUserResponsibilityEntity_HI baseUserResponsibilityEntityHi = new BaseUserResponsibilityEntity_HI();
            baseUserResponsibilityEntityHi.setUserId(userId);
            baseUserResponsibilityEntityHi.setResponsibilityId(120019);
            baseUserResponsibilityEntityHi.setStartDateActive(new Date());
            baseUserResponsibilityEntityHi.setCreationDate(new Date());
            baseUserResponsibilityEntityHi.setCreatedBy(-1);
            baseUserResponsibilityEntityHi.setVersionNum(0);
            baseUserResponsibilityEntityHi.setOperatorUserId(-1);
            baseUserResponsibilityEntityHi.setLastUpdateDate(new Date());

            baseUserResponsibilityDAO_HI.saveOrUpdate(baseUserResponsibilityEntityHi);
        }
    }


}
