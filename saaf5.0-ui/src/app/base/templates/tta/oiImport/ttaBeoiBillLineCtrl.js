'use strict';
define(['app', 'pinyin', 'ztree','XLSX'], function (app, pinyin, ztree,XLSX) {
    app.controller('ttaBeoiBillLineCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow, $http, saafLoading) {

        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteImportBEOIInfo,

                    {
                        'params': JSON.stringify({
                            "ttaBeoiBillImportId": row.ttaBeoiBillImportId,
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

        /**
         * 批量删除
         * @constructor
         */
        $scope.PLSC = function () {
            $scope.paramsPL = {} ;
            $('#PLDeleteTtaBeoiBillLine').modal('toggle')
        }

        $scope.saveWindow = function (invalid) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(
                URLAPI.deleteImportBEOIInfo,

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
            $('#PLDeleteTtaBeoiBillLine').modal('toggle');
        }
        //新增
        $scope.daoru = function () {
            $("#fileUpLoad").val('');
            $('#excelTtaBeoiBillLine').modal('toggle');
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
           // var fileName = file.name;
            //var sideAgrtHId = id;
            // if (sideAgrtHId == undefined || sideAgrtHId == null){
            //     SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            // }

           // fd.append('sideAgrtHId', id);
            //fd.append("fileName", fileName);
            saafLoading.show();
            fd.append('file', file);
            $http.post(URLAPI.saveImportBEOIInfo, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                saafLoading.hide();
                SIEJS.alert(response.msg, 'success', '确定');
                $('#excelTtaBeoiBillLine').modal('hide');
            }).error(function(response) {
                saafLoading.hide();
                SIEJS.alert(response.msg, 'error', '确定');
            });

        }
    });
});
