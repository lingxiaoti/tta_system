/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app','pinyin','ztree'], function(app,pinyin) {
    app.controller('departmentCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction','$timeout','pageResp',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction,$timeout,pageResp) {

            $scope.rootParentId = 0; // 默认顶层的ID
            $scope.centerCode = ''
            var reps = pageResp.get();
            $scope.treeUrl = 'findDeptTree';
            $scope.treeParams = {respId: -999};
            $scope.dataTableOfMember = [];
            $scope.paramsOfMember = {};

            /**
             * 点击节点查询节点下的数据
             * @param nodeData
             */
            $scope.clickNode = function(nodeData) {
                $scope.dataTable.restore();
                $timeout(function() {
                    $scope.params.respId = $scope.ctrlRespId;
                    $scope.params.parentDepartmentId = nodeData.departmentId;
                    $scope.dataTable.search(1);

                    $scope.paramsOfMember.respId = $scope.ctrlRespId;
                    $scope.paramsOfMember.departmentId = nodeData.departmentId;
                    $scope.dataTableOfMember.search(1);

                })

            }

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('新增部门','/departmentDetail/' + $scope.ctrlRespId + '/-1');
            }

            /**
             * 编辑
             * @param row
             */
            $scope.editing = function(row) {
                iframeTabAction('编辑部门','/departmentDetail/' + $scope.ctrlRespId + '/' + row.departmentId);
            }

    }])
})