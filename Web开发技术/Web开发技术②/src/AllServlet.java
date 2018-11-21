import Page.Commodity;
import Page.Page;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

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
                case 1:
                    difpage(request,response);
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

    //展示页面
    //设置分页相关参数方法
    public Page setpage(HttpServletRequest request,HttpServletResponse response)throws ClassNotFoundException,SQLException{
        String crd = request.getParameter("currentRecord");
        ArrayList<Commodity> result = select();
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
        ArrayList<Commodity> result = select();
        Page pager = new Page();
        pager = setpage(request,response);
        List<Commodity> subResult = null;
        int currentRecord = pager.getCurrentRecord();
        if (currentRecord==0){
            if (pager.getTotalRecord()<8){
                subResult = (List<Commodity>) result.subList(0,pager.getTotalRecord());
            }else {
                subResult = (List<Commodity>) result.subList(0,pager.getPageSize());
            }
        }
        else if(pager.getCurrentRecord()+pager.getPageSize() < result.size()){
            subResult = (List<Commodity>)result.subList(pager.getCurrentRecord(),pager.getCurrentRecord()+pager.getPageSize());
        }
        else {
            subResult = (List<Commodity>)result.subList(pager.getCurrentRecord(),result.size());
        }
        request.setAttribute("pages", pager);
        //System.out.println("diyige"+subResult.size());
        request.setAttribute("subResult", subResult);
        request.getRequestDispatcher("/a/layout.jsp").forward(request, response);
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
            HttpSession session = request.getSession();
            session.setAttribute("userName",userName);
            request.getRequestDispatcher("AllServlet?methodName=1&id=\"\"&name=\"\"").forward(request,response);
        }else {
            request.getRequestDispatcher("/a/adminError.jsp").forward(request,response);
        }
    }
    //数据库连接
    public Connection connect() throws ClassNotFoundException,SQLException {
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

    //select查询

    public ArrayList<Commodity> select()throws ClassNotFoundException,SQLException{
        Connection conn = null;
        Statement stat = null;
        ResultSet rs;
        rs = null;

        conn = connect();
        stat = conn.createStatement();
        ArrayList<Commodity>  result = new ArrayList<>();

        rs = stat.executeQuery("SELECT * from commodity;");

        while (rs.next()){
            Commodity cu = new Commodity();

            cu.setID(rs.getInt("ID"));
            cu.setName(rs.getString("Name"));
            cu.setKind(rs.getString("Kind"));
            cu.setDescription(rs.getString("Description"));
            cu.setFactory(rs.getString("Factory"));
            cu.setField(rs.getString("Field"));
            cu.setModelNumber(rs.getString("modelNumber"));
            cu.setImgPath(rs.getString("imgPath"));

            result.add(cu);

        }
        if (rs!=null){
            rs.close();
        }
        close(stat,conn);
        return result;
    }

}
