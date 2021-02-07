package com.sie.watsons.base.ttaImport.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaAboiSummaryDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiSummaryEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.TtaBeoiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaAboiSummaryEntity_HI_RO;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaBeoiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.ttaImport.model.entities.TtaAboiSummaryEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttaImport.model.inter.ITtaAboiSummary;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaAboiSummaryServer")
public class TtaAboiSummaryServer extends BaseCommonServer<TtaAboiSummaryEntity_HI> implements ITtaAboiSummary{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaAboiSummaryServer.class);

	@Autowired
	private ViewObject<TtaAboiSummaryEntity_HI> ttaAboiSummaryDAO_HI;

	@Autowired
	private TtaAboiSummaryDAO_HI ttaAboiSummaryDAOHi;

	@Autowired
	private BaseViewObject<TtaAboiSummaryEntity_HI_RO> ttaAboiSummaryDAO_HI_RO;

	public TtaAboiSummaryServer() {
		super();
	}

	/**
	 * AboiSummary批量导入
	 * @param queryParamJSON
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveImportAboiSummaryInfo(JSONObject queryParamJSON, MultipartFile file) throws Exception {
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaAboiSummaryEntity_HI_MODEL.class,0);
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
		if (list != null) {
			for(int i=0;i<list.size();i++){
				Map<String,Object> json = list.get(i);
				json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
				json.put("CREATION_DATE",new Date()) ;
				json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
				json.put("LAST_UPDATE_DATE",new Date()) ;
				json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
				json.put("VERSION_NUM",0) ;
				JSONObject errJson = new JSONObject();
				String msgStr = "";
				try {
					if(SaafToolUtils.isNullOrEmpty(json.get("ACCOUNT_MONTH"))){
						msgStr += "导入月份的值不能为空";
					}
					if(!"".equals(msgStr)){
						errJson.put("ROW_NUM",json.get("ROW_NUM"));
						errJson.put("ERR_MESSAGE",msgStr);
						errList.add(errJson);
					}
				}catch (Exception e){
					msgStr += ("有异常,数据有误.");
					errJson.put("ROW_NUM",json.get("ROW_NUM"));
					errJson.put("ERR_MESSAGE",msgStr);
					errList.add(errJson);
					e.printStackTrace();
				}
			}
		}else {
			JSONObject errJson = new JSONObject();
			errJson.put("ROW_NUM","0");
			errJson.put("ERR_MESSAGE","表头信息错误");
			errList.add(errJson);
		}

		if (!errList.isEmpty()){
			throw new Exception(errList.toJSONString());
		}else{
			if (list != null && !list.isEmpty()) {
				//业务会按照年度导入年至今的数据，故要删除当年数据，重新生成。
				String year = (list.get(0).get("ACCOUNT_MONTH") + "").substring(0, 4);
				ttaAboiSummaryDAOHi.executeSqlUpdate("delete from tta_aboi_summary t where substr(t.account_month,0,4) = " + year);
			}
			ttaAboiSummaryDAOHi.saveSeqBatchJDBC("TTA_ABOI_SUMMARY",list,"TTA_ABOI_SUMMARY_ID","SEQ_TTA_ABOI_SUMMARY.NEXTVAL");
		}
		return list.size();
	}

	/**
	 * AboiSummary查询
	 * @param queryParamJSON
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@Override
	public Pagination<TtaAboiSummaryEntity_HI_RO> findAboiSummaryInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception {
		StringBuffer sql = new StringBuffer(TtaAboiSummaryEntity_HI_RO.ABOI_SUMMARY_QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("accountMonth"))){

			if(queryParamJSON.getString("accountMonth").length() == 4){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date accountMonth = sdf.parse(queryParamJSON.getString("accountMonth"));
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
				String format = sdf1.format(accountMonth);
				LOGGER.info("格式化导入月份:{}",format);
				queryParamJSON.put("accountMonth",format);
				//SaafToolUtils.parperParam(queryParamJSON, "to_char(tas.account_Month,'yyyy')", "accountMonth", sql, map, "=");
				SaafToolUtils.parperParam(queryParamJSON, "to_char(tas.account_Month)", "accountMonth", sql, map, "=");
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				Date accountMonth = sdf.parse(queryParamJSON.getString("accountMonth"));
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMM");
				String format = sdf1.format(accountMonth);
				LOGGER.info("格式化导入月份:{}",format);
				queryParamJSON.put("accountMonth",format);
				SaafToolUtils.parperParam(queryParamJSON, "to_char(tas.account_Month)", "accountMonth", sql, map, "=");
			}

		}
		SaafToolUtils.parperHbmParam(TtaAboiSummaryEntity_HI_RO.class, queryParamJSON, "tas.rms_Code", "rmsCode", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaAboiSummaryEntity_HI_RO.class, queryParamJSON, "tas.vender_Name", "venderName", sql, map, "fulllike");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " TTA_ABOI_SUMMARY_ID desc", false);
		Pagination<TtaAboiSummaryEntity_HI_RO> resultList =ttaAboiSummaryDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 * 单条或者批量删除
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSONObject deleteImportAboiSummaryInfo(JSONObject queryParamJSON) throws Exception {
		JSONObject result = new JSONObject();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("flag"))){
			if("pl".equals(queryParamJSON.getString("flag"))){
				if(queryParamJSON.getString("month")!=null || !"".equals(queryParamJSON.getString("month"))){
					if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("month"))){
						String path="\\d{4}-\\d{1,2}"; //定义匹配规则
						Pattern p=Pattern.compile(path);//实例化Pattern
						Matcher m=p.matcher(queryParamJSON.getString("month"));//验证字符串内容是否合法
						if(!m.matches()){//使用正则验证
							throw new IllegalArgumentException("日期不能为空,请重新删除");
						}
					}

					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
					Date month = simpleDateFormat.parse(queryParamJSON.getString("month"));
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
					String format = sdf.format(month);
					ttaAboiSummaryDAO_HI.executeSqlUpdate("delete from TTA_ABOI_SUMMARY t where to_char(nvl(t.account_Month,to_number(to_char(sysdate,'yyyyMM')))) ='" + format+"'");
				}
			}else{
				if(queryParamJSON.getInteger("ttaAboiSummaryId")!=null || !"".equals(queryParamJSON.getInteger("ttaAboiSummaryId"))){
					ttaAboiSummaryDAO_HI.delete(queryParamJSON.getInteger("ttaAboiSummaryId"));
				}
			}
		}
		return result;
	}
}
