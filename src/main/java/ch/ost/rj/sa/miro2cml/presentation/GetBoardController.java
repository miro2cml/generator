package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.BusinessLogicController;
import ch.ost.rj.sa.miro2cml.model.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.GetBoardForm;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GetBoardController {
    private final Map<String, Resource> resourceMap = new HashMap<>();

    @GetMapping("/")
    public String getBoardFormView(Model model) {
        List<BoardType> boardTypes = Arrays.asList(BoardType.USE_CASE, BoardType.BOUNDED_CONTEXT_CANVAS, BoardType.CONTEXT_MAP, BoardType.EVENT_STORMING, BoardType.AUTOMATIC);
        model.addAttribute("boardTypes", boardTypes);
        GetBoardForm form = new GetBoardForm();
        model.addAttribute("form", form);
        model.addAttribute("module", "home");
        return "getBoardForm";
    }

    @PostMapping("/")
    public String getBoardFormSuccessView(GetBoardForm form, Model model) {
        model.addAttribute("form", form);
        BusinessLogicController businessLogicController = new BusinessLogicController(form.getBoardType(), form.getBoardId(), form.getAccessToken());
        businessLogicController.run();
        resourceMap.put(form.getBoardId(), businessLogicController.getCmlRessource());
        model.addAttribute("outputFile", businessLogicController.getCmlRessource());
        return "getBoardSuccess";
    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> downloadCML(@RequestParam(name = "name", required = true) String name) throws IOException {

        Resource resource = resourceMap.get(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.cml");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
