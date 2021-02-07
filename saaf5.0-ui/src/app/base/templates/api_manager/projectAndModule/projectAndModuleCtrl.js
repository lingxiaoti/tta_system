/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app'], function (app) {
    app.controller('projectAndModuleCtrl', ['$scope', 'httpServer', 'SIE.JS', 'URLAPI', function ($scope, httpServer, JS, URLAPI) {

        $scope.projectTable = {};
        $scope.projectParams = {};

        $scope.moduleTable = {};
        $scope.moduleParams = {};

        $scope.projectModalTitle = '新增项目';
        $scope.moduleModalTitle = '新增模块';


        //新增项目
        $scope.addProject = function () {
            $scope.project = {};
            $scope.projectModalTitle = '新增项目';
            $('#projectModal').modal('show');
        }

        //新增模块
        $scope.btnNew = function () {
            $scope.module = {};
            $scope.moduleModalTitle = '新增模块';
            $('#moduleModal').modal('show');
        }

        //提交项目表单
        $scope.confirmProject = function (formStatus) {
            if (!formStatus) {
                httpServer.post(URLAPI.baseApiCenterHSave, {
                    params: JSON.stringify($scope.project)
                }, function (res) {
                    console.log(res);
                    if ('S' == res.status) {
                        $scope.projectTable.getData();
                        $('#projectModal').modal('hide');
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('保存失败', 'error', '确定');
                    console.error(error);
                })
            } else {
                JS.alert('请填写完整表单', 'error', '确定');
            }
        }

        //修改项目
        $scope.updateProject = function (item) {
            item = item || $scope.dataTable.selectRow;

            $scope.project = angular.copy(item);
            $('#projectModal').modal('show');
            event.stopPropagation()
        }

        //删除项目
        $scope.deleteProject = function (item) {
            item = item || $scope.dataTable.selectRow;
            event.stopPropagation();
            JS.confirm('删除项目', '是否确定删除该项目？', '确定', function () {
                httpServer.post(URLAPI.baseApiCenterHDel, {
                    params: JSON.stringify({id: item.apihId})
                }, function (res) {
                    console.log(res);
                    if ('S' == res.status) {
                        $scope.projectTable.getData();
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

        //获取模块
        $scope.changeProject = function (item) {
            $scope.changeRow = angular.copy(item);
            $scope.moduleParams.centerCode = item.centerCode;
            $scope.moduleTable.search();
            $scope.moduleTable.selectRow=null;
        }

        //提交模块表单
        $scope.confirmModule = function (formStatus) {
            if (!formStatus) {
                $scope.module.centerCode = $scope.projectTable.data[$scope.projectTable.data.selectRow].centerCode;
                httpServer.post(URLAPI.baseApiCenterLSave, {
                    params: JSON.stringify($scope.module)
                }, function (res) {
                    console.log(res);
                    if ('S' == res.status) {
                        $scope.moduleTable.search();
                        $('#moduleModal').modal('hide');
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    JS.alert('保存失败', 'error', '确定');
                    console.error(error);
                })
            } else {
                JS.alert('请填写完整表单', 'error', '确定');
            }
        }

        //修改模块
        $scope.btnModify = function () {
            $scope.moduleModalTitle = '修改模块';

            $scope.module = angular.copy($scope.moduleTable.selectRow);
            $('#moduleModal').modal('show');
        }

        //删除模块
        $scope.btnDel = function () {
            var item =$scope.moduleTable.selectRow;
            if (item) {
                JS.confirm('删除模块', '是否确定删除该模块？', '确定', function () {
                    httpServer.post(URLAPI.baseApiCenterLDel, {
                        params: JSON.stringify({id: item.apilId})
                    }, function (res) {
                        console.log(res);
                        if ('S' == res.status) {
                            $scope.moduleTable.search();
                            $('#moduleModal').modal('hide');
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