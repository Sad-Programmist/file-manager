package caselab.test.file_manager;

import caselab.test.file_manager.controller.FileController;
import caselab.test.file_manager.data.dto.FileDto;
import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFile() {
        // Given
        FileUploadDto fileUploadDto = new FileUploadDto("Example", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                "Test file");
        when(fileService.uploadFile(fileUploadDto)).thenReturn(1L);

        // When
        ResponseEntity<Long> response = fileController.uploadFile(fileUploadDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void testGetFile_FileExists() {
        // Given
        Long fileId = 1L;
        FileDto fileDto = new FileDto("example.txt", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                LocalDateTime.now(), "Test file");
        when(fileService.getFile(fileId)).thenReturn(Optional.of(fileDto));

        // When
        ResponseEntity<FileDto> response = fileController.getFile(fileId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileDto, response.getBody());
    }

    @Test
    void testGetFile_FileNotFound() {
        // Given
        Long fileId = 1L;
        when(fileService.getFile(fileId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<FileDto> response = fileController.getFile(fileId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllFiles_WithFiles() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        FileDto fileDto1 = new FileDto("File 1", "content1", LocalDateTime.now(), "Description 1");
        FileDto fileDto2 = new FileDto("File 2", "content2", LocalDateTime.now(), "Description 2");
        Page<FileDto> fileDtoPage = new PageImpl<>(Arrays.asList(fileDto1, fileDto2), pageable, 2);
        when(fileService.getAllFiles(any(Pageable.class))).thenReturn(fileDtoPage);

        // When
        ResponseEntity<List<FileDto>> response = fileController.getAllFiles(0, 10);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<FileDto> fileDtoList = response.getBody();
        assertNotNull(fileDtoList);
        assertEquals(2, fileDtoList.size());
        assertEquals("File 1", fileDtoList.get(0).getTitle());
        assertEquals("File 2", fileDtoList.get(1).getTitle());
    }

    @Test
    void testGetAllFiles_NoFiles() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        when(fileService.getAllFiles(any(Pageable.class))).thenReturn(Page.empty());

        // When
        ResponseEntity<List<FileDto>> response = fileController.getAllFiles(0, 10);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<FileDto> fileDtoList = response.getBody();
        assertNotNull(fileDtoList);
        assertTrue(fileDtoList.isEmpty());
    }
}
