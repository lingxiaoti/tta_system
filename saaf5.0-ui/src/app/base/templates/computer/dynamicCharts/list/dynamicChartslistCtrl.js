/**
 * Created by houxingzhang on 2018-01-23.
 */
'use strict';
define(["app"], function (app) {
    app.controller('dynamicChartslistCtrl', function ($scope, $controller, httpServer, URLAPI, SIEJS, iframeTabAction) {


        $scope.btnDel = function () {

            var id = $scope.dataTable.selectRow.reportHeaderId;
            httpServer.remove(URLAPI.dynamicReportListDelete, {params: JSON.stringify({id: id})}, function (res) {
                if (res.status === 'S') {
                    $scope.dataTable.search();
                    $scope.dataTable.selectRow=null;
                }
            })
        }
        $scope.btnNew = function () {
            iframeTabAction('新增报表', '/dynamicCharts/detail/0')
        }

        $scope.btnModify = function () {
            iframeTabAction('动态报表详情：' + $scope.dataTable.selectRow.reportHeaderCode, '/dynamicCharts/detail/' + $scope.dataTable.selectRow.reportHeaderId)
        }

    });
});