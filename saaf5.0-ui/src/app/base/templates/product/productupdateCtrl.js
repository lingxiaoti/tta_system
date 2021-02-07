/**
 * Created by zhuchaopeng on 2018/6/15.
 */
define(["app"], function (app) {
    app.controller('productupdateCtrl', ['$scope', 'URLAPI', '$rootScope', 'iframeTabAction','$state', '$stateParams', "SIE.JS", 'httpServer',function saafTableController($scope, URLAPI, $rootScope,iframeTabAction, $state, $stateParams,JS, httpServer) {
    	
        $scope.dataTable = {}
        var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
        //获取url参数对象
        $scope.urlParams = urlToObj(location.hash);
        
        //url参数转对象
        function urlToObj(url) {
            var index = url.lastIndexOf('?');
            var params = url.substring(index + 1);
            var arr = params.split('&');
            var obj = {};
            arr.forEach(function (item) {
                obj[item.split('=')[0]] = item.split('=')[1];
            });
            return obj;
        }
        
        if($scope.urlParams.businessKey)
        	{
        	$scope.params.productHeadId=$scope.urlParams.businessKey;
        	//alert($scope.urlParams.businessKey+"=="+$scope.urlParams.businessKey.split("_")[0]);
            //保存流程实例id
            $scope.businessparam={
          	productHeadId:$scope.urlParams.businessKey.split("_")[0],
          	instanceid:$scope.urlParams.processInstanceId,
          	businesskey:$scope.urlParams.businessKey,
          	type:'update'
            };
            httpServer.post(URLAPI.saveProductInstanceById, {
                'params':JSON.stringify($scope.businessparam)
              },
              function (res) {
                if (res.status == 'S') {
                  //SIEJS.alert(res.msg, 'success');
                } else {
                    //SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
               // SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            });
            
        	}
        //搜索
        $scope.search = function () {
            $scope.dataTable.search(1);
        }

        $scope.btnDetail = function (item) {
         	item = item || $scope.dataTable.selectRow; 
                iframeTabAction('商品修改详情:' + item.productName , '/productDetails/'+ item.productHeadId.split("_")[0]);         
         }
        
       
        
        



    }]);
});
