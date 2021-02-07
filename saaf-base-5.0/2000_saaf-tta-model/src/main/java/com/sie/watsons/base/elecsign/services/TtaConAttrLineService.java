package com.sie.watsons.base.elecsign.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.elecsign.model.entities.readonly.TtaConAttrLineEntity_HI_RO;
import com.sie.watsons.base.elecsign.model.inter.ITtaConAttrLine;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaProposalConAttrLineService")
public class TtaConAttrLineService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaConAttrLineService.class);

	@Autowired
	private ITtaConAttrLine ttaProposalConAttrLineServer;

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

    @Autowired
    private ViewObject<BaseAttachmentEntity_HI> baseAttachmentDAO_HI;

	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaProposalConAttrLineServer;
	}


	/**
	 * 文件上传
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "ttaConAttrLineUploadFile", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file) {
		try {

			LOGGER.info("#############################文件上传开始 userId" + this.getSessionUserId() + "#########################################");
			List<ResultFileEntity> list = new ArrayList<>();
			request.setCharacterEncoding("utf-8");
			//1.//业务模块 "tta_side_agrt_header"
			String functionId = request.getParameter("functionId");
			//1.获取业务主键id
			String bussnessId = request.getParameter("id");
			String fileName = request.getParameter("fileName");
			String vendorCode = request.getParameter("vendorCode");
			String orderNo = request.getParameter("orderNo");
			String i = request.getParameter("i");
			String length = request.getParameter("length");
			LOGGER.info("业务主键 bussnessId: {},functionId:{},i:{},length:{}", bussnessId,functionId,i, length);
			Assert.notNull(file, "上传内容为空");
			Assert.notNull(bussnessId, "业务id 不能为空！");
			Assert.notNull(functionId, "functionId 不能为空!");
			if ("0".equals(i)) {
				List<BaseAttachmentEntity_HI_RO> baseAttachmentInfos = baseAttachmentServer.findBaseAttachmentAllFileId(Long.valueOf(bussnessId), "TTA_CON_ATTR_LINE");
				for (BaseAttachmentEntity_HI_RO baseAttachmentInfo : baseAttachmentInfos) {
					ttaProposalConAttrLineServer.deleteByFileId(baseAttachmentInfo.getFileId());
					baseAttachmentServer.deleteById(baseAttachmentInfo.getFileId());
				}
			}
			try (
					InputStream inputStream = file.getInputStream();
			) {
				//5.保存附件到文件系统和base_attachment中
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(bussnessId), this.getSessionUserId());
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(this.getSessionUserId());
				ttaConAttrLineEntity_hi.setFileId(resultFileEntity.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(resultFileEntity.getSourceFileName());
				ttaConAttrLineEntity_hi.setFileType("1");
				ttaConAttrLineEntity_hi.setFileUrl(resultFileEntity.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(0);
				ttaConAttrLineEntity_hi.setVendorCode(vendorCode);
				ttaConAttrLineEntity_hi.setOrderNo(orderNo);
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				list.add(resultFileEntity);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
		} catch (Exception e) {
			LOGGER.error("#############################文件上传失败:{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toJSONString();
		}
	}



	/**
	 * 文件上传
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "ttaPropoaslUploadFile", method = RequestMethod.POST)
	public String uploadPropoasl(@RequestParam("file") MultipartFile file) {
		try {

			LOGGER.info("#############################文件上传开始 userId" + this.getSessionUserId() + "#########################################");
			List<ResultFileEntity> list = new ArrayList<>();
			request.setCharacterEncoding("utf-8");
			//1.//业务模块 "tta_side_agrt_header"
			String functionId = request.getParameter("functionId");
			//1.获取业务主键id
			String bussnessId = request.getParameter("id");
			String fileName = request.getParameter("fileName");
			LOGGER.info("proposal生成PDF业务主键 bussnessId: {},functionId:{}", bussnessId,functionId);
			Assert.notNull(file, "上传内容为空");
			Assert.notNull(bussnessId, "业务id 不能为空！");
			Assert.notNull(functionId, "functionId 不能为空!");
			List<BaseAttachmentEntity_HI_RO> baseAttachmentInfos = baseAttachmentServer.findBaseAttachmentAllFileId(Long.valueOf(bussnessId), "TTA_PROPOSAL_HEADER_PDF");
			for (BaseAttachmentEntity_HI_RO baseAttachmentInfo : baseAttachmentInfos) {
				baseAttachmentServer.deleteById(baseAttachmentInfo.getFileId());
			}
			try (
					InputStream inputStream = file.getInputStream();
			) {
				//5.保存附件到文件系统和base_attachment中
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(bussnessId), this.getSessionUserId());
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", 1, list).toJSONString();
		} catch (Exception e) {
			LOGGER.error("#############################文件上传失败:{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toJSONString();
		}
	}

	/**
	 * 文件上传
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "ttaConAttrLineDeptUploadFile", method = RequestMethod.POST)
	public String ttaConAttrLineDeptUploadFile(@RequestParam("file") MultipartFile file) {
		try {

			LOGGER.info("#############################文件上传开始 userId" + this.getSessionUserId() + "#########################################");
			request.setCharacterEncoding("utf-8");
			String functionId = request.getParameter("functionId");
			//1.获取业务主键id
			String fileInfo = request.getParameter("fileInfo");
			JSONObject fileInfoJson = parseObject(fileInfo);
			String fileName = request.getParameter("fileName");
			Assert.notNull(file, "上传内容为空");
			Assert.notNull(functionId, "functionId 不能为空!");
			SaafToolUtils.validateJsonParms(fileInfoJson,"orgCode","deptCode","conYear","deptFileType");
			Integer findcount = ttaProposalConAttrLineServer.findcount(fileInfoJson);
			if(findcount.intValue() != 0){
				throw new IllegalArgumentException("当前orgCode,deptCode,附件类型,年度 已经存在,不能重复添加");
			}


			List<ResultFileEntity> list = new ArrayList<>();

			try (
					InputStream inputStream = file.getInputStream();
			) {
				//5.保存附件到文件系统和base_attachment中
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, null, this.getSessionUserId());
				TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
				ttaConAttrLineEntity_hi.setOperatorUserId(this.getSessionUserId());
				ttaConAttrLineEntity_hi.setFileId(resultFileEntity.getFileId().intValue());
				ttaConAttrLineEntity_hi.setFileName(resultFileEntity.getSourceFileName());
				ttaConAttrLineEntity_hi.setFileType(fileInfoJson.getString("deptFileType"));
				ttaConAttrLineEntity_hi.setFileUrl(resultFileEntity.getFilePath());
				ttaConAttrLineEntity_hi.setOrderVersion(0);
				ttaConAttrLineEntity_hi.setOrgCode(fileInfoJson.getString("orgCode"));
				ttaConAttrLineEntity_hi.setOrgName(fileInfoJson.getString("orgName"));
				ttaConAttrLineEntity_hi.setDeptCode(fileInfoJson.getString("deptCode"));
				ttaConAttrLineEntity_hi.setDeptName(fileInfoJson.getString("deptName"));
				ttaConAttrLineEntity_hi.setConYear(fileInfoJson.getInteger("conYear"));
				ttaConAttrLineEntity_hi.setOrderNo("0");
				ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);
				list.add(resultFileEntity);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
		} catch (Exception e) {
			LOGGER.error("#############################文件上传失败:{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toJSONString();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "findPagination")
	public String findTtaProposalConAttrLinePagination(@RequestParam(required = false) String params,
													@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
													@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		try {
			JSONObject queryParamJSON = parseObject(params);
			Pagination<TtaConAttrLineEntity_HI_RO> baseJobPagination = ttaProposalConAttrLineServer.findTtaProposalConAttrLinePagination(queryParamJSON, pageIndex, pageRows);
			JSONObject jsonResult = JSON.parseObject(JSON.toJSONString(baseJobPagination));
			jsonResult.put("status", SUCCESS_STATUS);
			return JSON.toJSONString(jsonResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "deleteInfo")
	public String deleteInfo(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			JSONObject result = ttaProposalConAttrLineServer.deleteInfo(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "findFileIds")
	public String findFileIds(@RequestParam(required = false) String params){
		try{
			JSONObject jsonObject = parseObject(params);
			JSONObject result = ttaProposalConAttrLineServer.findFileIds(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, result).toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

}