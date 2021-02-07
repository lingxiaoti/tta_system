'use strict';
define(['app','angularFileUpload', 'ztree'],function(app) {
  app.useModule('angularFileUpload');//按需加载弹出层模块
  app.controller('finishProcurementCtrl',['$scope', 'httpServer', 'URLAPI', '$timeout', 'SIE.JS', '$compile', '$stateParams','FileUploader',
    function($scope, httpServer, URLAPI, $timeout, SIEJS, $compile, $stateParams,FileUploader) {
      // 变量参数
      $scope.supplierlist = [], // 供应商信息列表数据
      $scope.barcodelist = [], // 条形码列表数据
      $scope.qalist = [], // 资质文件列表数据
      $scope.picfilelist = [
        {
          picType: 'Logo',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '正面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '背面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '侧面',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '已开封（正面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '已开封（背面）',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
        {
          picType: '宝贝详情',
          picUrl: '',
          createdBy: '',
          creationDate: '',
          falg: ' ',
        },
      ], // 图片文件
      $scope.packagelist = [], // 商品包装
      $scope.params = {}, // 保存提交参数 
      $scope.paramsDrug = {};  // 药品头信息
      $scope.paramsMedical = {}; // 医疗器械信息
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

      $scope.SupplierNameIndex = '';
      $scope.qaFiletypeIndex = '';
      $scope.mdqaFiletypeIndex = '';
      $scope.meqaFiletypeIndex = '';
      
      $scope.tabAction = ''; // 页签action标识
      $scope.processImageParams = {};// 流程图参数
      $scope.supplierAddDisable = true,
      $scope.fileData = [];
      $scope.uploadFileData = {};

      $scope.TgroupId = '';
      $scope.TgroupName = '';

      // ob模块
      $scope.isOB = false;
      $scope.plmDevelopmentInfoId = '', // 组件id
      $scope.productQaFileDataTable = []; // ob资质文件列表
      $scope.qaDetailTable = [];

      $scope.getValidDaysIndex = ''; // 包装的当前行
      // $scope.sxDaysDisabled = false; // 显示保质期天数

       $scope.userInfo = JSON.parse(localStorage.getItem('BASE_successLoginInfo')); // 获取用户信息

      if($scope.userInfo.userType == '60') {
        $scope.supplierAddDisable = false;
      }
      
      //  ----------------------------------流程  start --------------------------------------

      // 
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
      } else if ($scope.urlParams.businessKey) {

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
          businessKey: 529,
        })
      },
      function (res) {
        if (res.status === 'S') {
          $scope.processImageParams.instanceId = res.data.processInstanceId;
        }
        callback && callback(res);
      });
    };

   //  ----------------------------------查询  start --------------------------------------

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
        'params': JSON.stringify({'productHeadId':$scope.id})
      },
      function(res) {
        if (res.status == 'S') {
          $scope.params = res.data.headInfo;
          $scope.paramsDrug = res.data.drugInfo;
          $scope.paramsMedical = res.data.medicalInfo;
          $scope.barcodelist = res.data.barcodelist;
          $scope.packagelist = res.data.packagelist;
          // $scope.picfilelist = res.data.picfilelist;
          $scope.qalist = res.data.qalist;
          $scope.supplierlist = res.data.supplierlist;

          // 图片文件
          if(res.data.picfilelist.length == 0) {
          }else {
            $scope.picfilelist = res.data.picfilelist;
          }

          // if(res.data.qalist.length == 0 && $scope.params.otherInfo == '1') {
          //   $scope.qalist = $scope.drugfilelist;
          // }else if(res.data.qalist.length == 0 && $scope.params.otherInfo == '2') {
          //   $scope.qalist = $scope.medicalfilelist;
          // }else {
          //   $scope.qalist = res.data.qalist;
          // }
          // console.log($scope.qalist, '$scope.qalist');

          if($scope.params.obId) {
            $scope.isOB = true;
            $scope.getObHeadInfo();
          }
        }
      })
    };


    // 页面初始化即调用查询接口
    if(true) {
      $scope.searchDetails();
    }


    //  ----------------------------------ob资质表  start --------------------------------------

    //打开上传ob资质文件窗口
    $scope.productQaFileUploadShow = function(plmDevelopmentQaSummaryId){
      $scope.getObQaDetilasInfo(plmDevelopmentQaSummaryId);
      $('#fileUpload').modal('show');
    };

      // 获取ob头表信息
      $scope.getObHeadInfo = function() {
        httpServer.post(URLAPI.findPlmDevelopmentInfoInfo, {
          'params': JSON.stringify({obId: $scope.params.obId})
        },
        function (res) {
            if (res.status == 'S') {
              $scope.plmDevelopmentInfoId = res.data[0].plmDevelopmentInfoId;
              $scope.searchDevelopmentQaFileSummary(res.data[0].plmDevelopmentInfoId);
              $scope.searchBatchInfo(res.data[0].plmDevelopmentInfoId);
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        },
        function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      }
      // 查询ob产品资质文件详情信息
      $scope.getObQaDetilasInfo = function(plmDevelopmentQaSummaryId) {
        httpServer.post(URLAPI.findPlmDevelopmentQaDetailInfo, {
          'params': JSON.stringify({plmDevelopmentQaSummaryId: plmDevelopmentQaSummaryId})
        },
        function (res) {
            if (res.status == 'S') {
              $scope.qaDetailTable = res.data;
            } else {
                SIEJS.alert(res.msg, "error", "确定");
            }
        },
        function (err) {
            SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
        });
      };
      //查询ob产品资质文件汇总
      $scope.searchDevelopmentQaFileSummary = function (plmDevelopmentInfoId) {

        httpServer.post(URLAPI.findPlmDevelopmentQaSummaryInfo, {
                'params': JSON.stringify({plmDevelopmentInfoId: plmDevelopmentInfoId}),
                'pageRows': 1000
            },
            function (res) {
                if (res.status == 'S') {
                    $scope.productQaFileDataTable = res.data;
                } else {
                    SIEJS.alert(res.msg, "error", "确定");
                }
            },
            function (err) {
                SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
            }
        );
    };


    // ------------------------------------------ 批次信息 ---------------------------------------------

    $scope.searchBatchInfo = function (plmDevelopmentInfoId) {
      httpServer.post(URLAPI.findPlmDevelopmentBatchInfoInfo, {
              'params': JSON.stringify({plmDevelopmentInfoId: plmDevelopmentInfoId}),
              'pageRows': 1000
          },
          function (res) {
              if (res.status == 'S') {
                  $scope.batchInfoDataTable = res.data;
              } else {
                  SIEJS.alert(res.msg, "error", "确定");
              }
          },
          function (err) {
              SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
          }
      );
    };



      //  ----------------------------------按钮操作  start --------------------------------------

      // 保存
      $scope.btnSave = function () {
        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({
            'substituteType':item.substituteType, 
            'substitutePropetion':item.substitutePropetion,
            'startDate':item.startDate,
            'endDate':item.endDate,
            'supplierId':item.supplierId,
            'storeId': item.storeId
          });
        });


        $scope.params.buttonStatus ='save';
        $scope.params.isUpdatecheck='0';
        $scope.params.orderStatus = '2';
        var allData = {
          'headInfo': $scope.params,
          'drugInfo': $scope.paramsDrug,
          'medicalInfo': $scope.paramsMedical,
          'supplierlist': $scope.supplierlist,
          'barcodelist': $scope.barcodelist,
          'qalist': $scope.qalist,
          'picfilelist': $scope.picfilelist,
          'storelist': $scope.storelist
          // 'packagelist': $scope.packagelist,
        }
        httpServer.post(URLAPI.saveProductInfo, {
          'params': JSON.stringify(allData)
      },
        function (res) {
          if (res.status == 'S') {
            $scope.searchDetails();
            SIEJS.alert(res.msg, 'success');
          } else {
              SIEJS.alert(res.msg, "error", "确定");
          }
      },
      function (err) {
        SIEJS.alert('操作失败，请刷新页面再尝试，如果多次失败，请联系维护人员', "error", "确定");
      }
      )},
      // 提交
      $scope.btnSubmit = function () {

        $scope.storelist = [];
        //开供应商信息的数据分开两个表
        $scope.supplierlist.forEach((item) => {
          $scope.storelist.push({
            'substituteType':item.substituteType, 
            'substitutePropetion':item.substitutePropetion,
            'startDate':item.startDate,
            'endDate':item.endDate,
            'supplierId':item.supplierId,
            'storeId': item.storeId
          });
        });

        $scope.params.buttonStatus ='submit';
        $scope.params.orderStatus = '2';
        $scope.params.isUpdatecheck='0';

        if((!$scope.params.qaFileId||$scope.params.qaFileId == '')&&!$scope.isOB&&$scope.userInfo.userType=='60') {
          SIEJS.alert('资质组ID不能为空', "error", "确定");
          return;
        }
        if((!$scope.params.qaFilename||$scope.params.qaFilename == '')&&!$scope.isOB&&$scope.userInfo.userType=='60') {
          SIEJS.alert('资质组名称不能为空', "error", "确定");
          return;
        }

        if($scope.supplierlist.length==0) {
          SIEJS.alert('供应商信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          $scope.supplierlist.forEach((item) => {
            if(item.supplierName == '') {
             noEmFlag = 1;
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
          });
          if(noEmFlag == 1){
            SIEJS.alert('请选择供应商名称', "error", "确定");
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

        }

        if($scope.barcodelist.length==0){
          SIEJS.alert('条形码信息不能为空', "error", "确定");
          return;
        }else {
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

        if($scope.qalist.length==0&&!$scope.isOB){
          // SIEJS.alert($scope.isOB, "error", "确定");
          SIEJS.alert('资质文件信息不能为空', "error", "确定");
          return;
        }else {
          var noEmFlag = 0;
          var supplierIDarr = [];
          $scope.supplierlist.forEach((item) => {
            supplierIDarr.push(item.supplierId);
          });
          $scope.qalist.forEach((item,index,arr) => {
            if((item.qaFiletype == ''|| item.qaFiletype == '999')&&!$scope.isOB) {
             noEmFlag = 1;
            }
            if(item.qaUrl == ''&&!$scope.isOB) {
              noEmFlag = 2;
            }
            // if(item.qaCodetype == '') {
            //   noEmFlag = 3;
            // }
            if(item.qaCode == ''&&!$scope.isOB) {
              noEmFlag = 4;
            }
            if(item.datecurent == ''&&!$scope.isOB) {
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


          // 资质组与供应商对应验证
          var map=new Map();
          angular.forEach($scope.qalist,function(obj,rowindex){
                var supplierid=obj.supplierId;
                var groupId=obj.groupId; 
                var key=supplierid+"_"+groupId;
                  if(!map.has(key)){
                    map.set(key,key);
                  }
              });
          if(map.size!==$scope.supplierlist.length) {
            SIEJS.alert('请核对资质组与供应商是否已经完全匹配', "error", "确定");
            return;
          }
        }

        if($scope.picfilelist.length==0){
          SIEJS.alert('图片文件信息不能为空', "error", "确定");
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

        if($scope.params.otherInfo == '1'&&$scope.drugfilelist.length==0) {
          SIEJS.alert('药品资质文件信息不能为空', "error", "确定");
          return;
        }else if ($scope.params.otherInfo == '1'&&$scope.drugfilelist.length>0){
          var noEmFlag = 0;
          $scope.drugfilelist.forEach((item) => {
            if(item.fileUrl == '') {
             noEmFlag = 1;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请上传药品资质文件', "error", "确定");
            return;
          }
        }

        if($scope.params.otherInfo == '2'&&$scope.medicalfilelist.length==0) {
          SIEJS.alert('器械资质文件信息不能为空', "error", "确定");
          return;
        }else if($scope.params.otherInfo == '2'&&$scope.medicalfilelist.length>0){
          var noEmFlag = 0;
          $scope.medicalfilelist.forEach((item) => {
            if(item.fileUrl == '') {
             noEmFlag = 1;
            }
          });
          if(noEmFlag == 1){
            SIEJS.alert('请上传器械资质文件', "error", "确定");
            return;
          }
        }
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
          'storelist': $scope.storelist
          // 'packagelist': $scope.packagelist,
        }
        httpServer.post(URLAPI.saveProductInfo, {
            'params': JSON.stringify(allData)
        },
          function (res) {
            if (res.status == 'S') {
              // $scope.searchDetails();
              SIEJS.alert(res.msg, 'success');
              $timeout(function() {
                // 关闭当前页面
                window.parent.deleteHeadTab();
              },2000);
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
        if (list == 'qalist') {
          var innerId = {id: $scope.qalist[index].qaId};
          if(innerId.id == undefined) {
            $scope.qalist.splice(index, 1);
          }else {
            SIEJS.confirm('删除', '是否确定删除？', '确定', function () {
              httpServer.post(URLAPI.DeleteProductQaFileById, {
                'params': JSON.stringify(innerId)
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
            })
          }
        }else if (list == 'picfilelist') {
          var innerId = {id: $scope.picfilelist[index].picId};
          if(innerId.id == undefined) {
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
          var innerId = {id:  $scope.qalist[index].qaId};
          if(innerId.id == undefined) {
            $scope.qalist.splice(index, 1);
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
          var innerId = {id:  $scope.qalist[index].qaId};
          if(innerId.id == undefined) {
            $scope.qalist.splice(index, 1);
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
        }else if (list == 'barcodelist') {
          var innerId = {id: $scope.barcodelist[index].barcodeId};
          if(innerId.id == undefined) {
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


      // 供应商信息有效期的配对
      $scope.getIndexValidDays = function(index) {
        $scope.getValidDaysIndex = index;
      };
      $scope.validDaysChange = function (key) {
        if(key == '0') {
          $scope.supplierlist[$scope.getValidDaysIndex].sxDays = 3650;
        }else{
          $scope.supplierlist[$scope.getValidDaysIndex].sxDays = '';
        }
      }
      // $scope.sxDaysDisabled = function () {
      //   var sxDaysDisabledFlag = '';
      //   if($scope.supplierlist[$scope.getValidDaysIndex].validDays == '0') {
      //     sxDaysDisabledFlag =  true;
      //   }else{
      //     sxDaysDisabledFlag =  false;
      //   }
      //   return sxDaysDisabledFlag;
      // };
      


       //  ----------------------------------添加行子表  start --------------------------------------

      $scope.addSupplierInfo = function () {
        var supplierInfoDataobj = {
          supplierId: '',
          supplierName: '',
          weight: '',
          boxLength: '',
          boxBreadth: '',
          boxHeight: '',
          boxWeight: '',
          productLength: '',
          productBreadth: '',
          productHeight: '',
          innerpackageSpe: '',
          packageSpe: '',
          validDays: '',
          sxDays: '',
          place: '',
          productively: '',
          rateCode: '',
          currencyCost: '',
          price: '',
        };
        $scope.supplierlist.push(supplierInfoDataobj);
      };
      $scope.addBarcodeInfo = function () {
        var barcodeInfoDataobj = {
          obId: '',
          barcode: '',
          isMain: '',
        };
        $scope.barcodelist.push(barcodeInfoDataobj);
      };
      $scope.addqualInfo = function() {
        var qaInfoDataobj1 = {
          qaFiletype: '999',
          qaUrl: '',
          qaCodetype: '',
          qaCode: '',
          dateType: '',
          datecurent: '',
          groupId: $scope.params.qaFileId,
          groupName: $scope.params.qaFilename,
        };
        var qaInfoDataobj2 = {
          qaFiletype: '999',
          qaUrl: '',
          qaCodetype: '',
          qaCode: '',
          dateType: '',
          datecurent: '',
        };
        if($scope.userInfo.userType == '60') {
          $scope.qalist.push(qaInfoDataobj1);
        }else{
          $scope.qalist.push(qaInfoDataobj2);
        }
      };
      $scope.addImgInfo = function () {
        var imgInfoDataobj = {
          picType: '其他',
          picUrl: '',
        };
        $scope.picfilelist.push(imgInfoDataobj);
      };
      $scope.addPackageInfo = function () {
        var PackageInfoDataobj = {
          boxWeight: '',
          boxLength: '',
          boxBreadth: '',
          boxHeight: '',
          productLength: '',
          productBreadth: '',
          productHeight: '',
          innerpackageSpe: '',
          packageSpe: '',
          validDays: '',
          sxDays: '',
          place: '',
          productively: '',
        };
        $scope.packagelist.push(PackageInfoDataobj);
      };
      
      $scope.addMedicalfileInfo = function() {
        var MedicalfileDataobj = {
          qaFiletype: '999',
          qaUrl: '',
          qaCodetype: '',
          qaCode: '',
          dateType: '',
          datecurent: '',
        };
        $scope.medicalfilelist.push(MedicalfileDataobj);
      };
      $scope.addDrugfileInfo = function() {
        var drugfileDataobj = {
          qaFiletype: '999',
          qaUrl: '',
          qaCodetype: '',
          qaCode: '',
          dateType: '',
          datecurent: '',
        };
        $scope.drugfilelist.push(drugfileDataobj);
      };


      //  ---------------------------------- 资质文件类型匹配关系 start --------------------------------------

      $scope.getQaFiletypeIndex = function (index) {
        $scope.qaFiletypeIndex = index;
      }
      $scope.getmdqaFiletypeIndex = function (index) {
        $scope.mdqaFiletypeIndex = index;
      }
      $scope.getmeqaFiletypeIndex = function (index) {
        $scope.meqaFiletypeIndex = index;
      }
      // 普通商品资质文件类型change
      $scope.qaFiletypeChange = function () {
        switch($scope.qalist[$scope.qaFiletypeIndex].qaFiletype) {
          case '1': 
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '2':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '3':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '4':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '1';
            break;
          case '5':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '3';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '6':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '1';
            break;
          case '7':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '3';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '8':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '9':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '10':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
          case '11':
            $scope.qalist[$scope.qaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.qaFiletypeIndex].dateType = '3';
            break;
        }
      }

      // 药品资质文件类型change
      $scope.mdQaFiletypeChange = function () {
        switch($scope.qalist[$scope.mdqaFiletypeIndex].qaFiletype) {
          case '1': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '2': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '1';
          break;
          case '3': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '4': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '5': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;
          case '6': 
            $scope.qalist[$scope.mdqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.mdqaFiletypeIndex].dateType = '3';
          break;

        }
      }

      // 器械资质文件类型change
      $scope.meQaFiletypeChange = function (key) {
        switch(key) {
          case '1': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '2';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '1';
          break;
          case '2': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '4';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
          case '3': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
          case '4': 
            $scope.qalist[$scope.meqaFiletypeIndex].qaCodetype = '5';
            $scope.qalist[$scope.meqaFiletypeIndex].dateType = '3';
          break;
        }
      }
      
      // 药品与器械的资质文件类型的显示控制
      $scope.Number=function(value)
      {
    	  if(isNaN(parseInt(value))) {
    		  return 1;
    		}else{
    		  return 0;
    		}
      }

      // 药品项目品种Change
      $scope.projectClassChange = function() {
        $scope.paramsDrug.project = '';
      }

      //  ---------------------------------- 上传 start --------------------------------------

      // 上传获取当前行数
      $scope.getQaFileIndex = function(index) {
        $scope.qalistIndex = index;
      };
      //资质文件上传
      $scope.qaFileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      //资质文件上传后调用的方法
      $scope.qaFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
            var filename=res.data[0].fileName;
            var length=filename.length;
            var index=filename.lastIndexOf(".")+1;
            
            var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {

              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.qalist[$scope.qalistIndex].qaUrl = res.data[0].filePath; 
              $scope.qalist[$scope.qalistIndex].qaFileName = res.data[0].fileName; 
              $scope.showloadingFlag = false;
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
          SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.qaFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

    

      // 上传获取当前行数
      $scope.getPicIndex = function(index) {
        $scope.picListIndex = index;
      };

      //图片文件上传
      $scope.picFileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,

      });
      //图片文件上传后调用的方法
      $scope.picFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {

            // var filename=res.data[0].fileName;
            // var length=filename.length;
            // var index=filename.lastIndexOf(".")+1;
            
            // var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {
              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.picfilelist[$scope.picListIndex].picUrl = res.data[0].filePath; 
              $scope.picfilelist[$scope.picListIndex].picName = res.data[0].fileName; 
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.picFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };
      

      // 上传获取当前行数
      $scope.getFileIndex = function(index) {
        $scope.filelistIndex = index;
      };
      //附件文件上传
      $scope.FileUploader = new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      //附件文件上传后调用的方法
      $scope.FileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
          var filename=res.data[0].fileName;
          var length=filename.length;
          var index=filename.lastIndexOf(".")+1;
          var suffx=filename.substring(index,length);
          // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png'&&suffx!='xlsx'&&suffx!='png') {
          //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
          //   return;
          // }else {
            SIEJS.alert("上传成功！", "success", "确定");
            $('#saafLoading').hide();
            $scope.filelist[$scope.filelistIndex].fileUrl = res.data[0].filePath; 
            $scope.filelist[$scope.filelistIndex].fileName = res.data[0].fileName; 
          // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.FileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 上传获取当前行数
      $scope.getDrugFileIndex = function(index) {
        $scope.DrugFileIndex = index;
      };
      // 药品附件文件上传
      $scope.DrugFileUploader= new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
      });
      // 药品附件文件上传后调用的方法
      $scope.DrugFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
            var filename=res.data[0].fileName;
            var length=filename.length;
            var index=filename.lastIndexOf(".")+1;
            
            var suffx=filename.substring(index,length);

            // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
            //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
            //   return;
            // }else {
              SIEJS.alert("上传成功！", "success", "确定");
              $('#saafLoading').hide();
              $scope.qalist[$scope.DrugFileIndex].qaUrl = res.data[0].filePath; 
              $scope.qalist[$scope.DrugFileIndex].qaFileName = res.data[0].fileName; 
            // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.DrugFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 上传获取当前行数
      $scope.getMedFileIndex = function(index) {
        $scope.medFileIndex = index;
      };
      // 器械附件文件上传
      $scope.medFileUploader= new FileUploader({
        url: URLAPI.imgUpload,
        autoUpload: true,
        isUploading: true
      });
      // 器械附件文件上传后调用的方法
      $scope.medFileUploader.onCompleteItem = function (fileItem, res, status, headers) {
        if (res.status == 'S') {
          var filename=res.data[0].fileName;
          var length=filename.length;
          var index=filename.lastIndexOf(".")+1;
          
          var suffx=filename.substring(index,length);

          // if(suffx!='pdf'&&suffx!='jpeg'&&suffx!='jpg'&&suffx!='png') {
          //   SIEJS.alert("请选择pdf,jpeg,jpg,png文件！", "error", "确定");
          //   return;
          // }else {
            SIEJS.alert("上传成功！", "success", "确定");
            $('#saafLoading').hide();
            $scope.qalist[$scope.medFileIndex].qaUrl = res.data[0].filePath; 
            $scope.qalist[$scope.medFileIndex].qaFileName = res.data[0].fileName; 
          // }
        };
        if (res.status == 'E') {
          $('#saafLoading').hide();
            SIEJS.alert(res.msg, "error", "确定");
        };
      };

      $scope.medFileUploader.onBeforeUploadItem = function() {
        $('#saafLoading').show();
      };

      // 重新上传
      $scope.UploaderReset = function(index, list) {
        if(list == 'qalist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.qalist[index].qaUrl = '';
          });
        }else if(list == 'picfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.picfilelist[index].picUrl = '';
          });
        }else if(list == 'filelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.filelist[index].fileUrl = '';
          });
        }else if(list == 'drugfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.drugfilelist[index].fileUrl = '';
          });
        }else if(list == 'medicalfilelist') {
          SIEJS.confirm('重新上传需删除原有文件', '是否确定删除？', '确定', function () {
            $scope.medicalfilelist[index].fileUrl = '';
          });
        }
        
      };

      // 上传文件预览
      $scope.previewImg = function (index, list) {
        if(list == 'picfilelist') {
          var indexURL = $scope.picfilelist[index].picUrl;
        }else if (list == 'qalist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }else if (list == 'filelist') {
          var indexURL = $scope.filelist[index].fileUrl;
        }else if (list == 'drugfilelist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }else if (list == 'medicalfilelist') {
          var indexURL = $scope.qalist[index].qaUrl;
        }
        window.open(indexURL);
      };

      //  ---------------------------------- 供应商弹窗 start --------------------------------------

      // 显示弹窗数据
      $scope.showSupplierNameMol = function(index) {
        $('#supplierNameMol').modal('toggle');
        $scope.SupplierNameIndex = index;
      };
      // 选择弹窗数据
      $scope.SupplierNameReturn = function (key, value, currentList) {
        $scope.supplierlist[$scope.SupplierNameIndex].supplierName = currentList[0].userName;
        $scope.supplierlist[$scope.SupplierNameIndex].supplierId = currentList[0].userId;
      };
      

      // 显示资质组数据
      $scope.showqaGroupNameMol1 = function() {
        $('#qaGroupNameMol1').modal('toggle');
      };
      // 选择资质组数据
      $scope.qaGroupNameReturn1 = function (key, value, currentList) {
        $scope.TgroupName = currentList[0].qaGroupName;
        $scope.TgroupId = currentList[0].qaGroupCode;
        $scope.qalist.forEach((item) => {
          item.groupId = currentList[0].qaGroupCode;
          item.groupName = currentList[0].qaGroupName;
        });
      };


      // 显示资质组数据
      $scope.showqaGroupNameMol2 = function(index) {
        $('#qaGroupNameMol2').modal('toggle');
        $scope.showqaGroupNameIndex = index;
      };
      // 选择资质组数据
      $scope.qaGroupNameReturn2 = function (key, value, currentList) {
        $scope.qalist[$scope.showqaGroupNameIndex].groupId = currentList[0].qaGroupCode;
        $scope.qalist[$scope.showqaGroupNameIndex].groupName = currentList[0].qaGroupName;

        // $scope.params.qaFilename = currentList[0].qaGroupName;
        // $scope.params.qaFileId = currentList[0].qaGroupCode;
        // $scope.qalist.forEach((item) => {
        //   item.groupId = currentList[0].qaGroupCode;
        //   item.groupName = currentList[0].qaGroupName;
        // });
      };


      // 税收分类编码
      $scope.rateCodeShowMol = function(index) {
        $('#rateCodeShowMolID').modal('toggle');
        $scope.rateCodeIndex = index;
      }
      $scope.rateCodeMolReturn = function(key, value, currentList) {
        $scope.supplierlist[$scope.rateCodeIndex].rateCode = currentList[0].plmTaxTypeCode;
      }
      

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
      
    }]
  );
})