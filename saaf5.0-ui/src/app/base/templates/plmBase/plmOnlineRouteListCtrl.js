'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmOnlineRouteListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;

        $scope.plmOnlineRouteList = [];

        $scope.params = {};

        $scope.btnNew = function () {
            iframeTabAction('新增线上渠道', 'plmOnlineRouteDetail/')
        };


        $scope.btnDetail = function (data) {
            iframeTabAction('线上渠道详情：'+data.plmOnlineRouteName, 'plmOnlineRouteDetail/' + data.plmOnlineRouteId);
        };

        $scope.addPlmOnlineRouteName = function () {
            var params = {};
            httpServer.post(URLAPI.findPlmOnlineRouteInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmOnlineRouteList = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        $scope.addPlmOnlineRouteName();

    });
});
