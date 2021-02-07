/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('meetingApplyFormCtrl', function ($scope, $location, $rootScope, $state,URLAPI,httpServer, $stateParams, SIEJS,iframeTabAction,$timeout,$filter,findMeetingFacilityInfoPagination) {
       //会议管理
       $scope.findMeetingRoomInfo={}
       var startTime = $location.$$search.startTime?$location.$$search.startTime:-1;//开始时间
       var endTime = $location.$$search.endtTime?$location.$$search.endtTime:-1;//结束时间
       var meetingRoomId = $location.$$search.meetingRoomId;//会议室ID
       var meetingRoomName = $location.$$search.meetingRoomName;//会议室名称
       $scope.findMeetingRoomInfoCallback= function () {
           if($scope.formParams.meetingRoomId){
               findMeetingFacilityInfoPagination({meetingRoomId:$scope.formParams.meetingRoomId},function(res){
                   $scope.formParams.items=res.data;
               })
           }
       };
       setTimeout(function () {
           console.log($scope.formParams);
       },1000);
       if(startTime!=-1&&endTime!=-1){
           $scope.formParams.meetingBeginTime=startTime;
           $scope.formParams.meetingEndTime=endTime;
           $scope.formParams.meetingRoomId = meetingRoomId;
           $scope.formParams.meetingRoomName = meetingRoomName;
           console.log(meetingRoomId)
           $scope.findMeetingRoomInfoCallback();
       }
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
            $scope.getFileIds()
            return true
        } 
        //保存   
        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds()
            return true
        }  
        if($scope.action=='new'){
            $scope.formParams.meetingAddressType=1;
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
        };

        $scope.respCallback = function (respId,respList,curentResp) {
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.formParams.orgId = curentResp.proFileBeans[i].profileValue;
                    }
                }
            }
        }

        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateMeetingApplyByApproval");
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
