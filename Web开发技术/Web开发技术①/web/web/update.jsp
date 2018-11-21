<%--
  Created by IntelliJ IDEA.
  User: sx
  Date: 2018/10/13
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.uestc.www.servlets.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>修改成功</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
<br>

<h3>修改后的信息为：</h3>

<hr>
<br>
<br>
<table width="800" border="100" cellSpacing=1 style="font-size:15pt;border:dashed 1pt">
    <tr>
    <tr>
        <td>客户ID</td>
        <td>客户姓名</td>
        <td>性别</td>
        <td>职业</td>
        <td>受教育程度</td>
        <td>地址</td>
    </tr>
    <%
        String id = "";
        ArrayList<Customer> result = new ArrayList<>();
        result = (ArrayList<Customer>) request.getAttribute("result");
        if(!result.isEmpty()){
            for(int i = 0;i < result.size();i++){
                Customer cu = result.get(i);
                id = cu.getCustomerID();
                out.print("<tr>");
                out.print("<td>"+cu.getCustomerID()+"</td>");
                out.print("<td>"+cu.getCustomerName()+"</td>");
                out.print("<td>"+cu.getGender()+"</td>");
                out.print("<td>"+cu.getProfessional()+"</td>");
                out.print("<td>"+cu.getLevelOfEducation()+"</td>");
                out.print("<td>"+cu.getAddress()+"</td>");
            }
        }
    %>
</table>
<br>
<br>
<h3><a href=putin.jsp>返回信息输入页面</a></h3>
<h3><a href=AllServlet?methodName=<%=1 %>&id=<%="" %>&name=<%="" %>>返回信息查询页面</a></h3>
</body>
</html>
