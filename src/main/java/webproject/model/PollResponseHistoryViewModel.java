
package webproject.model;

public class PollResponseHistoryViewModel {
    private String question;
    private int pollId;
    private String content;
    private long postTime;

    public PollResponseHistoryViewModel() {
    }

    public PollResponseHistoryViewModel(String question, long postTime, int pollId, String content) {
        this.question = question;
        this.pollId = pollId;
        this.content = content;
        this.postTime = postTime;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPostTime() {
        return postTime;
    }

    public void setPostTime(long postTime) {
        this.postTime = postTime;
    }
}
