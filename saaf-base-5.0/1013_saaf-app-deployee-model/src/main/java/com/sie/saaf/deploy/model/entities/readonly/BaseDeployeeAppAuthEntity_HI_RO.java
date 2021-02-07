package com.sie.saaf.deploy.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class BaseDeployeeAppAuthEntity_HI_RO {

	public static final String QUERY_PERSON_SQL = "select \n" +
			"\t person.person_id as id,\n" +
			"\t users.user_name as code,\n" +
			"\t person.person_name as name,\n" +
			"\t department.department_id as departmentId,\n" +
			"\t department.department_code as departmentCode,\n" +
			"\t department.department_name as departmentName\n";

	public static final String QUERY_CUSTOMER_SQL = "select \n" +
			"\t customer.customer_id as id,\n" +
			"\t customer.customer_number as code,\n" +
			"\t customer.customer_name as name,\n" +
			"\t department.department_id as departmentId,\n" +
			"\t department.department_code as departmentCode,\n" +
			"\t department.department_name as departmentName\n";

	public static final String QUERY_STORE_SQL = "select \n" +
			"\t invStoreT.store_code as id,\n" +
			"\t invStoreT.store_code as code,\n" +
			"\t invStoreT.store_name as name,\n" +
			"\t department.department_id as departmentId,\n" +
			"\t department.department_code as departmentCode,\n" +
			"\t department.department_name as departmentName,\n" +
			"\t customer.customer_id as customerId,\n" +
			"\t customer.customer_number as customerCode,\n" +
			"\t customer.customer_name as customerName";

	public static final String QUERY_DEPT_SQL = "select \n" +
			"\t department.department_id as departmentId,\n" +
			"\t department.department_code as departmentCode,\n" +
			"\t department.department_name as departmentName\n" +
			"\t from base_department department\n" +
			"\t where 1=1\n" +
			"\t and department.date_from <= SYSDATE\n" +
			"\t and department.date_to >= SYSDATE\n" +
			"\t and department.enable_flag = 'Y'\n" +
			"\t and department.delete_flag = 0\n";


	public static final String QUERY_PERSON_IN_DEPT_SQL =
			"\t from base_department department__\n" +
			"\t ,base_position position__\n" +
			"\t ,base_person_assign personAssign__\n" +
			"\t ,base_person person__\n" +
			"\t ,base_users users__\n" +
			"\t where department__.date_from <=SYSDATE\n" +
			"\t and department__.date_to >= SYSDATE\n" +
			"\t and department__.enable_flag = 'Y'\n" +
			"\t and department__.delete_flag = 0\n" +
			"\t and department__.department_id = position__.department_id\n" +
			"\t and position__.enable_flag = 'Y'\n" +
			"\t and position__.position_id = personAssign__.position_id\n" +
			"\t and personAssign__.enable_flag = 'Y'\n" +
			"\t and personAssign__.person_id = person__.person_id\n" +
			"\t and person__.person_id = users__.person_id" +
			"\t and users__.user_type = '20'";

	public static final String QUERY_CUSTOMER_IN_DEPT_SQL =
			"\t from base_department department__\n" +
			"\t ,base_department_cust departmentCust__\n" +
			"\t ,base_customer customer__\n" +
			"\t ,base_users users__\n" +
			"\t where department__.date_from <= SYSDATE\n" +
			"\t and department__.date_to >= SYSDATE\n" +
			"\t and department__.enable_flag = 'Y'\n" +
			"\t and department__.delete_flag = 0\n" +
			"\t and department__.department_id = departmentCust__.department_id\n" +
			"\t and departmentCust__.enable_flag = 'Y'\n" +
			"\t and departmentCust__.delete_flag = 0\n" +
			"\t and departmentCust__.primary_flag = 'Y'\n" +
			"\t and departmentCust__.start_date <= SYSDATE\n" +
			"\t and departmentCust__.end_date >= SYSDATE\n" +
			"\t and departmentCust__.cust_account_id = customer__.customer_id\n" +
			"\t and customer__.status = 'A'\n" +
			"\t and customer__.delete_flag = 0\n" +
			"\t and customer__.customer_id = users__.cust_account_id\n" +
			"\t and users__.user_type = '30'";

	//门店
	public static final String QUERY_STORE_IN_CUSTOMER_SQL =
			"\t from base_department department__\n" +
			"\t ,base_department_cust departmentCust__\n" +
			"\t ,base_customer customer__\n" +
			"\t ,base_inv_store_t invStoreT__\n" +
			"\t ,base_users users__\n" +
			"\t where department__.date_from <= SYSDATE\n" +
			"\t and department__.date_to >= SYSDATE\n" +
			"\t and department__.enable_flag = 'Y'\n" +
			"\t and department__.delete_flag = 0\n" +
			"\t and department__.department_id = departmentCust__.department_id\n" +
			"\t and departmentCust__.enable_flag = 'Y'\n" +
			"\t and departmentCust__.delete_flag = 0\n" +
			"\t and departmentCust__.primary_flag = 'Y'\n" +
			"\t and departmentCust__.start_date <= SYSDATE\n" +
			"\t and departmentCust__.end_date >= SYSDATE\n" +
			"\t and departmentCust__.cust_account_id = customer__.customer_id\n" +
			"\t and customer__.status = 'A'\n" +
			"\t and customer__.delete_flag = 0\n" +
			"\t and customer__.customer_id = invStoreT__.cust_account_id\n" +
			"\t and invStoreT__.delete_flag = 0\n" +
			"\t and invStoreT__.start_date <= SYSDATE\n" +
			"\t and invStoreT__.end_date >= SYSDATE\n" +
			"\t and invStoreT__.store_user_id = users__.user_id\n" +
			"\t and users__.user_type = '40'\n";

    private String id; //主键Id
	private String code;//编码
    private String name; //名称
    private String departmentId; //部门ID
    private String departmentCode; //部门编码
    private String departmentName; //部门名称
	private String customerId; //经销商ID
	private String customerCode; //经销商编码
	private String customerName; //经销商名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
