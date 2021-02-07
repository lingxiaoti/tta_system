package com.sie.watsons.base.ttaImport.model.inter.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaTradeCalendarDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaTradeCalendarEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaTradeCalendarEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ttaImport.model.entities.TtaTradeCalendarEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttaImport.model.inter.ITtaTradeCalendar;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaTradeCalendarServer")
public class TtaTradeCalendarServer extends BaseCommonServer<TtaTradeCalendarEntity_HI> implements ITtaTradeCalendar{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTradeCalendarServer.class);

	@Autowired
	private ViewObject<TtaTradeCalendarEntity_HI> ttaTradeCalendarDAO_HI;

	@Autowired
	private TtaTradeCalendarDAO_HI ttaTradeCalendarDAO ;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Autowired
	private BaseViewObject<TtaTradeCalendarEntity_HI_RO> ttaTradeCalendarDAO_HI_RO;

	public TtaTradeCalendarServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportTCInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaTradeCalendarEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
			Boolean flag = (Boolean) result.get("flag");
			if(flag){
				list = (List<Map<String,Object>>) result.get("datas");
				// jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
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
				if(SaafToolUtils.isNullOrEmpty(json.get("TRADE_YEAR"))){
					msgStr += "【年yyyy】的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.get("TRADE_MONTH"))){
					msgStr += "【月mm】的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.get("WEEK_START"))){
					msgStr += "【周开始 yyyymmdd】的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.get("WEEK_END"))){
					msgStr += "【周结束 yyyymmdd】的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.get("WEEK_NUM"))){
					msgStr += "【月的第几周】的值不能为空";
				}
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
			ttaTradeCalendarDAO.saveSeqBatchJDBC("TTA_TRADE_CALENDAR",list,"TRANDE_ID","SEQ_TTA_TRADE_CALENDAR.NEXTVAL",jedisCluster,sessionBean);
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
	public Pagination<TtaTradeCalendarEntity_HI_RO> findTCInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer(TtaTradeCalendarEntity_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();

		SaafToolUtils.parperParam(queryParamJSON, "ttc.trade_Year", "tradeYear", sql, map, "fulllike");
		SaafToolUtils.parperParam(queryParamJSON, "ttc.trade_Month", "tradeMonth", sql, map, "fulllike");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " trande_Id desc", false);
		Pagination<TtaTradeCalendarEntity_HI_RO> resultList =ttaTradeCalendarDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportTCInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
			if("pl".equals(queryParamJSON.getString("flag"))){
				if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
					if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("month"))){
						SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
						String path="\\d{4}-\\d{1,2}"; //定义匹配规则
						Pattern p=Pattern.compile(path);//实例化Pattern
						Matcher m=p.matcher(queryParamJSON.getString("month"));//验证字符串内容是否合法
						if(!m.matches()){//使用正则验证
							throw new IllegalArgumentException("日期不能为空,请重新删除");
						}
					}
					ttaTradeCalendarDAO_HI.executeSqlUpdate("delete from tta_trade_calendar t where t.trade_Year =" + queryParamJSON.getString("month").substring(0, 4) +" and t.trade_Month =" +queryParamJSON.getString("month").substring(5, 7));
				}
			}else{
				if(queryParamJSON.getInteger("trandeId")!=null || !"".equals(queryParamJSON.getInteger("trandeId"))){
					ttaTradeCalendarDAO_HI.delete(queryParamJSON.getInteger("trandeId"));
				}
			}
		}
		return result;
	}
}
