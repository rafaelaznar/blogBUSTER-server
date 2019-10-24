package net.ausiasmarch.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.bean.BeanInterface;

public interface DaoInterface {

    BeanInterface get(int id) throws SQLException;

    List<BeanInterface> getAll() throws SQLException;

    int getCount() throws SQLException;

    ArrayList getPage(int page, int rpp) throws SQLException;

    Integer insert(BeanInterface oBean) throws SQLException;

    Integer remove(int id) throws SQLException;

    Integer update(BeanInterface oBean) throws SQLException;

}
