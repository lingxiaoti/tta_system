package com.sie.watsons.base.product.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductModifyCheckEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.product.model.inter.IPlmProductModifyCheck;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.util.ObjectUtils;

@Component("plmProductModifyCheckServer")
public class PlmProductModifyCheckServer extends BaseCommonServer<PlmProductModifyCheckEntity_HI> implements IPlmProductModifyCheck{
	private static final Logger LOGGER = LoggerFactory.getLogger(PlmProductModifyCheckServer.class);

	@Autowired
	private ViewObject<PlmProductModifyCheckEntity_HI> plmProductModifyCheckDAO_HI;

	public PlmProductModifyCheckServer() {
		super();
	}

    @Override
    public List<PlmProductModifyCheckEntity_HI>  findProductModifyCheckByEcoId(JSONObject jsonObject) throws Exception {
		Map<String,Object> findParam = new HashMap<>();
		Integer ecoId = jsonObject.getInteger("ecoId");
		if(ObjectUtils.isEmpty(ecoId)){
			throw new Exception("ecoId 不能为空！");
		}
		findParam.put("ecoId",jsonObject.getInteger("ecoId"));
		List<PlmProductModifyCheckEntity_HI> modifyCheckEntitys = plmProductModifyCheckDAO_HI.findByProperty(findParam);

        return modifyCheckEntitys;
    }
}
