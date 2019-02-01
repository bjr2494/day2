package edu.acc.j2ee.hash;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.codec.digest.Sha2Crypt;

public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"), destination;
        if (action == null || action.length() == 0) {
            action = this.getServletConfig().getInitParameter("default.action");
        }
        switch (action) {
            default:
            case "hash":
                destination = hash(request);
                break;
            case "view":
                destination = view(request);
                break;
            case "clear":
                destination = clear(request);
                break;
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

    private String clear(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Set<String> hashes = (Set<String>) this.getServletContext().getAttribute("hashes");
        hashes.clear();
        return "redirect:hash";
    }

    private String view(HttpServletRequest request) {
        return "view";
    }

    private String hash(HttpServletRequest request) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            return "hash";
        }
        String text = request.getParameter("text");
        if (text == null || text.length() < 1) {
            request.setAttribute("flash", "Text submission cannot be empty");
            return "hash";
        }
        String md5Salt = this.getServletConfig().getInitParameter("md5.salt");
        String shaSalt = this.getServletConfig().getInitParameter("sha.salt");
        
        String md5 = Md5Crypt.md5Crypt(text.getBytes(), md5Salt);
        String sha256 = Sha2Crypt.sha256Crypt(text.getBytes(), shaSalt);
        
        @SuppressWarnings("unchecked")
        Set<String> hashes = (Set<String>)this.getServletContext().getAttribute("hashes");
        boolean hashedBefore = !hashes.add(sha256);

        request.setAttribute("md5", md5);
        request.setAttribute("sha256", sha256);
        request.setAttribute("hashedBefore", hashedBefore);
        return "hash";
    }

}
