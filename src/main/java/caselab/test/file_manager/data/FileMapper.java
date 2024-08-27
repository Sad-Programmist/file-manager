package caselab.test.file_manager.data;

import caselab.test.file_manager.data.dto.FileUploadDto;
import caselab.test.file_manager.data.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {

    public FileEntity toEntity(FileUploadDto fileUploadDto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setTitle(fileUploadDto.getTitle());
        fileEntity.setFileContent(fileUploadDto.getFileContent());
        fileEntity.setDescription(fileUploadDto.getDescription());

        return fileEntity;
    }
}
