<%--
  Created by IntelliJ IDEA.
  User: sx
  Date: 2018/10/12
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Page.Page" %>
<%@ page import="Page.Commodity" %>
<%@ page import="java.util.List" %>
<%@ page import="count.SessionCounter" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>客户信息列表</title>
    <style>

        body {
            width: 1000px;
            margin: 40px auto;
            font-family: 'trebuchet MS', 'Lucida sans', Arial;
            font-size: 14px;
            color: #444;
        }
        
        table {
            *border-collapse: collapse; /* IE7 and lower */
            border-spacing: 0;
            width: 100%;    
        }
        
        .bordered {
            border: solid #ccc 1px;
            -moz-border-radius: 6px;
            -webkit-border-radius: 6px;
            border-radius: 6px;
            -webkit-box-shadow: 0 1px 1px #ccc; 
            -moz-box-shadow: 0 1px 1px #ccc; 
            box-shadow: 0 1px 1px #ccc;         
        }
        
        .bordered tr:hover {
            background: #fbf8e9;
            -o-transition: all 0.1s ease-in-out;
            -webkit-transition: all 0.1s ease-in-out;
            -moz-transition: all 0.1s ease-in-out;
            -ms-transition: all 0.1s ease-in-out;
            transition: all 0.1s ease-in-out;     
        }    
            
        .bordered td, .bordered th {
            border-left: 1px solid #ccc;
            border-top: 1px solid #ccc;
            padding: 10px;
            text-align: left;    
        }
        
        .bordered th {
            background-color: #dce9f9;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));
            background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);
            background-image:         linear-gradient(top, #ebf3fc, #dce9f9);
            -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
            -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
            box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
            border-top: none;
            text-shadow: 0 1px 0 rgba(255,255,255,.5); 
        }
        
        .bordered td:first-child, .bordered th:first-child {
            border-left: none;
        }
        
        .bordered th:first-child {
            -moz-border-radius: 6px 0 0 0;
            -webkit-border-radius: 6px 0 0 0;
            border-radius: 6px 0 0 0;
        }
        
        .bordered th:last-child {
            -moz-border-radius: 0 6px 0 0;
            -webkit-border-radius: 0 6px 0 0;
            border-radius: 0 6px 0 0;
        }
        
        .bordered th:only-child{
            -moz-border-radius: 6px 6px 0 0;
            -webkit-border-radius: 6px 6px 0 0;
            border-radius: 6px 6px 0 0;
        }
        
        .bordered tr:last-child td:first-child {
            -moz-border-radius: 0 0 0 6px;
            -webkit-border-radius: 0 0 0 6px;
            border-radius: 0 0 0 6px;
        }
        
        .bordered tr:last-child td:last-child {
            -moz-border-radius: 0 0 6px 0;
            -webkit-border-radius: 0 0 6px 0;
            border-radius: 0 0 6px 0;
        }
        
        
        
        /*----------------------*/
        
        .zebra td, .zebra th {
            padding: 10px;
            border-bottom: 1px solid #f2f2f2;    
        }
        
        .zebra tbody tr:nth-child(even) {
            background: #f5f5f5;
            -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset; 
            -moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;  
            box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;        
        }
        
        .zebra th {
            text-align: left;
            text-shadow: 0 1px 0 rgba(255,255,255,.5); 
            border-bottom: 1px solid #ccc;
            background-color: #eee;
            background-image: -webkit-gradient(linear, left top, left bottom, from(#f5f5f5), to(#eee));
            background-image: -webkit-linear-gradient(top, #f5f5f5, #eee);
            background-image:    -moz-linear-gradient(top, #f5f5f5, #eee);
            background-image:     -ms-linear-gradient(top, #f5f5f5, #eee);
            background-image:      -o-linear-gradient(top, #f5f5f5, #eee); 
            background-image:         linear-gradient(top, #f5f5f5, #eee);
        }
        
        .zebra th:first-child {
            -moz-border-radius: 6px 0 0 0;
            -webkit-border-radius: 6px 0 0 0;
            border-radius: 6px 0 0 0;  
        }
        
        .zebra th:last-child {
            -moz-border-radius: 0 6px 0 0;
            -webkit-border-radius: 0 6px 0 0;
            border-radius: 0 6px 0 0;
        }
        
        .zebra th:only-child{
            -moz-border-radius: 6px 6px 0 0;
            -webkit-border-radius: 6px 6px 0 0;
            border-radius: 6px 6px 0 0;
        }
        
        .zebra tfoot td {
            border-bottom: 0;
            border-top: 1px solid #fff;
            background-color: #f1f1f1;  
        }
        
        .zebra tfoot td:first-child {
            -moz-border-radius: 0 0 0 6px;
            -webkit-border-radius: 0 0 0 6px;
            border-radius: 0 0 0 6px;
        }
        
        .zebra tfoot td:last-child {
            -moz-border-radius: 0 0 6px 0;
            -webkit-border-radius: 0 0 6px 0;
            border-radius: 0 0 6px 0;
        }
        
        .zebra tfoot td:only-child{
            -moz-border-radius: 0 0 6px 6px;
            -webkit-border-radius: 0 0 6px 6px
            border-radius: 0 0 6px 6px
        }
          
        </style>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>
<body background="/img/bg1.jpg">

<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    response.flushBuffer();
%>
<br>
<h1>商品信息</h1> <br> <br>


<table class="bordered"  width="1000" border="300" cellspacing="1" style="border: 1pt dashed; font-size: 15pt;" height="31">
    <tr>
        <td>图片</td>
        <td>商品ID</td>
        <td>商品名称</td>
        <td>生产厂商</td>
        <td>类型</td>
        <td>型号</td>
        <td>产地</td>
        <td>商品描述</td>
        <td>上传</td>
    </tr>
    <%

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        Page pager = (Page)request.getAttribute("pages");

        List<Commodity> subResult = (List<Commodity>) request.getAttribute("subResult");

        //System.out.println(request.getAttribute("subResult"));
        //System.out.println("aaaaa");

        if(!subResult.isEmpty()){
            for (int i = 0;i < subResult.size();i++){
                Commodity cu = subResult.get(i);
                out.print("<tr>");
                out.print("<td><img src=\""+cu.getImgPath()+"\" alt=\"\" width=\"50px\" height=\"50px\">"+"</td>");
                out.print("<td>"+cu.getID()+"</td>");
                out.print("<td>"+cu.getName()+"</td>");
                out.print("<td>"+cu.getFactory()+"</td>");
                out.print("<td>"+cu.getKind()+"</td>");
                out.print("<td>"+cu.getModelNumber()+"</td>");
                out.print("<td>"+cu.getField()+"</td>");
                out.print("<td>"+cu.getDescription()+"</td>");
                //out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&methodName="+2+"&onclick=\"return confirmdialog()\">删除</a></td>");
                //out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&customerName=\"\""+"&methodName="+4+">更改</a></td>");

    %>

    <td>
        <form method="post" action="UploadServlet?ID=<%=cu.getID()%>" enctype="multipart/form-data">
            选择一个文件:
            <input type="file" name="uploadFile" >

            </input>
            <input type="submit" value="上传">

        </input>
            <!--<input type="submit" value="上传" > </input>-->
        </form>
    </td>




    <%
                out.print("</tr>");
            }
        }
    %>
</table>
<span><font size="2">总<%=pager.getTotalRecord() %>条记录|总<%=pager.getTotalPage() %>页
     |当前<%=pager.getCurrentPage()+1 %>页|每页<%=pager.getPageSize() %>条

     <%
         response.setCharacterEncoding("UTF-8");
         request.setCharacterEncoding("UTF-8");
         //out.print("<span><font size=\"2\"总"+pager.getTotalRecord()+"条记录|总"+pager.getTotalPage()+"页|当前"+pager.getCurrentRecord()+1+"页|每页"+pager.getPageSize()+"条");
         int last=pager.getCurrentRecord()-pager.getPageSize();
         int next=pager.getCurrentRecord()+pager.getPageSize();
         int currentRecord;
         if(last<0){
             out.println("首页|");
         }
         else{
             out.print("<a href='AllServlet?currentRecord="+last+"&methodName=1'>上一页</a>|");
         }
         if(next>=pager.getTotalRecord()){
             out.println("尾页|");
         }
         else{
             out.print("<a href='AllServlet?currentRecord="+next+"&methodName=1'>下一页</a>|");
         }
     %>
      </font>
      </span>
<br>
<br>
<br>
在线人数为：<%=SessionCounter.getActiveSessions() %>
</body>
</html>
