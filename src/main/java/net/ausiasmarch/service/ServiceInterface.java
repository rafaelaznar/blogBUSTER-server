
package net.ausiasmarch.service;

import java.sql.SQLException;

public interface ServiceInterface {

    String get() throws SQLException;

    String getAll() throws SQLException;

    String getCount() throws SQLException;

    String getPage() throws SQLException;

    String insert() throws SQLException;

    String remove() throws SQLException;

    String update() throws SQLException;
    
}
