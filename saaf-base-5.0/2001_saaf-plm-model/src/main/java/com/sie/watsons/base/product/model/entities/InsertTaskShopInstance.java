package com.sie.watsons.base.product.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.product.model.entities.readonly.VmiShopEntity_HI_RO2;

public class InsertTaskShopInstance implements Runnable {

	public List<VmiShopEntity_HI_RO2> datalist; // 店铺清单
	public JSONObject params;
	public SessionFactory sessionFactory;
	public Session session;

	public InsertTaskShopInstance(List<VmiShopEntity_HI_RO2> datajson,
			JSONObject params, SessionFactory sessionFactory) {
		this.datalist = datajson;
		this.params = params;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		List<PlmProductSupplierplaceinfoEntity_HI> ldata = new ArrayList<PlmProductSupplierplaceinfoEntity_HI>();
		Integer headId = params.getInteger("productHeadId");
		String suppliercode = params.getString("supplierCode");
		String supplierName = params.getString("supplierName");
		Integer userId = params.getInteger("uid");
		String rmscode = params.getString("rmsId");
		String productName = params.getString("productName");
		for (VmiShopEntity_HI_RO2 j : datalist) {
			String loc = j.getVsCode();
			PlmProductSupplierplaceinfoEntity_HI placeinfo = new PlmProductSupplierplaceinfoEntity_HI();
			placeinfo.setSupplierCode(suppliercode);
			placeinfo.setRmsId(rmscode);
			placeinfo.setStatus("1");
			placeinfo.setSupplierName(supplierName);
			placeinfo.setProductHeadId(String.valueOf(headId));
			placeinfo.setProductName(productName);
			placeinfo.setLocType("S");
			placeinfo.setLocation(loc);
			placeinfo.setOperatorUserId(userId);
			placeinfo.setCreatedBy(userId);

			placeinfo.setCreationDate(new Date());
			placeinfo.setLastUpdateDate(new Date());
			placeinfo.setVersionNum(0);
			placeinfo.setLastUpdatedBy(userId);
			placeinfo.setLastUpdateLogin(userId);
			ldata.add(placeinfo);

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
