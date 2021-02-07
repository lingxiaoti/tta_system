'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('plmPackageConsumptionReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {consumption:'Y'};

        $scope.restore = function(){
            $scope.params = {};
            $scope.params.consumption = 'Y';
        };


    });
});
