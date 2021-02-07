'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX,fixedReport) {
    app.controller('ttaReportABOISummaryCtrl', function ($scope, $filter, $location, $timeout,$rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,lookupCode,tableXlsExport,saafLoading) {

        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        $scope.cWTable = {};
        $scope.ABOIdDataTable = {};
        $scope.aboiParams = {};
        $scope.currentList2 = [] ;
        $scope.batchId = $stateParams.batchId;
        var date=new Date;
        var y = date.getFullYear();
        $scope.params = {'reportYear':y.toString()} ;
        $scope.paramsBrand = {'reportYear':y.toString()} ;
        $scope.paramsGroup = {'reportYear':y.toString()} ;
        $scope.title = {} ;
        $scope.splitDataTable = [] ;
        $scope.firstText = "请选择";
        $scope.search = function (){
            $scope.searchTitle();
            $scope.cWTable.getData() ;
            $scope.getHeight('fixedId','parentFixedId');

        };

        $scope.searchBrand = function (){
            $scope.BrandTable.getData() ;

        };
        $scope.searchGroup = function (){
            $scope.groupTable.getData() ;

        };

        $scope.afterGetData =function () {
            $scope.getHeight('fixedId','parentFixedId') ;
        };
        $scope.brandAfterGetData =function () {
            $scope.getHeight('brandFixedId','brandParentFixedId') ;
        };

        $scope.groupAfterGetData =function () {
            $scope.getHeight('groupFixedId','groupParentFixedId') ;
        };

        //查询标题
        $scope.searchTitle = function (){

            httpServer.post(URLAPI.findTermsListTtaReportAboiSummaryFixLine,
                {params: JSON.stringify({
                        reportYear:$scope.params.reportYear
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.title = res.data;
                        $scope.rowFcs = "";
                        $scope.rowAc = "";
                        for (var i = 0 ; i< $scope.title.length; i++) {
                            $scope.rowFcs =   $scope.rowFcs + 'row.fcs['+i+'].feeIntas*1 + ';
                            $scope.rowAc =   $scope.rowAc + 'row.ac['+i+'].feeIntas*1 + ';
                        }
                        $scope.rowFcs =   $scope.rowFcs + '0';
                        $scope.rowAc =   $scope.rowAc + '0';
                        $scope.rowFcsOfP =   "((" +$scope.rowFcs + ')/row.purchase*100).toFixed(2)';
                        $scope.rowAcOfP =   "((" +$scope.rowAc + ')/row.netpurchase*100).toFixed(2)';
                        console.log($scope.rowFcs) ;
                        console.log($scope.rowFcsOfP) ;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };
        $scope.searchTitle();


        $scope.tabChangeInfo = function (name) {
            $scope.tabAction = name;
            if (name=='cWInfo') {

            }else if (name=='brandInfo'){

            }
        };

        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }

        /**
         * 保存行
         */
        $scope.btnSave = function (form){
            if(!validateForm(form)){
                return;
            }
            $scope.saveAll =  $scope.cWTable.data ;
            httpServer.post(URLAPI.ttaReportAboiSummarySaveOrUpdateALL,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('保存', 'success', '确定');
                        $scope.search();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };


        /***************************上传end ****************/
        /**
         * 多选
         * @param item
         * @param e
         * @param t
         */
        $scope.check = function (item, e, t) {
            $scope.flag2 = false ;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.currentList2.length; i++) {
                if (item.cwId === $scope.currentList2[i].cwId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {
                item.checked = true; // 选中标识
                $scope.currentList2.push(item)
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－２０１８－１－９
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };


        // 全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag2 = false ;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.cWTable.data) {
                    var row = $scope.cWTable.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.cwId === list.cwId) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.cWTable.data) {
                    var row = $scope.cWTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };


        $scope.btnExport = function (){
            iframeTabAction('REPORT_ABOI_SUMMARY_12：','/reportExportRecord/' + 12);

        };


        $scope.getHeight = function (id ,id2){
            var w = jQuery(window);
            var _top = jQuery("#" + id2).offset().top * 1;
            var _h = w.innerHeight();
            jQuery("#" + id2).css("height", (_h - _top) + "px");
            w.bind('resize', function () {
                jQuery("#" + id2).css("height", (w.innerHeight() - _top) + "px");
                $timeout(function () {
                    $scope.$apply();
                }, 100)
            });
            //
            $timeout(function () {
                var divFix = document.getElementById(id) ;
                var divParentFixed = document.getElementById(id2) ;
                var rect = divFix.getBoundingClientRect();
                var rectParent = divParentFixed.getBoundingClientRect();
                if ((rect.height/rectParent.height) >= 0.9){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 'auto';
                }
            }, 200)
        }



    });
});
