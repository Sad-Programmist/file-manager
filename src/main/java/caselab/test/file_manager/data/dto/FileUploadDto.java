package caselab.test.file_manager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileUploadDto {
    private String title;
    private String fileContent;
    private String description;
}
