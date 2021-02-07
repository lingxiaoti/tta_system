package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaHwbBaseLineDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaHwbBaseLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaHwbBaseLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaHwbBaseLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaHwbBaseLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaHwbBaseLineServer")
public class TtaHwbBaseLineServer extends BaseCommonServer<TtaHwbBaseLineEntity_HI> implements ITtaHwbBaseLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaHwbBaseLineServer.class);

	@Autowired
	private ViewObject<TtaHwbBaseLineEntity_HI> ttaHwbBaseLineDAO_HI;

	@Autowired
	private BaseViewObject<TtaHwbBaseLineEntity_HI_RO> ttaHwbBaseLineDAO_HI_RO;

	public TtaHwbBaseLineServer() {
		super();
	}

	@Autowired
	private TtaHwbBaseLineDAO_HI ttaHwbBaseLineDAO;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportHwbBaseInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaHwbBaseLineEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
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

		for(int i=0;i<list.size();i++){
			Map<String,Object> json = list.get(i);
			JSONObject errJson = new JSONObject();
			json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("CREATION_DATE",new Date()) ;
			json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("LAST_UPDATE_DATE",new Date()) ;
			json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
			json.put("VERSION_NUM",0) ;
			String msgStr = "";
			try {
				if(!"".equals(msgStr)){
					errJson.put("ROW_NUM",json.get("ROW_NUM"));
					errJson.put("ERR_MESSAGE",msgStr);
					errList.add(errJson);
				}else{
					//	json.put("operatorUserId",queryParamJSON.get("operatorUserId"));
					//	super.saveOrUpdate(json);
				}
			}catch (Exception e){
				msgStr += ("有异常,数据有误.");
				errJson.put("ROW_NUM",json.get("ROW_NUM"));
				errJson.put("ERR_MESSAGE",msgStr);
				errList.add(errJson);
				e.printStackTrace();
			}
		}
		if (!errList.isEmpty()){
			throw new Exception(errList.toJSONString());
		}else{
			ttaHwbBaseLineDAO.saveSeqBatchJDBC("TTA_HWB_BASE_LINE",list,"HWB_BASE_LINE_ID","SEQ_TTA_HWB_BASE_LINE.NEXTVAL",jedisCluster,sessionBean);
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
	public Pagination<TtaHwbBaseLineEntity_HI_RO> findHwbBaseInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();

		if( !SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag")) && "CREATE".equals(queryParamJSON.getString("flag"))){
			sql.append(TtaHwbBaseLineEntity_HI_RO.CREATE_QUERY) ;
			SaafToolUtils.parperParam(queryParamJSON, "tobl.is_create", "isCreate", sql, map, "=");
		}else{
			sql.append(TtaHwbBaseLineEntity_HI_RO.QUERY) ;
		}
		SaafToolUtils.parperParam(queryParamJSON, "tobl.promotion_Section", "promotionSection", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tobl.vendor_code", "vendorCode", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tobl.vendor_name", "vendorName", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "tobl.hwb_Type", "hwbType", sql, map, "fulllike");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tobl.hwb_base_line_id desc", false);
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");

		Pagination<TtaHwbBaseLineEntity_HI_RO> resultList =ttaHwbBaseLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public List<TtaHwbBaseLineEntity_HI_RO> findHwbBaseInfoOne(JSONObject queryParamJSON) throws Exception{
		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		sql.append(TtaHwbBaseLineEntity_HI_RO.QUERY) ;
		SaafToolUtils.parperParam(queryParamJSON, "tobl.promotion_section", "promotionSection", sql, map, "=");
		sql.append(" and rownum =1") ;
		List<TtaHwbBaseLineEntity_HI_RO> list = ttaHwbBaseLineDAO_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportHwbBaseInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
			if("pl".equals(queryParamJSON.getString("flag"))){
				if(queryParamJSON.getString("promotionSection")!=null || !"".equals(queryParamJSON.getString("promotionSection"))){
					StringBuffer countSql = new StringBuffer("select count(tb.hwbBaseLineId) from TtaHwbBaseLineEntity_HI tb " +
							"where tb.promotionSection = :promotionSection");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("promotionSection",queryParamJSON.getString("promotionSection")) ;
					Integer count = ttaHwbBaseLineDAO_HI.count(countSql, map);
					if(count.intValue() == 0){
						throw new IllegalArgumentException("当前促销档期不存在,删除失败");
					}
					ttaHwbBaseLineDAO_HI.executeSqlUpdate("delete from tta_hwb_base_line t where t.promotion_section ='" + queryParamJSON.getString("promotionSection")+"'");
				}
			}else{
				if(queryParamJSON.getInteger("hwbBaseLineId")!=null || !"".equals(queryParamJSON.getInteger("hwbBaseLineId"))){
					ttaHwbBaseLineDAO_HI.delete(queryParamJSON.getInteger("hwbBaseLineId"));
				}
			}
		}
		return result;
	}
}
