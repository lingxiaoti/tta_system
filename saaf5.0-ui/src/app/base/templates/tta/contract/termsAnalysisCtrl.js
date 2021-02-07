/**
 * Created by lip on 2019/5/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('termsAnalysisCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction) {
        $scope.dataTable="";
        $scope.gpdor= 0;
        $scope.gpper= 0;//2018
        $scope.gpdor2= 0;//GP$
        $scope.gpper2= 0;//2019GP%
        $scope.ActualGp= 0;

        $scope.GPPER1= 0;
        $scope.GPPER2 = 0;
        $scope.GPPER3 = 0;
        $scope.GPPER4 = 0;
        $scope.GPPER5 = 0;
        $scope.GPPER6 = 0;
        $scope.GPPER7 = 0;
        $scope.GPPER8 = 0;
        $scope.GPPER9 = 0;

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

        $scope.TotalBeoiA = 0;
        $scope.TotalBeoiB = 0;
        $scope.TotalBeoiC = 0;
        $scope.TotalBeoiD = 0;
        $scope.TotalBeoiE = 0;

        $scope.TotalSroiA = 0;
        $scope.TotalSroiB = 0;
        $scope.TotalSroiC = 0;
        $scope.TotalSroiD = 0;
        $scope.TotalSroiE = 0;

        $scope.TotalAboiA = 0;
        $scope.TotalAboiB = 0;
        $scope.TotalAboiC = 0;
        $scope.TotalAboiD = 0;
        $scope.TotalAboiE = 0;

        //ae=n+u+ac
        $scope.TotalOiA = 0;
        $scope.TotalOiB = 0;
        $scope.TotalOiC = 0;
        $scope.TotalOiD = 0;
        $scope.TotalOiE = 0;

        //af=ae/c
        $scope.TotalOiPurchaseA = 0;
        $scope.TotalOiPurchaseB = 0;
        $scope.TotalOiPurchaseC = 0;
        $scope.TotalOiPurchaseD = 0;
        $scope.TotalOiPurchaseE = 0;

        //ag=af*(1-f)
        $scope.TotalOiSalesA = 0;
        $scope.TotalOiSalesB = 0;
        $scope.TotalOiSalesC = 0;
        $scope.TotalOiSalesD = 0;
        $scope.TotalOiSalesE = 0;

        //ah=f+ag
        $scope.NetMarginA = 0;
        $scope.NetMarginB = 0;
        $scope.NetMarginC = 0;
        $scope.NetMarginD = 0;
        $scope.NetMarginE = 0;

        $scope.print = function () {
            //页面打印缩放比例设置
            document.getElementsByTagName('body')[0].style.zoom=0.05;
            document.getElementsByTagName('body')[0].style.width='250%';
            document.getElementsByTagName('body')[0].style.height='200%';
            //
            //document.getElementsByTagName('body')[0].style.height ='500px';
            //$("body").style.width = 300;
            var bdhtml=$("body").html();//获取当前页的html代码
            var sprnstr="<!--startprint-->"; //设置打印开始区域
            var eprnstr="<!--endprint-->";//设置打印结束区域
            var prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);//从开始代码向后取html
            prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html
            window.document.body.innerHTML=prnhtml;

            //window.print();
        };



        //数据公式统一处理,主要计算GP%行数据
        $scope.sumdata =function (data) {
            //判断为空值后面就不算了，默认为零
            if(data.hterm2018.salesType == undefined){
                $scope.ActualGp = 0;
                $scope.gpper = 0;
            }else{
                //商讨确定为同一个gp
                //目前用$scope.GPPER2代替
                $scope.ActualGp = data.hterm2018.gp;

                if(data.hterm2018.salesType.indexOf("returnable")>0){
                    $scope.GPPER1= data.hterm2018.gp;
                }
                if(data.hterm2018.salesType.indexOf("general")>0&&data.hterm2018.termsVersion=="A"){
                    $scope.GPPER1= data.hterm2018.consignmentDiscount;
                }
                if(data.hterm2018.salesType.indexOf("general")>0&&data.hterm2018.termsVersion=="B"){
                    $scope.GPPER1= (data.hterm2018.consignmentDiscount+($scope.one+$scope.pre+$scope.can+$scope.jizhong)*(1-data.hterm2018.consignmentDiscount)).toFixed(2);
                }
                if(data.hterm2018.salesType.indexOf("Consignment")>0&&data.hterm2018.termsVersion=="A"){
                    $scope.GPPER1= (data.hterm2018.consignmentDiscount-0.02*(1-data.hterm2018.consignmentDiscount)).toFixed(2);
                }
                if(data.hterm2018.salesType.indexOf("Consignment")>0&&data.hterm2018.termsVersion=="B"){
                    $scope.GPPER1= (data.hterm2018.consignmentDiscount+($scope.one+$scope.pre+$scope.can+$scope.jizhong)*(1-data.hterm2018.consignmentDiscount) - 0.02*(1-data.hterm2018.consignmentDiscount)).toFixed(2);
                }
            }

            $scope.GPPER2 = data.hterm2018.gp;

            $scope.GPPER3=data.hterm2019.gp;

                if(data.hterm2019.salesType.indexOf("returnable")>0&&data.hterm2019.termsVersion=="A"){
                    $scope.GPPER3= (data.hterm2019.gp-0.02*(1-data.hterm2019.gp)).toFixed(2);
                }
                if(data.hterm2019.salesType.indexOf("returnable")>0&&data.hterm2019.termsVersion=="B"){
                    $scope.GPPER3= (data.hterm2019.gp+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.gp)-0.02*(1-data.hterm2019.gp)).toFixed(2);
                }
                if(data.hterm2019.salesType.indexOf("经仓")>0&&data.hterm2019.termsVersion=="A"){
                    $scope.GPPER3= (data.hterm2019.consignmentDiscount-0.02*(1-data.hterm2019.consignmentDiscount)).toFixed(2);
                }
                if(data.hterm2019.salesType.indexOf("经仓")>0&&data.hterm2019.termsVersion=="B"){
                    $scope.GPPER3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount)-0.02*(1-data.hterm2019.consignmentDiscount)).toFixed(2);
                }
                if(data.hterm2019.salesType.indexOf("常规")>0&&data.hterm2019.termsVersion=="A"){
                    $scope.GPPER3= data.hterm2019.consignmentDiscount;
                }
                if(data.hterm2019.salesType.indexOf("常规")>0&&data.hterm2019.termsVersion=="B"){
                    $scope.GPPER3= (data.hterm2019.consignmentDiscount+($scope.one2+$scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2)*(1-data.hterm2019.consignmentDiscount)).toFixed(2);
                }
            $scope.GPPER4 =($scope.GPPER2 /$scope.GPPER3).toFixed(2);

            $scope.GPPER5 =(parseFloat($scope.GPPER3) + parseFloat($scope.GPPER4)).toFixed(2);
            $scope.GPPER6 = "n/a";
            $scope.GPPER7 = ($scope.GPPER3 - $scope.GPPER1).toFixed(2);
            $scope.GPPER8 = "n/a"
            $scope.GPPER9 = ($scope.GPPER5 - $scope.GPPER2).toFixed(2);

            //BEOI表格Total数据
            if(data.hterm2019.termsVersion=="A"){
                $scope.TotalBeoiA =($scope.pre+$scope.can+$scope.jizhong+$scope.xuan+$scope.shang).toFixed(2);
                $scope.TotalBeoiB =($scope.pre+$scope.can+$scope.jizhong+$scope.xuan+$scope.shang).toFixed(2);
                $scope.TotalBeoiC =($scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2).toFixed(2);
            }
            if(data.hterm2019.termsVersion=="B"){
                $scope.TotalBeoiA ==($scope.xuan+$scope.shang).toFixed(2);
                $scope.TotalBeoiB =($scope.pre2+$scope.can2+$scope.jizhong2+$scope.cz2+$scope.shang2).toFixed(2);
                $scope.TotalBeoiC =0;
            }
        }

        //统计total行数据
        function sumTotal(data,data2){
            for(var i = 0;i<data2.length;i++){
                if(data2[i].oiType.indexOf("SROI")>=0){
                    $scope.TotalSroiA = (parseFloat($scope.TotalSroiA)+parseFloat(data[i].feeIntas)).toFixed(2);
                    $scope.TotalSroiB = (parseFloat($scope.TotalSroiB)+parseFloat(data[i].sumMoney)).toFixed(2);
                    $scope.TotalSroiC = (parseFloat($scope.TotalSroiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                    $scope.TotalSroiD = (parseFloat($scope.TotalSroiD)+parseFloat(data[i].ttaValue)).toFixed(2);
                    $scope.TotalSroiE = (parseFloat($scope.TotalSroiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                }
                if(data2[i].oiType.indexOf("ABOI")>=0){
                    $scope.TotalAboiA = (parseFloat($scope.TotalAboiA)+parseFloat(data[i].feeIntas)).toFixed(2);
                    $scope.TotalAboiB = (parseFloat($scope.TotalAboiB)+parseFloat(data[i].sumMoney)).toFixed(2);
                    $scope.TotalAboiC = (parseFloat($scope.TotalAboiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                    $scope.TotalAboiD = (parseFloat($scope.TotalAboiD)+parseFloat(data[i].ttaValue)).toFixed(2);
                    $scope.TotalAboiE = (parseFloat($scope.TotalAboiC)+parseFloat(data2[i].feeIntas)).toFixed(2);
                }
            }

            $scope.TotalOiA =(parseFloat($scope.TotalBeoiA) + parseFloat($scope.TotalSroiA) + parseFloat($scope.TotalAboiA)).toFixed(2);
            $scope.TotalOiB =(parseFloat($scope.TotalBeoiB) + parseFloat($scope.TotalSroiB) + parseFloat($scope.TotalAboiB)).toFixed(2);
            $scope.TotalOiC =(parseFloat($scope.TotalBeoiC) + parseFloat($scope.TotalSroiC) + parseFloat($scope.TotalAboiC)).toFixed(2);
            $scope.TotalOiD =(parseFloat($scope.TotalBeoiD) + parseFloat($scope.TotalSroiD) + parseFloat($scope.TotalAboiD)).toFixed(2);
            $scope.TotalOiE =(parseFloat($scope.TotalBeoiE) + parseFloat($scope.TotalSroiE) + parseFloat($scope.TotalAboiE)).toFixed(2);

            $scope.TotalOiPurchaseA = isNaN((parseFloat($scope.TotalOiA)/parseFloat(data[0].purchase)).toFixed(2))?0:(parseFloat($scope.TotalOiA)/parseFloat(data[0].purchase)).toFixed(2);
            $scope.TotalOiPurchaseB = isNaN((parseFloat($scope.TotalOiB)/parseFloat(data[0].purchase)).toFixed(2))?0:(parseFloat($scope.TotalOiB)/parseFloat(data[0].purchase)).toFixed(2);
            $scope.TotalOiPurchaseC = isNaN((parseFloat($scope.TotalOiC)/parseFloat(data2[0].purchase)).toFixed(2))?0:(parseFloat($scope.TotalOiC)/parseFloat(data2[0].purchase)).toFixed(2);
            $scope.TotalOiPurchaseD = isNaN((parseFloat($scope.TotalOiD)/parseFloat(data2[0].purchase)).toFixed(2))?0:(parseFloat($scope.TotalOiD)/parseFloat(data2[0].purchase)).toFixed(2);
            $scope.TotalOiPurchaseE = isNaN((parseFloat($scope.TotalOiE)/parseFloat(data2[0].purchase)).toFixed(2))?0:(parseFloat($scope.TotalOiE)/parseFloat(data2[0].purchase)).toFixed(2);

            $scope.TotalOiSalesA = (parseFloat($scope.TotalOiPurchaseA) * (1-parseFloat($scope.mid.GP1))).toFixed(2);
            $scope.TotalOiSalesB = (parseFloat($scope.TotalOiPurchaseB) * (1-parseFloat($scope.mid.GP2))).toFixed(2);
            $scope.TotalOiSalesC = (parseFloat($scope.TotalOiPurchaseC) * (1-parseFloat($scope.mid.GP3))).toFixed(2);
            $scope.TotalOiSalesD = (parseFloat($scope.TotalOiPurchaseD) * (1-parseFloat($scope.mid.GP4))).toFixed(2);
            $scope.TotalOiSalesE = (parseFloat($scope.TotalOiPurchaseE) * (1-parseFloat($scope.mid.GP5))).toFixed(2);

            $scope.NetMarginA = (parseFloat($scope.mid.GP1) + parseFloat($scope.TotalOiSalesA)).toFixed(2);
            $scope.NetMarginB = (parseFloat($scope.mid.GP2) + parseFloat($scope.TotalOiSalesB)).toFixed(2);
            $scope.NetMarginC = (parseFloat($scope.mid.GP3) + parseFloat($scope.TotalOiSalesC)).toFixed(2);
            $scope.NetMarginD = (parseFloat($scope.mid.GP4) + parseFloat($scope.TotalOiSalesD)).toFixed(2);
            $scope.NetMarginE = (parseFloat($scope.mid.GP5) + parseFloat($scope.TotalOiSalesE)).toFixed(2);
        }

        $scope.loadData =function (data,data2) {
            sumTotal(data,data2);
            var tb = document.getElementById("dataTable");
            for(var i = data2.length;i>0;i--){
                //第一行remark需要合并单元格
                if(i==1){

                    var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                    newTr.style = "background:#D3FF93";
                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML =    data2[i-1].oiType;
                    newTd1.rowSpan  = data2.length+2;
                    var newTd2 = newTr.insertCell();
                    newTd2.innerHTML =   data2[i-1].termsEn;
                    var newTd3 = newTr.insertCell();
                    newTd3.innerHTML =   data2[i-1].termsCn;

                    if(data2[i-1].oiType =="BEOI"){
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = (data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd5 = newTr.insertCell();
                        newTd5.innerHTML =   (data[i-1].sumMoney/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    }else{
                        var newTd4 = newTr.insertCell();
                        newTd4.innerHTML = data[i-1].feeIntas;
                        var newTd5 = newTr.insertCell();
                        newTd5.innerHTML =   data[i-1].sumMoney;
                        var newTd6 = newTr.insertCell();
                        newTd6.innerHTML =   data2[i-1].feeIntas;

                    }
                    var newTd7 = newTr.insertCell();
                    newTd7.innerHTML =  data2[i-1].ttaValue;
                    var newTd8 = newTr.insertCell();
                    newTd8.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd9 = newTr.insertCell();
                    newTd9.innerHTML =   "n/a";
                    var newTd10 = newTr.insertCell();
                    newTd10.innerHTML =  (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd11 = newTr.insertCell();
                    newTd11.innerHTML =   "n/a";
                    var newTd12 = newTr.insertCell();
                    newTd12.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd13 = newTr.insertCell();
                    if($scope.dataTable.contractLineList2018[0].remark == undefined){
                        newTd13.innerHTML =   "";
                    }else{
                        newTd13.innerHTML =   $scope.dataTable.contractLineList2018[0].remark;
                    }
                    newTd13.colSpan  = 7;
                    newTd13.rowSpan = data2.length+2;
                }
                else{

                    if(i == data2.length){
                        var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                        var newTd1 = newTr.insertCell();
                        newTd1.innerHTML =   data2[i-1].oiType+"%(as of purchase)";
                        newTd1.colSpan  = 2;
                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = newTr.insertCell();
                            if($scope.mid.Purchase1 ==0){
                                tnewTd2.innerHTML =  0;
                            }else{
                                tnewTd2.innerHTML =   $scope.TotalBeoiA/$scope.mid.Purchase1;
                            }

                            var tnewTd3 = newTr.insertCell();
                            if($scope.mid.Purchase2 ==0){
                                tnewTd3.innerHTML =   0;
                            }else{
                                tnewTd3.innerHTML =   $scope.TotalBeoiB/$scope.mid.Purchase2;
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =   0;
                            }else{
                                tnewTd4.innerHTML =   $scope.TotalBeoiC/$scope.mid.Purchase3;
                            }

                            var tnewTd5 = newTr.insertCell();
                            if($scope.mid.Purchase4 ==0){
                                tnewTd5.innerHTML =   0;
                            }else{
                                tnewTd5.innerHTML =   $scope.TotalBeoiD/$scope.mid.Purchase4;
                            }

                            var tnewTd6 = newTr.insertCell();
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   $scope.TotalBeoiE/$scope.mid.Purchase5;
                            }

                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =   parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);
                            $scope.header.BEOI=  parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);
                        }
                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = newTr.insertCell();
                            if($scope.mid.Purchase1 ==0){
                                tnewTd2.innerHTML =   0;
                            }else{
                                tnewTd2.innerHTML =   $scope.TotalSroiA/$scope.mid.Purchase1;
                            }

                            var tnewTd3 = newTr.insertCell();
                            if($scope.mid.Purchase2 ==0){
                                tnewTd3.innerHTML =   0;
                            }else{
                                tnewTd3.innerHTML =   $scope.TotalSroiB/$scope.mid.Purchase2;
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =   0;
                            }else{
                                tnewTd4.innerHTML =   $scope.TotalSroiC/$scope.mid.Purchase3;
                            }

                            var tnewTd5 = newTr.insertCell();
                            if($scope.mid.Purchase4 ==0){
                                tnewTd5.innerHTML =   0;
                            }else{
                                tnewTd5.innerHTML =   $scope.TotalSroiD/$scope.mid.Purchase4;
                            }

                            var tnewTd6 = newTr.insertCell();
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   $scope.TotalSroiE/$scope.mid.Purchase5;
                            }

                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =   parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);
                            $scope.header.SROI=  parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);


                        }
                        var tnewTd2 = newTr.insertCell();
                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            if($scope.mid.Purchase1 ==0){
                                tnewTd2.innerHTML =   0;
                            }else{
                                tnewTd2.innerHTML =   $scope.TotalAboiA/$scope.mid.Purchase1;
                            }

                            var tnewTd3 = newTr.insertCell();
                            if($scope.mid.Purchase2 ==0){
                                tnewTd3.innerHTML =   0;
                            }else{
                                tnewTd3.innerHTML =   $scope.TotalAboiB/$scope.mid.Purchase2;
                            }

                            var tnewTd4 = newTr.insertCell();
                            if($scope.mid.Purchase3 ==0){
                                tnewTd4.innerHTML =   0;
                            }else{
                                tnewTd4.innerHTML =   $scope.TotalAboiC/$scope.mid.Purchase3;
                            }
                            var tnewTd5 = newTr.insertCell();
                            if($scope.mid.Purchase4 ==0){
                                tnewTd5.innerHTML =   0;
                            }else{
                                tnewTd5.innerHTML =   $scope.TotalAboiD/$scope.mid.Purchase4;
                            }

                            var tnewTd6 = newTr.insertCell();
                            if($scope.mid.Purchase5 ==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   $scope.TotalAboiE/$scope.mid.Purchase5;
                            }


                            var newTd7 = newTr.insertCell();
                            newTd7.innerHTML =   "n/a";
                            var newTd8 = newTr.insertCell();
                            newTd8.innerHTML =   parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);
                            $scope.header.ABOI=  parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML);
                        }

                        if($scope.header.BEOI=""||$scope.header.BEOI==undefined){
                            $scope.header.BEOI=0;
                        }
                        if($scope.header.SROI=""||$scope.header.SROI==undefined){
                            $scope.header.SROI=0;
                        }
                        if($scope.header.ABOI=""||$scope.header.ABOI==undefined){
                            $scope.header.ABOI=0;
                        }
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   "n/a";
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   parseFloat(tnewTd6.innerHTML) - parseFloat(tnewTd3.innerHTML);

                        var tnewTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                        var tnewTd1 = tnewTr.insertCell();
                        tnewTd1.innerHTML =   "TOTAL"+data2[i-1].oiType+"$";
                        tnewTd1.colSpan  = 2;

                        if(data2[i-1].oiType.indexOf("BEOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            if($scope.mid.Purchase1==0){
                                tnewTd2.innerHTML =   0;
                            }else{
                                tnewTd2.innerHTML =   $scope.TotalBeoiA/$scope.mid.Purchase1;
                            }

                            var tnewTd3 = tnewTr.insertCell();
                            if($scope.mid.Purchase2==0){
                                tnewTd3.innerHTML =   0;
                            }else{
                                tnewTd3.innerHTML =   $scope.TotalBeoiB/$scope.mid.Purchase2;
                            }

                            var tnewTd4 = tnewTr.insertCell();
                            if($scope.mid.Purchase3==0){
                                tnewTd4.innerHTML =   0;
                            }else{
                                tnewTd4.innerHTML =   $scope.TotalBeoiC/$scope.mid.Purchase3;
                            }

                            var tnewTd5 = tnewTr.insertCell();
                            if($scope.mid.Purchase4==0){
                                tnewTd5.innerHTML =   0;
                            }else{
                                tnewTd5.innerHTML =   $scope.TotalBeoiD/$scope.mid.Purchase4;
                            }

                            var tnewTd6 = tnewTr.insertCell();
                            if($scope.mid.Purchase5==0){
                                tnewTd6.innerHTML =   0;
                            }else{
                                tnewTd6.innerHTML =   $scope.TotalBeoiE/$scope.mid.Purchase5;
                            }

                        }
                        if(data2[i-1].oiType.indexOf("SROI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            tnewTd2.innerHTML =   $scope.TotalSroiA;
                            var tnewTd3 = tnewTr.insertCell();
                            tnewTd3.innerHTML =   $scope.TotalSroiB;
                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   $scope.TotalSroiC;
                            var tnewTd5 = tnewTr.insertCell();
                            tnewTd5.innerHTML =   $scope.TotalSroiD;
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.innerHTML =   $scope.TotalSroiE;
                        }
                        if(data2[i-1].oiType.indexOf("ABOI")>=0){
                            var tnewTd2 = tnewTr.insertCell();
                            tnewTd2.innerHTML =   $scope.TotalAboiA;
                            var tnewTd3 = tnewTr.insertCell();
                            tnewTd3.innerHTML =   $scope.TotalAboiB;
                            var tnewTd4 = tnewTr.insertCell();
                            tnewTd4.innerHTML =   $scope.TotalAboiC;
                            var tnewTd5 = tnewTr.insertCell();
                            tnewTd5.innerHTML =   $scope.TotalAboiD;
                            var tnewTd6 = tnewTr.insertCell();
                            tnewTd6.innerHTML =   $scope.TotalAboiE;
                        }


                        var tnewTd7 = tnewTr.insertCell();
                        tnewTd7.innerHTML =   "n/a";
                        var tnewTd8 = tnewTr.insertCell();
                        tnewTd8.innerHTML = parseFloat(tnewTd4.innerHTML) - parseFloat(tnewTd2.innerHTML)  ;
                        var tnewTd9 = tnewTr.insertCell();
                        tnewTd9.innerHTML =   "n/a";
                        var tnewTd10 = tnewTr.insertCell();
                        tnewTd10.innerHTML =   parseFloat(tnewTd6.innerHTML) - parseFloat(tnewTd3.innerHTML);
                    }
                    var newTr = tb.insertRow(16);//添加新行，trIndex就是要添加的位置
                    var newTd1 = newTr.insertCell();
                    newTd1.innerHTML =   data2[i-1].termsEn;
                    var newTd2 = newTr.insertCell();
                    newTd2.innerHTML =   data2[i-1].termsCn;
                    var newTd3 = newTr.insertCell();
                    newTd3.innerHTML =  (data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd4 = newTr.insertCell();
                    newTd4.innerHTML =  (data[i-1].ttaValue/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd5 = newTr.insertCell();
                    newTd5.innerHTML =  (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    var newTd6 = newTr.insertCell();
                    newTd6.innerHTML =  data2[i-1].ttaValue;
                    if(data2[i-1].oiType.indexOf("BEOI")>=0){
                        var newTd7 = newTr.insertCell();
                        newTd7.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   "n/a";
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   "n/a";
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].ttaValue/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                    }
                    if(data2[i-1].oiType.indexOf("SROI")>=0){
                        var newTd7 = newTr.insertCell();
                        newTd7.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   (parseFloat(newTd8.innerHTML)/(data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase)).toFixed(2);
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].ttaValue/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =   (parseFloat(newTd10.innerHTML)/parseFloat(newTd4.innerHTML)).toFixed(2);
                    }
                    if(data2[i-1].oiType.indexOf("ABOI")>=0){
                        var newTd7 = newTr.insertCell();
                        newTd7.innerHTML =   parseFloat(newTd5.innerHTML)/parseFloat(newTd6.innerHTML);
                        var newTd8 = newTr.insertCell();
                        newTd8.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd9 = newTr.insertCell();
                        newTd9.innerHTML =   (parseFloat(newTd8.innerHTML)/(data[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase)).toFixed(2);
                        var newTd10 = newTr.insertCell();
                        newTd10.innerHTML =   (data2[i-1].feeIntas/$scope.dataTable.contractLineList2018[0].purchase - data[i-1].ttaValue/$scope.dataTable.contractLineList2018[0].purchase).toFixed(2);
                        var newTd11 = newTr.insertCell();
                        newTd11.innerHTML =   (parseFloat(newTd10.innerHTML)/parseFloat(newTd4.innerHTML)).toFixed(2);
                    }

                }


            }
        }



        $scope.header = {};

        $scope.mid = {};

        $scope.tail = {};

        $scope.initData = function (data) {
            if(data==undefined){
             return;
            }

            $scope.sumdata(data);
            var list = [];
            var list2 = [];
            var OIType="";
            //ABOI类型数据结果集的拆分，然后加载html
            for(var i=0;i<data.contractLineList2019.length;i++){
                if(data.contractLineList2019[i].termsCn.indexOf("一般购货折扣")>0){
                    $scope.one= data.contractLineList2018[i].ttaValue;
                    $scope.one2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("提前付款")>0){
                    $scope.pre= data.contractLineList2018[i].ttaValue;
                    $scope.pre2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("残损")>0){
                    $scope.can= data.contractLineList2018[i].ttaValue;
                    $scope.can2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("集中收货")>0){
                    $scope.jizhong= data.contractLineList2018[i].ttaValue;
                    $scope.jizhong2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("促销折扣")>0){
                    $scope.cz= data.contractLineList2018[i].ttaValue;
                    $scope.cz2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("商业发展")>0){
                    $scope.shang= data.contractLineList2018[i].ttaValue;
                    $scope.shang2= data.contractLineList2019[i].ttaValue;
                }
                if(data.contractLineList2019[i].termsCn.indexOf("宣传牌")>0){
                    $scope.xuan= data.contractLineList2018[i].ttaValue;
                    $scope.xuan2= data.contractLineList2019[i].ttaValue;
                }

            if(isNaN(data.hterm2019.salesUpScale)){
                data.hterm2019.salesUpScale =0
            }
            if(isNaN(data.contractLineList2019[0].feeIntas)){
                data.contractLineList2019[0].feeIntas = 0;
            }

                //中间表格赋值
            $scope.header.versionCode = data.header2019.versionCode;
            $scope.header.orderNbr = data.header2019.orderNbr;
            $scope.header.SupplierCode = data.item.vendorNbr;
            $scope.header.SupplierName = data.item.vendorName;
            $scope.header.Region= data.hterm2019.salesArea;
            $scope.header.Class= data.hterm2019.termsClass;
            $scope.header.Buyer= data.hterm2019.buyerCode;
            $scope.header.OwnerDept= data.item.deptDesc;
            $scope.header.TradingMode= data.hterm2019.salesType;
            $scope.header.ContractVersion= data.hterm2019.termsVersion;
            $scope.header.Brand= data.item.brandCn;
            $scope.header.Ne= data.header2019.isTermsConfirm;
            $scope.header.fp= 1;
            $scope.header.Purchase= data.hterm2019.fcsPurchse;
            $scope.header.GP= data.hterm2019.fcsSales;
                $scope.header.remark= data.hterm2019.remark;
            //$scope.header.BEOI= data.item.;
            //$scope.header.SROI= data.item.;
            //$scope.header.ABOI= data.item.;
            //$scope.header.TotalOI= data.item.;
            //$scope.header.NM= data.item.;
            $scope.mid.Markup1 = data.hterm2018.salesUpScale;
            $scope.mid.Markup2 = data.hterm2018.salesUpScale;

            $scope.mid.Markup3 = data.hterm2019.salesUpScale;
            $scope.mid.Markup4 = data.hterm2019.salesUpScale;
            $scope.mid.Markup5 = ($scope.mid.Markup3+$scope.mid.Markup4).toFixed(2);
            $scope.mid.Markup6 = "n/a";
            $scope.mid.Markup7 = ($scope.mid.Markup3-$scope.mid.Markup1).toFixed(2);
            $scope.mid.Markup8 = "n/a";
            $scope.mid.Markup9 = ($scope.mid.Markup5-$scope.mid.Markup2).toFixed(2);

            $scope.mid.FreeGood1 = data.contractLineList2018[0].feeIntas;
            $scope.mid.FreeGood2 = data.contractLineList2018[0].ttaValue;
            $scope.mid.FreeGood3= data.contractLineList2019[0].feeIntas;
            $scope.mid.FreeGood4 = data.contractLineList2019[0].purchase;
            $scope.mid.FreeGood5 = ($scope.mid.FreeGood3+$scope.mid.FreeGood4).toFixed(2);
            $scope.mid.FreeGood6 = ($scope.mid.FreeGood3 - $scope.mid.FreeGood1).toFixed(2);
            $scope.mid.FreeGood7 = isNaN(($scope.mid.FreeGood6/$scope.mid.FreeGood1).toFixed(2))?0:($scope.mid.FreeGood6/$scope.mid.FreeGood1).toFixed(2);
            $scope.mid.FreeGood8 = ($scope.mid.FreeGood5 - $scope.mid.FreeGood2).toFixed(2);
            $scope.mid.FreeGood9 = (($scope.mid.FreeGood8/$scope.mid.FreeGood2).toFixed(2))==Infinity?0:($scope.mid.FreeGood8/$scope.mid.FreeGood2).toFixed(2);

            $scope.mid.Purchase1 = data.contractLineList2018[0].purchase;
            $scope.mid.Purchase2= data.contractLineList2018[0].purchase;
            $scope.mid.Purchase3= data.contractLineList2019[0].purchase;
            $scope.mid.Purchase4 = data.contractLineList2019[0].purchase;
            $scope.mid.Purchase5 = $scope.mid.Purchase3;
            $scope.mid.Purchase6 = ($scope.mid.Purchase3-$scope.mid.Purchase1).toFixed(2);
            $scope.mid.Purchase7 = (($scope.mid.Purchase6/$scope.mid.Purchase1).toFixed(2))==Infinity?0:($scope.mid.Purchase6/$scope.mid.Purchase1).toFixed(2);
            $scope.mid.Purchase8 = (($scope.mid.Purchase5-$scope.mid.Purchase2).toFixed(2))==Infinity?0:($scope.mid.Purchase5-$scope.mid.Purchase2).toFixed(2);
            $scope.mid.Purchase9 = (($scope.mid.Purchase8/$scope.mid.Purchase2).toFixed(2))==Infinity?0:($scope.mid.Purchase8/$scope.mid.Purchase2).toFixed(2);

            $scope.mid.Salse1 = data.contractLineList2018[0].sales;
            $scope.mid.Salse2= data.contractLineList2018[0].sales;
            $scope.mid.Salse3= data.contractLineList2019[0].sales;
            $scope.mid.Salse4 = data.contractLineList2019[0].sales;
            $scope.mid.Salse5 = $scope.mid.Salse3;
            $scope.mid.Salse6 = ($scope.mid.Salse3-$scope.mid.Salse1).toFixed(2);
            $scope.mid.Salse7 = (($scope.mid.Salse6/$scope.mid.Salse1).toFixed(2))==Infinity?0:($scope.mid.Salse6/$scope.mid.Salse1).toFixed(2);
            $scope.mid.Salse8 = ($scope.mid.Salse5-$scope.mid.Salse2).toFixed(2);
            $scope.mid.Salse9 = (($scope.mid.Salse8/$scope.mid.Salse2).toFixed(2))==Infinity?0:($scope.mid.Salse8/$scope.mid.Salse2).toFixed(2);

            $scope.mid.GP1 = parseFloat((data.contractLineList2018[0].sales*$scope.GPPER1).toFixed(2));
            $scope.mid.GP2 = parseFloat((data.contractLineList2018[0].sales*$scope.GPPER2).toFixed(2));
            $scope.mid.GP3 = parseFloat(data.contractLineList2019[0].sales*$scope.GPPER3).toFixed(2);
            $scope.mid.GP4 = parseFloat((data.contractLineList2019[0].sales*$scope.GPPER4).toFixed(2));
            $scope.mid.GP5 = parseFloat((parseFloat($scope.mid.GP3)+$scope.mid.GP4).toFixed(2));
            if($scope.mid.GP1 == 0){
                $scope.mid.GP6 = 0;
                $scope.mid.GP7 = 0;
            }else{
                $scope.mid.GP6 = parseFloat(($scope.mid.GP3-$scope.mid.GP1).toFixed(2));
                $scope.mid.GP7 = parseFloat(($scope.mid.GP6/$scope.mid.GP1).toFixed(2));
            }
            if($scope.mid.GP2 == 0){
                $scope.mid.GP8 = 0;
                $scope.mid.GP9 = 0;
            }else{
                $scope.mid.GP8 = parseFloat(($scope.mid.GP5-$scope.mid.GP2).toFixed(2));
                $scope.mid.GP9 = parseFloat(($scope.mid.GP8/$scope.mid.GP2).toFixed(2));
            }

                //BEOI数据级别拆分html布局,按照oiType分组数据
                if(data.contractLineList2019.length!=1&&(OIType==""||data.contractLineList2019[i].oiType!=OIType)){
                    if(i!=0){
                        //load一次为一个BEOI表格数据
                        $scope.loadData(list,list2);
                    }
                    list=[];
                    list2=[];
                    list.push(data.contractLineList2018[i]);
                    list2.push(data.contractLineList2019[i]);
                    OIType = data.contractLineList2019[i].oiType;
                }else{
                    list.push(data.contractLineList2018[i]);
                    list2.push(data.contractLineList2019[i]);
                    if(i==data.contractLineList2019.length-1){
                        $scope.loadData(list,list2);
                    }
                }
            }



            $scope.tail.OI1 = $scope.TotalOiA;
            $scope.tail.OI2 = $scope.TotalOiB;
            $scope.tail.OI3 = $scope.TotalOiC;
            $scope.tail.OI4 = $scope.TotalOiD;
            $scope.tail.OI5 = $scope.TotalOiE;
            $scope.tail.OI6 = (parseFloat($scope.TotalOiC) - parseFloat($scope.TotalOiA)).toFixed(2);
            $scope.tail.OI7 = (parseFloat($scope.tail.OI6)/parseFloat($scope.TotalOiA)).toFixed(2);
            $scope.tail.OI8 = (parseFloat($scope.tail.OI5)-parseFloat($scope.TotalOiB)).toFixed(2);
            $scope.tail.OI9 = (parseFloat($scope.tail.OI8)/parseFloat($scope.TotalOiB)).toFixed(2);

            $scope.tail.Purchase1 = $scope.TotalOiPurchaseA;
            $scope.tail.Purchase2 = $scope.TotalOiPurchaseB;
            $scope.tail.Purchase3 = $scope.TotalOiPurchaseC;
            $scope.tail.Purchase4 = $scope.TotalOiPurchaseD;
            $scope.tail.Purchase5 = $scope.TotalOiPurchaseE;
            $scope.tail.Purchase6 = "n/a";
            $scope.tail.Purchase7 = (parseFloat($scope.TotalOiPurchaseC) - parseFloat($scope.TotalOiPurchaseA)).toFixed(2);
            $scope.tail.Purchase8 = "n/a";
            $scope.tail.Purchase9 = (parseFloat($scope.TotalOiPurchaseE) - parseFloat($scope.TotalOiPurchaseB)).toFixed(2);

            $scope.tail.Sales1 = $scope.TotalOiSalesA;
            $scope.tail.Sales2 = $scope.TotalOiSalesB;
            $scope.tail.Sales3 = $scope.TotalOiSalesC;
            $scope.tail.Sales4 = $scope.TotalOiSalesD;
            $scope.tail.Sales5 = $scope.TotalOiSalesE;
            $scope.tail.Sales6 = "n/a";
            $scope.tail.Sales7 = (parseFloat($scope.TotalOiSalesC) - parseFloat($scope.TotalOiSalesA)).toFixed(2);
            $scope.tail.Sales8 = "n/a";
            $scope.tail.Sales9 = (parseFloat($scope.TotalOiSalesE) - parseFloat($scope.TotalOiSalesB)).toFixed(2);

            $scope.tail.netMargin1 = $scope.NetMarginA;
            $scope.tail.netMargin2 = $scope.NetMarginB;
            $scope.tail.netMargin3 = $scope.NetMarginC;
            $scope.tail.netMargin4 = $scope.NetMarginD;
            $scope.tail.netMargin5 = $scope.NetMarginE;
            $scope.tail.netMargin6 = "n/a";
            $scope.tail.netMargin7 = (parseFloat($scope.NetMarginC) - parseFloat($scope.NetMarginA)).toFixed(2);
            $scope.tail.netMargin8 = "n/a";
            $scope.tail.netMargin9 = (parseFloat($scope.NetMarginE) - parseFloat($scope.NetMarginB)).toFixed(2);
        }

        //$scope.initData();
        //查询单据信息
        $scope.search = function () {
            httpServer.post(URLAPI.contractHeaderfindTerm, {
                    //'params': JSON.stringify({itemId: 4})
                    'params': JSON.stringify({poprosalId: '241'})
                },
                function (res) {
                    res = changeToNumber(res);
                    $scope.dataTable = res;
                    $scope.initData(res);
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }
        $scope.search();

        //转换字符串为浮点数字
        function changeToNumber(data){
            for(var i=0;i<data.contractLineList2019.length;i++){
                data.contractLineList2019[i].ttaValue = parseFloat(data.contractLineList2019[i].ttaValue);
                data.contractLineList2019[i].feeIntas = parseFloat(data.contractLineList2019[i].feeIntas);
                data.contractLineList2019[i].purchase = parseFloat(data.contractLineList2019[i].purchase);
                data.contractLineList2019[i].sales = parseFloat(data.contractLineList2019[i].sales);
            }
            for(var i=0;i<data.contractLineList2018.length;i++){
                data.contractLineList2018[i].ttaValue = parseFloat(data.contractLineList2018[i].ttaValue);
                data.contractLineList2018[i].feeIntas = parseFloat(data.contractLineList2018[i].feeIntas);
                data.contractLineList2018[i].purchase = parseFloat(data.contractLineList2018[i].purchase);
                data.contractLineList2018[i].sales = parseFloat(data.contractLineList2018[i].sales);
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
            var bb=document.getElementById("dataTable").getElementsByTagName("tr");
        }
    });
});
