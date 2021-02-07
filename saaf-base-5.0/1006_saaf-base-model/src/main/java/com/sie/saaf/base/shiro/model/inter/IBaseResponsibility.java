package com.sie.saaf.base.shiro.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.shiro.model.entities.BaseResponsibilityEntity_HI;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseRespRoleProfile_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityRole_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseResponsibilityUserEntity_HI_RO;
import com.sie.saaf.base.shiro.model.entities.readonly.BaseUserResponsibility_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

/**
 * 接口：对职责表base_responsibility进行CRUD操作
 *
 * @author ZhangJun
 * @creteTime 2017/12/13
 */
public interface IBaseResponsibility extends IBaseCommon<BaseResponsibilityEntity_HI> {

	/**
	 * 保存一条记录
	 *
	 * @param queryParamJSON 保存的数据参数<br>
	 *     {<br>
	 *         responsibilityId:职责Id,<br>
	 *         responsibilityCode:职责编号,<br>
	 *         responsibilityName:职责名称,<br>
	 *         responsibilityDesc:职责描述,<br>
	 *         startDateActive:生效时间,<br>
	 *         endDateActive:失效时间,<br>
	 *         versionNum:版本号,<br>
	 *         operatorUserId:操作者Id,<br>
	 *         roleIds:[1,2,3,4],//角色Id<br>
	 *         profiles:[{<br>
	 *             profileValueId:profile行表Id,<br>
	 *             profileId:profile主键,<br>
	 *             profileValue:profile值<br>
	 *             versionNum:版本号<br>
	 *         }]<br>
	 *     }
	 *
	 * @return {@link BaseRespRoleProfile_HI_RO}
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	BaseRespRoleProfile_HI_RO saveRespRoleProfile(JSONObject queryParamJSON) throws Exception;

	/**
	 * 根据角色Id查询分配的职责
	 *
	 * @param roleId 角色Id
	 *
	 * @return 职责与角色关系列表<br>
	 * [{<br>
	 * responsibilityId:职责标识,<br>
	 * responsibilityCode:职责编号,<br>
	 * responsibilityName:职责名称,<br>
	 * responsibilityDesc:职责描述,<br>
	 * startDateActive:生效时间,<br>
	 * endDateActive:失效时间,<br>
	 * creationDate:创建日期,<br>
	 * createdBy:创建人,<br>
	 * lastUpdatedBy:更新人,<br>
	 * lastUpdateDate:更新日期,<br>
	 * versionNum:版本号,<br>
	 * roleId:角色Id,<br>
	 * roleName:角色名称,<br>
	 * roleCode:角色编号,<br>
	 * roleDesc:角色描述,<br>
	 * systemCode:系统编码,<br>
	 * roleStartDateActive:生效时间,<br>
	 * roleEndDateActive:失效时间,<br>
	 * roleVersionNum:版本号,<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	List<BaseResponsibilityRole_HI_RO> findResponsibilityByRoleId(Integer roleId);

	public Pagination<BaseResponsibilityUserEntity_HI_RO> findCurrentResponsibilityPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	/**
	 * 根据用户Id查询职责
	 *
	 * @param userId 用户Id
	 *
	 * @return 用户与职责关系列表<br>
	 * [{<br>
	 * responsibilityId:职责标识<br>
	 * responsibilityCode:职责编号<br>
	 * responsibilityName:职责名称<br>
	 * responsibilityDesc:职责描述<br>
	 * startDateActive:生效时间<br>
	 * endDateActive:失效时间<br>
	 * creationDate:创建日期<br>
	 * createdBy:创建人<br>
	 * lastUpdatedBy:更新人<br>
	 * lastUpdateDate:更新日期<br>
	 * versionNum:版本号<br>
	 * }]
	 *
	 * @author ZhangJun
	 * @creteTime 2017/12/13
	 */
	List<BaseUserResponsibility_HI_RO> findResponsibilityByUserId(Integer userId);

	/**
	 * 根据Id查询职责，职责角色，职责profile
	 * @author ZhangJun
	 * @createTime 2018/1/12 15:43
	 * @description 根据Id查询职责
	 */
	JSONObject findRespRoleProfileById(Integer id);

	/**
	 *
	 * @author YangXiaowei
	 * @creteTime 2018/4/14
	 * @description  职责新增判断是否重复
	 */
	void checkOut(JSONObject queryParamJSON);

	@Override
	Pagination<BaseResponsibilityEntity_HI> findPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);
}
