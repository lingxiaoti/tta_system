/**
 * Created by dengdunxin on 2018/3/7.
 */
define(['webconfig'],function (webconfig) {

    return {


        //调度模块 ---------------------------------------------------------------------
        //job//////////////////////////////////////////////////////////////////
        //删除job
        jobDelete: webconfig.url.scheduleServer + "jobServices/deleteJob",
        //编辑（更新）job
        jobEdit: webconfig.url.scheduleServer + "jobServices/updateJob",
        //新增job
        saveJob: webconfig.url.scheduleServer + "jobServices/saveJob",
        //查询job
        jobFind: webconfig.url.scheduleServer + "jobServices/findJobs",
        //job参数///////////////////////////////////////////////////////////////
        //查询参数
        jobParamFind: webconfig.url.scheduleServer + "jobParameterServices/findJobParameters",
        //新增参数
        saveJobParam: webconfig.url.scheduleServer + "jobParameterServices/saveJobParameter",
        //编辑（更新）参数
        jobParamEdit: webconfig.url.scheduleServer+"jobParameterServices/updateJobParameter",
        //删除参数
        removeJobParam: webconfig.url.scheduleServer + "jobParameterServices/deleteJobParameter",
        //查询调度组织分配
        queryJobInstTree: webconfig.url.scheduleServer + 'scheduleJobAccessOrgsService/findJobsInstTree',
        //保存调度组织分配
        saveJobInst: webconfig.url.scheduleServer + 'scheduleJobAccessOrgsService/saveJobsInst',
        //查询调度职责分配
        queryJobRespList: webconfig.url.scheduleServer + 'scheduleJobRespService/findJobRespAll',
        //查询调度未分配的职责
        findRemainderJobResp: webconfig.url.scheduleServer + 'scheduleJobRespService/findRemainderJobResp',
        //保存分配给调度的职责
        saveJobResp: webconfig.url.scheduleServer + 'scheduleJobRespService/saveJobResp',
        //删除调度职责
        deleteJobResp: webconfig.url.scheduleServer + 'scheduleJobRespService/deleteJobResp',
        //请求//////////////////////////////////////////////////////////////////
        //查询请求
        requestFind: webconfig.url.scheduleServer + "scheduleServices/findRequests",
        //提交 新请求
        saveQequest: webconfig.url.scheduleServer + "scheduleServices/saveRequest",
        //查询请求执行日志
        requestFindLog: webconfig.url.scheduleServer + "scheduleServices/findRequestLog",
        //删除请求
        requestDelete: webconfig.url.scheduleServer + "scheduleServices/deleteSchedule",
        //删除请求
        requestDeleteMulti: webconfig.url.scheduleServer + "scheduleServices/deleteScheduleBatch",
        //（启动）提交 ‘已执行完毕’ 或 ‘已卸载’ 的请求（根据isRedo参数值来判断，值为‘YES’表示redoSubmit）
        requestRedoSubmit: webconfig.url.scheduleServer + "scheduleServices/saveRequest",
        //启动 处于 暂停状态的请求
        requestResume: webconfig.url.scheduleServer + "scheduleServices/resumeRequest",
        //暂停请求
        requestPause: webconfig.url.scheduleServer + "scheduleServices/pauseRequest",
        //取消请求
        requestCancel: webconfig.url.scheduleServer + "scheduleServices/cancelRequest",

        // 调度错误监控
        schedulingError: webconfig.url.scheduleServer + "scheduleErrorServices/findSchedulesErrors",
        // 调度错误监控 详情
        schedulingErrorDetail: webconfig.url.scheduleServer + "scheduleErrorServices/findSchedulesErrorLog",

        // ---------------------------调度模块------------------------------------------

    }
})