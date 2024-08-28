package caselab.test.file_manager.service;

import caselab.test.file_manager.data.FileMapper;
import caselab.test.file_manager.data.dto.FileDto;
import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.data.entity.FileEntity;
import caselab.test.file_manager.data.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FileService {

    private FileRepository fileRepository;

    private FileMapper fileMapper;

    @Transactional
    public Long uploadFile(FileUploadDto fileUploadDto) {
        FileEntity fileEntity = fileMapper.toEntity(fileUploadDto);
        return fileRepository.save(fileEntity).getId();
    }

    @Transactional
    public Optional<FileDto> getFile(Long id) {
        Optional<FileEntity> fileEntity = fileRepository.findById(id);
        return fileEntity.map(entity -> fileMapper.toDto(entity));
    }

}
