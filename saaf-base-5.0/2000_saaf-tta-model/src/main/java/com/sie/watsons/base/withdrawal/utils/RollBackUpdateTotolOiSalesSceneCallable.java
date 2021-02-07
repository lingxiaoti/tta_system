package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/31 9:39
 * OI账单场景一
 */
public class RollBackUpdateTotolOiSalesSceneCallable extends ParamCommonRollBackBean implements Callable<String> {

    private String startDate;
    private String endDate;
    private Integer oiType;//oi拆分场景
    public RollBackUpdateTotolOiSalesSceneCallable(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList,
                                                   Integer supplierItemId, String joinSupplierStr, String finalSplitString, List<String> supplierList, ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer,
                                                   String startDate, String endDate,Integer oiType) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.finalSplitString = finalSplitString;
        this.supplierList = supplierList;
        this.ttaSupplierItemRelSupplierServer = ttaSupplierItemRelSupplierServer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.oiType = oiType;
    }

    @Override
    public String call() throws Exception {
        return ttaSupplierItemRelSupplierServer.updateRollbackUpdateTotalOiSceneSupplierInfo(mainLatch, threadLatch, rollBack, resultList,supplierItemId,joinSupplierStr,startDate,endDate,
                finalSplitString,supplierList,oiType);
    }
}
