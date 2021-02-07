package com.sie.watsons.base.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductStoreEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.product.model.inter.IPlmProductStore;
import com.yhg.base.utils.SToolUtils;

@RestController
@RequestMapping("/plmProductStoreService")
public class PlmProductStoreService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductStoreService.class);
	@Autowired
	private IPlmProductHead plmProductHeadServer;

	@Autowired
	private IPlmProductStore plmProductStoreServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductStoreServer;
	}

	@RequestMapping(method = RequestMethod.POST, value = "DeleteStoreByStoreId")
	public String deleteByid(@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			Integer id = param.getInteger("storeId");
			PlmProductStoreEntity_HI store = plmProductStoreServer.getById(id);
			Integer headId = store.getProductHeadId();
			PlmProductHeadEntity_HI headobj = plmProductHeadServer
					.getById(headId);
			if (headobj != null) {
				String deleteSql = " delete from PlmProductConsaleinfoEntity_HI where request_id='"
						+ headobj.getPlmCode() + "' and store_id=" + id;
				plmProductHeadServer.execute(deleteSql);
			}
			plmProductStoreServer.deleteById(id);

			//

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

}