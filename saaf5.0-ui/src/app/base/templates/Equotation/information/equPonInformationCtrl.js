/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('equPonInformationCtrl', function ($scope, URLAPI, $rootScope, $state, SIEJS, $stateParams, iframeTabAction, httpServer) {
        $scope.dataTable = [];
        $scope.addParam = {}
        $scope.params = {}

        //-----------------------------------------------初始化--------------------------------------------
        //当前登录人所属部门
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.department = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        $scope.params.userFullName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userFullName;
        $scope.params.searchType = 'informationSearch';
        $scope.params.userId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userId;
        $scope.params.userNumber = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;
        console.log(JSON.parse(sessionStorage[appName + '_successLoginInfo']))
        //-----------------------------------------------删除--------------------------------------------
        $scope.delrow = function (item,index) {

            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.deleteInformation, {
                    params: JSON.stringify(item,index)
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
            iframeTabAction('报价资料开启新增', 'equPonInformationEdit/')
        }

        //修改
        $scope.edit = function (row) {
            iframeTabAction('报价资料开启详情：', 'equPonInformationEdit/' + row.informationId)
        }


    });
});