package dbmsproj.service;

import dbmsproj.entity.Stand;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SectionDao {

    private PostgreSqlConnection postgreSqlConnection;

    public List<String> getSectionNames() {
        List<String> sectionNames = new ArrayList<>();

        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM section";
            ResultSet r = stmt.executeQuery(sql);

            while (r.next()) {
                sectionNames.add(r.getString("secname"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return sectionNames;
    }

    public List<LocalDate> getSectionReservedDays(String sectionName) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        List<Date> dates = new ArrayList<>();
        List<LocalDate> localDates = new ArrayList<>();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "select reserved_days "
                    + "from section se, stand st, reservation_form rf, reserved_days rd "
                    + "where se.sectionno=st.sectionno "
                    + "and st.standno=rf.standno and rf.reservationno=rd.reservationno and se.secname=?;";

            PreparedStatement prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, sectionName);
            ResultSet resultSet = prepStatement.executeQuery();

            while(resultSet.next()) {
                dates.add(resultSet.getDate(1));
            }

            for (Date date : dates) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                localDates.add(LocalDate.of(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH)
                ));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return localDates;
    }

    public List<Stand> getUnreservedStands(String sectionName, LocalDate fromDate, LocalDate toDate) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        List<Stand> unreservedStands = new ArrayList<>();
        int sectionNumber = 0;

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM get_unreserved_stands(?, ?, ?)";
            PreparedStatement prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, sectionName);
            prepStatement.setDate(2, Date.valueOf(fromDate));
            prepStatement.setDate(3, Date.valueOf(toDate));

            ResultSet rs = prepStatement.executeQuery();
            sectionNumber = getSectionNumber(sectionName);

            while (rs.next()) {
                Stand stand = new Stand();
                stand.setStandNumber(rs.getInt(1));
                stand.setArea(rs.getInt(2));
                stand.setExposedSides(rs.getInt(3));
                stand.setSectionNo(sectionNumber);
                unreservedStands.add(stand);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return unreservedStands;
    }

    public int getSectionNumber(String sectionName) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        int sectionNumber = 0;

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            PreparedStatement prepStatement = conn.prepareStatement("SELECT sectionno FROM section WHERE secname=?");
            prepStatement.setString(1, sectionName);

            ResultSet result = prepStatement.executeQuery();

            while (result.next()) {
                sectionNumber = result.getInt(1);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return sectionNumber;
    }
}
