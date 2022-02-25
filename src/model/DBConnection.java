package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // creare conexiune in mod singleton la BD
    // JDBC name and database url
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=MvcBookStore;integratedSecurity=true;";

    public Connection connection;

    public static DBConnection db;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            // handle errors for Class.forName
            e.printStackTrace();
        }

    }

    public static synchronized DBConnection getConnection() {
        // pentru a ne asigura ca db este instantiat o singura data, indiferent de unde se apeleaza
        if (db == null) {
            db = new DBConnection();
        }
        return db;
    }

}
