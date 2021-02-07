/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('itemDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        var id = $stateParams.id;


        $scope.params = {};

        //查询单据信息
        $scope.search = function () {

            $scope.itemId = id;

            httpServer.post(URLAPI.itemFindById, {
                    'params': JSON.stringify({itemId: $scope.itemId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data;

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        //id不为空
        if ($stateParams.id) {

            //查询头信息
            $scope.search();
        } else {
            SIEJS.alert('操作失败，物料ID不能为空，请联系维护人员', "error", "确定");
        }


        $scope.btnSave = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.itemSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.itemId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        }



        //选择潜在供应商
        $scope.getLatenitemCode = function () {
            //  $scope.areaComponent = e;
            $('#latentitemCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectLatentitemReturn = function (key, value, currentList) {
            $scope.params.latentCode = currentList[0].itemCode;
            $scope.params.latentName = currentList[0].itemName;

        }


    });
});
