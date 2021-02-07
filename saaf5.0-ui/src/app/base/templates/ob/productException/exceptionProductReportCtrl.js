'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('exceptionProductReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {report:'Y'};

        $scope.restore = function () {
            $scope.params = {report:'Y'};
        }

        $scope.btnDetail = function (data) {
            iframeTabAction('异常详情：'+data.productExceptionNum, 'plmProductExceptionDetail/' + data.plmProductExceptionInfoId);
        };
    });
});
