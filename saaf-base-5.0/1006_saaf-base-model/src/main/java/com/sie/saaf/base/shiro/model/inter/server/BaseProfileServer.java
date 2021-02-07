package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.report.model.inter.IBaseReportHeader;
import com.sie.saaf.base.shiro.model.dao.BaseProfileDAO_HI;
import com.sie.saaf.base.shiro.model.entities.BaseProfileEntity_HI;
import com.sie.saaf.base.shiro.model.entities.BaseProfileValueEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseProfile;
import com.sie.saaf.base.shiro.model.inter.IBaseProfileValue;
import com.sie.saaf.common.constant.CommonConstants;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

/**
 * 对base_profile表进行CRUD操作
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
@Component("baseProfileServer")
public class BaseProfileServer extends BaseCommonServer<BaseProfileEntity_HI> implements IBaseProfile {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseProfileServer.class);
	@Autowired
	private BaseProfileDAO_HI baseProfileDAO_HI;
	@Autowired
	private IBaseReportHeader baseReportHeaderServer;

	@Autowired
	private IBaseProfileValue baseProfileValueServer;


	public BaseProfileServer() {
		super();
	}

	/**
	 * 分页查询数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * profileCode:profile编码<br>
	 * dsId:数据源Id<br>
	 * dsName:数据源名字<br>
	 * profileName:profile名称<br>
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
	 * profileId:主键ID<br>
	 * profileCode:profile编码<br>
	 * dsId:数据源Id<br>
	 * dsName:数据源名字<br>
	 * sourceSql:source_sql<br>
	 * profileName:profile名称<br>
	 * profileDesc:profile描述<br>
	 * systemCode:系统编码：为哪个系统定义的profile，默认为All表示所有系统适用<br>
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
	public Pagination<BaseProfileEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		SaafToolUtils.cleanNull(queryParamJSON,"profileCode","profileDesc","dsName","profileName");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("from BaseProfileEntity_HI baseProfile where 1=1 ");
		changeQuerySql(queryParamJSON,queryParamMap,sb,true);
		Pagination<BaseProfileEntity_HI> findListResult = baseProfileDAO_HI.findPagination(sb, queryParamMap,pageIndex,pageRows);
		return findListResult;
	}

	/**
	 * 保存一条数据
	 *
	 * @param queryParamJSON JSON参数<br>
	 * {<br>
	 * profileId:主键ID（更新数据时必填）<br>
	 * profileCode:profile编码<br>
	 * dsId:数据源Id<br>
	 * dsName:数据源名字<br>
	 * sourceSql:source_sql<br>
	 * profileName:profile名称<br>
	 * profileDesc:profile描述<br>
	 * systemCode:系统编码：为哪个系统定义的profile，默认为All表示所有系统适用<br>
	 * deleteFlag;//是否删除；0：否；1：删除<br>
	 * versionNum:版本号（更新数据时必填）<br>
	 *
	 * }
	 *
	 * @return BaseProfileEntity_HI对象
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	@Override
	public BaseProfileEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	@Override
	protected void setEntityDefaultValue(BaseProfileEntity_HI entity) {
		if(entity.getDeleteFlag()==null){
			entity.setDeleteFlag(CommonConstants.DELETE_FALSE);
		}
		if(StringUtils.isEmpty(entity.getSystemCode())){
			entity.setSystemCode(CommonConstants.PROFILE_SYSTEM_CODE_DEFAULT_VALUE);
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
		if(isValid){
			if(isHql){
				sql.append(" and baseProfile.deleteFlag=:deleteFlag ");
			}else {
				sql.append(" and baseProfile.delete_flag=:deleteFlag ");
			}
			paramsMap.put("deleteFlag", CommonConstants.DELETE_FALSE);
		}else{
			SaafToolUtils.parperParam(queryParamJSON,"baseProfile.delete_flag","deleteFlag",sql,paramsMap,"=",isHql);
		}
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.profile_code","profileCode",sql,paramsMap,"like",isHql);
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.ds_id","dsId",sql,paramsMap,"=",isHql);
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.ds_name","dsName",sql,paramsMap,"like",isHql);
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.profile_name","profileName",sql,paramsMap,"like",isHql);
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.system_code","systemCode",sql,paramsMap,"=",isHql);
		SaafToolUtils.parperParam(queryParamJSON,"baseProfile.profile_desc","profileDesc",sql,paramsMap,"like",isHql);

	}

	/**
	 * 更新删除标记
	 * @author ZhangJun
	 * @createTime 2018/1/10 14:07
	 * @description 此方法覆盖deleteAll，Service调用此方法时，更新Profile删除标记，不直接删除
	 */
	@Override
	public void deleteAll(List<Serializable> ids) {
		List<Integer> profileIds = new ArrayList<>();
		Iterator<Serializable> it = ids.iterator();
		while(it.hasNext()){
			profileIds.add(Integer.parseInt(it.next().toString()));
		}
		baseProfileDAO_HI.updateDeleteFlag(profileIds,CommonConstants.DELETE_TRUE);
	}

	/**
	 * 通过profileId查询ProfileS中对应SQL的语句结果
	 * @param queryParamJSON 查询参数
	 * {
	 *     profileId:profile主键
	 * }
	 * @return Profile对应SQL的查询结果集
	 * @author ZhangJun
	 * @createTime 2018/1/11 19:19
	 * @description 通过profileId查询ProfileS中对应SQL的语句结果
	 */
	@Override
	public List<JSONObject> findProfileSqlDatas(JSONObject queryParamJSON) throws SQLException {
		Integer profileId = queryParamJSON.getInteger("profileId");

		BaseProfileEntity_HI entity = getById(profileId);

		Pagination<JSONObject> pageList = baseReportHeaderServer.executeNativeSql(entity.getDsId(),entity.getSourceSql(),1,40, null);
		List<JSONObject> findList = pageList.getData();

		return findList;
	}


    /**
     * 通过查询职责表主键值中对应SQL的语句结果
     * @param
     * queryParamJSON 查询参数
     * {
     *      profileCode:profile编码
     * }
     * respIdList:用户职责Id集合
     * @return Profile对应SQL的查询结果集
     * @author huangminglin
     * @createTime 2018/3/12
     * @description 通过职责表主键值查询ProfileS中对应SQL的语句结果
     */
    @Override
    public List<String> findProfileSqlDatasByResponsibilityId(JSONObject queryParamJSON,List<Integer> respIdList) throws SQLException{

        //从参数中获取profileCode
        String profileCode = queryParamJSON.getString("profileCode");

        //获取profileValueEntity集合
        List<BaseProfileValueEntity_HI> profileValueEntityList = new ArrayList<>();

        for(Integer responsibilityId: respIdList) {
            List<BaseProfileValueEntity_HI> profileValueEntityListAll = baseProfileValueServer.findList("base_responsibility", responsibilityId);
            for(BaseProfileValueEntity_HI profileValueEntity : profileValueEntityListAll) {
                if(profileValueEntity.getDeleteFlag() == 0) {
                    profileValueEntityList.add(profileValueEntity);
                }
            }
        }

        //获取profile结果值集
        List<String> findList = new ArrayList<>();

        if(profileValueEntityList != null && profileValueEntityList.size() > 0){
            for(BaseProfileValueEntity_HI baseProfileValueEntity: profileValueEntityList) {
                Integer                   profileId              = baseProfileValueEntity.getProfileId();
                BaseProfileEntity_HI      entity                 = getById(profileId);

                if(entity!= null &&entity.getProfileCode().equals(profileCode)) {
                    findList.add(baseProfileValueEntity.getProfileValue());
                }
            }
        }

        return findList;
    }
}
