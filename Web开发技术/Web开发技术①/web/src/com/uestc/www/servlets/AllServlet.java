package com.uestc.www.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static sun.misc.Version.print;

/**
 * @创建人 贾敬哲
 * @创建时间 2018/10/11
 * @描述 电商平台客户管理模块
 */
@WebServlet("/AllServlet")
public class AllServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "Delete1350";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doPost(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String methonName = request.getParameter("methodName");
        int method = Integer.parseInt(methonName);

        try {
            switch (method){
                case 0:
                    insert(request,response);
                case 1:
                    //System.out.println("aaaa");
                    difpage(request,response);
                    break;
                case 2:
                    delete(request,response);
                    break;
                case 3:
                    update(request,response);
                    break;
                case 4:
                    update1(request,response);
                    break;
                case 5:
                    dispatch(request,response);
                    break;
                case 6:
                    Login(request,response);
                    break;

            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
        //doGet(request, response);
    }

    private void Login(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, ClassNotFoundException, SQLException{
        String userName = request.getParameter("Name");
        String userPwd = request.getParameter("Pwd");
        userName = userName.substring(1,userName.length()-1);
        userPwd = userPwd.substring(1,userPwd.length()-1);

        Cookie c = new Cookie("users", userName + "-" + userPwd);
        c.setMaxAge(6000*24);
        response.addCookie(c);
        if(userName.equals("admin")&& userPwd.equals("123456")){
            request.getRequestDispatcher("AllServlet?methodName=1&id=\"\"&name=\"\"").forward(request,response);
        }else {
            request.getRequestDispatcher("adminError.jsp").forward(request,response);
        }
    }
    //数据库连接
    public Connection connect() throws ClassNotFoundException,SQLException{
        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动器
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 打开一个连接
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);

            return conn;

        }catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return null;
    }

    //关闭数据库资源
    public void close(Statement stat,Connection conn) throws SQLException{
        if(stat!=null){
            stat.close();
        }
        if(conn != null){
            conn.close();
        }
    }

    //插入方法
    public void insert(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
        Connection conn = null;
        Statement stat = null;
        String customerID = request.getParameter("customerID");
        String customerName = request.getParameter("customerName");
        String gender = request.getParameter("gender");
        String professional = request.getParameter("professional");
        String levelOfEducation = request.getParameter("levelOfEducation");
        String address = request.getParameter("address");
        conn = connect();
        stat = conn.createStatement();
        String SqlJu = "INSERT INTO customers(customerID,customerName,gender,professional,levelOfEducation,address) value ("+customerID+
                ",'"+customerName+"','"+gender+"','"+professional+"','"+levelOfEducation+"','"+address+"');";
        stat.execute(SqlJu);
        close(stat,conn);
        request.getRequestDispatcher("AllServlet?methodName=1&id=\"\"&name=\"\"").forward(request,response);
    }


    //select查询
    public ArrayList<Customer> select(String id,String name)throws ClassNotFoundException,SQLException{
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        conn = connect();
        stat = conn.createStatement();
        ArrayList<Customer>  result = new ArrayList<>();
        if(id=="" &&name == ""){
            rs = stat.executeQuery("SELECT * from customers");
        }
        if(id==""&&name!=""){
            rs = stat.executeQuery("SELECT * FROM customers WHERE customerName LIKE '%"+name+"%'");
        }
        if (id!=""&&name==""){
            rs = stat.executeQuery("SELECT  * FROM customers WHERE customerID='"+id+"'");
        }
        if (id!=""&&name!=""){
            rs = stat.executeQuery("SELECT  * FROM customers WHERE customerID='"+id+"' AND customerName LIKE '%"+name+"%'");
        }

        while (rs.next()){
            Customer cu = new Customer();

            cu.setCustomerID(rs.getString("customerID"));
            cu.setCustomerName(rs.getString("customerName"));
            cu.setGender(rs.getString("gender"));
            cu.setLevelOfEducation(rs.getString("levelOfEducation"));
            cu.setAddress(rs.getString("address"));
            cu.setProfessional(rs.getString("professional"));
            result.add(cu);
        }
        if (rs!=null){
            rs.close();
        }
        close(stat,conn);
        return result;
    }

    //条件查询跳转
    public void dispatch(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException, ClassNotFoundException, SQLException{
        String id5 = request.getParameter("customerID");
        String name5 = request.getParameter("customerName");
        if(select(id5,name5).isEmpty()){
            request.getRequestDispatcher("selectnothing.jsp").forward(request,response);
        }else {
            request.setAttribute("SubResult", select(id5,name5));
            request.getRequestDispatcher("idnameselect.jsp").forward(request, response);
        }
    }
    //展示页面
       //设置分页相关参数方法
    public Page setpage(HttpServletRequest request,HttpServletResponse response)throws ClassNotFoundException,SQLException{
        String crd = request.getParameter("currentRecord");
        ArrayList<Customer> result = select("","");
        Page pager = new Page();
        pager.setTotalRecord(result.size());
        pager.setTotalPage(result.size(),pager.getPageSize());
        if(crd!=null){
            int currentRecord = Integer.parseInt(crd);
            pager.setCurrentRecord(currentRecord);
            pager.setCurrentPage(currentRecord,pager.getPageSize());
        }
        return pager;
    }
    //获得分页显示的子集
    public void difpage(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException,ClassNotFoundException,SQLException{
        ArrayList<Customer> result = select("","");
        Page pager = new Page();
        pager = setpage(request,response);
        List<Customer> subResult = null;
        int currentRecord = pager.getCurrentRecord();
        if (currentRecord==0){
            if (pager.getTotalRecord()<8){
                subResult = (List<Customer>) result.subList(0,pager.getTotalRecord());
            }else {
                subResult = (List<Customer>) result.subList(0,pager.getPageSize());
            }
        }
        else if(pager.getCurrentRecord()+pager.getPageSize() < result.size()){
            subResult = (List<Customer>)result.subList(pager.getCurrentRecord(),pager.getCurrentRecord()+pager.getPageSize());
        }
        else {
            subResult = (List<Customer>)result.subList(pager.getCurrentRecord(),result.size());
        }
        request.setAttribute("pages", pager);
        //System.out.println("diyige"+subResult.size());
        request.setAttribute("subResult", subResult);
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    //信息删除方法
    public void delete(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        Connection conn = null;
        Statement stat = null;
        conn = connect();
        stat = conn.createStatement();
        String id2 = request.getParameter("customerID");
        //问老师
        stat.execute("set SQL_Safe_updates = 0;");
        stat.execute("delete from customers where  customerID="+id2+"");
        request.getRequestDispatcher("delete.jsp").forward(request,response);
    }

    //信息修改方法
    public void update1(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String id4 = request.getParameter("customerID");
        request.setAttribute("result",select(id4,""));
        request.getRequestDispatcher("update1.jsp").forward(request,response);
    }

    public void update(HttpServletRequest request,HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        Connection conn = null;
        Statement stat = null;
        String id3 = request.getParameter("customerID");
        String customerName = request.getParameter("customerName");
        String gender = request.getParameter("gender");
        String professional = request.getParameter("professional");
        String levelOfEducation = request.getParameter("levelOfEducation");
        String address = request.getParameter("address");
        conn = connect();
        stat = conn.createStatement();
        stat.execute("set SQL_Safe_updates = 0;");
        stat.execute("UPDATE customers set customerID="+id3+",customerName='"+customerName+"',gender ='"+gender+"',professional='"+professional+"',levelOfEducation='"+levelOfEducation+"',address='"+address+"' Where customerID ="+id3);
        request.setAttribute("result",select(id3,""));
        request.getRequestDispatcher("update.jsp").forward(request,response);
    }
}