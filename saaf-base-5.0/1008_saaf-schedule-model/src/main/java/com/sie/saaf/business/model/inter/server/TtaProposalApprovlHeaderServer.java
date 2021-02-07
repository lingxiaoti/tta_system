package com.sie.saaf.business.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.business.model.entities.readonly.TtaProposalApprovlHeaderEntity_HI_RO;
import com.sie.saaf.schedule.utils.ResourceUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.saaf.business.model.entities.TtaProposalApprovlHeaderEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.saaf.business.model.inter.ITtaProposalApprovlHeader;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;

@Component("ttaProposalApprovlHeaderServer")
public class TtaProposalApprovlHeaderServer extends BaseCommonServer<TtaProposalApprovlHeaderEntity_HI> implements ITtaProposalApprovlHeader{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalApprovlHeaderServer.class);

	@Autowired
	private ViewObject<TtaProposalApprovlHeaderEntity_HI> ttaProposalApprovlHeaderDAO_HI;
	@Autowired
	private BaseViewObject<TtaProposalApprovlHeaderEntity_HI_RO> ttaProposalApprovlHeaderDAO_HI_RO;

	public TtaProposalApprovlHeaderServer() {
		super();
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL = new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findSubmitterList(JSONObject jsonObject) {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());
		StringBuffer querySQL = new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getSubmitterQuery(newDate +" 16:00:00", oldDate + " 16:00:00"));
		queryMap.put("way",jsonObject.getString("way"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findBICList() {
		Map<String, Object> queryMap = new HashMap<>();
		StringBuffer querySQL = new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.BIC_QUERY);
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public void saveOrUpdateAll(List<TtaProposalApprovlHeaderEntity_HI> personList) {
		ttaProposalApprovlHeaderDAO_HI.saveOrUpdateAll(personList);
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findOSDList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getOsdQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findDmList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getDmQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findCwList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getCwQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findNppList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getNppQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findHwbList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getHwbQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findFgList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getFgQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findSoleStandarList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getSoleStandarQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findNonSoleStandarList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getNonSoleStandarQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findStandarSupplementList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getSupplementStandarQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findNonStandarSupplementList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getSupplementNonStandarQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContractList() {
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());//结束时间
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());//开始时间
		System.out.println("oldDate"+oldDate);
		//oldDate = "2020-02-01";
		oldDate = ResourceUtils.getBmpStartTime();
		StringBuffer querySQL =
				new StringBuffer(TtaProposalApprovlHeaderEntity_HI_RO.getElecConQuery(newDate +" 16:00:00",oldDate +" 16:00:00"));
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	private List<TtaProposalApprovlHeaderEntity_HI_RO> getCommonSqlExcute(String sql){
		Map<String, Object> queryMap = new HashMap<>();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(new Date());
		String newDate= sdf.format(calendar.getTime());
		calendar.add(calendar.DATE,-1);
		String oldDate= sdf.format(calendar.getTime());
		StringBuffer querySQL = new StringBuffer(sql);
		queryMap.put("newDateString",newDate +" 16:00:00");
		queryMap.put("oldDateString",oldDate +" 16:00:00");
		//queryMap.put("oldDateString","2020-11-02 16:00:00");
		List<TtaProposalApprovlHeaderEntity_HI_RO> dataList = ttaProposalApprovlHeaderDAO_HI_RO.findList(querySQL,queryMap);
		return dataList;
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findOsdSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getOsdSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findDMSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getDmSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findCwSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getCwSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findNppSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getNppSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findHwbSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getHwbSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findFgSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getFgSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findSoleStandarSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getSoleStandarSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findNonSoleStandarSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getNonsoleStandarSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findSaStandarSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getSupplementStandarSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findSaNonStandarSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getSupplementNonStandarSubmitterQuery());
	}

	@Override
	public List<TtaProposalApprovlHeaderEntity_HI_RO> findElecContSubmitterList() {
		return getCommonSqlExcute(TtaProposalApprovlHeaderEntity_HI_RO.getElecContSubmitterQuery());
	}
}
