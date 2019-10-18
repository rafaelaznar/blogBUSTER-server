package net.ausiasmarch.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    
    
   
}
