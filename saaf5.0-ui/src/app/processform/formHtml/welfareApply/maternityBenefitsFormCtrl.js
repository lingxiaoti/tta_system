/**
 * Created by administered on 2018/8/16.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('maternityBenefitsFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
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
            $scope.getFileIds()
            return true
        } 
         //保存前回调   
        $scope.draftEvent.cusValidator = function(){
            $scope.getFileIds()
            return true
        }
        //通过前回调   
        $scope.passEvent.cusValidator = function(){
            //通过审批必须选择奶粉
            if($scope.formParams.secItems.length == 0){
                SIEJS.alert('请选择奶粉','error', "确定")
                return false
            }
            $scope.clearSecItems();
            return true;
        }
        //驳回前回调   
        $scope.refusalEvent.cusValidator = function(){
            $scope.clearSecItems();
            return true;
        }
        //驳回重审前回调   
        $scope.refusaApprovallEvent.cusValidator = function(){
            $scope.clearSecItems();
            return true;
        }    
        //计算领取开始时间、领取结束时间
        $scope.back = function (index) {
            $scope.formParams.items[index].endDay = moment($scope.formParams.items[index].childrenBirthday).add(3, "years").format("YYYY-MM-DD");
            $scope.formParams.items[index].startDay = moment($scope.formParams.requestTime).add(1, "months").startOf('months').format("YYYY-MM-DD")
            
        };
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
        //出生日期为空，清空当前index上的值
        $scope.clearItems = function(){
            for(var i = $scope.formParams.items.length-1;i >= 0;i--){
                if(!$scope.formParams.items[i].childrenBirthday || !$scope.formParams.items[i].endDay){
                    $scope.formParams.items.splice(i,1)
                }
            }
        }
        //产品信息为空，清空当前index上的值
        $scope.clearSecItems = function(){
            for(var i = $scope.formParams.secItems.length-1;i >= 0;i--){
                if(!$scope.formParams.secItems[i].productSku){
                    $scope.formParams.secItems.splice(i,1)
                }
            }
        }
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId; 
                $scope.positionByPersonParams.deptId=data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
                // $scope.clearItems();
                // $('.btn.btn-default.btn-xs.editDelete').prop({
                //     disabled:true
                // })
                $('.btn.btn-default.btn-xs.editDelete').modal('hide')
                // console.log($('.btn.btn-default.btn-xs.editDelete'))
                // console.log($scope.fileData)
            } 
        };
        
    });
});
