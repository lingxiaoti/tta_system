/**
 * Created by Administrator on 2018/6/25.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('fixedAssetsAdjustApplyFormCtrl', function ( $scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;

        //提交
        $scope.submitEvent.cusValidator = function(){
            $scope.getFileIds();
            if($scope.formParams.items.length < 1){
                SIEJS.alert('请填写调剂信息','error','确定');
                return;
            }
            return true
        } 
         //保存   
        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds();
            if($scope.formParams.items.length < 1){
                SIEJS.alert('请填写调剂信息','error','确定');
                return;
            }
            return true
        } 
        // 设置行数据里的 LOV 控件赋值
        $scope.storage = [];
        $scope.setRowInput = function (key,value,item) {
            var row = $scope.formParams.items[$scope.rowIndex];
            for(var i=0;i<$scope.formParams.items.length;i++){
                var data=$scope.formParams.items[i]
                if(data.assetCode==item[0].assetCode&&data.oldHrOrganizationId!=-1){
                    SIEJS.alert('该固定资产已经添加','error', "确定")
                    return;
                }

            }

            row.assetCode = item[0].assetCode;
            row.assetName = item[0].assetName;
            row.assetModel = item[0].assetModel;
            row.checkTime = item[0].checkTime;
            row.changeTime = item[0].changeTime;
            row.oldHrOrganizationId  = item[0].deptId;
            row.oldHrOrganizationName = item[0].deptName;
            row.oldUserName = item[0].assetUserPerson;
        };
        // 记录当前行index
        $scope.setRowIndex = function (index) {
            $scope.rowIndex = index;
            console.log(index)
            //$("#assetMaterialModel").modal('show');
        }

        if ($scope.action == 'new') {
            $scope.createRows('items',{oldHrOrganizationId:-1});
            $scope.fileData = []; // 上传文件
            
        }
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            } 
        }
        $scope.respCallback = function (respId,respList,curentResp) {
            for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                    $scope.formParams.orgId = curentResp.proFileBeans[i].profileValue;
                }
            }
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateAdjustApplyByApproval");
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
        // console.log($scope.params)
        // console.log($scope.variables)
    });
});
