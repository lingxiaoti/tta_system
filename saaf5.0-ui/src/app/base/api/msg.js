/*
 * API 拆分
 * 基础服务 \ 消息
 * */
define(['webconfig'],function (webconfig) {
    //var webconfig.url.baseServer = serviceURL + 'baseServer/' ; // 基础服务\ 主服务
    return {

      changeResp: webconfig.url.baseServer + 'baseAccreditService/changeResp', // 切换职责

    }
})