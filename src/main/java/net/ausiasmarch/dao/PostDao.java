package net.ausiasmarch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.bean.BeanInterface;
import net.ausiasmarch.bean.PostBean;

public class PostDao implements DaoInterface {

    Connection oConnection = null;

    public PostDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    @Override
    public PostBean get(int id) throws SQLException {
        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        String strSQL = "SELECT * FROM post WHERE id=?";
        oPreparedStatement = oConnection.prepareStatement(strSQL);
        oPreparedStatement.setInt(1, id);
        oResultSet = oPreparedStatement.executeQuery();
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

    @Override
    public int getCount() throws SQLException {
        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        String strSQL = "SELECT count(*) FROM post";
        oPreparedStatement = oConnection.prepareStatement(strSQL);
        oResultSet = oPreparedStatement.executeQuery();
        if (oResultSet.next()) {
            return oResultSet.getInt(1);
        } else {
            return -1;
        }
    }

    @Override
    public Integer update(BeanInterface oPostBeanParam) throws SQLException {
        PreparedStatement oPreparedStatement = null;
        String strSQL = "UPDATE post SET titulo = ?, cuerpo = ?, etiquetas = ?, fecha=? WHERE id = ?";
        int iResult;
        oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
        PostBean oPostBean = (PostBean) oPostBeanParam;
        oPreparedStatement.setString(1, oPostBean.getTitulo());
        oPreparedStatement.setString(2, oPostBean.getCuerpo());
        oPreparedStatement.setString(3, oPostBean.getEtiquetas());
        oPreparedStatement.setDate(4,  new java.sql.Date(oPostBean.getFecha().getTime()));                        
        oPreparedStatement.setInt(5, oPostBean.getId());
        iResult = oPreparedStatement.executeUpdate();
        return iResult;
    }

    @Override
    public List<BeanInterface> getAll() throws SQLException {
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM post LIMIT 100");
        List<BeanInterface> listaPostBean = new ArrayList();
        while (rs.next()) {
            PostBean oPostBean = new PostBean();
            oPostBean.setId(rs.getInt("id"));
            oPostBean.setTitulo(rs.getString("titulo"));
            oPostBean.setCuerpo(rs.getString("cuerpo"));
            oPostBean.setEtiquetas(rs.getString("etiquetas"));          
            oPostBean.setFecha(rs.getDate("fecha"));
            listaPostBean.add(oPostBean);        
        }
        return listaPostBean;
    }

    @Override
    public Integer insert(BeanInterface oPostBeanParam) throws SQLException {
        PreparedStatement oPreparedStatement;
        String strsql = "INSERT INTO post (titulo,cuerpo,etiquetas,fecha) VALUES(?,?,?,?)";
        oPreparedStatement = oConnection.prepareStatement(strsql);
        PostBean oPostBean = (PostBean) oPostBeanParam;
        oPreparedStatement.setString(1, oPostBean.getTitulo());
        oPreparedStatement.setString(2, oPostBean.getCuerpo());
        oPreparedStatement.setString(3, oPostBean.getEtiquetas());
        oPreparedStatement.setDate(4, new java.sql.Date(oPostBean.getFecha().getTime()));
        int iResult = oPreparedStatement.executeUpdate();
        return iResult;
    }

    @Override
    public Integer remove(int id) throws SQLException {
        PreparedStatement oPreparedStament = null;
        String strSQL = "";
        int iResult;
        strSQL = "DELETE ";
        strSQL += " FROM post ";
        strSQL += " WHERE id=?";
        oPreparedStament = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
        oPreparedStament.setInt(1, id);
        iResult = oPreparedStament.executeUpdate();
        return iResult;
    }

    @Override
    public ArrayList<PostBean> getPage(int page, int limit, List<String> orden) throws SQLException {

        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        int offset;

        if (page == 1) {
            offset = 0;
        } else {
            offset = (limit * page) - limit;
        }

        if (orden == null) {
        	oPreparedStatement = oConnection.prepareStatement("SELECT * FROM post LIMIT ? OFFSET ?");
        	oPreparedStatement.setInt(1, limit);
            oPreparedStatement.setInt(2, offset);
        } else {
        	String sqlQuery = "SELECT * FROM post ";
        	sqlQuery += "ORDER BY ";
        	for (int i = 1; i <= orden.size(); i++) {
        		if (orden.get((i-1)).equalsIgnoreCase("asc")) {
        			sqlQuery += "ASC ";
        		} else if (orden.get((i-1)).equalsIgnoreCase("desc")) {
        			sqlQuery += "DESC ";
        		} else {
        			sqlQuery += "? ";
        		}
        	}
        	sqlQuery += "LIMIT ? OFFSET ?";
        	oPreparedStatement = oConnection.prepareStatement(sqlQuery);
        	for (int i = 1; i < orden.size(); i++) {
        		if (orden.get((i-1)).equalsIgnoreCase("id")) {
        			oPreparedStatement.setInt(i, 1);
        		} else if (orden.get((i-1)).equalsIgnoreCase("titulo")) {
        			oPreparedStatement.setInt(i, 2);
        		} else if (orden.get((i-1)).equalsIgnoreCase("cuerpo")) {
        			oPreparedStatement.setInt(i, 3);
        		} else if (orden.get((i-1)).equalsIgnoreCase("etiquetas")) {
        			oPreparedStatement.setInt(i, 4);
        		} else if (orden.get((i-1)).equalsIgnoreCase("fecha")) {
        			oPreparedStatement.setInt(i, 5);
        		}
        		
        	}
        	oPreparedStatement.setInt((orden.size()), limit);
            oPreparedStatement.setInt((orden.size()+1), offset);
        }
        
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

}
