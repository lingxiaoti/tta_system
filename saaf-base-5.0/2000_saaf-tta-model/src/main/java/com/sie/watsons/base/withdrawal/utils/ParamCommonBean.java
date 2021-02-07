package com.sie.watsons.base.withdrawal.utils;

import com.sie.watsons.base.withdrawal.model.inter.ITtaSupplierItemHeader;
import com.sie.watsons.base.withdrawal.model.inter.server.TtaSupplierItemMidServer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;

/**
 * @author hmb
 * @date 2019/10/29 10:52
 */
public class ParamCommonBean {
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
    protected TtaSupplierItemMidServer.RollBack rollBack;
    protected BlockingDeque<Boolean> resultList;

    protected String startDate;//开始月份
    protected String endDate;//结束月份
    protected Integer supplierItemId;//中间表id
    protected String joinSupplierStr;//拼接供应商
    protected String finalSplitString;//拆分条件
    protected String splitSupplierCode;//拆分供应商编号
    protected Integer oiType;//oi拆分场景
    protected ITtaSupplierItemHeader ttaSupplierItemHeader;
}
