
define(["app","angularFileUpload"], function (app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('modifyProListCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
      //do something here

      $scope.params={
          orderStatus:'8'
      }
      //搜索
      $scope.search = function () {
          $scope.dataTable.search(1);
      }

      $scope.btnDetail = function (item) {
        // if(item.orderStatus =='6') {
           iframeTabAction('商品详情:' + item.productName, '/productDetails/'+ item.productHeadId)
        // }else if(item.orderStatus =='4') {
        //     iframeTabAction('商品详情:' + item.productName, '/productDetails/'+ item.productHeadId)
        // }
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
      
      $scope.ZsFileFileUploader=new FileUploader({
        url: URLAPI.imgUpload,
          autoUpload: true
      });
      $scope.ZsFileFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
          if (res.status == 'S') {
             // SIEJS.alert("上传成功！", "success", "确定");
            var filePath=res.data[0].filePath;
            var filename=res.data[0].fileName;
             var length=filename.length;
             var index=filename.lastIndexOf(".")+1;
             
             var suffx=filename.substring(index,length);

             if(suffx!='xls'&&suffx!='xlsx')
               {
               SIEJS.alert("请选择excel文件！", "error", "确定");
               return;
               }
             else
               {
              $scope.excelpath=filePath;
      
               }//else
             
          };
          if (res.status == 'E') {
              SIEJS.alert("操作失败! "+res.msg, "error", "确定");
          };
      };	
      
      
      // 查找正运行窗体是否为当前窗口，若是执行一次查询
    //   var checkTab = setInterval(function () {
    //     console.log(456456);
    //     angular.forEach(window.parent.saafrootScope.saafHeadTab,function (value, key) {
    //         if(value.active===true&&value.name==='商品列表'){
    //             $scope.isUpcomingActive = true;
    //         }else if(value.active===false) {
    //             $scope.isUpcomingActive = false;
    //         }
    //     });
    //     },2000);

    //     if($scope.isUpcomingActive===true){
    //         console.log('do it');
    //         $scope.dataTable.search(1);
    //     }
      

       $scope.fileopen = function () {
         $('#filediv').modal('toggle');
       }

      
      $scope.qd=function()
      {
         $('#filediv').modal('toggle');
           httpServer.post(URLAPI.updateProductByExcel, {
               'params': JSON.stringify({'filepath':$scope.excelpath})
             },
             function(res) {
               if (res.status == 'S') {
                 SIEJS.alert('操作成功','success',"确定");
                 var list=res.data[0];
                 angular.forEach(list,function(obj,rowindex){  //循环发起流程
               	      var businesskey=obj.productHeadId+"_"+obj.versionNum;
               	      var no=obj.plmCode;
               	   $scope.liuchen(businesskey,no);
                 });
               }
               else
                 {
                 SIEJS.alert(res.msg,'error',"确定");
                 }
             });
           
      }
      
      
      $scope.liuchen = function (productid,no) {

          $scope.extend ={
                  "tasks_assignees":[]
              }
              $scope.variables = []; //流程变量
              //angular.forEach($scope.formParams, function (value, key) {
                 // $scope.variables.push({
                      //name: key,
                    //  type: 'string',
                  //    value: value
                //  });
              //});

              $scope.properties={
                  "menucode": "HTGL",
                  "opinion": ""
              };
              $scope.paramsBpm={
                  "extend":$scope.extend,
                  "variables":$scope.variables,
                  "properties":$scope.properties,
                  "processDefinitionKey": "PLM_FZ.-999", //流程唯一标识，需修改为对应业务表单流程唯一标识
                  "saveonly": false,
                  "businessKey": productid, //单据ID
                  "title": "商品修改", //流程标题，修改为当前业务信息
                  "positionId": 0,
                  "orgId": 0,
                  "userType": userInfo.userType,
                  "billNo": no
              }
              httpServer.post(URLAPI.processStart, {
                  'params': JSON.stringify($scope.paramsBpm)
              }, function (res) {
                  if (res.status == 'S') {
                     SIEJS.alert("单号"+no+"流程发起成功!", "success", "确定");
                  }
                  else {
                      SIEJS.alert(res.msg, "error", "确定");
                  }
              }, function (err) {
                  SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              });
      }


      // 如果为当前页，请求一遍查询接口
      setInterval(() => {
        angular.forEach(window.parent.saafrootScope.saafHeadTab,function (value, key) {
            if(value.active==false&&value.name=='商品列表'){
                $scope.isUpcomingActive = true;
            }else if(value.active==true&&value.name=='商品列表') {
                if($scope.isUpcomingActive ==true) {
                    $scope.dataTable.search(1);
                    $scope.isUpcomingActive = false;
                }
            }
        });
    }, 1000);

  });
});
