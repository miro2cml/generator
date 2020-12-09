package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.model.boards.BoardPresentation;
import ch.ost.rj.sa.miro2cml.model.boards.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class BoardMappingController {
    private static final Logger logger = LoggerFactory.getLogger(BoardMappingController.class);

    @Autowired
    MiroApiServiceAdapter miroApiServiceAdapter;

    @Autowired
    private Environment environment;

    @Value("${miro2cml.baseUrl}")
    private String baseUrl;

    @GetMapping("/")
    public ModelAndView getBoardFormView(ModelMap model, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }
        BoardForm form = new BoardForm();
        List<BoardPresentation> boards = miroApiServiceAdapter.getMiroBoards(SessionHandlerService.getMiroAccessToken(session),SessionHandlerService.getMiroTeamId(session));
        setupModel(model, form, boards,false);
        return new ModelAndView("boardMappingView", model);
    }

    @PostMapping("/")
    public ModelAndView getBoardForm(ModelMap model, @ModelAttribute("form") BoardForm form, HttpSession session) {
        if (!SessionHandlerService.hasMiroAccessToken(session)) {
            return new ModelAndView("redirect:/auth");
        }
        List<BoardPresentation> boards = miroApiServiceAdapter.getMiroBoards(SessionHandlerService.getMiroAccessToken(session),SessionHandlerService.getMiroTeamId(session));
        setupModel(model,form,boards,true);
        logger.debug("boardID: " + form.getBoardId());
        logger.debug("commence with board mapping");
        MappingController mappingController = new MappingController(form.getBoardType(), form.getBoardId(), SessionHandlerService.getMiroAccessToken(session), environment, miroApiServiceAdapter);
        boolean isMappingSuccess = mappingController.startMappingProcess();
        logger.debug("finished board mapping, success?: " + isMappingSuccess);

        BoardPresentation convertedBoard = boards.stream().filter(board -> form.getBoardId().equals(board.getBoardId())).findFirst().orElse(null);
        model.addAttribute("convertedBoard", convertedBoard);
        if(convertedBoard!=null){
            String logDownloadLink = baseUrl + "/downloadLog?id=" + convertedBoard.getBoardId()+"&boardType="+form.getBoardType()+"&accessToken="+SessionHandlerService.getMiroAccessToken(session)+"&boardName="+convertedBoard.getName();
            String cmlDownloadLink = baseUrl + "/download?id=" + convertedBoard.getBoardId()+"&boardType="+form.getBoardType()+"&accessToken="+SessionHandlerService.getMiroAccessToken(session)+"&boardName="+convertedBoard.getName();
            model.addAttribute("logDownloadLink",logDownloadLink);
            model.addAttribute("cmlDownloadLink",cmlDownloadLink);
        }
        model.addAttribute("mappingMessages", mappingController.getMappingMessages());
        model.addAttribute("showCMLOutput", isMappingSuccess);
        if (isMappingSuccess) {
            String cmlPreview = mappingController.getCmlPreview();
            model.addAttribute("cmlPreview", cmlPreview);
            model.addAttribute("cmlPreviewLinesCount", cmlPreview.lines().count());
            model.addAttribute("perfectMapping", mappingController.isMappingFullSuccess());
        } else {
            String logPreview = mappingController.getLogPreview();
            model.addAttribute("logPreview", logPreview);
            model.addAttribute("logPreviewLinesCount", logPreview.lines().count());
        }
        return new ModelAndView("boardMappingView", model);
    }

    private void setupModel(ModelMap model, BoardForm form, List<BoardPresentation> boards, boolean viewHasOutputSection) {
        List<BoardType> boardTypes = Arrays.asList(BoardType.EducatedGuess, BoardType.UserStory, BoardType.BoundedContextCanvas, BoardType.EventStorming);
        model.addAttribute("boardTypes", boardTypes);
        model.addAttribute("form", form);
        model.addAttribute("miroBoards", boards);
        model.addAttribute("hasOutputSection", viewHasOutputSection);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadCML(@RequestParam(name = "id", required = true) String boardId, @RequestParam(name = "boardType", required = true) BoardType boardType, @RequestParam(name = "accessToken", required = true) String accessToken, @RequestParam(name = "boardName", required = true) String boardName) throws IOException {
        Resource resource;
        String fileName;
        MappingController mappingController = new MappingController(boardType, boardId, accessToken, environment, miroApiServiceAdapter);
        if (mappingController.startMappingProcess()) {
            resource = mappingController.getServableOutput();
            fileName = "M2C-" + boardName + "-" + boardId + ".cml";
        } else {
            resource = mappingController.getServableMappingLog();
            fileName = "M2C-" + boardName + "-" + boardId + ".log";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/downloadLog")
    public ResponseEntity<Resource> downloadMappingLog(@RequestParam(name = "id", required = true) String boardId, @RequestParam(name = "boardType", required = true) BoardType boardType, @RequestParam(name = "accessToken", required = true) String accessToken, @RequestParam(name = "boardName", required = true) String boardName) throws IOException {
        MappingController mappingController = new MappingController(boardType, boardId, accessToken, environment, miroApiServiceAdapter);
        mappingController.startMappingProcess();
        Resource resource = mappingController.getServableMappingLog();
        String fileName = "M2C-" + boardName + "-" + boardId + ".log";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
