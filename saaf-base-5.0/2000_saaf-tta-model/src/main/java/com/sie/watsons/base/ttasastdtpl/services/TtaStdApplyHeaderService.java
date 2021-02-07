package com.sie.watsons.base.ttasastdtpl.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.sso.model.inter.ISsoServer;
import com.sie.watsons.base.ttasastdtpl.model.entities.TtaStdApplyHeaderEntity_HI;
import com.sie.watsons.base.ttasastdtpl.model.entities.readonly.TtaStdApplyHeaderEntity_HI_RO;
import com.sie.watsons.base.ttasastdtpl.model.inter.ITtaStdApplyHeader;

import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.withdrawal.utils.ResourceUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/ttaStdApplyHeaderService")
public class TtaStdApplyHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaStdApplyHeaderService.class);

	@Autowired
	private ITtaStdApplyHeader ttaStdApplyHeaderServer;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	ISsoServer ssoServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaStdApplyHeaderServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = parseObject(params);
			}
			Pagination<TtaStdApplyHeaderEntity_HI_RO> result = ttaStdApplyHeaderServer.find(jsonObject, pageIndex, pageRows);
			jsonObject = (JSONObject) JSON.toJSON(result);
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params- 主键
	 * @description 作废数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cancel")
	public String cancel(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaStdApplyHeaderServer.updateStatus(Integer.parseInt(pkId),jsonObject.getInteger("varUserId"));
			}
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, null).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 查询详情
	 *
	 * @param params
	 *
	 * @returnfindById
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = parseObject(params);
			String stdApplyHeaderId = jsonObject.getString("stdApplyHeaderId");
			if (StringUtils.isBlank(stdApplyHeaderId)) {
				jsonObject.fluentPut("stdApplyHeaderId", jsonObject.getString("id"));
			}
			TtaStdApplyHeaderEntity_HI_RO instance = ttaStdApplyHeaderServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			JSONObject jsonObject = parseObject(params);
			TtaStdApplyHeaderEntity_HI instance = ttaStdApplyHeaderServer.saveOrUpdate(jsonObject,getUserSessionBean(), jsonObject.getInteger("varUserId"));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 待审批
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public String updateStatus(@RequestParam(required = false) String params) {
		try {
			JSONObject paramsJSON = parseObject(params);
			JSONArray jsonObject = ttaStdApplyHeaderServer.updateStatus(paramsJSON, paramsJSON.getIntValue("varUserId"));
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn("TTA标准模板申请审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.warn("TTA标准模板申请审批异常:{}--{}", e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "TTA标准模板申请服务异常", 0, null).toString();
		}
	}

	/**
	 * 变更
	 */
	@RequestMapping(method = RequestMethod.POST, value = "changeApplyAll")
	public String changeApplyAll(@RequestParam(required = true) String params) {
		try {

			JSONObject jsonObject = parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject, "stdApplyHeaderId");
			TtaStdApplyHeaderEntity_HI entity = ttaStdApplyHeaderServer.saveChangeApplyAll(jsonObject,getUserSessionBean(), jsonObject.getInteger("varUserId"));
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, entity).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param businessId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "showPdf", method = {RequestMethod.GET, RequestMethod.POST})
	public void showPdf(@RequestParam String businessId,@RequestParam String Certificate, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!ssoServer.authCookie(Certificate)) {
				throw new RuntimeException("无法打开文件，没有登录"+ Certificate + "-" +businessId);
			}
			Assert.notNull(businessId, "缺少参数 businessId");
			int userId = this.getSessionUserId();
			ttaStdApplyHeaderServer.showPdf(businessId,request,response);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "openEdit")
	public String openEdit(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			SaafToolUtils.validateJsonParms(jsonObject,"fileId");
			String url  = ResourceUtils.getPageOfficecUrl();
			JSONObject result = new JSONObject();
			PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
			poCtrl.setServerPage("/api/ttaServer/pageOfficeService/poserver.zz");
			poCtrl.setSaveFilePage(url + "pageOfficeService/saveEdit?businessId=" + jsonObject.getInteger("fileId") +"&Certificate=" +request.getHeader("Certificate")+"&type=TTA_STD_APPLY_HEADER");
			poCtrl.webOpen(url + "pageOfficeService/openDoc?businessId=" + jsonObject.getInteger("fileId") +"&Certificate=" +request.getHeader("Certificate")+"&type=TTA_STD_APPLY_HEADER", OpenModeType.docNormalEdit, getUserSessionBean().getUserFullName());
			String editor = poCtrl.getHtmlCode("PageOfficeCtrl1");
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, editor).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}



}