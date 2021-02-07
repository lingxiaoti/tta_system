/*
    created by hmb 2019.7.21
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','XLSX'], function (app, pinyin, ztree, angularFileUpload,XLSX) {
    app.useModule('angularFileUpload'); //按需加载弹出层模块
    app.controller('withdrawalDetailCtrl', function ($interval,$filter, httpServer, URLAPI, Base64, $window, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,Cookies,tableXlsExport) {
        //增加tokenCode 参数到Header
        var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        var tokenCode = null;
        if (( successLoginInfo != null && typeof(successLoginInfo) != "undefined") && typeof(successLoginInfo.TokenCode) != "undefined") {
            tokenCode = successLoginInfo.TokenCode;
        } else {
            tokenCode = "INDEX_TOKEN_CODE";
        }

        $scope.dataTables = {};
        $scope.params = {
            condition:'isSplitAndMerge'//区分是独家协议还是proposal补充与合并
        };
        $scope.project = {};
        $scope.dataParams = {};
        var id = $stateParams.id;
        $scope.splitConditionDetail = [];
        $scope.currentSplitConditionDetailRow ={};
        $scope.modify = false;//代表是否修改指定供应商操作
        $scope.operatSupplier = false;//是否是操作指定供应商操作
        $scope.purchaseStatus = "0";
        $scope.currentList2 = [] ;
        $scope.btnControl = "0";//按钮控制
        $scope.CountH = {};//计算高度
        $scope.legend = [];
        $scope.fieldObjet = {};
        $scope.fieldObjet.dataListOptions={};
        $scope.fieldObjet.dataMessage="唯一标识列不可修改";
        $scope.noRequireFieldArray = ['groupCode','groupName','deptCode','deptName','brandName','itemCode','itemName','splitSupplierCode','splitSupplierName','fcsPurchase','fcsSales','brandFcsPurchase','brandFcsSales'];//非必填
        $scope.gArray = ['groupCode','groupName'];
        $scope.gdArray = ['groupCode','groupName','deptCode','deptName'];
        $scope.gdbArray = ['groupCode','groupName','deptCode','deptName','brandName'];
        $scope.gdbiArray = ['groupCode','groupName','deptCode','deptName','brandName','itemCode','itemName'];

        $scope.initDataTableField = function(){
            $scope.legend = [
                {value:'mid',name:'唯一标识'},
                {value:'supplierCode',name:'原供应商编号'},
                {value:'supplierName',name:'原供应商名称'},
                {value:'groupCode',name:'GROUP'},
                {value:'groupName',name:'GROUP_DESC'},
                {value:'deptCode',name:'DEPT_CODE'},
                {value:'deptName',name:'DEPT_DESC'},
                {value:'brandName',name:'BRAND_CN'},
                {value:'itemCode',name:'ITEM'},
                {value:'itemName',name:'ITEM名称'},
                {value:'splitSupplierCode',name:'指定供应商编号'},
                {value:'splitSupplierName',name:'指定供应商名称'}
            ];
        };
        
        /**
         * 构造数据结构
          {
            instCode:{headName:'中心编码',require:true},//设置列信息instCode对应xls表的中心编码列，该选项为必填项
            itemCode:{headName:'内机编码',require:true},//设置列信息itemCode对应xls表的内机编码，该选项为必填项
            startDateActive:{headName:'生效日期',require:true},
            endDateActive:{headName:'失效日期'},
            enabledFlag:{headName:'是否生效',require:true},
            commissionStandard:{headName:'标准',require:true}
        }
         */
        $scope.initDataListOptions = function(splitCondition){
            $scope.fieldObjet.dataListOptions ={};
            if ($scope.legend.length != 0) {
                for (var index in $scope.legend) {
                    var arrLegend = $scope.legend[index];
                    var require = true;
                    if ($scope.noRequireFieldArray.indexOf(arrLegend.value) > -1) {
                        require = false;
                    }

                    if (splitCondition != undefined || splitCondition != null) {
                        switch (splitCondition) {
                            case 'Group':
                                if ($scope.gArray.indexOf(arrLegend.value)>-1){
                                    require = true;
                                }
                                break;
                            case 'Group+Dept':
                                if ($scope.gdArray.indexOf(arrLegend.value)>-1){
                                    require = true;
                                }
                                break;
                            case 'Group+Dept+Brand':
                                if ($scope.gdbArray.indexOf(arrLegend.value)>-1){
                                    require = true;
                                }
                                break;
                            case 'Group+Dept+Brand+Item':
                                if ($scope.gdbiArray.indexOf(arrLegend.value)>-1){
                                    require = true;
                                }
                                break;
                        }
                    }

                    var jsonObject = {headName:arrLegend.name,require:require};
                    $scope.fieldObjet.dataListOptions[arrLegend.value] = jsonObject;
                }
            }
        };

        $scope.init =function(){
            //按顺序初始化,切不可调换位置
            $scope.initDataTableField();
            $scope.initDataListOptions();
        }();

        $scope.$watch('dataTables.data', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag2){
                if(!$scope.flag2){
                    $scope.flag2 = true ;
                }
                return;
            }
            $timeout(function () {
                $scope.currentList2 = [] ;
            })

        }, true);

        //监听project.splitCondition
        $scope.$watch('project.splitCondition', function (newVal, oldVal) {
            if (newVal&&newVal != oldVal) {
                console.log("监听数据变化");
                console.log(newVal);
                console.log(oldVal);
                if (newVal == 'Group+Dept+Brand+Item') {
                    $scope.initDataTableField();
                    var proposalYear = $scope.project.proposalYear;
                    var proposalCode = $scope.project.proposalCode;
                    var fcsPurchaseName = proposalYear + ' TTA F’cs Purchase';
                    var fcsSalesName = proposalYear + ' TTA F’cs Sales';
                    var fcsPurchaseObject = {value:'fcsPurchase',name:fcsPurchaseName};
                    var fcsSalesObject = {value:'fcsSales',name:fcsSalesName};
                    var proposalFcsPurchaseName = "Proposal_" + proposalCode + " F’cs Purchase";
                    var proposalFcsSalesName = "Proposal_" + proposalCode + " F’cs Sales";
                    var proposalFcsPurchaseObject = {value:'brandFcsPurchase',name:proposalFcsPurchaseName};
                    var proposalFcsSalesObject = {value:'brandFcsSales',name:proposalFcsSalesName};
                    $scope.legend.push(fcsPurchaseObject);
                    $scope.legend.push(fcsSalesObject);
                    $scope.legend.splice(10,0,proposalFcsPurchaseObject);
                    $scope.legend.splice(11,0,proposalFcsSalesObject);
                } else {
                    $scope.initDataTableField();
                }

                $scope.initDataListOptions(newVal);
            }
        }, true);

        $scope.currentSplitConditionDetailRow.selectRow = null;//选中行
        $scope.currentSplitConditionDetailRow.selectRowList = [];//所有选中的行
        $scope.currentSplitConditionDetailRow.index = -1000;

        //分页初始化参数
        $scope.paging = {
            currentPage: 1, //默认显示第一页
            total: 0,
            pageSize: 5, //默认条数
            index:1
        };

        $scope.pageNumber = 0;

        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);

        var random_No = function (j) {
            var random_no = "";
            for (var i = 0; i < j; i++) {//j位随机数，用以加载时间日期后面
                random_no += Math.round(Math.random() * 10);
            }
            return random_no;
        };

        //选择供应商
        $scope.getSupplierCode = function () {
            $('#supplierCode').modal('toggle')
        };

        //点击供应商弹出框选择
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.project.supplierCode = currentList[0].supplierCode;
            $scope.project.supplierName = currentList[0].supplierName;
            $scope.project.supplierId  =currentList[0].supplierId;
            console.log("头表选择的供应商id: " + currentList[0].supplierId);

            $timeout(function () {
                $scope.proposalSupplierList.search(1);
            },600);

        };

        $scope.batchAppointSupplier = function(){
            $('#batchAppointVendorNbr').modal('toggle')
        };

        //批量指定供应商
        $scope.selectBatchAppointSupplierReturn = function(key,value,currentList){
            if ($scope.currentList2 && $scope.currentList2.length == 0){
                SIEJS.alert("请先选中数据,再执行批量指定操作", "error", "确定");
                return ;
            }
            for (let i = 0; i < $scope.currentList2.length; i++) {
                var currentList2Element = $scope.currentList2[i];
                currentList2Element.splitSupplierCode = currentList[0].supplierCode;
                currentList2Element.splitSupplierName = currentList[0].supplierName;
            }
        };

        //符合条件明细选择供应商弹窗,更新sale和purchase数据
        $scope.splitSelectSupplierCallback = function(key,value,currentList){
            if (!$scope.dataTables.data.selectRowData){
                SIEJS.alert("请选择一行", "error", "确定");
                return ;
            }
            //赋值给指定供应商
            $scope.dataTables.data.selectRowData.splitSupplierCode = currentList[0].supplierCode;
            $scope.dataTables.data.selectRowData.splitSupplierName = currentList[0].supplierName;

        };

        //符合条件明细行事件(点击行)
        $scope.splitConditionDetailRow = function (row, index,event) {
            $scope.currentSplitConditionDetailRow.selectRow = row;//当前选中的行
            //$scope.currentSplitConditionDetailRow.index = index;//当前选中的索引
            //改成分页选中索引的用法
            $scope.currentSplitConditionDetailRow.index = (index + (($scope.paging.index - 1) * $scope.paging.pageSize));//当前选中的索引
            $scope.currentSplitConditionDetailRow.currentDom = event ? event.currentTarget : null;//当前选中的dom元素
        };


/*        //复选框选中
        $scope.rowChecked = function (index, event, item) {
            var item = $scope.splitConditionDetail[index];//选中的某一行
            $scope.currentSplitConditionDetailRow.currentDom = event ? event.currentTarget : null;
            $scope.currentSplitConditionDetailRow.selectRow = item;
            //$scope.currentSplitConditionDetailRow.index = index;
            $scope.currentSplitConditionDetailRow.index = (index + (($scope.paging.index - 1) * $scope.paging.pageSize));

            item.checked = !item.checked;

            //{'key':'lookupCode','value':'meaning',delKey:'id'}
            if (item.checked) {
                var ispush = false;
                for (var n in $scope.currentSplitConditionDetailRow.selectRowList) {
                    var row = $scope.currentSplitConditionDetailRow.selectRowList[n];
                    if (row['mid'] === item['mid']) {
                        ispush = true;
                        break;
                    }
                }
                if (!ispush) {
                    $scope.currentSplitConditionDetailRow.selectRowList.push(item);
                }
            } else { // 删除当前选中行
                var x = -1;
                for (var y in $scope.currentSplitConditionDetailRow.selectRowList) {
                    var list = $scope.currentSplitConditionDetailRow.selectRowList[y];

                    if (list['mid'] === item['mid']) {
                        x = y;
                    }
                }
                $scope.currentSplitConditionDetailRow.selectRowList.splice(x, 1);
            }
        };*/

/*        // 全选按钮
        $scope.checkedAll = function (e) {
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.splitConditionDetail) {
                    var row = $scope.splitConditionDetail[n];
                    row.checked = true;
                    var isPush = false;
                    for (var m in $scope.currentSplitConditionDetailRow.selectRowList) {
                        var list = $scope.currentSplitConditionDetailRow.selectRowList[m];
                        if (row['mid'] === list['mid']) {
                            isPush = true;
                            break;
                        }
                    }
                    if (!isPush) $scope.currentSplitConditionDetailRow.selectRowList.push(row);

                }
            } else {// 反选
                $scope.selectedAll = false;
                for (var n in $scope.splitConditionDetail) {
                    var row = $scope.splitConditionDetail[n];
                    row.checked = false;
                    var index = $scope.currentSplitConditionDetailRow.selectRowList.indexOf(row);
                    $scope.currentSplitConditionDetailRow.selectRowList.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }
        };*/

        // 分页控件查询
        $scope.paging.search = function (index) {
            $scope.selectedAll = false;// 取消全选
            $scope.isExportData = false;
            $scope.findAccorWithConditionDetail(index);

        };

        $scope.setTtaContractLine = function (id){

            if( !$scope.CountH[id]) {
                var w = jQuery(window);
                var _top = jQuery("#" + id).offset().top * 1;
                $scope.CountH[id] = _top;
                var _h = w.innerHeight();
                jQuery("#" + id).css("height", (_h - _top) + "px");
                w.bind('resize', function () {
                    jQuery("#" + id).css("height", (w.innerHeight() - _top) + "px");
                    $timeout(function () {
                        $scope.$apply();
                    }, 100)
                });
            }
            return (w.innerHeight() - _top);
        } ;


        //保存
        $scope.btnSave = function (invalid) {
            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.editTtaSupplierItemHeaderEntityH, {
                    params: JSON.stringify({
                        headerInfo: $scope.project,
                        saveAll: $scope.dataTables.data
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.instance.supplierItemId;
                        $scope.search();
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                })
        };

        //查询头信息
        $scope.search = function () {
            $scope.supplierItemId = id;
            httpServer.post(URLAPI.findTtaSupplierItemHeaderInfoList, {
                    'params': JSON.stringify({supplierItemId: $scope.supplierItemId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.project = res.data[0];
                       var myDate =  new Date(Date.parse($scope.project.creationDate));
                        $scope.project.oldYear = myDate.getFullYear() - 1;
                        if ($scope.modify) {
                            $scope.findAccorWithConditionDetail(1);
                        }

                        $timeout(function () {
                            $scope.proposalSupplierList.search(1);
                        },500);

                        $timeout(function () {
                            $scope.proposalCodeList.search(1);
                        },500);

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        //查询符合条件明细
        $scope.findAccorWithConditionDetail = function (index) {
            $scope.dataParams.supplierItemHId =id;
            $timeout(function () {
                $scope.dataTables.getData();
            },100)
        };

        var timer;
        var exportTimer;
        function getCookie(name) {
            var cookie_start = document.cookie.indexOf(name);
            var cookie_end = document.cookie.indexOf(";", cookie_start);
            return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
        }

        //生成符合条件明细
        $scope.createSplitConditionDetail = function() {
            SIEJS.confirm('提示', '你确定要生成吗？', '确定', function () {
                var _time = {
                    m: 0,
                    s: 0
                };
                timer = setInterval(function () {
                    _time.s++;
                    if (_time.s === 60) {
                        _time.m++;
                        _time.s = 0;
                    }
                    var str = _time.m > 0 ? _time.m + '分' + (_time.s < 10 ? '0' + _time.s + '秒' : _time.s + '秒') : (_time.s < 10 ? '0' + _time.s + '秒' : _time.s + '秒');
                    $('#timerDetail').text(str)
                }, 1000);

                $('#showResultByDetail').html('<p>正在生成明细数据中,请耐心等候!</p>');
                $('#showResultByDetail').append("<div id=\"saafLoading1\" style=\"position: fixed; top: 0; width: 100%; z-index: 9000; height: 100%; \">\n" +
                    "                                <div  style=\"position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;\">\n" +
                    "                                    <img src=\"img/loading1.gif\">\n" +
                    "                                </div>\n" +
                    "                            </div>");
                $("#withdrawalDetailCreateDetailModal").modal('toggle');

                httpServer.post(URLAPI.submitTask, {
                    'params': JSON.stringify({
                        project:$scope.project,
                        type:"2"
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        // 查询状态
                        exportTimer = setInterval(function () {
                            searchStatus(res.data.createKey);
                        }, 1000);
                    } else {
                        clearInterval(timer);
                        $('#showResultByDetail').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>");
                    }
                }, function (err) {
                    clearInterval(timer);
                    $('#showResultByDetail').html('<h3 style="color:#FF0000;">执行失败</h3>');
                });
            });
        };

        function searchStatus(createKey) {
            var params = {
                params:JSON.stringify({
                    createKey:createKey
                })
            };
            $.ajax({
                url: URLAPI.appointVendorNbrStatus,
                type: 'post',
                data: params,
                timeout: 60000 * 5,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "Certificate": getCookie(window.appName + '_certificate') || sessionStorage[window.appName + '_certificate'] || 'nothing',
                    "TokenCode": tokenCode
                },
                dataType: 'html',
                success: function (res) {
                    var res = JSON.parse(res);
                    if (res.status === 'S') {
                        clearInterval(timer);
                        clearInterval(exportTimer);
                        $('#showResultByDetail').html('<h3 style="color:green;">' + res.msg + '</h3>');
                        $scope.findAccorWithConditionDetail(1);
                        $("#withdrawalDetailCreateDetailModal").modal('toggle');
                    } else  if (res.status === 'E' || res.status === 'M') {//失败
                        clearInterval(timer);
                        clearInterval(exportTimer);
                        $('#showResultByDetail').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>")
                    }
                },
                error: function (e) {
                    clearInterval(timer);
                    clearInterval(exportTimer);
                    $('#showResultByDetail').html('<h3 style="color:#FF0000;">执行失败</h3>')
                }
            });
        }

        $scope.submitFunction = function(){
            httpServer.post(URLAPI.submitTask, {
                'params': JSON.stringify({
                    supplierItemHeaderInfo:$scope.project,
                    //list:$scope.currentList2,
                    list:$scope.dataTables.data,
                    type:"1"
                })
            }, function (res) {
                if (res.status == 'S') {
                    // 查询状态
                    exportTimer = setInterval(function () {
                        exportStatus(res.data.createKey);
                    }, 1000);
                    //id = $scope.project.supplierItemId = res.data.supplierItemId;
                    //SIEJS.alert(res.msg, "success", "确定");
                } else {
                    clearInterval(timer);
                    $('#showResult').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>");
                    //SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                clearInterval(timer);
                $('#showResult').html('<h3 style="color:#FF0000;">执行失败</h3>');
                //SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            })
        };

        //提交
        $scope.btnSubmit = function(){
            var supplierItemId = $scope.project.supplierItemId;
            if (supplierItemId == undefined || supplierItemId == null) {
                SIEJS.alert("请先保存单据", 'warning');
                return;
            }
            if ($scope.project.billStatus == 'affirm') {
                SIEJS.alert("您已经确认过此单据,不需要再次提交", 'warning');
                return;
            }
            if ($scope.dataTables.data.length == 0) {
                SIEJS.alert('符合条件拆分明细数据为空,提交失败', "error");
                return ;
            }

            SIEJS.confirm('提示', '你确认提交吗?', '确定', function () {
                var _time = {
                    m: 0,
                    s: 0
                };
                timer = setInterval(function () {
                    _time.s++;
                    if (_time.s === 60) {
                        _time.m++;
                        _time.s = 0;
                    }
                    var str = _time.m > 0 ? _time.m + '分' + (_time.s < 10 ? '0' + _time.s + '秒' : _time.s + '秒') : (_time.s < 10 ? '0' + _time.s + '秒' : _time.s + '秒');
                    $('#timer').text(str)
                }, 1000);

                $('#showResult').html('<p>正在进行指定供应商行动,请耐心等候!</p>');
                $('#showResult').append("<div id=\"saafLoading1\" style=\"position: fixed; top: 0; width: 100%; z-index: 9000; height: 100%; \">\n" +
                    "                                <div  style=\"position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;\">\n" +
                    "                                    <img src=\"img/loading1.gif\">\n" +
                    "                                </div>\n" +
                    "                            </div>");
                $("#withdrawalDetailSumbitModal").modal('toggle');

                //提交之前先保存符合拆分条件明细数据
                $scope.checkSplitData($scope.submitFunction);
            });
        };

        //"{createKey:" + createKey + "}"
        function exportStatus(createKey) {
            var params = {
                params:JSON.stringify({
                    createKey:createKey
                })
            };
            $.ajax({
                url: URLAPI.appointVendorNbrStatus,
                type: 'post',
                data: params,
                timeout: 60000 * 5,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8",
                    "Certificate": getCookie(window.appName + '_certificate') || sessionStorage[window.appName + '_certificate'] || 'nothing',
                    "TokenCode": tokenCode
                },
                dataType: 'html',
                success: function (res) {
                    var res = JSON.parse(res);
                    if (res.status === 'S') {
                        clearInterval(timer);
                        clearInterval(exportTimer);
                        $('#showResult').html('<h3 style="color:green;">' + res.msg + '</h3>');
                        $scope.search();
                        $("#withdrawalDetailSumbitModal").modal('toggle');
                    } else  if (res.status === 'E' || res.status === 'M') {//失败
                        clearInterval(timer);
                        clearInterval(exportTimer);
                        $('#showResult').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>")
                    }
                },
                error: function (e) {
                    clearInterval(timer);
                    clearInterval(exportTimer);
                    $('#showResult').html('<h3 style="color:#FF0000;">执行失败</h3>')
                }
            });
        }

        //作废
        $scope.btnDiscard = function(){
            var supplierItemId = $scope.project.supplierItemId;
            if (supplierItemId == undefined || supplierItemId == null) {
                SIEJS.alert("此条单据没有保存,不能作废", 'warning');
                return;
            }

            if ($scope.project.billStatus != 'affirm'){
                SIEJS.alert("当前单据状态不在确认中,不能作废", 'warning');
                return;
            }
            SIEJS.confirm('提示', '你确定作废吗?', '确定', function () {
                httpServer.post(URLAPI.ttaSupplierItemDiscard, {
                    'params': JSON.stringify({
                        supplierItemId: supplierItemId,
                        status: $scope.project.billStatus
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.project.supplierItemId = res.data.supplierItemId;
                        id = $scope.project.supplierItemId;
                        $scope.search();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            })
        };

        //保存关联供应商数据
        $scope.setProposalSupplierList = function () {
              if ($scope.project.supplierItemId == undefined || $scope.project.supplierItemId == null) {
                  SIEJS.alert("请先保存单据信息", 'warning');
                  return;
              }
            var p = {
                data:{
                    supplierCode:$scope.project.supplierCode,
                    supplierName:$scope.project.supplierName
                },
                supplierItemId: $scope.project.supplierItemId,
                proposalSupplierList: $scope.proposalSupplierList.selectRowList
            };
            httpServer.post(URLAPI.ttaSupplierItemRelSupplierSave, {
                'params': JSON.stringify(p)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.dataTable.search(1);
                    SIEJS.alert(res.msg, 'success', '确定')
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
            });

        };
        //删除poposal
        $scope.btnDelProposalSupplier = function () {
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var item = $scope.dataTable.selectRow;
                httpServer.post(URLAPI.ttaSupplierItemRelSupplierDelete, {
                    'params': JSON.stringify({
                        id: item.supItemRelSId
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.selectRow = null;
                        $scope.dataTable.search(1);
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });

            });
        };

        //选择proposal信息
        $scope.getProposalCode =  function () {
            if (!$scope.project.supplierCode) {
                SIEJS.alert("请先选择供应商信息", "warning");
                return;
            }
            $scope.project.oldYear = $scope.project.startDate.substring(0,4);
            $scope.proposalCodeList.search();
            $('#withdrawalProposal').modal('toggle');
        };

        //点击确认按钮
        $scope.selectLatentWithdrawalReturn = function (key, value, currentList) {
            $scope.project.proposalCode = currentList[0].orderNbr;
            $scope.project.vendorNbr = currentList[0].vendorNbr;
            $scope.project.vendorName = currentList[0].vendorName;
            $scope.project.proposalYear = currentList[0].proposalYear;
            $scope.project.versionCode = currentList[0].versionCode;

        };

        //保存拆分明细数据
         $scope.saveSplitConditionDetail = function (callback) {
             $scope.saveAll =  $scope.dataTables.data ;
             httpServer.post(URLAPI.ttaSupplierItemMidServiceSplitConditionDetailSave, {
                 'params': JSON.stringify({
                     saveAll: $scope.saveAll,
                     headerInfo: $scope.project
                 })
             }, function (res) {
                 if (res.status == 'S') {
                     $scope.dataTables.getData();
                     if (callback){
                         callback();
                     } else {
                         SIEJS.alert(res.msg, "success", "确定");
                     }
                 } else {
                     if (callback) {
                         $("#withdrawalDetailSumbitModal").modal('toggle');
                     }
                     SIEJS.alert(res.msg, "error", "确定");
                 }
             }, function (err) {
                 if (callback) {
                     $("#withdrawalDetailSumbitModal").modal('toggle');
                 } else {
                     SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                 }
             });

         };

         //检查拆分明细数据
        $scope.checkSplitData = function (callback) {
            $scope.saveAll =  $scope.dataTables.data ;
            httpServer.post(URLAPI.ttaSupplierItemMidServiceCheckSplitData, {
                'params': JSON.stringify({
                    saveAll: $scope.saveAll,
                    headerInfo: $scope.project
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(res.data);
                    $scope.saveSplitConditionDetail(callback);
                } else {
                    if (callback) {
                        $("#withdrawalDetailSumbitModal").modal('toggle');
                    }
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                if (callback) {
                    $("#withdrawalDetailSumbitModal").modal('toggle');
                }
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });

        };

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
                if (item.mid === $scope.currentList2[i].mid) {
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
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－
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
                for (var n in $scope.dataTables.data) {
                    var row = $scope.dataTables.data[n];
                    if (true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.mid === list.mid) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.dataTables.data) {
                    var row = $scope.dataTables.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }
        };

        $scope.clearSelect = function (row) {
            row.splitSupplierCode = '';
            row.splitSupplierName = '';
        };

        //指定供应商
        $scope.designatedSupplier = function () {
            if($scope.currentList2.length == 0){
                SIEJS.alert('当前未选中请先选择数据', "error");
                return ;
            }
            SIEJS.confirm('提示', '确定要指定供应商吗？', '确定', function () {
                httpServer.post(URLAPI.ttaSupplierItemMidUpdateSupplier, {
                        'params': JSON.stringify(
                            {
                                supplierItemHeaderInfo:$scope.project,
                                list:$scope.currentList2,//选中的拆分明细数据
                            }),
                        __timeout:300
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.findAccorWithConditionDetail(1);
                            //SIEJS.alert("正在更新purchase数据,请稍后查看", "error", "确定");
                            SIEJS.alert("指定供应商成功", "success", "确定");
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            });
        };

        $scope.selectGroupReturn = function (key, value, currentList) {
            $scope.dataParams.groupCode = currentList[0].groupCode;
            $scope.dataParams.groupName = currentList[0].groupDesc;
        };

        $scope.selectDeptReturn = function (key, value, currentList) {
            $scope.dataParams.deptCode = currentList[0].departmentCode;
            $scope.dataParams.deptName = currentList[0].departmentName;
        };

        $scope.selectBrandReturn = function (key, value, currentList) {
            $scope.dataParams.brandCode = currentList[0].brandCode;
            $scope.dataParams.brandName = currentList[0].brandCn;
        };

        $scope.selectItemLovReturn = function(key, value, currentList) {
            $scope.dataParams.itemCode = currentList[0].itemNbr;
            $scope.dataParams.itemName = currentList[0].itemDescCn;
        };

        //重置表单
        $scope.formRestore = function () {
            $scope.dataParams.groupCode = '';
            $scope.dataParams.groupName = '';
            $scope.dataParams.deptCode = '';
            $scope.dataParams.deptName = '';
            $scope.dataParams.brandCode = '';
            $scope.dataParams.brandName = '';
            $scope.dataParams.itemCode = '';
            $scope.dataParams.itemName = '';
        };

        //导出模板
        $scope.btnExportSplitDetail = function () {
            if ($scope.dataParams.supplierItemHId == undefined || $scope.dataParams.supplierItemHId == null) {
                SIEJS.alert("头信息未保存,请先保存再重试!", "error", "确定");
                return;
            }
            if ($scope.splitDetailDataTables.length == 0) {
                SIEJS.alert("请先生成明细数据,再进行导出操作!", "error", "确定");
                return;
            }

            var name = 'splitDetailDataTables';
            var params = $scope.dataParams;
            var count = $scope.splitDetailDataTables.count;
            tableXlsExport(name, params, count);
        };

        //$scope.setTtaContractLine("nicescroll_tta_withdrawal");

        if (id) {//编辑
            $scope.modify = true;
            $scope.search();
        } else {//新增
            var _date = $filter('date')(new Date(), 'yyyyMM');
            console.log(_date);

            $scope.project.createByUser = userLoginInfo.userFullName;
            $scope.project.billStatus = 'make';//制作中
            $scope.project.isComplete = '0';//未更新

            $scope.project.majorDeptDesc = userLoginInfo.deptName;
            $scope.project.majorDeptId =  userLoginInfo.deptId;
            $scope.project.majorDeptCode = userLoginInfo.deptCode;

        }

    });
});