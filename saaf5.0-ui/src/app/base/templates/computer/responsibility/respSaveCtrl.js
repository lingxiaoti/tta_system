/**
 * Created by houxingzhang on 2018-01-09.
 */
'use strict';
define(['app', 'pinyin'], function (app, pinyin) {
    app.controller('respSaveCtrl', function ($scope, $http, $location, $rootScope, $state, $stateParams, $timeout, SIEJS, arrayFindItemIndex,
                                             arrayDeleteItem, findProfileSqlDatas, baseResponsibilitySave, responsibilityById,URLAPI,httpServer) {

        $scope.id = $stateParams.id;
        $scope.dataTable = [];
        $scope.roleList={};
        $scope.roleData = {
            data: [],
            count: 0
        };

        $scope.params={
            systemCode:appName
        }
        $scope.btnNewRole = function () {
            $scope.roleList.search(1);
        };
        $scope.btnNewProfile=function(){};
        // 选择角色设置到表格
        $scope.setRoleList = function () {
            $scope.roleList.selectRowList.map(function (item) {
                var index = arrayFindItemIndex($scope.roleData.data, item, 'roleId');
                if (index === -1) {
                    $scope.roleData.data.push(item);
                    $scope.roleData.count++;
                }
            });


            /*
             var list = $scope.roleList.selectRowList;
             if($scope.roleData.count && $scope.roleData.count >0){
             $scope.roleList.selectRowList.map(function(item){
             var index = arrayFindItemIndex($scope.roleData.data,item,'roleId');
             if (index === -1){
             $scope.roleData.data.push(item);
             $scope.roleData.count ++;
             }
             })
             }else{
             $scope.roleData = {
             count: list.length,
             data: list
             };
             }
             */

        };

        // 当前已选择的profile数据
        $scope.profileData = [];
        $scope.setProfile = function () {
            var list = angular.copy($scope.profileList.selectRow);
            findProfileSqlDatas({params: JSON.stringify({profileId: list.profileId})}, function (res) {
                if (res.status === 'S') {
                    list.option = res.data;
                    $scope.profileData.push(list);
                }else{
                    SIEJS.alert(res.msg, 'error');
                }
            })
        };

        // 删除当前关联角色
        $scope.roleRemove = function (item) {
            var n = arrayDeleteItem($scope.roleData.data, item, 'roleId');
            $scope.roleData.count = $scope.roleData.count - n;
        };

        // 删除当前关联profile
        $scope.profileRemove = function (index) {
            $scope.profileData.splice(index, 1);
        };

        // 保存
        $scope.btnSave = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'warning');
                return;
            }
            var p = $scope.params;
            p.respRoles = [];

            for (var n = 0; n < $scope.roleData.data.length; n++) {
                var item = $scope.roleData.data[n];
                if ($scope.id > 0) {
                    p.respRoles.push({
                        respRoleId: item.respRoleId,
                        roleId: item.roleId,
                        versionNum: item.versionNum,
                        responsibilityId: $scope.id // 当前职责ID
                    })
                } else {
                    p.respRoles.push({
                        roleId: item.roleId
                    })
                }

            }

            p.profileValues = [];

            if ($scope.profileData.length > 0) {
                for (var i = 0; i < $scope.profileData.length; i++) {
                    var row = $scope.profileData[i];
                    if ($scope.id > 0) {
                        p.profileValues.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue,
                            profileValueId: row.profileValueId,
                            versionNum: row.versionNum
                        })
                    } else {
                        p.profileValues.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue
                        })
                    }
                }
            }

            baseResponsibilitySave({params: JSON.stringify(p)}, function (res) {
                if (res.status === 'S') {
                    if ($scope.id > 0) {
                        $scope.getData();//重载数据
                    } else {
                        var rId = res.data[0].responsibilityId;
                        $scope.params.responsibilityId=rId;
                        $scope.params.versionNum=res.data[0].versionNum;
                      /*  $scope.params={
                            startDateActive:$rootScope.$getToday(0)
                        };*/
                    }
                }
            })
        }

        // 设置简拼
        $scope.setPinyin = function () {
            $scope.params.responsibilityCode = pinyin.shouxiePinyin($scope.params.responsibilityName);
        };

        // 重新新 增
        $scope.add = function () {
            $location.path('/systemsetting/respSave/0');
        };

        // 初始化数据
        $scope.getData = function () {
            if ($scope.id > 0) {

                responsibilityById({params: JSON.stringify({id: $scope.id * 1})}, function (res) {
                    $scope.params = res.data[0];


                    $scope.roleList.selectRowList = $scope.params.respRoles;

                    // 获取 角色
                    var list = $scope.roleList.selectRowList;
                    $scope.roleData = {
                        count: list.length,
                        data: list
                    };



                    $scope.profileData = $scope.params.profileValues;

                    $scope.profileData.map(function (item) {
                        findProfileSqlDatas({params: JSON.stringify({profileId: item.profileId})}, function (res) {
                            if (res.status === 'S') {
                                item.option = res.data;
                            }
                        })
                    })

                    $scope.profileData.forEach(function (item) {
                        var requestUrl;
                        if (item.profileCode=='VENDOR') {
                            requestUrl=URLAPI.baseVendorProfilefind
                        }else if (item.profileCode=='VENDOR_GROUP'){
                            requestUrl=URLAPI.baseVendorGroupProfilefind
                        } else {
                            return;
                        }

                        httpServer.post(requestUrl, {
                            params: {'vendorNbr':item.profileValue}
                        }, function (res) {
                            if ('S' == res.status && res.data && res.data.length>0) {
                                item.profileValueTxt=res.data[0].vendorName;
                            }
                        }, function (error) {
                            console.error(error);
                        })

                    })


                })
            }

        }
        $scope.getData();


        //供应商profile
        $scope.getProfileVal=function(){

        }

        $scope.vendorCallback=function () {
            $scope.profileData[$scope.dataTable.selectRow].profileValueTxt = $scope.vendorLov.selectRow.vendorName;
            $scope.profileData[$scope.dataTable.selectRow].profileValue = $scope.vendorLov.selectRow.vendorNbr;

        }



    });
});
