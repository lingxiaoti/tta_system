/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmSalesAreaDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};
        var oldData = {};

        $scope.plmSalesAreaRowDataTable = [];

        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;

        //查询单据信息
        $scope.search = function (string) {

            $scope.plmSalesAreaId = id;

            httpServer.post(URLAPI.findPlmSalesAreaInfo, {
                    'params': JSON.stringify({plmSalesAreaId: $scope.plmSalesAreaId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        oldData = angular.copy($scope.headerData);

                        if(string!=='header') {
                            $scope.searchPlmSalesAreaRow();
                        }

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };


        //id不为空，初始搜索
        if ($stateParams.id) {
            //查询头信息
            $scope.search();
        } else {
            $scope.headerData.billStatus = 'TODO';
            $scope.headerData.billStatusName = '制单中';
            $scope.headerData.creator = $scope.userName;
        }

        $scope.searchPlmSalesAreaRow = function () {
            httpServer.post(URLAPI.findPlmSalesAreaRowInfo, {
                    'params': JSON.stringify({plmSalesAreaId: $scope.plmSalesAreaId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.plmSalesAreaRowDataTable = res.data;

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                },false
            );
        };

    });
});
