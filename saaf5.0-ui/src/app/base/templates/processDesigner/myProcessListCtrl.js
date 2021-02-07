/**
 * Created by Administrator on 2018/5/11.
 */
'use strict';
define(['app','ztree'], function (app) {
    app.controller('myProcessListCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,
                                                  iframeTabAction, processGetStartUrl, processRevoke, processCommunicateSave,
                                                  processFindActiveTasks, processFindTaskUsers,processRevokeStatus) {
        $scope.params = {};
        $scope.myProcessParams = {};
        $scope.urgeParams = {};
        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
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


        $scope.statusList = [
            {key: '草稿', value: -1},
            {key: '审批中', value: 0},
            {key: '结束', value: 1}
        ];

        //点击行
        $scope.clickRow=function(item) {
            processRevokeStatus({
                processInstanceId: item.processInstanceId,
                taskId: item.taskId
            }, function (res) {
                // 是否可以撤回
                $scope.revokeStatus = res.data;
            },null,false)
        }

        // 点击栏目
        $scope.clickColumns = function (item) {
            var p = {
                listId: item.listId
            };
            processGetStartUrl({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    if (item.status === -1) { // 草稿
                        //iframeTabAction('发起流程：' + $scope.processTable.selectRow.name, 'showProcess/' + decodeURIComponent(res.data.url) + '&action=new');
                        iframeTabAction('编辑流程：' + item.title, 'showProcess/' +decodeURIComponent(res.data.url) + '&action=draft&activeTab=myProcess')
                    } else if (item.status === 1){ // 审批通过
                        iframeTabAction('流程详情：' + item.title,  'showProcess/' +decodeURIComponent(res.data.url) + '&action=detail&activeTab=myProcess')

                    }else if (item.status===0  && res.data.isStart===true){ // 审批驳回

                        iframeTabAction('审批驳回：' + item.title,  'showProcess/' +decodeURIComponent(res.data.url) + '&action=refusal&activeTab=myProcess')

                    }else if (item.status===0  && res.data.isStart===false){ // 审批中

                        iframeTabAction('流程详情：' + item.title,  'showProcess/' +decodeURIComponent(res.data.url) + '&action=detail&activeTab=myProcess')

                    }
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }

        // 催办弹窗
        $scope.bntUrge = function (item) {
            $scope.urgeParams.userId = '';
            item = item || $scope.myProcessTable.selectRow;
            // 催办参数
            $scope.urgeParams = {
                processInstanceId: item.processInstanceId
            };
            $scope.currentUserList = [];
            // 获取当前节点可选的人员
            processFindActiveTasks({
                processInstanceId: item.processInstanceId
            }, function (res) {
                $scope.taskList = res.data;
                debugger;

                var row = res.data[0];
                $scope.urgeParams.taskId= row.taskId; // 默认选择第一行
                if (row && row.userName) {
                    $scope.currentUserList.push({
                        "userFullName": row.userFullName,
                        "userId": row.userId,
                        "userName": row.userName
                    });
                    $("#modalUrgeUser").modal('show');
                    $scope.urgeParams.userId = $scope.currentUserList[0].userId; // 默认选择第一个审批 人
                } else {
                    $scope.getTaskUserList(row.taskId);

                }
                log($scope.currentUserList)
            })


        };

        // 切换任务节点
        $scope.changeTask = function() {
            $scope.urgeParams.userId=null;
            $scope.getTaskUserList($scope.urgeParams.taskId);
        }
        // 获取任务下的用户
        $scope.getTaskUserList = function(taskId) {
            $scope.currentUserList=[];
            processFindTaskUsers({
                taskId: taskId,
                includeDelegate:true
            }, function (data) {

                for (var i = 0; i < data.data.length; i++) {
                    $scope.currentUserList.push(
                        {
                            "userFullName": data.data[i].userFullName,
                            "userId": data.data[i].userId,
                            "userName": data.data[i].userName
                        }
                    )
                }
                $scope.urgeParams.userId = $scope.currentUserList[0].userId; // 默认选择第一个审批 人
                $("#modalUrgeUser").modal('show');
            })
        }
        // 催办
        $scope.urgeSave = function () {
            var p = {
                taskId: $scope.urgeParams.taskId,
                type: 'URGE',
                receiverId: $scope.urgeParams.userId,
                messageChannel: ''
            };

            processCommunicateSave(p, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert('催办成功');
                    $scope.myProcessTable.search(1);
                    $("#modalUrgeUser").modal('hide');
                } else {
                    SIEJS.alert('催办失败', 'warning');
                }
            })
        }

        // 撤回
        $scope.bntWithdraw = function (item) {
            item = item || $scope.myProcessTable.selectRow;
            var p = {
                processInstanceId: item.processInstanceId
            };
            processRevoke(p, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert('撤回成功');
                    $scope.myProcessTable.search(1);
                } else {
                    SIEJS.alert(res.msg, 'warning');
                }
            })
        }


        // 获取当前催办人id
        $scope.setUserId = function (id) {
            $scope.urgeParams.userId = id;
        }

    });
});
