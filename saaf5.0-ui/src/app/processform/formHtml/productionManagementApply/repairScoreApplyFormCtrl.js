/**
 * Created by lijiayao on 2018/8/21.
 */
'use strict';
define(['app','moment','angularFileUpload'], function (app,moment) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('repairScoreApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS) {
        $scope.params = {};
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.logo1Img = [];
                $scope.logo2Img = [];
                $scope.formParams.image1FileIds = [];
                $scope.formParams.image2FileIds = [];
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
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaWorkshopRepairPcByApproval");
            }
        });
        setTimeout(function () {
            $(".noEdit").find("input").attr("disabled","disabled");
            $(".noEdit").find("i").css("display","none");
        },1000)
        // $scope.submitEvent.cusValidator = function(){

        // }  
        // $scope.draftEvent.cusValidator = function(){
            
        // }

    });
});
