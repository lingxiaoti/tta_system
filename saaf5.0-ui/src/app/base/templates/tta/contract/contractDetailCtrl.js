/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('contractDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction) {
        var id = $stateParams.id;


        $scope.params = {};
        $scope.contractLineDataTable = [];
        $scope.contractDetailDataTable = [];

        $scope.status = {};


        //查询单据信息
        $scope.search = function () {

            $scope.contractHId = id;
            $scope.lineParams = {contractHId: $scope.contractHId};
            $scope.detailParams = {contractHId: $scope.contractHId};

            httpServer.post(URLAPI.contractHeaderFindById, {
                    'params': JSON.stringify({contractHId: $scope.contractHId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data;
                        $scope.status = $scope.params.billStatus;

                        $scope.searchLine();
                        $scope.searchDetail();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.searchLine = function () {



            httpServer.post(URLAPI.contractLineFind, {
                    'params': JSON.stringify($scope.lineParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractLineDataTable = res.data;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.searchDetail = function () {



            httpServer.post(URLAPI.contractDetailFind, {
                    'params': JSON.stringify($scope.detailParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractDetailDataTable = res.data;


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
            $scope.search();
        } else {
            $scope.params.billStatus = 'A';
            $scope.params.isSplit = 'N';
            $scope.params.isMultipleVendor = 'N';
        }


        $scope.btnSave = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.contractHeaderSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.contractHId;
                        $scope.search();
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




        //选择供应商
        $scope.getVendorNbr = function () {
            //  $scope.areaComponent = e;
            $('#prandcontractCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectcontractCodeReturn = function (key, value, currentList) {

            $scope.params.vendorNbr = currentList[0].supplierCode;
            $scope.params.vendorName = currentList[0].supplierName;

        }


        //选择proposal信息
        $scope.getProposalCode = function () {
            //  $scope.areaComponent = e;
            $('#latentcontractCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectLatentcontractReturn = function (key, value, currentList) {
            $scope.params.proposalCode = currentList[0].orderNbr;
            $scope.params.proposalYear = currentList[0].proposalYear;
            $scope.params.proposalVersion = currentList[0].versionCode;

        }



        //合共信息提交
        $scope.btnSubmit = function () {

            SIEJS.confirm('提示', '确认的提交合同信息？', '确定', function () {
                httpServer.post(URLAPI.contractHeaderConfirm,
                    {params: JSON.stringify({contractHId: $scope.contractHId})},
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
                            $scope.search();

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

        //合同信息作废
        $scope.btnDiscard = function () {

            SIEJS.confirm('提示', '确认作废合同信息？', '确定', function () {
                httpServer.post(URLAPI.contractHeaderCancelConfirm,
                    {params: JSON.stringify({contractHId: $scope.contractHId})},
                    function (res) {
                        if (res.status == "S") {


                            $scope.search();

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
        $scope.CountContractH = function () {
            var msMsg = "计算合同信息";
            if($scope.contractLineDataTable!=null||$scope.contractDetailDataTable!=null){
                msMsg = "计算合同信息前行信息将清空！";
            }

            SIEJS.confirm('提示', msMsg, '确定', function () {
                httpServer.post(URLAPI.contractHeaderCount,
                    {params: JSON.stringify({contractHId: $scope.contractHId})},
                    function (res) {
                        if (res.status == "S") {

                            var xFlag = res.data.xFlag;
                            var xMsg = res.data.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }

                            $scope.search();
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
