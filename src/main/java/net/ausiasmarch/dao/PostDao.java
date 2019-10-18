package net.ausiasmarch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static jdk.nashorn.internal.runtime.Debug.id;
import net.ausiasmarch.bean.PostBean;

public class PostDao {

    Connection oConnection = null;

    public PostDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public PostBean get(int id) throws SQLException {
        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        String strSQL = "SELECT * FROM post WHERE id=?";
        oPreparedStatement = oConnection.prepareStatement(strSQL);
        oPreparedStatement.setInt(1, id);
        oResultSet = oPreparedStatement.executeQuery(strSQL);
        PostBean oPostBean;
        if (oResultSet.next()) {
            oPostBean = new PostBean();
            oPostBean.setId(oResultSet.getInt("id"));
            oPostBean.setTitulo(oResultSet.getString("titulo"));
            oPostBean.setCuerpo(oResultSet.getString("cuerpo"));
            oPostBean.setEtiquetas(oResultSet.getString("etiquetas"));
            oPostBean.setFecha(oResultSet.getDate("fecha"));
        } else {
            oPostBean = null;
        }
        return oPostBean;
    }

    public Integer update(PostBean oPostBean) throws SQLException {

        PreparedStatement oPreparedStament = null;
        String strSQL = "";
        int iResult;

        strSQL = "UPDATE post ";
        strSQL += " SET ";
        strSQL += " titulo = " + oPostBean.getTitulo();
        strSQL += " cuerpo = " + oPostBean.getCuerpo();
        strSQL += " etiquetas = " + oPostBean.getEtiquetas();
        strSQL += " fecha = " + oPostBean.getFecha();
        strSQL += " WHERE id=?";

        PreparedStatement oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);

        oPreparedStament.setInt(1, oPostBean.getId());

        iResult = oPreparedStament.executeUpdate();

        return iResult;

    }

    public ArrayList<PostBean> getAll() throws SQLException {

        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        String strSQL = "SELECT * FROM post";
        oPreparedStatement = oConnection.prepareStatement(strSQL);
        oResultSet = oPreparedStatement.executeQuery(strSQL);

        ArrayList<PostBean> oPostBeanList = new ArrayList<>();
        while (oResultSet.next()) {
            PostBean oPostBean = new PostBean();
            oPostBean.setId(oResultSet.getInt("id"));
            oPostBean.setTitulo(oResultSet.getString("titulo"));
            oPostBean.setCuerpo(oResultSet.getString("cuerpo"));
            oPostBean.setEtiquetas(oResultSet.getString("etiquetas"));
            oPostBean.setFecha(oResultSet.getDate("fecha"));
            oPostBeanList.add(oPostBean);
        } 
        return oPostBeanList;

    }

    public Integer remove(PostBean oPostBean) throws SQLException {

        PreparedStatement oPreparedStament = null;
        String strSQL = "";
        int iResult;

        strSQL = "DELETE post ";
        strSQL += " titulo = " + oPostBean.getTitulo();
        strSQL += " cuerpo = " + oPostBean.getCuerpo();
        strSQL += " etiquetas = " + oPostBean.getEtiquetas();
        strSQL += " fecha = " + oPostBean.getFecha();
        strSQL += " WHERE id=?";

        PreparedStatement oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);

        oPreparedStament.setInt(1, oPostBean.getId());

        iResult = oPreparedStament.executeUpdate();

        return iResult;

    }

    public Integer newOne(PostBean oPostBean) throws SQLException {

        PreparedStatement oPreparedStament = null;
        String strSQL = "";
        int iResult;

        strSQL = "ADD post ";
        strSQL += " titulo = " + oPostBean.getTitulo();
        strSQL += " cuerpo = " + oPostBean.getCuerpo();
        strSQL += " etiquetas = " + oPostBean.getEtiquetas();
        strSQL += " fecha = " + oPostBean.getFecha();
        strSQL += " WHERE id=?";

        PreparedStatement oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);

        oPreparedStament.setInt(1, oPostBean.getId());

        iResult = oPreparedStament.executeUpdate();

        return iResult;

    }

    public ArrayList<PostBean> getPage(int page, int limit) throws SQLException {
        
        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        int offset;
        
        if (page==1){
            offset=0;
        } else {
            offset= (limit*page)-limit;
        }
       
        oPreparedStatement = oConnection.prepareStatement("SELECT * FROM post LIMIT ? OFFSET ?");
        oPreparedStatement.setInt(1, limit);
        oPreparedStatement.setInt(2, offset);
        oResultSet = oPreparedStatement.executeQuery();
        
        ArrayList<PostBean> oPostBeanList = new ArrayList<>();
        while (oResultSet.next()) {        
            PostBean oPostBean = new PostBean();
            oPostBean.setId(oResultSet.getInt("id"));
            oPostBean.setTitulo(oResultSet.getString("titulo"));
            oPostBean.setCuerpo(oResultSet.getString("cuerpo"));
            oPostBean.setEtiquetas(oResultSet.getString("etiquetas"));
            oPostBean.setFecha(oResultSet.getDate("fecha"));
            oPostBeanList.add(oPostBean);
        }
        
        return oPostBeanList;
    }
    
    public Integer getCount() throws SQLException {
             PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        String strSQL = "SELECT COUNT(*)  FROM post";
        oPreparedStatement = oConnection.prepareStatement(strSQL);
        oResultSet = oPreparedStatement.executeQuery(strSQL);
        
        int iResult=0;
        if (oResultSet.next()) {        
           iResult = oResultSet.getInt(0);
        }
        return iResult;
    }
   
   
}
