/**
 * Created by Administrator on 2016/9/26.
 */
define(["app",'XLSX'], function (app) {
    app.controller('equPosCreditAuditChangeCtrl', function ($scope, URLAPI, $rootScope, $state, SIEJS, $stateParams, iframeTabAction, httpServer,saafXlsExport) {
        $scope.dataTable = [];
        $scope.dataTable1 = [];
        $scope.addParam = {}
        $scope.params = {}

        var saafsuccessLoginInfo = eval('(' + sessionStorage[appName + '_successLoginInfo'] + ')');
        $scope.params.department = saafsuccessLoginInfo.deptCode;
        $scope.params.deptName = saafsuccessLoginInfo.deptName;

        //-----------------------------------------------初始化--------------------------------------------

        //excel模板下载
        $scope.excelModelDownLoad = function () {
            var _url = URLAPI.findEquPosRecoverInfo;

            var realName = [];
            var headName = [];
            headName = ["供应商档案号","分数", "结果", "有效期从", "有效期至"];
            realName = ["orgName", "organizationName", "itemCode", "supplierName", "supplierName"];
            var exportName = undefined;
            $scope.params.orgId = -1;
            saafXlsExport(_url, {'params': JSON.stringify($scope.params)}, exportName ? exportName : window.parent.$('#mainTabList .active a').text(), headName, realName);
        };
        //-----------------------------------------------删除--------------------------------------------
        $scope.delrow = function (item,index) {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                httpServer.post(URLAPI.deleteCreditAuditChange, {
                    params: JSON.stringify(item,index)
                }, function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.data.splice(index, 1);// 删除当前选中
                        SIEJS.alert("删除成功", "success", "确定");
                    } else {
                        SIEJS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            })
        }

        //-----------------------------------------------调整--------------------------------------------
        //导出
        $scope.myXlsExport = function () {
            //表格xls导出
            var _url = URLAPI.findEquPosCreditAuditChangeInfo;
            var realName = [];
            var headName = [];
            headName = ["预测单号", "库存组织", "供应商简称", "预测类型", "创建人"];
            realName = ["changeCreditAuditCode", "supplierName", "changeCreditAuditStatusName", "creationDate", "createdByName" ];
            var exportName = undefined;
            // $scope.dataTable.getData();
            saafXlsExport(_url, {'params': JSON.stringify($scope.params)}, exportName ? exportName : window.parent.$('#mainTabList .active a').text(), headName, realName);
        };

        //-----------------------------------------------跳转--------------------------------------------
        $scope.add = function () {
            iframeTabAction('供应商信用审核导入详情', 'equPosCreditAuditChangeEdit/')
        }

        //修改
        $scope.edit = function (row) {
            iframeTabAction('供应商信用审核导入详情：', 'equPosCreditAuditChangeEdit/' + row.changeCreditAuditHId)
        }
    });
});