/**
 * Created by Administrator on 2018/6/25.
 */
'use strict';
define(['app'], function (app) {
    app.controller('OAFormCtrl', function ($controller, $scope, $location, $rootScope, $state, $stateParams, SIEJS) {



        $scope.testB = function () {
            alert('本controller')
        };

        // 设置行数据里的 LOV 控件赋值
        $scope.setRowInput = function (key,value,item) {
          var row = $scope.formParams.apple[$scope.rowIndex];
            row.roleName = item[0].roleName;
            row.roleKey = item[0].roleKey;
            row.handlerExpressionType = item[0].handlerExpressionType;
        };

        // 记录当前行index
        $scope.setRowIndex = function (index) {
            $scope.rowIndex = index;
            $("#lovccRoleKeys").modal('show');
        }

    });
});
