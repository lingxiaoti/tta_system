package com.sie.watsons.base.usergroupdeptbrand.model.inter.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.report.utils.EasyExcelUtil;
import com.sie.watsons.base.usergroupdeptbrand.model.dao.TtaUserDataTypeDAO_HI;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserDataTypeEntity_HI_MODEL;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.readonly.TtaUserDataTypeEntity_HI_RO;
import com.yhg.hibernate.core.dao.BaseViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yhg.base.utils.SToolUtils;
import com.sie.watsons.base.usergroupdeptbrand.model.entities.TtaUserDataTypeEntity_HI;
import com.yhg.hibernate.core.dao.ViewObject;
import com.sie.watsons.base.usergroupdeptbrand.model.inter.ITtaUserDataType;
import com.sie.saaf.common.model.inter.server.BaseCommonServer;
import org.springframework.web.multipart.MultipartFile;

@Component("ttaUserDataTypeServer")
public class TtaUserDataTypeServer extends BaseCommonServer<TtaUserDataTypeEntity_HI> implements ITtaUserDataType{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaUserDataTypeServer.class);

	@Autowired
	private BaseCommonDAO_HI<TtaUserDataTypeEntity_HI> ttaUserDataTypeDAO_HI;

	@Autowired
	private BaseViewObject<TtaUserDataTypeEntity_HI_RO> ttaUserDataTypeDAO_HI_RO;

	@Autowired
	private redis.clients.jedis.JedisCluster jedisCluster;

	@Autowired
	private TtaUserDataTypeDAO_HI ttaUserDataTypeDAO ;

	public TtaUserDataTypeServer() {
		super();
	}

	/**
	 * OI批量导入
	 * @param queryParamJSON
	 * @return
	 * @throws Exception
	 */
	public int saveImportInfo(JSONObject queryParamJSON, MultipartFile file, UserSessionBean sessionBean) throws Exception{
		ttaUserDataTypeDAO_HI.executeSqlUpdate("delete from tta_user_data_type") ;
		jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'U'}");
		JSONArray errList = new JSONArray();
		List<Map<String,Object>> list = null ;
		if(file != null ){
			Map<String,Object> result = EasyExcelUtil.readExcel(file, TtaUserDataTypeEntity_HI_MODEL.class,0,jedisCluster,sessionBean);
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
		//	json.put("CREATED_BY",queryParamJSON.getInteger("varUserId")) ;
		//	json.put("CREATION_DATE",new Date()) ;
		//	json.put("LAST_UPDATED_BY",queryParamJSON.getInteger("varUserId")) ;
		//	json.put("LAST_UPDATE_DATE",new Date()) ;
		//	json.put("LAST_UPDATE_LOGIN",queryParamJSON.getInteger("varUserId")) ;
		//	json.put("VERSION_NUM",0) ;
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
			TtaUserDataTypeEntity_HI_RO tudtInfo = null;
			ttaUserDataTypeDAO.saveSeqBatchJDBC("TTA_USER_DATA_TYPE",list,"TTA_USER_DATA_TYPE_ID","SEQ_TTA_USER_DATA_TYPE.NEXTVAL",jedisCluster,sessionBean);
			StringBuffer sql = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();
			//1.校验是否存在为空的
			sql.append(TtaUserDataTypeEntity_HI_RO.TTA_USER_IS_NULL);
			 tudtInfo = ttaUserDataTypeDAO_HI_RO.get(sql, params);
			if (!SaafToolUtils.isNullOrEmpty(tudtInfo.getValueAll())) {
				jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'存在为空→"+tudtInfo.getValueAll()+"',orderNum:"+"'无'}");
				return 0;
			}
			//2.校验用户是否存在
			sql.setLength(0);
			sql.append(TtaUserDataTypeEntity_HI_RO.TTA_USER_IS_EXIT);
			tudtInfo = ttaUserDataTypeDAO_HI_RO.get(sql, params);
			if (!SaafToolUtils.isNullOrEmpty(tudtInfo.getValueAll())) {
				jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'用户不存在→"+tudtInfo.getValueAll()+"',orderNum:"+"'无'}");
				return 0;
			}
			//3.校验权限类型是否存在
			sql.setLength(0);
			sql.append(TtaUserDataTypeEntity_HI_RO.TTA_DATA_TYPE_IS_EXIT);
			tudtInfo = ttaUserDataTypeDAO_HI_RO.get(sql, params);
			if (!SaafToolUtils.isNullOrEmpty(tudtInfo.getValueAll())) {
				jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'权限类型不存在→"+tudtInfo.getValueAll()+"',orderNum:"+"'无'}");
				return 0;
			}
			//4.校验权限类型是否一致
			sql.setLength(0);
			sql.append(TtaUserDataTypeEntity_HI_RO.TTA_DATA_TYPE_IS_SAME);
			tudtInfo = ttaUserDataTypeDAO_HI_RO.get(sql, params);
			if (!SaafToolUtils.isNullOrEmpty(tudtInfo.getValueAll())) {
				jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'权限类型不一致→"+tudtInfo.getValueAll()+"',orderNum:"+"'无'}");
				return 0;
			}
			//5.校验数据是否重复
			sql.setLength(0);
			sql.append(TtaUserDataTypeEntity_HI_RO.TTA_DATA_IS_SAME);
			tudtInfo = ttaUserDataTypeDAO_HI_RO.get(sql, params);
			if (!SaafToolUtils.isNullOrEmpty(tudtInfo.getValueAll())) {
				jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'数据重复→"+tudtInfo.getValueAll()+"',orderNum:"+"'无'}");
				return 0;
			}
			//插入权限表
			ttaUserDataTypeDAO_HI.executeSqlUpdate(TtaUserDataTypeEntity_HI_RO.getInsertReportBase(sessionBean.getUserId())) ;
			//更新用户表
			ttaUserDataTypeDAO_HI.executeSqlUpdate(TtaUserDataTypeEntity_HI_RO.updateBaseUsers()) ;
			//删除中间表
			ttaUserDataTypeDAO_HI.executeSqlUpdate("delete from tta_user_data_type") ;
			jedisCluster.setex("TTA_USER_DATA_TYPE" + sessionBean.getCertificate(),3600,"{status:'S',currentStage:'完成',orderNum:"+"'无'}");

		}
		return list.size();
	}

}
