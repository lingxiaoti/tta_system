package com.sie.watsons.base.contract.model.inter.server;

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

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.contract.model.dao.TtaTermsAcCountDAO_HI;
import com.sie.watsons.base.contract.model.entities.readonly.TtaTermsAcCountEntity_HI_RO;
import com.sie.watsons.base.report.model.entities.readonly.TtaOiBillLineEntity_HI_RO;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.ttaImport.model.entities.TtaTermsAcCountEntity_HI_MODEL;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.contract.model.entities.TtaTermsAcCountEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.contract.model.inter.ITtaTermsAcCount;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaTermsAcCountServer")
public class TtaTermsAcCountServer extends BaseCommonServer<TtaTermsAcCountEntity_HI> implements ITtaTermsAcCount{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaTermsAcCountServer.class);

	@Autowired
	private ViewObject<TtaTermsAcCountEntity_HI> ttaTermsAcCountDAO_HI;
	@Autowired
	private TtaTermsAcCountDAO_HI ttaTermsAcCountDAO ;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Autowired
	private BaseViewObject<TtaTermsAcCountEntity_HI_RO> ttaTermsAcCountDAO_HI_RO;

	public TtaTermsAcCountServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportACInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		jedisCluster.setex(sessionBean.getCertificate(),3600,"{status,'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			//Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaTermsAcCountEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
            Map<String,Object> result = null;
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
			json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("CREATION_DATE",new Date()) ;
			json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
			json.put("LAST_UPDATE_DATE",new Date()) ;
			json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
			json.put("VERSION_NUM",0) ;			JSONObject errJson = new JSONObject();
			String msgStr = "";
			try {
				if(SaafToolUtils.isNullOrEmpty(json.get("YEAR"))){
					msgStr += "导入年份的值不能为空";
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
			//ttaTermsAcCountDAO.saveSeqBatchJDBC("TTA_TERMS_AC_COUNT",list,"TTA_TERMS_AC_COUNT_ID","SEQ_TTA_TERMS_AC_COUNT.NEXTVAL",jedisCluster,sessionBean);
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
	public Pagination<TtaTermsAcCountEntity_HI_RO> findACInfo(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows) throws Exception{
		StringBuffer sql = new StringBuffer(TtaTermsAcCountEntity_HI_RO.QUERY);
		Map<String,Object> map = new HashMap<String,Object>();
		if(!SaafToolUtils.isNullOrEmpty(queryParamJSON.getString("year"))){
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
			queryParamJSON.put("year",sdf.parse(queryParamJSON.getString("year")));
			SaafToolUtils.parperHbmParam(TtaOiBillLineEntity_HI_RO.class, queryParamJSON, "ttac.year", "year", sql, map, "=");
		}
		StringBuffer countSql = SaafToolUtils.getSimpleSqlCountString(sql,"count(*)");
		SaafToolUtils.changeQuerySort(queryParamJSON, sql, " tta_Terms_Ac_Count_Id desc", false);
		Pagination<TtaTermsAcCountEntity_HI_RO> resultList =ttaTermsAcCountDAO_HI_RO.findPagination(sql,countSql,map,pageIndex,pageRows);
		return resultList;
	}

	/**
	 *
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public JSONObject deleteImportACInfo(JSONObject queryParamJSON) throws Exception{
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
					ttaTermsAcCountDAO_HI.executeSqlUpdate("delete from tta_terms_ac_count t where to_char(nvl(t.year,sysdate),'yyyy-mm') ='" + queryParamJSON.getString("month")+"'");
				}
			}else{
				if(queryParamJSON.getInteger("ttaTermsAcCountId")!=null || !"".equals(queryParamJSON.getInteger("ttaTermsAcCountId"))){
					ttaTermsAcCountDAO_HI.delete(queryParamJSON.getInteger("ttaTermsAcCountId"));
				}
			}
		}
		return result;
	}

}
