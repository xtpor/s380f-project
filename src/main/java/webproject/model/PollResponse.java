
package webproject.model;

public class PollResponse {
    private String username;
    private int pollId;
    private int no;
    private long postTime;

    public PollResponse() {
    }

    public PollResponse(String username, int pollId, int no, long postTime) {
        this.username = username;
        this.pollId = pollId;
        this.no = no;
        this.postTime = postTime;
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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}
