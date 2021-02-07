/**
 * Created by husaiqiang on 2018/8/18.
 */
'use strict';
define(['app','angularFileUpload','webconfig'], function (app,angularFileUpload,webconfig) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('guideApplyFormCtrl', function ($scope, $location, $rootScope, $state, $stateParams, SIEJS,$timeout,$filter,httpServer, URLAPI,pageResp) {
        $scope.fileType = webconfig.fileFormat.testType;
        //  附件上传组件 的状态。当前表单为 detail时不可以上传，其它状态可以上传
        $scope.fileViewOnly = $scope.action ==='detail' ? 0:1;
        //添加门店
        $scope.formParams.guideRMapShops = [];
        $scope.addGuideRMapShop = function($index){
            $scope.formParams.guideRMapShops.splice($index + 1, 0, {});
        };


        //删除门店
        $scope.delGuideRMapShop = function($index){
            $scope.formParams.guideRMapShops.splice($index, 1);
        };

        //判断导购类型
        $scope.changeGuideType = function(){
            if ($scope.formParams.guideType == '1' && $scope.formParams.guideRMapShops.length > 1) {
                SIEJS.confirm('失败', '专导只能添加一家门店！', '确定');
                $scope.formParams.guideType = '';
            }
        };

        // 记录当前行index
        $scope.setRowIndex = function (index) {
            $scope.rowIndex = index;
            $("#erpStoreList").modal('show');
        }

        // 设置行数据里的 LOV 控件赋值
        $scope.setRowInput = function (key,value,item) {
            var flag = true;
            angular.forEach($scope.formParams.guideRMapShops,function(obj,index){
                var shopCode = obj.shopCode;
                if (shopCode == item[0].shopCode) {
                    SIEJS.confirm('添加失败', '不能重复添加同一家门店！', '确定');
                    flag = false;
                }
            });
            if (flag) {
                var row = $scope.formParams.guideRMapShops[$scope.rowIndex];
                row.shopCode = item[0].shopCode;
                row.shopCity = item[0].shopCity;
                row.shopLongitudeLatitude = item[0].shopLongitudeLatitude;
                row.shopAddr = item[0].shopAddr;
                row.shopName = item[0].shopName;
                row.shopProvince = item[0].shopProvince;
                row.guideGroupManagerId = item[0].guideGroupManagerId;
                row.guideGroupManagerName = item[0].guideGroupManagerName;
                row.guideSysType = item[0].guideSysType;
                row.guideSysShopName = item[0].guideSysShopName;
                row.guideSysManagerId = item[0].guideSysManagerId;
                row.guideSysManagerName = item[0].guideSysManagerName;
            }
        };

        //获取导购完善资料类型快码
        $scope.setGuideDataTypeList = function () {
            $scope.guideDataTypeList = [];
            for (var n = 0; n < window.parent.saafrootScope.lookupCode.length; n++) {
                var item =  window.parent.saafrootScope.lookupCode[n];
                if (item.lookupType === 'GUIDE_DATA_TYPE') {
                    // 为了兼容之前数据没有添加 systemCode
                    if (item.systemCode && item.systemCode.toLowerCase() === window.appName.toLowerCase()) {
                        $scope.guideDataTypeList.push(item) ;
                    }
                }
            }

            $scope.formParams.guideDatas = [];

            for (var n = 0; n < $scope.guideDataTypeList.length; n++) {
                var item =  $scope.guideDataTypeList[n];
                $scope.formParams.guideDatas.push({guideDataCode:item.lookupCode,guideDataStatus:'2',guideDataCodeMeaning:item.meaning});
            }

        }

        //根据身份证生成生日
        $scope.generateBirthday = function () {
            var idCard = $scope.formParams.guideId;
            if(idCard != null && idCard != '' && idCard.length == 18){
                $scope.formParams.guideBirthday = idCard.substring(6, 10) + "-" + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);

                //获取年龄
                var myDate = new Date();
                var month = myDate.getMonth() + 1;
                var day = myDate.getDate();
                var age = myDate.getFullYear() - idCard.substring(6, 10) - 1;
                if (idCard.substring(10, 12) < month || idCard.substring(10, 12) == month && idCard.substring(12, 14) <= day) {
                    age++;
                }
                $scope.formParams.guideAge = age;
            }
        }


        if ($scope.action == 'new') {
            $scope.addGuideRMapShop();
            $scope.setGuideDataTypeList();
            $scope.formParams.guideBirthdayType = '1';
            $scope.formParams.guideMarry = '1';
            $scope.formParams.guideBorn = '1';
            $scope.formParams.guideSocialInsurances = {};
            $scope.formParams.guideSocialInsurances.guideSiType = '1';
            var userResp = pageResp.get();

            if (userResp.primaryPosition) {
                $scope.formParams.superGuidePersonName = userResp.primaryPosition.personName;
                $scope.formParams.superGuidePersonId = userResp.primaryPosition.personId;
            }
        }

        // 省
        $scope.getProvinceDataByCountryId = function () {
            httpServer.post(URLAPI.getProvinceDataByCountryId, {
                params: JSON.stringify({
                    countryId: '999'
                }),
                pageIndex: -1,
                pageRows: -1
            }, function (res) {
                if (res.status === 'S') {
                    $scope.provinceList = res.data;

                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }
        // 市
        $scope.getCityDataByProvinceId = function (guideSiProvince) {
            httpServer.post(URLAPI.getCityDataByProvinceId, {
                params: JSON.stringify({
                    provinceId: guideSiProvince
                }),
                pageIndex: -1,
                pageRows: -1
            }, function (res) {
                if (res.status === 'S') {
                    $scope.cityList = res.data;
                } else {
                    SIEJS.alert(res.msg, 'error');
                }
            })
        }
        $scope.checkedProvince = function () {
            if ($scope.formParams.guideSocialInsurances.guideSiProvince != null && $scope.formParams.guideSocialInsurances.guideSiProvince != '') {
                $scope.getCityDataByProvinceId($scope.formParams.guideSocialInsurances.guideSiProvince);
            }else {
                $scope.cityList = [] ;
            }
            $scope.formParams.guideSocialInsurances.guideSiCity = '';
        }
        $scope.checkedCity = function () {
            $scope.formParams.guideSocialInsurances.guideSiPlace = $("#guideSiProvince").find("option:selected").text() + $("#guideSiCity").find("option:selected").text();
        }

        $scope.getProvinceDataByCountryId();
        //获取详情 用于省市加载
        $scope.search = function () {
            httpServer.post(URLAPI.findGuideInfo,{params: JSON.stringify({id: $location.$$search.businessKey})}, function (res) {
                if (res.status == "S") {
                    if ( res.data.guideSocialInsurances.guideSiProvince != null &&  res.data.guideSocialInsurances.guideSiProvince != '') {
                        var guideSiProvince = res.data.guideSocialInsurances.guideSiProvince;
                        $scope.getCityDataByProvinceId(guideSiProvince);
                    }
                } else {
                    SIEJS.alert(res.msg, 'error', '确定')
                }
            });
        };
        if ($scope.action != 'new') {
            $scope.search();
        }
        /*setTimeout(function () {
            if ($scope.formParams.guideSocialInsurances.guideSiProvince != null && $scope.formParams.guideSocialInsurances.guideSiProvince != '') {
                $scope.getCityDataByProvinceId();
            }
        }, 2000)*/

        $scope.respCallback=function(){
            $scope.orgFindDeptInfoParams={
                "respId":$scope.ctrlRespId
            }
            $scope.orgFindPersonInfoParams={
                "respId":$scope.ctrlRespId
            }
        }
    });
});
