
package webproject.bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;


@Component
public class DatabaseConfigurator implements InitializingBean {

    @Autowired DriverManagerDataSource dataSource;
    @Autowired ServletContext servletContext;
    Connection conn;


    @Override
    public void afterPropertiesSet() throws Exception {
        String tablesSql;
        String seedSql;
        try {
            String tablesSqlPath = servletContext.getRealPath("/WEB-INF/classes/tables.sql");
            tablesSql = new String(Files.readAllBytes(Paths.get(tablesSqlPath)));

            String seedSqlPath = servletContext.getRealPath("/WEB-INF/classes/seed.sql");
            seedSql = new String(Files.readAllBytes(Paths.get(seedSqlPath)));
        } catch (IOException ex) {
            System.err.println("[DatabaseConfigurator] Fatal error, cannot read the sql script file.");
            ex.printStackTrace();
            return;
        }

        try {
            conn = dataSource.getConnection();

            if (tablesCount() == 0) {
                System.out.println("[DatabaseConfigurator] The database is empty, creating tables");
                runStatements(tablesSql);

                System.out.println("[DatabaseConfigurator] Populating database with seed data");
                runStatements(seedSql);

                System.out.println(String.format("[DatabaseConfigurator] %s of tables created", tablesCount()));
            }
        } catch (SQLException ex) {
            System.err.println("[DatabaseConfigurator] Fatal error, encountered error while running sql script");
            ex.printStackTrace();
            return;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private int tablesCount() throws SQLException {
        PreparedStatement q = conn.prepareStatement("SELECT Count(*) FROM sqlite_master WHERE type = 'table'");
        ResultSet s = q.executeQuery();
        s.next();
        return s.getInt(1);
    }

    private void runStatements(String s) throws SQLException {
        String[] statements = s.split(";", -1);
        for (int i=0; i<statements.length - 1; i++) {
            String stmt = statements[i].replaceAll("\\s+", " ");
            System.out.println("[DatabaseConfigurator] " + stmt);
            conn.prepareStatement(stmt).executeUpdate();
        }
    }
}
