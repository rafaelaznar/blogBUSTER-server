package net.ausiasmarch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
        String strSQL = "UPDATE post SET titulo = ?, cuerpo = ?, etiquetas = ? WHERE id = ?";
        int iResult;
        oPreparedStatement = oConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
        PostBean oPostBean = (PostBean) oPostBeanParam;
        oPreparedStatement.setString(1, oPostBean.getTitulo());
        oPreparedStatement.setString(2, oPostBean.getCuerpo());
        oPreparedStatement.setString(3, oPostBean.getEtiquetas());
        oPreparedStatement.setInt(4, oPostBean.getId());
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
            oPostBean.setFecha(new Timestamp(rs.getTimestamp("fecha").getTime()));
            listaPostBean.add(oPostBean);
        }
        return listaPostBean;
    }

    @Override
    public Integer insert(BeanInterface oPostBeanParam) throws SQLException {
        PreparedStatement oPreparedStatement;
        String strsql = "INSERT INTO post (titulo,cuerpo,etiquetas) VALUES(?,?,?)";
        oPreparedStatement = oConnection.prepareStatement(strsql);
        PostBean oPostBean = (PostBean) oPostBeanParam;
        oPreparedStatement.setString(1, oPostBean.getTitulo());
        oPreparedStatement.setString(2, oPostBean.getCuerpo());
        oPreparedStatement.setString(3, oPostBean.getEtiquetas());
        //oPreparedStatement.setDate(4, (Date) oPostBean.getFecha());
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

    public ArrayList<PostBean> getPage(int page, int limit) throws SQLException {

        PreparedStatement oPreparedStatement;
        ResultSet oResultSet;
        int offset;

        if (page == 1) {
            offset = 0;
        } else {
            offset = (limit * page) - limit;
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

}
