'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('irTermsCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        $scope.params = {}
        $scope.logoImg = []
        $scope.batchId = $stateParams.batchId;
        $scope.params.batchId = $stateParams.batchId;

        $scope.btnModify=function(){
            $scope.addParams = {};
            $scope.modalTitle='编辑';
            $('#msgCfgModal').modal('toggle');
            $scope.addParams.bicRemark = $scope.dataTable.selectRow.bicRemark;
            $scope.addParams.followUpAction = $scope.dataTable.selectRow.followUpAction;
            $scope.addParams.extraPoPlanning = $scope.dataTable.selectRow.extraPoPlanning;
            $scope.addParams.remark = $scope.dataTable.selectRow.remark;

        }

        $scope.changePerson = function () {
            if(!$scope.dataTable.selectRow){
                SIEJS.alert('请选择数据！', 'warning', '确定');
                return ;
            }

            $('#userLov').modal('toggle');
        }

        $scope.selecUserReturn = function (key, value, currentList) {
            if(!$scope.dataTable.selectRow){
                SIEJS.alert("请先选择对应的行", "error", "确定");
                return;
            }
            if(currentList.length == 0){
                return ;
            }
            $scope.dataTable.selectRow.changePerson = currentList[0].userId;
            $scope.dataTable.selectRow.changePersonName = currentList[0].userFullName;
            httpServer.post(URLAPI.attendanceFeeSave, {
                params: JSON.stringify($scope.dataTable.selectRow)
            }, function (res) {
                if ('S' == res.status) {
                    JS.alert('保存成功');
                    $scope.dataTable.search();
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })
        }


        $scope.submit = function (item) {
            if($scope.dataTable.selectRow.status==1){
                return;
            }else{
                $scope.dataTable.selectRow.status = '1';
            }

            httpServer.post(URLAPI.irTermsSave, {
                params: JSON.stringify($scope.dataTable.selectRow)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.dataTable.search();
                    JS.alert('提交成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('提交失败', 'error', '确定');
            })

        }


        $scope.btnSave=function(){
            $scope.dataTable.selectRow.bicRemark = $scope.addParams.bicRemark;
            $scope.dataTable.selectRow.followUpAction = $scope.addParams.followUpAction;
            $scope.dataTable.selectRow.extraPoPlanning = $scope.addParams.extraPoPlanning;
            $scope.dataTable.selectRow.remark = $scope.addParams.remark;
            httpServer.post(URLAPI.irTermsSave, {
                params: JSON.stringify($scope.dataTable.selectRow)
            }, function (res) {
                if ('S' == res.status) {
                    $('#msgCfgModal').modal('toggle')
                    $scope.dataTable.search();
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })

        }



        //清除值
        $scope.paramsCancel = function (value) {
            $scope.params.deptCode = '';
            $scope.params.deptDesc = '';
        };

        //勾选部门
        $scope.getDeptCode = function (e) {
            $('#deptFeeALov').modal('toggle')
        };

        $scope.selectDeptFeeAReturn = function (key, value, currentList) {
            if (currentList && currentList.length > 0) {
                $scope.params.deptCode = '';
                $scope.params.deptDesc = '';
                for (var idx = 0; idx < currentList.length; idx ++) {
                    $scope.params.deptCode += currentList[idx].departmentCode;
                    $scope.params.deptDesc += currentList[idx].departmentName;
                    if (currentList.length != idx + 1) {
                        $scope.params.deptCode +=  ",";
                        $scope.params.deptDesc +=  ",";
                    }
                }
            }
        }


        //提交
        $scope.submitApproval = function (form) {
            if($scope.dataTable.selectRow.headerStatus != 'DS01'){
                SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                return;
            }

            $scope.extend = {
                "tasks_assignees": []
            }
            $scope.variables = []; //流程变量
            angular.forEach($scope.dataTable.selectRow, function (value, key) {
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
                "processDefinitionKey": "REPORT.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.dataTable.selectRow.batchId, //单据ID
                "title": "IR Term Checking Report 审批" + $scope.dataTable.selectRow.batchId, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.dataTable.selectRow.batchId
            };
            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    SIEJS.alert("提交成功", "success", "确定");
                }
                else {
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
