package com.sie.watsons.base.proposal.services;

import com.alibaba.fastjson.JSON;

import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.watsons.base.proposal.model.entities.TtaProposalHeaderEntity_HI;
import com.sie.watsons.base.proposal.model.entities.readonly.TtaProposalHeaderEntity_HI_RO;
import com.sie.watsons.base.proposal.model.inter.ITtaDeptFeeLine;
import com.sie.watsons.base.proposal.model.inter.ITtaProposalHeader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
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
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;


@RestController
@RequestMapping("/ttaProposalHeaderService")
public class TtaProposalHeaderService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaProposalHeaderService.class);

	@Autowired
	private ITtaProposalHeader ttaProposalHeaderServer;

	@Autowired
	private ITtaDeptFeeLine ttaDeptFeeLineServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaProposalHeaderServer;
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "find")
	public String find(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
				jsonObject = parseObject(params);
			UserSessionBean userSessionBean = this.getUserSessionBean();
			Pagination<TtaProposalHeaderEntity_HI_RO> result = ttaProposalHeaderServer.find(jsonObject, pageIndex, pageRows,userSessionBean);
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
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findVendor")
	public String findVendor(@RequestParam(required = false) String params,
					   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
					   @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject = parseObject(params);
			Pagination<TtaProposalHeaderEntity_HI_RO> result = ttaProposalHeaderServer.findVendor(jsonObject, pageIndex, pageRows);
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
	 * @param params
	 * @description 保存/修改
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveOrUpdate")
	public String saveOrUpdate(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaProposalHeaderEntity_HI instance = ttaProposalHeaderServer.saveOrUpdate(jsonObject, userId,request,response);
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
	 * @param params- 主键
	 * @description 删除数据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(@RequestParam(required = false) String params) {
		try {
			if (StringUtils.isBlank(params)) {
				return SToolUtils.convertResultJSONObj("F", "缺少参数:id", 0, null).toString();
			}
			JSONObject jsonObject = JSON.parseObject(params);
			String[] ids = jsonObject.getString("ids").split(",");
			for (String pkId : ids) {
				ttaProposalHeaderServer.delete(Integer.parseInt(pkId));
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
			JSONObject jsonObject = JSON.parseObject(params);
			String proposal = jsonObject.getString("proposalId");
			if (StringUtils.isBlank(proposal)) {
				jsonObject.fluentPut("proposalId", jsonObject.getString("id"));
			}
			TtaProposalHeaderEntity_HI_RO instance = ttaProposalHeaderServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findLov")
	public String findLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = this.parseObject(params);
			UserSessionBean userSessionBean = this.getUserSessionBean();
			Pagination<TtaProposalHeaderEntity_HI_RO> result = ttaProposalHeaderServer.find(jsonObject, pageIndex, pageRows,userSessionBean);
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
	 * @param params
	 * @description 切换版本
	 */
	@RequestMapping(method = RequestMethod.POST, value = "cutVersion")
	public String cutVersion(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Map<String, Object> resultMap = ttaProposalHeaderServer.callCopyOrder(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, resultMap).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * @param params
	 * @description 变更
	 */
	@RequestMapping(method = RequestMethod.POST, value = "changeOrder")
	public String changeOrder(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Map<String, Object> resultMap = ttaProposalHeaderServer.callChangeOrder(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, resultMap).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * @param params
	 * @description 改变状态
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateStatus")
	public String updateStatus(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			TtaProposalHeaderEntity_HI instance = ttaProposalHeaderServer.updateStatus(jsonObject, userId);
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
	 * @param params
	 * @description 审批前的校验
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findApproveCheck")
	public String findApproveCheck(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			ttaProposalHeaderServer.findApproveCheck(jsonObject, userId);
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
	 * @param params
	 * @description 审批
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateApprove")
	public String updateApprove(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			ttaProposalHeaderServer.updateApprove(jsonObject, userId);
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
	 *  proposal生成页面的单据变更
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "changProposalBillStatus")
	public String changProposalBillStatus(@RequestParam(required = true) String params){
		try {
			Integer userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			if (!"C".equals(jsonObject.get("status"))){
                throw new IllegalArgumentException("单据状态只有在审核通过才能进行操作,当前单据状态为: " + jsonObject.get("status"));
            }
			if (LOGGER.isDebugEnabled()){
			    LOGGER.debug("单据状态为{}",jsonObject.get("status"));
            }
            Map<String, Object> resultMap = ttaProposalHeaderServer.callChangProposalBillStatus(jsonObject, userId);
            Integer proposalId = (Integer)resultMap.get("proposalId");

            jsonObject = new JSONObject();
			JSONObject queryParamJSON = new JSONObject();
            queryParamJSON.put("proposalId",proposalId);
            TtaProposalHeaderEntity_HI_RO instance = ttaProposalHeaderServer.findByRoId(queryParamJSON);

            jsonObject.put("result",(JSONObject)JSON.toJSON(resultMap));
            jsonObject.put("data",(JSONObject)JSON.toJSON(instance));
            jsonObject.put(SToolUtils.STATUS, "S");
            jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
            return jsonObject.toString();

            //return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, resultMap).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * proposal生成管理:切换上一版本
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "cutProposalBillVersion")
	public String cutProposalBillVersion(@RequestParam(required = true) String params){
		try {
			int userId = getSessionUserId();
			Assert.notNull(userId,"用户id不存在");
			JSONObject jsonObject = JSON.parseObject(params);
			Map<String, Object> resultMap = ttaProposalHeaderServer.cutProposalBillVersion(jsonObject, userId);

			Integer proposalId = (Integer)resultMap.get("proposalId");

			jsonObject = new JSONObject();
			JSONObject queryParamJSON = new JSONObject();
			queryParamJSON.put("proposalId",proposalId);
			TtaProposalHeaderEntity_HI_RO instance = ttaProposalHeaderServer.findByRoId(queryParamJSON);

			jsonObject.put("result",(JSONObject)JSON.toJSON(resultMap));
			jsonObject.put("data",(JSONObject)JSON.toJSON(instance));
			jsonObject.put(SToolUtils.STATUS, "S");
			jsonObject.put(SToolUtils.MSG, SUCCESS_MSG);
			return jsonObject.toString();
			//return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, resultMap).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}


	/**
	 * 根据proposal制作年度寻找供应商的销售方式
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProposalSaleType")
	public String findProposalSaleType(@RequestParam(required = true) String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			TtaProposalHeaderEntity_HI_RO instance = ttaProposalHeaderServer.findProposalSaleType(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();

		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 功能描述： 更新是否跳过GM审批状态
	 * @author xiaoga
	 * @date 2019/8/16
	 * @param  
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateSkipStatus")
	public String updateSkipStatus(@RequestParam(required = true) String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			TtaProposalHeaderEntity_HI instance = ttaProposalHeaderServer.updateSkipStatus(jsonObject, this.getSessionUserId());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(value = "queryProccessNodeStauts",method = RequestMethod.POST)
	public String queryProccessNodeStauts(@RequestParam(required = true) String params) {
		try{
			JSONObject jsonParams = this.parseObject(params);
			Map<String, Object> proccessNodeStauts = ttaProposalHeaderServer.findProccessNodeStauts(jsonParams);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, proccessNodeStauts).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

/*	*//**
	 * 文件下载
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	@RequestMapping(value = "printDownload", method = {RequestMethod.GET, RequestMethod.POST})
	public String FileDownload(@RequestParam String hId,String type, HttpServletRequest request, HttpServletResponse response) {
		try {
			Assert.notNull(hId, "缺少参数 hId");
			int userId = this.getSessionUserId();

			//导出PDF
			this.download_file(Long.valueOf(hId),type,userId, request, response);
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "下载成功", 1, null).toJSONString();
		} catch (IllegalArgumentException e) {
			return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e.getMessage(), 0, null).toJSONString();
		}
	}*/

/*	*//**
	 * 下载文件
	 * @param fileId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public void download_file(Long fileId,String type,int userId, HttpServletRequest request, HttpServletResponse response) throws Exception{

		// 设置输出的格式
		response.reset();
		response.setHeader("Content-disposition",String.format("attachment; filename=\"%s\"", "pdf.pdf"));
		response.setContentType("multipart/form-data");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin","*");
		ServletOutputStream outputStream = response.getOutputStream();
		JSONObject jsonObject = new JSONObject();
		JSONObject instance = null ;
		if("FTA".equals(type)){
			jsonObject.put("proposalId",fileId) ;
			instance = ttaDeptFeeLineServer.findDeptFeeLineReport(jsonObject);
			instance.put("ttaDeptFeeReportHead",instance.get("head"));
			instance.put("partyA","甲方：");
		}

		String htmlStr = HtmlGenerator.generate("FTA.html", instance);
		System.out.println(htmlStr);
		PdfGenerator.generatePlus(htmlStr, outputStream);
	}*/

	/**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查找Proposal单据
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findProposalCode")
	public String findProposalCode(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = this.parseObject(params);
			Pagination<TtaProposalHeaderEntity_HI_RO> result = ttaProposalHeaderServer.findProposalCode(jsonObject, pageIndex, pageRows);
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
	 * @param params
	 * @description 更新供应商名称
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateVendorName")
	public String updateVendorName(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			ttaProposalHeaderServer.updateVendorName(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, jsonObject).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}