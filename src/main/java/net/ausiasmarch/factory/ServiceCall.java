package net.ausiasmarch.factory;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import net.ausiasmarch.service.PostService;
import net.ausiasmarch.service.UsuarioService;
import net.ausiasmarch.setting.ConfigurationSettings;

public class ServiceCall {

    HttpServletRequest oRequest;

    public static String executeService(HttpServletRequest oRequest) throws SQLException {
        String ob = oRequest.getParameter("ob");
        String op = oRequest.getParameter("op");
        String strResult = null;
        if (ob.equalsIgnoreCase("usuario")) {
            UsuarioService oUsuarioService = new UsuarioService(oRequest);
            switch (op) {
                case "login":
                    strResult = oUsuarioService.login();
                    break;
                case "check":
                    strResult = oUsuarioService.check();
                    break;
                case "logout":
                    strResult = oUsuarioService.logout();
                    break;
            }
        }
        if (ob.equalsIgnoreCase("post")) {
            PostService oPostService = new PostService(oRequest);
            switch (op) {
                case "get":
                    strResult = oPostService.get();
                    break;
                case "getcount":
                    strResult = oPostService.getCount();
                    break;
                case "getpage":
                    strResult = oPostService.getPage();
                    break;
                case "update":
                    strResult = oPostService.update();
                    break;
                case "remove":
                    strResult = oPostService.remove();
                    break;
                case "getall":
                    strResult = oPostService.getAll();
                    break;
                case "insert":
                    strResult = oPostService.insert();
                    break;
                case "fill":
                    if (ConfigurationSettings.environment == ConfigurationSettings.EnvironmentConstans.Debug) {
                        strResult = oPostService.fill();
                    }
                    break;
            }
        }
        return strResult;
    }
}
