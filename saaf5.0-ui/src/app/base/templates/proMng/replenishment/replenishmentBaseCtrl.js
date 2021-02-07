define(["app","angularFileUpload",'XLSX'], function (app, XLSX) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('replenishmentBaseCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
      //do something here
      $scope.params={}
      //搜索
      $scope.search = function () {
          $scope.dataTable.search(1);
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
             $('#saafLoading').hide();
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
              if( $scope.excelpath==filePath) {
                $scope.qd();
              }
      
               }//else
             
          };
          if (res.status == 'E') {
            $('#saafLoading').hide();
            SIEJS.alert("操作失败! "+res.msg, "error", "确定");
          };
      };	

      $scope.ZsFileFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };


      // 查找正运行窗体是否为当前窗口，若是执行一次查询
      // setInterval(function () {
      //     let isUpcomingActive = false;
      //     angular.forEach(window.parent.saafrootScope.saafHeadTab,function (value, key) {
      //         if(value.active===true&&value.name==='待处理商品'){
      //             isUpcomingActive = true;
      //         }else if(value.active===false) {
      //             isUpcomingActive = false;
      //         }
      //     });
      //     if($scope.windowActive===false&&isUpcomingActive===true){
      //         $scope.dataTable.search(1);
      //         $scope.windowActive = true;
      //     }
      // },2000);
      
      
      
      

       $scope.fileopen = function () {
         $('#filediv').modal('toggle');
       }

      
      $scope.qd=function(){
        console.log($scope.excelpath, '$scope.excelpath');
        if($scope.excelpath == undefined) {
          SIEJS.alert('请上传文件','erro',"确定");
        }else{
          $('#filediv').modal('toggle');
            httpServer.post(URLAPI.importWarehouse, {
              'params': JSON.stringify({'filepath':$scope.excelpath})
            },
            function(res) {
              if (res.status == 'S') {
                SIEJS.alert('上传成功','success',"确定");
              }else{
                SIEJS.alert(res.msg,'error',"确定");
              }
            });
        }
         
           
      }

      $scope.downloadTem = function() {
        window.location.href='app/base/templates/proMng/replenishment/baseModel.xls'
      }
  });
});
