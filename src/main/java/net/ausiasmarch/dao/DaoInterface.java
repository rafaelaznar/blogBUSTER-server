package net.ausiasmarch.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.bean.BeanInterface;

public interface DaoInterface {

    BeanInterface get(int id) throws SQLException;

    List<BeanInterface> getall() throws SQLException;

    int getcount() throws SQLException;

    ArrayList getpage(int page, int rpp) throws SQLException;

    Integer insert(BeanInterface oBean) throws SQLException;

    Integer remove(int id) throws SQLException;

    Integer update(BeanInterface oBean) throws SQLException;
    
}
