/**
 * Created by dengdunxin on 2018/1/6.
 */
/**
 * Created by dengdunxin on 2018/1/3.
 */
define(['app', 'pinyin'], function (app, pinyin) {
    app.controller('btnListCtrl', ['$scope', 'httpServer', 'URLAPI', 'SIE.JS', '$http', '$state', 'iframeTabAction', 'buttonSave', 'buttonDetel', 'setNewRow',
        function ($scope, httpServer, URLAPI, JS, $http, $state, iframeTabAction, buttonSave, buttonDetel, setNewRow) {

        $scope.params = {};
        $scope.btnNew = function () {
            $scope.isModify=false;
            $scope.current = {}
            $('#btnListModal').modal('toggle')
        }


        // 转换拼音
        $scope.toPinyin = function () {
            if ($scope.current.bbdName != undefined)
                $scope.current.bbdCode = pinyin.shouxiePinyin($scope.current.bbdName);
        }
        $scope.btnModify = function () {
            $scope.isModify=true;
            $scope.current = angular.copy($scope.dataTable.selectRow);
            $('#btnListModal').modal('toggle')

        }

        $scope.btnDel = function () {
            var row = $scope.dataTable.selectRow
            JS.confirm('您确认要删除吗？', '', '确定', function () {
                buttonDetel(
                    {params: JSON.stringify({id: row.bbdId})}
                    , function (res) {
                        if (res.status === 'S') {
                            JS.alert("操作成功", "success", "确定");
                            $scope.dataTable.search();
                        } else {
                            JS.alert(res.msg, "error", "确定");
                        }
                    })
            })
        }
        $scope.confirmBtnList = function () {

            buttonSave(
                {params: JSON.stringify($scope.current)}
                , function (res) {
                    if (res.status === 'S') {

                        if (!$scope.current.bbdId) {// 没有Id则为新赠，重新查询数据
                            $scope.dataTable.search();
                        } else {
                            $scope.current=res.data[0];
                            setNewRow($scope.dataTable.selectRow, $scope.current);// 更新成功，只更新当前行，不必再去重载当前表格
                        }
                        //$scope.dataTable.search();

                        $('#btnListModal').modal('toggle')
                    }
                })
        }
    }])
})