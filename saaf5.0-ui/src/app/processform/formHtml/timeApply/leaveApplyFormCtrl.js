/**
 * Created by Acer on 2018/8/9.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('leaveApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.paidSickFlag = false;
        $scope.inputChang=function(){
            if($scope.formParams.timeoffBeginDate && $scope.formParams.timeoffEndDate && $scope.formParams.timeoffType){
                $scope.formParams.timeoffDays=$scope.DateMinus($scope.formParams.timeoffBeginDate,$scope.formParams.timeoffEndDate)
                var p={
                    timeoffBeginDate:$scope.formParams.timeoffBeginDate,//请假开始时间
                    timeoffEndDate:$scope.formParams.timeoffEndDate,//请假结束时间
                    endDateType:$scope.formParams.endDateType,
                    beginDateType:$scope.formParams.beginDateType,
                    timeoffType:$scope.formParams.timeoffType//请假类型(1事假、2年假、3病假、4产假、5小产假、6陪产假、7婚假、8丧假、9调休  TIMEOFF_TYPE
                }
                console.log("****************************************************************");
                console.log(p);
                if(!$scope.formParams.beginDateType || !$scope.formParams.endDateType){
                    SIEJS.alert('请选择正确的请假时间', 'error', "确定");
                    return;
                }
                httpServer.post(URLAPI.findAttendTimeoffType,{params: JSON.stringify(p)},function (res) {
                    if (res.status == "S") {
                        console.log(res.data);
                        $scope.formParams.timeoffDays=res.data.timeoffDays;
                        $scope.maxTimeoffDays=res.data.timeoffDays;
                        console.log("计算请假天数****************************************************************");
                        console.log(res.data);
                    } else {
                        SIEJS.alert(res.msg, 'error', "确定");

                    }
                })
            }
        }
        $scope.timeoffChang=function(){
            if($scope.formParams.timeoffType){
                // $scope.formParams.timeoffDays=$scope.DateMinus($scope.formParams.timeoffBeginDate,$scope.formParams.timeoffEndDate)
                //修改状态，选择病假显示带薪病假字段
                if($scope.action == "refusal"){
                    if($scope.formParams.timeoffType == "3"){
                        $scope.paidSickFlag = true;
                    }else{
                        $scope.paidSickFlag = false;
                    }
                }
                var p={
                    timeoffType:$scope.formParams.timeoffType//请假业务类型(1事假、2年假、3病假、4产假、5小产假、6陪产假、7婚假、8丧假、9调休  TIMEOFF_TYPE
                }
                httpServer.post(URLAPI.findAttendTimeoffExpriedNumType,{params: JSON.stringify(p)},function (res) {
                    if (res.status == "S") {
                        if($scope.formParams.timeoffType == "2" || $scope.formParams.timeoffType == "3" || $scope.formParams.timeoffType == "9"){
                            $scope.formParams.expriedNum=res.data.expriedNum;
                            console.log("计算可用结余****************************************************************");
                            console.log(res.data);
                        }else{
                            $scope.formParams.expriedNum="";
                        }
                        $scope.inputChang();
                    } else {
                        SIEJS.alert(res.msg, 'error', "确定");

                    }
                })
            }
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
        if($scope.action=='new'){
            $scope.formParams.beginDateType='AM';
            $scope.formParams.endDateType='PM';
            $scope.fileData = []; // 上传文件

        }
        $scope.submitEvent.cusValidator= function () {
            var examine=false;
            if($scope.maxTimeoffDays<$scope.formParams.timeoffDays){
                SIEJS.alert('请假天数不能超过'+$scope.maxTimeoffDays+'天', 'error', "确定");
            }else{
                examine=true;
            }
            $scope.getFileIds()
            return examine;
        }
        $scope.draftEvent.cusValidator= function () {
            var examine=false;
            if($scope.maxTimeoffDays<$scope.formParams.timeoffDays){
                SIEJS.alert('请假天数不能超过'+$scope.maxTimeoffDays+'天', 'error', "确定");
            }else{
                examine=true;
            }
            $scope.getFileIds()
            return examine;
        }
        $scope.DateMinus= function(date1,date2){//date1:小日期   date2:大日期
            var sdate = new Date(date1);
            var now = new Date(date2);
            var days = now.getTime() - sdate.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            return day;
        }

        //处理
        // $scope.$on('businessLoad', function (event, msg) {
        //     if(msg){
        //         $scope.maxTimeoffDays=$scope.formParams.timeoffDays;
        //     }
        // })
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                if(data.timeoffType == "3"){
                    $scope.paidSickFlag = true;
                }
                console.log(data);
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.maxTimeoffDays=$scope.formParams.timeoffDays;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            }
        };
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateAttendTimeoffByApproval");
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
