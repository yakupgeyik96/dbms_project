package dbmsproj;

import dbmsproj.service.PostgreSqlConnection;
import org.postgresql.jdbc.PgArray;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class PostgreTest {
    public static void main(String[] args) {
        PostgreSqlConnection postgreSqlConnection = PostgreSqlConnection.getInstance();
        try (Connection conn = postgreSqlConnection.getConnection();
            Statement stmt = conn.createStatement())
        {
            /*ResultSet result = stmt.executeQuery("SELECT * FROM deneme2 WHERE id=1");

            ArrayList<Date> localDates = new ArrayList<>();



            while (result.next()) {
                PgArray arr = (PgArray) result.getArray("date_value");
                ResultSet rs = arr.getResultSet();
                while (rs.next()) {
                    localDates.add(rs.getDate(2));
                    System.out.println(rs.getDate(2));
                }
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(localDates.get(1));
            LocalDate localDate = LocalDate.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH)
            );*/

            //System.out.println(localDate);

            /*for (int i = 0; i < dates.size(); i++) {
                localDates[i] = dates.get(i);
            }

            String sql = "INSERT INTO deneme (date_value) "
                    + "VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setArray(1, conn.createArrayOf("date", localDates));
            statement.executeUpdate();*/

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Connection is success");
    }
}
