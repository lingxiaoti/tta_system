/**
 * Created by Administrator on 2018/6/23.
 */
'use strict';
define(['app'], function (app) {
    app.controller('logCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS) {
        $scope.params = {

        };
        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.btnDel = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }

        // 显示详情
        $scope.showDetail = function (item) {
            $("#modalShowDetail").modal('show');
            var msg = item.message;
            debugger;
            if (msg) {
                msg = msg.replace(/^\s+|\s+$/gm, '');
            }

            $scope.detail = msg;
        }


    });
});
