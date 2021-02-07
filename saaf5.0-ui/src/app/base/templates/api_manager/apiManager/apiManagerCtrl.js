/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app'], function (app) {
    app.controller('apiManagerCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', function ($scope, httpServer, URLAPI, JS, $http, $state, iframeTabAction) {
        $scope.dataTable = {};
        $scope.params = {};


        $scope.btnNew = function () {
            iframeTabAction("新增API", 'apiContent/')
        }

        $scope.btnDel = function (item) {
              item = item || $scope.dataTable.selectRow;

            if (item) {
                JS.confirm('删除API', '是否确定删除该API？', '确定', function () {
                    httpServer.post(URLAPI.baseApiManagementDel, {
                        params: JSON.stringify({id: item.apiId})
                    }, function (res) {
                        console.log(res);
                        if (res.status == 'S') {
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


        $scope.btnModify = function () {

            var row = $scope.dataTable.selectRow
            iframeTabAction("API详情："+row.interfaceName, 'apiContent/' + row.apiId);
        }

    }])
})