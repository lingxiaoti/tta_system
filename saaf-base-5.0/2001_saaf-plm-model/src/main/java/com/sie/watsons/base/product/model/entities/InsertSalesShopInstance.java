package com.sie.watsons.base.product.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;

public class InsertSalesShopInstance implements Runnable {

	public List<VmiShopEntity_HI_RO2> datalist; // 店铺清单
	public JSONObject params;
	public SessionFactory sessionFactory;
	public Session session;
	public PlmProductSalesPropertiesEntity_HI sales;

	public InsertSalesShopInstance(List<VmiShopEntity_HI_RO2> datajson,
			JSONObject params, SessionFactory sessionFactory,
			PlmProductSalesPropertiesEntity_HI sales) {
		this.datalist = datajson;
		this.params = params;
		this.sessionFactory = sessionFactory;
		this.sales = sales;
	}

	@Override
	public void run() {
		List<PlmProductSaleshopEntity_HI> ldata = new ArrayList<PlmProductSaleshopEntity_HI>();
		Integer productHeadId = params.getInteger("productHeadId");
		String plmcode = params.getString("plmCode");
		Integer userId = params.getInteger("uid");
		String productName = params.getString("productName");

		for (VmiShopEntity_HI_RO2 j : datalist) {
			String loc = j.getVsCode();
			PlmProductSaleshopEntity_HI console = new PlmProductSaleshopEntity_HI();

			console.setShopLoc(loc);
			console.setPlmCode(plmcode);
			console.setSalesId(sales.getSalesId());
			console.setProductHeadId(productHeadId);
			console.setProductName(productName);
			console.setCreatedBy(userId);
			console.setOperatorUserId(userId);
			console.setCreationDate(new Date());
			console.setIsSales(sales.getSalesProperties());

			console.setLastUpdateDate(new Date());
			console.setVersionNum(0);
			console.setLastUpdatedBy(userId);
			console.setLastUpdateLogin(userId);
			ldata.add(console);

		}
		try {
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			for (int d = 0; d < ldata.size(); d++) {
				session.save(ldata.get(d));
				if (d % 1000 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback(); // 出错将回滚事物
		}

	}

}
