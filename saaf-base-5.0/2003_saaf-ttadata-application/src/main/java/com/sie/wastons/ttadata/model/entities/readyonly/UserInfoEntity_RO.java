package com.sie.wastons.ttadata.model.entities.readyonly;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfoEntity_RO {
	private Integer userId;

	private String userName;

	private String userFullName;

	private BigDecimal phoneNumber;

	private String email;

	private Integer personId;

	private String userType;

	private String userDesc;

	private String isadmin;

	private String employeeNumber;
	private String personName;
	private String roleQA;
	private String roleIA;
	private String roleSecurity;
	private String jobCode;
	private String deptNo;
	// 显示中文
    private String departmentName;
    // 显示英文
    private String departmentFullName;
    // 名称英文
    private String personNameEn;
    private Integer departmentId;

    private String code;
    private String nameEn;
    private String nameCn;
    private Integer result;


	//查询用户上级信息
	public static final String QUERY_PROCCESS_USER_NODE="SELECT DISTINCT\n" +
			"\tU_UP.USER_ID,\n" +
			"\tU_UP.USER_NAME,\n" +
			"\tU_UP.PERSON_ID,\n" +
			"\tP_UP.EMPLOYEE_NUMBER,\n" +
			"\tP_UP.PERSON_NAME,\n" +
			"\tP_UP.POST_NAME as JOB_CODE\n" +
			"FROM\n" +
			"\tBASE_USERS U\n" +
			"\tJOIN BASE_PERSON P ON U.PERSON_ID = P.PERSON_ID\n" +
			"\tJOIN TTA_REPORT_RELATIONSHIP_IN R ON P.EMPLOYEE_NUMBER = R.EMPLOYEE_NO\n" +
			"\tJOIN BASE_PERSON P_UP ON P_UP.EMPLOYEE_NUMBER = R.REPORT_TO\n" +
			"\tJOIN BASE_PERSON_ASSIGN ASSIGN ON ASSIGN.PERSON_ID = P_UP.PERSON_ID\n" +
			"\tJOIN BASE_USERS U_UP ON U_UP.PERSON_ID = P_UP.PERSON_ID \n" +
			"WHERE\n" +
			"\t1 = 1";

	//查询联合部门LOV
	public static final String QUERY_SQL_UNION_DEPT = "select t.code\n" +
			"      ,t.name_cn nameCn\n" +
			"      ,t.name_en nameEn\n" +
			"from   tta_dept_in t where 1 = 1 ";

    //查询人员LOV
    public static final String QUERY_SQL_SCORING_MEMBER =
                    "SELECT p.person_id personId,\n" +
                            "       p.employee_number employeeNumber,\n" +
                            "       p.person_name personName,\n" +
                            "       p.PERSON_NAME_EN personNameEn,\n" +
                            "       p.DEPT_NO deptNo,\n" +
                            "       bd.department_id departmentId,\n" +
                            "       bd.department_name departmentName,\n" +
                            "       bd.DEPARTMENT_FULL_NAME departmentFullName,\n" +
                            "       bu.user_id userId,\n" +
                            "       'QA' roleQA,\n" +
                            "       'IA' roleIA,\n" +
                            "       'Security' roleSecurity\n" +
                            "  FROM base_person p, \n" +
                            "  base_users bu,\n" +
                            "  base_department bd\n" +
                            " WHERE p.person_id = bu.person_id\n" +
                            " and p.dept_no = bd.DEPARTMENT_CODE";

	public static String QUERY_SQL = "SELECT bu.user_id              userId,\n" +
            "       bu.USER_NAME            userName,\n" +
            "       bu.USER_FULL_NAME       userFullName,\n" +
            "       bu.PHONE_NUMBER         phoneNumber,\n" +
			"       bp.person_name_cn       personNameCn,\n" +
            "       bp.email                email,\n" +
            "       bp.dept_no              deptNo,\n" +
            "       bd.department_name      departmentName,\n" +
            "       bd.department_full_name departmentFullName\n" +
            "  FROM BASE_USERS bu\n" +
            "  left join base_person bp\n" +
            "    on bu.person_id = bp.person_id\n" +
            "  left join base_department bd\n" +
            "    on bd.department_code = bp.dept_no\n" +
            " WHERE 1 = 1";

	public static String QUERY_BY_USER_TYPE_SQL = "SELECT\n"
			+ "	baseUsers.user_id AS userId ,\n"
			+ "	baseUsers.person_id as personId,\n"
			+ "	baseUsers.user_full_name as userFullName,\n"
			+ "	baseUsers.user_type as userType,\n"
			+ "	baseUsers.user_desc as userDesc,\n"
			+ "	baseUsers.user_name as userName ,\n"
			+ "	baseUsers.isadmin as isadmin \n" + " FROM\n"
			+ "	base_users  baseUsers\n" + "WHERE 1=1 ";

	// 查询人员LOV
//	public static final String QUERY_SQL_SCORING_MEMBER = "SELECT p.employee_number employeeNumber\n"
//			+ "      ,p.person_name personName\n"
//			+ "      ,'QA' roleQA\n"
//			+ "      ,'IA' roleIA\n"
//			+ "      ,'Security' roleSecurity\n"
//			+ "FROM   base_person p WHERE 1 = 1";

	public static String QUERY_USER_BY_PROCCESS_START_USER_ID = "select  "
			+ "   t1.user_name,"
			+ "   t1.user_id, "
			+ "	t1.user_type from base_users t1\n"
			+ // --上级
			"inner join \n"
			+ "(\n"
			+ "select a.user_id, a.user_type, a.user_name, to_char(d.person_id) as person_id \n"
			+ // -- 上级父级
			"  from base_users a\n" + " inner join base_person b\n"
			+ "    on a.person_id = b.person_id\n"
			+ " inner join tta_report_relationship_in c\n"
			+ "    on c.employee_no = b.employee_number\n"
			+ "  left join base_person d\n"
			+ "    on d.employee_number = c.report_to\n"
			+ " where a.user_id =:userId" + "\n"
			+ " ) t2 on t1.person_id = t2.person_id";

	//查询接口权限
	public static final String QUERY_SQL_INTERFACE_ACCESS_CONTROL =
			"select count(1) result\n" +
					"from   base_responsibility_role rr\n" +
					"      ,base_role_menu rm\n" +
					"      ,base_menu bm\n" +
					"where rr.role_id = rm.role_id\n" +
					"and   rm.menu_id = bm.menu_id ";
}
