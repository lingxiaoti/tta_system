/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('elecSignListCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('电子签章新增', '/elecSignDetail/'+'-1')
            }
            /**
             *修改
             * @param row
             */
            $scope.btnModify = function () {
                var row = $scope.dataTable.selectRow;
                iframeTabAction('电子签章详情：'+ row.orderNo + "_" + row.orderVersion, '/elecSignDetail/' + row.elecConHeaderId); //172
            }

        }])
})