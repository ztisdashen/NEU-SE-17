<%--
  Created by IntelliJ IDEA.
  User: sx
  Date: 2018/10/12
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.uestc.www.servlets.Page" %>
<%@ page import="com.uestc.www.servlets.Customer" %>
<%@ page import="java.util.List" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
    <head>
        <base href="<%=basePath%>">
            <title>客户信息列表</title>
                <meta http-equiv="pragma" content="no-cache">
                <meta http-equiv="cache-control" content="no-cache">
                <meta http-equiv="expires" content="0">
                <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
                <meta http-equiv="description" content="This is my page">

        <script type="text/javascript">
            function confirmdialog(){
                if(window.confirm("您确定要删除此条信息？")){
                    return true;
                }
                else{
                    //  alert("取消删除！");
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <br>
        <h1>客户信息</h1> <br> <br>

        <h3>全部客户信息如下</h3>
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

                List<Customer> subResult = (List<Customer>) request.getAttribute("subResult");

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
                        //out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&methodName="+2+"&onclick=\"return confirmdialog()\">删除</a></td>");
                        //out.print("<td><a href=\"AllServlet?customerID="+cu.getCustomerID()+"&customerName=\"\""+"&methodName="+4+">更改</a></td>");

            %>
            <td><a href="AllServlet?customerID=<%=cu.getCustomerID() %>&methodName=<%=2 %>" onclick="return confirmdialog()">删除</a></td>

            <td><a href="AllServlet?customerID=<%=cu.getCustomerID() %>&customerName=<%="" %>&methodName=<%=4 %>">修改</a></td>


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
        <form action="AllServlet" method="post">
            <input type="hidden" name="methodName" value="5"/>
            <h3>按学号姓名查询:</h3>
            ID：<input type="text" name="customerID"  value="" title="学号必须为数字" ></input>
            姓名：<input type="text" name="customerName" value="" title=""></input>
            <input type="submit" value="查询" />
        </form>
        <br>
        <a href="putin.jsp" ><button value="客户列表" style="height: 25px;width: 80px" >添加信息</button></a>
        &nbsp;&nbsp;
        <input type=button name=”Submit” onclick=javascript:history.back(-1); value='返回上一页'>
    <br>
    </body>
</html>
