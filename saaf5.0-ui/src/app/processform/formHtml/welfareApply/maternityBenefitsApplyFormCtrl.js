/**
 * Created by administered on 2018/8/16.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('maternityBenefitsApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
        $scope.stateFlag = "";
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
            // console.log($scope.fileData)
            // console.log($scope.formParams.fileIds)
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
        if($scope.action == 'new'){
            $scope.fileData = []; // 上传文件
        }
        //提交前回调
        $scope.submitEvent.cusValidator = function(){
            $scope.clearItems()
            $scope.getFileIds()
            return true
        } 
         //保存前回调
        $scope.draftEvent.cusValidator = function(){
            $scope.clearItems()
            $scope.getFileIds()
            return true
        }
        
        //计算领取开始时间、领取结束时间
        $scope.back = function (index) {
            $scope.formParams.items[index].endDay = moment($scope.formParams.items[index].childrenBirthday).add(3, "years").format("YYYY-MM-DD");
            $scope.formParams.items[index].startDay = moment($scope.formParams.requestTime).add(1, "months").startOf('months').format("YYYY-MM-DD");
            //输入生日后删除，清空开始领取时间及结束领取时间
            if($scope.formParams.items[index].childrenBirthday == ''){
                $scope.formParams.items[index].endDay = '';
                $scope.formParams.items[index].startDay = '';
            }
        };
        //出生日期为空，清空当前index上的值
        $scope.clearItems = function(){
            // for(var i = 0;i < $scope.formParams.items.length;i++){
            //     if($scope.formParams.items[i].childrenBirthday == '' || $scope.formParams.items[i].endDay == ''){
            //         $scope.formParams.items.splice(i,1)
            //     }
            // }
        }
        // 设置行数据里的 LOV 控件赋值
        $scope.setRowInput = function (key,value,item) {
            var row = $scope.formParams.secItems[$scope.rowIndex];
            console.log($scope.row)
            for(var i=0;i<$scope.formParams.secItems.length;i++){
                var data=$scope.formParams.secItems[i]
                console.log(data)
                if(data.itemId==item[0].itemId&&data.itemId!=-1){
                    SIEJS.alert('该产品已经添加','error', "确定")
                    return;
                }

            }
            row.productSku = item[0].itemCode;
            row.productName = item[0].itemName;
            row.productUnit = item[0].unit_;
            row.itemId = item[0].itemId;
        };
        // 记录当前行index
        $scope.setRowIndex = function (index) {
            $scope.rowIndex = index;
        };
        //清除行数据里的 LOV 数据
        $scope.clearRowInput = function(row){
            row.productSku = '';
            row.productName = '';
            row.productUnit = '';
            row.itemId = '';
        } 
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId; 
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
                $scope.stateFlag = data.status;
                console.log(data);
            } 
        };
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaHrBirthByApproval");
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
