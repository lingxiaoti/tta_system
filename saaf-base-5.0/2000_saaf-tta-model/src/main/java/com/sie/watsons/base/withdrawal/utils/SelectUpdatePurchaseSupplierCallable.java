package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/10 15:18
 */
public class SelectUpdatePurchaseSupplierCallable implements Callable<String> {
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

    private ITtaSupplierItemHeader ttaSupplierItemHeader;


    private Integer supplierItemId;//头id
    private String joinSupplierStr;//拼接多个供应商
    private Integer yearItem;//年份
    private String splitCondition;//拆分条件

    private List<String> itemList;//item集合
    private String finalSplitString;
    private String joinMidId;

    public SelectUpdatePurchaseSupplierCallable(CountDownLatch mainLatch, CountDownLatch threadLatch,
                                                TtaSupplierItemMidServer.RollBack rollBack,
                                                BlockingDeque<Boolean> resultList, ITtaSupplierItemHeader ttaSupplierItemHeader,
                                                Integer supplierItemId, String joinSupplierStr, Integer yearItem,
                                                String splitCondition,List<String> itemList,String finalSplitString,String joinMidId) {
        this.mainLatch = mainLatch;
        this.threadLatch = threadLatch;
        this.rollBack = rollBack;
        this.resultList = resultList;
        this.ttaSupplierItemHeader = ttaSupplierItemHeader;
        this.supplierItemId = supplierItemId;
        this.joinSupplierStr = joinSupplierStr;
        this.yearItem = yearItem;
        this.splitCondition = splitCondition;
        this.itemList = itemList;
        this.finalSplitString = finalSplitString;
        this.joinMidId = joinMidId;
    }

    @Override
    public String call() throws Exception {
        return ttaSupplierItemHeader.updateSelectPurchaseSupplierInfo(mainLatch, threadLatch, rollBack, resultList,supplierItemId, joinSupplierStr, yearItem,splitCondition,itemList,finalSplitString,joinMidId);
    }
}
