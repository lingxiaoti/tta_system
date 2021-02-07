package com.sie.watsons.base.supplement.model.inter;

import com.alibaba.fastjson.JSONObject;
import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.product.model.entities.PlmProductFileEntity_HI;
import com.sie.watsons.base.supplement.model.entities.PlmSupplementLineFileEntity_HI;

import java.util.List;

public interface IPlmSupplementLineFile extends IBaseCommon<PlmProductFileEntity_HI>{

    public List<PlmSupplementLineFileEntity_HI> getFiles(JSONObject o);

    void saveFile(JSONObject queryParamJSON);

    void deleteFile(JSONObject param);
}
