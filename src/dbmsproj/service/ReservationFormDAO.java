package dbmsproj.service;

import dbmsproj.entity.ReservationForm;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    public List<ReservationForm> getReservationFormsByTenantNumber(int tenantNumber) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();
        List<ReservationForm> reservations = new ArrayList<>();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM reservation_form WHERE tenantno=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, tenantNumber);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                ReservationForm reservation = new ReservationForm();
                reservation.setReservationNumber(rs.getInt("reservationno"));
                reservation.setDateOfMade(convertDateToLocalDate(rs.getDate("date_of_made")));
                reservation.setTenantNumber(rs.getInt("tenantno"));
                reservation.setStandNumber(rs.getInt("standno"));
                reservation.setTotalPrice(rs.getDouble("total_price"));
                reservations.add(reservation);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return reservations;
    }

    public boolean deleteByReservationNumber(int reservationNumber) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "DELETE FROM reservation_form WHERE reservationno=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, reservationNumber);
            int i = preparedStatement.executeUpdate();
            if (i != 1) return false;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public void updateDateOfMadeByReservationNumber(int reservationNumber, LocalDate newDateOfMade) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "UPDATE reservation_form SET date_of_made=? WHERE reservationno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(newDateOfMade));
            ps.setInt(2, reservationNumber);
            ps.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public LocalDate convertDateToLocalDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        LocalDate localDate = LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        return localDate;
    }
}
