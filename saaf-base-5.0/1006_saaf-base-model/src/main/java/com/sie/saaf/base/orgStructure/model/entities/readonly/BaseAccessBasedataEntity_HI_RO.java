package com.sie.saaf.base.orgStructure.model.entities.readonly;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * BaseAccessBasedataEntity_HI_RO Entity Object
 * Sat Aug 04 16:37:35 CST 2018  Auto Generate
 */

public class BaseAccessBasedataEntity_HI_RO {
    //通过userType查询相关用户信息
    public static final String QUERY_USER_INFO_SQL = "select users.user_id userId\n" +
            "	   ,users.user_type userType\n" +
            "  from base_users users \n" +
            " where 1 = 1\n" +
            "\t and users.start_date <sysdate\n" +
            "\t and nvl(users.end_date,DATE_ADD(sysdate,INTERVAL 1 DAY)) >sysdate\n";

    //查询所有人员权限数据临时表的批次号
    public static final String QUERY_ALL_BATCH_CODE_SQL = "select personAccessTemp.batch_code batchCode\n" +
            "\t\t\t,personAccessTemp.org_id orgId\n" +
            "\t\t\t,personAccessTemp.user_id userId\n" +
            "\t\t\t,personAccessTemp.person_id personId\n" +
            "\t\t\t,personAccessTemp.position_id positionId\n" +
            "  from base_person_access_temp personAccessTemp\n" +
            "GROUP BY personAccessTemp.batch_code";

    //查询所有有效的职位信息
    public static final String QUERY_ALL_POSITION_SQL = "select position.ou_id orgId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "  from base_position position\n" +
            " where 1 = 1\n" +
            "\t and (ISNULL(position.date_from) OR position.date_from <= trunc(sysdate))\n" +
            "\t and (ISNULL(position.date_to) OR position.date_to >= trunc(sysdate))\n" +
            "\t and position.enable_flag = 'Y'\n";

    //查询职位分配的人员信息
    public static final String QUERY_POSITION_DISTRIBUTION_PERSON_SQL = "SELECT position.ou_id orgId\n" +
            "\t\t\t,position.position_id positionId\n" +
            "\t\t\t,personAssign.person_id personId\n" +
            "\t\t\t,users.user_id userId\n" +
            "\t\t\t,position.channel channelType\n" +
            "\tFROM base_position position\n" +
            "\t\t\t,base_person_assign personAssign\n" +
            "\t\t\t LEFT JOIN base_users users ON users.person_id = personAssign.person_id\n" +
            " WHERE 1 = 1\n" +
            "\t AND position.position_id = personAssign.position_id\n" +
            "\t AND personAssign.enable_flag = 'Y'\n" +
            "\t AND personAssign.date_from <= trunc(sysdate)\n" +
            "\t AND nvl(personAssign.date_to,trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t AND personAssign.delete_flag = 0\n";

    //人员20权限数据同步
    public static final String QUERY_PERSON_20_ACCESS_SYN = "SELECT personLevel.ou_id orgId\n" +
            "\t\t\t,position.position_id subordinatePositionId\n" +
            "\t\t\t,personAssignNext.person_id subordinatePersonId\n" +
            "  FROM base_person_level personLevel\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t LEFT JOIN base_person_assign personAssignNext ON personAssignNext.position_id = position.position_id AND personAssignNext.enable_flag = 'Y'\n" +
            "\tWHERE 1 = 1\n" +
            "\t\tAND personLevel.ou_id = :orgId\n" +
            "\t\tAND personLevel.mgr_position_id = :positionId\n" +
            "\t\tAND personLevel.enable_flag = 'Y'\n" +
            "\t\tAND personLevel.position_id = position.position_id\n" +
            "    AND position.enable_flag = 'Y'";

    //人员20权限数据同步 --> 存在权限表，不存在临时表 --> 删除老数据
    public static final String QUERY_EXIST_ACCESS_NOT_EXIST_TEMP_SQL = "select accessBasedata.access_id accessId\n" +
            "\t\t\t,accessBasedata.access_type accessType\n" +
            "\t\t\t,accessBasedata.org_id orgId\n" +
            "\t\t\t,accessBasedata.user_id userId\n" +
            "\t\t\t,accessBasedata.person_id personId\n" +
            "\t\t\t,accessBasedata.position_id positionId\n" +
            "\t\t\t,accessBasedata.subordinate_person_id subordinatePersonId\n" +
            "\t\t\t,accessBasedata.subordinate_position_id subordinatePositionId\n" +
            "  from base_access_basedata accessBasedata \n" +
            " where 1 = 1\n" +
            "\t and accessBasedata.access_type = '1'\n" +
            "\t and accessBasedata.user_id <> -10\n" +
            "\t and accessBasedata.org_id = :orgId\n" +
            "\t and accessBasedata.person_id = :personId\n" +
            "\t and NOT EXISTS (select 1 \n" +
            "\t\t\t\t\t\t\t\t\t\t from base_person_access_temp personAccessTemp \n" +
            "\t\t\t\t\t\t\t\t\t  where personAccessTemp.batch_code = :batchCode \n" +
            "\t\t\t\t\t\t\t\t\t\t  and personAccessTemp.org_id = accessBasedata.org_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\tand personAccessTemp.person_id = accessBasedata.person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  and personAccessTemp.subordinate_person_id = accessBasedata.subordinate_person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand personAccessTemp.subordinate_position_id = accessBasedata.subordinate_position_id)\n";

    //人员20权限数据同步 --> 存在临时表，不存在权限表 --> 插入新数据
    public static final String QUERY_EXIST_TEMP_NOT_EXIST_ACCESS_SQL = "select personAccessTemp.batch_code batchCode\n" +
            "\t\t\t,personAccessTemp.access_type accessType\n" +
            "\t\t\t,personAccessTemp.org_id orgId\n" +
            "\t\t\t,personAccessTemp.user_id userId\n" +
            "\t\t\t,personAccessTemp.person_id personId\n" +
            "\t\t\t,personAccessTemp.position_id positionId\n" +
            "\t\t\t,personAccessTemp.subordinate_person_id subordinatePersonId\n" +
            "\t\t\t,personAccessTemp.subordinate_position_id subordinatePositionId\n" +
            "\t\t\t,personAccessTemp.channel_type channelType\n" +
            "  from base_person_access_temp personAccessTemp\n" +
            " where 1 = 1\n" +
            "\t and personAccessTemp.batch_code = :batchCode\n" +
            "\t and personAccessTemp.org_id = :orgId\n" +
            "\t and personAccessTemp.person_id = :personId\n" +
            "\t and not EXISTS (select 1 \n" +
            "\t\t\t\t\t\t\t\t\t\t from base_access_basedata accessBasedata \n" +
            "\t\t\t\t\t\t        where accessBasedata.org_id = personAccessTemp.org_id \n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.person_id = personAccessTemp.person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.subordinate_person_id = personAccessTemp.subordinate_person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.subordinate_position_id = personAccessTemp.subordinate_position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  and accessBasedata.access_type = '1' and accessBasedata.user_id <> -10)\n";

    //人员10权限数据同步 --> 存在权限表，不存在新数据列表 --> 删除老数据
    public static final String QUERY_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "select accessBasedata.access_id accessId\n" +
            "  from base_access_basedata accessBasedata\n" +
            " where 1 = 1\n" +
            "\t and accessBasedata.access_type = '1'\n" +
            "\t and accessBasedata.user_id = -10\n" +
            "\t and not EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_person_level personLevel\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_position position\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_person_assign personAssignNext\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personLevel.enable_flag = 'Y'\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personLevel.delete_flag = 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personLevel.date_from <= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(personLevel.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personLevel.position_id = position.position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND position.delete_flag = 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(position.date_from, trunc(sysdate)) <= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(personAssignNext.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(position.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personAssignNext.enable_flag = 'Y'\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND position.enable_flag = 'Y'\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND position.position_id  = personAssignNext.position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(personAssignNext.date_from, trunc(sysdate)) <= trunc(sysdate)\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personAssignNext.delete_flag = 0\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personLevel.ou_id = accessBasedata.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND position.position_id = accessBasedata.subordinate_position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND personAssignNext.person_id = accessBasedata.subordinate_person_id)";


    //人员10权限数据同步 --> 存在新数据列表，不存在临时表 --> 插入新数据
    public static final String QUERY_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "SELECT personLevel.ou_id orgId\n" +
            "\t\t\t,'1' accessType\n" +
            "\t\t\t,-10 userId\n" +
            "\t\t\t,0 personId\n" +
            "\t\t\t,-10 positionId\n" +
            "\t\t\t,position.position_id subordinatePositionId\n" +
            "\t\t\t,personAssignNext.person_id subordinatePersonId\n" +
            "\t\t\t,position.channel channelType\n" +
            "\tFROM base_person_level personLevel\n" +
            "\t\t\t,base_position position\n" +
            "\t\t\t,base_person_assign personAssignNext\n" +
            " WHERE 1 = 1\n" +
            "\t AND personLevel.enable_flag = 'Y'\n" +
            "\t AND personLevel.delete_flag = 0\n" +
            "\t AND personLevel.date_from <= trunc(sysdate)\n" +
            "\t AND nvl(personLevel.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t AND personLevel.position_id = position.position_id\n" +
            "\t AND position.delete_flag = 0\n" +
            "\t AND nvl(position.date_from, trunc(sysdate)) <= trunc(sysdate)\n" +
            "\t AND nvl(personAssignNext.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t AND nvl(position.date_to, trunc(sysdate)) >= trunc(sysdate)\n" +
            "\t AND personAssignNext.enable_flag = 'Y'\n" +
            "\t AND position.enable_flag = 'Y'\n" +
            "\t AND position.position_id  = personAssignNext.position_id\n" +
            "\t AND nvl(personAssignNext.date_from, trunc(sysdate)) <= trunc(sysdate)\n" +
            "\t AND personAssignNext.delete_flag = 0\n" +
            "\t AND NOT EXISTS (select 1\n" +
            "\t\t\t\t\t\t\t\t\t\t from base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t  where 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t  and accessBasedata.access_type = '1'\n" +
            "\t\t\t\t\t\t\t\t\t\t  and accessBasedata.user_id = -10\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.org_id = personLevel.ou_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.subordinate_position_id = position.position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\tand accessBasedata.subordinate_person_id = personAssignNext.person_id)";

    //经销商全局用户权限 --> 存在权限表，不存在新数据列表，删除老数据
    public static final String QUERY_DISTRIBUTOR_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "SELECT accessBasedata.access_id accessId\n" +
            "\tFROM base_access_basedata accessBasedata\n" +
            " WHERE 1 = 1\n" +
            "\t AND accessBasedata.access_type = '2'\n" +
            "\t AND accessBasedata.user_id = -10\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_person_cust t1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_customer t2\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_warehouse_mapping t3\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE t1.enable_flag = 'Y'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.cust_account_id = t2.customer_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t2.STATUS = 'A'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t2.customer_number = t3.account_code\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.ou_id = t3.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t3.main_flag = 'YES'\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND t3.org_id = accessBasedata.org_id)\n";

    //经销商全局用户权限 --> 存在新数据列表，不存在权限表，插入新数据
    public static final String QUERY_DISTRIBUTOR_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "SELECT t1.ou_id orgId\n" +
            "\t\t\t,'2' accessType\n" +
            "\t\t\t,-10 userId\n" +
            "\t\t\t,0 personId\n" +
            "\t\t\t,-10 positionId\n" +
            "\t\t\t,t1.PERSON_ID subordinatePersonId\n" +
            "\t\t\t,t1.position_id subordinatePositionId\n" +
            "\t\t\t,t1.CUST_ACCOUNT_ID custAccountId\n" +
            "\t\t\t,t2.customer_number accountNumber\n" +
            "\t\t\t,t3.WAREHOUSE_CODE secondaryInventoryName\n" +
            "\t\t\t,t3.channel_code channelType\n" +
            "  FROM base_person_cust t1\n" +
            "\t\t\t,base_customer t2\n" +
            "\t\t\t,base_warehouse_mapping t3\n" +
            " WHERE t1.enable_flag = 'Y'\n" +
            "\t AND t1.cust_account_id = t2.customer_id\n" +
            "\t AND t2. STATUS = 'A'\n" +
            "\t AND t2.customer_number = t3.account_code\n" +
            "\t AND t1.ou_id = t3.org_id\n" +
            "\t AND t3.main_flag = 'YES'\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.access_type = '2'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.user_id = -10\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.org_id = t3.org_id)\n";

    //经销商员工用户权限 --> 存在权限表，不存在新数据列表，删除老数据
    public static final String QUERY_DISTRIBUTOR_PERSON_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "SELECT accessBasedata.access_id accessId\n" +
            "\tFROM base_access_basedata accessBasedata\n" +
            " WHERE 1 = 1\n" +
            "\t AND accessBasedata.access_type = '2'\n" +
            "\t AND accessBasedata.user_id <> - 10\n" +
            "\t AND accessBasedata.user_id = :userId\n" +
            "\t AND NOT EXISTS (select 1\n" +
            "\t\t\t\t\t\t\t\t\t\t from (select cab.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.access_type\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.user_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.subordinate_person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t ,cab.subordinate_position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\tfrom base_access_basedata cab\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t where cab.access_type = '1'\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t and cab.user_id <> -10\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t\t and cab.user_id = :userId\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t\t) t1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,base_person_cust t2\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,base_customer  t3\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t,base_warehouse_mapping t4\n" +
            "\t\t\t\t\t\t\t\t\t where t1.subordinate_person_id = t2.person_id\n" +
            "\t\t\t\t\t\t\t\t\t\t and t1.subordinate_position_id = t2.position_id\n" +
            "\t\t\t\t\t\t\t\t\t\t and t2.cust_account_id = t3.customer_id \n" +
            "\t\t\t\t\t\t\t\t\t\t and t3.status = 'A'\n" +
            "\t\t\t\t\t\t\t\t\t\t and t3.customer_number = t4.account_code\n" +
            "\t\t\t\t\t\t\t\t\t\t and t1.org_id = t4.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t and t4.main_flag = 'YES')\n";

    //经销商员工用户权限 --> 存在新数据列表，不存权限表，插入新数据
    public static final String QUERY_DISTRIBUTOR_PERSON_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "select t1.org_id orgId\n" +
            "      ,'2' accessType\n" +
            "      ,t1.user_id userId\n" +
            "      ,t1.person_id personId\n" +
            "      ,t1.position_id positionId\n" +
            "      ,t1.subordinate_person_id subordinatePersonId\n" +
            "      ,t1.subordinate_position_id subordinatePositionId\n" +
            "      ,t2.cust_account_id custAccountId\n" +
            "      ,t3.customer_number accountNumber\n" +
            "      ,t4.warehouse_code secondaryInventoryName\n" +
            "      ,t4.channel_code channelType\n" +
            "  from (select cab.org_id,\n" +
            "               cab.access_type,\n" +
            "               cab.user_id,\n" +
            "               cab.person_id,\n" +
            "               cab.position_id,\n" +
            "               cab.subordinate_person_id,\n" +
            "               cab.subordinate_position_id\n" +
            "          from base_access_basedata cab\n" +
            "         where cab.access_type = '1'\n" +
            "           and cab.user_id <> -10\n" +
            "           and cab.user_id = :userId \n" +
            "\t\t\t\t) t1\n" +
            "\t\t\t,base_person_cust t2\n" +
            "\t\t\t,base_customer  t3\n" +
            "\t\t\t,base_warehouse_mapping t4\n" +
            " where t1.subordinate_person_id = t2.person_id\n" +
            "\t and t1.subordinate_position_id = t2.position_id\n" +
            "\t and t2.cust_account_id = t3.customer_id \n" +
            "\t and t3.status = 'A'\n" +
            " \t and t3.customer_number = t4.account_code\n" +
            "\t and t1.org_id = t4.org_id\n" +
            "\t and t4.main_flag = 'YES'\n" +
            "\t and NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.access_type = '2'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.user_id <> - 10\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.user_id = :userId)\n";

    //经销商用户权限 --> 存在权限表，不存在新数据列表，删除老数据
    public static final String QUERY_DISTRIBUTOR_USER_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "SELECT accessBasedata.access_id accessId\n" +
            "\tFROM base_access_basedata accessBasedata\n" +
            " WHERE 1 = 1\n" +
            "\t AND accessBasedata.access_type = '2'\n" +
            "\t AND accessBasedata.user_id <> - 10\n" +
            "\t AND accessBasedata.user_id = :userId\n" +
            "\t AND ISNULL(accessBasedata.person_id)\n" +
            "\t AND ISNULL(accessBasedata.position_id)\n" +
            "\t AND ISNULL(accessBasedata.subordinate_person_id)\n" +
            "\t AND ISNULL(accessBasedata.subordinate_position_id)\n" +
            "\t AND NOT EXISTS (SELECT 1   \n" +
            "\t\t\t\t\t\t\t\t\t\t from base_users t1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_customer t2\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_warehouse_mapping t3\n" +
            "\t\t\t\t\t\t\t\t\t  where t1.user_type = '30'\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t1.start_date <=sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  and nvl(end_date, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t1.cust_account_id = t2.customer_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t2.status = 'A'\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t2.customer_number = t3.account_code\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t3.main_flag = 'YES'\n" +
            "\t\t\t\t\t\t\t\t\t    and t3.org_id = accessBasedata.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  and t1.user_id = accessBasedata.user_id)\n";

    //经销商用户权限 --> 存在新数据列表，不存在权限表，插入新数据
    public static final String QUERY_DISTRIBUTOR_USER_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "SELECT t3.org_id orgId\n" +
            "      ,'2' accessType\n" +
            "      ,t1.user_id userId \n" +
            "      ,t2.customer_id custAccountId\n" +
            "      ,t2.customer_number accountNumber  \n" +
            "\t\t\t,t3.warehouse_code secondaryInventoryName\n" +
            "      ,t3.channel_code channelType     \n" +
            "  from base_users t1\n" +
            "\t\t\t,base_customer t2\n" +
            "\t\t\t,base_warehouse_mapping t3\n" +
            " where t1.user_type = '30'\n" +
            "\t and t1.start_date <=sysdate\n" +
            "\t and nvl(end_date,DATE_ADD(sysdate,INTERVAL 1 DAY)) >sysdate\n" +
            "\t and t1.cust_account_id = t2.customer_id\n" +
            "\t and t2.status = 'A'\n" +
            "\t and t2.customer_number = t3.account_code\n" +
            "\t and t3.main_flag = 'YES'\n" +
            "\t and t1.user_id = :userId\n" +
            "\t and NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\tFROM base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t AND accessBasedata.access_type = '2'\n" +
            "\t\t\t\t\t\t\t\t\t\t AND accessBasedata.user_id <> - 10\n" +
            "\t\t\t\t\t\t\t\t\t\t AND ISNULL(accessBasedata.person_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t AND ISNULL(accessBasedata.position_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t AND ISNULL(accessBasedata.subordinate_person_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t AND ISNULL(accessBasedata.subordinate_position_id)\n" +
            "\t\t\t\t\t\t\t\t\t\t AND accessBasedata.org_id = t3.org_id\n" +
            "\t\t\t\t\t\t\t\t\t\t AND accessBasedata.user_id = t1.user_id)\n";

    //经销商访问子库权限 --> 存在权限表，不存在新数据列表，删除老数据
    public static final String QUERY_DEALER_SUB_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "SELECT accessBasedata.access_id accessId\n" +
            "\tFROM base_access_basedata accessBasedata\n" +
            " WHERE 1 = 1\n" +
            "\t AND accessBasedata.access_type = '4'\n" +
            "\t AND accessBasedata.user_id = :userId\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_users t1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_customer t2\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_warehouse_mapping t3\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE t1.user_type = '30'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.user_id = :userId\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND t1.start_date <sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t\tAND nvl(t1.end_date, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.cust_account_id = t2.customer_id\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t2. STATUS = 'A'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t2.customer_number = t3.account_code\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t3.start_date_active <sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND nvl(t3.end_date_active, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate)";

    //经销商访问子库权限 --> 存在新数据列表，不存在权限表，插入新数据
    public static final String QUERY_DEALER_SUB_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "'SELECT t3.org_id orgId\n" +
            "\t\t\t,'4' accessType\n" +
            "\t\t\t,t1.user_id userId\n" +
            "\t\t\t,t2.customer_id custAccountId\n" +
            "\t\t\t,t2.customer_number accountNumber\n" +
            "\t\t\t,t3.warehouse_code secondaryInventoryName\n" +
            "\t\t\t,t3.channel_code channelType\n" +
            "  FROM base_users t1\n" +
            "\t\t\t,base_customer t2\n" +
            "\t\t\t,base_warehouse_mapping t3\n" +
            " WHERE t1.user_type = '30'\n" +
            "\t AND t1.user_id = :userId\n" +
            "\t AND t1.start_date <sysdate\n" +
            "\t AND nvl(t1.end_date, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate\n" +
            "\t AND t1.cust_account_id = t2.customer_id\n" +
            "\t AND t2. STATUS = 'A'\n" +
            "\t AND t2.customer_number = t3.account_code\n" +
            "\t AND t3.start_date_active <sysdate\n" +
            "\t AND nvl(t3.end_date_active, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.access_type = '4'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.user_id = :userId)";

    //门店、分销商访问子库权限 --> 存在权限表，不存在新数据列表，删除老数据
    public static final String QUERY_STORE_SUB_EXIST_ACCESS_NOT_EXIST_NEW_LIST_SQL = "SELECT accessBasedata.access_id accessId\n" +
            "\tFROM base_access_basedata accessBasedata\n" +
            " WHERE 1 = 1\n" +
            "\t AND accessBasedata.access_type = '4'\n" +
            "\t AND accessBasedata.user_id = :userId\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_users t1\n" +
            "\t\t\t\t\t\t\t\t\t\t\t\t ,base_warehouse_mapping t2\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE t1.user_type = '40'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.user_id = :userId\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.start_date <sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND nvl(t1.end_date, DATE_ADD(sysdate,INTERVAL 1 DAY)) >sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t1.user_name = t2.warehouse_code\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND t2.start_date_active <sysdate\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND nvl(t2.end_date_active, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate)";

    //门店、分销商访问子库权限 --> 存在新数据列表，不存在权限表，插入新数据
    public static final String QUERY_STORE_SUB_EXIST_NEW_LIST_NOT_EXIST_ACCESS_SQL = "SELECT t2.org_id orgId\n" +
            "\t\t\t,'4' accessType\n" +
            "\t\t\t,t1.user_id userId\n" +
            "\t\t\t,t1.cust_account_id custAccountId\n" +
            "\t\t\t,t2.account_code accountNumber\n" +
            "\t\t\t,t2.warehouse_code secondaryInventoryName\n" +
            "\t\t\t,t2.channel_code channelType\n" +
            "  FROM base_users t1\n" +
            "\t\t\t,base_warehouse_mapping t2\n" +
            " WHERE t1.user_type = '40'\n" +
            "\t AND t1.user_id = :userId\n" +
            "\t and t1.start_date <sysdate\n" +
            "\t and nvl(t1.end_date, DATE_ADD(sysdate,INTERVAL 1 DAY)) >sysdate\n" +
            "\t AND t1.user_name = t2.warehouse_code\n" +
            "\t AND t2.start_date_active <sysdate\n" +
            "\t AND nvl(t2.end_date_active, DATE_ADD(sysdate, INTERVAL 1 DAY)) >sysdate\n" +
            "\t AND NOT EXISTS (SELECT 1\n" +
            "\t\t\t\t\t\t\t\t\t\t FROM base_access_basedata accessBasedata\n" +
            "\t\t\t\t\t\t\t\t\t  WHERE 1 = 1\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.access_type = '4'\n" +
            "\t\t\t\t\t\t\t\t\t\t  AND accessBasedata.user_id = :userId)";

    private Integer accessId; //主键ID
    private Integer orgId; //事业部
    private String accessType; //访问类型
    private Integer userId; //用户ID
    private String userType;
    private Integer positionId; //职位
    private Integer subordinatePersonId; //下级人员ID
    private Integer subordinatePositionId; //下级职位ID
    private Integer custAccountId; //经销商ID
    private String accountNumber; //经销商账号
    private String secondaryInventoryName; //子库名称
    private String channelType; //渠道类型
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate; //创建时间
    private Integer createdBy; //创建人
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateDate; //更新时间
    private Integer lastUpdatedBy; //更新人
    private Integer lastUpdateLogin; //最后更新登录ID
    private Integer deleteFlag; //删除标识
    private Integer versionNum; //版本号
    private Integer oaUserId; //OA用户ID
    private Integer personId; //人员ID
    private Integer operatorUserId;

    private String batchCode; //批次号（人员权限数据临时表中字段）

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getSubordinatePersonId() {
        return subordinatePersonId;
    }

    public void setSubordinatePersonId(Integer subordinatePersonId) {
        this.subordinatePersonId = subordinatePersonId;
    }

    public Integer getSubordinatePositionId() {
        return subordinatePositionId;
    }

    public void setSubordinatePositionId(Integer subordinatePositionId) {
        this.subordinatePositionId = subordinatePositionId;
    }

    public Integer getCustAccountId() {
        return custAccountId;
    }

    public void setCustAccountId(Integer custAccountId) {
        this.custAccountId = custAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecondaryInventoryName() {
        return secondaryInventoryName;
    }

    public void setSecondaryInventoryName(String secondaryInventoryName) {
        this.secondaryInventoryName = secondaryInventoryName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getLastUpdateLogin() {
        return lastUpdateLogin;
    }

    public void setLastUpdateLogin(Integer lastUpdateLogin) {
        this.lastUpdateLogin = lastUpdateLogin;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    public Integer getOaUserId() {
        return oaUserId;
    }

    public void setOaUserId(Integer oaUserId) {
        this.oaUserId = oaUserId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(Integer operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
}
