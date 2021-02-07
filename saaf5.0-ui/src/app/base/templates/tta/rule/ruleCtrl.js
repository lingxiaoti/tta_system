/**
 *
 */
'use strict';
define(['app'], function (app) {
    app.controller('ruleCtrl', function ($scope, $timeout, SIEJS, $location, $rootScope, $state, $stateParams,
                                                       userRespList,httpServer,URLAPI) {


        $scope.usersAuthorityTable =[];
        $scope.usersAuthorityTable2 = [];
        $scope.userGroupDeptBrand = 'userGroupDeptBrand' ;
        $scope.currentList2 = [] ;
        $scope.flag2 = true ;
        /*监听currentList2*/
        $scope.$watch('usersAuthorityTable2.data', function (newVal, oldVal) {
            if (newVal === oldVal || !newVal || !$scope.flag2){
                if(!$scope.flag2){
                    $scope.flag2 = true ;
                }
                return;
            }
            $timeout(function () {
                $scope.currentList2 = [] ;
            })
        }, true);

        /******************************----newStart------------***************************************/
        /**
         * 保存
         */
        $scope.btnSave = function (value) {
            if('2' === value){
                $scope.btnSaveLeft();
            }else{
                $scope.btnSaveRight('no');
            }
        }

        $scope.btnSaveLeft = function (){
            httpServer.post(URLAPI.userSaveAll,
                {params: JSON.stringify({"userList":$scope.usersAuthorityTable.data})},
                function (res) {
                    if (res.status == "S") {
                        $scope.usersAuthorityTable.getData($scope.usersAuthorityTable.curIndex);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }

        $scope.btnSaveRight = function (value,date){
            httpServer.post(URLAPI.userGroupSave,
                {params: JSON.stringify(
                        {"userGroupDeptBrand":value === "no"?$scope.usersAuthorityTable2.data:$scope.currentList2,
                            "flag":value,
                            "date":value === "no"? "":date.endTime
                        })
                },
                function (res) {
                    if (res.status == "S") {
                        if("new" === value ){
                            usersAuthorityTable.data.selectRowData.dataTypeFlag ='0';
                        }
                        $scope.usersAuthorityTable2.getData($scope.usersAuthorityTable2.curIndex);
                        SIEJS.alert(res.msg, 'success', '确定');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }


        /**
         * 新增
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selecGroupReturn = function (key, value, currentList) {
            if(!$scope.usersAuthorityTable.data.selectRowData || !$scope.usersAuthorityTable.data.selectRowData.userId){
                SIEJS.alert("请先选择对应的用户", "error", "确定");
                return;
            }
            if(currentList.length == 0){
                return ;
            }
            httpServer.post(URLAPI.userGroupSave,
                {params: JSON.stringify(
                        {"userGroupDeptBrand":currentList,
                            "userId":$scope.usersAuthorityTable.data.selectRowData.userId,
                            "flag":"new"
                        })},
                function (res) {
                    if (res.status == "S") {
                        $scope.usersAuthorityTable.data.selectRowData.dataTypeFlag = '0'
                        $scope.usersAuthorityTable2.getData();
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                    $("#saveButton").attr("disabled", true);
                }
            );
        }
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
                if (item.userGroupDeptBrandId === $scope.currentList2[i].userGroupDeptBrandId) {
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
        }



        $scope.save = function (invalid) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.btnSaveRight("current",$scope.addParams)
            $('#addEndTime').modal('toggle');
        }

        /******************************----newEnd-----------***************************************/

        $scope.selectUser = function () {
            $scope.userParams.customerId = $scope.currentCustomerId;
            $scope.userParams.selectFlag = true;
            $scope.userCustomerList($scope.userParams);
        }
        // 根据客户id查询已授权用户
        $scope.idParams = {};
        $scope.userCustomerList = function (item) {
            if (item.selectFlag) {
                console.log("selectFlag===" + item.selectFlag);
            } else {
                if ($scope.currentCustomerId === item.customerId) return; //防止重复点击单元格
            }
            //设置当前currentCustomerId
            $scope.currentCustomerId = item.customerId;
            $scope.idParams.customerId = item.customerId;
            $scope.idParams.userFullName_like = item.userFullName_like;
            $scope.idParams.userName_like = item.userName_like;
            var p = {
                params: JSON.stringify($scope.idParams)
            }
            customerUserAuthorityFind(p, function (res) {
                if (res.status === 'S') {
                    $scope.userTable = res.data;
                    // $scope.userTable.search(1,null,false);// 重载
                }
            });
        }

        //新增
        $scope.btnNew = function () {
            $scope.isModify=false;
            $scope.addParams = { };
            $('#addRule').modal('toggle');
        }

        $scope.save = function (invalid) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            httpServer.post(URLAPI.saveBaseRule,
                {params: JSON.stringify($scope.addParams)},
                function (res) {
                    if (res.status == "S") {
                        $scope.dataTable.search();
                        $('#addRule').modal('toggle');
                    } else {
                        SIEJS.alert(res.msg, "error", "确定");
                    }
                },
                function (err) {
                    SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
        }

        $scope.btnDel = function (item) {
            item = item || $scope.dataTable.selectRow;
;
            if (item) {
                var ids = [];
                ids.push(item)
                SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
                    httpServer.post(URLAPI.deleteBaseRule,
                        {params: JSON.stringify({ruleIds: ids})},
                        function (res) {
                            if (res.status == "S") {
                                $scope.dataTable.search();
                                SIEJS.alert('删除成功');
                            } else {
                                SIEJS.alert(res.msg, "error", "确定");
                            }
                        },
                        function (err) {
                            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                        }
                    );
                })
            }
        }

        $scope.btnModify = function () {
            $scope.isModify=true;
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $("#addRule :input").removeAttr("disabled");
            $("#addRule :button").removeAttr("disabled");
            $('#addRule').modal('toggle')
        }

        $scope.btnDetail = function () {
            $scope.isModify=true;
            var row = $scope.dataTable.selectRow;
            $scope.addParams = angular.copy(row);
            $("#addRule :input").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
            $("#addRule :button").not("[name='fullWindow']").not("[name='printButton']").attr("disabled","true");
            $('#addRule').modal('toggle')
        }
    });
});