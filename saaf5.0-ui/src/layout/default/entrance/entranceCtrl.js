/**
 * Created by Administrator on 2018/7/5.
 */
'use strict';
define(['app'], function (app) {
    app.controller('entranceCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,iframeTabAction) {
        //var gorouter =   window.appName.toLocaleLowerCase() +'/' + $location.url().split('?')[1].replace('returnURL=','')+'&entrance=true';
        var gorouter =   window.appName.toLocaleLowerCase() +'/showProcess/' + $location.url().split('?')[1].replace('returnURL=','');
        //var gorouter =   'showProcess/' + $location.url().split('?')[1].replace('returnURL=','');

        gorouter = decodeURIComponent(gorouter);
        console.log("邮件跳转链接:" + gorouter);
        log(gorouter);
        $location.url(gorouter);
        //window.location.replace(gorouter);


        //iframeTabAction('条款框架审批',gorouter);

        /*   var w = window.location;
       window.open( w.origin + w.pathname + '#/'  + gorouter,'_self');*/


    });
});
