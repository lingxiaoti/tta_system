package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.entities.TtaSupplierItemMidEntity_HI;
import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemRelSupplier;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/31 9:40
 * 回滚使用的参数实体类
 */
public class ParamCommonRollBackBean {
    /**
     * 主线程监控
     */
    protected CountDownLatch mainLatch;
    /**
     * 子线程监控
     */
    protected CountDownLatch threadLatch;
    /**
     * 是否回滚
     */
    protected RollBack rollBack;
    protected BlockingDeque<Boolean> resultList;

    protected Integer supplierItemId;
    protected String joinSupplierStr;
    protected String finalSplitString;
    protected List<String> supplierList;
    protected ITtaSupplierItemRelSupplier ttaSupplierItemRelSupplierServer;
}
