/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app'],function(app) {
    app.controller('clauseManageCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,iframeTabAction) {

            /**
             * 新增
             */
            $scope.btnNew = function() {
                iframeTabAction('条款框架新增', '/clauseManageD/'+'-1')
            }
            /**
             *修改
             * @param row
             */
            $scope.btnModify = function () {
                var row = $scope.dataTable.selectRow;
                iframeTabAction('条款框架详情'+ row.year,'/clauseManageD/' + row.teamFrameworkId);
            }
            /**
             * 复制往年的数据
             * @param key
             * @param value
             * @param currentList
             */
            $scope.selectDeptReturn = function(key, value, currentList) {
                httpServer.post(
                    URLAPI.saveOrUpdateCopy,
                    {
                        'params': JSON.stringify({"deptCode": key})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            iframeTabAction('条款框架详情:'+ res.data.year,'/clauseManageD/' + res.data.newId);
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('数据获取失败！', "error");
                    }
                );
            }
        }])
})