/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('employeeCtrl', function ($timeout,personDel, iframeTabAction, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS) {

        $scope.params={}

        $scope.btnNew = function () {
            iframeTabAction('员工详情', 'employeeDetail/')
        }
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            iframeTabAction('员工详情:' + row.personName, 'employeeDetail/' + row.personId)
        }

        $scope.btnDel = function (item) {
              item = item|| $scope.dataTable.selectRow
            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    personDel({params: JSON.stringify({id: item.personId})}, function (res) {
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
