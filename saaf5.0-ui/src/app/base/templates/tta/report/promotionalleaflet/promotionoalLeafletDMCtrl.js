'use strict';
define(['app', 'pinyin', 'ztree'], function (app, pinyin, ztree) {
    app.controller('promotionoalLeafletDMCtrl', function ($scope, $filter, $location, $rootScope, $state, $stateParams, SIEJS, httpServer, URLAPI, iframeTabAction, setNewRow) {

        $scope.params = {};
        $scope.logoImg = [];
/*        $scope.id= "";
        $scope.salesSiteId= "";
        $scope.parentId ="";
        $scope.promotionGuidelineId= "";*/



        $scope.btnNew=function(){
            $scope.modalTitle='新增';

            var row = $scope.dataTable.selectRow;

            if (row == undefined || row == null) {
                SIEJS.alert("请先选中一行数据",'warning','确定');
                return;
            }
            if (undefined == row.promotionalLeafId) {
                SIEJS.alert("请先选中一行数据",'warning','确定');
                return;
            }

            $scope.params.promotionalLeafId = row.promotionalLeafId;
            $scope.params.promotionalPeriod =row.promotionalPeriod;
            $scope.params.dmPage = row.dmPage;
            $scope.params.pDisPosition = row.pDisPosition;
            $scope.params.effectiveArea = row.effectiveArea;
            $scope.params.mapPosition = row.mapPosition;
            $scope.params.pActivity =row.pActivity;
            $scope.params.productCode =row.productCode;
            $scope.params.brandName = row.brandName;
            $scope.params.activityContent =row.activityContent;
            $scope.params.deptName =row.deptName;
            $scope.params.categoryName =row.categoryName;
            $scope.params.supplierCode =row.supplierCode;
            $scope.params.supplierName =row.supplierName;
            $scope.params.contractStatus =row.contractStatus;
            $scope.params.contractYear =row.contractYear;
            $scope.params.contractClause =row.contractClause;
            $scope.params.collStandard =row.collStandard;
            //应收金额
            $scope.params.receiAmount =row.receiAmount;
            $scope.params.unconfirmVendorCode =row.unconfirmVendorCode;
            $scope.params.unconfirmVendorName =row.unconfirmVendorName;
            $scope.params.affirmAmountDm =row.affirmAmountDm;
            $scope.params.affirmAmountMkt =row.affirmAmountMkt;
            $scope.params.diffValue1 =row.diffValue1;
            $scope.params.diffValue2 =row.diffValue2;
            $scope.params.purchasingReply =row.purchasingReply;
            $scope.params.exemptionReason =row.exemptionReason;
            $scope.params.exemptionReason2 =row.exemptionReason2;
            $scope.params.tiFormNo =row.tiFormNo;
            $scope.params.bicRemark =row.bicRemark;
            $scope.params.contractMasterDept =row.contractMasterDept;
            $scope.params.itemMasterDept =row.itemMasterDept;

          $('#msgCfgModal').modal('show');
        }
        $scope.change = function () {
            httpServer.post(URLAPI.monthlyCheckingVendor, {
                params: JSON.stringify({vendor: $scope.params.priorVendorCode,year:$scope.params.contractYear})
            }, function (res) {
                $scope.params.chargeStandards = res.data;
            }, function (error) {
                console.error(error);
                JS.alert('供应商查询失败', 'error', '确定');
            })

        }

        //选择供应商
        $scope.getVendorNbr = function () {
            $('#prandcontractCode').modal('show');
        };

        //点击确认按钮
        $scope.selectcontractCodeReturn = function (key, value, currentList) {
            $scope.params.unconfirmVendorCode = currentList[0].supplierCode;
            $scope.params.unconfirmVendorName = currentList[0].supplierName;
            $('#prandcontractCode').modal('hide');
        }

/*        $scope.btnModify = function () {
            var row = $scope.dataTable.selectRow

            iframeTabAction('促销位置基础数据编辑：'+row.contractCode, 'contractUpdateDetail/' + row.contractHId)
        }*/


/*        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;

            if (item) {
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {

                    httpServer.post(URLAPI.contractDel, {
                        params: JSON.stringify({ids: item.contractHId})
                    }, function (res) {
                        if ('S' == res.status) {
                            $scope.dataTable.search();
                            JS.alert('删除成功');
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                        }
                    }, function (error) {
                        console.error(error);
                        JS.alert('删除失败', 'error', '确定');
                    })

                })
            }
        }*/


        $scope.btnSave=function(){
            httpServer.post(URLAPI.promotionalLeafletDWSave, {
                params: JSON.stringify({
                    promotionalLeafId:$scope.params.promotionalLeafId,
                    promotionalPeriod:$scope.params.promotionalPeriod,
                    dmPage:$scope.params.dmPage ,
                    pDisPosition:$scope.params.pDisPosition ,
                    effectiveArea:$scope.params.effectiveArea ,
                    mapPosition:$scope.params.mapPosition,
                    pActivity:$scope.params.pActivity ,
                    brandName:$scope.params.brandName ,
                    activityContent:$scope.params.activityContent ,
                    deptName:$scope.params.deptName ,
                    categoryName:$scope.params.categoryName ,
                    supplierCode:$scope.params.supplierCode ,
                    supplierName:$scope.params.supplierName ,
                    contractStatus:$scope.params.contractStatus ,
                    contractYear:$scope.params.contractYear ,
                    contractClause:$scope.params.contractClause ,
                    collStandard:$scope.params.collStandard ,
                    //应收金额
                    receiAmount:$scope.params.receiAmount ,
                    unconfirmVendorCode:$scope.params.unconfirmVendorCode ,
                    unconfirmVendorName:$scope.params.unconfirmVendorName ,
                    affirmAmountDm:$scope.params.affirmAmountDm ,
                    affirmAmountMkt:$scope.params.affirmAmountMkt ,
                    diffValue1:$scope.params.diffValue1 ,
                    diffValue2:$scope.params.diffValue2 ,
                    purchasingReply:$scope.params.purchasingReply ,
                    exemptionReason:$scope.params.exemptionReason ,
                    exemptionReason2:$scope.params.exemptionReason2,
                    tiFormNo:$scope.params.tiFormNo ,
                    bicRemark:$scope.params.bicRemark,
                    contractMasterDept:$scope.params.contractMasterDept,
                    itemMasterDept:$scope.params.itemMasterDept
                })
            }, function (res) {
                if ('S' == res.status) {
                    $('#msgCfgModal').modal('toggle');
                    $scope.dataTable.search();
                    SIEJS.alert('保存成功');
                } else {
                    SIEJS.alert(res.msg, 'error', '确定');
                }
            }, function (error) {
                console.error(error);
                SIEJS.alert('保存失败', 'error', '确定');
            })
        }


    });
});
