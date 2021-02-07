'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('projectExceptionReportCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {report:'Y'};

        $scope.btnDetail = function (data) {
            iframeTabAction('项目详情：'+data.projectName, 'plmProjectDetail/' + data.plmProjectId);
        };

        $scope.restore = function () {
            $scope.params = {report:'Y'};
        }



    });
});
