package com.sie.saaf.common.bean;

/**
 * 单品码追溯记录
 * @author Administrator
 *
 */
public class ItemTraceaListEntity {

	private String occurDate;
	private String unitCode;
	private String fromSubInv;
	private String fromSubName;
	private String trxDesc;
	private String toSubInv;
	private String toSubName;
	private String memo;
	private String memNum;
	private String memberName;
	private String jfImei;
	private String orderNum;
	private String trxNo;

	public String getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getFromSubInv() {
		return fromSubInv;
	}

	public void setFromSubInv(String fromSubInv) {
		this.fromSubInv = fromSubInv;
	}

	public String getFromSubName() {
		return fromSubName;
	}

	public void setFromSubName(String fromSubName) {
		this.fromSubName = fromSubName;
	}

	public String getTrxDesc() {
		return trxDesc;
	}

	public void setTrxDesc(String trxDesc) {
		this.trxDesc = trxDesc;
	}

	public String getToSubInv() {
		return toSubInv;
	}

	public void setToSubInv(String toSubInv) {
		this.toSubInv = toSubInv;
	}

	public String getToSubName() {
		return toSubName;
	}

	public void setToSubName(String toSubName) {
		this.toSubName = toSubName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemNum() {
		return memNum;
	}

	public void setMemNum(String memNum) {
		this.memNum = memNum;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getJfImei() {
		return jfImei;
	}

	public void setJfImei(String jfImei) {
		this.jfImei = jfImei;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getTrxNo() {
		return trxNo;
	}

	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}
}
