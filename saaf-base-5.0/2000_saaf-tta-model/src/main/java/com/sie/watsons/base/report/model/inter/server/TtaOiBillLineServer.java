package com.sie.watsons.base.report.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.feedept.model.dao.TtaFeeDeptHDAO_HI;
import com.sie.watsons.base.report.model.dao.TtaOiBillLineDAO_HI;
import com.sie.watsons.base.report.model.entities.TtaOiBillLineEntity_HI_MODEL;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.report.model.entities.TtaOiBillLineEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.report.model.inter.ITtaOiBillLine;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaOiBillLineServer")
public class TtaOiBillLineServer extends BaseCommonServer<TtaOiBillLineEntity_HI> implements ITtaOiBillLine{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaOiBillLineServer.class);

	@Autowired
	private ViewObject<TtaOiBillLineEntity_HI> ttaOiBillLineDAO_HI;

	@Autowired
	private TtaOiBillLineDAO_HI ttaOiBillLineDAO;

	@Autowired
	private BaseViewObject<TtaOiBillLineEntity_HI_RO> ttaOiBillLineDAO_HI_RO;

	public TtaOiBillLineServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportOIInfo(JSONObject queryParamJSON, MultipartFile  file) throws Exception{
		JSONArray errList = new JSONArray();
        List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaOiBillLineEntity_HI_MODEL.class,0);
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
			//JSONObject json = (JSONObject) JSONObject.toJSON(o);
			JSONObject errJson = new JSONObject();
			String msgStr = "";
			try {
				if(SaafToolUtils.isNullOrEmpty(json.get("ACCOUNT_MONTH"))){
					msgStr += "导入月份的值不能为空";
				}
				if(SaafToolUtils.isNullOrEmpty(json.get("RMS_CODE"))){
					msgStr += "供应商编号的值不能为空";
				}
				//判断合同日期格式是否输入正确
//				if(!SaafToolUtils.isNullOrEmpty(json.getString("validBeginDate"))){
//					SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
//					String path="\\d{4}/\\d{1,2}/\\d{1,2}"; //定义匹配规则
//					Pattern p=Pattern.compile(path);//实例化Pattern
//					Matcher m=p.matcher(json.getString("validBeginDate"));//验证字符串内容是否合法
//					if(m.matches()){//使用正则验证
//						json.put("validBeginDate",format.parse(json.getString("validBeginDate")));
//					}else{
//						msgStr += "请输入正确的日期格式(2019/01/01)";
//					}
//				}
//				if(!SaafToolUtils.isNullOrEmpty(json.getString("accountMonth"))){
//					SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
//					String path="\\d{4}\\d{1,2}"; //定义匹配规则
//					Pattern p=Pattern.compile(path);//实例化Pattern
//					Matcher m=p.matcher(json.getString("accountMonth"));//验证字符串内容是否合法
//					if(m.matches()){//使用正则验证
//						json.put("accountMonth",format.parse(json.getString("accountMonth")));
//					}else{
//						msgStr += "请输入正确的日期格式(201901)";
//            }
//             }
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
			ttaOiBillLineDAO.saveSeqBatchJDBC("TTA_OI_BILL_LINE",list,"TTA_OI_BILL_IMPORT_ID","SEQ_TTA_OI_BILL_LINE.NEXTVAL");
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
	public Pagination<TtaOiBillLineEntity_HI_RO> findOIBInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer(TtaOiBillLineEntity_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("accountMonth"))){
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
			queryParamJSON.put("accountMonth",sdf.parse(queryParamJSON.getString("accountMonth")));
			SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "tob.account_Month", "accountMonth", sql, map, "=");
		}

		SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "tob.rms_Code", "rmsCode", sql, map, "fulllike");
		SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "tob.vender_Name", "venderName", sql, map, "fulllike");
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " TTA_OI_BILL_IMPORT_ID desc", false);
		Pagination<TtaOiBillLineEntity_HI_RO> resultList =ttaOiBillLineDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportOIInfo(JSONObject queryParamJSON) throws Exception{
		JSONObject result = new JSONObject();
		if(queryParamJSON.getInteger("ttaOiBillImportId")!=null || !"".equals(queryParamJSON.getInteger("ttaOiBillImportId"))){
			ttaOiBillLineDAO_HI.delete(queryParamJSON.getInteger("ttaOiBillImportId"));
		}
		return result;
	}
}
