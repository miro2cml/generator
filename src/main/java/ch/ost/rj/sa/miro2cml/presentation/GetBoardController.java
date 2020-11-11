package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GetBoardController {
    private final Map<String, Resource> resourceMap = new HashMap<>();

    @PostMapping("/getBoard")
    public String getBoard(BoardForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        System.out.println("boardID: " + form.getBoardId());
        System.out.println("log: board will now get Mapped");
        MappingController mappingController = new MappingController(form.getBoardType(), form.getBoardId(), SessionHandlerService.getMiroAccessToken(session));
        mappingController.run();
        System.out.println("log: board Mapped");
        resourceMap.put(form.getBoardId(), mappingController.getResource());
        model.addAttribute("outputFile", mappingController.getResource());
        return "cml-output";
    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> downloadCML(@RequestParam(name = "name", required = true) String name) throws IOException {

        Resource resource = resourceMap.get(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name + ".cml");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
