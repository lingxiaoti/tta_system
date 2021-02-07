/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('dealerApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,pageResp,httpServer,URLAPI) {
        $scope.params = {};
        // 设置行数据里的 LOV 控件赋值   转入
        //转入经销商
        $timeout(function () {
            $scope.localData = JSON.parse(localStorage.getItem("OA_currentResp"));
            $scope.responsibilityId = $scope.localData[0].resp.responsibilityId;
            $scope.warehouseParams = {
                respId:$scope.responsibilityId
            };
        },500);
        //获取orgId
        $scope.respCallback=function(respId, respList ,curentResp){
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.customerParams = {orgId:curentResp.proFileBeans[i].profileValue};
                    }
                }
            }
        }
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
            // console.log($scope.fileData)
            // console.log($scope.formParams.fileIds)
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;

        if($scope.action == 'new'){
            $scope.fileData = []; // 上传文件
        }
        $scope.setRowInput1 = function (key,value,item) {
            var row = $scope.formParams.items[$scope.rowIndex1];
            
            row.deCode = item[0].customerNumber;
            row.deName = item[0].customerName;
            // row.oldHrOrganizationId  = item[0].customerId;
        };
        // 记录当前行index
        $scope.setRowIndex1 = function (index) {
            $scope.rowIndex1 = index;
            //$("#assetMaterialModel").modal('show');
            //取消红框
            angular.forEach($scope.formParams.items,function(data,indexs){
                $("#formParams_items_deCode_"+indexs).css("border","1px solid #cccccc");
            })
            angular.forEach($scope.formParams.secItems,function(data,indexs){
                $("#formParams_secItems_deCode_"+indexs).css("border","1px solid #cccccc");
            })
        }
        //转入SKU产品
        $scope.setRowInput3 = function (key,value,item) {
            var row = $scope.formParams.items[$scope.rowIndex3];
            
            row.pSku = item[0].itemCode;
            row.pSkuName = item[0].itemName;
            // row.oldHrOrganizationId  = item[0].customerId;
        };
        // 记录当前行index
        $scope.setRowIndex3 = function (index) {
            $scope.rowIndex3 = index;
            //$("#assetMaterialModel").modal('show');
        }
        // 设置行数据里的 LOV 控件赋值  转出
        //转出经销商
        $scope.setRowInput2 = function (key,value,item) {
            var row = $scope.formParams.secItems[$scope.rowIndex2];
            
            row.deCode = item[0].customerNumber;
            row.deName = item[0].customerName;
            // row.oldHrOrganizationId  = item[0].customerId;
        };
        // 记录当前行index
        $scope.setRowIndex2 = function (index) {
            $scope.rowIndex2 = index;
            //$("#assetMaterialModel").modal('show');
            //取消红框
            angular.forEach($scope.formParams.items,function(data,indexs){
                $("#formParams_items_deCode_"+indexs).css("border","1px solid #cccccc");
            })
            angular.forEach($scope.formParams.secItems,function(data,indexs){
                $("#formParams_secItems_deCode_"+indexs).css("border","1px solid #cccccc");
            })
        }
        //转出产品
        $scope.setRowInput4 = function (key,value,item) {
            var row = $scope.formParams.secItems[$scope.rowIndex4];
            
            row.pSku = item[0].itemCode;
            row.pSkuName = item[0].itemName;
            // row.oldHrOrganizationId  = item[0].customerId;
        };
        // 记录当前行index
        $scope.setRowIndex4 = function (index) {
            $scope.rowIndex4 = index;
            //$("#assetMaterialModel").modal('show');
        }

        $scope.pageView.getBusinessFormCallback = function(res){
            if (res.status === 'S') {
                var data = res.data;
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId; 
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            } 
        };


        //保存前行为
        $scope.draftEvent.cusValidator=function(){
            var switchStr = true;
            //转出sku填写 数量必填
            angular.forEach($scope.formParams.items,function(data,index){
                if(data.pSku){
                    if(!data.pNum){
                        $("#formParams_items_pNum_"+index).css("border","1px solid #ff0000");
                        switchStr = false;
                    }
                }
                $("#formParams_items_pNum_"+index).focus(function(){
                    $(this).css("border","1px solid #cccccc");
                })
            });
            //转入sku填写 数量必填
            angular.forEach($scope.formParams.secItems,function(data,index){
                if(data.pSku){
                    if(!data.pNum){
                        $("#formParams_secItems_pNum_"+index).css("border","1px solid #ff0000");
                        switchStr = false;
                    }
                }
                $("#formParams_secItems_pNum_"+index).focus(function(){
                    $(this).css("border","1px solid #cccccc");
                })
            });
            //转出转入经销商不重复
            var t = true;
            var y = true;
            angular.forEach($scope.formParams.items,function(data,index){
                angular.forEach($scope.formParams.secItems,function(datas,indexs){
                    if(data.deCode == datas.deCode){
                        $("#formParams_items_deCode_"+index).css("border","1px solid #ff0000");
                        SIEJS.alert('转出转入经销商不可以重复','error',"确定");
                        switchStr = false;
                        t = false;
                    }
                });
                if(t){
                    $("#formParams_items_deCode_"+index).css("border","1px solid #cccccc");
                }
            });
            angular.forEach($scope.formParams.secItems,function(data,index){
                angular.forEach($scope.formParams.items,function(datas,indexs){
                    if(data.deCode == datas.deCode){
                        $("#formParams_secItems_deCode_"+index).css("border","1px solid #ff0000");
                        SIEJS.alert('转出转入经销商不可以重复','error',"确定");
                        switchStr = false;
                        y = false;
                    }
                });
                if(y){
                    $("#formParams_secItems_deCode_"+index).css("border","1px solid #cccccc");
                }
            });
            $scope.getFileIds()

            return switchStr;
        }
        //提交前行为
        $scope.submitEvent.cusValidator=function(){
            var switchStr = true;
            //转出sku填写 数量必填
            angular.forEach($scope.formParams.items,function(data,index){
                if(data.pSku){
                    if(!data.pNum){
                        $("#formParams_items_pNum_"+index).css("border","1px solid #ff0000");
                        switchStr = false;
                    }
                }
                $("#formParams_items_pNum_"+index).focus(function(){
                    $(this).css("border","1px solid #cccccc");
                })
            });
            //转入sku填写 数量必填
            angular.forEach($scope.formParams.secItems,function(data,index){
                if(data.pSku){
                    if(!data.pNum){
                        $("#formParams_secItems_pNum_"+index).css("border","1px solid #ff0000");
                        switchStr = false;
                    }
                }
                $("#formParams_secItems_pNum_"+index).focus(function(){
                    $(this).css("border","1px solid #cccccc");
                })
            });
            //转出转入经销商不重复
            var t = true;
            var y = true;
            angular.forEach($scope.formParams.items,function(data,index){
                angular.forEach($scope.formParams.secItems,function(datas,indexs){
                    if(data.deCode == datas.deCode){
                        $("#formParams_items_deCode_"+index).css("border","1px solid #ff0000");
                        SIEJS.alert('转出转入经销商不可以重复','error',"确定");
                        switchStr = false;
                        t = false;
                    }
                });
                if(t){
                    $("#formParams_items_deCode_"+index).css("border","1px solid #cccccc");
                }
            });
            angular.forEach($scope.formParams.secItems,function(data,index){
                angular.forEach($scope.formParams.items,function(datas,indexs){
                    if(data.deCode == datas.deCode){
                        $("#formParams_secItems_deCode_"+index).css("border","1px solid #ff0000");
                        SIEJS.alert('转出转入经销商不可以重复','error',"确定");
                        switchStr = false;
                        y = false;
                    }
                });
                if(y){
                    $("#formParams_secItems_deCode_"+index).css("border","1px solid #cccccc");
                }
            });
            $scope.getFileIds()

            return switchStr;
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateApDetrvByApproval");
            }
        });
        //流程审批，附件上传接口
        $scope.saveFile = function () {
            $scope.getFileIds();
            var p = {
                oaBusinessId :$scope.formParams.requestNum,
                oaFunctionId :$scope.formParams.oaFunctionId,
                fileIds:$scope.formParams.fileIds
            }
            httpServer.post(URLAPI.uploadFile, {
                params: JSON.stringify(p)
            },function(res) {
                if(res.status === 'S') {

                }else {
                    SIEJS.alert(res.msg,'error','确定');
                }
            },function(err) {
                SIEJS.alert('获取数据失败','error','确定');
            })
        }

        //通过前回调
        $scope.passEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }
        //驳回前回调
        $scope.refusalEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }
        //驳回重审前回调
        $scope.refusaApprovallEvent.cusValidator = function(){
            $scope.saveFile();
            return true;
        }

    });
});
