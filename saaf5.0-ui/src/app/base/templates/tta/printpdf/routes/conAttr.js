var express = require('express');
var router = express.Router();
var debug = require('debug')('printapp:server');
const puppeteer = require('puppeteer');
const pdftk = require('node-pdftk');
var bodyParser = require('body-parser');
const FormData = require('form-data');
const request = require('request');
const oss = require('os');

var path = require('path');
const fs = require("fs");
var Redis = require("ioredis");
console.time('打开浏览器耗时');
var cluster;
// start 需要修改在不同环境 例如 base  uat prod
// process.env = {
//     NODE_ENV:"development",
//     IP:"127.0.0.1:8080",
//     USER_NAME:"admin",
//     PASSWORD:"Pw123456!$"
// }
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

router.post('/conattr',function(req, res, next) {
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
    if ( !parems.userId) {
        res.json({ status: 'E',msg:'userId 不能为空' });
        return;
    }
    let  printPdfArray = [] ;
    let printPdfInputV = '\'{';
    let outputPdfName = '';
    let outputPdfBookmarkName = '';
    let BookmarkPdfName = '';
    let pdfName = '';

    // outputPdfName = __dirname + '/../pdf_file/' + parems.type + '_' +  '_' +parems.proposalId + '_' + 'CAT' + '_' + times + '.pdf' ;
    // outputPdfBookmarkName =  __dirname + '/../pdf_file/' + parems.type + '_' +  '_' +parems.proposalId  + '_' + 'CAT_BOOKMARK' + '_' + times + '.pdf' ;
    // BookmarkPdfName = __dirname + '/../pdf_file/'+ parems.type + '_' +  '_' +parems.proposalId +  '_' + 'CAT_BOOKMARK' + '_' + times + '.txt' ;





    let certificate  = parems.Certificate;
    let key = "cookie_" + certificate;
    cluster.hget(key,'sessionInfo').then(function(result){
        if ( result && JSON.parse(result).certificate === parems.Certificate) {
            printPDfFunc(req, res,puppeteer,parems,printPdfArray,outputPdfName,outputPdfBookmarkName,BookmarkPdfName,pdfName,url);

        }else {
            res.json({ status: 'E',msg:'请重新登陆' });

        }
    })
});
async  function printPDfFunc (req, res,puppeteer,parems,printPdfArray,outputPdfName,outputPdfBookmarkName,BookmarkPdfName,pdfName,url){
    browser = await puppeteer.launch({headless:true,args:['--no-sandbox', '--disable-setuid-sandbox'],defaultViewport:{width:1366,height:768}});
    //browser = await puppeteer.launch({headless:true,defaultViewport:{width:1366,height:768}});
    let scaleArray = [];
    console.time('创建页面耗时');
    const page = await browser.newPage();
    console.timeEnd('创建页面耗时');
    console.time('打开登陆页面加载耗时');
    await page.goto('http://' + url + '/print.html#/base/login');
    console.timeEnd('打开登陆页面加载耗时');


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

    for (let i = 0 ;i < parems.urlsAndParams.length; i++ ) {
        //贸易条款
        let param = parems.urlsAndParams[i];
        let printPdfInputObject = {};
        let printPdfCatName = '';
        console.log('http://' + url + '/print.html#/base' + param.url);
        await page.goto('http://' + url + '/print.html#/base' + param.url);
        await page.waitForSelector('#printPDFirst', {'visible': true});
        console.timeEnd('打开打印页面耗时');
        let printPDFClauseHeightO1 = await page.$$('#tradeClause');
        let printPDFClauseHeightO2 = await printPDFClauseHeightO1[0].boundingBox();
        let printPDFClausePlnHeight = await printPDFClauseHeightO2.height;
        let clausePlnHeightScale = 0.02 + Number((793.7 / (printPDFClausePlnHeight + 19)).toFixed(2) > 1 ? 1 : (793.7 / (printPDFClausePlnHeight + 19)).toFixed(2));

        if ((printPDFClausePlnHeight + 19) > 768) {
            await page.setViewport({width: 1366, height: printPDFClausePlnHeight + 19});
        }
        let pdfName = __dirname + '/../pdf_file/' + param.vendor_nbr + '_' + param.OrderNo + '_' + '01' + '.pdf';
        printPdfInputObject["A"] = pdfName;
        printPdfCatName += "A" + ' ';
        await page.pdf({
            path: pdfName,
            format: 'A4',
            pageRanges: '1',
            scale: Number(clausePlnHeightScale) + 0.01 + 0.0001,
            printBackground: true,
            landscape: true
        });

        //贸易协议
        let printPDFId3O = await page.$$('#printPDFId3');
        if (printPDFId3O.length != 0) {
            await page.click('#printPDFId2'); //贸易协议
            await page.waitForSelector('#ttaDeptFeeReport', {'visible': true});
            let printPDFDeptFeeReportHeightO1 = await page.$$('#ttaDeptFeeReport');
            let printPDFDeptFeeReportHeightO2 = await printPDFDeptFeeReportHeightO1[0].boundingBox();
            let printPDFDeptFeeReportHeight = await printPDFDeptFeeReportHeightO2.height;
            let deptFeeReportHeightScale = formatDecimal((793.7 / (printPDFDeptFeeReportHeight + 19)).toFixed(2) > 1 ? 1 : (793.7 / (printPDFDeptFeeReportHeight + 19)), 2);

            if ((printPDFDeptFeeReportHeight + 19) > 768) {
                await page.setViewport({width: 1366, height: printPDFDeptFeeReportHeight + 19});
            }
            scaleArray.push(deptFeeReportHeightScale);
            let pdfName2 = __dirname + '/../pdf_file/' + param.vendor_nbr + '_' + param.OrderNo + '_' + '02' + '.pdf';
            await page.pdf({
                path: pdfName2,
                format: 'A4',
                pageRanges: '1',
                scale: Number(deptFeeReportHeightScale) + 0.0001,
                printBackground: true,
                landscape: true
            });

            printPdfInputObject["B"] = pdfName2;
            printPdfCatName += "B";

            //合并pdf

            let pdfName3 = __dirname + '/../pdf_file/' + param.vendor_nbr + '_' + param.OrderNo + '_' + '03' + '.pdf';
            const form = new FormData();
            pdftk
                .input(printPdfInputObject)
                .cat(printPdfCatName)
                .output(pdfName3)
                .then(buffer => {
                    let formData = {
                        "id": parems.id,
                        "fileName": param.vendor_nbr + '_' + param.OrderNo + '_' + '00' + '.pdf',
                        "functionId": "TTA_CON_ATTR_LINE",
                        "vendorCode": param.vendor_nbr,
                        "orderNo": param.OrderNo,
                        "i": i,
                        "length": parems.urlsAndParams.length - 1,
                        "file": fs.createReadStream(pdfName3)
                    };
                    request.post({
                        url: 'http://' + process.env.IP + '/api/ttaServer/ttaProposalConAttrLineService/ttaConAttrLineUploadFile',
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
                                if (i == parems.urlsAndParams.length - 1) {
                                    res.json({status: 'S', msg: '成功'});

                                }
                            }
                        } else {
                            res.json({status: 'E', error});
                            console.log("e:" + error);
                            console.log("e:" + response);
                            console.log("e:" + body);

                        }
                    });

                });
        } else {
            let formData = {
                "id": parems.id,
                "fileName": param.vendor_nbr + '_' + param.OrderNo + '_' + '00' + '.pdf',
                "functionId": "TTA_CON_ATTR_LINE",
                "vendorCode": param.vendor_nbr,
                "orderNo": param.OrderNo,
                "i": i,
                "length": parems.urlsAndParams.length - 1,
                "file": fs.createReadStream(pdfName)
            };
            request.post({
                url: 'http://' + process.env.IP + '/api/ttaServer/ttaProposalConAttrLineService/ttaConAttrLineUploadFile',
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
                        if (i == parems.urlsAndParams.length - 1) {
                            res.json({status: 'S', msg: '成功'});

                        }
                    }
                } else {
                    res.json({status: 'E', error});
                    console.log("e:" + error);
                    console.log("e:" + response);
                    console.log("e:" + body);

                }
            });

        }



    }

    await browser.close().then(function () {

    });
}
module.exports = router;


