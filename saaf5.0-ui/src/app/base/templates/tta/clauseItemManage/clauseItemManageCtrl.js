/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('clauseItemManageCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {
            /**
             *修改
             * @param row
             */
            $scope.btnModify = function () {
                var row = $scope.dataTable.selectRow;
                iframeTabAction('条款名目详情'+ row.year,'/clauseItemManageD/' + row.teamFrameworkId);
            }
        }])
})