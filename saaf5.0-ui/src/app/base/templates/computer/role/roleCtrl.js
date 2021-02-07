/**
 * Created by houxingzhang on 2018-01-04.
 */
'use strict';
define(['app', 'pinyin'], function (app, pinyin) {
    app.controller('roleCtrl', function ($timeout, $scope, $http, $location, $rootScope, $state, $stateParams, roleSave, setNewRow, roleDelete, SIEJS) {

        $scope.params = {
            systemCode: appName
        };


        $scope.legend = [
            {key: 'roleName', label: '角色名称'},
            {key: 'roleCode', label: '角色编号'}
        ];

        if ($rootScope.currentResp.isAdmin) {
            $scope.legend.push({
                key: 'systemCode',
                label: '系统编码',
                type: 'lookCode',
                lookCode: 'SYSTEM_CODE'
            });
        }

        $scope.btnNew = function () {
            $scope.addParams={
                systemCode: appName
            };
            $("#addRole").modal('show');
        };
        // 编辑按钮
        $scope.btnModify = function () {
            $("#addRole").modal('show');
            $scope.addParams = angular.copy($scope.dataTable.selectRow);// 当前行
        };
        // 保存至数据库
        $scope.btnSave = function () {
            roleSave({params: JSON.stringify($scope.addParams)}, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert("保存成功", "success", "确定");
                    $("#addRole").modal('hide');

                    if (!$scope.addParams.roleId) {// 没有Id则为新赠，重新查询数据
                        $scope.dataTable.search();
                    } else {
                        $scope.addParams = res.data[0];
                        setNewRow($scope.dataTable.selectRow, $scope.addParams);// 更新成功，只更新当前行，不必再去重载当前表格
                    }
                    $scope.addParams = {};
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            })
        };
        // 转换拼音
        $scope.toPinyin = function () {
            $scope.addParams.roleCode = pinyin.shouxiePinyin($scope.addParams.roleName);
        }

        // 删除角色
        $scope.btnDel = function () {
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                roleDelete({params: JSON.stringify({id: $scope.dataTable.selectRow.roleId})}, function (res) {
                    if (res.status === 'S') {
                        SIEJS.alert("操作成功", "success", "确定");
                        $scope.dataTable.search();
                        $("#addRole").modal('hide')
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                })
            })
        }


    });
});
