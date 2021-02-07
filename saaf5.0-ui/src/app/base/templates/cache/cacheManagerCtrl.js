/**
 * Created by dengdunxin on 2018/1/4.
 */
define(['app'], function (app) {
    app.controller('cacheManagerCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', function ($scope, httpServer, URLAPI, JS, $http, $state) {
        $scope.dataTable = {};
        $scope.params = {};

        $scope.methodArr = [{key: 'GET', value: 'GET'}, {key: 'POST', value: 'POST'}];
        $scope.formList = {}

        $scope.deleteApi = function (item) {
            if (item) {
                JS.confirm('删除API', '是否确定删除该API？', '确定', function () {
                    httpServer.post(URLAPI.baseApiManagementDel, {
                        params: JSON.stringify({id: item.apiId})
                    }, function (res) {
                        console.log(res);
                        if ('S' == res.status) {
                            $scope.dataTable.getData();
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


        $scope.edit = function () {
            console.log($scope.params)
            // var id = $scope.dataTable.selectRow.apiId
            //
            // $state.go("apiContent", {id: id});
        }

    }])
})