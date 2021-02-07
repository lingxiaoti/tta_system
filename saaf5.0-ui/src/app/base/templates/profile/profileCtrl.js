/**
 * Created by dengdunxin on 2018/1/9.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('profileCtrl', function ($timeout, baseProfileDel, $scope, baseProfileSave, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction) {

        $scope.params = {

        }
        $scope.current = {}
        $timeout(function () {
            $scope.params = {
                isValid: true,
                profileCode:''
            }
            $scope.dataTable.search()
        }, 500)

        $scope.btnNew = function () {
            $scope.current = {};
            $('#Modal').modal('toggle')

        }

        $scope.search = function () {
            if(!$scope.params.profileCode){
                $scope.params.profileCode=''
            }
            //$scope.params.profileCode=$scope.params.profileCode!=''?$scope.params.profileCode.toUpperCase():$scope.params.profileCode;
            $scope.params.profileCode=$scope.params.profileCode.toUpperCase();
            //$scope.params = $scope.params
            $scope.params.isValid = true
            $scope.dataTable.search(1)

        }

        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            $scope.current = row;
            $('#Modal').modal('toggle')
        }

        $scope.confirm = function (invalid) {
            if (invalid){
                SIEJS.alert('请校验必填项','warning');
                return;
            }
            //锁定大写保存
            $scope.current.profileCode=$scope.current.profileCode.toUpperCase();
            baseProfileSave({params: JSON.stringify($scope.current)}, function (res) {
                if (res.status == "S") {
                    SIEJS.alert(res.msg, 'success')
                    $scope.dataTable.search();
                    $('#Modal').modal('toggle')

                } else {
                    SIEJS.alert(res.msg, 'error')
                }
            })
        }
        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;
            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    baseProfileDel({
                        params: JSON.stringify(
                            {
                                id: item.profileId,
                                isValid: true
                            })
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            $scope.dataTable.selectRow=null;
                            SIEJS.alert('删除成功');
                        } else {
                            SIEJS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        SIEJS.alert('删除失败', 'error', '确定');
                    })

                })
            }
        }

        $scope.getDs = function () {
            $('#dsnLov').modal('toggle')
        }
        $scope.saveDs = function () {
            $scope.current.dsName = $scope.current.rp.value
            $scope.current.dsId = $scope.current.rp.key
            console.log($scope.current)
        }

    });
});
