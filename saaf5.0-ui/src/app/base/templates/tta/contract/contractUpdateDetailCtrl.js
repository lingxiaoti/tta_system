/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','fixed'], function (app, pinyin, ztree, angularFileUpload,fixed) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('contractUpdateDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction,$compile,$timeout,$http, validateForm) {
    $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
    app.filter('filterByKey', function () { // key为变量
        return function (input, key, searchkey) {
            var newArr = [];
            if (!searchkey) {
                return input;
            }
            for (var i = 0, l = input.length; i < l; i++) {
                var value = input[i][key].toString();
                if (value.indexOf(searchkey) == -1) {
                    newArr.push(input[i]);
                }
            }
            return newArr;
        };
    });

        $scope.params = {};
        $scope.proposalInfoIsShow = true ;
        $scope.contractHeader = {}; //合同头部数据显示
        $scope.proposalTitleList = []; //proposal行title信息
        $scope.proposalTitleBottomList = [];
        $scope.proposalContractList = [];//proposal条款信息
        $scope.vendorContractList = [];
        $scope.warehouseParamsT = {lookupType :'TTA_TERMS_WH'};
        $scope.warehouseParamsTDataTable = [] ;
        // 切换页面触发查询函数
        $scope.tabChange = function (name) {
            /*if (name=='contractBox') {
                $scope.search();
            }*/
        };
        $scope.pdfDataList = [];// pdf需要的参数信息
/***************************************** 合同输出拆分与编辑界面 start********************************/
        //查看报表详情
        $scope.btnDetail = function () {
            //console.log($scope.dataTable.selectRow)
            if ($scope.vendorContractList.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var index = $scope.vendorContractList.selectRow;
            var arr = $scope.vendorContractList[index];
            var strParams = "&venderCode=" + arr[0].fieldValue
                + "&venderName=" +  arr[1].fieldValue
                + "&contractHId=" + $scope.contractHeader.contractHId
                + "&proposalId=" + $scope.contractHeader.proposalId
                + "&proposalCode=" + $scope.contractHeader.proposalCode
                + "&proposalYear=" + $scope.contractHeader.proposalYear
                + "&proposalVersion=" + $scope.contractHeader.proposalVersion;
            console.log("跳转页面的参数信息：" + strParams);
            iframeTabAction('报表详情：' + arr[1].fieldValue, '/contractTermsDetail/' + $scope.contractHeader.proposalId + "?onlyShow=1" + strParams);
        };

        $scope.btnParentDetail = function() {
            var str = "&proposalVendorNbr=" + $scope.contractHeader.contractCode + "&contractHId=" + $scope.contractHeader.contractHId;
            iframeTabAction('报表详情：' + $scope.contractHeader.vendorName , '/contractTermsDetail/' + $scope.contractHeader.proposalId + "?resource=PARENT_VENDER_CONTRACT" + str);
        };

        $scope.pdfPrint = function(url) {
            console.log("url:" + url);
            iframeTabAction('pdf输出打印' , url);
        };

        $scope.params.contractHId = $stateParams.id;
        var filterData =[];
        $scope.searchPro = function () {
            httpServer.post(URLAPI.contractLineFindPro, {
                    'params': JSON.stringify({orderNbr: $scope.params.proposalCode, proposalId: $scope.params.proposalId, contractHId: $scope.params.contractHId})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractHeader = res.contractHeader; //合同头部数据显示
                        $scope.proposalTitleList = res.proposalTitleList; //proposal行title信息
                        $scope.proposalTitleBottomList = res.proposalTitleBottomList; //proposal行title底部信息
                        $scope.proposalContractList = res.proposalContractList; //proposal条款信息
                        $scope.vendorContractList = res.vendorContractList;//拆分后的供应商条款信息
/*                        for(var i = 0 ; i<$scope.vendorContractList.length;i++){
                            filterData.push();
                        }*/
                        //打印需要设计的参数信息start
                        $scope.pdfDataList = [];
                        var  excludeParentVenderFlag = true;
                        
                        if ($scope.vendorContractList) {
                            for (var i = 0; i < $scope.vendorContractList.length; i++) {
                                var arr = $scope.vendorContractList[i];
                                if (arr[0].fieldValue == $scope.proposalContractList[0]) {
                                    excludeParentVenderFlag = false;
                                }
                                var strParams = "&venderCode=" + arr[0].fieldValue
                                    + "&venderName=" +  arr[1].fieldValue
                                    + "&contractHId=" + $scope.contractHeader.contractHId
                                    + "&proposalId=" + $scope.contractHeader.proposalId
                                    + "&proposalCode=" + $scope.contractHeader.proposalCode
                                    + "&proposalYear=" + $scope.contractHeader.proposalYear
                                    + "&proposalVersion=" + $scope.contractHeader.proposalVersion;
                                var url = '/contractTermsPdf/' + $scope.contractHeader.proposalId + "?onlyShow=1" + strParams;
                                $scope.pdfDataList.push({
                                                                     "url":url,
                                                                    "vendor_nbr":arr[0].fieldValue,
                                                                    "OrderNo":$scope.contractHeader.contractCode

                                                                });
                            }
                        }

                        if (excludeParentVenderFlag) {
                            var parentUrl = '/contractTermsPdf/' + $scope.contractHeader.proposalId + "?resource=PARENT_VENDER_CONTRACT" + "&proposalVendorNbr=" + $scope.contractHeader.contractCode + "&contractHId=" + $scope.contractHeader.contractHId;
                            $scope.pdfDataList.push({
                                "url":parentUrl,
                                "vendor_nbr":$scope.proposalContractList[0],
                                "OrderNo":$scope.contractHeader.contractCode

                            });
                            //$scope.pdfDataList.push(parentUrl);
                        }
                        //打印需要设计的参数信息end

                        $scope.getHeight();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询单据信息
        if ($stateParams.id) {
            //查询头信息
            $scope.searchPro()
        }

        //点击确认按钮
        $scope.selectLatentcontractReturn = function (key, value, currentList) {
            $scope.params.proposalCode = $scope.contractHeader.proposalCode = currentList[0].orderNbr;
            $scope.params.proposalId = currentList[0].proposalId;
            $scope.searchPro();
        };

        //指定供应商,并拆分供应商
        $scope.selectVendor = function (key, value, currentList) {
            if (currentList && currentList.length > 0) {
                SIEJS.confirm('提示', '要确认要拆分供应商吗？', '确定', function () {
                    httpServer.post(URLAPI.contractLineSave, {
                            params: JSON.stringify({
                                venderList: JSON.stringify(currentList),
                                contractHId :$scope.contractHeader.contractHId,
                                proposalId: $scope.contractHeader.proposalId,
                                isSpecial: $scope.contractHeader.isSpecial
                            })
                        },
                        function (res) {
                            if (res.status == 'S') {
                                $scope.searchPro();
                                SIEJS.alert(res.msg, "success", '确定')
                            } else {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                        },
                        function (err) {
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        }
                    );
                });
            }
        };

        //下载文件
        $scope.btnXZ = function () {
                    httpServer.post(URLAPI.ttaConAttrLineFindFileIds, {
                            params: JSON.stringify({
                                businessId :$scope.contractHeader.contractHId,
                                functionId: 'TTA_CON_ATTR_LINE'
                            })
                        },
                        function (res) {
                            if (res.status == 'S') {
                                var url = URLAPI.multiDownload + '?fileIds=' + res.data.fileIds +'&certificate='+(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing') +"&type=fast";
                                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                            } else {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                        },
                        function (err) {
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        }
                    );
        };

        $scope.btnCreate = function () {

            var loading = $("#saafLoading");
            if (loading.length === 0) {
                var loading = $('<div id="saafLoading"><div  style="position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;"><img src="img/loading1.gif"></div></div>');
                loading.css({
                    position: "fixed",
                    top: 0,
                    width: "100%",
                    "z-index": 9000,
                    "height": "100%",
                    'display': 'none'
                });
                loading.prependTo(angular.element('body'));
            } else {
                loading.removeAttr('data-loading');
            }
            loading.show();
            $http.post(URLAPI.contractLinePDFPrint, 'params=' +encodeURIComponent (JSON.stringify(
                {
                    urlsAndParams: $scope.pdfDataList,
                    userId: userLoginInfo.userId,
                    id:$scope.contractHeader.contractHId,
                    Certificate:sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
                })), {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                }
            }).success(function (response) {
                if (response.status == 'S') {
                    loading.hide();
                    SIEJS.alert(response.msg, 'success', '确定');

                } else {
                    loading.hide();
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                loading.hide();
                SIEJS.alert("失败", 'error', '确定');
            });
        };
        //删除行
        $scope.deleteRow = function () {
            if($scope.contractHeader.billStatus == "C"){
                SIEJS.alert('操作失败，确认状态不能删除！', "error", "确定");
                return "false";
            }
            SIEJS.confirm('提示', '确认要删除拆分后的结果吗？', '确定', function () {
                var vendorCodeList = [];
                var index = $scope.vendorContractList.selectRow;
                var arr = $scope.vendorContractList[index];
                var map = arr[0];
                if (map.fieldName == 'vendor_code') {
                    vendorCodeList.push(map.fieldValue);
                }
                httpServer.post(URLAPI.contractLineDelRow, {
                        'params': JSON.stringify({vendorCodeList: vendorCodeList, contractHId: $scope.params.contractHId})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.searchPro();
                            SIEJS.alert("删除成功", "success", '确定')
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            })
        };

        //清除值
        $scope.clearSelect = function (row){
            row.fieldValue = '';
        };

        //配送仓库
        $scope.selectWarehouse = function() {
            var selectArr = [];
            var list = $scope.warehouseParamsTDataTable.selectRowList;
            $scope.vendorContractList.selectRowData[$scope.vendorContractList.selectCol].fieldValue = '';
            angular.forEach(list, function(item,index,array){
                $scope.vendorContractList.selectRowData[$scope.vendorContractList.selectCol].fieldValue += item.meaning + ",";
                selectArr.push(item.meaning);
            });
            var  warehouseStr = $scope.proposalContractList[2] + "";
            var  warehouseArr = warehouseStr.split(",");
            if (warehouseStr.trim() == "" && selectArr.length > 0) {
                SIEJS.alert("不一致提示：原供应商有0个仓库，当前选择" + selectArr.length + "个仓库.", "error", "确定");
            } else {
                if (selectArr.length != warehouseArr.length) {
                    SIEJS.alert("不一致提示：原供应商有" + warehouseArr.length + "个仓库，当前选择有" + selectArr.length +"个仓库！", "error", "确定");
                }
            }
            $('#warehouseChange').modal('toggle');
        };

        //销售区域
        $scope.selectSalesArea = function(key, value, currentList) {
            var selectArr = [];
            var values = currentList.filter(function (x) {
                return x.meaning == '全国';
            });
            if(values.length>0 && currentList.length>1){
                SIEJS.alert('全国,其他不能同时选', "error", "确定");
                $('#salesAreaChange').modal("show");
                return ;
            }
            $scope.vendorContractList.selectRowData[$scope.vendorContractList.selectCol].fieldValue = '';
            angular.forEach(currentList, function(item,index,array){
                $scope.vendorContractList.selectRowData[$scope.vendorContractList.selectCol].fieldValue  += item.meaning+",";
                selectArr.push(item.meaning);
            });
            var  areaArrStr = ($scope.proposalContractList[3] + "");
            var areaArr = areaArrStr.split(",");//区域地址;
            if (areaArrStr.trim() == "" && selectArr.length > 0) {
                SIEJS.alert("不一致提示：原供应商有0个区域，当前选择" + selectArr.length + "个区域.", "error", "确定");
            } else {
                if (selectArr.length != areaArr.length) {
                    SIEJS.alert("不一致提示：原供应商有" + areaArr.length + "个区域，当前选择" + selectArr.length +"个区域。", "error", "确定");
                }
            }

            $('#salesAreaChange').modal("hide");
            $('.modal-backdrop').remove();
        };

        $scope.selectSalesAreaClick = function (key, value, currentList){
            var values = currentList.filter(function (x) {
                return x.meaning == '全国';
            });
            if(values.length>0 && currentList.length>1){
                SIEJS.alert('全国,其他不能同时选', "error", "确定");
                return {'flag':'-1','msg':'全国,其他不能同时选'};
            }
            return {'flag':'1','msg':''} ;
        };


        //保存头信息
        $scope.btnSave = function(value, form)  {
            /* if(!validateForm(form)){
                    return;
                }*/
            $scope.billStatus = $scope.contractHeader.billStatus;
            if ("D" == value) {
                $scope.contractHeader.billStatus = 'A';//取消状态，则赋值为制单状态
            } else {
                $scope.contractHeader.billStatus = value;
            }
            httpServer.post(URLAPI.contractHeaderSave, {
                    params: JSON.stringify({
                        contractHeader:$scope.contractHeader,/*保存头部信息*/
                        vendorContractList: $scope.vendorContractList, /*保存拆分信息，对应原始saveBatchSplitResult请求*/
                        optStatus : value, //操作类型,A保存，C确认,D取消
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params.contractHId = res.data.contractHId;
                        $scope.searchPro();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        $scope.contractHeader.billStatus =  $scope.billStatus;
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };



        //保存拆分信息
        $scope.saveBatchSplitResult = function() {
            httpServer.post(URLAPI.saveBatchSplitResult, {
                    params: JSON.stringify({vendorContractList:$scope.vendorContractList})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.searchPro();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //选择proposal信息
        $scope.getProposalCode = function () {
            //  $scope.areaComponent = e;
            $('#latentcontractCode').modal('toggle')
        };

        //合同表格选择供应商
        $scope.getContractVendor = function () {
            //  $scope.areaComponent = e;
            $scope.cityLovs.search();
            $('#contractVendors').modal('toggle')
        };

        //合同信息作废
        $scope.btnDiscard = function () {
            if($scope.hstatus == "C"||$scope.hstatus == "D"){
                SIEJS.alert('操作失败，非制作中状态不能修改', "error", "确定");
                return "false";
            }
            if($scope.contractHId == ""){
                SIEJS.alert('操作失败，请先保存数据', "error", "确定");
                return false;
            }
            SIEJS.confirm('提示', '确认作废合同信息？', '确定', function () {
                httpServer.post(URLAPI.contractHeaderCancelConfirm,
                    {params: JSON.stringify({contractHId: $scope.contractHId})},
                    function (res) {
                        if (res.status == "S") {


                            $scope.search();

                            SIEJS.alert("处理成功", "success", "确定");

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );
            })
        };


        //合共信息提交
        $scope.btnSubmit = function () {
            if($scope.hstatus == "C"||$scope.hstatus == "D"){
                SIEJS.alert('操作失败，非制作中状态不能修改', "error", "确定");
                return "false";
            }
            if($scope.contractHId == ""){
                SIEJS.alert('操作失败，请先保存数据', "error", "确定");
                return false;
            }
            SIEJS.confirm('提示', '确认的提交合同信息？', '确定', function () {
                httpServer.post(URLAPI.contractHeaderConfirm,
                    {params: JSON.stringify({contractHId: $scope.contractHId})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.search();

                            SIEJS.alert("处理成功", "success", "确定");

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );
            })
        };

        $scope.showBtn= function () {
            if (!$scope.params.contractHId){
                SIEJS.alert("请先选择合同编号并保存，才能改变拆分条件！", "error", "确定");
                return;
            }
            
            httpServer.post(URLAPI.contractHeaderSave, {
                    params: JSON.stringify({
                        contractHeader:$scope.contractHeader,/*保存头部信息*/
                        optStatus : 'A', //操作类型,A保存，C确认,D取消
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params.contractHId = res.data.contractHId;
                        $scope.searchPro();
                        SIEJS.alert("条件变更成功", "success", "确定");
                    } else {
                        $scope.contractHeader.billStatus =  $scope.billStatus;
                        SIEJS.alert("条件变更失败", "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        };

/***************************************** 合同输出拆分与编辑界面 end********************************/


        $scope.getHeight = function (){
            //获取div 的实际高度
            $timeout(function () {
                var divFix = document.getElementById('contractData') ;
                $scope.setTtaContractLine("parentFixedId");
                divFix.style.height = 'auto';
                var divParentFixed = document.getElementById('parentFixedId') ;
                var rect = divFix.getBoundingClientRect();
                var rectParent = divParentFixed.getBoundingClientRect();
                if ((rect.height/rectParent.height) >= 0.9){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 'auto';
                }
                var fixedTable1 = fixTable(document.getElementById('contractData'),0);
            }, 200)
        };

        $scope.setTtaContractLine = function (id){

          //  if( !$scope.CountH[id]) {
                var w = jQuery(window);
                var _top = jQuery("#" + id).offset().top * 1;
                //$scope.CountH[id] = _top;
                var _h = w.innerHeight();
                jQuery("#" + id).css("height", (_h - _top) + "px");
                w.bind('resize', function () {
                    jQuery("#" + id).css("height", (w.innerHeight() - _top) + "px");
                    $timeout(function () {
                        $scope.$apply();
                    }, 100)
                });
          //  }
            return (w.innerHeight() - _top);
        } ;
        $scope.ttaValueFunc = function(index){
            var reg=/\d{1,3}(?=(\d{3})+$)/g;
            //return (num + '').replace(reg, '$&,');
            var data = $filter('filterByKey')($scope.vendorContractList.selectRowData,'fieldName' ,'split_parent_id');
            var  dataOne  = data[index].fieldValue ? data[index].fieldValue : "";
            dataOne = dataOne.replace(/[^\-?\d.]/g,"");
            if(dataOne){
                if(dataOne.toString().indexOf(".")>-1){
                    if(dataOne.toString().split(".")[1].length == 0){
                        dataOne = dataOne.toString().slice(0,data[index].fieldValue.toString().length-1)
                    }

                }
                dataOne = dataOne.replace(/,/g,"") ;
                dataOne = (dataOne + '').replace(reg, '$&,') ;
                data[index+1].fieldValue = data[index+1].fieldValue.replace(/\d{1,3}(,\d{1,3}){0,}\.{0,}\d{0,}/, dataOne);
            } else {
                data[index+1].fieldValue = data[index+1].fieldValue.replace(/\d{1,3}(,\d{1,3}){0,}\.{0,}\d{0,}/, 0);
            }

        };

    /***************************  合同输出拆分与编辑界面 end****************/

    });
            
});
