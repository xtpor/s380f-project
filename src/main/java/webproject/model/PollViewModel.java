package webproject.model;

import java.util.List;
import java.util.Map;

public class PollViewModel {
    private int id;
    private String question;
    private List<PollOption> optionList;
//    private List<PollResponse> pollResponseList;
    private Map<Integer, Integer> count;
    private List<PollComment> pollCommentsList;

    public PollViewModel () {
    }

    public PollViewModel(int id, String question, List<PollOption> optionList, Map<Integer, Integer> count, List<PollComment> pollCommentsList) {
        this.id = id;
        this.question = question;
        this.optionList = optionList;
        this.count = count;
        this.pollCommentsList = pollCommentsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<PollOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<PollOption> optionList) {
        this.optionList = optionList;
    }

    public Map<Integer, Integer> getCount() {
        return count;
    }

    public void setCount(Map<Integer, Integer> count) {
        this.count = count;
    }

    public List<PollComment> getPollCommentsList() {
        return pollCommentsList;
    }

    public void setPollCommentsList(List<PollComment> pollCommentsList) {
        this.pollCommentsList = pollCommentsList;
    }
}
