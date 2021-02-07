package com.sie.saaf.business.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

import com.sie.saaf.common.util.SaafToolUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.saaf.business.model.entities.TtaShopMarketEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.business.model.inter.ITtaShopMarket;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaShopMarketServer")
public class TtaShopMarketServer extends BaseCommonServer<TtaShopMarketEntity_HI> implements ITtaShopMarket{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketServer.class);

	@Autowired
	private ViewObject<TtaShopMarketEntity_HI> ttaShopMarketDAO_HI;

	public TtaShopMarketServer() {
		super();
	}

	@Override
	public List<TtaShopMarketEntity_HI> saveBatchShopMarket(Collection<? extends TtaShopMarketEntity_HI> collection) throws Exception{
		List<TtaShopMarketEntity_HI> list = new ArrayList<TtaShopMarketEntity_HI>();
		//TtaShopMarketEntity_HI ttaShopMarketEntity_hi = findlist.get(0);
		//TtaShopMarketEntity_HI ttaShopMarketEntity_hi = SaafToolUtils.setEntity(TtaShopMarketEntity_HI.class, new JSONObject(),
		//		ttaShopMarketDAO_HI, 1);


		TtaShopMarketEntity_HI ttaShopMarketEntity_hi = new TtaShopMarketEntity_HI();
		ttaShopMarketEntity_hi.setCreatedBy(1);
		ttaShopMarketEntity_hi.setMarketName("广州夏园屈臣氏");
		ttaShopMarketEntity_hi.setAreaName("江南");
		ttaShopMarketEntity_hi.setCityName("广东广州");
		ttaShopMarketEntity_hi.setWarehouseCode("广州黄埔仓");
		ttaShopMarketEntity_hi.setWarehouseName("广州黄埔仓");
		ttaShopMarketEntity_hi.setShopName("广州夏园屈臣氏店铺");
		ttaShopMarketEntity_hi.setOpenShop(new Date());
		ttaShopMarketEntity_hi.setChannel(String.valueOf(0));
		ttaShopMarketEntity_hi.setShopLevel(String.valueOf(1));
		ttaShopMarketEntity_hi.setProvinceName("广东");

		list.add(ttaShopMarketEntity_hi);

		ttaShopMarketDAO_HI.saveOrUpdateAll(list);
		return list;
	}
}
