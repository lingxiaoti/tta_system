package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/10 12:06
 */
public class UpdatePurchaseSupplierCall implements Callable<String> {

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

    private Integer supplierItemId;//中间表id
    private String joinSupplierStr;//拼接供应商
    private Integer yearItem;//年份
    private String finalSplitString;//拆分条件
    private String splitSupplierCode;//拆分供应商编号
    private String splitSupplierName;//拆分供应商名称
    private ITtaSupplierItemHeader ttaSupplierItemHeader;

    public UpdatePurchaseSupplierCall(CountDownLatch mainLatch, CountDownLatch threadLatch, TtaSupplierItemMidServer.RollBack rollBack,
                                      BlockingDeque<Boolean> resultList, Integer supplierItemId,
                                      String joinSupplierStr, Integer yearItem, String finalSplitString,
                                      String splitSupplierCode, String splitSupplierName,
                                      ITtaSupplierItemHeader ttaSupplierItemHeader) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.yearItem = yearItem;
        this.finalSplitString = finalSplitString;
        this.splitSupplierCode = splitSupplierCode;
        this.splitSupplierName = splitSupplierName;
        this.ttaSupplierItemHeader = ttaSupplierItemHeader;
    }

    @Override
    public String call() throws Exception {
        //为了保证调用的方法有事务,所以创建的方法放在service层
        return ttaSupplierItemHeader.updatePurchaseSupplierInfo(mainLatch,threadLatch,rollBack,resultList,supplierItemId,joinSupplierStr,
                yearItem,finalSplitString,splitSupplierCode,splitSupplierName);
    }

}
