/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('qualityUpdateCtrl', ['$scope', '$state','URLAPI','$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state,URLAPI,$stateParams,iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        $scope.dataTable.data = [];
        $scope.params = {};
        //当前登录人所属部门和部门id
        $scope.params.deptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        // 新增--跳转功能
        $scope.btnNew = function () {
            iframeTabAction('年度质量审核导入更新新增', 'editQualityUpdate/')
        };

        $scope.toEditHtml = function (row) {
            iframeTabAction('年度质量审核导入更新', 'editQualityUpdate/'+row.updateHeadId)
        };

        $scope.deleteData = function (row,index) {
            JS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.deleteQualityUpdateLine, {
                    'params': JSON.stringify(row)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.getData();
                        JS.alert("删除成功", "success", "确定");
                    }
                    else {
                        JS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            })
        }
    }]);
});



