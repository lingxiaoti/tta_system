'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('supplementListCtrl', function ($timeout, httpServer, $filter, userDel, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction,URLAPI) {

        $scope.params = {};
        $scope.btnNew = function () {
            iframeTabAction('补充协议新增', 'supplementDetail/')
        };

        $scope.btnModify = function(){
            var row = $scope.dataTable.selectRow;
            iframeTabAction('补充协议编辑', 'supplementDetail/'+row.sideAgrtHId)
        };

        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    userDel({params: JSON.stringify({id: item.ssoId})}, function (res) {
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
    });
});
