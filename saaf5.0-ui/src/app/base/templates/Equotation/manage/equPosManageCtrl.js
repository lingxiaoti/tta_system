/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('equPosManageCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'deleteLookupLine', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, deleteLookupLine) {

        $scope.dataTable = [];
        $scope.addParam = {}
        $scope.params = {}
        $scope.params.deptCode = JSON.parse(sessionStorage[appName + '_successLoginInfo']).deptCode;
        //-----------------------------------------------初始化--------------------------------------------



        //-----------------------------------------------删除--------------------------------------------
        $scope.deleteData = function (row,index) {
            var sceneManageId = row.sceneManageId
            JS.confirm('删除', '是否确定删除？', '确定', function () {
                if(sceneManageId == null || sceneManageId =='' ){
                    $scope.dataTable.data.splice(index, 1);             //新增列没有ID直接删除
                    JS.alert("操作成功!已成功删除数据！", "success");
                }
                else {
                    httpServer.post(URLAPI.deleteSceneManage, {
                        params: JSON.stringify(row)
                    }, function (res) {
                        if (res.status == "S") {
                            $scope.dataTable.data.splice(index, 1);
                            JS.alert("操作成功!已成功删除数据！", "success");
                        } else {
                            JS.alert(res.msg, 'error', '确定');
                            console.error(res);
                        }
                    }, function (error) {
                        console.error(error);
                    });
                }
            })
        }

        //-----------------------------------------------弹出--------------------------------------------

        $scope.add = function () {
            $scope.addParameters = {};
            $scope.addParameters.introduceLocationFlag = "Y";
            $("#addParameters").modal('toggle');
        };

        //-----------------------------------------------保存--------------------------------------------
        $scope.addParams = function () {
            if($scope.addParameters.qualifiedCensorFlag!='Y'&&$scope.addParameters.creditAuditFlag!='Y'&&$scope.addParameters.csrAuditFlag!='Y'&&
                $scope.addParameters.qualityAuditFlag!='Y'&&$scope.addParameters.qualifiedAuditFlag!='Y' ){
                JS.alert("至少选择一个审核项", 'error', '确定');
            }
            else{
                httpServer.post(URLAPI.findSceneManageLine, {
                    params: JSON.stringify($scope.addParameters)
                }, function (res) {
                    if (res.status == "S") {
                        if(res.data > 0 ){
                            JS.confirm('生效', '场景类型已存在，是否失效原设置，请确认！', '确定', function () {
                                $scope.saveParams();
                            })
                        }else{
                            $scope.saveParams();
                        }
                    } else {
                        JS.alert(res.msg, 'error', '确定');
                        console.error(res);
                    }
                }, function (error) {
                    console.error(error);
                });
            }
        }

        $scope.saveParams = function () {
            httpServer.post(URLAPI.saveSceneManage, {
                params: JSON.stringify($scope.addParameters)
            }, function (res) {
                if (res.status == "S") {
                    $("#businessModal").modal("hide");
                    $scope.dataTable.getData();
                    JS.alert('保存成功');
                    $("#addParameters").modal('toggle');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });

        }

        $scope.submit = function () {
            httpServer.post(URLAPI.findSceneManageLine, {
                params: JSON.stringify($scope.addParameters)
            }, function (res) {
                if (res.status == "S") {
                   if(res.data > 0 ){
                       JS.confirm('生效', '场景类型已存在，是否失效原设置，请确认！', '确定', function () {
                           $scope.submitDatil();
                       })
                   }else{
                       $scope.submitDatil();
                   }
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

        $scope.submitDatil = function () {
            httpServer.post(URLAPI.sumbitSceneManage, {
                params: JSON.stringify($scope.dataTable.data.selectRowData)
            }, function (res) {
                if (res.status == "S") {
                    $scope.dataTable.getData();
                    JS.alert('操作成功');
                } else {
                    JS.alert(res.msg, 'error', '确定');
                    console.error(res);
                }
            }, function (error) {
                console.error(error);
            });
        }

    }]);
});