package library.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class testConnection {
    public static void main(String[] args) {
        try {
            // Load JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // SQL Authentication - Không cần DLL
            String url = "jdbc:sqlserver://DESKTOP-ESL4RAA\\SQLEXPRESS;databaseName=library_management;encrypt=false;trustServerCertificate=true;";
            String user = "Test123";
            String password = "Test@123";

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully with Java 21!\n");
            
            // Test SELECT * từ bảng books
            System.out.println("=== DANH SACH SACH ===");
            String sql = "SELECT * FROM books";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // In ra kết quả
            int count = 0;
            while (rs.next()) {
                count++;
                int id = rs.getInt("book_id");
                String name = rs.getString("book_name");
                String genre = rs.getString("genre");
                String author = rs.getString("author");
                int length = rs.getInt("book_length");
                int year = rs.getInt("publish_year");
                int quantity = rs.getInt("book_quantity");
                boolean available = rs.getBoolean("available");
                
                System.out.println("\n Sach #" + count);
                System.out.println("   ID: " + id);
                System.out.println("   Ten: " + name);
                System.out.println("   The loai: " + genre);
                System.out.println("   Tac gia: " + author);
                System.out.println("   So trang: " + length);
                System.out.println("   Nam XB: " + year);
                System.out.println("   So luong: " + quantity);
                System.out.println("   Trang thai: " + (available ? "San sang" : "Het hang"));
            }
            
            System.out.println("\nTong cong: " + count + " sach");
            
            // Đóng kết nối
            rs.close();
            stmt.close();
            connection.close();
            System.out.println("\nConnection closed!");

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found! Check classpath.");
            e.printStackTrace();
        }
    }
}