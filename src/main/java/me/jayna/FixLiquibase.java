package me.jayna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FixLiquibase {
    public static void main(String[] args) {
        try {
            // Datenbankverbindung herstellen
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jayna",
                "jayna",
                "geheim"
            );
            
            // Problematic Liquibase entry entfernen
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM DATABASECHANGELOG " +
                        "WHERE ID = '20240617170000-1' " +
                        "AND AUTHOR = 'jayna' " +
                        "AND FILENAME = 'config/liquibase/changelog/20240617170000_update_electricity_formname.xml'";
            
            int rowsAffected = stmt.executeUpdate(sql);
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
