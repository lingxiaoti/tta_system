package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/11 22:21
 */
public class RollbackUpdateSupplierCallable extends ParamCommonRollBackBean implements Callable<String> {
    private String monthDay;

    public RollbackUpdateSupplierCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList,
                                          Integer supplierItemId, String joinSupplierStr, String monthDay, String finalSplitString,List<String> supplierList,
                                          ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.monthDay = monthDay;
        this.finalSplitString = finalSplitString;
        this.supplierList = supplierList;
        this.ttaSupplierItemRelSupplierServer = ttaSupplierItemRelSupplierServer;
    }

    @Override
    public String call() throws Exception {
        return ttaSupplierItemRelSupplierServer.updateRollbackSupplierInfo(mainLatch, threadLatch, rollBack, resultList,supplierItemId,joinSupplierStr,monthDay,
                finalSplitString,supplierList);
    }
}
