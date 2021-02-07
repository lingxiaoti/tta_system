/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('employeeDetailCtrl', function ($timeout, $filter, personSave, personFind, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {

        var id = $stateParams.id


        $scope.search = function () {
            personFind({params: JSON.stringify({personId: id})}, function (res) {
                if (res.status == "S") {

                    $scope.params = res.data[0]
                } else {
                    SIEJS.alert(res.msg, 'error')
                }
            })
        }


        if (id == null || id == "" || id == undefined || id == "undefined") {
            //新增
        }
        else {
            //修改
            $scope.search();
            $timeout(function () {
                $scope.search();
            })
        }


        $scope.btnSave = function () {

            if (!$filter("carNo")($scope.params.cardNo) && $scope.params.cardNo !=undefined) {
                console.log($filter("carId")($scope.params.cardNo))
                SIEJS.alert('身份证错误', 'warning')
                return
            }
            if (!$filter("mobilePhone")($scope.params.mobilePhone) && $scope.params.mobilePhone !=undefined) {
                console.log($filter("mobilePhone")($scope.params.telPhone))
                SIEJS.alert('手机号错误', 'warning')
                return
            }
            if (!$filter("email")($scope.params.email) && $scope.params.email !=undefined) {
                SIEJS.alert('email错误', 'warning')
                return
            }

            personSave({params: JSON.stringify($scope.params)}, function (res) {
                if (res.status == "S") {
                    $scope.params = res.data[0];

                    SIEJS.alert(res.msg, 'success')
                } else {
                    SIEJS.alert(res.msg, 'error')
                }
            })
        }
    });
});
