package edu.acc.j2ee.hubbub;

import java.util.Date;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AppListener implements ServletContextListener, HttpSessionListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String u1user = sce.getServletContext().getInitParameter("user.1.name");
        String u1pass = sce.getServletContext().getInitParameter("user.1.pass");
        String u2user = sce.getServletContext().getInitParameter("user.2.name");
        String u2pass = sce.getServletContext().getInitParameter("user.2.pass");
        
        UserDao userDao = new UserDaoImpl();        
        User u1 = new User(u1user, u1pass, new Date(118, 5, 6));
        User u2 = new User(u2user, u2pass, new Date(119, 0, 28));
        userDao.addUser(u1);
        userDao.addUser(u2);
        sce.getServletContext().setAttribute("userDao", userDao);
        
        PostDao postDao = new PostDaoImpl();
        Post p1 = new Post("My first Hubbub Post!", u1);
        Post p2 = new Post("Joined 'cause johndoe told me to!", u2);
        postDao.addPost(p1);
        postDao.addPost(p2);
        sce.getServletContext().setAttribute("postDao", postDao);
        
        PagerDao pagerDao = new PagerDaoImpl();
        sce.getServletContext().setAttribute("pagerDao", pagerDao);
        
        String pageSizeTxt = sce.getServletContext().getInitParameter("page.size");
        int pageSize = Integer.parseInt(pageSizeTxt);
        Pager pager = new Pager(pageSize);
        sce.getServletContext().setAttribute("pager", pager);  
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {   
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
     }
}
