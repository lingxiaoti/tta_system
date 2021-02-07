define(['app',
    '../app/' + appName.toLowerCase() + '/routes/view'
], function (app, view) {
    var path = appName.toLowerCase();
    app.config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/' + path + '/E404');
        //默认视图
        $urlRouterProvider.when( '/' + path, '/' + path + '/login');
        // console.log(path + '/E404')
        $stateProvider
            .state('E404', {
                url: '/' + path + '/E404',
                templateUrl: 'layout/E404.html'
            })
            .state('error', {
                url: '/' + path + '/error/:msg',
                templateUrl: 'layout/default/errorpage/error.html',
                controllerUrl: '../layout/default/errorpage/errorCtrl'
            })
            .state('testDev', {
                url: '/' + path + '/testDev',
                templateUrl: 'test/t3/t3.html',
                controllerUrl: '../test/t3/t3Ctrl'
            })
            .state('entrance',
                {
                    stateName: '外部入口',
                    url: '/' + path + '/entrance',
                    templateUrl: 'layout/default/entrance/entrance.html',
                    controllerUrl: '../layout/default/entrance/entranceCtrl'
                })
            .state('t2', {
                url: '/' + path + '/t2',
                templateUrl: 'test/t2/t2.html',
                controllerUrl: '../test/t2/t2Ctrl'
            })
            .state('login', {
                url: '/' + path + '/login',
                templateUrl: 'layout/default/login/login.html',
                controllerUrl: '../layout/default/login/loginCtrl'
            })
            // .state('main', {
            //     url: '/' + path + '/main',
            //     templateUrl: 'layout/default/main/main.html',
            //     controllerUrl: '../layout/default/main/mainCtrl'
            // })
            
            .state('home', {
                url: '/' + path + '/home',
                template: '<div class="w100p h100p bg-white"  ui-view=""></div>',
                controllerUrl: '../layout/default/homeCtrl'
            })
            // 动态报表 图表类型配置
            .state('dynamicReportQuery',
                {
                    url: '/' + path + '/dynamicCharts/preview/dynamicReportQuery/:reportHeaderCode',
                    stateName: '动态报表预览',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/preview/dynamicReportQuery.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/preview/dynamicReportQueryCtrl'
                })

            .state('dynamicEchartsReport',
                {
                    url: '/' + path + '/dynamicCharts/preview/dynamicEchartsReport/:reportHeaderCode',
                    stateName: '动态图表预览',
                    templateUrl: 'app/base/templates/computer/dynamicCharts/preview/dynamicEchartsReport.html',
                    controllerUrl: '../app/base/templates/computer/dynamicCharts/preview/dynamicEchartsReportCtrl'
                })

            .state('dynamicReportGroupPerview', {
                url: '/' + path + '/dynamicCharts/preview/dynamicReportGroupPerview/:reportHeaderCode',
                stateName: '动态图表预览',
                templateUrl: 'app/base/templates/computer/dynamicCharts/preview/dynamicReportGroupPerview.html',
                controllerUrl: '../app/base/templates/computer/dynamicCharts/preview/dynamicReportGroupPerviewCtrl'
            })

    })


});
