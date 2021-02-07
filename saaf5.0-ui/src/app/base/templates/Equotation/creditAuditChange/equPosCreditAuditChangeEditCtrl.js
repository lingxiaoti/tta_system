/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload', 'XLSX','GooFlow'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPosCreditAuditChangeEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                                saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,$controller) {
        // ------------------------------------------------初始化------------------------------------------------
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        $scope.params = {}
        $scope.param = {}
        $scope.param.businessFlag = true;
        var changeCreditAuditHId = $stateParams.changeCreditAuditHId
        if ($stateParams.changeCreditAuditHId){
            changeCreditAuditHId = $stateParams.changeCreditAuditHId;
        }else if ($scope.urlParams.businessKey){
            changeCreditAuditHId = $scope.urlParams.businessKey;
            $scope.param.businessFlag = false;
        }

        $scope.dataTable = {};
        $scope.rowsIndex = 0;

        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        $scope.param.deptCode = saafsuccessLoginInfo.deptCode;
        $scope.param.deptName = saafsuccessLoginInfo.deptName;
// ------------------------------------------------调整------------------------------------------------
        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag = false;
            $scope.approveFlag = false;
            $scope.rejectFlag = false;
            $scope.cancelFlag = false;
            $scope.editFlag = false;
            if ($scope.params.changeCreditAuditStatus != '20' && $scope.params.changeCreditAuditStatus != '30') {
                $scope.saveFlag = true;  //提交与审批状态不能保存
                $scope.submitFlag = true;
            }
            if ($scope.params.changeCreditAuditStatus == '20') {
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag = true;
            }
            if ($scope.params.changeCreditAuditStatus == '50' && saafsuccessLoginInfo.userId == $scope.params.createdBy) {
                $scope.cancelFlag = true;   //驳回状态本人可以取消
            }
            if ($scope.params.changeCreditAuditStatus == '10' || $scope.params.changeCreditAuditStatus == '50') {
                $scope.editFlag = true; //拟定与驳回状态才能编辑
            }
        }

        /**** 新增或者保存 ***/
        if (changeCreditAuditHId) {
            $scope.params.changeCreditAuditHId = changeCreditAuditHId;
            httpServer.post(URLAPI.findSupCreditAuditChangeDatail, {
                // params: JSON.stringify($scope.params)
                params: JSON.stringify({
                    changeCreditAuditHId: changeCreditAuditHId
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data;
                    $scope.changeShowFlag();
                    $scope.dataTable.getData();
                    $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;

                    //查询各个子信息
                    $scope.historicParam = {}
                    $scope.historicParam.code = $scope.params.changeCreditAuditCode
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
            $scope.params.createdByName = saafsuccessLoginInfo.userFullName;
            $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
            $scope.params.creationDate = CurentTime();
            $scope.params.supCreditAuditChangeType = '10';
            $scope.params.changeCreditAuditStatus = '10';
            $scope.params.isSpecial = 'N';
            $scope.changeShowFlag();
        }

        $scope.selectSupplierLov = function (key, value, currentList) {
            var row = $scope.dataTable.data[$scope.index]
            row.supplierId = currentList[0].supplierId;
            row.supplierNumber = currentList[0].supplierNumber;
            row.supplierName = currentList[0].supplierName;
        };
        $scope.selectQualificationLov = function (key, value, currentList) {

            $scope.params.qualificationCode = currentList[0].qualificationNumber;
            $scope.params.qualificationId = currentList[0].qualificationId;
        }


        $scope.getSupplierCode = function (index) {
            $scope.index = index;
            $('#supplierLov').modal('toggle')
        };


        $scope.getQualificationCode = function () {
            $('#qualificationLov').modal('toggle')
        };

        $scope.changeLineScore = function (row) {
            if (row.creditAuditScore <= 40) {
                row.specialResults = 'Y';
            } else {
                row.specialResults = 'N';
            }
        }



        /**
         * 切换是否特批
         */
        $scope.changeIsSpecial = function (str) {
            $scope.params.isSpecial = str;

        }

        /**
         * 导入
         */
        $scope.saveImportChange = function (str) {

        }

        function CurentTime() {
            var now = new Date();
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();           //秒
            var clock = year + "-";
            if (month < 10)
                clock += "0";
            clock += month + "-";
            if (day < 10)
                clock += "0";
            clock += day + " ";
            if (hh < 10)
                clock += "0";
            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm + ":";
            if (ss < 10) clock += '0';
            clock += ss;
            return (clock);
        }

// ------------------------------------------------附件------------------------------------------------
        //上传附件
        $scope.uploadFile = function () {

            var fd = new FormData();
            var file = document.querySelector('#file').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = $scope.params.changeCreditAuditHId;
            if (!id) {
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
            }).error(function (response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }

        $scope.changeFile = function () {
            $scope.params.fileId = null;
            $scope.params.filePath = null;
            $scope.params.fileName = null;
        }

        //上传行附件
        $scope.uploadProfile = function () {
            var row = $scope.dataTable.data[$scope.dataTable.data.selectRow];
            var fd = new FormData();
            var file = document.querySelector('#file' + row.index).files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var id = row.fileId;
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
                row.fileId = response.data[0].fileId;
                row.fileName = response.data[0].fileName;
                row.filePath = response.data[0].filePath;
            }).error(function(response) {
                SIEJS.alert(response.msg, 'error', '确定');
            });
        }

        $scope.changeFile = function (row) {
            row.fileId = null;
            row.filePath = null;
            row.fileName = null;
        }

        $scope.deleteData = function (item,index) {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.deleteCreditAuditLine, {
                    params: JSON.stringify(item,index)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data.splice(index, 1);// 删除当前选中
                        SIEJS.alert("删除成功", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            })
        }
// ------------------------------------------------保存------------------------------------------------

        $scope.addL = function (action) {
                if (!$scope.dataTable.data) {
                    $scope.dataTable.data = [];
                }
                //$scope.addButton = true;
                var obj = {
                    sceneTypeName: "年度信用审核",
                    supplierNumber: "",
                    unitOfMeasure: "",
                    lengthValue: "",
                    widthValue: "",
                    heightValue: "",
                    itemPrice: "",
                    index : $scope.rowsIndex
                };
                if (!$scope.dataTable.data) {
                    $scope.dataTable.data.push(obj);
                } else {
                    $scope.dataTable.data.unshift(obj);
                }
            $scope.rowsIndex = $scope.rowsIndex-1;
        }

        $scope.save = function (action) {
            $scope.params.action = action;
            $scope.params.line = $scope.dataTable.data;
            httpServer.post(URLAPI.saveEquPosCreditAuditChange, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    $scope.params.changeCreditAuditCode = res.data.changeCreditAuditCode;
                    $scope.params.changeCreditAuditStatus = res.data.changeCreditAuditStatus;
                    $scope.params.creationDate = res.data.creationDate;
                    $scope.params.changeCreditAuditHId = res.data.changeCreditAuditHId;
                    $scope.changeShowFlag();
                    $scope.dataTable.getData();
                    SIEJS.alert("操作成功!", "success");
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        /**********************************工作流配置1**************************************/
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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.params.changeCreditAuditHId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'EQU_XYGXSP.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey:$scope.params.changeCreditAuditHId
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };
        //先判断是否业务form是否有id 再判断流程form的id
        // if ($stateParams.qualificationId){
        //     $scope.id = $stateParams.qualificationId;
        // }else if ($scope.urlParams.businessKey){
        //     $scope.id = $scope.urlParams.businessKey;
        // }

        $scope.btnSubmit = function () {

            SIEJS.confirm('提交', '是否确定提交？', '确定', function () {
                $scope.btnSubmit2();
            })
        }

        /**********************************工作流配置**************************************/

        //提交审批
        $scope.btnSubmit2 = function(){
            if($scope.params.changeCreditAuditStatus != '10' && $scope.params.changeCreditAuditStatus != '40'){
                SIEJS.alert('状态不是拟定，不允许提交审批', 'error', '确定');
                return;
            }
            $scope.extend ={
                "tasks_assignees":[]
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.params, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties={
                "menucode": "XYGXSP",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "EQU_XYGXSP.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.changeCreditAuditHId, //单据ID
                "title": "信用审核导入变更流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.params.changeCreditAuditCode
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params.changeCreditAuditStatus = '20'
                    SIEJS.alert('提交成功');
                    $scope.changeShowFlag();
                    // $scope.search($scope.params.changeCreditAuditHId);
                    // httpServer.post(URLAPI.saveEquPosCreditAuditChangeSubmit, {
                    //     'params': JSON.stringify($scope.params)
                    // }, function (res) {
                    //     if (res.status == 'S') {
                    //         $scope.params.changeCreditAuditStatus = '20'
                    //     }
                    //     else {
                    //         SIEJS.alert(res.msg, "error", "确定");
                    //     }
                    // }, function (err) {
                    //     SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // });
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
