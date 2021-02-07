package com.sie.watsons.base.plmBase.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.base.user.model.entities.BaseUsersEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandInfoEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.PlmUserBrandMapEntity_HI;
import com.sie.watsons.base.plmBase.model.entities.readonly.PlmBrandInfoEntity_HI_RO;
import com.sie.watsons.base.plmBase.model.inter.*;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.product.model.inter.IPlmUdaAttribute;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/plmBrandInfoService")
public class PlmBrandInfoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmBrandInfoService.class);
	@Autowired
	private IPlmBrandInfo plmBrandInfoServer;

	@Override
	public String delete(String params) {
		return super.delete(params);
	}

	@Autowired
	private IPlmBrandMap plmBrandMapServer;
	@Autowired
	private IPlmUserBrandMap plmUserBrandMapServer;
	@Autowired
	private IPlmGroupBrand groupBrandServer;
	@Autowired
	private IPlmMotherCompany motherCompanyServer;
	@Autowired
	private IPlmUdaAttribute udaAttributeServer;
	@Autowired
	private IPlmProductHead plmProductHeadServer;

	public static final String BASE_SERVER = "http://1006-SAAF-BASE-SERVER";

	public PlmBrandInfoService() {
		super();
	}

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmBrandInfoServer;
	}

	@Resource
	private RestTemplate restTemplate;

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 * @param pageIndex
	 *            当前页码
	 * @param pageRows
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmBrandInfoInfo")
	public String findPlmBrandInfoInfo(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		JSONObject queryParamJSON = parseObject(params);
		Pagination<PlmBrandInfoEntity_HI_RO> dataList = plmBrandInfoServer
				.findPlmBrandInfoInfo(queryParamJSON, pageIndex, pageRows);
		queryParamJSON = (JSONObject) JSON.toJSON(dataList);

		if (dataList.getData().size() > 0) {
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, SUCCESS_MSG);

		} else {
			queryParamJSON.put(SToolUtils.STATUS, "S");
			queryParamJSON.put(SToolUtils.MSG, "没有此品牌信息,是否要进入新建品牌界面?");
		}
		return queryParamJSON.toString();
	}

	/**
	 * 获取brandInfoInfo信息
	 */
	@PostMapping("getPlmBrandInfoInfoById")
	public String getPlmBrandInfoInfoById(@RequestParam(required = false) String brandInfoId,
										  @RequestParam(required = false) String brandCode) {

		PlmBrandInfoEntity_HI brandInfo = null;
		if(StringUtils.isNotBlank(brandInfoId)) {
			brandInfo = plmBrandInfoServer.getById(brandInfoId);
		}else if(StringUtils.isNotBlank(brandCode)){
			JSONObject obj = new JSONObject();
			obj.put("plmLocalBrandCode",brandCode);
			List<PlmBrandInfoEntity_HI> list = plmBrandInfoServer.findList(obj);
			if(CollectionUtils.isNotEmpty(list)) {
				brandInfo = list.get(0);
			}
		}else {
			return SToolUtils.convertResultJSONObj("E", "请求参数不正确"+brandInfoId+","+
					brandCode, 0,null)
					.toString();
		}
		if(null == brandInfo) {
			return SToolUtils.convertResultJSONObj("E", "该品牌信息不存在", 0,null)
					.toString();
		}

		boolean oldData = false;
		JSONObject object = new JSONObject();
		object.put("brandInfo",brandInfo);

		JSONObject params = new JSONObject();
		if(StringUtils.isNotBlank(brandInfo.getPlmMotherCompany())) {
			params.put("motherCompany",brandInfo.getPlmMotherCompany());
		}
		if(StringUtils.isNotBlank(brandInfo.getPlmGroupBrand())) {
			params.put("groupBrand",brandInfo.getPlmGroupBrand());
		}
		params.put("brandnameCn", brandInfo.getPlmBrandCn());
		params.put("brandnameEn",brandInfo.getPlmBrandEn());
		List<PlmProductHeadEntity_HI> productList = plmProductHeadServer.findList(params);
		if(CollectionUtils.isNotEmpty(productList)) {
			oldData = true;//有历史商品
		}

		object.put("oldData",oldData);

		List<PlmUserBrandMapEntity_HI> userBrandMaps = plmUserBrandMapServer.getUserBrandMaps(null, brandInfo.getPlmBrandInfoId());
		JSONArray suppliers = new JSONArray();
		for(PlmUserBrandMapEntity_HI brandMap:userBrandMaps) {
			JSONObject bm = new JSONObject();
			bm.put("mapId", brandMap.getMapId());
			bm.put("status", brandMap.getStatus());
			JSONObject supplier = new JSONObject();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Certificate",getUserSessionBean().getCertificate());
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			ResponseEntity<BaseUsersEntity_HI> result = restTemplate.exchange(BASE_SERVER+"/baseUsersService/getByUserId/"
					+brandMap.getSupUserId(), HttpMethod.GET, entity, BaseUsersEntity_HI.class);

			BaseUsersEntity_HI baseUser = result.getBody();

			bm.put("userBrandMapId",brandMap.getMapId());
			bm.put("userName",null != baseUser?baseUser.getUserName():" ");
			bm.put("userId",brandMap.getSupUserId());
			bm.put("phoneNumber",null != baseUser?baseUser.getPhoneNumber():" ");
			bm.put("userFullName",null != baseUser?baseUser.getUserFullName():" ");
			bm.put("namePingyin",null != baseUser?baseUser.getNamePingyin():" ");
			bm.put("personId",null != baseUser?baseUser.getPersonId():" ");
			bm.put("sourceCode",null != baseUser?baseUser.getSourceCode():" ");
			bm.put("sourceId",null != baseUser?baseUser.getSourceId():" ");
			bm.put("emailAddress",null != baseUser?baseUser.getEmailAddress():" ");
			bm.put("userType",null != baseUser?baseUser.getUserType():" ");
			suppliers.add(bm);
		}
		object.put("suppliers",suppliers);

		return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1,object).toString();
	}

	@Override
	public String saveOrUpdate(String params) {
		return super.saveOrUpdate(params);
	}

	@PostMapping("invalidBrandInfo")
	public String getPlmBrandInfoInfoById(@RequestParam(required = true) String brandInfoId)
	{
		PlmBrandInfoEntity_HI brandInfo = plmBrandInfoServer.getById(brandInfoId);
		if(null == brandInfo) {
			return SToolUtils.convertResultJSONObj("E", "未找到id为"+brandInfoId+"记录",
					0,null).toString();
		}

		brandInfo.setBillStatus("INACTIVE");
		brandInfo.setBillStatusName("已失效");
		plmBrandInfoServer.saveOrUpdate(brandInfo);
		plmUserBrandMapServer.updateStatusByBrandInfoId(brandInfo.getPlmBrandInfoId(),1);//关系修改为已失效

		return SToolUtils.convertResultJSONObj("S", "操作成功",
				0,null).toString();
	}

	/**
	 * @param params
	 * @return java.lang.String
	 * @description 保存
	 **/
	@RequestMapping(method = RequestMethod.POST, value = "savePlmBrandInfoInfo")
	public String savePlmBrandInfoInfo(@RequestParam(required = true) String params,
									   @RequestParam(required = false) String userBrands) {
		try {
			JSONObject parseObject = null;
			if (JSON.parse(params) instanceof JSONArray) {
				parseObject = parseObject(JSON.parseArray(params)
						.getJSONObject(0).toJSONString());
			} else {
				parseObject = parseObject(params);
			}

			//参数中获取用户关品牌信息数据
			JSONArray userBrandMaps = null;
            Integer groupbrandId = null;
            Integer motherCompanyId = null;
			String cname=parseObject.getString("plmBrandCn");
			String ename=parseObject.getString("plmBrandEn");
			groupbrandId= parseObject.getInteger("groupbrandId");
			motherCompanyId=parseObject.getInteger("motherCompanyId");

			if((null == groupbrandId && null == motherCompanyId)||
					(null != groupbrandId && null != motherCompanyId)) {
				throw new Exception("请选择'Group Brand'或'Mother Company'其中一个!");
			}

			PlmBrandInfoEntity_HI bi  = plmBrandInfoServer.findBybrandOrCompany(cname,ename,motherCompanyId, groupbrandId,
					parseObject.getInteger("plmBrandInfoId"),Arrays.asList(new String[]{"APPROVING","TODO",
							"RMSCONFIRM","EFFECTIVE"}));
			if(null != bi) {
				throw new Exception("(品牌管理)中该组合已经存在,请填写其它组合方式!");
			}

			PlmBrandMapEntity_HI bm = plmBrandMapServer.findBybrandOrCompany(cname,ename,motherCompanyId, groupbrandId,
					null, Arrays.asList(new String[]{"APPROVING","MAKING","SUCCESS","RMS_CONFIG"}));
			if(null != bm) {
				throw new Exception("该组合方式在（中英文品牌）中已经存在,请填写其它组合方式!");
			}

			PlmBrandInfoEntity_HI brandInfo = plmBrandInfoServer.savePlmBrandInfoInfo(parseObject);
			if(brandInfo.getBillStatus().equals("TODO")) {
				plmUserBrandMapServer.deleteByBrandInfoId(brandInfo.getPlmBrandInfoId());
			}

			if(StringUtils.isNotBlank(userBrands)) {
				userBrandMaps = JSONArray.parseArray(userBrands);
			}
			//创建Userbrandmap关系
			List<PlmUserBrandMapEntity_HI> list = new ArrayList<PlmUserBrandMapEntity_HI>();
			if(null != userBrandMaps && !userBrandMaps.isEmpty()) {
				Iterator<Object> it = userBrandMaps.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = (JSONObject) it.next();
					if(jsonObj.containsKey("userId")) {
						Integer userId = jsonObj.getInteger("userId");
						String userName = jsonObj.getString("userName");
						String userEmail = jsonObj.getString("userEmail");
						String profileCode = jsonObj.getString("profileCode");
						PlmUserBrandMapEntity_HI ubm = new PlmUserBrandMapEntity_HI();

						ubm.setBrandInfoId(brandInfo.getPlmBrandInfoId());
						ubm.setGroupBrandId(brandInfo.getGroupbrandId());
						ubm.setMotherCompanyId(brandInfo.getMotherCompanyId());
						ubm.setSupUserId(userId);
						ubm.setUserName(userName);
						ubm.setUserEmail(userEmail);
						ubm.setProfileCode(profileCode);
						ubm.setBrandCn(brandInfo.getPlmBrandCn());
						ubm.setBrandCnUdaId(brandInfo.getBrandCnUdaId());
						ubm.setBrandCnUdaValue(brandInfo.getBrandCnUdaValue());
						ubm.setBrandEn(brandInfo.getPlmBrandEn());
						ubm.setBrandEnUdaId(brandInfo.getBrandEnUdaId());
						ubm.setBrandEnUdaValue(brandInfo.getBrandEnUdaValue());
						ubm.setMotherCompanyName(brandInfo.getPlmMotherCompany());
						ubm.setGroupBrandName(brandInfo.getPlmGroupBrand());

						list.add(ubm);
					}
				}
				plmUserBrandMapServer.save(list);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("brandInfo",brandInfo);
			jsonObject.put("userBrandMaps",list);

			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,jsonObject).toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (IllegalStateException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(
					e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E",
					e.getMessage(), 0, JSONArray.parseArray(e.getMessage()))
					.toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 修改已生效的品牌信息中userbrandmap关系
	 * @param params
	 * @return
	 */
	@PostMapping("updateUserBrandMaps")
	public String updateUserBrandMaps(@RequestParam(required = true) String params) {
		try {
			JSONObject parseObject = null;
			parseObject = parseObject(params);
			Integer brandInfoId = parseObject.getInteger("brandInfoId");
			PlmBrandInfoEntity_HI brandInfo = plmBrandInfoServer.getById(brandInfoId);
			if(null == brandInfo) {
				return SToolUtils.convertResultJSONObj("E",
						"未找到id为"+brandInfoId+"的品牌信息记录", 0, null).toString();
			}

			//需要删除的记录
			JSONArray deleteMaps = null;
			//需要新增对的关系记录
			JSONArray addMaps = null;
			if(parseObject.containsKey("deleteMaps")) {
				deleteMaps = parseObject.getJSONArray("deleteMaps");
			}
			if(parseObject.containsKey("addMaps")) {
				addMaps = parseObject.getJSONArray("addMaps");
			}

			List<PlmUserBrandMapEntity_HI> delList = new ArrayList<PlmUserBrandMapEntity_HI>();
			List<PlmUserBrandMapEntity_HI> addList = new ArrayList<PlmUserBrandMapEntity_HI>();
			if(null != deleteMaps) {
				Iterator<Object> it   = deleteMaps.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = (JSONObject) it.next();
					PlmUserBrandMapEntity_HI ubm = null;
					if(jsonObj.containsKey("userBrandMapId")) {
						ubm = plmUserBrandMapServer.getById(jsonObj.getInteger("userBrandMapId"));
					}else {
						return SToolUtils.convertResultJSONObj("E",
								"需要删除供应商id为空", 0, null).toString();
					}

					if(ubm == null) {
						return SToolUtils.convertResultJSONObj("E",
								"查询不到供应商id为"+jsonObj.getInteger("userBrandMapId")+"的记录。", 0, null).toString();
					}
					ubm.setStatus(11);
					delList.add(ubm);
				}
			}

			if(null != addMaps) {
				Iterator<Object> it   = addMaps.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = (JSONObject) it.next();
					PlmUserBrandMapEntity_HI ubm = new PlmUserBrandMapEntity_HI();

					if(!jsonObj.containsKey("userId") || !jsonObj.containsKey("userName")) {//供应商id
						return SToolUtils.convertResultJSONObj("E",
								"缺少供应商id参数或账号参数", 0, null).toString();
					}else {
						ubm.setSupUserId(jsonObj.getInteger("userId"));
						ubm.setUserName(jsonObj.getString("userName"));
						if(jsonObj.containsKey("emailAddress")) {//邮箱
							ubm.setUserEmail(jsonObj.getString("emailAddress"));
						}
					}

					ubm.setBrandInfoId(brandInfo.getPlmBrandInfoId());

					ubm.setBrandInfoId(brandInfo.getPlmBrandInfoId());
					ubm.setGroupBrandId(brandInfo.getGroupbrandId());
					ubm.setMotherCompanyId(brandInfo.getMotherCompanyId());
					ubm.setBrandEn(brandInfo.getPlmBrandEn());
					ubm.setBrandEnUdaId(brandInfo.getBrandEnUdaId());
					ubm.setBrandEnUdaValue(brandInfo.getBrandEnUdaValue());
					ubm.setBrandCn(brandInfo.getPlmBrandCn());
					ubm.setBrandCnUdaId(brandInfo.getBrandCnUdaId());
					ubm.setBrandCnUdaValue(brandInfo.getBrandCnUdaValue());
					ubm.setMotherCompanyName(brandInfo.getPlmMotherCompany());
					ubm.setGroupBrandName(brandInfo.getPlmGroupBrand());
					ubm.setStatus(5);

					addList.add(ubm);
				}
			}

			if(CollectionUtils.isNotEmpty(delList) || CollectionUtils.isNotEmpty(addList)) {
				if(CollectionUtils.isNotEmpty(delList)) {
					plmUserBrandMapServer.save(delList);
				}
				if(CollectionUtils.isNotEmpty(addList)) {
					plmUserBrandMapServer.save(addList);
				}
				brandInfo.setBillStatus("EFFECTIVE_MODIFY");
				brandInfo.setBillStatusName("已生效_审核中");
				plmBrandInfoServer.saveOrUpdate(brandInfo);
			}

			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0,null).toString();

		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils
					.convertResultJSONObj("M", e.getMessage(), 0, null)
					.toString();
		} catch (IllegalStateException e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(
					e.getMessage().startsWith("[{") ? "ERR_IMPORT" : "E",
					e.getMessage(), 0, JSONArray.parseArray(e.getMessage()))
					.toString();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E",
					"服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 审批流回调方法，更改单据审批状态
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveConfirmedPlmBrandStatus")
	public String saveConfirmedPlmBrandStatus(
			@RequestParam(required = false) String params) {
		try {
			JSONObject paramsObject = parseObject(params);
			PlmBrandInfoEntity_HI entity = plmBrandInfoServer
					.saveConfirmedPlmBrandStatus(paramsObject);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, entity).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询
	 * 
	 * @param params
	 *            { teamFrameworkId:框架ID }
	 *            当前页码
	 *            页面行数
	 * @return Pagination
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findPlmBrandInfoCode")
	public String findPlmBrandInfoCode(
			@RequestParam(required = false) String params) {
		// JSONObject queryParamJSON = parseObject(params);
		PlmBrandInfoEntity_HI_RO data = plmBrandInfoServer
				.findPlmBrandInfocode();
		return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1,
				data).toString();
	}

	@PostMapping(value = "initMotherCompanyIdForMC")
	public String initMotherCompanyIdForMC() {
		try {
			plmBrandInfoServer.initMotherCompanyIdForMC();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "初始化成功",
					0, null).toString();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	@PostMapping(value = "initGroupBrandId")
	public String initGroupBrandId() {
		try {
			plmBrandInfoServer.initGroupBrandId();
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "初始化成功",
					0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}
}
