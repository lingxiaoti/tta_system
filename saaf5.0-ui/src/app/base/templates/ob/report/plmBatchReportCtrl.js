'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmBatchReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {report:'Y'};

        $scope.restore = function(){
            $scope.params = {};
            $scope.params.report = 'Y';
        };

        $scope.btnDetail = function (data) {
            iframeTabAction('产品详情：'+data.productName, 'plmDevelopmentDetail/' + data.plmDevelopmentInfoId);
        }


    });
});
