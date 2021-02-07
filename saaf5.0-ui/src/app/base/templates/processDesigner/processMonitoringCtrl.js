/**
 * Created by Administrator on 2018/7/2.
 */
'use strict';
define(['app', 'ztree'], function (app) {
    app.controller('processMonitoringCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,
                                                      processTaskTransfer, processStop, processFindActiveTasks,processGetStartUrl,iframeTabAction) {
        $scope.params = {
            searchAll: true
        };
        $scope.addParams = {};

        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
        };
        $scope.btnModify = function () {
            if (!$scope.dataTable.selectRow) {
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

        // 终止
        $scope.btnEnd = function () {
            $scope.approvalTitle = '终止流程';
            $("#approvalFormModal").modal('show');
            $scope.addParams = {
                listId: $scope.dataTable.selectRow.listId,
                reason: ''
            };


        };

        // 转交
        $scope.btnSend = function () {
            $scope.approvalTitle = '任务转交';
            $("#approvalFormModal").modal('show');
            $scope.showParams = {
                userName: ''
            };

            // 获取当前的任务节点
            processFindActiveTasks({
                processInstanceId: $scope.dataTable.selectRow.processInstanceId
            }, function (res) {
                $scope.taskList = res.data;
                $scope.addParams.taskId = res.data[0].taskId; // 默认选择第一个
            })
        };

        // 批量转交
        $scope.btnSendBatch = function () {
            //console.log($scope.dataTable);
            if($scope.dataTable.selectRowList && $scope.dataTable.selectRowList.length > 0){
                var taskIds=[];
                angular.forEach($scope.dataTable.selectRowList,function (data,index) {
                    if(data.cur_taskId){
                        taskIds.push(data.cur_taskId);
                    }
                });
                if(taskIds.length <1){
                    SIEJS.alert('请勾选审批中的流程！', 'error');
                    return;
                }
                $scope.approvalTitle = '任务批量转交';
                $scope.addParams.taskIds = taskIds;
                $("#approvalFormModal").modal('show');
                $scope.showParams = {
                    userName: '',
                    batch:true
                };
            }else{
                SIEJS.alert('请勾选需批量转交的流程！', 'warning');
            }

        }

        $scope.clickColumns = function (item) {
            var p = {
                listId: item.listId
            };
            processGetStartUrl({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    iframeTabAction('流程详情：' + item.title,  'showProcess/' +decodeURIComponent(res.data.url) + '&action=detail&activeTab=myProcess&processDefinitionId=' + item.processDefinitionId);
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        // 保存
        $scope.btnSave = function () {
            log($scope.addParams);
            switch ($scope.approvalTitle) {
                case '任务转交':
                case '任务批量转交':
                    processTaskTransfer($scope.addParams, function (res) {
                        if (res.status === 'S') {
                            SIEJS.alert('任务转交成功！', 'success');
                            $scope.dataTable.search();
                            $("#approvalFormModal").modal('hide');
                        } else {
                            SIEJS.alert(res.msg, 'warning');
                        }
                    });
                    break;

                case '终止流程':
                    processStop($scope.addParams, function (res) {
                        if (res.status === 'S') {
                            SIEJS.alert('终止成功！', 'success');
                            $scope.dataTable.search();
                            $("#approvalFormModal").modal('hide');
                        } else {
                            SIEJS.alert(res.msg, 'warning');
                        }
                    });
                    break;
            }
        }

    });
});
