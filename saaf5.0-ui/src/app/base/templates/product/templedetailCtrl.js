/**
 * Created by Administrator on 2016/9/26.
 */
define(["app"], function (app) {
    app.controller('templedetailCtrl', ['$scope', '$parse', '$filter', '$attrs', "SIE.JS", 'httpServer', 'URLAPI', '$stateParams', 'deleteLookupLine', function saafTableController($scope, $parse, $filter, $attrs, JS, httpServer, URLAPI, $stateParams, deleteLookupLine) {
        $scope.dataTable = [];
        $scope.params={}
        $scope.id=0;
        
        
        
        if($scope.id==0)
        	{
            httpServer.post(URLAPI.findTemple, {
                },
                function (res) {
                    if (res.status == 'S') {
                        $scope.params = res.data[0]; 
                        if($scope.params.productCategeery == '1') {
                            $scope.productCategeeryChangeFlag = true;
                          }else{
                            $scope.productCategeeryChangeFlag = false;
                          }
                    }
                },
                function (err) {
                    JS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
                }
            );
                     
        	}//search
        
        
        
        //保存
        $scope.btnSave = function () {
         	  httpServer.post(
                      URLAPI.saveTemple,
                      {
                    	  'params': JSON.stringify($scope.params)
                      },
                      function (res) {
                          if (res.status == "S") { 
                             JS.alert(res.msg, 'success');
                           // $location.url(window.appName.toLocaleLowerCase() +'/login');
                          } else {
                             JS.alert(res.msg, 'error')
                          }              
                      },
                      function (err) {
                         JS.alert("连接超时", "warning", "确定");
                      },
                      true);
        }

        // 商品种类change
      $scope.productCategeeryChange = function(key) {
        if(key == '1') {
          $scope.productCategeeryChangeFlag = true;
        }else{
          $scope.productCategeeryChangeFlag = false;
        }
      }

      // 获取TierOne
      $scope.getTierOne = function() {
        var areaObj = document.getElementsByName('TierOne');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier1 = checkedstr;
        console.log($scope.params.tier1, '$scope.params.tier1');
      };

      // 获取storeType
      $scope.getstoreType = function() {
        var areaObj = document.getElementsByName('storeType');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.storeType = checkedstr;
      };

      // 获取TierTow
      $scope.getTierTow = function() {
        var areaObj = document.getElementsByName('TierTow');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier2 = checkedstr;
      };

      // 获取getTierTFF
      $scope.getTierTow = function() {
        var areaObj = document.getElementsByName('TierTFF');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tier345 = checkedstr;
      };

      // 获取getTradeZone
      $scope.getTradeZone = function() {
        var areaObj = document.getElementsByName('TradeZone');
        var checkedArr = [];
        for( var k in areaObj) {
          if(areaObj[k].checked) {
            checkedArr.push(areaObj[k].value);
          }
        }
        var checkedstr = checkedArr.toString();
        checkedstr.replace('[', '').replace(']', '');
        $scope.params.tradeZone = checkedstr;
      };

      // allTierChange
      $scope.allTierChange = function() {
        if($scope.params.allTier == 'Y') {
          $('input[name="TierOne"]').attr('disabled', true);
          $('input[name="TierTow"]').attr('disabled', true);
          $('input[name="TierTFF"]').attr('disabled', true);
        }else{
          $('input[name="TierOne"]').attr('disabled', false);
          $('input[name="TierTow"]').attr('disabled', false);
          $('input[name="TierTFF"]').attr('disabled', false);
          $scope.params.tier1 ='';
          $scope.params.tier2 ='';
          $scope.params.tier345 ='';
        }
      };

      $scope.getstoreTypeAll = function () {
        if($scope.storeTypeAll == 'Y') {
          $('input[name="storeType"]').attr('checked', true);
          $timeout(function() {$scope.getstoreType();}, 500)  
        }else{
          $('input[name="storeType"]').attr('checked', false);
          $timeout(function() {$scope.getstoreType();}, 500)    
        }
      };

      $scope.tradeZoneAll = function () {
        if($scope.TradeZoneAll == 'Y') {
          $('input[name="TradeZone"]').attr('checked', true);
          $timeout(function() {$scope.getTradeZone();}, 500)  
        }else{
          $('input[name="TradeZone"]').attr('checked', false);
          $timeout(function() {$scope.getTradeZone();}, 500)  
        }
      };


      // 商品可退货属性回调
      $scope.productReturnChange = function (key) {
        if(key !== ''&& key !== '3') {
          $('#conditionId').attr('disabled', false)
        }else{
          $('#conditionId').attr('disabled', true)
        }
      }


      // 显示参考货品名称弹窗数据
      $scope.consultProductNameMol = function() {
        $('#consultProductNameMol').modal('toggle');
      };

      // 参考货号名称赋值
      $scope.referenceProductNameReturn = function (key, value, currentList) {
        $scope.params.consultProductno = currentList[0].plmCode;
        $scope.params.consultProductname = currentList[0].productName;
      }

      // 税收分类编码
      $scope.rateCodeShowMol = function() {
        $('#rateCodeShowMolID').modal('toggle');
        // $scope.rateCodeIndex = index;
      }
      $scope.rateCodeMolReturn = function(key, value, currentList) {
        $scope.params.rateClassCode = currentList[0].plmTaxTypeCode;
      }
      

    }]);
});