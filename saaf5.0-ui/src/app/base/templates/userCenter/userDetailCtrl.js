/**
 * Created by dengdunxin on 2018/1/8.
 */
'use strict';
define(['app', 'pinyin', 'ztree','angularFileUpload'], function (app, pinyin, ztree,angularFileUpload) {
    app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('userDetailCtrl', function (wechatFind, $filter, Base64, deleteUserResp, findProfileSqlDatas,$window,
                                               saveUserResp, userSave, $timeout, $scope, $http, $location, $rootScope, $state, $stateParams, setNewRow, SIEJS, userFindById,URLAPI,httpServer,saveUserGroupAssignsForUser,deleteUserGroupAssignsForUser) {
        var id = $stateParams.id
        $scope.userId = $stateParams.id //3/28 add by gjc
        $scope.dataTable = [];
        $scope.rpParams = {}
        $scope.groupParams = {
            userId:id
        }

        $scope.wechatParams = {}
        $scope.logoImg = []
        $scope.params={
            inPassword:''
        }
        $scope.idModify= !$stateParams.id;
        $scope.respLovParam={
            userId:id*1,
            isEfficacious:'Y'
        }
        $scope.currentIsadmin = JSON.parse(sessionStorage[window.appName.toUpperCase() + '_successLoginInfo']).isadmin;

        $scope.btnSave = function () {
            if (!$filter("mobilePhone")($scope.params.phoneNumber) && $scope.params.phoneNumber != undefined && $scope.params.phoneNumber != '') {
                SIEJS.alert('手机号错误', 'warning');
                return
            }
            // if (!$scope.params.encryptedPassword){
            //     $scope.params.password = Base64.encode($scope.params.inPassword);
            // }
            //
            // if($scope.params.encryptedPassword && $scope.params.inPassword){
            //     $scope.params.password = Base64.encode($scope.params.inPassword);
            // }

            if ($scope.params.inPassword) {
              $scope.params.password = Base64.encode($scope.params.inPassword);
            }

            if(($scope.params.userType == "60" || $scope.params.userType ==66)&&($scope.params.emailAddress ==""||$scope.params.emailAddress ==undefined)){
                SIEJS.alert('供应商邮件地址不能为空', "error", "确定");
                return false;
            }
            if($scope.logoImg.length){
                $scope.params['userHeadImgUrl']=$scope.logoImg[0].accessPath
            }else{
                $scope.params['userHeadImgUrl']=''
            }
            if ($scope.current.em.key != undefined) {
                $scope.params.personId = $scope.current.em.key

            }

            $scope.params.profileValues = [];

            var profileList =[];
            if ($scope.profileData.length > 0) {
                for (var i = 0; i < $scope.profileData.length; i++) {
                    var row = $scope.profileData[i];
                    if ($scope.id > 0) {
                        $scope.params.profileValues.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue,
                            profileValueId: row.profileValueId,
                            versionNum: row.versionNum
                        })
                        if (row.profileCode == 'VENDOR' || row.profileCode == 'VENDOR_GROUP'){
                          profileList.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue
                          })
                        }
                    } else {
                        if (!row.profileValue){
                            SIEJS.alert('请选择Profile值','warning');
                            return;
                        }
                        $scope.params.profileValues.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue
                        })
                        if (row.profileCode == 'VENDOR' || row.profileCode == 'VENDOR_GROUP'){
                          profileList.push({
                            profileId: row.profileId,
                            profileValue: row.profileValue
                          })
                        }
                    }
                }
            }
            //用户姓名放入userFullName
            $scope.params.userFullName = $scope.params.personName;
			      // 如果用户类型为供应商类型
            if ($scope.params.userType == '60'){
              // 必须设置供应商或者供应商组数据
              if (isEmpty(profileList)){
                SIEJS.alert('请至少设置 一个 Profile为供应商或者供应商组的数据','warning');
                return;
              }
              // 必须设置权限规则
              if (isEmpty($scope.params.authorityRule)){
                SIEJS.alert('权限规则为必填项','warning');
                return;
              }
              // 必须设置数据时间段
              if (isEmpty($scope.params.dataTimePeriod)){
                SIEJS.alert('数据时间段为必填项','warning');
                return;
              }
            }
            // 权限规则
            var authorityRule = $scope.params.authorityRule;
            // 数据时间段
            var dataTimePeriod = $scope.params.dataTimePeriod;
            var saleAmtLimitedDay = $scope.params.saleAmtLimitedDay;
            userSave({params: JSON.stringify($scope.params)}, function (res) {

              if (res.status == "S") {
                id = res.data[0].userId;
                $scope.userId = res.data[0].userId;/*3/30 add by gjc*/
                $scope.search();
                //SIEJS.alert(res.msg, 'success')
              } else {
                SIEJS.alert(res.msg, 'error')
              }
              // 处理权限数据
              if (res.status == "S" && res.data[0].userType == '60'){
                var list = {};
                list.userId = id;
                list.authorityRule = authorityRule;
                list.dataTimePeriod = dataTimePeriod;
                list.saleAmtLimitedDay = saleAmtLimitedDay;
                for (var i = 0; i < $scope.customizeList.length; i++) {
                  $scope.customizeList[i].id = i +1;
                }

                list.customRules = $scope.customizeList;
                if (isEmpty($scope.supplierList)) {
                  httpServer.post(URLAPI.findSupplier, {
                    'params': JSON.stringify({profileValues: profileList, userId: $scope.params.userId})
                  }, function (res) {
                    if (res.status == 'S') {
                      list.supplierAuthority = res.data;
                      httpServer.post(URLAPI.saveAuthoritySupplier, {
                        'params': JSON.stringify(list)
                      }, function (res) {
                        if (res.status == 'S') {
                        //  SIEJS.alert('权限数据生成中', 'success');
                          if ($scope.params.authorityRule != '2') {
                            $scope.customizeList = [];
                          }
                          $scope.search();
                        }else {
                          SIEJS.alert(res.msg,'error');
                          $scope.search();
                        }
                      });
                    }
                  }, function (err) {
                    SIEJS.alert('权限数据后台生成中', "success", "确定");
                  });

                } else {
                  list.supplierAuthority = $scope.supplierList;
                  httpServer.post(URLAPI.saveAuthoritySupplier, {
                    'params': JSON.stringify(list)
                  }, function (res) {
                    if (res.status == 'S') {
                      // SIEJS.alert(res.msg, 'success')
                      if ($scope.params.authorityRule != '2') {
                        $scope.customizeList = [];
                      }
                      //SIEJS.alert('权限数据生成中', 'success');
                      if ($scope.params.authorityRule != '2') {
                        $scope.customizeList = [];
                      }
                      $scope.search();
                    }else {
                      SIEJS.alert(res.msg,'error');
                      $scope.search();
                    }
                  }, function (err) {
                    SIEJS.alert('权限数据后台生成中', "success", "确定");
                  });
                }
              }
            })
        }


      $scope.importResult = function importResult(){
        httpServer.post(URLAPI.findAuthorityImportResult, {
          'params': JSON.stringify({userId:$scope.userId})
        }, function (res) {
          if (res.status == 'S') {
            if (!res.data) {
              swal.close();
              window.clearInterval($scope.setInterval);
            }
          } else {
            console.log(res.msg);
          }
        }, function (err) {
          console.log(err.msg);
        });
      }


        $scope.btnDelResp = function (data) {
            data = data|| $scope.rpTable.selectRow;
            deleteUserResp({
                params: JSON.stringify({
                    id: data.respUserId
                })
            }, function (res) {
                $timeout(function(){
                    $scope.rpTable.search(1);
                },200);
                // $window.location.reload();
            })

        }

        $scope.cancelPassword = function () {
           // alert("cancelPassword");
            var paramsUserType = document.getElementById("inPassword");
            if($scope.params.userType==60|| $scope.params.userType ==66){
                $scope.params.inPassword = "0";
                $("#inPassword").attr("disabled", true);
            }else{
                $("#inPassword").attr("disabled", false);
            }
        }

        $scope.saveResp = function (data) {
            var ids = [];
            ids.push($scope.params.userId)
            saveUserResp({
                params: JSON.stringify({
                    actionType: 1,
                    userIds: ids,
                    responsibilityIds: $scope.current.rp.key,
                })
            }, function (res) {
                if (res.status == "S") {
                    $scope.rpParams.userId = $scope.params.userId
                    $scope.rpTable.search(1);
                    $scope.rpLov.search(1);
                    SIEJS.alert(res.msg, 'success')
                } else {
                    SIEJS.alert(res.msg, 'error')
                }
            })
        }

      $scope.saveResp1 = function (data) {

        var ids = [];
        ids.push($scope.params.userId)
        saveUserResp({
          params: JSON.stringify({
            actionType: 1,
            userIds: ids,
            responsibilityIds: $scope.current.rp1.key,
          })
        }, function (res) {
          if (res.status == "S") {
            $scope.rpParams.userId = $scope.params.userId
            $scope.rpTable.search(1);
            $scope.rpLov1.search(1);
            SIEJS.alert(res.msg, 'success')
          } else {
            SIEJS.alert(res.msg, 'error')
          }
        })
      }

        $scope.selectPersonReturn = function (key, value, currentList) {
            $scope.params.phoneNumber=currentList[0].mobilePhone

        }
        // if ($stateParams.id)  {
        //   $scope.findSupplierAuthorityHead();
        // }
        $scope.findSupplierAuthorityHead = function () {
          httpServer.post(URLAPI.findSupplierAuthorityHead, {
            'params': JSON.stringify({userId: id})
          }, function (res) {
            if (res.status == 'S') {
              setTimeout(function () {
                if (isEmpty($scope.params.authorityRule)){
                  $scope.params.authorityRule = res.data.ruleId.toString();
                }
                if (isEmpty($scope.params.dataTimePeriod)){
                  $scope.params.dataTimePeriod = res.data.dataTimePeriod.toString();
                }
                if (isEmpty($scope.params.saleAmtLimitedDay)){
                  $scope.params.saleAmtLimitedDay = res.data.saleAmtLimitedDay.toString();
                }
                if (typeof res.data.list == "undefined" || res.data.list == null || res.data.list == "") {
                  return;
                }
                if (isEmpty($scope.customizeList)) {
                  $scope.customizeList = res.data.list;
                }
              }, 250);
            }
          });
        }

        $scope.search = function () {
            userFindById({params: JSON.stringify({id: parseInt(id)})}, function (res) {
                if (res.status == "S") {
                    var authorityRule =  $scope.params.authorityRule;
                    var dataTimePeriod =  $scope.params.dataTimePeriod;
                    $scope.params = res.data[0];
                    $scope.params.authorityRule = authorityRule;
                    $scope.params.dataTimePeriod = dataTimePeriod;
                    if($scope.params.userType == 60){
                        $("#inPassword").attr("disabled", true);
                    }

                    if($scope.params.userHeadImgUrl!=undefined&&$scope.params.userHeadImgUrl!=''){
                        $scope.logoImg=[{
                            accessPath:$scope.params.userHeadImgUrl
                        }]
                    }
                    //$scope.params.userHeadImgUrl!=''?[$scope.params.userHeadImgUrl]:[]
                    $scope.profileData = $scope.params.profileValues || [];
                    $scope.profileData.map(function (item) {
                        findProfileSqlDatas({params: JSON.stringify({profileId: item.profileId})}, function (res) {
                            if (res.status === 'S') {
                                item.option = res.data;
                            }
                        })

                        var requestUrl = '';
                        if (item.profileCode=='VENDOR') {
                            requestUrl=URLAPI.baseVendorProfilefind
                        }else if (item.profileCode=='VENDOR_GROUP'){
                            requestUrl=URLAPI.baseVendorGroupProfilefind
                        }else if (item.profileCode=='VENDOR_EXTERAL') {
                          requestUrl = URLAPI.baseVendorExteralProfilefind
                        }else {
                            return;
                        }
                        if (requestUrl){

                            httpServer.post(requestUrl, {
                                params: {'vendorNbr':item.profileValue}
                            }, function (res) {
                                if ('S' == res.status && res.data && res.data.length>0) {
                                    item.profileValueTxt=res.data[0].vendorName;
                                    // 如果是供应商，则加多一个coed显示
                                    if (item.profileCode=='VENDOR') {
                                      item.profileValueCode=res.data[0].vendorNbr;
                                    }
                                }
                            }, function (error) {
                                console.error(error);
                            })
                        }


                    })

                    $scope.rpParams.userId = $scope.wechatParams.id = parseInt(id);

                    //$scope.wechatTable.search();
                    /*setTimeout(function (){
                    $scope.rpTable.search();
                    }, 800);*/
                    /*3/27 modify by gjc*/
                    /*start*/
                  if($scope.setInterval == undefined){
                    httpServer.post(URLAPI.findAuthorityImportResult, {
                      'params': JSON.stringify({userId:$scope.userId})
                    }, function (res) {
                      if (res.status == 'S') {
                        if (res.data) {
                          swal({
                            title:'',
                            text: '权限数据生成中',
                            showConfirmButton:false,
                            imageUrl:'img/loading1.gif',
                            imageSize: "37x37"
                          });
                          $scope.setInterval = window.setInterval($scope.importResult,2000);
                        }
                      } else {
                        console.log(res.msg);
                      }
                    }, function (err) {
                      console.log(err.msg);
                    });
                  }

                    $timeout(function () {
                        $scope.findSupplierAuthorityHead();
                        $scope.rpTable.search();
                        $scope.groupTable.search();
                    },800)
                    /*end*/

                }
                else {
                    SIEJS.alert(res.msg, 'error')
                }

            })

        }


        $scope.getName = function () {
            $('#nameLov').modal('toggle')
        }

        if (id)  {
            //修改
            $scope.search();
          /*  setTimeout(function () {
                $scope.rpParams.userId = $scope.wechatParams.id = parseInt(id)
                //$scope.wechatTable.search();
                $scope.rpTable.search();
            }, 500)*/
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

            var index = $.inArray(row, rows)
            if (!$scope.logoImg.hasOwnProperty('deletedImages')) {
                $scope.logoImg.deletedImages = [];
            }
            if (rows[index].hasOwnProperty('id')) {
                $scope.logoImg.deletedImages.push({'id': rows[index].id});
            }

            rows.splice(index, 1);

        }

        // 当前已选择的profile数据
        $scope.profileData = [];
        $scope.setProfile = function () {
            var list = angular.copy($scope.profileList.selectRow);
            findProfileSqlDatas({params: JSON.stringify({profileId: list.profileId})}, function (res) {
                if (res.status === 'S') {
                    list.option = res.data;
                    $scope.profileData.push(list);
                }else{
                SIEJS.alert(res.msg, 'error');
            }
            })
        };
        // 删除当前关联profile
        $scope.btnDelProfile = function (index) {
            index=index || $scope.currentProfileRow.index;
            $scope.profileData.splice(index, 1);
        };

        // profile 选择行
        $scope.profileRow=function(item,index){
            $scope.currentProfileRow = item;
            $scope.currentProfileRow.index = index;
        }

        /*********************查询组织信息************************/
        $scope.getServiceStoreInfo = function () {
            $('#serviceStoreInfo').modal('toggle')
        };
        $scope.selectServiceStore = function (key, value, currentList) {
            $scope.params.serviceStoreId = currentList[0].storeId;
            $scope.params.serviceStoreName = currentList[0].storeName;
        };


        //选择部门
        $scope.getGroupCodeInfo = function () {
            //  $scope.areaComponent = e;
            $('#selectDeptId').modal('toggle')
        };

        //点击确认按钮
        $scope.selectDeptReturn = function (key, value, currentList) {
            $scope.params.groupCode = currentList[0].departmentCode;
            $scope.params.groupName = currentList[0].departmentName;
        }



        //供应商profile
        $scope.getProfileVal=function(){

        }

        $scope.vendorCallback=function () {
            $scope.profileData[$scope.currentProfileRow.index ].profileValueTxt = $scope.vendorLov.selectRow.vendorName;
            // 如果是供应商，则加多一个coed显示
            $scope.profileData[$scope.currentProfileRow.index ].profileValueCode = $scope.vendorLov.selectRow.vendorNbr;
            $scope.profileData[$scope.currentProfileRow.index ].profileValue = $scope.vendorLov.selectRow.vendorNbr;

        }

        $scope.vendorGroupCallback=function () {
            $scope.profileData[$scope.currentProfileRow.index ].profileValueTxt = $scope.vendorGroupLov.selectRow.vgHeadName;
            $scope.profileData[$scope.currentProfileRow.index ].profileValue = $scope.vendorGroupLov.selectRow.vgHeadName;

        }

      $scope.vendorExteralCallback=function () {
        $scope.profileData[$scope.currentProfileRow.index ].profileValueTxt = $scope.vendorExteralLov.selectRow.supplierName;
        $scope.profileData[$scope.currentProfileRow.index ].profileValue = $scope.vendorExteralLov.selectRow.supplierCode;

      }

        /*gjc start*/
        $scope.btnNewGroup = function () {
            if (!$scope.userId){
                SIEJS.alert('请先保存数据','warning');
                return
            } else {
                $scope.groupLov.search()
                $('#groupLov').modal('toggle')

            }

        }
        $scope.groupCallback = function (key,value,currentList) {
            if (currentList.length === 0){
                return
            }
            var params = {
                userGroups:[],
                userId:id
            }
            currentList.map(function (value) {
                if (!$scope.groupTable.list.find(item => item.userGroupCode === value.lookupCode)) {
                    params.userGroups.push({userGroupCode:value.lookupCode,userGroupName:value.meaning})
                }

            })
            $scope.dealGroupFunc(params,saveUserGroupAssignsForUser,'新增成功','已自动过滤曾经添加的群组')

        }
        $scope.dealGroupFunc = function (params,server,successmessage,tipMessage) {
            server(params, function (res) {
                if (res.status === 'S') {
                    SIEJS.alert(successmessage,'success','确认',tipMessage);
                    $scope.groupTable.search()
                    $scope.groupTable.cancel()
                }else{
                    SIEJS.alert(res.msg || '添加失败', 'error');
                }
            })

        }

        $scope.btnDelGroup = function () {
            var params = {
                ids:[],
                userId:id
            }
            $scope.groupTable.selectRowList.map(function (item) {
                params.ids.push(item.assignId)
            })
            $scope.dealGroupFunc(params,deleteUserGroupAssignsForUser,'删除成功')
        }
        /*gjc end*/



       /* -- 供应商数据权限 -- */
        // 判空处理
        function isEmpty(obj) {
          if (typeof obj == "undefined" || obj == null || obj == "") {
            return true;
          } else {
            return false;
          }
        }

       $scope.supplierList = [];
       $scope.dataAuthoritySupplier = function () {
           $scope.params.profileValues = [];
           if ($scope.profileData.length == 0) {
             $scope.supplierList = [];
           }
           for (var i = 0; i < $scope.profileData.length; i++) {
             var row = $scope.profileData[i];
             if ($scope.id > 0) {
               $scope.params.profileValues.push({
                 profileId: row.profileId,
                 profileValue: row.profileValue,
                 profileValueId: row.profileValueId,
                 versionNum: row.versionNum
               })
             } else {
               if (!row.profileValue){
                 $scope.supplierList = [];
                 return;
               }
               $scope.params.profileValues.push({
                 profileId: row.profileId,
                 profileValue: row.profileValue
               })
             }
          };
          httpServer.post(URLAPI.findSupplier, {
           'params': JSON.stringify({profileValues: $scope.params.profileValues,userId:$scope.params.userId})
          }, function (res) {
             if (res.status == 'S') {
               $scope.supplierList = res.data;
             }
         });
       }

      $scope.saveAuthoritySupplier = function () {
        $scope.params.supplierAuthority = $scope.supplierList;
        $scope.params.customRules = $scope.customizeList;
        httpServer.post(URLAPI.saveAuthoritySupplier, {
          'params': JSON.stringify($scope.params)
        }, function (res) {
          if (res.status == 'S') {
            SIEJS.alert('保存成功');
          }else {
            SIEJS.alert(res.msg,'error');
          }
        });

      }

       $scope.indenticalInd = function (index) {
         var rows = $scope.tableEditData.getSelectedRows();// 获取详细数据
         if (rows.length == 0){
           SIEJS.alert('请选择需要置' + index + '的供应商','warning');
           return;
         }
         rows.forEach(function (val) {
           val.indenticalInd = index;
         });
       }

      $scope.customizeList = [];
      $scope.customizeTableData = [];
      $scope.btnNew = function () {
        var data = {
        };
        $scope.customizeList.push(data);
      }

      // $scope.btnDel = function (e) {
      //   var rows = $scope.customizeTableData.getSelectedRows();// 获取详细数据
      //   console.log(rows);
      //   //var sequence = $scope.customizeTableData.getSelectedRowsQCS(); // 获取页面序号
      //   var sequence = []
      //   for (var i = 0; i < rows.length; i++) {
      //     if (rows[i].isChecked == 'Y') {
      //       sequence.push(i)
      //     }
      //   }
      //   console.log(sequence);
      //   if (sequence.length == 0) {
      //     SIEJS.alert("请选择需要删除的数据", 'error');
      //     return;
      //   }
      //   for (var i = sequence.length - 1; i >= 0; i--) {
      //     console.log(i);
      //     console.log(sequence[i]);
      //     $scope.customizeList.splice(sequence[i], 1);
      //   }
      //   e.stopPropagation()
      // }


      $scope.btnDel = function (e) {
        var rows = $scope.tableEditData.getSelectedRows();// 获取详细数据
        console.log(rows);
        var sequence = []; // 获取页面序号
        if ($scope.customizeList && Array.isArray($scope.customizeList))
          for (var i = 0; i < $scope.customizeList.length; i++) {
            if ($scope.customizeList[i].isChecked == 'Y') {
              sequence.push(i)
            }
          }
        console.log(sequence);
        var row = [];
        if (sequence.length == 0) {
          JS.alert("请选择需要删除的数据", 'error', '确定');
          return;
        }

        for (var i = sequence.length - 1; i >= 0; i--) {
          $scope.customizeList.splice(sequence[i], 1);
        }
        e.stopPropagation()
      }

      $scope.cleanType  = function(index,type,list){
        // type 为EX 时，SEQ 则需清空
        if (type == 'EX'){
          $scope.customizeList[index].seqNo = "";
        }
      }

      $scope.cleanItemType = function(index,itemType,list){
        $scope.customizeList[index].groups = "";
        $scope.customizeList[index].dept = "";
        $scope.customizeList[index].classes = "";
        $scope.customizeList[index].subClass = "";
        $scope.customizeList[index].item = "";
        $scope.customizeList[index].brandCn = "";
        $scope.customizeList[index].brandEn = "";
      }


      /* -- 分组搜索start --*/
      $scope.getItemGroup = function (index) {
        $scope.index = index;
        $('#itemGroupLov').modal('toggle');
        $scope.itemGroupLov.reset();
      }
      // 分组回调
      $scope.itemGroupCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].groups = currentList[0].groupCode;
        $scope.customizeList[$scope.index].dept = "";
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
      }
      $scope.groupCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].groups = "";
        $scope.customizeList[$scope.index].dept = "";
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
      }


      /* -- 部门搜索 -- */
      $scope.getDept = function (index) {
        if (isEmpty( $scope.customizeList[index].groups)){
          SIEJS.alert("请先选择Group后 再选择Class", 'warning');
          return;
        }
        $scope.index = index;
        $scope.deptLov.reset();
        setTimeout(function () {
          $scope.deptLov.search();
        }, 500);
        $('#deptLov').modal('toggle')
      }
      $scope.deptCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].dept = currentList[0].dept;
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
      }

      $scope.deptCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].dept = "";
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
      }



      /* -- class搜索 -- */
      $scope.getClass = function (index) {
        if (isEmpty( $scope.customizeList[index].dept)){
          SIEJS.alert("请先选择Dept后再选择Class", 'warning');
          return;
        }
        $scope.index = index;
        $scope.classLov.reset();
        setTimeout(function () {
          $scope.classLov.search();
        }, 500);
        $('#classLov').modal('toggle')
      }
      $scope.classCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].classes = currentList[0].classes;
        $scope.customizeList[$scope.index].subClass = "";
      }
      $scope.classCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
      }

      /* -- subClass搜索 -- */
      $scope.getSubClass = function (index) {
        if (isEmpty( $scope.customizeList[index].classes)){
          SIEJS.alert("请先选择Class后再选择Class", 'warning');
          return;
        }
        $scope.index = index;
        $scope.subClassLov.reset();
        setTimeout(function () {
          $scope.subClassLov.search();
        }, 500);
        $('#subClassLov').modal('toggle')
      }
      $scope.subClassCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].subClass = currentList[0].subClass;
      }
      $scope.subClassCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].subClass = "";
      }

      // 货品
      $scope.getItem = function (index) {
        $scope.index = index;
        $('#itemLov').modal('toggle')
        $scope.itemLov.reset();
      }

      $scope.itemBlur = function(index){
        if (isEmpty($scope.customizeList[index].item)){
          $scope.customizeList[$scope.index].dept = "";
          $scope.customizeList[$scope.index].classes = "";
          $scope.customizeList[$scope.index].subClass = "";
          $scope.customizeList[$scope.index].groups ="";
          $scope.customizeList[$scope.index].brandCn = "";
          $scope.customizeList[$scope.index].brandEn = "";
          return;
        }
        httpServer.post(URLAPI.findItem, {
          'params': JSON.stringify({item: $scope.customizeList[index].item})
        }, function (res) {
          if (res.status == 'S') {
            if (res.count == 0){
              $scope.customizeList[$scope.index].dept = "";
              $scope.customizeList[$scope.index].classes = "";
              $scope.customizeList[$scope.index].subClass = "";
              $scope.customizeList[$scope.index].groups ="";
              $scope.customizeList[$scope.index].brandCn = "";
              $scope.customizeList[$scope.index].brandEn = "";
              return;
            }
            $scope.customizeList[index].dept = res.data[0].dept;
            $scope.customizeList[index].classes = res.data[0].classes;
            $scope.customizeList[index].subClass = res.data[0].subClass;
            $scope.customizeList[index].groups = res.data[0].groups;
            $scope.customizeList[index].brandCn = res.data[0].brandCn;
            $scope.customizeList[index].brandEn = res.data[0].brandEn;
          }
        });
      }

      $scope.itemCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].item = currentList[0].item;
        $scope.customizeList[$scope.index].dept = currentList[0].dept;
        $scope.customizeList[$scope.index].classes = currentList[0].classes;
        $scope.customizeList[$scope.index].subClass = currentList[0].subClass;
        $scope.customizeList[$scope.index].groups = currentList[0].groups;
        $scope.customizeList[$scope.index].brandCn = currentList[0].brandCn;
        $scope.customizeList[$scope.index].brandEn = currentList[0].brandEn;
      }

      $scope.itemCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].item = "";
        $scope.customizeList[$scope.index].dept = "";
        $scope.customizeList[$scope.index].classes = "";
        $scope.customizeList[$scope.index].subClass = "";
        $scope.customizeList[$scope.index].groups ="";
        $scope.customizeList[$scope.index].brandCn = "";
        $scope.customizeList[$scope.index].brandEn = "";
      }

      // 品牌
      $scope.getBrand = function (index) {
        $scope.index = index;
        $('#brandLov').modal('toggle')
      }
      $scope.brandBlur = function (index) {
        if (isEmpty($scope.customizeList[index].brandCn)){
          $scope.customizeList[index].brandEn ="";
          $scope.customizeList[index].brandCnId ="";
          $scope.customizeList[index].brandCnValue ="";
          $scope.customizeList[index].brandEnId  ="";
          $scope.customizeList[index].brandEnValue ="";
          return;
        }
        httpServer.post(URLAPI.findBrand, {
          'params': JSON.stringify({brand: $scope.customizeList[index].brandCn})
        }, function (res) {
          if (res.status == 'S') {
            if (res.count == 0){
              $scope.customizeList[index].brandEn ="";
              $scope.customizeList[index].brandCnId ="";
              $scope.customizeList[index].brandCnValue ="";
              $scope.customizeList[index].brandEnId  ="";
              $scope.customizeList[index].brandEnValue ="";
              return;
            }
            $scope.customizeList[index].brandEn = res.data[0].brandnameEn;
            $scope.customizeList[index].brandCnId = res.data[0].brandCnId;
            $scope.customizeList[index].brandCnValue = res.data[0].brandCnValue
            $scope.customizeList[index].brandEnId  = res.data[0].brandEnId;
            $scope.customizeList[index].brandEnValue = res.data[0].brandEnValue;
          }
        });
      }
      $scope.brandCallback = function (key, value, currentList) {
        $scope.brandLov.reset();
        $scope.customizeList[$scope.index].brandCn = currentList[0].brandnameCn;
        $scope.customizeList[$scope.index].brandCnId = currentList[0].brandCnId;
        $scope.customizeList[$scope.index].brandCnValue = currentList[0].brandCnValue
        ;
        $scope.customizeList[$scope.index].brandEn = currentList[0].brandnameEn;
        $scope.customizeList[$scope.index].brandEnId = currentList[0].brandEnId;
        $scope.customizeList[$scope.index].brandEnValue = currentList[0].brandEnValue
      }
      $scope.brandCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].brandCn = "";
        $scope.customizeList[$scope.index].brandEn = "";

        $scope.customizeList[$scope.index].brandCnId = "";
        $scope.customizeList[$scope.index].brandCnValue = "";
        $scope.customizeList[$scope.index].brandEnId  = "";
        $scope.customizeList[$scope.index].brandEnValue = "";
      }

      // // 分组
      // $scope.getItemGroup = function (index) {
      //   $scope.index = index;
      //   $('#itemGroupLov').modal('toggle')
      // }
      // $scope.itemGroupCallback = function (key, value, currentList) {
      //   $scope.customizeList[$scope.index].groups = currentList[0].groupCode;
      // }

      $scope.storeParams = [];




      // $scope.cleanDeliverySourceWh = function () {
      //   alert("清理成功");
      //   $scope.customizeList[$scope.index].deliverySourceWh = "";
      // }

      $scope.getDeliverySourceWh = function (index) {
        $scope.index = index;
        $('#deliverySourceWhLov').modal('toggle')
        $scope.deliverySourceWhLov.reset();
        // if ($scope.storeParams[index] == "undefined"){
        //   $scope.storeParams[index] = {};
        // }
      }

      $scope.deliverySourceWhCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].deliverySourceWh = currentList[0].vhCode;
      }
      $scope.deliverySourceWhCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].deliverySourceWh = "";
      }



      $scope.areaDisabled = false;
      $scope.provinceDisabled = false;
      $scope.cityDisabled = false;
      // 地区
      $scope.getArea = function (index) {
        $scope.index = index;
        $scope.areaLov.reset();
        setTimeout(function () {
          $scope.areaLov.search();
        }, 500);
        $('#areaLov').modal('toggle')
      }
      $scope.areaCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].area = currentList[0].areaCh;
        $scope.provinceDisabled = true;
        $scope.cityDisabled = true;
        // $scope.customizeList[$scope.index].market ="";
        // $scope.customizeList[$scope.index].store ="";
      }
      $scope.areaCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].area = "";
      }


      // 省
      $scope.getProvince = function (index) {
        $scope.index = index;
        $scope.provinceLov.reset();
        setTimeout(function () {
          $scope.provinceLov.search();
        }, 500);
        $('#provinceLov').modal('toggle')
      }
      $scope.provinceCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].province = currentList[0].regionCh;
        // $scope.customizeList[$scope.index].market ="";
        // $scope.customizeList[$scope.index].store ="";
        $scope.provinceDisabled = true;
        $scope.cityDisabled = true;
        //
        // httpServer.post(URLAPI.findArea, {
        //   'params': JSON.stringify({warehouse: $scope.customizeList[$scope.index].deliverySourceWh,province: $scope.customizeList[$scope.index].province})
        // }, function (res) {
        //   if (res.status == 'S') {
        //     $scope.customizeList[$scope.index].area = res.data[0].areaCh;
        //   }
        // });

      }
      $scope.provinceCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].province ="";
      }

      // 市
      $scope.getCity = function (index) {
        $scope.index = index;
        $scope.cityLov.reset();
        setTimeout(function () {
          $scope.cityLov.search();
        }, 500);
        $('#cityLov').modal('toggle')
      }

      $scope.cityCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].city = currentList[0].cityCh;
        // httpServer.post(URLAPI.findProvince, {
        //   'params': JSON.stringify({warehouse: $scope.customizeList[$scope.index].deliverySourceWh,
        //     city: $scope.customizeList[$scope.index].city})
        // }, function (res) {
        //   if (res.status == 'S') {
        //     $scope.customizeList[$scope.index].province = res.data[0].regionCh;
        //     httpServer.post(URLAPI.findArea, {
        //       'params': JSON.stringify({warehouse: $scope.customizeList[$scope.index].deliverySourceWh,province: $scope.customizeList[$scope.index].province,
        //         city: $scope.customizeList[$scope.index].city})
        //     }, function (res) {
        //       if (res.status == 'S') {
        //         $scope.customizeList[$scope.index].area = res.data[0].areaCh;
        //       }
        //     });
        //   }
        // });
      }

      $scope.cityCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].city ="";
      }

      // 市场
      $scope.getMarket = function (index) {
        $scope.index = index;
        $scope.marketLov.reset();
        setTimeout(function () {
          $scope.marketLov.search();
        }, 500);
        $('#marketLov').modal('toggle')
      }
      $scope.marketCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].market = currentList[0].market;
      }
      $scope.marketCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].market ="";
      }

      // 仓库
      $scope.getVhCode = function (index) {
        $scope.index = index;
        $scope.vhCodeLov.reset();
        setTimeout(function () {
          $scope.vhCodeLov.search();
        }, 500);
        $('#vhCodeLov').modal('toggle')
      }
      $scope.vhCodeCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].wh = currentList[0].vhCode;
      }
      $scope.vhCodeCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].wh ="";
      }

      // 门店
      $scope.getVsCode = function (index) {
        $scope.index = index;
        $scope.vsCodeLov.reset();
        setTimeout(function () {
          $scope.vsCodeLov.search();
        }, 500);
        $('#vsCodeLov').modal('toggle')
      }
      $scope.vsCodeCallback = function (key, value, currentList) {
        $scope.customizeList[$scope.index].store = currentList[0].vsCode;
      }
      $scope.vsCodeCancel = function (key, value, currentList) {
        $scope.customizeList[$scope.index].store ="";
      }

    });
});
