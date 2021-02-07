package com.sie.watsons.base.ttaImport.model.dao;

import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaSupplierItemStoreEntity_HI;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("ttaSupplierItemStoreDAO_HI")
public class TtaSupplierItemStoreDAO_HI extends BaseCommonDAO_HI<TtaSupplierItemStoreEntity_HI> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierItemStoreDAO_HI.class);

	public TtaSupplierItemStoreDAO_HI() {
		super();
	}

}
