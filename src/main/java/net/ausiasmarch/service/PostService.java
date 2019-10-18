package net.ausiasmarch.service;

import com.google.gson.Gson;
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

    public PostService(HttpServletRequest oRequest) {
        this.oRequest = oRequest;
    }

    public String get() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        int id = Integer.parseInt(oRequest.getParameter("id"));
        PostDao oPostDao = new PostDao(oConection);
        PostBean oPostBean = oPostDao.get(id);
        Gson oGson = new Gson();
        String strJson = oGson.toJson(oPostBean);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
    }

    public String update() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostBean oPostBean = new PostBean();
        Gson oGson = new Gson();
        oPostBean = oGson.fromJson(oRequest.getParameter("data"), PostBean.class);
        PostDao oPostDao = new PostDao(oConection);
        ResponseBean oResponseBean;
        if (oPostDao.update(oPostBean) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        };
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }

    public String getAll() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostDao oPostDao = new PostDao(oConection);
        ArrayList<PostBean> oPostBeanList = oPostDao.getAll();
        Gson oGson = new Gson();
        String strJson = oGson.toJson(oPostBeanList);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
    }

      public String getPage() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        int pagina = Integer.parseInt(oRequest.getParameter("page"));
        int numeroRegistros = Integer.parseInt(oRequest.getParameter("limit"));
          PostDao oPostDao = new PostDao(oConection);
        ArrayList<PostBean> oPostBeanList = oPostDao.getPage(pagina,numeroRegistros);
        Gson oGson = new Gson();
        String strJson = oGson.toJson(oPostBeanList);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
      }

    public String newOne() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostBean oPostBean = new PostBean();
        Gson oGson = new Gson();
        oPostBean = oGson.fromJson(oRequest.getParameter("data"), PostBean.class);
        PostDao oPostDao = new PostDao(oConection);
        ResponseBean oResponseBean;
        if (oPostDao.newOne(oPostBean) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        };
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }

    public String remove() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostBean oPostBean = new PostBean();
        Gson oGson = new Gson();
        oPostBean = oGson.fromJson(oRequest.getParameter("data"), PostBean.class);
        PostDao oPostDao = new PostDao(oConection);
        ResponseBean oResponseBean;
        if (oPostDao.remove(oPostBean) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        };
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }
    
            public String getCount() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
         PostDao oPostDao = new PostDao(oConection);
        int total = oPostDao.getCount();
        Gson oGson = new Gson();
        String strJson = oGson.toJson(total);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
      }
      

}
