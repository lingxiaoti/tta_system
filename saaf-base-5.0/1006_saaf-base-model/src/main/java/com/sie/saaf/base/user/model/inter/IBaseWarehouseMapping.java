package com.sie.saaf.base.user.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseWarehouseMappingEntity_HI;
import com.sie.saaf.base.user.model.entities.readonly.BaseDelaer_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseWarehouseMapping_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseWarehouseMapping extends IBaseCommon<BaseWarehouseMappingEntity_HI> {

	Pagination<BaseWarehouseMapping_HI_RO> findROPagination(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<BaseWarehouseMappingEntity_HI> findBaseWarehouseMappingEntityInfo(JSONObject queryParamJSON);

	/**
	 * 根据操作字库编码获取所属经销商下属子库
	 * @param queryParamJSON {
	 *     warehouseCode:子库编码
	 * }
	 * @return {@link List<BaseWarehouseMappingEntity_HI>}
	 * @author ZhangJun
	 * @createTime 2018/1/30
	 * @description 根据操作字库编码获取所属经销商下属子库
	 */
	List<BaseWarehouseMappingEntity_HI> findChildrenWarehouseMapping(JSONObject queryParamJSON);

    /**
     * 根据Code查询
     * @param queryParamJSON{
     *                      accountCode     所属经销商编码
     *                      warehouseCode   仓库编码
     *                      accountName     所属经销商名称
     *                      warehouseName   仓库名称
     * }
     * @return
     */
	List<BaseWarehouseMappingEntity_HI> findBaseWarehouseMappingEntityInfoByCode(JSONObject queryParamJSON);

	List<BaseWarehouseMappingEntity_HI> findCacheWarehouse(JSONObject queryParamJSON);

	/**
	 * 查询经销商信息
	 * @author ZhangJun
	 * @createTime 2018/3/6
	 * @description 查询经销商信息
	 */
	List<BaseDelaer_HI_RO> findDelaerList(JSONObject queryParamJSON);

    List<BaseWarehouseMapping_HI_RO> findMainInv(JSONObject paramJSON);

    /**
     * 根据OU组织获取所属组织下的子库
	 * @return ['20198','20198001'] 当前所属OU组织可访问的，有效的子库编码
     * @author ZhangJun
     * @createTime 2018/4/17
     * @description 根据OU组织获取所属组织下的子库，OU组织从当前操作的职责中获取operationOrgId
     */
    List<String> findWarehouseCodeByOrgId(JSONObject paramsJSON);

	/**
	 * 同步子库信息
	 * @author ZhangJun
	 * @createTime 2018/3/14
	 * @description 同步子库信息
	 */
	JSONObject saveSyncBaseWarehouseMapping(JSONObject queryParamJSON);
}
