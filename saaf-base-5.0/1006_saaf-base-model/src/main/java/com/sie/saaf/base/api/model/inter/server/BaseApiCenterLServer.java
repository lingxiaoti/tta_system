package com.sie.saaf.base.api.model.inter.server;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.api.model.entities.BaseApiCenterLEntity_HI;
import com.sie.saaf.base.api.model.inter.IBaseApiCenterL;
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
 * API 项目模块/中心模块管理
 *
 * @author ZhangJun
 * @creteTime 2017-12-12
 */
@Component("baseApiCenterLServer")
public class BaseApiCenterLServer extends BaseCommonServer<BaseApiCenterLEntity_HI> implements IBaseApiCenterL {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseApiCenterLServer.class);
	@Autowired
	private ViewObject<BaseApiCenterLEntity_HI> baseApiCenterLDAO_HI;

	public BaseApiCenterLServer() {
		super();
	}

	/**
	 * 保存一条记录
	 *
	 * @param queryParamJSON 参数 <br>
	 * {<br>
	 * apilId:主键（更新时必填）<br>
	 * centerCode:项目/中心编码<br>
	 * modelName:模块名称<br>
	 * modelCode:模块编码<br>
	 * operatorUserId:操作者<br>
	 * versionNum:版本号（更新时必填）<br>
	 * }
	 *
	 * @return BaseApiCenterLEntity_HI对象
	 *
	 * @author ZhangJun
	 * @creteTime 2017-12-12
	 */
	@Override
	public BaseApiCenterLEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询
	 *
	 * @param queryParamJSON 查询条件<br>
	 * {<br>
	 * centerCode:项目/中心编码<br>
	 * modelName:模块名称<br>
	 * modelCode:模块编码<br>
	 * }
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 项目/中心模块分页列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * apihId: 主键,<br>
	 * centerCode: 项目/中心编码,<br>
	 * modelName: 名称,<br>
	 * modelCode: 编码,<br>
	 * creationDate: 创建时间,<br>
	 * lastUpdateDate: 更新时间,<br>
	 * versionNum: 版本号,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人<br>
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
	 * @creteTime 2017-12-12
	 */
	@Override
	public Pagination<BaseApiCenterLEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 根据模块编码查询记录
	 *
	 * @param modelCode 模块编码
	 *
	 * @return BaseApiCenterLEntity_HI
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/18
	 */
	@Override
	public List<BaseApiCenterLEntity_HI> findByModelCode(String modelCode) {
		return baseApiCenterLDAO_HI.findByProperty("modelCode", modelCode);

	}

	@Override
	protected void changeQuerySql(JSONObject queryParamJSON, Map<String, Object> paramsMap, StringBuffer sql, boolean isHql) {
		SaafToolUtils.parperHbmParam(BaseApiCenterLEntity_HI.class, queryParamJSON, "centerCode", "centerCode", sql,
				paramsMap, "=");
		SaafToolUtils.parperHbmParam(BaseApiCenterLEntity_HI.class, queryParamJSON, "modelName", "modelName", sql,
				paramsMap, "like");
		SaafToolUtils.parperHbmParam(BaseApiCenterLEntity_HI.class, queryParamJSON, "modelCode", "modelCode", sql,
				paramsMap, "=");
	}
}
