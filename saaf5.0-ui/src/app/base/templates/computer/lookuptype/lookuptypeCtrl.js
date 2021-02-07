/**
 * Created by dengdunxin on 2018/1/3.
 */
define(["app"], function (app) {
    app.controller('lookuptypeCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer) {
        //do something here

        $scope.params={
            systemCode:appName.toUpperCase()
        }
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        //修改
        $scope.btnModify = function () {
            //console.log($scope.dataTable.selectRow)
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var row = $scope.dataTable.selectRow;

            iframeTabAction('字典详情：' + row.lookupType, 'editLookuptype/' + row.lookupTypeId)
        }

        //删除
        $scope.btnDel = function () {
            if ($scope.dataTable.selectRow == null) {
                JS.alert('请您先选中要修改的行!!');
                return;
            }
            var lookupTypeId = $scope.dataTable.selectRow.lookupTypeId;


            JS.alert({
                title: "您确定要删除吗？",
                type: "warning",
                showCancelButton: true,
                closeOnConfirm: false,
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                confirmButtonColor: "#ec6c62"
            }, function () {
                httpServer.post(URLAPI.deleteLookupType, {
                    'params': JSON.stringify({id: lookupTypeId})
                }, function (res) {
                    if (res.status == 'S') {
                        $scope.dataTable.search();
                        // JS.alert(res.msg, 'success');
                        JS.alert(res.msg, "", "success");


                    }
                })

            });

        }
        $scope.btnNew = function (id) {
            iframeTabAction('新增字典', 'editLookuptype/')       	
        }//btnnew
    });
});
