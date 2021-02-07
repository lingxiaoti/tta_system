package com.sie.watsons.base.supplement.model.inter.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import com.sie.saaf.common.model.inter.server.GenerateCodeServer;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.supplement.model.entities.PlmSupLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupShopLogEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupWarehouseEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementHeadEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineFileEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupShopLogEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementHeadEntity_HI_RO;
import com.sie.watsons.base.supplement.model.entities.readonly.PlmSupplementLineEntity_HI_RO;
import com.sie.watsons.base.supplement.model.inter.IPlmSupShop;
import com.sie.watsons.base.supplement.model.inter.IPlmSupWarehouse;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementHead;
import com.sie.watsons.base.supplement.model.inter.IPlmSupplementLineFile;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@Component("plmSupplementHeadServer")
public class PlmSupplementHeadServer extends
		BaseCommonServer<PlmSupplementHeadEntity_HI> implements
		IPlmSupplementHead {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmSupplementHeadServer.class);
	@Autowired
	private ViewObject<PlmSupplementHeadEntity_HI> plmSupplementHeadDAO_HI;
	@Autowired
	private ViewObject<PlmSupplementLineEntity_HI> plmSupplementLineDAO_HI;
	@Autowired
	private BaseViewObject<PlmSupplementLineEntity_HI_RO> plmSupplementLineDAO_HI_RO;
	@Autowired
	private BaseViewObject<PlmSupplementHeadEntity_HI_RO> plmSupplementHeadDAO_HI_RO;
	@Autowired
	private BaseViewObject<PlmSupShopEntity_HI_RO> plmSupShopDAO_HI_RO;
	@Autowired
	private ViewObject<PlmSupWarehouseEntity_HI> plmSupWarehouseDAO_HI;
	@Autowired
	private ViewObject<PlmSupLogEntity_HI> plmSupLogDAO_HI;
	@Autowired
	private ViewObject<PlmSupShopLogEntity_HI> plmSupShopLogDAO_HI;
	@Autowired
	private ViewObject<PlmSupShopEntity_HI> plmSupShopDAO_HI;
	@Autowired
	private PlmSupShopLogServer plmSupShopLogServer;
	@Autowired
	private GenerateCodeServer generateCodeServer;
	@Autowired
	private IPlmSupWarehouse plmSupWarehouse;
	@Autowired
	private IPlmSupShop plmsupshop;
	@Autowired
	private redis.clients.jedis.JedisCluster jedis;
	@Autowired
	private IPlmSupplementLineFile plmSupplementLineFileDao;
	@Autowired
	private ViewObject<PlmSupplementLineFileEntity_HI> plmSupplementLineFileDAO_HI;

	public PlmSupplementHeadServer() {
		super();
	}

	/**
	 * 实现redis keys 模糊查询
	 * 
	 * @author hq
	 * @param pattern
	 * @return
	 */
	private Set<String> redisKeys(String pattern) {
		Set<String> keys = new HashSet<>();
		// 获取所有连接池节点
		Map<String, JedisPool> nodes = jedis.getClusterNodes();
		// 遍历所有连接池，逐个进行模糊查询
		for (String k : nodes.keySet()) {
			JedisPool pool = nodes.get(k);
			// 获取Jedis对象，Jedis对象支持keys模糊查询
			Jedis connection = pool.getResource();
			try {
				keys.addAll(connection.keys(pattern));
			} catch (Exception e) {
			} finally {
				// 一定要关闭连接！
				connection.close();
			}
		}
		return keys;
	}

	/**
	 * 获取申请头表的详情信息
	 */
	@Override
	public List<PlmSupplementHeadEntity_HI> findPlmSupplementHeadInfo(
			JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = SToolUtils
				.fastJsonObj2Map(queryParamJSON);
		List<PlmSupplementHeadEntity_HI> findListResult = plmSupplementHeadDAO_HI
				.findList("from PlmSupplementHeadEntity_HI", queryParamMap);
		return findListResult;
	}

	/**
	 * 保存申请头表的申请信息
	 */
	@Override
	public Object savePlmSupplementHeadInfo(JSONObject queryParamJSON)
			throws Exception {
		PlmSupplementHeadEntity_HI plmSupplementHeadEntity_HI = JSON
				.parseObject(queryParamJSON.toString(),
						PlmSupplementHeadEntity_HI.class);
//		if (verify(plmSupplementHeadEntity_HI)) {
//			throw new Exception("单据不能操作！");
//		}
		PlmSupplementHeadEntity_HI entity;
		Integer id = plmSupplementHeadEntity_HI.getPlmSupplementHeadId();
		if (id != null) {
			entity = this.getById(id);
			if (entity != null) {
				if (!entity.getOrderStatus().equals("0")) {
					throw new Exception("单据不能操作！");
				}
				BeanUtils.copyProperties(plmSupplementHeadEntity_HI, entity);
			} else {
				entity = plmSupplementHeadEntity_HI;
			}
		} else {
			entity = new PlmSupplementHeadEntity_HI();
			BeanUtils.copyProperties(plmSupplementHeadEntity_HI, entity);
		}

		if (entity.getSupCode() == null
				|| "".equals(entity.getSupCode())) {
			entity.setSupCode(getSupCode());
			entity.setCreator(queryParamJSON
					.getString("varUserFullName"));
			entity.setCreationDate(new Date());
		}
		// Object resultData =
		// plmSupplementHeadDAO_HI.save(entity);
		JSONArray lines = queryParamJSON.getJSONArray("lines");
		if (lines != null) {
			validLine(lines);
		}
		JSONArray file = queryParamJSON.getJSONArray("files");
		plmSupplementHeadDAO_HI.saveOrUpdate(entity);
		if (lines != null && lines.size() > 0) {
			for (int i = 0; i < lines.size(); i++) {
				JSONObject line = lines.getJSONObject(i);
				PlmSupplementLineEntity_HI l = JSON.parseObject(
						line.toString(), PlmSupplementLineEntity_HI.class);
				if (l.getPlmSupplementLineId() == null
						|| "".equals(l.getPlmSupplementLineId())) {
					l.setExeStatus("0");
					l.setExpireStatus("0");
					l.setHeadId(entity
							.getPlmSupplementHeadId());
				}
				plmSupplementLineDAO_HI.saveOrUpdate(l);
			}
		}
		if (file != null && file.size() > 0) {
			for (int i = 0; i < file.size(); i++) {
				JSONObject line = file.getJSONObject(i);
				PlmSupplementLineFileEntity_HI l = JSON.parseObject(
						line.toString(), PlmSupplementLineFileEntity_HI.class);
				l.setlineId(entity.getPlmSupplementHeadId()
						.toString());
				plmSupplementLineFileDAO_HI.saveOrUpdate(l);
			}
		}
		return entity.getPlmSupplementHeadId();
	}

	private boolean verify(PlmSupplementHeadEntity_HI plmSupplementHeadEntity_hi) {
		Integer id = plmSupplementHeadEntity_hi.getPlmSupplementHeadId();
		if (id == null) {
			return true;
		}
		JSONObject param = new JSONObject();
		param.put("plmSupplementHeadId", id);
//		PlmSupplementHeadEntity_HI entity = this.getById(id);
		List<PlmSupplementHeadEntity_HI> list = getHeader(param);
		if (list != null && list.size() > 0) {
			PlmSupplementHeadEntity_HI entity = list.get(0);
			if (entity == null) {
				return false;
			}
			if (!entity.getOrderStatus().equals("0")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	private void validLine(JSONArray lines) throws Exception {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		List<PlmSupplementLineEntity_HI> list = lines
				.toJavaList(PlmSupplementLineEntity_HI.class);
		if (!CollectionUtils.isEmpty(list)) {
			// List<Integer> sourceIdCollect1 = nopriceList.stream().filter(e ->
			// e.getSourceId() != null).map(e ->
			// e.getSourceId()).collect(Collectors.toList());
			// List<String> rmsCodes=
			// list.stream().map(l->l.getRmsCode()).distinct().collect(Collectors.toList());
			// if(!CollectionUtils.isEmpty(rmsCodes)){
			// for (String rmsCode: rmsCodes) {
			// List<PlmSupplementLineEntity_HI> varRmsCodeList=
			// list.stream().filter(l->rmsCode.equals(l.getRmsCode())).collect(Collectors.toList());
			// //区分meter 和 生效地点
			// List<String> meters=
			// varRmsCodeList.stream().map(l->l.getMeter()).distinct().collect(Collectors.toList());
			// if(CollectionUtils.isEmpty(meters)){
			// for (String meter: meters) {
			// List<PlmSupplementLineEntity_HI> varMeterList=
			// varRmsCodeList.stream().filter(l->meter.equals(l.getMeter())).collect(Collectors.toList());
			// if(!CollectionUtils.isEmpty(varMeterList) &&
			// varMeterList.size()>1 ){
			// String exeDateStr= ft.format( varMeterList.get(0).getExeDate());
			// List<PlmSupplementLineEntity_HI> existLine=
			// varMeterList.stream().filter(vl->!exeDateStr.equals(ft.format(
			// vl.getExeDate()))).collect(Collectors.toList());
			// if(!CollectionUtils.isEmpty(existLine)){
			// throw new Exception("相同产品,生效地,不同生效日期:"+rmsCode+","+meter);
			// }
			// }
			// }
			// }
			//
			//
			//
			//
			//
			// }
			//
			// }
		}

	}

	public void saveForWorkflow(JSONObject queryParamJSON) {
		String billNo = queryParamJSON.getString("billNo");
		String INCIDENT = queryParamJSON.getString("INCIDENT");
		String VERSION = queryParamJSON.getString("VERSION");
		String taskId = queryParamJSON.getString("TASKID");
		String TASKUSER = queryParamJSON.getString("TASKUSER");
		String allno = queryParamJSON.getString("ALLNO");

		int id = queryParamJSON.getInteger("id");
		PlmSupplementHeadEntity_HI entity = this.getById(id);
		String state = queryParamJSON.getString("state");
		entity.setOrderStatus(state);
		if (state.equals("1")) // 提交审批
		{

			if (allno.equals("Submit")) {
				entity.setProcessUser(TASKUSER);
				entity.setRmsReverBusinesskey(INCIDENT);
			} else {
				entity.setRmsReverBusinesskey(INCIDENT);
			}
			entity.setProcessReparam(null);
		} else if (state.equals("2")) { // 审批完成
			entity.setProcessReparam(null);
		} else if (state.equals("3")) { // 驳回

			String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION + "&&&"
					+ taskId + "&&&" + TASKUSER + "&&&" + allno;
			entity.setProcessReparam(refus);
			entity.setRmsReverBusinesskey(INCIDENT);
		} else {
			entity.setRmsReverBusinesskey(INCIDENT);
		}
		plmSupplementHeadDAO_HI.saveOrUpdate(entity);

		JSONObject param = new JSONObject();
		param.put("headId", id);
		List<PlmSupplementLineEntity_HI> lines = getLines(param);

		Map<String, Object> map = new HashMap<>();
		// 提交
		if ("1".equals(state)) {
			for (int i = 0; i < lines.size(); i++) {
				PlmSupplementLineEntity_HI line = lines.get(i);

				List<PlmSupShopEntity_HI_RO> checkingShops = getShopsByType(line);
				for (PlmSupShopEntity_HI_RO shop : checkingShops) {
					String expireDate = "";
					if (null != lines.get(i).getExpireDate()) {
						expireDate = lines.get(i).getExpireDate().toString()
								.split(" ")[0];
					}
					String status = transSupStatus(lines.get(i).getSupplementStatus());
					map.put(shop.getShopCode(),
							status
									+ "|"
									+ lines.get(i).getExeDate().toString()
											.split(" ")[0] + "|" + expireDate
									+ "|");
				}
				String mapStr = JSON.toJSONString(map);
				jedis.set("PlmSupplement" + entity.getSupCode()
						+ lines.get(i).getRmsCode() + lines.get(i).getPlmSupplementLineId(), mapStr);
			}
		}
		// 审核完
		if ("2".equals(state) || "3".equals(state) || "4".equals(state)) {
			for (int i = 0; i < lines.size(); i++) {
				List<PlmSupShopEntity_HI_RO> checkingShops = getShopsByType(lines
						.get(i));
				for (PlmSupShopEntity_HI_RO shop : checkingShops) {
					jedis.del("PlmSupplement" + entity.getSupCode()
							+ lines.get(i).getRmsCode() + lines.get(i).getPlmSupplementLineId());
				}
			}
		}

	}

	private String transSupStatus(String supplementStatus) {
		if ("4".equals(supplementStatus)) {
			return "0";
		}
		if ("5".equals(supplementStatus)) {
			return "1";
		}
		return supplementStatus;
	}

	private String getSupCode() {
		return generateCodeServer.generateCode("", new SimpleDateFormat(
				"yyyyMMdd"), 4);
	}

	public JSONObject findPlmSupItem(JSONObject queryParamJSON) {
		JSONArray arr = queryParamJSON.getJSONArray("lines");
		Set<String> set = null;
		for (int i = 0; i < arr.size(); i++) {
			JSONObject o = arr.getJSONObject(i);
			String item = o.getString("rmsCode");
			if (item == null || "".equals(item))
				continue;
			JSONObject en = new JSONObject();
			en.put("rmsCode", item);
			List<PlmSupplementLineEntity_HI_RO> list = getItems(en);
			if (list == null || list.size() == 0) {
				continue;
			} else {
				set = new HashSet<>();
				for (PlmSupplementLineEntity_HI_RO ro : list) {
					set.add(ro.getRegion());
				}
				setItemList(set, o);
				Date start = o.getDate("consultDate");
				Date end = o.getDate("consultEnddate");
				if (start != null && end != null) {
					SimpleDateFormat slf = new SimpleDateFormat("yyyyMMdd");
					String s = slf.format(start);
					String e = slf.format(end);
					o.put("consultDate", s);
					o.put("consultEnddate", e);
				}
			}
		}

		return queryParamJSON;
	}

	private void setItemList(Set<String> set, JSONObject o) {
		Iterator<String> itr = set.iterator();
		while (itr.hasNext()) {
			String s = itr.next();
			if (s != null && !"".equals(s)) {
				if (s.equals("South Area")) {
					o.put("southItemList", "Y");
				}
				if (s.equals("North Area")) {
					o.put("northItemList", "Y");
				}
				if (s.equals("East Area")) {
					o.put("eastItemList", "Y");
				}
				if (s.equals("West Area")) {
					o.put("westItemList", "Y");
				}
			}
		}
	}

	private List<PlmSupplementLineEntity_HI_RO> getItems(JSONObject o) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String sqlStr = PlmSupplementLineEntity_HI_RO.SQL_ITEM;
		String rmsId = o.getString("rmsCode");
		sqlStr = sqlStr.replace(":rmsId", rmsId);
		StringBuffer sql = new StringBuffer(sqlStr);
		// SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class, o,
		// sql, queryParamMap);
		List<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO
				.findList(sql, queryParamMap);
		return findListResult;
	}

	public Pagination<PlmSupplementLineEntity_HI_RO> getItems(JSONObject o,
			Integer pageIndex, Integer pageRows) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String sqlStr = PlmSupplementLineEntity_HI_RO.SQL_ITEM;
		String rmsId = o.getString("rmsCode");
		sqlStr = sqlStr.replace(":rmsId", rmsId);
		StringBuffer sql = new StringBuffer(sqlStr);
		o.remove("rmsCode");
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class, o,
				sql, queryParamMap);
		Pagination<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO
				.findPagination(sql, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	@Override
	public Pagination<PlmSupplementHeadEntity_HI_RO> findPlmSupplementInfo(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmSupplementHeadEntity_HI_RO.FINDPIMSINFO);

		// queryParamJSON.put("rmsCode","100000330");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String creator = queryParamJSON.getString("creator_like");
		if (creator != null && !creator.equals("")) {
			sql.append(" AND lower(H.CREATOR) LIKE '%" + creator.toLowerCase()
					+ "%' ");
			queryParamJSON.remove("creator_like");
		}

		setDefaultDate(queryParamJSON, "startDate", "endDate");
		String startDate = queryParamJSON.getString("startDate");
		String endDate = queryParamJSON.getString("endDate");
		if (startDate != null && endDate != null) {
			sql.append(" and to_char(H.creation_date,'yyyy-mm-dd') between '"
					+ startDate + "' and '" + endDate + "' ");
		}
		String rmsCode = queryParamJSON.getString("rmsCode");
		String deptCode = queryParamJSON.getString("deptCode");
		// String userGroupCode = queryParamJSON.getString("userGroupCode");

		String username = queryParamJSON.getString("varUserName");
		String userDept = queryParamJSON.getString("deptName");
		String orgCode = queryParamJSON.getString("orgCode");
		if (!username.equals("ADMIN")) // 采购只能看到自己创建的单据
		{
			if (!"623".equals(orgCode) && !"16855".equals(orgCode)
					&& !"54143".equals(orgCode)) {
				if (!"".equals(orgCode) && null != orgCode) {
					sql.append(" and  H.org_code = '" + orgCode + "' ");
				}
				// if(!"".equals(userGroupCode) && null!= userGroupCode &&
				// userGroupCode !="null") {
				// sql.append(" and  H.user_group_code = '" +userGroupCode +
				// "' ");
				// }
			}
		}
		queryParamJSON.remove("orgCode");

		SaafToolUtils.parperHbmParam(PlmSupplementHeadEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		sql.append(" order by h.CREATION_DATE DESC ");
		String str = sql.toString();
		if ("".equals(rmsCode) || null == rmsCode) {
			str = str.replace("l.rms_code as rmsCode,", "");
		}
		Pagination<PlmSupplementHeadEntity_HI_RO> findListResult = plmSupplementHeadDAO_HI_RO
				.findPagination(str, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	public Pagination<PlmSupplementLineEntity_HI_RO> findPlmSupplementLines(
			JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) {
		StringBuffer sql = new StringBuffer(
				PlmSupplementLineEntity_HI_RO.SQL3);

		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String creator = queryParamJSON.getString("creator_like");
		if (creator != null && !creator.equals("")) {
			sql.append(" AND lower(H.CREATOR) LIKE '%" + creator.toLowerCase()
					+ "%' ");
			queryParamJSON.remove("creator_like");
		}

		setDefaultDate(queryParamJSON, "startDate", "endDate");
		String startDate = queryParamJSON.getString("startDate");
		String endDate = queryParamJSON.getString("endDate");
		if (startDate != null && endDate != null) {
			sql.append(" and to_char(H.creation_date,'yyyy-mm-dd') between '"
					+ startDate + "' and '" + endDate + "' ");
		}
		String rmsCode = queryParamJSON.getString("rmsCode");
		String deptCode = queryParamJSON.getString("deptCode");
		// String userGroupCode = queryParamJSON.getString("userGroupCode");

		String username = queryParamJSON.getString("varUserName");
		String userDept = queryParamJSON.getString("deptName");
		String orgCode = queryParamJSON.getString("orgCode");
		if (!username.equals("ADMIN")) // 采购只能看到自己创建的单据
		{
			if (!"623".equals(orgCode) && !"16855".equals(orgCode)
					&& !"54143".equals(orgCode)) {
				if (!"".equals(orgCode) && null != orgCode) {
					sql.append(" and  H.org_code = '" + orgCode + "' ");
				}
				// if(!"".equals(userGroupCode) && null!= userGroupCode &&
				// userGroupCode !="null") {
				// sql.append(" and  H.user_group_code = '" +userGroupCode +
				// "' ");
				// }
			}
		}
		queryParamJSON.remove("orgCode");

		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		sql.append(" order by h.CREATION_DATE DESC ");
		String str = sql.toString();
		if ("".equals(rmsCode) || null == rmsCode) {
			str = str.replace("l.rms_code as rmsCode,", "");
		}
		Pagination<PlmSupplementLineEntity_HI_RO> findListResult = plmSupplementLineDAO_HI_RO
				.findPagination(str, queryParamMap, pageIndex, pageRows);
		return findListResult;
	}

	private void setDefaultDate(JSONObject queryParamJSON, String startDate,
			String endDate) {
		String s = queryParamJSON.getString(startDate);
		String e = queryParamJSON.getString(endDate);

		if (s != null || e != null) {
			if (s == null) {
				s = "2000-01-01";
				queryParamJSON.put(startDate, s);
			}
			if (e == null) {
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String n = sdf.format(now);
				e = n;
				queryParamJSON.put(endDate, e);
			}
		}
	}

	@Override
	public Object findPlmSupplementDetail(JSONObject queryParamJSON,
			Integer pageIndex, Integer pageRows) {
		if (queryParamJSON.containsKey("id")) {
			queryParamJSON.put("plmSupplementHeadId",
					queryParamJSON.getInteger("id"));
		}
		PlmSupplementHeadEntity_HI_RO head = getHead(queryParamJSON);
		if (head == null) {
			return null;
		}
		Integer headId = head.getPlmSupplementHeadId();
		queryParamJSON.put("headId", headId);
		if (!queryParamJSON.containsKey("plmSupplementHeadId")) {
			queryParamJSON.put("plmSupplementHeadId", headId);
			queryParamJSON.remove("supCode");
		}
		List<PlmSupplementLineEntity_HI_RO> lines = getLines2(queryParamJSON);
		queryParamJSON.put("lineId", headId);
		List<PlmSupplementLineFileEntity_HI> files = getFileList(queryParamJSON);
		queryParamJSON = (JSONObject) JSON.toJSON(head);
		queryParamJSON.put("lines", JSON.toJSON(lines));
		queryParamJSON.put("files", JSON.toJSON(files));
		return queryParamJSON;
	}

	@Override
	public List<PlmSupplementLineEntity_HI_RO> findLines(
			JSONObject queryParamJSON) {
		List<PlmSupplementLineEntity_HI_RO> list = getLines2(queryParamJSON);
		return list;
	}

	private List<PlmSupplementLineFileEntity_HI> getFileList(
			JSONObject queryParamJSON) {
		return plmSupplementLineFileDao.getFiles(queryParamJSON);
	}

	private List<PlmSupplementLineEntity_HI_RO> getLinesByDataOpen() {
		StringBuffer sql = new StringBuffer(
				PlmSupplementLineEntity_HI_RO.OPEN_LINES_SQL);
		return plmSupplementLineDAO_HI_RO.findList(sql,
				new HashMap<String, Object>());
	}

	private List<PlmSupplementLineEntity_HI_RO> getLinesByDataStop() {
		StringBuffer sql = new StringBuffer(
				PlmSupplementLineEntity_HI_RO.STOP_LINES_SQL);
		return plmSupplementLineDAO_HI_RO.findList(sql,
				new HashMap<String, Object>());
	}

	private List<PlmSupplementLineEntity_HI_RO> getLinesByInside(String headId) {
		String sqlStr = PlmSupplementLineEntity_HI_RO.INSIDE_LINES_SQL;
		sqlStr = sqlStr.replace(":headId", headId);
		StringBuffer sql = new StringBuffer(sqlStr);
		return plmSupplementLineDAO_HI_RO.findList(sql,
				new HashMap<String, Object>());
	}

	private List<PlmSupplementLineEntity_HI> getLines(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(
				" from PlmSupplementLineEntity_HI where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI.class,
				queryParamJSON, sql, queryParamMap);
		return plmSupplementLineDAO_HI.findList(sql, queryParamMap);
	}

	private List<PlmSupplementLineEntity_HI_RO> getLines2(
			JSONObject queryParamJSON) {
		String SQL2 = PlmSupplementLineEntity_HI_RO.SQL2;
		StringBuffer sql = new StringBuffer(SQL2);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		return plmSupplementLineDAO_HI_RO.findList(sql, queryParamMap);
	}

	private List<PlmSupplementLineEntity_HI_RO> getLinesNew(
			JSONObject queryParamJSON) {
		String SQL3 = PlmSupplementLineEntity_HI_RO.SQL3;
		StringBuffer sql = new StringBuffer(SQL3);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementLineEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		return plmSupplementLineDAO_HI_RO.findList(sql, queryParamMap);
	}

	// 与审批中互斥的货品conflict
	private String getConflictLines(String headId,
			List<PlmSupplementLineEntity_HI> lines) {
		String sqlStr = PlmSupplementLineEntity_HI_RO.CHECKING_SQL;
		sqlStr = sqlStr.replace(":headId", headId);
		StringBuffer sql = new StringBuffer(sqlStr);
		String conflictData = "";
		List<PlmSupplementLineEntity_HI_RO> findList = plmSupplementLineDAO_HI_RO
				.findList(sql, new HashMap<String, Object>());
		if (findList != null) {
			for (int i = 0; i < findList.size(); i++) {
				for (int m = 0; m < lines.size(); m++) {
					if (findList.get(i).getRmsCode()
							.equals(lines.get(m).getRmsCode())) {
						if ((findList.get(i).getSupplementStatus().equals("1") && lines
								.get(m).getSupplementStatus().equals("0"))
								|| (findList.get(i).getSupplementStatus()
										.equals("0") && lines.get(m)
										.getSupplementStatus().equals("1"))) {
							if (lines.get(m).getStore()
									.equals(findList.get(i).getStore())) {
								if (containStr(findList.get(i).getMeter(),
										lines.get(m).getMeter())) {
									if (conflictData.indexOf(lines.get(m)
											.getRmsCode()) < 0) {
										conflictData = conflictData + ",货品："
												+ lines.get(m).getRmsCode()
												+ ",地点："
												+ lines.get(m).getMeter()
												+ ",与审核中数据存在冲突";
									}
								}
							}

						}
						if (findList.get(i).getSupplementStatus().equals("0")
								&& lines.get(m).getSupplementStatus()
										.equals("0")) {
							if (lines.get(m).getStore()
									.equals(findList.get(i).getStore())) {
								if (containStr(findList.get(i).getMeter(),
										lines.get(m).getMeter())) {
									if (findList.get(i).getExeDate() != lines
											.get(m).getExeDate()
											|| findList.get(i).getExpireDate() != lines
													.get(m).getExpireDate()) {
										if (conflictData.indexOf(lines.get(m)
												.getRmsCode()) < 0) {
											conflictData = conflictData
													+ ",货品："
													+ lines.get(m).getRmsCode()
													+ ",地点："
													+ lines.get(m).getMeter()
													+ ",与审核中数据存在冲突";
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return conflictData;
	}

	private Boolean containStr(String str1, String str2) {
		String[] strList1 = str1.split("\\|");
		String[] strList2 = str2.split("\\|");
		for (int i = 0; i < strList1.length; i++) {
			for (int m = 0; m < strList2.length; m++) {
				if (strList1[i].equals(strList2[m])) {
					return true;
				}
			}
		}
		return false;
	}

	// 审批中货品对应的商铺
	private Map<String, String> getCheckingLinesShop(String headId,
			List<PlmSupShopEntity_HI_RO> shops) {
		String sqlStr = PlmSupplementLineEntity_HI_RO.CHECKING_SQL;
		sqlStr = sqlStr.replace(":headId", headId);
		StringBuffer sql = new StringBuffer(sqlStr);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		Map<String, String> repeatData = new HashMap<String, String>();
		List<PlmSupplementLineEntity_HI_RO> findList1 = plmSupplementLineDAO_HI_RO
				.findList(sql, queryParamMap);
		List<PlmSupplementLineEntity_HI> findList = transfer(findList1);
		if (findList != null && findList.size() > 0) {
			for (int i = 0; i < findList.size(); i++) {
				List<PlmSupShopEntity_HI_RO> checkingShops = getShopsByType(findList
						.get(i));
				if (checkingShops != null) {
					for (int m = 0; m < checkingShops.size(); m++) {
						for (int n = 0; n < shops.size(); n++) {
							if (checkingShops.get(m).getShopCode()
									.equals(shops.get(n).getShopCode())) {
								// 获取货品对应的店铺,key-货品编码，value-商铺编码（,）拼接
								String shop = repeatData.get(findList.get(i)
										.getRmsCode());
								if (null == shop) {
									repeatData.put(
											findList.get(i).getRmsCode(), shops
													.get(n).getShopCode());
								} else {
									repeatData.put(
											findList.get(i).getRmsCode(), shop
													+ ","
													+ shops.get(n)
															.getShopCode());
								}
							}
						}
					}
				}
			}
		}
		return repeatData;
	}

	// 查重
	private Map<String, String> checkShop(PlmSupplementLineEntity_HI line,
			List<PlmSupShopEntity_HI_RO> shops) throws ParseException {
		// 获取审批单据中该货品对应所有的信息
		Set<String> shopKeys = redisKeys("*" + line.getRmsCode() + "*");
		// String code = jedis.get("201912040056100413802");
		// String code2 = jedis.get("201912050041100413802");

		// supplementLineOpen();

		Map<String, String> infoMap = new HashMap<>();

//		JSONObject param = new JSONObject();

		// param.put("id",line.getHeadId());
		// param.put("state","1");
		// saveForWorkflow(param);

		for (PlmSupShopEntity_HI_RO shop : shops) {
			for (String shopKey : shopKeys) {
				// 获取redis审核中店铺
				// Map<String, String> shopMap = (Map<String, String>)
				// JSON.parse(shopKey);
				// String checkShop = shopMap.get(shop.getShopCode());
				String billNo = shopKey.substring(13, 25);
				String checkShopList = jedis.get(shopKey);
				Map<String, String> shopMap = (Map<String, String>) JSON
						.parse(checkShopList);
				String checkShop = shopMap.get(shop.getShopCode());
				String shopStr = "";
				String status = "";
				String exeDate = "";// 生效时间
				String expireDate = "";// 失效时间
				// 如果有值则认为有重复
				if (null != checkShop) {
					// shopStr = checkShop.split("\\|")[0].split(":")[0];
					status = checkShop.substring(0, 1);
					String[] strList = checkShop.split("\\|");
					String ss = checkShop.split("\\|")[1];
					exeDate = checkShop.split("\\|")[1].split(" ")[0];
					if (checkSta("open", line.getSupplementStatus())) {
						String[] s = checkShop.split("\\|");
						if (s.length == 3) {
							expireDate = checkShop.split("\\|")[2].split(" ")[0];
						}
					}

					// if(shopStr.indexOf(shop.getShopCode())>=0){
					// 都是开通状态时
					if (checkSta("open", line.getSupplementStatus())
							&& checkSta("open", status)) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String date1 = sdf.format(line.getExeDate());
						Date ex = line.getExpireDate();
						String date2 = null;
						if (ex != null) {
							date2 = sdf.format(line.getExpireDate());
						}
						if (!exeDate.equals(date1) || !expireDate.equals(date2)) {
							// infoMap.put("rmsCode", line.getRmsCode());
							// String str = infoMap.get("area") == null ? ""
							// : infoMap.get("area");
							// if (str.indexOf(shop.getArea()) < 0) {
							// infoMap.put("area", str + "," + shop.getArea());
							// }
							setInfoMap(infoMap, shop, line, billNo);
						}
					}
					if ((checkSta("stop", status)
							&& checkSta("open", line.getSupplementStatus()) || (checkSta(
							"open", status) && checkSta("stop",
							line.getSupplementStatus())))) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date date1 = sdf.parse(exeDate);
						Date date2 = null;
						if (expireDate != null && !"".equals(expireDate)) {
							date2 = sdf.parse(expireDate);
						}
//						if (checkSta("open", line.getSupplementStatus())) {
//						}
						if (line.getExpireDate() != null) {
							if (line.getExpireDate().getTime() < date1
									.getTime()){
//										|| line.getExeDate().getTime() > date2
//												.getTime()) {

							} else {
								// infoMap.put("rmsCode",
								// line.getRmsCode());
								// String str = infoMap.get("area") == null
								// ? ""
								// : infoMap.get("area");
								// if (str.indexOf(shop.getArea()) < 0) {
								// infoMap.put("area",
								// str + "," + shop.getArea());
								// }
								setInfoMap(infoMap, shop, line, billNo);
							}
						} else {
							if (date2 != null
									&& line.getExeDate().getTime() > date2
									.getTime()) {

							} else {

								setInfoMap(infoMap, shop, line, billNo);
							}

						}

						// }
					}
				}
			}
		}

		return infoMap;
	}

	private void setInfoMap(Map<String, String> infoMap,
			PlmSupShopEntity_HI_RO shop, PlmSupplementLineEntity_HI line,
			String billNo) {
		infoMap.put("rmsCode", line.getRmsCode());
		String str = infoMap.get("area") == null ? "" : infoMap.get("area");
		if (str.indexOf(shop.getArea()) < 0) {
			infoMap.put("area", str + "," + shop.getArea());
		}
		String billNum = infoMap.get("bill") == null ? "" : infoMap.get("bill");
		if (billNum.indexOf(billNo) < 0) {
			infoMap.put("bill", billNum + "," + billNo);
		}
	}

	public Boolean checkSta(String type, String sta) {
		if (type.equals("stop")) {
			if (sta.equals("1") || sta.equals("3") || sta.equals("5")) {
				return true;
			}
		}
		if (type.equals("open")) {
			if (sta.equals("0") || sta.equals("2") || sta.equals("4")) {
				return true;
			}
		}
		return false;
	}

	private List<PlmSupplementLineEntity_HI> transfer(
			List<PlmSupplementLineEntity_HI_RO> findList1) {
		List<PlmSupplementLineEntity_HI> results = new ArrayList<>();
		if (findList1 != null && findList1.size() > 0) {
			for (PlmSupplementLineEntity_HI_RO hi_ro : findList1) {
				PlmSupplementLineEntity_HI entity = new PlmSupplementLineEntity_HI();
				BeanUtils.copyProperties(hi_ro, entity);
				results.add(entity);
			}
		}

		return results;
	}

	private PlmSupplementHeadEntity_HI_RO getHead(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(
				PlmSupplementHeadEntity_HI_RO.FINDPIMSINFO_2);
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementHeadEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		List<PlmSupplementHeadEntity_HI_RO> findListResult = plmSupplementHeadDAO_HI_RO
				.findList(sql, queryParamMap);
		if (findListResult != null && findListResult.size() > 0) {
			return findListResult.get(0);
		}
		return null;
	}

	@Override
	public synchronized void updateShopsSupExe(JSONObject queryParamJSON) {
		// 用来去重复店铺的storeMap
		Map<String, Object> storeMapFrClose = new HashMap<>();
		Map<String, Object> storeMapFrOpen = new HashMap<>();
		Map<String, Object> logMa = new HashMap<>();

		// JSONArray arr = queryParamJSON.getJSONArray("lines");
		JSONObject param = new JSONObject();
		param.put("headId", queryParamJSON.getInteger("headId"));
		// List<PlmSupplementLineEntity_HI> lines = getLines(param);

		List<PlmSupplementLineEntity_HI_RO> lines = getLinesByDataOpen();
		if (lines != null && lines.size() > 0) {
			for (int i = 0; i < lines.size(); i++) {
				PlmSupplementLineEntity_HI line = changeEntity(lines.get(i));
				PlmSupplementLineEntity_HI_RO lineRo = lines.get(i);
//				if (!SaafToolUtils.isNullOrEmpty(line.getStore())) {
//
//				} else {
//
//				}

				JSONObject product = new JSONObject();
				// setQueryParam(line, product);
				List<PlmSupShopEntity_HI_RO> shops = getShopsByType(line);
				PlmSupShopEntity_HI tmp;
				if (line.getSupplementStatus().equals("0")
						|| line.getSupplementStatus().equals("4")) {
					for (PlmSupShopEntity_HI_RO shop : shops) {
						String key = shop.getItem() + shop.getShopCode();
						if (logMa.containsKey(key)) {
							continue;
						}
						logMa.put(key, shop);
						tmp = new PlmSupShopEntity_HI();
						tmp.setItem(shop.getItem());
						tmp.setShopCode(shop.getShopCode());
						saveOpenShopLog(tmp, lines.get(i).getSupCode());
						saveOpenLog(tmp, lines.get(i).getSupCode());
					}
				} else if (line.getSupplementStatus().equals("1")
						|| line.getSupplementStatus().equals("5")) {

					for (PlmSupShopEntity_HI_RO shop : shops) {
						String key = shop.getItem() + shop.getShopCode();
						if (logMa.containsKey(key)) {
							continue;
						}
						logMa.put(key, shop);
						tmp = new PlmSupShopEntity_HI();
						tmp.setItem(shop.getItem());
						tmp.setShopCode(shop.getShopCode());
						saveCloseShopLog(tmp, lines.get(i)
								.getSupCode());
						saveCloseLog(tmp, lines.get(i).getSupCode());
					}
				}
				// getConflictLines(queryParamJSON.getString("headId"),lines);
				//
				// getCheckingLinesShop(queryParamJSON.getString("headId"),shops);
				product.put("item", line.getRmsCode());
				List<PlmSupShopEntity_HI_RO> exShops = getExShops(product);
				if (line.getSupplementStatus().equals("1")
						|| line.getSupplementStatus().equals("5")) {
					// 如果是关停店铺就要把重复的店铺去掉再做保存
					List<PlmSupShopEntity_HI> newShops = processDup(shops,
							exShops, storeMapFrClose);
					// if(newShops.size()==0&&shops.size()>0){
					// for(int k=0;k<shops.size();k++){
					// PlmSupShopEntity_HI s = new PlmSupShopEntity_HI();
					// s.setWc(shops.get(k).getWc());
					// s.setItem(shops.get(k).getItem());
					// s.setPlmSupShopId(shops.get(k).getPlmSupShopId());
					// newShops.add(s);
					// }
					// }
					if (newShops != null && newShops.size() > 0) {
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmSupShopDAO_HI.saveOrUpdate(shop);
//							saveCloseLog(shop, lines.get(i).getSupCode());

//							saveCloseShopLog(shop, lines.get(i)
//									.getSupCode());
//							JSONObject queryJSON = new JSONObject();
//							queryJSON.put("productCode", shop.getItem());
//							queryJSON.put("shopCode", shop.getShopCode());
//							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//									.findShopList(queryJSON);
//							if (shopList.size() <= 0) {
//							}
							// saveBaseCloseLog(shop,lines.get(i).getSupCode());
						}

						// 需要更新仓库记录关停的店铺数
					}
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					Map<String, List<PlmSupShopEntity_HI_RO>> logMap = mergeLog(shops);
					baseLog(logMap, lineRo.getSupCode());
					// 更新关停的店铺
					updateWCStop(map, line, lineRo.getSupCode());
				} else if (line.getSupplementStatus().equals("0")
						|| line.getSupplementStatus().equals("4")) {
					// 如果是开通店铺就从关停的店铺里面删除掉
					List<PlmSupShopEntity_HI> newShops = processInter(shops,
							exShops, storeMapFrOpen);
					if (newShops != null && newShops.size() > 0) {
						// plmSupShopDAO_HI.deleteAll(newShops);
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmsupshop.deleteById(shop.getPlmSupShopId());
//							saveOpenLog(shop, lines.get(i).getSupCode());

//							JSONObject queryJSON = new JSONObject();
//							queryJSON.put("productCode", shop.getItem());
//							queryJSON.put("shopCode", shop.getShopCode());
//							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//									.findShopList(queryJSON);
//							if (shopList.size() <= 0) {
//							}
//							saveOpenShopLog(shop, lines.get(i).getSupCode());

						}
					}
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					Map<String, List<PlmSupShopEntity_HI_RO>> logMap = mergeLog(shops);
					baseLog(logMap, lineRo.getSupCode());
					// 更新开通的店铺
					updateWCOpen(map, line, lineRo.getSupCode());
				} else if (line.getSupplementStatus().equals("2")) {
					// 如果是开通店铺就从关停的店铺里面删除掉
//					List<PlmSupShopEntity_HI> newShops = processInter(shops,
//							exShops, storeMapFrOpen);
					List<PlmSupShopEntity_HI> newShops = transRO(shops);
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// setNullMap(map, shops, exShops);
					// 更新开通的店铺
					updateWCOpenOnly(map, line, lineRo.getSupCode());
				} else if (line.getSupplementStatus().equals("3")) {
					// 如果是关停店铺就要把重复的店铺去掉再做保存
//					List<PlmSupShopEntity_HI> newShops = processDup(shops,
//							exShops, storeMapFrClose);
					List<PlmSupShopEntity_HI> newShops = transRO(shops);
					// 需要更新仓库记录关停的店铺数
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// 更新关停的店铺
					updateWCStopOnly(map, line, lineRo.getSupCode());
				}

				PlmSupplementLineEntity_HI lineEntity = plmSupplementLineDAO_HI
						.getById(line.getPlmSupplementLineId());
				lineEntity.setExeStatus("1");
				lineEntity.setLastUpdateDate(new Date());
				plmSupplementLineDAO_HI.saveOrUpdate(lineEntity);
			}
		}
	}

	private List<PlmSupShopEntity_HI> transRO(List<PlmSupShopEntity_HI_RO> shops) {
		List<PlmSupShopEntity_HI> list = null;
		if (shops != null && shops.size() > 0) {
			list = new ArrayList<>();
			for (PlmSupShopEntity_HI_RO entity: shops) {
				PlmSupShopEntity_HI en = null;
				if (entity != null) {
					en = new PlmSupShopEntity_HI();
					en.setWc(entity.getWc());
					en.setShopCode(entity.getShopCode());
					en.setItem(entity.getItem());
					list.add(en);
				}
			}
		}
		return list;
	}

	private void setNullMap(Map<String, List<PlmSupShopEntity_HI>> map,
			List<PlmSupShopEntity_HI_RO> shops,
			List<PlmSupShopEntity_HI_RO> exShops) {

	}

	@Override
	public synchronized void updateShopsSupExpire(JSONObject queryParamJSON) {
		// 用来去重复店铺的storeMap
		Map<String, Object> storeMapFrClose = new HashMap<>();
		Map<String, Object> storeMapFrOpen = new HashMap<>();
		Map<String, Object> logMa = new HashMap<>();
		// JSONArray arr = queryParamJSON.getJSONArray("lines");
		JSONObject param = new JSONObject();
		param.put("headId", queryParamJSON.getInteger("headId"));
		// List<PlmSupplementLineEntity_HI> lines = getLines(param);
		List<PlmSupplementLineEntity_HI_RO> lines = getLinesByDataStop();
		if (lines != null && lines.size() > 0) {
			for (int i = 0; i < lines.size(); i++) {
				PlmSupplementLineEntity_HI line = changeEntity(lines.get(i));
				PlmSupplementLineEntity_HI_RO lineRo = lines.get(i);
				if (!SaafToolUtils.isNullOrEmpty(line.getStore())) {

				} else {

				}
				JSONObject product = new JSONObject();
				// setQueryParam(line, product);
				List<PlmSupShopEntity_HI_RO> shops = getShopsByType(line);
				PlmSupShopEntity_HI tmp;
				if (line.getSupplementStatus().equals("0")
						|| line.getSupplementStatus().equals("4")) {
					for (PlmSupShopEntity_HI_RO shop : shops) {
						String key = shop.getItem() + shop.getShopCode();
						if (logMa.containsKey(key)) {
							continue;
						}
						logMa.put(key, shop);
						tmp = new PlmSupShopEntity_HI();
						tmp.setItem(shop.getItem());
						tmp.setShopCode(shop.getShopCode());
						saveCloseShopLog(tmp, lines.get(i).getSupCode());
						saveCloseLog(tmp, lines.get(i).getSupCode());
					}
				}
				// getConflictLines(queryParamJSON.getString("headId"),lines);
				//
				// getCheckingLinesShop(queryParamJSON.getString("headId"),shops);

				List<PlmSupShopEntity_HI_RO> exShops = getExShops(product);
				if (line.getSupplementStatus().equals("0")
						|| line.getSupplementStatus().equals("4")) {
					// 如果是关停店铺就要把重复的店铺去掉再做保存
					List<PlmSupShopEntity_HI> newShops = processDup(shops,
							exShops, storeMapFrClose);
					if (newShops != null && newShops.size() > 0) {
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmSupShopDAO_HI.saveOrUpdate(shop);
//							saveCloseLog(shop, lines.get(i).getSupCode());
//							saveBaseCloseLog(shop, lines.get(i).getSupCode());

//							JSONObject queryJSON = new JSONObject();
//							queryJSON.put("productCode", shop.getItem());
//							queryJSON.put("shopCode", shop.getShopCode());
//							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//									.findShopList(queryJSON);
//							if (shopList.size() <= 0) {
//							}
//							saveCloseShopLog(shop, lines.get(i)
//									.getSupCode());
						}

						// 需要更新仓库记录关停的店铺数
						Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
						if (line.getSupplementStatus().equals("4")) {
							line.setSupplementStatus("5");
						}
						// 更新关停的店铺
						updateWCStop(map, line, lineRo.getSupCode());
					}
				} else if (line.getSupplementStatus().equals("1")
						|| line.getSupplementStatus().equals("5")) {
					// 如果是开通店铺就从关停的店铺里面删除掉
					List<PlmSupShopEntity_HI> newShops = processInter(shops,
							exShops, storeMapFrOpen);
					if (newShops != null && newShops.size() > 0) {
						// plmSupShopDAO_HI.deleteAll(newShops);
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmsupshop.deleteById(shop.getPlmSupShopId());
//							saveOpenLog(shop, lines.get(i).getSupCode());
//							saveBaseCloseLog(shop, lines.get(i).getSupCode());

//							JSONObject queryJSON = new JSONObject();
//							queryJSON.put("productCode", shop.getItem());
//							queryJSON.put("shopCode", shop.getShopCode());
//							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//									.findShopList(queryJSON);
//							if (shopList.size() <= 0) {
//								saveOpenShopLog(shop, lines.get(i).getSupCode());
//								saveBaseCloseShopLog(shop, lines.get(i)
//										.getSupCode());
//							}
						}
					}
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// 更新开通的店铺
					updateWCOpen(map, line, lineRo.getSupCode());
				} else if (line.getSupplementStatus().equals("2")) {
					// 如果是开通店铺就从关停的店铺里面删除掉
					List<PlmSupShopEntity_HI> newShops = processInter(shops,
							exShops, storeMapFrOpen);
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					line.setSupplementStatus("3");
					// 更新开通的店铺
					updateWCOpenOnly(map, line, lineRo.getSupCode());
				} else if (line.getSupplementStatus().equals("3")) {
					// 如果是关停店铺就要把重复的店铺去掉再做保存
					List<PlmSupShopEntity_HI> newShops = processDup(shops,
							exShops, storeMapFrClose);
					// 需要更新仓库记录关停的店铺数
					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// 更新关停的店铺
					// updateWCStopOnly(map, line);
				}

				PlmSupplementLineEntity_HI lineEntity = plmSupplementLineDAO_HI
						.getById(line.getPlmSupplementLineId());
				lineEntity.setExpireStatus("1");
				lineEntity.setLastUpdateDate(new Date());
				plmSupplementLineDAO_HI.saveOrUpdate(lineEntity);
			}
		}
	}

	// 定时处理货品状态
	private void supplementLineOpen() {
		List<PlmSupplementLineEntity_HI_RO> lines = getLinesByDataOpen();
		for (PlmSupplementLineEntity_HI_RO line : lines) {
			// 开通
			if ("0".equals(line.getSupplementStatus())) {
				if (line.getExeDate().getTime() < new Date().getTime()
						|| line.getExpireDate().getTime() > new Date()
								.getTime()) {
					PlmSupplementLineEntity_HI lineEntity = new PlmSupplementLineEntity_HI();
					lineEntity = changeEntity(line);
					List<PlmSupShopEntity_HI_RO> shops = getShopsByType(lineEntity);
					JSONObject product = new JSONObject();
					product.put("item", line.getRmsCode());
					List<PlmSupShopEntity_HI_RO> exShops = getExShops(product);

					// 如果是开通店铺就从关停的店铺里面删除掉
					List<PlmSupShopEntity_HI> newShops = processInter(shops,
							exShops, new HashMap<String, Object>());
					if (newShops != null && newShops.size() > 0) {
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmsupshop.deleteById(shop.getPlmSupShopId());
							saveOpenLog(shop, line.getSupCode());
							saveBaseCloseLog(shop, line.getSupCode());

							JSONObject queryJSON = new JSONObject();
							queryJSON.put("productCode", shop.getItem());
							queryJSON.put("shopCode", shop.getShopCode());
							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
									.findShopList(queryJSON);
							if (shopList.size() <= 0) {
								saveOpenShopLog(shop, line.getSupCode());
								saveBaseCloseShopLog(shop, line.getSupCode());
							}
						}
					}

					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// 更新开通的店铺
					// updateWCOpen(map, lineEntity);

					// 更改开通状态为已处理
					PlmSupplementLineEntity_HI entity = plmSupplementLineDAO_HI
							.getById(line.getPlmSupplementLineId());
					entity.setExeStatus("1");
					entity.setLastUpdateDate(new Date());
					plmSupplementLineDAO_HI.save(entity);
				}
			}
		}

	}

	// 定时处理货品状态
	private void supplementLineStop() {
		List<PlmSupplementLineEntity_HI_RO> lines = getLinesByDataStop();
		for (PlmSupplementLineEntity_HI_RO line : lines) {
			//
			if ("1".equals(line.getSupplementStatus())) {
				if (line.getExeDate().getTime() < new Date().getTime()) {
					PlmSupplementLineEntity_HI lineEntity = new PlmSupplementLineEntity_HI();
					lineEntity = changeEntity(line);
					List<PlmSupShopEntity_HI_RO> shops = getShopsByType(lineEntity);
					JSONObject product = new JSONObject();
					product.put("item", line.getRmsCode());
					List<PlmSupShopEntity_HI_RO> exShops = getExShops(product);

					// 如果是关停店铺就要把重复的店铺去掉再做保存
					List<PlmSupShopEntity_HI> newShops = processDup(shops,
							exShops, new HashMap<String, Object>());
					if (newShops != null && newShops.size() > 0) {
						for (PlmSupShopEntity_HI shop : newShops) {
							shop.setStopReason(line.getStopReason());
							plmSupShopDAO_HI.saveOrUpdate(shop);
							saveCloseLog(shop, line.getSupCode());
							saveBaseCloseLog(shop, line.getSupCode());

							JSONObject queryJSON = new JSONObject();
							queryJSON.put("productCode", shop.getItem());
							queryJSON.put("shopCode", shop.getShopCode());
							List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
									.findShopList(queryJSON);
							if (shopList.size() <= 0) {
								saveCloseShopLog(shop, line.getSupCode());
								saveBaseCloseShopLog(shop, line.getSupCode());
							}
						}

						// 需要更新仓库记录关停的店铺数
						Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
						// 更新关停的店铺
						// updateWCStop(map, lineEntity);
					}

					Map<String, List<PlmSupShopEntity_HI>> map = merge(newShops);
					// 更新开通的店铺
					// updateWCOpen(map, lineEntity);

					// 更改开通状态为已处理
					PlmSupplementLineEntity_HI entity = plmSupplementLineDAO_HI
							.getById(line.getPlmSupplementLineId());
					entity.setExpireStatus("1");
					entity.setLastUpdateDate(new Date());
					plmSupplementLineDAO_HI.save(entity);
				}
			}
		}

	}

	private PlmSupplementLineEntity_HI changeEntity(
			PlmSupplementLineEntity_HI_RO line) {
		PlmSupplementLineEntity_HI entity = new PlmSupplementLineEntity_HI();
		entity.setExeStatus(line.getExeStatus());
		entity.setLastUpdateDate(line.getLastUpdateDate());
		entity.setSupplementStatus(line.getSupplementStatus());
		entity.setCreatedBy(line.getCreatedBy());
		entity.setCreationDate(line.getCreationDate());
		entity.setEastItemList(line.getEastItemList());
		entity.setExeDate(line.getExeDate());
		entity.setExpireDate(line.getExpireDate());
		entity.setMeter(line.getMeter());
		entity.setPlmSupplementLineId(line.getPlmSupplementLineId());
		entity.setPogCode(line.getPogCode());
		entity.setPriorSupplier(line.getPriorSupplier());
		entity.setProductName(line.getProductName());
		entity.setStopReason(line.getStopReason());
		entity.setSupplementStatus(line.getSupplementStatus());
		entity.setStoreType(line.getStoreType());
		entity.setStore(line.getStore());
		entity.setExeStatus(line.getExeStatus());
		entity.setExpireStatus(line.getExpireStatus());
		entity.setRmsCode(line.getRmsCode());
		return entity;
	}

	// 定时处理货品状态
	private void updateSupplementLineStatus() {
		List<PlmSupplementLineEntity_HI_RO> lines = getLinesByDataOpen();
		for (PlmSupplementLineEntity_HI_RO line : lines) {
			// 开通
			if ("0".equals(line.getSupplementStatus())) {
				if (line.getExpireDate().getTime() > new Date().getTime()) {
					PlmSupplementLineEntity_HI entity = plmSupplementLineDAO_HI
							.getById(line.getPlmSupplementLineId());
					entity.setSupplementStatus("1");
					entity.setLastUpdateDate(new Date());
					plmSupplementLineDAO_HI.save(entity);
				}
			}
			// 停补
			if ("1".equals(line.getSupplementStatus())) {
				if (line.getExeDate().getTime() > new Date().getTime()) {
					PlmSupplementLineEntity_HI entity = plmSupplementLineDAO_HI
							.getById(line.getPlmSupplementLineId());
					entity.setSupplementStatus("0");
					entity.setLastUpdateDate(new Date());
					plmSupplementLineDAO_HI.save(entity);
				}
			}
		}

	}

	private List<PlmSupplementHeadEntity_HI> getHeader(JSONObject queryParamJSON) {
		StringBuffer sql = new StringBuffer(
				" from PlmSupplementHeadEntity_HI where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupplementHeadEntity_HI.class,
				queryParamJSON, sql, queryParamMap);
		return plmSupplementHeadDAO_HI.findList(sql, queryParamMap);
	}

	@Override
	public synchronized JSONObject updateShopsSupBefore(
			JSONObject queryParamJSON) throws Exception {
		// supplementLineStop();

		// 用来去重复店铺的storeMap
		List<PlmSupplementLineEntity_HI> storeMapFrClose = new ArrayList<>();
		List<PlmSupplementLineEntity_HI> storeMapFrOpen = new ArrayList<>();
		// List<PlmSupShopEntity_HI_RO> dupList = new ArrayList<>();
		Set<String> set = new HashSet<>();
		// JSONArray arr = queryParamJSON.getJSONArray("lines");
		JSONObject param = new JSONObject();
		param.put("headId", queryParamJSON.getInteger("headId"));
		PlmSupplementHeadEntity_HI plmSupplementHeadEntity_HI = new PlmSupplementHeadEntity_HI();
		plmSupplementHeadEntity_HI.setPlmSupplementHeadId(queryParamJSON.getInteger("headId"));
		if (verify(plmSupplementHeadEntity_HI)) {
			throw new Exception("单据不能操作！");
		}
		List<PlmSupplementLineEntity_HI> lines = getLines(param);
		List<PlmSupplementLineEntity_HI_RO> insideLines = getLinesByInside(queryParamJSON
				.getInteger("headId").toString());

		JSONObject query = new JSONObject();
		String orderType = "";
		query.put("plmSupplementHeadId", queryParamJSON.getInteger("headId")
				.toString());
		List<PlmSupplementHeadEntity_HI> header = getHeader(query);
		orderType = header.get(0).getOrderType();

		// 单据内部校验，区分开通和停补
		for (int k = 0; k < insideLines.size(); k++) {
			if ("0".equals(insideLines.get(k).getSupplementStatus())
					|| "2".equals(insideLines.get(k).getSupplementStatus())
					|| "4".equals(insideLines.get(k).getSupplementStatus())) {
				storeMapFrOpen.add(changeEntity(insideLines.get(k)));
			}
			if ("1".equals(insideLines.get(k).getSupplementStatus())
					|| "3".equals(insideLines.get(k).getSupplementStatus())
					|| "5".equals(insideLines.get(k).getSupplementStatus())) {
				storeMapFrClose.add(changeEntity(insideLines.get(k)));
			}

		}
		// 内部表层校验
		JSONArray array = checkInsideInfo(lines);
		if (array != null) {
			processErrArr(lines, array);
			queryParamJSON.put("errors", array);
			return queryParamJSON;
		}

		Boolean statu = checkConfiltInside(storeMapFrOpen, storeMapFrClose);
		if (statu) {
			throw new Exception("相同状态货品存在生效日期冲突！");
		}

		// 内部校验
		Map<String, String> insideInfo = checkInside(storeMapFrOpen,
				storeMapFrClose);
		if (insideInfo != null && insideInfo.size() != 0) {
			StringBuffer str = new StringBuffer();
			str.append("区域中店铺"
					+ insideInfo.get("area").substring(1,
							insideInfo.get("area").length()) + "与其他行数据存在冲突");
			throw new Exception(str.toString());
		}

		JSONArray errArray = new JSONArray();
		// PlmSupplementHeadEntity_HI entity =
		// this.getById(queryParamJSON.getInteger("headId"));
		if (lines != null && lines.size() > 0) {
			for (int i = 0; i < lines.size(); i++) {
				// JSONObject product = new JSONObject();
				PlmSupplementLineEntity_HI line = lines.get(i);
				if (line.getRmsCode() == null || "".equals(line.getRmsCode())) {
					throw new Exception("请选择正确的货品，货品不存！");
				}

				if (("7".equals(line.getStopReason()) && !"1".equals(orderType))) {
					throw new Exception("原因填写异常，非陈列图订单！");
				}
				List<PlmSupShopEntity_HI_RO> shops = getShopsByType(line);

				// String state = queryParamJSON.getString("state");

				// String confliPro =
				// getConflictLines(queryParamJSON.getString("headId"),lines);
				// if (!SaafToolUtils.isNullOrEmpty(confliPro)) {
				// throw new Exception(confliPro);
				// }

				// Map<String, String> confliShop =
				// getCheckingLinesShop(queryParamJSON.getString("headId"),shops);
				Map<String, String> confliShop = checkShop(line, shops);
				// if (confliShop != null && confliShop.size() != 0) {
				// StringBuffer str = new StringBuffer();
				// str.append("区域中店铺"+confliShop.get("area").substring(1,confliShop.get("area").length())+"与审核中数据存在冲突");
				// throw new Exception(str.toString());
				// }

				if (confliShop != null && confliShop.size() != 0) {
					String errMsg = "区域中地点"
							+ confliShop.get("area").substring(1)
							+ "与审核中数据存在冲突，冲突审核中单据为："
							+ confliShop.get("bill").substring(1);
					JSONObject errRow = new JSONObject();
					errRow.put("ERR_MESSAGE", errMsg);
					errRow.put("ROW_NUM", i + 1);
					errArray.add(errRow);
					continue;
				}
				if (line.getStoreType() != null
						&& !"".equals(line.getStoreType())) {
					// setQueryParam(line, product);
					// List<PlmSupShopEntity_HI_RO> shops = getShops(product);

					// List<PlmSupShopEntity_HI_RO> exShops =
					// getExShops(product);
					if (line.getSupplementStatus().equals("1")) {
						// 如果是关停店铺就要把重复的店铺去掉再做保存
						// processDup(shops, null, storeMapFrClose, line);
						// processDup(shops, exShops, storeMapFrClose, dupList);
						// 需要更新仓库记录关停的店铺数
						// Map<String, List<PlmSupShopEntity_HI>> map =
						// merge(newShops);
						// if (map != null){
						// for (List<PlmSupShopEntity_HI> list : map.values()){
						// PlmSupShopEntity_HI en = list.get(0);
						// param.put("warehouseCode", en.getWc());
						// param.put("productCode", en.getItem());
						// PlmSupWarehouseEntity_HI entity =
						// getWareHouse(param);
						// //如果开通时无法找到对应仓库则不处理
						// //只有对应仓库不为空，减少开通的店铺数
						// if (entity == null) {
						// if (line.getStoreType() != null &&
						// !"".equals(line.getStoreType())){
						// throw new Exception("无法找到仓库: " + en.getWc());
						// }
						// }
						// }
						// }
						// 更新关停的店铺
						// updateWCStop(map);
					} else if (line.getSupplementStatus().equals("0")) {
						// 如果是开通店铺就从关停的店铺里面删除掉
						// processInter(shops, null, storeMapFrOpen, line);
						// Map<String, List<PlmSupShopEntity_HI>> map =
						// merge(newShops);
						// 更新开通的店铺
						// updateWCOpen(map);
						// if (map != null){
						// for (List<PlmSupShopEntity_HI> list : map.values()){
						// PlmSupShopEntity_HI en = list.get(0);
						// param.put("warehouseCode", en.getWc());
						// param.put("productCode", en.getItem());
						// PlmSupWarehouseEntity_HI entity =
						// getWareHouse(param);
						// //如果开通时无法找到对应仓库则不处理
						// //只有对应仓库不为空，减少开通的店铺数
						// if (entity == null) {
						// if (line.getStoreType() != null &&
						// !"".equals(line.getStoreType())){
						// throw new Exception("无法找到仓库：" + en.getWc());
						// }
						// }
						// }
						// }
					}
				}
			}
			if (errArray.size() != 0) {
				processErrArr(lines, errArray);
				queryParamJSON.put("errors", errArray);
				return queryParamJSON;
			}
		}
		return queryParamJSON;
	}

	private void processErrArr(List<PlmSupplementLineEntity_HI> lines,
			JSONArray array) {
		for (int i = 0; i < array.size(); i++) {
			JSONObject o = array.getJSONObject(i);
			int rowNum = o.getInteger("ROW_NUM");
			PlmSupplementLineEntity_HI line = lines.get(rowNum - 1);
			o.put("rmsCode", line.getRmsCode());
			o.put("productName", line.getProductName());
			o.put("store", line.getStore());
			o.put("meter", line.getMeter());
			o.put("supplementStatus", line.getSupplementStatus());
			o.put("stopReason", line.getStopReason());
		}
	}

	// 针对页面内部货品做校验
	private JSONArray checkInsideInfo(List<PlmSupplementLineEntity_HI> list)
			throws Exception {
		Map<String, Map<String, String>> sumMap = new HashMap<>();
		Map<String, String> errorMap = new HashMap<>();

		// 填充数据
		for (int k = 0; k < list.size(); k++) {
			Map<String, String> map = new HashMap<>();
			String meter = "";

			List<PlmSupShopEntity_HI_RO> shops = getShopsByType(list.get(k));
			for (int i = 0; i < shops.size(); i++) {
				meter = meter + "|" + shops.get(i).getShopCode();
			}
			if (!"".equals(meter) && meter.split("\\|").length > 0) {
				meter = meter.substring(1, meter.length());
			}
			map.put("row", k + "");
			map.put("meter", meter);
			map.put("rmsCode", list.get(k).getRmsCode());
			map.put("exeDate", list.get(k).getExeDate().getTime() + "");
			if (list.get(k).getExpireDate() != null) {
				map.put("expireDate", list.get(k).getExpireDate().getTime()
						+ "");
			}
			map.put("status", list.get(k).getSupplementStatus());
			sumMap.put(k + "", map);
		}
		// 两个循环，确保前后数据都能匹配校验
		for (int m = 0; m < list.size(); m++) {
			Map<String, String> map1 = sumMap.get(m + "");
			for (int n = 0; n < list.size(); n++) {
				Map<String, String> map2 = sumMap.get(n + "");
				// 货号相同
				if (m != n && map1.get("rmsCode").equals(map2.get("rmsCode"))) {
					// 生效地点有重复
					if (checkMeter(map1.get("meter"), map2.get("meter"))) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						String date1 = sdf.format(new Date(Long.parseLong(map1
								.get("exeDate"))));
						String date2 = sdf.format(new Date(Long.parseLong(map2
								.get("exeDate"))));

						// 都为开通状态
						if ((map1.get("status").equals("0")
								|| map1.get("status").equals("2") || map1.get(
								"status").equals("4"))
								&& (map2.get("status").equals("0")
										|| map2.get("status").equals("2") || map2
										.get("status").equals("4"))) {
							String date3 = "";
							String date4 = "";
							if (!ObjectUtils.isEmpty(map1.get("expireDate"))
									&& !ObjectUtils.isEmpty(map2
											.get("expireDate"))) {
								date3 = sdf.format(new Date(Long.parseLong(map1
										.get("expireDate"))));
								date4 = sdf.format(new Date(Long.parseLong(map2
										.get("expireDate"))));
							}
							if (!date1.equals(date2) || !date3.equals(date4)) {
								errorMap.put(
										map1.get("row") + "-"
												+ map1.get("rmsCode"),
										map1.get("meter"));
								errorMap.put(
										map2.get("row") + "-"
												+ map1.get("rmsCode"),
										map2.get("meter"));
							}
						}
						// 停补开通状态,判断时间区间
						if ((map1.get("status").equals("1")
								|| map1.get("status").equals("3") || map1.get(
								"status").equals("5"))
								&& (map2.get("status").equals("0")
										|| map2.get("status").equals("2") || map2
										.get("status").equals("4"))) {
//							String date4 = sdf.format(new Date(Long
//									.parseLong(map2.get("expireDate"))));
//							if (!ObjectUtils.isEmpty(map2.get("expireDate"))) {
//								Long a = Long.parseLong(map1.get("exeDate"));
//								Long b = Long.parseLong(map2.get("expireDate"));
//								if (a.longValue() < b.longValue()) {
//									errorMap.put(
//											map1.get("row") + "-"
//													+ map1.get("rmsCode"),
//											map1.get("meter"));
//									errorMap.put(
//											map2.get("row") + "-"
//													+ map1.get("rmsCode"),
//											map2.get("meter"));
//								}
//							}
                            if (ObjectUtils.isEmpty(map2.get("expireDate")) && ObjectUtils.isEmpty(map1.get("expireDate"))) {
                                errorMap.put(
                                        map1.get("row") + "-"
                                                + map1.get("rmsCode"),
                                        map1.get("meter"));
                                errorMap.put(
                                        map2.get("row") + "-"
                                                + map1.get("rmsCode"),
                                        map2.get("meter"));
                            }
                            if (!ObjectUtils.isEmpty(map2.get("expireDate")) && ObjectUtils.isEmpty(map1.get("expireDate"))) {
                                Long a = Long.parseLong(map1.get("exeDate"));
                                Long b = Long.parseLong(map2.get("expireDate"));
                                if (a.longValue() <= b.longValue()) {
                                    errorMap.put(
                                            map1.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map1.get("meter"));
                                    errorMap.put(
                                            map2.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map2.get("meter"));
                                }

                            }
                            if (ObjectUtils.isEmpty(map2.get("expireDate")) && !ObjectUtils.isEmpty(map1.get("expireDate"))) {
                                Long a = Long.parseLong(map2.get("exeDate"));
                                Long b = Long.parseLong(map1.get("expireDate"));
                                if (a.longValue() <= b.longValue()) {
                                    errorMap.put(
                                            map1.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map1.get("meter"));
                                    errorMap.put(
                                            map2.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map2.get("meter"));
                                }

                            }
                            if (!ObjectUtils.isEmpty(map2.get("expireDate")) && !ObjectUtils.isEmpty(map1.get("expireDate"))) {
                                Long firStart = Long.parseLong(map1.get("exeDate"));
                                Long firEnd = Long.parseLong(map1.get("expireDate"));
                                Long secStart = Long.parseLong(map2.get("exeDate"));
                                Long secEnd = Long.parseLong(map2.get("expireDate"));
                                if (firStart.longValue() <= secEnd.longValue() && secStart.longValue() <= firEnd.longValue()) {
                                    errorMap.put(
                                            map1.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map1.get("meter"));
                                    errorMap.put(
                                            map2.get("row") + "-"
                                                    + map1.get("rmsCode"),
                                            map2.get("meter"));
                                }
                            }
						}
						// 开通停补状态,判断时间区间
//						if ((map1.get("status").equals("0")
//								|| map1.get("status").equals("2") || map1.get(
//								"status").equals("4"))
//								&& (map2.get("status").equals("1")
//										|| map2.get("status").equals("3") || map2
//										.get("status").equals("5"))) {
//							String date3 = sdf.format(new Date(Long
//									.parseLong(map1.get("expireDate"))));
//							if (!ObjectUtils.isEmpty(map2.get("expireDate"))) {
//								Long a = Long.parseLong(map2.get("exeDate"));
//								Long b = Long.parseLong(map1.get("expireDate"));
//								if (a.longValue() < b.longValue()) {
//									errorMap.put(
//											map1.get("row") + "-"
//													+ map1.get("rmsCode"),
//											map1.get("meter"));
//									errorMap.put(
//											map2.get("row") + "-"
//													+ map1.get("rmsCode"),
//											map2.get("meter"));
//								}
//							}
//						}
					}
				}
			}
		}
		JSONArray errArray = new JSONArray();
		// 处理校验信息
		for (int j = 0; j < list.size(); j++) {
			if (errorMap.get(j + "-" + list.get(j).getRmsCode()) != null) {
				String errMsg = "生效地点的店铺:"
						+ errorMap.get(j + "-" + list.get(j).getRmsCode())
						+ "与其他行数据存在冲突！";
				JSONObject errRow = new JSONObject();
				errRow.put("ERR_MESSAGE", errMsg);
				errRow.put("ROW_NUM", j + 1);
				errArray.add(errRow);
				continue;
			}
		}

		if (errArray.size() != 0) {
			return errArray;
		}
		return null;
	}

	// 查询两个生效地点是否存在地点重复
	private Boolean checkMeter(String meter1, String meter2) {
		String[] m1 = meter1.split("\\|");
		String[] m2 = meter2.split("\\|");
		for (int m = 0; m < m1.length; m++) {
			for (int n = 0; n < m2.length; n++) {
				if (m1[m].equals(m2[n])) {
					return true;
				}
			}
		}
		return false;
	}

	// 查询时间区域是否有交集,状态都为开通时
	private Boolean checkOpenDate(Date start1, Date start2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(start1);
		String date2 = sdf.format(start2);
		if (date1.equals(date2)) {
			return true;
		}
		return false;
	}

	// 查询时间区域是否有交集,状态都为停补时
	private Boolean checkCloseDate(Date start1, Date start2, Date end1,
			Date end2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(start1);
		String date2 = sdf.format(start2);
		// if(line.getExpireDate().getTime()<date1.getTime()||line.getExeDate().getTime()>date2.getTime()){
		//
		// }else{
		//
		// }
		return false;
	}

	// 针对页面内部货品做校验
	private Boolean checkConfiltInside(
			List<PlmSupplementLineEntity_HI> storeMapFrOpen,
			List<PlmSupplementLineEntity_HI> storeMapFrClose) {
		Boolean sta = false;
		if (storeMapFrOpen.size() > 1 && storeMapFrClose.size() > 0) {
			// 校验相同行不同生效日期时，提示冲突
			for (PlmSupplementLineEntity_HI open1 : storeMapFrOpen) {
				for (PlmSupplementLineEntity_HI open2 : storeMapFrOpen) {
					if (open1.getRmsCode().equals(open2.getRmsCode())
							&& open1.getMeter().equals(open2.getMeter())
							&& open1.getStore().equals(open2.getStore())
							&& open1.getSupplementStatus().equals(
									open2.getSupplementStatus())
							&& !open1.getPlmSupplementLineId().equals(
									open2.getPlmSupplementLineId())
							&& !open1.getExeDate().equals(open2.getExeDate())) {
						sta = true;
					}
				}
			}
		}
		if (storeMapFrClose.size() > 1) {
			for (PlmSupplementLineEntity_HI open1 : storeMapFrClose) {
				for (PlmSupplementLineEntity_HI open2 : storeMapFrClose) {
					if (open1.getRmsCode().equals(open2.getRmsCode())
							&& open1.getMeter().equals(open2.getMeter())
							&& open1.getStore().equals(open2.getStore())
							&& open1.getSupplementStatus().equals(
									open2.getSupplementStatus())
							&& !open1.getPlmSupplementLineId().equals(
									open2.getPlmSupplementLineId())
							&& !open1.getExeDate().equals(open2.getExeDate())) {
						sta = true;
					}
				}
			}
		}
		return sta;
	}

	// 针对页面内部货品做校验
	private Map<String, String> checkInside(
			List<PlmSupplementLineEntity_HI> storeMapFrOpen,
			List<PlmSupplementLineEntity_HI> storeMapFrClose) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> infoMap = new HashMap<>();
		// 如果有多条开通时
		if (storeMapFrOpen.size() > 0) {
			// 双重自循环
			for (PlmSupplementLineEntity_HI open1 : storeMapFrOpen) {
				List<PlmSupShopEntity_HI_RO> shops1 = getShopsByType(open1);
				Date expireDate = open1.getExpireDate();
				String exeDate1 = sdf.format(open1.getExeDate());
				String expireDate1 = null;
				if (expireDate != null) {
					expireDate1 = sdf.format(open1.getExpireDate());
				} else {
					expireDate1 = sdf.format(new Date(2099, 1, 1));
				}
				for (PlmSupplementLineEntity_HI open2 : storeMapFrOpen) {
					if (open1.getPlmSupplementLineId() != open2
							.getPlmSupplementLineId()
							&& open1.getRmsCode().equals(open2.getRmsCode())) {
						List<PlmSupShopEntity_HI_RO> shops2 = getShopsByType(open2);
						String ereaList = checkShopInside(shops1, shops2);
						// 存在重复则判断时间上是否有交集
						if (!"0".equals(ereaList)) {
							String exeDate2 = sdf.format(open2.getExeDate());
							Date expire = open2.getExpireDate();
							if (ObjectUtils.isEmpty(open2.getExpireDate())) {
								expire = new Date(2099, 1, 1);
							}
							String expireDate2 = sdf.format(expire);
							if (!exeDate1.equals(exeDate2)
									|| !expireDate1.equals(expireDate2)) {
								infoMap.put("rmsCode", open2.getRmsCode());
								String str = infoMap.get("area") == null ? ""
										: infoMap.get("area");
								str = str.replace("0,", str);
								infoMap.put("area", str + "," + ereaList);
							}
						}
					}
				}
			}
		}

		// 如果有
		if (storeMapFrOpen.size() > 0 && storeMapFrClose.size() > 0) {

			for (PlmSupplementLineEntity_HI open : storeMapFrOpen) {
				List<PlmSupShopEntity_HI_RO> shops1 = getShopsByType(open);
				// String exeDate1 = sdf.format(open.getExeDate());
				// String expireDate1 = sdf.format(open.getExpireDate());
				for (PlmSupplementLineEntity_HI close : storeMapFrClose) {
					if (open.getPlmSupplementLineId() != close
							.getPlmSupplementLineId()
							&& open.getRmsCode().equals(close.getRmsCode())) {
						List<PlmSupShopEntity_HI_RO> shops2 = getShopsByType(close);
						String ereaList = checkShopInside(shops1, shops2);
						// 存在重复则判断时间上是否有交集
						if (!"0".equals(ereaList)) {
							;
							if (open.getExpireDate().getTime() < close
									.getExeDate().getTime()) {

							} else {
								infoMap.put("rmsCode", close.getRmsCode());
								String str = infoMap.get("area") == null ? ""
										: infoMap.get("area");
								str = str.replace("0,", str);
								infoMap.put("area", str + "," + ereaList);
							}
						}
					}
				}
			}
		}
		return infoMap;
	}

	// 重复则返回1
	private String checkShopInside(List<PlmSupShopEntity_HI_RO> shops1,
			List<PlmSupShopEntity_HI_RO> shops2) {
		String ereaList = "0";
		for (PlmSupShopEntity_HI_RO shop1 : shops1) {
			for (PlmSupShopEntity_HI_RO shop2 : shops2) {
				if (shop1.getShopCode().equals(shop2.getShopCode())) {
					ereaList = ereaList + "," + shop1.getArea();
				}
			}
		}
		return ereaList;
	}

	public void deleteCahe() {
		Set<String> shopKeys = redisKeys("PlmSupplement*");
		for (String str: shopKeys) {
			LOGGER.info("key: " + str + "============================");
			jedis.del(str);
		}
//		List<PlmSupplementHeadEntity_HI_RO> findListResult = plmSupplementHeadDAO_HI_RO
//				.findList("select * from PLM_SUPPLEMENT_HEAD where order_status='1'",
//						new HashMap<String, Object>());
//		for (PlmSupplementHeadEntity_HI_RO ent : findListResult) {
//			JSONObject json = new JSONObject();
//			json.put("headId", ent.getPlmSupplementHeadId());
//			List<PlmSupplementLineEntity_HI> lines = getLines(json);
//			for (PlmSupplementLineEntity_HI line : lines) {
//				String str1 = jedis.get("PlmSupplement" + ent.getSupCode()
//						+ line.getRmsCode());
//				jedis.del(ent.getSupCode() + line.getRmsCode());
//				String str2 = jedis.get("PlmSupplement" + ent.getSupCode()
//						+ line.getRmsCode());
//				System.out.println(str1 + "====" + str2);
//			}
//		}

	}

	// 拿当前所有货品对应的店铺去对比审批中货品的店铺，有重复则返回
	private List<PlmSupShopEntity_HI> getRepeatData(
			List<PlmSupShopEntity_HI> newShop) {

		return null;
	}

	private void setQueryParam(PlmSupplementLineEntity_HI line,
			JSONObject product) {
		product.put("item", line.getRmsCode());
		if (line.getWarehouse() != null && !"".equals(line.getWarehouse())) {
			if (!line.getWarehouse().trim().equals("All")) {
				product.put("wc", line.getWarehouse());
			}
		}
		if (line.getRegion() != null && !"".equals(line.getRegion())) {
			if (!line.getRegion().trim().equals("All")) {
				product.put("region", line.getRegion());
			}
		}
		if (line.getMeter() != null && !"".equals(line.getMeter())) {
			product.put("meter", line.getMeter());
		}
		if (line.getPogCode() != null && !"".equals(line.getPogCode())) {
			product.put("pogCode", line.getPogCode());
		}
		if (line.getStore() != null && !"".equals(line.getStore())) {
			product.put("shopCode", line.getStore());
		}
	}

	private void saveCloseLog(PlmSupShopEntity_HI shop, String supCode) {
//		List<PlmSupLogEntity_HI> l = getLog(shop.getItem(), shop.getWc());
		PlmSupLogEntity_HI log;
//		if (l.size() > 0) {
//			log = l.get(0);
//		} else {
//		}
		log = new PlmSupLogEntity_HI();
		log.setLogType("细节表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getShopCode());
		log.setUpdateType("deactive");
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupLogDAO_HI.saveOrUpdate(log);
	}

	private void saveOpenLog(PlmSupShopEntity_HI shop, String supCode) {
//		List<PlmSupLogEntity_HI> l = getLog(shop.getItem(), shop.getWc());
		PlmSupLogEntity_HI log;
//		if (l.size() > 0) {
//			log = l.get(0);
//		} else {
//		}
		log = new PlmSupLogEntity_HI();
		log.setLogType("细节表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getShopCode());
		log.setUpdateType("active");
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupLogDAO_HI.saveOrUpdate(log);
	}

	private void saveBaseCloseLog(PlmSupShopEntity_HI shop, String supCode) {
//		List<PlmSupLogEntity_HI> l = getLog(shop.getItem(), shop.getWc());
		PlmSupLogEntity_HI log;
//		if (l.size() > 0) {
//			log = l.get(0);
//		} else {
//		}
		log = new PlmSupLogEntity_HI();
		log.setLogType("基础表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getWc());
		log.setUpdateType("update");
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupLogDAO_HI.saveOrUpdate(log);
	}

	private List<PlmSupShopLogEntity_HI> getLog(String item, String wc) {
		JSONObject queryParamJSON = new JSONObject();
		queryParamJSON.put("productCode", item);
		queryParamJSON.put("shopCode", wc);
		StringBuffer sql = new StringBuffer(
				" from PlmSupShopLogEntity_HI where 1=1 ");
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		SaafToolUtils.parperHbmParam(PlmSupLogEntity_HI.class,
				queryParamJSON, sql, queryParamMap);
		return plmSupShopLogDAO_HI.findList(sql, queryParamMap);
	}

	private void saveCloseShopLog(PlmSupShopEntity_HI shop, String supCode) {
		List<PlmSupShopLogEntity_HI> l = getLog(shop.getItem(), shop.getWc());
		PlmSupShopLogEntity_HI log;
		if (l.size() > 0) {
			log = l.get(0);
		} else {
			log = new PlmSupShopLogEntity_HI();
		}
		log.setLogType("细节表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getShopCode());
		log.setUpdateType("deactive");// 新增
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupShopLogDAO_HI.saveOrUpdate(log);
	}

	private void saveOpenShopLog(PlmSupShopEntity_HI shop, String supCode) {
		List<PlmSupShopLogEntity_HI> l = getLog(shop.getItem(), shop.getWc());
		PlmSupShopLogEntity_HI log;
		if (l.size() > 0) {
			log = l.get(0);
		} else {
			log = new PlmSupShopLogEntity_HI();
		}
		log.setLogType("细节表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getShopCode());
		log.setUpdateType("active");// 删除
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupShopLogDAO_HI.saveOrUpdate(log);
	}

	private void saveBaseCloseShopLog(PlmSupShopEntity_HI shop, String supCode) {
		PlmSupShopLogEntity_HI log = new PlmSupShopLogEntity_HI();
		log.setLogType("基础表");
		log.setProductCode(shop.getItem());
		log.setShopCode(shop.getWc());
		log.setUpdateType("update");// 更新
		log.setCreationDate(new Date());
		log.setSupCode(supCode);
		plmSupShopLogDAO_HI.saveOrUpdate(log);
	}

	private void baseLog(Map<String, List<PlmSupShopEntity_HI_RO>> logMap, String supCode) {
		if (logMap != null) {
			for (List<PlmSupShopEntity_HI_RO> list : logMap.values()) {
				PlmSupShopEntity_HI_RO entity = list.get(0);
				PlmSupShopEntity_HI en = new PlmSupShopEntity_HI();
				en.setItem(entity.getItem());
				en.setWc(entity.getWc());
				saveBaseCloseLog(en, supCode);
			}
		}
	}

	private void updateWCOpen(Map<String, List<PlmSupShopEntity_HI>> map,
			PlmSupplementLineEntity_HI line, String supCode) {
		if (map != null) {
			JSONObject param = new JSONObject();
			for (List<PlmSupShopEntity_HI> list : map.values()) {
				PlmSupShopEntity_HI en = list.get(0);
				param.put("warehouseCode", en.getWc());
				param.put("productCode", en.getItem());
				PlmSupWarehouseEntity_HI entity = getWareHouse(param);
				// 如果开通时无法找到对应仓库则不处理
				// 只有对应仓库不为空，减少开通的店铺数
				if (entity != null) {
					// if (entity.getStopShops() == entity.getTotalShops()){
					// entity.setState("");
					// }
//					if (entity.getStopShops().intValue() == entity
//							.getTotalShops().intValue()) {
//						entity.setState("1");
//					} else {
//						entity.setState("0");
//					}
					entity.setStopShops(entity.getStopShops() - list.size());
					setParam(entity, line);
					setOrderStatus(entity);
					plmSupWarehouseDAO_HI.saveOrUpdate(entity);

					JSONObject queryParamJSON = new JSONObject();
					queryParamJSON.put("productCode", entity.getProductCode());
					queryParamJSON.put("shopCode", entity.getWarehouseCode());
					List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
							.findShopList(queryParamJSON);
//					if (shopList.size() <= 0) {
//						saveBaseCloseShopLog(en, supCode);
//					}
//					saveBaseCloseLog(en, supCode);
				}
			}
		} else {
			updateNullMap(line);
		}
	}

	private void setParam(PlmSupWarehouseEntity_HI entity, PlmSupplementLineEntity_HI line) {
		if (line.getStore().equals("0")) {
			if (line.getStopReason() != null) {
				entity.setReason(line.getStopReason());
			}
			if (line.getStoreType() != null) {
				entity.setStoreWay(line.getStoreType());
			}
			if (line.getSupplementStatus() != null) {
				entity.setState(line.getSupplementStatus());
			}
		}
	}

	private void updateWCOpenOnly(Map<String, List<PlmSupShopEntity_HI>> map,
			PlmSupplementLineEntity_HI line, String supCode) {
		if (map != null) {
			JSONObject param = new JSONObject();
			for (List<PlmSupShopEntity_HI> list : map.values()) {
				PlmSupShopEntity_HI en = list.get(0);
				param.put("warehouseCode", en.getWc());
				param.put("productCode", en.getItem());
				PlmSupWarehouseEntity_HI entity = getWareHouse(param);
				// 如果开通时无法找到对应仓库则不处理
				// 只有对应仓库不为空，减少开通的店铺数
				if (entity != null) {
					// entity.setStopShops(entity.getStopShops() - list.size());
					// if (entity.getStopShops() == entity.getTotalShops()){
					// entity.setState("");
					// }
//					String status = getStatus(line);
//					entity.setState(status);
					setParam(entity, line);
					setOrderStatus(entity);

					// if (entity.getStopShops() == entity.getTotalShops()){
					// } else {
					// entity.setState("0");
					// }
					plmSupWarehouseDAO_HI.saveOrUpdate(entity);

//					JSONObject queryParamJSON = new JSONObject();
//					queryParamJSON.put("productCode", entity.getProductCode());
//					queryParamJSON.put("shopCode", entity.getWarehouseCode());
//					List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//							.findShopList(queryParamJSON);
//					if (shopList.size() <= 0) {
//						saveBaseCloseShopLog(en, supCode);
//					}

					saveBaseCloseLog(en, supCode);
				}
			}
		} else {
			if (line != null) {
				String list = line.getMeter();
				String[] l = list.split("\\|");
				for (String e : l) {
					if (e != null && !"".equals(e)) {
						JSONObject param = new JSONObject();
						param.put("warehouseCode", e);
						param.put("productCode", line.getRmsCode());
						PlmSupWarehouseEntity_HI entity = getWareHouse(param);
						// 如果开通时无法找到对应仓库则不处理
						// 只有对应仓库不为空，减少开通的店铺数
						if (entity != null) {
							// entity.setStopShops(entity.getStopShops() -
							// list.size());
							// if (entity.getStopShops() ==
							// entity.getTotalShops()){
							// entity.setState("");
							// }
							if (line.getStore().equals("0")) {
								entity.setState(line.getSupplementStatus());
								entity.setReason(line.getStopReason());
								entity.setStoreWay(line.getStoreType());
							}
							// String status = getStatus(line);
							// entity.setState(status);

							// if (entity.getStopShops() ==
							// entity.getTotalShops()){
							// } else {
							// entity.setState("0");
							// }
							setOrderStatus(entity);
							plmSupWarehouseDAO_HI.saveOrUpdate(entity);
//							PlmSupLogEntity_HI log = new PlmSupLogEntity_HI();
//							log.setLogType("基础表");
//							log.setProductCode(line.getRmsCode());
//							log.setShopCode(e);
//							log.setUpdateType("update");
//							log.setCreationDate(new Date());
//							log.setSupCode(entity.getSupplierCode());
//							plmSupLogDAO_HI.saveOrUpdate(log);
							// saveBaseCloseLog(en,entity.getSupplierCode());
						}

					}
				}
			}
		}
	}

	private void setOrderStatus(PlmSupWarehouseEntity_HI en) {
		if ((en.getState().equals("2") || en.getState().equals("4"))
				&& en.getStopShops().intValue() == en.getTotalShops().intValue()) {
			en.setOrderStatus("0");
			en.setOrderReason("1");
		} else if ((en.getState().equals("2") || en.getState().equals("4"))
				&& en.getStopShops().intValue() != en.getTotalShops().intValue()) {
			en.setOrderStatus("1");
			en.setOrderReason("2");
		} else if ((en.getState().equals("3") || en.getState().equals("5"))) {
			en.setOrderStatus("0");
			en.setOrderReason("0");
		}
		en.setOrderUpdateDate(new Date());
	}

	private void updateWCStop(Map<String, List<PlmSupShopEntity_HI>> map,
			PlmSupplementLineEntity_HI line, String supCode) {
		if (map != null) {
			JSONObject param = new JSONObject();
			for (List<PlmSupShopEntity_HI> list : map.values()) {
				PlmSupShopEntity_HI en = list.get(0);
				param.put("warehouseCode", en.getWc());
				if (en.getWc() == null) {
					continue;
				}
				param.put("productCode", en.getItem());
				PlmSupWarehouseEntity_HI entity = getWareHouse(param);
				if (entity == null) {
					// 如果仓库不存在就新增
					// 初始仓库实体
					// setWarehouseEntity(entity);
					// plmSupWarehouseDAO_HI.saveOrUpdate(entity);
				} else {
					// 如果仓库存在就更新
					if (entity.getTotalShops() < entity.getStopShops()
							+ list.size()) {
						entity.setStopShops(entity.getTotalShops());
					} else {
						entity.setStopShops(entity.getStopShops() + list.size());
					}
					setParam(entity, line);
//					if (entity.getStopShops().intValue() == entity.getTotalShops().intValue()){
//					 	entity.setState("5");
//					}

					setOrderStatus(entity);
					plmSupWarehouseDAO_HI.update(entity);

//					JSONObject queryParamJSON = new JSONObject();
//					queryParamJSON.put("productCode", entity.getProductCode());
//					queryParamJSON.put("shopCode", entity.getWarehouseCode());
//					List<PlmSupShopLogEntity_HI_RO> shopList = plmSupShopLogServer
//							.findShopList(queryParamJSON);
//					if (shopList.size() <= 0) {
//						saveBaseCloseShopLog(en, supCode);
//					}

//					saveBaseCloseLog(en, supCode);
				}
			}
		} else {
			updateNullMap(line);
		}
	}

	private void updateWCStopOnly(Map<String, List<PlmSupShopEntity_HI>> map,
			PlmSupplementLineEntity_HI line, String supCode) {
		if (map != null) {
			JSONObject param = new JSONObject();
			for (List<PlmSupShopEntity_HI> list : map.values()) {
				PlmSupShopEntity_HI en = list.get(0);
				param.put("warehouseCode", en.getWc());
				if (en.getWc() == null) {
					continue;
				}
				param.put("productCode", en.getItem());
				PlmSupWarehouseEntity_HI entity = getWareHouse(param);
				if (entity == null) {
					// 如果仓库不存在就新增
					// 初始仓库实体
					// setWarehouseEntity(entity);
					// plmSupWarehouseDAO_HI.saveOrUpdate(entity);
				} else {
					// 如果仓库存在就更新
					// entity.setStopShops(entity.getStopShops() + list.size());
//					String status = getStatus(line);
//					entity.setState(status);
					setParam(entity, line);
					// if (entity.getStopShops() == entity.getTotalShops()){
					// } else {
					// entity.setState("0");
					// }
					setOrderStatus(entity);
					plmSupWarehouseDAO_HI.saveOrUpdate(entity);
					saveBaseCloseLog(en, supCode);
				}
			}
		} else {
			updateNullMap(line);
		}
	}

	private void updateNullMap(PlmSupplementLineEntity_HI line) {
		if (line != null) {
			String list = line.getMeter();
			String[] l = list.split("\\|");
			for (String e : l) {
				if (e != null && !"".equals(e)) {
					JSONObject param = new JSONObject();
					param.put("warehouseCode", e);
					param.put("productCode", line.getRmsCode());
					PlmSupWarehouseEntity_HI entity = getWareHouse(param);
					// 如果开通时无法找到对应仓库则不处理
					// 只有对应仓库不为空，减少开通的店铺数
					if (entity != null) {
						// entity.setStopShops(entity.getStopShops() -
						// list.size());
						// if (entity.getStopShops() == entity.getTotalShops()){
						// entity.setState("");
						// }
//						String status = getStatus(line);
//						entity.setState(status);
						setParam(entity, line);
						// if (entity.getStopShops() == entity.getTotalShops()){
						// } else {
						// entity.setState("0");
						// }
						setOrderStatus(entity);
						plmSupWarehouseDAO_HI.saveOrUpdate(entity);
//						PlmSupLogEntity_HI log = new PlmSupLogEntity_HI();
//						log.setLogType("基础表");
//						log.setProductCode(line.getRmsCode());
//						log.setShopCode(e);
//						log.setUpdateType("update");
//						log.setCreationDate(new Date());
//						log.setSupCode(entity.getSupplierCode());
//						plmSupLogDAO_HI.saveOrUpdate(log);
						// saveBaseCloseLog(en,entity.getSupplierCode());
					}

				}
			}
		}
	}

	private String getStatus(PlmSupplementLineEntity_HI line) {
		String status = line.getSupplementStatus();
		if (!status.equals("0") && !status.equals("1")) {
			if (status.equals("2") || status.equals("4")) {
				status = "0";
			} else if (status.equals("3") || status.equals("5")) {
				status = "1";
			}
		}
		return status;
	}

	private PlmSupWarehouseEntity_HI getWareHouse(JSONObject queryParamJSON) {
		List<PlmSupWarehouseEntity_HI> list = plmSupWarehouse
				.getExWarehouses(queryParamJSON);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private Map<String, List<PlmSupShopEntity_HI>> merge(
			List<PlmSupShopEntity_HI> newShops) {
		Map<String, List<PlmSupShopEntity_HI>> map = null;
		if (newShops != null && newShops.size() > 0) {
			map = new HashMap<>();
			for (int k = 0; k < newShops.size(); k++) {
				PlmSupShopEntity_HI entity = newShops.get(k);
				if (entity != null) {
					if (map.get(entity.getWc()) == null) {
						List<PlmSupShopEntity_HI> entityList = new ArrayList<>();
						entityList.add(entity);
						map.put(entity.getWc(), entityList);
					} else {
						List<PlmSupShopEntity_HI> existedList = map.get(entity
								.getWc());
						existedList.add(entity);
					}
				}
			}
		} else {
			return null;
		}
		return map;
	}

	private Map<String, List<PlmSupShopEntity_HI_RO>> mergeLog(
			List<PlmSupShopEntity_HI_RO> newShops) {
		Map<String, List<PlmSupShopEntity_HI_RO>> map = null;
		if (newShops != null && newShops.size() > 0) {
			map = new HashMap<>();
			for (int k = 0; k < newShops.size(); k++) {
				PlmSupShopEntity_HI_RO entity = newShops.get(k);
				if (entity != null) {
					if (map.get(entity.getWc()) == null) {
						List<PlmSupShopEntity_HI_RO> entityList = new ArrayList<>();
						entityList.add(entity);
						map.put(entity.getWc(), entityList);
					} else {
						List<PlmSupShopEntity_HI_RO> existedList = map.get(entity
								.getWc());
						existedList.add(entity);
					}
				}
			}
		} else {
			return null;
		}
		return map;
	}

	/**
	 * 开通店铺时处理掉已经开通的店铺
	 * 
	 * @param shops
	 * @param exShops
	 * @return
	 */
	private List<PlmSupShopEntity_HI> processInter(
			List<PlmSupShopEntity_HI_RO> shops,
			List<PlmSupShopEntity_HI_RO> exShops,
			Map<String, Object> storeMapFrOpen) {
		List<PlmSupShopEntity_HI> list = new ArrayList<>();
		for (PlmSupShopEntity_HI_RO exshop : exShops) {
			for (PlmSupShopEntity_HI_RO shop : shops) {
				if (exshop.getShopCode().equals(shop.getShopCode())) {
					if (!storeMapFrOpen.containsKey(exshop.getItem() + ":"
							+ exshop.getShopCode())) {
						PlmSupShopEntity_HI s = new PlmSupShopEntity_HI();
						BeanUtils.copyProperties(exshop, s);
						list.add(s);
						storeMapFrOpen.put(s.getItem() + ":" + s.getShopCode(),
								"Y");
					}
				}
			}
		}
		return list;
	}

	/**
	 * 停补店铺时处理掉重复停补的店铺
	 * 
	 * @param shops
	 * @param exShops
	 */
	private List<PlmSupShopEntity_HI> processDup(
			List<PlmSupShopEntity_HI_RO> shops,
			List<PlmSupShopEntity_HI_RO> exShops, Map<String, Object> storeMap) {
		List<PlmSupShopEntity_HI> list = null;
		if (shops != null && shops.size() > 0) {
			list = new ArrayList<>();
			Map<String, PlmSupShopEntity_HI_RO> map = new HashMap<>();
			for (PlmSupShopEntity_HI_RO shop : shops) {
				boolean existed = false;
				if (exShops != null && exShops.size() > 0) {
					for (PlmSupShopEntity_HI_RO exShop : exShops) {
						if (shop.getShopCode().equals(exShop.getShopCode())) {
							existed = true;
							// PlmSupShopEntity_HI s = new
							// PlmSupShopEntity_HI();
							// BeanUtils.copyProperties(shop, s);
							// list.add(s);
						}
					}
				}

				if (!existed) {
					map.put(shop.getShopCode(), shop);
				}
			}
			for (PlmSupShopEntity_HI_RO s : map.values()) {
				if (!storeMap.containsKey(s.getItem() + ":" + s.getShopCode())) {
					PlmSupShopEntity_HI shop = new PlmSupShopEntity_HI();
					BeanUtils.copyProperties(s, shop);
					list.add(shop);
					storeMap.put(shop.getItem() + ":" + shop.getShopCode(),
							shop);
				}
			}
		}
		return list;
	}

	private List<PlmSupShopEntity_HI_RO> processDup(
			List<PlmSupShopEntity_HI_RO> shops,
			List<PlmSupShopEntity_HI_RO> exShops, Map<String, Object> storeMap,
			List<PlmSupShopEntity_HI_RO> dupResults) {
		List<PlmSupShopEntity_HI> list = null;
		// if (shops != null && shops.size() > 0 && exShops != null &&
		// exShops.size() > 0){
		list = new ArrayList<>();
		Map<String, PlmSupShopEntity_HI_RO> map = new HashMap<>();
		for (PlmSupShopEntity_HI_RO shop : shops) {
			boolean existed = false;
			for (PlmSupShopEntity_HI_RO exShop : exShops) {
				if (shop.getShopCode().equals(exShop.getShopCode())) {
					existed = true;
					dupResults.add(shop);
					// PlmSupShopEntity_HI s = new PlmSupShopEntity_HI();
					// BeanUtils.copyProperties(shop, s);
					// list.add(s);
				}
			}
			if (!existed) {
				map.put(shop.getShopCode(), shop);
			}
		}
		// }
		return dupResults;
	}

	/**
	 * 找到当前货品已经存在停补的店铺
	 * 
	 * @param product
	 * @return
	 */
	public List<PlmSupShopEntity_HI_RO> getExShops(JSONObject product) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(PlmSupShopEntity_HI_RO.EX_SQL);
		SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class, product,
				sql, queryParamMap);
		List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO
				.findList(sql, queryParamMap);
		return findListResult;
	}

	/**
	 * 找到当前货品对应的停补店铺
	 * 
	 * @param queryParamJSON
	 * @return
	 */
	public List<PlmSupShopEntity_HI_RO> getShops(JSONObject queryParamJSON) {
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer(PlmSupShopEntity_HI_RO.SQL);
		SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class,
				queryParamJSON, sql, queryParamMap);
		List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO
				.findList(sql, queryParamMap);
		return findListResult;
	}

	/**
	 * 根据类型找到当前货品对应的停补店铺
	 * 
	 * @param
	 * @return
	 */
	public List<PlmSupShopEntity_HI_RO> getShopsByType(
			PlmSupplementLineEntity_HI entity) {
		String s = entity.getStore();
		String m = "";
		if (s.equals("1")) {
			String[] meterList = entity.getMeter().split("\\|");
			for (String me: meterList) {
				if ("n".equals(me.toLowerCase())) {
					m += "North Area";
					m += "|";
				}
				if ("s".equals(me.toLowerCase())) {
					m += "South Area";
					m += "|";
				}
				if ("e".equals(me.toLowerCase())) {
					m += "East Area";
					m += "|";
				}
				if ("w".equals(me.toLowerCase())) {
					m += "West Area";
					m += "|";
				}
			}
			m = m.substring(0, m.length() - 1);
		}
		Map<String, Object> queryParamMap = new HashMap<String, Object>();
		String sqlStr = "";
		String meter = "";
		if (entity.getMeter() == null) {
			return null;
		}
        String[] meterList = null;
        if (s.equals("1")) {
            meterList = m.split("\\|");
        } else {
            meterList = entity.getMeter().split("\\|");
        }
		for (int i = 0; i < meterList.length; i++) {
			meter = meter + "'" + meterList[i] + "',";
		}
		if (meterList.length > 0) {
			meter = meter.substring(0, meter.length() - 1);
		}
		StringBuffer sql = null;
		if ("0".equals(entity.getStore())) {
			sqlStr = PlmSupShopEntity_HI_RO.WARE_SQL;
		}
		if ("1".equals(entity.getStore())) {
			sqlStr = PlmSupShopEntity_HI_RO.REGION_SQL;
		}
		if ("2".equals(entity.getStore())) {
			sqlStr = PlmSupShopEntity_HI_RO.POG_SQL;
		}
		if ("3".equals(entity.getStore())) {
			sqlStr = PlmSupShopEntity_HI_RO.STORE_SQL;
		}
		sqlStr = sqlStr.replace(":rmsId", entity.getRmsCode());
		sqlStr = sqlStr.replace(":area", meter);
		sql = new StringBuffer(sqlStr);
		SaafToolUtils.parperHbmParam(PlmSupShopEntity_HI_RO.class,
				new JSONObject(), sql, queryParamMap);
		List<PlmSupShopEntity_HI_RO> findListResult = plmSupShopDAO_HI_RO
				.findList(sql, queryParamMap);
		return findListResult;
	}
}
