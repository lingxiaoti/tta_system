/**
 * Created by Administrator on 2016/9/26.
 */
define(["app", "angularFileUpload"], function (app) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('baseSSODetailCtrl',
        function saafTableController($scope, $parse, $filter, arrayDeleteItem, SIEJS, httpServer, URLAPI, $stateParams) {

            var ssoId = $stateParams.id;
            $scope.logoImg = []
            $scope.dataTable = [];
            $scope.getOrderNo = function () {
                httpServer.post(URLAPI.getOrderNo, {
                        'params': JSON.stringify({}),
                    },
                    function (res) {
                        // console.info(res);
                        if (res.status == 'S') {
                            $scope.params.orderNo = res.data
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    }

                    ,
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                )
            }

            $scope.search = function (ssoId) {

                httpServer.post(URLAPI.SSOFind, {
                        'params': JSON.stringify(
                            {
                                sysSsoId: parseInt(ssoId)
                            }
                        ),
                        pageRows: 13,
                        pageIndex: 1
                    },
                    function (res) {
                        // console.info(res);
                        if (res.status == 'S') {
                            $scope.params = res.data[0];


                            if ($scope.params.imageUrl) {
                                $scope.logoImg.push({accessPath: $scope.params.imageUrl});

                            }

                            if ($scope.params != undefined)
                                $scope.dataTable = JSON.parse($scope.params.params)
                        }
                        else {
                            SIEJS.alert(res.msg, "error", "确定");
                        }
                    }

                    ,
                    function (err) {
                        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    }
                )


                ;
            }
            $scope.addCheck = true;
            $scope.updateCheck = true;


            if (ssoId == null || ssoId == "" || ssoId == undefined || ssoId == "undefined") {
                //新增
                console.log(ssoId)
                // $scope.getOrderNo();
            }
            else {
                $scope.search(ssoId);
            }

            $scope.paramsIndex = 1;
            //增加按钮
            $scope.add = function () {
                var Array = {
                    key: "",
                    value: "",
                    tab: '',
                    index: $scope.paramsIndex++
                };
                $scope.dataTable.push(Array);
            }


            //删除职责分配
            $scope.deleteAction = function (item, index) {

                SIEJS.confirm("您确定要删除吗 ?", '', '确定', function () {
                    $scope.dataTable.splice(index, 1);
                })
            }


            //保存
            $scope.save = function () {
                var paramsArr = [];
                $scope.dataTable.map(function (item) {
                    var params = {}
                    params[item.key] = item.value;
                    paramsArr.push(item)
                })
                $scope.params.params = JSON.stringify(paramsArr)
                if ($scope.logoImg.length > 0) {
                    $scope.params.imageUrl = $scope.logoImg[0].accessPath
                } else {

                    $scope.params.imageUrl ='';
                }
                httpServer.post(URLAPI.SSOSave, {
                    'params': JSON.stringify($scope.params)
                }, function (res) {
                    if (res.status == 'S') {

                        $scope.params = res.data
                        console.info(res);
                        SIEJS.alert("保存成功", "success", "确定");
                    }
                    else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                }, function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                });
            }


            //图片添加执行方法
            $scope.imgAddAction = function (rows, targetType, imgChannel, returnMessage) {

                var imgMessage = $.extend({}, returnMessage, {
                    "targetType": targetType,
                    "disabled": "Y",
                })
                rows.push(imgMessage);
            }

            //图片删除执行方法
            $scope.imGdeleteAction = function (rows, row) {
                $scope.logoImg = [];
                $scope.params.imageUrl = '';
                //rows.splice(index, 1);
            }


        });
});