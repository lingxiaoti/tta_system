var express = require('express');
var router = express.Router();
var debug = require('debug')('printapp:server');
const puppeteer = require('puppeteer');
const pdftk = require('node-pdftk');
var bodyParser = require('body-parser');
const FormData = require('form-data');
const request = require('request');
var path = require('path');
const fs = require("fs");
var Redis = require("ioredis");
console.time('打开浏览器耗时');
var cluster;
// start 需要修改在不同环境 例如 base  uat prod

var clusterFLag = process.env.NODE_ENV;
var url = process.env.IP;
var loginName = process.env.USER_NAME;
var password = process.env.PASSWORD;
console.log('clusterFLag:' + clusterFLag );
// end 需要修改在不同环境 例如 base  uat prod
if (clusterFLag == 'prod') {
    cluster = new Redis.Cluster([{port: 5000, host: "10.82.31.210"}, {port: 5002, host: "10.82.31.210"}, {port: 5000, host: "10.82.31.212"},
            {port: 5002, host: "10.82.31.212"}, {port: 5000, host: "10.82.31.211"}, {port: 5002, host: "10.82.31.211"}
        ],  {redisOptions: {password: "ttas23"}}
    );
}else if (clusterFLag == 'uat') {
    cluster = new Redis.Cluster([{port: 5000, host: "10.82.24.112"}, {port: 5000, host: "10.82.24.114"}, {port: 5002, host: "10.82.24.113"},
            {port: 5002, host: "10.82.24.112"}, {port: 5002, host: "10.82.24.114"}, {port: 5000, host: "10.82.24.113"}
        ],  {redisOptions: {password: "qcs123"}}
    );
}else {
    cluster = new Redis.Cluster([{port: 6379, host: "127.0.0.1"}, {port: 6380, host: "127.0.0.1"}, {port: 6381, host: "127.0.0.1"},
            {port: 6382, host: "127.0.0.1"}, {port: 6383, host: "127.0.0.1"}, {port: 6384, host: "127.0.0.1"}
        ],  {redisOptions: {password: "123456"}}
    );
}







var browserV ;
async function browserInit() {
    browserV = await puppeteer.launch({headless:true,args:['--no-sandbox', '--disable-setuid-sandbox'],defaultViewport:{width:1366,height:768}});
}
function formatDecimal(num, decimal) {
    num = num.toString();
    let index = num.indexOf('.');
    if (index !== -1) {
        num = num.substring(0, decimal + index + 1)
    } else {
        num = num.substring(0)
    }
    return parseFloat(num).toFixed(decimal)
}
//browserInit();
console.timeEnd('打开浏览器耗时');

router.post('/print',function(req, res, next) {
    const paremsString = req.body.params ;
    console.log("paremsString:" + paremsString);
    let parems ;
    if ( req.body.params) {
        parems = JSON.parse(paremsString);
        console.log("parems:" + parems);
    } else {
        parems = req.params ;
    }

    //const times = (new Date()) - 0;
    const times =  0;

    if ( !parems.Certificate) {
        res.json({ status: 'E',msg:'Certificate不能为空' });
        return;
    }
    if ( !parems.proposalId) {
        res.json({ status: 'E',msg:'proposalId 不能为空' });
        return;
    }
    if ( !parems.userId) {
        res.json({ status: 'E',msg:'userId 不能为空' });
        return;
    }
    if ( !parems.type) {
        res.json({ status: 'E',msg:'type 不能为空' });
        return;
    }
    if ((typeof parems.isTA) !=="boolean") {
        res.json({ status: 'E',msg:'是否打印贸易协议 不能为空' });
        return;
    }
    if ((typeof parems.isQuestion) !=="boolean") {
        res.json({ status: 'E',msg:'是否打印问卷调查 不能为空' });
        return;
    }
    let tabs = [];
    let tabs2 = [];
    if (parems.isTA && parems.isQuestion) {
        //品牌计划表、问卷调查表、TermsAnalysis、TTA Terms Comparision(TY VS LY)、贸易条款、贸易协议

        tabs = ['A','B','C','D','E','F'];
        //1. Trems analysis 2. TTA 贸易条款 3. 贸易条款之补充协议（根据促销陈列费、宣传单张费用的条款字段，有“按协定标准收取”展示，反之隐藏）
        //4. 条款对照 （TY vs LY） 5. 品牌计划表6. 问券
        tabs2 = ['C','E','F','D','A','B'];
    } else if(parems.isTA && !parems.isQuestion){
        tabs = ['A','C','D','E','F'];
        tabs2 = ['C','E','F','D','A'];
    } else if(!parems.isTA && parems.isQuestion){
        tabs = ['A','B','C','D','E'];
        //1. Trems analysis 2. TTA 贸易条款 3. 贸易条款之补充协议（根据促销陈列费、宣传单张费用的条款字段，有“按协定标准收取”展示，反之隐藏）
        //4. 条款对照 （TY vs LY） 5. 品牌计划表6. 问券
        tabs2 = ['C','E','D','A','B'];
    } else {
        tabs = ['A','C','D','E'];
        //1. Trems analysis 2. TTA 贸易条款 3. 贸易条款之补充协议（根据促销陈列费、宣传单张费用的条款字段，有“按协定标准收取”展示，反之隐藏）
        //4. 条款对照 （TY vs LY） 5. 品牌计划表6. 问券
        tabs2 = ['C','E','D','A'];
    }

    let  printPdfArray = [] ;
    let printPdfInputObject = {};
    let printPdfInputV = '\'{';
    let printPdfCatName = '' ;
    let outputPdfName = '';
    let outputPdfBookmarkName = '';
    let BookmarkPdfName = '';
    let pdfName = '';
    let pdfName2 = '';
    for (let i =0;i<tabs.length;i++) {
        pdfName = __dirname + '/../pdf_file/'+ parems.type + '_' + '_' +parems.proposalId + '_' + tabs[i] + '_' + '00000' + '_' + times + '.pdf' ;
        printPdfInputV += '"'+ tabs[i] +'":' + '"' + pdfName + '"';

        printPdfArray.push( {'name':tabs[i],'value' :pdfName});
    }
    for (let i =0;i<tabs2.length;i++) {
        pdfName2 = __dirname + '/../pdf_file/'+ parems.type + '_' + '_' +parems.proposalId + '_' + tabs2[i] + '_' + '00000' + '_' + times + '.pdf' ;
        printPdfCatName += tabs[i] + ' ';
        printPdfInputObject[tabs[i]] = pdfName2 ;

    }
    printPdfCatName = printPdfCatName.substr(0, printPdfCatName.length - 1);
    outputPdfName = __dirname + '/../pdf_file/' + parems.type + '_' +  '_' +parems.proposalId + '_' + 'CAT' + '_' + times + '.pdf' ;
    outputPdfBookmarkName =  __dirname + '/../pdf_file/' + parems.type + '_' +  '_' +parems.proposalId  + '_' + 'CAT_BOOKMARK' + '_' + times + '.pdf' ;
    BookmarkPdfName = __dirname + '/../pdf_file/'+ parems.type + '_' +  '_' +parems.proposalId +  '_' + 'CAT_BOOKMARK' + '_' + times + '.txt' ;





    console.time('开始-结束耗时');
    let certificate  = parems.Certificate;
    let key = "cookie_" + certificate;
    cluster.hget(key,'sessionInfo').then(function(result){
        if ( result && JSON.parse(result).certificate === parems.Certificate) {
            printPDfFunc(req, res,puppeteer,parems,printPdfArray,printPdfInputObject,printPdfCatName,outputPdfName,outputPdfBookmarkName,BookmarkPdfName,pdfName,url);

        }else {
            res.json({ status: 'E',msg:'请重新登陆' });

        }
    })
});
async  function printPDfFunc (req, res,puppeteer,parems,printPdfArray,printPdfInputObject,printPdfCatName,outputPdfName,outputPdfBookmarkName,BookmarkPdfName,pdfName,url){
    browser = await puppeteer.launch({headless:true,args:['--no-sandbox', '--disable-setuid-sandbox'],defaultViewport:{width:1366,height:768}});
    let scaleArray = [];
    console.time('创建页面耗时');
    const page = await browser.newPage();
    console.timeEnd('创建页面耗时');
    console.time('打开登陆页面加载耗时');
    await page.goto('http://' + url + '/print.html#/base/login');
    console.timeEnd('打开登陆页面加载耗时');
    let numAttr = 0;

    console.time('登陆加载耗时');
    await page.waitFor('body > div.h100p.ng-scope > div > div > div > div > form > div > div:nth-child(2) > input');
    await page.waitFor('body > div.h100p.ng-scope > div > div > div > div > form > div > div.mb10 > input');
    await page.waitFor('body > div.h100p.ng-scope > div > div > div > div > form > div > div.tc > button > span');
    await page.type('body > div.h100p.ng-scope > div > div > div > div > form > div > div:nth-child(2) > input', loginName);
    await page.type('body > div.h100p.ng-scope > div > div > div > div > form > div > div.mb10 > input', password);
    await page.click('body > div.h100p.ng-scope > div > div > div > div > form > div > div.tc > button > span'); //登录
    await page.waitFor('#main-menu > li:nth-child(1) > a > span');
    console.timeEnd('登陆加载耗时');
    console.time('打开打印页面耗时');
    console.log('地址：http://' + url + '/print.html#/base/proposalDetailPDF/' + parems.proposalId);
    await page.goto('http://' + url + '/print.html#/base/proposalDetailPDF/' + parems.proposalId);
    await page.waitForSelector('#printPDFirst',{'visible':true});
    //await page.waitFor('#printPDFirst');
    console.timeEnd('打开打印页面耗时');
//品牌计划表、问卷调查表、TermsAnalysis、TTA Terms Comparision(TY VS LY)、贸易条款、贸易协议
// A4的纸张大小  210mm×297mm  网页默认 DPI 96
    // 1英寸为 2.54 CM 25.4 mm
    // 所以对应的像素是 210/25.4*96 = 793.7 px  297/25.4*96 = 1122.51969 px  缩放比例为  793.7/高度
    console.time('打印耗时');
    const printPDFBrandPlnHeightO1 = await page.$$('#brandPln');
    const printPDFBrandPlnHeightO2 = await printPDFBrandPlnHeightO1[0].boundingBox();
    const printPDFBrandPlnHeight   =  await printPDFBrandPlnHeightO2.height ;
    const brandTableIds = await page.$$('#brandTableId');
    const brandTableId2 = await brandTableIds[0].boundingBox();
    const brandWidth = await brandTableId2.width ;
    const barndWidthScale = formatDecimal( ( (1100 /  brandWidth ).toFixed(2)) > 1 ? 1:(1100/  brandWidth) ,2);
    const barndHeightScale = formatDecimal(  (793.7 /  (printPDFBrandPlnHeight + 19) ).toFixed(2) > 1 ? 1:(793.7 /  (printPDFBrandPlnHeight +19)),2);
    if ( (printPDFBrandPlnHeight + 19) > 768) {
        await page.setViewport({width:1366,height:printPDFBrandPlnHeight + 19});
    }
    const brandPlnScale = (barndWidthScale > barndHeightScale ? barndHeightScale:barndWidthScale) ;
    scaleArray.push( brandWidth );
    scaleArray.push( brandPlnScale );
    scaleArray.push( barndHeightScale );
    await page.pdf({path: printPdfArray[0].value, format: 'A4',pageRanges:'1',scale:Number(brandPlnScale) + 0.0001,printBackground:true,landscape:true}).catch(err => console.log(err));


    if(parems.isQuestion) {
        await page.click('#printPDFQuestionnaire');//问卷调查表
        await page.waitForSelector('#questionnaire',{'visible':true});
        const printPDFQuestionnaireHeightO1 = await page.$$('#questionnaire');
        const printPDFQuestionnaireHeightO2 = await printPDFQuestionnaireHeightO1[0].boundingBox();
        const printPDFQuestionnaireHeight   =  await printPDFQuestionnaireHeightO2.height ;
        const questionnaireHeightScale = formatDecimal( (793.7 /  (printPDFQuestionnaireHeight + 19) ).toFixed(2) > 1 ? 1:(793.7 /  (printPDFQuestionnaireHeight +19)),2);
        if ( (printPDFQuestionnaireHeight + 19) > 768) {
            await page.setViewport({width:1366,height:printPDFQuestionnaireHeight + 19});
        }

        scaleArray.push( questionnaireHeightScale );
        await page.pdf({path: printPdfArray[++numAttr].value, format: 'A4',pageRanges:'1',scale:Number(questionnaireHeightScale) + 0.0001,printBackground:true,landscape:true});
    }

    await page.click('#printPDFNewAnalysis'); //TermsAnalysis
    await page.waitForSelector('#termsNewAnalysis',{'visible':true});
    const printPDFNewAnalysisHeightO1 = await page.$$('#analysisDataTableId');
    const printPDFNewAnalysisHeightO2 = await printPDFNewAnalysisHeightO1[0].boundingBox();
    const printPDFNewAnalysisHeight   =  await printPDFNewAnalysisHeightO2.height ;
    const newAnalysisWidth = await printPDFNewAnalysisHeightO2.width ;
    const newAnalysisWidthScale = formatDecimal(  ( (1122.51969 /  newAnalysisWidth).toFixed(2)) > 1 ? 1:(1122.51969 /  newAnalysisWidth),2);
    const newAnalysisHeightScale = formatDecimal( (793.7 /  (printPDFNewAnalysisHeight + 19)).toFixed(2) > 1 ? 1:(793.7 /  (printPDFNewAnalysisHeight +19)),2);
    if ( (printPDFNewAnalysisHeight + 19) > 768) {
        await page.setViewport({width:1366,height:printPDFNewAnalysisHeight + 19});
    }
    scaleArray.push( (newAnalysisWidthScale > newAnalysisHeightScale ? newAnalysisHeightScale:newAnalysisWidthScale) );
    await page.pdf({path: printPdfArray[++numAttr].value, format: 'A4',pageRanges:'1',scale:0.45,printBackground:true,landscape:true});


    await page.click('#printPDFComparision'); //TTA Terms Comparision(TY VS LY)
    await page.waitForSelector('#termsComparision',{'visible':true});
    const printPDFComparisionHeightO1 = await page.$$('#termsComparision');
    const printPDFComparisionHeightO2 = await printPDFComparisionHeightO1[0].boundingBox();
    const printPDFComparisionHeight   =  await printPDFComparisionHeightO2.height ;
    const comparisionHeightScale = formatDecimal(  (793.7 /  (printPDFComparisionHeight + 19)).toFixed(2) > 1 ? 1:(793.7 /  (printPDFComparisionHeight +19)),2);

    if ( (printPDFComparisionHeight + 19) > 768) {
        await page.setViewport({width:1366,height:printPDFComparisionHeight + 19});
    }
    scaleArray.push( comparisionHeightScale );
    await page.pdf({path: printPdfArray[++numAttr].value, format: 'A4',pageRanges:'1',scale:Number(comparisionHeightScale)  + 0.0001,printBackground:true,landscape:true});


    await page.click('#printPDFClause'); //贸易条款
    await page.waitForSelector('#tradeClause',{'visible':true});
    const printPDFClauseHeightO1 = await page.$$('#tradeClause');
    const printPDFClauseHeightO2 = await printPDFClauseHeightO1[0].boundingBox();
    const printPDFClausePlnHeight   =  await printPDFClauseHeightO2.height ;
    const clausePlnHeightScale = 0.02 + Number((793.7 /  (printPDFClausePlnHeight + 19)).toFixed(2) > 1 ? 1:(793.7 /  (printPDFClausePlnHeight +19)).toFixed(2))  ;

    if ( (printPDFClausePlnHeight + 19) > 768) {
        await page.setViewport({width:1366,height:printPDFClausePlnHeight + 19});
    }
    scaleArray.push( clausePlnHeightScale );
    await page.pdf({path: printPdfArray[++numAttr].value, format: 'A4',pageRanges:'1',scale:Number(clausePlnHeightScale)  + 0.01 + 0.0001,printBackground:true,landscape:true});


    if (parems.isTA) {
        await page.click('#printPDFDeptFeeReport'); //贸易协议
        await page.waitForSelector('#ttaDeptFeeReport',{'visible':true});
        const printPDFDeptFeeReportHeightO1 = await page.$$('#ttaDeptFeeReport');
        const printPDFDeptFeeReportHeightO2 = await printPDFDeptFeeReportHeightO1[0].boundingBox();
        const printPDFDeptFeeReportHeight   =  await printPDFDeptFeeReportHeightO2.height ;
        const deptFeeReportHeightScale = formatDecimal( (793.7 /  (printPDFDeptFeeReportHeight + 19)).toFixed(2) > 1 ? 1:(793.7 /  (printPDFDeptFeeReportHeight +19)),2);

        if ( (printPDFDeptFeeReportHeight + 19) > 768) {
            await page.setViewport({width:1366,height:printPDFDeptFeeReportHeight + 19});
        }
        scaleArray.push( deptFeeReportHeightScale );
        await page.pdf({path: printPdfArray[++numAttr].value, format: 'A4',pageRanges:'1',scale:Number(deptFeeReportHeightScale)  + 0.0001,printBackground:true,landscape:true});
    }

    console.timeEnd('打印耗时');
    console.log(scaleArray);
    await browser.close().then(function () {

    });


    //合并pdf
    console.time('合并PDF耗时');
    pdftk
        .input(printPdfInputObject)
        .cat(printPdfCatName)
        .output(outputPdfName)
        .then(buffer => {
            let text = '';
            //品牌计划表、问卷调查表、TermsAnalysis、TTA Terms Comparision(TY VS LY)、贸易条款、贸易协议
            let  names = [];
            if (parems.isTA && parems.isQuestion) {
                names = ['TermsAnalysis','贸易条款','贸易协议','TTA Terms Comparision(TY VS LY)','品牌计划表','问卷调查表'];
            } else if(parems.isTA && !parems.isQuestion){
                names = ['TermsAnalysis','贸易条款','贸易协议','TTA Terms Comparision(TY VS LY)','品牌计划表'];
            } else if(!parems.isTA && parems.isQuestion){
                names = ['TermsAnalysis','贸易条款','TTA Terms Comparision(TY VS LY)','品牌计划表','问卷调查表'];
            } else {
                names = ['TermsAnalysis','贸易条款','TTA Terms Comparision(TY VS LY)','品牌计划表'];
            }

            for (let i = 1;i<=names.length;i++) {
                text += 'BookmarkBegin\n';
                text += 'BookmarkTitle: ' + names[i-1] + '\n';
                text += 'BookmarkLevel: 1\n';
                text += 'BookmarkPageNumber: ' +i+ '\n';
            }
            fs.writeFile(BookmarkPdfName, text, (err, data) => {
                if (err) throw err;
                pdftk
                    .input({
                        Z: outputPdfName

                    })
                    .updateInfoUtf8(BookmarkPdfName)
                    .output(outputPdfBookmarkName)
                    .then(buffer => {
                        console.log('准备发送文件');
                        let orderNoV = parems.orderNo;
                        let fileName = parems.orderNo + '_' + parems.versionCode +  '.pdf';
                        let functionId = 'TTA_PROPOSAL_HEADER_PDF';
                        let id = parems.proposalId;
                        let formData = {
                            'id':id,
                            'fileName':fileName,
                            'functionId':functionId,
                            'orderNo':orderNoV,
                            'file':fs.createReadStream(outputPdfBookmarkName)
//                    "id":parems.proposalId,
//                    "fileName":parems.orderNo + '_' + parems.versionCode +  '.pdf',
//                    "functionId":"TTA_PROPOSAL_HEADER_PDF",
//                    "orderNo": parems.OrderNo
//                    //"file":fs.createReadStream(outputPdfBookmarkName)
                        };
                        request.post({
                            url: 'http://' + process.env.IP + '/api/ttaServer/ttaProposalConAttrLineService/ttaPropoaslUploadFile',
                            headers: {//设置请求头
                                "Certificate": parems.Certificate
                            },
                            formData: formData
                        }, function (error, response, body) {

                            if (!error && response.statusCode == 200) {
                                let bodyJson = JSON.parse(body);
                                if (bodyJson.status == 'E') {
                                    res.json({status: 'E', msg: body.msg});

                                } else {
                                    res.json({status: 'S', msg: '成功'});

                                }
                            } else {
                                res.json({status: 'E', error});
                                console.log("e:" + error);
                                console.log("e:" + response);
                                console.log("e:" + body);

                            }
                        });
                    })
                    .catch(err => {
                        console.log('错误2' + err);
                    });
                console.timeEnd('合并PDF耗时');
            });
        })
        .catch(err => {
            console.log('错误1' + err);
            // handle errors
        });




    console.timeEnd('开始-结束耗时');
}
module.exports = router;


