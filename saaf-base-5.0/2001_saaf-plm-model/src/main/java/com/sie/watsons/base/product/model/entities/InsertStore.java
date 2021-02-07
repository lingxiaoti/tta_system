package com.sie.watsons.base.product.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;

public class InsertStore implements Runnable {

	public List<JSONObject> datalist; // 店铺清单
	public JSONObject params;
	public SessionFactory sessionFactory;
	public Session session;
	public PlmProductStoreEntity_HI storeobj;

	public InsertStore(List<JSONObject> datalist, JSONObject params,
			SessionFactory sessionFactory, PlmProductStoreEntity_HI storeobj) {
		this.datalist = datalist;
		this.params = params;
		this.sessionFactory = sessionFactory;
		this.storeobj = storeobj;
	}

	@Override
	public void run() {
		List<PlmProductConsaleinfoEntity_HI> ldata = new ArrayList<PlmProductConsaleinfoEntity_HI>();
		String plmcode = params.getString("plmCode");
		String purchaseType = params.getString("purchaseType");
		Integer userId = params.getInteger("uid");
		for (JSONObject j : datalist) {
			String loc = j.getString("loc");
			PlmProductConsaleinfoEntity_HI console = new PlmProductConsaleinfoEntity_HI();
			console.setLoc(loc);
			console.setRequestId(plmcode);
			console.setStartDate(storeobj.getStartDate());
			console.setEndDate(storeobj.getEndDate());
			console.setSupplier(storeobj.getSupplierId());
			console.setStoreId(storeobj.getStoreId());
			if (purchaseType.equals("2")) // cvw
			{
				console.setCurrencyCode("CNY");
				console.setExchangeRate("1");
				console.setAdjBasis(storeobj.getSubstituteType());
				console.setAdjValue(storeobj.getSubstitutePropetion());
			} else {
				console.setLocType(storeobj.getSxWay());
				console.setConsimentType(storeobj.getSubstituteType());
				console.setConsignmentRate(storeobj.getSubstitutePropetion());
			}
			console.setCreatedBy(userId);
			console.setOperatorUserId(userId);
			console.setCreationDate(new Date());
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
