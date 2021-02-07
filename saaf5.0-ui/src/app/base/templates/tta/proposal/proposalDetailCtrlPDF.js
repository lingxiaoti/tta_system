/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app','jqueryUi','layui','multiple'], function (app,jqueryUi,layui,multiple) {
    app.controller('proposalDetailCtrlPDF', function ($http, $scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, setNewRow,$timeout ,lookupCode,$compile,tableXlsExport) {
        $scope.fileDataParams = {businessId: $stateParams.id, functionId: "tta_proposal_header"};
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.printPDFFlag = false;
        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        var d = new Date();
        d.setMonth(d.getMonth() + 2);
        $scope.nowDateParam = d.getFullYear() + '-' + (d.getMonth() + 1);
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
        $scope.ttaDeptFeeReportTableExcel = [];
        $scope.ttaDeptFeeReportHeadExcel ={};
        $scope.analysisTableData = [];
        $scope.urlParams = urlToObj(location.hash);
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
        // layui.use('table'); // 按需加载layui模块


        //获取字典信息
        var numss = 1;

        $scope.fileDataTableLoad = function () {
                $scope.printNum -= 1;
            console.log( '9A' + ((new Date()) - baseDate));
        };
       // terms  End  校验 *******************************************************************************************


        /**********************************工作流配置  start**************************************/

        //获取url参数对象


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
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                }
            );

        }

        /**********************************工作流配置   end**************************************/
        $scope.printNum = 11;
        var printNumBeginDate =new Date();
        var unPrintNum = $scope.$watch('printNum',function (newValue,oldValue) {
            if (newValue === 0) {
                $scope.printPDFFlag = true;
                console.log((new Date()) - printNumBeginDate) ;
                unPrintNum();
            }
        });


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
        };


        //查询单据信息品牌计划
        $scope.searchBrandPlnH = function () {

            httpServer.post(URLAPI.brandplnHFindById, {
                    'params': JSON.stringify({proposalId: $scope.id})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.brandparams = res.data;
                        $scope.iniData($scope.brandparams.yearCode);
                        console.log( '101A' + ((new Date()) - baseDate));
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
                    'params': JSON.stringify({proposalId: $scope.id})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.rpTable = res.data;
                            $scope.printNum -= 1;
                        console.log( 'A' + ((new Date()) - baseDate));
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
                    'params': JSON.stringify({proposalId: $scope.id})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.brandPlnLDataTable = res.data;
                            $scope.printNum -= 1;
                        console.log( '1A' + ((new Date()) - baseDate));
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
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

        //营改增加成比例
        $scope.salesUpScaleBlurFunc = function (value){
            var reg = /^[0-6]$/;
            if(!reg.test($scope.contractParams.salesUpScale)){
                jQuery("#contractParams_salesUpScale").tip('请输入邮箱或手机号');
            }
        };
        /***************************查询  TTA TERMS end ****************/






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
            });
                $scope.printNum -= 1;
            console.log( '2A' + ((new Date()) - baseDate));
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
                } else {
                    SIEJS.alert("加载答卷失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
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
        $scope.submitSurvey=function(){
            var flag = true;
            angular.forEach($scope.saveData.SurveyResultData,function (item,index) {
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
                    proposalId: $scope.id
                    //  注释 两个参数
                    //year: $scope.params.proposalYear,
                    //vendorNbr: $scope.params.vendorNbr
                })
            }, function (res) {
                if (res.status == 'S') {
                    $scope.termsComparisionPartList1 = res.data.partList1;
                    $scope.termsComparisionPartList2 = res.data.partList2;
                    $scope.termsComparisionPartList3 = res.data.partList3;
                        $scope.printNum -= 1;
                    console.log( '3A' + ((new Date()) - baseDate));
                } else {
                    SIEJS.alert("加载TTA Terms Comparision(TY vs LY)失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
            });
        };

        //查找terms头部信息
        $scope.queryTradeTermsHeader = function() {
            httpServer.post(URLAPI.termsHFindById, {
                    'params': JSON.stringify({proposalId: getId()})
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.contractParams = res.data;
                        var dicMap = res.data.dicMap;
                        //TERMS_TARGET
                        $scope.proposalNewYearRemark = dicMap.proposalNewYearRemark ? dicMap.proposalNewYearRemark : '注：对于同一产品品牌或数个产品品牌同属于一个公司或数个关联公司或同一集团而产品在不同销售区由不同经销商代理的情况，目标退佣按当年度屈臣氏向全国全部销售区域的各供应商采购的（折扣前）采购额总额确定对应的佣金比例，并分别按屈臣氏向各供应商采购的（折扣前）采购额确定该供应商的目标退佣金额。目标退佣的含税金额 = （不含税采购额 x 退佣比例）x （1 + 法定适用税率）';
                        //PARTY_A_COMPANY
                        $scope.companyName = dicMap.companyName ? dicMap.companyName : '武汉屈臣氏个人用品商店有限公司';
                        //TRAD_TERMS_CONTENT
                        $scope.termRemark = dicMap.termRemark ? dicMap.termRemark : '注:上述采购额，如未特别注明，为（折扣前）含税采购额。（折扣前）采购额是指按（折扣前）原价计算的采购金额，"（折扣前）原价是指扣除供应商特别给予屈臣氏的折扣前原价，折扣是指本《贸易条款》"上列明的折扣折让项目。';

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
                                    $scope.printNum -= 1;
                                console.log( '4A' + ((new Date()) - baseDate));
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
                                    $scope.printNum -= 1;
                                console.log( '5A' + ((new Date()) - baseDate));
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
            //贸易条款头部信息
            $scope.queryTradeTermsHeader();
            httpServer.post(URLAPI.findTermsAgrement, {
                params: JSON.stringify({
                    proposalId: getId()
                     //1.注释年度信息
                    //year: $scope.params.proposalYear,
                })
            }, function (res) {
                if (res.status == 'S') {
                    var tradeYear = res.data.tradeYear;
                    var saleWayArr = res.data.saleWayList; //销售方式 $scope.saleWayList
                    $scope.params.saleType = res.data.saleType;
                    $scope.params.proposalYear = res.data.tradeYear;//pdf打印的时候，年费无法获取

                    /*
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
                            // 2.返回年度信息$scope.params.proposalYear
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
                    */

                    $("#divSaleWay").html("");
                    if ($scope.saleWayList) {
                        var divSaleWayHtml = "";
                        for (var idx = 0; idx < saleWayArr.length; idx ++) {
                            //3.$scope.params.saleType 返回改字段
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
                        $scope.printNum -= 1;
                    console.log( '6A' + ((new Date()) - baseDate));
                } else {
                    SIEJS.alert("加载贸易条款失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
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


        $scope.searchQuestionDataTable = function (proposalId) {
            httpServer.post(URLAPI.queryQuestionNewMapDetailList,
                {'params': JSON.stringify({proposalId: parseInt(proposalId)})} ,
                function (res) {
                    if (res.status == 'S') {
                        $scope.questionDataTable = res.data;
                            $scope.printNum -= 1;
                        console.log( '7A' + ((new Date()) - baseDate));
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };




  /***************************查询问卷信息end****************/

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

        /***************************TT 贸易之协议补充 start****************/
        $scope.ttaDeptFeeReport = function (){
            httpServer.post(URLAPI.ttaDeptFeeLFindReport, {
                params: JSON.stringify({proposalId: getId()})
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ttaDeptFeeReportTable = res.data.ttaDeptFeeReportTable;
                    $scope.ttaDeptFeeReportHead = res.data.head ;

                    $scope.ttaDeptFeeReportTableExcel = res.data.ttaDeptFeeReportTable;
                    $scope.ttaDeptFeeReportHeadExcel = res.data.head;
                    //取出甲方

                    var values = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == "PARTY_A_COMPANY";
                    });
                    $scope.ttaDeptFeeReportHead.partyA =  values[0].meaning;
                        $scope.printNum -= 1;
                    console.log( '8A' + ((new Date()) - baseDate));
                } else {
                    SIEJS.alert("加载贸易协议:" + res.msg, "error", "确定");
                }
            }, function (err) {
            });
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

        /***************************TT 贸易之协议补充 end****************/
        //////////////////////////// analysis 改造开始//////////////////////////////////////////////////////////////////////////
        $scope.analysisHeader = {};
        $scope.analysisLineTopPart1 = [];
        $scope.analysisLineTopPart2 = [];
        $scope.analysisLineBeoi = [];
        $scope.analysisLineSroi = [];
        $scope.analysisLineAboi = [];
        $scope.analysisLineBottomPart = [];
        $scope.proposalNewYear = "";
        $scope.searchAnalysisData = function () {
            httpServer.post(URLAPI.analysisLineFind, {
                    'params': JSON.stringify({proposalId: getId()})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.analysisTableData = res.data;
                        if ($scope.analysisTableData.length > 0) {
                            $scope.analysisHeader = $scope.analysisTableData[0];
                        }
                        var mydate = new Date();
                        var proposalYear = mydate.getFullYear();
                        $scope.proposalNewYear = $scope.analysisHeader.proposalYear ? $scope.analysisHeader.proposalYear : proposalYear;
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
                            $scope.setTtaContractLine("nicescrolNewAlnlysisMain");
                        }, 200);
                        $scope.printNum -= 1;
                        console.log( '1234A' + ((new Date()) - baseDate));
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        };
    ///////////////////////////// analysis 改造结束////////////////////////////////////////////////////////////////////////
        $scope.iniData = function (yearCode) {
            var year = parseInt(yearCode);
            $scope.yearInfo.year =  year;
            $scope.yearInfo.lastYear =  year-1;
            $scope.yearInfo.beforeYear =  year-2;

        };


        var baseDate = new Date();
        //id不为空
        // if (getId()) {
        //     //问卷调查表
        //     //贸易条款
        //     $scope.tradeClause();
        //     $scope.searchQuestionDataTable($scope.id);
        //     $scope.searchAnalysisData();
        //     $scope.searchQuestionnaire();
        //     $scope.termsComparision();
        //
        //     //贸易协议
        //     $scope.ttaDeptFeeReport();
        //     $scope.searchBrandPlnH();
        //     $scope.searchBrandPlnL();
        //     $scope.searchRpTable();
        //
        // }

        //选择业务组
        $scope.getRelGroupCode = function () {
            $scope.deptProject.defaultDeptId = userLoginInfo.deptId;//默认当前登录人的部门id;
            $scope.brandPlnLineLov.search();
            $('#brandPlnLineLov').modal('toggle')
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
        $scope.lookupCodeParty = $rootScope.lookupCode;
        if ($scope.lookupCodeParty && $scope.lookupCodeParty.length > 0) {
            numss = numss + 1;
            if (getId()) {
                //问卷调查表
                //贸易条款
                $scope.tradeClause();
                $scope.searchQuestionDataTable($scope.id);
                $scope.searchAnalysisData();
                $scope.searchQuestionnaire();
                $scope.termsComparision();

                //贸易协议
                $scope.ttaDeptFeeReport();
                $scope.searchBrandPlnH();
                $scope.searchBrandPlnL();
                $scope.searchRpTable();

            }
        }

        $scope.$watch('lookupCodeParty', function (newVal, oldVal) {
            if (!newVal && newVal.length > 0 && numss == 1) {
                numss = numss + 1;
                if (getId()) {
                    //问卷调查表
                    //贸易条款
                    $scope.tradeClause();
                    $scope.searchQuestionDataTable($scope.id);
                    $scope.searchAnalysisData();
                    $scope.searchQuestionnaire();
                    $scope.termsComparision();

                    //贸易协议
                    $scope.ttaDeptFeeReport();
                    $scope.searchBrandPlnH();
                    $scope.searchBrandPlnL();
                    $scope.searchRpTable();

                }
            }
        }, true);
        //ttaTermAnalysis+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    });
});



