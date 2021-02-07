/**
 * Created by Administrator on 2018/5/11.
 */
'use strict';
define(['app','ztree'], function (app) {
    app.controller('myApprovalCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,processGetStartUrl,iframeTabAction,processRevoke,processGetTaskUrl) {
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
        // 撤回
        $scope.bntWithdraw = function (item) {
            item = item || $scope.myProcessTable.selectRow;
            var p = {
                // processInstanceId: item.processInstanceId,
                taskId:item.taskId
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

        $scope.clickColumns = function (item) {


            var p = {
                taskId: item.taskId
            };
            processGetTaskUrl({
                params: JSON.stringify(p)
            }, function (res) {
                if (res.status === 'S') {
                    iframeTabAction('流程详情：' + item.bpm_title,  'showProcess/' +decodeURIComponent(res.data.url) + '&action=detail')
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }
    });
});
