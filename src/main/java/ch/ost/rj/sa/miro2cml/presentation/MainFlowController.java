package ch.ost.rj.sa.miro2cml.presentation;


import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MainFlowController {
    private static final Logger logger = LoggerFactory.getLogger(MainFlowController.class);

    @Autowired
    GetBoardController getBoardController;
    @Autowired
    AuthorizationController authorizationController;

    @GetMapping("/")
    public ModelAndView rootEntryPoint(HttpSession session, ModelMap model) {

        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }
        logger.debug("call selectBoardView");
        return new ModelAndView("redirect:/selectBoard");
    }

}
