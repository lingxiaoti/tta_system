/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('jobCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('新增职务','/jobDetail/' + $scope.ctrlRespId + '/-1');
            }

            /**
             *修改
             * @param row
             */
            $scope.editing = function(row) {
                iframeTabAction('编辑职务','/jobDetail/' + $scope.ctrlRespId + '/' + row.jobId);
            }

        }])
})