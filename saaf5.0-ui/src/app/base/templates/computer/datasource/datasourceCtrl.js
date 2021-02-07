/**
 * Created by dengdunxin on 2018/1/4.
 */
define(['app'], function (app) {
    app.controller('datasourceCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', function ($scope, httpServer, URLAPI, JS, $http, $state) {


        $scope.btnModify = function () {
            $scope.current = angular.copy($scope.dataTable.selectRow)
            $('#datasourceModal').modal('toggle');
            //console.log($scope.dataTable.selectRow)
        }
        $scope.btnNew = function () {
            $scope.current = {}
            $('#datasourceModal').modal('toggle')
        }

        $scope.confirmData = function () {

            console.log($scope.dataTable)


           // $scope.current.dsWebserverAddress = 'jdbc:mysql://' + $scope.current.dsIp + ':' + $scope.current.dsPort + '/' + $scope.current.dsDbInstanceName
           //修改成oralce
            $scope.current.dsWebserverAddress = 'jdbc:oracle:thin:@://' + $scope.current.dsIp + ':' + $scope.current.dsPort + '/' + $scope.current.dsDbInstanceName

            httpServer.post(URLAPI.datasourceSave, {
                params: JSON.stringify($scope.current)
            }, function (res) {

                if ('S' == res.status) {
                    $scope.dataTable.search();
                    $('#datasourceModal').modal('toggle');

                    JS.alert(res.msg, 'success', '确定');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('删除失败', 'error', '确定');
            })


        }
        $scope.btnDel = function (item) {
            item = $scope.dataTable.selectRow;

            if (item) {
                JS.confirm('删除', '是否确定删除？', '确定', function () {
                    httpServer.post(URLAPI.datasourceDel, {
                        params: JSON.stringify({id: item.dsId})
                    }, function (res) {
                        console.log(res);
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            JS.alert('删除成功', 'success', '确定');
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


    }])
})