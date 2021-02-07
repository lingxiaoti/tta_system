/**
 * Created by dengdunxin on 2018/1/3.
 */
define(["app"], function (app) {
    app.controller('questionListCtrl', ['$scope','$rootScope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', function ($scope,$rootScope, httpServer, URLAPI, JS, $http, $state, iframeTabAction) {
            //do something here

        $scope.params={
            systemCode:appName.toUpperCase()
        }
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //修改
        $scope.btnModify = function () {
            //console.log($scope.dataTable.selectRow)
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var row = $scope.dataTable.selectRow;
            iframeTabAction('问卷详情：' + row.projectCnTitle, '/editQuestionDetail/' + row.qHeaderId);
        }

        //查看
        $scope.btnDetail = function () {
            //console.log($scope.dataTable.selectRow)
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var row = $scope.dataTable.selectRow;
            iframeTabAction('问卷详情：' + row.projectCnTitle, '/editQuestionDetail/' + row.qHeaderId + "?onlyShow=1");
        }



        //删除
        $scope.btnDel = function () {
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var qHeaderId = $scope.dataTable.selectRow.qHeaderId;
            JS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.deleteQuestionHeaderOrLine, {
                    'params': JSON.stringify({qHeaderId: qHeaderId})
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.search();
                        JS.alert(res.msg, "success", "确定");
                    }
                })
            });
        }

        $scope.btnNew = function (id) {
            iframeTabAction('新增题目', 'editQuestionDetail/')
        }
    }]);
});
