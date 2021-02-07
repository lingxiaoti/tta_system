/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload','jqueryUi','colResizable','jqprint', 'jqfixedHeaderTable','layui','multiple','XLSX'], function (app, pinyin, ztree, angularFileUpload,jqueryUi,colResizable,jqprint,jqfixedHeaderTable,layui,multiple,XLSX) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('proposalDetailCtrl', function ($http, $scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,dragTable,$timeout,validateForm,lookupCode,$compile,tableXlsExport,jsonToExcel,TableToExcel,saafLoading) {
        // layui.use('table'); // 按需加载layui模块
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.isShowFlag = $scope.userData.userType == '45' ? 1 : 0;

        $scope.isFlag = $scope.userData.userType == '45' ? true : false;

        $scope.onlyShowFlag = $location.search()['onlyShow'] == "1" ? true : false; //是否仅仅查看，不修改数据。
        if($scope.onlyShowFlag) {
            setInterval(function () {
                $(":input").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
                $(":button").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
            }, 500)
        }

        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        var d = new Date();
        d.setMonth(d.getMonth() + 2);
        $scope.nowDateParam = d.getFullYear() + '-' + (d.getMonth() + 1);

        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }

        if($scope.lookupCodeParty){


            // terms  Starat校验 *******************************************************************************************
            //获取 残损购货折扣 条款
            $scope.getShop = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_NAME' && x.lookupCode == '3';
            });
            //退货处理服务费
            $scope.getReturn = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_NAME' && x.lookupCode == '4';
            });

            //(集中收货) 购货折扣
            $scope.getCenter = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_NAME' && x.lookupCode == '5';
            });

            // 公司标准
            $scope.getCompany= $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_NAME' && x.lookupCode == '6';
            });

            //按其他标准
            $scope.getOther = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_NAME' && x.lookupCode == '2';
            });
            $scope.getTermsFree = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_FREE' ;
            });
            //获取税率
            $scope.getCurrentTax = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERM_CATEGORY_TAX' && x.lookupCode == '01';
            });
            $scope.getTTaControl = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TTA_TERMS_VALUE_CONTROL'
            });
            //获取税率
            $scope.getCurrentTermsCat = $scope.lookupCodeParty.filter(function (x) {
                return $scope.getCurrentTax && $scope.getCurrentTax.length>0 &&  x.lookupValuesId == $scope.getCurrentTax[0].parentLookupValuesId;
            });
        }
        // terms  End  校验 *******************************************************************************************
        var id = $stateParams.id;
        $scope.showInfoNavTabs = true;
        $scope.printCount = 0;
        $scope.tradePrintCount = 0;
        $scope.tabActionFlag = true ;
        $scope.dataTableNBExtendNbFeeThod = true ;
        $scope.brandPlnLDataTable = [];
        $scope.firstText = "请选择";
        $scope.params = {};
        $scope.qIsQuestConfirm = '';
        $scope.dataTableNBExtendFlag = '';
        $scope.contractParams = {};
        $scope.yearInfo = {};
        $scope.deleteTtaTerms =  [];
        $scope.dataTableNBExtend = [] ;
        $scope.storeNumber = [] ;
        $scope.lineList = [];
        $scope.warehouseParamsT = {lookupType :'TTA_TERMS_WH'};
        $scope.warehouseParamsTDataTable = [] ;
        $scope.termsComparisionPartList1 = [];
        $scope.termsComparisionPartList2 = [];
        $scope.termsComparisionPartList3 = [];
        $scope.ttaContractLineTable = [] ;
        $scope.termRemark = "";
        $scope.areaArr = [];
        $scope.warehouseArr = [];
        $scope.saleWayList = []; //销售方式
        $scope.companyName = '武汉屈臣氏个人用品商店有限公司'; //甲方公司默认值
        $scope.CountH = {};//计算高度
        $scope.questionReturnList= [];
        $scope.rpTable = [];
        $scope.deptProject={};
        $scope.deptProject.defaultDeptId = userLoginInfo.deptId;//默认当前登录人的部门id;
        $scope.clause={};
        $scope.brandPlnLInfo = {};
        $scope.changeReason= false;
        $scope.questionDataTable = [];
        $scope.fileDataParams = {};
        $scope.ttaDeptFeeReportTableExcel = [];
        $scope.ttaDeptFeeReportHeadExcel ={};
        $scope.analysisTableData = [];

        setInterval(function () {
            if($scope.isFlag && $scope.params.status != 'C') {
                $("#saveQuestionnaire").attr('disabled', false);
                $("#saveAnalisisTest").attr('disabled', false);
                if ($scope.params.newOrExisting == 'New_Vendor') { //新供应商变成不可用，且自动勾选。
                    $scope.params.isSkipApprove = 'Y'; //勾选
                    $("#isSkipApprove").attr('disabled', true);
                } else {
                    $("#isSkipApprove").attr('disabled', false);
                }
            }

            if ( $scope.params.status == 'C' || !$scope.isFlag) {
                $("#bicremark").attr('disabled', true);
                $("#brandExportId").removeAttr('disabled',false);
            }
        }, 100);

        $scope.AnalaysisNiceOpction = "{cursoropacitymin:1,railvalign: 'bottom',cursorcolor:'#1e90c2',autohidemode:false,cursorwidth:"+(20/window.devicePixelRatio)+",zindex:50}";
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
        //*********************控制页签显示  start**********************
        $scope.controllTabShow ={};
        $scope.controllTabShow.remarkFlag = false; //问卷remark信息控制，默认不可见。
        var actionFlag =$scope.urlParams.action;
        if ('approval' == actionFlag || actionFlag == 'detail' ||  ($scope.urlParams.activeTab != undefined && $scope.urlParams.activeTab == 'myProcess') ) {
            httpServer.post(URLAPI.proposalQueryProcessNodeStatus, {
                    'params': JSON.stringify({
                        procDefKey:"TTA_PROPOSAL.-999",
                        businessKey: getId(),
                        dicKey:"TTA_TERMS_PROCESS_CTL"
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.controllTabShow.flag = res.data.flag;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    //SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    //console.log("操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员");
                }
            );
            //问卷ramrk字段控制
            httpServer.post(URLAPI.proposalQueryProcessNodeStatus, {
                    'params': JSON.stringify({
                        procDefKey:"TTA_PROPOSAL.-999",
                        businessKey: getId(),
                        dicKey:"TTA_REMARK_PROCESS_CTL"
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.controllTabShow.remarkFlag = res.data.flag;
                        //console.log("当前remark字段调用后台是否显示，true显示，false不显示,当前返回的值:" + JSON.stringify(res.data))
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    //SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    //console.log("操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员");
                }
            );

        }

        //**********************控制页签显示 end************************


        //流程图参数
        $scope.processImageParams = {
            token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
            id: 'processImg',
            instanceId: $scope.urlParams.processInstanceId,
            key: 'TTA_PROPOSAL.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
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

        //表单是否变更
        $scope.formIsChanged = function(){

            /*            var endParams = JSON.stringify($scope.params);                  //proposal 头数据
                        var endBrandparams = JSON.stringify($scope.brandparams);            //品牌数据
                        var endBrandPlnLDataTable = JSON.stringify($scope.brandPlnLDataTable);      //品牌行数据
                        var endContractParams = JSON.stringify($scope.contractParams);          //条款头数据
                        var endDataTableTerms = JSON.stringify($scope.dataTableTerms);          //条款行数据
                        var endLineList = JSON.stringify($scope.lineList );                //问查
                        var endNbe = JSON.stringify($scope.nbe);                     // NPP*/
            var endTtaContractLineTable = JSON.stringify($scope.ttaContractLineTable);    //TTA Summary

            /*            if($scope.$scope.startParams != endParams){
                            return true;
                        }
                        if($scope.startBrandparams != endBrandparams){
                            return true;
                        }
                        if($scope.startBrandPlnLDataTable  != endBrandPlnLDataTable){
                            return true;
                        }
                        if($scope.startContractParams != endContractParams){
                            return true;
                        }
                        if($scope.startDataTableTerms != endDataTableTerms){
                            return true;
                        }
                        if($scope.startLineList != endLineList){
                            return true;
                        }
                        if($scope.startNbe != endNbe){
                            return true;
                        }*/
            if( $scope.startTtaContractLineTable &&($scope.startTtaContractLineTable != endTtaContractLineTable)){
                return true;
            }
            return false;
        };


        //提交
        $scope.submitApproval = function (value) {
            $scope.btnC('0',true);

            /*            if(!value ){
                            SIEJS.alert('请填写完整表单【 TTA Summary】', 'error', '确定');
                            return;
                        }*/

            //后台校验
            $scope.submitApproval2();



        };

        $scope.isSkipApproveFun = function () {
            httpServer.post(URLAPI.updateSkipStatus, {
                'params': JSON.stringify({proposalId : getId(), isSkipApprove : $scope.params.isSkipApprove})
            }, function (res) {
                if (res.status == 'S') {
                    console.log(JSON.stringify(res.data));
                    $scope.params.isSkipApprove = res.data.isSkipApprove;
                    SIEJS.alert('操作成功', "success", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.submitApprovalEnd = function (){
            /* $scope.clause = [{
                 name: "fcsPurchse",
                 type: 'string',
                 value: $scope.params.fcsPurchse
             }];*/
            $scope.clause.fcsPurchse = $scope.params.fcsPurchse;
            $scope.clause.deptCode = $scope.params.deptCode;
            $scope.clause.isNew = $scope.params.newOrExisting;
            $scope.clause.isSkipApprove = 'Y';//需要GM审批
            $scope.clause.majorDeptCode = $scope.params.majorDeptCode;//大部门

            //如果没有获取到值不能提交
            if (!$scope.clause.fcsPurchse || !$scope.clause.deptCode || !$scope.clause.isNew) {
                SIEJS.alert("数据没有加载完成，请稍后提交!", "error", "确定");
                return;
            }

            $scope.clause.param = {
                "orderNo":$scope.params.orderNbr,
                "vendorNbr":$scope.params.vendorNbr,
                "vendorName":$scope.params.vendorName,
                "proposalYear":$scope.params.proposalYear,
                "saleType":$scope.params.saleTypeName,
                "brandCn":$scope.contractParams.brandCn,
                "createdByCode":$scope.params.createdName,
                "createdByName":$scope.params.createdByName,
                "templeName":"TTA_PROPOSAL_EDIT_MAIL"
            };
            console.log("审批流参数信息: " + JSON.stringify($scope.clause));
            //console.log("审批流参数信息: " + JSON.stringify($scope.clause));
            $scope.extend = {
                "tasks_assignees": []
            };
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
                "paramss": $scope.params,
                "processDefinitionKey": "TTA_PROPOSAL.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                "saveonly": false,
                "businessKey": $scope.params.proposalId, //单据ID
                "title": "proposal审批" + $scope.params.orderNbr + "_" + $scope.params.versionCode, //流程标题，修改为当前业务信息
                "positionId": 0,
                "orgId": 0,
                "userType": "20",
                "billNo": $scope.params.orderNbr
            };

            httpServer.post(URLAPI.processStart, {
                'params': JSON.stringify($scope.paramsBpm)
            }, function (res) {
                if (res.status == 'S') {
                    $scope.btnC('-1',true);
                    id = getId();
                    $scope.search();
                    SIEJS.alert("提交成功", "success", "确定");
                    //$("#TJSP").removeAttr("disabled");
                }
                else {
                    $scope.btnC('0',false);
                    SIEJS.alert(res.msg, "error", "确定");

                }
            }, function (err) {
                $scope.btnC('0',false);
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                // $("#TJSP").removeAttr("disabled");
            });

        };


        /**********************************工作流配置   end**************************************/
        var TermsChange ={
            feeNotax: '',
            feeIntas: '',
            yTermsA: '',
            unit: '',
            unitId: '',
            rule:'',
            ttaValueRefer:'',
            yYear: '',
            collectType: "",
            referenceStandard: '',
            clauseTreeId: '',
            incomeType: '',
            yTerms:'',
            yTermsEn:'',
            termsSystem:'',
            code:'',
            pCode:'',
            relation:'',
            termCategory:'',
            proposalId:'',
            collectTypeList:'',
            incomeTypeList:'',
            clauseId:'',
            unitList:[],
            orderNo:'',
            termsLId:'',
            oiType:'',
            termsHId: '',
            oldClauseId:''
        };
        //选择proposal信息
        $scope.getLastYearOderNo = function () {
            //  $scope.areaComponent = e;

            if(!$scope.params.proposalYear){
                SIEJS.alert('请先选择PROPOSAL 年度', "error", "确定");
                return ;
            }
            if(!$scope.params.vendorNbr){
                SIEJS.alert('请先选择供应商', "error", "确定");
                return ;
            }
            $('#lastYearOrderNoId').modal('toggle')
        };

        //查询单据信息
        $scope.search = function () {

            $scope.proposalId = $scope.id;

            httpServer.post(URLAPI.proposalFindById, {
                    'params': JSON.stringify({proposalId: $scope.proposalId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data;
                        $scope.btnC('-1',false);
                        $scope.iniData();
                        $scope.brandPlnLineLov.search(1);
                        //控制按钮
                        //用于判断表单是否变更
                        $scope.startParams= JSON.stringify($scope.params);
                        if($scope.urlParams.businessKey) {
                            $scope.searchBrandPlnH();
                            $scope.searchTerms();
                            $scope.ttaContractLine();
                            $scope.termsComparision();
                            //console.log(888888888882);
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


        //点击确认按钮
        $scope.selectlastYearOrderNoReturn = function (key, value, currentList) {

            $scope.params.lastYearVendorCode = currentList[0].vendorNbr;
            //$scope.params.proposalYear = currentList[0].proposalYear;
            $scope.params.lastYearOrderNo = currentList[0].orderNbr;
            $scope.params.lastYearVendorName = currentList[0].vendorName;
            $scope.params.lastYearProposalId = key;
            //$scope.searchPro();
        };


        // tab 切换
        $scope.tabAction = 'LgFeeBatchHeadersDetailBusiness'; //根据页面配置
        $scope.tabActionChild = 'contractNew'; //根据页面配置
        $scope.tabChangeChild = function (name){
            $scope.tabActionChild = name;
        };
        $scope.tabChangeCheck = function (){
            if('ttaContractLine' === $scope.tabAction){
                if ($scope.formIsChanged()) {
                    $scope.tabActionFlag = false ;
                    SIEJS.alert('【 TTA Summary】数据已改变，请先保存', 'info', '确定');
                }
            }
        };

        $scope.analysisStatus = 0;
        $scope.tabChangeInfo = function (name) {
            $scope.tabAction = name;
            if (!getId() && 'opnProcess' != name) {
                return;
            }
            if (name=='proposalHeader') {
                $scope.search();

            }else if (name == 'brandPln') {
                if ($scope.params.isPlnConfirm == 'Y') {
                    $scope.showinfoButton = true;
                }else {
                    $scope.showinfoButton = false;
                }
                $scope.searchBrandPlnH();
            }else if (name == 'ttaTerms') {
                $scope.searchTerms();
            } else if (name == "questionnaire") {
                $scope.fileDataParams = {businessId: getId(), functionId: "tta_proposal_header"};
                $scope.searchQuestionnaire();
                $scope.fileDataTable.search();
                $scope.searchQuestionDataTable(getId());
            } else if (name == "termsComparision") {
                $scope.termsComparision();
            }else if(name == "newbreedExtend"){
                $scope.searchNBExtent();
            } else  if (name == 'tradeClause') {
                $scope.tradeClause();
            }else if(name == 'ttaContractLine'){
                $scope.ttaContractLine();
            }else if(name == 'ttaDeptFeeReport'){
                $scope.ttaDeptFeeReport();
            } else if (name == 'btnDeptFee') {
                //console.log("部门协定收费标准")
                $scope.searchA();
            }else if (name == 'termsAnalysis') {
                //2020.2.25 analysis改造，改存储数据方案，注释
                $scope.searchAnalysis();
            } else if (name == 'termsNewAnalysis') {
                $scope.searchNewAnalysis();
            } else if ('opnProcess') {
                $scope.tabChange('opnProcess')
            }

        };

        $scope.brandDetailParams = {};
        //查询单据信息品牌计划
        $scope.searchBrandPlnH = function () {
            $("#brandExportId").attr("disabled",false);
            httpServer.post(URLAPI.brandplnHFindById, {
                    'params': JSON.stringify({proposalId: getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.brandparams = res.data;
                        $scope.rpTable[0] = res.data;
                        $scope.brandDetailParams = {brandplnHId: $scope.brandparams.brandplnHId};
                        //用于判断表单是否变更
                        $scope.startBrandparams = JSON.stringify($scope.brandparams);
                        $scope.searchBrandPlnL();
                        //$scope.searchRpTable();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询汇总数据
        $scope.searchRpTable = function () {
            httpServer.post(URLAPI.brandplnHFind, {
                    'params': JSON.stringify({brandplnHId: $scope.brandparams.brandplnHId})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.rpTable = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询品牌明细
        $scope.searchBrandPlnL = function () {
            httpServer.post(URLAPI.brandplnLFind, {
                    'params': JSON.stringify({brandplnHId: $scope.brandparams.brandplnHId})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.brandPlnLDataTable = res.data;
                        //用于判断表单是否变更
                        $scope.startBrandPlnLDataTable = JSON.stringify($scope.brandPlnLDataTable);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //品牌明细导出
        $scope.brandBtnExport = function (key, value, currentList) {
            var name = 'brandDetalDataTable';
            var params = $scope.brandDetailParams;
            var count = $scope.brandDetalDataTable.count;
            tableXlsExport(name, params, count);
        };

        $scope.brandBtnPrint = function(){
            jQuery("#brandPln").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true
            })
        };



        /***************************查询  TTA TERMS start****************/
        $scope.searchTerms = function () {

            $scope.proposalId = id;

            httpServer.post(URLAPI.termsHFindById, {
                    'params': JSON.stringify({proposalId:  getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractParams = res.data;
                        if(!res.data.termsHId){
                            //  $scope.contractParams.createdBy = userLoginInfo.userId;
                            // $scope.contractParams.createdByName = userLoginInfo.userName;
                            // $scope.contractParams.employeeNumber  = userLoginInfo.employeeNumber ;
                        }else{
                            if(document.getElementById('contractParams_oldYear')){
                                // dragTable('contractParams_oldYear');
                            }
                            // dragTable('contractParams_year');
                            $timeout(function () {
                                $scope.startContractParams = JSON.stringify($scope.contractParams);
                            },0);
                            // $scope.initTable();
                            $scope.searchTermsLine();
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

        $scope.termsCountAmount = function (flag){
            $scope.termsCountTax = 0 ;
            var yTermsString = ['一般购货折扣','(提前付款) 购货折扣','(残损) 购货折扣','(集中收货) 购货折扣','促销折扣'];
            //获取目标退佣
            var values = $scope.dataTableTerms.filter(function (x) {
                return x.yTerms  == '目标退佣';
            });
            var valuesOne = $scope.dataTableTerms.filter(function (x) {
                return x.yTerms  == '第一层';
            });
            var returnString  = '-9999' ;
            $scope.returnArray = [] ;

            $scope.findMethodWindosOLd = $scope.termsFindMethodData.filter(function (x) {
                return x.pClauseId == valuesOne[0].clauseId;
            });
            $scope.findMethodWindos = angular.copy($scope.findMethodWindosOLd) ;
            $scope.findMethodWindos.push({'name':'end'});
            for (var i = 0 ;i<$scope.dataTableTerms.length;i++){
                //“一般购货折扣”+“（提前付款）购货折扣”+“（残损）购货折扣”+“（集中收货）购货折扣”+“促销折扣”

                if($scope.dataTableTerms[i].yTerms && (yTermsString.indexOf($scope.dataTableTerms[i].yTerms) > -1) && $scope.dataTableTerms[i].yYear  ){
                    if('一般购货折扣' == $scope.dataTableTerms[i].yTerms){
                        $scope.termsCountTax = $scope.termsCountTax + Number($scope.dataTableTerms[i].yYearOld?$scope.dataTableTerms[i].yYearOld : ($scope.dataTableTerms[i].yYear?$scope.dataTableTerms[i].yYear:0));
                    }else{
                        $scope.termsCountTax = $scope.termsCountTax + Number($scope.dataTableTerms[i].yYear?$scope.dataTableTerms[i].yYear:0);
                    }

                }
                if(values.length>0 &&  $scope.dataTableTerms[i].pCode == values[0].code && $scope.dataTableTerms[i].incomeType){
                    if(returnString != $scope.dataTableTerms[i].yTerms){
                        returnString = $scope.dataTableTerms[i].yTerms ;
                        var returnObject = {};
                        $scope.returnArray.push(returnObject) ;
                        returnObject.begin = $scope.dataTableTerms[i].yTerms ;
                    }
                    returnObject[$scope.dataTableTerms[i].incomeType] = $scope.dataTableTerms[i].yYearOld?$scope.dataTableTerms[i].yYearOld : $scope.dataTableTerms[i].yYear;
                    returnObject.clauseId = $scope.dataTableTerms[i].clauseId ;
                    returnObject.clauseTreeId = $scope.dataTableTerms[i].clauseTreeId ;
                }
            }
            if('B' == $scope.contractParams.agreementEdition){
                $scope.termsCountTax = 0;
            }
            //采购额(含税)*原返佣比率
            $scope.termsCountOldAmount = $scope.contractParams.fcsPurchse * $scope.termsCountTax /100 ;
            // [金额（含税）/(1+BEOI Tax 税率)+金额（含税）/(1+BEOI Tax 税率)*BEOI Tax 税率*（1+附加税税率）*屈臣氏分配比例]*（1+BEOI Tax 税率）
            $scope.termsCountNewAmount = ( $scope.termsCountOldAmount/((100 + Number($scope.contractParams.beoiTax)) / 100) +
                $scope.termsCountOldAmount/((100 + Number($scope.contractParams.beoiTax)) / 100) * (Number($scope.contractParams.beoiTax)/100)*
                ((100 + 12)/100) *(Number($scope.contractParams.redTicketScale)/100)   ) *   ((100 + Number($scope.contractParams.beoiTax)) / 100)  ;
            //新返佣金额（含税）/（1+BEOI Tax 税率）
            $scope.termsCountNewNoAmount = ( $scope.termsCountOldAmount/((100 + Number($scope.contractParams.beoiTax)) / 100) +
                $scope.termsCountOldAmount/((100 + Number($scope.contractParams.beoiTax)) / 100) * (Number($scope.contractParams.beoiTax)/100)*
                ((100 + 12)/100) *(Number($scope.contractParams.redTicketScale)/100)   )   ;

            $scope.taxCom =     $scope.termsCountNewAmount/$scope.contractParams.fcsPurchse * 100 ;
            $scope.additionalChange = $scope.termsCountNewNoAmount - $scope.termsCountNewAmount ;
            $scope.additionalChangeTax = ($scope.termsCountNewNoAmount - $scope.termsCountNewAmount) * (12/100);
            $scope.supplierAddFee = 0 -( ($scope.termsCountNewNoAmount - $scope.termsCountNewAmount) * (12/100)  +  $scope.additionalChange );

            $scope.findMethodWindosCurrent = $scope.findMethodWindos.filter(function (x) {
                return x.name.indexOf('含税')>-1;
            });
            if($scope.findMethodWindosCurrent.length == 0){
                $scope.findMethodWindosCurrent = $scope.findMethodWindos.filter(function (x) {
                    return x.name.indexOf('采购额')>-1;
                });
            }
            $scope.findMethodWindosReturn = $scope.findMethodWindos.filter(function (x) {
                return x.name.indexOf('佣金比例')>-1;
            });
            for(var i = 0;i<$scope.returnArray.length;i++){
                var name = $scope.findMethodWindosCurrent[0].name ;
                var nameReturn = $scope.findMethodWindosReturn[0].name ;
                //【第一层采购额（含税）*第一层原返佣比率/(1+BEOI Tax 税率)+第一层采购额（含税）*第一层原返佣比率/(1+BEOI Tax 税率)*BEOI Tax 税率*（1+附加税税率）*屈臣氏分配比例】*（1+BEOI Tax 税率）/第一层采购额（含税）
                var newTax =  ( Number($scope.returnArray[i][name]) * Number($scope.returnArray[i][nameReturn]) / ((100 + Number($scope.contractParams.beoiTax)) / 100) +
                    Number($scope.returnArray[i][name]) * Number($scope.returnArray[i][nameReturn]) / ((100 + Number($scope.contractParams.beoiTax)) / 100)*((Number($scope.contractParams.beoiTax)) / 100)
                    * ((100 + 12)/100) * (Number($scope.contractParams.redTicketScale)/100)
                ) * ((100 + Number($scope.contractParams.beoiTax)) / 100) / Number($scope.returnArray[i][name])   ;
                $scope.returnArray[i].end = newTax.toFixed(2);
            }
            $scope.termsCountTax = $scope.termsCountTax.toFixed(2);
            $scope.termsCountOldAmount =  $scope.termsCountOldAmount.toFixed(2);
            $scope.taxCom = $scope.taxCom.toFixed(2);
            $scope.termsCountNewAmount = $scope.termsCountNewAmount.toFixed(2);
            $scope.termsCountNewNoAmount = $scope.termsCountNewNoAmount.toFixed(2);
            $scope.additionalChange = $scope.additionalChange.toFixed(2);
            $scope.additionalChangeTax = $scope.additionalChangeTax.toFixed(2);
            $scope.supplierAddFee = $scope.supplierAddFee.toFixed(2);
            if(flag && '1' == flag){
                $('#termCountAmount').modal('toggle');
            }

        };

        //TTA 计算确认
        $scope.termsTtaSave = function (flag){
            if(flag && '2' == flag) {
                for (var i = 0; i < $scope.returnArray.length; i++) {
                    var values = $scope.dataTableTerms.filter(function (x) {
                        return x.clauseId && x.clauseTreeId && $scope.returnArray[i].clauseId == x.clauseId && $scope.returnArray[i].clauseTreeId == x.clauseTreeId;
                    });
                    values[0].yYear = $scope.returnArray[i].end;
                    var returnFee = $scope.dataTableTerms.filter(function (x) {
                        return x.yTerms  == '目标退佣';
                    });
                    if(values[0].pCode && returnFee[0].code
                        &&  values[0].pCode == returnFee[0].code && values[0].incomeType
                        &&  values[0].incomeType.indexOf('不含税') >-1){
                        var returnFees = $scope.dataTableTerms.filter(function (x) {
                            return x.pCode &&  x.pCode == returnFee[0].code  && x.incomeType &&  x.incomeType.indexOf('含税') >-1 && x.clauseTreeId != values[0].clauseTreeId && values[0].clauseId ==x.clauseId ;
                        });
                        if(returnFees.length>0){
                            returnFees[0].yYear = (currentRow.yYear * ((100 + Number($scope.contractParams.beoiTax)) / 100) ).toFixed(2);
                            //$scope.executeParams(returnFees[0],'1');
                        }

                    }
                }
                //一般购货折扣
                var termsValues = $scope.dataTableTerms.filter(function (x) {
                    return x.yTerms == '一般购货折扣';
                });
                var leftYear = 0;
                var rightYear = 0;
                if (!termsValues[0].yYear) {
                    leftYear = 0;
                } else {
                    leftYear = Number(termsValues[0].yYear);
                }
                if (!termsValues[0].yYearOld) {
                    rightYear = 0;
                } else {
                    rightYear = Number(termsValues[0].yYearOld);
                }
                if (leftYear == rightYear) {
                    if (termsValues[0].yYear) {
                        termsValues[0].yYear = Number(termsValues[0].yYear) + Number(($scope.taxCom - $scope.termsCountTax).toFixed(2));
                    } else {
                        termsValues[0].yYear = ($scope.taxCom - $scope.termsCountTax).toFixed(2);
                    }
                    $scope.executeParams(termsValues[0], '2',null);
                }


            }
            if(flag && '1' == flag ){
                $('#termCountAmount').modal('toggle');
            }

        };
        // 把表格转化成layuiTable
        /*        $scope.initTable = function (height) {
                    // var height = height;
                    $timeout(function() {
                        var contractParams_oldYearTheadHeight = height? height : $scope.setTtaContractLine("contractParamsScrollId");
                        console.log(contractParams_oldYearTheadHeight);
                        var table = layui.table;
                        //转换静态表格
                        table.init('contractParams_oldYearThead', {
                            height: contractParams_oldYearTheadHeight,
                            limit: 99 ,
                        });
                        table.init('contractParams_YearThead', {
                            height: contractParams_oldYearTheadHeight,
                            limit: 99 ,
                        });
                    }, 2000);
                };*/
        $scope.selectCurrent = function (){
            var pClaseId = $scope.dataTableTerms[$scope.dataTableTerms.selectRow].clauseId ;
            var currentAll = $scope.dataTableTerms.filter(function (x) {
                return x.clauseId  && pClaseId && x.clauseId == pClaseId;
            });
            $scope.selectTop = $scope.dataTableTerms.indexOf(currentAll[0]);
            $scope.selectBottom = $scope.dataTableTerms.indexOf(currentAll[currentAll.length-1]);
            $scope.dataTableTerms.selectRow = pClaseId;
        };
        $scope.invoiceTypeFunc = function (){
            if(!$scope.contractParams.invoiceType || 'A' == $scope.contractParams.invoiceType){
                $scope.contractParams.redTicketScale = '';
            }
        };
        $scope.searchTermsLine = function () {
            httpServer.post(URLAPI.termsLFindById, {
                    'params': JSON.stringify(
                        {   year: $scope.contractParams.proposalYear,
                            status:'DS03',
                            termsHId:$scope.contractParams.termsHId,
                            businessType:'01',
                            oldYear:$scope.contractParams.oldYear,
                            proposalId:$scope.contractParams.proposalId
                        }
                    )
                },
                function (res) {
                    if (res.status == 'S') {
                        //查询所有单位
                        if(res.data.length>0){
                            var current  = res.data.find(function(x){
                                return x.clauseId;
                            });
                            $scope.termsFindUnit(current.clauseId,res);
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

        $scope.termsFindUnit = function (clauseId, resv) {
            httpServer.post(URLAPI.termsLUnit, {
                    'params': JSON.stringify({clauseId: clauseId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.ttaTermsUnitAll = res.data ;
                        $scope.ttaTermsUnit = $scope.ttaTermsUnitAll.filter(function (x) {
                            return x.isMajor == 'Y';
                        });
                        //查询所有收取方式
                        $scope.termsFindMethod(clauseId, resv);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询收取方式
        $scope.termsFindMethod = function (clauseId, resv) {
            httpServer.post(URLAPI.termsLFindMethod, {
                    'params': JSON.stringify({termsHId: $scope.contractParams.termsHId})
                },
                function (res) {
                    if (res.status == 'S') {

                        $scope.termsFindMethodData = res.data;
                        angular.forEach(resv.data, function (item, index, array) {

                            //对应行得到对应的收款方式
                            if (item.clauseId) {
                                var findMethod = $scope.termsFindMethodData.filter(function (x) {
                                    return x.pClauseId == item.clauseId;
                                });
                                item.incomeTypeList = findMethod;
                            }
                            //得到对应的单位
                            if (item.clauseTreeId && item.incomeType && item.incomeType != "-1") {
                                var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                                    return x.clauseId == item.clauseTreeId;
                                });
                                item.unitList = findUnit;
                                item.unit = findUnit.length == 1? findUnit[0].unitValue:'';
                                item.unitId = findUnit.length == 1? findUnit[0].collectTypeId:'';
                                item.rule = findUnit.length == 1? findUnit[0].rule:'';
                            }
                            // if (!item.incomeType) {
                            //     item.incomeType = "-1";
                            // }
                        });
                        $scope.dataTableTerms = resv.data;
                        $scope.dataTableTermsOLd = resv.data;
                        //获取目标退佣
                        $scope.TargetCommissionRebate = $scope.dataTableTerms.filter(function (x) {
                            return x.yTerms  == '目标退佣';
                        });
                        //$scope.deptAccordDataTable = resv.data;

                        $timeout(function () {
                            // console.log(43434343434);
                            jQuery( "#contractParams_oldYear tr" ).resizable();
                            jQuery( "#contractParams_oldYear th" ).resizable();
                            jQuery( "#contractParams_oldYear td" ).resizable();
                            jQuery( "#contractParams_year tr" ).resizable();
                            jQuery( "#contractParams_year th" ).resizable();
                            jQuery( "#contractParams_year td" ).resizable();
                            $scope.setTtaContractLine("contractParamsScrollId");
                        },200);

                        //用于判断表单是否变更
                        $timeout(function () {
                            $scope.startDataTableTerms = JSON.stringify(resv.data);
                        },0);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        //营改增加成比例
        $scope.salesUpScaleBlurFunc = function (value){
            var reg = /^[0-6]$/;
            if(!reg.test($scope.contractParams.salesUpScale)){
                jQuery("#contractParams_salesUpScale").tip('请输入邮箱或手机号');
            }
        };
        /***************************查询  TTA TERMS end ****************/

        /***************************查询新品种宣传服务费  start****************/
        $scope.searchNBExtent = function () {
            httpServer.post(URLAPI.findNBExtend, {
                    'params': JSON.stringify({proposalId: getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.nbe = res.data;
                        //用于判断表单是否变更
                        $timeout(function () {
                            $scope.startNbe = JSON.stringify($scope.nbe);
                        },0);

                        $scope.searchNBExtentL($scope.nbe.newbreedExtendHId);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };


        $scope.searchNBExtentL = function (id) {
            httpServer.post(URLAPI.findNBExtendL, {
                    'params': JSON.stringify(
                        {
                            newbreedExtendHId:id
                        }
                    )
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.dataTableNBExtend = res.data;
                        $scope.storeNumberAll = $scope.dataTableNBExtend.filter(function (x) {
                            return x.chargeMethod && x.breadName == '0';
                        });
                        $scope.storeNumberNoAll = $scope.dataTableNBExtend.filter(function (x) {
                            return x.chargeMethod && x.breadName != '0';
                        });
                        var dataTableNBExtendGetData = $scope.dataTableNBExtend.filter(function (x) {
                            return x.chargeMethod ;
                        });
                        if (dataTableNBExtendGetData.length == 0 && $scope.nbe.newPayment && $scope.nbe.newPayment == '2'){
                            $scope.dataTableNBExtendNbFeeThod = true ;
                        }else{
                            $scope.dataTableNBExtendNbFeeThod = false ;
                        }
                        //用于判断表单是否变更
                        $timeout(function () {
                            $scope.startDataTableNBExtend = JSON.stringify($scope.dataTableNBExtend);
                        },0);

                        $scope.btnNBE($scope.nbe.isNewConfirm);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        //新品种收取范围发生变化的时候
        $scope.collectRangeFun = function (){
            if($scope.nbe.collectRange && $scope.nbe.collectRange != '3'){
                $scope.nbe.storeStyle = '';
            }
        };
        //保存 新品种宣传服务费
        $scope.saveNewbreedE = function (form,flag) {
            if(!validateForm(form)){
                return;
            }
            httpServer.post(URLAPI.saveOrUpdateNBExtend,
                {params: JSON.stringify(
                        {
                            nbe:$scope.nbe,
                            dataTableNBExtend:$scope.dataTableNBExtend,
                        }
                    )},
                function (res) {
                    if (res.status == 'S') {
                        $scope.nbe.isNewConfirm = 'N';
                        $scope.nbe.tphStatus = 'A';
                        if('1' ==  flag ){
                            SIEJS.alert(res.msg, 'success');
                            $scope.searchNBExtent();
                        }else{
                            httpServer.post(URLAPI.saveTtaNBEComfirm,
                                {params: JSON.stringify({
                                        proposalId: $scope.nbe.proposalId,
                                        newbreedExtendHId: $scope.nbe.newbreedExtendHId
                                    })},
                                function (res) {
                                    if (res.status == "S") {
                                        $scope.btnNBE('Y');
                                        $scope.nbe.isNewConfirm = 'Y';
                                        SIEJS.alert("处理成功", "success", "确定");
                                        $scope.searchNBExtent();
                                    } else {
                                        SIEJS.alert(res.msg, "error", "确定");
                                    }
                                },
                                function (err) {
                                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                                    $("#saveButton").attr("disabled", true);
                                }
                            );
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
        /**
         * 收取方式发生变化的时候
         */
        $scope.chargeMethodChange = function (row,value,lookUpData) {
            if ("" === value) {
                $scope.dataTableNBExtend[row].unit ="" ;
                $scope.dataTableNBExtend[row].breadQty ="" ;
                $scope.dataTableNBExtend[row].feeStandard ="" ;
                $scope.dataTableNBExtend[row].beyondChargeMethod = '';
                $scope.dataTableNBExtend[row].beyondFeeStandard = '';
                $scope.dataTableNBExtend[row].beyondUnit = '';

            } else{
                var values = lookUpData.filter(function (x) {
                    return x.lookupCode == value;
                });
                $scope.dataTableNBExtend[row].unit = typeof(values[0].description) == "undefined" ? "" : values[0].description;
                if( ! ("3" == value) ){  //按固定金额收取
                    $scope.dataTableNBExtend[row].beyondChargeMethod = '';
                    $scope.dataTableNBExtend[row].beyondFeeStandard = '';
                    $scope.dataTableNBExtend[row].beyondUnit = '';
                }
            }
            $scope.storeNumberAll = $scope.dataTableNBExtend.filter(function (x) {
                return x.chargeMethod && x.breadName == '0';
            });
            $scope.storeNumberNoAll = $scope.dataTableNBExtend.filter(function (x) {
                return x.chargeMethod && x.breadName != '0';
            });
            var dataTableNBExtendGetData2 = $scope.dataTableNBExtend.filter(function (x) {
                return x.chargeMethod ;
            });
            if (dataTableNBExtendGetData2.length == 0 && $scope.nbe.newPayment && $scope.nbe.newPayment == '2'){
                $scope.dataTableNBExtendNbFeeThod = true ;
            }else{
                $scope.dataTableNBExtendNbFeeThod = false ;
            }
            if('0' == $scope.dataTableNBExtend[row].breadName){
                for(var i = 0 ;i<$scope.dataTableNBExtend.length;i++){
                    if($scope.dataTableNBExtend[i].breadName != '0'){
                        $scope.dataTableNBExtend[i].feeStandard ='';
                        $scope.dataTableNBExtend[i].breadQty ='';
                    }
                }
            }else{
                for(var i = 0 ;i<$scope.dataTableNBExtend.length;i++){
                    if($scope.dataTableNBExtend[i].breadName == '0'){
                        $scope.dataTableNBExtend[i].feeStandard ='';
                        $scope.dataTableNBExtend[i].breadQty ='';
                    }
                }
            }

        };
        $scope.beyondChargeMethodChange = function (row,value,lookUpData){
            if ("" === value) {
                $scope.dataTableNBExtend[row].beyondUnit = "" ;
            }else{
                var values =  lookUpData.filter(function(x) {
                    return x.lookupCode == value ;
                });
                $scope.dataTableNBExtend[row].beyondUnit =  typeof(values[0].description) == "undefined" ?"":values[0].description ;
            }
        };

        //新品种  确认
        $scope.confirmNewbreedE = function (form) {
            if(!validateForm(form)){
                return;
            }
            $scope.saveNewbreedE(form,'2') ;


        };


        //新品种 取消确认
        $scope.CancelConfirmNewbreedE = function () {
            httpServer.post(URLAPI.saveTtaNBECancelComfirm,
                {params: JSON.stringify({
                        proposalId: $scope.nbe.proposalId,
                        newbreedExtendHId: $scope.nbe.newbreedExtendHId
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.btnNBE('N');
                        $scope.nbe.isNewConfirm = 'N';
                        $scope.searchNBExtent();
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
        };

        /**
         * 新品种宣传 按钮控制
         * @param value
         */
        $scope.btnNBE = function ( value){
        };
        /***************************查询新品种宣传服务费  end****************/

        /***************************查询问卷信息start****************/

        //问卷导出
        $scope.questionParams = {proposalId: getId()};
        $scope.btnExportQuestion = function () {
            var name = 'questionDataTable';
            var count = $scope.questionDataTable.count;
            tableXlsExport(name, $scope.questionParams, count);
        };

        $scope.saveData = {};
        $scope.hideSubmitBtnFlag=false;
        // 初始化保存数据的格式
        $scope.initData = function () {
            $scope.saveData = {
                // testQnHeadId: $scope.headList[0].testQnHeadId,
                // publishId:  $scope.headList[0].publishId,
                //qnType: $scope.headList[0].qnType,
                SurveyResultData:[]
            };
            angular.forEach($scope.lineList ,function (item) {
                $scope.saveData.SurveyResultData.push({
                    // testQnHeadId: $scope.headList[0].testQnHeadId,
                    // publishId:  $scope.headList[0].publishId,
                    qnLineId: item.qnLineId,
                    qnChoiceId:null,
                    qnChoiceResult:null
                })
            })
        };

        // 单选事件
        $scope.changeRadio = function (index,row) {
            //console.log(index + "" + JSON.stringify(row));
            $scope.saveData.SurveyResultData[index].qnChoiceId = row.qnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.qnChoice;
        };


        // 多选事件
        $scope.changeCheckbox = function (index,row) {
            //console.log(index + "" + JSON.stringify(row));
            var ids = '';
            var str = '';
            angular.forEach($scope.lineList[index].qnChoiceData,function (item) {
                if(item.itemValue){
                    ids = ids + item.qnChoiceId + ',';
                    str = str + item.qnChoice + ',';
                }
            });
            if(ids){
                ids = ids.substring(0,ids.length-1);
                str = str.substring(0,str.length-1);
            }
            $scope.saveData.SurveyResultData[index].qnChoiceId = ids;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = str;
        };

        // 下拉事件
        $scope.changeSelect = function (index,selectIndex) {
            // 根据数据下标和select选中的下标重新匹配数据
            var selectKey = $scope.lineList[index].itemValue;

            $scope.saveData.SurveyResultData[index].qnChoiceId = $scope.lineList[index].qnChoiceData[selectKey].qnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = $scope.lineList[index].qnChoiceData[selectKey].qnChoice;
        };

        // 文本事件
        $scope.changeText = function (index,row) {
            $scope.saveData.SurveyResultData[index].qnChoiceId = row.qnChoiceData[0].qnChoiceId;
            $scope.saveData.SurveyResultData[index].qnChoiceResult = row.itemValue;
        };

        $scope.searchQuestionnaire = function () {

            /* if ($scop.params.isTermsConfirm = 'Y') {
                 SIEJS.alert("TTA Terms是否确认", "error", "确定");
                 return;
             }*/
            httpServer.post(URLAPI.findSaafQuestionnaireByIdTest, {
                params: JSON.stringify({proposalId: getId()})
            }, function (res) {
                if (res.status == 'S') {
                    //先清空内容
                    $("#content").html('');
                    $scope.questionList= res.data.questionData;
                    $scope.qIsQuestConfirm = res.data.isQuestConfirm ;
                    $scope.questionReturnList = [] ;
                    $scope.questionReturnListSelect = [] ;
                    for(var i= 0;i<res.data.questionData.length;i++){

                        if("is_show_selection" == res.data.questionData[i].projectType){  //显示
                            //添加题目
                            var no = res.data.questionData[i].serialNumber; //序号
                            var question = res.data.questionData[i].projectCnTitle ; //问题
                            var questionHtml = '<div><P style="height: 33px; font-weight: bold" ><span>Q'+no+'：'+question+'</span></p>';

                            //添加选项
                            for (var j= 0;j<res.data.questionData[i].qnChoiceData.length;j++){
                                var dataLine = res.data.questionData[i].qnChoiceData[j] ;
                                var answerNo = dataLine.serialNumber ;
                                //var answerText = dataLine.ruleName;
                                //var answerHtml = '<P><span style="margin-left: 30px;">'+answerNo+'：'+answerText+'    结果:'+dataLine.autoCalcResult+'</span></p>';
                                var answerHtml = '';
                                if (dataLine.choiceCnContent) {
                                    answerHtml = '<P style="height: 31px" ><span style="margin-left: 30px;">'+answerNo+'：' + dataLine.choiceCnContent +'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计算结果为：<strong>'+dataLine.autoCalcResult+'</strong></span></p>';
                                } else {
                                    answerHtml = '<P style="height: 31px" ><span style="margin-left: 30px;">' + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计算结果为：<strong>' + dataLine.autoCalcResult+'</strong></span></p>';
                                }
                                questionHtml = questionHtml+answerHtml;
                            }
                            questionHtml = questionHtml+'</div>';
                            $("#content").append(questionHtml);
                        }else if('single_selection' == res.data.questionData[i].projectType){  //单选
                            //添加题目
                            var no = res.data.questionData[i].serialNumber; //序号
                            var question = res.data.questionData[i].projectCnTitle ; //问题
                            var start = '<div>' ;
                            var questionHtml = '<div style="width: 50%;float: left;"><P style="height: 33px; font-weight: bold" ><span>Q'+no+'：'+question+'</span></p>';
                            var questionRightStartHtml = '';
                            //添加表格
                            questionRightStartHtml  = '                                <div class="col-md-6" ng-nicescroll="" nice-option="{oneaxismousemode:true,railvalign: \'bottom\',cursorcolor:\'#1e90c2\'}" style="">\n' +
                                '                                    <div class="data-table">\n' +
                                '                                        <table class="table table-bordered table-hover table-condensed"\n' +
                                '                                               id="qnChoiceData_'+dataLine.qHeaderId+'"> <tbody>' ;


                            var questionRightEndHtml =   ' </tbody>\n' +
                                '       </table>\n' +
                                '   </div>\n' +
                                '</div>' ;
                            questionRightStartHtml =  questionRightStartHtml + '<tr class="text-center"></tr>';
                            //添加选项
                            //$("#content").append(questionHtml);
                            for (var j= 0;j<res.data.questionData[i].qnChoiceData.length;j++){
                                var dataLine = res.data.questionData[i].qnChoiceData[j] ;
                                var answerNo = dataLine.serialNumber ;
                                var answerText = dataLine.choiceCnContent;
                                var  dataLineCopy = angular.copy(dataLine);
                                $scope.questionReturnList.push(dataLineCopy);
                                var currentCheck =  (dataLine.autoCalcResult && dataLine.autoCalcResult =='Y')?'Y':'N';
                                if('Y' == currentCheck){
                                    res.data.questionData[i].ischeck = j;
                                }
                                var answerHtml = '<P style="height: 32px"><input type="radio" name="'+dataLine.qHeaderId+'" ng-checked="questionList['+i+'].qnChoiceData['+j+'].autoCalcResult==\'Y\'"  style="margin-left: 30px;" ' +
                                    'ng-click="questionClick(questionList['+i+'].qnChoiceData['+j+'],questionList['+i+'])">'+answerNo+'：'+answerText+'</p>';
                                questionHtml = questionHtml + answerHtml ;
                                //如果isShowChild : "Y"  直接展示
                                var cellStart = '<tr class="text-center"> ';
                                if("Y" == dataLine.isShowChild){
                                    for (var s= 0 ;s<dataLine.choiceChildList.length;s++){
                                        var childData = dataLine.choiceChildList[s] ;
                                        var childDataCopy = angular.copy(childData) ;
                                        childDataCopy.crrentParentId = dataLine.choiceLineId;
                                        $scope.questionReturnList.push(childDataCopy);
                                        var index = $scope.questionReturnList.indexOf(childDataCopy);
                                        if(childData.choiceCnContent){
                                            cellStart =  cellStart + '<td style="border: 0px;"   class="bg-gray-white" >'+childData.choiceCnContent+'</td>';
                                        }
                                        if("0" == childData.controlType){   //文本
                                            cellStart =  cellStart + '<td ng-show="true"><input  name ="' + childData.choiceLineId + '"  style="width: 100px;" ng-required="true" class="form-control input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        }else if("1" == childData.controlType){  //日期 data-stratdate="{{nowDateParam}}"
                                            cellStart =  cellStart + '<td ng-show="true"><input  name ="' + childData.choiceLineId + '"  style="width: 100px;"  ng-required="true" date-time-picker    data-date-format="yyyy-mm"\n' +
                                                'data-start-view="3" data-min-view="3" data-max-view="3" class="form-control radius3 input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        } else if("2" == childData.controlType){  //单选
                                            cellStart =  cellStart + '<td ng-show="true"><div class="input-group input-group-xs">'+
                                                '<div  name ="' + childData.choiceLineId + '"   lookup-code="'+childData.lookupType+'"  ng-required="true" ng-model="'+'questionReturnList['+index+'].autoCalcResult"></div></div></td>';
                                        } else if("3" == childData.controlType){  //多选

                                            $scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId]  =  $scope.lookupCodeParty.filter(function (x) {
                                                return x.lookupType == childData.lookupType ;
                                            });

                                            cellStart =  cellStart + '<td  ng-show="true">'+
                                                '<div class ="input-group input-group-xs" style="width: 100%">' +
                                                '<select  name ="' + childData.choiceLineId + '"  multiple="multiple"  ng-required="true" class     ="form-control radius3"   ng-model  ="'+'questionReturnList['+index+'].autoCalcResult" id="id_qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'">' ;
                                            for(var k = 0 ;k<$scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId].length;k++){
                                                if( childDataCopy.autoCalcResult && childDataCopy.autoCalcResult.indexOf($scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId][k].meaning) >-1){
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'['+k+'].meaning"  selected=""' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }else{
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'['+k+'].meaning"  ' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }


                                            }

                                            cellStart =  cellStart +
                                                '</select>' +
                                                '</div></td>';
                                            $scope.questionReturnListSelect.push("id_qnChoiceData_"+dataLine.qHeaderId +"_"+dataLine.choiceLineId);

                                        }
                                    }
                                }else{
                                    for (var s= 0 ;s<dataLine.choiceChildList.length;s++){
                                        var childData = dataLine.choiceChildList[s] ;
                                        var childDataCopy = angular.copy(childData) ;
                                        childDataCopy.crrentParentId = dataLine.choiceLineId;
                                        $scope.questionReturnList.push(childDataCopy);
                                        var index = $scope.questionReturnList.indexOf(childDataCopy);
                                        if('Y' == currentCheck){
                                            var  param = 'qnChoiceData_'+dataLine.qHeaderId ;
                                            $scope[param] = dataLine.choiceLineId ;
                                        }
                                        if(childData.choiceCnContent){
                                            cellStart =  cellStart + '<td class="bg-gray-white" ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'">'+childData.choiceCnContent+'</td>';
                                        }
                                        if("0" == childData.controlType){   //文本
                                            cellStart =  cellStart + '<td ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'"><input  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'" style="width: 100px;" class="form-control input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        }else if("1" == childData.controlType){  //日期 data-stratdate="{{nowDateParam}}"
                                            cellStart =  cellStart + '<td ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'"><input  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'" style="width: 100px;"  date-time-picker  data-date-format="yyyy-mm"\n' +
                                                'data-start-view="3" data-min-view="3" data-max-view="3" class="form-control radius3 input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        } else if("2" == childData.controlType){  //单选
                                            cellStart =  cellStart + '<td ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'"><div class="input-group input-group-xs">'+
                                                '<div  name ="' + childData.choiceLineId + '"   ng-required="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'" lookup-code="'+childData.lookupType+'"  ng-model="'+'questionReturnList['+index+'].autoCalcResult"></div></div></td>';
                                        } else if("3" == childData.controlType){  //多选

                                            $scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId]  =  $scope.lookupCodeParty.filter(function (x) {
                                                return x.lookupType == childData.lookupType ;
                                            });

                                            cellStart =  cellStart + '<td  ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'">'+
                                                '<div class ="input-group input-group-xs" style="width: 100%">' +
                                                '<select  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'" multiple="multiple" class     ="form-control radius3"   ng-model  ="'+'questionReturnList['+index+'].autoCalcResult" id="id_qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'">' ;
                                            for(var k = 0 ;k<$scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId].length;k++){
                                                if( childDataCopy.autoCalcResult && childDataCopy.autoCalcResult.indexOf($scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId][k].meaning) >-1){
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning"  selected=""' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }else{
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning"  ' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }


                                            }

                                            cellStart =  cellStart +
                                                '</select>' +
                                                '</div></td>';
                                            $scope.questionReturnListSelect.push("id_qnChoiceData_"+dataLine.qHeaderId +"_"+dataLine.choiceLineId+'_'+childData.choiceLineId);

                                        }

                                        /*                                        cellStart =  cellStart + '<td ng-show="qnChoiceData_'+dataLine.qHeaderId+'=='+dataLine.choiceLineId+'"><input style="width: 100px;" class="form-control input-xs" \n' +
                                                                                    'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';*/
                                    }
                                }
                                var cellEnd = '</tr>';
                                cellStart = cellStart +  cellEnd ;
                                questionRightStartHtml  = questionRightStartHtml +  cellStart ;
                            }
                            questionRightStartHtml  = questionRightStartHtml +  questionRightEndHtml ;
                            questionHtml  =  questionHtml  + '</div>';

                            start = start + questionHtml ;
                            start =  start +questionRightStartHtml ;

                            start  =  start  + '</div>';
                            var compiles = $compile(start)($scope);
                            $("#content").append(compiles);

                        }else if('multi_selection' == res.data.questionData[i].projectType){  //多选
                            //添加题目
                            var no = res.data.questionData[i].serialNumber; //序号
                            var question = res.data.questionData[i].projectCnTitle ; //问题
                            var start = '<div>' ;
                            var questionHtml = '<div style="width: 50%;float: left;"><P style="height: 34px; font-weight: bold"><span>Q'+no+'：'+question+'</span></p>';
                            var questionRightStartHtml = '';
                            //添加表格
                            questionRightStartHtml  = '                                <div class="col-md-6" ng-nicescroll="" nice-option="{oneaxismousemode:true,railvalign: \'bottom\',cursorcolor:\'#1e90c2\'}" style="">\n' +
                                '                                    <div class="data-table">\n' +
                                '                                        <table style="border: 0px solid #ddd;" class="table table-bordered table-hover table-condensed"\n' +
                                '                                               id="qnChoiceData'+dataLine.qHeaderId+'"> <tbody>' ;


                            var questionRightEndHtml =   ' </tbody>\n' +
                                '       </table>\n' +
                                '   </div>\n' +
                                '</div>' ;
                            //添加选项
                            questionRightStartHtml =  questionRightStartHtml + '<tr class="text-center"></tr>';
                            for (var j= 0;j<res.data.questionData[i].qnChoiceData.length;j++){
                                var dataLine = res.data.questionData[i].qnChoiceData[j] ;
                                var answerNo = dataLine.serialNumber ;
                                var answerText = dataLine.choiceCnContent;
                                var  dataLineCopy = angular.copy(dataLine);
                                $scope.questionReturnList.push(dataLineCopy);
                                var index = $scope.questionReturnList.indexOf(dataLineCopy);
                                var currentCheck =  ( dataLine.autoCalcResult && dataLine.autoCalcResult =='Y' || (dataLine.isShowChild == 'Y' ? true : false) ) ? 'Y' : 'N';
                                var answerHtml = '<P style="height: 32px;" ><input ng-disabled="\''+ dataLine.isShowChild + '\'==\'Y\'" type="checkbox" ng-checked="\''+currentCheck+'\'==\'Y\'"  ng-click="questionBoxClick(questionList['+i+'].qnChoiceData['+j+'],questionReturnList['+index+'])" id="choiceLineLeftId_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'"' +
                                    'name="'+dataLine.qHeaderId+'" style="margin-left: 30px;">'+answerNo+'：'+answerText+'</p>';
                                questionHtml  = questionHtml + answerHtml ;
                                //如果isShowChild : "Y"  直接展示
                                var cellStart = '<tr class="text-center" id="choiceLineId_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'"> ';
                                if("Y" == dataLine.isShowChild){
                                    for (var s= 0 ;s<dataLine.choiceChildList.length;s++){
                                        var childData = dataLine.choiceChildList[s] ;
                                        var childDataCopy = angular.copy(childData);
                                        childDataCopy.crrentParentId = dataLine.choiceLineId;
                                        $scope.questionReturnList.push(childDataCopy);
                                        var index = $scope.questionReturnList.indexOf(childDataCopy);
                                        if(childData.choiceCnContent){
                                            cellStart =  cellStart + '<td style="border: 0px"  class="bg-gray-white" ng-show="true">'+childData.choiceCnContent+'</td>';
                                        }
                                        if("0" == childData.controlType){   //文本
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="true"><input  name ="' + childData.choiceLineId + '"  ng-required="true" style="width: 100px;" class="form-control input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        }else if("1" == childData.controlType){  //日期 data-stratdate="{{nowDateParam}}"
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="true"><input  name ="' + childData.choiceLineId + '"  ng-required="true"  style="width: 100px;" date-time-picker  data-date-format="yyyy-mm"\n' +
                                                'data-start-view="3" data-min-view="3" data-max-view="3" class="form-control radius3 input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        } else if("2" == childData.controlType){  //单选
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="true"><div class="input-group input-group-xs">'+
                                                '<div  name ="' + childData.choiceLineId + '"  ng-required="true" lookup-code="'+childData.lookupType+'"  ng-model="'+'questionReturnList['+index+'].autoCalcResult"></div></div></td>';
                                        } else if("3" == childData.controlType){  //多选

                                            $scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId]  =  $scope.lookupCodeParty.filter(function (x) {
                                                return x.lookupType == childData.lookupType ;
                                            });

                                            cellStart =  cellStart + '<td  style="border: 0px"   ng-show="true">'+
                                                '<div class ="input-group input-group-xs" style="width: 100%">' +
                                                '<select  name ="' + childData.choiceLineId + '"  ng-required="true" multiple="multiple" class     ="form-control radius3"   ng-model  ="'+'questionReturnList['+index+'].autoCalcResult" id="id_qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'">' ;
                                            for(var k = 0 ;k<$scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId].length;k++){
                                                if( childDataCopy.autoCalcResult && childDataCopy.autoCalcResult.indexOf($scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId][k].meaning) >-1){
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning"  selected=""' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }else{
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'['+k+'].meaning"  ' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }

                                            }

                                            cellStart =  cellStart +
                                                '</select>' +
                                                '</div></td>';
                                            $scope.questionReturnListSelect.push("id_qnChoiceData_"+dataLine.qHeaderId +"_"+dataLine.choiceLineId+'_'+childData.choiceLineId);

                                        }
                                    }
                                }else{
                                    for (var s= 0 ;s<dataLine.choiceChildList.length;s++){
                                        var childData = dataLine.choiceChildList[s] ;
                                        var childDataCopy = angular.copy(childData);
                                        childDataCopy.crrentParentId = dataLine.choiceLineId;
                                        $scope.questionReturnList.push(childDataCopy);
                                        var index = $scope.questionReturnList.indexOf(childDataCopy);
                                        if('Y' == currentCheck){
                                            var  param = 'qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId ;
                                            $scope[param] = true ;
                                        }
                                        if(childData.choiceCnContent){
                                            cellStart =  cellStart + '<td style="border: 0px" class="bg-gray-white" ng-show="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'">'+childData.choiceCnContent+'</td>';
                                        }
                                        if("0" == childData.controlType){   //文本
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'"><input  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'" style="width: 100px;" class="form-control input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        }else if("1" == childData.controlType){  //日期  data-stratdate="{{nowDateParam}}"
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'"><input  name ="' + childData.choiceLineId + '"   ng-required="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'" style="width: 100px;"  date-time-picker  data-date-format="yyyy-mm"\n' +
                                                'data-start-view="3" data-min-view="3" data-max-view="3" class="form-control radius3 input-xs" \n' +
                                                'ng-model="'+'questionReturnList['+index+'].autoCalcResult"></td>';
                                        } else if("2" == childData.controlType){  //单选
                                            cellStart =  cellStart + '<td style="border: 0px"  ng-show="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'"><div class="input-group input-group-xs">'+
                                                '<div  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'" lookup-code="'+childData.lookupType+'"  ng-model="'+'questionReturnList['+index+'].autoCalcResult"></div></div></td>';
                                        } else if("3" == childData.controlType){  //多选

                                            $scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId]  =  $scope.lookupCodeParty.filter(function (x) {
                                                return x.lookupType == childData.lookupType ;
                                            });

                                            cellStart =  cellStart + '<td  style="border: 0px"   ng-show="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'">'+
                                                '<div class ="input-group input-group-xs" style="width: 100%">' +
                                                '<select  name ="' + childData.choiceLineId + '"  ng-required="qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'" multiple="multiple" class     ="form-control radius3"   ng-model  ="'+'questionReturnList['+index+'].autoCalcResult" id="id_qnChoiceData_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'">' ;
                                            for(var k = 0 ;k<$scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId].length;k++){
                                                if( childDataCopy.autoCalcResult && childDataCopy.autoCalcResult.indexOf($scope['qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId][k].meaning) >-1){
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning"  selected=""' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }else{
                                                    cellStart =  cellStart + '<option ng-bind="qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning"  ' +
                                                        'value="{{qnChoiceData_select_'+dataLine.qHeaderId+'_'+dataLine.choiceLineId+'_'+childData.choiceLineId+'['+k+'].meaning}}"></option>' ;
                                                }

                                            }

                                            cellStart =  cellStart +
                                                '</select>' +
                                                '</div></td>';
                                            $scope.questionReturnListSelect.push("id_qnChoiceData_"+dataLine.qHeaderId +"_"+dataLine.choiceLineId+'_'+childData.choiceLineId);

                                        }



                                    }
                                }



                                var cellEnd = '</tr>';
                                cellStart = cellStart +  cellEnd ;
                                questionRightStartHtml  = questionRightStartHtml +  cellStart ;
                            }
                            questionRightStartHtml  = questionRightStartHtml +  questionRightEndHtml ;
                            questionHtml  =  questionHtml  + '</div>';

                            start = start + questionHtml ;
                            start =  start +questionRightStartHtml ;

                            start  =  start  + '</div>';
                            var compiles = $compile(start)($scope);
                            $("#content").append(compiles);
                            $timeout(function () {
                                for (var v= 0;v<$scope.questionReturnListSelect.length;v++){
                                    jQuery("#"+$scope.questionReturnListSelect[v]).fSelect();
                                }
                            },0);

                        }
                    }
                    $scope.initData();
                    //用于判断表单是否变更
                    $scope.startLineList = JSON.stringify($scope.lineList);
                } else {
                    SIEJS.alert("加载答卷失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
                console.log("searchQuestionnaire error:" + err)
            });
        };
        //
        $scope.questionClick = function (value,parentValeu){
            var  param = 'qnChoiceData_'+value.qHeaderId ;
            if("undefined" != String(parentValeu.ischeck)){
                parentValeu.qnChoiceData[parentValeu.ischeck].autoCalcResult ='N';
                var old =  $scope.questionReturnList.filter(function(x) {
                    return x.choiceLineId == parentValeu.qnChoiceData[parentValeu.ischeck].choiceLineId ;
                });
                old[0].autoCalcResult = 'N';
            }
            $scope[param] = value.choiceLineId;
            value.autoCalcResult ='Y' ;
            var crruent =  $scope.questionReturnList.filter(function(x) {
                return x.choiceLineId == value.choiceLineId ;
            });
            crruent[0].autoCalcResult = 'Y';
            parentValeu.ischeck = parentValeu.qnChoiceData.indexOf(value) ;
        };
        $scope.questionBoxClick = function (value,returnValue){
            //多选控制
            var  param = 'choiceLineLeftId_'+value.qHeaderId+'_'+value.choiceLineId;
            var  id = "#" + param ;
            var flag = $(id).is(':checked');
            var right = 'qnChoiceData_' + value.qHeaderId+'_'+value.choiceLineId;
            $scope[right] = flag;
            if(returnValue){
                returnValue.autoCalcResult = flag?'Y':'N';
            }

        };

        $scope.saveQuestionnaire = function (value,form) {
            if(2 == value){
                if(!validateForm(form)){
                    return;
                }
            }
            //$scope.saveBatchQuestion();

            //问卷新供应商列表及附件上传参数拼装
            if ($scope.questionDataTable.length > 0) {
                angular.forEach($scope.questionDataTable, function (key, value) {
                    $scope.questionDataTable[value].proposalId = getId();
                })
            }

            var array = ["2","3","5"];
            for(var i = 0 ;i<$scope.questionReturnList.length;i++){
                if($scope.questionReturnList[i].crrentParentId){
                    if(-1 != array.indexOf($scope.questionReturnList[i].choiceType)){
                        var parents =  $scope.questionReturnList.filter(function(x) {
                            return x.choiceLineId == $scope.questionReturnList[i].crrentParentId ;
                        });
                        var isShowChildV = parents[0].isShowChild;
                        if( (!parents[0].autoCalcResult  || 'N' == parents[0].autoCalcResult ) && 'N' == isShowChildV){
                            $scope.questionReturnList[i].autoCalcResult = '';
                        }
                    }
                }
            }
            httpServer.post(URLAPI.submitQuestionTest, {
                'params': JSON.stringify(
                    {
                        proposalId:getId(),
                        status:value,
                        choiceList:$scope.questionReturnList,
                        lineArr: $scope.questionDataTable
                    }
                )
            }, function (res) {
                if (res.status == 'S') {
                    $scope.searchQuestionDataTable(getId());
                    if(2 == value){
                        $scope.qIsQuestConfirm = 'Y';
                    }
                    SIEJS.alert("成功!", "success", "确定");
                }else{
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });
        };

        $scope.CancelQuestionnaire = function () {
            SIEJS.confirm('取消确认', '确认取消后答卷信息将被重置并会重新加载（附件信息及产品清单除外）？', '确定', function () {
                httpServer.post(URLAPI.cancelQuestionTest, {
                    'params': JSON.stringify(
                        {
                            proposalId: getId(),
                        }
                    )
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.qIsQuestConfirm = 'N';
                        $scope.searchQuestionnaire();
                        $scope.searchQuestionDataTable(getId());
                        $scope.fileDataTable.search(1);
                        SIEJS.alert("取消确认!", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                });
            });
        };

        $scope.submitSurvey=function(){
            //console.log("SurveyResultData: " + JSON.stringify($scope.saveData));
            var flag = true;
            angular.forEach($scope.saveData.SurveyResultData,function (item,index) {
                //console.log(JSON.stringify(item.qnChoiceId));
                if(!item.qnChoiceId&&$scope.lineList[index].requireFlag == 'Y'&&$scope.lineList[index].displayFlag == 'Y'){
                    flag = false;
                }
            });
            if(!flag){
                SIEJS.alert("必填项不能空!", "error", "确定");
                return;
            }

            httpServer.post(URLAPI.saveSaafQuestionnaireResult, {
                'params': JSON.stringify($scope.saveData)
            }, function (res) {
                if (res.status == 'S') {
                    if (res.status == 'S') {
                        SIEJS.alert("提交问卷成功!", "success", "确定");
                        $scope.hideSubmitBtnFlag=true;
                    }else{
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }else{
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });
        };


        $scope.termsComparision = function () {
            httpServer.post(URLAPI.findTermsComparision, {
                params: JSON.stringify({
                    proposalId: getId(),
                    year: $scope.params.proposalYear,
                    vendorNbr: $scope.params.vendorNbr
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.termsComparisionPartList1 = res.data.partList1;
                    $scope.termsComparisionPartList2 = res.data.partList2;
                    $scope.termsComparisionPartList3 = res.data.partList3;
                    $timeout(function () {
                        jQuery( "#termsComparision tr" ).resizable();
                        jQuery( "#termsComparision th" ).resizable();
                        jQuery( "#termsComparision td" ).resizable();
                        $scope.setTtaContractLine("printContentId");
                    },200);
                } else {
                    SIEJS.alert("加载TTA Terms Comparision(TY vs LY)失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
                //console.log("加载TTA Terms Comparision(TY vs LY)失败 error:" + err)
            });
            $scope.getPrintCount('TTA_TERMS_COMPARISION');
        };

        //查找terms头部信息
        $scope.queryTradeTermsHeader = function() {
            httpServer.post(URLAPI.termsHFindById, {
                    'params': JSON.stringify({proposalId: getId()})
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.contractParams = res.data;
                        httpServer.post(URLAPI.findTtaShopMarketPagination, {
                            'params': JSON.stringify({flag: 'warehouse', pageIndex: 1, pageRows: 9999})
                        },function (res) {
                            if (res.status == 'S') {
                                $("#warehouseId").html('');
                                var html  = "";
                                var warehouseArr = res.data;
                                var varWarehouseDesc = $scope.contractParams.warehouseDesc;
                                for (var idx = 0; idx < warehouseArr.length; idx ++) {
                                    var warehouse = warehouseArr[idx].warehouseName;
                                    if (varWarehouseDesc && varWarehouseDesc.indexOf(warehouse) != -1) {
                                        html += "<ul style='display:inline-block;margin-left: 5px; padding:0;marign:0;'><li class='wrap'><span style='display: inline-block;background-color: #333 !important;width: 8px;height: 8px;margin-bottom: 1px'></span> " + warehouse + "</li></ul>";
                                    } else {
                                        html += "<ul style='display:inline-block;margin-left: 5px; padding:0;marign:0;'><li class='wrap'><span style='display: inline-block;border: 1px solid #333;width: 8px;height: 8px;margin-bottom: 1px'></span> " + warehouse + "</li></ul>";
                                    }
                                }
                                $("#warehouseId").append(html);
                            }
                        });

                        httpServer.post(URLAPI.findTtaShopMarketPagination, {
                            'params': JSON.stringify({flag: 'area', pageIndex: 1, pageRows: 9999})
                        }, function (res) {
                            if (res.status == 'S') {
                                $("#areaId").html('');
                                var html  = "";
                                //var tempArr =  $scope.contractParams.salesArea.split(",");
                                var areaArr = res.data;
                                var varSalesArea = $scope.contractParams.salesArea;
                                var areaFlag = varSalesArea ? varSalesArea.indexOf("全国") != -1 : false;
                                for (var idx = 0; idx < areaArr.length; idx ++) {
                                    var areaName = areaArr[idx].areaName;
                                    if (varSalesArea && varSalesArea.indexOf(areaName) != -1 || areaFlag) {
                                        html += "<ul style='display:inline-block;width:47%; padding:0;margin:0; text-align: center;' ><li  class='wrap' style='margin-left: 5px;'><span style='display: inline-block;background-color: #333 !important;width: 8px;height: 8px;margin-bottom: 1px'></span>" + areaName + "</li></ul>";
                                    } else {
                                        html += "<ul style='display:inline-block;width:47%; padding:0;margin:0; text-align: center;' ><li  class='wrap' style='margin-left: 5px;'><span style='display: inline-block;border: 1px solid #333;width: 8px;height: 8px;margin-bottom: 1px'></span>" + areaName + "</li></ul>";
                                    }
                                }
                                $("#areaId").append(html);
                            }
                        });
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //贸易条款
        $scope.tradeClause = function () {
            $scope.getPrintCount('TTA_TRADE_TERM');
            //贸易条款头部信息
            $scope.queryTradeTermsHeader();
            httpServer.post(URLAPI.findTermsAgrement, {
                params: JSON.stringify({
                    proposalId: getId(),
                    year: $scope.params.proposalYear,
                })
            }, function (res) {
                if (res.status == 'S') {
                    var tradeYear = res.data.tradeYear;
                    var saleWayArr = res.data.saleWayList; //销售方式 $scope.saleWayList
                    var proposalYearRemarkData = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == "TERMS_TARGET" && x.lookupCode == $scope.params.proposalYear;
                    });
                    if(proposalYearRemarkData.length > 0) {
                        $scope.proposalNewYearRemark = proposalYearRemarkData[0].meaning;
                    }else{
                        $scope.proposalNewYearRemark = '注：对于同一产品品牌或数个产品品牌同属于一个公司或数个关联公司或同一集团而产品在不同销售区由不同经销商代理的情况，目标退佣按当年度屈臣氏向全国全部销售区域的各供应商采购的（折扣前）采购额总额确定对应的佣金比例，并分别按屈臣氏向各供应商采购的（折扣前）采购额确定该供应商的目标退佣金额。目标退佣的含税金额 = （不含税采购额 x 退佣比例）x （1 + 法定适用税率）';
                    }

                    //条款备注
                    var termRemarkList = $scope.lookupCodeParty.filter(function (x) {
                        if ($scope.contractParams.invoiceType == 'A') {
                            return x.lookupType == "TRAD_TERMS_CONTENT" && x.lookupCode == ($scope.params.proposalYear);
                        } else {
                            return x.lookupType == "TRAD_TERMS_CONTENT" && x.lookupCode == ($scope.params.proposalYear + $scope.contractParams.agreementEdition);
                        }
                    });
                    if(termRemarkList.length > 0) {
                        $scope.termRemark = termRemarkList[0].meaning;
                    }else{
                        $scope.termRemark = '注:上述采购额，如未特别注明，为（折扣前）含税采购额。（折扣前）采购额是指按（折扣前）原价计算的采购金额，"（折扣前）原价是指扣除供应商特别给予屈臣氏的折扣前原价，折扣是指本《贸易条款》"上列明的折扣折让项目。';
                    }

                    var termCompanyList = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == "PARTY_A_COMPANY";
                    });
                    if(termCompanyList.length > 0) {
                        $scope.companyName = termCompanyList[0].meaning;
                    }
                    $("#divSaleWay").html("");
                    if ($scope.saleWayList) {
                        var divSaleWayHtml = "";
                        for (var idx = 0; idx < saleWayArr.length; idx ++) {
                            if (saleWayArr[idx].lookupCode == $scope.params.saleType) {
                                divSaleWayHtml += "<div style='width: 33%'><div class=\"input-group input-group-xs\"><p ng-class=\"spanBold\" style='margin-right: 20px'> <span style='display: inline-block;background-color: #333 !important;width: 8px;height: 8px;margin-bottom: 1px'></span> <strong>" + saleWayArr[idx].meaning + "</strong></p></div></div>";
                            } else {
                                divSaleWayHtml += "<div style='width: 33%;'><div class=\"input-group input-group-xs\"><p ng-class=\"spanBold\" style='margin-right: 20px'> <span style='display: inline-block;border: 1px solid #333 ;width: 8px;height: 8px;margin-bottom: 1px'></span> <strong>" + saleWayArr[idx].meaning + "</strong></p></div></div>";
                            }
                        }
                        $("#divSaleWay").html(divSaleWayHtml);
                    }

                    var  resultList = res.data.resultList;
                    if (!res.data || !res.data.resultList) {
                        SIEJS.alert('查询没有数据', "error", "确定");
                        return;
                    }
                    //console.log("贸易条款:" + JSON.stringify(resultList));
                    $("#tableId").html("");
                    $("#part2Table").html("");
                    var htmlStr = "";
                    for (var idx = 0; idx < resultList.length; idx ++) {
                        var listData = resultList[idx];
                        if (idx == 0) {
                            htmlStr += "<tr style=\"width: 400px;background-color: #F6A32D !important;\"><th style=\"width: 400px\">TRANDING TERMS 贸易条款</th><th style=\"width: 400px\">" + tradeYear + "</th></tr><tr><td colspan=\"2\" style=\"background: gold !important;\">" + listData.meaning + "</td></tr>";
                            var dataList = listData.reportList;
                            for (var i = 0; i < dataList.length; i++) {
                                var obj = dataList[i];
                                htmlStr += "<tr><td>" + obj.termsEn + obj.termsCn+ "</td><td style= \"text-align: center;\">"+ (obj.termsSystem ? obj.termsSystem: "" ) + "</td></tr>";
                            }
                            var tableIdContent = $compile(htmlStr)($scope);
                            $("#tableId").append(tableIdContent);
                        } else if (idx == 1) {
                            $scope.listData = resultList[idx];
                            htmlStr = "<tr><td colspan='2' style=\"background: gold !important;\">" + listData.meaning  + "</td></tr>";
                            var tdStr = "<tr>\n" +
                                "    <td colspan=\"2\"><table style=\"width: 70%;text-align: center;margin: 0 auto;\" class=\"table-bordered table-hover table-condensed\" id=\"part2Table\">\n" +
                                "            <tr><th rowspan='2'>目标退佣</th><th colspan='" + listData.headList.length  +"'>" + tradeYear + "</th></tr>\n" +
                                "            <tr><th ng-repeat='rowHead in listData.headList'>{{rowHead}}</th></tr></table>\n" + "<div style='margin: 10px 0;'>{{ proposalNewYearRemark}}</div>";
                            "    </td>\n" +
                            "</tr>";
                            var compiles4 = $compile(htmlStr + tdStr)($scope);
                            $("#tableId").append(compiles4);
                            htmlStr = "";
                            var trStr = "";//非目标退佣
                            var headBodyList = $scope.listData.headBodyList;
                            for (var rowIdx = 0; rowIdx < headBodyList.length; rowIdx ++) {
                                htmlStr += "<tr>";
                                var colData = headBodyList[rowIdx];
                                var tdStr = "";
                                for (var colIdx = 0; colIdx < colData.length; colIdx ++) {
                                    tdStr += "<td>" + (colData[colIdx] == '-999' ? "" : colData[colIdx])+ "</td>";
                                }
                                htmlStr += tdStr + "</tr>";
                            }
                            //第二部分免费货品
                            var part2BodyList = $scope.listData.reportList;
                            for (var idx = 0; idx < part2BodyList.length; idx ++) {
                                trStr += "<tr><td>" + part2BodyList[idx].termsEn + part2BodyList[idx].termsCn + "</td><td>" + (part2BodyList[idx].termsSystem ? part2BodyList[idx].termsSystem: "" ) + "</td></tr>";
                            }
                            $("#part2Table").append(htmlStr);
                            $("#tableId").append(trStr);
                        } else {
                            //第三部分数据
                            $("#part3Table").html('');
                            var listThreeData = resultList[idx];
                            var threeReportList = listThreeData.reportList;
                            htmlStr = "";
                            htmlStr += "<tr style=\"background: #F6A32D !important;\"><th style=\"width: 400px\">TRANDING TERMS 贸易条款</th><th style=\"width: 400px\">" + tradeYear + "</th></tr><tr><td colspan=\"2\" style=\"background: gold !important;\">" + listThreeData.meaning + "</td></tr>";
                            for (var i = 0; i < threeReportList.length; i++) {
                                var jsonObj = threeReportList[i];
                                htmlStr += "<tr><td>" + jsonObj.termsEn +"<br/>"+ jsonObj.termsCn+ "</td><td  style='text-align: center;'>"+ (jsonObj.termsSystem ? jsonObj.termsSystem: "" ) + "</td></tr>";
                            }
                            $("#part3Table").append(htmlStr);
                        }
                        /* $timeout(function () {
                             jQuery( "tr" ).resizable();
                             jQuery( "th" ).resizable();
                             jQuery( "td" ).resizable();
                            // $scope.setTtaContractLine("printContentId");
                         },200);*/
                    }
                } else {
                    SIEJS.alert("加载贸易条款失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
                //console.log("加载贸易条款失败 error:" + err)
            });
        };

        /*************************** 问卷表格数据 start ****************/

        //增加按钮
        $scope.btnNew = function () {
            if ($scope.questionDataTable.length == 120) {
                SIEJS.alert("不能超过120条记录", "error", "确定");
                return;
            }
            var rowObj = {
                proposalId: getId(),
                serialNumber : $scope.questionDataTable.selectRow,
                mapDetailId: null,
                skuDesc: null,
                cost: null,
                retailPrice: null,
                normalGp: null,
                promoPrice: null,
                promoGp: null
            };
            $scope.questionDataTable.push(rowObj);
        };

        //问卷上图新品行记录
        $scope.btnDel = function () {
            var index = $scope.questionDataTable.selectRow;
            var mapDetailId = $scope.questionDataTable[index].mapDetailId;
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if (mapDetailId == null || mapDetailId == "") {
                    $scope.questionDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                    $scope.$apply();
                } else {
                    //修改列有ID删除该行
                    httpServer.post(URLAPI.deleteQuestionNewMapDetail, {
                        'params': JSON.stringify({
                            mapDetailId: mapDetailId
                        })
                    }, function (res) {
                        if (res.status == 'S') {
                            $scope.searchQuestionDataTable(getId());
                            SIEJS.alert("删除成功", "success", "确定");
                            $scope.$apply();
                        } else
                            SIEJS.alert(res.msg, "error", "确定");
                    }, function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    });
                    $scope.$apply();
                }
            })
        };

        $scope.saveBatchQuestion = function () {
            if ($scope.questionDataTable.length > 0) {
                angular.forEach($scope.questionDataTable, function (key, value) {
                    $scope.questionDataTable[value].proposalId = getId();
                })
            }

            httpServer.post(URLAPI.saveOrUpadateBatchDetail, {
                'params': JSON.stringify({lineArr: $scope.questionDataTable, proposalId: getId()})
            }, function (res) {
                if (res.status == 'S') {
                    $scope.questionDataTable = res.data;
                    SIEJS.alert("保存成功", "success", "确定");
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
        };

        $scope.searchQuestionDataTable = function (proposalId) {
            httpServer.post(URLAPI.queryQuestionNewMapDetailList,
                {'params': JSON.stringify({proposalId: parseInt(proposalId)})} ,
                function (res) {
                    if (res.status == 'S') {
                        $scope.questionDataTable = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        /*************************** 问卷表格数据 end ****************/


        /*************************** 问卷图片上传 end ****************/
        $scope.fileDataParams = {businessId: getId(), functionId: "tta_proposal_header"};
        //上传附件
        $scope.uploadFile = function () {
            var f = $scope.myFile;
            var fd = new FormData();
            var file = document.querySelector('input[name=questionFile]').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            var fileName = file.name;
            var sideAgrtHId = getId();
            if (sideAgrtHId == undefined || sideAgrtHId == null){
                SIEJS.alert("头信息为空,上传失败", 'error', '确定');
            }

            fd.append('sideAgrtHId', sideAgrtHId);
            fd.append('file', file);
            fd.append("fileName", fileName);
            fd.append("functionId", "tta_proposal_header");
            $http.post(URLAPI.ttaSideAgrtHeaderUpload, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                if (response.status == 'S') {
                    SIEJS.alert(response.msg, 'success', '确定');
                    $scope.fileDataTable.search(1);
                } else {
                    SIEJS.alert(response.msg, 'error', '确定');
                }
            }).error(function(response) {
                SIEJS.alert("上传失败", 'error', '确定');
            });
        };
        //下载附件事件
        $scope.downFileEvent = function (m) {
            //console.log(msg);
            if (m.fileId == undefined || m.fileId == null){
                SIEJS.alert("当前没有选中一行数据,不能下载",'warning','确定');
                return;
            }
            var url = URLAPI.ttaSideAgrtHeaderDownLoad + '?fileId=' + m.fileId;
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };

        //删除附件
        $scope.delUploadFile = function () {
            var item = $scope.fileDataTable.selectRow;
            SIEJS.confirm('提示', '确定要删除所选的信息吗？', '确定', function () {
                var p = {
                    fileId: item.fileId //文件id
                };
                httpServer.post(URLAPI.ttaSideAgrtHeaderFileDelete, {
                    'params': JSON.stringify(p)
                }, function (res) {
                    if (res.status == 'S') {

                        $scope.fileDataTable.search(1);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert("删除失败! " + err.msg, "error", "确定");
                });

            });
        };

        /*************************** 问卷图片上传end ****************/



        /***************************查询问卷信息end****************/

        /***************************TT Summary start****************/
        $scope.ttaContractLine = function (){

            httpServer.post(URLAPI.contractLineFindTtaSummaryById, {
                'params': JSON.stringify({'proposalId':getId()})
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ttaContractProposalYear = res.data.length>0?res.data[0].proposalYear:'xxxxx';
                    $scope.ttaContractLineTable = res.data ;
                    //用于判断表单是否变更
                    $scope.startTtaContractLineTable = JSON.stringify($scope.ttaContractLineTable);
                    $timeout(function () {
                        jQuery( "#ttaContractLineTableId").colResizable({
                            liveDrag:false,
                            gripInnerHtml:"<div class='grip' style='width:20px;'"+
                                'height:15px;'+
                                'margin-top:-3px;'+
                                'margin-left:-5px;'+
                                'position:relative;'+
                                'z-index:88;'+
                                " 'cursor:e-resize;'></div>",
                            draggingClass:"dragging",
                            resizeMode:'overflow',
                        });
                        $scope.setTtaContractLine("ttaContractLineScroll");
                        // jQuery( "#ttaContractLineShow td" ).resizable();
                        // jQuery( "#ttaContractLineShow th" ).resizable();
                    },200);
                    if($scope.isShowFlag === 1) {
                        //console.log(123123123123123);
                        $('#tableRemoveId').removeAttr('readOnly');
                    }

                }else{
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });
        };

        $scope.setTtaAnalysis = function (id){
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
            return (w.innerHeight() - _top);
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
        $scope.showInfoNavTabsFun = function () {
            $scope.showInfoNavTabs = ($scope.showInfoNavTabs ?false: true);
            $timeout(function () {
                var w = jQuery(window);
                var id = 'contractParamsScrollId';
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
                //console.log((w.innerHeight() - _top), "heihgtshowInfoNavTabsFun");
                var showInfoNavTabsFunHeight = (w.innerHeight() - _top);
                $scope.initTable(showInfoNavTabsFunHeight);
            },500);
        };
        //保存合同行
        $scope.saveTtaContractLine = function (form) {
            if(!validateForm(form)){
                return;
            }
            httpServer.post(URLAPI.contractLineSaveOrUpdate,
                {params: JSON.stringify(
                        {
                            ttaContractLineTable:$scope.ttaContractLineTable,
                        }
                    )},
                function (res) {
                    if (res.status == 'S') {
                        $scope.ttaContractLineTable =  res.data.ttaContractLineTable;
                        //用于判断表单是否变更
                        $scope.startTtaContractLineTable = JSON.stringify($scope.ttaContractLineTable);
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

        /***************************TT Summary end****************/

        /***************************TT 贸易之协议补充 start****************/
        $scope.ttaDeptFeeReport = function (){
            httpServer.post(URLAPI.ttaDeptFeeLFindReport, {
                params: JSON.stringify({proposalId: getId()})
            }, function (res) {
                if (res.status == 'S') {
                    // var accordTypeInit ="-999";
                    // for(var i = 0;i<res.data.ttaDeptFeeReportTable.length;i++){
                    //         if(accordTypeInit != res.data.ttaDeptFeeReportTable[i].accordType){
                    //             accordTypeInit = res.data.ttaDeptFeeReportTable[i].accordType;
                    //             var newO = {
                    //                 itemDescCn:res.data.ttaDeptFeeReportTable[i].accordTypeName,
                    //                 lineCode:res.data.ttaDeptFeeReportTable[i].lineCode,
                    //                 flag:true} ;
                    //             res.data.ttaDeptFeeReportTable.splice(i, 0, newO);
                    //         }
                    // }
                    $scope.ttaDeptFeeReportTable = res.data.ttaDeptFeeReportTable;
                    $scope.ttaDeptFeeReportHead = res.data.head ;

                    $scope.ttaDeptFeeReportTableExcel = res.data.ttaDeptFeeReportTable;
                    $scope.ttaDeptFeeReportHeadExcel = res.data.head;
                    //取出甲方

                    var values = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == "PARTY_A_COMPANY";
                    });
                    $scope.ttaDeptFeeReportHead.partyA =  values[0].meaning;
                    $timeout(function () {
                        $scope.setTtaContractLine("ttaDeptFeeLFindReportScroll");
                        /*                        jQuery( "#ttaDeptFeeLFindReportTableId").colResizable({
                                                    liveDrag:false,
                                                    gripInnerHtml:"<div class='grip' style='width:20px;'"+
                                                    'height:15px;'+
                                                    'margin-top:-3px;'+
                                                    'margin-left:-5px;'+
                                                    'position:relative;'+
                                                    'z-index:88;'+
                                                    " 'cursor:e-resize;'></div>",
                                                    draggingClass:"dragging",
                                                    resizeMode:'overflow',
                                                });*/
                        jQuery( "#ttaDeptFeeLFindReportTableId td" ).resizable();
                        jQuery( "#ttaDeptFeeLFindReportTableId th" ).resizable();
                        jQuery( "#ttaDeptFeeLFindReportTableId tr" ).resizable();
                    },200);
                } else {
                    SIEJS.alert("加载贸易协议:" + res.msg, "error", "确定");
                }
            }, function (err) {
                //console.log("searchQuestionnaire error:" + err)
            });
        };

        //打印
        $scope.printTtaDeptFeeLFindReport = function (){
            jQuery("#ttaDeptFeeLFindReportPrint").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true,
            })
        };
        //base64转码
        var base64 = function (s) {
            return window.btoa(unescape(encodeURIComponent(s)));
        };
        //替换table数据和worksheet名字
        var format = function (s, c) {
            return s.replace(/{(\w+)}/g,
                function (m, p) {
                    return c[p];
                });
        };
        function tableToExcel(tableid, sheetName) {
            var uri = 'data:application/vnd.ms-excel;base64,';
            var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel"' +
                'xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="UTF-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>'
                + '<x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets>'
                + '</x:ExcelWorkbook></xml><![endif]-->' +
                ' <style type="text/css">' +
                '.proposalReport {\n' +
                '            background-color: #1e90c2 !important;\n' +
                ' }' +
                '</style>' +
                '</head><body ><table class="excelTable">{table}</table></body></html>';
            if (!tableid.nodeType) tableid = document.getElementById(tableid);
            var _table = tableid.innerHTML.replace(/<!--[^>]*-->/ig, '');
            var ctx = {worksheet: sheetName || 'Worksheet', table: _table};
            //document.getElementById("excelOut").href = uri + base64(format(template, ctx));
            window.open(uri + base64(format(template, ctx)));
        }

        $scope.ttaDeptFeeExport = function(){
            tableToExcel('ttaDeptFeeLFindReportTableId',"导出数据");
        };

        //excel导出(注释,不用这种方式的前端导出)
        /*   $scope.ttaDeptFeeExport = function(){
               if ($scope.ttaDeptFeeReportTableExcel.length == 0) {
                   SIEJS.alert("贸易协议无数据，不能执行导出操作!", "error", "确定");
                   return;
               }

               var sheetName = {itemDescCn:''};
               if ($scope.ttaDeptFeeReportHead.deptDesc1) {
                   sheetName["unit1"] = $scope.ttaDeptFeeReportHead.deptDesc1;
               }
               if ($scope.ttaDeptFeeReportHead.deptDesc2) {
                   sheetName["unit2"] = $scope.ttaDeptFeeReportHead.deptDesc2;
               }
               if ($scope.ttaDeptFeeReportHead.deptDesc3) {
                   sheetName["unit3"] = $scope.ttaDeptFeeReportHead.deptDesc3;
               }

               var datas = [];
               for (var index in $scope.ttaDeptFeeReportTableExcel) {
                   var deptFeeRow = $scope.ttaDeptFeeReportTableExcel[index];
                   var newData = {};
                   if (deptFeeRow.flag == 'true') {
                       newData["itemDescCn"] = deptFeeRow.itemDescCn;
                   } else {
                       newData["itemDescCn"] = "" + deptFeeRow.lineCode + deptFeeRow.itemDescCn;
                   }
                   if ($scope.ttaDeptFeeReportHead.deptDesc1) {
                       newData["unit1"] = deptFeeRow.unit1 ? deptFeeRow.unit1.replace(/\<br\/\>/g,"\n") : deptFeeRow.unit1;
                   }
                   if ($scope.ttaDeptFeeReportHead.deptDesc2) {
                       newData["unit2"] = deptFeeRow.unit2 ? deptFeeRow.unit2.replace(/\<br\/\>/g,"\n") : deptFeeRow.unit2;
                   }
                   if ($scope.ttaDeptFeeReportHead.deptDesc3) {
                       newData["unit3"] = deptFeeRow.unit3 ? deptFeeRow.unit3.replace(/\<br\/\>/g,"\n") : deptFeeRow.unit3;
                   }
                   datas.push(newData);
               }

   /!*            var sheetName = {
                   name: '姓名',
                   age: '年龄',
                   sex: '性别'
               };

               var datas = [
                   {"name": "路飞", "age": "100", "sex": "男"},
                   {"name": "女帝", "age": "30", "sex": "女"},
                   {"name": "娜美", "age": "30", "sex": "女"},
                   {"name": "索隆", "age": "solo", "sex": "男"},
               ];*!/
               console.log(datas); //JSON数据
               var workbook = jsonToExcel.toSheet({
                   sheetName: sheetName,
                   datas: datas
               });
               console.log(workbook);
               //console.log($scope.params);
               jsonToExcel.downloadExl(workbook,"贸易协议_" + $scope.params.orderNbr);
           }*/


        /***************************TT 贸易之协议补充 end****************/


        $scope.iniData = function () {
            var year = parseInt($scope.params.proposalYear);
            $scope.yearInfo.year =  year;
            $scope.yearInfo.lastYear =  year-1;
            $scope.yearInfo.beforeYear =  year-2;

        };



        //id不为空
        if (getId()) {

            //查询头信息
            $scope.search();
        } else {
            $scope.params.status='A';
            $scope.params.isTransdepart='N';
            $scope.params.isCrossYear='N';
            $scope.params.versionCode='1';

            $scope.params.isPlnConfirm='N';
            $scope.params.isTermsConfirm='N';
            $scope.params.isDepartConfirm='N';
            $scope.params.isNewConfirm='N';
            $scope.params.isQuestConfirm='N';

            //新增:设置当前登录人的主部门
            $scope.params.majorDeptDesc = userLoginInfo.deptName;
            $scope.params.majorDeptId =  userLoginInfo.deptId;
            $scope.params.majorDeptCode = userLoginInfo.deptCode;
        }


        $scope.btnSave = function () {
            /*            if (invalid) {
                            SIEJS.alert('请检查必填项', 'error', '确定');
                            return;
                        }*/

            httpServer.post(URLAPI.proposalSave, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.proposalId;
                        $scope.id = res.data.proposalId;
                        $scope.search();
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

        //更新供应商名称
        $scope.GXGYSMC = function(){
            httpServer.post(URLAPI.proposalUpdateVendorName, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        id = res.data.proposalId;
                        $scope.id = res.data.proposalId;
                        $scope.search();
                        SIEJS.alert("更新成功", 'success');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };


        //选择潜在供应商
        $scope.getSupplierCode = function () {
            if($scope.params.lastYearProposalId){
                SIEJS.confirm('当前操作会清除对应的历史供应商','','确定', function () {
                    $scope.params.lastYearVendorCode = '';
                    $scope.params.lastYearOrderNo = '';
                    $scope.params.lastYearVendorName = '';
                    $scope.params.lastYearProposalId = '';
                    $('#supplierCode').modal('toggle');
                });
            }else{
                $('#supplierCode').modal('toggle');
            }
        };
        $scope.newOrExistingFunc = function (value){
            if ($scope.params.newOrExisting == 'New_Vendor') { //新供应商变成不可用，且自动勾选。
                $scope.params.isSkipApprove = 'Y';
                $("#isSkipApprove").attr('disabled', true);
            } else {
                $scope.params.isSkipApprove = 'N';
                $("#isSkipApprove").attr('disabled', false);
            }
        };
        //点击确认按钮
        $scope.selectSupplierReturn = function (key, value, currentList) {
            $scope.params.vendorNbr = currentList[0].supplierCode;
            $scope.params.vendorName = currentList[0].supplierName;
            $scope.params.newOrExisting = currentList[0].isNew;
            //查询销售方式
            //$scope.params.suDeptCode = currentList[0].ownerDept;
            //$scope.params.suDeptName = currentList[0].deptName;

            //获取当前表单填写的proposal制作年度
            httpServer.post(URLAPI.proposalFindSaleType, {
                    'params': JSON.stringify({
                        vendorNbr:$scope.params.vendorNbr,
                        proposalYear:$scope.params.proposalYear,
                        newOrExisting:$scope.params.newOrExisting
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        var saleType = res.data.saleType;
                        if (saleType == 'no') {
                            $scope.params.saleType = '';
                        } else {
                            $scope.params.saleType = saleType;
                        }

                    } else {
                        //SIEJS.alert(res.msg, "error", "确定");
                        //console.log("查询失败");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.paramsProposalYearFun = function () {
            if ($scope.params.proposalYear &&
                !$scope.params.beginDate &&
                !$scope.params.endDate &&
                $scope.params.proposalYear.length== 4
            ) {
                $scope.params.beginDate = $scope.params.proposalYear + '-' + '01' + '-' + '01';
                $scope.params.endDate = $scope.params.proposalYear + '-' + '12' + '-' + '31';
            }
            if($scope.params.lastYearProposalId){
                SIEJS.alert("当前会清空历史单据号", 'success');
                $scope.params.lastYearVendorCode = '';
                $scope.params.lastYearOrderNo = '';
                $scope.params.lastYearVendorName = '';
                $scope.params.lastYearProposalId = '';
            }
        };

        //变更单据
        $scope.btnChange = function () {
            $scope.changeReason= true;
            $('#proposalChangeLov').modal('toggle');
        };


        $scope.saveProposalChange = function (invalid) {


            if (invalid) {
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }

            httpServer.post(URLAPI.proposalChange, {
                    'params': JSON.stringify({proposalId: getId(),remark:$scope.proposalChangeInfo.remark})
                },
                function (res) {
                    if (res.status == 'S') {
                        var proposalId = res.data.proposalId;
                        var xFlag = res.data.xFlag;
                        var xMsg = res.data.xMsg;
                        if(xFlag!=1){
                            SIEJS.alert(xMsg, "error", "确定");
                            return;
                        }
                        $('#proposalChangeLov').modal('hide');
                        id = proposalId;
                        $scope.search();

                        iframeTabAction('proposal变更', 'proposalDetail/' + proposalId);

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        };

        //切换版本
        $scope.btnCutVersion = function () {


            SIEJS.confirm('恢复上一版本', '是否确定恢复上一版本？', '确定', function () {
                httpServer.post(URLAPI.proposalCutVersion, {
                        'params': JSON.stringify({proposalId: getId()})
                    },
                    function (res) {
                        if (res.status == 'S') {
                            var proposalId = res.data.proposalId;
                            var xFlag = res.data.xFlag;
                            var xMsg = res.data.xMsg;
                            if (xFlag != 1) {
                                SIEJS.alert(xMsg, "error", "确定");
                                return;
                            }
                            SIEJS.alert("恢复成功", 'success');
                            id = proposalId;
                            $scope.search();


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




        //新增品牌明细saveBrandPlnL
        $scope.addBrandPlnL = function () {
            //   $scope.brandPlnLInfo = {brandplnHId: $scope.brandparams.brandplnHId,proposalId: $scope.params.proposalId,brandDetails: 'New_Brand'};
            //   $('#brandPlnLLov').modal('toggle');
            //增加按钮
            var newBreedArray = {
                brandplnHId: $scope.brandparams.brandplnHId,
                proposalId: getId(),
                brandDetails: 'New_Brand'
            };
            $scope.brandPlnLDataTable.push(newBreedArray);
        };



        //选择业务组
        $scope.getRelGroupCode = function () {
            $scope.deptProject.defaultDeptId = userLoginInfo.deptId;//默认当前登录人的部门id;
            $scope.brandPlnLineLov.search();
            $('#brandPlnLineLov').modal('toggle')
        };

        //点击确认按钮
        $scope.selectBrandPlnLineReturn = function (key, value, currentList) {
            var index=$scope.brandPlnLDataTable.selectRow;
            $scope.params.brandDetailDeptId = currentList[0].departmentId;
            $scope.brandPlnLDataTable[index].groupId =  currentList[0].departmentId;
            $scope.brandPlnLDataTable[index].groupCode = currentList[0].departmentCode;
            $scope.brandPlnLDataTable[index].groupDesc = currentList[0].departmentFullName;
        };


        //选择部门
        $scope.getRelDeptCode = function (row) {
            //console.log("-------groupId--------");
            //console.log(row);
            if (row.groupId !=undefined && row.groupId != null && row.groupId != "" ) {
                $scope.params.brandDetailDeptId =  row.groupId.toString();
            }
            $timeout(function () {
                $scope.departmentPlnDeptLov.search(1);
            });
            $('#brandPlnDeptLov').modal('toggle')
        };

        //点击确认按钮
        $scope.selectBrandPlnDeptReturn = function (key, value, currentList) {

            var index=$scope.brandPlnLDataTable.selectRow;

            $scope.brandPlnLDataTable[index].deptCode = currentList[0].departmentCode;
            //$scope.brandPlnLDataTable[index].deptDesc = currentList[0].departmentName;
            $scope.brandPlnLDataTable[index].deptDesc = currentList[0].departmentFullName;

        };


        //修改品牌信息
        $scope.editBrandPlnL = function () {
            if ($scope.rpTableBrandPlnL.selectRowList.length != 1) {
                SIEJS.alert("请选择一行数据进行修改!", "error", "确定");
                return;
            }
            $scope.brandPlnLInfo = $scope.rpTableBrandPlnL.selectRowList[0];
            $('#brandPlnLLov').modal('toggle');
        };

        //保存品牌明细
        $scope.saveBrandPlnL = function (form1,form2) {

            if(!validateForm(form1)){
                return;
            }
            if(!validateForm(form2)){
                return;
            }

            //为了不更新年度,删除key
            delete $scope.brandparams.yearCode;
            //参数
            var param= {
                project:$scope.params,//proposal单据的参数
                brandPlnLDataTable:$scope.brandPlnLDataTable,//品牌计划列表的数据
                brandparams:$scope.brandparams,//品牌列表头信息
                rpTable:$scope.rpTable
            };

            httpServer.post(URLAPI.brandplnLSave,
                {params: JSON.stringify(param)},
                function (res) {
                    if (res.status == "S") {
                        $scope.updateBrandPlnH();
                        //$scope.searchBrandPlnH();
                        //SIEJS.alert("处理成功", "success", "确定");

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

        //品牌计划确认
        $scope.confirmBrandPlnH = function (form1,form2) {
            if(!validateForm(form1)){
                return;
            }
            if(!validateForm(form2)){
                return;
            }

            httpServer.post(URLAPI.brandplnHConfirm,
                {params: JSON.stringify({proposalId: getId()})},
                function (res) {
                    if (res.status == "S") {
                        //$scope.searchBrandPlnL();
                        $scope.params.isPlnConfirm = 'Y';

                        //SIEJS.alert("处理成功", "success", "确定");
                        $scope.showinfoButton = true;//禁用
                        $scope.saveBrandPlnL(form1,form2);


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

        //品牌计划取消确认
        $scope.CancelConfirmBrandPlnH = function () {

            httpServer.post(URLAPI.brandplnHCheckConfirm,
                {params: JSON.stringify({proposalId: getId()})},
                function (res) {
                    if (res.status == "S") {
                        if (res.data == undefined || res.data === '') {
                            $scope.CancelConfirmRequestForBrand();
                        }else {
                            SIEJS.confirm('提示',res.data+ ',是否要点击取消?', '确定', function () {
                                $scope.CancelConfirmRequestForBrand();
                            });
                        }
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

        //品牌取消确认请求
        $scope.CancelConfirmRequestForBrand = function() {
            httpServer.post(URLAPI.brandplnHCancelConfirm,
                {params: JSON.stringify({proposalId: getId()})},
                function (res) {
                    if (res.status == "S") {
                        //$scope.searchBrandPlnL();
                        $scope.params.isPlnConfirm = 'N';
                        $scope.showinfoButton = false;

                        //取消确认
                        if($scope.brandPlnLDataTable != undefined && $scope.brandPlnLDataTable !=null ) {
                            for (let key in $scope.brandPlnLDataTable) {
                                let rowData = $scope.brandPlnLDataTable[key];
                                //取消控制
                                rowData.fcsPurchaseCon = 'N';
                                rowData.purchaseGrowthCon = 'N';
                                rowData.fcsSalesCon = 'N';
                                rowData.salesGrowthCon = 'N';
                            }
                        }
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

        };

        //取消问卷调查确认(不用)
        $scope.cancelConfirmQuestionReSearch = function(){
            httpServer.post(URLAPI.cancelQuestionTest, {
                'params': JSON.stringify(
                    {
                        proposalId: getId(),
                    }
                )
            }, function (res) {
                if (res.status == 'S') {
                    $scope.qIsQuestConfirm = 'N';
                    $scope.searchQuestionnaire();
                    $scope.searchQuestionDataTable(getId());
                    $scope.fileDataTable.search(1);
                    SIEJS.alert("取消确认成功!", "success", "确定");
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            });
        };

        //对输入空值,默认为0
        $scope.hs = function(x) {
            var f = parseFloat(x);
            if (isNaN(f)) {
                return 0;
            }
            return f;
        };

        var brandDate = new Date();
        var brandMonth = brandDate.getMonth();//

        //输入的金额有千分位分割符,去掉分割符
        $scope.formatAmount = function(amount) {
            var f = parseFloat(amount);
            if (isNaN(f)) {
                return 0;
            }
            amount = amount + "";
            return parseFloat(amount.replace(/,/g,""))
        };
        //自动改变值(Sales Growth)
        $scope.salesGrowthChange = function(row) {
            if (row.brandDetails === 'Existing_Brand') {
                //var a = parseFloat(row.sales);
                //var b = $scope.hs(row.salesGrowth);
                //F’cs Sales = Sales*（1+ Sales Growth%）
                //2019.11.27 改公式
                //row.fcsSales =((a / brandMonth * 12 )*(1+b/100)).toFixed(0);//2019.10.9修改为保留0位小数,之前为保留两位小数

                row.fcsSalesCon = 'Y';
                var a = parseFloat(row.annualSales.replace(/,/g,""));
                //var b = $scope.hs(row.salesGrowth);
                var b = $scope.formatAmount(row.salesGrowth);
                row.fcsSales = (Number(a) * (1 + b/100)).toFixed(0);
            }
        };

        $scope.fcsSalesChange =function(row){
            row.salesGrowthCon = 'Y';
            var fcsSales = row.fcsSales.replace(/,/g,"");
            //var annualSales = parseFloat(row.annualSales);
            var annualSales = $scope.formatAmount(row.annualSales);
            row.salesGrowth= annualSales == 0 ? 0 : (((Number(fcsSales) / annualSales) - 1 ) * 100).toFixed(2);
        };

        //自动改变值
        $scope.purchaseGrowthChange = function(row){
            if (row.brandDetails === 'Existing_Brand') {
                //var po = parseFloat(row.poRecord);
                //var pg = $scope.hs(row.purchaseGrowth);
                //F’cs Purchase = Purchase（PO Record）*（1+ Purchase Growth%）
                //row.fcsPurchase = ((po / brandMonth * 12 )*(1+pg/100)).toFixed(0);

                row.fcsPurchaseCon = 'Y';
                //var pg = $scope.hs(row.purchaseGrowth);
                var pg = $scope.formatAmount(row.purchaseGrowth);
                var annualPurchase = parseFloat(row.annualPurchase.replace(/,/g,""));
                row.fcsPurchase = ( Number(annualPurchase)*(1+pg/100) ).toFixed(0);
            }
        };

        $scope.fscPurchaseChange = function(row,value) {
            row.purchaseGrowthCon = 'Y';
            var fcsPurchase = row.fcsPurchase.replace(/,/g,"") ;
            //var annualPurchase = parseFloat(row.annualPurchase);
            var annualPurchase = $scope.formatAmount(row.annualPurchase);
            row.purchaseGrowth  = annualPurchase == 0 ? 0 :(((Number(fcsPurchase) / annualPurchase) - 1) *100).toFixed(2);
        };


        //(Brand Plan Adjust) 品牌调整计划
        $scope.brandPlanAdjustChange= function(row) {
            if (row.brandDetails === 'Existing_Brand') {
                var sales = parseFloat(row.sales);
                var salesGrowth = $scope.hs(row.salesGrowth);

                var poRecord = parseFloat(row.poRecord);
                var purchaseGrowth = $scope.hs(row.purchaseGrowth);

                //品牌计划调整比例
                var brandPlanAdjust = $scope.hs(row.brandPlanAdjust);

                //计算未来的值
                row.fcsSales = (sales * (1 + salesGrowth/100) * (1 + brandPlanAdjust/100)).toFixed(0);
                row.fcsPurchase = (poRecord * (1 + purchaseGrowth/100) * (1 + brandPlanAdjust/100)).toFixed(0);
            }
        };

        //***************************************品牌计划开始*************************************************************************************
        console.log("________________品牌计划初始化用户登录信息______________________");
        window.console.log(userLoginInfo);
        var tokenCode = null;
        if ((userLoginInfo != null && typeof(userLoginInfo) != "undefined") && typeof(userLoginInfo.TokenCode) != "undefined") {
            tokenCode = userLoginInfo.TokenCode;
        } else {
            tokenCode = "INDEX_TOKEN_CODE";
        }

        function getCookie(name) {
            var cookie_start = document.cookie.indexOf(name);
            var cookie_end = document.cookie.indexOf(";", cookie_start);
            return cookie_start == -1 ? '' : unescape(document.cookie.substring(cookie_start + name.length + 1, (cookie_end > cookie_start ? cookie_end : document.cookie.length)));
        }
        var timer;
        var exportTimer;
        $scope.runCountBrandPlnHFun = function(){
            SIEJS.confirm('提示', '您确定点击计算操作吗?', '确定', function () {
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
                $('#result').html('<p>品牌数据正在生成请耐心等候!</p>');
                $('#result').append("<div id=\"saafLoading1\" style=\"position: fixed; top: 0; width: 100%; z-index: 9000; height: 100%; \">\n" +
                    "                                <div  style=\"position: absolute; top:50%;left:50%; text-align: center;margin: 0 auto; width:100px;\">\n" +
                    "                                    <img src=\"img/loading1.gif\">\n" +
                    "                                </div>\n" +
                    "                            </div>");
                $("#ttaPropsalBrandCountBtnModal").modal('toggle');

                httpServer.post(
                    URLAPI.submitBrandCountTask, {
                        'params': JSON.stringify({
                            brandplnHId: $scope.brandparams.brandplnHId,
                            project:$scope.params,//proposal单据的参数
                            brandPlnLDataTable:$scope.brandPlnLDataTable,//品牌计划列表的数据
                            brandparams:$scope.brandparams//品牌列表头信息
                        })
                    },
                    function (res) {
                        if (res.status == 'S') {
                            // 查询状态
                            exportTimer = setInterval(function () {
                                exportStatus(res.data.createKey);
                            }, 1000);
                        } else {
                            clearInterval(timer);
                            $('#result').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>");
                        }
                    },
                    function (err) {
                        clearInterval(timer);
                        $('#result').html('<h3 style="color:#FF0000;">执行失败</h3>');
                    }
                );
            });
        };

        //查询状态
        function exportStatus(createKey) {
            var params = {
                params:"{createKey:" + createKey + "}"

            };
            $.ajax({
                url: URLAPI.getBrandCountCreateStatus,
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
                        $('#result').html('<h3 style="color:green;">' + res.msg + '</h3>');
                        $scope.searchBrandPlnH();
                        $("#ttaPropsalBrandCountBtnModal").modal('toggle');
                    } else  if (res.status === 'E' || res.status === 'M') {//失败
                        clearInterval(timer);
                        clearInterval(exportTimer);
                        $('#result').html("<h3 style='color:#FF0000;'>" + res.msg + "</h3>")
                    }
                },
                error: function (e) {
                    clearInterval(timer);
                    clearInterval(exportTimer);
                    $('#result').html('<h3 style="color:#FF0000;">执行失败</h3>')
                }
            });
        }

        $scope.brandImportButton = function(){
            $scope.updateStatusByBrand = true;
            $("#fileUpLoadByBrandLpn").val('');
            $("#brandErrorMsgId").html('');
            $('#brandExcelImportId').modal('toggle');
        };

        //品牌明细数据导入
        $scope.brandImport = function(invalid){
            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            var fd = new FormData();
            var file = document.getElementById('fileUpLoadByBrandLpn').files[0];
            if (!file) {
                SIEJS.alert("请选择上传文件", 'error', '确定');
                return;
            }
            fd.append('file', file);
            fd.append("proposalId", getId());
            saafLoading.show();
            $http.post(URLAPI.saveImportTtaBrandlpn, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                //查询品牌明细头数据
                //品牌明细行数据
                saafLoading.hide();
                if (response.status == 'S') {
                    $scope.searchBrandPlnH();
                    $('#brandExcelImportId').modal('toggle');
                    SIEJS.alert(response.msg, 'success', '确定');
                } else {
                    $scope.updateStatusByBrand = false;
                    $("#brandErrorMsgId").html("<p style='color: #ff0000;'>" + response.msg + "</p>");
                }
            }).error(function(response) {
                $scope.updateStatusByBrand = false;
                saafLoading.hide();
                //SIEJS.alert("导入失败!", 'error', '确定');
                $("#brandErrorMsgId").html("<p style='color: #ff0000;'>" + response.msg + "</p>");
            });
        };
        //***************************************品牌计划结束************************************************************************************

        //品牌计划计算
        $scope.CountBrandPlnH = function () {
            SIEJS.confirm('提示', '您确定点击计算操作吗?', '确定', function () {
                httpServer.post(URLAPI.brandplnHCount,
                    {params: JSON.stringify({
                            brandplnHId: $scope.brandparams.brandplnHId,
                            project:$scope.params,//proposal单据的参数
                            brandPlnLDataTable:$scope.brandPlnLDataTable,//品牌计划列表的数据
                            brandparams:$scope.brandparams//品牌列表头信息

                        }),
                        __timeout:600
                    },
                    function (res) {
                        if (res.status == "S") {
                            //console.log(res.data);
                            if (res.data == null || res.data.length == 0) {
                                SIEJS.alert("拉取品牌计划明细数据为空!", "success", "确定");
                            } else {
                                $scope.updateBrandPlnH();
                            }
                            //$scope.searchBrandPlnH();
                            //SIEJS.alert("处理成功", "success", "确定");

                        } else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    },
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        $("#saveButton").attr("disabled", true);
                    }
                );

            });
        };

        //更新品牌计划汇总数据
        $scope.updateBrandPlnH = function() {
            httpServer.post(URLAPI.brandplnHUpdate,
                {params: JSON.stringify({
                        brandplnHId: $scope.brandparams.brandplnHId
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.searchBrandPlnH();
                        SIEJS.alert("保存成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            )
        };

        //删除品牌明细
        $scope.delBrandPlnL = function () {
            var index=$scope.brandPlnLDataTable.selectRow;
            var nbLine = $scope.brandPlnLDataTable[index];
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                if (nbLine.brandplnLId == null || nbLine.brandplnLId == "") {
                    $scope.brandPlnLDataTable.splice(index, 1);             //新增列没有ID直接删除
                    SIEJS.alert("操作成功!", "已成功删除数据！", "success");
                    // $scope.$apply();
                } else {
                    var ids = nbLine.brandplnLId;
                    var existingBrand= nbLine.brandDetails;

                    if (existingBrand == 'Existing_Brand') {
                        SIEJS.alert("操作失败", "当前点击的是Existing_Brand,不允许删除！", "warning");
                        return;
                    }

                    httpServer.post(URLAPI.brandplnLDel,
                        {params: JSON.stringify({ids: ids,existingBrand:existingBrand})},
                        function (res) {
                            if (res.status == "S") {
                                //   $scope.rpTableBrandPlnL.search();
                                $scope.brandPlnLDataTable.splice(index, 1);
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
                }

            })

        };


        //清空品牌计划
        $scope.clearBrandPlnL = function () {

            SIEJS.confirm('提示', '确定要清空所选的品牌计划明细信息吗？', '确定', function () {
                httpServer.post(URLAPI.brandplnLClear,
                    {params: JSON.stringify({brandplnHId: $scope.brandparams.brandplnHId})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.brandPlnLDataTable = [];
                            $scope.searchBrandPlnL();
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







        $scope.submitApproval2 = function () {

            httpServer.post(URLAPI.findApproveCheck, {
                    'params': JSON.stringify($scope.params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.submitApprovalEnd();
                    } else {
                        $scope.btnC('0',false);
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    $scope.btnC('0',false);
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        /**
         * 清除销售区域
         */
        $scope.clearSalesArea = function() {
            $scope.contractParams.salesArea = '';
        };
        /**
         * 确定选择销售区域
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectSalesArea = function(key, value, currentList) {
            var values = currentList.filter(function (x) {
                return x.meaning == '全国';
            });
            if(values.length>0 && currentList.length>1){
                SIEJS.alert('全国,其他不能同时选', "error", "确定");
                $('#salesAreaChange').modal("show");
                return ;
            }
            $('#salesAreaChange').modal("hide");
            $('.modal-backdrop').remove();
            $scope.contractParams.salesArea= "";
            $scope.contractParams.warehouseDesc= "";
            $scope.contractParams.warehouseCode = "";
            angular.forEach(currentList, function(item,index,array){
                $scope.contractParams.salesArea = $scope.contractParams.salesArea+item.meaning+",";
            });
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

        /**
         * 清除仓库
         */
        $scope.clearWarehouse = function() {
            $scope.contractParams.warehouseCode = "";
            $scope.contractParams.warehouseDesc = "";
        };
        /**
         * 确定选择仓库
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selectWarehouse = function() {
            $scope.contractParams.salesArea= "";
            $scope.contractParams.warehouseCode = "";
            $scope.contractParams.warehouseDesc = "";
            var list = $scope.warehouseParamsTDataTable.selectRowList;
            angular.forEach(list, function(item,index,array){
                $scope.contractParams.warehouseCode = $scope.contractParams.warehouseCode+item.lookupCode+",";
                $scope.contractParams.warehouseDesc = $scope.contractParams.warehouseDesc+item.meaning+",";
            });
            $('#warehouseChange').modal('toggle');
        };

        //是否可退货发生变化的时候
        $scope.termsISFun = function () {
            if ($scope.contractParams.isReturn && $scope.contractParams.isReturn != 'CY') {
                $scope.contractParams.returnConditions = '';
            }
        };
        //保存tta terms
        $scope.saveTrems = function (form,flag) {
            if(!validateForm(form)){
                return;
            }
            $scope.contractParams.brandEn = $scope.contractParams.brandCn;
            httpServer.post(URLAPI.termsHSave,
                {params: JSON.stringify(
                        {
                            contractParams:$scope.contractParams,
                            dataTableTerms:$scope.dataTableTerms,
                            deleteTtaTerms:$scope.deleteTtaTerms
                        }
                    )},
                function (res) {
                    if (res.status == 'S') {
                        $scope.deleteTtaTerms = [];
                        if('2' == flag ){
                            httpServer.post(URLAPI.termsLComfirm,
                                {params: JSON.stringify({
                                        proposalId: $scope.contractParams.proposalId,
                                        termsHId:$scope.contractParams.termsHId,
                                        returnArray : $scope.returnArray,
                                        same : ($scope.taxCom - $scope.termsCountTax).toFixed(2),
                                        oldInvoiceType :$scope.contractParams.oldInvoiceType
                                    })},
                                function (res) {
                                    if (res.status == "S") {
                                        $scope.contractParams.isTermsConfirm = 'Y';
                                        $scope.searchTerms();
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
                        }else{
                            SIEJS.alert(res.msg, 'success');
                            $scope.searchTerms();
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
        //vLength
        $scope.TermsChangeIn = function (row, rowDate, rowValue) {
            var values = rowDate.filter(function (x) {
                return x.name == rowValue;
            });
            if(rowValue == undefined){
                row.incomeType = '';
            }
            // row.clauseTreeId = values.length>0?values[0].value:'' ;
            if (  !rowValue  ||    "" == rowValue) {
                //请选择
                var currentMethod = $scope.dataTableTerms.filter(function (x) {
                    if (!row.oldClauseId) { //不存在往年的时候
                        return x.clauseId == row.clauseId;
                    } else {
                        return x.oldClauseId == row.oldClauseId;
                    }
                });
                var currentMethodOld = currentMethod.filter(function (x) {
                    var flag = false;
                    if (x.oldClauseTreeId) {
                        flag = true;
                    }
                    return flag;
                });
                if (currentMethodOld.length == 0) {
                    for (var i = 0; i < currentMethod.length; i++) {
                        if (currentMethod[i] === row) {
                            continue;
                        }
                        var timesa = $scope.dataTableTerms.indexOf(currentMethod[i]);
                        if (currentMethod[i].termsLId) {
                            var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                return x.termsLId ==  currentMethod[i].termsLId
                            });
                            if(vLength.length==0){
                                $scope.deleteTtaTerms.push(angular.copy(currentMethod[i]));
                            }

                        }
                        $scope.dataTableTerms.splice(timesa, 1);
                    }
                } else {
                    var flag = false;
                    var bl;
                    var array = [];
                    var j = 0;
                    for (var i = 0; i < currentMethod.length; i++) {
                        if (!currentMethod[i].oldClauseTreeId) {
                            array.push(currentMethod[i]);
                        } else {
                            if (j == 0) {
                                for (name in TermsChange) {
                                    if ('termsLId' != name) {
                                        currentMethod[i][name] = row[name];
                                    }
                                }
                                row = currentMethod[i] ;
                                j++;
                            } else {
                                j++;
                                if (currentMethod[i].termsLId) {
                                    var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                        return x.termsLId ==  currentMethod[i].termsLId
                                    });
                                    if(vLength.length==0){
                                        $scope.deleteTtaTerms.push(angular.copy(currentMethod[i]));
                                    }
                                }
                                for (name in TermsChange) {
                                    if(   !(currentMethod[i].oldYTerms && 'oldClauseId' == name) ){
                                        currentMethod[i][name] = '';
                                    }

                                }

                            }
                        }
                    }
                    //删除空白的
                    for (var i = 0; i < array.length; i++) {
                        var tims = $scope.dataTableTerms.indexOf(array[i]);
                        if (array[i].termsLId) {
                            var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                return x.termsLId ==  array[i].termsLId
                            });
                            if(vLength.length== 0){
                                $scope.deleteTtaTerms.push(angular.copy(array[i]));
                            }

                        }
                        $scope.dataTableTerms.splice(tims, 1);
                    }

                }
            } else {
                //获取当前行 对应的值
                var valuesNoCopy = angular.copy(rowDate);

                if ("1" == values[0].flag) {    //当前为同层显示

                    //选中当前条款下的所有收取方式
                    var currentMethod = $scope.currentMethodFun(row);

                    //移动当前行的位置
                    $scope.moveCurrentRow(currentMethod, row, values, rowDate, rowValue, valuesNoCopy);

                } else {    //选择不是同层显示
                    //第一种 不是同层显示
                    //选取当前所有的收取方式
                    var currentMethod = $scope.dataTableTerms.filter(function (x) {
                        if (!row.oldClauseId) {   //不存在说明没有往年
                            return x.clauseId == row.clauseId;
                        } else {
                            return x.oldClauseId == row.oldClauseId;
                        }
                    });
                    //存在往年对应收取方式的
                    var currentMethodOld = currentMethod.filter(function (x) {
                        var flag = false;
                        if (x.oldClauseTreeId) {
                            flag = true;
                        }
                        return flag;
                    });

                    if (currentMethodOld.length == 0) {

                        var findSame = currentMethod.filter(function (x) {
                            var flag = false ;
                            if(x.clauseTreeId && row.clauseTreeId && x.clauseTreeId  == values[0].value){
                                if(row.termsLId && x.termsLId && x.termsLId != row.termsLId){
                                    flag = true ;
                                }
                            }
                            return flag ;
                        });
                        row = findSame.length>0?findSame[0]:row;
                        for (var i = 0; i < currentMethod.length; i++) {
                            if (currentMethod[i] === row) {
                                continue;
                            }
                            var timesa = $scope.dataTableTerms.indexOf(currentMethod[i]);
                            if (currentMethod[i].termsLId) {
                                var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                    return x.termsLId ==  currentMethod[i].termsLId
                                });
                                if(vLength.length== 0){
                                    $scope.deleteTtaTerms.push( angular.copy(currentMethod[i]) );
                                }

                            }
                            $scope.dataTableTerms.splice(timesa, 1);
                        }
                    } else {
                        var flag = false;
                        var bl;
                        for (var i = 0; i < currentMethod.length; i++) {
                            if (currentMethod[i].oldClauseTreeId == values[0].oldClauseTreeId2) {
                                for (name in TermsChange) {
                                    if ('termsLId' != name) {
                                        currentMethod[i][name] = row[name];
                                    }
                                }
                                flag = true;
                                bl = currentMethod[i];
                                if(currentMethod[i] && currentMethod[i].yTerms){
                                    currentMethod[i].clauseTreeId = values.length>0?values[0].value:'';
                                    currentMethod[i].yYear = values.length>0?values[0].ttaValue:'';
                                    currentMethod[i].referenceStandard =values.length>0?values[0].defaultValue:'';

                                    var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                                        var pId = currentMethod[i].clauseTreeId;
                                        return x.clauseId == pId;
                                    });
                                    currentMethod[i].unitList =findUnit;
                                    currentMethod[i].unit=findUnit.length == 1? findUnit[0].unitValue:'';
                                    currentMethod[i].unitId=findUnit.length == 1? findUnit[0].collectTypeId:'';
                                    currentMethod[i].rule=findUnit.length == 1? findUnit[0].rule:'';
                                    //  $scope.TermsChangeUnit(currentMethod[i],currentMethod[i].unitList, currentMethod[i].unit);
                                }

                            }
                        }
                        if (flag) {
                            var array = [];
                            for (var i = 0; i < currentMethod.length; i++) {
                                if (currentMethod[i] === bl) {

                                } else {
                                    if (!currentMethod[i].oldClauseTreeId) {
                                        array.push(currentMethod[i]);
                                    } else {
                                        if (currentMethod[i].termsLId) {
                                            var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                                return x.termsLId ==  currentMethod[i].termsLId
                                            });
                                            if(vLength.length ==0){
                                                $scope.deleteTtaTerms.push(angular.copy(currentMethod[i]));
                                            }

                                        }
                                        for (name in TermsChange) {
                                            if(   !(currentMethod[i].oldYTerms && 'oldClauseId' == name) ){
                                                currentMethod[i][name] = '';
                                            }
                                        }

                                    }
                                }
                            }
                            //删除空白的
                            for (var i = 0; i < array.length; i++) {
                                var times = $scope.dataTableTerms.indexOf(array[i]);
                                if (array[i].termsLId) {
                                    var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                        return x.termsLId ==  array[i].termsLId
                                    });
                                    if(vLength.length == 0){
                                        $scope.deleteTtaTerms.push(angular.copy(array[i]));
                                    }

                                }
                                $scope.dataTableTerms.splice(times, 1);
                            }
                        } else {
                            var array = [];
                            var j = 0;
                            for (var i = 0; i < currentMethod.length; i++) {
                                if (!currentMethod[i].oldClauseTreeId) {
                                    array.push(currentMethod[i]);
                                } else {
                                    if (j == 0) {
                                        for (name in TermsChange) {
                                            if ('termsLId' != name) {
                                                currentMethod[i][name] = row[name];
                                            }
                                        }
                                        var findUnit2 = $scope.ttaTermsUnit.filter(function (x) {
                                            var pId = currentMethod[i].clauseTreeId;
                                            return x.clauseId == pId;
                                        });
                                        // currentMethod[i].unitList =findUnit2;
                                        // currentMethod[i].unit=findUnit2.length == 1? findUnit2[0].unitValue:'';
                                        // currentMethod[i].rule=findUnit2.length == 1? findUnit2[0].rule:'';
                                        //  $scope.TermsChangeUnit(currentMethod[i],currentMethod[i].unitList, currentMethod[i].unit);
                                    } else {
                                        j++;
                                        if (currentMethod[i].termsLId) {
                                            var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                                return x.termsLId ==  currentMethod[i].termsLId
                                            });
                                            if(vLength.length == 0){
                                                $scope.deleteTtaTerms.push(angular.copy(currentMethod[i]));
                                            }

                                        }
                                        for (name in TermsChange) {
                                            if(   !(currentMethod[i].oldYTerms && 'oldClauseId' == name) ){
                                                currentMethod[i][name] = '';
                                            }
                                        }

                                    }
                                }
                            }
                            //删除空白的
                            for (var i = 0; i < array.length; i++) {
                                var times = $scope.dataTableTerms.indexOf(array[i]);
                                if (array[i].termsLId) {
                                    var vLength = $scope.deleteTtaTerms.filter(function (x) {
                                        return x.termsLId ==  array[i].termsLId
                                    });
                                    if(vLength.length ==0){
                                        $scope.deleteTtaTerms.push( angular.copy(array[i]));
                                    }

                                }
                                $scope.dataTableTerms.splice(times, 1);
                            }
                        }
                    }

                }
            }
            if(row && row.yTerms && values.length>0 &&  row.incomeType == values[0].name){
                row.clauseTreeId = values.length>0?values[0].value:'';
                row.yYear = values.length>0?values[0].ttaValue:'';
                row.referenceStandard =values.length>0?values[0].defaultValue:'';

                var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                    var pId = row.clauseTreeId;
                    return x.clauseId == pId;
                });
                row.unitList =findUnit;
                row.unit=findUnit.length == 1? findUnit[0].unitValue:'';
                row.unitId=findUnit.length == 1? findUnit[0].collectTypeId:'';
                row.rule =  findUnit.length == 1? findUnit[0].rule:'';
                $scope.TermsChangeUnit(row,row.unitList, row.unit);


                //条款为DM或者FYLER 或者促销陈列服务费,删除部门协定标准模块中的促销陈列费和快讯及传单table表格数据
                //row.yTerms === '促销陈列服务费'
                if(row && row.yTerms &&row.incomeType&& (row.yTerms === 'DM' || row.yTerms === 'FYLER' || row.yTerms.indexOf('陈列服务费') >-1 )
                    && (row.incomeType =='按公司标准' || row.incomeType =='按其他协定标准')){
                    if (row.incomeType == undefined || row.incomeType == '') return;
                    var distinguishField = '';
                    if (row.yTerms === 'DM') {
                        distinguishField = 'DM'
                    } else if (row.yTerms === 'FYLER') {
                        distinguishField = 'FLYER';
                        //row.yTerms === '促销陈列服务费'
                    } else if (row.yTerms.indexOf('陈列服务费') >-1
                        && (row.incomeType =='按公司标准' || row.incomeType =='按其他协定标准')) {
                        distinguishField = 'promotion' ;
                    }
                    $scope.deleteDeptAgreementStandardByTerms(distinguishField);
                }
                row.termsSystem = '';
                row.feeIntas = '';
                row.feeNotax = '';
                if(row.yTerms && row.incomeType &&  (row.yTerms == 'DM' || row.yTerms == 'FYLER'|| row.yTerms.indexOf('陈列服务费') >-1    )
                    &&  (row.incomeType =='按公司标准' || row.incomeType =='按其他协定标准')){
                    row.feeIntas = '';
                    row.feeNotax = '';
                    row.termsSystem = row.incomeType;
                }
            }else{
                if(!row.incomeType) {
                    row.termsSystem = '';
                    row.feeIntas = '';
                    row.feeNotax = '';
                    row.yYear = '';
                    row.unit = '';
                    row.unitId = '';
                    row.unitList = [];
                    row.clauseTreeId = row.oldAlineId;
                    row.referenceStandard = '';
                }else{
                    var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                        var pId = row.clauseTreeId;
                        return x.clauseId == pId;
                    });
                    row.rule =  findUnit.length == 1? findUnit[0].rule:'';
                    row.unitId =  findUnit.length == 1? findUnit[0].collectTypeId:'';
                }

                $scope.TermsChangeUnit(row,row.unitList, row.unit);
            }


            $scope.termsLSaveAll(row);


        };


        //删除部门协定标准 促销陈列费和快讯及传单table表格数据
        $scope.deleteDeptAgreementStandardByTerms = function(symbol){
            httpServer.post(URLAPI.termsLDeleteDataByYterms,
                {params: JSON.stringify({
                        proposalId: $scope.contractParams.proposalId,
                        symbol:symbol
                    })},
                function (res) {
                    if (res.status == "S") {
                        //res.data
                        //console.log("删除成功")
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
        /**
         * 保存行
         */
        $scope.termsLSaveAll = function (row){
            $scope.saveAll =  $scope.dataTableTerms.filter(function(x) {
                return x.clauseId && row.clauseId &&  x.clauseId== row.clauseId;
            });
            for (var i = $scope.deleteTtaTerms.length - 1; i >= 0; i--) {
                for (var j = 0; j  < $scope.saveAll.length; j++) {
                    if ( $scope.saveAll[j].termsLId && $scope.deleteTtaTerms[i].termsLId === $scope.saveAll[j].termsLId) {
                        $scope.deleteTtaTerms.splice(i, 1);
                        break;
                    }
                }
            }
            httpServer.post(URLAPI.termsLSaveAll,
                {params: JSON.stringify({
                        proposalId: $scope.contractParams.proposalId,
                        hId:$scope.contractParams.termsHId,
                        beoiTax: $scope.contractParams.beoiTax,
                        del: $scope.deleteTtaTerms,
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.deleteTtaTerms = [];
                        for (var i = 0 ; i<res.data.length;i++){
                            for(var j = 0 ;j<$scope.saveAll.length;j++){
                                if(i == j){
                                    $scope.saveAll[j].termsLId = res.data[i].termsLId ;
                                }
                            }
                        }
                        //退货   根据 集中折扣变化
                        if(  (row.yTerms == $scope.getCenter[0].meaning || row.yTerms == $scope.getReturn[0].meaning) ){
                            if (row.incomeType){
                                $scope.returnCount(true);
                            }else{
                                $scope.returnCount(false);
                            }

                        }
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
        /**
         * TERMS_ 监听合同版本
         */
        $scope.$watch('contractParams.agreementEdition', function (newVal, oldVal) {
            $scope.returnCount(true) ;
        }, true);
        /**
         * TERMS_ TEST
         */
        $scope.$watch('dataTableTerms', function (newVal, oldVal) {
            //console.log(newVal) ;
            //console.log(oldVal) ;
        }, true);
        /**
         * TERMS_ 监听销售方式
         */
        $scope.$watch('contractParams.salesType', function (newVal, oldVal) {
            $scope.returnCount(true) ;
        }, true);
        $scope.returnCount = function (returnLfag){
            if($scope.getReturn && $scope.getCenter && $scope.dataTableTerms){
                var returns = $scope.dataTableTerms.filter(function (x) {  //退货
                    return x.yTerms ==$scope.getReturn[0].meaning;
                });
                var centers = $scope.dataTableTerms.filter(function (x) {  //集中
                    return x.yTerms ==$scope.getCenter[0].meaning;
                });
                if (returnLfag) {
                    if($scope.contractParams.salesType &&  'A01' == $scope.contractParams.salesType){ //加上 寄售经仓    退货处理 集中收获，购货折扣 TTA  不可以改 退货金额不计算
                        if(returns.length>0 && centers.length>0 && returns[0].incomeType && centers[0].incomeType){
                            returns[0].yYear = centers[0].yYear ;
                            $scope.executeParams(returns[0],'1',null);
                        }

                    } else if ($scope.contractParams.salesType &&  'B01' == $scope.contractParams.salesType
                        && $scope.contractParams.agreementEdition && 'A' == $scope.contractParams.agreementEdition ){  //合同版本A  加上 P可退货购销    退货处理 集中收获，购货折扣 TTA不可以改 退货金额不计算
                        if(returns.length>0 && centers.length>0 && returns[0].incomeType && centers[0].incomeType){
                            returns[0].yYear = centers[0].yYear;
                            $scope.executeParams(returns[0],'1',null);
                        }
                    }else  if ($scope.contractParams.salesType &&  'B01' == $scope.contractParams.salesType
                        && $scope.contractParams.agreementEdition && 'B' == $scope.contractParams.agreementEdition){
                        if(returns.length>0 && centers.length>0 && returns[0].incomeType && centers[0].incomeType)
                            returns[0].yYear = centers[0].yYear == ""? "":Number(centers[0].yYear)* 2 ;
                        $scope.executeParams(returns[0],'1',null);
                    }
                }


                if (centers[0].yYear && $scope.contractParams.returnFreightFlag && 'Y' === $scope.contractParams.returnFreightFlag) {
                    returns[0].requireFlag = true;
                }else{
                    returns[0].requireFlag = false;
                }
            }
        };

        // TERMS_UNIT  单位发生改变的时候
        $scope.TermsChangeUnit = function (row, unitList, unit) {

            if( !unitList  ||  0 >= unitList.length){
                return 0 ;
            }


            //操作步骤 1. 当前行的旧主单位 是否存在 从单位,如果存在,先干掉
            //         2. 插入当前的新的主单位对应的从单位

            //1. 当前行的旧主单位 是否存在 从单位,如果存在,先干掉
            for (var i = $scope.dataTableTerms.length - 1; i >= 0; i--) {
                var crrentLine = $scope.dataTableTerms[i];

                if (crrentLine.clauseTreeId && row.clauseTreeId && crrentLine.isMajor
                    && crrentLine.clauseTreeId == row.clauseTreeId && 'N' == crrentLine.isMajor) {

                    if (crrentLine.termsLId) {
                        var vLength = $scope.deleteTtaTerms.filter(function (x) {
                            return x.termsLId ==  crrentLine.termsLId
                        });
                        if(vLength.length == 0){
                            $scope.deleteTtaTerms.push(angular.copy(crrentLine));
                        }

                    }
                    $scope.dataTableTerms.splice(i, 1);
                }
            }


            var index = $scope.dataTableTerms.indexOf(row);
            var crrentUnits = unitList.filter(function (x) {
                return x.unitValue == unit;
            });

            if(0  < crrentUnits.length &&  crrentUnits[0].collectTypeId ){
                row.unitId = crrentUnits[0].collectTypeId ;
                row.unit = crrentUnits[0].unitValue;
            }

            //获取从单位
            var subsidiaryUnits = $scope.ttaTermsUnitAll.filter(function (x) {
                if( 0  < crrentUnits.length &&  crrentUnits[0].collectTypeId && x.parentId){
                    return x.parentId == crrentUnits[0].collectTypeId;
                }else{
                    return false ;
                }
            });
            var newObject = {};

            for (var i = 0; i < subsidiaryUnits.length; i++) {
                newObject =
                    {
                        feeNotax: '',
                        feeIntas: '',
                        yTermsA: '',
                        unit: subsidiaryUnits[i].unitValue,
                        unitList: [],
                        yYear: subsidiaryUnits[i].standardValue,
                        rule : subsidiaryUnits[i].rule,
                        ttaValueRefer:subsidiaryUnits[i].standardValue,
                        referenceStandard: '',
                        collectType: "",
                        clauseTreeId: row.clauseTreeId,
                        incomeType: '',
                        proposalId: row.proposalId,
                        yTerms: '',
                        yTermsEn: '',
                        code:'',
                        pCode:'',
                        relation:row.relation,
                        termCategory:'',
                        incomeTypeList: [],
                        clauseId: row.clauseId,
                        oldClauseTreeId2: row.oldClauseTreeId2,
                        oiType: row.oiType,
                        oldClauseId: row.oldClauseId == undefined ? '' : row.oldClauseId,
                        orderNo: row.orderNo,
                        parentUnitId: row.unitId,
                        unitId:subsidiaryUnits[i].collectTypeId,
                        isMajor: 'N'
                    };
                $scope.dataTableTerms.splice(index + 1, 0, newObject);
            }

            row.isMajor = 'Y';
            row.termsSystem = '';
            row.feeIntas = '';
            row.feeNotax = '';
            // //退货   根据 集中折扣变化
            // if(  (row.yTerms == $scope.getCenter[0].meaning || row.yTerms == $scope.getReturn[0].meaning) && row.incomeType){
            //     $scope.returnCount();
            // }

            //$scope.termsLSaveAll(row);
            return  subsidiaryUnits.length;

        };

        $scope.currentMethodFun = function (row) {
            return $scope.dataTableTerms.filter(function (x) {
                var flag = false;
                switch ($scope.contractParams.oldYear) {
                    //没有往年的时候
                    case 0:
                        if (x.clauseId && row.clauseId && (x.clauseId == row.clauseId)) {
                            flag = true;
                        }
                        break;
                    case 1:
                        if (x.oldClauseId && row.oldClauseId && (x.oldClauseId == row.oldClauseId)) {
                            flag = true;
                        }
                        break;
                    default:
                        null;
                }
                return flag;
            });
        };

        $scope.moveCurrentRow = function (currentMethod,row,values,rowDate,rowValue,valuesNoCopy){

            //不存在往年的时候
            if(0 == $scope.contractParams.oldYear){
                //获取当前行的位置
                var  times =   $scope.dataTableTerms.indexOf(currentMethod[0]);
                var  rowCopy = angular.copy(row) ;
                var  newObject = {};

                for (var i = 0 ;i<currentMethod.length;i++){
                    if(currentMethod[i].termsLId){
                        var vLength = $scope.deleteTtaTerms.filter(function (x) {
                            return x.termsLId ==  currentMethod[i].termsLId
                        });
                        if(vLength.length == 0){
                            $scope.deleteTtaTerms.push(angular.copy(currentMethod[i])) ;
                        }

                    }
                    $scope.dataTableTerms.splice(times,1);
                }

                for(var i = 0 ;i<rowDate.length;i++){

                    //当前操作获取收取方式对应的单位
                    var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                        var pId = rowDate[i].value;
                        return x.clauseId == pId;
                    });

                    newObject = {
                        clauseId :rowCopy.clauseId,
                        termsHId :rowCopy.termsHId,
                        yTerms   :rowCopy.yTerms,
                        yTermsEn :rowCopy.yTermsEn,
                        code:rowCopy.code,
                        pCode:rowCopy.pCode,
                        relation :   rowCopy.relation,
                        termCategory:rowCopy.termCategory,
                        proposalId :rowCopy.proposalId,
                        yYear:rowDate[i].ttaValue,
                        referenceStandard:rowDate[i].defaultValue,
                        orderNo  :rowDate[i].orderNo,
                        clauseTreeId :rowDate[i].value,
                        incomeTypeList :rowCopy.incomeTypeList,
                        unitList :findUnit,
                        unit:findUnit.length == 1? findUnit[0].unitValue:'',
                        unitId:findUnit.length == 1? findUnit[0].collectTypeId:'',
                        rule:findUnit.length == 1? findUnit[0].rule:'',
                        oiType :rowCopy.oiType,
                        incomeType :rowDate[i].name

                    };
                    $scope.dataTableTerms.splice(times+i,0,newObject);
                    var currentRow = $scope.dataTableTerms[times+i] ;
                    //增加从单位
                    var addTimes = $scope.TermsChangeUnit(currentRow,currentRow.unitList, currentRow.unit);
                    times = times + addTimes ;
                }

            }else{
                var rowCopy = angular.copy(row) ;

                //2018有的收取方式  就复制
                var times = 0 ;
                var  deleteV = [] ;
                //    flagValues   当前条框名目的对应的所有历史 的行
                //   valuesNoCopy  当前条框名目下的所有收取方式
                var  spaceVlaue  =  [] ;
                for (var s = 0 ;s<currentMethod.length;s++){
                    if(!currentMethod[s].oldClauseTreeId){
                        spaceVlaue.push({'id':currentMethod[s].$$hashKey,'clauseTreeId':currentMethod[s].clauseTreeId}) ;
                    }
                }
                angular.forEach(currentMethod, function(iterms,index,array){
                    for (var i = 0; i < valuesNoCopy.length; i++) {

                        var spaceResult = spaceVlaue.filter(function (x) {
                            return x.clauseTreeId == valuesNoCopy[i].value;
                        });

                        if( (iterms.oldClauseTreeId && valuesNoCopy[i].oldClauseTreeId2 && ( iterms.oldClauseTreeId == valuesNoCopy[i].oldClauseTreeId2 ) )
                            ||  (!iterms.oldClauseTreeId  && spaceResult.length>0 &&  spaceResult[0].id == iterms.$$hashKey)     ){

                            var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                                var pId = valuesNoCopy[i].value;
                                return x.clauseId == pId;
                            });
                            var unit = findUnit.length == 1? findUnit[0].unitValue:'';
                            iterms.feeNotax= '';
                            iterms.feeIntas='';
                            iterms.yTermsA= '';
                            iterms.unit= unit;
                            iterms.unitId= findUnit.length == 1? findUnit[0].collectTypeId:'';
                            iterms.rule = findUnit.length == 1? findUnit[0].rule:'';
                            iterms.unitList =findUnit;
                            iterms.yYear= valuesNoCopy[i].ttaValue;
                            iterms.referenceStandard =valuesNoCopy[i].defaultValue ;
                            iterms.collectType="";
                            iterms.clauseTreeId= valuesNoCopy[i].value;
                            iterms.oldClauseTreeId2=valuesNoCopy[i].oldClauseTreeId2;
                            iterms.incomeType= valuesNoCopy[i].name;
                            iterms.proposalId = rowCopy.proposalId;
                            iterms.yTerms= rowCopy.yTerms;
                            iterms.yTermsEn= rowCopy.yTermsEn;
                            iterms.code= rowCopy.code;
                            iterms.pCode= rowCopy.pCode;
                            iterms.relation = rowCopy.relation;
                            iterms.termCategory =  rowCopy.termCategory ;
                            iterms.incomeTypeList= rowCopy.incomeTypeList;
                            iterms.clauseId= rowCopy.clauseId;
                            iterms.oldClauseId= rowCopy.oldClauseId==undefined?'':rowCopy.oldClauseId;
                            iterms.oiType = rowCopy.oiType;
                            iterms.orderNo=valuesNoCopy[i].orderNo;
                            deleteV.push(valuesNoCopy[i]);
                            //deleteV.sort(function(a, b){return b - a});

                            $scope.TermsChangeUnit(iterms,iterms.unitList, iterms.unit);

                        }
                    }
                    times = $scope.dataTableTerms.indexOf(iterms) ;
                });
                //valuesNoCopy  当前条框名目下的所有收取方式  操作：删除已经存在的对应的历史收取方式
                for (var i =0;i<deleteV.length;i++){
                    var index = valuesNoCopy.indexOf(deleteV[i]);
                    valuesNoCopy.splice(index,1) ;
                }
                if(!row.oldClauseId){
                    times = $scope.dataTableTerms.indexOf(row);

                    if(row.termsLId){
                        var vLength = $scope.deleteTtaTerms.filter(function (x) {
                            return x.termsLId ==  row.termsLId
                        });
                        if(vLength.length==0){
                            $scope.deleteTtaTerms.push(angular.copy(row)) ;
                        }

                    }
                    $scope.dataTableTerms.splice(times,1);
                }
                //操作的目的是  是不是同层显示(【"1" == values[0].flag 】lines-1704），如果是  所有收取方式都为一行
                //valuesNoCopy  当前条框名目下的所有收取方式  操作：删除已经存在的对应的历史收取方式，剩下的就是新增了
                angular.forEach(valuesNoCopy, function(iterms,index,array){

                    var findUnit = $scope.ttaTermsUnit.filter(function (x) {
                        var pId = iterms.value;
                        return x.clauseId == pId;
                    });
                    var unit = findUnit.length == 1? findUnit[0].unitValue:'';
                    var newObject =
                        {
                            feeNotax: '',
                            feeIntas: '',
                            yTermsA: '',
                            unit: unit,
                            unitId: findUnit.length == 1? findUnit[0].collectTypeId:'',
                            rule:findUnit.length == 1? findUnit[0].rule:'',
                            unitList :findUnit,
                            yYear: iterms.ttaValue,
                            referenceStandard :iterms.defaultValue ,
                            collectType: "",
                            clauseTreeId: iterms.value,
                            incomeType: iterms.name,
                            proposalId:rowCopy.proposalId,
                            yTerms: rowCopy.yTerms,
                            yTermsEn : rowCopy.yTermsEn,
                            code : rowCopy.code,
                            pCode : rowCopy.pCode,
                            relation:rowCopy.relation,
                            termCategory:rowCopy.termCategory,
                            incomeTypeList: row.incomeTypeList,
                            clauseId: rowCopy.clauseId,
                            oldClauseTreeId2:iterms.oldClauseTreeId2,
                            oiType:rowCopy.oiType,
                            oldClauseId: rowCopy.oldClauseId==undefined?'':rowCopy.oldClauseId,
                            orderNo:iterms.orderNo
                        };

                    $scope.dataTableTerms.splice(times + index, 0, newObject);
                    var currentRow = $scope.dataTableTerms[times+i] ;
                    var addTimes = $scope.TermsChangeUnit(currentRow,currentRow.unitList, currentRow.unit);
                    times = addTimes + times ;
                });
            }

        };
        $scope.confirmTremsBefore = function (){
            //计算好比例以及费用
            $scope.termsCountAmount('2');

            // 计算好的比例以及费用进行赋值
            // $scope.termsTtaSave('2') ;
        };
        //tta terms 确认
        $scope.confirmTrems = function (form) {

            if(!validateForm(form)){
                return;
            }

            //var endContractParams = JSON.stringify($scope.contractParams);
            //var  endDataTableTerms = JSON.stringify($scope.dataTableTerms);
            if(!$scope.startContractParams || !$scope.startDataTableTerms){
                SIEJS.alert("请先保存 or 没有行数据", "error", "确定");
                return ;
            }
            //判断是不是 红票 B   为红票
            if($scope.contractParams.invoiceType  && 'B' == $scope.contractParams.invoiceType){
                $scope.confirmTremsBefore();
            }
            //确认前先保存一遍
            $scope.saveTrems(form,'2');


        };


        //tta terms 取消确认
        $scope.CancelConfirmTrems = function () {
            httpServer.post(URLAPI.termsLCancelComfirm,
                {params: JSON.stringify({
                        proposalId: $scope.contractParams.proposalId,
                        termsHId:$scope.contractParams.termsHId
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.contractParams.isTermsConfirm = 'N';
                        $scope.searchTerms();
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
        };

        //tta terms 往年实际费用计算
        $scope.termsCountAmountFee = function () {
            httpServer.post(URLAPI.termsLCountFee,
                {params: JSON.stringify({
                        proposalId: $scope.contractParams.proposalId,
                    })},
                function (res) {
                    if (res.status == "S" && res.data.xFlag ==1) {
                        $scope.contractParams.isTermsConfirm = 'Y';
                        SIEJS.alert("往年费用计算成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.data.xMsg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };

        //选择部门
        $scope.getDeptCode = function () {
            //  $scope.areaComponent = e;
            if(! $scope.params.majorDeptId){
                SIEJS.alert('请先选择PROPOSAL 的部门', "error", "确定");
                return ;
            }
            $('#selectDeptId').modal('toggle')
        };

        //点击确认按钮
        $scope.selectDeptReturn = function (key, value, currentList) {
            $scope.contractParams.deptCode = currentList[0].departmentCode;
            $scope.contractParams.deptDesc = currentList[0].departmentName;
            $scope.contractParams.deptId = -1;

        };

        //清除值
        $scope.termsCancel = function (value){
            if('1' == value){
                $scope.contractParams.salesArea = '';
            }else{
                $scope.contractParams.warehouseDesc = '';
                $scope.contractParams.warehouseCode = '';
            }

        };
        function keepTwoDecimalFull(num) {
            var result = parseFloat(num);
            if (isNaN(result)) {
                alert('传递参数错误，请检查！');
                return false;
            }
            result = Math.round(num * 100) / 100;
            var s_x = result.toString();
            var pos_decimal = s_x.indexOf('.');
            if (pos_decimal < 0) {
                pos_decimal = s_x.length;
                s_x += '.';
            }
            while (s_x.length <= pos_decimal + 2) {
                s_x += '0';
            }
            return s_x;
        }
        $scope.saveRefreshNewbreedE  = function (){
            httpServer.post(URLAPI.deleteNBExtend,
                {params: JSON.stringify({
                        hId: $scope.nbe.newbreedExtendHId,
                    })},
                function (res) {
                    if (res.status == "S" ) {
                        SIEJS.alert("刷新成功", "success", "确定");
                        $scope.searchNBExtent();
                    } else {
                        SIEJS.alert(res.data.xMsg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        };
        //动态获取参数
        $scope.executeParams = function (row, selectFlag, object) {
            if (row.rule) {
                switch (row.rule) {
                    case 'CN01':
                        if (row.incomeType) {
                            var reg = /^((\d+\.\d{0,1}[1-9])|(\d+\.\d*[1-9]\d{1})|(\d*[1-9]\d*\.\d{2}))$/;
                            //var reg =(^[1-9](\d+)?(\.\d{1,2})?$)|(^\d\.\d{1,2}$);
                            if (row.yYear) {
                                row.yYear = keepTwoDecimalFull(row.yYear);
                                if (reg.test(row.yYear) == true) {
                                    row.yYear = keepTwoDecimalFull(row.yYear);
                                } else {
                                    var v = $scope.lookupCodeParty.filter(function (x) {
                                        return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN01';
                                    });
                                    SIEJS.alert(v[0].meaning, 'error');
                                    return;
                                }

                            } else if (object && object.target.value) {
                                var changeValue = object.target.value;
                                changeValue = keepTwoDecimalFull(changeValue);
                                if (reg.test(changeValue) == true) {
                                    row.yYear = changeValue;
                                } else {
                                    var v = $scope.lookupCodeParty.filter(function (x) {
                                        return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN01';
                                    });
                                    SIEJS.alert(v[0].meaning, 'error');
                                    return;
                                }
                            } else {
                                var v = $scope.lookupCodeParty.filter(function (x) {
                                    return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN01';
                                });
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN02':
                        if (row.incomeType) {
                            var reg = /^[1-9]\d*$/;
                            if (reg.test(row.yYear) == false) {
                                var v = $scope.lookupCodeParty.filter(function (x) {
                                    return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN02';
                                });
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN03':
                        var v = $scope.lookupCodeParty.filter(function (x) {
                            return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN03';
                        });
                        var reg = /^[1-9]\d*$/;
                        if (reg.test(row.yYear) == false) {
                            SIEJS.alert(v[0].meaning, 'error');
                            return;
                        } else {
                            if (Number(row.yYear) > Number(row.ttaValueRefer)) {
                                row.yYear = '';
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN04':
                        if (row.incomeType) {
                            var reg = /^(([1-9]\d+)|[1-9])(\.\d{1,2})?$/;
                            if (reg.test(row.yYear) == false) {
                                var v = $scope.lookupCodeParty.filter(function (x) {
                                    return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN04';
                                });
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN05':
                        if (row.yYear) {
                            var reg = /^((\d+\.\d{0,1}[1-9])|(\d+\.\d*[1-9]\d{1})|(\d*[1-9]\d*\.\d{2}))$/;
                            if (reg.test(row.yYear) == false) {
                                var v = $scope.lookupCodeParty.filter(function (x) {
                                    return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN05';
                                });
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN06':
                        var v = $scope.lookupCodeParty.filter(function (x) {
                            return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN06';
                        });
                        var reg = /^[1-9]\d*$/;
                        if ( row.yYear == undefined) {
                            SIEJS.alert(v[0].meaning, 'error');
                            return;
                        } else if (row.yYear) {
                            if (reg.test(row.yYear) == false) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            } else {
                                if (Number(row.yYear) > Number(row.ttaValueRefer)) {
                                    row.yYear = '';
                                    SIEJS.alert(v[0].meaning, 'error');
                                    return;
                                }
                            }
                        }
                        break;
                    case 'CN08':
                        var v = $scope.lookupCodeParty.filter(function (x) {
                            return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN08';
                        });
                        if (row.yYear) {
                            var reg = /^[1-9]\d*$/;
                            if (reg.test(row.yYear) == false) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        } else {
                            if (row.yYear == undefined) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN09':
                        var v = $scope.lookupCodeParty.filter(function (x) {
                            return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN09';
                        });
                        if (row.yYear) {
                            var reg = /^[1-9]\d*$/;
                            if (reg.test(row.yYear) == false) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            } else {
                                if (Number(row.yYear) > Number(row.ttaValueRefer)) {
                                    row.yYear = '';
                                    SIEJS.alert(v[0].meaning, 'error');
                                    return;
                                }
                            }
                        } else {
                            if (row.yYear == undefined) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    case 'CN10':
                        var v = $scope.lookupCodeParty.filter(function (x) {
                            return x.lookupType == 'TTA_TERMS_VALUE_CONTROL' && x.lookupCode == 'CN10';
                        });
                        if (row.yYear) {
                            var reg = /^[1-9]\d*$/;
                            if (reg.test(row.yYear) == false) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        } else {
                            if (row.yYear == undefined ) {
                                SIEJS.alert(v[0].meaning, 'error');
                                return;
                            }
                        }
                        break;
                    default:
                        console.error('TTA VALUE 没有匹配到');
                }
            }

            //

            if ((row.yYear || row.yYear == '0') && !row.incomeType && row.isMajor == 'Y') {
                row.yYear = '';
                SIEJS.alert('请选择收取方式', 'error');
            }
            var currentRow = {};
            var currentRows = [];
            var indexOf = 0;
            var flag = false;
            var ttaAll = [];

            //获取所有的主从 TTA
            var data = null;
            if (!row.isMajor || 'Y' == row.isMajor) {
                for (var i = 0; i < $scope.dataTableTerms.length; i++) {
                    data = $scope.dataTableTerms[i];
                    if (data.parentUnitId && row.unitId == data.parentUnitId) {
                        ttaAll.push(data.yYear);
                    }
                }

                currentRow = row;
            } else {
                currentRows = $scope.dataTableTerms.filter(function (x) {
                    return x.unitId == row.parentUnitId;
                });
                currentRow = currentRows[0];
                for (var i = 0; i < $scope.dataTableTerms.length; i++) {
                    data = $scope.dataTableTerms[i];
                    if (data.parentUnitId && currentRow.unitId == data.parentUnitId) {
                        ttaAll.push(data.yYear);
                    }
                }
            }
            // ttaAll.push(currentRow.yYear) ;

            currentRow.termsHId = $scope.contractParams.termsHId;
            if (!currentRow.yYear) {
                currentRow.feeIntas = '';
                currentRow.feeNotax = '';
                currentRow.termsSystem = '';
                currentRow.yYearOld = '';
                return;
            }
            if ('1' == selectFlag) {
                currentRow.yYearOld = currentRow.yYear;
            }

            var saves = [];
            if (currentRow === row) {
                saves.push(currentRow);
            } else {
                saves.push(currentRow);
                saves.push(row);
            }
            httpServer.post(URLAPI.termsLSaveAll,
                {
                    params: JSON.stringify({
                        proposalId: $scope.contractParams.proposalId,
                        hId: $scope.contractParams.termsHId,
                        beoiTax: $scope.contractParams.beoiTax,
                        del: $scope.deleteTtaTerms,
                        save: saves
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deleteTtaTerms = [];
                        var rowIsMajor = res.data.filter(function (x) {
                            return !x.isMajor || x.isMajor == 'Y';
                        });
                        if (rowIsMajor.length != 1) {
                            SIEJS.alert(' TTA TERMS 获取主单位ROW 失败', "error", "确定");
                            return;
                        }
                        httpServer.post(URLAPI.termsLfindValue,
                            {
                                params: JSON.stringify({
                                    clauseTreeId: rowIsMajor[0].clauseTreeId,
                                    termsLId: rowIsMajor[0].termsLId,
                                    proposalId: $scope.contractParams.proposalId,
                                    oldClauseTreeId: rowIsMajor[0].oldClauseTreeId2,
                                    ttaAlls: ttaAll
                                })
                            },
                            function (res) {
                                if (res.status == 'S') {
                                    if (0 != res.data.fee) {

                                        $scope.getTermsFreeIt = $scope.getTermsFree.filter(function (x) {
                                            return currentRow.incomeType && x.meaning == currentRow.incomeType && (x.lookupCode == '01' || x.lookupCode == '02');
                                        });
                                        $scope.getTermsFreeYterms = $scope.getTermsFree.filter(function (x) {
                                            return currentRow.yTerms && currentRow.yTerms.indexOf(x.meaning) != -1 && (x.lookupCode == '03');
                                        });
                                        if ($scope.getCurrentTermsCat && $scope.getCurrentTermsCat.length > 0) {
                                            if (currentRow.termCategory == $scope.getCurrentTermsCat[0].lookupCode) {
                                                //当前税率为  6
                                                if ($scope.getTermsFreeIt.length > 0 && $scope.getTermsFreeYterms.length > 0) {
                                                    currentRow.feeNotax = res.data.fee;
                                                    currentRow.feeIntas = res.data.fee * ((100 + Number($scope.getCurrentTax[0].meaning)) / 100);
                                                    currentRow.feeIntas = currentRow.feeIntas.toFixed(0);
                                                    currentRow.feeNotax = currentRow.feeNotax.toFixed(0);
                                                } else {
                                                    currentRow.feeIntas = res.data.fee;
                                                    currentRow.feeNotax = res.data.fee / ((100 + Number($scope.getCurrentTax[0].meaning)) / 100);
                                                    currentRow.feeNotax = currentRow.feeNotax.toFixed(0);
                                                    currentRow.feeIntas = currentRow.feeIntas.toFixed(0);
                                                }
                                            } else {
                                                //当前税率为  beoiTax
                                                if ($scope.getTermsFreeIt.length > 0 && $scope.getTermsFreeYterms.length > 0) {
                                                    currentRow.feeNotax = res.data.fee;
                                                    currentRow.feeIntas = res.data.fee * ((100 + Number($scope.contractParams.beoiTax)) / 100);
                                                    currentRow.feeIntas = currentRow.feeIntas.toFixed(0);
                                                    currentRow.feeNotax = currentRow.feeNotax.toFixed(0);
                                                } else {
                                                    currentRow.feeIntas = res.data.fee;
                                                    currentRow.feeNotax = res.data.fee / ((100 + Number($scope.contractParams.beoiTax)) / 100);
                                                    currentRow.feeNotax = currentRow.feeNotax.toFixed(0);
                                                    currentRow.feeIntas = currentRow.feeIntas.toFixed(0);
                                                }

                                            }
                                        } else {
                                            SIEJS.alert('TMERS 获取税率错误，请刷新界面从新尝试', "error", "确定");
                                            return;
                                        }

                                    } else {
                                        currentRow.feeIntas = '';
                                        currentRow.feeNotax = '';
                                    }
                                    currentRow.termsSystem = res.data.clauseText;


                                    //退货   根据 集中折扣变化
                                    if ((currentRow.yTerms == $scope.getCenter[0].meaning) ) {
                                        if (currentRow.incomeType) {
                                            $scope.returnCount(true);
                                        }else{
                                            $scope.returnCount(false);
                                        }

                                    }
                                    var returnFee = $scope.dataTableTerms.filter(function (x) {
                                        return x.yTerms == '目标退佣';
                                    });
                                    if (currentRow.pCode && returnFee[0].code
                                        && currentRow.pCode == returnFee[0].code && currentRow.incomeType
                                        && currentRow.incomeType.indexOf('含税') > -1
                                        && currentRow.incomeType.indexOf('不含税') == -1) {
                                        var returnFees = $scope.dataTableTerms.filter(function (x) {
                                            return x.pCode && x.pCode == returnFee[0].code && x.incomeType && x.incomeType.indexOf('不含税') > -1 && x.clauseTreeId != currentRow.clauseTreeId && currentRow.clauseId == x.clauseId;
                                        });
                                        if (returnFees.length > 0) {
                                            returnFees[0].yYear = (currentRow.yYear / ((100 + Number($scope.contractParams.beoiTax)) / 100)).toFixed(0);
                                            $scope.executeParams(returnFees[0], '1', null);
                                        }

                                    }
                                } else {

                                    SIEJS.alert(res.msg, "error", "确定");
                                }
                            },
                            function (err) {
                                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                            }
                        );
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {

                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //记录TTA Terms Comparision(TY vs LY)打印信息并打印
        $scope.printComparision = function () {
            httpServer.post(URLAPI.saveOrUpdatePrintCount, {
                    params: JSON.stringify({printType : "TTA_TERMS_COMPARISION", remark : "TTA Terms Comparision(TY vs LY)报表打印"})
                }, function (res) {
                    if (res.status == 'S') {
                        /* var oldstr = document.body.innerHTML;//保存当前页面的HTML
                         var newstr = document.getElementById("printContentId").innerHTML;//得到需要打印的元素HTML
                         document.body.innerHTML = newstr;
                         window.print();
                         document.body.innerHTML = oldstr;*/
                        jQuery("#ComparisionPrintContentId").print({
                            globalStyles:true,
                            mediaPrint:true,
                            iframe:true,
                        });
                        $scope.printCount = res.data;
                    } else {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };


        $scope.printTrade = function () {
            jQuery("#printTradeId").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true,
            })

            /*  httpServer.post(URLAPI.saveOrUpdatePrintCount, {
                      params: JSON.stringify({printType : "TTA_TRADE_TERM", remark : "贸易条款"})
                  }, function (res) {
                      if (res.status == 'S') {
                          /!*var oldstr = document.body.innerHTML;//保存当前页面的HTML
                          var newstr = document.getElementById("tradeClause").innerHTML;//得到需要打印的元素HTML
                          document.body.innerHTML = newstr;
                          window.print();
                          document.body.innerHTML = oldstr;*!/
                          $scope.tradePrintCount = res.data;
                          jQuery("#printTradeId").print({
                              globalStyles:true,
                              mediaPrint:true,
                              iframe:true,
                          })
                      } else {
                          SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                      }
                  }, function (err) {
                      SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                  }
              );*/

        };
        //window.document.body.style.background = "white";
        //window.document.body.style.overflowY = "scroll";

        $scope.getPrintCount = function (printTypeValue) {
            httpServer.post(URLAPI.getPrintCount, {
                    params: JSON.stringify({printType : printTypeValue})
                }, function (res) {
                    if (res.status == 'S') {
                        if ('TTA_TERMS_COMPARISION' == printTypeValue) {
                            $scope.printCount = res.data;
                        } else if ('TTA_TRADE_TERM' == printTypeValue) {
                            $scope.tradePrintCount = res.data;
                        }
                    } else {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //######################按钮控制 start########################################
        /**
         * 全局所有按钮按钮控制
         */
        $scope.btnC = function (currentStatus,btnCValue){
            //提交的时候立马控制按钮 -propopsal
            if('0' === currentStatus){   //propopsal 头的按钮
                if(btnCValue){
                    $("#buttonProposal button").attr("disabled","true");
                }else{
                    $("#buttonProposal button").removeAttr("disabled");
                }
            }
            else if("A"  != $scope.params.status || btnCValue){
                //proposal信息  页签控制
                $("#tabContent input").attr("disabled","true");
                $("#tabContent select").attr("disabled","true");
                $("#tabContent textarea").attr("disabled","true");
                $("#tabContent button").attr("disabled","true");
            }else if(!btnCValue){
                // removeAttr("disabled");
            }
            $("[name='fullWindow']").removeAttr("disabled");
            $("[name='printButton']").removeAttr("disabled");
            $("[name='proposalUpdateVendorName']").removeAttr("disabled");
            $("#brandExportId").removeAttr("disabled");
        };

        /**
         *
         * @param currentStatus
         * @param btnCValue
         */
        $scope.btnDeptFeeHAFormC = function (value) {
            if('Y' == value){ //已经确认
//                $("#deptFeeHAForm select").attr("disabled", "true");
                //  $("#deptFeeHAForm textarea").attr("disabled", "true");
                //  $("#deptFeeHAForm button").attr("disabled", "true");

                $("#btnSaveA").attr("disabled", "true");
                $("#confirmDeptFeeH").attr("disabled", "true");
                $("#CountDeptFeeH").attr("disabled", "true");
                $("#CancelConfirmDeptFeeH").removeAttr("disabled");
                $("#setStandardValueDeptFeeH").attr("disabled","true");
                //  $("#deptFeeHALine input").attr("disabled", "true");
                //  $("#deptFeeHALine select").attr("disabled", "true");
                // $("#deptFeeHALine textarea").attr("disabled", "true");
                // $("#deptFeeHALine button").attr("disabled", "true");
            } else if($scope.params.status &&   ($scope.params.status =='A' || $scope.params.status =='E')){
                $("#CancelConfirmDeptFeeH").attr("disabled", "true");
                $("#btnSaveA").removeAttr("disabled");
                $("#confirmDeptFeeH").removeAttr("disabled");
                $("#CountDeptFeeH").removeAttr("disabled");
                $("#setStandardValueDeptFeeH").removeAttr("disabled");
            }
        };


        //######################按钮控制 end ########################################

        //######################部门协定收费标准 start########################################
        var id = $scope.params.proposalId;
        $scope.deptFeeLADataTable = [];
        $scope.deptFeeLBDataTable = [];
        $scope.deptAccordDataTable= [];
        $scope.deptFeeLADataTableOldDataA= [];
        $scope.deptFeeLADataTableOldDataB= [];
        $scope.deptFeeLAllDataTableOldData = [];
        $scope.deptFeeLAllDataTableOldDataLeaflet = [];

        $scope.areaComponentA = {};
        $scope.areaComponentB = {};

        $scope.paramsA = {};
        $scope.paramsB = {};
        $scope.showDeptAgrInfoBoolean = false;//默认不显示
        $scope.oldProposalInfo = {};

        $scope.deptAgrInfoIndex = 1;

        //清空表格数据
        $scope.clearTableData = function () {
            $scope.deptFeeLADataTable = [];
            $scope.deptFeeLBDataTable = [];
        };
        function pad(num, n) {
            var len = num.toString().length;
            while(len < n) {
                num = "0" + num;
                len++;
            }
            return num;
        }

        $scope.selectPdfAll = function () {
            var url = URLAPI.ttaSideAgrtHeaderDownLoadType + '?functionId=TTA_PROPOSAL_HEADER_PDF&id=' + getId()+"&Certificate="+(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
            window.open(url, [""], [""]);//避开因同源策略而造成的拦截
        };
        //成册PDF查看
        $rootScope.printPdfAll = function (isSubmitFlag) {
        
            if ($scope.userData.userType == '50') {//GM权限直接查看,不考虑有没有生成。
                var url = URLAPI.ttaSideAgrtHeaderDownLoadType + '?functionId=TTA_PROPOSAL_HEADER_PDF&id=' + getId()+"&Certificate="+(sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing');
                window.open(url, [""], [""]);//避开因同源策略而造成的拦截
                return;
            }
            
            //查询是否展示 贸易协议
            httpServer.post(URLAPI.findDeptFeeShowHideByProposal, {
                    'params': JSON.stringify({
                        proposalId:getId()
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        var tradeAgreement = Number(res.data.flag) === 0 ? false :true;
                        var questionAgreement = $scope.paramsA.isHiddenQuestion === 'Y' ? false :true;
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
                        if (isSubmitFlag != '1') {//不是BIC流程页面点击时不需要显示，否则隐藏。
                            loading.show();
                        }
                        $http.post(URLAPI.proposalPDFPrint, 'params=' +encodeURIComponent (JSON.stringify(
                            {
                                orderNo: $scope.params.orderNbr,
                                versionCode:pad($scope.params.versionCode,2),
                                type:'PROPOSAL',
                                isTA:tradeAgreement,
                                isQuestion:questionAgreement,
                                userId: userLoginInfo.userId,
                                proposalId:Number(getId()),
                                Certificate:sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing'
                            })), {
                            transformRequest: angular.identity,
                            headers: {
                                'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                                'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                            }
                        }).success(function (response) {
                            if (response.status == 'S') {
                                //loading.hide();
                                if (isSubmitFlag != '1') {//不是BIC流程页审批面点击时不无需下载。
                                    $scope.selectPdfAll();
                                }
                            } else {
                                //loading.hide();
                                SIEJS.alert(response.msg, 'error', '确定');
                            }
                            if (isSubmitFlag != '1') {
                                loading.hide();
                            }
                        }).error(function(response) {
                            if (isSubmitFlag != '1') {
                                loading.hide();
                            }
                            SIEJS.alert("PDF生成失败", 'error', '确定');
                        });
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    //SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    //console.log("操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员");
                }
            );
        };
        // $scope.printPdfAll = function () {
        //     var pdfCertificate = sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing';
        //     var pdfFileUrl = URLAPI.proposalPDFPrint + '/Certificate/' + pdfCertificate + '/proposalId/' + Number(getId()) + '/userId/' + userLoginInfo.userId + '/orderNo/'+$scope.params.orderNbr+ '/versionCode/'+pad($scope.params.versionCode,2)+ '/type/' + 'PROPOSAL';
        //     sessionStorage.setItem("pdfDownloadParams", JSON.stringify({'url':pdfFileUrl,'title':'RPOPOSAL管理','name':$scope.params.orderNbr+'-'+pad($scope.params.versionCode,2)+".pdf"}));
        //     window.open('app/base/templates/tta/printpdf/proposalPrintPdf.html');
        //     // $http.post(URLAPI.proposalPDFPrint,{'params':JSON.stringify({
        //     //         "Certificate":sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
        //     //         "proposalId":Number(getId()),
        //     //         "userId":userLoginInfo.userId,
        //     //         "type":"PROPOSAL"
        //     //     })},{                transformRequest: function (obj) {
        //     //             var str = [];
        //     //             for (var p in obj) {
        //     //                 str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        //     //             }
        //     //             return str.join("&");
        //     //         },                headers: {
        //     //             'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
        //     //             'Content-Type': 'application/x-www-form-urlencoded'
        //     //         }
        //     //     }
        //     //
        //     // ).success(function (response) {
        //     //     var pdfFile = new Blob([response], {type: 'application/pdf'}); // 使用Blob将PDF Stream 转换为file
        //     //     var pdfFileUrl = window.URL.createObjectURL(pdfFile);
        //     //     window.open(pdfFileUrl);
        //     // }).error(function(response) {
        //     //     SIEJS.alert("下载失败!", 'error', '确定');
        //     // });
        //
        // };
        //查询单据信息
        $scope.searchA = function () {
            $scope.showDeptAgrInfoBoolean = false;
            $scope.proposalId = getId();
            //查询部门协定值,先清除促销陈列费,宣传单张表格数据
            $scope.clearTableData();
            httpServer.post(URLAPI.proposalFindById, {
                    'params': JSON.stringify({proposalId: getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        var value = '';
                        if(res.data.isDepartConfirm && 'Y' == res.data.isDepartConfirm){
                            value = 'Y';
                        }else{
                            value = 'N';
                        }
                        $scope.btnDeptFeeHAFormC(value);
                        $scope.paramsA = res.data;//头信息
                        $scope.searchTermLineByDeptFeeModel();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询TTA_TERMS中的行数据
        $scope.searchTermLineByDeptFeeModel = function(){
            httpServer.post(URLAPI.findTermLineDataByDeptFee, {
                    'params': JSON.stringify({proposalId:  getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptAccordDataTable = res.data;

                        //标识
                        var termCount = 0;
                        $scope.promotionFlag = false;
                        $scope.dmFlag = false;
                        $scope.fylerFlag = false;
                        //当DM,FYLER,促销陈列服务费 条款全为按公司标准的时候,不查询数据
                        for (var index in $scope.deptAccordDataTable) {
                            var row = $scope.deptAccordDataTable[index];
                            if (row.yTerms == undefined) {
                                continue;
                            }

                            if(row.yTerms.indexOf('陈列服务费') >-1 || row.yTerms === "DM" || row.yTerms === "FYLER") {
                                var inV = row.incomeType;//收取方式
                                if (inV == undefined){
                                    continue;
                                }

                                if(inV === "按公司标准"){
                                    termCount++;
                                } else  if (inV === "按其他协定标准") {
                                    if (row.yTerms.indexOf('陈列服务费') >-1) {
                                        row.yTerms = "促销陈列服务费";
                                    }

                                    switch (row.yTerms) {
                                        case  "促销陈列服务费":
                                            $scope.promotionFlag = true;
                                            break;
                                        case "DM":
                                            $scope.dmFlag = true;
                                            break;
                                        case "FYLER":
                                            $scope.fylerFlag = true;
                                            break;
                                    }
                                }
                            }
                        }

                        if (termCount != 3) {
                            if ($scope.promotionFlag && !$scope.dmFlag && !$scope.fylerFlag) {//[促销陈列服务费] 为按其他协定标准;DM,FYLER 为按公司标准
                                $scope.clearTableData();
                                $scope.searchLA();//促销陈列服务费
                            } else if (!$scope.promotionFlag && $scope.dmFlag && !$scope.fylerFlag) {//[DM] 为按其他协定标准;促销陈列服务费,FYLER 为按公司标准
                                $scope.clearTableData();
                                $scope.searchLB("DM");//宣传单费用
                            } else if (!$scope.promotionFlag && !$scope.dmFlag && $scope.fylerFlag ) {//[FYLER] 为按其他协定标准; DM,促销陈列服务费 为按公司标准
                                $scope.clearTableData();
                                $scope.searchLB("FLYER");
                            } else if ($scope.promotionFlag && $scope.dmFlag && !$scope.fylerFlag) {
                                $scope.clearTableData();
                                $scope.searchLA();
                                $scope.searchLB("DM");
                            }else if ($scope.promotionFlag && !$scope.dmFlag && $scope.fylerFlag ) {
                                $scope.clearTableData();
                                $scope.searchLA();
                                $scope.searchLB("FLYER");
                            }else if (!$scope.promotionFlag && $scope.dmFlag && $scope.fylerFlag) {
                                $scope.clearTableData();
                                $scope.searchLB();
                            }else if ($scope.promotionFlag && $scope.dmFlag && $scope.fylerFlag) {
                                $scope.clearTableData();
                                $scope.searchLA();
                                $scope.searchLB();
                            }else if (!$scope.promotionFlag && !$scope.dmFlag && !$scope.fylerFlag) {
                                $scope.clearTableData();
                            }

                            $scope.searchDeptStandardUnit();
                            $scope.searchDeptStandardUnitLeaflet();
                        } else if (termCount == 3) {
                            //清空数据
                            $scope.deptFeeLADataTable = [];
                            $scope.deptFeeLBDataTable = [];
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


        //促销陈列服务费
        $scope.searchLA = function () {
            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                    'params': JSON.stringify({proposalId: getId(),accordType: 'A'})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLADataTable = res.data;

                        if (res.data != null && res.data.length == 0) {//当没有数据的时候
                            for (var index in $scope.deptAccordDataTable) {
                                var row = $scope.deptAccordDataTable[index];

                                if (row.yTerms == undefined) {
                                    continue;
                                }

                                if(row.yTerms.indexOf('陈列服务费') >-1 && (row.incomeType =='按公司标准' || row.incomeType =='按其他协定标准') ) {
                                    var inV = row.incomeType;
                                    if (inV == undefined){
                                        continue;
                                    }

                                    if(inV == "按其他协定标准"){
                                        $scope.CountDeptFeeH('A');//拉取数据(包含),先删除数据,再新增
                                    }
                                }
                            }
                        }

                        var value ='';
                        $timeout(function () {
                            if($scope.paramsA.isDepartConfirm && 'Y' == $scope.paramsA.isDepartConfirm){
                                value = 'Y';
                            }else{
                                value = 'N';
                            }
                            $scope.btnDeptFeeHAFormC(value);
                        }, 0);
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //宣传单张
        $scope.searchLB = function (valParams) {

            //如果valParams 为undefined,那么查询的是全部的宣传单张数据
            var dmFlyerType = valParams == undefined ? "":valParams;
            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                    'params': JSON.stringify({
                        proposalId: getId(),
                        accordType: 'B',
                        dmFlyerType:dmFlyerType
                    })
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLBDataTable = res.data;

                        if (res.data != null && res.data.length == 0) {//没有数据时,去加载快讯及传单的数据
                            for (var index in $scope.deptAccordDataTable) {
                                var row = $scope.deptAccordDataTable[index];
                                if (row.yTerms == undefined) {
                                    continue;
                                }

                                if(row.yTerms == "DM" || row.yTerms == "FYLER") {
                                    var inV = row.incomeType;
                                    if (inV == undefined){
                                        continue;
                                    }

                                    if(inV == "按其他协定标准"){
                                        $scope.CountDeptFeeH('B',dmFlyerType);//拉取数据
                                    }
                                }
                            }
                        }

                        $timeout(function () {
                            var value = '';
                            if($scope.paramsA.isDepartConfirm && 'Y' == $scope.paramsA.isDepartConfirm){
                                value ='Y';
                            }else{
                                value = 'N';
                            }
                            $scope.btnDeptFeeHAFormC(value);
                        }, 0);

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //查询部门协定收费标准促销item设置
        $scope.searchDeptStandardUnit = function() {
            var params = {
                proposalId: getId(),
                majorDeptCode:$scope.params.majorDeptCode,
                proposalYear:$scope.params.proposalYear,
                accordType:"A"
            };

            httpServer.post(URLAPI.ttaDeptFeeLFindSearchPromotionCost, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLAllDataTableOldData = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.searchDeptStandardUnitLeaflet = function() {
            var params = {
                proposalId:getId(),
                majorDeptCode:$scope.params.majorDeptCode,
                proposalYear:$scope.params.proposalYear,
                accordType:"B"
            };

            httpServer.post(URLAPI.ttaDeptFeeLFindSearchPromotionCost, {
                    'params': JSON.stringify(params)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.deptFeeLAllDataTableOldDataLeaflet = res.data;
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //id不为空
        // if ($stateParams.id) {
        /*
                if ($scope.params.proposalId) {
                    //查询头信息
                    $scope.searchA();
                }
        */

        $scope.btnSaveA = function (form1,form2) {
            /* if (invalid) {
                 SIEJS.alert('请检查必填项', 'error', '确定');
                 return;
             }*/

            if(!validateForm(form1)){
                return;
            }
            if(!validateForm(form2)){
                return;
            }

            $scope.paramsA.lineA = $scope.deptFeeLADataTable;
            $scope.paramsA.lineB = $scope.deptFeeLBDataTable;

            httpServer.post(URLAPI.ttaDeptFeeHSave, {
                    'params': JSON.stringify($scope.paramsA)
                },
                function (res) {
                    if (res.status == 'S') {

                        $scope.searchA();
                        SIEJS.alert(res.msg, 'success',"确定");
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        //促销陈列费用模块(部门2或者部门3的协定标准值)
        $scope.getDeptAndUnitV = function(deptCode,areaComponentA) {
            $scope.deptFeeLAllDataTableOldDataA = [];
            for (var i = 0;i<$scope.deptFeeLAllDataTableOldData.length ;i++) {//找出选择的部门数据
                var oldDataRow = $scope.deptFeeLAllDataTableOldData[i] ;
                if (deptCode === oldDataRow.DEPT_CODE) {
                    $scope.deptFeeLAllDataTableOldDataA.push(oldDataRow);
                }

            }

            for(var index in $scope.deptFeeLADataTable) {//往促销陈列费表格中插入数据
                var rowData = $scope.deptFeeLADataTable[index];
                for(var a =0; a < $scope.deptFeeLAllDataTableOldDataA.length;a ++ ) {
                    var aRow = $scope.deptFeeLAllDataTableOldDataA[a];
                    if (rowData.lineCode === aRow.LINE_CODE) {//以此来作为唯一性
                        if (areaComponentA === "2") {
                            rowData.unit2 = aRow.UNITD;
                            rowData.standardValue2 = aRow.STANDARD_VALUED;
                        }else if (areaComponentA === "3") {
                            rowData.unit3 = aRow.UNITD;
                            rowData.standardValue3 = aRow.STANDARD_VALUED;
                        }

                    }
                }

            }
        };

        //宣传单张模块
        $scope.getDeptAndUnitAndLeaflet = function(deptCode,areaComponentA) {
            $scope.deptFeeLAllDataTableOldDataB = [];
            for (var i = 0;i<$scope.deptFeeLAllDataTableOldDataLeaflet.length ;i++) {//找出选择的部门数据
                var oldDataRow = $scope.deptFeeLAllDataTableOldDataLeaflet[i] ;
                if (deptCode === oldDataRow.DEPT_CODE) {
                    $scope.deptFeeLAllDataTableOldDataB.push(oldDataRow);
                }

            }

            for(var index in $scope.deptFeeLBDataTable) {//往宣传单促销费表格插入数据
                var rowData = $scope.deptFeeLBDataTable[index];
                for(var a =0; a < $scope.deptFeeLAllDataTableOldDataB.length;a ++ ) {
                    var aRow = $scope.deptFeeLAllDataTableOldDataB[a];
                    if (rowData.lineCode === aRow.LINE_CODE && rowData.unit === aRow.UNITD) {//以此来作为唯一性
                        if (areaComponentA === "2") {
                            rowData.unit2 = aRow.UNITD;
                            rowData.standardValue2 = aRow.STANDARD_VALUED;
                        }else if (areaComponentA === "3") {
                            rowData.unit3 = aRow.UNITD;
                            rowData.standardValue3 = aRow.STANDARD_VALUED;
                        }

                    }
                }

            }
        };

        //部门协定计算
        $scope.CountDeptFeeH = function (synbom,dmFlyerType) {
            //var msMsg = "计算部门协定收费标准信息";
            //if($scope.deptFeeLBDataTable!=null||$scope.deptFeeLADataTable!=null){
            //    msMsg = "计算部门协定收费标准信息前行信息将清空！";
            //}

            //SIEJS.confirm('提示', msMsg, '确定', function () {

            var dmFlyerTypeC = dmFlyerType == undefined ? "":dmFlyerType;
            httpServer.post(URLAPI.ttaDeptFeeHCount,
                {params: JSON.stringify({
                        proposalId: getId(),
                        accordType: synbom
                    })},
                function (res) {
                    if (res.status == "S") {

                        var xFlag = res.data.xFlag;
                        var xMsg = res.data.xMsg;
                        if (xFlag != 1) {
                            SIEJS.alert(xMsg, "error", "确定");
                            //console.log("错误信息" + xMsg);
                            return;
                        }

                        if (synbom ==='A') {
                            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                                    'params': JSON.stringify({proposalId: getId(),accordType: 'A'})
                                    ,pageRows: 1000,
                                    pageIndex: 1
                                },
                                function (res) {
                                    if (res.status == 'S') {
                                        $scope.deptFeeLADataTable = res.data;

                                        var value ='';
                                        $timeout(function () {
                                            if($scope.paramsA.isDepartConfirm && 'Y' == $scope.paramsA.isDepartConfirm){
                                                value = 'Y';
                                            }else{
                                                value = 'N';
                                            }
                                            $scope.btnDeptFeeHAFormC(value);
                                        }, 0);
                                    } else {
                                        SIEJS.alert(res.msg, "error", "确定");
                                    }
                                },
                                function (err) {
                                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                                }
                            );
                        }else if (synbom === 'B') {
                            httpServer.post(URLAPI.ttaDeptFeeLFind, {
                                    'params': JSON.stringify({
                                        proposalId: getId(),
                                        accordType: 'B',
                                        dmFlyerType:dmFlyerTypeC
                                    })
                                    ,pageRows: 1000,
                                    pageIndex: 1
                                },
                                function (res) {
                                    if (res.status == 'S') {
                                        $scope.deptFeeLBDataTable = res.data;

                                        $timeout(function () {
                                            var value = '';
                                            if($scope.paramsA.isDepartConfirm && 'Y' == $scope.paramsA.isDepartConfirm){
                                                value ='Y';
                                            }else{
                                                value = 'N';
                                            }
                                            $scope.btnDeptFeeHAFormC(value);
                                        }, 0);

                                    } else {
                                        SIEJS.alert(res.msg, "error", "确定");
                                    }
                                },
                                function (err) {
                                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                                }
                            );
                        }
                        //$scope.searchA();

                        //SIEJS.alert("处理成功", "success", "确定");

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
            //})
        };

        //选择部门
        $scope.getDeptCodeA = function (e) {
            $scope.areaComponentA = e;
            $('#deptFeeALov').modal('toggle')
        };

        //点击确认按钮
        $scope.selectDeptFeeAReturn = function (key, value, currentList) {
            if($scope.areaComponentA=="1") {
                $scope.paramsA.deptCode1 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc1 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="2") {
                if(currentList[0].departmentCode == $scope.paramsA.deptCode1) {
                    SIEJS.alert("部门2与部门1的值不能相同", "warning", "确定");
                    return;
                }

                $scope.paramsA.deptCode2 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc2 = currentList[0].departmentName;
                $scope.paramsA.isTransdepart = 'Y' ;

                $scope.getDeptAndUnitV($scope.paramsA.deptCode2,"2");
                $scope.getDeptAndUnitAndLeaflet($scope.paramsA.deptCode2,"2");

            }else if($scope.areaComponentA=="3") {
                if(currentList[0].departmentCode == $scope.paramsA.deptCode1) {
                    SIEJS.alert("部门3与部门1的值不能相同", "warning", "确定");
                    return;
                }

                if(currentList[0].departmentCode == $scope.paramsA.deptCode2) {
                    SIEJS.alert("部门3与部门2的值不能相同", "warning", "确定");
                    return;
                }

                $scope.paramsA.deptCode3 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc3 = currentList[0].departmentName;
                $scope.paramsA.isTransdepart = 'Y' ;

                $scope.getDeptAndUnitV($scope.paramsA.deptCode3,"3");
                $scope.getDeptAndUnitAndLeaflet($scope.paramsA.deptCode3,"3");
            }else if($scope.areaComponentA=="4") {
                $scope.paramsA.deptCode4 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc4 = currentList[0].departmentName;
            }else if($scope.areaComponentA=="5") {
                $scope.paramsA.deptCode5 = currentList[0].departmentCode;
                $scope.paramsA.deptDesc5 = currentList[0].departmentName;
            }

        };

        //清除值
        $scope.paramsACancel = function (value) {
            if ('2' == value) {
                if(!$scope.paramsA.deptCode3){
                    $scope.paramsA.isTransdepart = 'N' ;
                }
                $scope.paramsA.deptCode2 = '';
                $scope.paramsA.deptDesc2 = '';
            } else if ('3' == value) {
                if(!$scope.paramsA.deptCode2){
                    $scope.paramsA.isTransdepart = 'N' ;
                }
                $scope.paramsA.deptCode3 = '';
                $scope.paramsA.deptDesc3 = '';
            }

        };

        //部门协定确认
        $scope.confirmDeptFeeH = function (form1,form2) {

            if(!validateForm(form1)){
                return;
            }
            if(!validateForm(form2)){
                return;
            }

            SIEJS.confirm('提示', '确认的部门协定收费标准信息？', '确定', function () {
                httpServer.post(URLAPI.ttaDeptFeeHConfirm,
                    {params: JSON.stringify({proposalId: $scope.paramsA.proposalId})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.btnDeptFeeHAFormC('Y');//按钮控制
                            //var xFlag = res.data.xFlag;
                            //var xMsg = res.data.xMsg;
                            //if (xFlag != 1) {
                            //    SIEJS.alert(xMsg, "error", "确定");
                            //    return;
                            //}
                            //
                            //$scope.searchBrandPlnL();
                            //$scope.searchA();
                            // $scope.searchB();
                            //SIEJS.alert("处理成功", "success", "确定");

                            $scope.btnSaveA(form1,form2);

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

        //部门协定取消确认
        $scope.CancelConfirmDeptFeeH = function () {

            SIEJS.confirm('提示', '取消部门协定收费标准信息？', '确定', function () {
                httpServer.post(URLAPI.ttaDeptFeeHCancelConfirm,
                    {params: JSON.stringify({proposalId: $scope.paramsA.proposalId})},
                    function (res) {
                        if (res.status == "S") {
                            $scope.btnDeptFeeHAFormC('N');//按钮控制
                            //var xFlag = res.data.xFlag;
                            //var xMsg = res.data.xMsg;
                            //if (xFlag != 1) {
                            //    SIEJS.alert(xMsg, "error", "确定");
                            //    return;
                            //}
                            //
                            //$scope.searchBrandPlnL();
                            $scope.searchA();
                            // $scope.searchB(); 方法未实现
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

        //设置0值
        $scope.setStandardValue = function(){
            if($scope.paramsA.isDepartConfirm && 'Y' == $scope.paramsA.isDepartConfirm){
                SIEJS.alert('部门协定标准已为确认状态,不能设置协定标准值为0', "error", "确定");
                return;
            }

            SIEJS.confirm('提示', '你确定要设置协定标准值为0吗？', '确定', function () {
                if (($scope.deptFeeLADataTable && $scope.deptFeeLADataTable.length > 0) || ($scope.deptFeeLBDataTable && $scope.deptFeeLBDataTable.length > 0) ) {
                    for (var value in $scope.deptFeeLADataTable) {
                        var rowData = $scope.deptFeeLADataTable[value];
                        if (rowData.checked != '-1') {
                            continue;
                        }
                        if ($scope.paramsA.deptDesc1) {
                            rowData.standardValue1 = 0;
                        }
                        if ($scope.paramsA.deptDesc2) {
                            rowData.standardValue2 = 0;
                        }
                        if ($scope.paramsA.deptDesc3) {
                            rowData.standardValue3 = 0;
                        }
                    }

                    for (var index in $scope.deptFeeLBDataTable) {
                        var rowData = $scope.deptFeeLBDataTable[index];

                        if (rowData.checked != '-1') {
                            continue;
                        }
                        if ($scope.paramsA.deptDesc1) {
                            rowData.standardValue1 = 0;
                        }
                        if ($scope.paramsA.deptDesc2) {
                            rowData.standardValue2 = 0;
                        }
                        if ($scope.paramsA.deptDesc3) {
                            rowData.standardValue3 = 0;
                        }
                    }
                } else {
                    SIEJS.alert("当前促销服务陈列费和DM&Flyer没有数据,设置协定标准为0失败", "error", "确定");
                }
            })
        };


        //往年部门协定收费标准信息展示  ========================start============================================
        $scope.showOldYearDeptAgreement = function() {
            if (!$scope.showDeptAgrInfoBoolean) {
                $scope.showDeptAgrInfoBoolean = true;

            }else {
                $scope.showDeptAgrInfoBoolean = false;
            }

            if ($scope.showDeptAgrInfoBoolean == true) {
                if ($scope.deptAgrInfoIndex === 1) {//默认首次进来
                    httpServer.post(URLAPI.ttaDeptFeeLFindByOldYear, {
                            'params': JSON.stringify({
                                proposalId: getId(),
                                proposalYear:parseInt($scope.params.proposalYear) - 1,
                                vendorNbr:$scope.params.vendorNbr,
                                status:$scope.params.status,
                                newOrExisting:$scope.params.newOrExisting
                            })
                        },
                        function (res) {
                            if (res.status == 'S') {
                                $scope.oldProposalInfo = res.data.proposalHeader;
                                $scope.deptFeeLADataTableOldDataA= res.data.deptFeeLADataTableByOld;
                                $scope.deptFeeLADataTableOldDataB= res.data.deptFeeLBDataTableByOld;
                                $scope.deptAgrInfoIndex ++;
                            } else {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                        },
                        function (err) {
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        }
                    );
                }
            }
        };

        //往年部门协定收费标准信息展示  ========================end============================================

        //######################部门协定收费标准 end########################################

        $scope.byAsc = function (name) {
            return function (o, p) {
                var a, b;
                if (typeof o === "object" && typeof p === "object" && o && p) {
                    a = o[name];
                    b = p[name];
                    if (a === b) {
                        return 0;
                    }
                    if (typeof a === typeof b) {
                        return a < b ? -1 : 1;
                    }
                    return typeof a < typeof b ? -1 : 1;
                }
                else {
                    throw ("error");
                }
            }
        };



        //ttaTermAnalysis++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        $scope.dataTable="";
        $scope.gpdor= 0;
        $scope.gpper= 0;//2018
        $scope.gpdor2= 0;//GP$
        $scope.ActualGp= 0;

        $scope.ABOI1 = [];
        $scope.ABOI2 = [];
        $scope.BEOI1 = [];
        $scope.BEOI2 = [];
        $scope.SROI1 = [];
        $scope.SROI2 = [];

        $scope.ABOI ={};
        $scope.BABOI ={};
        $scope.ABOITail ={};


        $scope.gper = {};

        $scope.one= 0;//一般购货折扣
        $scope.pre= 0;//提前付款
        $scope.can= 0;//残损
        $scope.jizhong= 0;//集中收货
        $scope.cz= 0;//促销折扣
        $scope.shang= 0;//商业发展
        $scope.xuan= 0;//宣传牌

        $scope.gp2= "";
        $scope.one2= 0;//一般购货折扣
        $scope.pre2= 0;//提前付款
        $scope.can2= 0;//残损
        $scope.jizhong2= 0;//集中收货
        $scope.cz2= 0;//促销折扣
        $scope.shang2= 0;//商业发展
        $scope.xuan2= 0;//宣传牌

        //费用预估含税
        $scope.onefi= 0;//一般购货折扣
        $scope.prefi= 0;//提前付款
        $scope.canfi= 0;//残损
        $scope.jizhongfi= 0;//集中收货
        $scope.czfi= 0;//促销折扣
        $scope.shangfi= 0;//商业发展
        $scope.xuanfi= 0;//宣传牌

        $scope.onesum= 0;//一般购货折扣
        $scope.presum= 0;//提前付款
        $scope.cansum= 0;//残损
        $scope.jizhongsum= 0;//集中收货
        $scope.czsum= 0;//促销折扣
        $scope.shangsum= 0;//商业发展
        $scope.xuansum= 0;//宣传牌

        var mm = 0;
        var interval = 0;
        var ABOIlineNUM = 0;
        var oldYearABOILineNUM = 0;

        $scope.TotalBeoiA = 0;
        $scope.TotalBeoiAA = 0;
        $scope.TotalBeoiAAB = 0;
        $scope.TotalBeoiB = 0;
        $scope.TotalBeoiC = 0;
        $scope.TotalBeoiD = 0;
        $scope.TotalBeoiE = 0;

        $scope.TotalSroiA = 0;
        $scope.TotalSroiAA = 0;
        $scope.TotalSroiAB = 0;
        $scope.TotalSroiB = 0;
        $scope.TotalSroiC = 0;
        $scope.TotalSroiD = 0;
        $scope.TotalSroiE = 0;

        $scope.TotalAboiA = 0;
        $scope.TotalAboiAA = 0;
        $scope.TotalAboiAB = 0;
        $scope.TotalAboiB = 0;
        $scope.TotalAboiC = 0;
        $scope.TotalAboiD = 0;
        $scope.TotalAboiE = 0;

        //ae=n+u+ac
        $scope.TotalOiA = 0;
        $scope.TotalOiAA = 0;
        $scope.TotalOiAB = 0;
        $scope.TotalOiB = 0;
        $scope.TotalOiC = 0;
        $scope.TotalOiD = 0;
        $scope.TotalOiE = 0;

        //af=ae/c
        $scope.TotalOiPurchaseA = 0;
        $scope.TotalOiPurchaseAA = 0;
        $scope.TotalOiPurchaseAB = 0;
        $scope.TotalOiPurchaseB = 0;
        $scope.TotalOiPurchaseC = 0;
        $scope.TotalOiPurchaseD = 0;
        $scope.TotalOiPurchaseE = 0;

        //ag=af*(1-f)
        $scope.TotalOiSalesA = 0;
        $scope.TotalOiSalesAA = 0;
        $scope.TotalOiSalesAB = 0;
        $scope.TotalOiSalesB = 0;
        $scope.TotalOiSalesC = 0;
        $scope.TotalOiSalesD = 0;
        $scope.TotalOiSalesE = 0;

        //ah=f+ag
        $scope.NetMarginA = 0;
        $scope.NetMarginAA = 0;
        $scope.NetMarginAB = 0;
        $scope.NetMarginB = 0;
        $scope.NetMarginC = 0;
        $scope.NetMarginD = 0;
        $scope.NetMarginE = 0;

        $scope.remark = "";
        $scope.AnalysisProposalID = "";

        //取值年度为---->当前proposal制作年度-1 hmb修改
        $scope.initOldProposalYear =function (data) {
            //GP%
            $scope.oldProposalYearPurchase = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
            //2019.12.11 hmb添加
            for(var i = 0;i<data.length;i++) {
                if (data[i].termsCn.indexOf("一般购货折扣") >= 0) {
                    $scope.one = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (data[i].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2) ));
                } else if (data[i].termsCn.indexOf("提前付款") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.pre = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (data[i].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2) ));
                } else if (data[i].termsCn.indexOf("残损") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.can = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (data[i].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2) ));
                } else if ((data[i].termsCn.indexOf("集中收货") >= 0 || data[i].termsCn.indexOf("集中收退货") >= 0)&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.jizhong = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (data[i].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2) ));
                } else if (data[i].termsCn.indexOf("促销折扣") >= 0 )  {
                    $scope.cz = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (data[i].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2) ));
                }
            }

            //BEOI十几费用预估含税，符合一下条件才相加
            $scope.beoiPurchaseA = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
            $scope.beoiPurchaseB =$scope.adata.purchase/1000;

            for (var i = 0; i < data.length; i++) {
                //$scope.TotalBeoiA = (parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA) ).toFixed(2);
                //$scope.TotalBeoiB = (parseFloat($scope.TotalBeoiB)+($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB)).toFixed(2);

                if (data[i].termsCn.indexOf("一般购货折扣") >= 0) {
                    $scope.TotalBeoiA = parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA );
                    $scope.TotalBeoiB = parseFloat($scope.TotalBeoiB) + ($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB);
                } else if (data[i].termsCn.indexOf("提前付款") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.TotalBeoiA = parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA );
                    $scope.TotalBeoiB = parseFloat($scope.TotalBeoiB) + ($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB);
                } else if (data[i].termsCn.indexOf("残损") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.TotalBeoiA = parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA );
                    $scope.TotalBeoiB = parseFloat($scope.TotalBeoiB) + ($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB);
                } else if ((data[i].termsCn.indexOf("集中收货")>= 0||data[i].termsCn.indexOf("集中收退货") >= 0)&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                    $scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA );
                    $scope.TotalBeoiB = parseFloat($scope.TotalBeoiB) + ($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB);
                } else if (data[i].termsCn.indexOf("促销折扣") >= 0) {
                    $scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas)  / $scope.beoiPurchaseA );
                    $scope.TotalBeoiB =parseFloat($scope.TotalBeoiB) + ($scope.beoiPurchaseB == 0 ? 0 :parseFloat(data[i].sumMoney) / $scope.beoiPurchaseB);
                }
            }

            $scope.TotalBeoiA = (parseFloat($scope.TotalBeoiA) * $scope.beoiPurchaseA).toFixed(2);
            $scope.TotalBeoiAA = $scope.TotalBeoiA;
            $scope.TotalBeoiAAB = 0;
            $scope.TotalBeoiB = (parseFloat($scope.TotalBeoiB) * $scope.beoiPurchaseB).toFixed(2);

            if($scope.header.ContractVersion =="B"){//合同版本为B
                $scope.TotalBeoiA = 0;
                $scope.TotalBeoiAA = 0;
            }

        };

        //取值年度为---->当前proposal制作年度 hmb修改
        $scope.initCurrentProposalYear =function (data) {
            //用来计算GP%的值
            //2019.12.11 hmb添加
            $scope.currentProposalYearPurchase = $scope.adata.fpurchase/1000;
            for (var i in data) {
                var datum = data[i];
                if (datum.termsCn.indexOf("一般购货折扣") >=0 ) {
                    //$scope.one2 = parseFloat(datum.ttaValue)*1000;
                    $scope.one2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)));
                } else if (datum.termsCn.indexOf("提前付款") >=0 && datum.termsCn.indexOf("购货折扣") >= 0) {
                    $scope.pre2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)));
                } else if (datum.termsCn.indexOf("残损") >= 0&& datum.termsCn.indexOf("购货折扣") >= 0 ){
                    $scope.can2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)));
                } else if ( (datum.termsCn.indexOf("集中收货") >= 0|| datum.termsCn.indexOf("集中收退货") >= 0) && datum.termsCn.indexOf("购货折扣") >= 0) {
                    $scope.jizhong2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)));
                } else if (datum.termsCn.indexOf("促销折扣") >= 0 ) {
                    $scope.cz2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)));
                }
            }

            if($scope.header.ContractVersion =="A"){
                //BEOI十几费用预估含税，符合一下条件才相加
                $scope.beoiPurchaseC = $scope.adata.fpurchase/1000;

                for(var i = 0;i<data.length;i++) {
                    if (data[i].termsCn.indexOf("一般购货折扣") >= 0) {
                        //原来的代码
                        //$scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas));
                        $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+ ($scope.beoiPurchaseC == 0 ? 0 :parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas) / $scope.beoiPurchaseC) );
                    }
                    if (data[i].termsCn.indexOf("提前付款") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                        $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+ ($scope.beoiPurchaseC == 0 ? 0 :parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas) / $scope.beoiPurchaseC) );
                    }
                    if (data[i].termsCn.indexOf("残损") >= 0&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                        $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+ ($scope.beoiPurchaseC == 0 ? 0 :parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas) / $scope.beoiPurchaseC));
                    }
                    if ((data[i].termsCn.indexOf("集中收货") >= 0||data[i].termsCn.indexOf("集中收退货") >= 0)&&data[i].termsCn.indexOf("购货折扣") >= 0) {
                        $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+($scope.beoiPurchaseC == 0 ? 0 :parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas) / $scope.beoiPurchaseC));
                    }
                    if (data[i].termsCn.indexOf("促销折扣") >= 0) {
                        $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+ ($scope.beoiPurchaseC == 0 ? 0 :parseFloat(isNaN(data[i].feeIntas)?0:data[i].feeIntas) / $scope.beoiPurchaseC));
                    }
                }

                $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC) * parseFloat($scope.beoiPurchaseC)).toFixed(2);

            }else{
                $scope.TotalBeoiC = 0;
            }
        };

        $scope.print = function(){
            jQuery("#dataTable").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true
            })
        };

        $scope.analysisPrint = function(){
            jQuery("#analysisDataTableId").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true
            })
        };

        $scope.saveAnalisis=function(){
            var Markup4 =0;
            var FreeGood4 =document.getElementById('FreeGood4').value;
            var purchaseRemark =document.getElementById('premark').value;
            var bicRemark = document.getElementById('bicremark').value;//问卷remark字段保存
            if (bicRemark == null || bicRemark == undefined || bicRemark == "") {
                bicRemark = $scope.analysisData.bicRemark;
            }

            for(var i=0;i<10;i++){
                var obj = document.getElementById('AABOI'+i);
                if(obj!=null){
                    $scope.ABOI[obj.name] = document.getElementById('AABOI'+i).value;
                }
            }

            for(var k=0;k<10;k++){
                var obj = document.getElementById('BABOI'+k);
                if(obj!=null){
                    $scope.BABOI[obj.name] = document.getElementById('BABOI'+k).value;
                }
            }

            var FreeGood112 = isNaN(parseFloat($scope.mid.FreeGood112))?0:parseFloat($scope.mid.FreeGood112);

            httpServer.post(URLAPI.contractHeaderSaveAnalysis, {
                    //'params': JSON.stringify({itemId: 4})
                    'params': JSON.stringify({
                        proposalid: $scope.id,
                        aboiList:$scope.ABOI,
                        markup:Markup4,
                        freegoods:FreeGood4,
                        purchaseRemark:purchaseRemark,
                        bicRemark:bicRemark,
                        versionCode:$scope.analysisData.versionCode,
                        batch:$scope.analysisData.batch,
                        oldFreeGoods:FreeGood112,
                        oldAboiList:$scope.BABOI
                    })
                },
                function (res) {
                    $scope.searchAnalysis();
                    SIEJS.alert('保存成功', "success", "确定");
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );

        };

        //数据公式统一处理,主要计算GP%行数据
        $scope.sumdata =function (data) {

            //初始化gpper的值
            $scope.initOldProposalYear(data.contractLineList2018);
            $scope.initCurrentProposalYear(data.contractLineList2019);
            //判断为空值后面就不算了，默认为零
            if(data.hterm2018.salesType == undefined){
                $scope.ActualGp = 0;
                $scope.gpper = 0;
            }else{
                //商讨确定为同一个gp
                //目前用$scope.per.gp2代替
                $scope.ActualGp = data.hterm2018.gp;//没有使用


                //定位: anlysis_16    计算GP%
                //判断是否是新增供应商
                if ($scope.header.Ne == "N") {
                    $scope.gper.gp1 = 0.00;
                } else {//续签供应商
                    //六种条件:销售方式 + 合同版本(A,B)
                    if($scope.header.TradingMode.indexOf("returnable")>=0){
                        $scope.gper.gp1= (parseFloat(data.hterm2018.gp)).toFixed(2);//取TTA TERMS上的GP值
                    } else if($scope.header.TradingMode.indexOf("Consignment(general)")>=0&&$scope.header.ContractVersion=="A"){
                        $scope.gper.gp1= (data.hterm2018.consignmentDiscount).toFixed(2);
                    } else if($scope.header.TradingMode.indexOf("Consignment(general)")>=0&&$scope.header.ContractVersion=="B"){
                        $scope.gper.gp1= (data.hterm2018.consignmentDiscount+($scope.one+$scope.pre+$scope.can+$scope.jizhong + $scope.cz)*(1-data.hterm2018.consignmentDiscount/100)).toFixed(2);
                    } else if($scope.header.TradingMode.indexOf("Consignment Via")>=0&&$scope.header.ContractVersion=="A"){
                        $scope.gper.gp1= (data.hterm2018.consignmentDiscount-2*(1-data.hterm2018.consignmentDiscount/100)).toFixed(2);
                    } else if($scope.header.TradingMode.indexOf("Consignment Via")>=0&&$scope.header.ContractVersion=="B"){
                        $scope.gper.gp1= (data.hterm2018.consignmentDiscount+($scope.one+$scope.pre+$scope.can+$scope.jizhong + $scope.cz)*(1-data.hterm2018.consignmentDiscount/100) - 2*(1-data.hterm2018.consignmentDiscount/100)).toFixed(2);
                    }
                }
            }

            $scope.gper.gp112 = 0.00;
            $scope.gper.gp113 =parseFloat($scope.gper.gp1 + $scope.gper.gp112).toFixed(2);
            $scope.gper.gp2 = (parseFloat(data.gp)).toFixed(2);

            //新供应商
            if ($scope.header.Ne == "N") {
                if($scope.header.TradingMode.indexOf("returnable")>=0&&$scope.header.ContractVersion=="A"){//条件:  可退货购销 + 合同版本A + 新供应商
                    $scope.gper.gp3 = (data.hterm2019.gp-2*(1-data.hterm2019.gp/100)).toFixed(2);

                } else if ($scope.header.TradingMode.indexOf("returnable")>=0&&$scope.header.ContractVersion=="B") {//条件:可退货购销 + 合同版本B + 新供应商
                    $scope.gper.gp3= (data.hterm2019.gp+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.gp/100)-
                        2*(1-data.hterm2019.gp/100)).toFixed(2);

                } else if ($scope.header.TradingMode.indexOf("经仓")>=0&&$scope.header.ContractVersion=="A") {//条件:寄售经仓 + 合同版本A + 新供应商
                    //$scope.gper.gp3= (data.hterm2019.consignmentDiscount +
                    //    ($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
                    //公式: 寄售折扣-2%*（1-GP%)
                    $scope.gper.gp3 = (data.hterm2019.consignmentDiscount - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);
                } else if ($scope.header.TradingMode.indexOf("经仓")>=0&&$scope.header.ContractVersion=="B") {//条件:寄售经仓 + 合同版本B + 新供应商
                    $scope.gper.gp3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)-
                        2*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);

                } else if ($scope.header.TradingMode.indexOf("常规")>=0&&$scope.header.ContractVersion=="A") {//条件:寄售(常规) + 合同版本A + 新供应商
                    $scope.gper.gp3= data.hterm2019.consignmentDiscount;

                } else if ($scope.header.TradingMode.indexOf("常规")>=0&&$scope.header.ContractVersion=="B") {//条件:寄售(常规) + 合同版本B + 新供应商
                    $scope.gper.gp3= (data.hterm2019.consignmentDiscount +
                        ($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
                }

            } else {//续签供应商------->条件: 各种销售方式 + 合同版本
                if($scope.header.ContractVersion=="A") {//合同版本为A
                    $scope.gper.gp3=(parseFloat(data.fgp)).toFixed(2);
                } else {//合同版本为B
                    var currentProposalYearBeoiFeeIntas = ($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2);
                    var oldProposalYearBeoiFeeIntas = ($scope.one+$scope.pre+$scope.can+$scope.jizhong + $scope.cz);

                    if ((currentProposalYearBeoiFeeIntas - oldProposalYearBeoiFeeIntas) == 0) {
                        $scope.gper.gp3=(parseFloat(data.fgp)).toFixed(2);
                    } else {
                        $scope.gper.gp3 =   ((currentProposalYearBeoiFeeIntas - oldProposalYearBeoiFeeIntas) * (1 - parseFloat(data.fgp)/100) + parseFloat(data.fgp) ).toFixed(2)
                    }
                }
            }

            ////////////2019.12.4 hmb 注释 start////////////////////////
            /*  if($scope.header.TradingMode.indexOf("returnable")>=0&&$scope.header.ContractVersion=="A"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  $scope.gper.gp3= (data.hterm2019.gp-2*(1-data.hterm2019.gp/100)).toFixed(2);
              }
              if($scope.header.TradingMode.indexOf("returnable")>=0&&$scope.header.ContractVersion=="B"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  $scope.gper.gp3= (data.hterm2019.gp+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.gp/100)-2*(1-data.hterm2019.gp/100)).toFixed(2);
              }
              if($scope.header.TradingMode.indexOf("经仓")>=0&&$scope.header.ContractVersion=="A"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  //这条公式是错误的 2019.12.4 hmb查
                  //$scope.gper.gp3= (data.hterm2019.consignmentDiscount-2*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
                  //hmb改 用如下这条
                  $scope.gper.gp3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
              }
              if($scope.header.TradingMode.indexOf("经仓")>=0&&$scope.header.ContractVersion=="B"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  $scope.gper.gp3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)-2*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
              }
              if($scope.header.TradingMode.indexOf("常规")>=0&&$scope.header.ContractVersion=="A"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  $scope.gper.gp3= data.hterm2019.consignmentDiscount;
              }
              if($scope.header.TradingMode.indexOf("常规")>=0&&$scope.header.ContractVersion=="B"&&$scope.header.SupplierCode.indexOf("P")>=0){
                  $scope.gper.gp3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount/100)).toFixed(2);
              }*/
            ///////////////2019.12.4 hmb 注释 end//////////////////////////

            //定位: anlysis_15 计算 GP$
            //GP$的值需要gp%计算出来后才能计算
            $scope.mid.GP1 = parseFloat((data.contractLineList2018[0].sales*parseFloat($scope.gper.gp1)/100).toFixed(0));//1
            $scope.mid.GP112 = "-  ";//2
            $scope.mid.GP113 = $scope.mid.GP1;//3
            $scope.mid.GP2 = parseFloat(($scope.mid.Salse2*parseFloat($scope.gper.gp2)/100).toFixed(0));//4
            $scope.mid.GP3 = parseFloat($scope.mid.Salse3*parseFloat($scope.gper.gp3)/100).toFixed(0);//5

            $scope.mid.GP11 = parseFloat(($scope.mid.Salse1 ? $scope.mid.Salse1 : 0) * parseFloat($scope.gper.gp1)/100);//1
            $scope.mid.GP22 = parseFloat($scope.mid.Salse2*parseFloat($scope.gper.gp2)/100);//2
            $scope.mid.GP33 = parseFloat($scope.mid.Salse3*parseFloat($scope.gper.gp3)/100);//5



            $scope.mid.GP4 = 0;//6
            $scope.mid.GP5 = (parseFloat((parseFloat($scope.mid.GP33)+$scope.mid.GP4)).toFixed(0));//7
            if($scope.mid.GP1 == 0){
                //   $scope.mid.GP6 = 0;
                $scope.mid.GP6 = $scope.changeNumberToPosiNumber(parseFloat($scope.mid.GP33-$scope.mid.GP11).toFixed(0));//F  8
                $scope.mid.GP7 = 0;//9
            }else{
                $scope.mid.GP6 = $scope.changeNumberToPosiNumber((parseFloat(($scope.mid.GP33-$scope.mid.GP11)).toFixed(0)));//公式:F=C-A
                $scope.mid.GP7 = $scope.changeNumberToPosiNumber( (parseFloat(($scope.mid.GP33-$scope.mid.GP11)*100/parseFloat($scope.mid.GP11))).toFixed(2) );
            }
            if($scope.mid.GP2 == 0){
                //$scope.mid.GP8 = 0;
                $scope.mid.GP8 = $scope.changeNumberToPosiNumber( (parseFloat($scope.mid.GP5)-parseFloat($scope.mid.GP2)).toFixed(0) );//10
                $scope.mid.GP9 = 0;//11
            }else{
                $scope.mid.GP8 =$scope.changeNumberToPosiNumber((parseFloat($scope.mid.GP5)-parseFloat($scope.mid.GP2)).toFixed(0));
                $scope.mid.GP9 = $scope.changeNumberToPosiNumber(($scope.mid.GP8*100/parseFloat($scope.mid.GP2)).toFixed(2));
            }

            // 定位: anlysis_16  计算GP%
            //2020.1.17添加
            $scope.gper.gp4 =( $scope.mid.Salse3 == 0 ? 0 :parseFloat(data.analysisData.freegoods)/$scope.mid.Salse3 * 100).toFixed(2);
            $scope.gper.gp5 =(parseFloat($scope.gper.gp3) + parseFloat(isNaN($scope.gper.gp4) ? 0 : $scope.gper.gp4)).toFixed(2);

            $scope.gper.gp6 = "n/a";
            //2020.2.13注释
            //$scope.gper.gp7 = $scope.changeNumberToPosiNumber(($scope.gper.gp3 - $scope.gper.gp1).toFixed(2));
            $scope.gper.gp7 = $scope.changeNumberToPosiNumber(($scope.gper.gp5 - $scope.gper.gp1).toFixed(2));
            $scope.gper.gp8 = "n/a";
            $scope.gper.gp9 = $scope.changeNumberToPosiNumber(($scope.gper.gp5 - $scope.gper.gp2).toFixed(2) );

            if($scope.gper.gp1 == undefined){
                $scope.gper.gp1 = 0.00;
            }
        };

        /**
         * 统计total行数据
         * @param data 当前proposal制作年度-1的条款集合
         * @param data2 当前proposal制作年度的条款集合
         */
        function sumTotal(data,data2){
            for(var i = 0;i<data2.length;i++){
                if(data2[i].oiType.indexOf("SROI")>=0){
                    //计算 SROI$ 行的值(公式:节庆促销服务费 + 节庆促销服务费(3.8妇女节) + 新城市宣传推广服务费 + 新店宣传推广服务费 + 店铺升级推广服务费)
                    $scope.TotalSroiA = (parseFloat($scope.TotalSroiA)+parseFloat(data[i].feeIntas)).toFixed(2);
                    $scope.TotalSroiAA = 0;
                    $scope.TotalSroiAB = (parseFloat($scope.TotalSroiAB)+parseFloat(data[i].feeIntas)).toFixed(2);
                    $scope.TotalSroiB = (parseFloat($scope.TotalSroiB)+parseFloat(data[i].sumMoney)).toFixed(2);
                    $scope.TotalSroiC = (parseFloat($scope.TotalSroiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                    //$scope.TotalSroiD = (parseFloat($scope.TotalSroiD)+parseFloat(data[i].ttaValue)).toFixed(0);
                    $scope.TotalSroiD = 0;
                    $scope.TotalSroiE = (parseFloat($scope.TotalSroiE)+parseFloat(data2[i].feeIntas)).toFixed(2);
                }
                if(data2[i].oiType.indexOf("ABOI")>=0){

                    //计算 ABOI$的行的值(公式:数据分享费 + 促销陈列服务费 + 专柜促销陈列服务费 + 宣传单张、宣传牌推广服务费 + 新品种宣传推广服务费 + 商业发展服务费)
                    $scope.TotalAboiA = (parseFloat($scope.TotalAboiA)+parseFloat(data[i].feeIntas)).toFixed(2);
                    $scope.TotalAboiB = (parseFloat($scope.TotalAboiB)+parseFloat(data[i].sumMoney)).toFixed(2);
                    $scope.TotalAboiC = (parseFloat($scope.TotalAboiC)+parseFloat(data2[i].feeIntas)).toFixed(2);

                    var oldAboiObj = null;
                    var oldAboiVal = 0;
                    if($scope.oldAnalysisData.aboiList != undefined){
                        oldAboiObj = JSON.parse($scope.oldAnalysisData.aboiList);
                        oldAboiVal = eval('oldAboiObj.A'+data[i].contractLId);
                        if(oldAboiVal==undefined || oldAboiVal == ""){
                            oldAboiVal = 0;
                        }
                    }
                    $scope.TotalAboiAA = (parseFloat($scope.TotalAboiAA)+parseFloat(oldAboiVal)).toFixed(2);
                    $scope.TotalAboiAB = (parseFloat($scope.TotalAboiA)+parseFloat($scope.TotalAboiAA)).toFixed(2);

                    var obj = null;
                    var abval = 0;
                    if($scope.analysisData.aboiList!=undefined){
                        obj = JSON.parse($scope.analysisData.aboiList);
                        abval = eval('obj.A'+data2[i].contractLId);
                        if(abval==undefined || abval == ""){
                            abval = 0;
                        }
                    }

                    $scope.TotalAboiD = (parseFloat($scope.TotalAboiD)+parseFloat(abval)).toFixed(2);
                    $scope.TotalAboiE = (parseFloat($scope.TotalAboiC)+parseFloat($scope.TotalAboiD)).toFixed(2);
                }

                //if(data2[i].oiType.indexOf("BEOI")>=0){
                //    $scope.TotalBeoiA = (parseFloat($scope.TotalBeoiA)+parseFloat(data[i].feeIntas)).toFixed(2);
                //    $scope.TotalBeoiB= (parseFloat($scope.TotalBeoiB)+parseFloat(data[i].sumMoney)).toFixed(2);
                //    $scope.TotalBeoiC = (parseFloat($scope.TotalBeoiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                //    $scope.TotalBeoiD = (parseFloat($scope.TotalBeoiD)+parseFloat(data[i].ttaValue)).toFixed(2);
                //    $scope.TotalBeoiE = (parseFloat($scope.TotalBeoiE)+parseFloat(data2[i].feeIntas)).toFixed(2);
                //    $scope.TotalBeoiE = $scope.TotalBeoiC;
                //}
            }

            //计算Total OI$ 行的值(公式:Total BEOI$ + SROI$ + ABOI$)
            $scope.TotalOiA =(parseFloat($scope.TotalBeoiA) + parseFloat($scope.TotalSroiA) + parseFloat($scope.TotalAboiA)).toFixed(2);
            $scope.TotalOiAA =(parseFloat($scope.TotalBeoiAAB) + parseFloat($scope.TotalSroiAA) + parseFloat($scope.TotalAboiAA)).toFixed(2);
            $scope.TotalOiAB =(parseFloat($scope.TotalBeoiAA) + parseFloat($scope.TotalSroiAB) + parseFloat($scope.TotalAboiAB)).toFixed(2);
            $scope.TotalOiB =(parseFloat($scope.TotalBeoiB) + parseFloat($scope.TotalSroiB) + parseFloat($scope.TotalAboiB)).toFixed(2);
            $scope.TotalOiC =(parseFloat($scope.TotalBeoiC) + parseFloat($scope.TotalSroiC) + parseFloat($scope.TotalAboiC)).toFixed(2);
            $scope.TotalOiD =(parseFloat($scope.TotalBeoiD) + parseFloat($scope.TotalSroiD) + parseFloat($scope.TotalAboiD)).toFixed(2);
            $scope.TotalOiE =(parseFloat($scope.TotalBeoiC) + parseFloat($scope.TotalSroiE) + parseFloat($scope.TotalAboiE)).toFixed(2);

            //计算Total OI%(as of Purchase) 行的值 (公式:Total OI$ / Purchase$) 公式:af = ae /c
            $scope.TotalOiPurchaseA = isNaN((parseFloat($scope.TotalOiA)/parseFloat(data[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiA)*100/parseFloat(data[0].purchase)).toFixed(2)+"%";
            $scope.TotalOiPurchaseAA = isNaN((parseFloat($scope.TotalOiAA)/parseFloat(data[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiAA)*100/parseFloat(data[0].purchase)).toFixed(2)+"%";
            $scope.TotalOiPurchaseAB = isNaN((parseFloat($scope.TotalOiAB)/parseFloat(data[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiAB)*100/parseFloat(data[0].purchase)).toFixed(2)+"%";
            $scope.TotalOiPurchaseB = isNaN((parseFloat($scope.TotalOiB)/$scope.mid.Purchase2).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiB)*100/$scope.mid.Purchase2).toFixed(2)+"%";
            $scope.TotalOiPurchaseC = isNaN((parseFloat($scope.TotalOiC)/parseFloat(data2[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiC)*100/parseFloat(data2[0].purchase)).toFixed(2)+"%";
            $scope.TotalOiPurchaseD = isNaN((parseFloat($scope.TotalOiD)/parseFloat(data2[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiD)*100/parseFloat(data2[0].purchase)).toFixed(2)+"%";
            $scope.TotalOiPurchaseE = isNaN((parseFloat($scope.TotalOiE)/parseFloat(data2[0].purchase)).toFixed(2))?"0.00%":(parseFloat($scope.TotalOiE)*100/parseFloat(data2[0].purchase)).toFixed(2)+"%";

            //计算Total OI%(as of Sales) 行的值
            //2020.1.9注释
            //$scope.TotalOiSalesA = ((parseFloat($scope.TotalOiPurchaseA.replace("%","")) * (100-parseFloat($scope.gper.gp1)))/100).toFixed(2);
            //$scope.TotalOiSalesB = ((parseFloat($scope.TotalOiPurchaseB.replace("%","")) * (100-parseFloat($scope.gper.gp2)))/100).toFixed(2);
            //$scope.TotalOiSalesC = ((parseFloat($scope.TotalOiPurchaseC.replace("%","")) * (100-parseFloat($scope.gper.gp3)))/100).toFixed(2);
            //$scope.TotalOiSalesD = ( (parseFloat($scope.TotalOiPurchaseD.replace("%","")) * (100-parseFloat($scope.gper.gp3))) / 100 ).toFixed(2);//计算当前(年度+1)的年度的(on Top列) 的 (Total OI%(as of Sales)行) 所对应的单元格值   例如:当前年度是2019 未来年度:2019+1 = 2020
            //$scope.TotalOiSalesE = ((parseFloat($scope.TotalOiPurchaseE.replace("%","")) * (100-parseFloat($scope.gper.gp5)))/100).toFixed(2);

            //2020.1.8 改公式,改为:ag = ae/d
            $scope.TotalOiSalesA = ( parseFloat($scope.TotalOiA) / $scope.mid.Salse1 * 100).toFixed(2);
            $scope.TotalOiSalesAATemp = $scope.mid.Salse112 == "-  " ? 0 : $scope.mid.Salse112;
            $scope.TotalOiSalesAA = ( $scope.TotalOiSalesAATemp == 0 ? 0 :parseFloat($scope.TotalOiAA) / $scope.TotalOiSalesAATemp * 100).toFixed(2);
            $scope.TotalOiSalesAB = ( parseFloat($scope.TotalOiAB) / $scope.mid.Salse113 * 100).toFixed(2);
            $scope.TotalOiSalesB = ( parseFloat($scope.TotalOiB) / $scope.mid.Salse2 * 100).toFixed(2);
            $scope.TotalOiSalesC = ( parseFloat($scope.TotalOiC) / $scope.mid.Salse3 * 100).toFixed(2);
            $scope.TotalOiSalesDTemp = $scope.mid.Salse4 == "-  " ? 0 : $scope.mid.Salse4;
            $scope.TotalOiSalesD = ( $scope.TotalOiSalesDTemp == 0 ? 0 :parseFloat($scope.TotalOiD) / $scope.TotalOiSalesDTemp * 100).toFixed(2);
            $scope.TotalOiSalesE = ( parseFloat($scope.TotalOiE) / $scope.mid.Salse5 * 100).toFixed(2);

            if(isNaN($scope.TotalOiSalesAA)){
                $scope.TotalOiSalesAA = 0;
            }
            if(isNaN($scope.TotalOiSalesAB)){
                $scope.TotalOiSalesAB = 0;
            }
            if(isNaN($scope.TotalOiSalesA)){
                $scope.TotalOiSalesA = 0;
            }
            if(isNaN($scope.TotalOiSalesB)){
                $scope.TotalOiSalesB = 0;
            }
            if(isNaN($scope.TotalOiSalesC)){
                $scope.TotalOiSalesC = 0;
            }
            if(isNaN($scope.TotalOiSalesD)){
                $scope.TotalOiSalesD = 0;
            }
            if(isNaN($scope.TotalOiSalesE)){
                $scope.TotalOiSalesE = 0;
            }

            //计算Net Margin%(as of Sales) 行的值 公式:ah = f + ag
            $scope.NetMarginA = (parseFloat($scope.gper.gp1) + parseFloat($scope.TotalOiSalesA)).toFixed(2);
            $scope.NetMarginAA = (parseFloat($scope.gper.gp112) + parseFloat($scope.TotalOiSalesAA)).toFixed(2);
            $scope.NetMarginAB = (parseFloat($scope.gper.gp113) + parseFloat($scope.TotalOiSalesAB)).toFixed(2);
            $scope.NetMarginB = (parseFloat($scope.gper.gp2) + parseFloat($scope.TotalOiSalesB)).toFixed(2);
            $scope.NetMarginC = (parseFloat($scope.gper.gp3) + parseFloat($scope.TotalOiSalesC)).toFixed(2);
            $scope.NetMarginD = (parseFloat($scope.gper.gp4) + parseFloat($scope.TotalOiSalesD)).toFixed(2);
            $scope.NetMarginE = (parseFloat($scope.gper.gp5) + parseFloat($scope.TotalOiSalesE)).toFixed(2);

            if(isNaN($scope.NetMarginA)){
                $scope.NetMarginA = 0;
            }
            if(isNaN($scope.NetMarginAA)){
                $scope.NetMarginAA = 0;
            }
            if(isNaN($scope.NetMarginAB)){
                $scope.NetMarginAB = 0;
            }
            if(isNaN($scope.NetMarginB)){
                $scope.NetMarginB = 0;
            }
            if(isNaN($scope.NetMarginC)){
                $scope.NetMarginC = 0;
            }
            if(isNaN($scope.NetMarginD)){
                $scope.NetMarginD = 0;
            }
            if(isNaN($scope.NetMarginE)){
                $scope.NetMarginE = 0;
            }
        }

        //重新实现的四舍五入函数
        function myToFixed(data,val){
            var numbers = '';
            // 保留几位小数后面添加几个0
            for (var i = 0; i < val; i++) {
                numbers += '0';
            }
            var s = 1 + numbers;
            // 如果是整数需要添加后面的0
            var spot = "." + numbers;
            // Math.round四舍五入
            //  parseFloat() 函数可解析一个字符串，并返回一个浮点数。
            var value = Math.round(parseFloat(data) * s) / s;
            // 从小数点后面进行分割
            var d = value.toString().split(".");
            if (d.length == 1) {
                value = value.toString() + spot;
                return value;
            }
            if (d.length > 1) {
                if (d[1].length < 2) {
                    value = value.toString() + "0";
                }
                return value;
            }
        }

        //hmb注释 等正式上线之后需要注释这一段代码
        $scope.loadData =function (data,data2) {

            var tb = document.getElementById("dataTable");

            for(var i = data2.length;i>0;i--){
                //第一行remark需要合并单元格
                if(i==1){

                    var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                    newTr.setAttribute("class","analaysisOiType");

                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML = data2[i-1].oiType;
                    newTd1.rowSpan  = data2.length+2;
                    var newTd2 = newTr.insertCell();
                    newTd2.innerHTML = data2[i-1].termsEn;
                    newTd2.style = "text-align: left";
                    var newTd3 = newTr.insertCell();
                    newTd3.style="border-right: 3px solid #000000 !important;text-align: left;white-space: normal;padding: 0px;";
                    newTd3.innerHTML = "<div style='width: 280px;word-break:break-all'>"+data2[i-1].termsCn+"</div>";

                    if(data2[i-1].oiType =="BEOI"){
                        var newTd4 = newTr.insertCell();

                        newTd4.innerHTML = (data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";
                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd5.innerHTML =   (data[i-1].sumMoney/$scope.mid.Purchase2*100).toFixed(2)+"%";
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   (data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd7 = newTr.insertCell();
                        newTd7.innerHTML =  "0.00%";
                        var newTd8 = newTr.insertCell();
                        newTd8.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd8.innerHTML =   (data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   "n/a";

                        var newTd10 = newTr.insertCell();
                        var var10A = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0: $scope.dataTable.contractLineList2018[0].purchase;
                        var var10C = $scope.adata.fpurchase/1000;
                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber(( (var10C == 0?0:data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)) - (var10A == 0 ? 0:data[i-1].feeIntas*100/$scope.dataTable.contractLineList2018[0].purchase) ).toFixed(2))+"%";

                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =   "n/a";

                        var newTd12 = newTr.insertCell();
                        var var12B = $scope.mid.Purchase2;
                        var var12E = $scope.adata.fpurchase/1000;
                        newTd12.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd12.innerHTML =  $scope.changeNumberToPosiNumber(( (var12E==0?0:data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)) - (var12B == 0 ? 0 : data[i-1].sumMoney*100/$scope.mid.Purchase2) ).toFixed(2))+"%";
                    }
                    if(data2[i-1].oiType.indexOf("SROI")>=0){//首行SROI
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = (parseFloat(data[i-1].feeIntas)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important";
                        newTd5.innerHTML =   (parseFloat(data[i-1].sumMoney)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"){
                            newTd5.innerHTML = "-   ";
                        }
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd6.innerHTML == "0"||newTd6.innerHTML =="0.00"||newTd6.innerHTML=="NaN"){
                            newTd6.innerHTML = "-   ";
                        }
                        var newTd7 = newTr.insertCell();//第四列
                        newTd7.innerHTML =  "-   ";
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        newTd8.style="border-right: 3px solid #000000 !important";
                        newTd8.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"){
                            newTd9.innerHTML = "-   ";
                        }
                        var newTd10 = newTr.insertCell();
                        var sroiVar10A = data[i-1].feeIntas;
                        newTd10.innerHTML = $scope.changeNumberToPosiNumber((( sroiVar10A == 0 ? 0 :((data2[i-1].feeIntas - data[i-1].feeIntas)*100).toFixed(0)/data[i-1].feeIntas )).toFixed(2))+"%";
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                            newTd10.innerHTML = "0.00%";
                        }
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML = $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].sumMoney).toFixed(0));
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"){
                            newTd11.innerHTML = "-   ";
                        }
                        var newTd12 = newTr.insertCell();
                        newTd12.innerHTML =  $scope.changeNumberToPosiNumber((data[i-1].sumMoney == 0 ? 0 : (data2[i-1].feeIntas - data[i-1].sumMoney).toFixed(0)/data[i-1].sumMoney).toFixed(2) )+"%";
                        if(newTd12.innerHTML == "0"||newTd12.innerHTML =="0.00"||newTd12.innerHTML=="NaN"||newTd12.innerHTML=="Infinity"){
                            newTd12.innerHTML = "0.00%";
                        }
                    }
                    if(data2[i-1].oiType.indexOf("ABOI")>=0){//首行ABOI
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = (parseFloat(data[i-1].feeIntas)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd5.innerHTML =   (parseFloat(data[i-1].sumMoney)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"){
                            newTd5.innerHTML = "-   ";
                        }
                        var newTd6 = newTr.insertCell();
                        newTd6.id = "ABOILine"+(i-1)+""+1;
                        newTd6.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd6.innerHTML == "0"||newTd6.innerHTML =="0.00"||newTd6.innerHTML=="NaN"){
                            newTd6.innerHTML = "-   ";
                        }
                        var newTd7 = newTr.insertCell();
                        var val = 0;
                        newTd7.id = "ABOI"+(i-1);
                        newTd7.style="align = 'center'";
                        var obj = null;
                        var abval = 0;
                        if($scope.analysisData.aboiList!=undefined){
                            obj = JSON.parse($scope.analysisData.aboiList);
                            abval = eval('obj.A'+data2[i-1].contractLId);
                            if(abval==undefined){
                                abval = 0;
                            }
                        }
                        newTd7.innerHTML = "<input type='text' name ='A"+data2[i-1].contractLId+"' id='AABOI"+ABOIlineNUM+"' style='width: 100%; text-align: center;'  class='form-control radius3' required value='"+abval+"'>";
                        ABOIlineNUM = ABOIlineNUM+1;

                        var newTd8 = newTr.insertCell();//第五列
                        newTd8.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd8.id = "ABOILine"+(i-1)+""+2;
                        newTd8.innerHTML =   (parseFloat(data2[i-1].feeIntas)+parseFloat(abval)).toFixed(0);
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity"){
                            newTd9.innerHTML = "-   ";
                        }
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber((data[i-1].feeIntas == 0 ? 0 :parseFloat((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0))*100/data[i-1].feeIntas).toFixed(2))+"%";
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity%"){
                            newTd10.innerHTML = "0.00%";
                        }
                        var newTd11 = newTr.insertCell();
                        var aboi11B = isNaN(data[i - 1].sumMoney)? 0: data[i - 1].sumMoney;
                        var aboi11E = newTd8.innerHTML == "-   " ? 0 : newTd8.innerHTML;
                        newTd11.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(aboi11E) - (aboi11B == 0 ? 0 : data[i - 1].sumMoney)).toFixed(0));
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"||newTd11.innerHTML=="Infinity"){
                            newTd11.innerHTML = "-   ";
                        }
                        var newTd12 = newTr.insertCell();
                        newTd12.innerHTML =   $scope.changeNumberToPosiNumber( (data[i - 1].sumMoney == 0 ? 0 : parseFloat(newTd11.innerHTML)*100/data[i - 1].sumMoney).toFixed(2) )+"%";
                        if(newTd12.innerHTML == "0"||newTd12.innerHTML =="0.00"||newTd12.innerHTML=="NaN"||newTd12.innerHTML=="Infinity%"){
                            newTd12.innerHTML = "0.00%";
                        }
                    }


                    if(data2[i-1].oiType =="BEOI") {
                        var newTd13 = newTr.insertCell();
                        newTd13.colSpan = 7;
                        newTd13.rowSpan = $scope.datanum + 6;
                        newTd13.style = "border-right: 3px solid !important;";
                        //console.log("当前remark字段可见情况:" + $scope.controllTabShow.remarkFlag + ", status:" + $scope.params.status);
                        if ($scope.controllTabShow.remarkFlag || $scope.params.status == 'C') { //问卷remark信息控制, 返回true可见，否则不可见。
                            newTd13.innerHTML ="<strong><textarea id='bicremark' style='width: 100%;height: 100%'>"+$scope.analysisData.bicRemark+"</textarea></strong>";
                        } else {
                            //console.log("当前remark字段不可见:" + $scope.controllTabShow.remarkFlag)
                            newTd13.innerHTML ="<strong><textarea id='bicremark' style='width: 100%;height: 100%'></textarea></strong>";
                        }
                    }
                } else{

                    if(i == data2.length){
                        var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                        newTr.style = "background:#D3FF93 !important;font-weight: bold;";
                        newTr.setAttribute("class","analaysisOiType");

                        var newTd1 = newTr.insertCell();
                        newTd1.innerHTML =   data2[i-1].oiType+"%(as of purchase)";
                        newTd1.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd1.colSpan  = 2;

                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = newTr.insertCell();
                            var tnewTd3 = newTr.insertCell();
                            if ($scope.header.Ne == 'E') {//续签供应商
                                if ($scope.mid.Purchase1 == 0) {
                                    tnewTd2.innerHTML = "0.00%";
                                } else {
                                    tnewTd2.innerHTML = ($scope.TotalBeoiA / $scope.mid.Purchase1 * 100).toFixed(2) + "%";
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    tnewTd3.innerHTML = "0.00%";
                                } else {
                                    tnewTd3.innerHTML = ($scope.TotalBeoiB / $scope.mid.Purchase2 * 100).toFixed(2) + "%";
                                }
                            } else {
                                tnewTd2.innerHTML = "0.00%";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =   "0.00%";
                            }else{
                                tnewTd4.innerHTML =   ($scope.TotalBeoiC/$scope.mid.Purchase3*100).toFixed(2)+"%";
                            }

                            var tnewTd5 = newTr.insertCell();
                            tnewTd5.innerHTML =   "0.00%";
                            //if($scope.mid.Purchase4 ==0){
                            //    tnewTd5.innerHTML =   0;
                            //}else{
                            //    tnewTd5.innerHTML =   ($scope.TotalBeoiD/$scope.mid.Purchase4).toFixed(2)+"%";
                            //}

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =   "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalBeoiC*100/$scope.mid.Purchase5).toFixed(2)+"%";
                            }

                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase3 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase3*100 ).toFixed(2)) - parseFloat(( $scope.mid.Purchase1 == 0 ? 0 :$scope.TotalBeoiA/$scope.mid.Purchase1*100).toFixed(2))).toFixed(2) )+"%";
                            $scope.header.BEOI= $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase3 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase3*100).toFixed(2)) - parseFloat(( $scope.mid.Purchase1 == 0 ? 0  :$scope.TotalBeoiA/$scope.mid.Purchase1*100).toFixed(2))).toFixed(2) ) +"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd6.innerHTML.replace("%","")) - parseFloat(tnewTd3.innerHTML.replace("%",""))).toFixed(2))+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }
                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = newTr.insertCell();
                            var tnewTd3 = newTr.insertCell();

                            if ($scope.header.Ne == 'E') {//续签供应商
                                if ($scope.mid.Purchase1 == 0) {
                                    //tnewTd2.innerHTML = "-   ";
                                    tnewTd2.innerHTML = "0.00%";
                                } else {
                                    tnewTd2.innerHTML = ($scope.TotalSroiA * 100 / $scope.mid.Purchase1).toFixed(2) + "%";
                                    if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                        //tnewTd2.innerHTML = "-   ";
                                        tnewTd2.innerHTML = "0.00%";
                                    }
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    //tnewTd3.innerHTML = "-   ";
                                    tnewTd3.innerHTML = "0.00%";
                                } else {
                                    tnewTd3.innerHTML = ($scope.TotalSroiB * 100 / $scope.mid.Purchase2).toFixed(2) + "%";
                                    if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                        //tnewTd3.innerHTML = "-   ";
                                        tnewTd3.innerHTML = "0.00%";
                                    }
                                }
                            } else {
                                //tnewTd2.innerHTML = "-   ";
                                //tnewTd3.innerHTML = "-   ";
                                tnewTd2.innerHTML = "0.00%";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();

                            if($scope.mid.Purchase3 ==0){
                                //tnewTd4.innerHTML =   "-   ";
                                tnewTd4.innerHTML =   "0.00%";
                            }else{
                                tnewTd4.innerHTML =   ($scope.TotalSroiC*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                    //tnewTd4.innerHTML = "-   ";
                                    tnewTd4.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd5 = newTr.insertCell();
                            tnewTd5.innerHTML ="0.00%";
                            //if($scope.mid.Purchase4 ==0){
                            //    tnewTd5.innerHTML =   0;
                            //}else{
                            //    //tnewTd5.innerHTML =   $scope.TotalSroiD/$scope.mid.Purchase4;
                            //    tnewTd5.innerHTML =   0;
                            //}

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                //tnewTd6.innerHTML =   "-   ";
                                tnewTd6.innerHTML =   "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalSroiE*100/$scope.mid.Purchase5).toFixed(2)+"%";
                                if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                    //tnewTd6.innerHTML = "-   ";
                                    tnewTd6.innerHTML = "0.00%";
                                }
                            }

                            var newTd7 = newTr.insertCell();
                            //newTd7.innerHTML =   ($scope.TotalSroiC - $scope.TotalSroiA).toFixed(2)+"%";
                            newTd7.innerHTML = "n/a";
                            //下面的newTd7代码没用到,可以注释掉,多余代码
                            /*if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                                newTd7.innerHTML = "-   ";
                            }*/
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                                newTd8.innerHTML = "0.00%";
                            }
                            $scope.header.SROI=$scope.changeNumberToPosiNumber((parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2))+"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            var sroiVar10E = $scope.mid.Purchase5;
                            var sroiVar10B = $scope.mid.Purchase2;
                            newTd10.innerHTML =   $scope.changeNumberToPosiNumber( ((sroiVar10E == 0 ? 0 :$scope.TotalSroiE*100/$scope.mid.Purchase5).toFixed(2) - (sroiVar10B == 0 ? 0 : $scope.TotalSroiB*100/$scope.mid.Purchase2).toFixed(2)).toFixed(2))+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }

                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            //计算(当年proposal制作年度 - 1) 的TTA 和 Actual 的列的值
                            var tnewTd2 = newTr.insertCell();
                            var tnewTd3 = newTr.insertCell();
                            if ($scope.header.Ne == 'E'){//续签供应商
                                if($scope.mid.Purchase1 ==0){
                                    tnewTd2.innerHTML =  "0.00%";
                                }else{
                                    tnewTd2.innerHTML =   ($scope.TotalAboiA*100/$scope.mid.Purchase1).toFixed(2)+"%";
                                    if(tnewTd2.innerHTML == "0"||tnewTd2.innerHTML =="0.00"||tnewTd2.innerHTML=="NaN"){
                                        tnewTd2.innerHTML = "0.00%";
                                    }
                                }

                                tnewTd3.style="border-right: 3px solid #000000 !important;text-align: center;";
                                if($scope.mid.Purchase2 ==0){
                                    tnewTd3.innerHTML =   "0.00%";
                                }else{
                                    tnewTd3.innerHTML =   ($scope.TotalAboiB*100/$scope.mid.Purchase2).toFixed(2)+"%";
                                    if(tnewTd3.innerHTML == "0"||tnewTd3.innerHTML =="0.00"||tnewTd3.innerHTML=="NaN"){
                                        tnewTd3.innerHTML = "0.00%";
                                    }
                                }

                            } else {//新增供应商默认为0.00%
                                tnewTd2.innerHTML =  "0.00%";
                                tnewTd3.style="border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =  "0.00%";
                            } else {
                                tnewTd4.innerHTML =   ($scope.TotalAboiC*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                    tnewTd4.innerHTML = "0.00%";
                                }
                            }
                            var tnewTd5 = newTr.insertCell();
                            if($scope.mid.Purchase4 ==0){
                                tnewTd5.innerHTML =   "0.00%";
                            }else{
                                //计算的是用户编辑入的数据
                                tnewTd5.innerHTML =   ($scope.TotalAboiD*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd5.innerHTML == "0"||tnewTd5.innerHTML =="0.00"||tnewTd5.innerHTML=="NaN"){
                                    tnewTd5.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =  "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalAboiE*100/$scope.mid.Purchase5).toFixed(2)+"%";
                                if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                    tnewTd6.innerHTML = "0.00%";
                                }
                            }


                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            $scope.header.ABOI=  $scope.changeNumberToPosiNumber( (parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd6.innerHTML) - parseFloat(tnewTd3.innerHTML)).toFixed(2) )+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity%"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }


                        var tnewTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                        tnewTr.style = "font-weight: bold;";
                        tnewTr.setAttribute("class","analaysisOiType");
                        var tnewTd1 = tnewTr.insertCell();//TOTAL
                        tnewTd1.innerHTML =   "TOTAL "+data2[i-1].oiType+"$";
                        tnewTd1.style="border-right: 3px solid #000000 !important;text-align: center;";
                        tnewTd1.colSpan  = 2;

                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                if ($scope.mid.Purchase1 == 0) {
                                    tnewTd2.innerHTML = 0;
                                } else {
                                    tnewTd2.innerHTML = (parseFloat($scope.TotalBeoiA)).toFixed(0);
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    tnewTd3.innerHTML = 0;
                                } else {
                                    tnewTd3.innerHTML = (parseFloat($scope.TotalBeoiB)).toFixed(0);
                                }
                            } else {
                                tnewTd2.innerHTML = 0;
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = 0;
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            if($scope.mid.Purchase3==0){
                                tnewTd4.innerHTML = 0;
                            }else{
                                tnewTd4.innerHTML =   (parseFloat($scope.TotalBeoiC)).toFixed(0);
                            }

                            var tnewTd5 = tnewTr.insertCell();
                            tnewTd5.innerHTML =   "-  ";

                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   (parseFloat($scope.TotalBeoiC)).toFixed();
                            }
                            var tnewTd7 = tnewTr.insertCell();
                            tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiA)).toFixed(0) );
                            var tnewTd8 = tnewTr.insertCell();
                            tnewTd8.innerHTML =$scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiA))*100/parseFloat($scope.TotalBeoiA)).toFixed(2) )+"%";
                            var tnewTd9 = tnewTr.insertCell();
                            tnewTd9.innerHTML =  $scope.changeNumberToPosiNumber( ( parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiB)).toFixed(0) );
                            var tnewTd10 = tnewTr.insertCell();

                            tnewTd10.innerHTML =  $scope.changeNumberToPosiNumber( myToFixed((($scope.TotalBeoiC - $scope.TotalBeoiB).toFixed(0))*100/parseFloat($scope.TotalBeoiB),2) )+"%";
                            //tnewTd10.innerHTML =  ($scope.TotalBeoiC - $scope.TotalBeoiB).toFixed(0);
                        }
                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                tnewTd2.innerHTML = (parseFloat($scope.TotalSroiA)).toFixed(0);
                                if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                    tnewTd2.innerHTML = "-   ";
                                }
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = (parseFloat($scope.TotalSroiB)).toFixed(0);
                                if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                    tnewTd3.innerHTML = "-   ";
                                }

                            } else {
                                tnewTd2.innerHTML = "-   ";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "-   ";
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   (parseFloat($scope.TotalSroiC)).toFixed(0);
                            if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                tnewTd4.innerHTML = "-   ";
                            }
                            var tnewTd5 = tnewTr.insertCell();
                            //tnewTd5.innerHTML =   $scope.TotalSroiD;
                            tnewTd5.innerHTML =   "-    ";
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            tnewTd6.innerHTML =   (parseFloat($scope.TotalSroiE)).toFixed(0);
                            if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                tnewTd6.innerHTML = "-   ";
                            }
                            var tnewTd7 = tnewTr.insertCell();
                            tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiE -$scope.TotalSroiA).toFixed(0));
                            if(tnewTd7.innerHTML == "0"||tnewTd7.innerHTML =="0.00"||tnewTd7.innerHTML=="NaN"){
                                tnewTd7.innerHTML = "-   ";
                            }
                            var tnewTd8 = tnewTr.insertCell();
                            var sroi8A= $scope.TotalSroiA;
                            tnewTd8.innerHTML = $scope.changeNumberToPosiNumber((sroi8A == 0 ? 0 : (($scope.TotalSroiE -$scope.TotalSroiA)*100).toFixed(0)/$scope.TotalSroiA ).toFixed(2)) +"%";
                            if(tnewTd8.innerHTML == "0"||tnewTd8.innerHTML =="0.00"||tnewTd8.innerHTML=="NaN"){
                                tnewTd8.innerHTML = "0.00%";
                            }
                            var tnewTd9 = tnewTr.insertCell();
                            tnewTd9.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiE -$scope.TotalSroiB).toFixed(0));
                            if(tnewTd9.innerHTML == "0"||tnewTd9.innerHTML =="0.00"||tnewTd9.innerHTML=="NaN"){
                                tnewTd9.innerHTML = "-   ";
                            }
                            var tnewTd10 = tnewTr.insertCell();
                            tnewTd10.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiB == 0 ? 0 : (($scope.TotalSroiE -$scope.TotalSroiB)*100).toFixed(0)/$scope.TotalSroiB).toFixed(2) )+"%";
                            if(tnewTd10.innerHTML == "0"||tnewTd10.innerHTML =="0.00"||tnewTd10.innerHTML=="NaN"){
                                tnewTd10.innerHTML = "0.00%";
                            }
                        }

                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                tnewTd2.innerHTML = (parseFloat($scope.TotalAboiA)).toFixed(0);
                                if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                    tnewTd2.innerHTML = "-   ";
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = (parseFloat($scope.TotalAboiB)).toFixed(0);
                                if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                    tnewTd3.innerHTML = "-   ";
                                }

                            } else {
                                tnewTd2.innerHTML = "-   ";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "-   ";
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   (parseFloat($scope.TotalAboiC)).toFixed(0);
                            if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                tnewTd4.innerHTML = "-   ";
                            }
                            var tnewTd5 = tnewTr.insertCell();
                            //tnewTd5.innerHTML =   $scope.TotalAboiD;
                            //tnewTd5.innerHTML = $scope.ABOITail.sum;
                            tnewTd5.id = "aboitailsum";
                            tnewTd5.innerHTML = 0;//开始默认为0
                            if(tnewTd5.innerHTML == "0"||tnewTd5.innerHTML =="0.00"||tnewTd5.innerHTML=="NaN"){
                                tnewTd5.innerHTML = "-   ";
                            }
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            tnewTd6.innerHTML =   (parseFloat($scope.TotalAboiE)).toFixed(0);
                            if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                tnewTd6.innerHTML = "-   ";
                            }
                            var newTd8 = tnewTr.insertCell();
                            newTd8.innerHTML = $scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalAboiC)).toFixed(0) - (parseFloat($scope.TotalAboiA)).toFixed(0)).toFixed(0) );
                            if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity"){
                                newTd8.innerHTML = "-   ";
                            }
                            var newTd9 = tnewTr.insertCell();
                            newTd9.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat((parseFloat($scope.TotalAboiC)).toFixed(0) - (parseFloat($scope.TotalAboiA)).toFixed(0))*100/parseFloat($scope.TotalAboiA)).toFixed(2) )+"%";
                            if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity%"){
                                newTd9.innerHTML = "0.00%";
                            }
                            var newTd10 = tnewTr.insertCell();
                            newTd10.innerHTML = $scope.changeNumberToPosiNumber((parseFloat($scope.TotalAboiE) - parseFloat($scope.TotalAboiB)).toFixed(0));
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity"){
                                newTd10.innerHTML = "-   ";
                            }
                            var newTd11 = tnewTr.insertCell();
                            newTd11.innerHTML =  $scope.changeNumberToPosiNumber(((parseFloat($scope.TotalAboiE) - parseFloat($scope.TotalAboiB))*100/parseFloat($scope.TotalAboiB)).toFixed(2))+"%";
                            if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"||newTd11.innerHTML=="Infinity%"){
                                newTd11.innerHTML = "0.00%";
                            }
                        }

                    }

                    var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                    newTr.setAttribute("class","analaysisOiType");
                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML =   data2[i-1].termsEn;
                    newTd1.style = "text-align: left;";
                    var newTd2 = newTr.insertCell();
                    newTd2.style="border-right: 3px solid #000000 !important;text-align: left;";
                    newTd2.innerHTML =   data2[i-1].termsCn;
                    if(data2[i-1].oiType.indexOf("BEOI")>=0) {//中间行
                        var newTd3 = newTr.insertCell();
                        newTd3.innerHTML = (data[i - 1].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";
                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        //hmb查
                        newTd4.innerHTML = (data[i - 1].sumMoney*100 / $scope.mid.Purchase2).toFixed(2)+"%";
                        var newTd5 = newTr.insertCell();
                        newTd5.innerHTML = (data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                    }
                    if(data2[i-1].oiType.indexOf("SROI")>=0) {
                        var newTd3 = newTr.insertCell();
                        newTd3.innerHTML = (parseFloat(data[i - 1].feeIntas)).toFixed(0);
                        if(newTd3.innerHTML == "0"||newTd3.innerHTML =="0.00"||newTd3.innerHTML=="NaN"||newTd3.innerHTML=="Infinity%"){
                            newTd3.innerHTML = "-   ";
                        }
                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd4.innerHTML = (parseFloat(data[i - 1].sumMoney)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"||newTd4.innerHTML=="Infinity%"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.innerHTML = (parseFloat(data2[i - 1].feeIntas)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"||newTd5.innerHTML=="Infinity%"){
                            newTd5.innerHTML = "-   ";
                        }
                    }

                    if(data2[i-1].oiType.indexOf("ABOI")>=0) {
                        var newTd3 = newTr.insertCell();
                        newTd3.innerHTML = (parseFloat(data[i - 1].feeIntas)).toFixed(0);
                        if(newTd3.innerHTML == "0"||newTd3.innerHTML =="0.00"||newTd3.innerHTML=="NaN"||newTd3.innerHTML=="Infinity%"){
                            newTd3.innerHTML = "-   ";
                        }
                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd4.innerHTML = (parseFloat(data[i - 1].sumMoney)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"||newTd4.innerHTML=="Infinity%"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.id = "ABOILine"+(i-1)+1;
                        newTd5.innerHTML = (parseFloat(data2[i - 1].feeIntas)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"||newTd5.innerHTML=="Infinity%"){
                            newTd5.innerHTML = "-   ";
                        }
                    }


                    var newTd6 = newTr.insertCell();

                    var obj1 = null;
                    var abval1 = 0;
                    if($scope.analysisData.aboiList!=undefined){
                        obj1 = JSON.parse($scope.analysisData.aboiList);
                        abval1 = eval('obj1.A'+data2[i-1].contractLId);
                        if(abval1==undefined || abval1 == ""){
                            abval1 = 0;
                        }
                    }

                    //合同行数据分类处理
                    if(data2[i-1].oiType.indexOf("SROI")>=0) {
                        newTd6.innerHTML = "-   ";
                    }
                    if(data2[i-1].oiType.indexOf("ABOI")>=0){
                        newTd6.id = "ABOI"+(i-1);
                        newTd6.innerHTML = "<input type='text' name ='A"+data2[i-1].contractLId+"' id='AABOI"+ABOIlineNUM+"' style='width: 100%;text-align: center;'  class='form-control radius3' required value='"+abval1+"'>";
                        ABOIlineNUM = ABOIlineNUM+1;
                    }
                    if(data2[i-1].oiType.indexOf("BEOI")>=0){
                        newTd6.innerHTML = "0.00%";
                    }
                    if(data2[i-1].oiType.indexOf("BEOI")>=0){//中间行
                        var newTd7 = newTr.insertCell();
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.innerHTML =   (data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   "n/a";
                        var newTd9 = newTr.insertCell();
                        var beoiFcsPurchaseC = isNaN($scope.adata.fpurchase/1000)?0:$scope.adata.fpurchase/1000;
                        var beoiFcsPurchaseA = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
                        newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (( beoiFcsPurchaseC == 0 ? 0: data2[i - 1].feeIntas*100 / beoiFcsPurchaseC )- (beoiFcsPurchaseA == 0? 0:data[i - 1].feeIntas*100 / beoiFcsPurchaseA) ).toFixed(2))+"%";
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   "n/a";
                        var newTd11 = newTr.insertCell();

                        var var11A = $scope.adata.fpurchase/1000;
                        var var11B = $scope.mid.Purchase2;
                        newTd11.innerHTML =  $scope.changeNumberToPosiNumber(( (var11A == 0?0:data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)) - (var11B == 0? 0:data[i - 1].sumMoney*100 / $scope.mid.Purchase2)).toFixed(2))+"%";
                    }
                    if(data2[i-1].oiType.indexOf("SROI")>=0){
                        var newTd7 = newTr.insertCell();//第五列
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.innerHTML =   (data2[i-1].feeIntas).toFixed(0);
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0) );
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                            newTd8.innerHTML = "-  ";
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber(((data2[i-1].feeIntas - data[i-1].feeIntas)*100/data[i - 1].feeIntas).toFixed(2))+"%";
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity"){
                            newTd9.innerHTML = "0.00%";
                        }
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML) - parseFloat(newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML)).toFixed(0));
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                            newTd10.innerHTML = "-   ";
                        }
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =  $scope.changeNumberToPosiNumber( (parseFloat(newTd10.innerHTML.indexOf("- ")>=0?0:newTd10.innerHTML)*100/parseFloat(newTd4.innerHTML)).toFixed(2))+"%";
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"){
                            newTd11.innerHTML = "0.00%";
                        }
                    }
                    if(data2[i-1].oiType.indexOf("ABOI")>=0){
                        var newTd7 = newTr.insertCell();//第五列
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.id = "ABOILine"+(i-1)+2;
                        newTd7.innerHTML =   (parseFloat(data2[i - 1].feeIntas)+parseFloat(abval1)).toFixed(0);
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"||newTd7.innerHTML=="Infinity"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =  $scope.changeNumberToPosiNumber( (data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        var avg9=data[i-1].feeIntas;
                        newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (avg9 ==0?0:parseFloat((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0))*100/data[i-1].feeIntas).toFixed(2))+"%";
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity%"){
                            newTd9.innerHTML = "0.00%";
                        }
                        var newTd10 = newTr.insertCell();
                        var cell5 = newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML;
                        var cell2 = newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML;
                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML) - parseFloat(newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML)).toFixed(0));
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity"){
                            newTd10.innerHTML = "-   ";
                        }
                        var newTd11 = newTr.insertCell();
                        var suml = data[i - 1].sumMoney;
                        newTd11.innerHTML =   $scope.changeNumberToPosiNumber( (suml==0?0:parseFloat(newTd10.innerHTML.indexOf("- ")>=0?0:newTd10.innerHTML)*100/suml).toFixed(2))+"%";
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"||newTd11.innerHTML=="Infinity%"){
                            newTd11.innerHTML = "0.00%";
                        }
                    }

                }


            }
        };

        //hmb注释 等正式上线可放开此代码
        /*$scope.loadData =function (data,data2) {

            var tb = document.getElementById("dataTable");

            for(var i = data2.length;i>0;i--){
                //insertRow方法的索引是从0开始
                //第一行remark需要合并单元格
                if(i==1){

                    var newTr = tb.insertRow(18);//添加新行，trIndex就是要添加的位置
                    newTr.setAttribute("class","analaysisOiType");

                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML = data2[i-1].oiType;
                    newTd1.rowSpan  = data2.length+2;
                    var newTd2 = newTr.insertCell();
                    newTd2.innerHTML = data2[i-1].termsEn;
                    newTd2.style = "text-align: left";
                    var newTd3 = newTr.insertCell();
                    newTd3.style="border-right: 3px solid #000000 !important;text-align: left;white-space: normal;padding: 0px;";
                    newTd3.innerHTML = "<div style='width: 280px;word-break:break-all'>"+data2[i-1].termsCn+"</div>";

                    if(data2[i-1].oiType =="BEOI"){
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = (data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";

                        var newTd444 = newTr.insertCell();
                        newTd444.innerHTML = "0.00%";
                        var newTd445 = newTr.insertCell();
                        newTd445.innerHTML = (data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";

                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd5.innerHTML =   (data[i-1].sumMoney/$scope.mid.Purchase2*100).toFixed(2)+"%";
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   (data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd7 = newTr.insertCell();
                        newTd7.innerHTML =  "0.00%";
                        var newTd8 = newTr.insertCell();
                        newTd8.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd8.innerHTML =   (data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   "n/a";

                        var newTd10 = newTr.insertCell();
                        //2020.2.13 hmb 注释
                        //var var10A = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0: $scope.dataTable.contractLineList2018[0].purchase;
                        //var var10C = $scope.adata.fpurchase/1000;
                        //newTd10.innerHTML =  $scope.changeNumberToPosiNumber(( (var10C == 0?0:data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)) - (var10A == 0 ? 0:data[i-1].feeIntas*100/$scope.dataTable.contractLineList2018[0].purchase) ).toFixed(2))+"%";

                        var var10A = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0: $scope.dataTable.contractLineList2018[0].purchase;
                        var var10C = $scope.adata.fpurchase/1000;
                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber(( (var10C == 0?0:data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)) - (var10A == 0 ? 0:data[i-1].feeIntas*100/$scope.dataTable.contractLineList2018[0].purchase) ).toFixed(2))+"%";

                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =   "n/a";

                        var newTd12 = newTr.insertCell();
                        var var12B = $scope.mid.Purchase2;
                        var var12E = $scope.adata.fpurchase/1000;
                        newTd12.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd12.innerHTML =  $scope.changeNumberToPosiNumber(( (var12E==0?0:data2[i-1].feeIntas*100/($scope.adata.fpurchase/1000)) - (var12B == 0 ? 0 : data[i-1].sumMoney*100/$scope.mid.Purchase2) ).toFixed(2))+"%";
                    }

                    if(data2[i-1].oiType.indexOf("SROI")>=0){//首行SROI
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = (parseFloat(data[i-1].feeIntas)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"){
                            newTd4.innerHTML = "-   ";
                        }

                        var newTd444 = newTr.insertCell();
                        newTd444.innerHTML = "-   ";
                        var newTd445 = newTr.insertCell();
                        newTd445.innerHTML =   (parseFloat(data[i-1].feeIntas)).toFixed(0);
                        if(newTd445.innerHTML == "0"||newTd445.innerHTML =="0.00"||newTd445.innerHTML=="NaN"){
                            newTd445.innerHTML = "-   ";
                        }

                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important";
                        newTd5.innerHTML =   (parseFloat(data[i-1].sumMoney)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"){
                            newTd5.innerHTML = "-   ";
                        }
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd6.innerHTML == "0"||newTd6.innerHTML =="0.00"||newTd6.innerHTML=="NaN"){
                            newTd6.innerHTML = "-   ";
                        }
                        var newTd7 = newTr.insertCell();//第四列
                        newTd7.innerHTML =  "-   ";
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        newTd8.style="border-right: 3px solid #000000 !important";
                        newTd8.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"){
                            newTd9.innerHTML = "-   ";
                        }
                        var newTd10 = newTr.insertCell();
                        var sroiVar10A = data[i-1].feeIntas;
                        newTd10.innerHTML = $scope.changeNumberToPosiNumber((( sroiVar10A == 0 ? 0 :((data2[i-1].feeIntas - data[i-1].feeIntas)*100).toFixed(0)/data[i-1].feeIntas )).toFixed(2))+"%";
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                            newTd10.innerHTML = "0.00%";
                        }
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML = $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].sumMoney).toFixed(0));
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"){
                            newTd11.innerHTML = "-   ";
                        }
                        var newTd12 = newTr.insertCell();
                        newTd12.innerHTML =  $scope.changeNumberToPosiNumber((data[i-1].sumMoney == 0 ? 0 : (data2[i-1].feeIntas - data[i-1].sumMoney).toFixed(0)/data[i-1].sumMoney).toFixed(2) )+"%";
                        if(newTd12.innerHTML == "0"||newTd12.innerHTML =="0.00"||newTd12.innerHTML=="NaN"||newTd12.innerHTML=="Infinity"){
                            newTd12.innerHTML = "0.00%";
                        }
                    }

                    if(data2[i-1].oiType.indexOf("ABOI")>=0){//首行ABOI
                        var newTd4 = newTr.insertCell();
                        newTd4.id = "oldYeaABOILine"+(i-1)+""+1;
                        newTd4.innerHTML = (parseFloat(data[i-1].feeIntas)).toFixed(0);//费用预估
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"){
                            newTd4.innerHTML = "-   ";
                        }

                        var newTd444 = newTr.insertCell();
                        newTd444.id = "oldYearABOI"+(i-1);
                        newTd444.style="align = 'center'";

                        var oldYearOnTopObj = null;
                        var aboiValue = 0;
                        if($scope.oldAnalysisData.aboiList != undefined){
                            oldYearOnTopObj = JSON.parse($scope.oldAnalysisData.aboiList);//解析为JSON对象
                            var attrKey = "A" + data[i-1].contractLId;
                            aboiValue = oldYearOnTopObj[attrKey];
                            if(aboiValue==undefined || aboiValue == ""){
                                aboiValue = 0;
                            }
                        }

                        newTd444.innerHTML = "<input type='text' name ='A"+data[i-1].contractLId+"' id='BABOI"+oldYearABOILineNUM+"' style='width: 100%; text-align: center;'  class='form-control radius3' required value='"+aboiValue+"'>";
                        oldYearABOILineNUM = oldYearABOILineNUM+1;

                        var newTd445 = newTr.insertCell();
                        newTd445.id = "oldYeaABOILine"+(i-1)+""+2;
                        newTd445.innerHTML =   (parseFloat(data[i-1].feeIntas)+parseFloat(aboiValue)).toFixed(0);
                        if(newTd445.innerHTML == "0"||newTd445.innerHTML =="0.00"||newTd445.innerHTML=="NaN"||newTd445.innerHTML=="Infinity"){
                            newTd445.innerHTML = "-   ";
                        }

                        var newTd5 = newTr.insertCell();
                        newTd5.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd5.innerHTML =   (parseFloat(data[i-1].sumMoney)).toFixed(0);//实际费用
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"){
                            newTd5.innerHTML = "-   ";
                        }
                        var newTd6 = newTr.insertCell();
                        newTd6.id = "ABOILine"+(i-1)+""+1;
                        newTd6.innerHTML =   (parseFloat(data2[i-1].feeIntas)).toFixed(0);
                        if(newTd6.innerHTML == "0"||newTd6.innerHTML =="0.00"||newTd6.innerHTML=="NaN"){
                            newTd6.innerHTML = "-   ";
                        }
                        var newTd7 = newTr.insertCell();
                        var val = 0;
                        newTd7.id = "ABOI"+(i-1);
                        newTd7.style="align = 'center'";
                        var obj = null;
                        var abval = 0;
                        if($scope.analysisData.aboiList != undefined){
                            obj = JSON.parse($scope.analysisData.aboiList);
                            //abval = eval('obj.A'+data2[i-1].contractLId);
                            var abvalKey = "A" + data2[i-1].contractLId;
                            abval = obj[abvalKey];
                            if(abval==undefined || abval == ""){
                                abval = 0;
                            }
                        }
                        newTd7.innerHTML = "<input type='text' name ='A"+data2[i-1].contractLId+"' id='AABOI"+ABOIlineNUM+"' style='width: 100%; text-align: center;'  class='form-control radius3' required value='"+abval+"'>";
                        ABOIlineNUM = ABOIlineNUM+1;

                        var newTd8 = newTr.insertCell();//第五列
                        newTd8.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd8.id = "ABOILine"+(i-1)+""+2;
                        newTd8.innerHTML =   (parseFloat(data2[i-1].feeIntas)+parseFloat(abval)).toFixed(0);
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        //2020.2.13 hmb 注释
                        //newTd9.innerHTML =  $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        var AboiFristRowValE = (parseFloat(data2[i-1].feeIntas) + parseFloat(abval)).toFixed(0);
                        var AboiFristRowVal445 = (parseFloat(data[i-1].feeIntas) + parseFloat(aboiValue)).toFixed(0);
                        //newTd9.innerHTML =  $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber((AboiFristRowValE - AboiFristRowVal445).toFixed(0));
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity"){
                            newTd9.innerHTML = "-   ";
                        }
                        var newTd10 = newTr.insertCell();
                        //2020.2.13 hmb 注释
                        //newTd10.innerHTML =  $scope.changeNumberToPosiNumber((data[i-1].feeIntas == 0 ? 0 :parseFloat((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0))*100/data[i-1].feeIntas).toFixed(2))+"%";

                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber((AboiFristRowVal445 == 0 ? 0 :parseFloat(AboiFristRowValE - AboiFristRowVal445)*100 / AboiFristRowVal445 ).toFixed(2))+"%";
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity%"){
                            newTd10.innerHTML = "0.00%";
                        }
                        var newTd11 = newTr.insertCell();
                        var aboi11B = isNaN(data[i - 1].sumMoney)? 0: data[i - 1].sumMoney;
                        var aboi11E = newTd8.innerHTML == "-   " ? 0 : newTd8.innerHTML;
                        newTd11.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(aboi11E) - (aboi11B == 0 ? 0 : data[i - 1].sumMoney)).toFixed(0));
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"||newTd11.innerHTML=="Infinity"){
                            newTd11.innerHTML = "-   ";
                        }
                        var newTd12 = newTr.insertCell();
                        newTd12.innerHTML =   $scope.changeNumberToPosiNumber( (data[i - 1].sumMoney == 0 ? 0 : parseFloat(newTd11.innerHTML)*100/data[i - 1].sumMoney).toFixed(2) )+"%";
                        if(newTd12.innerHTML == "0"||newTd12.innerHTML =="0.00"||newTd12.innerHTML=="NaN"||newTd12.innerHTML=="Infinity%"){
                            newTd12.innerHTML = "0.00%";
                        }
                    }


                    if(data2[i-1].oiType =="BEOI") {
                        var newTd13 = newTr.insertCell();
                        newTd13.colSpan = 7;
                        newTd13.rowSpan = $scope.datanum+ 6;
                        newTd13.style = "border-right: 3px solid !important;";
                        //console.log("当前remark字段可见情况:" + $scope.controllTabShow.remarkFlag + ", status:" + $scope.params.status);
                        if ($scope.controllTabShow.remarkFlag || $scope.params.status == 'C') { //问卷remark信息控制, 返回true可见，否则不可见。
                            newTd13.innerHTML ="<strong><textarea id='bicremark' style='width: 100%;height: 100%'>"+$scope.analysisData.bicRemark+"</textarea></strong>";
                        } else {
                            //console.log("当前remark字段不可见:" + $scope.controllTabShow.remarkFlag)
                            newTd13.innerHTML ="<strong><textarea id='bicremark' style='width: 100%;height: 100%'></textarea></strong>";
                        }
                    }
                } else{

                    if(i == data2.length){ //前端生成的html顺序是从下往上开始生成的,data2.length 是表格的倒叙第一行,分类型(BEOI,SROI,ABOI)查看
                        var newTr = tb.insertRow(18);//添加新行，trIndex就是要添加的位置
                        newTr.style = "background:#D3FF93 !important;font-weight: bold;";
                        newTr.setAttribute("class","analaysisOiType");

                        var newTd1 = newTr.insertCell();
                        newTd1.innerHTML =   data2[i-1].oiType+"%(as of purchase)";
                        newTd1.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd1.colSpan  = 2;

                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = newTr.insertCell();

                            var tnewTd222 = newTr.insertCell();
                            tnewTd222.innerHTML = "0.00%";

                            var tnewTd223 = newTr.insertCell();
                            if($scope.mid.Purchase113 ==0){
                                tnewTd223.innerHTML =   "0.00%";
                            }else{
                                tnewTd223.innerHTML =   ($scope.TotalBeoiAA*100/$scope.mid.Purchase113).toFixed(2)+"%";
                            }

                            var tnewTd3 = newTr.insertCell();
                            if ($scope.header.Ne == 'E') {//续签供应商
                                if ($scope.mid.Purchase1 == 0) {
                                    tnewTd2.innerHTML = "0.00%";
                                } else {
                                    tnewTd2.innerHTML = ($scope.TotalBeoiA / $scope.mid.Purchase1 * 100).toFixed(2) + "%";
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    tnewTd3.innerHTML = "0.00%";
                                } else {
                                    tnewTd3.innerHTML = ($scope.TotalBeoiB / $scope.mid.Purchase2 * 100).toFixed(2) + "%";
                                }
                            } else {
                                tnewTd2.innerHTML = "0.00%";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =   "0.00%";
                            }else{
                                tnewTd4.innerHTML =   ($scope.TotalBeoiC/$scope.mid.Purchase3*100).toFixed(2)+"%";
                            }

                            var tnewTd5 = newTr.insertCell();
                            tnewTd5.innerHTML =   "0.00%";
                            //if($scope.mid.Purchase4 ==0){
                            //    tnewTd5.innerHTML =   0;
                            //}else{
                            //    tnewTd5.innerHTML =   ($scope.TotalBeoiD/$scope.mid.Purchase4).toFixed(2)+"%";
                            //}

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =   "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalBeoiC*100/$scope.mid.Purchase5).toFixed(2)+"%";
                            }

                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            //newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase3 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase3*100 ).toFixed(2)) - parseFloat(( $scope.mid.Purchase1 == 0 ? 0 :$scope.TotalBeoiA/$scope.mid.Purchase1*100).toFixed(2))).toFixed(2) )+"%";
                            //$scope.header.BEOI= $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase3 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase3*100).toFixed(2)) - parseFloat(( $scope.mid.Purchase1 == 0 ? 0  :$scope.TotalBeoiA/$scope.mid.Purchase1*100).toFixed(2))).toFixed(2) ) +"%";
                            newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase5 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase5*100 ).toFixed(2)) - parseFloat(( $scope.mid.Purchase113 == 0 ? 0 :$scope.TotalBeoiAA/$scope.mid.Purchase113*100).toFixed(2))).toFixed(2) )+"%";
                            $scope.header.BEOI= $scope.changeNumberToPosiNumber((parseFloat(( $scope.mid.Purchase5 == 0 ? 0 :$scope.TotalBeoiC/$scope.mid.Purchase5*100).toFixed(2)) - parseFloat(( $scope.mid.Purchase113 == 0 ? 0  :$scope.TotalBeoiAA/$scope.mid.Purchase113*100).toFixed(2))).toFixed(2) ) +"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd6.innerHTML.replace("%","")) - parseFloat(tnewTd3.innerHTML.replace("%",""))).toFixed(2))+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }

                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = newTr.insertCell();

                            var tnewTd222 = newTr.insertCell();
                            tnewTd222.innerHTML = "0.00%";
                            var tnewTd223 = newTr.insertCell();
                            if($scope.mid.Purchase113 ==0){
                                tnewTd223.innerHTML =   "0.00%";
                            }else{
                                tnewTd223.innerHTML =   ($scope.TotalSroiAB*100/$scope.mid.Purchase113).toFixed(2)+"%";
                                if(tnewTd223.innerHTML == "0"||tnewTd223.innerHTML =="0.00"||tnewTd223.innerHTML=="NaN"){
                                    tnewTd223.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd3 = newTr.insertCell();

                            if ($scope.header.Ne == 'E') {//续签供应商
                                if ($scope.mid.Purchase1 == 0) {
                                    //tnewTd2.innerHTML = "-   ";
                                    tnewTd2.innerHTML = "0.00%";
                                } else {
                                    tnewTd2.innerHTML = ($scope.TotalSroiA * 100 / $scope.mid.Purchase1).toFixed(2) + "%";
                                    if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                        //tnewTd2.innerHTML = "-   ";
                                        tnewTd2.innerHTML = "0.00%";
                                    }
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    //tnewTd3.innerHTML = "-   ";
                                    tnewTd3.innerHTML = "0.00%";
                                } else {
                                    tnewTd3.innerHTML = ($scope.TotalSroiB * 100 / $scope.mid.Purchase2).toFixed(2) + "%";
                                    if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                        //tnewTd3.innerHTML = "-   ";
                                        tnewTd3.innerHTML = "0.00%";
                                    }
                                }
                            } else {
                                //tnewTd2.innerHTML = "-   ";
                                //tnewTd3.innerHTML = "-   ";
                                tnewTd2.innerHTML = "0.00%";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();

                            if($scope.mid.Purchase3 ==0){
                                //tnewTd4.innerHTML =   "-   ";
                                tnewTd4.innerHTML =   "0.00%";
                            }else{
                                //TTA列
                                tnewTd4.innerHTML =   ($scope.TotalSroiC*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                    //tnewTd4.innerHTML = "-   ";
                                    tnewTd4.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd5 = newTr.insertCell();
                            tnewTd5.innerHTML ="0.00%";
                            //if($scope.mid.Purchase4 ==0){
                            //    tnewTd5.innerHTML =   0;
                            //}else{
                            //    //tnewTd5.innerHTML =   $scope.TotalSroiD/$scope.mid.Purchase4;
                            //    tnewTd5.innerHTML =   0;
                            //}

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                //tnewTd6.innerHTML =   "-   ";
                                tnewTd6.innerHTML =   "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalSroiE*100/$scope.mid.Purchase5).toFixed(2)+"%";
                                if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                    //tnewTd6.innerHTML = "-   ";
                                    tnewTd6.innerHTML = "0.00%";
                                }
                            }

                            var newTd7 = newTr.insertCell();
                            //newTd7.innerHTML =   ($scope.TotalSroiC - $scope.TotalSroiA).toFixed(2)+"%";
                            newTd7.innerHTML = "n/a";
                            //下面的newTd7代码没用到,可以注释掉,多余代码
                            /!*if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                                newTd7.innerHTML = "-   ";
                            }*!/
                            var newTd8 = newTr.insertCell();
                            //2020.2.13 hmb注释
                            //newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd6.innerHTML.replace("%","")) - parseFloat(tnewTd223.innerHTML.replace("%",""))).toFixed(2) )+"%";

                            if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                                newTd8.innerHTML = "0.00%";
                            }
                            $scope.header.SROI=$scope.changeNumberToPosiNumber((parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2))+"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            var sroiVar10E = $scope.mid.Purchase5;
                            var sroiVar10B = $scope.mid.Purchase2;
                            newTd10.innerHTML =   $scope.changeNumberToPosiNumber( ((sroiVar10E == 0 ? 0 :$scope.TotalSroiE*100/$scope.mid.Purchase5).toFixed(2) - (sroiVar10B == 0 ? 0 : $scope.TotalSroiB*100/$scope.mid.Purchase2).toFixed(2)).toFixed(2))+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }

                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            //计算(当年proposal制作年度 - 1) 的TTA 和 Actual 的列的值
                            var tnewTd2 = newTr.insertCell();

                            var tnewTd222 = newTr.insertCell();
                            if($scope.mid.Purchase1 ==0){
                                tnewTd222.innerHTML =   "0.00%";
                            }else{
                                //计算的是用户编辑入的数据
                                tnewTd222.innerHTML =   ($scope.TotalAboiAA*100/ $scope.mid.Purchase1 ).toFixed(2)+"%";
                                if(tnewTd222.innerHTML == "0"||tnewTd222.innerHTML =="0.00"||tnewTd222.innerHTML=="NaN"){
                                    tnewTd222.innerHTML = "0.00%";
                                }
                            }
                            var tnewTd223 = newTr.insertCell();
                            if($scope.mid.Purchase113 ==0){
                                tnewTd223.innerHTML =  "0.00%";
                            }else{
                                tnewTd223.innerHTML =   ($scope.TotalAboiAB*100/$scope.mid.Purchase113).toFixed(2)+"%";
                                if(tnewTd223.innerHTML == "0"||tnewTd223.innerHTML =="0.00"||tnewTd223.innerHTML=="NaN"){
                                    tnewTd223.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd3 = newTr.insertCell();
                            if ($scope.header.Ne == 'E'){//续签供应商
                                if($scope.mid.Purchase1 ==0){
                                    tnewTd2.innerHTML =  "0.00%";
                                }else{
                                    tnewTd2.innerHTML =   ($scope.TotalAboiA*100/$scope.mid.Purchase1).toFixed(2)+"%";
                                    if(tnewTd2.innerHTML == "0"||tnewTd2.innerHTML =="0.00"||tnewTd2.innerHTML=="NaN"){
                                        tnewTd2.innerHTML = "0.00%";
                                    }
                                }

                                tnewTd3.style="border-right: 3px solid #000000 !important;text-align: center;";
                                if($scope.mid.Purchase2 ==0){
                                    tnewTd3.innerHTML =   "0.00%";
                                }else{
                                    tnewTd3.innerHTML =   ($scope.TotalAboiB*100/$scope.mid.Purchase2).toFixed(2)+"%";
                                    if(tnewTd3.innerHTML == "0"||tnewTd3.innerHTML =="0.00"||tnewTd3.innerHTML=="NaN"){
                                        tnewTd3.innerHTML = "0.00%";
                                    }
                                }

                            } else {//新增供应商默认为0.00%
                                tnewTd2.innerHTML =  "0.00%";
                                tnewTd3.style="border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "0.00%";
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =  "0.00%";
                            } else {
                                tnewTd4.innerHTML =   ($scope.TotalAboiC*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                    tnewTd4.innerHTML = "0.00%";
                                }
                            }
                            var tnewTd5 = newTr.insertCell();
                            if($scope.mid.Purchase4 ==0){
                                tnewTd5.innerHTML =   "0.00%";
                            }else{
                                //计算的是用户编辑入的数据
                                tnewTd5.innerHTML =   ($scope.TotalAboiD*100/$scope.mid.Purchase3).toFixed(2)+"%";
                                if(tnewTd5.innerHTML == "0"||tnewTd5.innerHTML =="0.00"||tnewTd5.innerHTML=="NaN"){
                                    tnewTd5.innerHTML = "0.00%";
                                }
                            }

                            var tnewTd6 = newTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =  "0.00%";
                            }else{
                                tnewTd6.innerHTML =   ($scope.TotalAboiE*100/$scope.mid.Purchase5).toFixed(2)+"%";
                                if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                    tnewTd6.innerHTML = "0.00%";
                                }
                            }


                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            //2020.2.13注释
                            //newTd8.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            //$scope.header.ABOI=  $scope.changeNumberToPosiNumber( (parseFloat(tnewTd4.innerHTML.replace("%","")) - parseFloat(tnewTd2.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            newTd8.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(tnewTd6.innerHTML.replace("%","")) - parseFloat(tnewTd223.innerHTML.replace("%",""))).toFixed(2) )+"%";
                            $scope.header.ABOI=  $scope.changeNumberToPosiNumber( (parseFloat(tnewTd6.innerHTML.replace("%","")) - parseFloat(tnewTd223.innerHTML.replace("%",""))).toFixed(2) )+"%";

                            var newTd9 = newTr.insertCell();
                            newTd9.innerHTML =   "n/a";
                            var newTd10 = newTr.insertCell();
                            newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(tnewTd6.innerHTML) - parseFloat(tnewTd3.innerHTML)).toFixed(2) )+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity%"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }


                        var tnewTr = tb.insertRow(18);//添加新行，trIndex就是要添加的位置
                        tnewTr.style = "font-weight: bold;";
                        tnewTr.setAttribute("class","analaysisOiType");
                        var tnewTd1 = tnewTr.insertCell();//TOTAL
                        tnewTd1.innerHTML =   "TOTAL "+data2[i-1].oiType+"$";
                        tnewTd1.style="border-right: 3px solid #000000 !important;text-align: center;";
                        tnewTd1.colSpan  = 2;

                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();

                            var tnewTd222 = tnewTr.insertCell();//On Top 列
                            tnewTd222.innerHTML = "-  ";
                            var tnewTd223 = tnewTr.insertCell();//TTA+On Top 列
                            if($scope.mid.Purchase5==0){
                                tnewTd223.innerHTML = 0;
                            }else{
                                tnewTd223.innerHTML =   (parseFloat($scope.TotalBeoiAA)).toFixed();
                            }

                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                if ($scope.mid.Purchase1 == 0) {
                                    tnewTd2.innerHTML = 0;
                                } else {
                                    tnewTd2.innerHTML = (parseFloat($scope.TotalBeoiA)).toFixed(0);
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                if ($scope.mid.Purchase2 == 0) {
                                    tnewTd3.innerHTML = 0;
                                } else {
                                    tnewTd3.innerHTML = (parseFloat($scope.TotalBeoiB)).toFixed(0);
                                }
                            } else {
                                tnewTd2.innerHTML = 0;
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = 0;
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            if($scope.mid.Purchase3==0){
                                tnewTd4.innerHTML = 0;
                            }else{
                                tnewTd4.innerHTML =   (parseFloat($scope.TotalBeoiC)).toFixed(0);
                            }

                            var tnewTd5 = tnewTr.insertCell();
                            tnewTd5.innerHTML =   "-  ";

                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            if($scope.mid.Purchase5==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   (parseFloat($scope.TotalBeoiC)).toFixed();
                            }
                            var tnewTd7 = tnewTr.insertCell();
                            //2020.2.13注释
                            //tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiA)).toFixed(0) );
                            tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiAA)).toFixed(0) );
                            var tnewTd8 = tnewTr.insertCell();
                            //2020.2.13 hmb注释
                            //tnewTd8.innerHTML =$scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiA))*100/parseFloat($scope.TotalBeoiA)).toFixed(2) )+"%";
                            tnewTd8.innerHTML =$scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiAA))*100/parseFloat($scope.TotalBeoiAA)).toFixed(2) )+"%";
                            var tnewTd9 = tnewTr.insertCell();
                            tnewTd9.innerHTML =  $scope.changeNumberToPosiNumber( ( parseFloat($scope.TotalBeoiC) - parseFloat($scope.TotalBeoiB)).toFixed(0) );
                            var tnewTd10 = tnewTr.insertCell();

                            tnewTd10.innerHTML =  $scope.changeNumberToPosiNumber( myToFixed((($scope.TotalBeoiC - $scope.TotalBeoiB).toFixed(0))*100/parseFloat($scope.TotalBeoiB),2) )+"%";
                            //tnewTd10.innerHTML =  ($scope.TotalBeoiC - $scope.TotalBeoiB).toFixed(0);
                        }

                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = tnewTr.insertCell();

                            var tnewTd222 = tnewTr.insertCell();
                            tnewTd222.innerHTML = "-    ";
                            var tnewTd223 = tnewTr.insertCell();
                            tnewTd223.innerHTML =   (parseFloat($scope.TotalSroiAB)).toFixed(0);
                            if(tnewTd223.innerHTML == "0"||tnewTd223.innerHTML =="0.00"||tnewTd223.innerHTML=="NaN"){
                                tnewTd223.innerHTML = "-   ";
                            }

                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                tnewTd2.innerHTML = (parseFloat($scope.TotalSroiA)).toFixed(0);
                                if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                    tnewTd2.innerHTML = "-   ";
                                }
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = (parseFloat($scope.TotalSroiB)).toFixed(0);
                                if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                    tnewTd3.innerHTML = "-   ";
                                }

                            } else {
                                tnewTd2.innerHTML = "-   ";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "-   ";
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   (parseFloat($scope.TotalSroiC)).toFixed(0);
                            if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                tnewTd4.innerHTML = "-   ";
                            }
                            var tnewTd5 = tnewTr.insertCell();
                            //tnewTd5.innerHTML =   $scope.TotalSroiD;
                            tnewTd5.innerHTML =   "-    ";
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            tnewTd6.innerHTML =   (parseFloat($scope.TotalSroiE)).toFixed(0);
                            if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                tnewTd6.innerHTML = "-   ";
                            }
                            var tnewTd7 = tnewTr.insertCell();
                            //2020.2.13 hmb 注释
                            //tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiE -$scope.TotalSroiA).toFixed(0));
                            tnewTd7.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiE -$scope.TotalSroiAB).toFixed(0));
                            if(tnewTd7.innerHTML == "0"||tnewTd7.innerHTML =="0.00"||tnewTd7.innerHTML=="NaN"){
                                tnewTd7.innerHTML = "-   ";
                            }
                            var tnewTd8 = tnewTr.insertCell();
                            //2020.2.13 hmb注释
                            //var sroi8A= $scope.TotalSroiA;
                            //tnewTd8.innerHTML = $scope.changeNumberToPosiNumber((sroi8A == 0 ? 0 : (($scope.TotalSroiE -$scope.TotalSroiA)*100).toFixed(0)/$scope.TotalSroiA ).toFixed(2)) +"%";
                            var sroi8A= $scope.TotalSroiAB;
                            tnewTd8.innerHTML = $scope.changeNumberToPosiNumber((sroi8A == 0 ? 0 : (($scope.TotalSroiE -$scope.TotalSroiAB)*100).toFixed(0)/$scope.TotalSroiAB ).toFixed(2)) +"%";

                            if(tnewTd8.innerHTML == "0"||tnewTd8.innerHTML =="0.00"||tnewTd8.innerHTML=="NaN"){
                                tnewTd8.innerHTML = "0.00%";
                            }
                            var tnewTd9 = tnewTr.insertCell();
                            tnewTd9.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiE -$scope.TotalSroiB).toFixed(0));
                            if(tnewTd9.innerHTML == "0"||tnewTd9.innerHTML =="0.00"||tnewTd9.innerHTML=="NaN"){
                                tnewTd9.innerHTML = "-   ";
                            }
                            var tnewTd10 = tnewTr.insertCell();
                            tnewTd10.innerHTML =  $scope.changeNumberToPosiNumber(($scope.TotalSroiB == 0 ? 0 : (($scope.TotalSroiE -$scope.TotalSroiB)*100).toFixed(0)/$scope.TotalSroiB).toFixed(2) )+"%";
                            if(tnewTd10.innerHTML == "0"||tnewTd10.innerHTML =="0.00"||tnewTd10.innerHTML=="NaN"){
                                tnewTd10.innerHTML = "0.00%";
                            }
                        }

                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();

                            var tnewTd222 = tnewTr.insertCell();
                            tnewTd222.id = "oldAboiTailSum";
                            tnewTd222.innerHTML = 0;//开始默认为0,然后加载完html,再获取ABOI类型的条款总值
                            if(tnewTd222.innerHTML == "0"||tnewTd222.innerHTML =="0.00"||tnewTd222.innerHTML=="NaN"){
                                tnewTd222.innerHTML = "-   ";
                            }
                            var tnewTd223 = tnewTr.insertCell();
                            tnewTd223.innerHTML =   (parseFloat($scope.TotalAboiAB)).toFixed(0);
                            if(tnewTd223.innerHTML == "0" || tnewTd223.innerHTML =="0.00" || tnewTd223.innerHTML=="NaN"){
                                tnewTd223.innerHTML = "-   ";
                            }

                            var tnewTd3 = tnewTr.insertCell();

                            if ($scope.header.Ne == 'E') {
                                tnewTd2.innerHTML = (parseFloat($scope.TotalAboiA)).toFixed(0);
                                if (tnewTd2.innerHTML == "0" || tnewTd2.innerHTML == "0.00" || tnewTd2.innerHTML == "NaN") {
                                    tnewTd2.innerHTML = "-   ";
                                }

                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = (parseFloat($scope.TotalAboiB)).toFixed(0);
                                if (tnewTd3.innerHTML == "0" || tnewTd3.innerHTML == "0.00" || tnewTd3.innerHTML == "NaN") {
                                    tnewTd3.innerHTML = "-   ";
                                }

                            } else {
                                tnewTd2.innerHTML = "-   ";
                                tnewTd3.style = "border-right: 3px solid #000000 !important;text-align: center;";
                                tnewTd3.innerHTML = "-   ";
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   (parseFloat($scope.TotalAboiC)).toFixed(0);
                            if(tnewTd4.innerHTML == "0"||tnewTd4.innerHTML =="0.00"||tnewTd4.innerHTML=="NaN"){
                                tnewTd4.innerHTML = "-   ";
                            }
                            var tnewTd5 = tnewTr.insertCell();
                            //tnewTd5.innerHTML =   $scope.TotalAboiD;
                            //tnewTd5.innerHTML = $scope.ABOITail.sum;
                            tnewTd5.id = "aboitailsum";
                            tnewTd5.innerHTML = 0;//开始默认为0
                            if(tnewTd5.innerHTML == "0"||tnewTd5.innerHTML =="0.00"||tnewTd5.innerHTML=="NaN"){
                                tnewTd5.innerHTML = "-   ";
                            }
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.style="border-right: 3px solid #000000 !important;text-align: center;";
                            tnewTd6.innerHTML =   (parseFloat($scope.TotalAboiE)).toFixed(0);
                            if(tnewTd6.innerHTML == "0"||tnewTd6.innerHTML =="0.00"||tnewTd6.innerHTML=="NaN"){
                                tnewTd6.innerHTML = "-   ";
                            }
                            var newTd7 = tnewTr.insertCell();
                            //2020.2.13 hmb 注释
                            //newTd8.innerHTML = $scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalAboiC)).toFixed(0) - (parseFloat($scope.TotalAboiA)).toFixed(0)).toFixed(0) );
                            newTd7.innerHTML = $scope.changeNumberToPosiNumber( ((parseFloat($scope.TotalAboiE)).toFixed(0) - (parseFloat($scope.TotalAboiAB)).toFixed(0)).toFixed(0) );
                            if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"||newTd7.innerHTML=="Infinity"){
                                newTd7.innerHTML = "-   ";
                            }
                            var newTd8 = tnewTr.insertCell();
                            //2020.2.13 hmb 注释
                            //newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat((parseFloat($scope.TotalAboiC)).toFixed(0) - (parseFloat($scope.TotalAboiA)).toFixed(0))*100/parseFloat($scope.TotalAboiA)).toFixed(2) )+"%";
                            newTd8.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat((parseFloat($scope.TotalAboiE)).toFixed(0) - (parseFloat($scope.TotalAboiAB)).toFixed(0))*100/parseFloat($scope.TotalAboiAB)).toFixed(2) )+"%";
                            if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity%"){
                                newTd8.innerHTML = "0.00%";
                            }
                            var newTd9 = tnewTr.insertCell();
                            newTd9.innerHTML = $scope.changeNumberToPosiNumber((parseFloat($scope.TotalAboiE) - parseFloat($scope.TotalAboiB)).toFixed(0));
                            if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity"){
                                newTd9.innerHTML = "-   ";
                            }
                            var newTd10 = tnewTr.insertCell();
                            newTd10.innerHTML =  $scope.changeNumberToPosiNumber(((parseFloat($scope.TotalAboiE) - parseFloat($scope.TotalAboiB))*100/parseFloat($scope.TotalAboiB)).toFixed(2))+"%";
                            if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity%"){
                                newTd10.innerHTML = "0.00%";
                            }
                        }

                    }

                    var newTr = tb.insertRow(18);//添加新行，trIndex就是要添加的位置
                    newTr.setAttribute("class","analaysisOiType");
                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML =   data2[i-1].termsEn;
                    newTd1.style = "text-align: left;";
                    var newTd2 = newTr.insertCell();
                    newTd2.style="border-right: 3px solid #000000 !important;text-align: left;";
                    newTd2.innerHTML =   data2[i-1].termsCn;

                    if(data2[i-1].oiType.indexOf("BEOI")>=0) {//中间行
                        var newTd3 = newTr.insertCell();
                        newTd3.innerHTML = (data[i - 1].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";

                        var newTd333 = newTr.insertCell();
                        newTd333.innerHTML = "0.00%";
                        var newTd334 = newTr.insertCell();
                        newTd334.innerHTML =   (data[i - 1].feeIntas / $scope.dataTable.contractLineList2018[0].purchase*100).toFixed(2)+"%";

                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        //hmb查 实际费用
                        newTd4.innerHTML = (data[i - 1].sumMoney*100 / $scope.mid.Purchase2).toFixed(2)+"%";
                        var newTd5 = newTr.insertCell();
                        //费用预估
                        newTd5.innerHTML = (data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                    }

                    if(data2[i-1].oiType.indexOf("SROI")>=0) {
                        var newTd3 = newTr.insertCell();
                        newTd3.innerHTML = (parseFloat(data[i - 1].feeIntas)).toFixed(0);
                        if(newTd3.innerHTML == "0"||newTd3.innerHTML =="0.00"||newTd3.innerHTML=="NaN"||newTd3.innerHTML=="Infinity%"){
                            newTd3.innerHTML = "-   ";
                        }

                        var newTd333 = newTr.insertCell();
                        newTd333.innerHTML = "-   ";
                        var newTd334 = newTr.insertCell();
                        newTd334.innerHTML =   data[i-1].feeIntas;
                        if(newTd334.innerHTML == "0"|| newTd334.innerHTML =="0.00"|| newTd334.innerHTML=="NaN"){
                            newTd334.innerHTML = "-   ";
                        }

                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd4.innerHTML = (parseFloat(data[i - 1].sumMoney)).toFixed(0);
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"||newTd4.innerHTML=="Infinity%"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.innerHTML = (parseFloat(data2[i - 1].feeIntas)).toFixed(0);
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"||newTd5.innerHTML=="Infinity%"){
                            newTd5.innerHTML = "-   ";
                        }
                    }

                    if(data2[i-1].oiType.indexOf("ABOI")>=0) {
                        var newTd3 = newTr.insertCell();
                        newTd3.id = "oldYeaABOILine"+(i-1)+1;
                        newTd3.innerHTML = (parseFloat(data[i - 1].feeIntas)).toFixed(0);//往年费用预估(含税)
                        if(newTd3.innerHTML == "0"||newTd3.innerHTML =="0.00"||newTd3.innerHTML=="NaN"||newTd3.innerHTML=="Infinity%"){
                            newTd3.innerHTML = "-   ";
                        }

                        var newTd333 = newTr.insertCell();

                        var oldYearOnTopObj = null;
                        var aboiValue = 0;
                        if($scope.oldAnalysisData.aboiList!=undefined){
                            oldYearOnTopObj = JSON.parse($scope.oldAnalysisData.aboiList);//解析为JSON对象
                            var attrKey = "A" + data[i-1].contractLId;
                            aboiValue = oldYearOnTopObj[attrKey];
                            if(aboiValue==undefined || aboiValue == ""){
                                aboiValue = 0;
                            }
                        }

                        newTd333.id = "oldYearABOI"+(i-1);
                        newTd333.innerHTML = "<input type='text' name ='A"+data[i-1].contractLId+"' id='BABOI"+oldYearABOILineNUM+"' style='width: 100%;text-align: center;'  class='form-control radius3' required value='"+aboiValue+"'>";
                        oldYearABOILineNUM = oldYearABOILineNUM + 1;

                        var newTd334 = newTr.insertCell();
                        newTd334.id = "oldYeaABOILine"+(i-1)+2;
                        newTd334.innerHTML =   (parseFloat(data[i - 1].feeIntas)+parseFloat(aboiValue)).toFixed(0);
                        if(newTd334.innerHTML == "0"|| newTd334.innerHTML =="0.00"||newTd334.innerHTML=="NaN"||newTd334.innerHTML=="Infinity"){
                            newTd334.innerHTML = "-   ";
                        }

                        var newTd4 = newTr.insertCell();
                        newTd4.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd4.innerHTML = (parseFloat(data[i - 1].sumMoney)).toFixed(0);//往年实际费用(含税)
                        if(newTd4.innerHTML == "0"||newTd4.innerHTML =="0.00"||newTd4.innerHTML=="NaN"||newTd4.innerHTML=="Infinity%"){
                            newTd4.innerHTML = "-   ";
                        }
                        var newTd5 = newTr.insertCell();
                        newTd5.id = "ABOILine"+(i-1)+1;
                        newTd5.innerHTML = (parseFloat(data2[i - 1].feeIntas)).toFixed(0);//当前费用预估(含税)
                        if(newTd5.innerHTML == "0"||newTd5.innerHTML =="0.00"||newTd5.innerHTML=="NaN"||newTd5.innerHTML=="Infinity%"){
                            newTd5.innerHTML = "-   ";
                        }
                    }


                    var newTd6 = newTr.insertCell();

                    var obj1 = null;
                    var abval1 = 0;
                    if($scope.analysisData.aboiList != undefined){
                        obj1 = JSON.parse($scope.analysisData.aboiList);
                        //abval1 = eval('obj1.A'+data2[i-1].contractLId);
                        var attrKey = "A" + data2[i-1].contractLId;
                        abval1 = obj1[attrKey];
                        if(abval1==undefined || abval1 == ""){
                            abval1 = 0;
                        }

                        //另外一种取属性值的方法:
                        //var attrKey = "A" + data2[i-1].contractLId;
                        //abval1 = obj1[attrKey];
                    }

                    //合同行数据分类处理
                    if(data2[i-1].oiType.indexOf("BEOI")>=0){
                        newTd6.innerHTML = "0.00%";
                    }

                    if(data2[i-1].oiType.indexOf("SROI")>=0) {
                        newTd6.innerHTML = "-   ";
                    }
                    if(data2[i-1].oiType.indexOf("ABOI")>=0){
                        newTd6.id = "ABOI"+(i-1);
                        newTd6.innerHTML = "<input type='text' name ='A"+data2[i-1].contractLId+"' id='AABOI"+ABOIlineNUM+"' style='width: 100%;text-align: center;'  class='form-control radius3' required value='"+abval1+"'>";
                        ABOIlineNUM = ABOIlineNUM+1;
                    }

                    if(data2[i-1].oiType.indexOf("BEOI")>=0){//中间行
                        var newTd7 = newTr.insertCell();
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.innerHTML =   (data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)).toFixed(2)+"%";
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   "n/a";
                        var newTd9 = newTr.insertCell();
                        //2020.2.13 hmb 注释
                        //var beoiFcsPurchaseC = isNaN($scope.adata.fpurchase/1000)?0:$scope.adata.fpurchase/1000;
                        //var beoiFcsPurchaseA = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
                        //newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (( beoiFcsPurchaseC == 0 ? 0: data2[i - 1].feeIntas*100 / beoiFcsPurchaseC )- (beoiFcsPurchaseA == 0? 0:data[i - 1].feeIntas*100 / beoiFcsPurchaseA) ).toFixed(2))+"%";

                        var beoiFcsPurchaseC = isNaN($scope.adata.fpurchase/1000)?0:$scope.adata.fpurchase/1000;
                        var beoiFcsPurchaseA = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
                        newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (( beoiFcsPurchaseC == 0 ? 0: data2[i - 1].feeIntas*100 / beoiFcsPurchaseC )- (beoiFcsPurchaseA == 0? 0:data[i - 1].feeIntas*100 / beoiFcsPurchaseA) ).toFixed(2))+"%";

                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   "n/a";
                        var newTd11 = newTr.insertCell();

                        var var11A = $scope.adata.fpurchase/1000;
                        var var11B = $scope.mid.Purchase2;
                        newTd11.innerHTML =  $scope.changeNumberToPosiNumber(( (var11A == 0?0:data2[i - 1].feeIntas*100 / ($scope.adata.fpurchase/1000)) - (var11B == 0? 0:data[i - 1].sumMoney*100 / $scope.mid.Purchase2)).toFixed(2))+"%";
                    }

                    if(data2[i-1].oiType.indexOf("SROI")>=0){
                        var newTd7 = newTr.insertCell();//第五列
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.innerHTML =   data2[i-1].feeIntas;
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   $scope.changeNumberToPosiNumber((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0) );
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"){
                            newTd8.innerHTML = "-  ";
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =  $scope.changeNumberToPosiNumber(((data2[i-1].feeIntas - data[i-1].feeIntas)*100/data[i - 1].feeIntas).toFixed(2))+"%";
                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity"){
                            newTd9.innerHTML = "0.00%";
                        }
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   $scope.changeNumberToPosiNumber( (parseFloat(newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML) - parseFloat(newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML)).toFixed(0));
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"){
                            newTd10.innerHTML = "-   ";
                        }
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =  $scope.changeNumberToPosiNumber( (parseFloat(newTd10.innerHTML.indexOf("- ")>=0?0:newTd10.innerHTML)*100/parseFloat(newTd4.innerHTML)).toFixed(2))+"%";
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"){
                            newTd11.innerHTML = "0.00%";
                        }
                    }

                    if(data2[i-1].oiType.indexOf("ABOI")>=0){
                        var newTd7 = newTr.insertCell();//第五列
                        newTd7.style="border-right: 3px solid #000000 !important;text-align: center;";
                        newTd7.id = "ABOILine"+(i-1)+2;
                        newTd7.innerHTML =   (parseFloat(data2[i - 1].feeIntas)+parseFloat(abval1)).toFixed(0);
                        if(newTd7.innerHTML == "0"||newTd7.innerHTML =="0.00"||newTd7.innerHTML=="NaN"||newTd7.innerHTML=="Infinity"){
                            newTd7.innerHTML = "-   ";
                        }
                        var newTd8 = newTr.insertCell();
                        //2020.2.13 hbm 注释
                        //newTd8.innerHTML =  $scope.changeNumberToPosiNumber( (data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0));
                        var AboiRowValueE = (parseFloat(data2[i - 1].feeIntas)+parseFloat(abval1)).toFixed(0);
                        var AboiRowValue334 = (parseFloat(data[i - 1].feeIntas)+parseFloat(aboiValue)).toFixed(0);
                        newTd8.innerHTML =  $scope.changeNumberToPosiNumber( (AboiRowValueE - AboiRowValue334).toFixed(0) );
                        if(newTd8.innerHTML == "0"||newTd8.innerHTML =="0.00"||newTd8.innerHTML=="NaN"||newTd8.innerHTML=="Infinity"){
                            newTd8.innerHTML = "-   ";
                        }
                        var newTd9 = newTr.insertCell();
                        //2020.2.13 hmb 注释
                        //var avg9=data[i-1].feeIntas;
                        //newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (avg9 ==0?0:parseFloat((data2[i-1].feeIntas - data[i-1].feeIntas).toFixed(0))*100/data[i-1].feeIntas).toFixed(2))+"%";

                        var avg9=AboiRowValue334;
                        newTd9.innerHTML =   $scope.changeNumberToPosiNumber( (avg9 ==0?0:parseFloat((AboiRowValueE - AboiRowValue334).toFixed(0))*100/avg9).toFixed(2))+"%";

                        if(newTd9.innerHTML == "0"||newTd9.innerHTML =="0.00"||newTd9.innerHTML=="NaN"||newTd9.innerHTML=="Infinity%"){
                            newTd9.innerHTML = "0.00%";
                        }
                        var newTd10 = newTr.insertCell();
                        var cell5 = newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML;
                        var cell2 = newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML;
                        newTd10.innerHTML =  $scope.changeNumberToPosiNumber((parseFloat(newTd7.innerHTML.indexOf("- ")>=0?0:newTd7.innerHTML) - parseFloat(newTd4.innerHTML.indexOf("- ")>=0?0:newTd4.innerHTML)).toFixed(0));
                        if(newTd10.innerHTML == "0"||newTd10.innerHTML =="0.00"||newTd10.innerHTML=="NaN"||newTd10.innerHTML=="Infinity"){
                            newTd10.innerHTML = "-   ";
                        }
                        var newTd11 = newTr.insertCell();
                        var suml = data[i - 1].sumMoney;
                        newTd11.innerHTML =   $scope.changeNumberToPosiNumber( (suml==0?0:parseFloat(newTd10.innerHTML.indexOf("- ")>=0?0:newTd10.innerHTML)*100/suml).toFixed(2))+"%";
                        if(newTd11.innerHTML == "0"||newTd11.innerHTML =="0.00"||newTd11.innerHTML=="NaN"||newTd11.innerHTML=="Infinity%"){
                            newTd11.innerHTML = "0.00%";
                        }
                    }
                }
            }
        }*/


        $scope.header = {};
        $scope.analysisData = {};
        $scope.oldAnalysisData = {};
        $scope.mid = {};
        $scope.tail = {};
        $scope.adata = {};
        $scope.datanum = 0;
        $scope.proposalNewYear = "";

        /**
         * 将数字转换为正数   例如: 3 ---> +3;  -3 --->-3
         * @value:输入的数字
         */
        $scope.changeNumberToPosiNumber = function(value) {
            //(value <= 0?"''：+") + value;
            return (value <= 0? "":"+") + value;

        };

        $scope.initAnalysisData = function (data) {
            if(data==undefined){
                return;
            }
            $scope.adata = data;
            //$("#Markup4").attr("disabled", false);
            $("#FreeGood4").attr("disabled", false);
            $("#FreeGood112").attr("disabled", false);

            $scope.proposalNewYear = data.contractLineList2019[0].proposalYear;//proposal制作年度

            $scope.ABOI1 = [];
            $scope.ABOI2 = [];
            $scope.BEOI1 = [];
            $scope.BEOI2 = [];
            $scope.SROI1 = [];
            $scope.SROI2 = [];

            $scope.Benchmark = "";
            $scope.datanum = data.contractLineList2019.length;
            var OIType="";
            //ABOI类型数据结果集的拆分，然后加载html
            for(var i=0;i<data.contractLineList2019.length;i++){
                //分类只存以下三个类型，分别存入当前年度和(当前年度+1)集合中,然后按照顺序ABOI,SROI,BEOI输出html页面
                if(data.contractLineList2019[i].oiType == "BEOI") {
                    $scope.BEOI1.push(data.contractLineList2018[i]);
                    $scope.BEOI2.push(data.contractLineList2019[i]);
                }else if(data.contractLineList2019[i].oiType == "SROI"){
                    $scope.SROI1.push(data.contractLineList2018[i]);
                    $scope.SROI2.push(data.contractLineList2019[i]);
                } else if(data.contractLineList2019[i].oiType == "ABOI"){
                    $scope.ABOI1.push(data.contractLineList2018[i]);
                    $scope.ABOI2.push(data.contractLineList2019[i]);
                }
            }

            if(isNaN(data.hterm2019.salesUpScale)){
                data.hterm2019.salesUpScale =0
            }
            if(isNaN(data.contractLineList2019[0].feeIntas)){
                data.contractLineList2019[0].feeIntas = 0;
            }

            //定位:anlysis_5 , anlysis_6
            //中间表格赋值
            $scope.header.versionCode = data.header2019.versionCode;
            $scope.header.orderNbr = data.header2019.orderNbr;
            $scope.header.SupplierCode = data.header2019.vendorNbr;
            $scope.header.SupplierName = data.header2019.vendorName;
            if(data.hterm2019.warehouseDesc==undefined){
                $scope.header.Region= data.hterm2019.salesArea;
            } else {
                $scope.header.Region= data.hterm2019.warehouseDesc;
            }

            //2019.11.28 hmb 修改
            var analasiLookupTypeClass = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_CLASS' && x.lookupCode == data.hterm2019.termsClass;
            });
            $scope.header.Class = analasiLookupTypeClass.length > 0 ? analasiLookupTypeClass[0].meaning : '';
            $scope.header.Buyer= data.hterm2019.buyerCode;
            $scope.header.OwnerDept= data.hterm2019.deptDesc;

            //定位:anlysis_20(这里是用名称判断,不符合规则,用deptCode判断,后续再改 2019.11.28 查)
            /*if(data.hterm2019.deptDesc=="Cosmetics"){
                $scope.Benchmark ="Benchmark Cosmetics NM 50.6%";
            }
            if(data.hterm2019.deptDesc=="Food and Sundries"){
                $scope.Benchmark ="Benchmark Food and Sundries NM 41.2%";
            }
            if(data.hterm2019.deptDesc=="General Merchandise"){
                $scope.Benchmark ="Benchmark General Merchandise NM 60.6%";
            }
            if(data.hterm2019.deptDesc=="Health and Fitness"){
                $scope.Benchmark ="Benchmark Health and Fitness NM 49.1%";
            }
            if(data.hterm2019.deptDesc=="Personal Care"){
                $scope.Benchmark ="Benchmark Personal Care NM 43.4%";
            }
            if(data.hterm2019.deptDesc=="Skincare"){
                $scope.Benchmark ="Benchmark Skincare NM 49.1%";
            }*/

            switch (data.hterm2019.deptCode) {
                case "1"://Health and Fitness
                    $scope.Benchmark ="Benchmark Health and Fitness NM 49.1%";
                    break;
                case "2"://Personal Care
                    $scope.Benchmark ="Benchmark Personal Care NM 43.4%";
                    break;
                case "3"://Skincare
                    $scope.Benchmark ="Benchmark Skincare NM 49.1%";
                    break;
                case "4"://Cosmetics
                    $scope.Benchmark ="Benchmark Cosmetics NM 50.6%";
                    break;
                case "5"://Food and Sundries
                    $scope.Benchmark ="Benchmark Food and Sundries NM 41.2%";
                    break;
                case "6"://General Merchandise
                    $scope.Benchmark ="Benchmark General Merchandise NM 60.6%";
                    break;
            }

            ////////2019.11.27 hmb  改成快码值取值方式
            var anlaysiLookupValue = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'SALE_WAY' && x.lookupCode == data.hterm2019.salesType;
            });

            $scope.header.TradingMode = anlaysiLookupValue.length > 0 ? anlaysiLookupValue[0].meaning : '';

            //定位: anlysis_5
            $scope.header.ContractVersion= data.hterm2019.agreementEdition;
            $scope.header.Brand= data.hterm2019.brandCn;
            $scope.header.Ne= data.header2019.newOrExisting;
            if(data.header2019.newOrExisting.indexOf("Exist")>=0){
                $scope.header.Ne= "E";//存在供应商(续签供应商)
            }else{
                $scope.header.Ne= "N";//新供应商
            }
            $scope.header.fp= (parseFloat(data.hterm2019.fcsPurchse)/1000).toFixed(0);
            $scope.header.Purchase= data.hterm2019.fcsPurchse;
            $scope.header.GP= data.hterm2019.fcsSales;
            $scope.header.remark= data.hterm2019.remark;

            //定位 anlysis_1
            $scope.analysisData.versionCode = data.analysisData.versionCode;
            $scope.analysisData.proposalid = data.analysisData.proposalid;
            $scope.analysisData.markup = data.analysisData.markup;
            $scope.analysisData.freegoods = data.analysisData.freegoods;
            $scope.analysisData.oldFreeGoods = data.analysisData.oldFreeGoods;//往年的freeGoods 举例：今年是2020年，往年的freeGoods是2019年的
            $scope.analysisData.aboiList = data.analysisData.aboiList;
            $scope.analysisData.oldAboiList = data.analysisData.oldAboiList;//往年的aboi类型的数据
            $scope.analysisData.bicRemark = data.analysisData.bicRemark;
            $scope.analysisData.purchaseRemark = data.analysisData.purchaseRemark;
            $scope.analysisData.batch = data.analysisData.batch;

            //hmb注释 等正式上线可放开此代码
            //往年Proposal相关的aboi信息
            //$scope.oldAnalysisData.freegoods = data.oldAnalysisData.freegoods;
            //$scope.oldAnalysisData.aboiList = data.oldAnalysisData.aboiList;

            if($scope.analysisData.purchaseRemark==undefined){
                document.getElementById("premark").innerHTML ="";
            }else{
                document.getElementById("premark").innerHTML =$scope.analysisData.purchaseRemark;
            }

            $scope.buser = data.buser;
            $scope.header.BEOI= "0.00%";
            $scope.header.SROI= "0.00%";
            $scope.header.ABOI= "0.00%";
            $scope.header.TotalOI= "0.00%";
            $scope.header.NM= "0.00%";

            //定位: anlysis_11  计算Markup%
            $scope.mid.Markup1 = parseFloat(data.hterm2018.salesUpScale).toFixed(2);
            $scope.mid.MarkupOnTop = "0.00";//On Top 列 第2列
            $scope.mid.MarkupTtaOnTop = (parseFloat($scope.mid.Markup1)).toFixed(2);//TTA + On Top 列 第3列
            $scope.mid.Markup2 = parseFloat(data.hterm2018.salesUpScale).toFixed(2);

            $scope.mid.Markup3 = parseFloat(data.hterm2019.salesUpScale).toFixed(2);
            $scope.mid.Markup4 = parseFloat(data.analysisData.markup).toFixed(2)+"%";
            $scope.mid.Markup5 = (parseFloat($scope.mid.Markup3)).toFixed(2);
            $scope.mid.Markup6 = "n/a";
            //hmb标注, //$scope.mid.Markup7 这一段代码等正式上线之后需要放开
            //2020.2.12修改
            $scope.mid.Markup7 = $scope.changeNumberToPosiNumber(($scope.mid.Markup3-$scope.mid.Markup1).toFixed(2));
            //$scope.mid.Markup7 = $scope.mid.Markup5 - $scope.mid.MarkupTtaOnTop;
            $scope.mid.Markup8 = "n/a";
            $scope.mid.Markup9 = $scope.changeNumberToPosiNumber(($scope.mid.Markup5-$scope.mid.Markup2).toFixed(2));




            //定位: anlysis_12  计算Free Goods$
            $scope.mid.FreeGood1 = parseFloat(data.freeGood2018.feeIntas).toFixed(0);
            //$scope.mid.FreeGood2 = parseFloat(data.freeGood2018.ttaValue).toFixed(0); 2019.12.5 hmb 注释,改为取费用预估
            //hmb注释 等正式上线可放开此代码
            //$scope.mid.FreeGood112 =isNaN(parseFloat(data.oldAnalysisData.freegoods))? 0:parseFloat(data.oldAnalysisData.freegoods).toFixed(0);//前端页面可编辑的数据
            //$scope.mid.FreeGood113 = (parseFloat($scope.mid.FreeGood1)+parseFloat(data.oldAnalysisData.freegoods)).toFixed(0);
            $scope.mid.FreeGood2 = parseFloat(data.freeGood2018.feeIntas).toFixed(0);//取值为proposal对应的proposal制作年度 - 1 的费用预估
            $scope.mid.FreeGood3=  parseFloat(data.freeGood2019.feeIntas).toFixed(0);
            $scope.mid.FreeGood4 = parseFloat(data.analysisData.freegoods).toFixed(0);//这个格子是前端页面可编辑的数据
            if(isNaN($scope.mid.FreeGood4)){
                $scope.mid.FreeGood4= 0;
            }
            $scope.mid.FreeGood5 = (parseFloat($scope.mid.FreeGood3)+parseFloat(isNaN(data.analysisData.freegoods) ? 0 : data.analysisData.freegoods)).toFixed(0);
            $scope.mid.FreeGood6 = $scope.changeNumberToPosiNumber(($scope.mid.FreeGood3 - $scope.mid.FreeGood1).toFixed(0));
            //$scope.mid.FreeGood7 = $scope.changeNumberToPosiNumber( isNaN(($scope.mid.FreeGood6/$scope.mid.FreeGood1).toFixed(0))?0:($scope.mid.FreeGood6/$scope.mid.FreeGood1).toFixed(0) );
            //$scope.mid.FreeGood6 = $scope.changeNumberToPosiNumber(($scope.mid.FreeGood5 - $scope.mid.FreeGood113).toFixed(0));
            $scope.mid.FreeGood7 = $scope.changeNumberToPosiNumber( isNaN(($scope.mid.FreeGood6/$scope.mid.FreeGood113).toFixed(0))?0:($scope.mid.FreeGood6/$scope.mid.FreeGood113).toFixed(0) );
            $scope.mid.FreeGood8 = $scope.changeNumberToPosiNumber(($scope.mid.FreeGood5 - $scope.mid.FreeGood2).toFixed(0));
            $scope.mid.FreeGood9 = $scope.changeNumberToPosiNumber( (($scope.mid.FreeGood8/$scope.mid.FreeGood2).toFixed(0))==Infinity?0:($scope.mid.FreeGood8/$scope.mid.FreeGood2).toFixed(0) );


            //定位:anlysis_13 计算Purchase$
            //对应的表格列 A 代表第一列
            $scope.mid.PurchaseA = isNaN(data.contractLineList2018[0].purchase) ? 0 : data.contractLineList2018[0].purchase;//1
            $scope.mid.PurchaseB = isNaN(data.purchase/1000) ? 0 : data.purchase/1000;//2
            $scope.mid.PurchaseC = isNaN(data.fpurchase/1000) ? 0 : data.fpurchase/1000;//3
            $scope.mid.PurchaseD = 0;//4
            $scope.mid.PurchaseE = $scope.mid.PurchaseC;//5
            $scope.mid.PurchaseF = $scope.mid.PurchaseC - $scope.mid.PurchaseA ;//6
            $scope.mid.PurchaseG = $scope.mid.PurchaseF / $scope.mid.PurchaseA;//7
            $scope.mid.PurchaseH = $scope.mid.PurchaseE - $scope.mid.PurchaseB;//8
            $scope.mid.PurchaseI = $scope.mid.PurchaseH / $scope.mid.PurchaseB;//9


            $scope.mid.Purchase1 = (data.contractLineList2018[0].purchase).toFixed(0);//1
            $scope.mid.Purchase112 = "-  ";//2
            $scope.mid.Purchase113 = $scope.mid.Purchase1;//3
            $scope.mid.Purchase2= (data.purchase/1000).toFixed(0);//4
            $scope.mid.Purchase3= (data.fpurchase/1000).toFixed(0);//5
            $scope.mid.Purchase4 = "-  ";//6
            $scope.mid.Purchase5 = $scope.mid.Purchase3;//7
            //hmb 2019.12.20注释
            //$scope.mid.Purchase6 = $scope.changeNumberToPosiNumber(($scope.mid.Purchase3-$scope.mid.Purchase1).toFixed(0));
            //$scope.mid.Purchase7 = $scope.changeNumberToPosiNumber( (($scope.mid.Purchase6*100/$scope.mid.Purchase1).toFixed(2))==Infinity?0:($scope.mid.Purchase6*100/$scope.mid.Purchase1).toFixed(2) );
            //$scope.mid.Purchase8 = $scope.changeNumberToPosiNumber( (($scope.mid.Purchase5-$scope.mid.Purchase2).toFixed(0))==Infinity?0:($scope.mid.Purchase5-$scope.mid.Purchase2).toFixed(0) );
            //$scope.mid.Purchase9 = $scope.changeNumberToPosiNumber( (($scope.mid.Purchase8*100/$scope.mid.Purchase2).toFixed(2))==Infinity?0:($scope.mid.Purchase8*100/$scope.mid.Purchase2).toFixed(2) );
            //$scope.mid.Purchase6 = $scope.changeNumberToPosiNumber(($scope.mid.PurchaseC - $scope.mid.PurchaseA).toFixed(0));//8
            //$scope.mid.Purchase7 = $scope.changeNumberToPosiNumber( (($scope.mid.PurchaseG * 100).toFixed(2))==Infinity?0:($scope.mid.PurchaseG * 100).toFixed(2) );//9
            $scope.mid.Purchase6 = $scope.changeNumberToPosiNumber(($scope.mid.PurchaseE - $scope.mid.PurchaseA).toFixed(0));//8
            $scope.mid.Purchase7 = $scope.changeNumberToPosiNumber( (($scope.mid.PurchaseG * 100).toFixed(2))==Infinity?0:($scope.mid.PurchaseG * 100).toFixed(2) );//9
            $scope.mid.Purchase8 = $scope.changeNumberToPosiNumber(  ($scope.mid.PurchaseH).toFixed(0) );//10
            $scope.mid.Purchase9 = $scope.changeNumberToPosiNumber( (($scope.mid.PurchaseI * 100).toFixed(2))==Infinity?0:($scope.mid.PurchaseI * 100).toFixed(2) );//11


            //定位:anlysis_14  计算Sales$
            //注释后面的数字代表的是表格的列
            $scope.mid.SalseA = isNaN(data.contractLineList2018[0].sales) ? 0 : data.contractLineList2018[0].sales;//1
            $scope.mid.SalseB = data.sales/1000;//2
            $scope.mid.SalseC = data.fsales/1000;//3
            $scope.mid.SalseD = 0;//4
            $scope.mid.SalseE = $scope.mid.SalseC;//5
            $scope.mid.SalseF = $scope.mid.SalseC - $scope.mid.SalseA;//6
            $scope.mid.SalseG = $scope.mid.SalseF / $scope.mid.SalseA;//7
            $scope.mid.SalseH = $scope.mid.SalseE - $scope.mid.SalseB;//8
            $scope.mid.SalseI = $scope.mid.SalseH / $scope.mid.SalseB;//9

            $scope.mid.Salse1 = (data.contractLineList2018[0].sales).toFixed(0);//1
            $scope.mid.Salse112 = "-  ";//2
            $scope.mid.Salse113 = $scope.mid.Salse1;//3
            $scope.mid.Salse2= (data.sales/1000).toFixed(0);//4
            $scope.mid.Salse3= (data.fsales/1000).toFixed(0);//5
            $scope.mid.Salse4 = "-  ";//6
            $scope.mid.Salse5 = $scope.mid.Salse3;//7
            //hmb 2019.12.20 注释
            //$scope.mid.Salse6 = $scope.changeNumberToPosiNumber( (parseFloat($scope.mid.Salse3)-parseFloat($scope.mid.Salse1)).toFixed(0) );
            //$scope.mid.Salse7 = $scope.changeNumberToPosiNumber( (($scope.mid.Salse6*100/$scope.mid.Salse1).toFixed(2))==Infinity?0:($scope.mid.Salse6*100/$scope.mid.Salse1).toFixed(2) );
            //$scope.mid.Salse8 = $scope.changeNumberToPosiNumber( ($scope.mid.Salse5-$scope.mid.Salse2).toFixed(0) );
            //$scope.mid.Salse9 = $scope.changeNumberToPosiNumber( (($scope.mid.Salse8*100/$scope.mid.Salse2).toFixed(2))==Infinity?0:($scope.mid.Salse8*100/$scope.mid.Salse2).toFixed(2) );

            $scope.mid.Salse6 = $scope.changeNumberToPosiNumber( (parseFloat($scope.mid.SalseC) - parseFloat($scope.mid.SalseA)).toFixed(0) );//8
            $scope.mid.Salse7 = $scope.changeNumberToPosiNumber( (($scope.mid.SalseG * 100).toFixed(2))==Infinity?0:($scope.mid.SalseG * 100).toFixed(2) );//9
            $scope.mid.Salse8 = $scope.changeNumberToPosiNumber( ($scope.mid.SalseH).toFixed(0) );//10
            $scope.mid.Salse9 = $scope.changeNumberToPosiNumber( (($scope.mid.SalseI * 100).toFixed(2))==Infinity?0:($scope.mid.SalseI * 100).toFixed(2) );//11

            //定位:add_new_Anlysis_14  Sales / Purchase% 注释后面的数字代表的是列数
            $scope.mid.SalsePurchaseA = ($scope.mid.SalseA / $scope.mid.PurchaseA) * 100;//1
            $scope.mid.SalsePurchaseB = 0;//2
            $scope.mid.SalsePurchaseC = ($scope.mid.SalseA / $scope.mid.PurchaseA) * 100;//3
            $scope.mid.SalsePurchaseD = ($scope.mid.SalseB / $scope.mid.PurchaseB) * 100;//4
            $scope.mid.SalsePurchaseE = ($scope.mid.SalseC / $scope.mid.PurchaseC) * 100;//5
            $scope.mid.SalsePurchaseF = 0;//6
            $scope.mid.SalsePurchaseG = ($scope.mid.SalseE / $scope.mid.PurchaseE) * 100;//7
            $scope.mid.SalsePurchaseH = "n/a";//8
            $scope.mid.SalsePurchaseI = $scope.mid.SalsePurchaseG - $scope.mid.SalsePurchaseC;//9
            $scope.mid.SalsePurchaseJ = "n/a";//10
            $scope.mid.SalsePurchaseK = $scope.mid.SalsePurchaseG - $scope.mid.SalsePurchaseD;//11

            $scope.mid.SalsePurchase1 = (($scope.mid.SalseA / $scope.mid.PurchaseA) * 100).toFixed(2);//1
            $scope.mid.SalsePurchase2 = "-  ";//2
            $scope.mid.SalsePurchase3 = (($scope.mid.SalseA / $scope.mid.PurchaseA) * 100).toFixed(2);//3
            $scope.mid.SalsePurchase4 = (($scope.mid.SalseB / $scope.mid.PurchaseB) * 100).toFixed(2);//4
            $scope.mid.SalsePurchase5 = (($scope.mid.SalseC / $scope.mid.PurchaseC) * 100).toFixed(2);//5
            $scope.mid.SalsePurchase6 = "-  ";//6
            $scope.mid.SalsePurchase7 =  (($scope.mid.SalseE / $scope.mid.PurchaseE) * 100).toFixed(2);//7
            $scope.mid.SalsePurchase8 = "n/a";
            $scope.mid.SalsePurchase9 = ($scope.mid.SalsePurchaseI).toFixed(2);
            $scope.mid.SalsePurchase10 = "n/a";
            $scope.mid.SalsePurchase11 = ($scope.mid.SalsePurchaseK).toFixed(2);

            //计算GP$ 和 GP%的值
            $scope.sumdata(data);

            /**
             *  统计total行数据
             *  例如行名称为:
             *  Total OI$,
             *  Total OI%(as of Purchase),
             *  Total OI%(as of Sales),
             *  Net Margin%(as of Sales)
             */
            sumTotal(data.contractLineList2018,data.contractLineList2019);

            //定位: anlysis_17 计算 Total OI$
            $scope.tail.OI1 = parseFloat($scope.TotalOiA).toFixed(0);//1
            $scope.tail.OI112 = parseFloat($scope.TotalOiAA).toFixed(0);//2
            $scope.tail.OI113 = parseFloat($scope.TotalOiAB).toFixed(0);//3
            $scope.tail.OI2 = parseFloat($scope.TotalOiB).toFixed(0);//4
            $scope.tail.OI3 = parseFloat($scope.TotalOiC).toFixed(0);//5
            $scope.tail.OI4 = parseFloat($scope.TotalOiD).toFixed(0);//6
            $scope.tail.OI5 = parseFloat($scope.TotalOiE).toFixed(0);//7
            //$scope.tail.OI6 = $scope.changeNumberToPosiNumber( (parseFloat($scope.TotalOiC) - parseFloat($scope.TotalOiA)).toFixed(0) );//8
            //$scope.tail.OI7 = $scope.changeNumberToPosiNumber( (parseFloat($scope.tail.OI6)*100/parseFloat($scope.TotalOiA)).toFixed(2))+"%";//9
            $scope.tail.OI6 = $scope.changeNumberToPosiNumber( (parseFloat($scope.TotalOiE) - parseFloat($scope.TotalOiAB)).toFixed(0) );//8
            $scope.tail.OI7 = $scope.changeNumberToPosiNumber( (parseFloat($scope.tail.OI6)*100/parseFloat($scope.TotalOiAB)).toFixed(2))+"%";//9
            $scope.tail.OI8 = $scope.changeNumberToPosiNumber( (parseFloat($scope.tail.OI5)-parseFloat($scope.TotalOiB)).toFixed(0) );//10
            $scope.tail.OI9 = $scope.changeNumberToPosiNumber( (parseFloat($scope.tail.OI8)*100/parseFloat($scope.TotalOiB)).toFixed(2))+"%";//11

            //定位: anlysis_18 计算 Total OI%(as of Purchase)
            $scope.tail.Purchase1 = $scope.TotalOiPurchaseA==0?"0.00%": $scope.TotalOiPurchaseA;//1
            $scope.tail.Purchase112 = $scope.TotalOiPurchaseAA;//2
            $scope.tail.Purchase113 = $scope.TotalOiPurchaseAB;//3
            $scope.tail.Purchase2 = $scope.TotalOiPurchaseB;//4
            $scope.tail.Purchase3 = $scope.TotalOiPurchaseC;//5
            $scope.tail.Purchase4 = $scope.TotalOiPurchaseD;//6
            $scope.tail.Purchase5 = $scope.TotalOiPurchaseE;//7
            $scope.tail.Purchase6 = "n/a";//8
            //2020.2.13 hmb注释
            //$scope.tail.Purchase7 = $scope.changeNumberToPosiNumber( (parseFloat($scope.TotalOiPurchaseC) - parseFloat($scope.TotalOiPurchaseA)).toFixed(2) )+"%";//9
            $scope.tail.Purchase7 = $scope.changeNumberToPosiNumber( (parseFloat($scope.TotalOiPurchaseE) - parseFloat($scope.TotalOiPurchaseAB)).toFixed(2) )+"%";//9
            $scope.tail.Purchase8 = "n/a";//10
            $scope.tail.Purchase9 = $scope.changeNumberToPosiNumber( (parseFloat($scope.TotalOiPurchaseE) - parseFloat($scope.TotalOiPurchaseB)).toFixed(2) )+"%";//11

            //定位: anlysis_19 计算 Total OI%(as of Sales)
            $scope.tail.Sales1 = $scope.TotalOiSalesA==0?"0.00%":$scope.TotalOiSalesA;//1
            $scope.tail.Sales112 = $scope.TotalOiSalesAA==0?"0.00%":$scope.TotalOiSalesAA;//2
            $scope.tail.Sales113 = $scope.TotalOiSalesAB==0?"0.00%":$scope.TotalOiSalesAB;//3
            $scope.tail.Sales2 = $scope.TotalOiSalesB==0?"0.00%":$scope.TotalOiSalesB;//4
            $scope.tail.Sales3 = $scope.TotalOiSalesC==0?"0.00%":$scope.TotalOiSalesC;//5
            $scope.tail.Sales4 = $scope.TotalOiSalesD==0?"0.00%":$scope.TotalOiSalesD;//6
            $scope.tail.Sales5 = $scope.TotalOiSalesE==0?"0.00%":$scope.TotalOiSalesE;//7
            $scope.tail.Sales6 = "n/a";//8
            //2020.2.13 hmb注释
            //$scope.tail.Sales7 = $scope.changeNumberToPosiNumber((parseFloat($scope.TotalOiSalesC) - parseFloat($scope.TotalOiSalesA)).toFixed(2))+"%";//9
            $scope.tail.Sales7 = $scope.changeNumberToPosiNumber((parseFloat($scope.TotalOiSalesE) - parseFloat($scope.TotalOiSalesAB)).toFixed(2))+"%";//9
            $scope.tail.Sales8 = "n/a";//10
            $scope.tail.Sales9 = $scope.changeNumberToPosiNumber((parseFloat($scope.TotalOiSalesE) - parseFloat($scope.TotalOiSalesB)).toFixed(2))+"%";//11

            //定位: anlysis_20 计算 Net Margin%(as of Sales)
            $scope.tail.netMargin1 = (parseFloat($scope.NetMarginA==0?"0.00":$scope.NetMarginA)).toFixed(2);//1
            //$scope.tail.netMargin112 = (parseFloat($scope.NetMarginAA==0?"0.00":$scope.NetMarginAA)).toFixed(2);//2
            $scope.tail.netMargin112 = (parseFloat($scope.TotalOiSalesAA + $scope.gper.gp1)).toFixed(2);//2
            $scope.tail.netMargin113 = (parseFloat($scope.NetMarginAB==0?"0.00":$scope.NetMarginAB)).toFixed(2);//3
            $scope.tail.netMargin2 = (parseFloat($scope.NetMarginB==0?"0.00":$scope.NetMarginB)).toFixed(2);//4
            $scope.tail.netMargin3 = (parseFloat($scope.NetMarginC==0?"0.00":$scope.NetMarginC)).toFixed(2);//5
            $scope.tail.netMargin4 = (parseFloat($scope.TotalOiSalesD) + parseFloat($scope.gper.gp4)).toFixed(2);//6
            $scope.tail.netMargin5 = (parseFloat($scope.NetMarginE==0?"0.00":$scope.NetMarginE)).toFixed(2);//7
            $scope.tail.netMargin6 = "n/a";//8
            //2020.2.13 hmb注释
            //$scope.tail.netMargin7 = $scope.changeNumberToPosiNumber((parseFloat($scope.NetMarginC) - parseFloat($scope.NetMarginA)).toFixed(2))+"%";//9
            $scope.tail.netMargin7 = $scope.changeNumberToPosiNumber((parseFloat($scope.NetMarginE) - parseFloat($scope.NetMarginAB)).toFixed(2))+"%";//9
            $scope.tail.netMargin8 = "n/a";//10
            $scope.tail.netMargin9 = $scope.changeNumberToPosiNumber((parseFloat($scope.NetMarginE) - parseFloat($scope.NetMarginB)).toFixed(2))+"%";//11

            //动态生成ABOI,SROI,BEOI html元素之前,先删除动态生成的html元素
            //hmb
            $(".analaysisOiType").each(function (index,element) {
                $(this).remove();
            });

            //生成ABOI,SROI,BEOI HTML元素操作
            $scope.loadData($scope.ABOI1,$scope.ABOI2);
            $scope.loadData($scope.SROI1,$scope.SROI2);
            $scope.loadData($scope.BEOI1,$scope.BEOI2);

            //生成HTML元素之后,绑定事件
            for(var m=0;m<10;m++){
                var num = $scope.ABOI1.length - m-1;
                var c = document.getElementById("AABOI"+num);
                if(c != null){
                    c.addEventListener("change",changeNum);
                }

                var baboi = document.getElementById("BABOI" + num);

                if (baboi != null) {
                    baboi.addEventListener("change",changeNumByOldYearOnTop);
                }

            }

            //hmb注释 等正式上线可放开此代码
            /*if(data.oldAnalysisData.aboiList != undefined){
                var obj = JSON.parse(data.oldAnalysisData.aboiList);
                var count = Object.keys(obj).length;
                for(var n= 0;n<count;n++){
                    var cid = document.getElementById("BABOI"+n).name;
                    var abval = eval('obj.'+cid);
                    if(abval ==undefined || abval == ""){
                        abval = 0;
                    }
                    document.getElementById("BABOI"+n).value = abval;
                }
            }*/

            //重新赋值初始化
            if(data.analysisData.aboiList != undefined){
                var obj = JSON.parse(data.analysisData.aboiList);
                var count = Object.keys(obj).length;
                for(var n= 0;n<count;n++){
                    var cid = document.getElementById("AABOI"+n).name;
                    var abval = eval('obj.'+cid);
                    if(abval ==undefined || abval == ""){
                        abval = 0;
                    }
                    document.getElementById("AABOI"+n).value = abval;
                }
            }

            //hmb注释 等正式上线可放开此代码
            /*$scope.ABOITail.ABOIAASum = 0;
            for (var aboiAA=0;aboiAA<10;aboiAA++) {
                var elementById = document.getElementById("BABOI" + aboiAA);
                if (elementById != null) {
                    $scope.ABOITail.ABOIAASum = (parseFloat($scope.ABOITail.ABOIAASum)+parseFloat(elementById.value)).toFixed(0);
                }
            }
            document.getElementById("oldAboiTailSum").innerHTML = $scope.ABOITail.ABOIAASum;
            document.getElementById("oldTotalSum").innerHTML = $scope.ABOITail.ABOIAASum;
            */

            //$scope.tail.OI112 = isNaN(parseFloat($scope.ABOITail.ABOIAASum))? 0 : parseFloat($scope.ABOITail.ABOIAASum);


            $scope.ABOITail.sum = 0;
            //初始化统计数据ABOI
            for(var j=0;j<10;j++){
                var aa = document.getElementById("AABOI"+j);
                if(aa != null){
                    $scope.ABOITail.sum = (parseFloat($scope.ABOITail.sum)+parseFloat(aa.value)).toFixed(0);
                }
            }
            document.getElementById("aboitailsum").innerHTML = $scope.ABOITail.sum;
            document.getElementById("totalsum").innerHTML = $scope.ABOITail.sum;


            $timeout(function () {
                $scope.setTtaContractLine("nicescrolAlnlysisMain");
            }, 200);
        };

        function changeNumByOldYearOnTop() {
            for(var k=0;k<10;k++){
                var oldYeaABOILineVal = document.getElementById("oldYeaABOILine" + k + 1); //对应前端页面的第一列(tta列)
                if(oldYeaABOILineVal==null){
                    return;
                }
                var oldYeaABOILineValTta = oldYeaABOILineVal.innerHTML;
                var numIndex = $scope.ABOI1.length - k-1;
                document.getElementById("BABOI" + numIndex).value = document.getElementById("BABOI"+ numIndex ).value.replace(/[^\d]/g,'');
                var baboiOnTopValue = document.getElementById("BABOI" + numIndex).value;
                if(oldYeaABOILineValTta.indexOf("- ")>=0){
                    oldYeaABOILineValTta = 0;
                }else{
                    oldYeaABOILineValTta= oldYeaABOILineValTta.replace(",","");
                }
                var ttaOnTopValue = (parseFloat(oldYeaABOILineValTta) + parseFloat(baboiOnTopValue)).toFixed(0);
                document.getElementById("oldYeaABOILine" + k + 2).innerHTML = ttaOnTopValue;

                $scope.ABOITail.ABOIAASum = 0;
                for (var aboiAA=0;aboiAA<10;aboiAA++) {
                    var elementById = document.getElementById("BABOI" + aboiAA);
                    if (elementById != null) {
                        $scope.ABOITail.ABOIAASum = (parseFloat($scope.ABOITail.ABOIAASum)+parseFloat(elementById.value)).toFixed(0);
                    }
                }
                document.getElementById("oldAboiTailSum").innerHTML = $scope.ABOITail.ABOIAASum;
                //$scope.tail.OI112 = isNaN(parseFloat($scope.ABOITail.ABOIAASum))? 0 : parseFloat($scope.ABOITail.ABOIAASum);
                document.getElementById("oldTotalSum").innerHTML = $scope.ABOITail.ABOIAASum;
            }
        }


        function changeNum(){
            for(var k=0;k<10;k++){
                var aa = document.getElementById("ABOILine"+k+1);
                if(aa==null){
                    return;
                }
                var a = document.getElementById("ABOILine"+k+1).innerHTML;

                var num = $scope.ABOI1.length - k-1;
                document.getElementById("AABOI"+num).value = document.getElementById("AABOI"+num).value.replace(/[^\d]/g,'');
                var c = document.getElementById("AABOI"+num).value;
                //var b = document.getElementById("ABOI"+k).innerHTML.split("value")[1];
                //var c = b.substring(2, b.length-2);
                if(a.indexOf("- ")>=0){
                    a=0;
                }else{
                    a= a.replace(",","");
                }
                var d = (parseFloat(a) + parseFloat(c)).toFixed(0);
                document.getElementById("ABOILine"+k+2).innerHTML = d;

                $scope.ABOITail.sum = 0;
                for(var j=0;j<10;j++){
                    var aa = document.getElementById("AABOI"+j);
                    if(aa != null){
                        $scope.ABOITail.sum = (parseFloat($scope.ABOITail.sum)+parseFloat(aa.value)).toFixed(0);
                    }
                }
                document.getElementById("aboitailsum").innerHTML = $scope.ABOITail.sum;
                document.getElementById("totalsum").innerHTML = $scope.ABOITail.sum;
            }
        }
        $scope.changefreeGoods = function () {
            var freeGood3 = document.getElementById("freeGood3").innerHTML;
            var freeGood4 = document.getElementById("FreeGood4").value;
            if(freeGood3.indexOf("-">=0)){
                if (freeGood4 == "") {
                    document.getElementById("freeGood5").innerHTML = 0;
                } else {
                    document.getElementById("freeGood5").innerHTML = (parseFloat(freeGood4)).toFixed(0);
                }

            }else{
                document.getElementById("freeGood5").innerHTML = (parseFloat(freeGood3.innerHTML.replace(",",""))+parseFloat(freeGood4)).toFixed(0);
            }


            //2020.1.17添加
            var gperGp3 = document.getElementById("gperGp3").innerHTML;//固定值
            var gperGp4 = document.getElementById("gperGp4");//变化值

            var midSales = $scope.mid.Salse3;
            var midValue = 0;
            if (midSales.indexOf("-") >= 0 || midSales == 0) {
                gperGp4.innerHTML = 0;
            } else {
                if (freeGood4 == "") {
                    gperGp4.innerHTML = "0.00%";
                } else {
                    midValue = (parseFloat(freeGood4) / parseFloat(midSales)*100).toFixed(2);
                    gperGp4.innerHTML = parseFloat(midValue) + "%";
                }
            }

            if (gperGp3.indexOf("0.00%")>=0) {
                document.getElementById("gperGp5").innerHTML = gperGp4;
            }else {
                document.getElementById("gperGp5").innerHTML = (parseFloat(gperGp3.replace("%","")) + parseFloat(midValue)).toFixed(2) + "%";
            }


        };

        $scope.changeOldfreeGoods = function(){
            var FreeGood1Value = parseFloat($scope.mid.FreeGood1);
            var FreeGood112Value = isNaN(parseFloat($scope.mid.FreeGood112))? 0: parseFloat($scope.mid.FreeGood112);
            $scope.mid.FreeGood113 = FreeGood1Value + FreeGood112Value;
        };

        $scope.changeMarkUp = function () {
            var markup3 = document.getElementById("markup3");
            var markup4 = document.getElementById("Markup4").value;
            document.getElementById("markup5").innerHTML = (parseFloat(markup3.innerHTML.replace("%",""))+parseFloat(markup4.replace("%",""))).toFixed(2)+"%";
        };
        //$scope.initData();
        $scope.remarkString = "";

        //查询单据信息
        $scope.searchAnalysis = function () {
            //////////////////////////初始化参数 start
            $scope.dataTable="";
            $scope.gpdor= 0;
            $scope.gpper= 0;//2018
            $scope.gpdor2= 0;//GP$
            $scope.ActualGp= 0;

            $scope.ABOI1 = [];
            $scope.ABOI2 = [];
            $scope.BEOI1 = [];
            $scope.BEOI2 = [];
            $scope.SROI1 = [];
            $scope.SROI2 = [];

            $scope.ABOI ={};
            $scope.BABOI ={};
            $scope.ABOITail ={};


            $scope.gper = {};

            $scope.one= 0;//一般购货折扣
            $scope.pre= 0;//提前付款
            $scope.can= 0;//残损
            $scope.jizhong= 0;//集中收货
            $scope.cz= 0;//促销折扣
            $scope.shang= 0;//商业发展
            $scope.xuan= 0;//宣传牌

            $scope.gp2= "";
            $scope.one2= 0;//一般购货折扣
            $scope.pre2= 0;//提前付款
            $scope.can2= 0;//残损
            $scope.jizhong2= 0;//集中收货
            $scope.cz2= 0;//促销折扣
            $scope.shang2= 0;//商业发展
            $scope.xuan2= 0;//宣传牌

            //费用预估含税
            $scope.onefi= 0;//一般购货折扣
            $scope.prefi= 0;//提前付款
            $scope.canfi= 0;//残损
            $scope.jizhongfi= 0;//集中收货
            $scope.czfi= 0;//促销折扣
            $scope.shangfi= 0;//商业发展
            $scope.xuanfi= 0;//宣传牌

            $scope.onesum= 0;//一般购货折扣
            $scope.presum= 0;//提前付款
            $scope.cansum= 0;//残损
            $scope.jizhongsum= 0;//集中收货
            $scope.czsum= 0;//促销折扣
            $scope.shangsum= 0;//商业发展
            $scope.xuansum= 0;//宣传牌

            mm = 0;
            interval = 0;
            ABOIlineNUM = 0;
            oldYearABOILineNUM = 0;

            $scope.TotalBeoiA = 0;
            $scope.TotalBeoiAA = 0;
            $scope.TotalBeoiAAB = 0;
            $scope.TotalBeoiB = 0;
            $scope.TotalBeoiC = 0;
            $scope.TotalBeoiD = 0;
            $scope.TotalBeoiE = 0;

            $scope.TotalSroiA = 0;
            $scope.TotalSroiAA = 0;
            $scope.TotalSroiAB = 0;
            $scope.TotalSroiB = 0;
            $scope.TotalSroiC = 0;
            $scope.TotalSroiD = 0;
            $scope.TotalSroiE = 0;

            $scope.TotalAboiA = 0;
            $scope.TotalAboiAA = 0;
            $scope.TotalAboiAB = 0;
            $scope.TotalAboiB = 0;
            $scope.TotalAboiC = 0;
            $scope.TotalAboiD = 0;
            $scope.TotalAboiE = 0;

            //ae=n+u+ac
            $scope.TotalOiA = 0;
            $scope.TotalOiAA = 0;
            $scope.TotalOiAB = 0;
            $scope.TotalOiB = 0;
            $scope.TotalOiC = 0;
            $scope.TotalOiD = 0;
            $scope.TotalOiE = 0;

            //af=ae/c
            $scope.TotalOiPurchaseA = 0;
            $scope.TotalOiPurchaseAA = 0;
            $scope.TotalOiPurchaseAB = 0;
            $scope.TotalOiPurchaseB = 0;
            $scope.TotalOiPurchaseC = 0;
            $scope.TotalOiPurchaseD = 0;
            $scope.TotalOiPurchaseE = 0;

            //ag=af*(1-f)
            $scope.TotalOiSalesA = 0;
            $scope.TotalOiSalesAA = 0;
            $scope.TotalOiSalesAB = 0;
            $scope.TotalOiSalesB = 0;
            $scope.TotalOiSalesC = 0;
            $scope.TotalOiSalesD = 0;
            $scope.TotalOiSalesE = 0;

            //ah=f+ag
            $scope.NetMarginA = 0;
            $scope.NetMarginAA = 0;
            $scope.NetMarginAB = 0;
            $scope.NetMarginB = 0;
            $scope.NetMarginC = 0;
            $scope.NetMarginD = 0;
            $scope.NetMarginE = 0;

            //////////////////////////初始化参数 end

            httpServer.post(URLAPI.contractHeaderfindTerm, {
                    //'params': JSON.stringify({itemId: 4})
                    'params': JSON.stringify({proposalId: $scope.id})
                },
                function (res) {
                    if(res.datasize == 0){
                        SIEJS.alert('当年Proposal数据为空', "error", "确定");
                        return;
                    }
                    if (res.status == 'E') {
                        SIEJS.alert(res.msg, "error", "确定");
                        return;
                    }
                    if(res.datasize == 9999){
                        SIEJS.alert('次年Proposalheader数据为空', "error", "确定");
                        return;
                    }
                    $scope.remarkString=res.remark;
                    //document.getElementById("remarkStr").innerHTML= "abc<br/>n";
                    $scope.remark = res.remark;
                    res = changeToNumber(res);
                    $scope.dataTable = res;
                    $scope.initAnalysisData(res);

                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
            interval = setInterval(test,500);
        };

        function test(){

            mm=mm+1;
            if(mm<60){
                var text = document.getElementById("ordernbr");
                if(text!=undefined&&text!=null){
                    toZero();
                    mm = 12;
                }
            }else{
                clearInterval(interval);
                interval =null;
            }

        }
        //$scope.searchAnalysis();

        //转换字符串为浮点数字
        function changeToNumber(data){
            for(var i=0;i<data.contractLineList2019.length;i++){
                data.contractLineList2019[i].ttaValue = (parseFloat(data.contractLineList2019[i].ttaValue)/1000);
                data.contractLineList2019[i].feeIntas = (parseFloat(data.contractLineList2019[i].feeIntas)/1000);
                data.contractLineList2019[i].feeNotax = (parseFloat(data.contractLineList2019[i].feeNotax)/1000);
                data.contractLineList2019[i].purchase = (parseFloat(data.contractLineList2019[i].purchase)/1000);
                data.contractLineList2019[i].sales = (parseFloat(data.contractLineList2019[i].sales)/1000);
            }
            for(var i=0;i<data.contractLineList2018.length;i++){
                data.contractLineList2018[i].ttaValue =( parseFloat(data.contractLineList2018[i].ttaValue)/1000);
                data.contractLineList2018[i].feeIntas = (parseFloat((data.contractLineList2018[i].feeIntas === undefined || data.contractLineList2018[i].feeIntas === "") ? 0 : data.contractLineList2018[i].feeIntas) /1000);
                data.contractLineList2018[i].feeNotax = (parseFloat((data.contractLineList2018[i].feeNotax === undefined || data.contractLineList2018[i].feeNotax === "") ? 0 : data.contractLineList2018[i].feeNotax)/1000);
                data.contractLineList2018[i].purchase = (parseFloat(data.contractLineList2018[i].purchase)/1000);
                data.contractLineList2018[i].sales = (parseFloat(data.contractLineList2018[i].sales)/1000);
            }
            data.hterm2019.fcsPurchse = parseFloat(data.hterm2019.fcsPurchse);
            data.hterm2019.fcsSales = parseFloat(data.hterm2019.fcsSales);
            data.hterm2019.salesUpScale = parseFloat(data.hterm2019.salesUpScale);

            data.hterm2018.fcsPurchse = parseFloat(data.hterm2018.fcsPurchse);
            data.hterm2018.fcsSales = parseFloat(data.hterm2018.fcsSales);
            data.hterm2018.salesUpScale = parseFloat(data.hterm2018.salesUpScale);
            return data;
        }


        function toZero(){

            var trs=document.getElementById("dataTable").getElementsByTagName("tr");
            for(var i = 0;i<trs.length;i++){
                var cells = trs[i].cells;
                //避开不用处理的行
                if(i == 4 || i>7){
                    for(var k = 0;k<cells.length;k++){
                        //供应商编号，跳开
                        if(i==4&&k==0){
                            continue;
                        }
                        if(cells[k].innerHTML=='NaN'||cells[k].innerHTML=='Infinity'||cells[k].innerHTML=='false'){
                            cells[k].innerHTML = 0;
                        }else{
                            var sta= isNumber(cells[k].innerHTML);
                            if(sta){
                                //cells[k].innerHTML= (parseFloat(cells[k].innerHTML)/1000).toFixed(2);
                                cells[k].innerHTML = $scope.formatnum(cells[k].innerHTML);
                            }
                        }
                        if(cells[k].innerHTML.indexOf("NaN%")>=0||cells[k].innerHTML.indexOf("Infinity%")>=0||cells[k].innerHTML=="0%"){
                            cells[k].innerHTML = "0.00%";
                        }
                        if(cells[k].innerHTML=="0"){
                            cells[k].innerHTML = "-  ";
                        }
                        if(cells[k].innerHTML.indexOf("-,")>=0){
                            cells[k].innerHTML = cells[k].innerHTML.replace("-,","-");
                        }
                        if(cells[k].innerHTML.indexOf("-0.00%")>=0){
                            cells[k].innerHTML = "0.00%";
                        }
                        if(cells[k].innerHTML.indexOf("0.00%%")>=0){
                            cells[k].innerHTML = "0.00%";
                        }
                        if(cells[k].innerHTML.indexOf("NaN.00%")>=0){
                            cells[k].innerHTML = "0.00%";
                        }
                        if(cells[k].innerHTML.indexOf("Infinity.00%")>=0){
                            cells[k].innerHTML = "0.00%";
                        }

                    }
                }

            }
        }

        function isNumber(val){

            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
            var regNegZ = /^(\\-|\\+)?\d+(\\.\\d+)?$/;
            var regNegA = /^\d+(?=\.{0,1}\d+$|$)/;
            var isReg = /^\+?[0-9][0-9]*$/;
            if(regPos.test(val) || regNeg.test(val) || regNegZ.test(val) ||  regNegA.test(val) || isReg.test(val)){
                return true;
            }else{
                return false;
            }

        }


        //格式化数字为字符串，12856114726.00  -》12,856,114,726.00
        $scope.formatnum = function (num) {
            num=num+"";
            var tail = "."+num.split(".")[1];//将字符串根据.号拆分
            if(tail == ".undefined"){
                tail ="";
            }
            num = num.split(".")[0];
            var result = [ ], counter = 0;
            num = (num || 0).toString().split('');//变成数组
            for (var i = num.length - 1; i >= 0; i--) {
                counter++;
                result.unshift(num[i]);
                if (!(counter % 3) && i != 0) {
                    if (num[i-1] != '+') {//判断前面一个是否是 + 号,如果是,那么就不加逗号
                        result.unshift(',');
                    }
                }
            }
            return result.join('')+tail;
        };


        //ttaTermAnalysis+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//////////////////////////// analysis 改造开始//////////////////////////////////////////////////////////////////////////

        $scope.initNewAnalysisData = function (data,synbol) {
            $scope.analysisDataList = [];
            $scope.refreshAnalysisData = [];
            $scope.refreshAnalysisData = data.analysisDataList;

            if(data == undefined){
                return;
            }

            $scope.adata = data;
            $scope.BEOI1 = [];
            $scope.BEOI2 = [];
            $scope.SROI1 = [];
            $scope.SROI2 = [];
            $scope.ABOI1 = [];
            $scope.ABOI2 = [];

            $scope.datanum = data.contractLineList2019.length;//条款的长度
            //ABOI类型数据结果集的拆分，然后加载html
            for(var i=0;i<data.contractLineList2019.length;i++){
                //分类只存以下三个类型，分别存入当前年度和(当前年度+1)集合中,然后按照顺序ABOI,SROI,BEOI输出html页面
                if(data.contractLineList2019[i].oiType == "BEOI") {
                    $scope.BEOI1.push(data.contractLineList2018[i]);
                    $scope.BEOI2.push(data.contractLineList2019[i]);
                }else if(data.contractLineList2019[i].oiType == "SROI"){
                    $scope.SROI1.push(data.contractLineList2018[i]);
                    $scope.SROI2.push(data.contractLineList2019[i]);
                } else if(data.contractLineList2019[i].oiType == "ABOI"){
                    $scope.ABOI1.push(data.contractLineList2018[i]);
                    $scope.ABOI2.push(data.contractLineList2019[i]);
                }
            }

            if(isNaN(data.hterm2019.salesUpScale)){
                data.hterm2019.salesUpScale =0
            }
            if (data.contractLineList2019.length == 0) {

            } else {
                if(isNaN(data.contractLineList2019[0].feeIntas)){
                    data.contractLineList2019[0].feeIntas = 0;
                }
            }

            var analasiLookupTypeClass = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'TERMS_CLASS' && x.lookupCode == data.hterm2019.termsClass;
            });

            $scope.Benchmark = "";
            switch (data.hterm2019.deptCode) {
                case "1"://Health and Fitness
                    $scope.Benchmark ="Benchmark Health and Fitness NM 49.1%";
                    break;
                case "2"://Personal Care
                    $scope.Benchmark ="Benchmark Personal Care NM 43.4%";
                    break;
                case "3"://Skincare
                    $scope.Benchmark ="Benchmark Skincare NM 49.1%";
                    break;
                case "4"://Cosmetics
                    $scope.Benchmark ="Benchmark Cosmetics NM 50.6%";
                    break;
                case "5"://Food and Sundries
                    $scope.Benchmark ="Benchmark Food and Sundries NM 41.2%";
                    break;
                case "6"://General Merchandise
                    $scope.Benchmark ="Benchmark General Merchandise NM 60.6%";
                    break;
            }

            var anlaysiLookupValue = $scope.lookupCodeParty.filter(function (x) {
                return x.lookupType == 'SALE_WAY' && x.lookupCode == data.hterm2019.salesType;
            });

            //=========================2020.2.27号开始
            ////////////////////////////////数据构造开始///////////////////////////////////////////////////////////////
            $scope.proposalNewYear = data.header2019.proposalYear;//proposal制作年度
            var analysiOrderNo = 0;//排序号
            var dataType = "head";//默认头部分,数据区分:head 头部分  line 行部分
            //对analysis数据进行构造并计算
            $scope.analysis = {};
            $scope.tempData = {};
            $scope.analysis.markup = {};
            $scope.analysis.freeGood = {};
            $scope.analysis.purchase = {};
            $scope.analysis.sales = {};
            $scope.analysis.salsePurchase = {};
            $scope.analysis.gp = {};
            $scope.analysis.gper = {};//GP百分比
            $scope.analysis.tailOI = {};
            $scope.analysis.tailPurchase = {};
            $scope.analysis.tailSales = {};
            $scope.analysis.tailNetMargin = {};
            $scope.remarkResult ={};
            $scope.remarkResult = data.remarkResult;

            $scope.analysis.header = {
                supplierCode:data.header2019.vendorNbr,//供应商编号
                supplierName:data.header2019.vendorName,//供应商名称
                region:data.hterm2019.warehouseDesc ? data.hterm2019.warehouseDesc : data.hterm2019.salesArea,//区域
                classs:analasiLookupTypeClass.length > 0 ? analasiLookupTypeClass[0].meaning : '',//类型
                buyer:data.buser,//采购人
                ownerDept:data.hterm2019.deptDesc,
                tradingMode:anlaysiLookupValue.length > 0 ? anlaysiLookupValue[0].meaning : '',
                contractVersion:data.hterm2019.agreementEdition,
                brand:data.hterm2019.brandCn,
                newOrExisting:data.header2019.newOrExisting.indexOf("Exist") >= 0 ? "E" : "N",
                forecastPurchase:(parseFloat(data.hterm2019.fcsPurchse) / 1000).toFixed(0),
                purchase: 0.00,//默认0.00, 取Purchase$行	TTA+On Top vs  TTA+On Top列的格子值
                gp:0.00,//默认0.00 取GP%(as of Forecast/Actual sales)行 TTA+On Top vs  TTA+On列的格子值
                beoi:0.00,
                sroi:0.00,
                aboi:0.00,
                totaloi:0.00,
                nm:0.00,//默认0.00 Net Margin%(as of Forecast/Actual sales)行 TTA+On Top vs  TTA+On Top 列的格子值
                batchNumber:data.remarkResult.batch,//批次号 第一次加载的时候,动态生成批次,规则:proposal单据号 + proposal版本号
                proposalYear:data.header2019.proposalYear,
                proposalId:data.header2019.proposalId,
                versionCode:data.header2019.versionCode,//默认版本为1
                purchaseRemark: data.analysisData.length != 0 ? data.analysisData[0].purchaseRemark : data.remarkResult.purchaseRemark,
                bicRemark: data.analysisData.length != 0 ? data.analysisData[0].bicRemark : data.remarkResult.bicRemark,
                benchMark:$scope.Benchmark
            };
            //添加头部分
            $scope.analysis.header.oiType = "header";
            $scope.analysis.header.dataType = dataType;
            $scope.analysis.header.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.header.orderNo = analysiOrderNo;
            $scope.analysisDataList.push($scope.analysis.header);

            //行部分 等所有行执行完之后,再统一复制头部分的数据到行数据中
            //Markup%
            dataType = "line";
            analysiOrderNo++;
            $scope.analysis.markup.oiType = "markup";
            $scope.analysis.markup.dataType = dataType;
            $scope.analysis.markup.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.markup.orderNo= analysiOrderNo ;
            $scope.analysis.markup.termsComparison= "Markup%" ;
            //对应数据列开始,即TTA列开始,字母对应的excel模板中的列
            $scope.analysis.markup.formerYearTta = parseFloat(data.hterm2018.salesUpScale).toFixed(2);//1 A
            $scope.analysis.markup.formerYearOntop = 0.00;//2
            $scope.analysis.markup.formerYearTtaOntop = (parseFloat($scope.analysis.markup.formerYearTta)).toFixed(2);//3
            $scope.analysis.markup.formerYearActual = parseFloat(data.hterm2018.salesUpScale).toFixed(2);//4 B
            $scope.analysis.markup.currentYearTta = parseFloat(data.hterm2019.salesUpScale).toFixed(2);//5 C
            $scope.analysis.markup.currentYearOntop =0.00;//6 D
            $scope.analysis.markup.currentYearTtaOntop = (parseFloat($scope.analysis.markup.currentYearTta)).toFixed(2);//7 E
            $scope.analysis.markup.ttaOntopIncreAmt = 0.00;//8 F  前端显示为 n/a 字符,数据库存储为0.00,最后需要对格子做特殊处理变为0.00
            $scope.analysis.markup.ttaOntopPercentGain = $scope.analysis.markup.currentYearTtaOntop - $scope.analysis.markup.formerYearTtaOntop;//9 G 9 = 7 - 3
            $scope.analysis.markup.ttaOntopActIncreAmt = 0.00;//10 H  n/a
            $scope.analysis.markup.ttaOntopActPercentGain = ($scope.analysis.markup.currentYearTtaOntop - $scope.analysis.markup.formerYearActual).toFixed(2);//11 I
            $scope.analysisDataList.push($scope.analysis.markup);

            //Free Goods$
            analysiOrderNo++;
            $scope.analysis.freeGood.oiType = "freeGood";
            $scope.analysis.freeGood.dataType = dataType;
            $scope.analysis.freeGood.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.freeGood.orderNo= analysiOrderNo;
            $scope.analysis.freeGood.termsComparison = "Free Goods$";
            $scope.analysis.freeGood.formerYearTta = parseFloat(data.freeGood2018.feeIntas).toFixed(0);//1 A
            $scope.analysis.freeGood.formerYearOntop = data.oldAnalysisData.freegood ? data.oldAnalysisData.freegood : 0;//2 前端输入框,第一次拉取数据,因为没有输入的值,所以默认值为0
            $scope.analysis.freeGood.formerYearTtaOntop = (parseFloat($scope.analysis.freeGood.formerYearTta) + parseFloat($scope.analysis.freeGood.formerYearOntop)).toFixed(0);//3
            $scope.analysis.freeGood.formerYearActual = parseFloat(data.freeGood2018.feeIntas).toFixed(0);//4 B取值为proposal对应的proposal制作年度 - 1 的费用预估;
            $scope.analysis.freeGood.currentYearTta = parseFloat(data.freeGood2019.feeIntas).toFixed(0);//5 C
            $scope.analysis.freeGood.currentYearOntop = data.analysisData.length != 0 ? data.analysisData[0].freegoods : 0 ;//6 D 需要修改,这个格子是前端页面可编辑的数据 默认为0
            $scope.analysis.freeGood.currentYearTtaOntop = (parseFloat($scope.analysis.freeGood.currentYearTta) + parseFloat($scope.analysis.freeGood.currentYearOntop)).toFixed(0);//7 E
            $scope.analysis.freeGood.ttaOntopIncreAmt = ($scope.analysis.freeGood.currentYearTtaOntop - $scope.analysis.freeGood.formerYearTtaOntop).toFixed(0);//8 F 8 = 7-3
            $scope.analysis.freeGood.ttaOntopPercentGain = $scope.analysis.freeGood.formerYearTtaOntop == 0 ? 0.00 : ($scope.analysis.freeGood.ttaOntopIncreAmt * 100 / $scope.analysis.freeGood.formerYearTtaOntop).toFixed(2);  // 9 G 9 = 8 / 3
            $scope.analysis.freeGood.ttaOntopActIncreAmt = ($scope.analysis.freeGood.currentYearTtaOntop - $scope.analysis.freeGood.formerYearActual).toFixed(0);//10 H  10 = 7 -4
            $scope.analysis.freeGood.ttaOntopActPercentGain = $scope.analysis.freeGood.formerYearActual == 0 ? 0.00 : ($scope.analysis.freeGood.ttaOntopActIncreAmt * 100 / $scope.analysis.freeGood.formerYearActual).toFixed(2);//11 I  11 = 10 / 4
            $scope.analysisDataList.push($scope.analysis.freeGood);

            //Purchase$
            $scope.mid.PurchaseA = data.contractLineList2018.length == 0 ? 0 : (isNaN(data.contractLineList2018[0].purchase) ? 0 : data.contractLineList2018[0].purchase);//1 tta
            $scope.mid.PurchaseB = 0;//2 on top
            $scope.mid.PurchaseC = $scope.mid.PurchaseA + $scope.mid.PurchaseB;//3 tta + on top
            $scope.mid.PurchaseD = isNaN(data.purchase/1000) ? 0 : data.purchase/1000;//4 actual
            $scope.mid.PurchaseE = isNaN(data.fpurchase/1000) ? 0 : data.fpurchase/1000; //5 tta
            $scope.mid.PurchaseF = 0;//6 on top
            $scope.mid.PurchaseG = $scope.mid.PurchaseE + $scope.mid.PurchaseF;//7 tta + on top
            $scope.mid.PurchaseH = $scope.mid.PurchaseG - $scope.mid.PurchaseC;//8 = 7 - 3
            $scope.mid.PurchaseI = $scope.mid.PurchaseH * 100 / $scope.mid.PurchaseC;//9 = 8 / 3
            $scope.mid.PurchaseJ = $scope.mid.PurchaseG - $scope.mid.PurchaseD;//10 = 7 - 4
            $scope.mid.PurchaseK = $scope.mid.PurchaseJ * 100 / $scope.mid.PurchaseD;//11 = 10 / 4

            analysiOrderNo++;
            $scope.analysis.purchase.oiType = "purchase";
            $scope.analysis.purchase.dataType = dataType;
            $scope.analysis.purchase.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.purchase.orderNo= analysiOrderNo;
            $scope.analysis.purchase.termsComparison = "Purchase$";
            $scope.analysis.purchase.formerYearTta = data.contractLineList2018.length == 0 ? 0 : isNaN(data.contractLineList2018[0].purchase) ? 0 : (data.contractLineList2018[0].purchase).toFixed(0);//1
            $scope.analysis.purchase.formerYearOntop = 0;//2 "-  " 这是前端要显示的值,数据库存0
            $scope.analysis.purchase.formerYearTtaOntop = parseFloat($scope.analysis.purchase.formerYearTta) + parseFloat($scope.analysis.purchase.formerYearOntop);//3  3=1+2
            $scope.analysis.purchase.formerYearActual = isNaN(data.purchase/1000) ? 0 : parseFloat(data.purchase/1000).toFixed(0);//4
            $scope.analysis.purchase.currentYearTta = isNaN(data.fpurchase/1000) ? 0 : parseFloat(data.fpurchase/1000).toFixed(0);//5
            $scope.analysis.purchase.currentYearOntop = 0;//6 "-  " 这是前端要显示的值,数据库存0
            $scope.analysis.purchase.currentYearTtaOntop = parseFloat($scope.analysis.purchase.currentYearTta) + parseFloat($scope.analysis.purchase.currentYearOntop);//7 7 = 5 + 6
            $scope.analysis.purchase.ttaOntopIncreAmt = (parseFloat($scope.analysis.purchase.currentYearTtaOntop) - parseFloat($scope.analysis.purchase.formerYearTtaOntop)).toFixed(0);//8 8 = 7 - 3
            $scope.analysis.purchase.ttaOntopPercentGain = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0 : ($scope.analysis.purchase.ttaOntopIncreAmt * 100 / $scope.analysis.purchase.formerYearTtaOntop).toFixed(2) ;//9 9 = 8 / 3
            $scope.analysis.purchase.ttaOntopActIncreAmt = ($scope.analysis.purchase.currentYearTtaOntop - $scope.analysis.purchase.formerYearActual).toFixed(0);//10 10 = 7 - 4
            $scope.analysis.purchase.ttaOntopActPercentGain = $scope.analysis.purchase.formerYearActual == 0 ? 0 : ($scope.analysis.purchase.ttaOntopActIncreAmt * 100 / $scope.analysis.purchase.formerYearActual).toFixed(2);//11 11 = 10 / 4
            $scope.analysisDataList.push($scope.analysis.purchase);

            //Sales$
            $scope.mid.SalseA = data.contractLineList2018.length == 0 ? 0 : isNaN(data.contractLineList2018[0].sales) ? 0 : data.contractLineList2018[0].sales;//1 tta
            $scope.mid.SalseB = 0;//"-  " 2 on top
            $scope.mid.SalseC = $scope.mid.SalseA + $scope.mid.SalseB;//3 tta + on top  3 = 1 + 2
            $scope.mid.SalseD = data.sales/1000;//4 actual
            $scope.mid.SalseE = data.fsales/1000;//5 tta
            $scope.mid.SalseF = 0;//"-  " 6 on top
            $scope.mid.SalseG = $scope.mid.SalseE + $scope.mid.SalseF;//7 tta + on top 7 = 5 + 6
            $scope.mid.SalseH = $scope.mid.SalseG - $scope.mid.SalseC;//8   8 = 7 - 3
            $scope.mid.SalseI = $scope.mid.SalseH * 100 / $scope.mid.SalseC;//9  9 = 8 / 3
            $scope.mid.SalseJ = $scope.mid.SalseG - $scope.mid.SalseD;//10 10 = 7 - 4
            $scope.mid.SalseK = $scope.mid.SalseJ * 100 / $scope.mid.SalseD;//11 11 = 10 / 4

            analysiOrderNo++;
            $scope.analysis.sales.oiType = "salse";
            $scope.analysis.sales.dataType = dataType;
            $scope.analysis.sales.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.sales.orderNo= analysiOrderNo;
            $scope.analysis.sales.termsComparison = "Sales$";
            $scope.analysis.sales.formerYearTta = data.contractLineList2018.length == 0 ? 0 : isNaN(data.contractLineList2018[0].sales) ? 0 :(data.contractLineList2018[0].sales).toFixed(0);//1 tta
            $scope.analysis.sales.formerYearOntop = 0;//2 on top
            $scope.analysis.sales.formerYearTtaOntop = parseFloat($scope.analysis.sales.formerYearTta) + parseFloat($scope.analysis.sales.formerYearOntop);//3 tta + on top  3 = 1 + 2
            $scope.analysis.sales.formerYearActual = isNaN(data.sales/1000) ? 0 : (parseFloat(data.sales/1000)).toFixed(0);//4 actual
            $scope.analysis.sales.currentYearTta = isNaN(data.fsales/1000) ? 0 : (parseFloat(data.fsales/1000)).toFixed(0);//5 tta
            $scope.analysis.sales.currentYearOntop = 0;//6 on top
            $scope.analysis.sales.currentYearTtaOntop = parseFloat($scope.analysis.sales.currentYearTta) + parseFloat($scope.analysis.sales.currentYearOntop);//7 tta + on top 7 = 5 + 6
            $scope.analysis.sales.ttaOntopIncreAmt = parseFloat($scope.analysis.sales.currentYearTtaOntop) - parseFloat($scope.analysis.sales.formerYearTtaOntop);//8   8 = 7 - 3
            $scope.analysis.sales.ttaOntopPercentGain = $scope.analysis.sales.formerYearTtaOntop == 0 ? 0 : ($scope.analysis.sales.ttaOntopIncreAmt * 100 / $scope.analysis.sales.formerYearTtaOntop).toFixed(2) ;//9 9 = 8 / 3
            $scope.analysis.sales.ttaOntopActIncreAmt = $scope.analysis.sales.currentYearTtaOntop - $scope.analysis.sales.formerYearActual;//10  10 = 7 - 4
            $scope.analysis.sales.ttaOntopActPercentGain = $scope.analysis.sales.formerYearActual == 0 ? 0 : ($scope.analysis.sales.ttaOntopActIncreAmt * 100 / $scope.analysis.sales.formerYearActual).toFixed(2);//11  11 = 10 / 4
            $scope.analysisDataList.push($scope.analysis.sales);

            //  Sales / Purchase %
            $scope.mid.SalsePurchaseA = ($scope.mid.SalseA / $scope.mid.PurchaseA) * 100;//1
            $scope.mid.SalsePurchaseB = 0;//2
            $scope.mid.SalsePurchaseC = ($scope.mid.SalseC / $scope.mid.PurchaseC) * 100;//3
            $scope.mid.SalsePurchaseD = ($scope.mid.SalseD / $scope.mid.PurchaseD) * 100;//4
            $scope.mid.SalsePurchaseE = ($scope.mid.SalseE / $scope.mid.PurchaseE) * 100;//5
            $scope.mid.SalsePurchaseF = 0;//6
            $scope.mid.SalsePurchaseG = ($scope.mid.SalseG / $scope.mid.PurchaseG) * 100;//7
            $scope.mid.SalsePurchaseH = 0;// n/a 8
            $scope.mid.SalsePurchaseI = $scope.mid.SalsePurchaseG - $scope.mid.SalsePurchaseC;//9
            $scope.mid.SalsePurchaseJ = 0;// n/a 10
            $scope.mid.SalsePurchaseK = $scope.mid.SalsePurchaseG - $scope.mid.SalsePurchaseD;//11

            analysiOrderNo++;
            $scope.analysis.salsePurchase.oiType = "salsePurchase";
            $scope.analysis.salsePurchase.dataType = dataType;
            $scope.analysis.salsePurchase.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.salsePurchase.orderNo= analysiOrderNo;
            $scope.analysis.salsePurchase.termsComparison = "Sales / Purchase %";
            $scope.analysis.salsePurchase.formerYearTta = $scope.analysis.purchase.formerYearTta == 0 ? 0 : ($scope.analysis.sales.formerYearTta * 100 / $scope.analysis.purchase.formerYearTta).toFixed(2);//1
            $scope.analysis.salsePurchase.formerYearOntop = 0;// "-  " 2
            $scope.analysis.salsePurchase.formerYearTtaOntop = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0 : ($scope.analysis.sales.formerYearTtaOntop * 100 / $scope.analysis.purchase.formerYearTtaOntop).toFixed(2);//3
            $scope.analysis.salsePurchase.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0 : ($scope.analysis.sales.formerYearActual * 100 / $scope.analysis.purchase.formerYearActual).toFixed(2);//4
            $scope.analysis.salsePurchase.currentYearTta = $scope.analysis.purchase.currentYearTta == 0 ? 0 : ($scope.analysis.sales.currentYearTta * 100 / $scope.analysis.purchase.currentYearTta).toFixed(2);//5
            $scope.analysis.salsePurchase.currentYearOntop = 0;// "-  "  6
            $scope.analysis.salsePurchase.currentYearTtaOntop = $scope.analysis.purchase.currentYearTtaOntop == 0 ? 0 : ($scope.analysis.sales.currentYearTtaOntop * 100 / $scope.analysis.purchase.currentYearTtaOntop).toFixed(2);//7
            $scope.analysis.salsePurchase.ttaOntopIncreAmt = 0;//"n/a" 8
            $scope.analysis.salsePurchase.ttaOntopPercentGain = (parseFloat($scope.analysis.salsePurchase.currentYearTtaOntop) - parseFloat($scope.analysis.salsePurchase.formerYearTtaOntop)).toFixed(2);//9  9 = 7 - 3
            $scope.analysis.salsePurchase.ttaOntopActIncreAmt = 0; // "n/a" 10
            $scope.analysis.salsePurchase.ttaOntopActPercentGain = (parseFloat($scope.analysis.salsePurchase.currentYearTtaOntop) - parseFloat($scope.analysis.salsePurchase.formerYearActual)).toFixed(2);//11  11 = 7 - 4;
            $scope.analysisDataList.push($scope.analysis.salsePurchase);

            //计算GP% 的值
            //>>>>>>>>>>>>>>>>>>>即计算BEOI类型的条款值开始<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            //计算>>>>>往年的BEOI类型的条款值和条款值的累加
            $scope.oldProposalYearPurchase = $scope.beoiPurchaseA = $scope.dataTable.contractLineList2018.length == 0 ? 0 : isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
            $scope.beoiPurchaseD =$scope.adata.purchase / 1000;
            for (var i = 0; i < data.contractLineList2018.length; i++) {
                var termRowData = data.contractLineList2018[i];
                if (termRowData.oiType.indexOf("BEOI") >= 0) {
                    if (termRowData.termsCn.indexOf("一般购货折扣") >= 0) {
                        $scope.one = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (termRowData.feeIntas * 100 / $scope.oldProposalYearPurchase).toFixed(2)));

                        $scope.TotalBeoiA = parseFloat($scope.TotalBeoiA) + (parseFloat(isNaN(termRowData.feeIntas) ? 0 : termRowData.feeIntas));
                        $scope.TotalBeoiD = parseFloat($scope.TotalBeoiD) + (parseFloat(termRowData.sumMoney));
                    } else if (termRowData.termsCn.indexOf("提前付款") >= 0 && termRowData.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.pre = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (termRowData.feeIntas * 100 / $scope.oldProposalYearPurchase).toFixed(2)));

                        $scope.TotalBeoiA = parseFloat($scope.TotalBeoiA) + (parseFloat(isNaN(termRowData.feeIntas) ? 0 : termRowData.feeIntas));
                        $scope.TotalBeoiD = parseFloat($scope.TotalBeoiD) + (parseFloat(termRowData.sumMoney));
                    } else if (termRowData.termsCn.indexOf("残损") >= 0 && termRowData.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.can = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (termRowData.feeIntas * 100 / $scope.oldProposalYearPurchase).toFixed(2)));

                        $scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + (parseFloat(isNaN(termRowData.feeIntas) ? 0 : termRowData.feeIntas));
                        $scope.TotalBeoiD =parseFloat($scope.TotalBeoiD) + (parseFloat(termRowData.sumMoney));
                    } else if ((termRowData.termsCn.indexOf("集中收货") >= 0 || termRowData.termsCn.indexOf("集中收退货") >= 0) && termRowData.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.jizhong = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (termRowData.feeIntas * 100 / $scope.oldProposalYearPurchase).toFixed(2)));

                        $scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + (parseFloat(isNaN(termRowData.feeIntas) ? 0 : termRowData.feeIntas));
                        $scope.TotalBeoiD =parseFloat($scope.TotalBeoiD) + (parseFloat(termRowData.sumMoney));
                    } else if (termRowData.termsCn.indexOf("促销折扣") >= 0 )  {
                        $scope.cz = parseFloat(($scope.oldProposalYearPurchase == 0 ? 0 : (termRowData.feeIntas * 100 / $scope.oldProposalYearPurchase).toFixed(2)));

                        $scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + (parseFloat(isNaN(termRowData.feeIntas) ? 0 : termRowData.feeIntas));
                        $scope.TotalBeoiD =parseFloat($scope.TotalBeoiD) + (parseFloat(termRowData.sumMoney));
                    }
                }
            }
            //$scope.TotalBeoiA =parseFloat($scope.TotalBeoiA) + ($scope.beoiPurchaseA == 0 ? 0:parseFloat(isNaN(termRowData[i].feeIntas)?0:termRowData[i].feeIntas)  / $scope.beoiPurchaseA );
            //$scope.TotalBeoiD =parseFloat($scope.TotalBeoiD) + ($scope.beoiPurchaseD == 0 ? 0 :parseFloat(termRowData[i].sumMoney) / $scope.beoiPurchaseD);
            //$scope.TotalBeoiA = (parseFloat($scope.TotalBeoiA) * $scope.beoiPurchaseA).toFixed(2);
            //$scope.TotalBeoiAA = $scope.TotalBeoiA;
            //$scope.TotalBeoiAAB = 0;
            //$scope.TotalBeoiB = (parseFloat($scope.TotalBeoiB) * $scope.beoiPurchaseB).toFixed(2);
            $scope.TotalBeoiA = (parseFloat($scope.TotalBeoiA)).toFixed(0); //tta 1
            $scope.TotalBeoiB = 0;//on top 2
            $scope.TotalBeoiC = $scope.TotalBeoiA;//tta + on top 3
            $scope.TotalBeoiD = (parseFloat($scope.TotalBeoiD)).toFixed(0);//actual 4

            //如果是拆分的,取拆后的TTA TERMS上的合同版本
            if (data.hterm2018.isSplit && data.hterm2018.isSplit == "1") {//拆分TTA TERMS
                if (data.hterm2018.agreementEdition && data.hterm2018.agreementEdition == "B") {
                    $scope.TotalBeoiA = 0;
                    $scope.TotalBeoiC = 0;
                }
            } else {
                if($scope.analysis.header.contractVersion == "B"){//合同版本为B
                    $scope.TotalBeoiA = 0;
                    $scope.TotalBeoiC = 0;
                }
            }

            //计算>>>>>当年的BEOI类型的条款值和条款值的累加
            $scope.currentProposalYearPurchase = $scope.beoiPurchaseE = $scope.adata.fpurchase / 1000;
            for (var j = 0; j < data.contractLineList2019.length; j++) {
                var datum = data.contractLineList2019[j];
                if (datum.oiType.indexOf("BEOI") >= 0) {
                    if (datum.termsCn.indexOf("一般购货折扣") >= 0) {
                        $scope.one2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas * 100 / $scope.currentProposalYearPurchase).toFixed(2)));
                        $scope.TotalBeoiE = parseFloat($scope.TotalBeoiE) + (parseFloat(isNaN(datum.feeIntas)?0:datum.feeIntas));
                    } else if (datum.termsCn.indexOf("提前付款") >= 0 && datum.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.pre2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas * 100 / $scope.currentProposalYearPurchase).toFixed(2)));
                        $scope.TotalBeoiE = parseFloat($scope.TotalBeoiE)+ (parseFloat(isNaN(datum.feeIntas)?0:datum.feeIntas));
                    } else if (datum.termsCn.indexOf("残损") >= 0 && datum.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.can2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas * 100 / $scope.currentProposalYearPurchase).toFixed(2)));
                        $scope.TotalBeoiE = parseFloat($scope.TotalBeoiE)+ (parseFloat(isNaN(datum.feeIntas)?0:datum.feeIntas));
                    } else if ((datum.termsCn.indexOf("集中收货") >= 0 || datum.termsCn.indexOf("集中收退货") >= 0) && datum.termsCn.indexOf("购货折扣") >= 0) {
                        $scope.jizhong2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas * 100 / $scope.currentProposalYearPurchase).toFixed(2)));
                        $scope.TotalBeoiE = parseFloat($scope.TotalBeoiE)+ (parseFloat(isNaN(datum.feeIntas)?0:datum.feeIntas));
                    } else if (datum.termsCn.indexOf("促销折扣") >= 0) {
                        $scope.cz2 = parseFloat(($scope.currentProposalYearPurchase == 0 ? 0 : (datum.feeIntas * 100 / $scope.currentProposalYearPurchase).toFixed(2)));
                        $scope.TotalBeoiE = parseFloat($scope.TotalBeoiE)+ (parseFloat(isNaN(datum.feeIntas)?0:datum.feeIntas));
                    }
                }
            }
            //$scope.TotalBeoiE = (parseFloat($scope.TotalBeoiE) * parseFloat($scope.beoiPurchaseC)).toFixed(2);//tta
            $scope.TotalBeoiE = (parseFloat($scope.TotalBeoiE)).toFixed(2);//tta 5

            if($scope.analysis.header.contractVersion == "B"){
                $scope.TotalBeoiE = 0;// 2020 tta
            }
            $scope.TotalBeoiF = 0;// "-" 2020 on top 6
            $scope.TotalBeoiG = parseFloat($scope.TotalBeoiE);// tta + on top 7
            $scope.TotalBeoiH = parseFloat($scope.TotalBeoiG) - parseFloat($scope.TotalBeoiC);//8
            $scope.TotalBeoiI = $scope.TotalBeoiC == 0 ? 0 : (parseFloat($scope.TotalBeoiH) * 100/ parseFloat($scope.TotalBeoiC)).toFixed(2);//9
            $scope.TotalBeoiJ = parseFloat($scope.TotalBeoiG) - parseFloat($scope.TotalBeoiD);//10
            $scope.TotalBeoiK = $scope.TotalBeoiD == 0 ? 0 : (parseFloat($scope.TotalBeoiJ) * 100/ parseFloat($scope.TotalBeoiD)).toFixed(2);//11
            //>>>>>>>>>>>>>>>>>>>即计算BEOI类型的条款值结束<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

            //判断是新供应商还是续签供应商
            if ($scope.analysis.header.newOrExisting == "N") {
                $scope.gper.gp1 = 0.00;
            } else {//续签供应商
                //六种条件:销售方式 + 合同版本(A,B)
/*                if($scope.analysis.header.tradingMode.indexOf("returnable") >= 0){//tta B01
                    $scope.gper.gp1= (parseFloat(data.hterm2018.gp)).toFixed(2);//取TTA TERMS上的GP值
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment(general)") >= 0 && $scope.analysis.header.contractVersion == "A"){//B04
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment(general)") >= 0 && $scope.analysis.header.contractVersion == "B"){//B04
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment Via") >= 0 && $scope.analysis.header.contractVersion == "A"){//A01
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment Via") >= 0 && $scope.analysis.header.contractVersion == "B"){//A01
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100) - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                }*/

                if(data.hterm2019.salesType && (data.hterm2019.salesType === "B01" || data.hterm2019.salesType === "B02")){//tta B01或者B02
                    var htermGp = undefined != data.hterm2018.isSplit && data.hterm2018.isSplit == "1" ? undefined == data.hterm2018.splitGp ? 0.00 : data.hterm2018.splitGp : data.hterm2018.gp;
                    $scope.gper.gp1= (parseFloat(htermGp)).toFixed(2);//取TTA TERMS上的GP值
                } else if(data.hterm2019.salesType && (data.hterm2019.salesType === "B04" || data.hterm2019.salesType === "B03") && $scope.analysis.header.contractVersion == "A"){//B04
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount).toFixed(2);
                } else if(data.hterm2019.salesType && (data.hterm2019.salesType === "B04" || data.hterm2019.salesType === "B03") && $scope.analysis.header.contractVersion == "B"){//B04
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if(data.hterm2019.salesType && (data.hterm2019.salesType === "A01" || data.hterm2019.salesType === "A02") && $scope.analysis.header.contractVersion == "A"){//A01
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if(data.hterm2019.salesType && (data.hterm2019.salesType === "A01" || data.hterm2019.salesType === "A02") && $scope.analysis.header.contractVersion == "B"){//A01
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100) - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                }
            }

            //如果往年的TTA TERMS是拆分的,那么取拆后的GP%
            if (undefined != data.hterm2018.isSplit && data.hterm2018.isSplit == "1") {
                $scope.gper.gp1 = undefined == data.hterm2018.splitGp ? 0.00 : parseFloat(data.hterm2018.splitGp).toFixed(2);
            }

            $scope.gper.gp2 = 0.00;//on top
            $scope.gper.gp3 =parseFloat(parseFloat($scope.gper.gp1) + parseFloat($scope.gper.gp2)).toFixed(2);//tta + on top
            $scope.gper.gp4 = (parseFloat(data.gp)).toFixed(2);//actual
            //判断是新供应商还是续签供应商
            if ($scope.analysis.header.newOrExisting == "N") {//tta
        /*        if($scope.analysis.header.tradingMode.indexOf("returnable") >=0 && $scope.analysis.header.contractVersion == "A"){//条件:  可退货购销(B01) + 合同版本A + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("returnable") >=0 && $scope.analysis.header.contractVersion == "B") {//条件:可退货购销(B01) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.gp / 100)-
                        2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("经仓") >=0 && $scope.analysis.header.contractVersion == "A") {//条件:寄售经仓(A01) + 合同版本A + 新供应商
                    //$scope.gper.gp5 = (data.hterm2019.consignmentDiscount +
                    //    ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);
                } else if ($scope.analysis.header.tradingMode.indexOf("经仓") >= 0 && $scope.analysis.header.contractVersion == "B") {//条件:寄售经仓(A01) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)-
                        2 * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("常规") >= 0 && $scope.analysis.header.contractVersion == "A") {//条件:寄售(常规) (B04)+ 合同版本A + 新供应商
                    $scope.gper.gp5 = data.hterm2019.consignmentDiscount;

                } else if ($scope.analysis.header.tradingMode.indexOf("常规") >= 0 && $scope.analysis.header.contractVersion == "B") {//条件:寄售(常规)(B04) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount +
                        ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);
                }*/

                if(data.hterm2019.salesType && (data.hterm2019.salesType === "B01" || data.hterm2019.salesType === "B02") && $scope.analysis.header.contractVersion == "A"){//条件:  可退货购销(B01) + 合同版本A + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if (data.hterm2019.salesType && (data.hterm2019.salesType === "B01" || data.hterm2019.salesType === "B02") && $scope.analysis.header.contractVersion == "B") {//条件:可退货购销(B01) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.gp / 100)-
                        2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if (data.hterm2019.salesType && (data.hterm2019.salesType === "A01" || data.hterm2019.salesType === "A02") && $scope.analysis.header.contractVersion == "A") {//条件:寄售经仓(A01) + 合同版本A + 新供应商
                    //$scope.gper.gp5 = (data.hterm2019.consignmentDiscount +
                    //    ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);
                } else if (data.hterm2019.salesType && (data.hterm2019.salesType === "A01" || data.hterm2019.salesType === "A02") && $scope.analysis.header.contractVersion == "B") {//条件:寄售经仓(A01) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)-
                        2 * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);

                } else if (data.hterm2019.salesType && (data.hterm2019.salesType === "B04" || data.hterm2019.salesType === "B03") && $scope.analysis.header.contractVersion == "A") {//条件:寄售(常规) (B04)+ 合同版本A + 新供应商
                    $scope.gper.gp5 = data.hterm2019.consignmentDiscount;

                } else if (data.hterm2019.salesType && (data.hterm2019.salesType === "B04" || data.hterm2019.salesType === "B03") && $scope.analysis.header.contractVersion == "B") {//条件:寄售(常规)(B04) + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount +
                        ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);
                }


            } else {//续签供应商------->条件: 各种销售方式 + 合同版本
                if($scope.analysis.header.contractVersion == "A") {//合同版本为A
                    $scope.gper.gp5=(parseFloat(data.fgp)).toFixed(2);
                } else {//合同版本为B
                    var currentProposalYearBeoiFeeIntas = ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2);
                    var oldProposalYearBeoiFeeIntas = ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz);

                    if ((currentProposalYearBeoiFeeIntas - oldProposalYearBeoiFeeIntas) == 0) {
                        $scope.gper.gp5=(parseFloat(data.fgp)).toFixed(2);
                    } else {
                        $scope.gper.gp5 = ((currentProposalYearBeoiFeeIntas - oldProposalYearBeoiFeeIntas) * (1 - parseFloat(data.fgp)/100) + parseFloat(data.fgp)).toFixed(2);
                    }
                }
            }
            //这条公式在第一次加载,可能在刷新按钮的时候有问题,请注意,当freegoods有数据的时候,重新点刷新按钮会把数据刷没,因为刷新按钮是重新拉数,到时候要考虑,刷新的时候是更新这个格子的值
            $scope.gper.gp6 =($scope.analysis.salsePurchase.currentYearTta  == 0 ? 0 : parseFloat($scope.analysis.freeGood.currentYearOntop) / parseFloat($scope.analysis.salsePurchase.currentYearTta) * 100).toFixed(2);
            $scope.gper.gp7 =(parseFloat($scope.gper.gp5) + parseFloat(isNaN($scope.gper.gp6) ? 0 : $scope.gper.gp6)).toFixed(2);
            $scope.gper.gp8 = 0.00;// n/a
            $scope.gper.gp9 = (parseFloat($scope.gper.gp7) - parseFloat($scope.gper.gp3)).toFixed(2);
            $scope.gper.gp10 = 0.00;// n/a
            $scope.gper.gp11 = (parseFloat($scope.gper.gp7) - parseFloat($scope.gper.gp4)).toFixed(2);

            // GP%(as of Forecast/Actual sales)
            //analysiOrderNo++;
            $scope.analysis.gper.oiType = "gper";
            $scope.analysis.gper.dataType = dataType;
            //$scope.analysis.gper.anaysisId = "anaysis_" + analysiOrderNo;
            //$scope.analysis.gper.orderNo= analysiOrderNo;
            $scope.analysis.gper.termsComparison = "GP%(as of Forecast/Actual sales)";
            $scope.analysis.gper.formerYearTta = parseFloat($scope.gper.gp1);//1 tta
            $scope.analysis.gper.formerYearOntop = parseFloat($scope.gper.gp2);//2 on top
            $scope.analysis.gper.formerYearTtaOntop = parseFloat($scope.gper.gp3);// 3 tta + on top
            $scope.analysis.gper.formerYearActual = parseFloat($scope.gper.gp4);//4 actual
            $scope.analysis.gper.currentYearTta = parseFloat($scope.gper.gp5);//5 tta
            $scope.analysis.gper.currentYearOntop = parseFloat($scope.gper.gp6);//6 on top
            $scope.analysis.gper.currentYearTtaOntop = parseFloat($scope.gper.gp7);//7 tta + top
            $scope.analysis.gper.ttaOntopIncreAmt = parseFloat($scope.gper.gp8);//8
            $scope.analysis.gper.ttaOntopPercentGain = parseFloat($scope.gper.gp9);//9 9 = 7 - 3
            $scope.analysis.gper.ttaOntopActIncreAmt = parseFloat($scope.gper.gp10);//10
            $scope.analysis.gper.ttaOntopActPercentGain = parseFloat($scope.gper.gp11);//11 11 =  7 - 4

            //GP$
            //GP$的值需要gp%计算出来后才能计算
            analysiOrderNo++;
            $scope.analysis.gp.oiType = "GP";
            $scope.analysis.gp.dataType = dataType;
            $scope.analysis.gp.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.gp.orderNo= analysiOrderNo;
            $scope.analysis.gp.termsComparison = "GP$";

            //如下为设置GP%的排序号
            analysiOrderNo++;
            $scope.analysis.gper.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.gper.orderNo= analysiOrderNo;
            //$scope.analysis.sales.formerYearTta
            $scope.analysis.gp.formerYearTta = data.contractLineList2018.length == 0 ? 0 : parseFloat((parseFloat(data.contractLineList2018[0].sales) * parseFloat($scope.analysis.gper.formerYearTta)/ 100).toFixed(0));//1 tta
            $scope.analysis.gp.formerYearOntop = 0;// "-  " 2 on top
            $scope.analysis.gp.formerYearTtaOntop = parseFloat($scope.analysis.gp.formerYearTta);//3 tta + on top
            $scope.analysis.gp.formerYearActual = parseFloat((parseFloat($scope.analysis.sales.formerYearActual) * parseFloat($scope.analysis.gper.formerYearActual) / 100).toFixed(0));//4 actual
            $scope.analysis.gp.currentYearTta = parseFloat(parseFloat($scope.analysis.sales.currentYearTta) * parseFloat($scope.analysis.gper.currentYearTta) / 100).toFixed(0);//5 tta
            $scope.analysis.gp.currentYearOntop = 0;//6 on top
            $scope.analysis.gp.currentYearTtaOntop = parseFloat((parseFloat($scope.analysis.gp.currentYearTta) + parseFloat($scope.analysis.gp.currentYearOntop))).toFixed(0);//7 tta + on top
            $scope.analysis.gp.ttaOntopIncreAmt = parseFloat((parseFloat($scope.analysis.gp.currentYearTtaOntop) - parseFloat($scope.analysis.gp.formerYearTtaOntop))).toFixed(0);//8 8 = 7 - 3
            $scope.analysis.gp.ttaOntopPercentGain = $scope.analysis.gp.formerYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.gp.ttaOntopIncreAmt * 100 / parseFloat($scope.analysis.gp.formerYearTtaOntop))).toFixed(2);//9 9 = 8 / 3
            $scope.analysis.gp.ttaOntopActIncreAmt = (parseFloat($scope.analysis.gp.currentYearTtaOntop) - parseFloat($scope.analysis.gp.formerYearActual)).toFixed(0);//10  10 = 7 - 4
            $scope.analysis.gp.ttaOntopActPercentGain = $scope.analysis.gp.formerYearActual == 0 ? 0 : (parseFloat($scope.analysis.gp.ttaOntopActIncreAmt) * 100 / parseFloat($scope.analysis.gp.formerYearActual)).toFixed(2);//11 11 = 10 / 4
            $scope.analysisDataList.push($scope.analysis.gp);//先添加GP$再添加GP%
            $scope.analysisDataList.push($scope.analysis.gper);

            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>构造BEOI,SROI,ABOI条款费用数据 开始<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            // BEOI
            for (var boi = 0; boi < $scope.BEOI2.length; boi++) {
                var rowData1 = $scope.BEOI1[boi];//往年
                var rowData2 = $scope.BEOI2[boi];//当年
                analysiOrderNo++;
                var beoiObject = {};
                beoiObject.oiType = "BEOI";
                beoiObject.dataType = dataType;
                beoiObject.anaysisId = "anaysis_" + analysiOrderNo;
                beoiObject.orderNo = analysiOrderNo;
                beoiObject.termsComparison = rowData2.termsCn;
                beoiObject.contractLId = rowData2.contractLId;
                beoiObject.oldContractLId = rowData1.contractLId;
                beoiObject.termsEn = rowData2.termsEn;
                beoiObject.termsCn = rowData2.termsCn;
                beoiObject.formerYearTta = $scope.dataTable.contractLineList2018.length == 0 ? 0 : $scope.dataTable.contractLineList2018[0].purchase == 0 ? 0.00 : (parseFloat(rowData1.feeIntas) * 100 / parseFloat($scope.dataTable.contractLineList2018[0].purchase)).toFixed(2);//1
                beoiObject.formerYearOntop = 0.00;//2
                beoiObject.formerYearTtaOntop = (parseFloat(beoiObject.formerYearTta) + parseFloat(beoiObject.formerYearOntop)).toFixed(2);//3
                beoiObject.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0.00 : (parseFloat(rowData1.sumMoney) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
                beoiObject.currentYearTta = $scope.adata.fpurchase == 0 ? 0.00 : (parseFloat(rowData2.feeIntas) * 100 / parseFloat($scope.adata.fpurchase / 1000)).toFixed(2);//5
                beoiObject.currentYearOntop = 0.00;//6
                beoiObject.currentYearTtaOntop = (parseFloat(beoiObject.currentYearTta) + parseFloat(beoiObject.currentYearOntop)).toFixed(2);//7
                beoiObject.ttaOntopIncreAmt = 0.00;// n/a 8
                var a = isNaN($scope.adata.fpurchase / 1000) ? 0 : $scope.adata.fpurchase/1000;
                var b = $scope.dataTable.contractLineList2018.length == 0 ? 0 : isNaN($scope.dataTable.contractLineList2018[0].purchase) ? 0 : $scope.dataTable.contractLineList2018[0].purchase;
                beoiObject.ttaOntopPercentGain = (parseFloat((a == 0 ? 0 : parseFloat(rowData2.feeIntas) * 100 / parseFloat(a))) - parseFloat((b == 0 ? 0 : parseFloat(rowData1.feeIntas) * 100 / parseFloat(b)))).toFixed(2);//9  9 = 7 - 3
                beoiObject.ttaOntopActIncreAmt = 0.00;// n/a 10
                var c = isNaN($scope.adata.fpurchase / 1000) ? 0 : $scope.adata.fpurchase/1000;
                var d = isNaN($scope.adata.purchase / 1000) ? 0 : $scope.adata.purchase/1000;
                beoiObject.ttaOntopActPercentGain = (parseFloat((c == 0 ? 0 : parseFloat(rowData2.feeIntas) * 100 / parseFloat(c))) - parseFloat((d == 0 ? 0 : parseFloat(rowData1.sumMoney) * 100 / parseFloat(d)))).toFixed(2);//11 11 = 7 - 4
                $scope.analysisDataList.push(beoiObject);

                if (boi == $scope.BEOI2.length - 1) {//最后一行
                    //前端倒数第二行
                    var lastTwoRowTotolBeoi = {};
                    analysiOrderNo++;
                    lastTwoRowTotolBeoi.oiType = "BEOI";
                    lastTwoRowTotolBeoi.dataType = dataType;
                    lastTwoRowTotolBeoi.anaysisId = "anaysis_" + analysiOrderNo;
                    lastTwoRowTotolBeoi.orderNo = analysiOrderNo;
                    lastTwoRowTotolBeoi.termsComparison = "TOTAL BEOI$";
                    lastTwoRowTotolBeoi.termsEn = "TOTAL BEOI$";
                    lastTwoRowTotolBeoi.termsCn = "TOTAL BEOI$";
                    lastTwoRowTotolBeoi.formerYearTta = parseFloat($scope.TotalBeoiA);//1
                    lastTwoRowTotolBeoi.formerYearOntop = parseFloat($scope.TotalBeoiB);//2
                    lastTwoRowTotolBeoi.formerYearTtaOntop = parseFloat($scope.TotalBeoiC);//3
                    lastTwoRowTotolBeoi.formerYearActual = parseFloat($scope.TotalBeoiD);//4
                    lastTwoRowTotolBeoi.currentYearTta = parseFloat($scope.TotalBeoiE);//5
                    lastTwoRowTotolBeoi.currentYearOntop = parseFloat($scope.TotalBeoiF);//6
                    lastTwoRowTotolBeoi.currentYearTtaOntop = parseFloat($scope.TotalBeoiG);//7
                    lastTwoRowTotolBeoi.ttaOntopIncreAmt = parseFloat($scope.TotalBeoiH);//8
                    lastTwoRowTotolBeoi.ttaOntopPercentGain = parseFloat($scope.TotalBeoiI).toFixed(2);//9
                    lastTwoRowTotolBeoi.ttaOntopActIncreAmt = parseFloat($scope.TotalBeoiJ);//10
                    lastTwoRowTotolBeoi.ttaOntopActPercentGain = parseFloat($scope.TotalBeoiK).toFixed(2);//11

                    //前端倒数第一行
                    var lastFirstRowBeoiPercent = {};
                    analysiOrderNo++;
                    lastFirstRowBeoiPercent.oiType = "BEOI";
                    lastFirstRowBeoiPercent.dataType = dataType;
                    lastFirstRowBeoiPercent.anaysisId = "anaysis_" + analysiOrderNo;
                    lastFirstRowBeoiPercent.orderNo = analysiOrderNo;
                    lastFirstRowBeoiPercent.termsComparison = "BEOI%(as of purchase)";
                    lastFirstRowBeoiPercent.termsEn = "BEOI%(as of purchase)";
                    lastFirstRowBeoiPercent.termsCn = "BEOI%(as of purchase)";
                    lastFirstRowBeoiPercent.formerYearTta = $scope.analysis.purchase.formerYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolBeoi.formerYearTta) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//1
                    lastFirstRowBeoiPercent.formerYearOntop = 0.00;// 0.00% 2
                    lastFirstRowBeoiPercent.formerYearTtaOntop = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolBeoi.formerYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTtaOntop)).toFixed(2);//3
                    lastFirstRowBeoiPercent.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0.00 : (parseFloat(lastTwoRowTotolBeoi.formerYearActual) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
                    lastFirstRowBeoiPercent.currentYearTta = $scope.analysis.purchase.currentYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolBeoi.currentYearTta) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//5
                    lastFirstRowBeoiPercent.currentYearOntop = 0.00;//6
                    lastFirstRowBeoiPercent.currentYearTtaOntop = $scope.analysis.purchase.currentYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolBeoi.currentYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTtaOntop)).toFixed(2);//7
                    lastFirstRowBeoiPercent.ttaOntopIncreAmt = 0.00;//n/a 8
                    lastFirstRowBeoiPercent.ttaOntopPercentGain = (parseFloat(lastFirstRowBeoiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowBeoiPercent.formerYearTtaOntop)).toFixed(2);//9 9 = 7 - 3
                    lastFirstRowBeoiPercent.ttaOntopActIncreAmt = 0.00;// n/a 10
                    lastFirstRowBeoiPercent.ttaOntopActPercentGain = (parseFloat(lastFirstRowBeoiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowBeoiPercent.formerYearActual)).toFixed(2);//11  11 = 7-4

                    if ($scope.analysis.header.newOrExisting == "N") {//新供应商
                        lastFirstRowBeoiPercent.formerYearTta = 0.00;
                        lastFirstRowBeoiPercent.formerYearActual = 0.00;
                    }
                    $scope.analysisDataList.push(lastTwoRowTotolBeoi);
                    $scope.analysisDataList.push(lastFirstRowBeoiPercent);
                }
            }

            var sroiFormerYearTtaTotal = 0;
            var sroiFormerYearOntopTotal = 0;
            var sroiFormerYearTtaOntopTotal = 0;
            var sroiFormerYearActualTotal = 0;
            var sroiCurrentYearTtaTotal = 0;
            var sroiCurrentYearOntopTotal = 0;
            var sroiCurrentYearTtaOntopTotal = 0;

            //SROI
            for (var i = 0; i < $scope.SROI2.length; i++) {
                var sroiRowData1 = $scope.SROI1[i];//往年
                var sroiRowData2 = $scope.SROI2[i];//当年
                analysiOrderNo++;
                var sroiObject = {};
                sroiObject.oiType = "SROI";
                sroiObject.dataType = dataType;
                sroiObject.anaysisId = "anaysis_" + analysiOrderNo;
                sroiObject.orderNo = analysiOrderNo;
                sroiObject.termsComparison = sroiRowData2.termsCn;
                sroiObject.contractLId = sroiRowData2.contractLId;
                sroiObject.oldContractLId = sroiRowData1.contractLId;
                sroiObject.termsEn = sroiRowData2.termsEn;
                sroiObject.termsCn = sroiRowData2.termsCn;
                sroiRowData1.feeIntas = (sroiRowData1.feeIntas == undefined || sroiRowData1.feeIntas == "") ? 0 : sroiRowData1.feeIntas;
                sroiObject.formerYearTta = (parseFloat(sroiRowData1.feeIntas)).toFixed(0);//1
                sroiObject.formerYearOntop = 0;//原来显示的值:"-   " 2
                sroiObject.formerYearTtaOntop = parseFloat(sroiObject.formerYearTta);//3
                sroiObject.formerYearActual = (parseFloat(sroiRowData1.sumMoney)).toFixed(0);//4
                sroiObject.currentYearTta = (parseFloat(sroiRowData2.feeIntas)).toFixed(0);//5
                sroiObject.currentYearOntop = 0;//"-   " 6
                sroiObject.currentYearTtaOntop = parseFloat(sroiObject.currentYearTta);//7
                sroiObject.ttaOntopIncreAmt = (parseFloat(sroiObject.currentYearTtaOntop) - parseFloat(sroiObject.formerYearTtaOntop)).toFixed(0) ;//8  8 = 7 - 3
                sroiObject.ttaOntopPercentGain = sroiObject.formerYearTtaOntop == 0 ? 0 : (parseFloat(sroiObject.ttaOntopIncreAmt) * 100 / parseFloat(sroiObject.formerYearTtaOntop)).toFixed(2);//9 9 = 8 / 3
                sroiObject.ttaOntopActIncreAmt = parseFloat(sroiObject.currentYearTtaOntop) - parseFloat(sroiObject.formerYearActual);//10 10 = 7 - 4
                sroiObject.ttaOntopActPercentGain = sroiObject.formerYearActual == 0 ? 0 : (parseFloat(sroiObject.ttaOntopActIncreAmt) * 100 / parseFloat(sroiObject.formerYearActual)).toFixed(2);//11 11 = 10 / 4
                $scope.analysisDataList.push(sroiObject);

                sroiFormerYearTtaTotal = (parseFloat(sroiFormerYearTtaTotal) + parseFloat(sroiRowData1.feeIntas)).toFixed(0);
                sroiFormerYearTtaOntopTotal = (parseFloat(sroiFormerYearTtaOntopTotal) + parseFloat(sroiRowData1.feeIntas)).toFixed(0);
                sroiFormerYearActualTotal = (parseFloat(sroiFormerYearActualTotal) + parseFloat(sroiRowData1.sumMoney)).toFixed(0);
                sroiCurrentYearTtaTotal = (parseFloat(sroiCurrentYearTtaTotal) + parseFloat(sroiRowData2.feeIntas)).toFixed(0);
                sroiCurrentYearTtaOntopTotal = (parseFloat(sroiCurrentYearTtaOntopTotal) + parseFloat(sroiRowData2.feeIntas)).toFixed(0);

                if (i == $scope.SROI2.length - 1) {
                    //前端倒数第一行
                    var lastTwoRowTotolSroi = {};
                    analysiOrderNo++;
                    lastTwoRowTotolSroi.oiType = "SROI";
                    lastTwoRowTotolSroi.dataType = dataType;
                    lastTwoRowTotolSroi.anaysisId = "anaysis_" + analysiOrderNo;
                    lastTwoRowTotolSroi.orderNo = analysiOrderNo;
                    lastTwoRowTotolSroi.termsComparison = "TOTAL SROI$";
                    lastTwoRowTotolSroi.termsEn = "TOTAL SROI$";
                    lastTwoRowTotolSroi.termsCn = "TOTAL SROI$";
                    lastTwoRowTotolSroi.formerYearTta = parseFloat(sroiFormerYearTtaTotal).toFixed(0);//1
                    lastTwoRowTotolSroi.formerYearOntop = sroiFormerYearOntopTotal;//2
                    lastTwoRowTotolSroi.formerYearTtaOntop = parseFloat(sroiFormerYearTtaOntopTotal).toFixed(0);//3
                    lastTwoRowTotolSroi.formerYearActual = parseFloat(sroiFormerYearActualTotal).toFixed(0);//4
                    lastTwoRowTotolSroi.currentYearTta = parseFloat(sroiCurrentYearTtaTotal).toFixed(0);//5
                    lastTwoRowTotolSroi.currentYearOntop = sroiCurrentYearOntopTotal;//6
                    lastTwoRowTotolSroi.currentYearTtaOntop = parseFloat(sroiCurrentYearTtaOntopTotal).toFixed(0);//7
                    lastTwoRowTotolSroi.ttaOntopIncreAmt = (parseFloat(lastTwoRowTotolSroi.currentYearTtaOntop) - parseFloat(lastTwoRowTotolSroi.formerYearTtaOntop)).toFixed(0);//8 8 = 7- 3
                    lastTwoRowTotolSroi.ttaOntopPercentGain = lastTwoRowTotolSroi.formerYearTtaOntop == 0 ? 0 : (parseFloat(lastTwoRowTotolSroi.ttaOntopIncreAmt) * 100 / parseFloat(lastTwoRowTotolSroi.formerYearTtaOntop)).toFixed(2);//9 9 = 8 / 3
                    lastTwoRowTotolSroi.ttaOntopActIncreAmt = parseFloat(lastTwoRowTotolSroi.currentYearTtaOntop) - parseFloat(lastTwoRowTotolSroi.formerYearActual);//10  10 = 7 - 4
                    lastTwoRowTotolSroi.ttaOntopActPercentGain = lastTwoRowTotolSroi.formerYearActual == 0 ? 0 : (parseFloat(lastTwoRowTotolSroi.ttaOntopActIncreAmt) * 100 / parseFloat(lastTwoRowTotolSroi.formerYearActual)).toFixed(2) ;//11 11 = 10 / 4

                    if ($scope.analysis.header.newOrExisting == "N") {//新供应商
                        lastTwoRowTotolSroi.formerYearTta = 0;
                        lastTwoRowTotolSroi.formerYearActual = 0;
                    }

                    //前端倒数第一行
                    var lastFirstRowSroiPercent = {};
                    analysiOrderNo++;
                    lastFirstRowSroiPercent.oiType = "SROI";
                    lastFirstRowSroiPercent.dataType = dataType;
                    lastFirstRowSroiPercent.anaysisId = "anaysis_" + analysiOrderNo;
                    lastFirstRowSroiPercent.orderNo = analysiOrderNo;
                    lastFirstRowSroiPercent.termsComparison = "SROI%(as of purchase)";
                    lastFirstRowSroiPercent.termsEn = "SROI%(as of purchase)";
                    lastFirstRowSroiPercent.termsCn = "SROI%(as of purchase)";
                    lastFirstRowSroiPercent.formerYearTta = $scope.analysis.purchase.formerYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolSroi.formerYearTta) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//1
                    lastFirstRowSroiPercent.formerYearOntop = 0.00;//2
                    lastFirstRowSroiPercent.formerYearTtaOntop = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolSroi.formerYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTtaOntop)).toFixed(2);//3
                    lastFirstRowSroiPercent.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0.00 : (parseFloat(lastTwoRowTotolSroi.formerYearActual) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
                    lastFirstRowSroiPercent.currentYearTta = $scope.analysis.purchase.currentYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolSroi.currentYearTta) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//5
                    lastFirstRowSroiPercent.currentYearOntop = 0.00;//6
                    lastFirstRowSroiPercent.currentYearTtaOntop = $scope.analysis.purchase.currentYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolSroi.currentYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTtaOntop)).toFixed(2);//7
                    lastFirstRowSroiPercent.ttaOntopIncreAmt = 0.00;// n/a 8
                    lastFirstRowSroiPercent.ttaOntopPercentGain = (parseFloat(lastFirstRowSroiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowSroiPercent.formerYearTtaOntop)).toFixed(2);//9  9 = 7 - 3
                    lastFirstRowSroiPercent.ttaOntopActIncreAmt = 0.00;// n/a 10
                    lastFirstRowSroiPercent.ttaOntopActPercentGain = (parseFloat(lastFirstRowSroiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowSroiPercent.formerYearActual)).toFixed(2);//11  11 = 7 - 4
                    if ($scope.analysis.header.newOrExisting == "N") {//新供应商
                        lastFirstRowSroiPercent.formerYearTta = 0;
                        lastFirstRowSroiPercent.formerYearActual = 0;
                    }
                    $scope.analysisDataList.push(lastTwoRowTotolSroi);
                    $scope.analysisDataList.push(lastFirstRowSroiPercent);
                }
            }

            var aboiFormerYearTtaTotal = 0.00;
            var aboiFormerYearOntopTotal = 0.00;
            var aboiFormerYearTtaOntopTotal = 0.00;
            var aboiFormerYearActualTotal = 0.00;
            var aboiCurrentYearTtaTotal = 0.00;
            var aboiCurrentYearOntopTotal = 0.00;
            var aboiCurrentYearTtaOntopTotal = 0.00;

            var oldAnalysisDataAboi = data.oldAnalysisData;
            //2020.3.23 analysis数据从tta_analysis_edit_data数据表迁移到tta_analysis_line数据表中
            var analysisDataObject = null;
            if(data.analysisData != undefined || data.analysisData != null) {
                if (data.analysisData.length != 0) {
                    if (data.analysisData[0].aboiList != undefined) {
                        analysisDataObject = JSON.parse(data.analysisData[0].aboiList);
                    }
                }
            }

            //ABOI
            for (var k = 0; k < $scope.ABOI2.length; k++) {
                var aboiRowData1 = $scope.ABOI1[k];//往年
                var aboiRowData2 = $scope.ABOI2[k];//当年

                var aboiTermsOntopVal = 0;
                var aboiTermsOntopKey = "A" + aboiRowData1.contractLId;
                if(oldAnalysisDataAboi != undefined || oldAnalysisDataAboi != null) {
                    aboiTermsOntopVal = oldAnalysisDataAboi[aboiTermsOntopKey];
                    if (aboiTermsOntopVal == undefined || aboiTermsOntopVal == "") {
                        aboiTermsOntopVal = 0;
                    }
                }

                //从tta_analysis_edit_data表数据迁移到tta_analysis_line表,取的是当年度的
                var aboiTermsVal = 0;
                var attrKey = "A" + aboiRowData2.contractLId;
                if (analysisDataObject != null) {
                    aboiTermsVal = analysisDataObject[attrKey];
                    if(aboiTermsVal == undefined || aboiTermsVal == ""){
                        aboiTermsVal = 0;
                    }
                }

                analysiOrderNo++;
                var aboiObject = {};
                aboiObject.oiType = "ABOI";
                aboiObject.dataType = dataType;
                aboiObject.anaysisId = "anaysis_" + analysiOrderNo;
                aboiObject.orderNo = analysiOrderNo;
                aboiObject.termsComparison = aboiRowData2.termsCn;
                aboiObject.contractLId = aboiRowData2.contractLId;
                aboiObject.oldContractLId = aboiRowData1.contractLId;
                aboiObject.termsEn = aboiRowData2.termsEn;
                aboiObject.termsCn = aboiRowData2.termsCn;
                aboiRowData1.feeIntas = (aboiRowData1.feeIntas == undefined || aboiRowData1.feeIntas == "") ? 0 : aboiRowData1.feeIntas;
                aboiObject.formerYearTta = (parseFloat(aboiRowData1.feeIntas)).toFixed(0);//1
                aboiObject.formerYearOntop = oldAnalysisDataAboi ? parseFloat(aboiTermsOntopVal).toFixed(0) : 0;//2,这个要修改,再次刷新时,这个值不应该刷掉,应该查询,带过来更新
                aboiObject.formerYearTtaOntop = (parseFloat(aboiObject.formerYearTta) + parseFloat(aboiObject.formerYearOntop)).toFixed(0);//3
                aboiObject.formerYearActual = (parseFloat(aboiRowData1.sumMoney)).toFixed(0);//4
                aboiObject.currentYearTta = (parseFloat(aboiRowData2.feeIntas)).toFixed(0);//5
                aboiObject.currentYearOntop = analysisDataObject ? parseFloat(aboiTermsVal).toFixed(0) : 0;// 这个是前端输入框,当已经填了数字,并保存,那么这个输入框的值重新加载时不能清除 6
                aboiObject.currentYearTtaOntop = (parseFloat(aboiObject.currentYearTta) + parseFloat(aboiObject.currentYearOntop)).toFixed(0);//7
                aboiObject.ttaOntopIncreAmt = (parseFloat(aboiObject.currentYearTtaOntop) - parseFloat(aboiObject.formerYearTtaOntop)).toFixed(0) ;//8  8 = 7 - 3
                aboiObject.ttaOntopPercentGain = aboiObject.formerYearTtaOntop == 0 ? 0 : (parseFloat(aboiObject.ttaOntopIncreAmt) * 100 / parseFloat(aboiObject.formerYearTtaOntop)).toFixed(2);//9 9 = 8 / 3
                aboiObject.ttaOntopActIncreAmt = (parseFloat(aboiObject.currentYearTtaOntop) - parseFloat(aboiObject.formerYearActual)).toFixed(0);//10 10 = 7 - 4
                aboiObject.ttaOntopActPercentGain = aboiObject.formerYearActual == 0 ? 0 : (parseFloat(aboiObject.ttaOntopActIncreAmt) * 100 / parseFloat(aboiObject.formerYearActual)).toFixed(2);//11 11 = 10 / 4
                $scope.analysisDataList.push(aboiObject);

                aboiFormerYearTtaTotal = (parseFloat(aboiFormerYearTtaTotal) + parseFloat(aboiRowData1.feeIntas)).toFixed(0);
                aboiFormerYearOntopTotal = (parseFloat(aboiFormerYearOntopTotal) + parseFloat(aboiObject.formerYearOntop)).toFixed(0);//这个可能要修改
                aboiFormerYearTtaOntopTotal = (parseFloat(aboiFormerYearTtaTotal) + parseFloat(aboiFormerYearOntopTotal)).toFixed(0);
                aboiFormerYearActualTotal = (parseFloat(aboiFormerYearActualTotal) + parseFloat(aboiRowData1.sumMoney)).toFixed(0);
                aboiCurrentYearTtaTotal = (parseFloat(aboiCurrentYearTtaTotal) + parseFloat(aboiRowData2.feeIntas)).toFixed(0);
                aboiCurrentYearOntopTotal = (parseFloat(aboiCurrentYearOntopTotal) + parseFloat(aboiObject.currentYearOntop)).toFixed(0);//这个可能要修改
                aboiCurrentYearTtaOntopTotal = (parseFloat(aboiCurrentYearTtaTotal) + parseFloat(aboiCurrentYearOntopTotal)).toFixed(0);

                if (k == $scope.ABOI2.length - 1) {
                    //前端倒数第一行
                    var lastTwoRowTotolAboi = {};
                    analysiOrderNo++;
                    lastTwoRowTotolAboi.oiType = "ABOI";
                    lastTwoRowTotolAboi.dataType = dataType;
                    lastTwoRowTotolAboi.anaysisId = "anaysis_" + analysiOrderNo;
                    lastTwoRowTotolAboi.orderNo = analysiOrderNo;
                    lastTwoRowTotolAboi.termsComparison = "TOTAL ABOI$";
                    lastTwoRowTotolAboi.termsEn = "TOTAL ABOI$";
                    lastTwoRowTotolAboi.termsCn = "TOTAL ABOI$";
                    lastTwoRowTotolAboi.formerYearTta = parseFloat(aboiFormerYearTtaTotal).toFixed(0);//1
                    lastTwoRowTotolAboi.formerYearOntop = parseFloat(aboiFormerYearOntopTotal);//2
                    lastTwoRowTotolAboi.formerYearTtaOntop = parseFloat(aboiFormerYearTtaOntopTotal).toFixed(0);//3
                    lastTwoRowTotolAboi.formerYearActual = parseFloat(aboiFormerYearActualTotal).toFixed(0);//4
                    lastTwoRowTotolAboi.currentYearTta = parseFloat(aboiCurrentYearTtaTotal).toFixed(0);//5
                    lastTwoRowTotolAboi.currentYearOntop = parseFloat(aboiCurrentYearOntopTotal);//6
                    lastTwoRowTotolAboi.currentYearTtaOntop = parseFloat(aboiCurrentYearTtaOntopTotal).toFixed(0);//7
                    lastTwoRowTotolAboi.ttaOntopIncreAmt = (parseFloat(lastTwoRowTotolAboi.currentYearTtaOntop) - parseFloat(lastTwoRowTotolAboi.formerYearTtaOntop)).toFixed(0);//8 8 = 7- 3
                    lastTwoRowTotolAboi.ttaOntopPercentGain = lastTwoRowTotolAboi.formerYearTtaOntop == 0 ? 0 : (parseFloat(lastTwoRowTotolAboi.ttaOntopIncreAmt) * 100 / parseFloat(lastTwoRowTotolAboi.formerYearTtaOntop)).toFixed(2);//9 9 = 8 / 3
                    lastTwoRowTotolAboi.ttaOntopActIncreAmt = parseFloat(lastTwoRowTotolAboi.currentYearTtaOntop) - parseFloat(lastTwoRowTotolAboi.formerYearActual);//10  10 = 7 - 4
                    lastTwoRowTotolAboi.ttaOntopActPercentGain = lastTwoRowTotolAboi.formerYearActual == 0 ? 0 : (parseFloat(lastTwoRowTotolAboi.ttaOntopActIncreAmt) * 100 / parseFloat(lastTwoRowTotolAboi.formerYearActual)).toFixed(2) ;//11 11 = 10 / 4

                    if ($scope.analysis.header.newOrExisting == "N") {//新供应商
                        lastTwoRowTotolAboi.formerYearTta = 0;
                        lastTwoRowTotolAboi.formerYearActual = 0;
                    }

                    //前端倒数第一行
                    var lastFirstRowAboiPercent = {};
                    analysiOrderNo++;
                    lastFirstRowAboiPercent.oiType = "ABOI";
                    lastFirstRowAboiPercent.dataType = dataType;
                    lastFirstRowAboiPercent.anaysisId = "anaysis_" + analysiOrderNo;
                    lastFirstRowAboiPercent.orderNo = analysiOrderNo;
                    lastFirstRowAboiPercent.termsComparison = "ABOI%(as of purchase)";
                    lastFirstRowAboiPercent.termsEn = "ABOI%(as of purchase)";
                    lastFirstRowAboiPercent.termsCn = "ABOI%(as of purchase)";
                    lastFirstRowAboiPercent.formerYearTta = $scope.analysis.purchase.formerYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.formerYearTta) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//1
                    lastFirstRowAboiPercent.formerYearOntop = $scope.analysis.purchase.formerYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.formerYearOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//2
                    lastFirstRowAboiPercent.formerYearTtaOntop = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.formerYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTtaOntop)).toFixed(2);//3
                    lastFirstRowAboiPercent.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.formerYearActual) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
                    lastFirstRowAboiPercent.currentYearTta = $scope.analysis.purchase.currentYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.currentYearTta) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//5
                    lastFirstRowAboiPercent.currentYearOntop = $scope.analysis.purchase.currentYearTta == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.currentYearOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//6
                    lastFirstRowAboiPercent.currentYearTtaOntop = $scope.analysis.purchase.currentYearTtaOntop == 0 ? 0.00 : (parseFloat(lastTwoRowTotolAboi.currentYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTtaOntop)).toFixed(2);//7
                    lastFirstRowAboiPercent.ttaOntopIncreAmt = 0.00;// n/a 8
                    lastFirstRowAboiPercent.ttaOntopPercentGain = (parseFloat(lastFirstRowAboiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowAboiPercent.formerYearTtaOntop)).toFixed(2);//9  9 = 7 - 3
                    lastFirstRowAboiPercent.ttaOntopActIncreAmt = 0.00;// n/a 10
                    lastFirstRowAboiPercent.ttaOntopActPercentGain = (parseFloat(lastFirstRowAboiPercent.currentYearTtaOntop) - parseFloat(lastFirstRowAboiPercent.formerYearActual)).toFixed(2);//11  11 = 7 - 4
                    if ($scope.analysis.header.newOrExisting == "N") {//新供应商
                        lastFirstRowAboiPercent.formerYearTta = 0.00;
                        lastFirstRowAboiPercent.formerYearActual = 0.00;
                    }
                    $scope.analysisDataList.push(lastTwoRowTotolAboi);
                    $scope.analysisDataList.push(lastFirstRowAboiPercent);
                }
            }
            //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>构造BEOI,SROI,ABOI条款费用数据 结束<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            //统计汇总数据
            analysiOrderNo++;
            $scope.analysis.tailOI.oiType = "tailOI";
            $scope.analysis.tailOI.dataType = dataType;
            $scope.analysis.tailOI.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.tailOI.orderNo= analysiOrderNo;
            $scope.analysis.tailOI.termsComparison = "Total OI$";

            //获取汇总行
            var sumLength1 = 8 + $scope.BEOI2.length + 2 - 1 - 1;
            var sumLength2 = 8 + $scope.BEOI2.length + 2 + $scope.SROI2.length + 2 - 1 - 1;
            var sumLength3 = 8 + $scope.BEOI2.length + 2 + $scope.SROI2.length + 2 + $scope.ABOI2.length + 2 - 1 - 1;

            //计算Total OI$ 行的值(公式:Total BEOI$ + SROI$ + ABOI$) 公式:ae = n + u + ac
            $scope.analysis.tailOI.formerYearTta = (parseFloat($scope.analysisDataList[sumLength1].formerYearTta) + parseFloat($scope.analysisDataList[sumLength2].formerYearTta) + parseFloat($scope.analysisDataList[sumLength3].formerYearTta)).toFixed(0);//1
            $scope.analysis.tailOI.formerYearOntop = (parseFloat($scope.analysisDataList[sumLength1].formerYearOntop) + parseFloat($scope.analysisDataList[sumLength2].formerYearOntop) + parseFloat($scope.analysisDataList[sumLength3].formerYearOntop)).toFixed(0);//2 "-
            $scope.analysis.tailOI.formerYearTtaOntop = (parseFloat($scope.analysisDataList[sumLength1].formerYearTtaOntop) + parseFloat($scope.analysisDataList[sumLength2].formerYearTtaOntop) + parseFloat($scope.analysisDataList[sumLength3].formerYearTtaOntop)).toFixed(0);//3
            $scope.analysis.tailOI.formerYearActual = (parseFloat($scope.analysisDataList[sumLength1].formerYearActual) + parseFloat($scope.analysisDataList[sumLength2].formerYearActual) + parseFloat($scope.analysisDataList[sumLength3].formerYearActual)).toFixed(0);//4
            $scope.analysis.tailOI.currentYearTta = (parseFloat($scope.analysisDataList[sumLength1].currentYearTta) + parseFloat($scope.analysisDataList[sumLength2].currentYearTta) + parseFloat($scope.analysisDataList[sumLength3].currentYearTta)).toFixed(0);//5
            $scope.analysis.tailOI.currentYearOntop = (parseFloat($scope.analysisDataList[sumLength1].currentYearOntop) + parseFloat($scope.analysisDataList[sumLength2].currentYearOntop) + parseFloat($scope.analysisDataList[sumLength3].currentYearOntop)).toFixed(0);//6
            $scope.analysis.tailOI.currentYearTtaOntop = (parseFloat($scope.analysisDataList[sumLength1].currentYearTtaOntop) + parseFloat($scope.analysisDataList[sumLength2].currentYearTtaOntop) + parseFloat($scope.analysisDataList[sumLength3].currentYearTtaOntop)).toFixed(0);//7
            $scope.analysis.tailOI.ttaOntopIncreAmt = (parseFloat($scope.analysis.tailOI.currentYearTtaOntop) - parseFloat($scope.analysis.tailOI.formerYearTtaOntop)).toFixed(0);//8 8 = 7 - 3
            $scope.analysis.tailOI.ttaOntopPercentGain = $scope.analysis.tailOI.formerYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.ttaOntopIncreAmt) * 100 / parseFloat($scope.analysis.tailOI.formerYearTtaOntop)).toFixed(2);//9 9 = 8 / 3
            $scope.analysis.tailOI.ttaOntopActIncreAmt = (parseFloat($scope.analysis.tailOI.currentYearTtaOntop) - parseFloat($scope.analysis.tailOI.formerYearActual)).toFixed(0);//10 10 = 7 - 4
            $scope.analysis.tailOI.ttaOntopActPercentGain = $scope.analysis.tailOI.formerYearActual == 0 ? 0 : (parseFloat($scope.analysis.tailOI.ttaOntopActIncreAmt) * 100 / parseFloat($scope.analysis.tailOI.formerYearActual)).toFixed(2);//11 11 = 10 / 4


            //计算Total OI%(as of Purchase) 行的值 (公式:Total OI$ / Purchase$) 公式:af = ae /c
            analysiOrderNo++;
            $scope.analysis.tailPurchase.oiType = "tailPurchase";
            $scope.analysis.tailPurchase.dataType = dataType;
            $scope.analysis.tailPurchase.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.tailPurchase.orderNo= analysiOrderNo;
            $scope.analysis.tailPurchase.termsComparison = "Total OI%(as of Purchase)";
            $scope.analysis.tailPurchase.formerYearTta = $scope.analysis.purchase.formerYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearTta) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//1
            $scope.analysis.tailPurchase.formerYearOntop = $scope.analysis.purchase.formerYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTta)).toFixed(2);//2 "-
            $scope.analysis.tailPurchase.formerYearTtaOntop = $scope.analysis.purchase.formerYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.formerYearTtaOntop)).toFixed(2);//3
            $scope.analysis.tailPurchase.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearActual) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
            $scope.analysis.tailPurchase.currentYearTta = $scope.analysis.purchase.currentYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearTta) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//5
            $scope.analysis.tailPurchase.currentYearOntop = $scope.analysis.purchase.currentYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTta)).toFixed(2);//6
            $scope.analysis.tailPurchase.currentYearTtaOntop = $scope.analysis.purchase.currentYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearTtaOntop) * 100 / parseFloat($scope.analysis.purchase.currentYearTtaOntop)).toFixed(2);//7
            $scope.analysis.tailPurchase.ttaOntopIncreAmt = 0.00;// n/a 8
            $scope.analysis.tailPurchase.ttaOntopPercentGain = (parseFloat($scope.analysis.tailPurchase.currentYearTtaOntop) - parseFloat($scope.analysis.tailPurchase.formerYearTtaOntop)).toFixed(2);//9  9 = 7 - 3
            $scope.analysis.tailPurchase.ttaOntopActIncreAmt = 0.00;// n/a 10
            $scope.analysis.tailPurchase.ttaOntopActPercentGain = (parseFloat($scope.analysis.tailPurchase.currentYearTtaOntop) - parseFloat($scope.analysis.tailPurchase.formerYearActual)).toFixed(2);//11 11 = 7 - 4

            //计算 Total OI%(as of Sales)  公式:ag = ae / d
            analysiOrderNo++;
            $scope.analysis.tailSales.oiType = "tailSales";
            $scope.analysis.tailSales.dataType = dataType;
            $scope.analysis.tailSales.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.tailSales.orderNo= analysiOrderNo;
            $scope.analysis.tailSales.termsComparison = "Total OI%(as of Forecast/Actual sales)";
            $scope.analysis.tailSales.formerYearTta = $scope.analysis.sales.formerYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearTta) * 100 / parseFloat($scope.analysis.sales.formerYearTta)).toFixed(2);//1
            $scope.analysis.tailSales.formerYearOntop = $scope.analysis.sales.formerYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearOntop) * 100 / parseFloat($scope.analysis.sales.formerYearTta)).toFixed(2);//2 "-
            $scope.analysis.tailSales.formerYearTtaOntop = $scope.analysis.sales.formerYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearTtaOntop) * 100 / parseFloat($scope.analysis.sales.formerYearTtaOntop)).toFixed(2);//3
            $scope.analysis.tailSales.formerYearActual = $scope.analysis.sales.formerYearActual == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearActual) * 100 / parseFloat($scope.analysis.sales.formerYearActual)).toFixed(2);//4
            $scope.analysis.tailSales.currentYearTta = $scope.analysis.sales.currentYearTta == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearTta) * 100 / parseFloat($scope.analysis.sales.currentYearTta)).toFixed(2);//5
            $scope.analysis.tailSales.currentYearOntop = $scope.analysis.sales.currentYearOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearOntop) * 100 / parseFloat($scope.analysis.sales.currentYearOntop)).toFixed(2);//6
            $scope.analysis.tailSales.currentYearTtaOntop = $scope.analysis.sales.currentYearTtaOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.currentYearTtaOntop) * 100 / parseFloat($scope.analysis.sales.currentYearTtaOntop)).toFixed(2);//7
            $scope.analysis.tailSales.ttaOntopIncreAmt = 0.00;// n/a 8
            $scope.analysis.tailSales.ttaOntopPercentGain = (parseFloat($scope.analysis.tailSales.currentYearTtaOntop) - parseFloat($scope.analysis.tailSales.formerYearTtaOntop)).toFixed(2);//9  9 = 7 - 3
            $scope.analysis.tailSales.ttaOntopActIncreAmt = 0.00;// n/a 10
            $scope.analysis.tailSales.ttaOntopActPercentGain = (parseFloat($scope.analysis.tailSales.currentYearTtaOntop) - parseFloat($scope.analysis.tailSales.formerYearActual)).toFixed(2);//11 11 = 7 - 4

            //计算Net Margin%(as of Sales) 行的值 公式:ah = f + ag
            analysiOrderNo++;
            $scope.analysis.tailNetMargin.oiType = "tailNetMargin";
            $scope.analysis.tailNetMargin.dataType = dataType;
            $scope.analysis.tailNetMargin.anaysisId = "anaysis_" + analysiOrderNo;
            $scope.analysis.tailNetMargin.orderNo= analysiOrderNo;
            $scope.analysis.tailNetMargin.termsComparison = "Net Margin%(as of Forecast/Actual sales)";
            $scope.analysis.tailNetMargin.formerYearTta = (parseFloat($scope.analysis.gper.formerYearTta) + parseFloat($scope.analysis.tailSales.formerYearTta)).toFixed(2);//1
            $scope.analysis.tailNetMargin.formerYearOntop = (parseFloat($scope.analysis.gper.formerYearOntop) + parseFloat($scope.analysis.tailSales.formerYearOntop)).toFixed(2);//2
            $scope.analysis.tailNetMargin.formerYearTtaOntop = (parseFloat($scope.analysis.gper.formerYearTtaOntop) + parseFloat($scope.analysis.tailSales.formerYearTtaOntop)).toFixed(2);//3
            $scope.analysis.tailNetMargin.formerYearActual = (parseFloat($scope.analysis.gper.formerYearActual) + parseFloat($scope.analysis.tailSales.formerYearActual)).toFixed(2);//4
            $scope.analysis.tailNetMargin.currentYearTta = (parseFloat($scope.analysis.gper.currentYearTta) + parseFloat($scope.analysis.tailSales.currentYearTta)).toFixed(2);//5
            $scope.analysis.tailNetMargin.currentYearOntop = (parseFloat($scope.analysis.gper.currentYearOntop) + parseFloat($scope.analysis.tailSales.currentYearOntop)).toFixed(2);//6
            $scope.analysis.tailNetMargin.currentYearTtaOntop = (parseFloat($scope.analysis.gper.currentYearTtaOntop) + parseFloat($scope.analysis.tailSales.currentYearTtaOntop)).toFixed(2);//7
            $scope.analysis.tailNetMargin.ttaOntopIncreAmt = 0.00;// n/a 8
            $scope.analysis.tailNetMargin.ttaOntopPercentGain = (parseFloat($scope.analysis.tailNetMargin.currentYearTtaOntop) - parseFloat($scope.analysis.tailNetMargin.formerYearTtaOntop)).toFixed(2);//9 9 = 7 - 3
            $scope.analysis.tailNetMargin.ttaOntopActIncreAmt = 0.00;// n/a 10
            $scope.analysis.tailNetMargin.ttaOntopActPercentGain = (parseFloat($scope.analysis.tailNetMargin.currentYearTtaOntop) - parseFloat($scope.analysis.tailNetMargin.formerYearActual)).toFixed(2);//11 11 = 7 - 4

            $scope.analysisDataList.push($scope.analysis.tailOI);
            $scope.analysisDataList.push($scope.analysis.tailPurchase);
            $scope.analysisDataList.push($scope.analysis.tailSales);
            $scope.analysisDataList.push($scope.analysis.tailNetMargin);

            //设置数据到头部分
            var percentLength1 = 8 + $scope.BEOI2.length + 2 - 1 ;//beoi
            var percentLength2 = 8 + $scope.BEOI2.length + 2 + $scope.SROI2.length + 2 - 1 ;//sroi
            var percentLength3 = 8 + $scope.BEOI2.length + 2 + $scope.SROI2.length + 2 + $scope.ABOI2.length + 2 - 1 ;//aboi

            $scope.analysis.header.purchase = parseFloat($scope.analysis.purchase.ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.gp = parseFloat($scope.analysis.gper.ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.beoi = parseFloat($scope.analysisDataList[percentLength1].ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.sroi = parseFloat($scope.analysisDataList[percentLength2].ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.aboi = parseFloat($scope.analysisDataList[percentLength3].ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.totaloi = parseFloat($scope.analysis.tailPurchase.ttaOntopPercentGain).toFixed(2);
            $scope.analysis.header.nm = parseFloat($scope.analysis.tailNetMargin.ttaOntopPercentGain).toFixed(2);

            //复制对象的属性
            for (var a = 1; a < $scope.analysisDataList.length; a++) {
                var destObject = $scope.analysisDataList[a];
                copyObjectAttribute($scope.analysis.header,destObject);
            }
            ////////////////////////////////数据构造结束///////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (synbol == 0) {//第一次加载
                //保存analysis构造数据
                $scope.reloadAnalysisSave($scope.analysisDataList);
            } else {//刷新数据操作
                //console.log(JSON.stringify($scope.analysisDataList));
                //console.log(JSON.stringify($scope.refreshAnalysisData));
                var beoiList = [];
                var sroiList = [];
                var aboiList = [];
                for (var i = 0; i < $scope.refreshAnalysisData.length; i++) {
                    var  refreshAnalysisData= $scope.refreshAnalysisData[i];

                    if (refreshAnalysisData.oiType == 'BEOI'){
                        beoiList.push(refreshAnalysisData);
                    } else if (refreshAnalysisData.oiType == 'SROI') {
                        sroiList.push(refreshAnalysisData);
                    } else if (refreshAnalysisData.oiType == 'ABOI') {
                        aboiList.push(refreshAnalysisData);
                    }
                    for (var j = 0; j < $scope.analysisDataList.length; j++) {
                        var initAnalysisRowData = $scope.analysisDataList[j];
                        if (refreshAnalysisData.anaysisId == initAnalysisRowData.anaysisId && refreshAnalysisData.proposalId == initAnalysisRowData.proposalId){
                            copyObjectVal(initAnalysisRowData,refreshAnalysisData);
                            break;
                        }
                    }
                }

                $scope.publicCountAnalysisVal($scope.refreshAnalysisData);
                $scope.countAnalysis($scope.refreshAnalysisData,beoiList,sroiList,aboiList);
                $scope.reloadAnalysisSave($scope.refreshAnalysisData,1);
            }

            $timeout(function () {
                //$scope.setTtaContractLine("nicescrolNewAlnlysisMain");
                $scope.setTtaAnalysis("nicescrolNewAlnlysisMain");
            }, 200);
        };

        /**
         * 复制对象的属性值
         * @param scource 源对象
         * @param target 目标对象
         */
        function copyObjectVal(scource,target) {
            var excludeKeyArray= ['oiType','dataType','anaysisId','orderNo','termsComparison','id','batchNumber','proposalId'];
            for (var key in scource) {
                if ((scource["oiType"] == 'freeGood' || scource["oiType"] == 'ABOI') && ( key == 'formerYearOntop' || key == 'currentYearOntop')) {
                    continue;
                }

                if (excludeKeyArray.indexOf(key) < 0) {//如果源对象的不在数组中,那么就更新目标对象的属性值
                    target[key] = scource[key];
                }
            }
        }

        /**
         * 复制对象属性
         */
        function copyObjectAttribute(src,dest) {
            var excludeArray = ['oiType','dataType','anaysisId','orderNo','termsComparison'];
            if (!angular.isObject(src)) {
                throw new Error("src为空对象");
            }

            if (!angular.isObject(dest)) {
                throw new Error("dest为空对象");
            }

            for (var destKey in src) {
                if (excludeArray.indexOf(destKey) < 0) {
                    dest[destKey] = src[destKey];
                }
            }
        }

        $scope.reloadAnalysisSave = function (analysisData,inputIndx) {
            if (analysisData.length <= 0) {
                SIEJS.alert("加载数据为空,加载失败", 'warning',"确定");
                return;
            }
            //保存构造数据
            httpServer.post(URLAPI.analysisLineReloadSaveAnalysis, {
                    'params': JSON.stringify({
                        analysisData:analysisData
                    })
                },
                function (res) {
                    if (res.status == 'S') {
                        //console.log(res);
                        if (inputIndx != undefined && inputIndx == 1) {
                            SIEJS.alert("刷新数据成功", 'success',"确定");
                        } else if (inputIndx != undefined && inputIndx == 2) {
                            SIEJS.alert("保存成功", 'success',"确定");
                        }
                        $scope.searchAnalysisData();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.initAnalysisParams = function() {
            //////////////////////////初始化参数 start
            $scope.dataTable="";
            $scope.gpdor= 0;
            $scope.gpper= 0;//2018
            $scope.gpdor2= 0;//GP$
            $scope.ActualGp= 0;

            $scope.ABOI1 = [];
            $scope.ABOI2 = [];
            $scope.BEOI1 = [];
            $scope.BEOI2 = [];
            $scope.SROI1 = [];
            $scope.SROI2 = [];

            $scope.ABOI ={};
            $scope.BABOI ={};
            $scope.ABOITail ={};


            $scope.gper = {};

            $scope.one= 0;//一般购货折扣
            $scope.pre= 0;//提前付款
            $scope.can= 0;//残损
            $scope.jizhong= 0;//集中收货
            $scope.cz= 0;//促销折扣
            $scope.shang= 0;//商业发展
            $scope.xuan= 0;//宣传牌

            $scope.gp2= "";
            $scope.one2= 0;//一般购货折扣
            $scope.pre2= 0;//提前付款
            $scope.can2= 0;//残损
            $scope.jizhong2= 0;//集中收货
            $scope.cz2= 0;//促销折扣
            $scope.shang2= 0;//商业发展
            $scope.xuan2= 0;//宣传牌

            //费用预估含税
            $scope.onefi= 0;//一般购货折扣
            $scope.prefi= 0;//提前付款
            $scope.canfi= 0;//残损
            $scope.jizhongfi= 0;//集中收货
            $scope.czfi= 0;//促销折扣
            $scope.shangfi= 0;//商业发展
            $scope.xuanfi= 0;//宣传牌

            $scope.onesum= 0;//一般购货折扣
            $scope.presum= 0;//提前付款
            $scope.cansum= 0;//残损
            $scope.jizhongsum= 0;//集中收货
            $scope.czsum= 0;//促销折扣
            $scope.shangsum= 0;//商业发展
            $scope.xuansum= 0;//宣传牌

            mm = 0;
            interval = 0;
            ABOIlineNUM = 0;
            oldYearABOILineNUM = 0;

            $scope.TotalBeoiA = 0;
            $scope.TotalBeoiAA = 0;
            $scope.TotalBeoiAAB = 0;
            $scope.TotalBeoiB = 0;
            $scope.TotalBeoiC = 0;
            $scope.TotalBeoiD = 0;
            $scope.TotalBeoiE = 0;

            $scope.TotalSroiA = 0;
            $scope.TotalSroiAA = 0;
            $scope.TotalSroiAB = 0;
            $scope.TotalSroiB = 0;
            $scope.TotalSroiC = 0;
            $scope.TotalSroiD = 0;
            $scope.TotalSroiE = 0;

            $scope.TotalAboiA = 0;
            $scope.TotalAboiAA = 0;
            $scope.TotalAboiAB = 0;
            $scope.TotalAboiB = 0;
            $scope.TotalAboiC = 0;
            $scope.TotalAboiD = 0;
            $scope.TotalAboiE = 0;

            //ae=n+u+ac
            $scope.TotalOiA = 0;
            $scope.TotalOiAA = 0;
            $scope.TotalOiAB = 0;
            $scope.TotalOiB = 0;
            $scope.TotalOiC = 0;
            $scope.TotalOiD = 0;
            $scope.TotalOiE = 0;

            //af=ae/c
            $scope.TotalOiPurchaseA = 0;
            $scope.TotalOiPurchaseAA = 0;
            $scope.TotalOiPurchaseAB = 0;
            $scope.TotalOiPurchaseB = 0;
            $scope.TotalOiPurchaseC = 0;
            $scope.TotalOiPurchaseD = 0;
            $scope.TotalOiPurchaseE = 0;

            //ag=af*(1-f)
            $scope.TotalOiSalesA = 0;
            $scope.TotalOiSalesAA = 0;
            $scope.TotalOiSalesAB = 0;
            $scope.TotalOiSalesB = 0;
            $scope.TotalOiSalesC = 0;
            $scope.TotalOiSalesD = 0;
            $scope.TotalOiSalesE = 0;

            //ah=f+ag
            $scope.NetMarginA = 0;
            $scope.NetMarginAA = 0;
            $scope.NetMarginAB = 0;
            $scope.NetMarginB = 0;
            $scope.NetMarginC = 0;
            $scope.NetMarginD = 0;
            $scope.NetMarginE = 0;
            //////////////////////////初始化参数 end
        };

        $scope.searchNewAnalysis = function () {
            $scope.initAnalysisParams();
            $scope.searchAnalysisData();
        };

        $scope.analysisHeader = {};
        $scope.analysisLineTopPart1 = [];
        $scope.analysisLineTopPart2 = [];
        $scope.analysisLineBeoi = [];
        $scope.analysisLineSroi = [];
        $scope.analysisLineAboi = [];
        $scope.analysisLineBottomPart = [];
        $scope.searchAnalysisData = function () {
            httpServer.post(URLAPI.analysisLineFind, {
                    'params': JSON.stringify({proposalId: getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.analysisTableData = res.data;
                        if ($scope.analysisTableData != null && $scope.analysisTableData.length == 0) {
                            console.log("#####################<<<<<<Analysis初始化数据>>>>>>#####################");
                            //0:第一次加载 1:点击刷新按钮
                            $scope.initAnalysisSearchOrReresh(0);
                        } else {
                            console.log("######################<<<<<Analysis查询数据>>>>>>>#####################");
                            //console.log($scope.analysisTableData);
                            $scope.analysisHeader = $scope.analysisTableData[0];
                            $scope.proposalNewYear = $scope.analysisHeader.proposalYear;
                            $scope.analysisLineTopPart1 = [];
                            $scope.analysisLineTopPart2 = [];
                            $scope.analysisLineBeoi = [];
                            $scope.analysisLineSroi = [];
                            $scope.analysisLineAboi = [];
                            $scope.analysisLineBottomPart = [];

                            var includeOiTypeArray = ['tailOI','tailPurchase','tailSales','tailNetMargin'];
                            for (var index = 0; index < $scope.analysisTableData.length; index++) {
                                var termsObject = $scope.analysisTableData[index];
                                if (index >= 1 && index <= 5) {
                                    $scope.analysisLineTopPart1.push(termsObject);
                                } else if(index >= 6 && index <= 7) {
                                    $scope.analysisLineTopPart2.push(termsObject);
                                } else if (termsObject.oiType == 'BEOI'){
                                    $scope.analysisLineBeoi.push(termsObject);
                                } else if (termsObject.oiType == 'SROI') {
                                    $scope.analysisLineSroi.push(termsObject);
                                } else if (termsObject.oiType == 'ABOI') {
                                    $scope.analysisLineAboi.push(termsObject);
                                } else if (includeOiTypeArray.indexOf(termsObject.oiType) >= 0 ) {
                                    $scope.analysisLineBottomPart.push(termsObject);
                                }
                            }

                            $timeout(function () {
                                $scope.setTtaAnalysis("nicescrolNewAlnlysisMain");
                            }, 200);
                            //console.log(JSON.stringify($scope.analysisTableData));
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

        //初始化查询analysis数据
        $scope.initAnalysisSearchOrReresh = function(synbol){
            httpServer.post(URLAPI.contractHeaderCallAnalysis, {
                    'params': JSON.stringify({proposalId: $scope.id})
                },
                function (res) {
                    if (res.status == 'E') {
                        SIEJS.alert(res.msg, "error", "确定");
                        return;
                    }
                    //console.log(JSON.stringify(res.data));
                    res = changeToNumber(res.data);
                    $scope.dataTable = res;
                    $scope.initNewAnalysisData(res,synbol);
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };

        $scope.saveAnalyisisByButton = function () {
            for (var i = 1; i < $scope.analysisTableData.length; i++) {
                var rowData = $scope.analysisTableData[i];
                rowData.purchaseRemark = $scope.analysisTableData[0].purchaseRemark;
                rowData.bicRemark = $scope.analysisTableData[0].bicRemark;
            }
            $scope.reloadAnalysisSave($scope.analysisTableData,2);
        };

        $scope.analysisRefresh = function() {
            $scope.initAnalysisParams();
            //0:第一次加载analysis数据,1:点击刷新按钮操作
            $scope.initAnalysisSearchOrReresh(1);
        };

        $scope.changeFreedGoodFormerYearOntop = function(row) {
            $scope.publicCountAnalysisVal($scope.analysisTableData);
        };

        $scope.publicCountAnalysisVal = function(dataList){
            var rowData2 = dataList[2];
            rowData2.formerYearTtaOntop = parseFloat(rowData2.formerYearTta) + parseFloat($scope.formatAmount(rowData2.formerYearOntop));//3 = 1 + 2
            rowData2.currentYearTtaOntop = parseFloat(rowData2.currentYearTta) + parseFloat($scope.formatAmount(rowData2.currentYearOntop));//7 = 5 + 6
            rowData2.ttaOntopIncreAmt = (parseFloat(rowData2.currentYearTtaOntop) - parseFloat(rowData2.formerYearTtaOntop)).toFixed(0);//8 = 7 - 3
            rowData2.ttaOntopPercentGain = rowData2.formerYearTtaOntop == 0 ? 0 : (parseFloat(rowData2.ttaOntopIncreAmt) * 100 / parseFloat(rowData2.formerYearTtaOntop)).toFixed(2);// 9 = 8 / 3
            rowData2.ttaOntopActIncreAmt = (parseFloat(rowData2.currentYearTtaOntop) - parseFloat(rowData2.formerYearActual)).toFixed(0);// 10 = 7 - 4
            rowData2.ttaOntopActPercentGain = rowData2.formerYearActual == 0 ? 0 : (parseFloat(rowData2.ttaOntopActIncreAmt) * 100 / parseFloat(rowData2.formerYearActual)).toFixed(2);// 11 = 10 / 4

            var rowData4 = dataList[4];
            var rowData7 = dataList[7];
            rowData7.currentYearOntop = rowData4.currentYearTta == 0 ? 0 : (parseFloat($scope.formatAmount(rowData2.currentYearOntop)) * 100 / parseFloat(rowData4.currentYearTta)).toFixed(2);//6
            rowData7.currentYearTtaOntop = (parseFloat(rowData7.currentYearTta) + parseFloat(rowData7.currentYearOntop)).toFixed(2); // 7 = 5 + 6
            rowData7.ttaOntopPercentGain = (parseFloat(rowData7.currentYearTtaOntop) - parseFloat(rowData7.formerYearTtaOntop)).toFixed(2);//9 9 = 7 - 3
            rowData7.ttaOntopActPercentGain = (parseFloat(rowData7.currentYearTtaOntop) - parseFloat(rowData7.formerYearActual)).toFixed(2);//11 11 = 7 - 4

            var lastRowData = dataList[dataList.length - 1];
            lastRowData.currentYearTtaOntop = (parseFloat(rowData7.currentYearTtaOntop) + parseFloat(dataList[dataList.length - 2].currentYearTtaOntop)).toFixed(2);//公式: ah = f + ag
            lastRowData.ttaOntopPercentGain = (parseFloat(lastRowData.currentYearTtaOntop) - parseFloat(lastRowData.formerYearTtaOntop)).toFixed(2);//9 = 7 - 3
            lastRowData.ttaOntopActPercentGain = (parseFloat(lastRowData.currentYearTtaOntop) - parseFloat(lastRowData.formerYearActual)).toFixed(2);// 11 = 7 - 4

            for (var rowIdx = 0; rowIdx < dataList.length; rowIdx++) {
                dataList[rowIdx].gp = parseFloat(dataList[7].ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].nm = parseFloat(dataList[dataList.length - 1].ttaOntopPercentGain).toFixed(2);
            }
        };

        $scope.countAnalysis = function (dataList,beoi,sroi,aboi) {
            var aboiFristCountLength = 8 + beoi.length + sroi.length - 1 + 1;//代表索引
            var aboiLastCountLength = aboiFristCountLength + aboi.length - 2 - 1;
            var sumAboiOntopVal1 = 0;
            var sumAboiOntopVal2 = 0;
            var sumAboiTTaOntopVal1 = 0;
            var sumAboiTTaOntopVal2 = 0;
            for (var j = aboiFristCountLength; j <= aboiLastCountLength; j++) {
                var aboiRowData = dataList[j];
                aboiRowData.formerYearTtaOntop = parseFloat(aboiRowData.formerYearTta) + parseFloat($scope.formatAmount(aboiRowData.formerYearOntop));//3 = 1 + 2
                aboiRowData.currentYearTtaOntop = parseFloat(aboiRowData.currentYearTta) + parseFloat($scope.formatAmount(aboiRowData.currentYearOntop));//7 = 5 + 6
                aboiRowData.ttaOntopIncreAmt = (parseFloat(aboiRowData.currentYearTtaOntop) - parseFloat(aboiRowData.formerYearTtaOntop)).toFixed(0);//8 = 7 - 3
                aboiRowData.ttaOntopPercentGain = aboiRowData.formerYearTtaOntop == 0 ? 0 : (parseFloat(aboiRowData.ttaOntopIncreAmt) * 100 / parseFloat(aboiRowData.formerYearTtaOntop)).toFixed(2);// 9 = 8 / 3
                aboiRowData.ttaOntopActIncreAmt = (parseFloat(aboiRowData.currentYearTtaOntop) - parseFloat(aboiRowData.formerYearActual)).toFixed(0);// 10 = 7 - 4
                aboiRowData.ttaOntopActPercentGain = aboiRowData.formerYearActual == 0 ? 0 : (parseFloat(aboiRowData.ttaOntopActIncreAmt) * 100 / parseFloat(aboiRowData.formerYearActual)).toFixed(2);// 11 = 10 / 4
                sumAboiOntopVal1 = parseFloat(sumAboiOntopVal1) + parseFloat($scope.formatAmount(aboiRowData.formerYearOntop));
                sumAboiTTaOntopVal1 = parseFloat(sumAboiTTaOntopVal1) + parseFloat(aboiRowData.formerYearTtaOntop);

                sumAboiOntopVal2 = parseFloat(sumAboiOntopVal2) + parseFloat($scope.formatAmount(aboiRowData.currentYearOntop));
                sumAboiTTaOntopVal2 = parseFloat(sumAboiTTaOntopVal2) + parseFloat(aboiRowData.currentYearTtaOntop);
            }
            var beoiTotalLength = 8 + beoi.length - 1 - 1;
            var sroiTotalLength = 8 + beoi.length + sroi.length - 1 - 1;
            var aboiTotalLength = 8 + beoi.length + sroi.length + aboi.length - 1 - 1;

            //aboi的汇总行($) 倒数第二行
            var aboiLastRowData = dataList[aboiTotalLength];
            aboiLastRowData.formerYearOntop = parseFloat(sumAboiOntopVal1).toFixed(0);
            aboiLastRowData.formerYearTtaOntop = parseFloat(sumAboiTTaOntopVal1).toFixed(0);
            aboiLastRowData.currentYearOntop = parseFloat(sumAboiOntopVal2).toFixed(0);
            aboiLastRowData.currentYearTtaOntop =  parseFloat(sumAboiTTaOntopVal2).toFixed(0);
            aboiLastRowData.ttaOntopIncreAmt = (parseFloat(aboiLastRowData.currentYearTtaOntop) - parseFloat(aboiLastRowData.formerYearTtaOntop)).toFixed(0); // 8 = 7 - 3
            aboiLastRowData.ttaOntopPercentGain = aboiLastRowData.formerYearTtaOntop == 0 ? 0 : (parseFloat(aboiLastRowData.ttaOntopIncreAmt) * 100 / parseFloat(aboiLastRowData.formerYearTtaOntop)).toFixed(2); //9 = 8 / 3
            aboiLastRowData.ttaOntopActIncreAmt = (parseFloat(aboiLastRowData.currentYearTtaOntop) - parseFloat(aboiLastRowData.formerYearActual)).toFixed(0);//10 = 7 - 4
            aboiLastRowData.ttaOntopActPercentGain = aboiLastRowData.formerYearActual == 0 ? 0 : (parseFloat(aboiLastRowData.ttaOntopActIncreAmt) * 100 / parseFloat(aboiLastRowData.formerYearActual)).toFixed(2);// 11 = 10 / 4

            //aboi的汇总百分比行(%) 倒数第-行
            var aboiLastPercentRowData = dataList[aboiTotalLength + 1];
            aboiLastPercentRowData.formerYearOntop = dataList[3].formerYearTta == 0 ? 0 : (parseFloat(aboiLastRowData.formerYearOntop) * 100 / parseFloat(dataList[3].formerYearTta)).toFixed(2);
            aboiLastPercentRowData.formerYearTtaOntop = dataList[3].formerYearTtaOntop == 0 ? 0 : (parseFloat(aboiLastRowData.formerYearTtaOntop) * 100 / parseFloat(dataList[3].formerYearTtaOntop)).toFixed(2);
            aboiLastPercentRowData.currentYearOntop = dataList[3].currentYearTta == 0 ? 0 : (parseFloat(aboiLastRowData.currentYearOntop) * 100 / parseFloat(dataList[3].currentYearTta)).toFixed(2);
            aboiLastPercentRowData.currentYearTtaOntop = dataList[3].currentYearTtaOntop == 0 ? 0 : (parseFloat(aboiLastRowData.currentYearTtaOntop) * 100 / parseFloat(dataList[3].currentYearTtaOntop)).toFixed(2);
            aboiLastPercentRowData.ttaOntopPercentGain = (parseFloat(aboiLastPercentRowData.currentYearTtaOntop) - parseFloat(aboiLastPercentRowData.formerYearTtaOntop)).toFixed(2);
            aboiLastPercentRowData.ttaOntopActPercentGain = (parseFloat(aboiLastPercentRowData.currentYearTtaOntop) - parseFloat(aboiLastPercentRowData.formerYearActual)).toFixed(2);

            var beoiLastRowData = dataList[beoiTotalLength];//beoi类型的汇总行
            var sroiLastRowData = dataList[sroiTotalLength];//sroi类型的汇总行

            var lastRowIdx = dataList.length - 1;//最后一行的索引
            var tailOiRowData = dataList[lastRowIdx - 3];//倒数第四行
            tailOiRowData.formerYearOntop = (parseFloat(beoiLastRowData.formerYearOntop) + parseFloat(sroiLastRowData.formerYearOntop) + parseFloat(aboiLastRowData.formerYearOntop)).toFixed(0);
            tailOiRowData.formerYearTtaOntop = (parseFloat(beoiLastRowData.formerYearTtaOntop) + parseFloat(sroiLastRowData.formerYearTtaOntop) + parseFloat(aboiLastRowData.formerYearTtaOntop)).toFixed(0);
            tailOiRowData.currentYearOntop = (parseFloat(beoiLastRowData.currentYearOntop) + parseFloat(sroiLastRowData.currentYearOntop) + parseFloat(aboiLastRowData.currentYearOntop)).toFixed(0);
            tailOiRowData.currentYearTtaOntop = (parseFloat(beoiLastRowData.currentYearTtaOntop) + parseFloat(sroiLastRowData.currentYearTtaOntop) + parseFloat(aboiLastRowData.currentYearTtaOntop)).toFixed(0);
            tailOiRowData.ttaOntopIncreAmt = (parseFloat(tailOiRowData.currentYearTtaOntop) - parseFloat(tailOiRowData.formerYearTtaOntop)).toFixed(0);
            tailOiRowData.ttaOntopPercentGain = tailOiRowData.formerYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.ttaOntopIncreAmt) * 100 / parseFloat(tailOiRowData.formerYearTtaOntop)).toFixed(2);
            tailOiRowData.ttaOntopActIncreAmt = (parseFloat(tailOiRowData.currentYearTtaOntop) - parseFloat(tailOiRowData.formerYearActual)).toFixed(0);
            tailOiRowData.ttaOntopActPercentGain = tailOiRowData.formerYearActual == 0 ? 0 : (parseFloat(tailOiRowData.ttaOntopActIncreAmt) * 100 / parseFloat(tailOiRowData.formerYearActual)).toFixed(2);

            var tailPurchaseRowData = dataList[lastRowIdx - 2];//倒数第三行
            tailPurchaseRowData.formerYearOntop = dataList[3].formerYearTta == 0 ? 0 : (parseFloat(tailOiRowData.formerYearOntop) * 100 / parseFloat(dataList[3].formerYearTta)).toFixed(2);
            tailPurchaseRowData.formerYearTtaOntop = dataList[3].formerYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.formerYearTtaOntop) * 100 / parseFloat(dataList[3].formerYearTtaOntop)).toFixed(2);
            tailPurchaseRowData.currentYearOntop = dataList[3].currentYearTta == 0 ? 0 : (parseFloat(tailOiRowData.currentYearOntop) * 100 / parseFloat(dataList[3].currentYearTta)).toFixed(2);
            tailPurchaseRowData.currentYearTtaOntop = dataList[3].currentYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.currentYearTtaOntop) * 100 / parseFloat(dataList[3].currentYearTtaOntop)).toFixed(2);
            tailPurchaseRowData.ttaOntopPercentGain = (parseFloat(tailPurchaseRowData.currentYearTtaOntop) - parseFloat(tailPurchaseRowData.formerYearTtaOntop)).toFixed(2);
            tailPurchaseRowData.ttaOntopActPercentGain = (parseFloat(tailPurchaseRowData.currentYearTtaOntop) - parseFloat(tailPurchaseRowData.formerYearActual)).toFixed(2);

            var tailSalesRowData = dataList[lastRowIdx - 1];//倒数第二行
            tailSalesRowData.formerYearOntop = dataList[4].formerYearTta == 0 ? 0 : (parseFloat(tailOiRowData.formerYearOntop) * 100 / parseFloat(dataList[4].formerYearTta)).toFixed(2);
            tailSalesRowData.formerYearTtaOntop = dataList[4].formerYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.formerYearTtaOntop) * 100 / parseFloat(dataList[4].formerYearTtaOntop)).toFixed(2);
            tailSalesRowData.currentYearOntop = dataList[4].currentYearOntop == 0 ? 0 : (parseFloat(tailOiRowData.currentYearOntop) * 100 / parseFloat(dataList[4].currentYearOntop)).toFixed(2);
            tailSalesRowData.currentYearTtaOntop = dataList[4].currentYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.currentYearTtaOntop) * 100 / parseFloat(dataList[4].currentYearTtaOntop)).toFixed(2);
            tailSalesRowData.ttaOntopPercentGain = (parseFloat(tailSalesRowData.currentYearTtaOntop) - parseFloat(tailSalesRowData.formerYearTtaOntop)).toFixed(2);
            tailSalesRowData.ttaOntopActPercentGain = (parseFloat(tailSalesRowData.currentYearTtaOntop) - parseFloat(tailSalesRowData.formerYearActual)).toFixed(2);

            var tailNetMarginRowData = dataList[lastRowIdx];//倒数第一行
            tailNetMarginRowData.formerYearOntop = (parseFloat(dataList[7].formerYearOntop) + parseFloat(tailSalesRowData.formerYearOntop)).toFixed(2);
            tailNetMarginRowData.formerYearTtaOntop = (parseFloat(dataList[7].formerYearTtaOntop) + parseFloat(tailSalesRowData.formerYearTtaOntop)).toFixed(2);
            tailNetMarginRowData.currentYearOntop = (parseFloat(dataList[7].currentYearOntop) + parseFloat(tailSalesRowData.currentYearOntop)).toFixed(2);
            tailNetMarginRowData.currentYearTtaOntop = (parseFloat(dataList[7].currentYearTtaOntop) + parseFloat(tailSalesRowData.currentYearTtaOntop)).toFixed(2);
            tailNetMarginRowData.ttaOntopPercentGain = (parseFloat(tailNetMarginRowData.currentYearTtaOntop) - parseFloat(tailNetMarginRowData.formerYearTtaOntop)).toFixed(2);// 9 = 7 - 3
            tailNetMarginRowData.ttaOntopActPercentGain = (parseFloat(tailNetMarginRowData.currentYearTtaOntop) - parseFloat(tailNetMarginRowData.formerYearActual)).toFixed(2);// 11 = 7 - 4;

            //设置数据到头部分
            for (var rowIdx = 0; rowIdx < dataList.length; rowIdx++) {
                dataList[rowIdx].gp = parseFloat(dataList[7].ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].beoi = parseFloat(dataList[beoiTotalLength + 1].ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].sroi = parseFloat(dataList[sroiTotalLength + 1].ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].aboi = parseFloat(dataList[aboiTotalLength + 1].ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].totaloi = parseFloat(tailPurchaseRowData.ttaOntopPercentGain).toFixed(2);
                dataList[rowIdx].nm = parseFloat(tailNetMarginRowData.ttaOntopPercentGain).toFixed(2);
            }
        };

        $scope.changeAboiOntopEvent = function(aboiRow) {
            $scope.countAnalysis($scope.analysisTableData,$scope.analysisLineBeoi,$scope.analysisLineSroi,$scope.analysisLineAboi);
        };


///////////////////////////// analysis 改造结束////////////////////////////////////////////////////////////////////////
        //isEqual：判断两个对象是否键值对应相等
        function isEqual(a,b){
            //如果a和b本来就全等
            if(a===b){
                //判断是否为0和-0
                return a !== 0 || 1/a ===1/b;
            }
            //判断是否为null和undefined
            if(a==null||b==null){
                return a===b;
            }
            //接下来判断a和b的数据类型
            var classNameA=toString.call(a),
                classNameB=toString.call(b);
            //如果数据类型不相等，则返回false
            if(classNameA !== classNameB){
                return false;
            }
            //如果数据类型相等，再根据不同数据类型分别判断
            switch(classNameA){
                case '[object RegExp]':
                case '[object String]':
                    //进行字符串转换比较
                    return '' + a ==='' + b;
                case '[object Number]':
                    //进行数字转换比较,判断是否为NaN
                    if(+a !== +a){
                        return +b !== +b;
                    }
                    //判断是否为0或-0
                    return +a === 0?1/ +a === 1/b : +a === +b;
                case '[object Date]':
                case '[object Boolean]':
                    return +a === +b;
            }
            //如果是对象类型
            if(classNameA == '[object Object]'){
                //获取a和b的属性长度
                var propsA = Object.getOwnPropertyNames(a),
                    propsB = Object.getOwnPropertyNames(b);
                if(propsA.length != propsB.length){
                    return false;
                }
                for(var i=0;i<propsA.length;i++){
                    var propName=propsA[i];
                    //如果对应属性对应值不相等，则返回false
                    if(a[propName] !== b[propName]){
                        return false;
                    }
                }
                return true;
            }
            //如果是数组类型
            if(classNameA == '[object Array]'){
                if(a.toString() == b.toString()){
                    return true;
                }
                return false;
            }
        }


        //新增
        $scope.daoru = function () {
            $("#fileUpLoad").val('');
            $('#excelUp').modal('toggle');
        };

        //上传附件
        $scope.uploadSave = function (invalid) {
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
            // fd.append('sideAgrtHId', id);
            fd.append('file', file);
            fd.append("proposalId", getId());
            $http.post(URLAPI.importNewProductList, fd, {
                transformRequest: angular.identity,
                headers: {
                    'Certificate': sessionStorage[appName + '_certificate'] || localStorage[appName + '_certificate'] || 'nothing',
                    'Content-Type': undefined
                }
            }).success(function (response) {
                $('#excelUp').modal('toggle');
                $scope.searchQuestionDataTable(getId());
                SIEJS.alert(response.msg, 'success', '确定');
            }).error(function(response) {
                SIEJS.alert("导入失败!", 'error', '确定');
            });
        };

        if (getId()) {
            $scope.searchA();
        }

        /**
         $scope.search();
         $scope.searchBrandPlnH();
         $scope.searchTerms();
         $scope.searchNBExtent();
         $scope.ttaContractLine();
         $scope.ttaDeptFeeReport();
         $scope.searchA();
         */

        window.onresize = function(){
            console.log("窗口大小被改变");
            $scope.AnalaysisNiceOpction = "{cursoropacitymin:1,railvalign: 'bottom',cursorcolor:'#1e90c2',autohidemode:false,cursorwidth:"+(20/window.devicePixelRatio)+",zindex:50}";
            $scope.setTtaAnalysis("nicescrolNewAlnlysisMain");
        }


    });
});



