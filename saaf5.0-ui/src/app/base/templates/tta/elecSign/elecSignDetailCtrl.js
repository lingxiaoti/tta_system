/**
 * Created by hmx on 2018/9/6.
 */
'use strict';
define(['app','ztree','GooFlow'],function(app) {
    app.controller('elecSignDetailCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','$timeout','validateForm','$filter','$location','$rootScope','$state','iframeTabAction',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout,validateForm,$filter, $location, $rootScope, $state,iframeTabAction) {
            // tab 切换
            $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
            $scope.isShowFlag = $scope.userData.userType == '45' ? 1 : 0;
            $scope.fileDataParams = {};
            $scope.fileDataParams.functionId = 'tta_supplier_attr';
            $scope.selectVendor = null;
            $scope.tab = {
                active: 0,
                nav: ['合同附件信息', '主合同附件', '电子签章返回信息'],
                click: function (i) {
                    $scope.tab.active = i
                }
            };
            $scope.isSpecialFlag = 'N';

            $scope.tabAction = 'clauseManageDBusiness'; //根据页面配置
            //获取url参数对象
            $scope.urlParams = urlToObj(location.hash);
            //先判断是否业务form是否有id 再判断流程form的id
            if ($stateParams.itemId) {
                $scope.id = $stateParams.itemId;
            } else if ($scope.urlParams.businessKey) {
                $scope.id = $scope.urlParams.businessKey;
            }
            $scope.elecConHeaderId = getId();
            $scope.treeParams = {elecConHeaderId:$scope.elecConHeaderId};
            $scope.processStatus = "DRAFT";// status 流程状态  DRAFT.草稿   APPROVAL.审批中  ALLOW.审批通过  REFUSAL.审批驳回 CLOSED.已关闭
            $scope.params = {};
            $scope.headerData = {};//电子签章主信息
            $scope.headerData.contractNoRequire = "false";
            $scope.contractDataTable = {}; //电子盖章合同附件信息
            $scope.eleCcontractResutDataTable = {}; //电子签章返回信息
            $scope.propcessStatus = 'A';
            $scope.showParams = {};
            $scope.current = {};
            $scope.current.range = {};
            $scope.contractNoRequire = "false";//合同书编号非必填


            //如果需要特批
            setInterval(function () {
                if ($scope.contractDataTable.list && $scope.headerData && $scope.headerData.isSpecial == 'Y') {
                    var list = $scope.contractDataTable.list;
                     for (var i = 0; i < i < list.length; i ++) {
                        if (list[i].fileName.indexOf('合同书') >0 && $scope.headerData.isSpecial == 'Y'){
                            $scope.isSpecialFlag = 'Y';
                            break;
                        }
                    }
                }
            }, 500);
            
            /**
             * 获取头表以及树的信息
             */
            $scope.getElecHeader = function(id) {
                if("-1" == id){
                    return ;
                }
                httpServer.post(URLAPI.findElecConHeaderById, {
                    params: JSON.stringify({
                        elecConHeaderId: id
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.headerData = res.data.head;
                        $scope.contractNoRequire = $scope.headerData.contractNoRequire;
                        $scope.headerData.checkList = res.data.checkList;
                        $scope.params.conYear = $scope.headerData.contractYear;
                        $scope.params.vendorCode = $scope.headerData.vendorCode;
                        $scope.propcessStatus = $scope.headerData.status;
                        if ($scope.headerData.changeType) {
                            $timeout(function () {
                                $scope.rangeTree.search (null, function () {
                                    $scope.rangeTree.setSelect($scope.headerData.changeType);
                                }) ;
                            }, 100)
                        }

                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                })
            };


            $scope.queryCheckList = function () {
                if (!$scope.elecConHeaderId || $scope.elecConHeaderId == '-1') {
                    SIEJS.alert('请先保存单据主体信息！','error','确定');
                    return;
                }
                httpServer.post(URLAPI.findAttCheckList, {
                    params: JSON.stringify({
                        elecConHeaderId: getId()
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.headerData.checkList = res.data;
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                });
            };
            if ($scope.elecConHeaderId && $scope.elecConHeaderId != '-1') {
                $scope.queryCheckList();
            }


            //初始化跳表头信息
            if ($scope.elecConHeaderId && $scope.elecConHeaderId != '-1') {
                $scope.getElecHeader($scope.elecConHeaderId);
                $scope.params.elecConHeaderId = $scope.elecConHeaderId;
            } else {
                $scope.propcessStatus = $scope.headerData.status = 'A';
                $scope.headerData.versionNum = 1;
            }
            //保存电子签章头信息
            $scope.btnSave = function () {
                httpServer.post(URLAPI.saveElecConHeader, {
                    'params': JSON.stringify($scope.headerData)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.id = $stateParams.itemId = $scope.params.elecConHeaderId = $scope.elecConHeaderId = res.data.head.elecConHeaderId;
                        $scope.getElecHeader($scope.elecConHeaderId);//查询头信息
                        $scope.contractDataTable.search(1);//查询合同附件第1部分
                        if (res.data.head.isElecFlag == 'Y') {
                            $scope.eleCcontractResutDataTable.search(1);//查询电子签章返回第3部分
                        }
                        //$scope.queryCheckList(1);//查询附件选项信息
                        $scope.headerData.checkList = res.data.checkList;
                        SIEJS.alert(res.msg, "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            };

            $scope.isThirdStamp = function() {
                var falg = true;
                if ($scope.headerData.isThird == 'N'){
                    SIEJS.confirm('取消丙方供应商', '如果确认将清除丙方供应商相关信息？', '确定', function () {
                        falg = false;
                        $scope.headerData.isThird = 'N';
                        $scope.headerData.thirdContractYear = '';//合同年份
                        $scope.headerData.thirdVendorName = '';
                        $scope.headerData.thirdVendorCode = '';
                        $scope.headerData.thirdVenderLinkMan = '';//供应商联系人
                        $scope.headerData.thirdVenderPhone = '';//供应商手机号
                        $scope.headerData.isThirdCollect = '';//资料是否收集完整
                        $scope.headerData.thirdSupplierId = '';
                    });
                }
                if (falg) {
                    $scope.headerData.isThird = 'Y';
                }
            };


            //选择供应商信息
            $scope.getProposalCode = function (flag) {
                if($scope.contractDataTable && $scope.contractDataTable.count > 0 ) {
                    SIEJS.alert('请确保无合同附件信息的条件下切换供应商信息！', 'error', '确定');
                    return;
                }
                $scope.selectVendor = flag;
                $('#latentcontractCode').modal('toggle')
            };

            //点击确认按钮供应商信息
            $scope.selectLatentcontractReturn = function (key, value, currentList) {
                if ($scope.selectVendor == 'third') {
                    $scope.headerData.thirdContractYear =  currentList[0].proposalYear;//合同年份
                    $scope.headerData.thirdVendorName = currentList[0].vendorName;
                    $scope.headerData.thirdVendorCode = currentList[0].vendorCode;
                    $scope.headerData.thirdVenderLinkMan = currentList[0].venderLinkMan;//供应商联系人
                    $scope.headerData.thirdVenderPhone = currentList[0].venderPhone;//供应商手机号
                    $scope.headerData.isThirdCollect = currentList[0].isCollect;//资料是否收集完整
                    $scope.headerData.thirdSupplierId = $scope.fileDataParams.businessId = currentList[0].supplierId;
                    $scope.headerData.contractThirdHId = currentList[0].contractHId;//拆分供应商合同头id
                    $scope.headerData.thirdProposalId =  currentList[0].proposalId;
                } else {
                    //$scope.headerData.contractYear =  currentList[0].proposalYear;//合同年份
                    $scope.headerData.vendorName = currentList[0].vendorName;
                    $scope.headerData.vendorCode = currentList[0].vendorCode;
                    $scope.headerData.venderLinkMan = currentList[0].venderLinkMan;//供应商联系人
                    $scope.headerData.venderPhone = currentList[0].venderPhone;//供应商手机号
                    $scope.headerData.isCollect = currentList[0].isCollect;//资料是否收集完整
                    $scope.headerData.supplierId = $scope.fileDataParams.businessId = currentList[0].supplierId;
                    $scope.headerData.contractHId = currentList[0].contractHId;
                    $scope.headerData.proposalId = currentList[0].proposalId;
                    $scope.headerData.isSpecial = currentList[0].isSpecial ;//是否特批
                }
            };

            $scope.fileInfoFunc = function (supplierId) {
                $scope.fileDataParams.businessId = supplierId; //$scope.headerData.supplierId;
                $scope.fileDataTable.search(1);
                $('#excelUp2').modal('toggle');
            };

            $scope.changeTime = function(){
                debugger;
                var date = $scope.headerData.effectDate;//合同生效日期
                var reg = /[-\/ ]/;
                date = date.replace(/-/mig,'/');
                var _date = reg.test(date) ? new Date(date).getTime() : date;
                $scope.headerData.contractYear = $filter('date')(_date, 'yyyy');//格式化赋值给合同年度
            };

            //选择特批单号
            $scope.getSpecial = function () {
                $('#sepecialLov').modal('toggle')
            };

            //点击确认特批单号
            $scope.getSpecialConfirm = function (key, value, currentList) {
                $scope.headerData.specialNbr =  currentList[0].orderNo;//特批单号
                $scope.headerData.contractSpecialHeaderId = currentList[0].ttaContractSpecialHeaderId;//特批单id
            };

            //查看特批单
            $scope.showSpecail = function () {
                iframeTabAction('合同审核特批申请-详情：' + $scope.headerData.specialNbr , 'ttaContractSpecialHeaderD/' + $scope.headerData.contractSpecialHeaderId);
            };

            //合同编号：逻辑调整为，根据年度+供应商编号从合同领用功能匹配获取LOGNO，并且不存在当前已选择的单号。 【合同书编号 电子签章头上加一个选择框数据来源于领用表】
            $scope.getContractConfirm = function (key, value, currentList) {
                    $scope.headerData.contractNo =  currentList[0].logNo;//领用单号
            };
            //获取合同编号，源于合同领用表
            $scope.getContractNo = function() {
                $scope.cityLov.search();
                $('#findReceive').modal('toggle');
            };

            //清除特批单
            $scope.cancelSpecial = function() {
                $scope.headerData.specialNbr = '';
                $scope.headerData.contractSpecialHeaderId = '';
            };
            
            //清除合同书编号
            $scope.cancelContract = function() {
                $scope.headerData.contractNo = '';
            };

            $scope.btnNew = function () {
                $("th>input[type=checkbox]").prop("checked", false);
                $scope.dataTable.selectRowList = []; //移除自动选中
                $scope.dataTable.search();
                $('#nameLov').modal('toggle');
            };

            $scope.findAndSaveContractNo = function(){
                httpServer.post(URLAPI.findAndSaveContractNo, {
                    params: JSON.stringify({
                        elecConHeaderId: $scope.id,
                        vendorNbr:$scope.headerData.vendorCode,
                        year:$scope.headerData.contractYear
                    })
                },function(res) {
                    if(res.status === 'S') {
                        $scope.headerData.contractNo = res.data.contractNo;
                    }else {
                        SIEJS.alert(res.msg,'error','确定');
                    }
                },function(err) {
                    SIEJS.alert('获取数据失败','error','确定');
                });
            };

            //保存参数信息
            $scope.saveBatch = function () {
                var list = $scope.dataTable.selectRowList;
                if (list == null || list.length == 0) {
                    SIEJS.alert("没有选中,请选择！", 'error');
                    return;
                }
                if (!$scope.headerData.vendorCode) {
                    SIEJS.alert("请先选择头信息的乙方供应商信息!", 'error');
                    return;
                }
                if (!$scope.headerData.contractYear) {
                    SIEJS.alert("请先选择合同生效日期!", 'error');
                    return;
                }
                var arr = [];
                for (var idx in list) {
                    var json = list[idx];
                    json.elecConHeaderId = $scope.elecConHeaderId;
                    json.proposalConAttrLineId = json.proposalConAttrLineId;
                    arr.push(json);
                }
                console.log("arr:" + JSON.stringify(arr));
                httpServer.post(URLAPI.saveContractAttrList, {
                    params: JSON.stringify({attArr: JSON.stringify(arr),orderNo: $scope.headerData.orderNo})
                }, function (res) {
                    $('#nameLov').modal('toggle');
                    if (res.status == 'S') {
                        //只要有一项是贸易合同或者业务合同书,合同书编号都是必填的
                        for (var i = 0; i < list.length; i++) {
                            var row = list[i];
                            //业务合同书,贸易合同,合同编号必填,独家协议,补充协议非必填
                            if (row.fileType && (row.fileType === "1" || row.fileType === "10" || row.fileType === "20")){
                                $scope.contractNoRequire = "true";
                                $scope.headerData.contractNoRequire = $scope.contractNoRequire;
                                //合同编号为空时才去查找
                                if (!$scope.headerData.contractNo) {
                                    $scope.findAndSaveContractNo();
                                }
                                break;
                            }
                        }
                        SIEJS.alert(res.msg, 'success');
                    } else {
                        SIEJS.alert(res.msg, 'error');
                    }
                    $scope.headerData.elecFileId = null;
                    $scope.contractDataTable.search(1);//查询合同附件第1部分
                }, function (error) {
                    $('#nameLov').modal('toggle');
                    SIEJS.alert(res.msg, 'error');
                })
            };

            $scope.btnDel = function () {
                var selectRow = $scope.contractDataTable.selectRow;
                var idsArr = [];
                idsArr.push(selectRow.elecConAttrLineId);
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    httpServer.post(URLAPI.deleteContractDetail, {
                        'params': JSON.stringify({
                            ids: idsArr,
                            elecConHeaderId: $scope.elecConHeaderId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.headerData.elecFileId = null;
                            $scope.contractDataTable.search(1);//查询合同附件第1部分
                            debugger;
                            $timeout(function () {
                                var flag = false;
                                for (let i = 0; i < $scope.contractDataTable.list.length; i++) {
                                    var row = $scope.contractDataTable.list[i];
                                    if (row.elecConAttrLineId == selectRow.elecConAttrLineId) continue;
                                    if (row.fileType === '1' || row.fileType === '10' || row.fileType === "20"){
                                        flag = true;
                                        break;
                                    }
                                }
                                //没找到业务合同书,贸易合同,合同书编号设置为非必填
                                if (!flag) {
                                    $scope.contractNoRequire = "false";
                                    $scope.headerData.contractNoRequire = $scope.contractNoRequire;
                                    $scope.headerData.contractNo = "";
                                } else {
                                    $scope.contractNoRequire = "true";
                                    $scope.headerData.contractNoRequire = $scope.contractNoRequire;
                                    //$scope.headerData.contractNo = "";
                                }
                            },700);
                            SIEJS.alert(res.msg, "success", "确定");
                        } else
                            SIEJS.alert(res.msg, "error", "确定");
                    }, function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                })
            };

            $scope.downFileEvent = function (m) {
                if (m.fileId == undefined || m.fileId == null){
                    SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                    return;
                }
                var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                // $scope.attachmentDownTable.search(1);
            };

            $scope.downFileElecCon = function () {
                if ($scope.headerData.elecFileId == undefined || $scope.headerData.elecFileId == null){
                    SIEJS.alert("请先生成电子合同,不能下载",'warning','确定');
                    return;
                }
                var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + $scope.headerData.elecFileId;
                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
            };


            $scope.downElecFileEvent = function (m) {
                if (m.fileId == undefined || m.fileId == null){
                    SIEJS.alert("当前没有选中或者附件没有生成,不能下载",'warning','确定');
                    return;
                }
                var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                // $scope.attachmentDownTable.search(1);
            };


            //变更
            $scope.btnChange = function (proposal) {
                if($scope.headerData.status != 'C'){
                    SIEJS.alert("单据状态需要是审核通过才能变更");
                    return;
                }
                SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                    httpServer.post(URLAPI.changeElecAll, {
                            'params': JSON.stringify($scope.headerData)
                        },
                        function (res) {
                            if (res.status == 'S') {
                                //$scope.rowData = res.data;
                                //setNewRow($scope.contractDataTable.selectRow, $scope.rowData);// 更新成功，只更新当前行，不必再去重载当前表格
                                if (res.data && res.data.elecConHeaderId) {
                                    iframeTabAction('电子签章详情：' + res.data.orderNo + "_" + res.data.orderVersion,'/elecSignDetail/' + res.data.elecConHeaderId); //172
                                }
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



            // 选择范围
            $scope.setRangeVal = function (nodes, keys, values) {
                var codes = [];
                var names = [];
                for (var i = 0; i < nodes.length; i++) {
                    codes.push(nodes[i].code);
                    names.push(nodes[i].name)
                }
                var arrCode = codes.join(',');
                $scope.headerData.changeTypeName = names.join(',');
                $scope.headerData.changeType = codes.join(',');
            };


            /**********************************工作流配置**************************************/
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

            //流程图参数
            $scope.processImageParams = {
                token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
                id: 'processImg',
                instanceId: $scope.urlParams.processInstanceId,
                key: 'TTA_ELEC_CONTRACT.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
            };

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

            //提交
            $scope.submitApproval = function (form) {
                if(!$scope.contractDataTable || $scope.contractDataTable.count ==0 ) {
                    SIEJS.alert('合同附件不能为空', 'error', '确定');
                    return;
                }
                if (!$scope.headerData.elecFileId){
                    SIEJS.alert('电子合同附件没有生成，建议先手动生成并检查电子文件再提交审批！', 'error', '确定');
                    return;
                }
                if($scope.headerData.status != 'A'){
                    SIEJS.alert('状态不是制单中，不允许提交审批', 'error', '确定');
                    return;
                }
                if(!validateForm(form)){
                    return;
                }

                if ($scope.headerData.isElecFlag == 'Y' ) {
                     if ($scope.headerData.isCollect == 'N') {
                         SIEJS.alert('乙方资料不完整，不能提交审批！', 'error', '确定');
                         return;
                     }

                    if ($scope.headerData.isThird == 'Y' && $scope.headerData.isThirdCollect == 'N') {
                        SIEJS.alert('丙方资料不完整，不能提交审批！', 'error', '确定');
                        return;
                    }
                }


                $scope.extend = {
                    "tasks_assignees": []
                };
                $scope.variables = []; //流程变量
                angular.forEach($scope.headerData, function (value, key) {
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
                    "processDefinitionKey": "TTA_ELEC_CONTRACT.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                    "saveonly": false,
                    "businessKey": $scope.headerData.elecConHeaderId, //单据ID
                    "title": "TTA电子合同签章审批" + $scope.headerData.orderNo + "_" + $scope.headerData.orderVersion, //流程标题，修改为当前业务信息
                    "positionId": 0,
                    "orgId": 0,
                    "userType": "20",
                    "billNo": $scope.headerData.orderNo
                };
                httpServer.post(URLAPI.processStart, {
                    'params': JSON.stringify($scope.paramsBpm)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.propcessStatus = 'B';
                        $timeout(function () {
                            $scope.btnC('DS02');
                        }, 100);
                        $scope.getElecHeader(getId());
                        SIEJS.alert("提交成功", "success", "确定");
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                        $scope.getElecHeader($scope.paramsBpm.businessKey);

                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // $("#TJSP").removeAttr("disabled");
                });
            };
            /**********************************工作流配置**************************************/

            /**
             * 按钮控制
             * @param value
             */
            $scope.btnC = function ( value){
                if("DS01"  != value){
                    $("#clauseForm input").attr("disabled","true");
                    //$scope.clause.flag=true ;
                    $("#clauseForm select").attr("disabled","true");
                    $("#clauseForm textarea").attr("disabled","true");
                    $("#clauseForm button").attr("disabled","true");
                    $("#btnSave").attr("disabled","true");
                    $("#submitApprovala").attr("disabled","true");
                    if("DS02"  == value){
                        $("#btnChangeBt").attr("disabled","true");
                        $("#btnSave").attr("disabled","true");
                    }else{
                        $("#btnChangeBt").removeAttr("disabled");
                    }
                }else{
                    $("#btnChangeBt").attr("disabled","true");
                }
            };

            //控制滚动条高度
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
            } ;


            setInterval(function () {
                if ($scope.propcessStatus == 'A') {
                    $("#btnChangeBt").removeAttr("disabled");
                    $("#btnSave").removeAttr("disabled");
                    //$("#btnNew").removeAttr("disabled");
                    //$("#btnDel").removeAttr("disabled");
                } else {
                    $("#submitApprovala").attr("disabled","true"); //提交审批
                    //$("#btnSave").attr("disabled","true");//保存
                    $("#btnNew").attr("disabled", "true");
                    $("#btnDel").attr("disabled", "true");
                }

                if ($scope.propcessStatus == 'C') {
                    $("#btnSave").attr("disabled","true");//保存
                    $("#btnChangeBt").removeAttr("disabled");//变更启用
                } else {
                    $("#btnChangeBt").attr("disabled","true");//变更禁用
                }
            }, 10);

            $scope.getName = function () {
                $('#nameLovList').modal('toggle')
            };

            $scope.selectPersonReturn = function (key, value, currentList) {
                $scope.headerData.personId = currentList[0].personId;
            };

            $scope.isSkipApproveFun = function () {
                httpServer.post(URLAPI.updateEleSkipStatus, {
                    'params': JSON.stringify({contractHId : getId(), isGmApprove : $scope.params.isGmApprove})
                }, function (res) {
                    if (res.status == 'S') {
                        console.log(JSON.stringify(res.data));
                        $scope.params.isGmApprove = res.data.isGmApprove;
                        SIEJS.alert('操作成功', "success", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            };

            /*
             * 生成电子签章合同
             */
            $scope.creatElectContract = function () {
                if(!$scope.contractDataTable || $scope.contractDataTable.count ==0 ) {
                    SIEJS.alert('合同附件不能为空', 'error', '确定');
                    return;
                }
                httpServer.post(URLAPI.createContractAttr, {
                    'params': JSON.stringify({elecConHeaderId : $scope.id})
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.headerData.elecFileId = res.headerData.elecFileId;
                        SIEJS.alert('操作成功', "success", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            };


        }])
});
