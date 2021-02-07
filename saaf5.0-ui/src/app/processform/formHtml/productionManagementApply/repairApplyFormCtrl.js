/**
 * Created by lijiayao on 2018/8/21.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('repairApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
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

        if($scope.action == "new"){
            $scope.fileData = []; // 上传文件
        }

        $scope.formParams.image1FileIds = [];
        $scope.formParams.image2FileIds = [];
        $scope.logo1Img = [];
        $scope.logo2Img = [];
        $scope.ware1Img = [];
        $scope.ware2Img = [];
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.logo1Img = [];
                $scope.logo2Img = [];
                $scope.formParams.image1FileIds = [];
                $scope.formParams.image2FileIds = [];
                $scope.statusFlag = data.status;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
                for(var i=0;i<data.image1FileList.length;i++){
                    $scope.logo1Img.push({accessPath:data.image1FileList[i].filePath});
                    $scope.formParams.image1FileIds.push(data.image1FileList[i].fileId);
                }
                for(var i=0;i<data.image2FileList.length;i++){
                    $scope.logo2Img.push({accessPath:data.image2FileList[i].filePath});
                    $scope.formParams.image2FileIds.push(data.image2FileList[i].fileId);
                }
            } 
        };

        $scope.submitEvent.cusValidator = function(){
            $scope.getFileIds();
            for(var i=0;i<$scope.ware1Img.length;i++){
                $scope.formParams.image1FileIds.push($scope.ware1Img[i].fileId);
            }
            for(var i=0;i<$scope.ware2Img.length;i++){
                $scope.formParams.image2FileIds.push($scope.ware2Img[i].fileId);
            }
            return true
        }

        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds();
            for(var i=0;i<$scope.ware1Img.length;i++){
                $scope.formParams.image1FileIds.push($scope.ware1Img[i].fileId);
            }
            for(var i=0;i<$scope.ware2Img.length;i++){
                $scope.formParams.image2FileIds.push($scope.ware2Img[i].fileId);
            }
            return true
        }
        //图片添加执行方法
        $scope.imgAddAction = function (rows, targetType, imgChannel, returnMessage) {
            var imgMessage = $.extend({}, returnMessage, {
                "targetType": targetType,
                "disabled": "Y",
            })
            rows.push(imgMessage);
            $scope.ware1Img = rows;
        }
        $scope.imgAddActionSec = function (rows, targetType, imgChannel, returnMessage) {
            var imgMessage = $.extend({}, returnMessage, {
                "targetType": targetType,
                "disabled": "Y",
            })
            rows.push(imgMessage);
            $scope.ware2Img = rows;
        }
        //图片删除执行方法
        $scope.imGdeleteAction = function (rows, row) {
            var index = $.inArray(row, rows)
            if (!$scope.logo1Img.hasOwnProperty('deletedImages')) {
                $scope.logo1Img.deletedImages = [];
            }
            if (rows[index].hasOwnProperty('id')) {
                $scope.logo1Img.deletedImages.push({'id': rows[index].id});
            }
            rows.splice(index, 1);
        }
        $scope.imGdeleteActionSec = function (rows, row) {
            var index = $.inArray(row, rows)
            if (!$scope.logo2Img.hasOwnProperty('deletedImages')) {
                $scope.logo2Img.deletedImages = [];
            }
            if (rows[index].hasOwnProperty('id')) {
                $scope.logo2Img.deletedImages.push({'id': rows[index].id});
            }
            rows.splice(index, 1);
        }

        setTimeout(function () {
            if($scope.action == "detail" || $scope.action == "approval"){
                $(".noEdit").find("input").attr("disabled","disabled");
                $(".noEdit").find("i").css("display","none");
            }
        },1000)
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaWorkshopRepairPcByApproval");
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
