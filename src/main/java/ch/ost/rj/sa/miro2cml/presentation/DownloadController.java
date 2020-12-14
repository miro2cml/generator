package ch.ost.rj.sa.miro2cml.presentation;

import ch.ost.rj.sa.miro2cml.business_logic.MappingController;
import ch.ost.rj.sa.miro2cml.business_logic.model.MappingResult;
import ch.ost.rj.sa.miro2cml.business_logic.model.BoardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DownloadController {

    @Autowired
    MappingController mappingController;

    @GetMapping("/download/{id}/{boardType}/{accessToken}/{boardName}")
    public ResponseEntity<Resource> downloadCML(@PathVariable final String id,
                                                @PathVariable final BoardType boardType,
                                                @PathVariable final String accessToken,
                                                @PathVariable final String boardName) throws IOException {

        Resource resource;
        String fileName;

        MappingResult result = mappingController.startMappingProcess(boardType, id, accessToken);

        if (result.isSuccess()) {
            resource = result.getCMLResource();
            fileName = "M2C-" + boardName + "-" + id + ".cml";
        } else {
            resource = result.getServableMappingLog();
            fileName = "M2C-" + boardName + "-" + id + ".log";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }

    @GetMapping("/downloadLog/{boardId}/{boardType}/{accessToken}/{boardName}")
    public ResponseEntity<Resource> downloadMappingLog(@PathVariable final String boardId,
                                                @PathVariable final BoardType boardType,
                                                @PathVariable final String accessToken,
                                                @PathVariable final String boardName) throws IOException {

        MappingResult result = mappingController.startMappingProcess(boardType, boardId, accessToken);
        Resource resource = result.getServableMappingLog();
        String fileName = "M2C-" + boardName + "-" + boardId + ".log";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.TEXT_PLAIN)
                .body(resource);
    }
}
