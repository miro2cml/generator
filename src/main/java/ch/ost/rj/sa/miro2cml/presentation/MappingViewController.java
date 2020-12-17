package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.BoardRepresentationProvider;
import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.business_logic.model.BoardRepresentation;
import ch.ost.rj.sa.miro2cml.business_logic.model.BoardType;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingResult;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@PropertySource("classpath:application.properties")
public class MappingViewController {
    private static final Logger logger = LoggerFactory.getLogger(MappingViewController.class);
    @Autowired
    MappingController mappingController;
    @Autowired
    private BoardRepresentationProvider boardProvider;
    @Value("${miro2cml.baseUrl}")
    private String baseUrl;

    @GetMapping("/")
    public ModelAndView geBoardSelectionView(ModelMap model, @ModelAttribute("form") BoardForm form, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }

        ImmutableTriple<Boolean, Boolean, List<BoardRepresentation>> getBoardsResult = boardProvider.getBoards(SessionHandlerService.getMiroAccessToken(session), SessionHandlerService.getMiroTeamId(session));

        setBasicSetupForModel(model, form, getBoardsResult.getRight(), getBoardsResult.getLeft(), getBoardsResult.getMiddle(), false);
        return new ModelAndView("boardMappingView", model);
    }

    @PostMapping("/")
    public ModelAndView getBoardSelectionAndOutputView(ModelMap model, @ModelAttribute("form") BoardForm form, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }
        System.out.println(new Date().getTime());
        ImmutableTriple<Boolean, Boolean, List<BoardRepresentation>> getBoardsResult = boardProvider.getBoards(SessionHandlerService.getMiroAccessToken(session), SessionHandlerService.getMiroTeamId(session));
        List<BoardRepresentation> boards = getBoardsResult.getRight();
        Boolean success = getBoardsResult.getLeft();
        Boolean exceeded = getBoardsResult.getMiddle();

        setBasicSetupForModel(model, form, boards, success, exceeded, true);

        logger.debug("boardID: " + form.getBoardId());
        logger.debug("commence with board mapping");

        MappingResult result = mappingController.startMappingProcess(form.getBoardType(), form.getBoardId(), SessionHandlerService.getMiroAccessToken(session));
        boolean isMappingSuccess = result.isSuccess();
        logger.debug("finished board mapping, success?: " + isMappingSuccess);

        BoardRepresentation convertedBoard = boards.stream().filter(board -> form.getBoardId().equals(board.getBoardId())).findFirst().orElse(null);
        model.addAttribute("convertedBoard", convertedBoard);
        if (convertedBoard != null) {
            String logDownloadLink = baseUrl + "/downloadLog/" + convertedBoard.getBoardId() + "/" + form.getBoardType() + "/" + SessionHandlerService.getMiroAccessToken(session) + "/" + convertedBoard.getName();
            String cmlDownloadLink = baseUrl + "/download/" + convertedBoard.getBoardId() + "/" + form.getBoardType() + "/" + SessionHandlerService.getMiroAccessToken(session) + "/" + convertedBoard.getName();
            model.addAttribute("logDownloadLink", logDownloadLink);
            model.addAttribute("cmlDownloadLink", cmlDownloadLink);
        }
        model.addAttribute("mappingMessages", result.getMappingMessages().getMessages());
        model.addAttribute("showCMLOutput", isMappingSuccess);

        if (isMappingSuccess) {
            String cmlPreview = result.getCmlPreview();
            model.addAttribute("cmlPreview", cmlPreview);
            model.addAttribute("cmlPreviewLinesCount", cmlPreview.lines().count());
            model.addAttribute("perfectMapping", result.isPerfectSuccess());
        } else {
            String logPreview = result.getLogPreview();
            model.addAttribute("logPreview", logPreview);
            model.addAttribute("logPreviewLinesCount", logPreview.lines().count());
        }
        System.out.println(new Date().getTime());
        return new ModelAndView("boardMappingView", model);
    }

    private void setBasicSetupForModel(ModelMap model, BoardForm form, List<BoardRepresentation> boards, Boolean getBoardsSuccess, Boolean boardLimitExceeded, boolean viewHasOutputSection) {
        List<BoardType> boardTypes = Arrays.asList(BoardType.EducatedGuess, BoardType.UserStory, BoardType.BoundedContextCanvas, BoardType.EventStorming);
        model.addAttribute("boardTypes", boardTypes);
        model.addAttribute("form", form);
        model.addAttribute("miroBoards", boards);
        model.addAttribute("getBoardsSuccess", getBoardsSuccess);
        model.addAttribute("boardLimitExceeded", boardLimitExceeded);
        model.addAttribute("hasOutputSection", viewHasOutputSection);
    }
}
