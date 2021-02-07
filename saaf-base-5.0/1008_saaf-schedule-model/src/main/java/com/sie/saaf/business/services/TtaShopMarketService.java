package com.sie.saaf.business.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.business.model.entities.TtaShopMarketEntity_HI;
import com.sie.saaf.business.model.inter.ITtaShopMarket;

import com.sie.saaf.common.util.SpringBeanUtil;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ttaShopMarketService")
public class TtaShopMarketService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaShopMarketService.class);

	@Autowired
	private ITtaShopMarket ttaShopMarketServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaShopMarketServer;
	}

	/**
	 * 批量保存店铺对应市场数据
	 */
	@RequestMapping("/saveBatchShopMarket")
	public static String saveBatchShopMarket(){
		try {
			ITtaShopMarket ttaShopMarketServer = (ITtaShopMarket)SpringBeanUtil.getBean("ttaShopMarketServer");
			List<TtaShopMarketEntity_HI> findlist = ttaShopMarketServer.findList(new JSONObject());
			System.out.println("查询的条数: " + findlist.size());
			for (TtaShopMarketEntity_HI ttaShopMarketEntity_hi : findlist) {
				System.out.println("市场名字:" + ttaShopMarketEntity_hi.getMarketName());
			}

			List<TtaShopMarketEntity_HI> list = new ArrayList<TtaShopMarketEntity_HI>();
			//TtaShopMarketEntity_HI ttaShopMarketEntity_hi = findlist.get(0);
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
			System.out.println(list.toString());

			List<TtaShopMarketEntity_HI> ttaShopMarketEntity_his = ttaShopMarketServer.saveBatchShopMarket(list);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, ttaShopMarketEntity_his).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}