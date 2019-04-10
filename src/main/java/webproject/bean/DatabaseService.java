package webproject.bean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webproject.model.Attachment;
import webproject.model.Comment;
import webproject.model.Lecture;
import webproject.model.User;

@Service
public class DatabaseService implements InitializingBean {

    @Autowired
    private DriverManagerDataSource dataSource;
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

    public void createLecture(String title, List<MultipartFile> files) throws IOException {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");

            PreparedStatement st1 = conn.prepareStatement("INSERT INTO lecture VALUES (?, ?)");
            st1.setString(1, null);
            st1.setString(2, title);
            st1.executeUpdate();

            PreparedStatement st2 = conn.prepareStatement("SELECT * FROM lecture ORDER BY lid DESC");
            ResultSet rs2 = st2.executeQuery();

            PreparedStatement st3 = conn.prepareStatement("INSERT INTO attachment VALUES (?, ?, ?, ?, ?)");
            for (MultipartFile file : files) {
                System.out.println(file.getOriginalFilename());
                System.out.println(file.getContentType());
                System.out.println(Arrays.toString(file.getBytes()));

                st3.setObject(1, null);
                st3.setObject(2, rs2.getString(1));
                st3.setObject(3, file.getOriginalFilename());
                st3.setObject(4, file.getContentType());
                st3.setObject(5, file.getBytes());
                st3.addBatch();
            }
            st3.executeBatch();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteLecture(int id) throws IOException {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("DELETE FROM lecture WHERE lid = ?");
            st1.setInt(1, id);
            st1.executeUpdate();
            PreparedStatement st2 = conn.prepareStatement("DELETE FROM attachment WHERE lid = ?");
            st2.setInt(1, id);
            st2.executeUpdate();
            PreparedStatement st3 = conn.prepareStatement("DELETE FROM comment WHERE lid = ?");
            st3.setInt(1, id);
            st3.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteAttachment(int aid) throws IOException {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("DELETE FROM attachment WHERE aid = ?");
            st1.setInt(1, aid);
            st1.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void addAttachment(int lid, List<MultipartFile> attachments) throws IOException {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st = conn.prepareStatement("INSERT INTO attachment VALUES (?, ?, ?, ?, ?)");
            for (MultipartFile file : attachments) {
                System.out.println(file.getOriginalFilename());
                System.out.println(file.getContentType());
                System.out.println(Arrays.toString(file.getBytes()));

                st.setObject(1, null);
                st.setObject(2, lid);
                st.setObject(3, file.getOriginalFilename());
                st.setObject(4, file.getContentType());
                st.setObject(5, file.getBytes());
                st.addBatch();
            }
            st.executeBatch();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Lecture> listLectures() {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM lecture");
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
            ArrayList<Lecture> result = new ArrayList<>();
            while (rs1.next()) {
                Lecture l;
                l = new Lecture(rs1.getInt(1), rs1.getString(2));
                result.add(l);
            }

            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Lecture getLecture(int lectureId) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM lecture WHERE lid = ?");
            st1.setInt(1, lectureId);
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
            return new Lecture(rs1.getInt(1), rs1.getString(2));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void makeComment(int lid, String name, String comment) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("insert into comment values (?, ?, ?, ?)");
            st1.setObject(1, null);
            st1.setInt(2, lid);
            st1.setString(3, name);
            st1.setString(4, comment);
            st1.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteComment(int cid) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("DELETE FROM comment WHERE cid = ?");
            st1.setInt(1, cid);
            st1.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Object getCommentsFromLect(int lid) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM comment WHERE lid = ?");
            st1.setInt(1, lid);
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
            ArrayList<Comment> result = new ArrayList<>();
            while (rs1.next()) {
                Comment c = new Comment(rs1.getInt(1), rs1.getInt(2), rs1.getString(3), rs1.getString(4));
                result.add(c);
            }
            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<User> listUsersByType(boolean isLecturer) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM users WHERE is_admin = ?");
            st1.setBoolean(1, isLecturer);
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
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
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            s.execute("COMMIT");
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
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st = conn.prepareStatement("UPDATE users "
                    + "SET username = ?, password = ?, is_admin = ? "
                    + "WHERE username = ?");
            st.setString(1, u.getUsername());
            st.setString(2, u.getPassword());
            st.setBoolean(3, u.isLecturer());
            st.setString(4, u.getUsername());
            st.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteUser(String username) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st = conn.prepareStatement("DELETE FROM users WHERE username = ?");
            st.setString(1, username);
            st.executeUpdate();
            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Attachment getAttach(int lid, int aid) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM attachment WHERE lid = ? AND aid = ?");
            st1.setInt(1, lid);
            st1.setInt(2, aid);
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
            return new Attachment(rs1.getInt(1), rs1.getInt(2), rs1.getString(3), rs1.getString(4), (byte[]) rs1.getObject(5));
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<Attachment> listLectAttach(int lid) {
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM attachment WHERE lid = ?");
            st1.setInt(1, lid);
            ResultSet rs1 = st1.executeQuery();
            s.execute("COMMIT");
            ArrayList<Attachment> result = new ArrayList<>();
            while (rs1.next()) {
                Attachment a = new Attachment(rs1.getInt(1), rs1.getInt(2), rs1.getString(3), rs1.getString(4), (byte[]) rs1.getObject(5));
                result.add(a);
            }

            return result;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
