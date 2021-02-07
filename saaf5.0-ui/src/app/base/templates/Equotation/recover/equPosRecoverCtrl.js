/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('equPosRecoverCtrl', function ($scope, URLAPI, $rootScope, $state, SIEJS, $stateParams, iframeTabAction, httpServer) {
        $scope.dataTable = [];
        $scope.addParam = {}
        $scope.params = {}

        //-----------------------------------------------初始化--------------------------------------------
        //当前登录人所属部门
        $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        //-----------------------------------------------删除--------------------------------------------
        $scope.delrow = function (row,index) {

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                httpServer.post(URLAPI.deleteSupplierRecover, {
                    params: JSON.stringify(row)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data.splice(index, 1);// 删除当前选中
                        SIEJS.alert("删除成功", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            })
        }

        //-----------------------------------------------调整--------------------------------------------


        //-----------------------------------------------跳转--------------------------------------------
        $scope.add = function () {
            iframeTabAction('新增供应商恢复详情', 'equPosRecoverEdit/')
        }
        //修改
        $scope.edit = function (row) {
            iframeTabAction('编辑供应商恢复详情', 'equPosRecoverEdit/' + row.supRecoverId)
        }
    });
});