package net.ausiasmarch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
    
    public List<PostBean> getall() throws SQLException{
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM post LIMIT 100");     
        List<PostBean> listaPostBean = new ArrayList();
        while(rs.next()){
            PostBean oPostBean = new PostBean();
            
            oPostBean.setId(rs.getInt("id"));
            oPostBean.setTitulo(rs.getString("titulo"));
            oPostBean.setCuerpo(rs.getString("cuerpo"));
            oPostBean.setEtiquetas(rs.getString("etiquetas"));
            oPostBean.setFecha(new Timestamp(rs.getTimestamp("fecha").getTime()));
            
            listaPostBean.add(oPostBean);        
        }
        
    return listaPostBean;
    }

}
