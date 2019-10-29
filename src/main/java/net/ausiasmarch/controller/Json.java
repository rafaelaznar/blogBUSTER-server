
package net.ausiasmarch.controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.ausiasmarch.bean.ResponseBean;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.factory.ConnectionFactory;
import net.ausiasmarch.factory.ServiceCall;
import net.ausiasmarch.setting.ConfigurationSettings;
import net.ausiasmarch.setting.ConfigurationSettings.EnvironmentConstans;
import net.ausiasmarch.setting.ConnectionSettings;

public class Json extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String ob = request.getParameter("ob");
        String op = request.getParameter("op");
        if (("".equalsIgnoreCase(ob) && "".equalsIgnoreCase(op)) || (ob == null && op == null)) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>BlogBuster</title></head><body>");
                out.println("<h1>Bienvenidos al servidor WEB BlogBuster</h1>");
                ConnectionInterface oConnectionImplementation = null;
                Connection oConnection = null;
                try {
                    oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
                    oConnection = oConnectionImplementation.newConnection();
                    out.println("<h3>Database Connection OK</h3>");
                } catch (Exception ex) {
                    out.println("<h3>Database Connection KO</h3>");
                } finally {
                    if (oConnection != null) {
                        try {
                            oConnection.close();
                        } catch (SQLException ex) {
                            out.println("<h3>Error Closing Connection</h3>");
                        }
                    }
                    if (oConnectionImplementation != null) {
                        try {
                            oConnectionImplementation.disposeConnection();
                        } catch (SQLException ex) {
                            out.println("<h3>Error Closing Connection</h3>");
                        }
                    }
                    out.println("</body></html>");
                }
            }
        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
            response.setHeader("Access-Control-Allow-Methods", "GET,POST, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "86400");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-requested-with, Content-Type");
            try (PrintWriter out = response.getWriter()) {
                try {
                    out.print(ServiceCall.executeService(request));
                } catch (Exception ex) {
                    if (ConfigurationSettings.environment == EnvironmentConstans.Debug) {
                        out.print(ex);
                        ex.printStackTrace();
                    } else {
                        ResponseBean oResponseBean = new ResponseBean(500, "BlogBuster ERROR: Please contact your administrator");
                        Gson oGson = new Gson();
                        out.print(oGson.toJson(oResponseBean));
                    }
                }
            }
        }
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
