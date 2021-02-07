/**
 * Created by houxingzhang on 2016-09-12.
 */
//'use strict';
define(["app"], function (app) {
    app.controller('responsibilityCtrl', function ($timeout, $scope, $rootScope,
                                                   SIEJS, responsibilityDelete) {

        $scope.params = {
            systemCode: appName
        };

        $scope.legend = [
            {key: 'responsibilityName_like', label: '职责名称'},
            {key: 'responsibilityCode_like', label: '职责编号'},
            {key: 'responsibilityDesc_like', label: '职责描述'},
            {key:'isEfficacious',label:'是否生效',type:'lookCode',lookCode:'YES_OR_NO'},
            {key:'startDateActive_gte',label:'生效日期自',type:'time',endDate:'startDateActive_lte'},
            {key:'startDateActive_lt',label:'生效日期至',type:'time',startDate:'startDateActive_gte'},

        ];

        if ($rootScope.currentResp.isAdmin) {
            $scope.legend.push({
                key: 'systemCode',
                label: '系统编码',
                type: 'lookCode',
                lookCode: 'SYSTEM_CODE'
            });

            $scope.legend = [
                {key: 'responsibilityName_like', label: '职责名称'},
                {key: 'responsibilityCode_like', label: '职责编号'},
                {  key: 'systemCode',
                    label: '系统编码',
                    type: 'lookCode',
                    lookCode: 'SYSTEM_CODE'
                },
                {key: 'responsibilityDesc_like', label: '职责描述'},
                {key:'isEfficacious',label:'是否生效',type:'lookCode',lookCode:'YES_OR_NO'},
                {key:'startDateActive_gte',label:'生效日期自',type:'time',endDate:'startDateActive_lte'},
                {key:'startDateActive_lt',label:'生效日期至',type:'time',startDate:'startDateActive_gte'},

            ];

        }

        //删除
        $scope.btnDel = function () {
            responsibilityDelete({params: JSON.stringify({id: $scope.dataTable.selectRow.responsibilityId})}, function (res) {
                if (res.status === 'S') {
                    $scope.dataTable.search();
                }
            })
        }

        $scope.btnNew = function () {
            $rootScope.goto('添加职责', '/systemsetting/respSave/0')
        };

        $scope.btnModify = function () {

            $rootScope.goto('职责详情：' + $scope.dataTable.selectRow.responsibilityName , '/systemsetting/respSave/' + $scope.dataTable.selectRow.responsibilityId);
        }
    });
});
