package com.sie.saaf.base.api.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiManagementEntity_HI;
import com.sie.saaf.base.api.model.entities.readonly.BaseApiManagementLookupValues_HI_RO;
import com.sie.saaf.base.api.model.inter.IBaseApiManagement;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模块API管理
 * 
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
@Component("baseApiManagementServer")
public class BaseApiManagementServer extends BaseCommonServer<BaseApiManagementEntity_HI>
		implements IBaseApiManagement {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiManagementServer.class);
	@Autowired
	private ViewObject<BaseApiManagementEntity_HI> baseApiManagementDAO_HI;
	@Autowired
	private BaseViewObject<BaseApiManagementLookupValues_HI_RO> baseApiManagementLookupValusDAO_HI_RO;

	public BaseApiManagementServer() {
		super();
	}

	/**
	 * 保存一条记录
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            参数<br>
	 *            {<br>
	 *            apiId:主键ID（更新时必填）<br>
	 *            interfaceName:API名称<br>
	 *            requestMode:请求方式：Post/Get<br>
	 *            apiStatus:状态<br>
	 *            urlAddress:服务地址<br>
	 *            developer:开发人员<br>
	 *            apiDesc:详细描述<br>
	 *            requestParam:请求参数<br>
	 *            requestParamDict:请求参数描述<br>
	 *            responseParam:返回参数<br>
	 *            responseParamDict:返回参数描述<br>
	 *            centerName:项目/中心名称<br>
	 *            centerCode:项目/中心编码<br>
	 *            modelName:模块名称<br>
	 *            modelCode:模块编码<br>
	 *            operatorUserId:操作用户<br>
	 *            }
	 * @return BaseApiManagementEntity_HI对象
	 */
	@Override
	public BaseApiManagementEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		String centerCode = queryParamJSON.getString("centerCode");
		String modelCode = queryParamJSON.getString("modelCode");
		String urlAddress = queryParamJSON.getString("urlAddress");

		SaafToolUtils.validateJsonParms(queryParamJSON,"centerCode","modelCode","interfaceName","urlAddress");


		JSONObject params = new JSONObject();
		params.put("centerCode",centerCode);
		params.put("modelCode",modelCode);
		params.put("urlAddress",urlAddress.replaceAll("\\s",""));

		if(StringUtils.isBlank(queryParamJSON.getString("apiId"))) {
			//新增时判断URL是否存在
			StringBuffer sb = new StringBuffer();
			sb.append("from BaseApiManagementEntity_HI where centerCode=:centerCode and modelCode=:modelCode and urlAddress=:urlAddress");
			List<BaseApiManagementEntity_HI> findList = baseApiManagementDAO_HI.findList(sb, params);

			Assert.isTrue(findList.isEmpty(), "服务地址[" + urlAddress + "]已存在");
		}

		queryParamJSON.put("urlAddress", urlAddress.replace("\\s",""));
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询API列表
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            apiId:主键ID<br>
	 *            interfaceName:API名称<br>
	 *            requestMode:请求方式：Post/Get<br>
	 *            apiStatus:状态<br>
	 *            urlAddress:服务地址<br>
	 *            developer:开发人员<br>
	 *            apiDesc:详细描述<br>
	 *            requestParam:请求参数<br>
	 *            requestParamDict:请求参数描述<br>
	 *            responseParam:返回参数<br>
	 *            responseParamDict:返回参数描述<br>
	 *            centerName:项目/中心名称<br>
	 *            centerCode:项目/中心编码<br>
	 *            modelName:模块名称<br>
	 *            modelCode:模块编码<br>
	 *            versionNum:版本号<br>
	 *            interfaceName:API名称<br>
	 *            requestMode:请求方式：Post/Get<br>
	 *            apiStatus:状态<br>
	 *            developer:开发人员<br>
	 *            centerName:项目/中心名称<br>
	 *            centerCode:项目/中心编码<br>
	 *            modelName:模块名称<br>
	 *            modelCode:模块编码<br>
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return API列表信息<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         apiId:主键ID<br>
	 *         interfaceName:API名称<br>
	 *         requestMode:请求方式：Post/Get<br>
	 *         apiStatus:状态<br>
	 *         urlAddress:服务地址<br>
	 *         developer:开发人员<br>
	 *         apiDesc:详细描述<br>
	 *         requestParam:请求参数<br>
	 *         requestParamDict:请求参数描述<br>
	 *         responseParam:返回参数<br>
	 *         responseParamDict:返回参数描述<br>
	 *         centerName:项目/中心名称<br>
	 *         centerCode:项目/中心编码<br>
	 *         modelName:模块名称<br>
	 *         modelCode:模块编码<br>
	 *         versionNum:版本号<br>
	 *         creationDate:创建时间<br>
	 *         createdBy:创建人<br>
	 *         lastUpdatedBy:更新人<br>
	 *         lastUpdateDate:更新时间<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	@Override
	public Pagination<BaseApiManagementEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON,pageIndex,pageRows);
	}

	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.api_Id", "apiId", sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.interface_Name", "interfaceName", sql, paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.developer", "developer", sql,	paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.center_Name", "centerName", sql, paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.center_Code", "centerCode", sql,paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.model_Name", "modelName", sql,paramsMap, "like",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.model_Code", "modelCode", sql,paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.api_Status", "apiStatus", sql,paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.request_Mode", "requestMode",sql, paramsMap, "=",isHql);
		SaafToolUtils.parperParam(queryParamJSON, "baseApiManagement.url_address", "urlAddress", sql,paramsMap, "like",isHql);
	}


	@Override
	public Pagination<BaseApiManagementLookupValues_HI_RO> findBaseApiManagementPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sb = new StringBuffer(BaseApiManagementLookupValues_HI_RO.QUERY_SQL_1);
		Map<String,Object> queryParamMap = new HashMap<String,Object>();
		changeQuerySql(queryParamJSON,queryParamMap,sb,false);
		changeQuerySort(queryParamJSON,sb," baseApiManagement.creation_date desc",false);
		Pagination<BaseApiManagementLookupValues_HI_RO> findList = baseApiManagementLookupValusDAO_HI_RO.findPagination(sb.toString(),SaafToolUtils.getSqlCountString(sb),queryParamMap,pageIndex,pageRows);

		return findList;
	}
}
