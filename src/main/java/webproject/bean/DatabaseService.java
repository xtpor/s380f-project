
package webproject.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import webproject.model.User;


@Service
public class DatabaseService implements InitializingBean {

    @Autowired private DriverManagerDataSource dataSource;
    private Connection conn;


    @Override
    public void afterPropertiesSet() throws Exception {
        conn = dataSource.getConnection();
    }

    public boolean createUser(User user) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");

            PreparedStatement st1 = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
            st1.setString(1, user.getUsername());
            ResultSet rs1 = st1.executeQuery();
            rs1.next();
            boolean userExisted = rs1.getInt(1) > 0;

            if (userExisted) {
                s.execute("ROLLBACK");
                return false;
            } else {
                PreparedStatement st2 = conn.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
                st2.setString(1, user.getUsername());
                st2.setString(2, user.getPassword());
                st2.setBoolean(3, user.isLecturer());
                st2.executeUpdate();

                s.execute("COMMIT");
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<User> listUsersByType(boolean isLecturer) {
        try {
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM users WHERE is_admin = ?");
            st1.setBoolean(1, isLecturer);
            ResultSet rs1 = st1.executeQuery();

            ArrayList<User> result = new ArrayList<>();
            while (rs1.next()) {
                User u = new User();
                u.setUsername(rs1.getString(1));
                u.setPassword(rs1.getString(2));
                u.setLecturer(rs1.getBoolean(3));
                result.add(u);
            }

            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public User findUserByUsername(String username) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString(1));
                u.setPassword(rs.getString(2));
                u.setLecturer(rs.getBoolean(3));
                return u;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void updateUser(User u) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE users " + 
                    "SET username = ?, password = ?, is_admin = ? " +
                    "WHERE username = ?");
            st.setString(1, u.getUsername());
            st.setString(2, u.getPassword());
            st.setBoolean(3, u.isLecturer());
            st.setString(4, u.getUsername());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void deleteUser(String username) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            st.setString(1, username);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
