package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.presentation.model.BoardForm;
import ch.ost.rj.sa.miro2cml.presentation.utility.SessionHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(GetBoardController.class);
    private final Map<String, Resource> outputResourceMap = new HashMap<>();
    private final Map<String, Resource> mappingLogRessourceMap = new HashMap<>();
    //Todo: limit mapsizes to 100 -> probably over a fifo queue -> (if size >= 100 -> pop -> delete from map and after that save

    @PostMapping("/getBoard")
    public String getOutput(BoardForm form, Model model, HttpSession session) {
        model.addAttribute("form", form);
        logger.debug("boardID: " + form.getBoardId());
        logger.debug("commence with board mapping");
        MappingController mappingController = new MappingController(form.getBoardType(), form.getBoardId(), SessionHandlerService.getMiroAccessToken(session));
        boolean succes = mappingController.run();
        logger.debug("finished board mapping, success?: " + succes);

        mappingLogRessourceMap.put(form.getBoardId(), mappingController.getServableMappingLog());
        model.addAttribute("mappingMessages",mappingController.getMappingMessages());
        if (succes){
            outputResourceMap.put(form.getBoardId(), mappingController.getServableOutput());
            model.addAttribute("perfectMapping",mappingController.isMappingFullSuccess());
            return "output";
        }
        else {
            return "no-output";
        }
    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> downloadCML(@RequestParam(name = "name", required = true) String name) throws IOException {

        Resource resource = outputResourceMap.get(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name + ".cml");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    @GetMapping(path = "/downloadLog")
    public ResponseEntity<Resource> downloadMappingLog(@RequestParam(name = "name", required = true) String name) throws IOException {

        Resource resource = mappingLogRessourceMap.get(name);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name + ".log");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
