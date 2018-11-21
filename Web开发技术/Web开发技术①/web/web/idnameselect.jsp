<%--
  Created by IntelliJ IDEA.
  User: sx
  Date: 2018/10/13
  Time: 16:19
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

    <title>按客户ID查询</title>

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
<h3>符合条件的客户信息</h3><hr>
<br>
<table width="1000" border="100" cellspacing="1" style="border: 1pt dashed; font-size: 15pt;" height="31">
    <tr>
        <td>客户ID</td>
        <td>客户姓名</td>
        <td>性别</td>
        <td>职业</td>
        <td>受教育程度</td>
        <td>地址</td>
        <td>删除</td>
        <td>修改</td>
    </tr>
    <%
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Page pager = (Page)request.getAttribute("pages");

        List<Customer> subResult = (List<Customer>) request.getAttribute("SubResult");

        //System.out.println(request.getAttribute("subResult"));
        //System.out.println("aaaaa");
        if(!subResult.isEmpty()){
            for (int i = 0;i < subResult.size();i++){
                Customer cu = subResult.get(i);
                out.print("<tr>");
                out.print("<td>"+cu.getCustomerID()+"</td>");
                out.print("<td>"+cu.getCustomerName()+"</td>");
                out.print("<td>"+cu.getGender()+"</td>");
                out.print("<td>"+cu.getProfessional()+"</td>");
                out.print("<td>"+cu.getLevelOfEducation()+"</td>");
                out.print("<td>"+cu.getAddress()+"</td>");
                out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&methodName="+2+"onclick=\"return confirmdialog()\">删除</a></td>");
                out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&name=\"\""+"&methodName="+4+">修改</a></td>");
            }
        }
    %>
    <%
        out.print("</tr>");
    %>
</table>
<br>
<br>
<h4><a href=AllServlet?methodName=<%=1 %>&customerID=<%="" %>&customerName=<%="" %>>返回信息查询页面</a></h4>
</body>
</html>

