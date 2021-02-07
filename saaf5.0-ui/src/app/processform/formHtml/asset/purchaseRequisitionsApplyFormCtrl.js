/**
 * Created by Administrator on 2018/6/25.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('purchaseRequisitionsApplyFormCtrl', function ( $scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
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

        if($scope.action == 'new'){
            $scope.fileData = []; // 上传文件
        }
        //提交
        $scope.submitEvent.cusValidator = function(){
            $scope.getFileIds();
            if($scope.formParams.items.length < 1){
                SIEJS.alert('请填写采购信息','error','确定');
                return;
            }
            return true
        } 
         //保存   
        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds()
            if($scope.formParams.items.length < 1){
                SIEJS.alert('请填写采购信息','error','确定');
                return;
            }
            return true
        }  
        $scope.btnSave= function () {
            $scope.save('UNCOMMITTED','保存成功')
        }
        $scope.showTableData= function () {
            console.log($scope.formParams)
        }
        $scope.btnSubmit=function(){
            $scope.save('COMMITTED','提交成功')
        }
        $scope.save = function (item,tips) {
            if(!$scope.current.templeContent||$scope.current.templeContent==''){
                JS.alert('请输入日志模版', 'error', "确定");
                return
            }
            $scope.current['status']=item;
            httpServer.post(URLAPI.saveJournalTemplate,{params: JSON.stringify($scope.current)},function (res) {
                if (res.status == "S") {
                    JS.alert(tips,'success')
                    $scope.current['templeId'] = res.data.id;
                } else {
                    JS.alert(res.msg, 'error', "确定");

                }
            })



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

        //审批流更新表单换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateAssetApplyByApproval");
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

        //if ($scope.action == 'new') {
        //    $scope.createRows('apple',{});
        //}


        /*$scope.init= function () {

            //orgId //事业部
            //requestUserId; //申请人用户id
            //requestPersonId; //申请申请人工id
            //requestPersonName; //申请申请人工名称
            //hrOrganizationId; //申请人申请部门id
            //positionId; //申请人职位id

            $scope.userData=JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
            $scope.positionList=JSON.parse(localStorage.getItem(appName + '_successLoginInfo')).positionList[0];
            $scope.formParams.orgId = $scope.userData.orgId;
            //申請人
            $scope.formParams.requestPersonId = $scope.userData.personId;
            //$scope.formParams.requestPersonName = $scope.userData.personName;
            $scope.formParams.requestPersonName = '张三';
            angular.forEach($scope.userData.positionList,function(data,index){
                if(data.primaryFlag=='Y'){
                    //部門
                    $scope.formParams.hrOrganizationName = data.orgName;
                    $scope.formParams.hrOrganizationId = data.orgId;
                    //$scope.formParams.hrOrganizationName = data.deptName;
                    //$scope.formParams.hrOrganizationName = data.deptId;

                }
            })

            $scope.formParams.positionId=$scope.userData.positionId;
            $scope.formParams.positionName=$scope.positionList.positionName;

        }
        if($scope.action=='new'){
            $scope.init();

        }*/
    });
});
