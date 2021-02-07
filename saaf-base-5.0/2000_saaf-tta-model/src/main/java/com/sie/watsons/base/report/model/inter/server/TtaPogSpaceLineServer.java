package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafBeanUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.model.dao.TtaPogSpaceLineDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaPogSpaceLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaPogSpaceLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaPogSpaceLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaPogSpaceLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Component("ttaPogSpaceLineServer")
public class TtaPogSpaceLineServer extends BaseCommonServer<TtaPogSpaceLineEntity_HI> implements ITtaPogSpaceLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaPogSpaceLineServer.class);

	@Autowired
	private ViewObject<TtaPogSpaceLineEntity_HI> ttaPogSpaceLineDAO_HI;

	@Autowired
	private TtaPogSpaceLineDAO_HI ttaPogSpaceLineDAO ;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Autowired
	private BaseViewObject<TtaPogSpaceLineEntity_HI_RO> ttaPogSpaceLineDAO_HI_RO;

	public TtaPogSpaceLineServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportPogInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaPogSpaceLineEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
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
			ttaPogSpaceLineDAO.saveSeqBatchJDBC("TTA_POG_SPACE_LINE",list,"POG_SPACE_LINE_ID","SEQ_TTA_POG_SPACE_LINE.NEXTVAL",jedisCluster,sessionBean);
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
	public Pagination<TtaPogSpaceLineEntity_HI_RO> findPogInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();

		if( !SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag")) && "CREATE".equals(queryParamJSON.getString("flag"))){
			sql.append(TtaPogSpaceLineEntity_HI_RO.CREATE_QUERY) ;
			SaafToolUtils.parperParam(queryParamJSON, "tpsl.is_create", "isCreate", sql, map, "=");
		}else{
			sql.append(TtaPogSpaceLineEntity_HI_RO.QUERY) ;
			SaafToolUtils.parperParam(queryParamJSON, "tpsl.item_code", "itemCode", sql, map, "fulllike");
		}
		SaafToolUtils.parperParam(queryParamJSON, "tpsl.promotion_section", "promotionSection", sql, map, "fulllike");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, "tpsl.pog_space_line_id desc", false);
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");

		Pagination<TtaPogSpaceLineEntity_HI_RO> resultList =ttaPogSpaceLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public List<TtaPogSpaceLineEntity_HI_RO> findPogInfoOne(JSONObject queryParamJSON) throws Exception{
		StringBuffer sql = new StringBuffer();
		Map<String,Object> map = new HashMap<String,Object>();
		sql.append(TtaPogSpaceLineEntity_HI_RO.QUERY) ;
		SaafToolUtils.parperParam(queryParamJSON, "tpsl.promotion_section", "promotionSection", sql, map, "=");
		sql.append(" and rownum =1") ;
		List<TtaPogSpaceLineEntity_HI_RO> list = ttaPogSpaceLineDAO_HI_RO.findList(sql, map);
		return list;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportPogInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
			if("pl".equals(queryParamJSON.getString("flag"))){
				if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
					StringBuffer countSql = new StringBuffer("select count(tp.pogSpaceLineId) from TtaPogSpaceLineEntity_HI tp " +
							"where tp.promotionSection = :promotionSection");
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("promotionSection",queryParamJSON.getString("month")) ;
					Integer count = ttaPogSpaceLineDAO_HI.count(countSql, map);
					if(count.intValue() == 0){
						throw new IllegalArgumentException("当前促销区间不存在,删除失败");
					}
					ttaPogSpaceLineDAO_HI.executeSqlUpdate("delete from tta_pog_space_line t where t.promotion_section ='" + queryParamJSON.getString("month")+"'");
				}
			}else{
				if(queryParamJSON.getInteger("pogSpaceLineId")!=null || !"".equals(queryParamJSON.getInteger("pogSpaceLineId"))){
					ttaPogSpaceLineDAO_HI.delete(queryParamJSON.getInteger("pogSpaceLineId"));
				}
			}
		}
		return result;
	}

}
