package net.ausiasmarch.service;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.ausiasmarch.bean.ResponseBean;
import net.ausiasmarch.factory.GsonFactory;

public class UsuarioService {

    HttpServletRequest oRequest = null;

    public UsuarioService(HttpServletRequest oRequest) {
        this.oRequest = oRequest;
    }

    public String login() {
        HttpSession oSession = oRequest.getSession();
        ResponseBean oResponseBean = null;
        if (oRequest.getParameter("username").equals("rafa") && oRequest.getParameter("password").equals("bitnami")) {
            oSession.setAttribute("usuario", oRequest.getParameter("username"));
            oResponseBean = new ResponseBean(200, "Welcome");
        } else {
            oResponseBean = new ResponseBean(500, "Wrong password");
        }
        Gson oGson = GsonFactory.getGson();
        return oGson.toJson(oResponseBean);
    }

    public String check() {
        HttpSession oSession = oRequest.getSession();
        ResponseBean oResponseBean = null;
        if (oSession.getAttribute("usuario") != null) {
            oResponseBean = new ResponseBean(200, (String) oSession.getAttribute("usuario"));
        } else {
            oResponseBean = new ResponseBean(500, "No active session");
        }
        Gson oGson = GsonFactory.getGson();
        return oGson.toJson(oResponseBean);
    }

    public String logout() {
        HttpSession oSession = oRequest.getSession();
        oSession.invalidate();
        ResponseBean oResponseBean = null;
        oResponseBean = new ResponseBean(200, "No active session");
        Gson oGson = GsonFactory.getGson();
        return oGson.toJson(oResponseBean);
    }

}
