package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


public class UpdateSelectTotalOiSceneCallable extends ParamCommonBean implements Callable<String> {
    private String joinMidId;

    public UpdateSelectTotalOiSceneCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack, BlockingDeque<Boolean> resultList, String joinSupplierStr, String splitString, ITtaSupplierItemHeader ttaSupplierItemHeader, String joinMidId
            ,String startDate,String endDate, Integer oiType) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.joinSupplierStr = joinSupplierStr;
        this.finalSplitString = splitString;
        this.ttaSupplierItemHeader = ttaSupplierItemHeader;
        this.joinMidId = joinMidId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.oiType = oiType;
    }

    @Override
    public String call() throws Exception {
        return ttaSupplierItemHeader.updateSelectTotalOiSaleSceneSupplierInfo(mainLatch,threadLatch,rollBack,resultList,joinSupplierStr,finalSplitString,joinMidId,startDate,endDate,oiType);
    }
}
