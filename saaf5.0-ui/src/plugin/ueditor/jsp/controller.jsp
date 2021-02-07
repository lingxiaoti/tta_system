<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
    response.setHeader("Content-Type" , "text/html");
    request.setAttribute("serverType", "filmManege");
    request.setAttribute("fdfsClientPath", "/fdfs_client.properties");
    request.setAttribute("ueditorImage", "ueditor");


	String rootPath = application.getRealPath( "/" );

	out.write( new ActionEnter( request, rootPath ).exec() );

	
%>