
define(["app","angularFileUpload"], function (app) {
	  app.useModule('angularFileUpload');//按需加载弹出层模块
    app.controller('addObGoodsCtrl', function ($scope, URLAPI, $rootScope, $state, $stateParams, iframeTabAction, httpServer,FileUploader,SIEJS) {
        //do something here

        $scope.params={
        	productStatus:'TO_BE_INTRODUCED'
            //systemCode:appName.toUpperCase()
        }
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }


  $scope.btnNew=function()
  {
	   if ($scope.dataTable.selectRowList.length == 0){
           SIEJS.alert('请选择数据','warning');
           return;
       }
       else
       {

    	   $scope.allData = {
                   oblist: $scope.dataTable.selectRowList
               }
               var datastr=JSON.stringify($scope.allData);
    	      // alert(datastr);
               httpServer.post(URLAPI.addobProduct, {
                   'params': datastr
               }, function (res) {
                   if (res.status == 'S') {                  	
                   	SIEJS.alert("操作成功", "success", "确定"); 
                   	 $scope.search(1);
                   	 $scope.dataTable.cancel();
                   	 
                   	iframeTabAction('待处理商品', '/productlist');
                  }
                   else {
                   	SIEJS.alert(res.msg, "error", "确定");
 
                 }
               }, function (err) {
               	SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");                 
              },true);
      }
        
  }
        
        
        

    });
});
