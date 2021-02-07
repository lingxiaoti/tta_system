'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('purchaseSupplierCenterCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {}
        $scope.logoImg = []

        $scope.btnNew = function () {
            iframeTabAction('新增供应商', 'purchaseSupplierDetail/')
        }


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('供应商详情：'+row.supplierName, 'purchaseSupplierDetail/' + row.supplierId)
        }
        //图片添加执行方法
        $scope.imgAddAction = function (rows, targetType, imgChannel, returnMessage) {

            var imgMessage = $.extend({}, returnMessage, {
                "targetType": targetType,
                "disabled": "Y",
            })
            rows.push(imgMessage);
        }

        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    httpServer.post(URLAPI.supplierDel, {
                        params: JSON.stringify({ids: item.supplierId})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            JS.alert('删除成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        JS.alert('删除失败', 'error', '确定');
                    })

                })
            }
        }


    });
});
