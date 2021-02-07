/**
 * Created by lijiayao on 2018/8/20.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('internshipApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
        $scope.ljyParams = {
            respId:'',
            departmentCode:''
        };
        $scope.ljyParams1 = {};
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
        $scope.respCallback=function(respId,respList,curentResp){
            $scope.ljyParams.respId = respId;
        }
        $scope.getDepartment = function() {
            $scope.ljyParams.departmentCode = $scope.formParams.traineeDeptId;
            $scope.formParams.traineePositionName = '';
        }
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                // $scope.formParams = res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
                console.log($scope.formParams.items)
                for(var i = 0;i < data.items.length;i++){
                    $scope.getTitle(i)
                    console.log(i)
                }
            } 
        };
        $scope.ljyParams1 = {'respId':$location.$$search.respId}
         //陪同人与申请人一致，可修改
        if ($scope.action == 'new') {
            $scope.fileData = []; // 上传文件
            $scope.formParams.traineeDeptName = '生产部';
            $scope.formParams.traineeDeptId = 240015;
        };
        //鼠标悬浮提示
        $scope.getTitle = function(index){
            
            var contentId = '#formParams_items_content_' + index;
            var expectedEffectId = '#formParams_items_expectedEffect_' + index;
            $(contentId).prop({
                title:$scope.formParams.items[index].content
            })
            $(expectedEffectId).prop({
                title:$scope.formParams.items[index].expectedEffect
            })
            console.log($(contentId))
            console.log($(expectedEffectId))
            console.log(index)
            // console.log($($scope.formParams.items[index].content))
            // console.log($($scope.formParams.items[index].expectedEffect))
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaWorkshopPracticeByApproval");
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
