package caselab.test.file_manager;

import caselab.test.file_manager.data.FileMapper;
import caselab.test.file_manager.data.dto.FileDto;
import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.data.entity.FileEntity;
import caselab.test.file_manager.data.repository.FileRepository;
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

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFile() {
        // Given
        String title = "Example";
        String fileContent = "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==";
        String description = "Test file";
        FileUploadDto fileUploadDto = new FileUploadDto(title, fileContent, description);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);

        when(fileMapper.toEntity(fileUploadDto)).thenReturn(fileEntity);
        when(fileRepository.save(any(FileEntity.class))).thenReturn(fileEntity);

        // When
        Long result = fileService.uploadFile(fileUploadDto);

        // Then
        assertEquals(1L, result);
    }

    @Test
    void testGetFileById_FileExists() {
        // Given
        Long fileId = 1L;
        String title = "Example";
        String fileContent = "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==";
        LocalDateTime creationDate = LocalDateTime.of(2024, 8, 28, 1, 10, 34);
        String description = "Test file";
        FileEntity fileEntity = new FileEntity(fileId, title, fileContent, creationDate, description);

        FileDto fileDto = new FileDto(title, fileContent, creationDate, description);

        when(fileRepository.findById(fileId)).thenReturn(Optional.of(fileEntity));
        when(fileMapper.toDto(fileEntity)).thenReturn(fileDto);

        // When
        Optional<FileDto> result = fileService.getFile(fileId);

        // Then
        assertTrue(result.isPresent());
        FileDto actualFileDto = result.get();
        assertEquals(title, actualFileDto.getTitle());
        assertEquals(fileContent, actualFileDto.getFileContent());
        assertEquals(creationDate, actualFileDto.getCreationDate());
        assertEquals(description, actualFileDto.getDescription());
    }

    @Test
    void testGetFileById_FileNotFound() {
        // Given
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // When
        Optional<FileDto> result = fileService.getFile(fileId);

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAllFiles_WithFiles() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        LocalDateTime creationDate1 = LocalDateTime.of(2024, 8, 28, 1, 10, 34);
        FileEntity fileEntity1 = new FileEntity(1L, "File 1", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                creationDate1, "Description 1");

        LocalDateTime creationDate2 = LocalDateTime.of(2024, 8, 28, 1, 11, 34);
        FileEntity fileEntity2 = new FileEntity(2L, "File 2", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                creationDate2, "Description 2");

        FileDto fileDto1 = new FileDto("File 1", "content1", creationDate1, "Description 1");
        FileDto fileDto2 = new FileDto("File 2", "content2", creationDate2, "Description 2");

        Page<FileEntity> fileEntityPage = new PageImpl<>(Arrays.asList(fileEntity1, fileEntity2), pageable, 2);
        when(fileRepository.findAll(pageable)).thenReturn(fileEntityPage);
        when(fileMapper.toDto(fileEntity1)).thenReturn(fileDto1);
        when(fileMapper.toDto(fileEntity2)).thenReturn(fileDto2);

        // When
        Page<FileDto> result = fileService.getAllFiles(pageable);

        // Then
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals("File 1", result.getContent().get(0).getTitle());
        assertEquals("File 2", result.getContent().get(1).getTitle());
    }

    @Test
    void testGetAllFiles_NoFiles() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<FileEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(fileRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<FileDto> result = fileService.getAllFiles(pageable);

        // Then
        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void testGetAllFiles_Pagination() {
        // Given
        Pageable pageable = PageRequest.of(1, 2);

        LocalDateTime creationDate3 = LocalDateTime.of(2024, 8, 28, 1, 10, 34);
        FileEntity fileEntity1 = new FileEntity(3L, "File 3", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                creationDate3, "Description 3");

        LocalDateTime creationDate4 = LocalDateTime.of(2024, 8, 28, 1, 11, 34);
        FileEntity fileEntity2 = new FileEntity(4L, "File 4", "VGhpcyBpcyBhIHRlc3QgZmlsZSBjb250ZW50Lg==",
                creationDate4, "Description 4");

        FileDto fileDto1 = new FileDto("File 3", "content3", creationDate3, "Description 3");
        FileDto fileDto2 = new FileDto("File 4", "content4", creationDate4, "Description 4");

        Page<FileEntity> fileEntityPage = new PageImpl<>(Arrays.asList(fileEntity1, fileEntity2), pageable, 4);
        when(fileRepository.findAll(pageable)).thenReturn(fileEntityPage);
        when(fileMapper.toDto(fileEntity1)).thenReturn(fileDto1);
        when(fileMapper.toDto(fileEntity2)).thenReturn(fileDto2);

        // When
        Page<FileDto> result = fileService.getAllFiles(pageable);

        // Then
        assertEquals(4, result.getTotalElements());
        assertEquals(2, result.getContent().size());
        assertEquals("File 3", result.getContent().get(0).getTitle());
        assertEquals("File 4", result.getContent().get(1).getTitle());
    }
}
