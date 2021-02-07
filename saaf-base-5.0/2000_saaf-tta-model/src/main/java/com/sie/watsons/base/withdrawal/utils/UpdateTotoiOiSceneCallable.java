package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 所有oi拆分场景Callable类
 */
public class UpdateTotoiOiSceneCallable extends ParamCommonBean implements Callable<String> {

    public UpdateTotoiOiSceneCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, Integer supplierItemId, String joinSupplierStr, String finalSplitString, String splitSupplierCode, ITtaSupplierItemHeader ttaSupplierItemHeader,
                                      String startDate, String endDate,Integer oiType) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.finalSplitString = finalSplitString;
        this.splitSupplierCode = splitSupplierCode;
        this.ttaSupplierItemHeader = ttaSupplierItemHeader;
        this.startDate = startDate;
        this.endDate = endDate;
        this.oiType = oiType;
    }


    @Override
    public String call() throws Exception {
        //拆分所有场景的供应商数据
        return ttaSupplierItemHeader.updateOiSceneSupplierInfo(mainLatch,threadLatch,rollBack,resultList,supplierItemId,joinSupplierStr,finalSplitString
                ,splitSupplierCode,startDate,endDate,oiType);
    }
}
