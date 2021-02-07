package com.sie.wastons.ttadata.model.inter;

import java.math.BigDecimal;
import java.util.List;
import com.sie.wastons.ttadata.model.entities.VmiSalesInSumEntity_HI;

public interface IVmiSalesInSum{
	public List<VmiSalesInSumEntity_HI> findSalesInSum(String store, String itemCode)throws Exception;

	//获取上级邮箱地址
	public String[] findMgrEmail(BigDecimal userId,BigDecimal createdBy)throws Exception;

	//获取汇报线邮箱地址
	public String[] findMgrLineEmail(BigDecimal userId)throws Exception;

}
