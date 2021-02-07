package com.sie.watsons.base.poc.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.poc.model.entities.ItemMasterEntity_HI;
import com.sie.watsons.base.poc.model.entities.StoreItem;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromGroupsEntity_HI_RO;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromHeadEntity_HI_RO;
import com.sie.watsons.base.poc.model.entities.readonly.XxPromStoreEntity_HI_RO;

import java.util.List;
import java.util.concurrent.Callable;

public interface IItemMaster extends IBaseCommon<ItemMasterEntity_HI> {

	List<ItemMasterEntity_HI> findItemMasterInfo(JSONObject queryParamJSON);

	Object saveItemMasterInfo(JSONObject queryParamJSON);

	void addRedis() throws Exception;

	void getStoreRedis();

	void delRedis();

    Object getStoreMap(JSONObject paramJSON);

	Object callChecking(JSONObject paramJSON) throws Exception;

	Object startCheckingMultiThreads(JSONObject paramJSON);

	List<XxPromGroupsEntity_HI_RO> getGroups(String pid);

	List<XxPromStoreEntity_HI_RO> getStore(String xxPromId);

	XxPromHeadEntity_HI_RO getHead(JSONObject paramJSON);

	List<StoreItem> callChecking(JSONObject paramJSON, XxPromHeadEntity_HI_RO head, List<XxPromGroupsEntity_HI_RO> goodslist, XxPromStoreEntity_HI_RO s) throws Exception;
}
