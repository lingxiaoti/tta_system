'use strict';
define(['app'],function(app) {
  app.controller('addNotObGoodsCtrl',['$scope', 'httpServer', 'URLAPI', 'SIEJS', '$stateParams','$timeout',
    function($scope,httpServer,URLAPI,SIEJS,$stateParams,$timeout) {
      // 变量参数
      $scope.params = {},
      $scope.barcodelist = [],
      $scope.supplierlist = [],
      $scope.isOB = false;
      $scope.SupplierNameIndex = '';
      $scope.productId = $stateParams.productHeadId;
      
      // 保存
      $scope.btnSave = function () {
        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({'substituteType':item.substituteType, 'substitutePropetion':item.substitutePropetion,
          'startDate':item.startDate,'endDate':item.endDate,'supplierId':item.supplierId,'storeId': item.storeId});
        });
        $scope.params.buttonStatus='save';
        $scope.params.orderStatus = '1';
        $scope.params.isUpdatecheck='0';
        
        var allData = {
          'headInfo': $scope.params,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'storelist': $scope.storelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
          },
          function (res) {
            if (res.status == 'S') {
              
              // if( $stateParams.productHeadId == '0') {
              //   console.log(243234234);
              //   $scope.productId = res.data;
              // }else {
              //   $scope.productId = $stateParams.productHeadId;
              // }
              $scope.productId = res.data.productHeadId;
              
              $scope.searchDetails();

              SIEJS.alert(res.msg, 'success');
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
          },
          function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          }
        )
      };
      // 提交
      $scope.btnSubmit = function () {
        $scope.params.buttonStatus ='submit';
        $scope.params.orderStatus = '1';
        $scope.params.isUpdatecheck='0';

        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({'substituteType':item.substituteType, 'substitutePropetion':item.substitutePropetion,
          'startDate':item.startDate,'endDate':item.endDate,'supplierId':item.supplierId, 'storeId': item.storeId});
        });

        // 验证
        if($scope.supplierlist.length==0) {
          SIEJS.alert('供应商信息不能为空', "error", "确定");
          return;
        }else {

          // 如果供应商只有一条，默认是优先供应商
          if($scope.supplierlist.length==1) {
            $scope.supplierlist[0].isMainsupplier = 'Y';
          }

          var noEmFlag = 0;
          $scope.supplierlist.forEach((item) => {
            if(!item.supplierName||item.supplierName == '') {
             noEmFlag = 1;
            }
            if(!item.isMainsupplier||item.isMainsupplier == ''||item.isMainsupplier==undefined) {
              noEmFlag = 2;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请选择供应商名称', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择优先供应商', "error", "确定");
            return;
          }
        }
        
        if($scope.barcodelist.length==0){
          SIEJS.alert('条形码信息不能为空', "error", "确定");
          return;
        }else {

          // 如果条码只有一条，默认是主条码
          if($scope.barcodelist.length==1) {
            $scope.barcodelist[0].isMain = 'Y';
          }
          
          
          var noEmFlag = 0;
          $scope.barcodelist.forEach((item) => {
            if(item.barcode == '') {
             noEmFlag = 1;
            }
            if(item.isMain == '') {
              noEmFlag = 2;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请填写条形码', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择主条形码', "error", "确定");
            return;
          }
        }
        var allData = {
          'headInfo': $scope.params,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'storelist': $scope.storelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
            'params': JSON.stringify(allData)
        },
          function (res) {
            if (res.status == 'S') {
              $scope.searchDetails();
              SIEJS.alert(res.msg, 'success');
              $timeout(function() {
                // 关闭当前页面
                window.parent.deleteHeadTab();
              },1000);
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        },
        function (err) {
          SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      };
      // 删除
      $scope.del = function(index, list) {
        if(list == 'supplierlist') {
          var innerId = $scope.supplierlist[index].id;
          if(innerId == undefined) {
            $scope.supplierlist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductSupplierById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.supplierlist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'barcodelist') {
          var innerId = $scope.barcodelist[index].barcodeId;
          if(innerId == undefined) {
            $scope.barcodelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteBarcodeById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.barcodelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }
      };
      //作废
      $scope.btnDiscard = function(){
        if($scope.params.obId) {
          SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
            httpServer.post(URLAPI.updateobProduct, {
              'params': JSON.stringify({'obId': $scope.params.obId})
            },
            function (res) {
              if (res.status == 'S') {
                SIEJS.alert(res.msg, 'success');
                $scope.searchDetails();
              } else {
                  SIEJS.alert(res.msg, "error", "确定");
              }
            },
            function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            })
          });
        }else{
          SIEJS.alert('此商品尚未保存，无法作废', "warning", "确定");
        }
        
      };
      // 查询
      $scope.searchDetails = function () {
        httpServer.post(URLAPI.findProductById, {
          'params': JSON.stringify({'productHeadId':$scope.productId})
        },
        function(res) {
          if (res.status == 'S') {
            if(res.data.headInfo) {
            $scope.params = res.data.headInfo;
            $scope.barcodelist = res.data.barcodelist;
            $scope.supplierlist = res.data.supplierlist;

            // 默认特殊商品类型为普通商品
            if(!$scope.params.productHeadId) {
              $scope.params.otherInfo = '0';
            }
            
              if($scope.params.obId) {
                $scope.isOB = true;
              }
            }
          }
        })
      };

      if($scope.productId) {
        $scope.searchDetails();
      }
      // 部门change
      $scope.departmentOnchange = function() {
        if($scope.params.department.length==4) {
          // 自动带出采购类型
          var judgeCode = $scope.params.department.substring(2,3);
          if(judgeCode<5) {
            $scope.params.purchaseType = '1';
          }else if(judgeCode<8) {
            $scope.params.purchaseType = '3';
          }else {
            $scope.params.purchaseType = '2';
          }
        }else if($scope.params.department.length>4) {
          $scope.params.department = $scope.params.department.substring(0,4);
        }
      };

      // 行表新增
      $scope.addSupplierInfo = function () {
        var supplierInfoDataobj = {
          supplierCode: '',
          supplierName: '',
          productReturn: '',
          condition: '',
        };
        $scope.supplierlist.push(supplierInfoDataobj);
      };
      $scope.addBarcodeInfo = function () {
        var barcodeInfoDataobj = {
          obId: $scope.params.obId,
          barcode: '',
          isMain: '',
        };
        $scope.barcodelist.push(barcodeInfoDataobj);
      };
      // 显示供应商弹窗
      $scope.showSupplierNameMol = function(index) {
        $('#supplierNameMol').modal('toggle');
        $scope.SupplierNameIndex = index;
      };
      
      // 供应商弹窗数据
      $scope.SupplierNameReturn = function (key, value, currentList) {
        var stop = 0;
        $scope.supplierlist.forEach((item) => {
          if(item.supplierId == currentList[0].userId) {
            SIEJS.alert('供应商已存在', "error", "确定");
            stop = 1;
          }
        }); 
        if(stop == 1) {
          return;
        }
        $scope.supplierlist[$scope.SupplierNameIndex].supplierName = currentList[0].userName;
        $scope.supplierlist[$scope.SupplierNameIndex].supplierId= currentList[0].userId;
      };

      // 显示部门弹窗
      $scope.showdeptNameMol = function() {
        $('#deptNameMol').modal('toggle');
      };

      // 部门弹窗数据
      $scope.deptCodeReturn = function (key, value, currentList) {
        $scope.params.department = currentList[0].plmDeptCode;
        $scope.params.departmentDescript= currentList[0].plmDeptName;

        // 自动带出采购类型
        var judgeCode = $scope.params.department.substring(2,3);
        if(judgeCode<5) {
          $scope.params.purchaseType = '1';
        }else if(judgeCode<8) {
          $scope.params.purchaseType = '3';
        }else {
          $scope.params.purchaseType = '2';
        }
      };

      // 显示分类弹窗
      $scope.showdeptClassMol = function() {
        if($scope.params.department) {
          $('#deptClassMol').modal('toggle');
        }else{
          SIEJS.alert('请先选择部门编码', 'error');
        }
      };

      // 分类弹窗数据
      $scope.deptClassCodeReturn = function (key, value, currentList) {
        $scope.params.classes = currentList[0].plmClassCode;
        $scope.params.classDesc= currentList[0].plmClassName;
      };

      // 显示子分类弹窗
      $scope.showdeptSubClassMol = function() { 
        $('#deptSubClassMol').modal('toggle');
      };

      // 子分类弹窗数据
      $scope.deptSubClassCodeReturn = function (key, value, currentList) {
        $scope.params.subClass = currentList[0].plmSubclassCode;
        $scope.params.subclassDesc= currentList[0].plmSubclassName;
      };

      // 显示品牌弹窗
      $scope.showBrandMol = function() {
        $('#brandMol').modal('toggle');
      };

      // 品牌弹窗数据
      $scope.brandReturn = function (key, value, currentList) {
        $scope.params.brandnameCn = currentList[0].plmBrandCn;
        $scope.params.brandnameEn= currentList[0].plmBrandEn;
        $scope.params.groupBrand= currentList[0].plmGroupBrand;
        $scope.params.motherCompany= currentList[0].plmMotherCompany;
      };


      // 条形码单选
      $scope.selectcheckbox=function(index){
    	 if($scope.barcodelist[index].isMain=='Y'){
          angular.forEach($scope.barcodelist,function(obj,rowindex){
        	  if(rowindex!=index){
        		  obj.isMain='N';
        		  }
          });
        }     
      }

      // 优先供应商
      $scope.supplierSelectcheckbox=function(index){
        if($scope.supplierlist[index].isMainsupplier=='Y'){
           angular.forEach($scope.supplierlist,function(obj,rowindex){
             if(rowindex!=index){
               obj.isMainsupplier='N';
               }
           });
         }     
       }
      
  }
  ]);
})