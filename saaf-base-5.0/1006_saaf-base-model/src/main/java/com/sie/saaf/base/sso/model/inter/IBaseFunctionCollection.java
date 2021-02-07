package com.sie.saaf.base.sso.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.sso.model.entities.BaseFunctionCollectionEntity_HI;
import com.sie.saaf.base.sso.model.entities.readonly.BaseFunctionCollectionEntity_HI_RO;
import com.sie.saaf.base.user.model.entities.readonly.BaseMenuRoleEntity_HI_RO;
import com.sie.saaf.common.bean.UserSessionBean;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

public interface IBaseFunctionCollection {

	List<BaseFunctionCollectionEntity_HI> findBaseFunctionCollectionInfo(JSONObject queryParamJSON);

	Object saveBaseFunctionCollectionInfo(JSONObject queryParamJSON);

	Pagination<BaseFunctionCollectionEntity_HI_RO> findFunctionCollections(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

	List<BaseFunctionCollectionEntity_HI_RO> findUserCollection(JSONObject queryParamJSON);

	List<BaseMenuRoleEntity_HI_RO> findPopularMenu(UserSessionBean userSessionBean, String roles);

	BaseFunctionCollectionEntity_HI saveOrUpdate(JSONObject paramsJSON, int userId) throws Exception;

	void delete(String[] ids);

	List<BaseFunctionCollectionEntity_HI_RO> findInCollection(JSONObject queryParamJSON);

	List<BaseFunctionCollectionEntity_HI> saveInCollection(String menuIds[], Integer userId, Integer respId, String systemCode);

	void  deleteInCollection(Integer functionCollectionId);
}
