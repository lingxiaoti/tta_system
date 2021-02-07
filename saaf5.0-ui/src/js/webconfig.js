/**
 * Created by houxingzhang on 2018-03-10.
 */
'use strict';

define(function () {

    //Employee portal系统跳转到TTA系统的链接
    window.employee_portal_url = "http://localhost:63342/saaf5.0-ui/src/index.html#/base/login?empno=EA90BB1AF6417BD12C11DCE936BE0489" +
        "&Language=zh-CN&key=be19644a&check=60638AFDB5C2CBCC8F8A587C65746EF58F1E06EB89DA8964";
    window.isEmployeePortal = 1;

    var sessionTime = 60; //系统会话时长
    var exportPageRows = 9999; //表格导出最大记录数
    var apiToggle = false;  // 数据读取开关  true 是读json数据源，false为读取后台服务 ，用于本地无数据时测试使用，小心使用　★★★★★★★★★
    //window.testServerURL = 'http://e.ausnutria.com'; // 为兼容 ueditor路径 ，采用全局变量模式
    //window.testServerURL = '//192.168.195.121'; // UAT
    window.testServerURL = '//127.0.0.1';//测试环境
    //window.testServerURL = '//192.168.27.92';//测试环境
    var host = '//' + window.location.host;
    var host3 = '';
    var domain = ['localhost', '127.0.0.1', '192.168.99.37'];// 如果当前域名为此列表中的域名，则使用预设好的服务IP地址。否则使用当前运行的ip或域名
    var h = window.location.hostname;
    host = domain.indexOf(h) === -1 ? host : window.testServerURL;
    host="http://127.0.0.1:8089";
    //host="http://192.168.27.92:8089";

    var hostta="http://127.0.0.1:8089";
    //var hostta="http://192.168.27.92:8089";

    host3="http://127.0.0.1:8089";
    //host3="http://192.168.27.92:8089"
    // host="http://172.16.10.70/baseservice";//正式环境
    // host="http://172.16.0.156:2211/";

    /*var host2="http://127.0.0.1:2215/api";*/
    var host2="http://127.0.0.1:8089";
    //var host2="http://192.168.27.92:8089";

    var hostExp="http://127.0.0.1:2213";
    //var hostExp="http://192.168.27.92:2213";

    var md5Config = {
        prefix:'',//md5加密的前缀字符串
        suffix:'lo0.1l@g9v#' //md5加密的后缀字符串
    };

    // 企业列表
    var compList = [
        {
            name: '企业管理平台',
            domain: 'e.ausnutria.com',
            apiUrl: host,
            imagePath: 'sie',
        }

    ];
    // APP应用列表配置
    var appList = [
        {
            appName: 'BASE',
            appTitle: 'SAAF后台管理'
        },
        {
            appName: 'WMS',
            appTitle: 'WMS管理'
        }
    ];

    var jumpUrl = "http://wtccn-vm-sdst/portal/login.aspx";//默认调整EP测试环境
    switch (h) {
        case "10.82.24.107":
            jumpUrl = "http://wtccn-vm-sdst/portal/login.aspx";
            break;
        case "10.82.31.206":
            //jumpUrl = "http://wtccn-gz-ept/Portal/Login.aspx";//EP正式环境
            jumpUrl = "http://wtccn-watsonsportal:2020/wui/index.html#/?logintype=1&_key=7qlz7s";//EP正式环境
            break;
        case "localhost":
        case "127.0.0.1":
            jumpUrl = "http://wtccn-vm-sdst/portal/login.aspx";
    }
    var defaultApp = 'BASE';// 默认进入应用
    var SSOName = '';// 单点登录 首页的 名称
    // var hostServer = host + '/api';
    var hostServer = host ;
    var hosttaServer = host ;
    var msgServer = host3;
    var hostServerExp = hostExp ;
    var hostServer2 = host2 ;
    /*    sessionStorage.processServer = hostServer + '/bpmServer/'; // 流程设计 服务*/
    // sessionStorage.processServer = "http://127.0.0.1:2212" + '/'; // 流程设计 服务
    window.processServer = 'http://127.0.0.1';
    // window.processServer = 'http://172.16.0.156';
    //sessionStorage.processServer = window.processServer+ ":2212" + '/bpmServer/'; // 流程设计 服务
    sessionStorage.processServer = window.processServer+ ":8089" + '/api/bpmServer/'; // 流程设计 服务

    // window.baseServerURL = host + '/baseServer/';// 注册全局变量　基础服务SAAF
    window.baseServerURL = host + '/';// 注册全局变量　基础服务SAAF
    window.schedule = host + '/schedule/';// 注册全局变量　基础服务SAAF
    window.schedule ='http://127.0.0.1:2215/';
    window.bpmServerURL=window.baseServerURL;
    //window.exportServerURL = host + '/exportServer/';
    return {
        url: {
            service: hostServer + '/',//  当前的服务接口地址
            baseServer: hostServer + '/api/baseServer/', //基础服务SAAF
            ttaServer: hosttaServer + '/api/ttaServer/', //tta
            //ttaServer: hostta + '/ttaServer/', //tta
            messageSever: msgServer + '/api/messageServer/', //消息服务SAAF
            // baseServer: hostServer + '/', //基础服务SAAF
            pdaServer: hostServer + '/pdaServer/', //  　基础服务SAAF
            pdapoolServer: hostServer + '/pdapoolServer/', //  基础服务SAAF
            pdainvServer: hostServer + '/pdaServer/', //  　基础服务SAAF
            outStorgeTranServer: hostServer + '/outStorgeTranServer/', //  　基础服务SAAF
            /*scheduleServer: hostServer2 + '/schedule/', //  　基础服务SAAF*/
            scheduleServer: hostServer2 + '/api/schedule/', //  　基础服务SAAF
            processServer: sessionStorage.processServer,   // 流程设计 服务
            //exportServer: hostServerExp + '/exportServer/' ,//导出
            exportServer: hostServerExp + '/' ,//导出
            equotationServer: hosttaServer + '/api/equotationServer/', //equ
            vmiServer: hostServer + '/api/vmiServer/vmi/', // vmi服务
            oaServer:hostServerExp + '/oaServer/' // OA服务
        },
        host: host, // 当前主机名
        SSOName: SSOName,
        sessionTime: sessionTime,
        apiToggle: apiToggle,
        exportPageRows: exportPageRows,
        compList: compList,
        appList: appList,
        md5Config: md5Config,
        defaultApp: defaultApp, // 默认进入系统
        jumpUrl:jumpUrl
    };
});