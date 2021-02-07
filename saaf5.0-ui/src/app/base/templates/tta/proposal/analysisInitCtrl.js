/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app','jqueryUi','layui','multiple'], function (app,jqueryUi,layui,multiple) {
    app.controller('analysisInitCtrl', function ($http, $scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, setNewRow,$timeout ,lookupCode,$compile,tableXlsExport) {
    // layui.use('table'); // 按需加载layui模块

        $scope.header = {};
        $scope.analysisData = {};
        $scope.oldAnalysisData = {};
        $scope.mid = {};
        $scope.tail = {};
        $scope.adata = {};
        $scope.datanum = 0;
        $scope.proposalNewYear = "";
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



        $scope.fileDataParams = {businessId: $stateParams.id, functionId: "tta_proposal_header"};
        $scope.userData = JSON.parse(localStorage.getItem(appName + '_successLoginInfo'));
        $scope.printPDFFlag = false;
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
        $scope.fileDataTableLoad = function () {
                $scope.printNum -= 1;
            console.log( '9A' + ((new Date()) - baseDate));
        };
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
        $scope.ttaDeptFeeReportTableExcel = [];
        $scope.ttaDeptFeeReportHeadExcel ={};
        $scope.analysisTableData = [];

        /**********************************工作流配置  start**************************************/

        //获取url参数对象
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
        $scope.printNum = 1;
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
            if(isNaN(data.contractLineList2019[0].feeIntas)){
                data.contractLineList2019[0].feeIntas = 0;
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
                versionCode:1,//默认版本为1
                purchaseRemark: data.analysisData.length != 0 ? data.analysisData[0].purchaseRemark : data.remarkResult.purchaseRemark,//这个需要修改为存储analysis数据的表取数 2020.3.23 号修改
                bicRemark: data.analysisData.length != 0 ? data.analysisData[0].bicRemark : data.remarkResult.bicRemark,//需要修改 2020.3.23修改
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
            $scope.mid.PurchaseA = isNaN(data.contractLineList2018[0].purchase) ? 0 : data.contractLineList2018[0].purchase;//1 tta
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
            $scope.analysis.purchase.formerYearTta = isNaN(data.contractLineList2018[0].purchase) ? 0 : (data.contractLineList2018[0].purchase).toFixed(0);//1
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
            $scope.mid.SalseA = isNaN(data.contractLineList2018[0].sales) ? 0 : data.contractLineList2018[0].sales;//1 tta
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
            $scope.analysis.sales.formerYearTta = isNaN(data.contractLineList2018[0].sales) ? 0 :(data.contractLineList2018[0].sales).toFixed(0);//1 tta
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
            $scope.oldProposalYearPurchase = $scope.beoiPurchaseA = isNaN($scope.dataTable.contractLineList2018[0].purchase)?0:$scope.dataTable.contractLineList2018[0].purchase;
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

            if($scope.analysis.header.contractVersion == "B"){//合同版本为B
                $scope.TotalBeoiA = 0;
                $scope.TotalBeoiC = 0;
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
            $scope.TotalBeoiE = (parseFloat($scope.TotalBeoiE)).toFixed(0);//tta 5

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
                if($scope.analysis.header.tradingMode.indexOf("returnable") >= 0){//tta
                    $scope.gper.gp1= (parseFloat(data.hterm2018.gp)).toFixed(2);//取TTA TERMS上的GP值
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment(general)") >= 0 && $scope.analysis.header.contractVersion == "A"){
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment(general)") >= 0 && $scope.analysis.header.contractVersion == "B"){
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment Via") >= 0 && $scope.analysis.header.contractVersion == "A"){
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                } else if($scope.analysis.header.tradingMode.indexOf("Consignment Via") >= 0 && $scope.analysis.header.contractVersion == "B"){
                    $scope.gper.gp1= (data.hterm2018.consignmentDiscount + ($scope.one + $scope.pre + $scope.can + $scope.jizhong + $scope.cz) * (1 - data.hterm2018.consignmentDiscount / 100) - 2 * (1 - data.hterm2018.consignmentDiscount / 100)).toFixed(2);
                }
            }
            $scope.gper.gp2 = 0.00;//on top
            $scope.gper.gp3 =parseFloat(parseFloat($scope.gper.gp1) + parseFloat($scope.gper.gp2)).toFixed(2);//tta + on top
            $scope.gper.gp4 = (parseFloat(data.gp)).toFixed(2);//actual
            //判断是新供应商还是续签供应商
            if ($scope.analysis.header.newOrExisting == "N") {//tta
                if($scope.analysis.header.tradingMode.indexOf("returnable") >=0 && $scope.analysis.header.contractVersion == "A"){//条件:  可退货购销 + 合同版本A + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp - 2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("returnable") >=0 && $scope.analysis.header.contractVersion == "B") {//条件:可退货购销 + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.gp + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.gp / 100)-
                        2 * (1 - data.hterm2019.gp / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("经仓") >=0 && $scope.analysis.header.contractVersion == "A") {//条件:寄售经仓 + 合同版本A + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount +
                        ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("经仓") >= 0 && $scope.analysis.header.contractVersion == "B") {//条件:寄售经仓 + 合同版本B + 新供应商
                    $scope.gper.gp5 = (data.hterm2019.consignmentDiscount + ($scope.one2 + $scope.pre2 + $scope.can2 + $scope.jizhong2 + $scope.cz2) * (1 - data.hterm2019.consignmentDiscount / 100)-
                        2 * (1 - data.hterm2019.consignmentDiscount / 100)).toFixed(2);

                } else if ($scope.analysis.header.tradingMode.indexOf("常规") >= 0 && $scope.analysis.header.contractVersion == "A") {//条件:寄售(常规) + 合同版本A + 新供应商
                    $scope.gper.gp5 = data.hterm2019.consignmentDiscount;

                } else if ($scope.analysis.header.tradingMode.indexOf("常规") >= 0 && $scope.analysis.header.contractVersion == "B") {//条件:寄售(常规) + 合同版本B + 新供应商
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
            $scope.analysis.gp.formerYearTta = parseFloat((parseFloat(data.contractLineList2018[0].sales) * parseFloat($scope.analysis.gper.formerYearTta)/ 100).toFixed(0));//1 tta
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
                beoiObject.formerYearTta = $scope.dataTable.contractLineList2018[0].purchase == 0 ? 0.00 : (parseFloat(rowData1.feeIntas) * 100 / parseFloat($scope.dataTable.contractLineList2018[0].purchase)).toFixed(2);//1
                beoiObject.formerYearOntop = 0.00;//2
                beoiObject.formerYearTtaOntop = (parseFloat(beoiObject.formerYearTta) + parseFloat(beoiObject.formerYearOntop)).toFixed(2);//3
                beoiObject.formerYearActual = $scope.analysis.purchase.formerYearActual == 0 ? 0.00 : (parseFloat(rowData1.sumMoney) * 100 / parseFloat($scope.analysis.purchase.formerYearActual)).toFixed(2);//4
                beoiObject.currentYearTta = $scope.adata.fpurchase == 0 ? 0.00 : (parseFloat(rowData2.feeIntas) * 100 / parseFloat($scope.adata.fpurchase / 1000)).toFixed(2);//5
                beoiObject.currentYearOntop = 0.00;//6
                beoiObject.currentYearTtaOntop = (parseFloat(beoiObject.currentYearTta) + parseFloat(beoiObject.currentYearOntop)).toFixed(2);//7
                beoiObject.ttaOntopIncreAmt = 0.00;// n/a 8
                var a = isNaN($scope.adata.fpurchase / 1000) ? 0 : $scope.adata.fpurchase/1000;
                var b = isNaN($scope.dataTable.contractLineList2018[0].purchase) ? 0 : $scope.dataTable.contractLineList2018[0].purchase;
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
            $scope.analysis.tailSales.formerYearOntop = $scope.analysis.sales.formerYearOntop == 0 ? 0 : (parseFloat($scope.analysis.tailOI.formerYearOntop) * 100 / parseFloat($scope.analysis.sales.formerYearOntop)).toFixed(2);//2 "-
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
            $scope.analysis.tailNetMargin.formerYearOntop = (parseFloat($scope.analysis.gper.formerYearTta) + parseFloat($scope.analysis.tailSales.formerYearOntop)).toFixed(2);//2
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
                $scope.setTtaContractLine("nicescrolNewAlnlysisMain");
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

        /*$scope.searchNewAnalysis = function () {
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
            $scope.searchAnalysisData();
        }*/

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
                data.contractLineList2018[i].feeIntas = (parseFloat(data.contractLineList2018[i].feeIntas)/1000);
                data.contractLineList2018[i].feeNotax = (parseFloat(data.contractLineList2018[i].feeNotax)/1000);
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
                                $scope.setTtaContractLine("nicescrolNewAlnlysisMain");
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
            tailSalesRowData.formerYearOntop = dataList[4].formerYearOntop == 0 ? 0 : (parseFloat(tailOiRowData.formerYearOntop) * 100 / parseFloat(dataList[4].formerYearOntop)).toFixed(2);
            tailSalesRowData.formerYearTtaOntop = dataList[4].formerYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.formerYearTtaOntop) * 100 / parseFloat(dataList[4].formerYearTtaOntop)).toFixed(2);
            tailSalesRowData.currentYearOntop = dataList[4].currentYearOntop == 0 ? 0 : (parseFloat(tailOiRowData.currentYearOntop) * 100 / parseFloat(dataList[4].currentYearOntop)).toFixed(2);
            tailSalesRowData.currentYearTtaOntop = dataList[4].currentYearTtaOntop == 0 ? 0 : (parseFloat(tailOiRowData.currentYearTtaOntop) * 100 / parseFloat(dataList[4].currentYearTtaOntop)).toFixed(2);
            tailSalesRowData.ttaOntopPercentGain = (parseFloat(tailSalesRowData.currentYearTtaOntop) - parseFloat(tailSalesRowData.formerYearTtaOntop)).toFixed(2);
            tailSalesRowData.ttaOntopActPercentGain = (parseFloat(tailSalesRowData.currentYearTtaOntop) - parseFloat(tailSalesRowData.formerYearActual)).toFixed(2);

            var tailNetMarginRowData = dataList[lastRowIdx];//倒数第一行
            tailNetMarginRowData.formerYearOntop = (parseFloat(dataList[7].formerYearTta) + parseFloat(tailSalesRowData.formerYearOntop)).toFixed(2);
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
        $scope.iniData = function (yearCode) {
            var year = parseInt(yearCode);
            $scope.yearInfo.year =  year;
            $scope.yearInfo.lastYear =  year-1;
            $scope.yearInfo.beforeYear =  year-2;

        };


        var baseDate = new Date();
        //id不为空
        if (getId()) {
            $scope.searchAnalysisData();
        }

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
        }
        //ttaTermAnalysis+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    });
});



