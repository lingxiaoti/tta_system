define(["app"], function (app) {
    app.controller('submitRequestCtrl',
        function (SIEJS, $rootScope, $scope, httpServer, URLAPI, $state, $timeout, iframeTabAction, requestDeleteMulti) {
            $scope.params = {};

            $scope.btnNew = function () {
                iframeTabAction('新增请求', 'requestContent/')
            };
            $scope.btnDetail = function () {
                iframeTabAction('提交请求详情:' + $scope.dataTable.selectRow.jobName, 'requestContent/' + $scope.dataTable.selectRow.scheduleId)
            }
            //删除请求
            $scope.btnDel = function () {
                //console.log($scope.delparams)
                //删除操作
                //$scope.dataTable.delete();

                //scheduleId = scheduleId || $scope.dataTable.selectRow.scheduleId;

                var idList = [];
                $scope.dataTable.selectRowList.map(function (item, index) {
                    idList.push({
                        scheduleId: item.scheduleId
                    })
                });

                requestDeleteMulti({
                    'params': JSON.stringify(
                        {scheduleIdDetails: idList}
                    )
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.search(1,null,true);// 清除之前选择的数据
                        $scope.dataTable.selectRowList=[];

                    }
                });

                /*SIEJS.confirm('删除请求', '是否确定删除该请求？', '确定', function () {
                 httpServer.remove(URLAPI.requestDeleteMulti, {
                 'params': JSON.stringify(
                 {scheduleIdDetails: idList}
                 )
                 }, function (res) {
                 if (res.status == 'S') {
                 $scope.dataTable.search(1);
                 }
                 } );
                 })*/
            }


            //表格，单击事件
            $scope.requestOnClick = function (row) {
                $scope.detail = row;
            }

            //获取请求执行日志
            $scope.btnLog = function () {
                if ($scope.dataTable.selectRow.scheduleId == undefined) {
                    SIEJS.alert('请选中表格行进行操作', "warning", "确定");
                    return;
                }
                //
                httpServer.post(URLAPI.requestFindLog, {
                    'params': JSON.stringify({
                        scheduleId: $scope.dataTable.selectRow.scheduleId
                    })
                }, function (res) {
                    $scope.requestLog = res.data;
                    $("#lookLog").modal('show');
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            }

            //取消请求
            $scope.btnCancel = function () {
                SIEJS.confirm('取消请求', '是否确定取消该请求？', '确定', function () {
                    if ($scope.dataTable.selectRow.scheduleId == undefined) {
                        SIEJS.alert('请选中表格行进行操作', "warning", "确定");
                        return;
                    }
                    //
                    httpServer.post(URLAPI.requestCancel, {
                        'params': JSON.stringify({
                            scheduleId: $scope.dataTable.selectRow.scheduleId
                        })
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('取消成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }

                    }, function (err) {
                        console.error(err);
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                })
            }

            //暂停请求
            $scope.btnPending = function () {
                SIEJS.confirm('暂挂请求', '是否确定暂挂该请求？', '确定', function () {
                    if ($scope.dataTable.selectRow.scheduleId == undefined) {
                        SIEJS.alert('请选中表格行进行操作', "warning", "确定");
                        return;
                    }
                    //
                    httpServer.post(URLAPI.requestPause, {
                        'params': JSON.stringify({
                            scheduleId: $scope.dataTable.selectRow.scheduleId
                        })
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            SIEJS.alert('暂挂成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }

                    }, function (err) {
                        console.error(err);
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                })
            }

            //启动请求
            $scope.btnStart = function () {
                SIEJS.confirm('启动请求', '是否确定启动该请求？', '确定', function () {
                    if ($scope.dataTable.selectRow.phaseCode == 'JOBPAUSED_PHASECODE') {
                        //启动处于暂停状态的请求
                        httpServer.post(URLAPI.requestResume, {
                            'params': JSON.stringify({
                                scheduleId: $scope.dataTable.selectRow.scheduleId
                            })
                        }, function (res) {
                            console.log(res);
                            if ('S' == res.status) {
                                $scope.dataTable.search();
                                SIEJS.alert('启动成功');
                            } else {
                                SIEJS.alert(res.msg, 'error', '确定');
                            }
                        }, function (err) {
                            console.error(err);
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        });
                    } else if ($scope.dataTable.selectRow.phaseCode == 'JOBWASEXECUTED_PHASECODE' || $scope.dataTable.selectRow.phaseCode == 'JOBUNSCHEDULED_PHASECODE') {
                        // $scope.putRequestParams($scope.dataTable.selectRow.jobId,$scope.dataTable.selectRow.argumentsText);//字符参数还原为参数表单中的值 add by liujun
                        $scope.dataTable.selectRow.isRedo = 'YES';
                        var param = new Array();
                        httpServer.post(URLAPI.jobParamFind, {
                            params: JSON.stringify({
                                jobId: $scope.dataTable.selectRow.jobId
                            })
                        }, function (res) {
                            if (res.data.length > 0) {
                                var argumentsJson = JSON.parse($scope.dataTable.selectRow.argumentsText);
                                angular.forEach(argumentsJson, function (val1, key1) {
                                    angular.forEach(res.data, function (val2, key2) {
                                        if (key1 == val2.paramName) {
                                            val2.defaultValue = val1;
                                            param.push(val2);
                                        }
                                    })
                                })
                                $scope.dataTable.selectRow.param = param;
                            } else {
                                $scope.dataTable.selectRow.param = [];
                            }
                            sendRequest($scope.dataTable.selectRow);
                        }, function (error) {
                            console.log(error);
                        })
                    } else {
                        SIEJS.alert('该状态下无法启动', 'error', '确定');
                    }
                })
            }

            function sendRequest(obj) {
                httpServer.post(URLAPI.saveQequest, {
                    params: JSON.stringify(obj)
                }, function (res) {
                    console.log(res);
                    if ('S' == res.status) {
                        $scope.dataTable.search();
                        SIEJS.alert('启动成功');
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    SIEJS.alert('启动失败', 'error', '确定 ');
                })
            }


        });
});