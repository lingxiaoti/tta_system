/**
 * Created by 31502 on 9/14 0014.
 */
define(["app"], function (app) {
    app.controller('concurrentProgramsCtrl', ['SIE.JS', '$scope', 'httpServer', 'URLAPI', '$state', 'iframeTabAction', function (JS, $scope, httpServer, URLAPI, $state, iframeTabAction) {

        $scope.dataTable = {};
        $scope.params = {};
        $scope.deleteJob = function (obj) {
            console.log($scope.dataTable)
            if (obj) {
                JS.confirm('删除并发程序', '是否确定删除该条并发程序？', '确定', function () {
                    httpServer.post(URLAPI.jobDelete, {
                        'params': JSON.stringify({
                            jobId: obj.jobId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            //刷新表格
                            $scope.dataTable.search();
                            JS.alert(res.msg, "success", "确定");
                        }
                        else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        console.error(err);
                        JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                })
            }
        }

        $scope.btnNew = function () { //根据不同类型跳转不同的行页面
            // $state.go('home.addConcurrentPrograms', {
            //     jobId: jobId
            // });

            iframeTabAction('新增并发', 'concurrentProgramsDetail/')

        }

        $scope.btnModify = function () {

            iframeTabAction('并发详情：'+ $scope.dataTable.selectRow.jobName, 'concurrentProgramsDetail/' + $scope.dataTable.selectRow.jobId)

        }

        //新增job按钮事件
        // $scope.newJob = function () { //根据不同类型跳转不同的行页面
        //     $state.go('home.addConcurrentPrograms', {
        //         jobId: -1
        //     });
        // }
    }]);
});