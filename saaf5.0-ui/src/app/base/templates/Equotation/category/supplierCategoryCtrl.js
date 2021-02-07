/**
 * Created by sie_chubaodong on 2019/9/4.
 */
define(["app"], function (app) {
    app.controller('supplierCategoryCtrl', ['$scope', '$state', 'URLAPI', '$stateParams', 'iframeTabAction', "SIE.JS", 'httpServer', function ($scope, $state, URLAPI, $stateParams, iframeTabAction, JS, httpServer) {
        $scope.dataTable = {};
        $scope.dataTable.data = [];
        $scope.flags = {};
        $scope.params = {};
        $scope.params.lineData = [];
        //当前登录人所属部门和部门id
        $scope.flags.departmentId = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptId;
        $scope.params.departmentId = $scope.flags.departmentId;
        //当前登录人所属部门
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        console.log($scope.params.deptCode);
        $scope.params.deptName = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptName;
        console.log($scope.params.deptName);

        //增加按钮
        $scope.btnNew = function () {
            var supplierCategoryArray = {
                departmentId: $scope.params.departmentId,
                categoryCodeFirst: "",
                categoryCodeSecond: "",
                categoryCodeThird: "",
                factoryCategoryCode: "",
                lastUpdateDate: "",
                lastUpdateBy: $scope.params.lastUpdateBy,
                invalidDate: ""
            };
            $scope.dataTable.data.unshift(supplierCategoryArray);
        };

        $scope.btnDel = function (row, index) {
            var categoryMaintainId = row.categoryMaintainId
            JS.confirm('删除', '是否确定删除？', '确定', function () {
                if (categoryMaintainId == null || categoryMaintainId == '') {
                    $scope.dataTable.data.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!已成功删除数据！", "success");
                }
            })
        };

        /**
         * 数据校验
         */
        $scope.dataChecked = function () {
            var tempDataTable = $scope.dataTable.data;
            var lineData = [];
            var count = 0;
            if (tempDataTable.length < 1) {
                JS.alert("没有数据，请选择条件搜索！", "warning", "确定");
                return false;
            }
            //只能处理单据行状态为“N”的数据
            for (var i = 0; i < tempDataTable.length; i++) {
                var tempObj = tempDataTable[i];
                if (tempObj.selectFlag == 'N' || tempObj.selectFlag == undefined) {
                    continue;
                }
                count++;
                lineData.push(tempObj)
            }
            if (count == 0) {
                JS.alert("请至少勾选一行数据", "warning", "确定");
                return false;
            }
            $scope.params.lineData = lineData;
            return true;
        };

        //保存
        $scope.btnSave = function () {
            // 选择选择的行进行保存
            if ($scope.dataChecked()) {
                for (var i = 0; i < $scope.params.lineData.length; i++) {
                    if (!$scope.params.lineData[i].categoryCodeFirst) {
                        JS.alert("一级分类不能为空！", "warning", "确定");
                        return;
                    }
                    if (!$scope.params.lineData[i].categoryCodeSecond) {
                        JS.alert("二级分类不能为空！", "warning", "确定");
                        return;
                    }
                    if (!$scope.params.lineData[i].categoryCodeThird) {
                        JS.alert("三级分类不能为空！", "warning", "确定");
                        return;
                    }
                    if (!$scope.params.lineData[i].factoryCategoryCode) {
                        JS.alert("生产工厂类型不能为空！", "warning", "确定");
                        return;
                    }
                }

                httpServer.post(URLAPI.saveSupplierCategoryList, {
                    'params': JSON.stringify($scope.params)
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.getData();
                        JS.alert("保存成功", "success", "确定");
                    }
                    else {
                        JS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            }
        }
    }]);
});



