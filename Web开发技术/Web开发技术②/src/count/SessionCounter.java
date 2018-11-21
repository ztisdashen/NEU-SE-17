package count;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCounter implements HttpSessionListener {

    private static int activeSessions = -2;
    //session创建时执行
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
    }
    //session销毁时执行
    public void sessionDestroyed(HttpSessionEvent se) {
        if (activeSessions > 0)
            activeSessions--;
    }
    //获取活动的session个数(在线人数)
    public static int getActiveSessions() {
        return activeSessions;
    }

}