/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('plmDeptListDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, $compile, FileUploader, iframeMessage) {
        var id = $stateParams.id;

        $scope.headerData = {};

        $scope.plmDeptClassTable = [];
        $scope.plmDeptClassParams = {};
        $scope.plmDeptSubclassTable = [];
        $scope.plmDeptSubclassParams = {};


        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName;

        //查询单据信息
        $scope.search = function (string) {

            $scope.plmDeptListId = id;

            httpServer.post(URLAPI.findPlmDeptListInfo, {
                    'params': JSON.stringify({plmDeptListId: $scope.plmDeptListId})
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.headerData = res.data[0];
                        $scope.plmDeptListId = res.data[0].plmDeptListId;
                        $scope.plmDeptClassParams.plmDeptListId = $scope.plmDeptListId;
                        $scope.plmDeptSubclassParams.plmDeptListId = $scope.plmDeptListId;
                        $scope.plmDeptClassTable.search();

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        if(id===undefined){

        }
        else {
            $scope.search();
        }

        $scope.$watch("plmDeptClassTable.selectRow",function (newVal) {
            if(newVal!==undefined&&newVal!==null) {
                $scope.plmDeptSubclassParams.plmDeptListId = newVal.plmDeptListId;
                $scope.plmDeptSubclassParams.plmDeptClassId = newVal.plmDeptClassId;
                $scope.plmDeptSubclassTable.search();
            }
        })


    });
});
