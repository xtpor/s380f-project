
package webproject.model;

public class PollComment {
    private int id;
    private String username;
    private int pollId;
    private long postTime;
    private String content;

    public PollComment() {
    }

    public PollComment(int id, String username, int pollId, long postTime, String content) {
        this.id = id;
        this.username = username;
        this.pollId = pollId;
        this.postTime = postTime;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
