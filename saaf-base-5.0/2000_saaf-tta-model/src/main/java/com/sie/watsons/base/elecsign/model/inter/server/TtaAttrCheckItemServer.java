package com.sie.watsons.base.elecsign.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.elecsign.model.entities.TtaAttrCheckItemEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.elecsign.model.inter.ITtaAttrCheckItem;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaAttrCheckItemServer")
public class TtaAttrCheckItemServer extends BaseCommonServer<TtaAttrCheckItemEntity_HI> implements ITtaAttrCheckItem{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAttrCheckItemServer.class);

	@Autowired
	private ViewObject<TtaAttrCheckItemEntity_HI> ttaAttrCheckItemDAO_HI;

	public TtaAttrCheckItemServer() {
		super();
	}

}
