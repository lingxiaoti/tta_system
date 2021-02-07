
define(["app","angularFileUpload"], function (app) {
	  app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('productlistCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
        //do something here
        $scope.params={
            orderStatus: '0',
        }
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }
       
        $scope.btnDetail = function (item) {
         	var item = item || $scope.dataTable.selectRow;
            if(item.orderStatus == '1') {
                iframeTabAction('采购发起1:' + item.productName , '/addNotObGoods/'+ item.productHeadId)
            }else if(item.orderStatus == '2') {
                iframeTabAction('供应商完善2:' + item.productName ,'/supplierFinish/'+ item.productHeadId)
            }else if(item.orderStatus == '3') {
                iframeTabAction('采购完善3:' +item.productName, '/finishProcurement/'+ item.productHeadId)
            }else if(item.orderStatus == '4') {
                iframeTabAction('新增审批中:' + item.productName, '/finishProcurement/'+ item.productHeadId)
            }else if(item.orderStatus == '7') {
                iframeTabAction('修改审批中:' + item.productName, '/productDetails/'+ item.productHeadId)
            }
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



        // 如果为当前页，请求一遍查询接口
        setInterval(() => {
            angular.forEach(window.parent.saafrootScope.saafHeadTab,function (value, key) {
                if(value.active==false&&value.name=='待处理商品'){
                    $scope.isUpcomingActive = true;
                }else if(value.active==true&&value.name=='待处理商品') {
                    if($scope.isUpcomingActive ==true) {
                        $scope.dataTable.search(1);
                        $scope.isUpcomingActive = false;
                    }
                }
            });
        }, 1000);




         $scope.fileopen = function () {
        	 $('#filediv').modal('toggle');
         }

        
        $scope.qd=function()
        {
        	 $('#filediv').modal('toggle');
             httpServer.post(URLAPI.SaveProductByExcel, {
                 'params': JSON.stringify({'filepath':$scope.excelpath})
               },
               function(res) {
                 if (res.status == 'S') {
              	   SIEJS.alert('操作成功','success',"确定");
                 }
                 else
                	 {
                	 SIEJS.alert(res.msg,'error',"确定");
                	 }
               });
             
        }

        $scope.downloadTem = function() {
            window.location.href='app/base/templates/product/temple.xls'
        };
            
    });
});
