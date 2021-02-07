package com.sie.watsons.base.productEco.services;


import java.util.Date;
import java.util.List;

import com.sie.watsons.base.product.model.entities.PlmDataAclHeaderEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmDataAclLineEntity_HI;
import com.sie.watsons.base.product.model.entities.PlmProductHeadEntity_HI;
import com.sie.watsons.base.product.model.inter.IPlmDataAclHeader;
import com.sie.watsons.base.product.model.inter.IPlmDataAclLine;
import com.sie.watsons.base.product.model.inter.IPlmProductHead;
import com.sie.watsons.base.redisUtil.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.product.model.entities.PlmProductModifyCheckEntity_HI;
import com.sie.watsons.base.productEco.model.entities.PlmProductHeadEcoEntity_HI;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductHeadEcoEntity_HI_RO;
import com.sie.watsons.base.productEco.model.entities.readonly.PlmProductParamEcoEntity_HI_RO;
import com.sie.watsons.base.productEco.model.inter.IPlmProductHeadEco;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;

@RestController
@RequestMapping("/plmProductHeadEcoService")
public class PlmProductHeadEcoService extends CommonAbstractService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlmProductHeadEcoService.class);

	@Autowired
	private IPlmProductHeadEco plmProductHeadEcoServer;

	@Autowired
	private ViewObject<PlmProductHeadEntity_HI> plmProductHeadServer;

	@Override
	public IBaseCommon getBaseCommonServer() {
		return this.plmProductHeadEcoServer;
	}
	@Autowired
	private IPlmDataAclHeader plmDataAclHeaderServer;// 权限表
	@Autowired
	private IPlmDataAclLine IPlmDataAclLineServer; // 权限行表
	/**
	 * 2020/05/26
	 * 
	 * @Title: 修改时新增 , 修改表数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "addModifyProductHead")
	public String addModifyProductHead(
			@RequestParam(required = false) String params) {
		try {

			JSONObject param = parseObject(params);

			SaafToolUtils
					.validateJsonParms(param, "productHeadId", "varUserId");

			plmProductHeadEcoServer.addModifyProductHead(param);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2020/05/26
	 * 
	 * @Title: 查询修改修改信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findModifyProductHead")
	public String findModifyProductHead(
			@RequestParam(required = false) String params) {
		try {

			JSONObject param = parseObject(params);

			SaafToolUtils
					.validateJsonParms(param, "productHeadId", "varUserId");

			// todo:根据2个参数 查询到修改单的ID
			Integer ecoId = plmProductHeadEcoServer.getEcoIdByParams(param);
			// 根据修改单ID 查询放回结果
			PlmProductParamEcoEntity_HI_RO ecoResult = plmProductHeadEcoServer
					.findEcoInfoByEcoId(ecoId);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, ecoResult).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2020/05/26
	 * 
	 * @Title: 查询修改修改信息
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateModifyProductHead")
	public String updateModifyProductHead(
			@RequestParam(required = false) String params) {
		try {
//			JSONObject data = JSONObject.parseObject(params);
			Integer orgId=this.getUserSessionBean().getOrgId();
			JSONObject param = parseObject(params);
			JSONObject jo = param.getJSONObject("headInfo");
			if(jo.containsKey("ecoId"))
			{
				Integer ecoId=jo.getInteger("ecoId");
				PlmProductHeadEcoEntity_HI sc=plmProductHeadEcoServer.getById(ecoId); //原单据数据
				//判断权限
				if (sc.getEcoDeptId() != null) {
					Integer ecodept = sc.getEcoDeptId();
					JSONObject deptparam = new JSONObject();
					deptparam.put("deptId", orgId);
					List<PlmDataAclHeaderEntity_HI> headli = plmDataAclHeaderServer
							.findList(deptparam);
					if (headli.size() > 0) {
						PlmDataAclHeaderEntity_HI headinfo = headli.get(0);
						Integer headid = headinfo.getHeadId();
						JSONObject lineparam = new JSONObject();
						lineparam.put("headId", headid);
						lineparam.put("enableFlag", "Y");
						List<PlmDataAclLineEntity_HI> linedata = IPlmDataAclLineServer
								.findList(lineparam);
						for (PlmDataAclLineEntity_HI line : linedata) {
							String groupCode = line.getGroupCode();
							String acltype = line.getAclType();
							if (acltype.equals("UPDATE")) {
								if (!groupCode.equals("PUBLIC")) {
									if (!orgId.equals(ecodept)) {
										//throw new ServerException("当前用户没有该单据修改权限,无法保存或提交!");
										return SToolUtils.convertResultJSONObj(ERROR_STATUS,
												"当前用户没有该单据修改权限,无法保存或提交!", 0, null).toString();
									}
								}
							}
						}
					}
				}
				//判断状态
				if (sc.getEcoStatus() != null) {
					String oldorders = sc.getEcoStatus();
					if (oldorders.equals("APPROVING") || oldorders.equals("RMS_CONFIG") || oldorders.equals("SUCCESS") || oldorders.equals("EFFECTIVE")
							|| oldorders.equals("CANCEL")) {

						return SToolUtils.convertResultJSONObj(ERROR_STATUS,
								"当前修改单状态不允许保存或提交", 0, null).toString();
					}
				}
			}
			// SaafToolUtils.validateJsonParms(param, "ecoId","varUserId");
			String result = plmProductHeadEcoServer
					.updateModifyProductHead(param);

			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, result).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2020/05/26
	 * 
	 * @Title: 查询修改单分页
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProductHeadEcoList")
	public String findProductHeadEcoList(
			@RequestParam(required = false) String params,
			@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
			@RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject param = parseObject(params);

			// SaafToolUtils.validateJsonParms(param,
			// "productHeadId","varUserId");

			// todo:分页查询数据
			// Integer ecoId =
			// plmProductHeadEcoServer.findProductHeadEcoList(param);
			// 根据修改单ID 查询放回结果
			 ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			 ResultUtils.getLookUpValue("PLM_PRODUCT_STATUS");
			 ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
			 ResultUtils.getLookUpValue("PLM_PRODUCT_UPD_STATUS");
			 ResultUtils.getLookUpValue("PLM_PRODUCT_UPD_STATUS");
			Pagination<PlmProductHeadEcoEntity_HI_RO> ecoResult = plmProductHeadEcoServer
					.findProductHeadEcoList(param, pageIndex, pageRows);
			String resultString = JSONObject.toJSONString(ecoResult);
			JSONObject jsonObject = JSONObject.parseObject(resultString);
			jsonObject.put(SToolUtils.STATUS, SUCCESS_STATUS);
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}

	}

	/**
	 * 2020/05/26
	 * 
	 * @Title: 查询修改单分页
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEcoInfoByEcoId")
	public String findEcoInfoByEcoId(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "ecoId");
			Integer ecoId = param.getInteger("ecoId");
			ResultUtils.getLookUpValue("PLM_PRODUCT_PURCHASE");
			ResultUtils.getLookUpValue("PLM_PRODUCT_STATUS");
			ResultUtils.getLookUpValue("PLM_PRODUCT_ORTHERINFO");
			ResultUtils.getLookUpValue("PLM_PRODUT_ORDERSTATUS");
			PlmProductParamEcoEntity_HI_RO ecoResult = plmProductHeadEcoServer
					.findEcoInfoByEcoId(ecoId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, ecoResult).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 2020/05/26
	 * 
	 * @Title: 查询修改单分页
	 */
	@RequestMapping(method = RequestMethod.POST, value = "diferenceCheckByEcoId")
	public String diferenceCheckByEcoId(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);
			SaafToolUtils.validateJsonParms(param, "ecoId");
			Integer ecoId = param.getInteger("ecoId");
			String ecoResult = plmProductHeadEcoServer
					.diferenceCheckByEcoId(ecoId);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, ecoResult).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}

	// 修改单据状态
	/**
	 * 2020/06/12
	 * 
	 * @author caojin
	 * @Title: 修改单据状态
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateHeadByEcoCode")
	public String updateHeadByEcoCode(
			@RequestParam(required = false) String params) {
		try {
			JSONObject param = parseObject(params);

			PlmProductHeadEcoEntity_HI head = null;
			SaafToolUtils.validateJsonParms(param, "ecoNo", "status");
			String econo = param.getString("ecoNo");
			String orderStatus = param.getString("status");
			JSONObject ecojson = new JSONObject();
			ecojson.put("ecoNo", econo);
			String billNo = param.getString("billNo");
			String INCIDENT = param.getString("INCIDENT");
			String VERSION = param.getString("VERSION");
			String taskId = param.getString("TASKID");
			String TASKUSER = param.getString("TASKUSER");
			String allno = param.getString("ALLNO");

			List<PlmProductHeadEcoEntity_HI> heli = plmProductHeadEcoServer
					.findList(ecojson);
			if (heli.size() > 0) {
				head = heli.get(0);
				
			}

			if (head != null) {
				//head.setOrderStatus(orderStatus);
				head.setEcoStatus(orderStatus);
				if (allno.equals("submit")) { // 保存当前流程提交人
					head.setUpdateInstanceid(TASKUSER);
					head.setRmsReverBusinesskey(INCIDENT);
					
				}
				if (orderStatus.equals("REJECT")) // 已驳回
				{
					String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
							+ "&&&" + taskId + "&&&" + TASKUSER + "&&&" + allno;
					head.setAddProcessname(refus);
					head.setRmsReverBusinesskey(INCIDENT);
				} else if (orderStatus.equals("APPROVING")
						&& !allno.equals("submit")) // 取消
				{
					head.setAddProcessname(null);
				} else if (orderStatus.equals("RMS_CONFIG")) {
					String refus = billNo + "&&&" + INCIDENT + "&&&" + VERSION
							+ "&&&" + taskId + "&&&" + TASKUSER + "&&&" + allno;
					head.setAddProcessname(refus);
					head.setRmsReverBusinesskey(INCIDENT);

					PlmProductHeadEntity_HI he=plmProductHeadServer.getById(head.getProductHeadId());
					he.setLastUpdateDate(new Date());
					plmProductHeadServer.update(he);

				}

				else {
					head.setRmsReverBusinesskey(INCIDENT);

				}
				plmProductHeadEcoServer.update(head);
				plmProductHeadEcoServer.updateModify(" update PLM_PRODUCT_MODIFY_CHECK set status='"+orderStatus+"' where eco_id="+head.getEcoId() );
			
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG,
					1, "success").toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS,
					e.getMessage(), 0, null).toString();
		}
	}
}