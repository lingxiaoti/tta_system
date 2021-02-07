package com.sie.watsons.base.supplier.services;

import com.alibaba.fastjson.JSON;
import com.sie.saaf.common.bean.ResultFileEntity;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IFastdfs;
import com.sie.saaf.common.util.SaafToolUtils;
import com.sie.watsons.base.elecsign.model.entities.TtaConAttrLineEntity_HI;
import com.sie.watsons.base.supplement.model.inter.ITtaSideAgrtHeader;
import com.sie.watsons.base.supplier.model.entities.TtaSupplierAttrEntity_HI;
import com.sie.watsons.base.supplier.model.inter.ITtaSupplierAttr;

import com.alibaba.fastjson.JSONObject;
import com.yhg.base.utils.SToolUtils;
import com.yhg.hibernate.core.paging.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sie.saaf.common.services.CommonAbstractService;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ttaSupplierAttrService")
public class TtaSupplierAttrService extends CommonAbstractService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TtaSupplierAttrService.class);

	@Autowired
	private ITtaSideAgrtHeader ttaSideAgrtHeaderServer;

	@Autowired
	private ITtaSupplierAttr ttaSupplierAttrServer;


	@Autowired
	private IFastdfs fastdfsServer;

	@Override
	public IBaseCommon getBaseCommonServer(){
		return this.ttaSupplierAttrServer;
	}

	/**
	 * 文件上传
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "ttaSupplierAttrUploadFile", method = RequestMethod.POST)
	public String ttaSupplierAttrUploadFile(@RequestParam("file") MultipartFile file) {
		try {
			LOGGER.info("#############################文件上传开始 userId" + this.getSessionUserId() + "#########################################");
			request.setCharacterEncoding("utf-8");
			String functionId = request.getParameter("functionId");
			Assert.notNull(functionId, "functionId 不能为空!");
			String fileName = request.getParameter("fileName");
			Assert.notNull(file, "上传内容为空");
			String businessId = request.getParameter("businessId");
			Assert.notNull(businessId, "businessId 不能为空!");

			//1.获取业务主键id
			String fileInfo = request.getParameter("fileInfo");
			JSONObject fileInfoJson = parseObject(fileInfo);

			List<ResultFileEntity> list = new ArrayList<>();
			try (
					InputStream inputStream = file.getInputStream();
			) {
				//5.保存附件到文件系统和base_attachment中
				ResultFileEntity resultFileEntity = fastdfsServer.fileUpload(inputStream, fileName, functionId, Long.parseLong(businessId), this.getSessionUserId());
				Assert.notNull(resultFileEntity.getFileId(), "上传文件失败！");
				TtaSupplierAttrEntity_HI ttaSupplierAttrEntity_HI = JSON.parseObject(fileInfoJson.toJSONString(), TtaSupplierAttrEntity_HI.class);
				ttaSupplierAttrEntity_HI.setStatus("Y");
				ttaSupplierAttrEntity_HI.setOperatorUserId(this.getSessionUserId());
				ttaSupplierAttrEntity_HI.setCreationDate(new Date());
				ttaSupplierAttrEntity_HI.setFileId(resultFileEntity.getFileId().intValue());
				ttaSupplierAttrServer.save(ttaSupplierAttrEntity_HI);
				list.add(resultFileEntity);
			}
			return SToolUtils.convertResultJSONObj(SUCCESS_STATUS, "上传成功", list.size(), list).toJSONString();
		} catch (Exception e) {
			LOGGER.error("文件上传失败:{}", e);
			return SToolUtils.convertResultJSONObj(ERROR_STATUS, e.getMessage(), 0, null).toJSONString();
		}
	}


	@PostMapping("findSupplierAttachmentList")
	public String findBaseAttachmentEntity(@RequestParam(required = false) String params,
										   @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
										   @RequestParam(required = false, defaultValue = "5") Integer pageRows) throws Exception {
		try {
			JSONObject parameters = this.parseObject(params);
			String functionId = parameters.getString("functionId");
			Integer businessId = parameters.getInteger("businessId");
			Pagination<BaseAttachmentEntity_HI_RO> baseAttachmentEntity = new Pagination<BaseAttachmentEntity_HI_RO>();
			if (StringUtils.isNotBlank(functionId) && businessId != null) {
				baseAttachmentEntity = ttaSupplierAttrServer.findBaseAttachmentEntity(parameters, pageIndex, pageRows);
			}
			JSONObject results = JSONObject.parseObject(JSON.toJSONString(baseAttachmentEntity));
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败：" + e.getMessage(), 0, e)
					.toJSONString();
		}
	}

	@PostMapping("deletAttr")
	public String deletAttr(@RequestParam(required = false) String params) throws Exception {
		try {
			JSONObject results = new JSONObject();
			JSONObject parameters = this.parseObject(params);
			Integer id = parameters.getInteger("fileId");
			TtaSupplierAttrEntity_HI entityHi = ttaSupplierAttrServer.getById(id);
			entityHi.setStatus("N");
			ttaSupplierAttrServer.saveOrUpdate(entityHi);
			results.put(SToolUtils.STATUS, SUCCESS_STATUS);
			results.put(SToolUtils.MSG, "成功");
			return results.toString();
		} catch (Exception e) {
			LOGGER.error("params_" + params + "_e_" + e.getMessage(), e);
			return SToolUtils.convertResultJSONObj("E", "查询失败：" + e.getMessage(), 0, e)
					.toJSONString();
		}
	}


}