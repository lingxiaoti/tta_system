/**
 * Created by Administrator on 2018/5/11.
 */
'use strict';
define(['app', 'ztree'], function (app) {
    app.controller('myUpcomingCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS, processGetTaskUrl,/*processHistoricOpinions,*/
                                               processTaskComplete, iframeTabAction, processEntrustApproval, processBatchComplete) {
        $scope.params = {};
        $scope.addParams = {};
        $scope.apporvalParams = {};
        $scope.showParams = {};

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
                    console.log(res.data.url)
                    if (res.data.isStart === true) { // 草稿
                        iframeTabAction('审批驳回：' + item.bpm_title, 'showProcess/' + decodeURIComponent(res.data.url) +
                            '&action=refusal')

                    } else {
                        iframeTabAction('待审批流程：' + item.bpm_title, 'showProcess/' + decodeURIComponent(res.data.url) +
                            '&action=approval')

                    }

                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }

        // 委托审批 按钮
        $scope.btnEntrustApproval = function () {
            $("#entrustPerson").modal('show');

        }
        // 获取最近 50 回复
/*        $scope.getOpinionList = function () {
            processHistoricOpinions(function (res) {
                $scope.OpinionsList = res.data;
            })
        }();*/
        //  委托代办 保存
        $scope.submitEntrustApprova = function () {

            SIEJS.confirm('您将委托给：' + $scope.showParams.userName, '', '确定', function (y) {
                if (y) {
                    $scope.addParams.taskIds = $scope.currentModel.key;

                    processEntrustApproval($scope.addParams, function (res) {
                        if (res.status === 'S') {
                            $("#entrustPerson").modal('hide');

                            $scope.dataTable.seach(1);

                        }
                    })
                }

            })

        }

        // 批量审批
        $scope.batchApproval=function(){
            $scope.showBtnApproval('Y','审批');
        }

        // 批量驳回
        $scope.batchRejected=function(){
            $scope.showBtnApproval('RJ','驳回');
        }


        // 显示审核弹窗
        $scope.showBtnApproval = function (type, txt) {
            $scope.addParams = {};
            $scope.addParams.opinion = '批量审批通过';
            $("#approvalFormModal").modal('show');

            $scope.apporvalParamsType = type;
            $scope.apporvalParamsBtn = txt;

            $scope.showParams = {};
        }

        // 审核功能
        $scope.btnApproval = function () {
            //  进行任务办理
            $scope.apporvalParams = {
                taskIds: $scope.currentModel.key,
                properties: {
                    opinion: $scope.addParams.opinion + '',
                    option: $scope.apporvalParamsType
                }
            };


            processBatchComplete($scope.apporvalParams, function (res) {
                if (res.status === 'S') {
                    $("#approvalFormModal").modal('hide');
                    $scope.dataTable.cancel();
                    $scope.dataTable.search(1);

                    swal({
                        title: ($scope.apporvalParamsBtn  +'成功')|| "提交成功",
                        text: '',
                        type: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });

                }else {
                    SIEJS.alert($scope.apporvalParamsBtn + '失败','error','确定', res.msg);
                }

            })
        }

        // 快捷回复
        $scope.toOpinion = function () {
            $scope.addParams.opinion = $scope.showParams.opinion + '';
        }
    });
});
