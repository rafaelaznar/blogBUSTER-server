/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.connection;

/**
 *
 * @author raznara
 */
public class ConnectionFactory {

    public static ConnectionInterface getConnection() {
        return new BoneCPImplementation();
    }
}
