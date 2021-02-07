'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('feedeptCenterCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {}
        $scope.logoImg = []

        $scope.btnNew = function () {
            iframeTabAction('新增部门协定收费标准', 'feedeptDetail/')
        }


        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('部门协定收费标准详情：'+row.yearCode, 'feedeptDetail/' + row.feedeptId);
        }


        $scope.btnDel = function (feedept) {
            feedept = feedept || $scope.dataTable.selectRow;

            if (feedept) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    httpServer.post(URLAPI.feedeptHDel, {
                        params: JSON.stringify({ids: feedept.feedeptId})
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
