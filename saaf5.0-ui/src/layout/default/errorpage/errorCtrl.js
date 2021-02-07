'use strict';
define(["app", 'hilink_softphone'], function (app, hilink_softphone) {
    app.controller('errorCtrl', function ($interval, saveTabToStorage, changePassword, gotoLogin, $rootScope, $scope, httpServer, Cookies,
                                         arrayFindObj, arrayDeleteItem, $compile, clearStore, systemJump, lookupCode, findUserInfo, SIEJS, URLAPI, $state, tabAction, Base64, Fullscreen, $http, $location, $stateParams, iframeTabAction, $timeout, $window, logout,$filter) {
        var msg = $stateParams.msg;
        //console.log(msg);
        $scope.erorr_msg = decodeURIComponent(msg);

        //不用这种方式输出错误信息
/*        if ($location.$$search.action === 'error') {
            $scope.erorr_msg ='登录出错了';
        }*/

    });
});
