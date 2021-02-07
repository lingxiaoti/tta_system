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
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.dao.TtaSroiBillLineDAO_HI;
import com.sie.watsons.base.ttaImport.model.entities.TtaSroiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.ttaImport.model.entities.readonly.TtaSroiBillLineEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sie.watsons.base.ttaImport.model.entities.TtaSroiBillLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.ttaImport.model.inter.ITtaSroiBillLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaSroiBillLineServer")
public class TtaSroiBillLineServer extends BaseCommonServer<TtaSroiBillLineEntity_HI> implements ITtaSroiBillLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSroiBillLineServer.class);

	@Autowired
	private ViewObject<TtaSroiBillLineEntity_HI> ttaSroiBillLineDAO_HI;

	@Autowired
	private TtaSroiBillLineDAO_HI ttaSroiBillLineDAO ;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Autowired
	private BaseViewObject<TtaSroiBillLineEntity_HI_RO> ttaSroiBillLineDAO_HI_RO;

	public TtaSroiBillLineServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportSROIInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
        jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
	    JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaSroiBillLineEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
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
			ttaSroiBillLineDAO.saveSeqBatchJDBC("TTA_SROI_BILL_LINE",list,"TTA_SROI_BILL_IMPORT_ID","SEQ_TTA_SROI_BILL_LINE.NEXTVAL",jedisCluster,sessionBean);
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
	public Pagination<TtaSroiBillLineEntity_HI_RO> findSROIInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer(TtaSroiBillLineEntity_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("accountMonth"))){
			if(queryParamJSON.getString("accountMonth").length() == 4){
				SaafToolUtils.parperParam(queryParamJSON, "to_char(tsbl.account_Month,'yyyy')", "accountMonth", sql, map, "=");
			}else{
				SaafToolUtils.parperParam(queryParamJSON, "to_char(tsbl.account_Month,'yyyy-mm')", "accountMonth", sql, map, "=");
			}

		}
		SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "tsbl.rms_Code", "rmsCode", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "tsbl.vender_Name", "venderName", sql, map, "fulllike");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tta_Sroi_Bill_Import_Id desc", false);
		Pagination<TtaSroiBillLineEntity_HI_RO> resultList =ttaSroiBillLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportSROIInfo(JSONObject queryParamJSON) throws Exception{
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
					ttaSroiBillLineDAO_HI.executeSqlUpdate("delete from tta_sroi_bill_line t where to_char(nvl(t.account_Month,sysdate),'yyyy-mm') ='" + queryParamJSON.getString("month")+"'");
				}
			}else{
				if(queryParamJSON.getInteger("ttaSroiBillImportId")!=null || !"".equals(queryParamJSON.getInteger("ttaSroiBillImportId"))){
					ttaSroiBillLineDAO_HI.delete(queryParamJSON.getInteger("ttaSroiBillImportId"));
				}
			}
		}
		return result;
	}

}
