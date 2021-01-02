package dbmsproj;

import dbmsproj.service.PostgreSqlConnection;
import java.sql.*;

public class PostgreTest {
    public static void main(String[] args) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        try (Connection conn = postgreSqlConnection.getConnection();
            Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM standPrice(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, 4.0F);
            ps.setInt(2, 2);
            ps.setInt(3, 10);
            ps.setInt(4, 10);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getDouble(1));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Connection is success");
    }
}
