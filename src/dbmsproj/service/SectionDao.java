package dbmsproj.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
            String sql = "SELECT * FROM section";
            ResultSet r = stmt.executeQuery(sql);

            while (r.next()) {
                sectionNames.add(r.getString("section_name"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return sectionNames;
    }
}
