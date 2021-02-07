package com.sie.watsons.base.pon.itproject.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.itproject.model.entities.EquPonItProjectAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonItProjectAttachment extends IBaseCommon<EquPonItProjectAttachmentEntity_HI>{
    //IT报价管理-立项单据附件查询，分页查询
    JSONObject findItProjectAttachment(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //IT报价管理-立项单据附件删除
    void deleteItProjectAttachment(JSONObject params) throws Exception;
}
