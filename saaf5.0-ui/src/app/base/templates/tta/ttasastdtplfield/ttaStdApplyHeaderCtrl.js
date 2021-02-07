'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('ttaStdApplyHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {}
        $scope.logoImg = []

        $scope.btnNew = function () {
            iframeTabAction('新增标准模板申请', 'ttaStdApplyHeaderD/')
        }


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('标准模板申请详情：' + row.orderNo + '-' + row.versionCode, 'ttaStdApplyHeaderD/' + row.stdApplyHeaderId);
        }


        $scope.btnDiscard = function () {
            var row = $scope.dataTable.selectRow;

            if (row) {
                SIEJS.confirm('作废', '是否确定作废？', '确定', function () {

                    httpServer.post(URLAPI.cancelTtaStdApplyHeader, {
                        params: JSON.stringify({ids: row.stdApplyHeaderId})
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
