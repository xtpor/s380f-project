package webproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import webproject.bean.DatabaseService;
import webproject.model.*;

import java.security.Principal;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("poll")
public class PollController {

    @Autowired
    DatabaseService databaseService;

    @RequestMapping("/")
    public ModelAndView index(@RequestParam("id") int id, Principal principal) {
        ModelAndView mv = new ModelAndView("poll/index");

        Poll poll = databaseService.findPollByPollId(id);
        if (poll == null) {
            return new ModelAndView("redirect:/");
        }
        mv.addObject("poll", poll);
        mv.addObject("options", databaseService.findPollOptionListByPollId(id));
        mv.addObject("counts", databaseService.findCountsByPollId(id));
        mv.addObject("selectedOption", databaseService.findPollResponseByUserInPoll(principal.getName(), id));
        mv.addObject("comments", databaseService.findPollCommentListByPollId(id));

        return mv;
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addForm() {
        return "poll/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String createPoll(PollViewModel pollViewModel) {
        int pollId = databaseService.createPoll(pollViewModel.getQuestion());

        for(PollOption option : pollViewModel.getOptionList()) {
            option.setPollId(pollId);
            if (option.getContent().trim().length() > 0) {
                databaseService.createPollOption(option);
            }
        }
        return "redirect:/?status=add-poll-success";
//            return "redirect:/?status=add-poll-failed";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public ModelAndView editForm(@RequestParam("id") int id) {
        Poll poll = databaseService.findPollByPollId(id);
        List<PollOption> option = databaseService.findPollOptionListByPollId(id);

        if (poll == null) {
            return new ModelAndView("index");
        } else {
            PollViewModel pollViewModel = new PollViewModel();
            pollViewModel.setId(poll.getId());
            pollViewModel.setQuestion(poll.getQuestion());
            pollViewModel.setOptionList(option);

            return new ModelAndView("poll/edit").addObject("pollViewModel", pollViewModel);
        }
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editPoll(PollViewModel pollViewModel) {
        System.out.println("edit: poll id is " + pollViewModel.getId());

        for(PollOption option : pollViewModel.getOptionList()) {
            option.setPollId(pollViewModel.getId());
            option.setContent(option.getContent().trim());
            databaseService.updatePollOption(option);
        }

        return "redirect:/poll/?id="+pollViewModel.getId()+"&status=edit-poll-option-success";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public ModelAndView deleteForm(@RequestParam("id") int id) {
        ModelAndView mv = new ModelAndView("poll/delete");
        mv.addObject("id", id);
        return mv;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") int id) {
        databaseService.deletePoll(id);
        return "redirect:/";
    }

    @RequestMapping(value = "vote", method = RequestMethod.POST)
    public String vote(PollResponse pollResponse, Principal principal) {
        // Check any response made by the user related to the poll
        pollResponse.setUsername(principal.getName());
        pollResponse.setPostTime(System.currentTimeMillis() / 1000L);

        // Update poll response if voted before
        if (databaseService.findPollResponseByUserInPoll(principal.getName(), pollResponse.getPollId()) != -1) {
            databaseService.updatePollResponse(pollResponse);
            return "redirect:/poll/?status=edit-vote-success&id=" + pollResponse.getPollId();
        }

        databaseService.createPollResponse(pollResponse);
        return "redirect:/poll/?status=vote-success&id=" + pollResponse.getPollId();
    }

    @RequestMapping(value = "history", method = RequestMethod.GET)
    public ModelAndView voteHistory(Principal principal) {
        List<PollResponseHistoryViewModel> userResponses = databaseService.findPollResponsesByUser(principal.getName());
        ModelAndView mv = new ModelAndView("poll/history");
        mv.addObject("responses", userResponses);
        return mv;
    }

    @RequestMapping(value = "comment", method = RequestMethod.POST)
    public String comment(PollComment pollComment, Principal principal) {
        if (pollComment.getContent().trim().length() == 0) {
            return "redirect:/poll/?status=comment-empty&id=" + pollComment.getPollId();
        }
        pollComment.setUsername(principal.getName());
        pollComment.setPostTime(System.currentTimeMillis() / 1000L);

        databaseService.createPollComment(pollComment);
        return "redirect:/poll/?status=comment-success&id=" + pollComment.getPollId();
    }


    @RequestMapping(value = "delete-comment", method = RequestMethod.POST)
    public String deleteComment(DeleteCommentForm form) {
        databaseService.deletePollComment(form.commentId);
        return "redirect:/poll/?status=delete-comment-success&id="+ form.id;
    }

    static class DeleteCommentForm {
        private int id;
        private int commentId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }
    }
}
