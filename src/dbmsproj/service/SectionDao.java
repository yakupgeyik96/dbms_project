package dbmsproj.service;

import dbmsproj.entity.Stand;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SectionDao {

    private PostgreSqlConnection postgreSqlConnection;

    public List<String> getSectionNames() {
        List<String> sectionNames = new ArrayList<>();

        postgreSqlConnection = PostgreSqlConnection.getInstance();

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM all_sections";
            ResultSet r = stmt.executeQuery(sql);

            while (r.next()) {
                sectionNames.add(r.getString(1));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return sectionNames;
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

    public String getSectionNameByStandNumber(int standNumber) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        String sectionName = "";

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "select secname from section where sectionno=(select sectionno from stand where standno=?)";
            PreparedStatement prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, standNumber);
            ResultSet result = prepStatement.executeQuery();
            while (result.next()) {
                sectionName = result.getString("secname");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return sectionName;
    }

    public double calculateStandPrice(float area, int exposedSides, int meterPrice, int sidePrice) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        double standPrice = 0;

        try (Connection conn = postgreSqlConnection.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM standPrice(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1, area);
            ps.setInt(2, exposedSides);
            ps.setInt(3, meterPrice);
            ps.setInt(4, sidePrice);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                standPrice = rs.getDouble(1);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return standPrice;
    }
}
