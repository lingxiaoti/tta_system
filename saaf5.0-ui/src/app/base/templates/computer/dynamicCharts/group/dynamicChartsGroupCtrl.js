/**
 * Created by houxingzhang on 2018-01-23.
 */
'use strict';
define(["app"], function (app) {
    app.controller('dynamicChartsGroupCtrl', function ($scope, $controller, $rootScope,
                                                       iframeTabAction) {


        $scope.btnModify = function () {
            if ($scope.dataTable.selectRow)
            {
                var url = '/dynamicCharts/groupDetail/' + $scope.dataTable.selectRow.reportGroupId +
                    '?params=' + JSON.stringify(($scope.dataTable.selectRow));

                $rootScope.goto( '报表组详情：'+ $scope.dataTable.selectRow.menuTabName , url);
            }
        };
        $scope.btnDel=function(){
            $scope.dataTable.delete();
        }
        $scope.btnNew=function(){
            iframeTabAction('新增报表组','/dynamicCharts/groupDetail/0')
        }



    });
});