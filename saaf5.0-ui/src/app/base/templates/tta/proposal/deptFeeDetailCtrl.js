/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('deptFeeDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        var id = $stateParams.id;

        $scope.deptFeeLADataTable = [];
        $scope.deptFeeLBDataTable = [];

        $scope.areaComponentA = {};
        $scope.areaComponentB = {};

        $scope.paramsA = {};

        $scope.paramsB = {};


        //查询单据信息
        $scope.searchA = function () {

            $scope.proposalId = id;

            httpServer.post(URLAPI.proposalFindById, {
                    'params': JSON.stringify({proposalId: $scope.proposalId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.paramsA = res.data;


                        $scope.searchLA();
                        $scope.searchLB();

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.searchLA = function () {



            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                    'params': JSON.stringify({proposalId: $scope.proposalId,accordType: 'A'})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLADataTable = res.data;




                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }




        $scope.searchLB = function () {



            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                    'params': JSON.stringify({proposalId: $scope.proposalId,accordType: 'B'})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLBDataTable = res.data;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }


















        //id不为空
        if ($stateParams.id) {

            //查询头信息
            $scope.searchA();
        } else {



        }





        $scope.btnSaveA = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            $scope.paramsA.lineA = $scope.deptFeeLADataTable;
            $scope.paramsA.lineB = $scope.deptFeeLBDataTable;

            httpServer.post(URLAPI.ttaDeptFeeHSave, {
                    'params': JSON.stringify($scope.paramsA)
                },
                function (res) {
                    if (res.status == 'S') {

                        $scope.searchA();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        }





        //选择部门
        $scope.getDeptCodeA = function (e) {
              $scope.areaComponentA = e;
            $('#deptFeeALov').modal('toggle')
        };

        //点击确认按钮
        $scope.selectDeptFeeAReturn = function (key, value, currentList) {
            if($scope.areaComponentA=="1") {
                $scope.paramsA.deptCode1 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc1 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="2") {
                $scope.paramsA.deptCode2 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc2 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="3") {
                $scope.paramsA.deptCode3 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc3 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="4") {
                $scope.paramsA.deptCode4 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc4 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="5") {
                $scope.paramsA.deptCode5 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc5 = currentList[0].departmentName;
            }

        }






        //部门协定确认
        $scope.confirmDeptFeeH = function () {

            SIEJS.confirm('提示', '确认的部门协定收费标准信息？', '确定', function () {
            httpServer.post(URLAPI.ttaDeptFeeHConfirm,
                {params: JSON.stringify({proposalId: $scope.paramsA.proposalId})},
                function (res) {
                    if (res.status == "S") {

                        //var xFlag = res.data.xFlag;
                        //var xMsg = res.data.xMsg;
                        //if (xFlag != 1) {
                        //    SIEJS.alert(xMsg, "error", "确定");
                        //    return;
                        //}
                        //
                        //$scope.searchBrandPlnL();
                        $scope.searchA();
                        $scope.searchB();
                        SIEJS.alert("处理成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
                })



        }

        //部门协定取消确认
        $scope.CancelConfirmDeptFeeH = function () {

            SIEJS.confirm('提示', '取消部门协定收费标准信息？', '确定', function () {
            httpServer.post(URLAPI.ttaDeptFeeHCancelConfirm,
                {params: JSON.stringify({proposalId: $scope.paramsA.proposalId})},
                function (res) {
                    if (res.status == "S") {

                        //var xFlag = res.data.xFlag;
                        //var xMsg = res.data.xMsg;
                        //if (xFlag != 1) {
                        //    SIEJS.alert(xMsg, "error", "确定");
                        //    return;
                        //}
                        //
                        //$scope.searchBrandPlnL();
                        $scope.searchA();
                        $scope.searchB();
                        SIEJS.alert("处理成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
            })
        }


        //部门协定计算
        $scope.CountDeptFeeH = function () {
            var msMsg = "计算部门协定收费标准信息";
            if($scope.deptFeeLBDataTable!=null||$scope.deptFeeLADataTable!=null){
                msMsg = "计算部门协定收费标准信息前行信息将清空！";
            }

            SIEJS.confirm('提示', msMsg, '确定', function () {
            httpServer.post(URLAPI.ttaDeptFeeHCount,
                {params: JSON.stringify({proposalId: id})},
                function (res) {
                    if (res.status == "S") {

                        var xFlag = res.data.xFlag;
                        var xMsg = res.data.xMsg;
                        if (xFlag != 1) {
                            SIEJS.alert(xMsg, "error", "确定");
                            return;
                        }

                        $scope.searchA();
                        SIEJS.alert("处理成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
            })
        }








    });
});
