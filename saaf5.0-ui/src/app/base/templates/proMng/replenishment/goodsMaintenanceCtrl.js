define(["app",'angularFileUpload'], function (app) {
  app.useModule('angularFileUpload');//按需加载上传
  app.controller('goodsMaintenanceCtrl',['$scope', 'httpServer', 'URLAPI', '$timeout','SIE.JS', '$compile', '$stateParams','FileUploader','iframeTabAction',
  function($scope, httpServer, URLAPI, $timeout, SIEJS, $compile, $stateParams,FileUploader,iframeTabAction) {
      //do something here
      $scope.params={}
      //搜索
      $scope.search = function () {
          $scope.dataTable.search(1);
      }

      // 新增
      $scope.btnNew = function() {
        iframeTabAction('新增申请单:' , '/newApplicationForm')
      }

      // 查询
      $scope.query = function() {
        httpServer.post(URLAPI.findPlmLineDetail, {
          'params': JSON.stringify({'plmSupplementLineId':$stateParams.productId})
        }, function (res) {
            if (res.status == 'S') {
              $scope.params = res;
              $scope.paramsLine = res.line;
              if($scope.paramsLine.stopReason == '4'||$scope.paramsLine.stopReason == '5'||$scope.paramsLine.stopReason == '6') {
                $scope.mustUpload = '1';
              }
            }
            else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        }, function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      }

      if(true) {
        $scope.query();
        $scope.orderStatus = sessionStorage.orderStatus;
      }

      

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
      

      // 上传获取当前行数
      $scope.getFileIndex = function(index) {
        $scope.filelistIndex = index;
      };
      //附件文件上传
      $scope.SFileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      //附件文件上传后调用的方法
      $scope.SFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
          var filename=res.data[0].fileName;
          var length=filename.length;
          var index=filename.lastIndexOf(".")+1;
          var suffx=filename.substring(index,length);
            SIEJS.alert("上传成功！", "success", "确定");
            $('#saafLoading').hide();
            $scope.params.file[$scope.filelistIndex].fileUrl = res.data[0].filePath; 
            $scope.params.file[$scope.filelistIndex].fileName = res.data[0].fileName; 
            $scope.params.file[$scope.filelistIndex].flags = res.data[0].fileSize; 
            $scope.params.file[$scope.filelistIndex].creationDate = res.data[0].uploadDate; 
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.SFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };
      

      // 删除
      $scope.del = function(index, item) {
        if(item.fileUrl!== '') {
          SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
            httpServer.post(URLAPI.deleteProductFileById, {
              'params': JSON.stringify({'fileId': item.fileId.toString()})
            }, function (res) {
                if (res.status == 'S') {
                  SIEJS.alert('删除成功', "success", "确定");
                }else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
            }, function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
          });
        }else{
          $scope.params.file.splice(index);
        }
      }
      // 添加
      $scope.addFileInfo = function() {
        var FileInfoDataobj = {
          flags: '',
          creationDate: '',
          fileRemarks: '',
          fileUrl: '',
          fileName: '',
        };
        if($scope.params.file == undefined) {
          $scope.params.file = [];
          $scope.params.file.push(FileInfoDataobj);
        }else{
          $scope.params.file.push(FileInfoDataobj);
        }
      }
      
      // 保存
      $scope.btnSave = function() {
        if($scope.mustUpload == '1') {
          SIEJS.alert('请上传附件', "erro", "确定");
        }else{
          httpServer.post(URLAPI.saveFile, {
            'params': JSON.stringify({'plmSupplementLineId':$stateParams.productId,'lines': $scope.params.file })
          }, function (res) {
              if (res.status == 'S') {
                $scope.params = res;
                $scope.query();
                SIEJS.alert('保存成功', "success", "确定");
              }
              else {
                SIEJS.alert(res.msg, "error", "确定");
              }
          }, function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          });
        }
      }


      // 重新上传
      $scope.UploaderReset = function(index, list) {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.params.file[index].fileUrl = '';
          });
      };

      // 上传文件预览
      $scope.previewImg = function (index) {
        var indexURL = $scope.params.file[index].fileUrl;
        window.open(indexURL);
      };
          
  }]);
});
