/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','GooFlow'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('equPonStandardsEditCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas, $window,
                                                        saveUserResp, userSave, $timeout, $scope, httpServer, URLAPI, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,$controller) {
        $controller('processBase', {$scope:$scope}); // 继承基础的流程控制器
        // ------------------------------------------------初始化------------------------------------------------
        // var standardsId = $stateParams.standardsId
        if ($stateParams.standardsId){
            var standardsId = $stateParams.standardsId;
        }else if ($scope.urlParams.businessKey){
            var standardsId = $scope.urlParams.businessKey;
        }
        $scope.params = {}
        $scope.param = {}
        $scope.param.selectId = standardsId;
        $scope.dataTable = {};
        $scope.columns = []
        $scope.lineNumber = 1;
        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        $scope.param.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.param.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
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

// ------------------------------------------------调整------------------------------------------------
        $scope.changeShowFlag = function () {
            $scope.saveFlag = false;
            $scope.submitFlag = false;
            $scope.approveFlag = false;
            $scope.rejectFlag = false;
            $scope.cancelFlag = false;
            $scope.editFlag = false;
            if ($scope.params.standardsStatus != '20' && $scope.params.standardsStatus != '30') {
                $scope.saveFlag = true;  //提交与审批状态不能保存
                $scope.submitFlag = true;
            }
            if ($scope.params.standardsStatus == '20') {
                $scope.approveFlag = true;  //提交与审批状态不能保存
                $scope.rejectFlag = true;
            }
            if ($scope.params.standardsStatus == '50' && saafsuccessLoginInfo.userId == $scope.params.createdBy) {
                $scope.cancelFlag = true;   //驳回状态本人可以取消
            }
            if ($scope.params.standardsStatus == '10' || $scope.params.standardsStatus == '50') {
                $scope.editFlag = true; //拟定与驳回状态才能编辑
            }
        }
        $scope.initialize = function (standardsId) {
            if (standardsId) {
                $scope.params.standardsId = standardsId;
                console.log($scope.params)
                httpServer.post(URLAPI.findPonStandardsDatail, {
                    params: JSON.stringify({
                        standardsId: standardsId
                    })
                }, function (res) {
                    if (res.status == "S") {

                        $scope.processInstanceParams = {
                            processInstanceId: $scope.params.procInstId
                        };
                        $scope.params = res.data.hEntity;
                        $scope.dataTable.data = res.data.lineData;
                        $scope.columns = [];
                        for (var i = 1; i <= $scope.params.tableLv; i++) {
                            var obj = {
                                column: i + "级权重(%)",
                                lineLv: i
                            };
                            $scope.columns.push(obj);
                        }
                        $scope.changeShowFlag();

                        //查询各个子信息
                        $scope.historicParam = {}
                        $scope.historicParam.code = $scope.params.standardsCode
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
                $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
                $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
                $scope.params.standardsStatus = '10';
                $scope.params.tableLv = 1;
                var obj = {
                    column: "1级权重(%)",
                    lineLv: 1
                };
                $scope.columns.push(obj);
                $scope.changeShowFlag();
                //    IT的新增  1、第一层默认分为“1非价格”、“2价格”(不可增加子层)，“1非价格”默认分为第二层“1.1IT部门”、“1.2用户部门”(可新增子层)；
                if($scope.params.deptName=='IT'){
                    $scope.params.tableLv = 2;
                    obj = {
                        column: "2级权重(%)",
                        lineLv: 2
                    };
                    $scope.columns.push(obj);
                    $scope.dataTable.data = [];
                    var row = {
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "1 非价格",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    row = {
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "1.1 IT部门",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    row = {
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "1.2 用户部门",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "2 价格",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    $scope.lineNumber = 3;
                } else if($scope.params.deptName!='IT'){
                    $scope.params.tableLv = 2;
                    obj = {
                        column: "2级权重(%)",
                        lineLv: 2
                    };
                    $scope.columns.push(obj);
                    $scope.dataTable.data = [];
                    var row = {
                        lineNumber: 1,
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "Cost",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    row = {
                        lineNumber: 1.1,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Cost",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    row = {
                        lineNumber: 2,
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "Product related",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 2.1,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Panel test",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 2.2,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Factory audit",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 2.3,
                        lineLv: 2,
                        isAdd: "Y",
                        // gradingDivision: "New concept from supplier",
                        gradingDivision: "Innovation",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 2.4,
                        lineLv: 2,
                        isAdd: "Y",
                        // gradingDivision: "Component support",
                        gradingDivision: "PKG Innovation",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 3,
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "Service & NPD support",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 3.1,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Effective & accurate feedback",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 3.2,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Payment terms",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 3.3,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Current supplier performance",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 4,
                        lineLv: 1,
                        isAdd: "Y",
                        gradingDivision: "Commercial evaluation",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 4.1,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Commercial contract",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    var row = {
                        lineNumber: 4.2,
                        lineLv: 2,
                        isAdd: "Y",
                        gradingDivision: "Supplier spend profile",
                        index: $scope.rowsIndex
                    };
                    $scope.dataTable.data.push(row);
                    $scope.lineNumber = 3;
                }

            }
        }

        $scope.submitInitialize = function (standardsId) {
            if (standardsId) {
                $scope.params.standardsId = standardsId;
                console.log($scope.params)
                httpServer.post(URLAPI.findPonStandardsDatail, {
                    params: JSON.stringify($scope.params)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.params = res.data.hEntity;
                    }
                }, function (error) {
                    console.error(error);
                });
            }
        }

        $scope.initialize(standardsId);

        $scope.getStandardsLov = function (action) {
            $scope.param.action = action;
            if(action=='copy'){
                SIEJS.confirm('复制评分标准,将删除所有行数据', '是否确定？', '确定', function () {
                    $('#standardsLov').modal('toggle')
                })
            }else if (action=='parent'){
                $('#standardsLov').modal('toggle')
            }
        };

        $scope.selectStandardsLov = function (key, value, currentList) {
            var action = $scope.param.action;
            if(action=='copy'){
                $scope.param.standardsId = currentList[0].standardsId;
                $scope.param.deleteId = $scope.params.standardsId;
                $scope.params.copyStandardsCode = currentList[0].standardsCode;
                httpServer.post(URLAPI.findCopyStandardsLov, {
                    params: JSON.stringify($scope.param)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data = res.data.lineData;
                        console.log(res.data.lineData);
                        $scope.columns = [];
                        $scope.params.tableLv = res.data.hEntity.tableLv
                        for (var i = 1; i <= $scope.params.tableLv; i++) {
                            var obj = {
                                column: i + "级权重(%)",
                                lineLv: i
                            };
                            $scope.columns.push(obj);
                        }
                    }
                }, function (error) {
                    console.error(error);
                });
            } else if(action=='parent'){
                $scope.params.parentStandardsCode = currentList[0].standardsCode;
            }

        }

// ------------------------------------------------删除行------------------------------------------------

        $scope.deleteData = function (row, index) {
            var nextLine = $scope.dataTable.data[index+1];
            if(nextLine&&row.lineLv < nextLine.lineLv){
                SIEJS.alert("存在下级评分标准,无法删除", "error", "确定");
            }else{
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    httpServer.post(URLAPI.deletePonStandardsLine, {
                        params: JSON.stringify(row)
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
        }
// ------------------------------------------------新增行------------------------------------------------

        $scope.addL = function () {
            if (!$scope.dataTable.data) {
                $scope.dataTable.data = [];
            }
            var row = {
                lineLv: 1,
                isAdd: "Y",
                supplierNumber: "",
                index: $scope.rowsIndex
            };
            $scope.dataTable.data.push(row);
            $scope.lineNumber = $scope.lineNumber + 1;
        }

        $scope.add = function (line, index) {
            if (!$scope.dataTable.data) {
                $scope.dataTable.data = [];
            }
            $scope.copyData = [];
            if ($scope.params.tableLv == line.lineLv) {
                $scope.params.tableLv = $scope.params.tableLv + 1
                var obj = {
                    column: $scope.params.tableLv + "级权重(%)",
                    lineLv: $scope.params.tableLv
                };
                $scope.columns.push(obj);
            }
            var row = {
                lineLv: line.lineLv + 1,
                isAdd: "Y",
                supplierNumber: "",
                index: $scope.rowsIndex
            };
            for (var i = 0; i < $scope.dataTable.data.length; i++) {
                console.log($scope.dataTable.data[i].lineLv)
                $scope.copyData.push($scope.dataTable.data[i])
                if (index == i) {
                    $scope.copyData.push(row)
                }
            }
            $scope.dataTable.data = $scope.copyData;
        }

// ------------------------------------------------保存------------------------------------------------

        $scope.updateAction = function (action) {
            $scope.params.action = action;
        }
        $scope.btnSubmit1 = function(){
            if($scope.params.standardsName ==null){
                SIEJS.alert("评分名称不能为空!", "success");
            }else if($scope.params.score ==null&&$scope.param.deptName=='IT'){
                SIEJS.alert("总分不能为空!", "success");
            }else{
                $scope.params.action = 'submit';
                $scope.save();
            }
        }


        $scope.save = function () {

            $scope.params.line = $scope.dataTable.data;
            httpServer.post(URLAPI.savePonStandards, {
                'params': JSON.stringify($scope.params),
            }, function (res) {
                if (res.status == "S") {
                    $scope.params.standardsId = res.data.standardsId;
                    $scope.initialize($scope.params.standardsId);
                    if($scope.params.action == 'submit'){
                        $scope.btnSubmit();
                    }else{
                        SIEJS.alert("操作成功!", "success");
                    }
                    $scope.changeShowFlag();
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                    if(res.data.standardsId!=null){
                        $scope.submitInitialize(res.data.standardsId)
                    }
                }
            }, function (error) {
                console.error(error);
            });
        }

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
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.params.standardsId;
        }

        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate')||localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'PFBZSPOEM.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
                        businessKey:$scope.params.standardsId
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
        $scope.btnSubmit = function(){
            if($scope.params.standardsStatus != '10' && $scope.params.standardsStatus != '40'){
                SIEJS.alert('状态不是拟定或驳回，不允许提交审批', 'error', '确定');
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
                "menucode": "PFBZSP",
                "opinion": ""
            };
            $scope.paramsBpm={
                "extend":$scope.extend,
                "variables":$scope.variables,
                "properties":$scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": "PFBZSPOEM.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.standardsId, //单据ID
                "title": "评分标准审批流程", //流程标题
                "positionId": 0,
                "orgId": 0,
                "userType": "30",
                "billNo": $scope.params.standardsCode
            }
            httpServer.post(URLAPI.bpmStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params.standardsStatus = '20'
                    $scope.changeShowFlag();
                    SIEJS.alert('提交成功');
                    // httpServer.post(URLAPI.savePonStandardsSubmit, {
                    //     'params': JSON.stringify($scope.params)
                    // }, function (res) {
                    //     if (res.status == 'S') {
                    //         // $scope.search($scope.params.supSuspendId);
                    //         $scope.params.standardsStatus = '20'
                    //         SIEJS.alert('提交成功');
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
