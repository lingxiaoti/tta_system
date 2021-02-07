/**
 * Created by lijiayao on 2018/8/21.
 */
'use strict';
define(['app','moment','angularFileUpload'], function (app,moment) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('repairResultsApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS) {
        $scope.params = {};
        $scope.formParams.image1FileIds = [];
        $scope.formParams.image2FileIds = [];
        $scope.logo1Img = [];
        $scope.logo2Img = [];
        $scope.ware1Img = [];
        $scope.ware2Img = [];
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
        $scope.submitEvent.cusValidator = function(){
            for(var i=0;i<$scope.ware1Img.length;i++){
                $scope.formParams.image1FileIds.push($scope.ware1Img[i].fileId);
            }
            for(var i=0;i<$scope.ware2Img.length;i++){
                $scope.formParams.image2FileIds.push($scope.ware2Img[i].fileId);
            }
            return true
        }
        $scope.draftEvent.cusValidator = function(){
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

    });
});
