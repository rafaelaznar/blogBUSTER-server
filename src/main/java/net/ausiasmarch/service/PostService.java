package net.ausiasmarch.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import net.ausiasmarch.bean.BeanInterface;

import net.ausiasmarch.bean.PostBean;
import net.ausiasmarch.bean.ResponseBean;
import net.ausiasmarch.connection.ConnectionInterface;
import net.ausiasmarch.dao.PostDao;
import net.ausiasmarch.factory.ConnectionFactory;
import net.ausiasmarch.factory.GsonFactory;
import net.ausiasmarch.setting.ConnectionSettings;

public class PostService implements ServiceInterface {

    HttpServletRequest oRequest = null;
    String[] frasesInicio = {"El fin de la estructura ", "La agrupacion logica ", "Una visión de una idea que "};
    String[] frasesMitad = {"dirige los objetivos ", "garantiza el deseo ", " sin la capacidad de ejecución"};
    String[] frasesFinal = {"de la reestructuracion agraria.", " en el uso de la misma.", " es únicamente una alucinación."};

    public PostService(HttpServletRequest oRequest) {
        this.oRequest = oRequest;
    }

    @Override
    public String get() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        int id = Integer.parseInt(oRequest.getParameter("id"));
        PostDao oPostDao = new PostDao(oConection);
        PostBean oPostBean = oPostDao.get(id);
        Gson oGson = GsonFactory.getGson();
        String strJson = oGson.toJson(oPostBean);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
    }

    @Override
    public String getpage() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        int iRpp = Integer.parseInt(oRequest.getParameter("rpp"));
        int iPage = Integer.parseInt(oRequest.getParameter("page"));
        PostDao oPostDao = new PostDao(oConection);
        ArrayList alPostBean = oPostDao.getpage(iRpp, iPage);
        Gson oGson = GsonFactory.getGson();
        String strJson = oGson.toJson(alPostBean);
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + strJson + "}";
    }

    @Override
    public String getcount() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostDao oPostDao = new PostDao(oConection);
        int iCount = oPostDao.getcount();
        oConnectionImplementation.disposeConnection();
        if (iCount < 0) {
            return "{\"status\":500,\"response\":" + iCount + "}";
        } else {
            return "{\"status\":200,\"response\":" + iCount + "}";
        }
    }

    @Override
    public String update() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConnection = oConnectionImplementation.newConnection();
        Gson oGson = GsonFactory.getGson();
        ResponseBean oResponseBean;
        PostBean oPostBean = new PostBean();
        String data = oRequest.getParameter("data");
        oPostBean = oGson.fromJson(data, PostBean.class);
        PostDao oPostDao = new PostDao(oConnection);
        if (oPostDao.update(oPostBean) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        }
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }

    @Override
    public String getAll() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostDao oPostDao = new PostDao(oConection);
        Gson oGson = GsonFactory.getGson();
        String message = "";
        List<BeanInterface> listaPostBean = oPostDao.getall();
        if (listaPostBean == null) {
            message = "\"La lista está vacia\"";
        } else {
            //oGson = gh.getGson();
            message = oGson.toJson(listaPostBean);
        }
        oConnectionImplementation.disposeConnection();
        return "{\"status\":200,\"response\":" + message + "}";
    }

    @Override
    public String insert() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        final GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson oGson = GsonFactory.getGson();
        PostBean oPostBean = oGson.fromJson(oRequest.getParameter("data"), PostBean.class);
        ResponseBean oResponseBean;
        PostDao oPostDao = new PostDao(oConection);
        if (oPostDao.insert(oPostBean) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        };
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);

    }

    @Override
    public String remove() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostDao oPostDao = new PostDao(oConection);
        Gson oGson = GsonFactory.getGson();
        int id = Integer.parseInt(oRequest.getParameter("id"));
        ResponseBean oResponseBean;
        if (oPostDao.remove(id) == 0) {
            oResponseBean = new ResponseBean(500, "KO");
        } else {
            oResponseBean = new ResponseBean(200, "OK");
        }
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }

    public String fill() throws SQLException {
        ConnectionInterface oConnectionImplementation = ConnectionFactory.getConnection(ConnectionSettings.connectionPool);
        Connection oConection = oConnectionImplementation.newConnection();
        PostDao oPostDao = new PostDao(oConection);
        Gson oGson = GsonFactory.getGson();
        Date date1 = new GregorianCalendar(2014, Calendar.JANUARY, 1).getTime();
        Date date2 = new GregorianCalendar(2019, Calendar.DECEMBER, 31).getTime();
        int numPost = Integer.parseInt(oRequest.getParameter("number"));
        for (int i = 0; i < numPost; i++) {
            PostBean oPostBean = new PostBean();
            Date randomDate = new Date(ThreadLocalRandom.current()
                    .nextLong(date1.getTime(), date2.getTime()));
            oPostBean.setTitulo(generaTexto(1));
            oPostBean.setCuerpo(generaTexto(5));
            oPostBean.setEtiquetas(generaTexto(1));
            oPostBean.setFecha(randomDate);
            oPostDao.insert(oPostBean);
        }
        ResponseBean oResponseBean = new ResponseBean(200, "Insertados los registros con exito");
        oConnectionImplementation.disposeConnection();
        return oGson.toJson(oResponseBean);
    }

    private String generaTexto(int longitud) {
        String fraseRandom = "";
        for (int i = 0; i < longitud; i++) {
            fraseRandom += frasesInicio[(int) (Math.random() * frasesInicio.length) + 0];
            fraseRandom += frasesMitad[(int) (Math.random() * frasesMitad.length) + 0];
            fraseRandom += frasesFinal[(int) (Math.random() * frasesFinal.length) + 0];
        }
        return fraseRandom;
    }
}
