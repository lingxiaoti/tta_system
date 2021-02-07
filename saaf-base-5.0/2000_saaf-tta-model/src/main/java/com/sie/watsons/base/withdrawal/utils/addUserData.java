package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class addUserData implements Callable<List<Object>> {

    /**
     * 主线程监控
     */
    private CountDownLatch mainLatch;
    /**
     * 子线程监控
     */
    private CountDownLatch threadLatch;
    /**
     * 是否回滚
     */
    private TtaSupplierItemMidServer.RollBack rollBack;
    private BlockingDeque<Boolean> resultList;
    private List<Object> taskList;
    private ITtaSupplierItemHeader ttaSupplierItemHeaderServer;

    public addUserData(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack,
                       BlockingDeque<Boolean> resultList, List<Object> taskList,ITtaSupplierItemHeader ttaSupplierItemHeaderServer) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.taskList = taskList;
        this.ttaSupplierItemHeaderServer = ttaSupplierItemHeaderServer;
    }

    @Override
    public List<Object> call() throws Exception {
        return ttaSupplierItemHeaderServer.insertNewCompanyUsers( mainLatch,  threadLatch,
                rollBack, resultList,
                taskList);
    }
}
