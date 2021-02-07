'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmSalesAreaListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.groupNameList = [];



        $scope.btnDetail = function (data) {
            iframeTabAction('售价区域详情：'+data.groupName, 'plmSalesAreaDetail/' + data.plmSalesAreaId);
        };

        $scope.addPlmSalesArea = function () {
            var params = {};
            httpServer.post(URLAPI.findPlmSalesAreaInfo, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.groupNameList = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        $scope.addPlmSalesArea();
    });
});
