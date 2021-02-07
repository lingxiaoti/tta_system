'use strict';
define(['app', 'pinyin', 'ztree','XLSX','fixedReport'], function (app, pinyin, ztree,XLSX,fixedReport) {
    app.controller('ttaHouseKeepingCtrl', function ($scope, $filter, $location, $timeout,$rootScope, $state, $stateParams, SIEJS, httpServer,validateForm, URLAPI, iframeTabAction, setNewRow,$http,lookupCode,tableXlsExport,saafLoading) {
        $scope.buttonFlag = true ;
        $scope.userType = JSON.parse(sessionStorage[appName + '_successLoginInfo']).userType;
        //$scope.ttaContractRecordHeaderTable = {};
        /**
         * select ti.uda4,ti.uda8 from tta_item ti where ti.uda4 <> 'Exclusive Brand /  独家品牌' and  ti.uda8 not in('PEB','EB');
         * @type {{}}
         */

        $scope.houseKeepingTable = {};
        $scope.ABOIdDataTable = {};
        $scope.aboiParams = {};
        var date=new Date;
        $scope.info1 = {
            startDate:date.getFullYear()*1-1,
            endDate:date.getFullYear()
        };
        $scope.currentList2 = [] ;
        $scope.batchId = $stateParams.batchId;
        $scope.selectVendorParams = {};

        var y = date.getFullYear();
        $scope.params = {'reportYear':y.toString()} ;
        $scope.paramsBrand = {'reportYear':y.toString()} ;
        $scope.paramsGroup = {'reportYear':y.toString()} ;
        $scope.title = {} ;
        $scope.splitDataTable = [] ;
        $scope.firstText = "请选择";
        $scope.search = function (){
            $scope.ttaContractRecordHeaderTable.getData() ;
            $scope.getHeight('fixedId','parentFixedId');

        };

        $scope.searchBrand = function (){
            $scope.BrandTable.getData() ;

        };
        $scope.searchGroup = function (){
            $scope.groupTable.getData() ;

        };

        $scope.afterGetData =function () {
            $scope.getHeight('fixedId','parentFixedId') ;
        };
        $scope.brandAfterGetData =function () {
            $scope.getHeight('brandFixedId','brandParentFixedId') ;
        };

        $scope.groupAfterGetData =function () {
            $scope.getHeight('groupFixedId','groupParentFixedId') ;
        };

        //获取字典信息
        $scope.lookupCodeParty = window.parent.saafrootScope.lookupCode || $rootScope.lookupCode;
        if (!$scope.lookupCodeParty) {
            lookupCode(function (res) {
                $scope.lookupCodeParty = $rootScope.lookupCode = res.data;
            });
        }
/*        //增加按钮
        $scope.btnNew = function () {
            if (!$scope.ttaContractRecordHeaderTable) {
                $scope.ttaContractRecordHeaderTable = {};
            }
            if (!$scope.ttaContractRecordHeaderTable.isNew) {
                $scope.ttaContractRecordHeaderTable.data = [] ;
                $scope.ttaContractRecordHeaderTable.isNew = true ;
            }
            var date= new Date();
            $scope.info1 = {
                startDate:date.getFullYear()*1-1,
                endDate:date.getFullYear() + 1
            };
            var info = {
                applyDate:dateFtt('yyyy-MM-dd',date)
            };
            $scope.ttaContractRecordHeaderTable.data.unshift(info);
        };*/


        function dateFtt(fmt,date)
        { //author: meizz
            var o = {
                "M+" : date.getMonth()+1,     //月份
                "d+" : date.getDate(),     //日
                "h+" : date.getHours(),     //小时
                "m+" : date.getMinutes(),     //分
                "s+" : date.getSeconds(),     //秒
                "q+" : Math.floor((date.getMonth()+3)/3), //季度
                "S" : date.getMilliseconds()    //毫秒
            };
            if(/(y+)/.test(fmt))
                fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
            for(var k in o)
                if(new RegExp("("+ k +")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            return fmt;
        }
        $scope.click = function (index) {
            $timeout(function(){
                document.getElementById("id_reason" + index).focus();
            },0)

        };

        //行选择部门
        $scope.getDeptCodeL = function (value) {
            $('#selectTtaContractRecordHeaderDeptIdL').modal('toggle')
        };
        //点击确认按钮
        $scope.selectDeptLReturn = function (key, value, currentList) {
            var row = $scope.ttaContractRecordHeaderTable.data[$scope.ttaContractRecordHeaderTable.data.selectRow];
            row.deptCode = currentList[0].departmentCode;
            row.deptName = currentList[0].departmentName;
        };


        $scope.selectPersonReturn = function (key, value, currentList) {
            var row = $scope.ttaContractRecordHeaderTable.data[$scope.ttaContractRecordHeaderTable.data.selectRow];
            row.name = currentList[0].userFullName;
            row.userId = currentList[0].userId;
            row.userId = currentList[0].userId;
            row.deptCode = currentList[0].groupCode;
            row.deptName = currentList[0].groupName;

        };
        $scope.getSupplierCode = function (row) {
            if (!row.year) {
                SIEJS.alert('请选选择合同年份！', "error", "确定");
                return;
            }
            $scope.selectVendorParams.proposalYear = row.year;
            $('#supplierCode').modal('toggle');
        };

        $scope.selectSupplierReturn = function (key, value, currentList) {
            var row = $scope.ttaContractRecordHeaderTable.data[$scope.ttaContractRecordHeaderTable.data.selectRow];
            row.vendorNbr = currentList[0].vendorCode;
            row.vendorName = currentList[0].vendorName;
            row.saleTypeName = currentList[0].saleTypeName;
            row.saleType = currentList[0].saleType;
            row.brand = currentList[0].brandCn;
            row.attributeA = currentList[0].agreementEdition;//合同版本号
            row.orderNbr = currentList[0].orderNbr;
        };
        /**
         * 保存行
         */
        $scope.btnSave = function (form){
            if(!validateForm(form)){
                return;
            }
            $scope.saveAll =  $scope.ttaContractRecordHeaderTable.data ;
            httpServer.post(URLAPI.saveOrUpdateALLTtaContractRecordHeader,
                {params: JSON.stringify({
                        save:$scope.saveAll
                    })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert('保存', 'success', '确定');
                        $scope.search();
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


        /***************************上传end ****************/
        /**
         * 多选
         * @param item
         * @param e
         * @param t
         */
        $scope.check = function (item, e, t) {
            $scope.flag2 = false ;
            var checked = e.currentTarget.checked; // 当前选中状态
            var currentIsPush = true;
            var currentIndex = -1;
            for (var i = 0; i < $scope.currentList2.length; i++) {
                if (item.cwId === $scope.currentList2[i].cwId) {
                    currentIsPush = false;
                    currentIndex = i;
                    break;
                }
            }

            if (checked && currentIsPush && t !== false) {
                item.checked = true; // 选中标识
                $scope.currentList2.push(item)
            }
            if (!checked && !currentIsPush) {
                item.checked = false; // 选中标识
                $scope.currentList2.splice(currentIndex, 1); // 删除当前选择的数据　－－－－－－－－－－－－－－－－－－－－－－－－２０１８－１－９
            }
            if (checked && !currentIsPush) {
                item.checked = true; // 选中标识
            }
        };


        // 全选按钮
        $scope.checkedAll = function (e) {
            $scope.flag2 = false ;
            if (e.target.checked) {// 全选
                $scope.selectedAll = true;
                for (var n in $scope.ttaContractRecordHeaderTable.data) {
                    var row = $scope.ttaContractRecordHeaderTable.data[n];
                    if (/*!$scope.setCheckedDisabled(row)*/ true) { // 非被禁用
                        row.checked = true;
                        var isPush = false;
                        for (var m in $scope.currentList2) {
                            var list = $scope.currentList2[m];
                            if (row.cwId === list.cwId) {
                                isPush = true;
                                break;
                            }
                        }
                        if (!isPush) $scope.currentList2.push(row);
                    }

                }
            } else {// 返选
                $scope.selectedAll = false;
                for (var n in $scope.ttaContractRecordHeaderTable.data) {
                    var row = $scope.ttaContractRecordHeaderTable.data[n];
                    row.checked = false;
                    var index = $scope.currentList2.indexOf(row);
                    $scope.currentList2.splice(index--, 1);// for 循环使用 splice 删除数组请 把下标 --
                }
            }

        };





        $scope.getHeight = function (id ,id2){
            var w = jQuery(window);
            var _top = jQuery("#" + id2).offset().top * 1;
            var _h = w.innerHeight();
            jQuery("#" + id2).css("height", (_h - _top) + "px");
            w.bind('resize', function () {
                jQuery("#" + id2).css("height", (w.innerHeight() - _top) + "px");
                $timeout(function () {
                    $scope.$apply();
                }, 100)
            });
            //
            $timeout(function () {
                var divFix = document.getElementById(id) ;
                var divParentFixed = document.getElementById(id2) ;
                var rect = divFix.getBoundingClientRect();
                var rectParent = divParentFixed.getBoundingClientRect();
                if ((rect.height/rectParent.height) >= 0.9){
                    divFix.style.height = 90+'%' ;
                }else{
                    divFix.style.height = 'auto';
                }
            }, 200)
        }



    });
});
