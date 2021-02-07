package com.sie.saaf.common.model.inter;

import com.sie.saaf.common.model.entities.BaseAttachmentEntity_HI;
import com.sie.saaf.common.model.entities.readonly.BaseAttachmentEntity_HI_RO;

import java.util.List;

public interface IBaseAttachment extends IBaseCommon<BaseAttachmentEntity_HI>{


    List<BaseAttachmentEntity_HI_RO> findBaseAttachmentInfos(Long businessId, String functionId);

    BaseAttachmentEntity_HI_RO findBaseAttachmentInfo(Long businessId, String functionId);

    BaseAttachmentEntity_HI findBaseAttachmentInfoByFun(Long businessId, String functionId);

    BaseAttachmentEntity_HI findBaseAttachmentInfo(Long fileId);

    BaseAttachmentEntity_HI saveBaseAttachmentInfo(BaseAttachmentEntity_HI instance);

    void deleteBaseAttachment(Long fileId);

    BaseAttachmentEntity_HI  updateBusinessId(Long fileId, Long businessId, String functionId);

    BaseAttachmentEntity_HI_RO findMaxBaseAttachmentInfo(Long businessId, String functionId);

    String findBaseAttachmentFileIds(Long businessId, String functionId);

    List<BaseAttachmentEntity_HI_RO> findBaseAttachmentAllFileId(Long businessId, String functionId);
}
