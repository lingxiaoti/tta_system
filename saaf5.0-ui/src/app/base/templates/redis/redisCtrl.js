/**
 * Created by dengdunxin on 2018/2/25.
 */
define(['app'], function (app) {
    app.controller('redisCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', 'redisSave', 'redisDel', function ($scope, httpServer, URLAPI, JS, $http, $state, iframeTabAction, redisSave, redisDel) {
        $scope.dataTable = {};
        $scope.params = {
            redisType: 'GLOBAL_REDIS_CACHE'
        };
        $scope.current = {};


        $scope.btnNew = function () {
            $scope.current = {};
            $('#redis').modal('toggle')
        }

        $scope.btnDel = function (item) {
            item = $scope.dataTable.selectRow;
            if (item) {
                JS.confirm('是否确定删除?', ' ', '确定', function () {


                    redisDel({
                        params: JSON.stringify({
                            redisKey: item.redisKey,
                            redisType: $scope.params.redisType
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.dataTable.search();
                            $scope.dataTable.selectRow=null;
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
            $scope.current = angular.copy($scope.dataTable.selectRow)
            $('#redis').modal('toggle')

        }

        $scope.save = function () {

            redisSave({params: JSON.stringify($scope.current)}, function (res) {
                if (res.status == "S") {
                    $('#redis').modal('toggle')
                    JS.alert(res.msg, 'success', '确定');
                    $scope.dataTable.search()
                } else {
                    JS.alert(res.msg, 'error', '确定');

                }


            })
            console.log(212121)
        }

    }])
})