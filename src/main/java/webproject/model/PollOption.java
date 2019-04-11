
package webproject.model;

public class PollOption {
    private int pollId;
    private int no;
    private String content;

    public PollOption() {
    }

    public PollOption(int pollId, int no, String content) {
        this.pollId = pollId;
        this.no = no;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
