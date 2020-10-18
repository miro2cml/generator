package ch.ost.rj.sa.miro2cml.presentation;
import ch.ost.rj.sa.miro2cml.business_logic.BusinessLogicController;
import ch.ost.rj.sa.miro2cml.model.BoardType;
import ch.ost.rj.sa.miro2cml.presentation.model.GetBoardForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.ost.rj.sa.miro2cml.data_access.MiroApiConsumer.getBoardWidgets;

@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message}")
    private String message;

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    private Map<String, ByteArrayResource> resourceMap = new HashMap<>();

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
        BusinessLogicController businessLogicController = new BusinessLogicController(form.getBoardType(),form.getBoardId(),form.getAccessToken());
        businessLogicController.run();
        resourceMap.put(form.getBoardId(),businessLogicController.getCml());
        model.addAttribute("outputFile",businessLogicController.getCml());
        return "getBoardSuccess";
    }
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadCML(@RequestParam(name = "name", required = true) String name) throws IOException {

        ByteArrayResource resource = resourceMap.get(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.cml");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    // /hello?miroid=kotlin
    @GetMapping("/hello")
    public String mainWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "")
                    String name, Model model) {

        model.addAttribute("message", name);
        getBoardWidgets("5832d294-d90a-40b2-a83b-9aa5232b1493", "o9J_ki4it0o=");
        return "welcome"; //view
    }

}
