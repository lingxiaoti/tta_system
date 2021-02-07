'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('ttaContractSpecialHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.logoImg = [];

        $scope.btnNew = function () {
            iframeTabAction('新增-合同审核特批申请', 'ttaContractSpecialHeaderD/')
        };


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;

            iframeTabAction('合同审核特批申请-详情：' + row.orderNo , 'ttaContractSpecialHeaderD/' + row.ttaContractSpecialHeaderId);
        };


        $scope.btnDiscard = function () {
            var row = $scope.dataTable.selectRow;

            if (row) {
                SIEJS.confirm('作废', '是否确定作废？', '确定', function () {

                    httpServer.post(URLAPI.cancelTtaContractSpecialHeader, {
                        params: JSON.stringify({ids: row.ttaContractSpecialHeaderId})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('作废成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('作废失败', 'error', '确定');
                    })

                })
            }
        }


    });
});
