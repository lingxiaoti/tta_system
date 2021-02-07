'use strict';
define(['app',"angularFileUpload"],function(app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('qaExclusiveApprovalCtrl',['$scope', 'httpServer', 'URLAPI', '$timeout','SIE.JS', '$compile', '$stateParams','FileUploader','iframeTabAction',
    function($scope, httpServer, URLAPI, $timeout, SIEJS, $compile, $stateParams,FileUploader,iframeTabAction) {
      // 变量参数
      $scope.params = {};
      $scope.paramsDrug = {}; // 药品属性
      $scope.paramsMedical = {}; // 医疗器械属性
      $scope.drugfilelist = [
        {
          qaFiletype: '药品注册证',
          qaUrl: '',
          qaCodetype: '3',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '质量标准',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '标签备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '说明书备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
      ]; // 药品资质文件
      $scope.medicalfilelist = [
        {
          qaFiletype: '产品技术要求',
          qaUrl: '',
          qaCodetype: '2',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '合格证明文件',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '标签备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
        {
          qaFiletype: '说明书备案样式',
          qaUrl: '',
          qaCodetype: '5',
          qaCode: '',
          dateType: '3',
          datecurent: '',
        },
      ]; //  器械资质文件

      var userLoginInfo = JSON.parse(sessionStorage[appName + '_successLoginInfo']);
      //获取url参数对象
      $scope.urlParams = urlToObj(location.hash);
       //alert(JSON.stringify($scope.urlParams));
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
      //先判断是否业务form是否有id 再判断流程form的id
      if ($stateParams.productHeadId) {
          $scope.id = $stateParams.productHeadId;
      } else if ($scope.urlParams.businessKey) {
    	 // alert($scope.urlParams.toString());
          $scope.id = $scope.urlParams.businessKey;
          //保存流程实例id
          $scope.businessparam={
        	productHeadId:$scope.urlParams.businessKey,
        	instanceid:$scope.urlParams.processInstanceId,
        	type:"save"
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
         
      $scope.isOB = false;
      $scope.supplierlist = [], // 供应商信息列表数据
      $scope.barcodelist = [], // 条形码列表数据
      $scope.qalist = [], // 资质文件列表数据
      $scope.picfilelist = [
        {
          picType: 'Logo',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '正面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '背面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '侧面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '已开封（正面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '已开封（背面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
        {
          picType: '宝贝详情',
          picUrl: '',
          createdBy: '',
          creationDate: '',
        },
      ], // 图片文件
      $scope.packagelist = [], // 商品包装
      $scope.pricelist = [], // 商品售价
      $scope.placelist = [], // 地点信息关联
      $scope.saleslist = [], // 可销售属性
      $scope.channallist = [], // 线上渠道
      $scope.filelist = [], // 附件
      $scope.modifyLogData = [], // 修日志企
      $scope.modalParams = {}, // 模板数据
      $scope.copyDataParams = {
        orderStatus: '6'
      }, // 复制资料数据

      $scope.picfilelistIndex = '', // 图片上传是需要获取当前行数
      $scope.qalistIndex = '', // 资质文件上传需要的当前行数
      $scope.filelistIndex = '', // 附件上传需要的当前行数
      $scope.drugFileIndex = '', // 药品资质上传
      $scope.medFileIndex = '', // 器械资质上传

      $scope.qaFiletypeIndex = '';
      $scope.mdqaFiletypeIndex = '';
      $scope.meqaFiletypeIndex = '';

      $scope.SupplierNameIndex = '', // 供应商获取当前行
      $scope.diDianSupplierNameReturnIndex = '', // 地点获取当前行
      $scope.getValidDaysIndex = '', // 包装的当前行
      $scope.sxDaysDisabled = false, // 显示保质期天数

      $scope.tabAction = '', // 页签action标识
      $scope.processImageParams = {};// 流程图参数

      $scope.storeTypeAll = ''; // storeType是否全选标识
      $scope.TradeZoneAll = ''; // TradeZone是否全选标识

      $scope.addPriceInfoFlag = true; // addPriceInfo控制售价添加的标识

      $scope.addpriceCtrl = ''; // 售价分组的控制标识

      var userInfo = JSON.parse(localStorage.getItem('BASE_successLoginInfo')); // 获取用户信息

      
      
    // 切换页签
    $scope.tabChangeInfo = function (name) {
      $scope.tabAction = name;
      if (name == 'flowChart') {
        $scope.tabChange('flowChart');
      }
    };
  

      // 查询
      $scope.searchDetails = function () {
        httpServer.post(URLAPI.findProductById, {
          'params': JSON.stringify({'productHeadId': $scope.id})
        },
        function(res) {
          if (res.status == 'S') {
            $scope.params = res.data.headInfo;
            $scope.paramsDrug = res.data.drugInfo;
            $scope.paramsMedical = res.data.medicalInfo;
            // $scope.medicalfilelist = res.data.medicalfilelist;
            // $scope.drugfilelist = res.data.drugfilelist;
            $scope.barcodelist = res.data.barcodelist;
            $scope.channallist = res.data.channallist;
            $scope.filelist = res.data.filelist;
            $scope.packagelist = res.data.packagelist;
            $scope.picfilelist = res.data.picfilelist;
            $scope.placelist = res.data.placelist;
            $scope.pricelist = res.data.pricelist;
            $scope.qalist = res.data.qalist;
            $scope.saleslist = res.data.saleslist;
            $scope.supplierlist = res.data.supplierlist;

            // if(res.data.qalist.length == 0 && $scope.params.otherInfo == '1') {
            //   $scope.qalist = $scope.drugfilelist;
            // }else if(res.data.qalist.length == 0 && $scope.params.otherInfo == '2') {
            //   $scope.qalist = $scope.medicalfilelist;
            // }else {
            //   $scope.qalist = res.data.qalist;
            // }

            // ob商品需执行的操作
            if($scope.params.obId) {
              $scope.isOB = true;
              $scope.getObHeadInfo();
            }
            // 季节性商品默认为NO
            if(!$scope.params.sesionProduct) {
              $scope.params.sesionProduct = '0';
            }
            // allTier为Y时，默认禁用
            if($scope.params.allTier == 'Y') {
              $('input[name="TierOne"]').attr('disabled', true);
              $('input[name="TierTow"]').attr('disabled', true);
              $('input[name="TierTFF"]').attr('disabled', true);
            }

            // 售价分组的控制
            if($scope.params.otherInfo == '1') {
              $scope.addpriceCtrl = '1';
            }else {
              $scope.addpriceCtrl = '0';
            }

            // 商品种类pog控制
            if($scope.params.productCategeery == '1') {
              $scope.productCategeeryChangeFlag = true;
            }else{
              $scope.productCategeeryChangeFlag = false;
            }
          }
        })
      };

      // 自动查询
      if(true) {
        $scope.searchDetails();
      }

      // 保存
      $scope.btnSave = function () {

        var allData = {
          'headInfo': $scope.params,
          'drugInfo': $scope.paramsDrug,
          'medicalInfo': $scope.paramsMedical,
          'medicalfilelist': $scope.medicalfilelist,
          'drugfilelist': $scope.drugfilelist,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'qalist': $scope.qalist,
          'picfilelist': $scope.picfilelist,
          // 'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          // 'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
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
      })},
      // 提交
      $scope.btnSubmit = function () {
        if($scope.supplierlist.length==0){
          SIEJS.alert('供应商信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.supplierlist.forEach((item) => {
            if(item.supplierName == '') {
             noEmFlag = 1;
            }
            if(!item.sxWay||item.sxWay == '') {
              noEmFlag = 2;
            }
            if(!item.productLength||item.productLength == '') {
              noEmFlag = 3;
            }
            if(!item.productBreadth||item.productBreadth == '') {
              noEmFlag = 4;
            }
            if(!item.productHeight||item.productHeight == '') {
              noEmFlag = 5;
            }
            if(!item.validDays||item.validDays == '') {
              noEmFlag = 6;
            }
            if(!item.sxDays||item.sxDays == '') {
              noEmFlag = 7;
            }
            if(!item.place||item.place == '') {
              noEmFlag = 8;
            }
            if(!item.productively||item.productively == '') {
              noEmFlag = 9;
            }
            if(item.sxWay == '3'&&item.placeNote == '') {
              noEmFlag = 10;
            }
            if(item.sxWay == '5'&&item.area == '') {
              noEmFlag = 11;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请选择供应商名称', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请选择生效方式', "error", "确定");
            return;
          }
          if(noEmFlag == 3){
            SIEJS.alert('请输入商品深', "error", "确定");
            return;
          }
          if(noEmFlag == 4){
            SIEJS.alert('请输入商品宽', "error", "确定");
            return;
          }
          if(noEmFlag == 5){
            SIEJS.alert('请输入商品高', "error", "确定");
            return;
          }
          if(noEmFlag == 6){
            SIEJS.alert('请输入有效期限（保质期）', "error", "确定");
            return;
          }
          if(noEmFlag == 7){
            SIEJS.alert('请输入保质期天数', "error", "确定");
            return;
          }
          if(noEmFlag == 8){
            SIEJS.alert('请输入产地', "error", "确定");
            return;
          }
          if(noEmFlag == 9){
            SIEJS.alert('请输入多产地', "error", "确定");
            return;
          }
          if(noEmFlag == 10){
            SIEJS.alert('请输入地点清单', "error", "确定");
            return;
          }
          if(noEmFlag == 11){
            SIEJS.alert('请选择区域', "error", "确定");
            return;
          }

        }

        if ($scope.barcodelist.length==0) {
          SIEJS.alert('条码信息不能为空', "error", "确定");
          return;
        }else{
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
        
        if ($scope.qalist.length==0&&!$scope.isOB) {
          SIEJS.alert('资质文件信息不能为空', "error", "确定");
          return;
        }else{
          var noEmFlag = 0;
          $scope.qalist.forEach((item) => {
            if(item.qaFiletype == ''|| item.qaFiletype == '999') {
             noEmFlag = 1;
            }
            if(item.qaUrl == '') {
              noEmFlag = 2;
            }
            // if(item.qaCodetype == '') {
            //   noEmFlag = 3;
            // }
            if(item.qaCode == '') {
              noEmFlag = 4;
            }
            if(item.datecurent == '') {
              noEmFlag = 5;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请输入资质文件类型', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请上传资质文件', "error", "确定");
            return;
          }
          // if(noEmFlag == 3){
          //   SIEJS.alert('请输入资质编码类型', "error", "确定");
          //   return;
          // }
          if(noEmFlag == 4){
            SIEJS.alert('请输入资质文件证书编码', "error", "确定");
            return;
          }
          if(noEmFlag == 5){
            SIEJS.alert('请选择资质文件信息日期', "error", "确定");
            return;
          }
        }
        
        if ($scope.picfilelist.length==0) {
          SIEJS.alert('图片信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.picfilelist.forEach((item) => {
            if(item.picUrl == ''|| item.picUrl == undefined) {
             noEmFlag = 1;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请上传图片文件', "error", "确定");
            return;
          }
        }

        if ($scope.pricelist.length==0) {
          SIEJS.alert('商品售价信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.pricelist.forEach((item) => {
            if(item.priceGroup == '') {
             noEmFlag = 1;
            }
            if(item.priceArea == '') {
              noEmFlag = 2;
             }
             if(item.unitPrice == '') {
              noEmFlag = 3;
             }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请填写售价分组', "error", "确定");
            return;
          }
          if(noEmFlag == 2){
            SIEJS.alert('请填写售价区域', "error", "确定");
            return;
          }
          if(noEmFlag == 3){
            SIEJS.alert('请填写零售价', "error", "确定");
            return;
          }
        }

        // if ($scope.packagelist.length==0) {
        //   SIEJS.alert('商品包装信息不能为空', "error", "确定");
        //   return;
        // }else if ($scope.pricelist.length==0) {
        //   SIEJS.alert('商品售价信息不能为空', "error", "确定");
        //   return;
        // }

        var allData = {
          'headInfo': $scope.params,
          'drugInfo': $scope.paramsDrug,
          'medicalInfo': $scope.paramsMedical,
          'medicalfilelist': $scope.medicalfilelist,
          'drugfilelist': $scope.drugfilelist,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'qalist': $scope.qalist,
          'picfilelist': $scope.picfilelist,
          // 'packagelist': $scope.packagelist,
          'pricelist': $scope.pricelist,
          // 'placelist': $scope.placelist,
          'saleslist': $scope.saleslist,
          'channallist': $scope.channallist,
          'filelist': $scope.filelist
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
      },
      function (res) {
        if (res.status == 'S') {
          // SIEJS.alert(res.msg, 'success');
          $scope.liuchen();
          $scope.searchDetails();
        } else {
            SIEJS.alert(res.msg, "error", "确定");
        }
      },
      function (err) {
        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
      })
      },
      // 删除
      $scope.del = function(index, list) {
        if (list == 'qalist') {
          var innerId = {id: $scope.qalist[index].qaId};
          if(innerId == undefined) {
            $scope.qalist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductQaFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.qalist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'picfilelist') {
          var innerId = {id: $scope.picfilelist[index].picId};
          if(innerId == undefined) {
            $scope.picfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeletePicFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.picfilelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'drugfilelist') {
          var innerId = {id: $scope.drugfilelist[index].drugfileId};
          if(innerId == undefined) {
            $scope.drugfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteDrugfileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.drugfilelist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'medicalfilelist') {
          var innerId = {id: $scope.medicalfilelist[index].id};
          if(innerId == undefined) {
            $scope.medicalfilelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteMedicalfileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.medicalfilelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'pricelist') {
          var innerId = {id: $scope.pricelist[index].priceId};
          if(innerId == undefined) {
            $scope.pricelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductPriceById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.pricelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'placelist') {
          var innerId = {id: $scope.placelist[index].placeId};
          if(innerId == undefined) {
            $scope.placelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductPlaceById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.placelist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'saleslist') {
          var innerId = {id: $scope.saleslist[index].salesId};
          if(innerId == undefined) {
            $scope.saleslist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductSalePropertiesById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.saleslist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'channallist') {
          var innerId = {id: $scope.channallist[index].channalId};
          if(innerId == undefined) {
            $scope.channallist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductChannalById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.channallist.splice(index, 1);
                } else {
                  SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'filelist') {
          var innerId = {id: $scope.filelist[index].fileId};
          if(innerId == undefined) {
            $scope.filelist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductFileById, {
                'params': JSON.stringify(innerId)
              },
              function (res) {
                if (res.status == 'S') {
                  SIEJS.alert(res.msg, 'success');
                  $scope.filelist.splice(index, 1);
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
              },
              function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              })
            })
          }
        }else if (list == 'supplierlist') {
          var innerId = {id: $scope.supplierlist[index].id};
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
          var innerId = {id: $scope.barcodelist[index].barcodeId};
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
        SIEJS.confirm('作废', '是否确定作废？', '确定', function () {
          httpServer.post(URLAPI.updateobProduct, {
            'params': JSON.stringify({'productHeadId': $stateParams.productHeadId})
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
      };


      // 药品与器械的资质文件类型的显示控制
      $scope.Number=function(value){
    	  if(isNaN(parseInt(value))) {
    		  return 1;
    		}else{
    		  return 0;
    		}
      }

      // ------------------------------------------- 提交工作流 --------------------------------------
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

        //先判断是否业务form是否有id 再判断流程form的id
        if ($stateParams.productHeadId) {
          $scope.id = $stateParams.productHeadId;
        }else if ($scope.urlParams.businessKey) {
            $scope.id = $scope.urlParams.businessKey;
        }
    
        //流程图参数
        $scope.processImageParams = {
          token: sessionStorage.getItem(window.appName + '_certificate') || localStorage.getItem(window.appName + '_certificate'),
          id: 'processImg',
          instanceId: null,
          key: 'PLM_FZ.-999' //流程唯一标识，在流程管理->流程设计->设计 流程中获取，流程配置时修改为对应表单的流程唯一标识
        };
    
        $scope.tabChange = function (name) {
            $scope.tabAction = name;
            if (!$scope.processImgLoad) {
                $scope.getProcessInfo(function () {
                    var p = $scope.processImageParams;
                    $timeout(function () {
                        processImageDraw(p.token, p.id, p.instanceId, p.key); //  绘制流程图
                        $scope.processImgLoad = true;
                    }, 100)
                });
            }
        };
    
        // 获取流程信息
        $scope.getProcessInfo = function (callback) {
          httpServer.post(URLAPI.processGet, {
            'params': JSON.stringify({
              processDefinitionKey: $scope.processImageParams.key,
              businessKey: $stateParams.productHeadId,
            })
          },
          function (res) {
            if (res.status === 'S') {
              $scope.processImageParams.instanceId = res.data.processInstanceId;
            }
            callback && callback(res);
          });
        };


        //  提交审批流
        $scope.liuchen = function (id) {

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
                  "businessKey": $stateParams.productHeadId, //单据ID
                  "title": "商品新增", //流程标题，修改为当前业务信息
                  "positionId": 0,
                  "orgId": 0,
                  "userType": userInfo.userType,
                  "billNo": $scope.params.plmCode
              }
              httpServer.post(URLAPI.processStart, {
                  'params': JSON.stringify($scope.paramsBpm)
              }, function (res) {
                  if (res.status == 'S') {
                     SIEJS.alert("提交成功", "success", "确定");
                  }
                  else {
                      SIEJS.alert(res.msg, "error", "确定");
                  }
              }, function (err) {
                  SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
              });
      }

      $scope.conditionsApprol = function() {
        $scope.conditionsApprolFlag = true;
        $scope.pageView.form();
      }

      $scope.pageView.form = function () {
        $("#provConditionId").attr('disabled', false);
      }

    }]
  );
})