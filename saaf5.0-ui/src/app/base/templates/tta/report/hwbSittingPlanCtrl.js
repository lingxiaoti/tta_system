'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree,XLSX) {
    app.controller('hwbSittingPlanCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除'+row.timesVersion+'批次数据吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteImportSittingPlanInfo,

                    {
                        'params': JSON.stringify({
                            "timesVersion": row.timesVersion
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            SIEJS.alert(res.msg, "error");
                        } else {
                            SIEJS.alert(res.msg, "success");
                            $scope.dataTable.search(1);

                        }
                    }
                )
            });
        }
    });
});
