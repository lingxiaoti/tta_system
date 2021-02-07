package com.sie.watsons.base.pon.project.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.yhg.hibernate.core.paging.Pagination;
import java.util.List;
import com.sie.watsons.base.pon.project.model.entities.EquPonProjectAttachmentEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;

public interface IEquPonProjectAttachment extends IBaseCommon<EquPonProjectAttachmentEntity_HI>{
    //报价管理-立项单据附件查询，分页查询
    JSONObject findProjectAttachment(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    //报价管理-立项单据附件保存
    EquPonProjectAttachmentEntity_HI saveProjectAttachment(JSONObject params) throws Exception;

    //报价管理-立项单据附件删除
    void deleteProjectAttachment(JSONObject params) throws Exception;
}
