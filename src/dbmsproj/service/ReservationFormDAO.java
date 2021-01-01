package dbmsproj.service;

import java.sql.*;
import java.time.LocalDate;

public class ReservationFormDAO {
    private PostgreSqlConnection postgreSqlConnection;

    public void saveCurrentReservation(LocalDate dateOfMade, int tenantNumber, int standNumber, double totalPrice) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "INSERT INTO reservation_form (date_of_made, tenantno, standno, total_price)"
                    + " VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(dateOfMade));
            preparedStatement.setInt(2, tenantNumber);
            preparedStatement.setInt(3, standNumber);
            preparedStatement.setInt(4, (int) totalPrice);
            int r = preparedStatement.executeUpdate();
            System.out.println(r);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getReservationNumberByTenantAndStandNumber(int tenantNumber, int standNumber, LocalDate date) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();
        int reservationNumber = 0;

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM reservation_form WHERE tenantno=? AND standno=? AND date_of_made=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tenantNumber);
            ps.setInt(2, standNumber);
            ps.setDate(3, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                reservationNumber = rs.getInt(1);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return reservationNumber;
    }
}
