/**
 * Created by dengdunxin on 2018/1/3.
 */
/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app'], function (app) {
    app.controller('baseSSORoleCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', '$timeout', function ($scope, httpServer, URLAPI, JS, $http, $state, $timeout) {

        $scope.SSOCopy = {};
        $scope.checkGroup = [];
        $scope.respParams = {}
        $scope.changeSys = function () {

            $timeout(function () {
                var item = $scope.sysTable.selectRow;
                console.log(item.systemCode)
                $scope.respParams.systemCode = item.systemCode;
                $scope.respTable.search();
            }, 200)

        }

        $scope.btnNew = function () {
            // $scope.roleLov.clear()
            $scope.respLov.search();
            $('#allRoleModal').modal('toggle')
        }


        $scope.confirmAllRole = function () {
            var arr = []
            $scope.respLov.selectRowList.map(function (item) {
                var obj = {
                    responsibilityId: item.responsibilityId,
                    systemCode: $scope.respParams.systemCode
                }
                arr.push(obj)
            })
            httpServer.post(URLAPI.ssoRespSave, {
                params: JSON.stringify(arr)
            }, function (res) {
                console.log(res);
                if ('S' == res.status) {
                    JS.alert(res.msg);
                    $scope.checkGroup = [];
                    $scope.respTable.search();
                    // $('#allRoleModal').modal('toggle')
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('删除失败', 'error', '确定');
            })
        }
        $scope.btnDel = function (item) {

            if (item) {
                JS.confirm('删除', '是否确定删除该职责？', '确定', function () {
                    httpServer.post(URLAPI.SSORespDel, {
                        params: JSON.stringify({id: item.responsibilitySysId})
                    }, function (res) {
                        console.log(res);
                        if ('S' == res.status) {
                            $scope.respTable.search();
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