package com.sie.watsons.report.model.inter.server;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.watsons.report.model.entities.TtaPrintRecordEntity_HI;
import com.sie.watsons.report.model.inter.ITtaPrintRecord;
import com.yhg.hibernate.core.dao.ViewObject;

@Component("ttaPrintRecordServer")
public class TtaPrintRecordServer extends BaseCommonServer<TtaPrintRecordEntity_HI> implements ITtaPrintRecord{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPrintRecordServer.class);

	@Autowired
	private ViewObject<TtaPrintRecordEntity_HI> ttaPrintRecordDAO_HI;

	public TtaPrintRecordServer() {
		super();
	}

	@Override
	public int saveOrUpdatePrintCount(TtaPrintRecordEntity_HI entity) {
		List<TtaPrintRecordEntity_HI> entityList = ttaPrintRecordDAO_HI.findByProperty(new JSONObject().fluentPut("printType", entity.getPrintType()));
		if (entityList == null || entityList.isEmpty()) {
			entity.setPrintCount(0);
			ttaPrintRecordDAO_HI.save(entity);
		} else {
			entity = entityList.get(0);
			entity.setPrintCount(entity.getPrintCount() + 1);
			entity.setLastUpdateDate(new Date());
			ttaPrintRecordDAO_HI.saveOrUpdate(entity);
		}
		return entity.getPrintCount();
	}
}
