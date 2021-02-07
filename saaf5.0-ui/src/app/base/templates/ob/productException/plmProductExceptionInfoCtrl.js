'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmProductExceptionInfoCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};

        $scope.btnNew = function () {
            iframeTabAction('新增产品异常', 'plmProductExceptionDetail/')
        };

        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('异常详情：'+row.productExceptionNum, 'plmProductExceptionDetail/' + row.plmProductExceptionInfoId)
        };

        $scope.btnDetail = function (data) {
            iframeTabAction('异常详情：'+data.productExceptionNum, 'plmProductExceptionDetail/' + data.plmProductExceptionInfoId);
        };

        $scope.btnDiscard = function () {
            $scope.discardProject();
        };

        $scope.discardProject = function () {
            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                var params = $scope.dataTable.selectRow;
                params.productExceptionBillStatus = 'ABANDONED';
                params.commandStatus = 'ABANDONED';

                httpServer.post(URLAPI.savePlmProductExceptionInfoInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.dataTable.search(1);
                            SIEJS.alert(res.msg, 'success','确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        }



    });
});
