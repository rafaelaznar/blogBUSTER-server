package net.ausiasmarch.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import net.ausiasmarch.bean.PostBean;
import net.ausiasmarch.bean.ResponseBean;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.dao.PostDao;
import net.ausiasmarch.factory.ConnectionFactory;
import net.ausiasmarch.setting.ConnectionSettings;

public class PostService {

    HttpServletRequest oRequest = null;
    Gson gson = new GsonBuilder().setDateFormat("dd/mm/yyyy HH:mm").create();

    public PostService(HttpServletRequest oRequest) {
        this.oRequest = oRequest;
    }


      public String getPage() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        int pagina = Integer.parseInt(oRequest.getParameter("page"));
        int numeroRegistros = Integer.parseInt(oRequest.getParameter("limit"));
          PostDao oPostDao = new PostDao(oConection);
        ArrayList<PostBean> oPostBeanList = oPostDao.getPage(pagina,numeroRegistros);
        Gson oGson = gson;
        String strJson = oGson.toJson(oPostBeanList);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
      }


}
