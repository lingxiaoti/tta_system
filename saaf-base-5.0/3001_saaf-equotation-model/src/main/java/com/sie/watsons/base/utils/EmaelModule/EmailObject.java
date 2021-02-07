/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sie.watsons.base.utils.EmaelModule;

import java.util.List;

/**
 *
 * @author Jason_JH.Liu
 */
public class EmailObject {
            public String appcode;
            public String requestid;
            public String appkey;

            public String to;
            public String subject;
            public String body;
            public String carboncopys;
            public String blindcarboncopys;
            public String bodyencoding;/* UTF8 / UTF7 / UTF32 / Unicode / Default / BigendianUnicode / ASCII */
            public String bodyformat;/* html / text */
            public String priority;/* high / low / normal */

            public String hasattached; /* y / n */
            public List<EmailAttachment> attachments;
            public int throughoutsidegateway;
            public String from;
            public String frompwd;
            public String fromnickname;

            public List<EmailLinkedResource> linkedresources;

            public EmailObject()
            {
                bodyencoding = "UTF8";
                bodyformat = "Html";
                priority = "Normal";
                hasattached = "N";
                throughoutsidegateway = 0;
            }     
}
