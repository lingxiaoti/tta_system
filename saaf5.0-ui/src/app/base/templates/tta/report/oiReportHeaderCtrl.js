'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('oiReportHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        $scope.reportType = $stateParams.reportType;
        $scope.params = {}
        $scope.logoImg = []
        $scope.currentData = {};

        $scope.params.reportType = $scope.reportType;

        $scope.btnSave=function(){
            httpServer.post(URLAPI.salesSiteSave, {
                params: JSON.stringify({salesSite:$scope.addParams.salesSite,salesSite2:$scope.addParams.salesSite2,
                    working:$scope.addParams.working,status:$scope.addParams.status,
                    lineCode:$scope.addParams.lineCode,detailSite:$scope.addParams.detailSite,
                    promotionGuideline:$scope.addParams.promotionGuideline})
            }, function (res) {
                if ('S' == res.status) {
                    $('#msgCfgModal').modal('toggle')
                    $scope.dataTable.search();
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })

        }

        /**
         * 发布
         * @constructor
         */
        $scope.FBOI = function (confirmStatus) {
            if(!$scope.dataTable  || !($scope.dataTable.selectRow) ){
                SIEJS.alert('请选择一行！', "error");
                return ;
            }
            var params = $scope.dataTable.selectRow;
            if (confirmStatus) {
                params.confirmStatus = confirmStatus;
            }
            httpServer.post(URLAPI.saveOrUpdateOiReportHeader, {
                params: JSON.stringify(params)
            }, function (res) {
                if ('S' == res.status) {
                    $scope.dataTable.search();
                    SIEJS.alert('发布成功');
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                SIEJS.alert('发布失败', 'error', '确定');
            })

        }


        //根据页面传参判断要跳转的报表页面
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            if ($scope.reportType == "GP"){
                iframeTabAction('YTD GP% Simulation_sample：'+row.batchCode, 'oiGpReport/' + row.batchCode);
            }

        }

        $scope.btnNew=function(){
            var row = $scope.dataTable.selectRow;
            if ($scope.reportType == "GP") {
                $('#selectTtaOiGP').modal('toggle'); // 第5个OI报表数据新增
            }
        }

        /**
         * C&W 生成数据
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectCwReturn = function(key, value, currentList) {
            //var row = $scope.dataTable.selectRow;
            httpServer.post(
                URLAPI.saveOrUpdateFindTtaCw,
                {
                    'params': JSON.stringify({"promotionSection": value,'pogSpaceLineId':key})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('C&W_REPORT：'+ res.data.report.batchId,'/CWcheckingreport/' + res.data.report.batchId);
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

        /****************************** 第5个报表GP start **************************************************************/
        /**
         * 第5个报表 gp成数据
         */
        $scope.selectGpReturn = function(key) {
            httpServer.post(
                URLAPI.saveGpSimulation, {
                    'params': JSON.stringify({"endDate": $scope.currentData.endDate})
                },
                function (res) {
                    if (res.status == 'S') {
                        $('#selectTtaOiGP').modal('toggle'); // 第5个OI报表数据新增
                        iframeTabAction('YTD GP% Simulation_sample：'+ res.data.report.batchCode, 'oiGpReport/' +  res.data.report.batchCode);
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
        /****************************** 第5个报表GP end**************************************************************/

        /**
         * 新增FG数据
         */
        $scope.freeGoodsNewData = function () {
            httpServer.post(
                URLAPI.saveFreeGoodsByPoList,
                {
                    'params': JSON.stringify({})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('Free Goods Checking Report：'+res.data.fgReport.batchId, '/freeGoods2/' + res.data.fgReport.batchId);
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

        /**
         * NPP新品报表生成数据
         */
        $scope.NppNewProductData = function () {
            httpServer.post(
                URLAPI.saveOrUpdateByNppQuery,
                {
                    'params': JSON.stringify({})
                },
                function (res) {
                    if (res.status == 'S') {
                        //iframeTabAction('Free Goods Checking Report：'+res.data.fgReport.batchId, '/freeGoods2/' + res.data.fgReport.batchId);
                        iframeTabAction('NPP new Product checking Report：'+res.data.report.batchId, '/NppNewProduct/' + res.data.report.batchId);
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

        /**
         * OSD 生成数据
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectOSDReturn = function(key, value, currentList) {
            //var row = $scope.dataTable.selectRow;
            httpServer.post(
                URLAPI.saveOrUpdateTtaOsdMajorFind,
                {
                    'params': JSON.stringify({"promotionSection": value,'osdBaseLineId':key})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('OSD_REPORT：'+ res.data.report.batchId,'/monthlyChecking2/' + res.data.report.batchId);
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


        /**
         * HWB 生成数据
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectHwbReturn = function(key, value, currentList) {
            //var row = $scope.dataTable.selectRow;
            httpServer.post(
                URLAPI.saveOrUpdateTtaHwbMajorFind,
                {
                    'params': JSON.stringify({"promotionSection": value,'hwbBaseLineId':key})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('HWB_REPORT：'+ res.data.report.batchId,'/ttaHwbChecking/' + res.data.report.batchId);
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

    });
});
