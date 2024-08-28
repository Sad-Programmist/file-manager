package caselab.test.file_manager.controller;

import caselab.test.file_manager.data.dto.FileDto;
import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFile(@PathVariable Long id) {
        Optional<FileDto> fileDto = fileService.getFile(id);

        return fileDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FileDto>> getAllFiles(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").descending());
        Page<FileDto> files = fileService.getAllFiles(pageable);

        return ResponseEntity.ok(files.getContent());
    }
}
