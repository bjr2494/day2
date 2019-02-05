package edu.acc.j2ee.hubbub;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
    private UserDao userDao;
    private PostDao postDao;

    @Override
    public void init() {
        userDao = this.getUserDao();
        postDao = this.getPostDao();
    }
    // <editor-fold defaultstate="collapsed" desc="processRequest() method. Click on the + sign on the left to edit the code.">
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"), destination;
        if (action == null || action.length() == 0)
            action = this.getServletConfig().getInitParameter("default.action");
        switch (action) {
            default:
            case "login": destination = login(request); break;
            case "logout": destination = logout(request); break;
            case "timeline": destination = timeline(request); break;
            case "join": destination = join(request); break;
            case "post": destination = post(request); break;
        }
        String redirect = this.getServletConfig().getInitParameter("redirect.tag");
        if (destination.startsWith(redirect)) {
            response.sendRedirect("main?action=" + destination.substring(
                destination.indexOf(redirect) + redirect.length()));
            return;
        }
        String viewDir = this.getServletConfig().getInitParameter("view.dir");
        String viewType = this.getServletConfig().getInitParameter("view.type");
        request.getRequestDispatcher(viewDir + destination + viewType)
                .forward(request, response);
    }
    //</editor-fold>
    private String login(HttpServletRequest request) {
        
        if (request.getSession().getAttribute("user") != null)
            return "redirect:timeline";
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "login";
        
        String userText = request.getParameter("username");
        String passText = request.getParameter("password");
        
        String destination = "login";
        if (!userDao.validate(userText, passText))
            request.setAttribute("flash", "Invalid Credentials");
        else {
            User user = userDao.authenticate(userText, passText);
            if (user == null)
                request.setAttribute("flash", "Access Denied");
            else {
                request.getSession().setAttribute("user", user);
                destination = "redirect:timeline";
            }
        }
        return destination;
    }
    
    private String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:timeline";
    }
    
    private String timeline(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Pager pager = (Pager)request.getServletContext().getAttribute("pager");
        
        String ps = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(ps); 
        int adjPageSize = pageSize - 1;
        List<Post> thePosts = this.getPostDao().retrieveSizeOfPosts();
        int size = thePosts.size();
        request.getServletContext().setAttribute("size", size);
        
        List<Integer> options = this.getPagerDao().determineDropDownOptions(pageSize, size);
        request.setAttribute("options", options);
        
        String pagePrompt = request.getParameter("page");
        int page = Integer.parseInt(pagePrompt);
        
        int currentPage = this.getPagerDao().fetchCurrentPage(page, thePosts, size, adjPageSize);
        request.setAttribute("currentPage", currentPage);
         
        List<Post> posts = this.getPostDao().findByRange(currentPage, size);
        request.setAttribute("posts", posts);
        
        if (pagePrompt == null || pagePrompt.length() == 0) ;
        else if (pagePrompt.equalsIgnoreCase("start")) {
            this.getPagerDao().start(currentPage);
        }
        else if (pagePrompt.equalsIgnoreCase("next")) {
            this.getPagerDao().next(currentPage, size, adjPageSize);
        }
        else if (pagePrompt.equalsIgnoreCase("prev")) {
            this.getPagerDao().prev(currentPage);
        }
        else if (pagePrompt.equalsIgnoreCase(("end"))) {
            this.getPagerDao().end(currentPage, size, adjPageSize);
        }
        
        try {
            int newSize = Integer.parseInt(request.getParameter("size"));
            pager.setPageSize(newSize);
        }
        catch (Exception e) {}

        boolean back = this.getPagerDao().isBack(currentPage);
        request.setAttribute("back", back);
        
        boolean more = this.getPagerDao().isMore(currentPage, size, pageSize);
        request.setAttribute("more", more);

        return "timeline";
    }
    
    private String join(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null)
            return "redirect:timeline";
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "join";

        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        if (!password1.equals(password2)) {
            request.setAttribute("flash", "Passwords don't match");
            return "join";
        }
 
        if (!userDao.validate(username, password2)) {
            request.setAttribute("flash", "Username or password invalid");
            return "join";
        }

        if (userDao.findByUsername(username) != null) {
            request.setAttribute("flash", "That username is taken");
            return "join";
        }
        
        User user = new User(username, password1);
        userDao.addUser(user);
        request.getSession().setAttribute("user", user);
        return "redirect:timeline";
    }
    
    private String post(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        User author = (User)request.getSession().getAttribute("user");
        if (author == null)
            return "redirect:timeline";
        if (request.getMethod().equalsIgnoreCase("GET"))
            return "post";
        String content = request.getParameter("content");
        if (content == null || content.length() < 1) {
            request.setAttribute("flash", "Your post was empty");
            return "post";
        }
        if (content.length() > 255) {
            request.setAttribute("flash", "Too much post in yer post");
            request.setAttribute("content", content);
            return "post";
        }
        Post post = new Post(content, author);
        postDao.addPost(post);
        return "redirect:timeline";
    }
    
    private UserDao getUserDao() {
        @SuppressWarnings("unchecked")
        UserDao dao = (UserDao)this.getServletContext().getAttribute("userDao");
        return dao;
    }
    
    private PostDao getPostDao() {
        @SuppressWarnings("unchecked")
        PostDao dao = (PostDao)this.getServletContext().getAttribute("postDao");
        return dao;
    }
    
    private PagerDao getPagerDao() {
        @SuppressWarnings("unchecked")
        PagerDao dao = (PagerDao)this.getServletContext().getAttribute("pagerDao");
        return dao;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
