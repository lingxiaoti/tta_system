/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sie.watsons.base.redisUtil.EmailModule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jason_JH.Liu
 */
public class EmailParameters {
    public String appcode;
    public String apptoken;
    public String to;
    public String subject;
    public String body;
    public String carboncopys;
    public String blindcarboncopys;
    public String bodyencoding; /* UTF8 / UTF7 / UTF32 / Unicode / Default / BigendianUnicode / ASCII */
    public String bodyformat;/* html / text */
    public String priority;/* high / low / normal */
    public List<String> attachments;
    public int throughoutsidegateway;
    public String from;
    public String frompwd;
    public String fromnickname;
    public List<ecLinkedResource> linkedresources;

    public EmailParameters(){
        appcode = "";
        apptoken = "";
        to = "";
        subject = "";
        body = "";
        carboncopys = "";
        blindcarboncopys = "";
        bodyencoding = "UTF8";
        bodyformat = "html";
        priority = "normal";
        throughoutsidegateway = 0;
        attachments = new ArrayList<String>();
        linkedresources = new ArrayList<ecLinkedResource>();
    }
}
