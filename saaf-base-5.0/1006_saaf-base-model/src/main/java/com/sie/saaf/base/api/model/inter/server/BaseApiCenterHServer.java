package com.sie.saaf.base.api.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiCenterHEntity_HI;
import com.sie.saaf.base.api.model.entities.BaseApiCenterLEntity_HI;
import com.sie.saaf.base.api.model.entities.BaseApiManagementEntity_HI;
import com.sie.saaf.base.api.model.inter.IBaseApiCenterH;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
 * API 项目/中心管理
 * 
 * @author ZhangJun 
 * @creteTime 2017-12-12
 */
@Component("baseApiCenterHServer")
public class BaseApiCenterHServer extends BaseCommonServer<BaseApiCenterHEntity_HI> implements IBaseApiCenterH {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiCenterHServer.class);
	@Autowired
	private ViewObject<BaseApiCenterHEntity_HI> baseApiCenterHDAO_HI;

	@Autowired
	private ViewObject<BaseApiCenterLEntity_HI> baseApiCenterLDAO_HI;

	@Autowired
	private ViewObject<BaseApiManagementEntity_HI> baseApiManagementDAO_HI;

	public BaseApiCenterHServer() {
		super();
	}

	/**
	 * 保存一条数据
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            参数 <br>
	 *            {<br>
	 *            apihId:主键（更新时必填）<br>
	 *            centerName:名称<br>
	 *            centerCode:编码<br>
	 *            operatorUserId:操作者<br>
	 *            versionNum:版本号（更新时必填）<br>
	 *            }
	 * @return BaseApiCenterHEntity_HI对象
	 */
	@Override
	public BaseApiCenterHEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询数据
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 * @param queryParamJSON
	 *            查询条件<br>
	 *            {<br>
	 *            centerName:名称<br>
	 *            centerCode:编码<br>
	 *            }
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 项目/中心分页列表<br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         apihId: 主键,<br>
	 *         centerCode: 编码,<br>
	 *         centerName: 名称,<br>
	 *         creationDate: 创建时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         versionNum: 版本号,<br>
	 *         createdBy:创建人,<br>
	 *         lastUpdatedBy:更新人<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 *
	 */
	@Override
	public Pagination<BaseApiCenterHEntity_HI> findPagination(JSONObject queryParamJSON,Integer pageIndex,Integer pageRows) {
		return super.findPagination(queryParamJSON,pageIndex,pageRows);
	}

	/**
	 * 根据项目中心编码获取一条数据
	 * @param centerCode 项目中心编码
	 * @return BaseApiCenterHEntity_HI对象集合
	 * @author ZhangJun
	 * @creteTime 2017-12-118
	 */
	@Override
	public List<BaseApiCenterHEntity_HI> findByCenterCode(String centerCode) {
		return baseApiCenterHDAO_HI.findByProperty("centerCode",centerCode);
	}

	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql,boolean isHql) {
		SaafToolUtils.parperHbmParam(BaseApiCenterHEntity_HI.class,queryParamJSON, "centerName", "centerName", sql, paramsMap, "like");
		SaafToolUtils.parperHbmParam(BaseApiCenterHEntity_HI.class,queryParamJSON, "centerCode", "centerCode", sql, paramsMap, "=");
	}

	@Override
	public void delete(Integer id){
		if (id==null)
			return;
		BaseApiCenterHEntity_HI header=baseApiCenterHDAO_HI.getById(id);
		if (header==null)
			return;
		List<BaseApiCenterLEntity_HI> lineList= baseApiCenterLDAO_HI.findByProperty("centerCode",header.getCenterCode());
		for (BaseApiCenterLEntity_HI item:lineList){
			List apiList= baseApiManagementDAO_HI.findByProperty("modelCode",item.getModelCode());
			baseApiManagementDAO_HI.delete(apiList);
		}
		baseApiCenterLDAO_HI.delete(lineList);
		baseApiCenterHDAO_HI.delete(header);
	}

}
