/**
 * create by hmb
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload', 'XLSX'], function (app, pinyin, ztree, angularFileUpload, XLSX) {
    app.useModule('angularFileUpload'); //按需加载弹出层模块
    app.controller('exclusiveDetailCtrl', function ($filter, httpServer, URLAPI, Base64, $window, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS,
                                                    arrayFindItemIndex, arrayDeleteItem, validateForm, poorYear) {
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo')) || JSON.parse(sessionStorage[appName + '_successLoginInfo']);//用户信息
        $scope.isShowFlag = $scope.userData.userType == '45' ? 1 : 0;//用户类型:45-->BIC
        $scope.tabAction = 'exclusiveDetailBusiness';//默认显示业务表单

        $scope.standardType = $stateParams.type;
        $scope.id = $stateParams.id;
        $scope.soleAgrtInfoFlag = false;
        $scope.soleItemModelShow = false;
        $scope.params = {};
        if ($scope.standardType) {
            if ('nostandard' === $scope.standardType) {
                $scope.project = {
                    agrtType: 'nostandard'
                };
            } else {
                $scope.project = {
                    agrtType: 'standard'
                };
            }
        }

        $scope.exclusiveProtocolInfo = [];
        $scope.itemDetail = [];
        $scope.selectRowListItem = [];
        $scope.currentItemDetailRow = {};
        $scope.proposalSupplierData = [];//当前已选择的poposal供应商数据
        $scope.clause = {};
        $scope.currentSoleAgrtInfo = {};
        $scope.currentSoleAgrtInfo.selectRowData = null;
        $scope.selectRowList = [];
        $scope.currentSelectList = [];
        $scope.selectStartDate = (new Date().getFullYear() - 1) + "-01-01";//独家销售起始时间选择只能大于往年的时间
        $scope.current = {};
        $scope.soleItem = {};
        $scope.exceptionRemarkRequired = false;
        $scope.solAgrtInfoModif = "new";
        $scope.selectSoleDept = "new";
        $scope.selectSoleItemBrand = "new";
        $scope.selectDeptList = [];
        $scope.flag = false;
        $scope.flag2 = false;
        $scope.processImageParams = {};
        $scope.itemParam = {};

        /*监听$scope.selectRowList*/
        $scope.$watch('exclusiveProtocolInfo', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag) {
                if (!$scope.flag) {
                    $scope.flag = true;
                }
                return;
            }
            $timeout(function () {
                $scope.selectRowList = [];
            })
        }, true);

        $scope.$watch('itemDetail', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag2) {
                if (!$scope.flag2) {
                    $scope.flag2 = true;
                }
                return;
            }
            $timeout(function () {
                $scope.selectRowListItem = [];
            })
        }, true);

        $scope.$watch('project.agrtType', function (newVal, oldVal) {
            if (newVal && newVal != oldVal) {
                console.log("进来了.........")


            }
        }, true);

        //分页初始化参数
        $scope.paging = {
            currentPage: 1, //默认显示第一页
            total: 0,
            pageSize: 10, //默认条数
            index: 1
        };

        $scope.pageNumber = 0;

        //保存独家协议信息主信息
        $scope.btnSave = function () {
            $("#saveButton").attr("readonly", true);
            httpServer.post(URLAPI.saveOrUpdateTtaSoleAgrt, {
                'params': JSON.stringify($scope.project)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.id = res.data.soleAgrtHId;
                    $scope.search();
                    $scope.searchSoleSupplier();
                    $("#saveButton").attr("readonly", false);
                    SIEJS.alert(res.msg, "success", "确定");
                } else {
                    $("#saveButton").attr("readonly", false);
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                $("#saveButton").attr("readonly", false);
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        /**********************************工作流配置 start**************************************/
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);

        //先判断是否业务form是否有id 再判断流程form的id
        if ($stateParams.id) {
            $scope.id = $stateParams.id;
        } else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;

        }

        //如果表单的ID与页面的头ID不一致，需要做兼容处理
        //url参数转对象
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }

        //获取单据ID
        function getId() {
            return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
        }

        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };

        function getCookie(name) {
            var cookie_start = document.cookie.indexOf(name);
            var cookie_end = document.cookie.indexOf(";", cookie_start);
            return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
        }

        $scope.btnContractEdit = function () {
            var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
            var tokenCode = successLoginInfo.TokenCode;
            var Certificate = getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || 'nothing';
            var strValue = JSON.stringify({
                "tokenCode": tokenCode,
                "Certificate": Certificate,
                "url": URLAPI.openEditTtaSoleNonStandarHeader,
                "fileId": $scope.currentSoleAgrtInfo.selectRowData.soleAgrtInfoId
            });
            POBrowser.openWindowModeless('office/word/edit.html', 'width=1000px;height=800px;', strValue);
        };
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
            httpServer.post(URLAPI.processGet, {
                    'params': JSON.stringify({
                        processDefinitionKey: $scope.processImageParams.key,
                        businessKey: $scope.id
                    })
                },
                function (res) {
                    if (res.status === 'S') {
                        $scope.processImageParams.instanceId = res.data.processInstanceId;
                    }
                    callback && callback(res);
                });
        };


        //提交审批
        $scope.submitApproval = function () {
            $("#submitApproval").attr("disabled", "true");//提交前先禁用提交按钮
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined || soleAgrtHId == null) {
                SIEJS.alert("请先保存单据", 'warning');
                return;
            }
            if ($scope.project.agrtType == undefined) {
                SIEJS.alert("请检查独家协议类型是否为空", 'warning');
                return;
            }

            $scope.extend = {
                "tasks_assignees": []
            };
            $scope.clause.isSkipApprove = $scope.project.isSkipApprove;//是否需要GM审批
            $scope.clause.deptCode = $scope.project.deptCode;
            $scope.variables = []; //流程变量
            angular.forEach($scope.clause, function (value, key) {
                $scope.variables.push({
                    name: key,
                    type: 'string',
                    value: value
                });
            });

            $scope.properties = {
                "menucode": "HTGL",
                "i": ""
            };
            $scope.paramsBpm = {
                "extend": $scope.extend,
                "variables": $scope.variables,
                "properties": $scope.properties,
                "responsibilityId": "990021",
                "respId": "990021",
                "processDefinitionKey": ($scope.project.agrtType == 'nostandard' ? 'TTA_SOLE_NON_STANDAR_HEADER.-999' : 'TTA_SOLE_STANDAR_HEADER.-999'), //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.project.soleAgrtHId, //单据ID
                "title": ($scope.project.agrtType == 'nostandard' ? "新增独家协议(非标准)" : "独家协议(标准)") + $scope.project.soleAgrtCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.project.soleAgrtCode
            };

            httpServer.post(URLAPI.ttaSoleItemFindSingal, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data && res.data > 0) {
                        $scope.submitApprovalOpertor();
                    } else {
                        $("#submitApproval").removeAttr("disabled");
                        SIEJS.alert("ITEM明细数据不能为空", "error", "确定");
                    }
                    //SIEJS.alert(res.msg, 'success', '确定')
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                $("#submitApproval").removeAttr("disabled");
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        var statusTimer;
        $scope.submitApprovalOpertor = function () {
            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    SIEJS.alert("提交成功", "success", "确定");
                    statusTimer = setInterval(function () {
                        $scope.checkBillsStatus();
                    }, 1000);
                } else {
                    $("#submitApproval").removeAttr("disabled");
                    SIEJS.alert(res.msg, "error", "确定");

                }
            }, function (err) {
                //$scope.btnC('0',false);
                $("#submitApproval").removeAttr("disabled");
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });
        };

        $scope.checkBillsStatus = function () {
            httpServer.post(URLAPI.checkBillsStatus, {
                'params': JSON.stringify({
                    soleAgrtHId: getId(),
                })
            }, function (res) {
                if (res.status == 'S') {
                    if (res.data && res.data.status === 'B') {
                        clearInterval(statusTimer);
                        $scope.search();
                    }
                } else {
                    clearInterval(statusTimer);
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                clearInterval(statusTimer);
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        /**********************************工作流配置 end****************************************/
        //选择供应商
        $scope.getVendorCode = function () {
            $('#supplierCode').modal('toggle')
        };

        //点击供应商弹出框选择
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.project.vendorCode = currentList[0].supplierCode;
            $scope.project.vendorName = currentList[0].supplierName;
            $scope.params.vendorNbr = currentList[0].supplierCode;
        };

        //作废
        $scope.btnDiscard = function () {
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined || soleAgrtHId == null) {
                SIEJS.alert("此条单据没有保存,不能作废", 'warning');
                return;
            }

            if ($scope.project.status != 'A') {
                SIEJS.alert("当前单据状态不在制作中,不能作废", 'warning');
                return;
            }

            httpServer.post(URLAPI.ttaSoleAgrtDiscard, {
                'params': JSON.stringify({
                    soleAgrtHId: soleAgrtHId,
                    status: $scope.project.status
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.project.soleAgrtHId = res.data.soleAgrtHId;
                    $scope.id = res.data.soleAgrtHId;
                    $scope.search();//查询独家协议头表信息
                    SIEJS.alert(res.msg, "success", "确定");
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //变更
        $scope.btnChange = function () {
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined && soleAgrtHId == null) {
                SIEJS.alert("您还未保存此单据,不能进行变更操作", "warning", "确定");
                return;
            }
            //单据状态
            if ($scope.project.status != undefined && $scope.project.status != 'C') {
                SIEJS.alert("您当前单据状态不在审批通过中,不能进行变更操作", "warning", "确定");
                return;
            }

            SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                httpServer.post(URLAPI.ttaSoleAgrtChange, {
                        'params': JSON.stringify($scope.project)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var xFlag = res.result.xFlag;
                            var xMsg = res.result.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }
                            $scope.id = res.data.soleAgrtHId;
                            $scope.search();
                            $scope.searchSoleSupplier();
                            $scope.searchSoleAgrtInfo();
                            SIEJS.alert(res.msg, 'success');
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

        //取消
        $scope.btnCancal = function () {
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined || soleAgrtHId == null) {
                SIEJS.alert("此条单据没有保存,不能点击取消操作", 'warning');
                return;
            }
            if ($scope.project.status != 'C') {
                SIEJS.alert("当前单据状态不在审批通过状态,不能取消", 'warning');
                return;
            }
            httpServer.post(URLAPI.ttaSoleAgrtCancal, {
                'params': JSON.stringify({
                    soleAgrtHId: soleAgrtHId,
                    status: $scope.project.status
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.project.soleAgrtHId = res.data.soleAgrtHId;
                    $scope.id = res.data.soleAgrtHId;
                    $scope.search();
                    $scope.searchSoleSupplier();
                    SIEJS.alert(res.msg, "success", "确定");
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //合同模板打印
        $scope.btnContractView = function () {
            var contractV = $scope.currentSoleAgrtInfo.selectRowData;
            if (contractV === undefined || contractV == null) {
                SIEJS.alert("请先选择一行独家协议信息,再进行打印", 'warning');
                return;
            }

            httpServer.post(URLAPI.ttaSoleAgrtContractFind, {
                'params': JSON.stringify(contractV)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ruleId = res.data.rulId;
                    var url = URLAPI.ttaSoleAgrtContractDownload + '?ruleId=' + $scope.ruleId + "&soleAgrtInfoId=" + contractV.soleAgrtInfoId + "&soleAgrtHId=" + $scope.id + "&type=" + $scope.project.agrtType;
                    window.open(url, [""], [""]);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });

        };
        $scope.getContractVendorProposal = function () {
            if (!$scope.project.soleAgrtHId) {
                SIEJS.alert("请先保存头信息", "warning");
                return;
            }
            if (!$scope.project.vendorCode) {
                SIEJS.alert("请先选择供应商信息", "warning");
                return;
            }
            if (!$scope.project.startDate) {
                SIEJS.alert("请填写独家销售起始时间", "warning");
                return;
            }
            if (!$scope.project.endDate) {
                SIEJS.alert("请填写独家销售结束时间", "warning");
                return;
            }
            $scope.proposalSupplierList.search();
            $('#proposalSupplierLov').modal('toggle')
        };


        $scope.searchSoleSupplier = function () {
            $scope.params.soleAgrtHId = $scope.id;
            $timeout(function () {
                $scope.dataTable.search(1);
            }, 200)
        };

        //删除poposal
        $scope.btnDelProposalSupplier = function () {
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                //var item = $scope.dataTable.selectRow;
                var deleteIds = [];
                var selectRowList = $scope.dataTable.selectRowList;
                for (var row in selectRowList) {
                    deleteIds.push(selectRowList[row].soleSupplierId);
                }
                httpServer.post(URLAPI.ttaProposalSupplierDelete, {
                    'params': JSON.stringify({
                        ids: deleteIds
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        //$scope.dataTable.selectRow = null;
                        $scope.dataTable.selectRowList = [];
                        $scope.searchSoleSupplier();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });

            });
        };

        //保存poposal供应商数据
        $scope.setProposalSupplierList = function (key, value, currentList) {
            //console.log($scope.dataTable);
            if ($scope.project.soleAgrtHId == undefined || $scope.project.soleAgrtHId == null) {
                SIEJS.alert("请先保存头信息", 'warning');
                return;
            }

            //$scope.proposalSupplierList.selectRowList
            httpServer.post(URLAPI.ttaProposalSupplierSave, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.project.soleAgrtHId,
                    selectRow: currentList.length == 0 ? $scope.proposalSupplierList.selectRow : currentList[0]
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.params.soleAgrtHId = res.data.soleAgrtHId;
                    $scope.searchSoleSupplier();
                    SIEJS.alert(res.msg, 'success', '确定')
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
            });

        };

        //搜索
        $scope.search = function () {
            httpServer.post(URLAPI.findTtaSoleAgrtHeader, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.project = res.data;
                    $scope.params = $scope.project;
                    $timeout(function () {
                        if ($scope.project.status == 'B') {
                            //1. 审批中，禁用所用的select,input，textarea，button
                            //$(":input").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
                            //$(":button").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
                            $("#controlDiv select").attr("disabled", "true");
                            $("#controlDiv textarea").attr("disabled", "true");
                            $("#controlDiv input").attr("disabled", "true");

                            if ($scope.isShowFlag == '1') {
                                $("[name='controlName'] input").removeAttr("disabled");
                                $("[name='controlName'] textarea").removeAttr("disabled");
                                $("[name='controlName'] select").removeAttr("disabled");
                                $("#enableOp").removeAttr("disabled");
                            }
                        } else if ($scope.project.status == 'C') {
                            //2.审批通过
                            $(":input").not("[name='fullWindow']").not("[name='printButton']").not("[name='controlName']").attr("disabled", "true");
                            $(":button").not("[name='fullWindow']").not("[name='printButton']").not("[name='controlName']").attr("disabled", "true");
                            $("#contractViewId").removeAttr("disabled");
                        }
                    }, 300);

                    //流程图参数
                    $scope.processImageParams = {
                        token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
                        id: 'processImg',
                        instanceId: $scope.urlParams.processInstanceId,
                        key: ($scope.project.agrtType == 'nostandard' ? 'TTA_SOLE_NON_STANDAR_HEADER.-999' : 'TTA_SOLE_STANDAR_HEADER.-999') //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
                    };

                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //BIC在未审批通过时都可以保存如下信息
        setInterval(function () {
            if ($scope.project.status != 'A') {
                $("button[name='readyBtn']").attr("disabled", true);
            }
            if ($scope.isShowFlag == '1') {//&& $scope.project.status != 'C'
                $("[name='controlName'] input").removeAttr("disabled");
                $("[name='controlName'] textarea").removeAttr("disabled");
                $("[name='controlName'] select").removeAttr("disabled");
                $("#enableOp").removeAttr("disabled");
            }

        }, 200);


        //独家协议信息删除(soleAgrtInfo)
        $scope.deleteSoleAgrtInfo = function () {
            if ($scope.selectRowList.length == 0) {
                SIEJS.alert('请先选择一行数据', "warning", '确定');
                return;
            }

            var ids = [];
            for (var i = 0; i < $scope.selectRowList.length; i++) {
                var row = $scope.selectRowList[i];
                var soleAgrtInfoId1 = row.soleAgrtInfoId;
                ids.push(soleAgrtInfoId1);
            }

            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                httpServer.post(URLAPI.ttaSoleAgrtInfoDelete, {
                    'params': JSON.stringify({
                        ids: ids
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.searchSoleAgrtInfo();
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            });
        };

        //独家协议信息(soleAgrtInfo)
        $scope.btnNewSoleAgrtInfo = function () {
            $scope.soleAgrtInfoFlag = true;
            $scope.current = {};
            $scope.solAgrtInfoModif = "new";
            $scope.current.supplierCode = $scope.project.vendorCode;
            $scope.current.supplierName = $scope.project.vendorName;
            $scope.current.saleStartDate = $scope.project.startDate;
            $scope.current.saleEndDate = $scope.project.endDate;
            $scope.current.sysEndDate = $scope.project.endDate;
            $scope.current.saleRegion = 'A';
            $scope.current.isNewSole = 'Y';
            $scope.current.isAutoDeferral = 'Y';
            $scope.current.isSpecial = 'N';
            $timeout(function () {
                $('#soleAgrtModal').modal('toggle');
            }, 5);
        };

        $scope.btnUdateSoleAgrtInfo = function () {
            $scope.soleAgrtInfoFlag = true;
            $scope.solAgrtInfoModif = "update";
            $scope.current = $scope.currentSoleAgrtInfo.selectRowData;
            $timeout(function () {
                $('#soleAgrtModal').modal('toggle');
            }, 5);
        };


        $scope.selectProductTypeCallback = function (key) {
            debugger;
            if (key && key == '1') {//全品牌产品
                $scope.current.isSpecial = 'N';
            } else if (key && key == '2') {//指定产品
                $scope.current.isSpecial = 'Y'
            }
        };

        $scope.isExceptionCallback = function (key) {
            if (key && key == 'Y') {
                $scope.exceptionRemarkRequired = true;
                $("#exceptionRemarkId").attr("required", "true");
            } else if (key && key == 'N') {
                $scope.exceptionRemarkRequired = false;
                $("#exceptionRemarkId").removeAttr("required");
            }
        };

        $scope.getProposalBrandBtn = function (row) {
            if (row) {
                $scope.selectSoleItemBrand = "update";
            } else {
                $scope.selectSoleItemBrand = "new";
            }
            if ($scope.dataTable.list.length == 0) {
                SIEJS.alert("请先维护Proposal信息", 'warning', '确定');
                return;
            }
            $scope.proposalBrandLov.search();
            $('#buttonBrandLov').modal('toggle');
        };

        $scope.selectedProposalBrand = function (key, value, currentList) {
            if ($scope.selectSoleItemBrand === "new") {
                $scope.current.soleBrandCn = currentList[0].brandCn;
                $scope.current.proposalId = currentList[0].proposalId;
                //$scope.current.brandplnLId = currentList[0].brandplnLId;
                //$scope.current.groupCode = currentList[0].groupCode;
                //$scope.current.groupDesc = currentList[0].groupDesc;
                //$scope.current.deptCode = currentList[0].deptCode;
                //$scope.current.deptDesc = currentList[0].deptDesc;
                //$scope.current.brandCn = currentList[0].brandCn;
                //$scope.current.brandEn = currentList[0].brandEn;
            } else {
                $scope.currentSoleAgrtInfo.selectRowData['soleBrandCn'] = currentList[0].brandCn;
                $scope.currentSoleAgrtInfo.selectRowData['proposalId'] = currentList[0].proposalId;
            }
        };

        //独家协议信息保存
        $scope.btnSaveSoleAgrtInfo = function (flag) {
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined || soleAgrtHId == null) {
                SIEJS.alert("请先保存单据,再进行操作", 'warning');
                return;
            }
            $scope.soleData = [];
            if (flag === '1') {//点击修改按钮
                $scope.soleData.push($scope.current);
            } else if (flag === '2') {//独家协议信息保存按钮
                $scope.soleData = $scope.exclusiveProtocolInfo;
            }

            httpServer.post(URLAPI.ttaSoleAgrtInfoBatchSave, {
                'params': JSON.stringify({
                    soleAgrtHId: soleAgrtHId,
                    exclusiveProtocolInfo: $scope.soleData
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.info(res);
                    //$scope.exclusiveProtocolInfo = res.data.soleAgrtInfoLine;
                    $scope.searchSoleAgrtInfo();
                    if (flag === '1') {
                        $scope.searchSoleItemInfo($scope.current);
                    } else if (flag === '2') {
                        if ($scope.currentSoleAgrtInfo.selectRowData) {
                            $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                        }
                    }
                    if (flag === '1') {
                        $('#soleAgrtModal').modal('toggle');
                    }
                    SIEJS.alert(res.msg, 'success', '确定');
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //独家协议信息列表的行点击事件
        $scope.solAgrtInfoRow = function (item, index) {
            $scope.searchSoleItemInfo(item);
        };

        //独家协议信息保存(soleAgrtInfo弹出框)
        $scope.savesoleAgrtInfoModelData = function (formData) {
            if (!validateForm(formData)) {
                return;
            }
            var soleAgrtHId = $scope.project.soleAgrtHId;
            if (soleAgrtHId == undefined || soleAgrtHId == null) {
                SIEJS.alert("请先保存单据,再进行操作", 'warning');
                return;
            }

            //新增
            if ($scope.solAgrtInfoModif === "new") {
                httpServer.post(URLAPI.ttaSoleAgrtInfoSave, {
                    'params': JSON.stringify({
                            soleAgrtHId: soleAgrtHId,
                            current: $scope.current
                        }
                    )
                }, function (res) {
                    if (res.status == 'S') {
                        //1.查询独家协议信息
                        $scope.searchSoleAgrtInfo();
                        //2.查询ITEM明细数据
                        $scope.searchSoleItemInfo(res.data);
                        $("#soleAgrtModal").modal('hide');
                        SIEJS.alert(res.msg, 'success', '确定')
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("保存失败! " + err.msg, "error", "确定");
                });
            } else {//修改
                $scope.btnSaveSoleAgrtInfo('1');
            }
        };

        //查询SoleAgrtInfo信息
        $scope.searchSoleAgrtInfo = function () {
            httpServer.post(URLAPI.ttaSoleAgrtInfoFind, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.exclusiveProtocolInfo = res.data;
                    if ($scope.exclusiveProtocolInfo.length > 0) {
                        $scope.selectedAll = true;
                        var row = $scope.exclusiveProtocolInfo[0];//取某一行
                        row.checked = true;
                        $scope.currentSoleAgrtInfo.selectRow = $scope.exclusiveProtocolInfo.length - 1;
                        $scope.currentSoleAgrtInfo.selectRowData = row;
                        $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                    }

                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        //*****************************全选和多选*****************************************
        //全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag = false;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.exclusiveProtocolInfo) {
                    var row = $scope.exclusiveProtocolInfo[n];
                    if (true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.selectRowList) {//选中的数据
                            var list = $scope.selectRowList[m];
                            if (row.soleAgrtInfoId === list.soleAgrtInfoId) {//如果存在,就放入选中的集合中
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) {
                            $scope.selectRowList.push(row);
                            $scope.currentSelectList.push(row.soleAgrtInfoId);
                        }
                    }

                }
            } else {// 反选
                $scope.selectedAll = false;
                for (var n in $scope.exclusiveProtocolInfo) {
                    var row = $scope.exclusiveProtocolInfo[n];
                    row.checked = false;
                    var index = $scope.selectRowList.indexOf(row);
                    var supplierIdIdx = $scope.currentSelectList.indexOf(row.soleAgrtInfoId);
                    $scope.selectRowList.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                    $scope.currentSelectList.splice(supplierIdIdx--, 1);
                }
            }

        };

        //多选
        $scope.check = function (item, e, t) {
            $scope.flag = false;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.selectRowList.length; i++) {
                if (item.soleAgrtInfoId === $scope.selectRowList[i].soleAgrtInfoId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {//如果是选中的并且未放入选中的选中集合中的,那么就放入到选中的集合中
                item.checked = true; // 选中标识
                $scope.selectRowList.push(item);
                $scope.currentSelectList.push(item.soleAgrtInfoId);
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.selectRowList.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－
                $scope.currentSelectList.splice(currentIndex, 1);
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };

        //*****************************全选和多选*****************************************

        //选择供应商
        $scope.selectedSupplier = function (data) {
            //console.log(data);
            console.log($scope.supplierlistLov.selectRow);
            var row = $scope.supplierlistLov.selectRow;
            if (row.supplierCode !== $scope.project.vendorCode) {
                SIEJS.alert("当前选择的供应商和独家协议头信息的供应商须一致", "warning", "确定");
                return;
            }
            $scope.current.supplierCode = row.supplierCode;
            $scope.current.supplierName = row.supplierName;
        };

        //选择部门
        $scope.selectDepartmentCallback = function () {
            var deptCodeArray = [];
            var deptDescArr = [];
            if ($scope.departmentLov.selectRowList && $scope.departmentLov.selectRowList.length > 0) {
                for (var index in $scope.departmentLov.selectRowList) {
                    var selectRow = $scope.departmentLov.selectRowList[index];
                    deptCodeArray.push(selectRow.deptCode);
                    deptDescArr.push(selectRow.deptDesc);
                }
            }
            if ($scope.selectSoleDept === "new") {
                $scope.current.orgCode = deptCodeArray.join(",");
                $scope.current.orgName = deptDescArr.join(",");
                $scope.selectDeptList = $scope.current.orgCode;
            } else {
                $scope.currentSoleAgrtInfo.selectRowData['orgCode'] = deptCodeArray.join(",");
                $scope.currentSoleAgrtInfo.selectRowData['orgName'] = deptDescArr.join(",");
                $scope.selectDeptList = $scope.currentSoleAgrtInfo.selectRowData['orgCode'];
            }
        };

        //*****************************soleItem 全选和多选*****************************************
        //全选按钮
        $scope.itemCheckedAll = function (e) {
            $scope.flag2 = false;
            if (e.target.checked) {// 全选
                $scope.itemSelectedAll = true;
                for (var n in $scope.itemDetail) {
                    var row = $scope.itemDetail[n];
                    row.checked = true;
                    var isPush = false;
                    for (var m in $scope.selectRowListItem) {//选中的数据
                        var list = $scope.selectRowListItem[m];
                        if (row.soleItemId === list.soleItemId) {//如果存在,就放入选中的集合中
                            isPush = true;
                            break;
                        }
                    }
                    if (!isPush) {
                        $scope.selectRowListItem.push(row);
                    }
                }
            } else {// 反选
                $scope.itemSelectedAll = false;
                for (var n in $scope.itemDetail) {
                    var row = $scope.itemDetail[n];
                    row.checked = false;
                    var index = $scope.selectRowListItem.indexOf(row);
                    $scope.selectRowListItem.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };

        //多选
        $scope.itemCheck = function (item, e, t) {
            $scope.flag2 = false;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.selectRowListItem.length; i++) {
                if (item.soleItemId === $scope.selectRowListItem[i].soleItemId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {//如果是选中的并且未放入选中的选中集合中的,那么就放入到选中的集合中
                item.checked = true; // 选中标识
                $scope.selectRowListItem.push(item);
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.selectRowListItem.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };

        //*****************************全选和多选*****************************************

        //item新增
        $scope.itemNew = function () {
            //$scope.itemModal.search();
            $scope.soleItemModelShow = true;
            $scope.soleItem.vendorNbr = $scope.project.vendorCode;
            $scope.soleItem.vendorName = $scope.project.vendorName;
            $scope.soleItem.groupCode = $scope.project.deptCode;
            $scope.soleItem.groupName = $scope.project.deptName;
            $scope.soleItem.deptCode = "";
            $scope.soleItem.deptDesc = "";
            $scope.soleItem.brandCn = "";
            $scope.soleItem.brandEn = "";
            $scope.soleItem.barCode = "";
            $scope.soleItem.itemCode = "";
            $scope.soleItem.itemName = "";

            $timeout(function () {
                $('#soleItemMode').modal('toggle');
            }, 5);

        };

        $scope.soleItemNewDept = function () {
            $scope.departmentLov1.search();
            $("#buttonDeptLov1").modal('toggle');
        };

        //item弹窗中选择item信息
        $scope.selectSoleItemCallback = function (data) {
            var row = $scope.soleItemLov.selectRow;
            $scope.soleItem.vendorNbr = row.vendorNbr;
            $scope.soleItem.vendorName = row.vendorName;
            $scope.soleItem.groupCode = row.groupCode;
            $scope.soleItem.groupName = row.groupDesc;
            $scope.soleItem.deptCode = row.deptCode;
            $scope.soleItem.deptDesc = row.deptDesc;
            $scope.soleItem.brandCn = row.brandCn;
            $scope.soleItem.brandEn = row.brandEn;
            $scope.soleItem.itemCode = row.itemNbr;
            $scope.soleItem.itemName = row.itemDescCn;
            $scope.soleItem.barCode = row.upc;
        };

        $scope.soleItemNewBrand = function () {
            $("#brandLov").modal('toggle');
        };

        $scope.selectBrandReturn = function (key, value, currentList) {
            $scope.soleItem.brandCn = currentList[0].brandCn;
            $scope.soleItem.brandEn = currentList[0].brandEn;
        };

        $scope.saveSoleItemModelData = function (soleItemForm) {
            if (!validateForm(soleItemForm)) {
                return;
            }
            var soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData['soleAgrtInfoId'];
            if (soleAgrtInfoId == undefined || soleAgrtInfoId == null) {
                SIEJS.alert('请先选择一行独家协议信息数据', warning, '确定');
                return;
            }

            httpServer.post(URLAPI.ttaSoleItemNewSave, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id,
                    soleAgrtInfoId: soleAgrtInfoId,
                    soleItem: $scope.soleItem
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                    $('#soleItemMode').modal('toggle');
                    SIEJS.alert(res.msg, 'success', '确定')
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });


        };

        $scope.returnFlash = function () {
            $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
        };
        //item明细删除
        $scope.itemDelete = function () {
            var selectRowListItem = $scope.selectRowListItem;
            if (selectRowListItem.length == 0) {
                SIEJS.alert('请先选择一行数据', "warning", '确定');
                return;
            }
            var ids = [];
            for (var i = 0; i < $scope.selectRowListItem.length; i++) {
                var soleItemId = $scope.selectRowListItem[i].soleItemId;
                ids.push(soleItemId);
            }

            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                httpServer.post(URLAPI.ttaSoleItemDelete, {
                    'params': JSON.stringify({
                        ids: ids
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        //$scope.itemDetail.splice(index, 1);
                        $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            });
        };

        $scope.saveSoleItemCallBack = function (key, value, currentList) {
            var soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData['soleAgrtInfoId'];
            if (soleAgrtInfoId == undefined || soleAgrtInfoId == null) {
                SIEJS.alert('请先选择一行独家协议信息数据', warning, '确定');
                return;
            }
            var selectRowList = $scope.itemSlelectModel.selectRowList;
            if (selectRowList.length === 0) {
                SIEJS.alert("请先选择一行产品信息", "warning", "确定");
                return;
            }

            httpServer.post(URLAPI.ttaSoleItemSave, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id,
                    soleAgrtInfoId: soleAgrtInfoId,
                    selectRowList: selectRowList
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                    //$("#soleItemId").modal('hide');
                    SIEJS.alert(res.msg, 'success', '确定')
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.searchSoleItemInfo = function (item, index) {
            var p = {
                'params': JSON.stringify({
                    soleAgrtInfoId: item.soleAgrtInfoId
                }),
                pageIndex: index || $scope.paging.currentPage,
                pageRows: 10//默认容量
            };
            httpServer.post(URLAPI.ttaSoleItemFind, p, function (res) {
                if (res.status === 'S') {
                    $scope.itemDetail = [];
                    $scope.itemDetail = res.data;
                    //$scope.selectRowListItem = [];
                    //$scope.paging.currentPage = $scope.paging.index = p.pageIndex
                    $scope.paging.total = res.count;
                    $scope.paging.index = p.pageIndex;
                    $scope.pageNumber = angular.copy($scope.paging.index);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                $scope.paging.total = 0;// 清除分页
                SIEJS.alert("查询失败! " + err.msg, "error", "确定");
            });
        };

        //ITEM明细******批量更新**********(soleItem)
        $scope.itemSaveItemDetail = function () {
            var soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData['soleAgrtInfoId'];
            httpServer.post(URLAPI.ttaSoleItemBatchSave, {
                'params': JSON.stringify({
                    soleAgrtInfoId: soleAgrtInfoId,
                    soleItemList: $scope.itemDetail
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                    SIEJS.alert(res.msg, 'success', '确定');
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert("保存失败! " + err.msg, "error", "确定");
            });
        };

        //excel模板下载
        $scope.excelModelDownLoad = function () {
            window.location.href = "app/base/templates/tta/exampleFlie/exclusiveItem.xlsx";
        };

        $scope.isSkipApproveFun = function () {
            httpServer.post(URLAPI.updateExclusiveSkipStatus, {
                'params': JSON.stringify({
                    soleAgrtHId: getId(),
                    isSkipApprove: $scope.project.isSkipApprove
                })
            }, function (res) {
                if (res.status == 'S') {
                    console.log(JSON.stringify(res.data));
                    $scope.project.isSkipApprove = res.data.isSkipApprove;
                    SIEJS.alert('操作成功', "success", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.changeDate = function () {
            if (($scope.project.startDate == undefined || $scope.project.startDate == '') ||
                ($scope.project.endDate == undefined || $scope.project.endDate == '')) {
                return;
            }
            var year = poorYear($scope.project.startDate, $scope.project.endDate, 1);
            var year2 = poorYear($scope.project.startDate, $scope.project.endDate, 0);
            if (year < 1 || year2 >= 3) {
                $scope.project.isSkipApprove = 'Y';
            } else {
                $scope.project.isSkipApprove = 'N';
            }
            if ($scope.currentSoleAgrtInfo.selectRowData) {
                $scope.currentSoleAgrtInfo.selectRowData['saleStartDate'] = $scope.project.startDate;
                $scope.currentSoleAgrtInfo.selectRowData['saleEndDate'] = $scope.project.endDate;
            }
        };

        $scope.soleNewChangeDate = function(type){
           $scope.startDate = "";
           $scope.endDate = "";
            if (type === 'new') {
                $scope.startDate = $scope.current.saleStartDate;
                $scope.endDate = $scope.current.saleEndDate;
                $scope.current.sysEndDate = $scope.current.saleEndDate;//系统实际结束时间等于独家销售结束时间
            } else {
                $scope.startDate = $scope.currentSoleAgrtInfo.selectRowData['saleStartDate'];
                $scope.endDate = $scope.currentSoleAgrtInfo.selectRowData['saleEndDate'];
                $scope.currentSoleAgrtInfo.selectRowData['sysEndDate'] = $scope.currentSoleAgrtInfo.selectRowData['saleEndDate'];//系统实际结束时间等于独家销售结束时间
            }

            if (($scope.startDate == undefined || $scope.startDate == '') ||
                ($scope.endDate == undefined || $scope.endDate == '')) {
                return;
            }
            var year = poorYear($scope.startDate, $scope.endDate, 1);
            var year2 = poorYear($scope.startDate, $scope.endDate, 0);
            if (year < 1 || year2 >= 3) {
                $scope.project.isSkipApprove = 'Y';
            } else {
                $scope.project.isSkipApprove = 'N';
            }
            $scope.project.startDate = $scope.startDate;
            $scope.project.endDate = $scope.endDate;
        };

        $scope.getSoleInfoModal = function (flag) {
            $scope.selectSoleDept = flag;
            $scope.departmentLov.search();
            $("#buttonDeptLov").modal('toggle');
        };

        $scope.selectDepartmentRowBack = function (key, value, currentList) {
            $scope.soleItem.deptCode = currentList[0].deptCode;
            $scope.soleItem.deptDesc = currentList[0].deptDesc;
        };

        $scope.itemSelect = function () {
            if ($scope.currentSoleAgrtInfo.selectRowData["soleAgrtInfoId"] === undefined
                || $scope.currentSoleAgrtInfo.selectRowData["soleAgrtInfoId"] == null) {
                SIEJS.alert('请先选择一行独家协议信息', "warning", '确定');
                return;
            }
            $scope.itemParam.soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData["soleAgrtInfoId"];
            $("#soleItemSelectId > th > input[type=checkbox]").prop("checked", false);
            $scope.itemDataTable.selectRowList = []; //移除自动选中
            $scope.itemDataTable.search();
            $('#soleItemSelectId').modal('toggle');
        };

        $scope.saveSoleItem = function () {
            var soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData['soleAgrtInfoId'];
            if (soleAgrtInfoId == undefined || soleAgrtInfoId == null) {
                SIEJS.alert('请先选择一行独家协议信息数据', warning, '确定');
                return;
            }
            var selectRowList = $scope.itemDataTable.selectRowList;
            if (selectRowList == null || selectRowList.length === 0) {
                SIEJS.alert("没有选中,请选择", "warning", "确定");
                return;
            }

            httpServer.post(URLAPI.ttaSoleItemSave, {
                'params': JSON.stringify({
                    soleAgrtHId: $scope.id,
                    soleAgrtInfoId: soleAgrtInfoId,
                    selectRowList: selectRowList
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                    SIEJS.alert(res.msg, 'success', '确定');
                    $('#soleItemSelectId').modal('toggle');
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert(res.msg, 'success', '确定');
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.addItemData = function () {
            var soleAgrtInfoId = $scope.currentSoleAgrtInfo.selectRowData['soleAgrtInfoId'];
            if (soleAgrtInfoId == undefined || soleAgrtInfoId == null) {
                SIEJS.alert('请先选择一行独家协议信息数据', warning, '确定');
                return;
            }
            SIEJS.confirm('提示', '确定要添加所有的ITEM信息吗？', '确定', function () {
                httpServer.post(URLAPI.ttaAddAllItemData, {
                    'params': JSON.stringify({
                        soleAgrtHId: $scope.id,
                        soleAgrtInfoId: soleAgrtInfoId
                    })
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.searchSoleItemInfo($scope.currentSoleAgrtInfo.selectRowData);
                        $scope.itemDataTable.search();
                        $('#soleItemSelectId').modal('toggle');
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            });
        };

        if ($scope.id) {//修改
            $scope.search();
            $scope.searchSoleSupplier();
            $scope.searchSoleAgrtInfo();
        } else {
            $scope.project.status = 'A';
        }

        setInterval(function () {
            $("#nicescroll_t_itemSelectModalId").removeAttr("ng-nicescroll");
            $("#nicescroll_t_itemSelectModalId").css({
                "overflow":"auto"
            })
        },1000);

    });
});