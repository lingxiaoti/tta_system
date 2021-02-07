/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPosRecoverEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                      saveUserResp, userSave, $timeout, $scope,httpServer,URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, $controller) {
        // ------------------------------------------------初始化------------------------------------------------
        var supRecoverId = null;
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        if ($stateParams.supRecoverId){
            supRecoverId = $stateParams.supRecoverId;
        }else if ($scope.urlParams.businessKey){
            supRecoverId = $scope.urlParams.businessKey;
        }
        $scope.params = {}
        $scope.param = {}

        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');

        $scope.param.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.param.type = 'recover';
// ------------------------------------------------调整------------------------------------------------
        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag= false;
            $scope.approveFlag= false;
            $scope.rejectFlag= false;
            $scope.cancelFlag= false;
            $scope.editFlag= false;
            if($scope.params.supRecoverStatus!='20'&&$scope.params.supRecoverStatus!='30'){
                $scope.saveFlag = true;  //提交与审批状态不能保存
                $scope.submitFlag = true;
            }
            if($scope.params.supRecoverStatus=='20'){
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag= true;
            }
            if($scope.params.supRecoverStatus=='50'&&saafsuccessLoginInfo.userId == $scope.params.createdBy){
                $scope.cancelFlag = true;   //驳回状态本人可以取消
            }
            if($scope.params.supRecoverStatus=='10'||$scope.params.supRecoverStatus=='50'){
                $scope.editFlag= true; //拟定与驳回状态才能编辑
            }
        }

        if (supRecoverId) {

            $scope.params.supRecoverId = supRecoverId;
            console.log( $scope.params)
            httpServer.post(URLAPI.findSupRecoverDatail, {
                // params: JSON.stringify($scope.params)
                // params: JSON.stringify($scope.params)
                params: JSON.stringify({
                    supRecoverId: supRecoverId
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    $scope.changeShowFlag();
                    $scope.processInstanceParams = {
                        processInstanceId: $scope.params.procInstId
                    };
                    $scope.taskTable.search();

                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.params.supRecoverCode
                    httpServer.post(URLAPI.getActivitiesHistoric, {
                        params: JSON.stringify($scope.historicParam)
                    }, function (resa) {
                        if (resa.status == "S") {
                            $scope.processInstanceParams = {
                                processInstanceId: resa.data
                            };
                            $scope.taskTable.search();
                        }
                    }, function (error) {
                    });


                }
            }, function (error) {
                console.error(error);
            });
        } else {
            $scope.params = {};
            $scope.params.createdByName = saafsuccessLoginInfo.userFullName;
            $scope.params.creationDate = CurentTime();
            //当前登录人所属部门
            $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.params.supRecoverType = '10';
            $scope.params.supRecoverStatus = '10';
            $scope.changeShowFlag();
        }

        $scope.selectSupplierLov = function (key, value, currentList) {
            console.log(currentList)
            $scope.params.supplierStatus = currentList[0].supplierStatus;
            $scope.params.supplierDeptStatusName = currentList[0].supplierDeptStatusName;
            $scope.params.supplierId = currentList[0].supplierId;
            $scope.params.supplierNumber = currentList[0].supplierNumber;
            $scope.params.supplierName = currentList[0].supplierName;
        };

        function CurentTime()
        {
            var now = new Date();
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();           //秒
            var clock = year + "-";
            if(month < 10)
                clock += "0";
            clock += month + "-";
            if(day < 10)
                clock += "0";
            clock += day + " ";
            if(hh < 10)
                clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm + ":";
            if (ss < 10) clock += '0';
            clock += ss;
            return(clock);
        }
// ------------------------------------------------附件------------------------------------------------
        //上传附件
        $scope.uploadFile = function () {

            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[type=file]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.params.supSuspendId;
            if(!id){
                id = '0';
            }
            fd.append('bussnessId', id);
            fd.append('file', file);
            fd.append("fileName", fileName);
            $http.post(URLAPI.saveEquFileUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                console.log(response);
                SIEJS.alert(response.msg, 'success', '确定');
                $scope.params.fileId = response.data[0].fileId;
                $scope.params.fileName = response.data[0].fileName;
                $scope.params.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        $scope.changeFile = function () {
            $scope.params.fileId = null;
            $scope.params.filePath = null;
            $scope.params.fileName = null;
        }

        //弹出供应商
        $scope.getSupplierLov = function () {
            $('#supplierLov').modal('toggle')
        };
// ------------------------------------------------保存------------------------------------------------

        $scope.save = function (action) {
            if ($scope.params.supplierName == null || $scope.params.supplierName == ""
            ) {
                SIEJS.alert("请维护供应商信息。", "error", "确定");
            } else if ($scope.params.supRecoverReason == null || $scope.params.supRecoverReason == ""){
                SIEJS.alert("请填写恢复原因。", "error", "确定");
            }else {
                $scope.params.action = action;
                if(action ==='reject'&&$scope.params.rejectReason==null){
                    SIEJS.alert("请填写驳回原因。", "error", "确定");
                    return;
                }
                httpServer.post(URLAPI.saveEquPosRecover, {
                    'params': JSON.stringify($scope.params),
                }, function (res) {
                    if (res.status == "S") {
                        $scope.params.supRecoverCode = res.data.supRecoverCode;
                        $scope.params.supRecoverStatus = res.data.supRecoverStatus;
                        $scope.params.creationDate = res.data.creationDate;
                        $scope.params.supRecoverId = res.data.supRecoverId;
                        $scope.changeShowFlag();
                        SIEJS.alert("操作成功!", "success");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            }
        };

        $scope.search = function (supRecoverId) {
            $scope.params.supRecoverId = supRecoverId;
            httpServer.post(URLAPI.findSupRecoverDatail, {
                params: JSON.stringify($scope.params)
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    $scope.changeShowFlag();
                    $scope.processInstanceParams = {
                        processInstanceId: $scope.params.procInstId
                    };
                    $scope.taskTable.search();
                    $scope.params.supRecoverStatus == '20'
                }
            }, function (error) {
                console.error(error);
            });
        };

        /**********************************工作流配置**************************************/
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index+1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.params.supRecoverId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_HFSH.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };
        // tab 切换
        $scope.tabAction = 'requestFundsBusiness'; //根据页面配置
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if ((!$scope.processImgLoad) && (name == 'opinion')) {
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
                        businessKey:$scope.params.supRecoverId
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit = function(status){

            if($scope.params.supRecoverStatus != '10' && $scope.params.supRecoverStatus != '20'){
                SIEJS.alert('供应商恢复状态不是拟定或待审核，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend ={
                "tasks_assignees":[]
            };
            $scope.variables = []; //流程变量
            angular.forEach($scope.params, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties={
                "menucode": "HFSH",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_HFSH.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.supRecoverId, //单据ID
                "title": "供应商恢复审核流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.params.supRecoverCode
            };
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    // httpServer.post(URLAPI.saveEquPosRecoverSumbit, {
                    //     'params': JSON.stringify($scope.params)
                    // }, function (res) {
                    //     if (res.status == 'S') {
                    //         $scope.search($scope.params.supRecoverId);
                    //         $scope.params.supRecoverStatus == '20'
                    //         SIEJS.alert('提交成功');
                    //     }
                    //     else {
                    //         SIEJS.alert(res.msg, "error", "确定");
                    //     }
                    // }, function (err) {
                    //     SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // });
                    $scope.search($scope.params.supRecoverId);
                    $scope.params.supRecoverStatus == '20'
                    $scope.changeShowFlag();
                    SIEJS.alert('提交成功');
                }
                else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        }


    });
});
