/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree', 'angularFileUpload'], function (app, pinyin, ztree, angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('contractDetailCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction) {
        var id = $stateParams.id;
        $scope.HeaderHtml0 ="<div style=''><div class='' ng-nicescroll='' id='nicescroll_proLineDataTable' nice-option='{oneaxismousemode:true,railvalign: " +
            "'bottom',cursorcolor:'#1e90c2'}'>" +
            "<table  class=' nicescroll-height  table table-bordered table-hover table-condensed'>" +
            "<thead class='bg-gray-white'>" +
            "<tr class='text-center'><th><div style='width: 8px'></div></th>" +
            "<th><div style='width: 120px'>供应商编号</div></th>" +
            "<th>供应商名称</th>" +
            "<th>部门</th>" +
            "<th>商标</th>" +
            "<th>Purchase</th>" +
            "<th>Sales</th>" +
            "<th>销售区域</th>" +
            "<th>配送仓库</th>" ;
        $scope.HeaderHtml ="<div style=''><div class='' ng-nicescroll='' id='nicescroll_proLineDataTable' nice-option='{oneaxismousemode:true,railvalign: " +
            "'bottom',cursorcolor:'#1e90c2'}'>" +
            "<table  class=' nicescroll-height  table table-bordered table-hover table-condensed'>" +
            "<thead class='bg-gray-white'>" +
            "<tr class='text-center'><th><div style='width: 8px'></div></th>" +
            "<th><div style='width: 120px'>供应商编号</div></th>" +
            "<th>供应商名称</th>" +
            "<th>部门</th>" +
            "<th>商标</th>" +
            "<th>Purchase</th>" +
            "<th>Sales</th>" +
            "<th>销售区域</th>" +
            "<th>配送仓库</th>" ;
        $scope.tail = "</thead>" +
            "</tbody></table></div></div>";

        $scope.contractHeaderHtml = "";
        $scope.contractRowHtmlTerm0 ="<tr class='text-left'>" +
            "<th class='text-left'><div style='width: 120px' class='form-group col-xs-4 col-md-3'><div class='input-group input-group-xs'>" +
            "<input type='text' id='row0' style='width: 70px'  class='form-control radius3' required ng-model='params.vendor'>" +
            "<span class='input-group-btn ng-scope'><button id = 'ttt' class='btn btn-default'  type='button' >" +
            "<i class='fa fa-search'></i></button></span></div></div>" +
            "</th>" +
            "</tr>";
        $scope.contractRowHtmlTerm ="<tr class='text-left'>" +
            "<th class='text-left'><div style='width: 120px' class='form-group col-xs-4 col-md-3'><div class='input-group input-group-xs'>" +
            "<input type='text' id='row0' style='width: 70px'  class='form-control radius3' required ng-model='params.vendor'>" +
            "<span class='input-group-btn ng-scope'><button id = 'ttt' class='btn btn-default'  type='button' >" +
            "<i class='fa fa-search'></i></button></span></div></div>" +
            "</th>" +
            "</tr>";

        $scope.proposalDataRowHtml="";
        $scope.contractRow = "";//当前点击的row
        $scope.contractRowHtmlList =[];
        $scope.params = {};
        $scope.termsCnList = [];
        $scope.oiTypeList = [];
        $scope.contractLineDataTable = [];
        $scope.contractDetailDataTable = [];
        $scope.num = 0;
        $scope.status = {};
        $scope.clickBtnId = "";
        $scope.proposalId = "";

        $scope.contractHId = "";

        $scope.provendorCode = "";
        $scope.provendorName ="";

        $scope.vendorName = "";

        var rownum = 0;
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();


        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }


        var currentdate = date.getFullYear() + month + strDate;
        $scope.params.contractCode = 'PR'+currentdate.toString()+Math.random().toString().substring(2,6);

        $rootScope.userName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userName; // 重新获取userName，预防页面刷新丢失

        $scope.params.createdName = $rootScope.userName;

        $scope.params.creationDate = CurentTime();

        function CurentTime()
        {
            var now = new Date();

            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日

            var hh = now.getHours();            //时
            var mm = now.getMinutes();          //分
            var ss = now.getSeconds();           //秒

            var clock = year + "-";

            if(month < 10)
                clock += "0";

            clock += month + "-";

            if(day < 10)
                clock += "0";

            clock += day + " ";

            if(hh < 10)
                clock += "0";

            clock += hh + ":";
            if (mm < 10) clock += '0';
            clock += mm + ":";

            if (ss < 10) clock += '0';
            clock += ss;
            return(clock);
        }

        function disableAccount() {
            /**
             * 获取 checkbox 的值
             * @type {NodeList}
             */
           var obj = document.getElementsByName("checkName");

            for(var k in obj){
                if(obj[k].checked)
                    alert("22");
            }

        }

        //合同行编辑，type为处理类型，num为处理的行编号
        function rowEdit(type,num) {
            if(type == "add"){

                   var row = $scope.proposalDataRowHtml;
                   var rows ="";
                   for(var i = 0;i<$scope.num+1;i++){
                       var str = "input"+i;
                       var reg = new RegExp(str, "g");
                       row=row.replace(reg,"input-"+$scope.contractRowHtmlList.length+"-"+i);
                   }
                row=row.replace("checkbox1","checkbox"+$scope.contractRowHtmlList.length);
                $scope.contractRowHtmlList.push(row);
            }
            if(type == "delete"){
                $scope.contractRowHtmlList.splice(0, 1);
            }

        }


        //查询单据信息
        $scope.search = function () {

            $scope.contractHId = id;
            $scope.lineParams = {contractHId: $scope.contractHId};
            $scope.detailParams = {contractHId: $scope.contractHId};

            httpServer.post(URLAPI.contractHeaderFindById, {
                    'params': JSON.stringify({contractHId: $scope.contractHId})
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data;
                        $scope.sta = '9999';
                        $scope.params.contractCode = 'ABC12345678';
                        $scope.status = $scope.params.billStatus;

                        $scope.searchLine();
                        $scope.searchDetail();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

//填充proposal表格
        $scope.loadda = function() {
            var testdiv = document.getElementById("prodata");
            testdiv.innerHTML=$scope.HeaderHtml;
        }
        //$scope.loadda();


        $scope.searchPro = function () {
            $scope.HeaderHtml = $scope.HeaderHtml0;
            $scope.contractHeaderHtml = "";
            $scope.contractRowHtmlTerm = $scope.contractRowHtmlTerm0;
            $scope.contractRowHtmlList =[];
            $scope.proposalDataRowHtml= "";

            httpServer.post(URLAPI.contractLineFindPro, {
                    'params': JSON.stringify({proposalCode: $scope.params.proposalCode,proposalYear:$scope.params.proposalYear,versionCode:$scope.params.proposalVersion})
                    ,pageRows: 1000,
                    pageIndex: 1
                },
                function (res) {

                    if (res.status == 'S') {
                     //   $scope.contractLineDataTable = res.data;
                        var j = 0;
                        var vendorCode = "";
                        for (i = 0; i < res.data.length; i++) {
                            $scope.num = $scope.getRowsNum(res.data);
                            $scope.loadHtmlHeader($scope.num,res.data);
                              if(vendorCode=="" || vendorCode!=res.data[i].VENDORCODE ){
                                  j=j+1;
                                  vendorCode=res.data[i].VENDORCODE;
                                var line = {
                                    "vendorName": res.data[i].VENDORNAME
                                    , "vendorCode": res.data[i].VENDORCODE
                                    , "orgCode": res.data[i].ORGCODE
                                    , "tradeMark": res.data[i].TRADEMARK

                                }
                         };
//放进数组
                                $scope.contractLineDataTable.push(line);

                        }

                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        //统计出最大terms值
        $scope.getRowsNum = function (data) {
            var j = 0;//历史最大值保存
            var k = 0;//目前最大值保存
            var vendor = "";
            for (i = 0; i < data.length; i++) {
                //alert(data[i].VENDORCODE);
                 if(vendor == "" ||vendor!=data[i].VENDORCODE){
                     vendor = data[i].VENDORCODE;
                     if(i==0){
                         $scope.termsCnList.push(data[i].TERMSCN);//获取条款类目
                         $scope.oiTypeList.push(data[i].OITYPE);
                     }
                     if(j<k){
                         j = k;
                     }
                    k = 1;//新开始k=1
                 }else{
                     k = k + 1;
                     if(j<k){
                         $scope.termsCnList.push(data[i].TERMSCN);//获取条款类目
                         $scope.oiTypeList.push(data[i].OITYPE);
                     }
                 }
            }
            if(j<k){
                j = k;
            }
            return j;
        }

        $scope.loadHtmlHeader = function (termNum,data) {
            //for(var i = 0; i < termNum*6; i++){
            //    $scope.HeaderHtml = $scope.HeaderHtml + "<th>BEOI</th>";
            //}
            for(var i = 0;i<$scope.oiTypeList.length;i++){
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.oiTypeList[i]+"</th>";
            }
            $scope.HeaderHtml = $scope.HeaderHtml + "</tr><tr class='text-center'><th><div style='width: 8px'></div></th>";
            for(var i = 0; i < 8; i++){
                $scope.HeaderHtml = $scope.HeaderHtml + "<th></th>";
            }
            for(var i = 0;i<$scope.termsCnList.length;i++){
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+$scope.termsCnList[i]+"</th>";
            }
            $scope.HeaderHtml = $scope.HeaderHtml + "</tr><tr class='text-center'><th><div style='width: 8px'></div></th>";
            for(var i = 0; i < 8; i++){
                $scope.HeaderHtml = $scope.HeaderHtml + "<th></th>";
            }
            for(var i = 0;i<$scope.termsCnList.length;i++){
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>收取方式</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>TTA值</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>单位</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>条款</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>费用预估（不含税）</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>费用预估（含税）</th>";
            }

            $scope.HeaderHtml = $scope.HeaderHtml + "</tr>";
            var cvendor = "";
            for(var i = 0;i<data.length;i++){
                if(cvendor == "" || cvendor != data[i].VENDORCODE){
                    cvendor = data[i].VENDORCODE;
                    $scope.HeaderHtml = $scope.HeaderHtml + "<tr class='text-center'><th><div style='width: 8px'></div></th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].VENDORCODE+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].VENDORNAME+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].ORGCODE+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].TRADEMARK+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].PURCHASE+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].SALES+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].SALESAREA+"</th>";
                    $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].DELIVERYGRANARY+"</th>";

                    $scope.provendorCode = data[i].VENDORCODE;
                    $scope.provendorName = data[i].VENDORNAME;

                    if($scope.proposalId == ""){
                        $scope.proposalId = data[i].PROPOSALID;
                    }
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<tr class='text-center'><th>" +
                        "<div style='width: 8px'><input name='checkbox' id='checkbox1' type='checkbox'>" +
                        "<input type='text' id='proposalId' hidden ='true'  value='"+data[i].PROPOSALID+"'>" +
                        "<input type='text' id='contractHId' hidden ='true' value='"+data[i].CONTRACTHID+"'>" +
                        "</div></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><div style='width: 120px' class='form-group col-xs-4 col-md-3'><div class='input-group input-group-xs'>" +
                    "<input type='text' id='input1' style='width: 70px'  class='form-control radius3' value='"+data[i].VENDORCODE+"' required '>" +
                    "<span class='input-group-btn ng-scope'><button id = 'vendorbtn' class='btn btn-default'  type='button' >" +
                    "<i class='fa fa-search'></i></button></span></div></div>" ;
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input2'  class='form-control radius3' value='"+data[i].VENDORNAME+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input3'  class='form-control radius3' value='"+data[i].ORGCODE+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input4'  class='form-control radius3' value='"+data[i].TRADEMARK+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input5'  class='form-control radius3' value='"+data[i].PURCHASE+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input6'  class='form-control radius3' value='"+data[i].SALES+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input7'  class='form-control radius3' value='"+data[i].SALESAREA+"' required ></th>";
                    $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input8'  class='form-control radius3' value='"+data[i].DELIVERYGRANARY+"' required ></th>";
                }

                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].COLLECTTYPE+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].TTAVALUE+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].UNIT+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].TERMSSYSTEM+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].FEEINTAS+"</th>";
                $scope.HeaderHtml = $scope.HeaderHtml + "<th>"+data[i].FEENOTAX+"</th>";

                var kk = 6*i+8;
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+1)+"'  class='form-control radius3' value='"+data[i].COLLECTTYPE+"' required ></th>";
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+2)+"''  class='form-control radius3' value='"+data[i].TTAVALUE+"' required ></th>";
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+3)+"'  class='form-control radius3' value='"+data[i].UNIT+"' required ></th>";
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+4)+"'  class='form-control radius3' value='"+data[i].TERMSSYSTEM+"' required ></th>";
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+5)+"'  class='form-control radius3' value='"+data[i].FEEINTAS+"' required ></th>";
                $scope.proposalDataRowHtml = $scope.proposalDataRowHtml + "<th><input type='text' style='width: 80px' id='input"+(kk+6)+"'  class='form-control radius3' value='"+data[i].FEENOTAX+"' required ></th>";

            }
            $scope.contractHeaderHtml = $scope.HeaderHtml+"</tr>";
          //  $scope.HeaderHtml = $scope.HeaderHtml + $scope.tail;

            //在此可以确定好合同条款表格的头格式，以及行宽度
            $scope.loadContractHtml();

            var testdiv = document.getElementById("prodata");
            testdiv.innerHTML=$scope.HeaderHtml+ $scope.tail;

           //事件绑定
            $scope.bindEvn();
        }

        //事件绑定
        $scope.bindEvn = function () {
            for(var i = 0;i<100;i++){
                var bt = document.getElementById("btn"+i);
                bt.onclick = $scope.getVendor;

            }

        }



        //合同HTML代码封装
        $scope.loadContractHtml = function () {
            //$scope.rowEdit("add",0);
            $scope.contractHeaderHtml = $scope.HeaderHtml;
            for(var i =0;i<$scope.contractRowHtmlList.length;i++){
                $scope.contractHeaderHtml = $scope.contractHeaderHtml+$scope.contractRowHtmlList[i];
            }
            if($scope.contractRowHtmlList.length == 0){
                $scope.contractHeaderHtml = $scope.HeaderHtml;
            }
            $scope.contractHeaderHtml = $scope.contractHeaderHtml + $scope.tail;
           // $scope.contractHeaderHtml = $scope.HeaderHtml+$scope.tail;
            var contractData = document.getElementById("contractData");
            contractData.innerHTML=$scope.contractHeaderHtml;
        }


        $scope.searchLine = function () {
            httpServer.post(URLAPI.contractLineFind, {
                    'params': JSON.stringify($scope.lineParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractLineDataTable = res.data;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }


        $scope.searchDetail = function () {



            httpServer.post(URLAPI.contractDetailFind, {
                    'params': JSON.stringify($scope.detailParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractDetailDataTable = res.data;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.loadContractLine = function () {



            httpServer.post(URLAPI.contractDetailFind, {
                    'params': JSON.stringify($scope.detailParams)
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.contractDetailDataTable = res.data;


                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }




        //id不为空
        if ($stateParams.id) {

            //查询头信息
            $scope.search();
        } else {
            $scope.params.billStatus = 'A';
            $scope.params.isSplit = 'N';
            $scope.params.isMultipleVendor = 'N';
        }
        //保存前判断是否存在相同供应商
        $scope.sameData = function () {
            var vendorList = [];
            for(var i = 0;i<128;i++){
                var obj = document.getElementById("input-"+i+"-2");
                if(obj != null){
                    vendorList.push(obj)
                }
            }
            for(var k = 0 ;k<vendorList.length;k++){
                for(var j = 0;j<vendorList.length;j++){
                    if(vendorList[k].value==vendorList[j].value&&k!=j){
                        SIEJS.alert('数据中不能存在相同的供应商，请重新操作', "error", "确定");
                        return "false";
                    }
                }
            }

        }


        //保存
        $scope.btnSave = function () {
            var josnList = [];
            var line = [];

            var sta = $scope.sameData();
            if(sta== "false"){
                return false;
            }
            var header = {};
            header.contractCode = document.getElementById("contractCode1").value;
            header.vendorNbr = document.getElementById("vendorNbr1").value;
            header.vendorName = document.getElementById("vendorName1").value;
            header.billStatus = document.getElementById("billStatus1").value;
            header.proposalCode = document.getElementById("proposalCode1").value;
            header.proposalYear = document.getElementById("proposalYear1").value;
            header.proposalVersion = document.getElementById("proposalVersion1").value;
            header.createdName = document.getElementById("createdName1").value;
            header.creationDate = document.getElementById("creationDate1").value;
            header.confirmDate = document.getElementById("confirmDate1").value;

            httpServer.post(URLAPI.contractHeaderSave, {
                    params: JSON.stringify(header)
                },
                function (res) {
                    $scope.contractHId = res.data.contractHId;
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
            //行处理
            for(var i = 0;i<128;i++){
                var obj = document.getElementById("input-"+i+"-1");

                if(obj!=null){
                    var object = "";
                    var data = {};
                    data.proposalId = $scope.proposalId;
                    if(cid!=null){
                        data.contractHlId = $scope.contractHId;
                    }

                    //列处理
                  for(var k = 1;k<6555;k++){
                      object = document.getElementById("input-"+i+"-"+k);
                      if(object == null){
                          break;
                      }else{
                              eval('data.term'+k+' = object.value');
                      }

                  }
                }else{
                    break;
                }
                line.push(data);
            };



            httpServer.post(URLAPI.contractLineSave, {
                    params: JSON.stringify(line)
                },
                function (res) {
                    SIEJS.alert("保存成功", "success", '确定')
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );


        }

//合共信息提交
        $scope.btnSubmit = function () {
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



        }


        //选择供应商
        $scope.getVendorNbr = function () {
            //  $scope.areaComponent = e;
            $('#prandcontractCode').modal('toggle')
        };

        //合同行Line选择供应商
        $scope.getVendor = function () {
            $scope.clickBtnId = this.id;
            //  $scope.areaComponent = e;
            $('#contractCode').modal('toggle')
        };

        //合同表格选择供应商
        $scope.getContractVendor = function () {
            //  $scope.areaComponent = e;
            $('#contractVendor').modal('toggle')
        };

        //点击确认按钮
        $scope.selectcontractCodeReturn = function (key, value, currentList) {
            $scope.params.vendorNbr = currentList[0].supplierCode;
            $scope.params.vendorName = currentList[0].supplierName;

        }
        //点击确认按钮
        $scope.contractCodeReturn = function (key, value, currentList) {
            var brow = $scope.clickBtnId.split("btn")[1];
            document.getElementById("input-"+brow+"-1").value=currentList[0].supplierCode;
            document.getElementById("input-"+brow+"-2").value=currentList[0].supplierName;

        }

        //指定供应商
        $scope.selectVendor = function (key, value, currentList) {
            var row = $scope.proposalDataRowHtml;
            var rows ="";
            var str ="";
            var reg = "";
            for(var i = 0;i<currentList.length;i++){

                for(var kk = 0;kk<$scope.num*6+9;kk++){

                         str = "input"+kk;
                        reg = new RegExp(str, "g");
                        row=row.replace(reg,"input-"+$scope.contractRowHtmlList.length+"-"+kk);

                }
                row=row.replace("checkbox1","checkbox"+$scope.contractRowHtmlList.length);
                row=row.replace("vendorbtn","btn"+$scope.contractRowHtmlList.length);
                row=row.replace($scope.provendorCode,currentList[i].supplierCode);
                row=row.replace($scope.provendorName,currentList[i].supplierName);
                $scope.contractRowHtmlList.push(row);
                row = $scope.proposalDataRowHtml;
            }
            $scope.loadContractHtml();
            $scope.bindEvn();


        }
        //拆分
        $scope.splitLine = function (key, value, currentList) {
            var row = $scope.proposalDataRowHtml;
            var rows ="";
            var str ="";
            var reg = "";
            for(var kk = 0;kk<$scope.num*6+9;kk++){

                str = "input"+kk;
                reg = new RegExp(str, "g");
                row=row.replace(reg,"input-"+$scope.contractRowHtmlList.length+"-"+kk);

            }
            row=row.replace("checkbox1","checkbox"+$scope.contractRowHtmlList.length);
            row=row.replace("vendorbtn","btn"+$scope.contractRowHtmlList.length);

            row=row.replace("contractHId","contractHId"+$scope.contractRowHtmlList.length);
            $scope.contractRowHtmlList.push(row);
            row = $scope.proposalDataRowHtml;
            $scope.loadContractHtml();
            $scope.bindEvn();
        }


        //选择proposal信息
        $scope.getProposalCode = function () {
            //  $scope.areaComponent = e;
            $('#latentcontractCode').modal('toggle')
        };

        //点击确认按钮
        $scope.selectLatentcontractReturn = function (key, value, currentList) {

            $scope.params.proposalCode = currentList[0].orderNbr;
            $scope.params.proposalYear = currentList[0].proposalYear;
            $scope.params.proposalVersion = currentList[0].versionCode;

            $scope.searchPro();
        }





        //合同信息作废
        $scope.btnDiscard = function () {
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
        }
        //删除行
        $scope.deleteRow = function () {
            SIEJS.confirm('提示', '确认删除？', '确定', function () {
                var obj = document.getElementsByName("checkbox");

                for(var i = obj.length-1;i>0;i--){
                    if(obj[i].checked){
                        var delrow = obj[i].id.split("checkbox")[1];
                        $scope.contractRowHtmlList.splice(delrow, 1);
                    }

                }
                $scope.loadContractHtml();
                $scope.bindEvn();
            })
        }

        $scope.showBtn= function () {
            var obj = document.getElementById("chk");
            if(obj.checked){
                document.getElementById("splitSupplier").disabled=true;
            }else{
                document.getElementById("splitSupplier").disabled=false;
            }
        }
    });
});
