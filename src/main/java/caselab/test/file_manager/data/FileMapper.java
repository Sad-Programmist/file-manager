package caselab.test.file_manager.data;

import caselab.test.file_manager.data.dto.FileDto;
import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.data.entity.FileEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FileMapper {

    public FileEntity toEntity(FileUploadDto fileUploadDto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setTitle(fileUploadDto.getTitle());
        fileEntity.setFileContent(fileUploadDto.getFileContent());
        fileEntity.setDescription(fileUploadDto.getDescription());

        return fileEntity;
    }

    public FileDto toDto(FileEntity fileEntity) {
        String title = fileEntity.getTitle();
        String fileContent = fileEntity.getFileContent();
        LocalDateTime creationDate = fileEntity.getCreationDate();
        String description = fileEntity.getDescription();

        return new FileDto(title, fileContent, creationDate, description);
    }
}
