package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingResult;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.BoardPresentationData;
import ch.ost.rj.sa.miro2cml.business_logic.model.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@PropertySource("classpath:application.properties")
public class MappingViewController {
    private static final Logger logger = LoggerFactory.getLogger(MappingViewController.class);

    @Autowired
    MiroApiServiceAdapter miroApiServiceAdapter ;

    @Autowired
    private Environment environment;

    @Value("${miro2cml.baseUrl}")
    private String baseUrl;


    @Autowired
    MappingController mappingController;


    @GetMapping("/")
    public ModelAndView geBoardSelectionView(ModelMap model, @ModelAttribute("form") BoardForm form, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }

        ImmutableTriple<Boolean,Boolean,List<BoardPresentationData>> getBoardsResult = miroApiServiceAdapter.getMiroBoards(SessionHandlerService.getMiroAccessToken(session), SessionHandlerService.getMiroTeamId(session));
        List<BoardPresentationData> boards = getBoardsResult.getRight();
        Boolean success = getBoardsResult.getLeft();
        Boolean exceeded = getBoardsResult.getMiddle();



        setBasicSetupForModel(model, form, boards,success,exceeded, false);
        return new ModelAndView("boardMappingView", model);
    }

    @PostMapping("/")
    public ModelAndView getBoardSelectionAndOutputView(ModelMap model, @ModelAttribute("form") BoardForm form, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }
        ImmutableTriple<Boolean,Boolean,List<BoardPresentationData>> getBoardsResult = miroApiServiceAdapter.getMiroBoards(SessionHandlerService.getMiroAccessToken(session), SessionHandlerService.getMiroTeamId(session));
        List<BoardPresentationData> boards = getBoardsResult.getRight();
        Boolean success = getBoardsResult.getLeft();
        Boolean exceeded = getBoardsResult.getMiddle();

        setBasicSetupForModel(model, form, boards,success,exceeded, true);
        logger.debug("boardID: " + form.getBoardId());
        logger.debug("commence with board mapping");

        MappingResult result = mappingController.startMappingProcess(form.getBoardType(), form.getBoardId(), SessionHandlerService.getMiroAccessToken(session));
        boolean isMappingSuccess = result.isSuccess();
        logger.debug("finished board mapping, success?: " + isMappingSuccess);

        BoardPresentationData convertedBoard = boards.stream().filter(board -> form.getBoardId().equals(board.getBoardId())).findFirst().orElse(null);
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

        return new ModelAndView("boardMappingView", model);
    }

    private void setBasicSetupForModel(ModelMap model, BoardForm form, List<BoardPresentationData> boards, Boolean getBoardsSuccess, Boolean boardLimitExceeded, boolean viewHasOutputSection) {
        List<BoardType> boardTypes = Arrays.asList(BoardType.EducatedGuess, BoardType.UserStory, BoardType.BoundedContextCanvas, BoardType.EventStorming);
        model.addAttribute("boardTypes", boardTypes);
        model.addAttribute("form", form);
        model.addAttribute("miroBoards", boards);
        model.addAttribute("getBoardsSuccess",getBoardsSuccess);
        model.addAttribute("boardLimitExceeded",boardLimitExceeded);
        model.addAttribute("hasOutputSection", viewHasOutputSection);
    }
}
