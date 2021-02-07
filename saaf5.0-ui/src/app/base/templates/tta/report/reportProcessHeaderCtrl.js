'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('reportProcessHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        $scope.reportType = $stateParams.reportType;
        $scope.params = {}
        $scope.logoImg = []
        //根据页面传参判断要跳转的报表页面
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;

            iframeTabAction('流程单据：'+row.batchCode, 'reportProcessHeaderD/' + row.reportProcessHeaderId);

        }
    });
});
