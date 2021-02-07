/**
 * Created by Administrator on 2018/5/10.
 */
'use strict';
define(['app', 'ztree'], function (app) {
    app.controller('processInitiateCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, openWindow,
                                                    processGetStartUrl, iframeTabAction) {
        $scope.params = {};
        //流程分类对象
        $scope.sort = {};
        //流程对象
        $scope.process = {};
        //流程表格对象
        $scope.processTable = {};
        //流程表格查询参数对象
        $scope.processParams = {
            deployed: true
        };

        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
        };
        $scope.btnModify = function () {
            if (!dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.btnDel = function () {
            if (!$scope.dataTable.selectRow) {
                SIEJS.alert('请选择一条数据', 'warning');
                return;
            }
        };
        $scope.save = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            log($scope.addParams)
        }
        //发起流程
        $scope.btnInitiate = function () {
            if ($scope.processTable.selectRow.status == '未发布') {
                SIEJS.alert('该流程未发布，不可发起', 'error');
                return;
            }

            processGetStartUrl({
                params: JSON.stringify({
                    processDefinitionKey: $scope.processTable.selectRow.key,
                    responsibilityId: $scope.ctrlRespId

                })
            }, function (res) {
                if (res.status === 'S') {
                    if (res.data.url) {
                        // 使用include 方式加载表单
                        iframeTabAction('发起流程：' + $scope.processTable.selectRow.name, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=new');


                    } else {
                        SIEJS.alert('流程没有设置表单', 'error');
                    }
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })

        }


        // 树节点点击
        $scope.treeClick = function (item) {
            $scope.processParams.categoryId = item.categoryId;

            $scope.sort.isActive = false;
            $scope.sort.selectNode = item;

            $scope.processTable.selectRow = false;
            $scope.processTable.search();
        }

        // 树加载完成
        $scope.treeLoad = function(ztree) {
            var selectedNode = ztree.getSelectedNodes();
            var nodes = ztree.getNodes();
            var childNodes = ztree.transformToArray(nodes[0]);
            ztree.checkNode(nodes[0], true, true);// 选中节点
            ztree.selectNode(nodes[0]); //选中第一个节点
            ztree.expandNode(nodes[0], true); // 展开节点
            $scope.treeClick(nodes[0]);
        }

    });
});
