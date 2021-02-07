/**
 * Created by lijiayao on 2018/8/16.
 */
'use strict';
define(['app', 'moment', 'angularFileUpload','webconfig'], function (app, moment,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('housingSubsidiesApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,httpServer,URLAPI) {
        $scope.params = {};
        $scope.fileType = webconfig.fileFormat.testType;
        // 上传文件ID
        $scope.getFileIds = function () {
            $scope.formParams.fileIds = [];
            for (var i = 0; i < $scope.fileData.file.length; i++) {
                $scope.formParams.fileIds.push($scope.fileData.file[i].fileId);
            }
        }
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
        if ($scope.action == 'new') {
            $scope.fileData = []; // 上传文件
        }
        //提交
        $scope.submitEvent.cusValidator = function () {
            $scope.getFileIds()
            // 附件非空验证
            if ($scope.formParams.fileIds[0] == undefined) {
                SIEJS.alert('附件不能为空', 'error');
                return false
            }
            return true
        }
        //保存
        $scope.draftEvent.cusValidator = function () {
            $scope.getFileIds()
            // 附件非空验证
            if ($scope.formParams.fileIds[0] == undefined) {
                SIEJS.alert('附件不能为空', 'error');
                return false
            }
            return true
        }
        $scope.pageView.getBusinessFormCallback = function (res) {
            if (res.status === 'S') {
                var data = res.data;
                console.log(data);
                $scope.positionByPersonParams.orgId = data.orgId;
                $scope.positionByPersonParams.personId = data.personId;
                $scope.positionByPersonParams.deptId = data.deptId;
                $scope.fileData.file = data.fileList;//上传组件数据
                $scope.getFileIds();
            }
        };
        //省市区控件验证
        $scope.selectCity = function (index, cityStr) {
            $scope.selectIndex = index;
            $scope.cityStr = cityStr;
            var id = $scope.cityStr + 'RegionId';
            //$scope.tableEnditType=type;
            if ($scope.formParams[id]) {
                $scope.businessCity.setSelected(angular.copy($scope.formParams[id]));
            } else {
                $scope.businessCity.reset();
            }
            ;
            $('#citysModal').modal('show');
            console.log($scope.businessCity);

        };

        //行程起点
        $scope.scheduleForCtyCallback = function (index) {
            $scope.selectCity(index, 'bef');
        }
        //行程终点
        $scope.scheduleToCtyCallback = function (index) {
            $scope.selectCity(index, 'now');
        };
        //出发
        $scope.citySave1 = function () {
            var str = $scope.cityStr + 'Address';
            var RegionId = $scope.cityStr + 'RegionId';
            var countroy = $scope.cityStr + 'Country'//国
            var province = $scope.cityStr + 'Province'//省
            var city = $scope.cityStr + 'City'//市
            //var area=$scope.cityStr+'Area'//区

            var data = angular.copy($scope.citys1)
            console.log($scope.cityStr)
            switch ($scope.cityStr) {
                case 'bef':
                    if ($scope.formParams.nowRegionId) {
                        if ($scope.formParams.nowRegionId.toString() === data.id.toString()) {
                            SIEJS.alert('原驻地不能和调入地一致', 'error');
                            return;
                        }
                        debugger;
                    }
                    break;
                case 'now':
                    if ($scope.formParams.befRegionId) {
                        if ($scope.formParams.befRegionId.toString() === data.id.toString()) {
                            SIEJS.alert('原驻地不能和调入地一致', 'error');
                            return;
                        }

                    }
                    break;
                default:
                    console.error('没有匹配到tableEnditType');
            }
            $scope.formParams[str]=data.cityName;
            $scope.formParams[RegionId]=data.id;
            $scope.formParams[countroy]=data.id[0];
            $scope.formParams[province]=data.id[1];
            $scope.formParams[city]=data.id[2];
            console.log()
            $('#citysModal').modal('hide');
        }
        //到达
        $scope.citySave2 = function () {
            var data = angular.copy($scope.citys2)
            if ($scope.formParams.befAdd) {
                if ($scope.formParams.befCity.toString() === data.id[2].toString()) {
                    SIEJS.alert('原驻地不能和调入地一致', 'error');
                    return;
                } else {
                    $scope.formParams.nowAdd = data.cityName;
                    $scope.formParams.nowProvince = data.id[1];
                    $scope.formParams.nowCity = data.id[2];
                }
            } else {
                $scope.formParams.nowAdd = data.cityName;
                $scope.formParams.nowProvince = data.id[1];
                $scope.formParams.nowCity = data.id[2];
            }
            console.log(data);
            $('#citysModal2').modal('hide');
        }
        //审批流更新表单更换接口
        $(function () {
            if($scope.action == "approval"){
                $("#processForm").attr("data-api-update","updateOaHrHSubsidyByApproval");
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
