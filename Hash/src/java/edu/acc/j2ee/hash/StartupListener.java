package edu.acc.j2ee.hash;

import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Set<String> hashes = new TreeSet<>();
        sce.getServletContext().setAttribute("hashes", hashes);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        @SuppressWarnings("unchecked")
        Set<String> hashes = (Set<String>)sce.getServletContext().getAttribute("hashes");
        hashes.clear();
    }
}
