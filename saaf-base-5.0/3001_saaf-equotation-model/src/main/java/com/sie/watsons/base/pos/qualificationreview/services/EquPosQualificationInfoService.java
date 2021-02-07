package com.sie.watsons.base.pos.qualificationreview.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosQualificationInfoEntity_HI;
import com.sie.watsons.base.pos.qualificationreview.model.inter.IEquPosQualificationInfo;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.EmaelModule.*;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sie.watsons.base.utils.EmailUtil.SendEmail;

@RestController
@RequestMapping("/equPosQualificationInfoService")
public class EquPosQualificationInfoService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(EquPosQualificationInfoService.class);

	@Autowired
	private IEquPosQualificationInfo equPosQualificationInfoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.equPosQualificationInfoServer;
	}

	/**
	 * 资质审查单据，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findQualificationList")
	public String findQualificationList(@RequestParam(required = false) String params,
						   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
						   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"ZZSC");
			//权限校验end

			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosQualificationInfoServer.findList(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("qualificationStatus");
			incomingParam.add("sceneType");
			efferentParam.add("qualificationStatusMeaning");
			efferentParam.add("sceneTypeMeaning");
			typeParam.add("EQU_QUALIFICATION_STATUS");
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues2(  data ,   incomingParam,  efferentParam,  typeParam);
//			for(int i = 0; i < data.size(); i++){
//				JSONObject json = data.getJSONObject(i);
//				JSONObject activityJson = ResultUtils.getActivitiesHistoric(json.getString("qualificationNumber"));
//                if(!ObjectUtils.isEmpty(activityJson)){
//                    json.put("procInstId", activityJson.getString("procInstId"));
//                    System.out.println(json.getString("procInstId"));
//                }
//			}
//            for (Object datum : data) {
//                JSONObject json = JSONObject.parseObject(datum.toString());
//                JSONObject activityJson = ResultUtils.getActivitiesHistoric(json.getString("qualificationNumber"));
//                if(!ObjectUtils.isEmpty(activityJson)){
//                    json.put("procInstId", activityJson.getString("procInstId"));
//                    System.out.println(json.getString("procInstId"));
//                }
//            }
			result.put("data",data);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-保存
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveQualificationInfo")
	public String saveQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosQualificationInfoEntity_HI instance = equPosQualificationInfoServer.saveQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, instance).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-删除
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteQualificationInfo")
	public String deleteQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			Integer listId = equPosQualificationInfoServer.deleteQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, listId).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-提交
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "submitQualificationInfo")
	public String submitQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoServer.submitQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, entity).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-取消
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cancelQualificationInfo")
	public String cancelQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoServer.cancelQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, entity).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-撤回
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "withdrawQualificationInfo")
	public String withdrawQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoServer.withdrawQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, entity).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据-驳回供应商
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "rejectQualificationInfo")
	public String rejectQualificationInfo(@RequestParam(required = false) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoServer.rejectQualificationInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, entity).toString();
		}  catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 品类查询(一级分类)，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findFirstCategoryLov")
	public String findFirstCategoryLov(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosQualificationInfoServer.findFirstCategoryLov(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("categoryCodeFirst");
			efferentParam.add("categoryLevelFirst");
			typeParam.add("EQU_CATEGORY_FIRST_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 品类查询(二级分类)，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSecondCategoryLov")
	public String findSecondCategoryLov(@RequestParam(required = false) String params,
									   @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
									   @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosQualificationInfoServer.findSecondCategoryLov(paramsJONS,pageIndex,pageRows);

			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("categoryCodeSecond");
			efferentParam.add("categoryLevelSecond");
			typeParam.add("EQU_CATEGORY_SECOND_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 品类查询(三级分类)，分页查询
	 * @param params 参数：密级Entity中的字段
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findThridCategoryLov")
	public String findThridCategoryLov(@RequestParam(required = false) String params,
										@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosQualificationInfoServer.findThridCategoryLov(paramsJONS,pageIndex,pageRows);
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("categoryCodeThird");
			efferentParam.add("categoryLevelThird");
			typeParam.add("EQU_CATEGORY_THIRD_TYPE");
			JSONArray data = result.getJSONArray("data");
			data = ResultUtils.getLookUpValues(  data ,   incomingParam,  efferentParam,  typeParam);
			result.put("data",data);

			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 供应商准入管理查询
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierManagerList")
	public String findSupplierManagerList(@RequestParam(required = false) String params,
										  @RequestParam(required = false,defaultValue = "1") Integer pageIndex,
										  @RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject paramsJONS =parseObject(params);
			JSONObject result  =equPosQualificationInfoServer.findSupplierManagerList(paramsJONS,pageIndex,pageRows);

			List<String> typeParam = new ArrayList<>();
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			typeParam.add("EQU_SUPPLIER_SCENE_TYPE");
			List<JSONObject> sceneTypeLookupList = ResultUtils.getReturnJson(incomingParam,efferentParam,typeParam);
			JSONObject sceneTypeJson = sceneTypeLookupList.size() > 0 ? sceneTypeLookupList.get(0) : new JSONObject();

			JSONArray data = result.getJSONArray("data");
			for(int i = 0; i < data.size(); i++){
				JSONObject resultJson = data.getJSONObject(i);
				resultJson.put("sceneTypeMeaning",sceneTypeJson.getString(resultJson.getString("sceneType")));

				List<String> incomingParam2 = new ArrayList<>();
				List<String> efferentParam2 = new ArrayList<>();
				List<String> typeParam2 = new ArrayList<>();
				if(resultJson.getString("currentHandleOrderNumber").startsWith("ZZSC")){
					typeParam2.add("EQU_QUALIFICATION_STATUS");
				}else if(resultJson.getString("currentHandleOrderNumber").startsWith("XYSH")){
					typeParam2.add("EDU_SUP_SUSPEND_STATUS");
				}else if(resultJson.getString("currentHandleOrderNumber").startsWith("CSRSH")){
					typeParam2.add("EQU_CSR_AUDIT_STATUS");
				}else if(resultJson.getString("currentHandleOrderNumber").startsWith("ZLSH")){
					typeParam2.add("EQU_QUALITY_AUDIT_STATUS");
				}else if(resultJson.getString("currentHandleOrderNumber").startsWith("RKSH")){
					typeParam2.add("EDU_SUP_SUSPEND_STATUS");
				}
				List<JSONObject> list = ResultUtils.getReturnJson(incomingParam2,efferentParam2,typeParam2);
				JSONObject currentHandleOrderStatusJson = list.size() > 0 ? list.get(0) : new JSONObject();
				resultJson.put("currentHandleOrderStatusMeaning",currentHandleOrderStatusJson.getString(resultJson.getString("currentHandleOrderStatus")));
			}

//
//			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);

			result.put("data",data);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查找导航菜单节点
	 * @param params 参数：密级Entity中的字段
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findNavigationMenuNodeList")
	public String findNavigationMenuNodeList(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJONS =this.parseJson(params);
			JSONObject result  =equPosQualificationInfoServer.findNavigationMenuNodeList(paramsJONS);
			result.put(SToolUtils.STATUS, "S");
			result.put(SToolUtils.MSG, SUCCESS_MSG);
			return result.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 资质审查单据审批回调接口
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "qualificationApprovalCallback")
	public String qualificationApprovalCallback(@RequestParam(required = false) String params) {
		try {
			System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
			JSONObject paramsObject = parseObject(params);
			int userId = paramsObject.getIntValue("userId");
			EquPosQualificationInfoEntity_HI entity = equPosQualificationInfoServer.qualificationApprovalCallback(paramsObject,userId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "userRespGenerater")
	public String userRespGenerater(@RequestParam(required = false) String params) {
		try {
			//int[] numbers ={940,941,942,943,944,945,946,947,948,949,950,951,952,953,954,955,956,957,958,959,960,961,962,963,964,965,966,967,968,969,970,971,972,973,974,975,976,977,978,979,980,981,982,983,984,985,986,987,988,989,990,991,992,993,994,995,996,997,998,999,1000,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1016,1017,1018,1019,1020,1021,1022,1023,1024,1025,1026,1027,1028,1029,1030,1031,1032,1033,1034,1035,1036,1037,1038,1039,1040,1041,1042,1043,1044,1045,1046,1047,1048,1049,1050,1051,1052,1053,1054,1055,1056,1057,1058,1059,1060,1061,1062,1063,1064,1065,1066,1067,1068,1069,1070,1071,1072,1073,1074,1075,1076,1077,1078,1079,1080,1081,1082,1083,1084,1085,1086,1087,1088,1089,1090,1091,1092,1093,1094,1095,1096,1097,1098,1099,1100,1101,1102,1103,1104,1105,1106,1107,1108,1109,1110,1111,1112,1113,1114,1115,1116,1117,1118,1119,1120,1121,1122,1123,1124,1125,1126,1127,1128,1129,1130,1131,1132,1133,1134,1135,1136,1137,1138,1139,1140,1141,1142,1143,1144,1145,1146,1147,1148,1149,1150,1151,1152,1153,1154,1155,1156,1157,1158,1159,1160,1161,1162,1163,1164,1165,1166,1167,1168,1169,1170,1171,1172,1173,1174,1175,1176,1177,1178,1179,1180,1181,1182,1183,1184,1185,1186,1187,1188,1189,1190,1191,1192,1193,1194,1195,1196,1197,1198,1199,1200,1201,1202,1203,1204,1205,1206,1207,1208,1209,1210,1211,1212,1213,1214,1215,1216,1217,1218,1219,1220,1221,1222,1223,1224,1225,1226,1227,1228,1229,1230,1231,1232,1233,1234,1235,1236,1237,1238,1239,1240,1241,1242,1243,1244,1245,1246,1247,1248,1249,1250,1251,1252,1253,1254,1255,1256,1257,1258,1259,1260,1261,1262,1263,1264,1265,1266,1267,1268,1269,1270,1271,1272,1273,1274,1275,1276,1277,1278,1279,1280,1281,1282,1283,1284,1285,1286,1287,1288,1289,1290,1291,1292,1293,1294,1295,1296,1297,1298,1299,1300,1301,1302,1303,1304,1305,1306,1307,1308,1309,1310,1311,1312,1313,1314,1315,1316,1317,1318,1319,1320,1321,1322,1323,1324,1325,1326,1327,1328,1329,1330,1331,1332,1333,1334,1335,1336,1337,1338,1339,1340,1341,1342,1343,1344,1345,1346,1347,1348,1349,1350,1351,1352,1353,1354,1355};
			int[] numbers ={56,374,371,372,373,656,655,742,1746,1747,1748,1749,1750,1751,1752,1753,1754,1755,1756,1757,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,1770,1771,1772,1773,1774,1775,1776,1777,1778,1779,1780,1781,1782,1783,1784,1785,1786,1787,1788,1789,1790,1791,1826,1827,1828,1829,1830,1831,1832,1792,1793,1794,1795,1796,1797,1798,1799,1800,1801,1802,1803,1804,1805,1806,1807,1808,1809,1810,1811,1812,1813,1814,1815,1816,1817,1818,1819,1820,1821,1822,1823,1824,1825,1575,1738,1739,1740,1741,1742,1743,1744,1745};
			for(int i = 0; i < numbers.length; i++){
				JSONObject paramsObj = new JSONObject();
				paramsObj.put("actionType",1);
				paramsObj.put("userIds","[" + numbers[i] + "]");
				paramsObj.put("responsibilityIds","[35]");
				JSONObject reqParams = new JSONObject();
				reqParams.put("params", paramsObj);

				JSONObject resultObj = ResultUtils.getServiceResult("http://1002-saaf-api-gateway/api/baseServer/baseUserResponsibilityService/saveUserResp", reqParams);
				System.out.println(resultObj.toString());
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, null).toString();
		}catch (Exception e){
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}
}