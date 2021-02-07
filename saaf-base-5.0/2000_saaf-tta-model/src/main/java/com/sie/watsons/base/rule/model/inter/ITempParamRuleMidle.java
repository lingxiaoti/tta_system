package com.sie.watsons.base.rule.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.watsons.base.rule.model.entities.TempParamRuleMidleEntity_HI;

import java.util.List;

public interface ITempParamRuleMidle extends IBaseCommon<TempParamRuleMidleEntity_HI>{

    /**
     * 功能描述：批量保存参数信息
     * @author xiaoga
     * @date 2019/5/31
     * @param
     * @return
     */
    public void saveParams(List<TempParamRuleMidleEntity_HI> entityHiList);

    /**
     * 功能描述： 删除规则参数
     * @author xiaoga
     * @date 2019/5/31
     * @param
     * @return
     */
    public void deleteParamById(List<TempParamRuleMidleEntity_HI> entityList);
}
