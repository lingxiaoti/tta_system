package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BasePositionEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionOrg_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BasePositionPerson_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * 接口：对职位表Base_Position进行CRUD操作
 * 
 * @author ZhangJun
 * @creteTime 2017-12-11
 */
public interface IBasePosition extends IBaseCommon<BasePositionEntity_HI> {

	/**
	 * 询人员、职位、部门关系<用于登录获取人员的职位和部门信息>
	 * @param personId
	 * @return
	 */
	List<BasePositionPerson_HI_RO> findPersonPositionOrgRelationByPersonId(Integer personId);

	/**
	 * 查询员工职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param personId
	 *            人员Id
	 * @return 员工职位列表 <br>
	 *         [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }]
	 */
	List<BasePositionPerson_HI_RO> findBasePositionsByPersonId(Integer personId);

	/**
	 * 查询部门下设置的职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param orgId
	 *            组织机构Id
	 * @return 部门下设置的职位列表 <br>
	 *         [{<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         orgName:组织名称
	 *         }]
	 */
	Pagination<BasePositionOrg_HI_RO> findBasePositionsByOrgId(Integer orgId, Integer pageIndex, Integer pageRows);

	/**
	 * 关联base_person_organization分页查询职位
	 * 
	 * @author ZhangJun
	 * @creteTime 2017-12-11
	 * @param queryParamJSON
	 *            查询参数<br>
	 *            {<br>
	 *            orgId:组织Id,<br>
	 *            positionName:职位名称,<br>
	 *            sourceSystemId:源系统Id,<br>
	 *            jobId:职务Id,<br>
	 *            enabled:是否启用,<br>
	 *            startDate:生效时间,<br>
	 *            endDate:失效时间<br>
	 *            }<br>
	 * @param pageIndex
	 *            页码
	 * @param pageRows
	 *            每页查询记录数
	 * @return 根据条件查询的职位列表 <br>
	 *         { <br>
	 *         count: 总记录数,<br>
	 *         curIndex: 当前页索引,<br>
	 *         data: [{<br>
	 *         personId:人员Id,<br>
	 *         creationDate: 创建时间<br>
	 *         enabled: 是否启用,<br>
	 *         endDate: 失效时间,<br>
	 *         lastUpdateDate: 更新时间,<br>
	 *         orgId: 组织Id,<br>
	 *         positionId: 职位Id,<br>
	 *         positionName: 职位名称,<br>
	 *         sourceSystemId: 源系统Id,<br>
	 *         startDate: 生效时间,<br>
	 *         versionNum: 版本号<br>
	 *         }],<br>
	 *         firstIndex: 首页索引,<br>
	 *         lastIndex: 尾页索引,<br>
	 *         nextIndex: 下一页索引,<br>
	 *         pageSize: 每页记录数,<br>
	 *         pagesCount: 总页数,<br>
	 *         preIndex: 上一页索引<br>
	 *         }
	 */
	Pagination<BasePositionPerson_HI_RO> findBasePositionsJoinPersonOrg(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
