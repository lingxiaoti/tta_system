/**
 * Created by yangjie on 2018/7/18.
 */
'use strict';
define(['app'], function (app) {
    app.controller('msgInstanceDetailCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
        $scope.search = function () {
            //基本信息
            // findMsgInstanceDetailById({params: JSON.stringify({id: $scope.$stateParams.msgInstanceId})}, function (res) {
            //     if (res.status == "S") {
            //         $scope.params = res.data;
            //     } else {
            //         SIEJS.alert(res.msg, 'error', '确定')
            //     }
            // });

            httpServer.post(URLAPI.findMsgInstanceDetailById, {
                'params': JSON.stringify(
                    {
                        id: $scope.$stateParams.msgInstanceId
                    }
                )
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params = res.data;
                }else{
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });

        };
        $scope.search();
    });
});