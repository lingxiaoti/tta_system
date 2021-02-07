'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('reportHeaderCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {
        $scope.reportType = $stateParams.reportType;
        $scope.params = {};
        $scope.logoImg = [];
        $scope.addDate = {};
        $scope.deptGroupTable = {};
        $scope.osdDeptGroupTable = {};
        $scope.cwDeptGroupTable = {};
        $scope.hwbDeptGroupTable = {};
        $scope.nppDeptGroupTable = {};


        $scope.params.reportType = $scope.reportType;
        $scope.params.isPublish = $location.search()['isPublish'];

        if ($scope.reportType === 'FG') {
            $scope.legend = [
                {'value':'promotionSectionName','name':'年份'},
                {'value':'batchId','name':'批次号'},
                {'value':'isPublish','name':'是否发布','boolean':'icon'},
                {'value':'isPublishByName','name':'发布人'},
                {'value':'isPublishDate','name':'发布时间'}
            ];

            $scope.searchLegend = [
                {key:'promotionSection',label:'年份',type:'time','date':'yyyy'},
                {key:'batchId',label:'批次号'},
                {key:'isPublish',label:'是否发布',type:'lookCode',lookCode:'YES_OR_NO'}
            ]
        } else {
            $scope.legend = [
                {'value':'promotionSectionName','name':'促销期间'},
                {'value':'batchId','name':'批次号'},
                {'value':'isPublish','name':'是否发布','boolean':'icon'},
                {'value':'isPublishByName','name':'发布人'},
                {'value':'isPublishDate','name':'发布时间'}
            ];
            $scope.searchLegend = [
                {key:'promotionSection',label:'促销期间'},
                {key:'batchId',label:'批次号'},
                {key:'isPublish',label:'是否发布',type:'lookCode',lookCode:'YES_OR_NO'}
            ]
        }

        $scope.btnSave=function(){
            httpServer.post(URLAPI.salesSiteSave, {
                params: JSON.stringify({salesSite:$scope.addParams.salesSite,salesSite2:$scope.addParams.salesSite2,
                    working:$scope.addParams.working,status:$scope.addParams.status,
                    lineCode:$scope.addParams.lineCode,detailSite:$scope.addParams.detailSite,
                    promotionGuideline:$scope.addParams.promotionGuideline})
            }, function (res) {
                if ('S' == res.status) {
                    $('#msgCfgModal').modal('toggle');
                    $scope.dataTable.search();
                    JS.alert('保存成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                JS.alert('保存失败', 'error', '确定');
            })

        };

        /**
         * 发布
         * @constructor
         */
        $scope.FBOI = function () {
            if(!$scope.dataTable  || !($scope.dataTable.selectRow) ){
                SIEJS.alert('请选择一行！', "error");
                return ;
            }

            httpServer.post(URLAPI.saveOrUpdateReportHeader, {
                params: JSON.stringify($scope.dataTable.selectRow)
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

        };


        //根据页面传参判断要跳转的报表页面
        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow;
            if($scope.reportType == "IR"){
                iframeTabAction('newReport'+row.batchId, 'irTerms2/' + row.batchId);
            } else if ($scope.reportType == "CW"){
                iframeTabAction('C&W_REPORT：'+row.batchId, 'CWcheckingreport/' + row.batchId);
            } else if ($scope.reportType == "FG"){
                iframeTabAction('Free Goods Checking Report：'+row.batchId, 'freeGoods2/' + row.batchId);
            } else if ($scope.reportType == "OSD"){
                iframeTabAction('OSD_REPORT：'+row.batchId, 'monthlyChecking2/' + row.batchId);
            } else if ($scope.reportType == "HWB"){
                iframeTabAction('HWB checking Report：'+row.batchId, 'ttaHwbChecking/' + row.batchId);
            } else if ($scope.reportType == "DM"){
                iframeTabAction('DM checking Report：'+row.batchId, 'DMcheckingreport/' + row.batchId);
            }else if ($scope.reportType == "NPP") {
                iframeTabAction('NPP new Product checking Report：'+row.batchId, 'NppNewProduct/' + row.batchId);
            }

        };
        $scope.selectReturn =function(key, value, currentList) {

        };
        $scope.btnPowerSearch = function () {
            var row = $scope.dataTable.selectRow;
            if (!row) {
                SIEJS.alert('请选择一行', 'error', '确定');
                return;
            }
            $scope.powerParam = {'batchCode':row.batchId};
            if($scope.reportType == "IR"){
            } else if ($scope.reportType == "CW"){
                // iframeTabAction('C&W checking Report：', 'CWcheckingreport');
                $('#selectCwSearch').modal('toggle');
            } else if ($scope.reportType == "FG"){
                //iframeTabAction('Free Goods Checking Report：', 'freeGoods');
                $scope.freeGoodsNewData();
            } else if ($scope.reportType == "OSD"){
                //iframeTabAction('Monthly Checking Report：', 'monthlyChecking');
                $('#selectOsdSearch').modal('toggle');
            } else if ($scope.reportType == "HWB"){
                //  iframeTabAction('Attendance Fee checking Report：', 'ttaHwbChecking');
                $('#selectHwbSearch').modal('toggle');
            } else if ($scope.reportType == "DM") {
                $('#selectDmSearch').modal('toggle');
            } else if ($scope.reportType == "NPP") {
                $("#selectNPPSearch").modal('toggle');
            }

        };
        $scope.btnNew=function(){
            var row = $scope.dataTable.selectRow;

            if($scope.reportType == "IR"){
                iframeTabAction('IR Term Checking Report：', 'irTerms');
            } else if ($scope.reportType == "CW"){
               // iframeTabAction('C&W checking Report：', 'CWcheckingreport');
                $('#selectTtaOiCw').modal('toggle');
            } else if ($scope.reportType == "FG"){
                //iframeTabAction('Free Goods Checking Report：', 'freeGoods');
                //$scope.freeGoodsNewData();
                $("#freeGoodModal").modal('toggle');
            } else if ($scope.reportType == "OSD"){
                //iframeTabAction('Monthly Checking Report：', 'monthlyChecking');
                $('#selectTtaOiOSD').modal('toggle');
            } else if ($scope.reportType == "HWB"){
              //  iframeTabAction('Attendance Fee checking Report：', 'ttaHwbChecking');
                $('#selectTtaOiHwb').modal('toggle');
            } else if ($scope.reportType == "DM") {
                $('#selectTtaOiDM').modal('toggle');
            } else if ($scope.reportType == "NPP") {
                //iframeTabAction('Free Goods Checking Report：', 'freeGoods');
                //$scope.NppNewProductData();
                $("#NppModal").modal('toggle');
            }

        };

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
        };

        /****************************** DM报表 start**************************************************************/
        /**
         * DM 生成数据
         */
        $scope.selectDmReturn = function(key, value, currentList) {
            //var row = $scope.dataTable.selectRow;
            httpServer.post(
                URLAPI.saveOrUpdateDmOrder,
                {
                    'params': JSON.stringify({"durationPeriod": value})
                },
                function (res) {
                    if (res.status == 'S') {
                        iframeTabAction('DM_REPORT：'+ res.data.report.batchId,'/DMcheckingreport/' + res.data.report.batchId);
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        };
        /****************************** DM报表 end**************************************************************/

        /**
         * 新增FG数据
         */
        $scope.freeGoodsNewData = function () {
            httpServer.post(
                URLAPI.saveFreeGoodsByPoList,
                {
                    'params': JSON.stringify({
                        yearMonth:$scope.addDate.yearMonth
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $("#freeGoodModal").modal('toggle');
                        iframeTabAction('Free Goods Checking Report：'+res.data.fgReport.batchId, '/freeGoods2/' + res.data.fgReport.batchId);
                        $scope.dataTable.search(1);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        };

        /**
         * NPP新品报表生成数据
         */
        $scope.NppNewProductData = function () {
            $("#NppModal").modal('toggle');
            httpServer.post(
                URLAPI.saveOrUpdateByNppQuery,
                {
                    'params': JSON.stringify({
                        yearMonth:$scope.addDate.yearMonth
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        //iframeTabAction('Free Goods Checking Report：'+res.data.fgReport.batchId, '/freeGoods2/' + res.data.fgReport.batchId);
                        iframeTabAction('NPP new Product checking Report：'+res.data.report.batchId, '/NppNewProduct/' + res.data.report.batchId);
                        $scope.dataTable.search(1);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('数据获取失败！', "error");
                }
            );
        };

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
        };


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
        };


        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow;
            if (row.isPublish == 'Y') {
                SIEJS.alert('只能删除未发布的记录！', "error");
                return;
            }
            SIEJS.confirm('您确认要删除吗？', '', '确定', function () {
                httpServer.post(
                    URLAPI.deleteReportHeader,
                    {
                        'params': JSON.stringify({
                            "reportId": row.id
                        })
                    },
                    function (res) {
                        if (res.status.toLowerCase() == 'e') {
                            $scope.dataTable.search(1);
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
