package caselab.test.file_manager.controller;

import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Long> uploadFile(@RequestBody FileUploadDto fileUploadDto) {
        Long id = fileService.uploadFile(fileUploadDto);

        return ResponseEntity.ok(id);
    }
}
