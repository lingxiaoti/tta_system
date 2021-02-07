/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('tempSpecialCtrl', ['$scope', '$state','URLAPI','$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state,URLAPI,$stateParams,iframeTabAction, JS, httpServer) {
        $scope.dataTable = {}
        $scope.dataTable.data = [];
        $scope.params = {};
        //当前登录人所属部门和部门id
        $scope.params.accessDeptId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;

        // 新增--跳转功能
        $scope.btnNew = function () {
            iframeTabAction('供应商入库临时特批审核新增', 'editTempSpecial/')
        };

        $scope.toEditHtml = function (row) {
            iframeTabAction('供应商入库临时特批更新详情', 'editTempSpecial/' + row.tempSpecialId);
        };

        $scope.deleteData = function (row,index) {
            var tempSpecialId = row.tempSpecialId;
            JS.confirm('删除', '是否确定删除？', '确定', function () {
                // if(categoryMaintainId == null || categoryMaintainId =='' ){
                //     $scope.dataTable.data.splice(index, 1);             //新增列没有ID直接删除
                //     JS.alert("操作成功!已成功删除数据！", "success");
                // }
                httpServer.post(URLAPI.deleteTempSpecial, {
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



