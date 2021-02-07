package com.sie.saaf.base.user.model.entities.readonly;

import com.sie.saaf.base.user.model.entities.BaseUserGroupAssignEntity_HI;

public class BaseUserGroupAssignEntity_HI_RO extends
		BaseUserGroupAssignEntity_HI {

	public static final String QUERY = "   SELECT t.assign_id  assignId\n"
			+ "        ,t.user_id    userId\n"
			+ "        ,t.user_group_code  userGroupCode \n"
			+ "        ,t.enable_flag      enableFlag \n"
			+ "        ,t.created_by       createdBy \n"
			+ "        ,t.last_updated_by  lastUpdatedBy \n"
			+ "        ,t.creation_date    creationDate \n"
			+ "        ,t.last_update_login  lastUpdateLogin \n"
			+ "        ,t.last_update_date   lastUpdateDate \n"
			+ "        ,t.version_num        versionNum \n"
			+ "        ,nvl(blv.meaning ,'') userGroupName \n"
			+ "        ,bu.user_name     userName \n"
			+ "        ,bu.user_full_name     userFullName \n"
			+ "    FROM base_user_group_assign t \n"
			+ "   INNER JOIN base_users bu \n"
			+ "      ON bu.user_id = t.user_id \n"
			+ "    LEFT JOIN base_lookup_values blv \n"
			+ "      ON blv.lookup_type = 'USER_GROUP' \n"
			+ "     AND blv.lookup_code = t.user_group_code " + " where 1=1 \n";

	public static final String QUERY_FOR_PERSON = " SELECT t5.user_id  userId \n"
			+ "      ,t5.user_name    userName\n"
			+ "      ,t6.user_group_code  userGroupCode\n"
			+ "      ,t6.user_group_name  userGroupName\n"
			+ "  FROM base_users                 t1\n"
			+ "      ,base_person                t2\n"
			+ "      ,tta_report_relationship_in t3\n"
			+ "      ,base_person                t4\n"
			+ "      ,base_users                 t5\n"
			+ "      ,base_user_group_assign     t6\n"
			+ " WHERE t1.person_id = t2.person_id\n"
			+ "   AND t2.employee_number = t3.employee_no\n"
			+ "   AND t3.report_to = t4.employee_number\n"
			+ "   AND t4.person_id = t5.person_id\n"
			+ "   AND t5.user_id = t6.user_id  \n "
			+ " START WITH t1.user_id = :userId \n"
			+ "CONNECT BY nocycle PRIOR t3.report_to = t3.employee_no ";

	String userName;
	String userFullName;
	String employeeNumber;
	private String postName;

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
