package com.sie.saaf.business.model.inter;

import com.sie.saaf.business.model.entities.TtaOrgInEntity_HI;
import com.sie.saaf.business.model.entities.readonly.TtaDeptInEntity_HI_RO;
import com.sie.saaf.business.model.entities.readonly.TtaOiSalesSceneEntity_HI_RO;
import com.sie.saaf.common.model.inter.IBaseCommon;

import java.util.HashMap;

public interface ITtaOiSplite extends IBaseCommon<HashMap>{

    /**
     * 功能描述： OI分摊 场景一：SALES占比非purchase_type计算
     */
    public void saveOiSaleScene(String yearMonth);

    /**
     * 功能描述： 场景二：PO（不含退货）占比计算
     */
    public void saveOiPoScene(String yearMonth);

    /**
     * 功能描述：场景三：PO（退货RV）占比计算
     */
    public void saveOiPoRvScene(String yearMonth);


    /**
     * 功能描述： 场景五：ABOI(试用装)计算,不需要计算比值
     */
    public void saveOiAboiSuitScene(String yearMonth);


    /**
     * 功能描述：场景六：OTHER OI(L&N)占比计算
     */
    public void saveTtaOiLnScene(String yearMonth);

    /**
     * 功能描述：场景四
     */
    public void saveFourABOI(String yearMonth);

    public void findTest();
}
