package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.services.GenerateCodeService;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaSystemCurrentLineDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaPogSpaceLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.TtaSystemCurrentLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaSystemCurrentLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.withdrawal.utils.WDatesUtils;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaSystemCurrentLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaSystemCurrentLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisCluster;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Component("ttaSystemCurrentLineServer")
public class TtaSystemCurrentLineServer extends BaseCommonServer<TtaSystemCurrentLineEntity_HI> implements ITtaSystemCurrentLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSystemCurrentLineServer.class);

	@Autowired
	private ViewObject<TtaSystemCurrentLineEntity_HI> ttaSystemCurrentLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaSystemCurrentLineEntity_HI_RO> ttaSystemCurrentLineDAO_HI_RO;

	@Autowired
	private TtaSystemCurrentLineDAO_HI ttaSystemCurrentLineDAOHi;

	@Autowired
	private JedisCluster jedisCluster;

	@Autowired
	private GenerateCodeService codeService;

	public TtaSystemCurrentLineServer() {
		super();
	}

	/**
	 * 批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaSystemCurrentLineEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
			Boolean flag = (Boolean) result.get("flag");
			if(flag){
				list = (List<Map<String,Object>>) result.get("datas");
			}else{
				JSONObject errJson2 = new JSONObject();
				errJson2.put("ROW_NUM",'0');
				errJson2.put("ERR_MESSAGE","表头信息错误");
				errList.add(errJson2) ;
			}
		}

		String nppBatch = null;
		if (list != null) {
			nppBatch = codeService.getTtaNppImportBatch("SC");//system_current 基础表导入数据批次
			boolean isQuery = false;
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> json = list.get(i);
				if (!isQuery) {
					List<Map<String, Object>> mapList = ttaSystemCurrentLineDAOHi.queryNamedSQLForList(TtaSystemCurrentLineEntity_HI_RO.getCountByIsCreate((Integer) json.get("MONTH_N")), new HashMap<>());
					if (CollectionUtils.isNotEmpty(mapList)) {
						BigDecimal cou = (BigDecimal)mapList.get(0).get("COU");
						if (cou.intValue() > 0) {
							throw new IllegalArgumentException("系统中已存在[#]月份的数据,请检查!".replace("#",json.get("MONTH_N").toString()));
						} else {
							isQuery = true;
						}
					}
				}
				JSONObject errJson = new JSONObject();
				json.put("CREATED_BY", queryParamJSON.getInteger("varUserId"));
				json.put("CREATION_DATE", new Date());
				json.put("LAST_UPDATED_BY", queryParamJSON.getInteger("varUserId"));
				json.put("LAST_UPDATE_DATE", new Date());
				json.put("LAST_UPDATE_LOGIN", queryParamJSON.getInteger("varUserId"));
				json.put("VERSION_NUM", 0);
				json.put("IMPORT_BATCH",nppBatch);
				json.put("SYSTEM_CURRENT_ID",null);
				String msgStr = "";
				try {
					if (!"".equals(msgStr)) {
						errJson.put("ROW_NUM", json.get("ROW_NUM"));
						errJson.put("ERR_MESSAGE", msgStr);
						errList.add(errJson);
					} else {
						//	json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
						//	super.saveOrUpdate(json);
					}
				} catch (Exception e) {
					msgStr += ("有异常,数据有误.");
					errJson.put("ROW_NUM", json.get("ROW_NUM"));
					errJson.put("ERR_MESSAGE", msgStr);
					errList.add(errJson);
					e.printStackTrace();
				}
			}
		} else {
			JSONObject errJson = new JSONObject();
			errJson.put("ROW_NUM","0");
			errJson.put("ERR_MESSAGE","表头信息错误");
			errList.add(errJson);
		}

		if (!errList.isEmpty()){
			throw new RuntimeException(errList.toJSONString());
		} else{
			ttaSystemCurrentLineDAOHi.saveSeqBatchJDBC("TTA_SYSTEM_CURRENT_LINE",list,"SYSTEM_CURRENT_ID","SEQ_TTA_SYSTEM_CURRENT_LINE.NEXTVAL",jedisCluster,sessionBean);
			jedisCluster.setex(sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

		}
		return list.size();
	}

	/**
	 *
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaSystemCurrentLineEntity_HI_RO> findInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer(TtaSystemCurrentLineEntity_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("month"))){
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
			queryParamJSON.put("month",sdf.parse(queryParamJSON.getString("month")));
			SaafToolUtils.parperHbmParam(TtaSystemCurrentLineEntity_HI_RO.class, queryParamJSON, "tsc.month", "month", sql, map, "=");
		}

		SaafToolUtils.parperHbmParam(TtaSystemCurrentLineEntity_HI_RO.class, queryParamJSON, "tsc.item", "item", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaSystemCurrentLineEntity_HI_RO.class, queryParamJSON, "tsc.brand_Cn", "brandCn", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaSystemCurrentLineEntity_HI_RO.class, queryParamJSON, "tsc.vendor_nbr", "vendorNbr", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaSystemCurrentLineEntity_HI_RO.class, queryParamJSON, "tsc.vendor_name", "vendorName", sql, map, "fulllike");

		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " SYSTEM_CURRENT_ID desc", false);
		Pagination<TtaSystemCurrentLineEntity_HI_RO> resultList =ttaSystemCurrentLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(queryParamJSON.getInteger("systemCurrentId")!=null || !"".equals(queryParamJSON.getInteger("systemCurrentId"))){
			ttaSystemCurrentLineDAO_HI.delete(queryParamJSON.getInteger("systemCurrentId"));
		}
		return result;
	}

}
