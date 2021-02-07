/**
 * Created by dengdunxin on 2018/1/9.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('configurationCtrl', function ($timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, iframeTabAction) {


        $scope.add = function () {
            console.log(222)
        }


        $scope.update = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('用户维护详情', 'userDetail/'+row.personId)
        }
    });
});
