package com.sie.watsons.base.product.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductMedicalinfoEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductMedicalinfo;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("plmProductMedicalinfoServer")
public class PlmProductMedicalinfoServer extends BaseCommonServer<PlmProductMedicalinfoEntity_HI> implements IPlmProductMedicalinfo{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductMedicalinfoServer.class);

	@Autowired
	private ViewObject<PlmProductMedicalinfoEntity_HI> plmProductMedicalinfoDAO_HI;

	public PlmProductMedicalinfoServer() {
		super();
	}

}
