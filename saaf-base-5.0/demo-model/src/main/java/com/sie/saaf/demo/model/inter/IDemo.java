package com.sie.saaf.demo.model.inter;

import com.sie.saaf.common.model.inter.IBaseCommon;
import com.sie.saaf.demo.model.entities.DemoEntity_HI;
import com.sie.saaf.transaction.annotation.TransMessageConsumer;
import com.sie.saaf.transaction.annotation.TransMessageProvider;
import com.sie.saaf.transaction.annotation.TransMsgParam;

/**
 * @author ZhangJun
 * @createTime 2018-10-10 8:21 PM
 * @description
 */
public interface IDemo extends IBaseCommon<DemoEntity_HI> {


	void saveTransactionProduct();

	void saveTransMessageConsumer(Long msgId, DemoEntity_HI instance);
}
