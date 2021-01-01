package dbmsproj.service;

import java.sql.*;
import java.time.LocalDate;

public class ReservedDaysDAO {

    private PostgreSqlConnection postgreSqlConnection;

    public void saveReservedDays(int reservationNumber, LocalDate date) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "INSERT INTO reserved_days (reservationno, reserved_days) "
                    + "VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationNumber);
            ps.setDate(2, Date.valueOf(date));
            ps.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
