/**
 * Created by dengdunxin on 2018/1/30.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin) {
    app.controller('collectCtrl', function ($timeout, $filter, collectSave, collectDel, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction) {

        $scope.params = {
            bltSystemCode:window.appName
        }
        $scope.btnNew = function () {
            $scope.addParams = {};
            $('#addCollect').modal('toggle')
        }


        $scope.btnModify = function () {

            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row)


            $('#addCollect').modal('toggle')
        }

        $scope.btnDel = function (item) {
          item = item || $scope.dataTable.selectRow;
            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    collectDel({params: JSON.stringify({id: item.functionCollectionId})}, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
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


        $scope.btnSave = function () {



            // $scope.addParams.resourceType = parseInt($scope.addParams.resourceType)
            collectSave({params: JSON.stringify($scope.addParams)},
                function (res) {
                    if ('S' == res.status) {
                        $scope.dataTable.search(1);
                        $('#addCollect').modal('toggle')
                        $scope.dataTable.selectRow=res.data;
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    SIEJS.alert('删除失败', 'error', '确定');
                })
        }

    });
});
