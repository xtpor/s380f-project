
package webproject.model;

public class Poll {
    private String question;
    private int id;

    public Poll() {
    }

    public Poll(String question, int id) {
        this.question = question;
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
