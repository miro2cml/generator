package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.GetPotentialBoardsController;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SelectBoardController {
    private final Map<String, Resource> resourceMap = new HashMap<>();

    @GetMapping("/selectBoard")
    public ModelAndView getBoardFormView(ModelMap model, HttpSession session) {

        GetPotentialBoardsController boardSelectionService = new GetPotentialBoardsController(SessionHandlerService.getMiroAccessToken(session),SessionHandlerService.getMiroTeamId(session));
        List<BoardPresentation> boards = boardSelectionService.run();
        model.addAttribute("miroBoards", boards);

        BoardForm form = new BoardForm();
        model.addAttribute("form", form);

        return new ModelAndView("selectBoard", model);
    }
}
