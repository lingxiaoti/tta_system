/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app','ztree'],function(app,ztree) {
    app.controller('ttaContractSpecialHeaderDCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','validateForm','iframeTabAction','$sce','$timeout',
        function($scope,httpServer,URLAPI,SIEJS,$stateParams,validateForm,iframeTabAction,$sce,$timeout) {
            $scope.info = {};
            $scope.dataTable = [];
            $scope.proposalParams = {};
            $scope.url = URLAPI.showPdfPageOfficeService;
            $scope.dataTable.selectRow;
            $scope.PageOfficeUrl = $sce.trustAsResourceUrl(URLAPI.ttaPageOfficeService + '/pageoffice.js');
            var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
            /**********************************工作流配置  start**************************************/



            //获取url参数对象
            $scope.urlParams = urlToObj(location.hash);

            //先判断是否业务form是否有id 再判断流程form的id
            if ($stateParams.id) {
                $scope.id = $stateParams.id;
            } else if ($scope.urlParams.businessKey) {
                $scope.id = $scope.urlParams.businessKey;

            }

            //获取单据ID
            function getId() {
                return $scope.urlParams.businessKey ? $scope.urlParams.businessKey : $scope.id;
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

            $scope.tabAction = 'infoBusiness'; //根据页面配置
            //流程图参数
            $scope.processImageParams = {
                token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
                id: 'processImg',
                instanceId: $scope.urlParams.processInstanceId,
                key: 'TTA_CONTRACT_SPECIAL_HEADER.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
            };

            $scope.tabChange = function (name) {
                $scope.tabAction = name;
                if (!$scope.processImgLoad) {
                    $scope.getProcessInfo(function () {
                        var p = $scope.processImageParams;
                        $timeout(function () {
                            processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                            $scope.processImgLoad = true;
                        }, 1000)
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
            $scope.btnSubmit = function (newForm) {
                $scope.btnSave(newForm,'2');
            };

            $scope.isSkipApproveFun = function () {
                httpServer.post(URLAPI.updateSkipStatusTtaSoleNonStandarHeader, {
                    'params': JSON.stringify({soleNonStandarHeaderId : getId(), isSkipApprove : $scope.info.isSkipApprove})
                }, function (res) {
                    if (res.status == 'S') {
                        console.log(JSON.stringify(res.data));
                        $scope.info.isSkipApprove = res.data.isSkipApprove;
                        SIEJS.alert('操作成功', "success", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            };

            $scope.submitApprovalEnd = function (){
                $scope.infoApprove = { };
                $scope.infoApprove.deptCode = $scope.info.deptCode;
                $scope.infoApprove.isSkipApprove = $scope.info.isSkipApprove;
                $scope.infoApprove.paymentDate = $scope.info.paymentDate;
                $scope.infoApprove.param = { };
                $scope.extend = {
                    "tasks_assignees": []
                };
                $scope.variables = []; //流程变量
                angular.forEach($scope.infoApprove, function (value, key) {
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
                    "processDefinitionKey": "TTA_CONTRACT_SPECIAL_HEADER.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                    "saveonly": false,
                    "businessKey": $scope.info.ttaContractSpecialHeaderId, //单据ID
                    "title": "合同审核特批-审批" + $scope.info.orderNo , //流程标题，修改为当前业务信息
                    "positionId": 0,
                    "orgId": 0,
                    "userType": "20",
                    "billNo": $scope.info.orderNo
                };

                httpServer.post(URLAPI.processStart, {
                    'params': JSON.stringify($scope.paramsBpm)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.search();
                        SIEJS.alert("提交成功", "success", "确定");
                    }
                    else {
                       // $scope.btnC('0',false);
                        SIEJS.alert(res.msg, "error", "确定");

                    }
                }, function (err) {
                   // $scope.btnC('0',false);
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    // $("#TJSP").removeAttr("disabled");
                });

            };


            /**********************************工作流配置   end**************************************/


            var setting = {
                view: {
                    showIcon: false,
                    fontCss: {}
                },
                data: {
                    key: {
                        name: "tplName"
                    },
                    simpleData: {
                        enable: "false",
                        idKey: "saStdTplDefHeaderId",
                        pIdKey: "parentId",
                        rootPId: 0
                    }
                },
                check: {
                    enable: true,
                    chkStyle: "radio",
                    radioType: "all",
                    chkboxType: {"Y": "", "N": ""}
                },
                callback: {
                    //设置父节点不可选
                    beforeCheck: function (treeId, treeNode) {
                        //    return !treeNode.isParent;
                    }
                }
            };
            var treeObj = null;
            //选择协议
            $scope.getTplName = function () {

                if (!$scope.info.tplType) {
                    SIEJS.alert('请先选择模板类型','error','确定');
                    return ;
                }

                if ('1' === $scope.info.tplType) { //补充协议

                    httpServer.post(URLAPI.findTtaSaStdTplDefHeaderTree,{
                        params: JSON.stringify({ })
                    },function(res) {
                        if(res.status === 'S') {
                            treeObj = $.fn.zTree.init($("#InfoTplTypeSaZtree"), setting, res.data);
                            treeObj.expandAll(true);
                            $('#addInfoSa').modal('toggle');
                        }else{
                            SIEJS.alert(res.msg,'error','确定');
                        }
                    },function(err) {
                        SIEJS.alert('标准模板申请选择协议类型，获取数据失败','error','确定');
                    });

                } else if ('2' === $scope.info.tplType) { //独家协议
                    $('#selectTempId').modal('toggle');
                }

            };

            //补充协议选择保存
            $scope.saveTplType = function (type){

                if(treeObj.getCheckedNodes().length !=0){
                    $scope.info.tplId = treeObj.getCheckedNodes()[0].saStdTplDefHeaderId;
                    $scope.info.quoteTplName = treeObj.getCheckedNodes()[0].tplName;
                }
                $('#addInfoSa').modal('toggle');
            };

            //独家协议选择保存
            $scope.selectTempReturn = function (key, value, currentList) {
                $scope.info.tplId = currentList[0].rulId;
                $scope.info.quoteTplName = currentList[0].ruleName;
            };

            //查询单据信息
            $scope.search = function () {

                httpServer.post(URLAPI.findByIdTtaContractSpecialHeader, {
                        'params': JSON.stringify({ttaContractSpecialHeaderId: getId()})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var Certificate = getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || 'nothing';
                            $scope.btnC(res.data.status);
                            $scope.info = res.data;
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            };

            $scope.btnSave = function (newForm,param) {
                if(!validateForm(newForm)){
                    return;
                }
                httpServer.post(URLAPI.saveOrUpdateTtaContractSpecialHeader, {
                        'params': JSON.stringify($scope.info)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            if ('1' === param) {
                                $scope.id = res.data.ttaContractSpecialHeaderId;
                                $scope.search();
                                SIEJS.alert(res.msg, 'success');
                            } else if ( '2' === param ) {
                                $scope.submitApprovalEnd();
                            }

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );


            };
            function getCookie(name) {
                var cookie_start = document.cookie.indexOf(name);
                var cookie_end = document.cookie.indexOf(";", cookie_start);
                return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
            }
            $scope.openEditor = function () {
                var successLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
                var tokenCode = successLoginInfo.TokenCode;
                var Certificate= getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || 'nothing';
                var  strValue = JSON.stringify({
                    "tokenCode":tokenCode,
                    "Certificate":Certificate,
                    "url":URLAPI.openEditTtaSoleNonStandarHeader,
                    "fileId":$scope.info.soleNonStandarHeaderId
                });
                POBrowser.openWindowModeless('office/word/edit.html', 'width=1000px;height=800px;',strValue);
            };
            //变更
            $scope.btnChange = function () {
                if($scope.info.status != 'C'){
                    SIEJS.alert("单据状态需要是审核通过才能变更");
                    return;
                }
                SIEJS.confirm('变更', '是否确定变更？', '确定', function () {
                    httpServer.post(URLAPI.changeApplyAllTtaSoleNonStandarHeader, {
                            'params': JSON.stringify($scope.info)
                        },
                        function (res) {
                            if (res.status == 'S') {
                                $scope.btnC('D');
                                if (res.data && res.data.soleNonStandarHeaderId) {
                                    iframeTabAction('独家协议(非标准)详情：' + res.data.orderNo + '-' + res.data.orderVersion, 'ttaSoleNonStandarHeaderD/' + res.data.soleNonStandarHeaderId);
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
            //id不为空
            if (getId()) {

                //查询头信息
                $scope.search();
            } else {
                $scope.info.createdByName = userLoginInfo.userName;
                $scope.info.createdBy = userLoginInfo.userId;
                $scope.info.status = 'A';
                //新增:设置当前登录人的主部门
                $scope.info.majorDeptDesc = userLoginInfo.deptName;
                $scope.info.majorDeptId =  userLoginInfo.deptId;
                $scope.info.majorDeptCode = userLoginInfo.deptCode;
            }
            $scope.btnC = function (value) {
                if ("A" != value) {
                    $("#newForm input").attr("disabled", "true");
                    $("#newForm select").attr("disabled", "true");
                    $("#newForm textarea").attr("disabled", "true");
                    $("#newForm button").attr("disabled", "true");

                    $("#saveBtnId").attr("disabled", "true");
                    $("#submitBtnId").attr("disabled", "true");
                    $("#proposalIdSave").attr("disabled", "true");
                    $("#proposalIdDelete").attr("disabled", "true");

                    if ('B' == value  || 'E' ==value || 'D' == value) {
                        $("#btnOtherId").attr("disabled", "true");
                    }else if ('C' == value) {
                        $("#content button").attr("disabled", "true");
                        $("#btnOtherId").removeAttr("disabled");
                    }else{
                        $("#btnOtherId").removeAttr("disabled");
                    }
                } else if('A' ==value){
                    $("#btnOtherId").attr("disabled", "true");
                }
            };

            //选择供应商
            $scope.getSupplierCode = function () {
                $('#supplierCode').modal('toggle');
            };
            //点击确认按钮
            $scope.selectSupplierReturn = function (key, value, currentList) {
                $scope.info.vendorNbr = currentList[0].supplierCode;
                $scope.info.vendorName = currentList[0].supplierName;
            };

            $scope.getContractVendorProposal = function(){
                $('#proposalSupplierLov').modal('toggle')
            };

            //保存poposal供应商数据
            $scope.setProposalSupplierList = function (key, value, currentList) {

                if (!$scope.info.soleNonStandarHeaderId) {
                    SIEJS.alert("请先保存头信息", 'warning');
                    return;
                }

                if(currentList.length == 0){
                    return ;
                }
                httpServer.post(URLAPI.saveOrUpdateAllTtaSoleNonStandarLine, {
                        'params': JSON.stringify({'list':currentList,'id':$scope.info.soleNonStandarHeaderId})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            $scope.dataTable.search(1);
                            SIEJS.alert(res.msg, 'success');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );

            };

            function proposalTableSearch() {
                $scope.proposalParams = {
                    soleNonStandarHeaderId: $scope.info.soleNonStandarHeaderId
                };
                $timeout(function () {
                    $scope.dataTable.search(1);
                }, 100)

            }

            //NEW
            //删除信息
            $scope.btnDelProposalSupplier = function () {
                var selectRowList = $scope.dataTable.selectRowList;
                if (!selectRowList.length) {
                    SIEJS.alert("请选择要删除的数据!", "error", "确定");
                    return;
                }
                var ids = "";
                for (var i = 0; i < selectRowList.length; i++) {
                    ids = ids + selectRowList[i].soleNonStandarLineId + ",";
                }


                SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                    httpServer.post(URLAPI.deleteTtaSoleNonStandarLine,
                        {params: JSON.stringify({ids: ids})},
                        function (res) {
                            if (res.status == "S") {
                                $scope.dataTable.selectRowList = [];
                                $scope.dataTable.search();
                                SIEJS.alert("处理成功", "success", "确定");
                            } else {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                        },
                        function (err) {
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                            $("#btnArrival").attr("disabled", true);
                        }
                    );
                });
            };

            $scope.btnDiscard = function (info) {

                if (info) {
                    SIEJS.confirm('作废', '是否确定作废？', '确定', function () {

                        httpServer.post(URLAPI.cancelTtaContractSpecialHeader, {
                            params: JSON.stringify({ids: info.ttaContractSpecialHeaderId})
                        }, function (res) {
                            if ('S' == res.status) {
                                $scope.dataTable.search();
                                SIEJS.alert('作废成功');
                            } else {
                                SIEJS.alert(res.msg, 'error', '确定');
                            }
                        }, function (error) {
                            console.error(error);
                            SIEJS.alert('作废失败', 'error', '确定');
                        })

                    })
                }
            }

        }])
});
