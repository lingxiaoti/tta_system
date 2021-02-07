/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app'], function (app) {
    app.controller('apiContentCtrl', ['$scope', 'httpServer', 'SIE.JS', 'URLAPI', '$stateParams', '$rootScope', function ($scope, httpServer, JS, URLAPI, $stateParams, $rootScope) {

        $scope.methodArr = [{key: 'get', value: 'get'}, {key: 'post', value: 'post'}];

        $scope.projectTable = {};
        $scope.projectParams = {};

        $scope.moduleTable = {};
        $scope.moduleParams = {};
        $scope.init = function () {
            console.log($stateParams.id)
            if ($stateParams.hasOwnProperty('id') && $stateParams.id != '' && $stateParams.id != null && $stateParams.id != undefined) {
                getApiContent($stateParams.id);
            } else {
                $scope.apiContent = {};
                $scope.apiContent.requestMode = 'post';
                $scope.apiContent.spaStatus = '测试';
                $scope.apiContent.developer = JSON.parse(sessionStorage[appName + '_successLoginInfo']).employeeName;
            }
        }

        function getApiContent(id) {
            console.log(id)
            httpServer.post(URLAPI.baseApiManagementFindById, {
                params: JSON.stringify({
                    id: id
                })
            }, function (res) {
                console.log(res.data[0])
                $scope.apiContent = res.data[0];
            }, function (error) {
                console.error(error);
                JS.alert('获取API信息失败', 'error', '确定');
            })
        }

        $scope.getProject = function () {
            $('#peojectModal').modal('show');
        }

        $scope.getModule = function () {
            $scope.moduleParams.centerCode = $scope.apiContent.centerCode;
            $scope.moduleTable.getData();
            $('#moduleModal').modal('show');
        }

        //提交选中项目表单
        $scope.confirmProject = function (formStatus) {
            if (!formStatus) {
                var project = $scope.projectTable.data[$scope.projectTable.data.selectRow];
                if ($scope.apiContent.centerName != project.centerName) {
                    $scope.apiContent.modelName = '';
                    $scope.apiContent.modelCode = '';
                }
                $scope.apiContent.centerName = project.centerName;
                $scope.apiContent.centerCode = project.centerCode;
                $scope.moduleParams.centerCode = project.centerCode;
                $('#peojectModal').modal('hide');
            }
        }

        //提交选中模块表单
        $scope.confirmModule = function (formStatus) {
            if (!formStatus) {
                var module = $scope.moduleTable.data[$scope.moduleTable.data.selectRow];
                $scope.apiContent.modelName = module.modelName;
                $scope.apiContent.modelCode = module.modelCode;
                $('#moduleModal').modal('hide');
            }
        }

        //提交api表单
        $scope.btnSave = function (formStatus) {
            console.log($scope.apiContent)
            console.log(formStatus)
            if (!formStatus) {

                httpServer.post(URLAPI.baseApiManagementSave, {
                    params: JSON.stringify($scope.apiContent)
                }, function (res) {
                    console.log(res);
                    if ('S' == res.status) {
                        $scope.apiContent = res.data[0];
                        $stateParams.id = $scope.apiContent.apiId
                        JS.alert('保存成功');
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                    }
                }, function (error) {
                    console.error(error);
                    JS.alert('保存失败', 'error', '确定');
                })
            }
        }

    }])
})