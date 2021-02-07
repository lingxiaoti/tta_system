/**
 * Created by Acer on 2018/7/19.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('travelApplyVerificationCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,httpServer,URLAPI) {
        $scope.params = {};
        $scope.current={};
        $scope.businessCity={};
        $scope.mybusinessCity={};
        $scope.tableEnditAudit={}
        $scope.personCusBankPaginatioLov={};
        $scope.fileType = webconfig.fileFormat.testType;
        $scope.status = false;
        $scope.newParams = {};
        // 上传文件ID
        $scope.getFileIds = function(){
            $scope.formParams.fileIds = [];
            for(var i = 0; i<$scope.fileData.file.length;i++){
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var today = year+"-"+month;
        if($scope.action == 'new'){
            $scope.fileData = []; // 上传文件
            $scope.formParams.priod = today;
        }

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
            $scope.expensePeriodLovParams={
                "respId":$scope.ctrlRespId,
                "periodType":'P'
            }
            if (curentResp.proFileBeans.length != 0) {
                for (var i = 0; i < curentResp.proFileBeans.length; i++) {
                    if (curentResp.proFileBeans[i].profileCode == "BRC_OU") {
                        $scope.newParams.orgId = curentResp.proFileBeans[i].profileValue;
                    }
                }
            }
        }
        $timeout(function(){
            $scope.personCusBankPaginatioParams={
                personId:$scope.formParams.personId
            }
        },200)
        $scope.showCurrent=function(){
            console.log($scope.current)
        };
        $scope.personCusBankCallback=function(){
            $scope.formParams.pActBank=$scope.personCusBankPaginatioLov.selectRow.bankName;

        }
        $scope.personCusBankCancelCallback=function(){
            $scope.formParams.pActBank='';
        };
        if($scope.action == "approval"){
            $scope.status = true;
        }else {
            $scope.status = false;
        }
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                console.log(data);
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                if($scope.formParams.secItems.length==0){
                    $scope.formParams.secItems.push({})
                }
                $scope.getFileIds();
            }
        };

        $scope.selectCity= function (index,type,cityStr) {
            $scope.selectIndex=index;
            $scope.cityStr=cityStr;
            var id=$scope.cityStr+'RegionId';
            $scope.tableEnditType=type;
            if($scope.formParams[$scope.tableEnditType][$scope.selectIndex][id]){
                $scope.businessCity.setSelected(angular.copy($scope.formParams[$scope.tableEnditType][$scope.selectIndex][id]));
            }else{
                $scope.businessCity.reset();
            };
            $('#citysModal').modal('show');
        };

        //行程起点
        $scope.scheduleForCtyCallback=function(index){
            $scope.selectCity(index,'items','start');
        }
        //行程终点
        $scope.scheduleToCtyCallback=function(index){
            $scope.selectCity(index,'items','fin');
        };
        //驻地回调
        $scope.selectMcity=function(){
            var id='mRegionId';
            if($scope.formParams['mCity']){
                $scope.mybusinessCity.setSelected(angular.copy($scope.formParams['mCity'][id]));
            }else{
                $scope.mybusinessCity.reset();
            };
            $('#mycitysModal').modal('show');
        }
        //驻地保存
        $scope.mycitysSave=function(cityStr){
            var str= cityStr+'Address';
            var RegionId= cityStr+'RegionId';
            var countroy=cityStr+'Country'//国
            var province=cityStr+'Province'//省
            var city=cityStr+'City'//市
            //var area=$scope.cityStr+'Area'//区
            var data=angular.copy($scope.mycitys)
            $scope.formParams[str]=data.cityName;
            $scope.formParams[RegionId]=data.id;
            $scope.formParams[countroy]=data.id[0];
            $scope.formParams[province]=data.id[1];
            $scope.formParams[city]=data.id[2];

            $('#mycitysModal').modal('hide');
        }
        $scope.citySave=function(){

            var str= $scope.cityStr+'Address';
            var RegionId= $scope.cityStr+'RegionId';
            var countroy=$scope.cityStr+'Country'//国
            var province=$scope.cityStr+'Province'//省
            var city=$scope.cityStr+'City'//市
            //var area=$scope.cityStr+'Area'//区
            var data=angular.copy($scope.citys)
            /*switch($scope.cityStr){
             case 'fin':
             if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].startRegionId){
             if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].startRegionId.toString()===data.id.toString()){
             SIEJS.alert('出发城市不能和到达城市一样', 'error');
             return;
             }
             debugger;
             }
             break;
             case 'start':
             console.log($scope.formParams[$scope.tableEnditType][$scope.selectIndex].finRegionId)
             if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].finRegionId){
             if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].finRegionId.toString()===data.id.toString()){
             SIEJS.alert('出发城市不能和到达城市一样', 'error');
             return;
             }

             }
             break;
             default:
             console.error('没有匹配到tableEnditType');
             }*/
            $scope.formParams[$scope.tableEnditType][$scope.selectIndex][str]=data.cityName;
            $scope.formParams[$scope.tableEnditType][$scope.selectIndex][RegionId]=data.id;
            $scope.formParams[$scope.tableEnditType][$scope.selectIndex][countroy]=data.id[0];
            $scope.formParams[$scope.tableEnditType][$scope.selectIndex][province]=data.id[1];
            $scope.formParams[$scope.tableEnditType][$scope.selectIndex][city]=data.id[2];

            $('#citysModal').modal('hide');
        }

        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateApTrvByApproval");
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

        //保存核销明细
        $scope.saveVerification = function () {
            httpServer.post(URLAPI.updateApTrvByApproval, {
                params: JSON.stringify($scope.formParams)
            }, function (res) {
                if (res.status === 'S') {
                    console.log(res.data);
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (err) {
                SIEJS.alert(res.msg, 'error', '确定');
            })
        }
        //通过前回调
        $scope.passEvent.cusValidator = function(){
            $scope.saveFile();
            $scope.saveVerification();
            return true;
        }
        //驳回前回调
        $scope.refusalEvent.cusValidator = function(){
            $scope.saveFile();
            $scope.saveVerification();
            return true;
        }
        //驳回重审前回调
        $scope.refusaApprovallEvent.cusValidator = function(){
            $scope.saveFile();
            $scope.saveVerification();
            return true;
        }

        //$scope.draftEvent.cusValidator=function(){
        //    console.log('draftEvent');
        //    return false
        //}

        // var reg=/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
        // $scope.inputErrors='';
        // $scope.inputTest=true;
        // if($scope.action=='new'){
        //     $scope.createRows('items', {
        //         "carM": 0,
        //         "sleepM": 0,
        //         "otherM": 0,
        //         "totalM": 0,
        //         "voiceNum": 0,
        //         }
        //     )
        // }
        // $scope.addTable=function(){
        //     $scope.createRows('items', {
        //             "carM": 0,
        //             "sleepM": 0,
        //             "otherM": 0,
        //             "totalM": 0,
        //             "voiceNum": 0,
        //         }
        //     )
        // }
        // $scope.inputKeyUp=function(itme,index,type){
        //         switch (type){
        //             case 'carM':
        //                 if(!itme.carM){
        //                     $scope.inputErrors='请输入正确的交通费用'
        //                 }else {
        //                     $scope.inputErrors=''
        //                     $scope.carTotla();
        //                     $scope.totalMfn(index);
        //                 }
        //                 break;
        //             case 'sleepM':
        //                 if(!itme.sleepM){
        //                     $scope.inputErrors='请输入正确的住宿费用'
        //                 }else {
        //                     $scope.inputErrors='';
        //                     $scope.sleepTotla();
        //                     $scope.totalMfn(index);
        //                 }

        //                 break;
        //             case 'otherM':
        //                 if(!itme.otherM){
        //                     $scope.inputErrors='请输入正确的补助金额'
        //                 }else {
        //                     $scope.inputErrors=''
        //                     $scope.otherTotla();
        //                     $scope.totalMfn(index);
        //                 }
        //                 break;
        //             case 'voiceNum':
        //                 if(!itme.voiceNum){
        //                     $scope.inputErrors='请输入正确的自驾行程公里'
        //                 }else {
        //                     $scope.inputErrors=''
        //                 }
        //                 break;
        //         }
        // }
        //$scope.formParams.interfaceName=$filter('date')(new Date(), 'yyyy-MM-dd')
        // $scope.tableCallBack=function(index){
        //     $scope.tableEnditIndex=index
        // }
        // //合计金额
        // $scope.totalMfn=function(index){
        //   var data=$scope.formParams.items[index];
        //     var otherM=Number(data.otherM);
        //     var sleepM=Number(data.sleepM);
        //     var carM=Number(data.carM);
        //     data.totalM=otherM+sleepM+carM;
        // }
        // //交通费用
        // $scope.carTotla=function(){

        //     var t=0;
        //     var data=$scope.formParams.items;
        //     for(var i=0;i<$scope.formParams.items.length;i++){
        //         if(!reg.test(data[i].carM)&&data[i].carM!=undefined){
        //             $scope.inputErrors='请输入正确的交通费用';
        //         }else if(data[i].carM!=undefined){
        //             $scope.inputErrors=''
        //             t+=parseInt(data[i].carM)
        //         }
        //     }
        //     $scope.cart=t
        // };
        // //住宿费用
        // $scope.sleepTotla=function(){
        //     var t=0;
        //     var data=$scope.formParams.items;
        //     for(var i=0;i<$scope.formParams.items.length;i++){
        //         if(!reg.test(data[i].sleepM)&&data[i].sleepM!=undefined){
        //             //$scope.inputErrors='请输入正确的住宿费用';

        //         }else if(data[i].sleepM!=undefined){
        //             //$scope.inputErrors=''
        //             t+=parseInt(data[i].sleepM)
        //         }
        //     }
        //     $scope.sleept=t
        // };
        // //补助费用
        // $scope.otherTotla=function(){
        //     var t=0;
        //     var data=$scope.formParams.items;
        //     for(var i=0;i<$scope.formParams.items.length;i++){
        //         if(!reg.test(data[i].otherM)&&data[i].otherM!=undefined){
        //             //$scope.inputErrors='请输入正确的补助费用';
        //         }else if(data[i].otherM!=undefined){
        //             //$scope.inputErrors=''
        //             t+=parseInt(data[i].otherM)
        //         }
        //     }
        //     $scope.othert=t

        // };
        // $scope.abc=function(){
        //     return $scope.formParams.items[0].carM*3
        // }
        // //响应ajax回调
        // $scope.$on("businessLoad", function(e,data) {
        //     if(data){
        //         // $scope.carTotla()
        //         // $scope.sleepTotla()
        //         // $scope.otherTotla()
        //     }
        // })

        //获取费用期间
        setTimeout(function () {
            httpServer.post(URLAPI.ems_findExpensePeriodByOrgList, {
                params: JSON.stringify({orgId: $scope.newParams.orgId, periodType: 'P'}),
                pageIndex: 1,
                pageRows: 10000
            }, function (res) {
                if (res.status === 'S') {
                    $scope.expensePeriodList = res.data;
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (err) {
                SIEJS.alert('查询渠道数据失败', 'error', '确定');
            })
        },500);

    });
});
