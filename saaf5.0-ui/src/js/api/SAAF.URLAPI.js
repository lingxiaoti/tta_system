/**
 * Created by houxingzhang on 2018-1-06.
 * 服务ＡＰＩ主模块
 * 把多个ＡＰＩ进行合并
 */


require(['angular',
        '../app/' + appName.toLowerCase() + '/api/main',
        './api/base',
    'webconfig'
    ],
    function (angular, api, baseapi,webconfig) {
        var module = angular.module('SAAF.URLAPI', []);
        var apiList = $.extend(api, baseapi);
        if (webconfig.apiToggle) { //使用JSON数据，仅做无服务时测试使用
            for (var n in apiList) {
                apiList[n] = 'json/' + n + '.json'
            }
            // 处理菜单
            apiList.menuListByReps='json/menuListByReps_'+window.appName + '.json';
           // apiList.findMenuInfo='json/menuListByReps_'+window.appName + '.json';//切换本地服务JSON
        }


        module.constant('URLAPI', apiList);
        //module.constant('URLAPI', api);
        return module;
    }
)
;