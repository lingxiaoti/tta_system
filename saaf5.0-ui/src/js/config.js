/**
 * Created by houxingzhang on 2016-09-05.
 */
var config={
    paths:{
        'es5':'../plugin/es5-shim.min',
        'jquery': '../plugin/jquery-3.3.1.min',
        'GooFlow': '../plugin/GooFlow/plugin/GooFlow.min',
        // 'jquery': '../plugin/jquery',
        'bootstrap': '../plugin/bootstrap.min',
        'angular': '../plugin/angular',

        //'angular': '../plugin/angular.ie.min',
        'angular-ui-router': ['../plugin/angular-ui-router.min', '//cdn.staticfile.org/angular-ui-router/0.2.8/angular-ui-router.min'],
        'angular-async-loader': '../plugin/angular-async-loader.min', //按需加载核心文件
        'angular-require': '../plugin/angular-require.min', //按需加载　
        'app': 'app', //入口文件
        'SAAF.URLAPI': 'api/SAAF.URLAPI',
        'route': 'routes/view',//路由配置
        'SAAF.filters': 'filter/SAAF.filters',// 过滤器
        'SAAF.Directives': 'directives/SAAF.Directives',//指令
        'SAAF.DirectivesForCommon': 'directives/SAAF.DirectivesForCommon',//指令
        'EMS.ControlDirectives': 'directives/EMS.Control.Directives',//指令
        'SIE.Service': 'waiter/SIE.Services',// SIE 服务,
        'http.Service': 'waiter/http.Services',// SIE 服务,
        'iframe.Services': 'waiter/iframe.Services',//父页面获取子页面属性判断是否调用子页面方法
        'bw.paging':'../plugin/paging', // 分页
        //插件类
        'ngDialog': '../plugin/ng-dialog/ngDialog.min',//弹出层
        'sweetalert': '../plugin/sweetalert/sweetalert.min',//　对话框
        'jquery-nicescrool': '../plugin/angular-nicescroll/jquery.nicescroll.min',//JQ 滚动条
        'angular-nicescroll': '../plugin/angular-nicescroll/angular-nicescroll.min', // 滚动条

        'datetimepicker': '../plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN',  //　日期控件
        'bootstrap-colorpicker': '../plugin/bootstrap-colorpicker/bootstrap-colorpicker.min',  //　颜色选择器
        'ng.ueditor':['../plugin/ueditor/ueditor.all'],
        'ueditor_config':['../plugin/ueditor/ueditor_config'],
        'ng_ueditor':['../plugin/ueditor/angular-ueditor.min'],
        'ztree': '../plugin/ztree/jquery.ztree.all',
        'autoValidate': '../plugin/autoValidate/jcs-auto-validate',//自动校验   该功能未使用
        'angularFileUpload': '../plugin/angular-file-upload-master/dist/angular-file-upload',//文件上传
        'jquery-barcode': '../plugin/barcode/jquery-barcode.min',
        // 'XLSX': '../plugin/xlsx/xlsx.min',  ZL 修改导入插件报错
        'XLSX': '../plugin/xlsx/xlsx.full.min',
        'jspdfAA':'../plugin/AA_TEST/jspdf.min',
        'canvasAA':'../plugin/AA_TEST/html2canvas',
        'v':'../plugin/AA_TEST/v',
        'fixed':"../plugin//fixed-table",
        'fixedReport':"../plugin/fixed-report-table",
        'jszip': '../plugin/xlsx/jszip.min',
        'contextify': '../plugin/jquery.contextify'
        ,'echarts':'../plugin/echarts/echarts.min'
        ,'chinaContourMap':'../plugin/echarts/china-contour'
        ,'chinaMap':'../plugin/echarts/china'
        ,'worldMap':'../plugin/echarts/world'
        ,'bmap':'../plugin/echarts/bmap.min',
        'sortable':'../plugin/sortable/Sortable.min',
        'select2':'../plugin/select2/select2.min',
        'pinyin':'../plugin/pinyin',
        'jqueryUi':'../plugin/jquery-ui/jquery-ui.min',
        'colResizable':'../plugin/colResizable-1.6.min',
        'jqprint':'../plugin/jQuery.print',
        'jqfixedHeaderTable': '../plugin/jquery.fixedheadertable.min',
        'md5':'../plugin/md5',
        'imgPreview':'../plugin/imgPreview/imgPreview',
        'ajaxfileupload': '../plugin/ajaxfileupload',
        'ngWebsocket':'../plugin/ng-webSocket/ng-webSocket',
        'ws':'../plugin/ng-webSocket/web_socket',
        'telConfig':'telConfig',
        'hilink_softphone':'hilink_softphone',
        'moment':'../plugin/moment',
        'layui':'../plugin/layui/layui.all',
        'multiple':'../plugin/multiple/fSelect'



    },
    shim:{
        'select2':{
            deps: ['jquery', 'angular'],
            exports: 'select2'
        },
        'sortable': {
            exports: 'sortable'
        },
        'bootstrap': {
            deps: ['jquery']
        },
        'GooFlow': {
            deps: ['jquery',
                'css!../plugin/GooFlow/plugin/GooFlow.min.css',
                'css!../plugin/GooFlow/fontawesome/css/font-awesome.css'],
            exports: 'GooFlow'
        },
        'jqueryUi': {
            deps: ['jquery',
                'css!../plugin/jquery-ui/jquery-ui.min.css'],
            exports: 'jqueryUi'
        },
        'angular': {
            deps: ['jquery', 'bootstrap'],
            exports: 'angular'
        },
        'angular-ui-router': {
            deps: ['angular'],
            exports: 'angular-ui-router'
        },
        'angular-require': {
            deps: ['angular'],
            exports: 'angular-require'
        },
        'angular-async-loader': {
            deps: ['angular'],
            exports: 'angular-async-loader'
        },
        'bw.paging':{
            deps: ['angular']
        },
        'ngDialog': ['css!../plugin/ng-dialog/ngDialog.min', 'css!../plugin/ng-dialog/ngDialog-theme-default'], //加载 依赖的css
        'sweetalert': ['css!../plugin/sweetalert/sweetalert'],
        'angular-nicescroll': {deps: ['jquery','jquery-nicescrool', 'angular']}, //　滚动条的依赖

        'datetimepicker': {
            deps: ['jquery',
                './plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.js',
                'css!../plugin/bootstrap-datetimepicker/bootstrap-datetimepicker.min'],
            exports: 'datetimepicker'
        },
        'bootstrap-colorpicker': {
            deps:  ['jquery', 'css!../plugin/bootstrap-colorpicker/bootstrap-colorpicker.min'],
            exports: 'bootstrap-colorpicker'
        },
        'ng.ueditor': {
            deps: ['ueditor_config', 'ng_ueditor']
        },
        'ztree': {
            deps: ['jquery', 'css!../plugin/ztree/zTreeStyle'],
            exports: 'ztree'
        },

        'angular-file-upload': ['jquery', 'angular'],
        'jquery-barcode': ['jquery'],
        'XLSX': {
            deps: ['../plugin/xlsx/iemagic', 'jszip', '../plugin/xlsx/ods'],
            exports: 'XLSX'
        },
        'imgPreview':{
            deps:['jquery','css!../plugin/imgPreview/imgPreview'],
            exports: 'imgPreview'
        },
        'ajaxfileupload':{
            desp:['jquery']
        },
        'layui':{
            deps: ['css!../plugin/layui/css/layui.css'],
            exports: 'layui'
        },
        'multiple':{
            deps: ['jquery','css!../plugin/multiple/fSelect.css'],
            exports: 'multiple'
        },
    },
    map:{
        'css': '../plugin/css.min'
    },
    prodPaths: {
        'GooFlow': '../plugin/GooFlow/plugin/GooFlow.min',
        'iframe.Services': 'waiter/iframe.Services'

    },
    prodShim: {
        'GooFlow': {
            deps: ['jquery',
                'css!../plugin/GooFlow/plugin/GooFlow.min.css',
                'css!../plugin/GooFlow/fontawesome/css/font-awesome.css'],
            exports: 'GooFlow'
        }
    }

}
/*var configHost =  window.location.host;
configHost=configHost.indexOf(':')!=-1?configHost.substring(0,configHost.indexOf(':')):configHost
var configDomain = ['localhost', '127.0.0.1','192.168.195.121'];
var switchs=configDomain.indexOf(configHost)!=-1?true:false//true 本地，false远程上线正式

var paths=switchs?configJSON.devPaths:configJSON.prodPaths
var shim=switchs?configJSON.devShim:configJSON.prodShim
var map=switchs?configJSON.devMap:configJSON.prodMap*/
require.config({
    baseUrl: 'js',
    urlArgs: _REQUIRE_CONFIG_urlArgs , //防止读取缓存，调试用
    waitSeconds: 0,
    paths:config.paths,
    shim: config.shim,
    map: { //配置css  按需加载css
        '*': config.map
    }
});
