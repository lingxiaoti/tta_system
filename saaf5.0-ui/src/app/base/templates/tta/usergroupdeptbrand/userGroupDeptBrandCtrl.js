/**
 *
 */
'use strict';
define(['app'], function (app) {
    app.controller('userGroupDeptBrandCtrl', function ($scope, $timeout, SIEJS, $location, $rootScope, $state, $stateParams,
                                                          userRespList,httpServer,URLAPI,iframeTabAction) {


        $scope.userPowerParams = {
            findFlag :'userGroupDeptBrand'
        };
        $scope.userPowerParamsDept = {
            findFlag :'findGroupDept'
        };
        $scope.userPowerParamsGroup = {
            findFlag :'findGroup'
        };

        $scope.usersAuthorityTable ={};
        $scope.usersAuthorityParams2 ={};
        $scope.usersAuthorityTable2 = {};
        $scope.findFlag = 'userGroupDeptBrand' ;
        $scope.currentList2 = [] ;
        $scope.flag2 = true ;

        /******************************----newStart------------***************************************/
        /**
         * 保存
         */
        $scope.btnSave = function (value) {
                if('2' === value){
                    $scope.btnSaveLeft();
                }else{
                    $scope.btnSaveRight('no','');
                }
        };

        $scope.btnSearch = function() {
            iframeTabAction('人员权限查询', '/userGroupDeptBrandSearch/-1');
        };
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
        };

        $scope.btnSaveRxpire = function (date,type){
            httpServer.post(URLAPI.userGroupSaveRxpire,
                {params: JSON.stringify(
                        {"userGroupDeptBrand":type === "checked"?$scope.currentList2:$scope.usersAuthorityTable2.data,
                            "flag":type,
                            "userId":$scope.usersAuthorityTable.data.selectRowData.userId,
                            "date":date.endTime
                        })
                },
                function (res) {
                    if (res.status == "S") {
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
        };

        $scope.btnSaveRight = function (value,date){
            httpServer.post(URLAPI.userGroupSave,
                {params: JSON.stringify(
                    {"userGroupDeptBrand":value === "no"?$scope.usersAuthorityTable2.data:($scope.currentList2.length == 0?$scope.usersAuthorityTable2.data:$scope.currentList2),
                      "flag":value,
                        "userId":$scope.usersAuthorityTable.data.selectRowData.userId,
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
        };
        $scope.afterGetData = function () {
            $scope.currentList2 = [] ;
        };

        /**
         * 新增
         * @param key
         * @param value
         * @param currentList
         */
        $scope.selecGroupReturn = function () {
            if(!$scope.usersAuthorityTable.data.selectRowData || !$scope.usersAuthorityTable.data.selectRowData.userId){
                SIEJS.alert("请先选择对应的用户", "error", "确定");
                return;
            }
            var list = [];
            if("1" == $scope.usersAuthorityTable.data.selectRowData.dataType){
                list = $scope.userPowerGroupTable.selectRowList;
                $('#userGroupLov').modal('toggle');
            }else if("2" == $scope.usersAuthorityTable.data.selectRowData.dataType){
                list = $scope.userPowerDeptTable.selectRowList;
                $('#userGroupDeptLov').modal('toggle');
            }else{
                list = $scope.userPowerTable.selectRowList;
                $('#userGroupDeptBrandLov').modal('toggle');
            }
            if(list.length == 0){
                return ;
            }
            httpServer.post(URLAPI.userGroupSave,
                {params: JSON.stringify(
                    {"userGroupDeptBrand":list,
                    "userId":$scope.usersAuthorityTable.data.selectRowData.userId,
                     "flag":"new",
                      "dataType":$scope.usersAuthorityTable.data.selectRowData.dataType
                    })},
                function (res) {
                    if (res.status == "S") {
                        $scope.usersAuthorityTable.data.selectRowData.dataTypeFlag = '0';
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
        };
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
        };

        $scope.SX = function () {
            var title = "" ;
            if($scope.currentList2.length== 0){
                title ="当前未选中，是否失效当前页面的所有数据";
            }else{
                title = "是否失效当前页面选中的数据"
            }
            $scope.addParams = {
                title:title
            };
            $('#addEndTime').modal('toggle')
        };
        $scope.btnPower = function () {
            $('#nameTtaUserGroupDeptBrandId').modal('toggle');
        };
        $scope.save = function (invalid,type) {

            if(invalid){
                SIEJS.alert('请检查必填项', 'error', '确定');
                return;
            }
            $scope.btnSaveRxpire($scope.addParams,type);
            $('#addEndTime').modal('toggle');
        };
        $scope.selectUserReturn = function (key, value, currentList) {
            httpServer.post(URLAPI.userGroupSavePower,
                {params: JSON.stringify(
                        {
                            "userId":$scope.usersAuthorityTable.data.selectRowData.userId,
                            "toUserId":currentList[0].userId,
                        })},
                function (res) {
                    if (res.status == "S") {
                        SIEJS.alert(res.msg, 'success', '确定');
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
        };
        /******************************----newEnd-----------***************************************/

        $scope.selectUser = function () {
            $scope.userParams.customerId = $scope.currentCustomerId;
            $scope.userParams.selectFlag = true;
            $scope.userCustomerList($scope.userParams);
        };
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
            };
            customerUserAuthorityFind(p, function (res) {
                if (res.status === 'S') {
                    $scope.userTable = res.data;
                    // $scope.userTable.search(1,null,false);// 重载
                }
            });
        };

        $scope.btnNew = function () {
            if(!$scope.usersAuthorityTable.data.selectRowData){
                SIEJS.alert('左边数据请点击选择一行！', 'warning', '确定');
                return ;
            }
            if(!$scope.usersAuthorityTable.data.selectRowData.dataType){
                SIEJS.alert('左边选中的行请先选择权限类型！', 'warning', '确定');
                return ;
            }

            $scope.userPowerGroupTable.selectRowList =[];
            $scope.userPowerDeptTable.selectRowList = [];
            $scope.userPowerTable.selectRowList = [];

            if("1" == $scope.usersAuthorityTable.data.selectRowData.dataType){
                $('#userGroupLov').modal('toggle');
            }else if("2" == $scope.usersAuthorityTable.data.selectRowData.dataType){
                $('#userGroupDeptLov').modal('toggle');
            }else{
                $('#userGroupDeptBrandLov').modal('toggle');
            }

        };
        $scope.rightSearch = function (){
            if( !$scope.usersAuthorityTable.data ||  !$scope.usersAuthorityTable.data.selectRowData){
                SIEJS.alert('左边数据请点击选择一行,再执行右边的查询！', 'warning', '确定');
                return ;
            }
            $scope.usersAuthorityParams2.userId =$scope.usersAuthorityTable.data.selectRowData.userId;
            $scope.usersAuthorityTable2.getData();
        }

    });
});