/**
 * Created by hmx on 2018/9/4.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'],function(app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    //app.controller('ttaSaNonStandarHeaderDCtrl',['$scope', 'httpServer', 'URLAPI', 'SIE.JS','$stateParams','validateForm','iframeTabAction','$sce','$timeout',
    //    function($scope,httpServer,URLAPI,SIEJS,$stateParams,validateForm,iframeTabAction,$sce,$timeout,$http) {
            app.controller('ttaSaNonStandarHeaderDCtrl', function($scope,httpServer,URLAPI,SIEJS,$stateParams,validateForm,iframeTabAction,$sce,$timeout,$http) {
            $scope.info = {};
            $scope.dataTable = [];
            $scope.proposalParams = {};
            $scope.url = URLAPI.showPdfPageOfficeService;
            $scope.dataTable.selectRow;
            $scope.selectStartDate = new Date().getFullYear() + "-01-01";
            $scope.PageOfficeUrl = $sce.trustAsResourceUrl(URLAPI.ttaPageOfficeService + '/pageoffice.js');
            var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
            $scope.isShowFlag = userLoginInfo.userType == '45' ? 1 : 0;
            $scope.userData = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
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
                key: 'TTA_SA_NON_STANDAR_HEADER.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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
            $scope.btnSubmit = function (protocolForm) {
                $scope.btnSave(protocolForm,'2');
            };



            $scope.submitApprovalEnd = function (){
                $scope.infoApprove = { };
                $scope.infoApprove.deptCode = $scope.info.deptCode;
                $scope.infoApprove.isSkipApprove = $scope.info.isSkipApprove;//是否需要GM审批
                $scope.infoApprove.tplCode = $scope.info.tplCode;//模板编码
                $scope.infoApprove.param = { };
                $scope.extend = {
                    "tasks_assignees": []
                };
                //$scope.infoApprove.isSkipApprove = 'Y';//需要GM审批
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
                    "processDefinitionKey": "TTA_SA_NON_STANDAR_HEADER.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                    "saveonly": false,
                    "businessKey": $scope.info.saNonStandarHeaderId, //单据ID
                    "title": "补充协议(非标准)-审批" + $scope.info.orderNo + "-" + $scope.info.orderVersion, //流程标题，修改为当前业务信息
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

            };

            //补充协议选择保存
            $scope.saveTplType = function (type){

                if(treeObj.getCheckedNodes().length !=0){
                    $scope.info.tplId = treeObj.getCheckedNodes()[0].saStdTplDefHeaderId;
                    $scope.info.quoteTplName = treeObj.getCheckedNodes()[0].tplName;
                    $scope.info.tplCode = treeObj.getCheckedNodes()[0].tplCode;
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
                httpServer.post(URLAPI.findByIdTtaSaNonStandarHeader, {
                        'params': JSON.stringify({saNonStandarHeaderId: getId()})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var Certificate = getCookie(appName + '_certificate') || sessionStorage[appName + '_certificate'] || 'nothing';
                            $scope.btnC(res.data.status);
                            $scope.info = res.data;
                            $scope.info.type ='nostandard';
                            proposalTableSearch();
                            var pdfObject = document.getElementById("pdfId");
                            if (!pdfObject && $scope.info.saNonStandarHeaderId) {
                                $("#content").append("<iframe id =\"pdfId\" width=\"100%\" height=\"100%\"   src=\""+ $scope.url+"?businessId="+ $scope.info.saNonStandarHeaderId +"&Certificate=" +Certificate+ "&type=" + "TTA_SA_NON_STANDAR_HEADER" +"\"></iframe>");

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

            $scope.btnSave = function (protocolForm,param) {
                if(!validateForm(protocolForm)){
                    return;
                }
                httpServer.post(URLAPI.saveOrUpdateTtaSaNonStandarHeader, {
                        'params': JSON.stringify($scope.info)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            if ('1' === param) {
                                $scope.id = res.data.saNonStandarHeaderId;
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
                    "url":URLAPI.openEditTtaSaNonStandarHeader,
                    "fileId":$scope.info.saNonStandarHeaderId
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
                    httpServer.post(URLAPI.changeApplyAllTtaSaNonStandarHeader, {
                            'params': JSON.stringify($scope.info)
                        },
                        function (res) {
                            if (res.status == 'S') {
                                $scope.btnC('D');
                                if (res.data && res.data.saNonStandarHeaderId) {
                                    iframeTabAction('独家协议(非标准)详情：' + res.data.orderNo + '-' + res.data.orderVersion, 'ttaSoleNonStandarHeaderD/' + res.data.saNonStandarHeaderId);
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
                $scope.info.type = 'nostandard';

                //新增:设置当前登录人的主部门
                $scope.info.majorDeptDesc = userLoginInfo.deptName;
                $scope.info.majorDeptId =  userLoginInfo.deptId;
                $scope.info.majorDeptCode = userLoginInfo.deptCode;
            }
            $scope.btnC = function (value) {
                if ("A" != value) {
                    $("#protocolForm input").attr("disabled", "true");
                    $("#protocolForm select").attr("disabled", "true");
                    $("#protocolForm textarea").attr("disabled", "true");
                    $("#protocolForm button").attr("disabled", "true");

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
            $scope.getSupplierCode = function (type) {
                $scope.vendorCodeType = type;
                $('#supplierCode').modal('toggle');
            };
            //点击确认按钮
            $scope.selectSupplierReturn = function (key, value, currentList) {
                if ('B' == $scope.vendorCodeType) {
                    $scope.info.partyBVendorName = currentList[0].supplierName;
                } else if ('C' == $scope.vendorCodeType) {
                    $scope.info.partyCVendorName = currentList[0].supplierName;
                } else {
                    $scope.info.vendorCode = currentList[0].supplierCode;
                    $scope.info.vendorName = currentList[0].supplierName;
                }

            };

            $scope.getContractVendorProposal = function(){
                $('#proposalSupplierLov').modal('toggle')
            };

            $scope.isSkipApproveFun = function () {
                httpServer.post(URLAPI.updateSkipStatusTtaSaNonStandarHeader, {
                    'params': JSON.stringify({saNonStandarHeaderId : getId(), isSkipApprove : $scope.info.isSkipApprove})
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

            //保存poposal供应商数据
            $scope.setProposalSupplierList = function (key, value, currentList) {

                if (!$scope.info.saNonStandarHeaderId) {
                    SIEJS.alert("请先保存头信息", 'warning');
                    return;
                }

                if(currentList.length == 0){
                    return ;
                }
                httpServer.post(URLAPI.saveOrUpdateAllTtaSaNonStandarLine, {
                        'params': JSON.stringify({'list':currentList,'id':$scope.info.saNonStandarHeaderId})
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
                    saNonStandarHeaderId: $scope.info.saNonStandarHeaderId
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
                    ids = ids + selectRowList[i].saNonStandarLineId + ",";
                }


                SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                    httpServer.post(URLAPI.deleteTtaSaNonStandarLine,
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

                        httpServer.post(URLAPI.cancelTtaSaNonStandarHeader, {
                            params: JSON.stringify({ids: info.saNonStandarHeaderId})
                        }, function (res) {
                            if ('S' == res.status) {
                                $scope.search();
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
            };
            setInterval(function () {
                $("[name='contractEdit']").removeAttr("disabled");
            }, 500);

            //上传附件
            //$event,'sourceFileName','file','TTA_SA_NON_STANDAR_HEADER_ATTACHMENT','attachmentFileId'
            $scope.upload = function (form,property,elementId,type,propertyId) {
                if ($scope.info[property]) {
                    SIEJS.alert("当前已经有文件请先删除，再上传", 'error', '确定');
                    return;
                }
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
                var fd = new FormData();
                var file = document.getElementById(elementId).files[0];
                if (!file) {
                    SIEJS.alert("请选择上传文件", 'error', '确定');
                    loading.hide();
                    return;
                }
                var fileName = file.name;
                var saNonStandarHeaderId = $scope.info.saNonStandarHeaderId;
                if (saNonStandarHeaderId == undefined || saNonStandarHeaderId == null){
                    SIEJS.alert("头信息为空,上传失败", 'error', '确定');
                }
                fd.append('sideAgrtHId', saNonStandarHeaderId);
                fd.append('file', file);
                fd.append("fileName", fileName);
                fd.append("functionId", type);

                $http.post(URLAPI.ttaSideAgrtHeaderUpload, fd, {
                    transformRequest: angular.identity,
                    headers: {
                        'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                        'Content-Type': undefined
                    }
                }).success(function (response) {
                    if (response.status == 'S') {
                        loading.hide();
                        $scope.info[property] = response.data[0].sourceFileName ;//文件名
                        $scope.info[propertyId] = response.data[0].fileId ;//文件id
                        SIEJS.alert(response.msg, 'success', '确定');
                    } else {
                        loading.hide();
                        SIEJS.alert(response.msg, 'error', '确定');
                    }
                }).error(function(response) {
                    loading.hide();
                    SIEJS.alert("上传失败", 'error', '确定');
                });
            };

            //删除附件
            //info,'sourceFileName','file','TTA_SA_NON_STANDAR_HEADER_ATTACHMENT','attachmentFileId'
            $scope.delUploadFile = function (row, property, elementId, type, propertyId) {
                SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                    var p = {
                        fileId: row[propertyId] //文件id
                    };
                    httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                        'params': JSON.stringify(p)
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.info[property] = null ;//文件名
                            $scope.info[propertyId] = null ;//文件id
                            SIEJS.alert(res.msg, 'success', '确定');
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    }, function (err) {
                        SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                    });
                });
            };

            //下载附件
            //info,'sourceFileName','file','TTA_SA_NON_STANDAR_HEADER_ATTACHMENT','attachmentFileId'
            $scope.downFileEvent = function (row,property,elementId,type,propertyId) {
                //判断文件id是否存在
                if (row[propertyId] == undefined || row[propertyId] == null){
                    SIEJS.alert("当前没有附件可下载!",'warning','确定');
                    return;
                }
                var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + row[propertyId];
                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
            };

            $scope.btnPrint = function () {
                httpServer.post(URLAPI.saNonStandarHeaderPrint, {
                        'params': JSON.stringify($scope.info)
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + res.data;
                            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                );
            }


        });
});
