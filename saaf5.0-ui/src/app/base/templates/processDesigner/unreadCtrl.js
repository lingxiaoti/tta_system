/**
 * Created by Administrator on 2018/6/15.
 */
'use strict';
define(['app', 'ztree'], function (app) {
    app.controller('unreadCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, processGetTaskUrl,
                                           iframeTabAction, processNotifyTaskStatus) {
        $scope.params = {};
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

        $scope.clickColumns = function (item) {
            var p = {
                taskId: item.taskId
            };
            processGetTaskUrl({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    iframeTabAction('待审批流程：' + item.bpm_title, 'showProcess/' + decodeURIComponent(res.data.url)  +
                        '&action=unread')
                    $scope.read([item.notifyId]);

                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }


        // 设置已读
        $scope.read = function (id, tip) {
            processNotifyTaskStatus({
                notifyIds: id,
                status: 1
            }, function (res) {
                if (res.status === 'S' ) {
                    $scope.dataTable.search();// 重载表格
                    $scope.dataTable.cancel();
                    
                    if (tip){
                        SIEJS.alert('已全部设置已读');
                    }
                }
            })
        }


        // 设置已读按钮
        $scope.btnReadAll = function () {
            $scope.read($scope.currentTable.key, true)
        }
    });
});
