/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['webconfig'], function (webconfig) {

    return {
        /*流程引擎*/
        // 1.1.	流程角色查询
        processFindRoles: webconfig.url.processServer + 'bpmRoleService/findRoles',
        // 1.2.	流程角色保存
        processRoleSave: webconfig.url.processServer + 'bpmRoleService/save',
        // 1.3.	流程角色删除
        processRoleDelete: webconfig.url.processServer + 'bpmRoleService/delete',

        // 2.1.	流程分类查询
        processListCategories: webconfig.url.processServer + 'bpmCategoryService/findCategories',
        // 2.2.	流程分类保存
        processCategoriesSave: webconfig.url.processServer + 'bpmCategoryService/save',
        // 2.3.	流程分类删除
        processCategoriesDelete: webconfig.url.processServer + 'bpmCategoryService/delete',
        // 3.1.1.	流程模型查询   与 4.1.	可发起流程查询 一样
        //processFindModels: webconfig.url.processServer + 'bpmModelService/findModels',
        // 3.1.1.	流程模型保存
        processModelsSave: webconfig.url.processServer + 'bpmModelService/save',
        // 3.1.1.	流程模型删除
        processModelsDelete: webconfig.url.processServer + 'bpmModelService/delete',
        // 3.1.4.	流程模型导出
        processModelsExport: webconfig.url.processServer + 'bpmModelService/export',
        //3.1.5.	流程在线设计
        processOnline: webconfig.url.processServer + 'bpmModelService/export',
        //3.1.6.	流程发布
        processDeploy: webconfig.url.processServer + 'bpmModelService/deploy',
        //3.2.1.	流程任务节点查询
        processFindUserTasks: webconfig.url.processServer + 'bpmModelService/findUserTasks',
        //3.2.1.	流程任务节点查询 查询下个节点后的所有节点
        processfindFollowUserTasks: webconfig.url.processServer + 'bpmModelService/findFollowUserTasks',
        // 3.2.2.	流程设置查询
        processFindTaskConfig: webconfig.url.processServer + 'bpmTaskConfigService/findTaskConfig',
        //  3.2.3.	流程设置保存
        processTaskConfigSave: webconfig.url.processServer + 'bpmTaskConfigService/save',
        // 3.2.4.	流程设置删除
        processTaskConfigDelete: webconfig.url.processServer + 'bpmTaskConfigService/delete',
        // 3.3.1.	流程版本查询
        processFindDefinitions: webconfig.url.processServer + 'bpmProcessService/findDefinitions',
        // 3.3.2.	流程版本挂起
        processSuspend: webconfig.url.processServer + 'bpmProcessService/suspend',
        // 3.3.3.	流程版本激活
        processActivate: webconfig.url.processServer + 'bpmProcessService/activate',
        // 3.3.4.	流程版本删除
        processDelete: webconfig.url.processServer + 'bpmProcessService/delete',
        // 3.3.5.	流程版本导出
        processExport: webconfig.url.processServer + 'bpmProcessService/export',
        // 4.1.	可发起流程查询   与 一样  3.1.1.	流程模型查询
        processFindModels: webconfig.url.processServer + 'bpmModelService/findModels',
        //4.2.	流程发起表单查询
        processGetStartUrl: webconfig.url.processServer + 'bpmProcessService/getStartUrl',
        //4.3.	流程发起记录查询
        processFindBpmLists: webconfig.url.processServer + 'bpmListService/findBpmLists',
        //4.4.	流程发起详细信息查询
        processGet: webconfig.url.processServer + 'bpmListService/get',
        //4.5.	流程发起保存
        processSave: webconfig.url.processServer + 'bpmListService/save',
        //4.6.	流程发起

        // 4.7.	选人任务节点查询(发起/任务办理)
        processFindAssigneeUserTasks: webconfig.url.processServer + 'bpmProcessService/findAssigneeUserTasks',

        // 4.6.	任务节点办理人查询
        processFindTaskUsers: webconfig.url.processServer + 'bpmTaskService/findTaskUsers',

        processStart: webconfig.url.processServer + 'bpmProcessService/start',
        //5.1.	流程待办任务查询
        processFindTodoTasks: webconfig.url.processServer + 'bpmTaskService/findTodoTasks',
        // 5.2.	流程任务办理历史记录查询
        processFindHistoricTasks: webconfig.url.processServer + 'bpmHistoryService/findHistoricTasks',
        // 5.3.	当前活动的任务查询
        processFindActiveTasks: webconfig.url.processServer + 'bpmTaskService/findActiveTasks',
        // 5.4.	流程已办理的任务查询
        processFindHistoricActivities: webconfig.url.processServer + 'bpmHistoryService/findHistoricActivities',
        // 5.5.	任务详细信息查询
        processTaskDetail: webconfig.url.processServer + 'bpmTaskService/get',
        //5.6.	任务办理
        processTaskComplete: webconfig.url.processServer + 'bpmTaskService/complete',
        // 5.6.	任务办理表单地址查询
        processGetTaskUrl: webconfig.url.processServer + 'bpmProcessService/getTaskUrl',

        //  7.1.	流程任务代办设置查询
        findDelegateConfig: webconfig.url.processServer + 'bpmTaskDelegateConfigService/findDelegateConfig',
        //7.4.	流程任务代办查询
        findDelegates: webconfig.url.processServer + 'bpmTaskDelegateService/findDelegates',
        //  7.6.	流程任务代办批量添加
        processEntrustApproval: webconfig.url.processServer + 'bpmTaskDelegateService/save',
        // 流程任务批量办理
        processBatchComplete: webconfig.url.processServer + 'bpmTaskService/batchComplete',
        // 待阅列表
        findNotifyTasks: webconfig.url.processServer + 'bpmNotifyTaskService/findNotifyTasks',
        // 催办、发起沟通
        processCommunicateSave: webconfig.url.processServer + 'bpmCommunicateService/save',
        // 流程抄送
        processNotifyTaskSave: webconfig.url.processServer + 'bpmNotifyTaskService/save',
        // 添加助审任务节点
        processAddSubTask: webconfig.url.processServer + 'bpmTaskService/addSubTask',
        //  流程撤回
        processRevoke: webconfig.url.processServer + 'bpmTaskService/revoke',
        // 获取当前流程是否可以进行撤回
        processRevokeStatus: webconfig.url.processServer + '/bpmTaskService/getRevokeStatus',
        // 待阅 阅读
        processNotifyTaskStatus: webconfig.url.processServer + 'bpmNotifyTaskService/updateStatus',

        // 流程类型分类及实例 父子结构，用于显示树
        findCategoriesModels: webconfig.url.processServer + 'bpmCategoryService/findCategories',

        // 任务代办设置保存
        processDelegateConfigSave: webconfig.url.processServer + 'bpmTaskDelegateConfigService/save',

        // 任务代办设置 删除
        processDelegateDel:  webconfig.url.processServer + 'bpmTaskDelegateConfigService/delete',

        //  任务代办设置保存 状态 更新
        processDelegateConfigStatus: webconfig.url.processServer + 'bpmTaskDelegateConfigService/updateStatus',

        // 催办设置列表
        processFindUrgeConfig: webconfig.url.processServer + 'bpmTaskUrgeConfigService/findUrgeConfig',

        // 催办保存 bpmTaskUrgeConfigService/save
        processTaskUrgeConfigSave: webconfig.url.processServer + 'bpmTaskUrgeConfigService/save',

        // 催办删除 bpmTaskUrgeConfigService/delete
        processTaskUrgeConfigDelete: webconfig.url.processServer + 'bpmTaskUrgeConfigService/delete',
        // 催办删除 bpmTaskUrgeConfigService/delete
        processTaskUrgeConfigUpdate: webconfig.url.processServer + 'bpmTaskUrgeConfigService/updateStatus',

        // 流程终止  bpmProcessService/stop
        processStop: webconfig.url.processServer + 'bpmProcessService/stop',
        //  流程任务转办
        processTaskTransfer: webconfig.url.processServer + 'bpmTaskService/transfer',

        //  查询已定义的流程详细信息
        processModelDetail: webconfig.url.processServer + 'bpmModelService/get',
        // 流程导出 请用get 方式
        samProcessExport : webconfig.url.processServer + 'bpmModelService/export',
        // 流程导入
        samProcessImport : webconfig.url.processServer + 'bpmModelService/import',
        // 流程复制
        samProcessCopy : webconfig.url.processServer + 'bpmModelService/copy',

        // 批量替换流程角色审批人
        samUpdateRoleProcessers:webconfig.url.processServer + 'bpmRoleService/updateRoleProcessers',
        // 批量替换流程列表审批人
        samUpdateTaskConfigProcessers:webconfig.url.processServer + 'bpmTaskConfigService/updateTaskConfigProcessers'

    }
})