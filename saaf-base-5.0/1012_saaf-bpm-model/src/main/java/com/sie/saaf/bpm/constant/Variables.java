package com.sie.saaf.bpm.constant;

/**
 * 流程初始变量
 */
public enum Variables {
    //流程发起时传入
    startPositionId,//流程提交人职位ID
    startOrgId,//流程提交人组织ID
    startResponsibilityId,//流程提交人职责ID
    startUserType,//流程提交人用户类型
    startRoleType,//流程提交人角色类型
    startCustAccountId,//流程提交经销商ID
    applyPersonId,//流程主体员工ID
    applyPositionId,//流程主体职位ID

    //流程中心自动注入（当applyPersonId不为空时，取applyPersonId相关的信息）：
    startUserId,//流程提交人ID
    startPersonId,//流程提交人员工ID
    startUserName,//流程提交人用户名
    startPositionName,//流程提交人职位名称
    startDepartmentId,//流程提交人部门ID
    startDepartmentCode,//流程提交人部门编码
    startDepartmentName,//流程提交人部门名称
    startDepartmentType,//是否省区，10-部门，20-省区
    startJobId,//流程提交人职务ID
    startJobCode,//流程提交人职务编码
    startJobName,//流程提交人职务名称
    startChannel,//流程提交人渠道
}
