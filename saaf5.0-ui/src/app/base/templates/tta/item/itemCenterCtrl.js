'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('itemCenterCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {}
        $scope.logoImg = []

        $scope.btnNew = function () {
            iframeTabAction('新增物料', 'itemDetail/')
        }


        $scope.btnDetail = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('物料详情：'+row.itemDescCn, 'itemDetail/' + row.itemId)
        }


        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    httpServer.post(URLAPI.itemDel, {
                        params: JSON.stringify({ids: item.itemId})
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
