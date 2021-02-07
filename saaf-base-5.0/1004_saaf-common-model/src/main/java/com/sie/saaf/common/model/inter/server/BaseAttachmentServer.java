package com.sie.saaf.common.model.inter.server;

import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseAttachment;
import com.yhg.hibernate.core.dao.BaseViewObject;
import com.yhg.hibernate.core.dao.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

@Component("baseAttachmentServer")
public class BaseAttachmentServer extends BaseCommonServer<BaseAttachmentEntity_HI> implements IBaseAttachment {
//	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAttachmentServer.class);
	@Autowired
	private ViewObject<BaseAttachmentEntity_HI> baseAttachmentDAO_HI;
	@Autowired
	private BaseViewObject<BaseAttachmentEntity_HI_RO> baseAttachmentDAO_HI_RO;
	public BaseAttachmentServer() {
		super();
	}


	/**
	 *	根据业务id、文件来源查询附件信息
	 * @param businessId	业务id
	 * @param functionId	文件来源(文档、会议、申请单据等功能ID)
	 * @return
	 */
	@Override
	public List<BaseAttachmentEntity_HI_RO> findBaseAttachmentInfos(Long businessId, String functionId) {
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		return baseAttachmentDAO_HI_RO.findList(BaseAttachmentEntity_HI_RO.QUERY_SELECT_SQL, businessId, functionId);
	}

	/**
	 *	根据业务id、文件来源查询附件信息
	 * @param businessId	业务id
	 * @param functionId	文件来源(文档、会议、申请单据等功能ID)
	 * @return
	 */
	@Override
	public BaseAttachmentEntity_HI findBaseAttachmentInfoByFun(Long businessId, String functionId) {
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		HashMap<String, Object> stringObjectHashMap = new HashMap<>();
		stringObjectHashMap.put("businessId",businessId);
		stringObjectHashMap.put("functionId",functionId);
		List<BaseAttachmentEntity_HI> byProperty = baseAttachmentDAO_HI.findByProperty(stringObjectHashMap);

		if (byProperty.size()==0)
			return null;
		return byProperty.get(0);
	}

	/**
	 *	根据业务id、文件来源查询附件信息
	 * @param businessId	业务id
	 * @param functionId	文件来源(文档、会议、申请单据等功能ID)
	 * @return
	 */
	@Override
	public BaseAttachmentEntity_HI_RO findBaseAttachmentInfo(Long businessId, String functionId) {
		List<BaseAttachmentEntity_HI_RO> list = findBaseAttachmentInfos(businessId,functionId);
		if (list.size()==0)
			return null;
		return list.get(0);
	}

	public BaseAttachmentEntity_HI_RO findMaxBaseAttachmentInfo(Long businessId, String functionId) {
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		List<BaseAttachmentEntity_HI_RO> list = baseAttachmentDAO_HI_RO.findList(BaseAttachmentEntity_HI_RO.QUERY_SELECT_SQL_MAX, functionId,businessId);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	};

	@Override
	public String findBaseAttachmentFileIds(Long businessId, String functionId) {
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		List<BaseAttachmentEntity_HI_RO> list = baseAttachmentDAO_HI_RO.findList(BaseAttachmentEntity_HI_RO.QUERY_SELECT_FILE_IDS_SQL, functionId,businessId);
		if (list.size() != 0) {
			return list.get(0).getFileIds();
		}else {
			return "";
		}

	};

	@Override
	public List<BaseAttachmentEntity_HI_RO> findBaseAttachmentAllFileId(Long businessId, String functionId) {
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		List<BaseAttachmentEntity_HI_RO> list = baseAttachmentDAO_HI_RO.findList(BaseAttachmentEntity_HI_RO.QUERY_SELECT_FILE_IDS_ALL_SQL, functionId,businessId);
		return list;

	};

	@Override
	public BaseAttachmentEntity_HI findBaseAttachmentInfo(Long fileId) {
		Assert.notNull(fileId,"fileId is required");
		BaseAttachmentEntity_HI instance= baseAttachmentDAO_HI.getById(fileId);
		Assert.notNull(instance,"data not exist with fileId:"+fileId);
		return  instance;
	}

	/**
	 * 新增
	 * @param instance
	 * @return
	 */
	@Override
	public BaseAttachmentEntity_HI saveBaseAttachmentInfo(BaseAttachmentEntity_HI instance) {
		baseAttachmentDAO_HI.save(instance);
		return instance;
	}

	/**
	 * 删除附件
	 * @param fileId
	 */
	@Override
	public void deleteBaseAttachment(Long fileId) {
		BaseAttachmentEntity_HI instance= baseAttachmentDAO_HI.getById(fileId);
		Assert.notNull(instance,"data not exist with fileId:"+fileId);
		instance.setDeleteFlag(1);
		baseAttachmentDAO_HI.update(instance);
	}

	/**
	 *
	 * 更新附件表业务主键
	 * @param fileId		文件id
	 * @param businessId	业务id
	 * @param functionId	文件来源(文档、会议、申请单据等功能ID)
	 * @return
	 */
	@Override
	public  BaseAttachmentEntity_HI  updateBusinessId(Long fileId, Long businessId, String functionId){
		Assert.notNull(fileId,"fileId is required");
		Assert.notNull(businessId,"businessId is required");
		Assert.notNull(functionId,"functionId is required");
		BaseAttachmentEntity_HI instance= baseAttachmentDAO_HI.getById(fileId);
		Assert.notNull(instance,"data not exist with fileId:"+fileId);
		instance.setBusinessId(businessId);
		instance.setFunctionId(functionId);
		return instance;
	}





}
