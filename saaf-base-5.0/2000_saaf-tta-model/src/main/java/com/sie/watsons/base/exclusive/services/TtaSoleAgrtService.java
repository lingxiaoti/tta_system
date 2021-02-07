package com.sie.watsons.base.exclusive.services;

import com.alibaba.fastjson.JSONArray;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.bean.UserSessionBean;
import com.sie.saaf.common.model.dao.BaseCommonDAO_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.HtmlUtils;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.saaf.common.util.Word2PdfUtil;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtEntity_HI;
import com.sie.watsons.base.exclusive.model.entities.TtaSoleAgrtInfoEntity_HI;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrtInfo;
import com.sie.watsons.base.exclusive.utils.DynamicTableDTO;
import com.sie.watsons.base.exclusive.utils.OperateDTO;
import com.sie.watsons.base.exclusive.utils.WordUtils;
import com.sie.watsons.base.params.model.entities.readonly.TempParamDefEntity_HI_RO;
import com.sie.watsons.base.params.model.inter.ITempParamDef;
import com.sie.watsons.base.rule.model.entities.TempRuleDefEntity_HI;
import com.sie.watsons.base.rule.model.entities.readonly.RuleFileTemplateEntity_HI_RO;
import com.sie.watsons.base.rule.model.entities.readonly.TempRuleDefEntity_HI_RO;
import com.sie.watsons.base.rule.model.inter.IRuleFileTemplate;
import com.sie.watsons.base.rule.model.inter.ITempRuleDef;
import com.sie.watsons.base.rule.services.TempRuleDefService;
import com.sie.watsons.base.supplement.model.entities.TtaSaStdHeaderEntity_HI;
import com.sie.watsons.base.supplement.model.entities.readonly.TtaSaStdHeaderEntity_HI_RO;
import com.sie.watsons.base.withdrawal.utils.MergePdf;
import com.yhg.base.utils.StringUtils;
import com.yhg.hibernate.core.dao.ViewObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleAgrtInfoEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleItemEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.entities.readonly.TtaSoleSupplierEntity_HI_RO;
import com.sie.watsons.base.exclusive.model.inter.ITtaSoleAgrt;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;

import java.io.*;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ttaSoleAgrtService")
public class TtaSoleAgrtService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSoleAgrtService.class);

	@Autowired
	private ITtaSoleAgrt ttaSoleAgrtServer;
	@Autowired
	private IRuleFileTemplate ruleFileTemplate;

	@Autowired
	private IBaseAttachment baseAttachmentServer;

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private TempRuleDefService tempRuleDefService;

	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtEntity_HI> ttaSoleAgrtDAO_HI;

	@Autowired
	private BaseCommonDAO_HI<TtaSoleAgrtInfoEntity_HI> ttaSoleAgrtInfoEntity_HI;

	@Autowired
	private ViewObject<TtaConAttrLineEntity_HI> ttaProposalConAttrLineDAO_HI;



	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSoleAgrtServer;
	}
	
	/**
     * 新增头或者更新头
     * @param params
     * @return
     * @throws Exception 
     */        
	@PostMapping("saveOrUpdateTtaSoleAgrt")
	public String editTtaSoleAgrt(@RequestParam(required = true)String params)  {
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			UserSessionBean userSessionBean = this.getUserSessionBean();
			TtaSoleAgrtEntity_HI result = ttaSoleAgrtServer.saveTtaSoleAgrt(parameters,userSessionBean);
			return SToolUtils.convertResultJSONObj("S", "保存成功", 1, result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "保存失败", 0, e).toJSONString();
		}
	}

	/**
	 * 查询详情
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findById")
	@Override
	public String findById(String params) {
		try{
			JSONObject jsonObject = JSON.parseObject(params);
			Integer soleAgrtHId = jsonObject.getInteger("soleAgrtHId");
			if (SaafToolUtils.isNullOrEmpty(soleAgrtHId)){
				jsonObject.put("soleAgrtHId",jsonObject.getInteger("id"));
			}
			TtaSoleAgrtEntity_HI_RO instance = ttaSoleAgrtServer.findByRoId(jsonObject);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 独家协议列表
	 * @param params
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @throws Exception
	 */
	@PostMapping("findTtaSoleAgrtPagination")
    public String findTtaSoleAgrtPagination(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSoleAgrtEntity_HI_RO> page = ttaSoleAgrtServer.findTtaSoleAgrtEntity_HI_RO(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }

	/**
	 * 根据独家渠道类别,独家产品类型,是否包含电商渠道,是否包含独家渠道例外情形 查询 规则设定信息
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ttaSoleAgrtContractFind")
	public String ttaSoleAgrtContractFind(@RequestParam String params) {
		String result = "";
		try {
			JSONObject paramJson = this.parseObject(params);
			TempRuleDefEntity_HI_RO ruleDefEntity = ttaSoleAgrtServer.findRuleInfoByCondition(paramJson);
			result = SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, ruleDefEntity).toString();
			LOGGER.info(".ttaSoleAgrtContractFind 入参:{}, 出参:{}", new Object[]{paramJson, result});
		} catch (Exception e) {
			LOGGER.error("ttaSoleAgrtContractFind error:{}", e);
			result = SToolUtils.convertResultJSONObj(ERROR_STATUS, "查询失败", 1, null).toString();
		}
		return result;
	}

	/**
	 * 合同查看
	 * @param ruleId
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="download")
	public String download(@RequestParam(required = false) Integer ruleId,@RequestParam(required = true) Integer soleAgrtInfoId,
						   @RequestParam(required = true) Integer soleAgrtHId,@RequestParam(required = true) String type){
		LOGGER.info("合同查看下载需要的参数,ruleId:{},soleAgrtInfoId:{},soleAgrtHId:{},type:{}");
		String fileName = "";
		OutputStream outputStream = null;
		InputStream in = null;
		ByteArrayInputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
		ByteArrayOutputStream byteOut = null;
		XWPFTemplate template = null;
		ByteArrayInputStream byteInput = null;
		try{
			Assert.notNull(soleAgrtHId,"独家协议标准头信息未保存,请先保存");
			Assert.notNull(soleAgrtInfoId,"当前合同下载缺失独家协议信息主键");
			Assert.notNull(type,"类型不能为空");
			//合成pdf所需要的字节数组
			List<byte[]> pdfList = new ArrayList<>();
			if ("standard".equals(type)) {
				RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
				if (entity == null || entity.getFileContent() == null) {
					LOGGER.info(this.getClass() + "独家协议模板word文件未上传!");
					return  convertResultJSONObj(ERROR_STATUS,"独家协议模板word文件未上传!",null);
				}
				if (ruleId != null) {
					Map<String, Object> resultMap = tempRuleDefService.generateDocx(ruleId, false);
					in = (FileInputStream)resultMap.get("fis");
					fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".pdf";
				} else {
					throw new IllegalArgumentException("当前选择的独家协议信息未找到合同模板");
				}

				//替换,设置参数
				Map<String, Object> underLineMap = ttaSoleAgrtServer.setParam(soleAgrtInfoId, soleAgrtHId);
				template = XWPFTemplate.compile(in).render(underLineMap);
				byteOut = new ByteArrayOutputStream();
				template.write(byteOut);
				ByteArrayInputStream wordStream = new ByteArrayInputStream(byteOut.toByteArray());
				ByteArrayOutputStream pdfOutputStream = Word2PdfUtil.getDocToPdfOutputStream(wordStream);
				pdfOutputStream.close();
				wordStream.close();
				pdfList.add(pdfOutputStream.toByteArray());
			}else {
				//非标
				BaseAttachmentEntity_HI_RO fileInfo = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(soleAgrtInfoId), "TTA_SOLE_AGRT_INFO");
				inputStream = (ByteArrayInputStream)fastdfsServer.getInputStream(fileInfo.getBucketName(), fileInfo.getPhyFileName());
				byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream((ByteArrayInputStream)inputStream);
				String sourceFileName = fileInfo.getSourceFileName();
				fileName = new String(sourceFileName.substring(0, sourceFileName.lastIndexOf(".")).getBytes(), "ISO8859-1") + ".pdf";
				byteOutputStream.close();
				pdfList.add(byteOutputStream.toByteArray());
			}

			TtaSoleAgrtInfoEntity_HI soleAgrtInfoEntityHi = ttaSoleAgrtInfoEntity_HI.getById(soleAgrtInfoId);
			byte[] pdfItemByte = null;
			if (soleAgrtInfoEntityHi != null) {
				//如果产品类型是全品牌独家,不输出指定产品清单模板
				if (!"1".equals(soleAgrtInfoEntityHi.getProductType())){
					//获取pdf流
					pdfItemByte = ttaSoleAgrtServer.findItemList(soleAgrtInfoId);
				}
			}

			if (pdfItemByte != null) {
				pdfList.add(pdfItemByte);
			}
			//byteInput = new ByteArrayInputStream(MergePdf.mergePdfFiles(pdfList));
			byteInput = new ByteArrayInputStream(MergePdf.mergePdfFilesAndDelWhitePage(pdfList));

			byteInput = ttaSoleAgrtServer.findTtaSoleAgrtHeader(soleAgrtHId,byteInput);
			response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			outputStream = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = byteInput.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
		}catch(Exception e){
			LOGGER.error("文件下载异常", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} finally{
			try {
				if(null != outputStream){
					outputStream.close();
				}
				if (null != in) {
					in.close();
				}
				if (null != template) {
					template.close();
				}
				if (null != byteOut) {
					byteOut.close();
				}
				if (null != byteInput) {
					byteInput.close();
				}
			} catch (IOException e) {
				LOGGER.error("文件下载关闭文件流异常", e);
			}
		}
		return null;
	}


	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value="trrigerDownload")
	public String trrigerDownload(@RequestParam(required = true) String soleAgrtCode){
		LOGGER.info("合同查看下载需要的参数,ruleId:{},soleAgrtInfoId:{},soleAgrtHId:{},type:{}");
		String fileName = "";
		OutputStream outputStream = null;
		InputStream in = null;
		ByteArrayInputStream inputStream = null ;
		ByteArrayOutputStream byteOutputStream = null ;
		ByteArrayOutputStream byteOut = null;
		XWPFTemplate template = null;
		ByteArrayInputStream byteInput = null;
		try{
			Assert.notNull(soleAgrtCode,"缺失单据编号");
			TtaSoleAgrtEntity_HI instance = null;
			List<TtaSoleAgrtEntity_HI> soleList = ttaSoleAgrtServer.findSoleAgrt(soleAgrtCode);
			//List<TtaSoleAgrtEntity_HI> soleList = ttaSoleAgrtDAO_HI.findByProperty(new JSONObject().fluentPut("soleAgrtCode", soleAgrtCode));
			if (CollectionUtils.isNotEmpty(soleList)) {
				instance = soleList.get(0);
			}
			Assert.notNull(instance,"没有找到独家协议信息");
			Integer soleAgrtHId = instance.getSoleAgrtHId();
			String type = instance.getAgrtType();
			Integer soleAgrtInfoId = null;
			TempRuleDefEntity_HI_RO ruleInfoByCondition = null;
			Integer ruleId = null;

			List<TtaSoleAgrtInfoEntity_HI> byProperty = ttaSoleAgrtServer.findSoleAgrtInfo(soleAgrtHId);
			//List<TtaSoleAgrtInfoEntity_HI> byProperty = ttaSoleAgrtInfoEntity_HI.findByProperty(new JSONObject().fluentPut("soleAgrtHId",instance.getSoleAgrtHId()));
			if (CollectionUtils.isNotEmpty(byProperty)) {
				TtaSoleAgrtInfoEntity_HI soleAgrtInfoEntity_hi = byProperty.get(0);
				soleAgrtInfoId = soleAgrtInfoEntity_hi.getSoleAgrtInfoId();
				JSONObject queryParamJSON = new JSONObject();
				queryParamJSON.put("channelType",soleAgrtInfoEntity_hi.getChannelType());//独家渠道类别
				queryParamJSON.put("productType",soleAgrtInfoEntity_hi.getProductType());//独家产品类型
				queryParamJSON.put("isEcChannel",soleAgrtInfoEntity_hi.getIsEcChannel());//是否包含电商渠道
				queryParamJSON.put("isException",soleAgrtInfoEntity_hi.getIsException());//是否包含独家渠道例外情形
				ruleInfoByCondition = ttaSoleAgrtServer.findRuleInfoByCondition(queryParamJSON);
				ruleId = ruleInfoByCondition.getRulId();
			}

			Assert.notNull(soleAgrtHId,"独家协议标准头信息未保存,请先保存");
			Assert.notNull(soleAgrtInfoId,"当前合同下载缺失独家协议信息主键");
			Assert.notNull(type,"类型不能为空");
			Assert.notNull(ruleInfoByCondition,"独家协议模板文件未上传");
			//合成pdf所需要的字节数组
			List<byte[]> pdfList = new ArrayList<>();
			if ("standard".equals(type)) {
				RuleFileTemplateEntity_HI_RO entity = ruleFileTemplate.findByBusinessType("1");
				if (entity == null || entity.getFileContent() == null) {
					LOGGER.info(this.getClass() + "独家协议模板word文件未上传!");
					return  convertResultJSONObj(ERROR_STATUS,"独家协议模板word文件未上传!",null);
				}
				if (ruleId != null) {
					Map<String, Object> resultMap = tempRuleDefService.generateDocx(ruleId, false);
					in = (FileInputStream)resultMap.get("fis");
					fileName = new String((resultMap.get("fileName") + "").getBytes(), "ISO8859-1") + ".pdf";
					fileName = resultMap.get("fileName") + "" + ".pdf";
				} else {
					throw new IllegalArgumentException("当前选择的独家协议信息未找到合同模板");
				}

				//替换,设置参数
				Map<String, Object> underLineMap = ttaSoleAgrtServer.setParam(soleAgrtInfoId, soleAgrtHId);
				template = XWPFTemplate.compile(in).render(underLineMap);
				byteOut = new ByteArrayOutputStream();
				template.write(byteOut);
				ByteArrayInputStream wordStream = new ByteArrayInputStream(byteOut.toByteArray());
				ByteArrayOutputStream pdfOutputStream = Word2PdfUtil.getDocToPdfOutputStream(wordStream);
				pdfOutputStream.close();
				wordStream.close();
				pdfList.add(pdfOutputStream.toByteArray());
			}else {
				//非标
				BaseAttachmentEntity_HI_RO fileInfo = baseAttachmentServer.findMaxBaseAttachmentInfo(Long.valueOf(soleAgrtInfoId), "TTA_SOLE_AGRT_INFO");
				inputStream = (ByteArrayInputStream)fastdfsServer.getInputStream(fileInfo.getBucketName(), fileInfo.getPhyFileName());
				byteOutputStream = Word2PdfUtil.getDocToPdfOutputStream((ByteArrayInputStream)inputStream);
				fileName = new String(fileInfo.getSourceFileName().getBytes(), "ISO8859-1") + ".pdf";
				byteOutputStream.close();
				pdfList.add(byteOutputStream.toByteArray());
			}

			//获取pdf流
			byte[] pdfItemByte = ttaSoleAgrtServer.findItemList(soleAgrtInfoId);
			if (pdfItemByte != null) {
				pdfList.add(pdfItemByte);
			}
			byteInput = new ByteArrayInputStream(MergePdf.mergePdfFiles(pdfList));
			//插入水印和logo
			byteInput = ttaSoleAgrtServer.insertWatermarkAndLogo(soleAgrtHId,byteInput);

			//插入文件到文件服务器上
			ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(byteInput,fileName, "TTA_SOLE_CONTRACT_FILE", Long.valueOf(soleAgrtHId), -1);
			TtaConAttrLineEntity_HI ttaConAttrLineEntity_hi = new TtaConAttrLineEntity_HI();
			ttaConAttrLineEntity_hi.setOperatorUserId(-1);
			ttaConAttrLineEntity_hi.setFileId(resultFileEntity.getFileId().intValue());
			ttaConAttrLineEntity_hi.setFileName(resultFileEntity.getSourceFileName());
			if ("standard".equals(instance.getAgrtType())) {
				ttaConAttrLineEntity_hi.setFileType("2");//独家协议-标准
			} else {
				ttaConAttrLineEntity_hi.setFileType("3");//独家协议-非标
			}
			ttaConAttrLineEntity_hi.setFileUrl(resultFileEntity.getFilePath());
			ttaConAttrLineEntity_hi.setOrderVersion(Integer.valueOf(instance.getSoleAgrtVersion()));
			ttaConAttrLineEntity_hi.setVendorCode(instance.getVendorCode());
			ttaConAttrLineEntity_hi.setOrderNo(instance.getSoleAgrtCode());
			ttaConAttrLineEntity_hi.setOrgCode(instance.getOrgCode());
			ttaConAttrLineEntity_hi.setOrgName(instance.getOrgName());
			ttaConAttrLineEntity_hi.setDeptCode(instance.getDeptCode());
			ttaConAttrLineEntity_hi.setDeptName(instance.getDeptName());
			ttaProposalConAttrLineDAO_HI.saveOrUpdate(ttaConAttrLineEntity_hi);

		}catch(Exception e){
			LOGGER.error("文件下载异常", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		} finally{
			try {
				if(null != outputStream){
					outputStream.close();
				}
				if (null != in) {
					in.close();
				}
				if (null != template) {
					template.close();
				}
				if (null != byteOut) {
					byteOut.close();
				}
				if (null != byteInput) {
					byteInput.close();
				}
			} catch (IOException e) {
				LOGGER.error("文件下载关闭文件流异常", e);
			}
		}
		return "执行成功";
	}

	@PostMapping("findTtaSoleSupplierEntity_HI_RO")
    public String findTtaSoleSupplierEntity_HI_RO(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSoleSupplierEntity_HI_RO> page = ttaSoleAgrtServer.findTtaSoleSupplierEntity_HI_RO(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
	
	@PostMapping("findTtaSoleAgrtInfoEntity")
    public String findTtaSoleAgrtInfoEntity(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSoleAgrtInfoEntity_HI_RO> page = ttaSoleAgrtServer.findTtaSoleAgrtInfoEntity_HI_RO(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }
	
	@PostMapping("findTtaSoleItemEntity")
    public String findTtaSoleItemEntity(@RequestParam(required = false) String params,
		@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
		@RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
        try {
			JSONObject parameters = this.parseObject(params);
			Pagination<TtaSoleItemEntity_HI_RO> page = ttaSoleAgrtServer.findTtaSoleItemEntity_HI_RO(parameters, pageIndex, pageRows);
			JSONObject results = JSONObject
					.parseObject(JSON.toJSONString(page));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败", 0, e)
					.toJSONString();
		} 
    }

	/**
	 * 提交
	 * @param params
	 * @return
	 */
	@PostMapping("ttaSoleAgrtSubmitInfo")
    public String ttaSoleAgrtSubmitInfo(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSoleAgrtEntity_HI result = ttaSoleAgrtServer.sumbitSoleAgrtInfo(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", "提交成功", 1, result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "提交失败", 0, e).toJSONString();
		}
	}

	/**
	 * 更新状态
	 * @param params
	 * @return
	 */
	@PostMapping("updateStatus")
	public String updateStatus(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			ttaSoleAgrtServer.updateStatus(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, null).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "服务异常:" + e.getMessage(), 0, e).toJSONString();
		}
	}

	/**
	 * 取消
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "ttaSoleAgrtCancal")
	public String deleteSoleAgrtInfoById(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSoleAgrtEntity_HI result = ttaSoleAgrtServer.ttaSoleAgrtCancal(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, result).toJSONString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	@RequestMapping(method = RequestMethod.POST,value = "checkBillsStatus")
	public String checkTtaSoleAgrtBillCodeStatus(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSoleAgrtEntity_HI result = ttaSoleAgrtServer.checkTtaSoleAgrtBillCodeStatus(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 1, result).toJSONString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error("错误信息 :{},具体错误:{} ",e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 作废
	 * @param params
	 * @return
	 */
	@PostMapping("ttaSoleAgrtDiscard")
	public String ttaSoleAgrtDiscard(@RequestParam(required = true) String params){
		try {
			int userId = this.getSessionUserId();
			JSONObject parameters = this.parseObject(params);
			TtaSoleAgrtEntity_HI result = ttaSoleAgrtServer.ttaSoleAgrtDiscard(parameters,userId);
			return SToolUtils.convertResultJSONObj("S", "作废成功", 1, result).toJSONString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(),e);
			return SToolUtils.convertResultJSONObj("E", "作废失败", 0, e).toJSONString();
		}
	}

	/**
	 * 变更单据
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "ttaSoleAgrtChange")
	public String callSoleAgrtChangStatus(@RequestParam(required = true) String params){
		try {
			Integer userId = getSessionUserId();
			JSONObject jsonObject =this.parseObject(params);
			JSONObject instance = ttaSoleAgrtServer.callSoleAgrtChangStatus(jsonObject, userId);
			instance.put(SToolUtils.STATUS, "S");
			instance.put(SToolUtils.MSG, SUCCESS_MSG);
			return instance.toString();
		}catch (IllegalArgumentException e){
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 *更新GM审批状态
	 * @param params
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "updateExclusiveSkipStatus")
	public String updateSkipStatus(@RequestParam(required = true) String params) {
		try{
			JSONObject jsonObject = this.parseObject(params);
			TtaSoleAgrtEntity_HI instance = ttaSoleAgrtServer.updateExclusiveSkipStatus(jsonObject,this.getUserSessionBean());
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, instance).toString();
		}catch (Exception e){
			LOGGER.error(e.getMessage(),e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}

	public static void main(String[] args) {
		LocalDateTime time = LocalDateTime.now();
		//格式化输出
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
		System.out.println(time.format(formatter));

		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy");//年份
		String yearformat = time.format(formatter1);
		String substring = yearformat.substring(2);
		System.out.println(substring);

		DateTimeFormatter formatter2= DateTimeFormatter.ofPattern("MM");//月份
		System.out.println(time.format(formatter2));

		DateTimeFormatter formatter3= DateTimeFormatter.ofPattern("dd");//日
		System.out.println(time.format(formatter3));

		//LocalDate
		LocalDate now = LocalDate.now();
		System.out.println(now.getYear());
		System.out.println(now.getMonthValue());
		System.out.println(now.getDayOfMonth());

		System.out.println("Date转换成LocalDate");

		//LocalDate localDate = date2LocalDate(new Date());
		//System.out.println(localDate.getYear());
		//System.out.println(localDate.getMonthValue());
	}

}