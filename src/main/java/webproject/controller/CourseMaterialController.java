package webproject.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import webproject.bean.DatabaseService;
import webproject.model.Attachment;
import webproject.model.Lecture;
import webproject.view.DownloadingView;

@Controller
@RequestMapping("lecture")
public class CourseMaterialController {

    @Autowired
    DatabaseService databaseService;

    @RequestMapping(value = "addlecture", method = RequestMethod.GET)
    public ModelAndView lecture() {
        return new ModelAndView("addLecture", "lectureForm", new Form());
    }

    @RequestMapping(value = "addlecture", method = RequestMethod.POST)
    public String createLecture(Form form) throws IOException {
        databaseService.createLecture(form.getTitle(), form.getAttachments());
        return "redirect:/lecture/addlecture?status=addlecture-success";
    }

    public static class Form {

        private String title;
        private List<MultipartFile> attachments;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }

    }

    @RequestMapping(value = "view/{lid}", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable("lid") int lid, ModelMap model) {
        Lecture lecture = databaseService.getLecture(lid);
        model.addAttribute("lecture", lecture);
        model.addAttribute("comments", databaseService.getCommentsFromLect(lid));
        model.addAttribute("attachments", databaseService.listLectAttach(lid));
        ModelAndView m = new ModelAndView("viewLecture");
        m.addObject("form", new Form());
        m.addObject("commentForm", new CommentForm());
        return m;
    }

    @RequestMapping(value = "view/{lid}", method = RequestMethod.POST)
    public String comment(@PathVariable("lid") int lid, CommentForm comment, Form form) throws IOException {
        System.out.println(comment.getId());
        if (comment.getId() > 0) {
            databaseService.makeComment(comment.getId(), comment.getName(), comment.getComment());
        } else {
            databaseService.addAttachment(lid, form.getAttachments());
        }
        return "redirect:/lecture/view/" + lid;
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String deleteForm(@RequestParam("lid") int lid) throws IOException {
        databaseService.deleteLecture(lid);
        return "redirect:/";
    }

    @RequestMapping(value = "comment/delete", method = RequestMethod.GET)
    public String deleteComment(@RequestParam("cid") int cid, @RequestParam("lid") int lid) throws IOException {
        databaseService.deleteComment(cid);
        return "redirect:/lecture/view/" + lid;
    }

    @RequestMapping(value = "/{lid}/attachment/{aid}", method = RequestMethod.GET)
    public DownloadingView download(@PathVariable("lid") int lid, @PathVariable("aid") int aid) {
        Attachment a = databaseService.getAttach(lid, aid);
        return new DownloadingView(a.getName(), a.getMimeContentType(), a.getContents());
    }

    @RequestMapping(value = "attachment/delete", method = RequestMethod.GET)
    public String deleteAttachment(@RequestParam("aid") int aid, @RequestParam("lid") int lid) throws IOException {
        databaseService.deleteAttachment(aid);
        return "redirect:/lecture/view/" + lid;
    }

    private static class CommentForm {

        private int id;
        private String name;
        private String comment;

        public CommentForm() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }
}
