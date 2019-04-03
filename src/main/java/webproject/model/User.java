
package webproject.model;


public class User {
    private String username;
    private String password;
    private boolean lecturer;

    public User() {
    }

    public User(String username, String password, boolean lecturer) {
        this.username = username;
        this.password = password;
        this.lecturer = lecturer;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLecturer() {
        return lecturer;
    }

    public void setLecturer(boolean lecturer) {
        this.lecturer = lecturer;
    }
}
