/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('reportProcessHeaderDCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams,$timeout, SIEJS, httpServer, URLAPI, iframeTabAction) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.tabAction = 'reportProcessHeaderDBusiness'; //根据页面配置
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //先判断是否业务form是否有id 再判断流程form的id
        $scope.cwOneTable = [] ;
        $scope.dmTable = [];
        $scope.osdTable = [];
        $scope.nppTable = [];
        $scope.hwbTable = [] ;
        if ($stateParams.itemId) {
            $scope.id = $stateParams.itemId;
        } else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;
        }

        //如果表单的ID与页面的头ID不一致，需要做兼容处理
        //url参数转对象
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'TTA_ACTIVITY.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };

        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };

        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey: $scope.id
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };

        $scope.search = function() {
            httpServer.post(URLAPI.findTtaReportProcessHeaderOne, {
                params: JSON.stringify({
                    reportProcessHeaderId: $scope.id
                })
            },function(res) {
                if(res.status === 'S') {
                    $scope.params  =  res.data;
                    $scope.cwParams =  {'reportProcessHeaderId':$scope.id,'flag':'process'} ;
                    //$scope.params.isGroupByMonth = ("NPP"== $scope.params.reportType || "HWB" == $scope.params.reportType) ? "N" : "Y";
                    if("CW" == res.data.reportType){
                        $timeout(function(){
                            $scope.cwOneTable.search(1) ;
                        },200)

                    } else if ("DM" == res.data.reportType) {
                        $scope.dmTable.search(1);
                    } else if ("OSD" == res.data.reportType) {
                        $scope.osdTable.search(1);
                    } else if ("NPP" == res.data.reportType) {
                        $scope.nppTable.search(1);
                    } else if ("HWB" == res.data.reportType){
                        $timeout(function () {
                            $scope.hwbTable.search(1);
                        }, 500)

                    }
                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败1','error','确定');
            })
        };

        $scope.search();

        //提交
        $scope.submitApproval = function () {
            if('DS01' !=$scope.params.status){
                SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                return;
            }

            if (!$scope.params.reportType) {
                SIEJS.alert('关键的流程参数正在加载中，请稍后提交', 'error', '确定');
                return;
            }
            /**
             * 六大报表各自补充
             */
            if('CW' == $scope.params.reportType){

                if($scope.cwOneTable.list.length == 0){
                    SIEJS.alert('当前页面没有数据,不允许提交审批', 'error', '确定');
                    return;
                }

            }

            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.variables = []; //流程变量
            $scope.propcessVariables = {
                "reportType": $scope.params.reportType,
                "isGroupByMonth": ("FG"== $scope.params.reportType || "HWB" == $scope.params.reportType) ? "Y" : "N",
                "deptCode": $scope.userData.groupCode
            };
            angular.forEach($scope.propcessVariables, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "HTGL",
                "opinion": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "TTA_ACTIVITY.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.reportProcessHeaderId, //单据ID
                "title": $scope.params.reportType + "_REPORT_审批" + $scope.params.batchCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.params.batchCode
            };
            console.log("提交的流程参数信息是：" + JSON.stringify($scope.paramsBpm));
            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.search();
                    SIEJS.alert("提交成功", "success", "确定");
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                    $scope.getClauseh($scope.paramsBpm.businessKey,null);

                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });
        }


    });
});
