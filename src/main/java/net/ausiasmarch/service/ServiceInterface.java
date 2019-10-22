
package net.ausiasmarch.service;

import java.sql.SQLException;

public interface ServiceInterface {

    String get() throws SQLException;

    String getAll() throws SQLException;

    String getcount() throws SQLException;

    String getpage() throws SQLException;

    String insert() throws SQLException;

    String remove() throws SQLException;

    String update() throws SQLException;
    
}
