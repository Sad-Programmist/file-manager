package caselab.test.file_manager.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class FileDto {
    private String title;
    private String fileContent;
    private LocalDateTime creationDate;
    private String description;
}
