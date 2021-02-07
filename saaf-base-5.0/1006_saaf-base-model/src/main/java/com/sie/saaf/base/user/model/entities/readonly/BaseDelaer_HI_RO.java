package com.sie.saaf.base.user.model.entities.readonly;

/**
 * @author ZhangJun
 * @createTime 2018-03-06 16:10
 * @description
 */
public class BaseDelaer_HI_RO {
	public static final String QUERY_1 = "select distinct account_id as accountId,account_name as accountName,account_code as accountCode from base_warehouse_mapping where 1=1 and account_id is not null ";

	public static final String QUERY_DELARE="SELECT \n" +
			"\tcustomer_id AS accountId,\n" +
			"\tcustomer_name AS accountName,\n" +
			"\tcustomer_number AS accountCode \n" +
			"FROM base_customer WHERE 1=1 AND delete_flag='0'";


	private Integer accountId;
	private String accountCode;
	private String accountName;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
