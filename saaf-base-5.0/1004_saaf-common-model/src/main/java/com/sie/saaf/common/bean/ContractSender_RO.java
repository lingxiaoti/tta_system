package com.sie.saaf.common.bean;

/**
 * TtaAttrCheckItemEntity_HI_RO Entity Object
 * Thu Jun 18 18:45:57 CST 2020  Auto Generate
 */

public class ContractSender_RO {
	/**
	 * 1.描述： 发件人所属企业需为集
	 * 团的子公司。 不填则为
	 * 开发者账号所在的企
	 * 业。
	 *
	 * 2.是否必填： 否
	 * 3.名称：发件人所属企业
	 */
	private String enterpriseName;

	/**
	 * 1.描述：发件人账号必须是企业
	 * 成员账号， 不填则为开
	 * 发者账号所在企业的主
	 * 管理员账号。
	 *
	 * 2.是否必填： 否
	 * 3.名称：发件人账号
	 */
	private String account;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
