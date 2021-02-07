package com.sie.saaf.base.shiro.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseOrgResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.inter.IBaseOrgResponsibility;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("baseOrgResponsibilityServer")
public class BaseOrgResponsibilityServer extends BaseCommonServer<BaseOrgResponsibilityEntity_HI> implements IBaseOrgResponsibility {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseOrgResponsibilityServer.class);
//	@Autowired
//	private ViewObject<BaseOrgResponsibilityEntity_HI> baseOrgResponsibilityDAO_HI;

	public BaseOrgResponsibilityServer() {
		super();
	}


	/**
	 * 保存职责组织
	 *
	 * @param queryParamJSON 保存参数<br>
	 * {<br>
	 * orgRespId:主键(更新时必填),<br>
	 * responsibilityId:职责主键,<br>
	 * orgId:组织机构Id,<br>
	 * startDateActive:生效时间,<br>
	 * endDateActive:失效时间,<br>
	 * versionNum:版本号(更新时必填),<br>
	 * operatorUserId:操作用户,<br>
	 * }
	 *
	 * @return BaseOrgResponsibilityEntity_HI对象
	 * {<br>
	 *  orgRespId:主键
	 *  responsibilityId:职责标识
	 *  orgId:组织机构Id
	 *  startDateActive:生效时间
	 *  endDateActive:失效时间
	 *  creationDate:创建日期
	 *  createdBy:创建人
	 *  lastUpdatedBy:更新人
	 *  lastUpdateDate:更新日期
	 *  versionNum:版本号
	 *  lastUpdateLogin:
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/10 10:24
	 * @description 保存职责组织
	 */
	@Override
	public BaseOrgResponsibilityEntity_HI saveOrUpdate(JSONObject queryParamJSON) {
		return super.saveOrUpdate(queryParamJSON);
	}

	/**
	 * 分页查询
	 *
	 * @param queryParamJSON 查询参数<br>
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页数据列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 *  orgRespId:主键
	 *  responsibilityId:职责标识
	 *  orgId:组织机构Id
	 *  startDateActive:生效时间
	 *  endDateActive:失效时间
	 *  creationDate:创建日期
	 *  createdBy:创建人
	 *  lastUpdatedBy:更新人
	 *  lastUpdateDate:更新日期
	 *  versionNum:版本号
	 *  lastUpdateLogin:
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
	 * @createTime 2018/1/10 10:28
	 * @description 分页查询
	 */
	@Override
	public Pagination<BaseOrgResponsibilityEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		return super.findPagination(queryParamJSON, pageIndex, pageRows);
	}

	/**
	 * 设置保存默认值
	 * @author ZhangJun
	 * @createTime 2018/1/10 10:32
	 * @description 设置保存默认值
	 */
	@Override
	protected void setEntityDefaultValue(BaseOrgResponsibilityEntity_HI entity) {
		if (entity.getStartDateActive() == null) {
			entity.setStartDateActive(new Date());
		}
	}

	/**
	 * 保存数据
	 * @param queryParamJSON 参数
	 * {<br>
	 *     responsibilityId:职责Id,<br>
	 *     orgIds:[1,2,3,4]组织Id数组<br>
	 * }
	 * @author ZhangJun
	 * @createTime 2018/1/10 11:21
	 * @description 保存数据 
	 */
	@Override
	public void saveOrgResp(JSONObject queryParamJSON) {
		Integer responsibilityId = queryParamJSON.getInteger("responsibilityId");
		JSONArray orgIds = queryParamJSON.getJSONArray("orgIds");
		List<BaseOrgResponsibilityEntity_HI> entities = new ArrayList<>();
		for(int i=0;i<orgIds.size();i++){
			Integer orgId = orgIds.getInteger(i);

			BaseOrgResponsibilityEntity_HI entity = new BaseOrgResponsibilityEntity_HI();
			entity.setResponsibilityId(responsibilityId);
			entity.setOrgId(orgId);
			entity.setStartDateActive(new Date());
			entity.setOperatorUserId(queryParamJSON.getInteger("operatorUserId"));
			entities.add(entity);
		}
		if(!entities.isEmpty()) {
			this.save(entities);
		}
	}
}
