package Upload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/testdb?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "Delete1350";
        public UploadServlet() {
        super();
        }

public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
        }

public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String ID = request.getParameter("ID");

        String contentType=request.getContentType();
        String servername=request.getServerName();
        String realpath=request.getRealPath(servername);



        InputStream in=null;
        OutputStream out=null;
        if(contentType.indexOf("multipart/form-data")>=0){
            in=request.getInputStream();
            int formlength=request.getContentLength();
            byte[] formcontent=new byte[formlength];
            int totalread=0;
            int nowread=0;
            while(totalread<formlength){
                nowread=in.read(formcontent,totalread, formlength);
                totalread+=nowread;
            }
            String strcontent=new String(formcontent);

            int typestart=strcontent.indexOf("Content-Type:")+14;
            int typeend=strcontent.indexOf("\n", typestart)-1;
            String formType=strcontent.substring(typestart, typeend);
            if(formType.equals("image/jpeg")||formType.equals("image/png")||formType.equals("image/gif")||formType.equals("image/pjepg")){

                int filenamestart=strcontent.indexOf("filename=\"")+10;
                int filenameend=strcontent.indexOf("\n",filenamestart)-2;
                String filename=strcontent.substring(filenamestart,filenameend);
                filename=filename.substring(filename.lastIndexOf("."));
                String newfilename=""+(new Date()).getDate()+(new Date()).getHours()+(new Date()).getMinutes()+(new Date()).getSeconds();
                newfilename=newfilename+filename;

                String tmpName = newfilename;

                realpath=realpath+"";

                newfilename=realpath+newfilename;

                System.out.println("###\n"+realpath);

                int filestart=strcontent.indexOf("\n",typestart)+1;
                filestart=strcontent.indexOf("\n",filestart)+1;
                int intboundary=contentType.indexOf("boundary=")+10;
                String strboundary=contentType.substring(intboundary);
                int fileend=strcontent.indexOf(strboundary,filestart)-4;
                String saveFile=strcontent.substring(filestart,fileend);
                int contentstart=strcontent.substring(0,filestart).getBytes().length;
                int contentend=strcontent.substring(0,fileend).getBytes().length;


                File myfile=new File(realpath);
                if(!myfile.exists()){
                    myfile.mkdirs();
                }
                out=new FileOutputStream(newfilename);
                out.write(formcontent, contentstart,contentend-contentstart);

                try {
                    UpdatePath(Integer.parseInt(ID),tmpName);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("AllServlet?methodName=6&Name=\"admin\"&Pwd=\"123456\"");
            }else{
                response.sendRedirect("/a/error.jsp");
            }
        }
        }

    private void UpdatePath(int ID, String path) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement stat = null;
        conn = connect();
        stat = conn.createStatement();
        //System.out.println("update commodity set imgPath =\"localhost"+path+"\"   where ID = "+ID+";");

        stat.execute("update commodity set imgPath = \"localhost"+path+"\"   where ID = "+ID+";");

    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request,response);
        }

public void init() throws ServletException {
        // Put your code here
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

        }