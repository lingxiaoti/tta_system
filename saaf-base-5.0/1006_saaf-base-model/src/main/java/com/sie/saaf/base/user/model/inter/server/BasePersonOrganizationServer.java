package com.sie.saaf.base.user.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePersonOrganizationEntity_HI;
import com.sie.saaf.base.user.model.inter.IBasePersonOrganization;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 人员与组织关系
 * 
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
@Component("basePersonOrganizationServer")
public class BasePersonOrganizationServer extends BaseCommonServer<BasePersonOrganizationEntity_HI>
		implements IBasePersonOrganization {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BasePersonOrganizationServer.class);
	@Autowired
	private ViewObject<BasePersonOrganizationEntity_HI> basePersonOrganizationDAO_HI;

	public BasePersonOrganizationServer() {
		super();
	}

	/**
	 * 保存一条记录
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            对象参数<br>
	 *            {<br>
	 *            personOrgId:主键Id（更新时必填）<br>
	 *            positionId:职位Id<br>
	 *            orgId:组织Id<br>
	 *            personId:人员Id<br>
	 *            startDate:生效日期<br>
	 *            endDate:失效日期<br>
	 *            versionNum: 版本号（更新时必填）<br>
	 *            }<br>
	 * @return BasePersonOrganizationEntity_HI对象
	 */
	@Override
	public BasePersonOrganizationEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}


	/**
	 * 设置默认值
	 * @param entity
	 */
	@Override
	protected void setEntityDefaultValue(BasePersonOrganizationEntity_HI entity) {
		if (entity.getStartDate() == null) {
			//默认生效日期为当前时间
			entity.setStartDate(new Date());
		}
	}

	/**
	 * 分页查询人员、组织、职位关系列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            查询参数
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页显示记录数
	 * @return 人员、组织、职位关系列表<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         personOrgId:主键Id<br>
	 *         positionId:职位Id<br>
	 *         orgId:组织Id<br>
	 *         personId:人员Id<br>
	 *         startDate:生效日期<br>
	 *         endDate:失效日期<br>
	 *         creationDate:创建时间<br>
	 *         createdBy:创建人<br>
	 *         lastUpdatedBy:更新人<br>
	 *         lastUpdateDate:更新时间<br>
	 *         versionNum:版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }<br>
	 */
	@Override
	public Pagination<BasePersonOrganizationEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex,
																	  Integer pageRows) {
		return super.findPagination(queryParamJSON,pageIndex,pageRows);
	}

	/**
	 * 保存组织与人员关系
	 * @param queryParamJSON 保存参数<br>
	 * {<br>
	 *     positionId:职位Id,<br>
	 *     orgId:组织Id,<br>
	 *     personIds:[人员Id数组],<br>
	 *     startDate:生效日期,<br>
	 *     endDate:失效日期<br>
	 * }
	 * @return {@link List<BasePersonOrganizationEntity_HI>}
	 * @author ZhangJun
	 * @createTime 2018/1/21 13:47
	 * @description 保存组织与人员关系
	 */
	@Override
	public List<BasePersonOrganizationEntity_HI> saveOrgPerson(JSONObject queryParamJSON) {
		Integer orgId = queryParamJSON.getInteger("orgId");
		Integer positionId = queryParamJSON.getInteger("positionId");
		JSONArray personIds = queryParamJSON.getJSONArray("personIds");
		Integer operatorUserId = queryParamJSON.getInteger("operatorUserId");

		Assert.notNull(orgId,"组织Id不能为空");
		Assert.notEmpty(personIds,"人员不能为空");

		StringBuffer sb = new StringBuffer("from BasePersonOrganizationEntity_HI where 1=1 ");
		Map<String,Object> params = new HashMap<>();
//		params.put("orgId",orgId);
//		if(positionId != null ){
//			params.put("positionId",positionId);
//		}

		SaafToolUtils.parperHbmParam(BasePersonOrganizationEntity_HI.class,queryParamJSON,sb,params);
		List<BasePersonOrganizationEntity_HI> list = this.basePersonOrganizationDAO_HI.findList(sb,params);
		Map<String,BasePersonOrganizationEntity_HI> assemIdMap = new HashMap<>();//已存在记录
		if(list!=null && !list.isEmpty()){
			for(BasePersonOrganizationEntity_HI entity : list){
				if(positionId!=null) {
					String key =entity.getOrgId()+"_"+entity.getPositionId()+"_"+entity.getPersonId();
					assemIdMap.put(key,entity);
				}else{
					String key = entity.getOrgId()+"_"+entity.getPersonId();
					assemIdMap.put(key,entity);
				}
			}
		}

		List<BasePersonOrganizationEntity_HI> entities = new ArrayList<>();//需要保存的记录
		List<BasePersonOrganizationEntity_HI> assemEntities = new ArrayList<>();//需要保存的记录

		if(personIds!=null && !personIds.isEmpty()){
			for(int i=0;i<personIds.size();i++){
				Integer personId = personIds.getInteger(i);

				String key = "";
				if(positionId != null){
					key = orgId+"_"+positionId+"_"+personId;
				}else{
					key = orgId+"_"+personId;
				}
				if(assemIdMap.containsKey(key)){
					//如果记录已存在，则不再添加
					assemEntities.add(assemIdMap.get(key));
					continue;
				}

				BasePersonOrganizationEntity_HI model = new BasePersonOrganizationEntity_HI();
				model.setPersonId(personId);
				model.setOrgId(orgId);
				model.setPositionId(positionId);
				model.setOperatorUserId(operatorUserId);
				this.setEntityDefaultValue(model);
				entities.add(model);
			}
			if(!entities.isEmpty()) {
				//如果有需要添加的记录，则执行保存
				this.save(entities);
			}
			entities.addAll(assemEntities);
		}

		return entities;
	}
}
