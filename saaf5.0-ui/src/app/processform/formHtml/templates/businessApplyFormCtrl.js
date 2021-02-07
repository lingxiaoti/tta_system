/**
 * Created by Acer on 2018/8/18.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块

    app.controller('businessApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        log($stateParams)
        $scope.params = {};
        $scope.ljyParams = false;//
        $scope.citys={}
        $scope.tableEnditOne={};
        $scope.tableEnditTwo={};
        $scope.tableEnditThree={};
        $scope.businessCity={};
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
        $scope.btnNew = function () {
            $('#addSourceType').modal('show');
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
            console.log( $scope.businessCity);

        };
        //行程计划 出发城市
        $scope.scheduleForCtyCallback=function(index){
            $scope.selectCity(index,'scheduleItems','leave');
        }
        //行程计划 到达城市
        $scope.scheduleToCtyCallback=function(index){
            $scope.selectCity(index,'scheduleItems','arrival');
        };
        //交通票务 出发城市
        $scope.ticketItemsForCtyCallback= function (index) {
            console.log('ticketItemsForCtyCallback');
            $scope.selectCity(index,'ticketItems','leave');
        };
        //交通票务 到达城市
        $scope.ticketItemsToCtyCallback=function(index){
            $scope.selectCity(index,'ticketItems','arrival');
        };
        //住宿预订
        $scope.hotelItemsForCtyCallback= function (index) {
            $scope.selectCity(index,'hotelItems','checkIn');
        };
        $scope.citySave=function(){
            var str= $scope.cityStr+'Address';
            var RegionId= $scope.cityStr+'RegionId';
            var countroy=$scope.cityStr+'Country'//国
            var province=$scope.cityStr+'Province'//省
            var city=$scope.cityStr+'City'//市
            //var area=$scope.cityStr+'Area'//区
            var data=angular.copy($scope.citys)
            console.log($scope.cityStr)
            /*switch($scope.cityStr){
                case 'arrival':
                    if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].leaveRegionId){
                        if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].leaveRegionId.toString()===data.id.toString()){
                            SIEJS.alert('出发城市不能和到达城市一样', 'error');
                            return;
                        }
                        debugger;
                    }
                    break;
                case 'leave':
                    console.log($scope.formParams[$scope.tableEnditType][$scope.selectIndex].arrivalRegionId)
                    if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].arrivalRegionId){
                        if($scope.formParams[$scope.tableEnditType][$scope.selectIndex].arrivalRegionId.toString()===data.id.toString()){
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
        
        $scope.inputChang = function(){
            console.log($scope.formParams)
            if($scope.formParams.travelBeginDate && $scope.formParams.travelEndDate){
                $scope.formParams.travelDays = $scope.DateMinus($scope.formParams.travelBeginDate,$scope.formParams.travelEndDate)
                console.log($scope.formParams.travelDays)

            }
        }
        $scope.DateMinus = function(date1,date2){//date1:小日期   date2:大日期
            var sdate = new Date(date1);
            var now = new Date(date2);
            var days = now.getTime() - sdate.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24)) + 1;
            return day;
        }
        //处理
        if ($scope.action == 'new') {
            $scope.formParams.isDrive='N';
            $scope.formParams.isBookTicket='Y';
            $scope.formParams.isBookHotel='Y';
            $scope.fileData = []; // 上传文件
            $scope.w = $scope.$watch('findData', function (nval, oval) {
                $scope.formParams.travelPersonId = $scope.formParams.personId;
                $scope.formParams.travelPersonName = $scope.formParams.personName;
                if (nval === oval) return;
                //$scope.borrowMoneyStr();
            })
            $scope.$on("$destroy", function () {
                $scope.w(); //　销毁监听
            })
        }
        //交通非行政预定，清除数据
        $scope.clearTicketItems = function(){
            if($scope.formParams.isBookTicket == 'Y'){
                $scope.formParams.ticketItems = [];
            }else{
                $scope.formParams.ticketItems = [{}];
            }
            // console.log($scope.formParams.ticketItems)
            // console.log(1)
        }
         //住宿非行政预定，清除数据
         $scope.clearHotelItems = function(){
            if($scope.formParams.isBookHotel == 'Y'){
                $scope.formParams.hotelItems = [];
            }else{
                $scope.formParams.hotelItems = [{}];
            }
            // console.log($scope.formParams.hotelItems)
            // console.log(1)
        }
        if ($scope.action == 'detail') {
            $scope.w = $scope.$watch('findData', function (nval, oval) {
                if (nval === oval) return;
                $scope.ljyParams = true;
                //$scope.borrowMoneyStr();
            })
            $scope.$on("$destroy", function () {
                $scope.w(); //　销毁监听
            })
        }
        // $scope.draftEvent.cusValidator=function(){
        //     console.log('draftEvent')
        //     return false;
        // }
        // $scope.submitEvent.cusValidator=function(){
        //     console.log('submitEvent')
        //     return false;
        // }
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

        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaTravelByApproval");
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
