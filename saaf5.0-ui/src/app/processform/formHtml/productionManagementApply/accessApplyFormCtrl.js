/**
 * Created by lijiayao on 2018/8/20.
 */
'use strict';
define(['app','moment','angularFileUpload','webconfig'], function (app,moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('accessApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
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
            if(!$scope.formParams.enterAreaCode){
                SIEJS.alert("请选择需进入区域",'warning','确定');
                return;
            }
            $scope.getFileIds()
            return true
        } 
         //保存   
        $scope.draftEvent.cusValidator = function(){
            if(!$scope.formParams.enterAreaCode){
                SIEJS.alert("请选择需进入区域",'warning','确定');
                return;
            }
            $scope.getFileIds()
            return true
        }    
        // $scope.ljyParams1 = {'respId':$location.$$search.respId}
        $scope.getDepartment = function(key,value,item){
            $scope.formParams.bizDeptName = item[0].departmentName;
            $scope.formParams.bizDeptId = item[0].departmentId;
            $scope.formParams.bizPositionName = item[0].positionName;
            $scope.formParams.bizPositionId = item[0].positionId;
            $scope.formParams.bizPersonName = item[0].personName;
            $scope.formParams.bizPersonId = item[0].personId;
        }
        $scope.getBuData = function (hasIds) {
            if(hasIds){
                $scope.enterAreaCode = hasIds.split(',');
            }
            httpServer.post(URLAPI.queryLookupLineDic,{params: JSON.stringify({lookupType:'ENTRY_AREA_NAME',systemCode:'OA'})},function(res){
                if (res.status === 'S') {
                    console.log(res.data)
                    $scope.userRespList = res.data;
                    $scope.buData = [];
                    $scope.buDataString='';
                    $scope.userRespList.map(function (item, index) {
                            if (hasIds && hasIds.indexOf(item.lookupCode)>-1){
                                item['checked'] = true;
                            }
                            $scope.buData.push(item)
                    });
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        //$scope.getBuData();
        $scope.checkOU = function (event, item) {
            if($scope.isEdit) return;
            if ($scope.action == 'detail') {
                item.checked = item.checked;
                return
            };
            item.checked = !item.checked;
            if (event.target.nodeName.toLowerCase() === 'label') {
                event.preventDefault();
            }
            //ID NAME
            if (!$scope.lookupTypeId){
                $scope.lookupTypeId = [];
                $scope.nName = [];
            }

            if (item.checked) {
                $scope.lookupTypeId.push(item.lookupCode);
                $scope.nName.push(item.meaning);
            } else {
                $scope.lookupTypeId.splice($scope.lookupTypeId.indexOf(item.lookupCode), 1);
                $scope.nName.splice($scope.nName.indexOf(item.meaning), 1);
            }
            var str=''
            angular.forEach($scope.lookupTypeId,function(data,index){
                str=str+data+','
            })
            str = str.substring(0,str.lastIndexOf(','));
            $scope.formParams.enterAreaCode = str;
            
            var str1=''
            angular.forEach($scope.nName,function(data,index){
                str1=str1+data+','
            })
            str1 = str1.substring(0,str1.lastIndexOf(','));
            $scope.formParams.enterAreaName = str1;

            log(str);
            log(str1);
        }
        //陪同人与申请人一致，可修改
        if ($scope.action == 'new') {
            setTimeout(function(){
                $scope.formParams.bizDeptName = $scope.formParams.deptName;
                $scope.formParams.bizDeptId = $scope.formParams.deptId;
                $scope.formParams.bizPositionName = $scope.formParams.positionName;
                $scope.formParams.bizPositionId = $scope.formParams.positionId;
                $scope.formParams.bizPersonName = $scope.formParams.personName;
                $scope.formParams.bizPersonId = $scope.formParams.personId;
                $scope.formParams.upstreamFlag = 'Y';
                $scope.formParams.healthCertificate = 'Y';
            }, 500);
            
            $scope.getBuData();
            $scope.fileData = []; // 上传文件
        };
        
        $scope.pageView.getBusinessFormCallback=function(res){
            if (res.status === 'S') {
                var data=res.data;
                $scope.positionByPersonParams.orgId=data.orgId;
                $scope.positionByPersonParams.personId= data.personId;      
                $scope.positionByPersonParams.deptId=data.deptId;
                console.log(res)
                $scope.getBuData(res.data.enterAreaCode);
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
                // $scope.getBuData(res.data.orgIds);
            } 
        };
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaWorkshopEntryByApproval");
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
