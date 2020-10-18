package ch.ost.rj.sa.miro2cml.presentation;
import ch.ost.rj.sa.miro2cml.model.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.GetBoardForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/")
    public String main(Model model) {
        List<BoardType> boardTypes =  Arrays.asList(BoardType.USE_CASE,BoardType.BOUNDED_CONTEXT_CANVAS,BoardType.CONTEXT_MAP,BoardType.EVENT_STORMING,BoardType.AUTOMATIC);
        model.addAttribute("boardTypes", boardTypes);
        model.addAttribute("message", message);
        model.addAttribute("tasks", tasks);
        GetBoardForm form = new GetBoardForm();
        model.addAttribute("form",form);

        return "welcome"; //view
    }
    @PostMapping("/")
    public String resolveGetBoardForm(GetBoardForm form, Model model){
        model.addAttribute("form",form);

        //ToDo: (async) call into business logic to start conversion

        return "getBoardSuccess";
    }

    // /hello?miroid=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);

        return "welcome"; //view
    }

}
