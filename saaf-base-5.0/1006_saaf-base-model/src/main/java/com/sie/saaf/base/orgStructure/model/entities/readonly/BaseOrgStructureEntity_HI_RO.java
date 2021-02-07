package com.sie.saaf.base.orgStructure.model.entities.readonly;

/**
 * 组织架构
 *
 * @auther: huqitao 2018/6/29
 */
public class BaseOrgStructureEntity_HI_RO {
    public static final String QUERY_USER_SQL =
            "SELECT users.user_id mgrUserId\n" +
                    "      ,users.user_name mgrUserName\n" +
                    "  FROM base_users    users\n" +
                    " WHERE 1 = 1\n" +
                    "   AND users.person_id = :personId\n";

    //查询导购的上级人员ID
    public static final String QUERY_GUIDE_PERSON_ID = "select guide.super_guide_person_id mgrPersonId\n" +
            "  from oa.oa_hr_guide_r guide\n" +
            "\t\t  ,base_users users\n" +
            " where upper(guide.guide_emp_no) = users.user_name\n" +
            "   and status = 'ALLOW'\n" +
            "   and guide.delete_flag <> 1\n" +
            "   and users.user_id = :userId";

    //查询人员信息
    public static final String QUERY_PERSON_SQL = "SELECT\n" +
            "\tmgrPersonAssign.person_id mgrPersonId,\n" +
            "\tmgrPersonAssign.position_id mgrPositionId,\n" +
            "\tusers.user_id mgrUserId,\n" +
            "\tusers.user_name mgrUserName,\n" +
            "\tusers.user_full_name mgrUserFullName\n" +
            "FROM\n" +
            "\tbase_person_assign mgrPersonAssign,\n" +
            "\tbase_users users\n" +
            "WHERE\n" +
            "\t1 = 1\n" +
            "AND mgrPersonAssign.enable_flag = 'Y'\n" +
            "AND mgrPersonAssign.person_id = users.person_id\n" +
            "AND mgrPersonAssign.person_id = :personId\n";

    //根据职位，查找人员的直接上级
    public static final String QUERY_IMMEDIATE_SUPERIOR_SQL = "SELECT position.ou_id ouId\n" +
            "\t\t\t,personAssign.person_id personId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,position.department_id departmentId\n" +
            "\t\t\t,position.job_id jobId\n" +
            "\t\t\t,mgrPersonAssign.person_id mgrPersonId\n" +
            "      ,personLevel.mgr_position_id mgrPositionId\n" +
            "      ,users.user_id mgrUserId\n" +
            "      ,users.user_name mgrUserName\n" +
            "\t\t\t,users.user_full_name mgrUserFullName\n" +
            "  FROM base_position position\n" +
            "\t\t\t,base_person_level  personLevel\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t,base_person_assign mgrPersonAssign\n" +
            "\t\t\t,base_users    users\n" +
            " WHERE 1 = 1\n" +
            "\t AND position.delete_flag = 0\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.ou_id = personLevel.ou_id\n" +
            "\t AND position.position_id = personLevel.position_id\n" +
            "   AND personLevel.position_id = personAssign.position_id\n" +
            "\t AND personLevel.mgr_position_id = mgrPersonAssign.position_id\n" +
            "   AND personLevel.ou_id = mgrPersonAssign.ou_id\n" +
            "   AND mgrPersonAssign.enable_flag = 'Y'\n" +
            "   AND mgrPersonAssign.person_id = users.person_id\n";

//    //根据职位，获取人员的直接上级的人员和用户信息
//    public static final String QUERY_GET_PRE_PARENT_SQL="SELECT\n" +
//            "\tpersonLevel.position_id positionId,\n" +
//            "\tpersonAssign.person_id mgrPersonId,\n" +
//            "\tpersonLevel.mgr_position_id mgrPositionId,\n" +
//            "\tusers.user_id mgrUserId,\n" +
//            "\tusers.user_name mgrUserName,\n" +
//            "  personAssign.job_id  mgrJobId,\n" +
//            "  bj.job_code  mgrJobCode,\n" +
//            "\tperson.person_name mgrPersonName,\n" +
//            "\tusers.user_full_name mgrUserFullName\n" +
//            "FROM\n" +
//            "\tbase_person_level personLevel,\n" +
//            "\tbase_person_assign personAssign,\n" +
//            "\tbase_person person,\n" +
//            "\tbase_users users,\n" +
//            "\tbase_job bj \n "+
//            "WHERE\n" +
//            "\t1 = 1 \n" +
//            "\tAND personLevel.position_id = :positionId \n" +
//            "\tAND personAssign.job_id=bj.job_id \n" +
//            "\tAND personLevel.enable_flag = 'Y' \n" +
//            "\tAND personLevel.mgr_position_id = personAssign.position_id \n" +
//            "\tAND personLevel.ou_id = personAssign.ou_id \n" +
//            "\tAND personAssign.enable_flag = 'Y' \n" +
//            "\tAND personAssign.person_id = users.person_id\n" +
//            "\tAND person.person_id=personAssign.person_id  ";

    //根据部门departmentId查找部门负责人
    public static final String QUERY_DEPT_LEADER_SQL = "SELECT personAssign.person_id personId\n" +
            "      ,position.position_id positionId\n" +
            "      ,position.position_name positionName\n" +
            "      ,users.user_id userId\n" +
            "      ,users.user_name userName\n" +
            "      ,users.user_full_name userFullName\n" +
            "  FROM base_department department\n" +
            "      ,base_position position\n" +
            "      ,base_person_assign personAssign\n" +
            "      ,base_users users \n" +
            " WHERE 1 = 1 \n" +
            "   AND department.department_id = :departmentId\n" +
            "   AND department.department_id = position.department_id\n" +
            "   AND department.ou_id = position.ou_id\n" +
            "   AND position.enable_flag = 'Y'\n" +
            "   AND position.position_id = personAssign.position_id\n" +
            "   AND personAssign.enable_flag = 'Y'\n" +
            "   AND personAssign.mgr_flag = 'Y'\n" +
            "   AND personAssign.person_id = users.person_id\n";

    //通过userId获取用户信息
    public static final String QUERY_USER_INFO_SQL = "SELECT users.user_name userName\n" +
            "\t\t\t,users.person_id personId\n" +
            "\t\t\t,blv.meaning userType\n" +
            "\t\t\t,person.postal_address postalAddress\n" +
            "\t\t\t,personAssign.ou_id ouId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,position.channel channel\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "  FROM base_users users\n" +
            "\t\t\t LEFT JOIN base_lookup_values blv ON blv.lookup_type = 'USER_TYPE'\n" +
            "\t\t\t\t\t\t AND blv.lookup_code = users.user_type AND blv.system_code = 'BASE'\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_department department\n" +
            " WHERE 1 = 1\n" +
            "\t AND users.user_id = :userId\n" +
            "\t AND users.person_id = person.person_id\n" +
            "\t AND person.person_id = personAssign.person_id\n" +
            "\t AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.primary_flag = 'Y'\n" +
            "\t AND personAssign.position_id = position.position_id\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.department_id = department.department_id\n" +
            "\t AND department.enable_flag = 'Y'";

    //根据人员查询职位
    public static final String QUERY_POSITION_BY_PERSON_ID = "SELECT position.ou_id ouId\n" +
            "      ,position.department_id departmentId\n" +
            "      ,position.position_id positionId\n" +
            "      ,position.position_name positionName\n" +
            "      ,personAssign.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "      ,personAssign.primary_flag primaryFlag\n" +
            "      ,personAssign.mgr_flag mgrFlag\n" +
            "  FROM base_position position\n" +
            "      ,base_person_assign personAssign\n" +
            "\t\t\t,base_person person\n" +
            " WHERE 1 = 1\n" +
            "   AND personAssign.date_from <= sysdate \n" +
            "   AND (personAssign.date_to IS NULL OR personAssign.date_to >= DATE_FORMAT(sysdate,'%Y-%m-%d') )\n"+
            "   AND position.position_id = personAssign.position_id\n" +
            "   AND position.ou_id = personAssign.ou_id\n" +
            "   AND personAssign.delete_flag = 0 "+
            //"   AND position.enable_flag = 'Y'\n" +
            "   AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.person_id = person.person_id\n";

    //人员职位等信息

    public static final String QUERY_POSITION_PAGINATION_SQL = "SELECT position.ou_id ouId\n" +
            "      ,position.department_id departmentId\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "      ,position.position_id positionId\n" +
            "      ,position.position_name positionName\n" +
            "      ,personAssign.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "      ,personAssign.primary_flag primaryFlag\n" +
            "\t\t\t,decode(personAssign.primary_flag,'Y','是','否') primaryFlagName\n" +
            "      ,personAssign.mgr_flag mgrFlag\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "\t\t\t,job.job_id jobId\n" +
            "\t\t\t,job.job_code jobCode\n" +
            "\t\t\t,job.job_name jobName\n" +
            "  FROM base_position position\n" +
            "\t\t\t,base_department department\n" +
            "\t\t\t,base_channel channel\n" +
            "      ,base_person_assign personAssign\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t,base_job job\n" +
            " WHERE 1 = 1\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.delete_flag = 0\n" +
            "   AND position.position_id = personAssign.position_id\n" +
            "\t AND position.department_id = department.department_id\n" +
//            "\t AND department.date_from <= trunc(sysdate)\n" +
//            "\t AND department.date_to >= trunc(sysdate)\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.delete_flag = 0\n" +
            "\t AND department.channel = channel.CHANNEL_CODE\n" +
            "   AND position.ou_id = personAssign.ou_id\n" +
            "   AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.person_id = person.person_id\n" +
            "\t AND position.job_id = job.job_id\n" +
            "\t AND job.enable_flag = 'Y'\n" +
            "\t AND job.delete_flag = 0\n";

    //根据职位查询人员
    public static final String QUERY_PERSON_BY_POSITION_ID = "select users.user_id userId,\n" +
            "       users.user_name userName,\n" +
            "       users.user_full_name userFullName,\n" +
            "       personAssign.primary_flag primaryFlag,\n" +
            "       personAssign.mgr_flag mgrFlag\n" +
            "  from base_person_assign personAssign,\n" +
            "       base_person person,\n" +
            "       base_users users\n" +
            " where personAssign.position_id = :positionId\n" +
            "   and personAssign.person_id = person.person_id\n" +
            "   and person.person_id = users.person_id\n";

    //根据上级部门查找下级部门
    public static final String QUERY_LOWER_DEPT_SQL = "SELECT department.ou_id ouId\n" +
            "\t\t\t,(select ouBlv.meaning \n" +
            "\t\t\t\t\tfrom base_lookup_values ouBlv\n" +
            "\t\t\t\t where ouBlv.lookup_type = 'BASE_OU'\n" +
            "\t\t\t\t\t and ouBlv.system_code = 'BASE'\n" +
            "\t\t\t\t\t and ouBlv.lookup_code = department.ou_id) ouName\n" +
            "\t\t\t,(case  \n" +
            "      when department.parent_department_id = department.department_id  then\n" +
            "        '0'\n" +
            "      else\n" +
            "        to_char(department.parent_department_id)\n" +
            "      end )  parentDepartmentId\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_level departmentLevel\n" +
            "\t\t\t,department.department_seq departmentSeq\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "  FROM base_department department\n" +
            "      ,base_channel channel\n" +
            " WHERE 1 = 1\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.delete_flag = 0" +
            "\t AND department.channel = channel.CHANNEL_CODE\n";

    //根据上级部门查找下级部门
    public static final String QUERY_LOWER_DEPT_NEW_SQL = "SELECT department.ou_id ouId\n" +
            "\t\t\t,(select ouBlv.meaning \n" +
            "\t\t\t\t\tfrom base_lookup_values ouBlv\n" +
            "\t\t\t\t where ouBlv.lookup_type = 'BASE_OU'\n" +
            "\t\t\t\t\t and ouBlv.system_code = 'BASE'\n" +
            "\t\t\t\t\t and ouBlv.lookup_code = department.ou_id) ouName\n" +
            "\t\t\t,if(department.parent_department_id = department.department_id, '0', department.parent_department_id) parentDepartmentId\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_level departmentLevel\n" +
            "\t\t\t,(SELECT COUNT(1)\n" +
            "\t\t\t\t\tFROM base_department t1\n" +
            "\t\t\t\t\t\t\t,base_position t2\n" +
            "\t\t\t\t\t\t\t,base_person_assign t3\n" +
            "\t\t\t\t\t\t\t,base_person t4\n" +
            "\t\t\t\t\t\t\t,base_department_hierarchy d1\n" +
            "\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t AND t1.enable_flag = 'Y'\n" +
            "\t\t\t\t\t AND t1.department_id = t2.department_id\n" +
            "\t\t\t\t\t AND t2.enable_flag = 'Y'\n" +
            "\t\t\t\t\t AND t2.position_id = t3.position_id\n" +
            "\t\t\t\t\t AND t3.enable_flag = 'Y'\n" +
            "\t\t\t\t\t AND t3.person_id = t4.person_id\n" +
            "\t\t\t\t\t AND d1.department_id = department.department_id\n" +
            "\t\t\t\t\t AND t1.department_id = d1.subordinate_department_id) personNum\n" +
            "\t\t\t,department.department_seq departmentSeq\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "  FROM base_department department\n" +
            "      ,base_channel channel\n" +
            " WHERE 1 = 1\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.delete_flag = 0" +
            "\t AND department.channel = channel.CHANNEL_CODE\n";

    //根据上级部门查找下级部门
    public static final String QUERY_LOWER_DEPT_LIST_SQL = "SELECT department.ou_id ouId\n" +
            "      ,ouBlv.meaning ouName\n" +
            "\t\t\t,(case  \n" +
            "      when department.parent_department_id = department.department_id  then\n" +
            "        '0'\n" +
            "      else\n" +
            "        to_char(department.parent_department_id)\n" +
            "      end )  parentDepartmentId\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_level departmentLevel\n" +
            "\t\t\t,department.department_seq departmentSeq\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "  FROM base_department department\n" +
            "      ,base_channel channel\n" +
            "      ,base_lookup_values ouBlv\n" +
            " WHERE 1 = 1\n" +
            "   AND department.channel = channel.CHANNEL_CODE\n" +
            "   AND department.ou_id = ouBlv.lookup_code\n" +
            "   AND ouBlv.lookup_type = 'BASE_OU'\n" +
            "   AND ouBlv.system_code = 'BASE'\n";

    //根据条件查询部门信息
    public static final String QUERY_BASE_DEPT_SQL = "SELECT *\n" +
            "  FROM (SELECT dd.ou_id\n" +
            "              ,hh.department_name AS parent_department_name\n" +
            "              ,hh.department_id   AS parent_department_id\n" +
            "              ,dd.department_name\n" +
            "              ,dd.department_id\n" +
            "          FROM base_department dd\n" +
            "          LEFT JOIN base_department hh\n" +
            "            ON dd.parent_department_id = hh.department_id\n" +
            "         WHERE 1 = 1\n" +
//            "           AND dd.date_from <=sysdate\n" +
//            "           AND dd.date_to >=sysdate\n" +
//            "           AND HH.date_from <=sysdate\n" +
//            "           AND hh.date_to >=sysdate\n" +
            "        UNION\n" +
            "        SELECT aa.ou_id\n" +
            "              ,aa.department_name AS parent_department_name\n" +
            "              ,hh.parent_department_id\n" +
            "              ,hh.department_name\n" +
            "              ,hh.department_id\n" +
            "          FROM base_department hh\n" +
            "          LEFT JOIN base_department aa\n" +
            "            ON hh.parent_department_id = aa.department_id\n" +
            "         WHERE 1 = 1\n" +
//            "           AND HH.date_from <=sysdate\n" +
//            "           AND hh.date_to >=sysdate\n" +
            "        ) bm\n" +
            " WHERE 1 = 1\n";

    //根据上级部门查询部门信息
    public static final String QUERY_BASE_DEPT_PARENT_SQL = "SELECT * FROM (SELECT\n" +
            "\tdd.ou_id,\n" +
            "\tHH.DEPARTMENT_NAME AS parent_department_name,\n" +
            "\tHH.DEPARTMENT_ID AS parent_department_id,\n" +
            "\tDD.DEPARTMENT_NAME,\n" +
            "\tDD.DEPARTMENT_ID\n" +
            "FROM\n" +
            "\tbase_department DD\n" +
            "LEFT JOIN base_department HH ON DD.parent_department_id = HH.department_id\n" +
            "WHERE\n" +
            "\t1 = 1  and DD.enable_flag='Y' and DD.delete_flag=0 AND DD.ou_id = :orgId\n" +
            "AND DD.DEPARTMENT_ID IN (\n" +
            "\tSELECT\n" +
            "\t\tBH.SUBORDINATE_DEPARTMENT_ID\n" +
            "\tFROM\n" +
            "\t\tBASE_DEPARTMENT_HIERARCHY BH\n" +
            "\tWHERE\n" +
            "\t\t bh.department_id=:departmentId\n" +
            ")" +
            ") BM WHERE 1=1\n";
    //根据部门查找人员信息
    public static final String QUERY_PERSON_POSITION_INFO_SQL = "SELECT person.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\tFROM base_department department\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t,base_person person\n" +
            " WHERE 1 = 1 \n" +
            "\t AND department.department_id = :departmentId \n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.department_id = position.department_id\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.position_id = personAssign.position_id\n" +
            "\t AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.person_id = person.person_id\n";

    public static final String QUERY_PERSON_INFO_SQL = "SELECT department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_level departmentLevel\n" +
            "\t\t\t,to_char(department.parent_department_id) as  parentDepartmentId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "\t\t\t,job.job_id jobId\n" +
            "\t\t\t,job.job_name jobName\n" +
            "\t\t\t,personAssign.mgr_flag mgrFlag\n" +
            "\t\t\t,personAssign.primary_flag primaryFlag\n" +
            "\t\t\t,person.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,person.employee_number employeeNumber\n" +
            "\t\t\t,person.tel_phone telPhone\n" +
            "\t\t\t,person.mobile_phone mobilePhone\n" +
            "\t\t\t,person.email email\n" +
            "\t\t\t,person.postal_address postalAddress\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,users.user_full_name userFullName\n" +
            "\t\t\t,users.user_type userType\n" +
            "\t\t\t,users.user_head_img_url userHeadImgUrl\n" +
            "  FROM base_department department\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_channel channel\n" +
            "\t\t\t,base_job job\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t,base_users users\n" +
            " WHERE 1 = 1\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.department_id = position.department_id\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.channel = channel.CHANNEL_CODE \n" +
            "\t AND position.job_id = job.job_id\n" +
            "\t AND position.position_id = personAssign.position_id\n" +
            "\t AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.person_id = person.person_id\n" +
            "\t AND person.enabled = 'Y'\n" +
            "\t AND person.person_id = users.person_id\n";

    public static final String QUERY_PERSON_INFO_SQL2 = "SELECT department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_level departmentLevel\n" +
           "\t\t\t,department.parent_department_id parentDepartmentId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "\t\t\t,job.job_id jobId\n" +
            "\t\t\t,job.job_name jobName\n" +
            "\t\t\t,personAssign.mgr_flag mgrFlag\n" +
            "\t\t\t,personAssign.primary_flag primaryFlag\n" +
            "\t\t\t,person.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,person.employee_number employeeNumber\n" +
            "\t\t\t,person.tel_phone telPhone\n" +
            "\t\t\t,person.mobile_phone mobilePhone\n" +
            "\t\t\t,person.email email\n" +
            "\t\t\t,person.postal_address postalAddress\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,users.user_full_name userFullName\n" +
            "\t\t\t,users.user_type userType\n" +
            "\t\t\t,users.user_head_img_url userHeadImgUrl\n" +
            "\t\t\t,row_number() OVER(PARTITION BY person.person_id ORDER BY personAssign.primary_flag desc,person.person_id desc) as row_flg\n" +
            "  FROM base_department department\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_channel channel\n" +
            "\t\t\t,base_job job\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t,base_users users\n" +
            " WHERE 1 = 1\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.department_id = position.department_id\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.channel = channel.CHANNEL_CODE \n" +
            "\t AND position.job_id = job.job_id\n" +
            "\t AND position.position_id = personAssign.position_id\n" +
            "\t AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.person_id = person.person_id\n" +
            "\t AND person.enabled = 'Y'\n" +
            "\t AND person.person_id = users.person_id\n";

    //直接上级相关信息
    public static final String QUERY_MGR_JOB_PERSON = "SELECT personLevel.level_id levelId\n" +
            "\t\t\t,personLevel.position_id positionId\n" +
            "\t\t\t,personAssign.person_id personId\n" +
            "\t\t\t,personLevel.mgr_position_id mgrPositionId\n" +
            "\t\t\t,position.position_name mgrPositionName\n" +
            "\t\t\t,job.job_id mgrJobId\n" +
            "\t\t\t,job.job_code mgrJobCode\n" +
            "\t\t\t,job.job_name mgrJobName\n" +
            "\t\t\t,mgrPersonAssign.person_id mgrPersonId\n" +
            "      ,(SELECT A.person_name FROM base_person A WHERE A.person_id = mgrPersonAssign.person_id) mgrPersonName \n" +
//            "\t\t\t,c.user_id mgrUserId\n" +
//            "\t\t\t,c.user_name mgrUserName\n" +
            " FROM base_person_level personLevel\n" +
            "\t\t\tLEFT JOIN base_person_assign personAssign ON personAssign.position_id = personLevel.position_id AND personAssign.enable_flag = 'Y' AND personAssign.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_position position ON personLevel.mgr_position_id = position.position_id AND position.enable_flag = 'Y' AND position.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_job job ON position.job_id = job.job_id AND job.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_person_assign mgrPersonAssign ON mgrPersonAssign.position_id = personLevel.mgr_position_id AND mgrPersonAssign.enable_flag = 'Y' AND mgrPersonAssign.ou_id = personLevel.ou_id\n" +
//            "      LEFT JOIN base_users c ON mgrPersonAssign.person_id = c.person_id\n" +
            " WHERE 1 = 1\n" +
            "   AND personLevel.enable_flag = 'Y'\n" +
            "\t AND personLevel.delete_flag = 0\n";
//            "   AND NOT EXISTS (select 1 from dual where mgrPersonAssign.person_id IS NULL AND c.user_id IS NOT NULL)\n";

    //直接上级职位和职位
    public static final String QUERY_MGR_JOB_PERSON_NEW_SQL = "SELECT personLevel.level_id levelId\n" +
            "\t\t\t,personLevel.position_id positionId\n" +
//            "\t\t\t,personAssign.person_id personId\n" +
            "\t\t\t,personLevel.mgr_position_id mgrPositionId\n" +
//            "\t\t\t,position.position_name mgrPositionName\n" +
            "\t\t\t,job.job_id mgrJobId\n" +
            "\t\t\t,job.job_code mgrJobCode\n" +
            "\t\t\t,job.job_name mgrJobName\n" +
            "\t\t\t,mgrPersonAssign.person_id mgrPersonId\n" +
//            "\t\t\t,t5.person_name mgrPersonName\n" +
//            "\t\t\t,t5.user_id mgrUserId\n" +
//            "\t\t\t,t5.user_name mgrUserName\n" +
            " FROM base_person_level personLevel\n" +
//            "\t\t\tLEFT JOIN base_person_assign personAssign ON personAssign.position_id = personLevel.position_id AND personAssign.enable_flag = 'Y' AND personAssign.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_position position ON position.position_id = personLevel.mgr_position_id AND position.enable_flag = 'Y' AND position.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_job job ON job.job_id = position.job_id AND job.ou_id = personLevel.ou_id\n" +
            "\t\t\tLEFT JOIN base_person_assign mgrPersonAssign ON mgrPersonAssign.position_id = personLevel.mgr_position_id AND mgrPersonAssign.enable_flag = 'Y' AND mgrPersonAssign.ou_id = personLevel.ou_id\n" +
//            "\t\t\tLEFT JOIN (\n" +
//            "\t\t\t\tSELECT bp.person_id\n" +
//            "\t\t\t\t\t\t\t,bp.person_name\n" +
//            "\t\t\t\t\t\t\t,bu.user_id\n" +
//            "\t\t\t\t\t\t\t,bu.user_name\n" +
//            "\t\t\t\t  FROM base_person bp\n" +
//            "\t\t\t\t\t\t\t LEFT JOIN base_users bu ON bu.person_id = bp.person_id\n" +
//            "\t\t\t ) t5 ON t5.person_id = mgrPersonAssign.person_id\n" +
            " WHERE 1 = 1\n" +
            "   AND personLevel.enable_flag = 'Y'\n" +
            "\t AND personLevel.delete_flag = 0\n";

    //根据职位查找所有下级人员信息(包含多级)
    public static final String QUERY_ALL_LOWER_PERSON_INFO_BY_POSITIONID = "SELECT personLevel.mgr_position_id mgrPositionId\n" +
            "\t\t\t,personLevel.position_id positionId\n" +
            "\t\t\t,t3.person_id personId\n" +
            "\t\t\t,t3.person_name personName\n" +
            "\t\t\t,t3.user_id userId\n" +
            "\t\t\t,t3.user_name userName\n" +
            "  FROM base_person_level personLevel\n" +
            "\t\t\t LEFT JOIN base_person_assign personAssign ON (personAssign.position_id = personLevel.position_id AND personAssign.enable_flag = 'Y')\n" +
            "\t\t\t LEFT JOIN (SELECT bp.person_id person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,bp.person_name person_name\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,bu.user_id user_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,bu.user_name user_name\n" +
            "\t\t\t\t\t\t\t\t\t\tFROM base_person bp\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t LEFT JOIN base_users bu ON bu.person_id = bp.person_id) t3 ON t3.person_id = personAssign.person_id\n" +
            " WHERE 1 = 1\n" +
            "\t AND personLevel.mgr_position_id = :positionId\n" +
            "\t AND personLevel.enable_flag = 'Y'";

    //人员权限（找出自己以及自己下级所有扥人员--传入的是USER_ID，员工用户）--维护界面的
    public static final String QUERY_ACCESS_PERSON_SQL = "SELECT accessBasedata.access_id accessId\t\t\t\n" +
            "\t\t\t,department.department_id departmentId \n" +
            "      ,department.department_name departmentName \n" +
            "      ,(department.department_name||'.'|| department.suffix) departmentFullName \n"+
            "\t\t\t,job.job_id jobId \n" +
            "      ,job.job_name jobName \n" +
            "\t\t\t,accessBasedata.subordinate_position_id positionId\n" +
            "\t\t\t,position.position_name positionName \n" +
            "\t\t\t,accessBasedata.subordinate_person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,person.employee_number employeeNumber\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,channel.channel_id channelId\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "  FROM base_access_basedata accessBasedata\n" +
            "       LEFT JOIN base_channel channel ON channel.CHANNEL_CODE = accessBasedata.channel_type\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t LEFT JOIN base_users users ON users.person_id = person.person_id \n" +
            "\t\t\t\t\t\t AND nvl(users.start_date, sysdate-1) <=sysdate \n" +
            "\t\t\t\t\t\t AND nvl(users.end_date, (sysdate + 1)) >=sysdate\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_department department\n" +
            "\t\t\t,base_job job\n" +
            "  WHERE 1 = 1\n" +
            "    AND accessBasedata.delete_flag='0'\n"+
            "    AND accessBasedata.access_type = '1'\n" +
            "    AND accessBasedata.ORG_ID = :orgId\n" +
            "    AND accessBasedata.USER_ID = :userId \n" +
            "    AND users.delete_flag='0'\n"+
            "    AND accessBasedata.subordinate_person_id = person.person_id\n" +
            "    AND person.enabled = 'Y'\n" +
            "    AND accessBasedata.subordinate_position_id = position.position_id\n" +
            "    AND accessBasedata.org_id = position.ou_id\n" +
            //"    AND (ISNULL(position.date_from) OR position.date_from <= trunc(sysdate))\n" +
            //"    AND (ISNULL(position.date_to) OR position.date_to >= trunc(sysdate))\n" +
            "    AND position.enable_flag = 'Y'\n" +
            "    AND position.delete_flag = 0\n" +
            "    AND position.department_id = department.department_id\n" +
            "    AND department.enable_flag = 'Y'\n" +
            "    AND department.delete_flag = 0\n" +
            "    AND position.job_id = job.job_id\n" +
            //"    AND job.date_from <= trunc(sysdate)\n" +
            //"    AND (ISNULL(job.date_to) OR job.date_to >= trunc(sysdate))\n" +
            "    AND job.enable_flag = 'Y'\n" +
            "    AND job.delete_flag = 0\n";

    //人员权限（找出自己以及自己下级所有扥人员--传入的是USER_ID，员工用户）--列表界面的
    public static final String QUERY_ACCESS_PERSON_LIST_SQL = "SELECT accessBasedata.access_id accessId\t\t\t\n" +
            "\t\t\t,department.department_id departmentId \n" +
            "      ,department.department_name departmentName \n" +
            "      ,(department.department_name||'.'|| department.suffix) departmentFullName \n"+
            "\t\t\t,job.job_id jobId \n" +
            "      ,job.job_name jobName \n" +
            "\t\t\t,accessBasedata.subordinate_position_id positionId\n" +
            "\t\t\t,position.position_name positionName \n" +
            "\t\t\t,accessBasedata.subordinate_person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,person.employee_number employeeNumber\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,channel.channel_id channelId\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "  FROM base_access_basedata accessBasedata\n" +
            "       LEFT JOIN base_channel channel ON channel.CHANNEL_CODE = accessBasedata.channel_type\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t LEFT JOIN base_users users ON users.person_id = person.person_id \n" +
            "\t\t\t\t\t\t AND nvl(users.start_date, sysdate -1) <=sysdate \n" +
            "\t\t\t\t\t\t AND nvl(users.end_date, sysdate + 1) >=sysdate\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_department department\n" +
            "\t\t\t,base_job job\n" +
            "  WHERE 1 = 1\n" +
            "    AND accessBasedata.access_type = '1'\n" +
            "    AND accessBasedata.ORG_ID = :orgId\n" +
            "    AND accessBasedata.USER_ID = :userId \n" +
            "    AND accessBasedata.subordinate_person_id = person.person_id\n" +
            //"    AND person.enabled = 'Y'\n" +
            "    AND accessBasedata.subordinate_position_id = position.position_id\n" +
            "    AND accessBasedata.org_id = position.ou_id\n" +
            //"    AND (ISNULL(position.date_from) OR position.date_from <= trunc(sysdate))\n" +
            //"    AND (ISNULL(position.date_to) OR position.date_to >= trunc(sysdate))\n" +
            //"    AND position.enable_flag = 'Y'\n" +
            //"    AND position.delete_flag = 0\n" +
            "    AND position.department_id = department.department_id\n" +
            //"    AND department.enable_flag = 'Y'\n" +
            //"    AND department.delete_flag = 0\n" +
            "    AND position.job_id = job.job_id\n" ;
    //"    AND job.date_from <= trunc(sysdate)\n" +
    //"    AND (ISNULL(job.date_to) OR job.date_to >= trunc(sysdate))\n" +
    //"    AND job.enable_flag = 'Y'\n" +
    //"    AND job.delete_flag = 0\n";

    //经销商权限（找出自己以及自己下级所关联的经销商—传入的是USER_ID，员工用户，经销商用户）
    public static final String QUERY_ACCESS_CUSTOMER_SQL = "SELECT DISTINCT accessBasedata.org_id orgId\n" +
            "\t\t\t,accessBasedata.user_id userId\n" +
            "\t\t\t,department.department_id departmentId \n" +
            "      ,department.department_name departmentName \n" +
            "\t\t\t,customer.customer_id customerId \n" +
            "      ,customer.customer_name customerName \n" +
            "      ,customer.customer_number customerNumber \n" +
            "\t\t\t,accessBasedata.secondary_inventory_name subInvName \n" +
            "\t\t\t,channel.channel_id channelId\n" +
            "\t\t\t,channel.CHANNEL_CODE channelCode\n" +
            "\t\t\t,channel.CHANNEL_NAME channelName\n" +
            "\t\t\t,accessBasedata.subordinate_person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,accessBasedata.subordinate_position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "  FROM base_access_basedata accessBasedata\n" +
            "\t\t\t LEFT JOIN base_person person ON person.person_id = accessBasedata.subordinate_person_id AND person.enabled = 'Y'\n" +
            "\t\t\t LEFT JOIN base_position position ON position.position_id = accessBasedata.position_id AND position.ou_id = accessBasedata.org_id\n" +
            "\t\t\t,base_customer customer\n" +
            "      ,base_channel channel\n" +
            "\t\t\t,base_department_cust departmentCust\n" +
            "      ,base_department department\n" +
            " WHERE 1 = 1\n" +
            "   AND accessBasedata.ACCESS_TYPE = '2'\n" +
            "   AND accessBasedata.ORG_ID = :orgId\n" +
            "   AND accessBasedata.USER_ID = :userId\n" +
            "\t AND accessBasedata.cust_account_id = customer.customer_id\n" +
            "   AND customer.status = 'A'\n" +
            "   AND accessBasedata.channel_type = channel.CHANNEL_CODE\n" +
            "\t AND customer.customer_id = departmentCust.cust_account_id\n" +
            "   AND departmentCust.delete_flag = 0\n" +
            "   AND departmentCust.department_id = department.department_id\n";

    //经销商对应业务人员
    public static final String QUERY_CUSTOMER_AND_PERSON_SQL = "SELECT DISTINCT personCust.cust_account_id custAccountId\n" +
            "\t\t\t,person.person_id personId\n" +
            "\t\t\t,person.person_name personName\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,position.position_name positionName\n" +
            "\t\t\t,job.job_id jobId\n" +
            "\t\t\t,job.job_name jobName\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "  FROM base_person_cust personCust\n" +
            "\t\t\t,base_person person\n" +
            "\t\t\t LEFT JOIN base_users users ON users.person_id = person.person_id\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_job job\n" +
            "\t\t\t,base_department department\n" +
            " WHERE 1 = 1\n" +
            "\t AND personCust.enable_flag = 'Y'\n" +
            "\t AND personCust.person_id = person.person_id\n" +
            "\t AND personCust.ou_id = position.ou_id\n" +
            "\t AND personCust.position_id = position.position_id\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.job_id = job.job_id\n" +
            "\t AND job.enable_flag = 'Y'\n" +
            "\t AND job.date_from <= trunc(sysdate)\n" +
            "\t AND (ISNULL(job.date_to) OR job.date_to >= trunc(sysdate))\n" +
            "\t AND position.department_id = department.department_id";

    //部门经销商树
    public static final String QUERY_DEPT_CUS_TREE_SQL = "SELECT department.ou_id ouId\n" +
            "\t\t\t,if(department.parent_department_id = department.department_id, '0', department.parent_department_id) parentDepartmentId\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,customer.customer_id customerId\n" +
            "\t\t\t,customer.customer_number customerNumber\n" +
            "\t\t\t,customer.customer_name customerName\n" +
            "\tFROM base_department department\n" +
            "\t\t\t,base_department_cust departmentCust\n" +
            "\t\t\t,base_customer customer\n" +
            " WHERE 1 = 1\n" +
//            "\t AND department.date_from <= trunc(sysdate)\n" +
//            "\t AND department.date_to >= trunc(sysdate)\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.delete_flag = 0\n" +
            "\t AND department.department_id = departmentCust.department_id\n" +
            "\t AND departmentCust.enable_flag = 'Y'\n" +
            "\t AND departmentCust.delete_flag = 0\n" +
            "\t AND departmentCust.primary_flag = 'Y'\n" +
            "\t AND departmentCust.start_date <= trunc(sysdate)\n" +
            "\t AND departmentCust.end_date >= trunc(sysdate)\n" +
            "\t AND departmentCust.cust_account_id = customer.customer_id\n" +
            "\t AND customer.status = 'A'\n" +
            "\t AND customer.delete_flag = 0\n";

    //查询部门下的人员,包含user信息
    public static final String QUERY_PERSON_IN_DEPT_SQL = "SELECT department.department_id departmentId\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,position.position_id positionId\n" +
            "      ,position.position_name positionName\n" +
            "\t\t\t,person.person_id personId\n" +
            "      ,person.person_name personName\n" +
            "      ,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,users.user_full_name userFullName\n" +
            "\t\t\t,users.user_type userType\n" +
            "  FROM base_department department\n" +
            "      ,base_position position\n" +
            "      ,base_person_assign personAssign\n" +
            "      ,base_person person\n" +
            "\t\t\t,base_users users\n" +
            " WHERE 1 = 1 \n" +
            "   AND department.enable_flag = 'Y'\n" +
            "   AND department.department_id = position.department_id\n" +
            "   AND position.enable_flag = 'Y'\n" +
            "   AND position.position_id = personAssign.position_id\n" +
            "   AND personAssign.enable_flag = 'Y'\n" +
            "   AND users.user_type = '20'"+
            "   AND personAssign.person_id = person.person_id\n" +
            "\t AND person.person_id = users.person_id\n";

    //查询部门下的经销商,包含user信息
    public static final String QUERY_CUSTOMER_IN_DEPT_SQL = "SELECT department.ou_id ouId\n" +
            "\t\t\t,department.department_id departmentId\n" +
            "\t\t\t,department.department_name departmentName\n" +
            "\t\t\t,department.department_code departmentCode\n" +
            "\t\t\t,customer.customer_id customerId\n" +
            "\t\t\t,customer.customer_number customerNumber\n" +
            "\t\t\t,customer.customer_name customerName\n" +
            "\t\t\t,customer.customer_name personName\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,users.user_name userName\n" +
            "\t\t\t,users.user_full_name userFullName\n" +
            "\t\t\t,users.user_type userType\n" +
            "\tFROM base_department department\n" +
            "\t\t\t,base_department_cust departmentCust\n" +
            "\t\t\t,base_customer customer\n" +
            "\t\t\t,base_users users\n" +
            " WHERE 1 = 1\n" +
//            "\t AND department.date_from <= trunc(sysdate)\n" +
//            "\t AND department.date_to >= trunc(sysdate)\n" +
            "\t AND department.enable_flag = 'Y'\n" +
            "\t AND department.delete_flag = 0\n" +
            "\t AND department.department_id = departmentCust.department_id\n" +
            "\t AND departmentCust.enable_flag = 'Y'\n" +
            "\t AND departmentCust.delete_flag = 0\n" +
            "\t AND departmentCust.primary_flag = 'Y'\n" +
            "\t AND departmentCust.start_date <= trunc(sysdate)\n" +
            "\t AND departmentCust.end_date >= trunc(sysdate)\n" +
            "\t AND departmentCust.cust_account_id = customer.customer_id\n" +
            "\t AND customer.status = 'A'\n" +
            "\t AND customer.delete_flag = 0\n" +
            "\t AND customer.customer_id = users.cust_account_id\n" +
            "\t AND users.user_type = '30'";

    //查询经销商下的门店,包含user信息
    public static final String QUERY_STORE_IN_CUSTOMER_SQL = "SELECT\n" +
            "\tinvStoreT.ou_id ouId,\n" +
            "\tinvStoreT.store_code storeCode,\n" +
            "\tinvStoreT.store_name storeName,\n" +
            "\tinvStoreT.store_name personName,\n" +
            "\tusers.user_id userId,\n" +
            "\tusers.user_name userName,\n" +
            "\tusers.user_full_name userFullName,\n" +
            "\tusers.user_type userType,\n" +
            "\tdepartment.department_id departmentId,\n" +
            "\tdepartment.department_name departmentName,\n" +
            "\tdepartment.department_code departmentCode\n" +
            "FROM\n" +
            "\tbase_inv_store_t invStoreT,\n" +
            "\tbase_department_cust bdc,\n" +
            "\tbase_users users,\n" +
            "  base_department\tdepartment\n" +
            "WHERE\n" +
            "\t1 = 1 \n" +
            "\tAND invStoreT.delete_flag = 0 \n" +
            "\tAND invStoreT.start_date <= CURDATE( ) AND invStoreT.end_date >= CURDATE( ) \n" +
            "\tAND invStoreT.store_user_id = users.user_id \n" +
            "\tAND users.user_type = '40'\n" +
            "\tAND bdc.cust_account_id = invStoreT.cust_account_id\n" +
            "\tAND invStoreT.ou_id=bdc.ou_id\n" +
            "\tAND department.department_id=bdc.department_id    ";

    public static final String QUERY_FIND_PERSON_NEW = "select s.person_id personId\n" +
            "\t\t\t,s.person_name personName\n" +
            "\t\t\t,s.employee_number employeeNumber\n" +
            "\t\t\t,s.department_id departmentId\n" +
            "\t\t\t,substring_index(GROUP_CONCAT(s.position_name SEPARATOR ','), ',', 1) positionName\n" +
            "\t\t\t,substring_index(GROUP_CONCAT(s.primary_flag SEPARATOR ','), ',', 1) primaryFlag\n" +
            "  from (select person.person_id\n" +
            "\t\t\t\t\t\t\t,person.person_name\n" +
            "\t\t\t\t\t\t\t,person.employee_number\n" +
            "\t\t\t\t\t\t\t,position.position_name\n" +
            "\t\t\t\t\t\t\t,personAssign.primary_flag\n" +
            "\t\t\t\t\t\t\t,position.ou_id\n" +
            "\t\t\t\t\t\t\t,department.department_id\n" +
            "\t\t\t\t\tfrom base_person person\n" +
            "\t\t\t\t\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t\t\t\t\t,base_position position\n" +
            "\t\t\t\t\t\t\t,base_department department" +
            "\t\t\t\t where 1 = 1\n" +
            "\t\t\t\t\t\tand person.person_id = personAssign.person_id\n" +
            "\t\t\t\t\t\tand personAssign.ou_id = position.ou_id\n" +
            "\t\t\t\t\t\tand personAssign.position_id = position.position_id\n" +
            "\t\t\t\t\t\tand department.department_id = position.department_id\n" +
            "\t\t\t\t\t\tand department.enable_flag = 'Y'\n" +
            "\t\t\t\t\tORDER BY personAssign.primary_flag desc) s\n" +
            " where 1 = 1\n";

    private Integer ouId;//事业部ID
    private Integer orgId;//事业部ID
    private String ouName;

    private Integer custAccountId; //经销商ID
    private Integer departmentId; //部门ID
    private String departmentCode;//部门编码
    private String departmentName; //部门名称
    private String departmentFullName;//部门名称改
    private Integer departmentLevel;//部门层级
    private Integer departmentSeq;////部门排序
    private Integer channelId; //渠道ID
    private String channelCode; //渠道编码
    private String channelName; //渠道名称
    private Integer customerId; //经销商ID
    private String customerName; //经销商名称
    private String customerNumber; //经销商编码
    private String subInvName; //子库

    private Integer positionId;//职位ID
    private String positionName; //职位名称
    private String channel; //渠道;
    private Integer jobId;//职务ID
    private String jobCode;
    private String jobName;//职务名称
    private Integer levelId;
    private String mgrFlag;//是否部门负责人
    private String primaryFlag;//是否主职位标记
    private String primaryFlagName;//是否主职位
    private Integer personId; //人员ID
    private String personName;//人员名称
    private String employeeNumber; //员工号
    private String telPhone; //座机号
    private String mobilePhone; //手机号
    private String email; //邮箱
    private String postalAddress; //地址
    private Integer userId;//用户ID
    private String userName; //用户名称
    private String userFullName;//用户姓名
    private String userType; //用户类型
    private String userHeadImgUrl; //头像

    private String mgrJobName; //上级人员ID
    private Integer mgrPositionId; //上级职位ID
    private String mgrPositionName;
    private Integer mgrPersonId; //上级人员ID
    private String mgrPersonName;//上级人员名称
    private Integer mgrUserId;//上级用户ID
    private String mgrUserName;//上级用户名称
    private String parentDepartmentId;//上级部门ID
    private String parentDepartmentName;//上级部门名称
    private String accessId;

    private String storeCode; //门店编码
    private String storeName; //门店名称
    private String mgrUserFullName;//用户全称
    private Integer mgrJobId;
    private String mgrJobCode;
    private Integer personNum;//人员数量

    public Integer getOuId() {
        return ouId;
    }

    public void setOuId(Integer ouId) {
        this.ouId = ouId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOuName() {
        return ouName;
    }

    public void setOuName(String ouName) {
        this.ouName = ouName;
    }

    public Integer getCustAccountId() {
        return custAccountId;
    }

    public void setCustAccountId(Integer custAccountId) {
        this.custAccountId = custAccountId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
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

    public Integer getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Integer departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public Integer getDepartmentSeq() {
        return departmentSeq;
    }

    public void setDepartmentSeq(Integer departmentSeq) {
        this.departmentSeq = departmentSeq;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getSubInvName() {
        return subInvName;
    }

    public void setSubInvName(String subInvName) {
        this.subInvName = subInvName;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getMgrFlag() {
        return mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }

    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    public String getPrimaryFlagName() {
        return primaryFlagName;
    }

    public void setPrimaryFlagName(String primaryFlagName) {
        this.primaryFlagName = primaryFlagName;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserHeadImgUrl() {
        return userHeadImgUrl;
    }

    public void setUserHeadImgUrl(String userHeadImgUrl) {
        this.userHeadImgUrl = userHeadImgUrl;
    }

    public String getMgrJobName() {
        return mgrJobName;
    }

    public void setMgrJobName(String mgrJobName) {
        this.mgrJobName = mgrJobName;
    }

    public Integer getMgrPositionId() {
        return mgrPositionId;
    }

    public void setMgrPositionId(Integer mgrPositionId) {
        this.mgrPositionId = mgrPositionId;
    }

    public String getMgrPositionName() {
        return mgrPositionName;
    }

    public void setMgrPositionName(String mgrPositionName) {
        this.mgrPositionName = mgrPositionName;
    }

    public Integer getMgrPersonId() {
        return mgrPersonId;
    }

    public void setMgrPersonId(Integer mgrPersonId) {
        this.mgrPersonId = mgrPersonId;
    }

    public String getMgrPersonName() {
        return mgrPersonName;
    }

    public void setMgrPersonName(String mgrPersonName) {
        this.mgrPersonName = mgrPersonName;
    }

    public Integer getMgrUserId() {
        return mgrUserId;
    }

    public void setMgrUserId(Integer mgrUserId) {
        this.mgrUserId = mgrUserId;
    }

    public String getMgrUserName() {
        return mgrUserName;
    }

    public void setMgrUserName(String mgrUserName) {
        this.mgrUserName = mgrUserName;
    }

    public String getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(String parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    public String getParentDepartmentName() {
        return parentDepartmentName;
    }

    public void setParentDepartmentName(String parentDepartmentName) {
        this.parentDepartmentName = parentDepartmentName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getMgrUserFullName() {
        return mgrUserFullName;
    }

    public void setMgrUserFullName(String mgrUserFullName) {
        this.mgrUserFullName = mgrUserFullName;
    }

    public Integer getMgrJobId() {
        return mgrJobId;
    }

    public void setMgrJobId(Integer mgrJobId) {
        this.mgrJobId = mgrJobId;
    }

    public String getMgrJobCode() {
        return mgrJobCode;
    }

    public void setMgrJobCode(String mgrJobCode) {
        this.mgrJobCode = mgrJobCode;
    }

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public String getDepartmentFullName() {
        return departmentFullName;
    }

    public void setDepartmentFullName(String departmentFullName) {
        this.departmentFullName = departmentFullName;
    }
}
