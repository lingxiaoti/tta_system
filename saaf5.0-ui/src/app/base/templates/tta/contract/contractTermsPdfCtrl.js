/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app','jqueryUi','jqprint','layui'], function (app,jqueryUi,jqprint,layui) {
    //app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('contractTermsPdfCtrl', function ($http, $scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow,dragTable,$timeout,validateForm,$compile,tableXlsExport) {
    // layui.use('table'); // 按需加载layui模块
        $scope.tab = {
            active: 0,
            nav: ['贸易条款', '贸易协议'],
            click: function (i) {
                $scope.tab.active = i
            }
        }
        var id = $stateParams.id;
        $scope.params = {};
        $scope.orderNo = {};
        $scope.params.venderCode = $location.search()['venderCode'];
        $scope.params.contractHId = $location.search()['contractHId'];
        $scope.params.proposalId = $location.search()['proposalId'];
        $scope.params.proposalCode = $location.search()['proposalCode'];
        $scope.params.proposalYear =$location.search()['proposalYear'];
        $scope.params.resource =$location.search()['resource'];
        $scope.params.venderName =$location.search()['venderName'];
        $scope.params.proposalVersion = $location.search()['proposalVersion'];
        $scope.params.proposalVendorNbr = $location.search()['proposalVendorNbr'];
        $scope.printPDFFlag = false;
        $scope.printNum = 3;
        $scope.isDmOtherStandard = false; //针对条款 PROMOTION FUND (LEAFLETS、DM&FLYER),如果按照"DM按其他协定标准" 则需要显示补充协议。
        $scope.companyConvertFlag = false;

        $scope.isPdfPrint = $location.search()['isPdfPrint'] ? true : false; //是否pdf后台打印，用于不必要的按钮控制。

        console.log("出入接收的参数信息是:" + JSON.stringify($scope.params));

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
        var unPrintNum = $scope.$watch('printNum',function (newValue,oldValue) {
            if (newValue === 0) {
                if($scope.ttaDeptFeeReportTable != null && $scope.ttaDeptFeeReportTable.length != 0 && $scope.isDmOtherStandard) {
                    $("#printPDFIdAll").append("<a id='printPDFId3'></a>");
                }
                $scope.printPDFFlag = true;
                unPrintNum();
            }
        });
        
        //获取当前用户信息
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        var d = new Date()
        d.setMonth(d.getMonth() + 2);
        $scope.nowDateParam = d.getFullYear() + '-' + (d.getMonth() + 1);

        //获取字典信息
        // $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        // if (!$scope.lookupCodeParty) {
        //     lookupCode(function (res) {
        //         $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
        //     });
        // }

	/*
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
        */
       // terms  End  校验 *******************************************************************************************
        $scope.showInfoNavTabs = true;
        $scope.printCount = 0;
        $scope.tradePrintCount = 0;
        $scope.tabActionFlag = true ;
        $scope.brandPlnLDataTable = [];
        $scope.firstText = "请选择";
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
        /*
        $scope.controllTabShow ={};
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
                    console.log("操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员");
                }
            );
        }
        */
        //**********************控制页签显示 end************************
        /*
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

        //表单是否变更
        $scope.formIsChanged = function(){
            var endTtaContractLineTable = JSON.stringify($scope.ttaContractLineTable);    //TTA Summary

            if( $scope.startTtaContractLineTable &&($scope.startTtaContractLineTable != endTtaContractLineTable)){
                return true;
            }
            return false;
        }
        */
        /**********************************工作流配置   end**************************************/

        // tab 切换
        $scope.tabAction = 'LgFeeBatchHeadersDetailBusiness'; //根据页面配置
        $scope.tabActionChild = 'contractNew'; //根据页面配置
        $scope.tabChangeChild = function (name){
        $scope.tabActionChild = name;
        }


        /***************************贸易条款 start****************/
        //查找terms头部信息
        $scope.queryTradeTermsHeader = function() {
            httpServer.post(URLAPI.termsHFindById, {
                    'params': JSON.stringify({
                        proposalId: getId(),
                        resource : $scope.params.resource ? $scope.params.resource : "VENDER_CONTRACT",
                        vendorContractParams :$scope.params
                        })
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
                            }
                        });
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.tradeClause = function () {
            //$scope.getPrintCount('TTA_TRADE_TERM');
            //贸易条款头部信息
            $scope.queryTradeTermsHeader();
            httpServer.post(URLAPI.findTermsAgrement, {
                params: JSON.stringify({
                    proposalId: getId(),
                    year: $scope.params.proposalYear,
                    contractHId:  $scope.params.contractHId == 'undefined' ?  null : $scope.params.contractHId,
                    resource : $scope.params.resource ? $scope.params.resource : "VENDER_CONTRACT",
                    vendorContractParams :$scope.params
                })
            }, function (res) {
                if (res.status == 'S') {
                    var tradeYear = res.data.tradeYear;
                    var saleWayArr = res.data.saleWayList; //销售方式 $scope.saleWayList
                    $scope.params.saleType = res.data.saleType;
                    $scope.companyConvertFlag = res.data.companyConvertFlag;
                  /*  var proposalYearRemarkData = $scope.lookupCodeParty.filter(function (x) {
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
                    }*/
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
                    $scope.orderNo = (res.data.orderNo + "").replace(/-/g,"_");
                    
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
                                if (i == 0) {
                                    htmlStr += "<tr><td>" + obj.termsEn + obj.termsCn+ "</td><td style= \"text-align: center;\">"+ (obj.termsSystem ? (obj.termsSystem == 0 ? "" : obj.termsSystem): "" ) + "</td></tr>";
                                } else {
                                    htmlStr += "<tr><td>" + obj.termsEn + obj.termsCn+ "</td><td style= \"text-align: center;\">"+ (obj.termsSystem ? obj.termsSystem: "" ) + "</td></tr>";
                                }
                            }
                            var tableIdContent = $compile(htmlStr)($scope);
                            $("#tableId").append(tableIdContent);
                        } else if (idx == 1) {
                            $scope.listData = resultList[idx];
                            htmlStr = "<tr><td colspan='2' style=\"background: gold !important;\">" + listData.meaning  + "</td></tr>";
                            var tdStr = "<tr>\n" +
                                "    <td colspan=\"2\"><table style=\"width: 70%;text-align: center;margin: 0 auto;\" class=\"table-bordered table-hover table-condensed\" id=\"part2Table\">\n" +
                                "            <tr><th rowspan='2'>目标退佣</th><th colspan='" + listData.headList.length  +"'>" + tradeYear + "</th></tr>\n" +
                                "            <tr><th ng-repeat='rowHead in listData.headList'>{{rowHead}}</th></tr></table>\n" + "<div style='margin: 10px 0;'>{{proposalNewYearRemark}}</div>"
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
                            for (var idy = 0; idy< part2BodyList.length; idy ++) {
                                trStr += "<tr><td>" + part2BodyList[idy].termsEn + part2BodyList[idy].termsCn + "</td><td>" + (part2BodyList[idy].termsSystem ? part2BodyList[idy].termsSystem: "" ) + "</td></tr>";
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
                                if ((jsonObj.termsSystem ? jsonObj.termsSystem: "" ).indexOf("DM按其他协定标准")) {
                                    $scope.isDmOtherStandard = true; //需要显示协定标准
                                }
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
                } else {
                    SIEJS.alert("加载贸易条款失败:" + res.msg, "error", "确定");
                }
            }, function (err) {
                console.log("加载贸易条款失败 error:" + err)
            });
        }
        /***************************贸易条款 end ****************/

        /***************************TT 贸易之协议补充 start****************/
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

        $scope.ttaDeptFeeReport = function (){
            httpServer.post(URLAPI.ttaDeptFeeLFindReport, {
                params: JSON.stringify({proposalId: getId(), venderName: $scope.params.venderName})
            }, function (res) {
                if (res.status == 'S') {
                    $scope.ttaDeptFeeReportTable = res.data.ttaDeptFeeReportTable;
                    $scope.ttaDeptFeeReportHead = res.data.head ;
                    //取出甲方
                   /* var values = $scope.lookupCodeParty.filter(function (x) {
                        return x.lookupType == "PARTY_A_COMPANY";
                    });
                    $scope.ttaDeptFeeReportHead.partyA =  values[0].meaning;*/
                    $scope.ttaDeptFeeReportHead.partyA = res.data.companyNameDic ? res.data.companyNameDic : '武汉屈臣氏个人用品商店有限公司';
//                     $timeout(function () {
//                         $scope.setTtaContractLine("ttaDeptFeeLFindReportScroll");
// /*                        jQuery( "#ttaDeptFeeLFindReportTableId").colResizable({
//                             liveDrag:false,
//                             gripInnerHtml:"<div class='grip' style='width:20px;'"+
//                             'height:15px;'+
//                             'margin-top:-3px;'+
//                             'margin-left:-5px;'+
//                             'position:relative;'+
//                             'z-index:88;'+
//                             " 'cursor:e-resize;'></div>",
//                             draggingClass:"dragging",
//                             resizeMode:'overflow',
//                         });*/
//                         jQuery( "#ttaDeptFeeLFindReportTableId td" ).resizable();
//                         jQuery( "#ttaDeptFeeLFindReportTableId th" ).resizable();
//                         jQuery( "#ttaDeptFeeLFindReportTableId tr" ).resizable();
//                     },200);

                    $scope.printNum -= 1;
                } else {
                    SIEJS.alert("加载贸易协议:" + res.msg, "error", "确定");
                }
            }, function (err) {
                console.log("searchQuestionnaire error:" + err)
            });
        }
        //打印
        $scope.printTtaDeptFeeLFindReport = function (){
            jQuery("#ttaDeptFeeLFindReportPrint").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true,
            })
        }
        /***************************TT 贸易之协议补充 end****************/

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
                       })
                       $scope.printCount = res.data;
                   } else {
                       SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                   }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.printTrade = function () {
            jQuery("#printTradeId").print({
                globalStyles:true,
                mediaPrint:true,
                iframe:true,
            })
   /*         httpServer.post(URLAPI.saveOrUpdatePrintCount, {
                    params: JSON.stringify({printType : "TTA_TRADE_TERM", remark : "贸易条款"})
                }, function (res) {
                    if (res.status == 'S') {
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
        }
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
        }

        //######################按钮控制 start########################################


        $scope.tabChangeInfo = function (name) {
            $scope.tabAction = name;
            if (!getId() && 'opnProcess' != name) {
                return;
            }
            if (name == 'tradeClause') { //
                $scope.tradeClause();
            } else if(name == 'ttaDeptFeeReport'){ //ttaDeptFeeReport
                $scope.ttaDeptFeeReport();
            }  else if (name == 'opnProcess') {
                $scope.tabChange('opnProcess')
            }
        };

        //id不为空
        if (getId()) {
            $scope.tabChangeInfo('tradeClause');
            $scope.tabChangeInfo('ttaDeptFeeReport');
        }


    });
});

