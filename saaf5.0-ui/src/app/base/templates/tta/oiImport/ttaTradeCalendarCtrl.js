'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree,XLSX) {
    app.controller('ttaTradeCalendarCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,$http,saafLoading) {
        $scope.buttonFlag = true ;
        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteImportTCInfo,

                    {
                        'params': JSON.stringify({
                            "trandeId": row.trandeId,
                            flag :'single'
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
        };
        var exportTimer;
        /**
         * 批量删除
         * @constructor
         */
        $scope.PLSC = function () {
            $scope.paramsPL = {} ;
            $('#PLDelete').modal('toggle')
        }
        // 查询导出结果
        function exportStatus(code) {
            var params = {
                params:"{Certificate:\""+code+"\"}"

            };
            $.ajax({
                url: URLAPI.getOiStatus,
                type: 'post',
                data: params,
                timeout: 60000 * 5,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "Certificate": sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                },
                dataType: 'html',
                success: function (res) {
                    var res = JSON.parse(res);
                    if (res.status === 'S') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        clearInterval(exportTimer);
                    } else  if (res.status === 'E' || res.status === 'M') {
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        clearInterval(exportTimer);
                    }else{
                        $scope.messageCurrentStage = res.data.currentStage ;
                        jQuery("#value1").text(res.data.currentStage);
                        jQuery("#value2").text(res.data.orderNum);
                        $scope.messageCurrentOrderNum = res.data.orderNum ;
                    }

                },
                error: function (e) {
                    clearInterval(exportTimer);
                }
            });
        }
        $scope.saveWindow = function (invalid) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(
                URLAPI.deleteImportTCInfo,

                {
                    'params': JSON.stringify({
                        "month": $scope.paramsPL.month,
                        flag :'pl'
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
            );
            $('#PLDelete').modal('toggle');
        }
        //新增
        $scope.daoru = function () {
            $('#excelTtaTradeCalendar').modal('toggle');
            $scope.buttonFlag = true ;
        }


        //上传附件
        $scope.save = function (invalid) {
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            var fd = new FormData();
            var file = document.getElementById('fileUpLoad').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            saafLoading.show();
            $scope.buttonFlag = false ;
            fd.append('file', file);
            exportTimer = setInterval(function () {
                exportStatus(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
            }, 5000);
            $http.post(URLAPI.saveImportTCInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                saafLoading.hide();
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function(response) {
                saafLoading.hide();
                SIEJS.alert(response.msg, 'error', '确定');
                $scope.buttonFlag = true ;
                clearInterval(exportTimer);
            });

        }
    });
});
