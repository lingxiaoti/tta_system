/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('officeSuppliesApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,arrayUnique,httpServer,URLAPI) {
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
            $scope.createRows('items',{admSplDId:-1});
            $scope.fileData = []; // 上传文件

        }
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        $scope.tableCallBack=function(index){
            $scope.tableEnditIndex=index
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

        $scope.subTotal=function(index,item){
            var reg=/^-?[1-9]\d*$/;
            var data=$scope.formParams.items[index]
            if(item.splNum==''||!data.splPrice) return;
            if(reg.test(item.splNum)){
                var splPrice=Number(data.splPrice);
                var splNum=Number(item.splNum);
                var splSum=0
                $scope.formParams.items[index].subTotal=(splPrice*splNum).toFixed(2);
                //计算总数量
                angular.forEach($scope.formParams.items,function(data,index){
                    splSum=splSum+Number( data.subTotal);
                })
                if($scope.formParams.splSum){
                    $scope.formParams['splSum']=splSum;
                }else{
                    $scope.formParams.splSum=splSum;
                };

            }else{
                SIEJS.alert('请输入正整数', "error", "确定");
                $timeout(function(){
                    data.splNum='';
                },200)

            }

        };
        //添加
        $scope.setRowInput=function(){
            var data=$scope.findOfficeAdmGiftPagination.selectRowList;
            var appleData=$scope.formParams.items;
            var newData=[];
            for(var i=0;i<data.length;i++){
                var obj={
                    splMemo:'',
                    splNum:'',
                    subTotal:'',
                    admSplDId:'',
                    admSplHId:'',
                    splSku:data[i].sku,
                    splPrice:data[i].admGiftPrice,
                    splName:data[i].skuName,
                    admGiftId:data[i].admGiftId
                };
                newData.push(obj);
            };

            if(appleData[0].giftId!=-1){
                for(var i=0;i<newData.length;i++){
                    for(var j=0;j<appleData.length;j++){
                        if(newData[i].splSku==appleData[j].splSku){
                            SIEJS.alert('该'+newData[i].splName+'办公用品已经添加','error', "确定")
                            return;
                        }
                    }
                }
            };
            if(appleData[0].admSplDId!=-1){
                newData=$scope.formParams.items.concat(newData);
                newData=arrayUnique(newData,'splSku');
            };
            $scope.formParams.items=newData;
        }

        $scope.setRowIndex = function (index) {
            $scope.rowIndex = index;
        }
        //办公用品名称lov
        $scope.setRowName=function(key,value,item){
            var appleData = $scope.formParams.items;
            for(var i=0;i<appleData.length;i++){
                if(appleData[i].admGiftId == item[0].admGiftId){
                    SIEJS.alert('该'+item[0].skuName+'办公用品已经添加','error', "确定")
                    return;
                }
            }
            $scope.formParams.items[$scope.rowIndex] = {
                splMemo:'',
                splNum:'',
                subTotal:'',
                admSplDId:'',
                admSplHId:'',
                splSku:item[0].sku,
                splPrice:item[0].admGiftPrice,
                splName:item[0].skuName,
                admGiftId:item[0].admGiftId
            }
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOfficeSplHByApproval");
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
