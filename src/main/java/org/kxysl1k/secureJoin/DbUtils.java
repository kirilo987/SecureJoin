package org.kxysl1k.secureJoin;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static final Dotenv dotenv = Dotenv.load();

    public static Connection connectToDatabase() throws SQLException {
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");
        return DriverManager.getConnection(url, user, pass);
    }
}
