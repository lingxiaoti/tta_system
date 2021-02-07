package com.sie.watsons.base.pos.recover.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.watsons.base.pos.qualificationreview.model.entities.EquPosZzscCredentialAttachEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.EquPosSupplierRecoverEntity_HI;
import com.sie.watsons.base.pos.recover.model.entities.readonly.EquPosSupplierRecoverEntity_HI_RO;
import com.sie.watsons.base.pos.recover.model.inter.IEquPosSupplierRecover;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSuppInfoWithDeptEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierCredentialsEntity_HI;
import com.sie.watsons.base.pos.supplierinfo.model.entities.EquPosSupplierInfoEntity_HI;
import com.sie.watsons.base.utils.CommonUtils;
import com.sie.watsons.base.utils.ResultUtils;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.dao.ViewObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@RestController
@RequestMapping("/equPosSupplierRecoverService")
public class EquPosSupplierRecoverService extends CommonAbstractService {
private static final Logger LOGGER = LoggerFactory.getLogger(EquPosSupplierRecoverService.class);
	@Autowired
	private IEquPosSupplierRecover equPosSupplierRecoverServer;
	public EquPosSupplierRecoverService() {
		super();
	}

	@Override
	public IBaseCommon<?> getBaseCommonServer() {
		return null;
	}

	@Autowired
	private IFastdfs fastdfsServer;

	@Autowired
	private ViewObject<EquPosSupplierInfoEntity_HI> equPosSupplierInfoDAO_HI;

	@Autowired
	private ViewObject<EquPosSuppInfoWithDeptEntity_HI> equPosSuppInfoWithDeptDAO_HI;

	@Autowired
	private ViewObject<EquPosZzscCredentialAttachEntity_HI> equPosZzscCredentialAttachDAO_HI;

	@Autowired
	private ViewObject<EquPosSupplierCredentialsEntity_HI> equPosSupplierCredentialsDAO_HI;

	private String UTF8 ="utf-8";

	public static final String FILE_TYPE = "JPEG、PSD、PDD、JPEG、SVG、DOC、DOCX、XLS、MSG、RAR、ZIP、PPTX、XLSX、XML、TXT、BMP、GIF、PDF、JPG、PNG、XLS、XLSX、RAR、CSV";


	/**
	 * 查询供应商暂停淘汰INFO（分页）
	 * @param params
	 * @param pageIndex 页码
	 * @param pageRows 每页查询记录数
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findEquPosRecoverInfo")
	public String findEquPosRecoverInfo(@RequestParam(required = false) String params,
											@RequestParam(required = false,defaultValue = "1") Integer pageIndex,
											@RequestParam(required = false,defaultValue = "10") Integer pageRows) {
		LOGGER.info(params);
		try {
			//权限校验begin
//			JSONObject checkParamsJONS =parseObject(params);
//			CommonUtils.interfaceAccessControl(checkParamsJONS.getInteger("operationRespId"),"GYSHF");
			//权限校验end
			Pagination<EquPosSupplierRecoverEntity_HI_RO> infoList = equPosSupplierRecoverServer.findEquPosRecoverInfo(params, pageIndex, pageRows);


			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierStatus");
			incomingParam.add("supRecoverStatus");
			incomingParam.add("piSupplierStatus");
			efferentParam.add("supplierDeptStatusName");
			efferentParam.add("supRecoverStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			JSONObject returnJson = (JSONObject) JSON.toJSON(infoList);
			JSONArray data = returnJson.getJSONArray("data");
			data = ResultUtils.getLookUpValues( data , incomingParam, efferentParam, typeParam);
			returnJson.put("data",data);
			returnJson.put("status","S");
			return JSON.toJSONString(returnJson);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
		}
	}


    /**
     * 查询供应商暂停淘汰详情
     * @param params
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "findSupRecoverDatail")
    public String findSupRecoverDatail(@RequestParam  String params ) {
        LOGGER.info(params);
        try {
			EquPosSupplierRecoverEntity_HI_RO  returnJson = equPosSupplierRecoverServer.findSupRecoverDatail(params );

			JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(returnJson));
			List<String> incomingParam = new ArrayList<>();
			List<String> efferentParam = new ArrayList<>();
			List<String> typeParam = new ArrayList<>();
			incomingParam.add("supplierStatus");
			incomingParam.add("suprecoverStatus");
			incomingParam.add("piSupplierStatus");
			efferentParam.add("supplierDeptStatusName");
			efferentParam.add("supRecoverStatusName");
			efferentParam.add("supplierStatusName");
			typeParam.add("EQU_SUPPLIER_STATUS");
			typeParam.add("EDU_SUP_SUSPEND_STATUS");
			typeParam.add("EQU_SUPPLIER_STATUS");
			json = ResultUtils.getLookUpValues( json , incomingParam, efferentParam, typeParam);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS   , "", 0, json).toString();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }


    /**
	 * @param params    {
	 *                  }
	 * @param pageIndex
	 * @param pageRows
	 * @return
	 * @description 查询供应商列表（带分页 字典）
	 */
	@RequestMapping(method = RequestMethod.POST, value = "findSupplierLov")
	public String findSupplierLov(@RequestParam(required = false) String params,
						  @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
						  @RequestParam(required = false, defaultValue = "10") Integer pageRows) {
		try {

			JSONObject jsonObject = new JSONObject();
			if (StringUtils.isNotBlank(params)) {
				jsonObject = JSON.parseObject(params);
			}
			Pagination<EquPosSupplierRecoverEntity_HI_RO> result = equPosSupplierRecoverServer.findSupplierLov(jsonObject, pageIndex, pageRows);
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
	 * @description 提交
	 */
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosRecoverSumbit")
	public String saveEquPosRecoverSumbit(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierRecoverEntity_HI renturnEntity = equPosSupplierRecoverServer.saveEquPosRecoverSumbit(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, renturnEntity).toString();
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
	@RequestMapping(method = RequestMethod.POST, value = "saveEquPosRecover")
	public String saveEquPosRecover(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			EquPosSupplierRecoverEntity_HI renturnEntity = equPosSupplierRecoverServer.saveEquPosRecover(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, renturnEntity).toString();
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
	 * @description 删除
	 */
	@RequestMapping(method = RequestMethod.POST, value = "deleteSupplierRecover")
	public String deleteSupplierRecover(@RequestParam(required = true) String params) {
		try {
			int userId = getSessionUserId();
			JSONObject jsonObject = JSON.parseObject(params);
			Integer listId = equPosSupplierRecoverServer.deleteSupplierRecover(jsonObject, userId);
			return SToolUtils.convertResultJSONObj("S", SUCCESS_MSG, 0, listId).toString();
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("E", e.getMessage(), 0, null).toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "服务异常," + e.getMessage(), 0, null).toString();
		}
	}

	/**
	 * 文件上传
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveEquFilesUpload", method = RequestMethod.POST)
	public String saveEquFilesUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		ServletContext servletContext = request.getSession().getServletContext();
		System.out.println(servletContext.toString());

		String functionId = "EQU_POS_SUPPLIER_RECOVER";//业务模块
		try {
			String fileAllName = request.getParameter("fileName");
			String[] fileNames = fileAllName.split("-");

			if(fileNames.length!=3){
				return SToolUtils.convertResultJSONObj("F","附件名称有误", 0, null).toJSONString();
			}
			String supplierName = fileNames[0];
			String typeName = fileNames[1];
			String fileName = fileNames[2];
			System.out.println(
					"-----------------------------"
			);
			System.out.println(
					fileName
			);
			System.out.println(
					"-----------------------------"
			);
			request.setCharacterEncoding(UTF8);
			List<ResultFileEntity> list = new ArrayList<>();
			//1.将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//request.getParameterMap().get("sideAgrtHId") 获取普通参数的另外的一种方式
			//2.获取业务主键id
			String bussnessId = multiRequest.getParameter("bussnessId");//业务id
			LOGGER.info("业务主键id: {}", bussnessId);
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				//3.这里的name为fileItem的alias属性值，相当于form表单中name
				String name = (String) iter.next();
				//4.根据name值拿取文件
				System.out.println(1);
				MultipartFile file1 = multiRequest.getFile(name);
				Assert.notNull(file1, "未选择上传内容");
				//String fileName = file1.getOriginalFilename(); //文件名 不用这种方式获取文件名,有乱码问题

				try (
						InputStream inputStream = file.getInputStream();
				) {
					//5.保存附件到文件系统和base_attachment中
                    Integer userId = getSessionUserId();
                    ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(bussnessId),userId);
					//获取供应商信息
					List<EquPosSupplierInfoEntity_HI> equPosSupplierInfoEntityHi = equPosSupplierInfoDAO_HI.findList("FROM EquPosSupplierInfoEntity_HI WHERE supplierName = '"+ supplierName+"'");

					if(equPosSupplierInfoEntityHi.size()==0){
						return SToolUtils.convertResultJSONObj("F","供应商有误", 0, null).toJSONString();
					}
					Map<String, Object> map = new HashMap<>();
					map.put("supplierId",equPosSupplierInfoEntityHi.get(0).getSupplierId());
					List<EquPosSuppInfoWithDeptEntity_HI> deptDAOList =
							equPosSuppInfoWithDeptDAO_HI.findList(" from  EquPosSuppInfoWithDeptEntity_HI where supplierId = :supplierId and deptCode = '0E' ", map);

					EquPosSupplierInfoEntity_HI supplierInfoEntityHi = equPosSupplierInfoEntityHi.get(0);
					EquPosSuppInfoWithDeptEntity_HI suppInfoWithDeptEntityHi = deptDAOList.get(0);
					//保存公司简介
					if("公司简介".equals(typeName)){
						suppInfoWithDeptEntityHi.setFileName(fileName);
						suppInfoWithDeptEntityHi.setFilePath(resultFileEntity.getFilePath());
						suppInfoWithDeptEntityHi.setOperatorUserId(1);
						equPosSuppInfoWithDeptDAO_HI.save(suppInfoWithDeptEntityHi);
					}
//					CSR报告
					else if("CSR报告".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
								"FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = 'CSR报告' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId",map);
						if (credentialAttachEntityHis.size()>0){
							credentialAttachEntityHi = credentialAttachEntityHis.get(0);
						}else{
                            credentialAttachEntityHi.setFileType("csrReport");
							credentialAttachEntityHi.setFixFlag("N");
                        }
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("CSR报告");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");

                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
//					生产许可证
					else if("生产许可证".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
								"FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = '生产许可证' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId",map);
						if (credentialAttachEntityHis.size()>0){
							credentialAttachEntityHi = credentialAttachEntityHis.get(0);
						}else{
                            credentialAttachEntityHi.setFileType("productionPermit");
							credentialAttachEntityHi.setFixFlag("N");
                        }
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("生产许可证");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");

                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
//					质量安全管理体系认证证明
					else if("质量安全管理体系认证证明".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
								"FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = '质量安全管理体系认证证明' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId",map);
						if (credentialAttachEntityHis.size()>0){
							credentialAttachEntityHi = credentialAttachEntityHis.get(0);
						}else{
                            credentialAttachEntityHi.setFileType("qualitySafety");
							credentialAttachEntityHi.setFixFlag("N");
                        }
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("质量安全管理体系认证证明");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");
                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
//					消防验收证明
					else if("消防验收证明".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
								"FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = '消防验收证明' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId",map);
						if (credentialAttachEntityHis.size()>0){
							credentialAttachEntityHi = credentialAttachEntityHis.get(0);
						}else{
                            credentialAttachEntityHi.setFileType("fireAcceptance");
							credentialAttachEntityHi.setFixFlag("N");
                        }
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("消防验收证明");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");
                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
//					建筑工程竣工验收报告
					else if("建筑工程竣工验收报告".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						List<EquPosZzscCredentialAttachEntity_HI> credentialAttachEntityHis = equPosZzscCredentialAttachDAO_HI.findList("" +
								"FROM EquPosZzscCredentialAttachEntity_HI where deptmentCode = '0E' AND attachmentName = '建筑工程竣工验收报告' AND fileType = 'FIXED' AND filePath IS NULL and supplierId = :supplierId",map);
						if (credentialAttachEntityHis.size()>0){
							credentialAttachEntityHi = credentialAttachEntityHis.get(0);
						}else{
                            credentialAttachEntityHi.setFileType("projectAcceptance");
							credentialAttachEntityHi.setFixFlag("N");
                        }
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("建筑工程竣工验收报告");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");
                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
//					其他生产资质信息
					else if("生产资质信息".equals(typeName)){
						EquPosZzscCredentialAttachEntity_HI credentialAttachEntityHi = new EquPosZzscCredentialAttachEntity_HI();
						credentialAttachEntityHi.setOperatorUserId(1);
						credentialAttachEntityHi.setAttachmentName("其他生产资质信息");
						credentialAttachEntityHi.setSupplierId(supplierInfoEntityHi.getSupplierId());
						credentialAttachEntityHi.setFileName(fileName);
						credentialAttachEntityHi.setFilePath(resultFileEntity.getFilePath());
						credentialAttachEntityHi.setDeptmentCode("0E");
						credentialAttachEntityHi.setIsProductFactory("Y");
						credentialAttachEntityHi.setFixFlag("N");
						credentialAttachEntityHi.setFileType("other");
                        credentialAttachEntityHi.setFileId(resultFileEntity.getFileId().intValue());
						equPosZzscCredentialAttachDAO_HI.save(credentialAttachEntityHi);
					}
					//营业执照复印件(附件)
					else if("营业执照复印件(附件)".equals(typeName)){
					    List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis =	equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ",map);
						if(supplierCredentialsEntityHis.size()>0){
							EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
							supplierCredentialsEntityHi.setLicenseFileName(fileName);
							supplierCredentialsEntityHi.setLicenseFilePath(resultFileEntity.getFilePath());
							supplierCredentialsEntityHi.setOperatorUserId(1);
							equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
						}
					}
					else if("银行开户许可证(附件)".equals(typeName)){
						List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis =	equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ",map);
						if(supplierCredentialsEntityHis.size()>0){
							EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
							supplierCredentialsEntityHi.setBankFileName(fileName);
							supplierCredentialsEntityHi.setBankFilePath(resultFileEntity.getFilePath());
							supplierCredentialsEntityHi.setOperatorUserId(1);
							equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
						}
					}
					else if("一般纳税人证明(附件)".equals(typeName)){
						List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis =	equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ",map);
						if(supplierCredentialsEntityHis.size()>0){
							EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
							supplierCredentialsEntityHi.setTaxpayerFileName(fileName);
							supplierCredentialsEntityHi.setTaxpayerFilePath(resultFileEntity.getFilePath());
							supplierCredentialsEntityHi.setOperatorUserId(1);
							equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
						}
					}
					else if("组织机构代码证(附件)".equals(typeName)){
						List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis =	equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ",map);
						if(supplierCredentialsEntityHis.size()>0){
							EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
							supplierCredentialsEntityHi.setTissueFileName(fileName);
							supplierCredentialsEntityHi.setTissueFilePath(resultFileEntity.getFilePath());
							supplierCredentialsEntityHi.setOperatorUserId(1);
							equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
						}
					}
					else if("税务登记证(附件)".equals(typeName)){
						List<EquPosSupplierCredentialsEntity_HI> supplierCredentialsEntityHis =	equPosSupplierCredentialsDAO_HI.findList("from EquPosSupplierCredentialsEntity_HI WHERE supplierId = :supplierId AND deptCode = '0E' ",map);
						if(supplierCredentialsEntityHis.size()>0){
							EquPosSupplierCredentialsEntity_HI supplierCredentialsEntityHi = supplierCredentialsEntityHis.get(0);
							supplierCredentialsEntityHi.setTaxFileName(fileName);
							supplierCredentialsEntityHi.setTaxFilePath(resultFileEntity.getFilePath());
							supplierCredentialsEntityHi.setOperatorUserId(1);
							equPosSupplierCredentialsDAO_HI.save(supplierCredentialsEntityHi);
						}
					}
					String filePath = resultFileEntity.getFilePath();
					resultFileEntity.setFilePath(filePath+"?attname="+fileName);
					list.add(resultFileEntity);
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage(), e1);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e1.getMessage(), 0, null).toJSONString();
		}
	}


	/**
	 * 文件上传
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveEquFileUpload", method = RequestMethod.POST)
	public String saveEquFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		ServletContext servletContext = request.getSession().getServletContext();
		System.out.println(servletContext.toString());

		String functionId = "EQU_POS_SUPPLIER_RECOVER";//业务模块
		try {
			request.setCharacterEncoding(UTF8);
			List<ResultFileEntity> list = new ArrayList<>();
			//1.将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//request.getParameterMap().get("sideAgrtHId") 获取普通参数的另外的一种方式
			//2.获取业务主键id
			String bussnessId = multiRequest.getParameter("bussnessId");//业务id
			LOGGER.info("业务主键id: {}", bussnessId);
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				//3.这里的name为fileItem的alias属性值，相当于form表单中name
				String name = (String) iter.next();
				//4.根据name值拿取文件
				MultipartFile file1 = multiRequest.getFile(name);
				Assert.notNull(file1, "未选择上传内容");
				//String fileName = file1.getOriginalFilename(); //文件名 不用这种方式获取文件名,有乱码问题
				String fileName = request.getParameter("fileName");
				String str2 = fileName.substring(fileName.lastIndexOf(".")+1);
				if (FILE_TYPE.indexOf(str2.toUpperCase())==-1){
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "无法上传此格式附件", 0, null).toJSONString();
				}
				if(!checkFileSize( file.getSize(),100,"M")){
					return SToolUtils.convertResultJSONObj(ERROR_STATUS, "附件大小不能超过100M!", 0, null).toJSONString();
				}
				try (
						InputStream inputStream = file.getInputStream()
				){
					//5.保存附件到文件系统和base_attachment中
                    Integer userId = getSessionUserId();
                    ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.valueOf(bussnessId),userId);
                    String filePath = resultFileEntity.getFilePath();
					fileName = fileName.replaceAll(",","%2C");
					resultFileEntity.setFilePath(filePath+"?attname="+fileName);
					list.add(resultFileEntity);
				}
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
		} catch (IOException e) {
			LOGGER.warn(e.getMessage());
			return SToolUtils.convertResultJSONObj("F", e.getMessage(), 0, null).toJSONString();
		} catch (Exception e1) {
			LOGGER.error(e1.getMessage(), e1);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, "服务异常 " + e1.getMessage(), 0, null).toJSONString();
		}
	}

	public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}


    /**
     * 供应商恢复回调接口
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "processCallback")
    public String processCallback(@RequestParam(required = false) String params) {
        try {
            System.out.println("回调开始了！！！！！！！！！！！！！！！！！！！！！！！");
            JSONObject paramsObject = parseObject(params);
            int userId = paramsObject.getIntValue("userId");
            EquPosSupplierRecoverEntity_HI entity = equPosSupplierRecoverServer.processCallback(paramsObject,userId);
            return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, SUCCESS_MSG, 1, entity).toString();
        }catch (Exception e){
            return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toString();
        }
    }

}
