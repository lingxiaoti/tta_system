/**
 * Created by dengdunxin on 2018/3/8.
 */
define(['app'], function (app) {
    app.controller('requestContentCtrl', ['$scope', 'httpServer', 'URLAPI', '$stateParams', 'SIE.JS', '$filter',
        function ($scope, httpServer, URLAPI, $stateParams, JS, $filter) {

            $scope.params = {};
            $scope.requestParamsTable = [];
            // $scope.requestParams = {};

            $scope.disabledSave = false;

            $scope.checkedParams = [];

            $scope.request = {};

            $scope.isShow = !$stateParams.id;

            $scope.request.argumentsText = '';
            $scope.request.argumentsTextObj = {};

            $scope.init = function () {
                if ($stateParams.hasOwnProperty('id') && $stateParams.id != '' && $stateParams.id != null && $stateParams.id != undefined) {

                    getRequest($stateParams.id);
                } else {
                    $scope.request = {};
                    $scope.request.scheduleType = 'IMMEDIATE';
                    $scope.request.isRedo = 'NO';
                    $scope.request.scheduleInterval = 'S';
                }
            }

            function getRequest(id) {
                httpServer.post(URLAPI.requestFind, {
                    params: JSON.stringify({
                        scheduleId: id
                    })
                }, function (res) {
                    $scope.request = res.data[0];
                    $scope.request.scheduleExpectEndDate=$filter('date')($scope.request.scheduleExpectEndDate);
                    $scope.request.actualStartDate=$filter('date')($scope.request.actualStartDate);
                    $scope.request.actualCompletionDate=$filter('date')($scope.request.actualCompletionDate);
                    $scope.request.previousFireTime=$filter('date')($scope.request.previousFireTime);
                    $scope.request.nextFireTime=$filter('date')($scope.request.nextFireTime);
                    $scope.request.scheduleExpectStartDate=$filter('date')($scope.request.scheduleExpectStartDate);

                    $scope.disabledSave =!( $scope.request.phaseCode==='JOBUNSCHEDULED_PHASECODE' || $scope.request.phaseCode==='JOBWASEXECUTED_PHASECODE');
                }, function (error) {
                    console.error(error);
                    JS.alert('获取数据失败', 'error', '确定');
                })
            }

            //获取默认参数
            $scope.$watch('request.jobName', function () {
                if ($scope.request.hasOwnProperty('jobId') && $scope.request.jobId != '' && $scope.request.jobId != null && $scope.request.jobId != undefined) {
                    httpServer.post(URLAPI.jobParamFind, {
                        params: JSON.stringify({
                            jobId: $scope.request.jobId
                        })
                    }, function (res) {
                        $scope.checkedParams = res.data;
                        $scope.requestParamsTable = angular.copy(res.data);
                        if (!$scope.request.hasOwnProperty('scheduleId')) {
                            buildArgumentsText(res.data, 0);
                            buildArgumentsTextObj(res.data, 0);
                        }
                    }, function (error) {
                    })
                }
            })

            window.changeCornexpressValue = function (str) {
                $scope.$apply(function () {
                    $scope.request.cornexpress = str;
                })
            }

            //提交表单
            $scope.saveConcurrentRequest = function (formStatus) {
                if (!formStatus) {
                    $scope.request.param = $scope.checkedParams;
                    if ($scope.request.hasOwnProperty('scheduleId')) {
                        $scope.request.isRedo = 'YES';
                    }
                    httpServer.post(URLAPI.saveQequest, {
                        params: JSON.stringify($scope.request)
                    }, function (res) {
                        console.log(res);
                        if ('S' == res.status) {
                            getRequest(res.data.scheduleId);
                            JS.alert('保存成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        JS.alert('保存失败', 'error', '确定 ');
                    })
                } else {
                    JS.alert('请填写完整表单', 'error', '确定');
                }
            }

            //添加属性
            $scope.addParams = function () {
                //$scope.requestParamsTable = [];
                $scope.checkedParams = [];


                if ($scope.request.hasOwnProperty('jobId') && $scope.request.jobId) {

                    // if ($scope.request.argumentsText != '' && $scope.request.argumentsText != null && $scope.request.argumentsText != undefined) {
                    //     var defaultParams = JSON.parse($scope.request.argumentsText);
                        angular.forEach($scope.request.argumentsTextObj, function (val, key) {
                            console.log(key)
                            for (var i = 0; i < $scope.requestParamsTable.length; i++) {
                                if (key === $scope.requestParamsTable[i].paramName) {
                                    $scope.requestParamsTable[i].checked = true;

                                    $scope.requestParamsTable[i].defaultValue = val;

                                    $scope.checkedParams.push($scope.requestParamsTable[i]);
                                    break;
                                }
                            }
                        })
                    // }

                    $('#addParamsModal').modal('show');
                } else {
                    JS.alert('未选择请求名称', 'error', '确定');
                }
            }

            //监听已选的属性，一般做为检测默认值
            $scope.$watch('requestParamsTable', function (newVal, oldVal) {
                if (newVal.data != undefined && newVal.data != null) {
                    angular.forEach($scope.checkedParams, function (val, key) {
                        for (var i = 0; i < $scope.requestParamsTable.length; i++) {
                            if (val.paramId === $scope.requestParamsTable[i].paramId) {
                                $scope.requestParamsTable[i].checked = true;
                                break;
                            }
                        }
                    })

                    $scope.changeArr = true;
                    for (var e = 0; e < $scope.requestParamsTable.length; e++) {
                        if (!$scope.requestParamsTable[e].checked) {
                            $scope.changeArr = false;
                            break;
                        }
                    }
                }
            })

            //全选当前页的数据
            $scope.changeArrRow = function (changeArr, arr) {
                if (changeArr) {
                    for (var i = 0; i < arr.length; i++) {
                        if (!arr[i].checked) {
                            arr[i].checked = true;
                            $scope.checkedParams.push(arr[i]);
                        }
                    }
                } else {
                    for (var i = 0; i < arr.length; i++) {
                        arr[i].checked = false;
                        for (var e = 0; e < $scope.checkedParams.length; e++) {
                            if (arr[i].paramId === $scope.checkedParams[e].paramId) {
                                $scope.checkedParams.splice(e, 1);
                            }
                        }
                    }
                }
            }

            //单行选中
            $scope.changeRow = function (row) {
                if (row.checked) {
                    $scope.checkedParams.push(row);
                } else {
                    for (var i = 0; i < $scope.checkedParams.length; i++) {
                        if (row.paramId === $scope.checkedParams[i].paramId) {
                            $scope.checkedParams.splice(i, 1);
                            break;
                        }
                    }

                }
                $scope.changeArr = true;
                for (var e = 0; e < $scope.requestParamsTable.length; e++) {
                    if (!$scope.requestParamsTable[e].checked) {
                        $scope.changeArr = false;
                        break;
                    }
                }
                console.log($scope.checkedParams)
            }

            //提交添加属性表单
            $scope.confirmAddParams = function (formStatus) {
                if (!formStatus) {
                    buildArgumentsText($scope.checkedParams, 1);
                    buildArgumentsTextObj($scope.checkedParams, 1);
                    $('#addParamsModal').modal('hide');
                }
            }

            //组装参数（json格式）
            function buildArgumentsText(arr, action) {
                $scope.request.argumentsText = '';
                if (action) {
                    //手动做提交操作
                    angular.forEach(arr, function (val, key) {
                        if (key === 0) {
                            if ('STRING' == val.paramType || 'DATE' == val.paramType) {
                                $scope.request.argumentsText = "\"" + val.paramName + "\":\"" + val.defaultValue + "\"";
                            } else {
                                $scope.request.argumentsText = "\"" + val.paramName + "\":" + val.defaultValue;
                            }
                        }
                        if (key > 0) {
                            if ('STRING' == val.paramType || 'DATE' == val.paramType) {
                                $scope.request.argumentsText = $scope.request.argumentsText + ",\"" + val.paramName + "\":\"" + val.defaultValue + "\"";
                            } else {
                                $scope.request.argumentsText = $scope.request.argumentsText + ",\"" + val.paramName + "\":" + val.defaultValue;
                            }
                        }
                    })
                } else {
                    //自动获取已启用的
                    angular.forEach(arr, function (val, key) {
                        if (val.isEnabled == 'true' && key === 0) {
                            if ('STRING' == val.paramType || 'DATE' == val.paramType) {
                                $scope.request.argumentsText = "\"" + val.paramName + "\":\"" + val.defaultValue + "\"";
                            } else {
                                $scope.request.argumentsText = "\"" + val.paramName + "\":" + val.defaultValue;
                            }
                        }
                        if (val.isEnabled == 'true' && key > 0) {
                            if ('STRING' == val.paramType || 'DATE' == val.paramType) {
                                $scope.request.argumentsText = $scope.request.argumentsText + ",\"" + val.paramName + "\":\"" + val.defaultValue + "\"";
                            } else {
                                $scope.request.argumentsText = $scope.request.argumentsText + ",\"" + val.paramName + "\":" + val.defaultValue;
                            }
                        }
                    })
                }
                $scope.request.argumentsText = '{' + $scope.request.argumentsText + '}';
            }

            //组装参数（json格式）
            function buildArgumentsTextObj(arr, action) {
                $scope.request.argumentsTextObj = {};
                if (action) {
                    //手动做提交操作
                    angular.forEach(arr, function (val, key) {
                        $scope.request.argumentsTextObj[val.paramName] = val.defaultValue;
                    })
                } else {
                    //自动获取已启用的
                    angular.forEach(arr, function (val, key) {
                        if (val.isEnabled == 'true') {
                            $scope.request.argumentsTextObj[val.paramName] = val.defaultValue;
                        }
                    })
                }
            }

            //添加cron
            $scope.setCron = function () {
                setTimeout(function () {
                    console.log($scope.request.cornexpress)
                }, 5000)
                console.log(navigator)
                if (navigator.userAgent.indexOf("Chrome") > 0 || navigator.userAgent.indexOf("Firefox") > 0) {
                    var winOption = "height=" + 500 + ",width=" + 1200 + ",top=50,left=50,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,fullscreen=0";
                    if ($scope.request.cornexpress) {
                        window.open("plugin/Cron/CronExpress3.html?cornexpress="+$scope.request.cornexpress, null, winOption);
                    }else{
                        window.open("plugin/Cron/CronExpress3.html", null, winOption);
                    }
                }

                else {
                    if ($scope.request.cornexpress) {
                        $scope.request.cornexpress = window.showModalDialog("plugin/Cron/CronExpress3.html", $scope.request.cornexpress, "dialogWidth=900px;dialogHeight=500px;location=no");
                    }
                    else {
                        $scope.request.cornexpress = window.showModalDialog("plugin/Cron/CronExpress3.html", null, "dialogWidth=900px;dialogHeight=500px;location=no");
                    }
                }
            }

            //change运行计划
            $scope.changeScheduleType = function (str) {
                console.log(str)
                if (str != 'IMMEDIATE' && ($scope.request.scheduleExpectStartDate == '' || $scope.request.scheduleExpectStartDate == null
                    || $scope.request.scheduleExpectStartDate == undefined)) {
                    $scope.request.scheduleExpectStartDate = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss')
                }
                if (str == 'IMMEDIATE') {
                    $scope.request.scheduleExpectStartDate = '';
                }
            }

            $scope.rowClick = function (row, index) {
                $scope.requestParamsTable.selectRow = index;

                if (row.isRequired === 'false') {
                    row.checked = !row.checked;
                    $scope.changeRow(row);

                }

            }
        }])
})