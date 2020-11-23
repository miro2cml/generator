package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class SelectBoardTypeController {
    @GetMapping("/selectBoardType")
    public ModelAndView selectBoardType(ModelMap model, BoardForm form) {
        List<BoardType> boardTypes = Arrays.asList(BoardType.USE_CASE, BoardType.BOUNDED_CONTEXT_CANVAS, BoardType.CONTEXT_MAP, BoardType.EVENT_STORMING, BoardType.AUTOMATIC);
        model.addAttribute("boardTypes", boardTypes);
        model.addAttribute("form", form);
        //TODO add boardId to logger
        return new ModelAndView("selectBoardType", model);
    }
}
