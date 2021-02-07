'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree, XLSX) {
    app.controller('plmHelpStatementsListCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};

        $scope.btnDetail = function (data) {
            iframeTabAction('字段说明详情：'+data.fieldMeaning, 'plmHelpStatementsDetail/' + data.plmHelpStatementsId);
        };

        $scope.btnNew = function () {
            iframeTabAction('新增字段说明：', 'plmHelpStatementsDetail/');
        };

        $scope.btnModify = function () {
            var headerData = $scope.dataTable.selectRow;
            iframeTabAction('字段说明详情：'+headerData.fieldMeaning, 'plmHelpStatementsDetail/' + headerData.plmHelpStatementsId);
        };

        $scope.restore = function () {
            $scope.params = {};
        };

        $scope.restore();

        $scope.btnDiscard = function () {
            var params = angular.copy($scope.dataTable.selectRow);
            params.billStatusName = '已失效';
            SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
                httpServer.post(URLAPI.savePlmHelpStatementsInfo, {
                        'params': JSON.stringify(params)
                    },
                    function (res) {
                        if (res.status === 'S') {
                            SIEJS.alert('操作成功', 'success', '确定');
                            $scope.dataTable.search(1);
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
