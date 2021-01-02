package dbmsproj.service;

import dbmsproj.entity.ReservedDays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservedDaysDAO {

    private PostgreSqlConnection postgreSqlConnection;

    public List<ReservedDays> getReservedDaysByReservationNumber(int reservationNumber) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();
        List<ReservedDays> reservedDays = new ArrayList<>();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM reserved_days WHERE reservationno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReservedDays reservedDay = new ReservedDays();
                reservedDay.setReservationNumber(rs.getInt(1));
                reservedDay.setReservedDays(convertDateToLocalDate(rs.getDate(2)));
                reservedDays.add(reservedDay);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return reservedDays;
    }

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

    public void updateReservedDays(int reservationNumber, LocalDate oldDate, LocalDate newDate) {
        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "update reserved_days set reserved_days=? where reservationno=? and reserved_days=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(newDate));
            ps.setInt(2, reservationNumber);
            ps.setDate(3, Date.valueOf(oldDate));
            ps.executeUpdate();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
