
package webproject.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import webproject.model.*;


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



    // Poll

    public int createPoll(String question) {
        try {
                Statement s = conn.createStatement();
                s.execute("BEGIN");

                PreparedStatement st1 = conn.prepareStatement("INSERT INTO polls VALUES (null, ?)");
                st1.setString(1, question);
                st1.executeUpdate();

                PreparedStatement st2 = conn.prepareStatement("SELECT MAX(id) FROM polls");
                ResultSet rs = st2.executeQuery();
                int pollId = rs.getInt(1);

                s.execute("COMMIT");

                return pollId;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Poll> getPollList() {
        try {
            PreparedStatement st1 = conn.prepareStatement("SELECT * FROM polls");
            ResultSet rs1 = st1.executeQuery();

            ArrayList<Poll> pollList = new ArrayList<>();
            while (rs1.next()) {
                Poll poll = getPollFromQuery(rs1);
                pollList.add(poll);
            }

            return pollList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Poll findPollByPollId(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM polls WHERE id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return getPollFromQuery(rs);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deletePoll(int id) { // TODO delete related poll options, responses, comments
        try {
            Statement s = conn.createStatement();
            s.execute("BEGIN");

            // delete related poll options
            PreparedStatement st1 = conn.prepareStatement("DELETE FROM poll_options WHERE pollId = ?");
            st1.setInt(1, id);
            st1.executeUpdate();

            // delete related poll responses
            PreparedStatement st2 = conn.prepareStatement("DELETE FROM poll_responses WHERE pollId = ?");
            st2.setInt(1, id);
            st2.executeUpdate();

            // delete related poll comments
            PreparedStatement st3 = conn.prepareStatement("DELETE FROM poll_comments WHERE pollId = ?");
            st3.setInt(1, id);
            st3.executeUpdate();

            PreparedStatement st4 = conn.prepareStatement("DELETE FROM polls WHERE id = ?");
            st4.setInt(1, id);
            st4.executeUpdate();

            s.execute("COMMIT");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Poll getPollFromQuery(ResultSet rs) {
        try {
            Poll poll = new Poll();
            poll.setId(rs.getInt(1));
            poll.setQuestion(rs.getString(2));
            return poll;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



    // Poll Option

    public void createPollOption(PollOption pollOption) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO poll_options VALUES (?, ?, ?)");
            System.out.println("[Poll Option] Creating option: " + pollOption.getPollId() + ", " + pollOption.getNo() + ", " + pollOption.getContent());
            st.setInt(1, pollOption.getPollId());
            st.setInt(2, pollOption.getNo());
            st.setString(3, pollOption.getContent());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PollOption> findPollOptionListByPollId(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM poll_options WHERE poll_options.pollId == ?");
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            List<PollOption> optionList = new ArrayList<PollOption>();

            while (rs.next()) {
                try {
                    PollOption option = new PollOption();
                    option.setPollId(rs.getInt(1));
                    option.setNo(rs.getInt(2));
                    option.setContent(rs.getString(3));
                    optionList.add(option);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return optionList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePollOption(PollOption pollOption) {
        try {
            // TODO Should I clear counter for modified option
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE poll_options SET content = ? WHERE pollId = ? AND no = ?"
            );
            st.setString(1, pollOption.getContent());
            st.setInt(2, pollOption.getPollId());
            st.setInt(3, pollOption.getNo());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



    // Poll Response

    public void createPollResponse(PollResponse pollResponse) {
        try {
            // username, poll_id, no, post_time,
            PreparedStatement st = conn.prepareStatement("INSERT INTO poll_responses VALUES (?, ?, ?, ?)");
            st.setString(1, pollResponse.getUsername());
            st.setInt(2, pollResponse.getPollId());
            st.setInt(3, pollResponse.getNo());
            st.setLong(4, pollResponse.getPostTime());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Map<Integer, Integer> findCountsByPollId(int id) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT no, count(username) FROM poll_responses WHERE pollId = ? GROUP BY no;"
            );
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            Map<Integer, Integer> counts = new Hashtable<Integer, Integer>();

            while (rs.next()) {
                try {
                    int no = rs.getInt(1);
                    int count = rs.getInt(2);
                    counts.put(no, count);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return counts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int findPollResponseByUserInPoll(String username, int pollId) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "SELECT no FROM poll_responses WHERE username = ? AND pollId = ?"
            );
            st.setString(1, username);
            st.setInt(2, pollId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updatePollResponse(PollResponse pollResponse) {
        try {
            PreparedStatement st = conn.prepareStatement(
                    "UPDATE poll_responses SET no = ?, post_time = ? WHERE username = ? AND pollId = ?"
            );
            st.setInt(1, pollResponse.getNo());
            st.setLong(2, pollResponse.getPostTime());
            st.setString(3, pollResponse.getUsername());
            st.setInt(4, pollResponse.getPollId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



    // Poll Comments

    public void createPollComment(PollComment pollComment) {
        try {
            // username, poll_id, no, post_time,
            PreparedStatement st = conn.prepareStatement("INSERT INTO poll_comments VALUES (null, ?, ?, ?, ?)");
            st.setString(1, pollComment.getUsername());
            st.setInt(2, pollComment.getPollId());
            st.setLong(3, pollComment.getPostTime());
            st.setString(4, pollComment.getContent());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<PollComment> findPollCommentListByPollId(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM poll_comments WHERE pollId == ?");
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            List<PollComment> commentList = new ArrayList<PollComment>();

            while (rs.next()) {
                try {
                    PollComment comment = new PollComment();
                    comment.setId(rs.getInt(1));
                    comment.setUsername(rs.getString(2));
                    comment.setPollId(rs.getInt(3));
                    comment.setPostTime(rs.getLong(4));
                    comment.setContent(rs.getString(5));
                    commentList.add(comment);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return commentList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deletePollComment(int id) { // TODO delete related poll options, responses, comments
        try {
            // delete related poll comments
            PreparedStatement st = conn.prepareStatement("DELETE FROM poll_comments WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
