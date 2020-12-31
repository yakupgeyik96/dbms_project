package dbmsproj.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TenantDAO {
    private PostgreSqlConnection postgreSqlConnection;

    public List<String> getTenantTCNumbers() {
        List<String> tcNumbers = new ArrayList<>();

        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM tenant";
            ResultSet r = stmt.executeQuery(sql);

            while (r.next()) {
                tcNumbers.add(r.getString("tcno"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return tcNumbers;
    }

    public boolean signupUser(String firstName, String lastName, String phoneNumber, String tcNumber) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "INSERT INTO tenant(first_name, last_name, tcno, phone_number) "
                    + "VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, tcNumber);
            preparedStatement.setString(4, phoneNumber);

            int i = preparedStatement.executeUpdate();
            System.out.println("----> " + i);

            if(i != 0) return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
