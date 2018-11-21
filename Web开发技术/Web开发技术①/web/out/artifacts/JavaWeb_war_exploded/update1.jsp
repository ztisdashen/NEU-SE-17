<%--
  Created by IntelliJ IDEA.
  User: sx
  Date: 2018/10/13
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.uestc.www.servlets.Page" %>
<%@ page import="com.uestc.www.servlets.Customer" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>学生信息修改</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
</head>
<script type="text/javascript">
    function validate()
    {
        var id=document.forms[0].customerID.value;
        var name=document.forms[0].customerName.value;
        var age=document.forms[0].professional.value;
        var major=document.forms[0].address.value;
        if(id<=0){
            alert("ID不能为空，请输入ID！");
            return false;
        }
        else if(name.length<=0){
            alert("姓名不能为空，请输入姓名！");
            return false;
        }
        else if(age.length<=0){
            alert("职业不能为空，请输入职业！");
            return false;
        }

        else if(major.length<=0){
            alert("地址不能为空，请输入所在地址！");
            return false;
        }

        else{
            return true;
        }
        //document.getElementById("form").submit();
    }
</script>
<body>
    <br>
    <h2>客户信息</h2>
    <br>
    <h3>要修改的学生信息如下</h3>
        <table width="800" border="100" cellspacing="1" style="border: 1pt dashed; font-size: 15pt;" height="31">
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
        <h3>将学生信息更改为：</h3>
        <form action="AllServlet" method="post" onSubmit="return validate()">
            <input type="hidden" name="methodName" value="3"/>
            <h4>  客户ID：<input type="text" name="customerID" class="{required:true}" title="ID必须为数字"></input><br></h4>
            <h4>  客户姓名：<input type="text" name="customerName"title="姓名不能为空"></input><br></h4>
            <h4>  性别：<input type="radio" name="gender" value="男" checked = "checked">男
                <input type="radio" name="gender" value="女">女<br></h4>
            <h4>  职业：<input type="text" name="professional"title="职业不能为空"></input><br></h4>
            <h4>  文化程度：<select name="levelOfEducation">
                <option value ="小学" selected = "selected">小学</option>
                <option value ="初中">初中</option>
                <option value="高中">高中</option>
                <option value="技工学校">技工学校</option>
                <option value="中等专业学校">中等专业学校</option>
                <option value="大学专科">大学专科</option>
                <option value="大学本科">大学本科</option>
                <option value="研究生">研究生</option>
                <option value="博士">博士</option>
            </select></h4>
            <h4>  住址：<input type="text" name="address"title="地址不能为空"></input><br></h4>
            <input type="submit" value="提交"/>&nbsp;<input type=button name=”Submit” onclick=javascript:history.back(-1); value='返回上一页'>
        </form>
</body>
</html>
