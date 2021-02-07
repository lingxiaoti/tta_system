package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResourceEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseMenuResource_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRoleResource_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * 接口：对资源表base_resource进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/14
 */
public interface IBaseResource extends IBaseCommon<BaseResourceEntity_HI> {

	/**
	 * 校验按钮名称或编码
	 * @author ZhangJun
	 * @createTime 2018/3/16
	 * @description 校验按钮名称或编码
	 */
	JSONObject validCodeOrName(JSONObject queryParamJSON);

	/**
	 * 根据菜单查询资源
	 *
	 * @param menuId 菜单Id
	 *
	 * @return 资源列表<br>
	 * [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/14
	 */
	List<BaseResourceEntity_HI> findBaseResourceByMenuId(Integer menuId);

	/**
	 * 根据角色Id查询角色所分配的资源
	 *
	 * @param roleId 角色Id
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 *
	 * @return 分页资源列表<br>
	 * { <br>
	 * count: 总记录数,<br>
	 * curIndex: 当前页索引,<br>
	 * data: [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
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
	Pagination<BaseRoleResource_HI_RO> findBaseResourceByRoleId(Integer roleId, Integer pageIndex, Integer pageRows);

	/**
	 * 查询资源分页列表
	 * @author ZhangJun
	 * @createTime 2018/1/19 09:21
	 * @description 查询资源分页列表
	 */
	Pagination<BaseMenuResource_HI_RO> findResourcePagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 根据菜单和职责id获取资源
	 * @param menuId 菜单Id
	 * @param respId 职责Id
	 *
	 * @return 资源列表<br>
	 * [{<br>
	 * resourceId:资源标识<br>
	 * menuId:菜单Id，节点标识 对应到功能<br>
	 * resourceCode:资源编号（与功能按钮编码对应）<br>
	 * buttonUrl:按钮对应的执行方法地址<br>
	 * orderNo:排序号<br>
	 * resourceType:类型标识(按钮、方法、字段名、代码片段)<br>
	 * resourceName:资源名称<br>
	 * resourceDesc:资源描述<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }]<br>
	 * @author ZhangJun
	 * @createTime 2018/2/2
	 * @description 根据菜单和职责id获取资源
	 */
	List<BaseRoleResource_HI_RO> findBaseResourceByRespMenuId(Integer menuId, Integer respId);
}
