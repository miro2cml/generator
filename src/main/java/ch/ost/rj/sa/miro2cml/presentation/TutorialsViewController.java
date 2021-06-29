package ch.ost.rj.sa.miro2cml.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TutorialsViewController {
    @GetMapping(path = "/Tutorial")
    public String getTutorial(Model model) {
        model.addAttribute("module", "tutorials");
        return "Tutorial";
    }
    @GetMapping(path = "/UserStories")
    public String getUserStories(Model model) {
        model.addAttribute("module", "tutorials");
        return "UserStories";
    }
    @GetMapping(path = "/EventStorming")
    public String getEventStorming(Model model) {
        model.addAttribute("module", "tutorials");
        return "EventStorming";
    }

    @GetMapping(path = "/BoundedContext")
    public String getBoundedContext(Model model) {
        model.addAttribute("module", "tutorials");
        return "BoundedContext";
    }

}
