package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/11 22:21
 */
public class RollbackUpdatePurchaseSupplierCallable extends ParamCommonRollBackBean implements Callable<String> {
    private Integer yearItem;
    public RollbackUpdatePurchaseSupplierCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList,
                                                  Integer supplierItemId, String joinSupplierStr, Integer yearItem, String finalSplitString,List<String> supplierList,
                                                  ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.yearItem = yearItem;
        this.finalSplitString = finalSplitString;
        this.supplierList = supplierList;
        this.ttaSupplierItemRelSupplierServer = ttaSupplierItemRelSupplierServer;
    }

    @Override
    public String call() throws Exception {
        return ttaSupplierItemRelSupplierServer.updateRollbackPurchaseSupplierInfo(mainLatch, threadLatch, rollBack, resultList,supplierItemId,joinSupplierStr,yearItem,
                finalSplitString,supplierList);
    }
}
