package com.sie.watsons.base.questionnaire.model.inter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionChoiceLineEntity_HI;
import com.sie.watsons.base.questionnaire.model.entities.readonly.TtaQuestionNewMapDetailEntity_HI_RO;
import com.yhg.hibernate.core.paging.Pagination;

import java.util.List;

import com.sie.watsons.base.questionnaire.model.entities.TtaQuestionNewMapDetailEntity_HI;
import com.sie.saaf.common.model.inter.IBaseCommon;
import org.springframework.web.multipart.MultipartFile;

public interface ITtaQuestionNewMapDetail extends IBaseCommon<TtaQuestionNewMapDetailEntity_HI> {

    public void saveOrUpadateBatchDetail(JSONObject jsonParams);

    public Pagination<TtaQuestionNewMapDetailEntity_HI_RO> queryQuestionNewMapDetailList(JSONObject queryParamJSON, Integer pageIndex, Integer pageRows);

    public void  saveImportNewProductList(JSONObject jsonObject, MultipartFile file) throws Exception;

}
