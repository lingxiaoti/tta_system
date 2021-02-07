package com.sie.saaf.bpm.model.entities.readonly;

import java.util.List;

/**
 * @author laoqunzhao
 * @createTime 2018-06-01
 * @description 实现流程统计
 */
public class ActStatisticEntity_RO{

	private long amount;
	private long count;
	private List<?> statistics;
	
	
	public long getAmount() {
		return amount;
	}
	
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<?> getStatistics() {
		return statistics;
	}
	
	public void setStatistics(List<?> statistics) {
		this.statistics = statistics;
	}
	

}
