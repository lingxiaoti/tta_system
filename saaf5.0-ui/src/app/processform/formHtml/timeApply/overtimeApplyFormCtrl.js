/**
 * Created by Acer on 2018/8/10.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('overtimeApplyFormCtrl', function ($scope, $location, $rootScope, $state,httpServer,URLAPI, $stateParams, SIEJS) {
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

        if($scope.action=='new'){
            $scope.formParams.attendTimeType = 10;
            $scope.fileData = []; // 上传文件
            $scope.formParams.overtimeBenefit = 1; // 默认加班补偿为调休
        }
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.maxOverTimeHoures=$scope.formParams.overtimeHours;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            }
        };
        // //处理
        // if ($scope.action == 'detail' || $scope.action == 'edit') {
        //     $scope.w = $scope.$watch('findData', function (nval, oval) {
        //         if (nval === oval) return;
        //         $scope.maxOverTimeHoures=$scope.formParams.overtimeHours;
        //     })
        //     $scope.$on("$destroy", function () {
        //         $scope.w(); //　销毁监听
        //     })
        // }
        $scope.overTime=function(){
            if($scope.formParams.timeoffBeginDate&&$scope.formParams.timeoffEndDate){
                if($scope.maxOverTimeHoures<$scope.formParams.overtimeHours){
                    SIEJS.alert('加班时数不能超过'+$scope.maxOverTimeHoures+'小时', 'error', "确定");
                }
            }
        }
        $scope.inputChang=function(){
            if($scope.formParams.timeoffBeginDate&&$scope.formParams.timeoffEndDate){
                $scope.formParams.timeoffDays=$scope.DateMinus($scope.formParams.timeoffBeginDate,$scope.formParams.timeoffEndDate)
                var p={
                    timeoffBeginDate:$scope.formParams.timeoffBeginDate,//加班开始时间
                    timeoffEndDate:$scope.formParams.timeoffEndDate,//加班结束时间
                    attendTimeType:$scope.formParams.attendTimeType//加班时间分类 10. 按工作日时间加班、20. 按小时加班',
                }
                httpServer.post(URLAPI.findWorkOverTimeType,{params: JSON.stringify(p)},function (res) {
                    if (res.status == "S") {
                        $scope.formParams.overtimeHours=res.data.overtimeHours;
                        $scope.maxOverTimeHoures=res.data.overtimeHours;
                        $scope.formParams.attendType=res.data.attendType;
                        $scope.formParams.attendTypeStr=res.data.attendTypeStr;

                    } else {
                        SIEJS.alert(res.msg, 'error', "确定");

                    }
                })

            }
        }
       $scope.submitEvent.cusValidator= function () {
            var examine=false;

            if($scope.maxOverTimeHoures<$scope.formParams.overtimeHours){
                SIEJS.alert('加班时数不能超过'+$scope.maxOverTimeHoures+'小时', 'error', "确定");
            }else{
                examine=true;
            }
            $scope.getFileIds();
           if($scope.formParams.attendTimeType == "20"){
               if($scope.formParams.overtimeHours < 3){
                   SIEJS.alert('加班时间必须大于3小时','error','确定');
                   return;
               }
           }
            return examine;
        }
        $scope.draftEvent.cusValidator= function () {
            var examine=false;
            
            if($scope.maxOverTimeHoures<$scope.formParams.overtimeHours){
                SIEJS.alert('加班时数不能超过'+$scope.maxOverTimeHoures+'小时', 'error', "确定");
                
            }else{
                examine=true;
            }
            $scope.getFileIds()
            if($scope.formParams.attendTimeType == "20"){
                console.log($scope.formParams.overtimeHours);
                if($scope.formParams.overtimeHours < 3){
                    SIEJS.alert('加班时间必须大于3小时','error','确定');
                    return;
                }
            }
            return examine;
        }


        $scope.changAttend=function(e){
            if($scope.formParams.timeoffEndDate){
                $scope.formParams.timeoffEndDate='';
            }
            if($scope.formParams.timeoffBeginDate){
                $scope.formParams.timeoffBeginDate='';
            }
            if($scope.formParams.attendTypeStr){
                $scope.formParams.attendTypeStr='';
            }
            if($scope.formParams.attendType){
                $scope.formParams.attendType='';
            }
            if($scope.formParams.overtimeHours){
                $scope.formParams.overtimeHours='';
            }
            if($scope.maxOverTimeHoures){
                $scope.maxOverTimeHoures=0;
            }
        }
        $scope.DateMinus= function(date1,date2){//date1:小日期   date2:大日期
            var sdate = new Date(date1);
            var now = new Date(date2);
            var days = now.getTime() - sdate.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            return day;
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateWorkOverTimeByApproval");
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
