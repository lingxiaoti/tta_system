/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app'], function (app) {
    app.controller('baseSSOCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', function ($scope, httpServer, URLAPI, JS, $http, $state, iframeTabAction) {
        $scope.params = {};
        $scope.SSOCopy = {};
        $scope.paramsGroup = [{}];
        $scope.key = [];
        $scope.value = [];
        $scope.existList = JSON.parse(sessionStorage[appName + '_successLoginInfo']);

        // $scope.getKeyValue = function () {
        //
        //     for (var i in  $scope.existList) {
        //         $scope.key.push(i)
        //         $scope.value.push($scope.existList[i])
        //     }
        //
        // }
        // $scope.getKeyValue()

        $scope.btnNew = function () {

            iframeTabAction('SSO详情', 'baseSSODetail/')

        }

        $scope.btnModify = function (item) {
            $scope.SSOCopy = $scope.dataTable.selectRow;
            iframeTabAction('SSO详情', 'baseSSODetail/' + $scope.SSOCopy.sysSsoId)

        }
        $scope.btnSave = function () {
            var paramsArr = [];
            $scope.paramsGroup.map(function (item) {
                var params = {}

                params[item.key] = item.value;
                paramsArr.push(item)

            })
            $scope.SSOCopy.params = JSON.stringify(paramsArr)

            httpServer.post(URLAPI.SSOSave, {
                params: JSON.stringify($scope.SSOCopy)
            }, function (res) {
                console.log(res);
                if ('S' == res.status) {
                    $scope.dataTable.search();
                    JS.alert(res.msg);
                    $('#SSOModal').modal('hide');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('删除失败', 'error', '确定');
            })
        }
        $scope.btnDel = function () {
            var item = $scope.dataTable.selectRow;

            if (item) {
                JS.confirm('删除SSO', '是否确定删除该SSO？', '确定', function () {
                    httpServer.post(URLAPI.SSODel, {
                        params: JSON.stringify({id: item.sysSsoId})
                    }, function (res) {
                        console.log(res);
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


    }])
})