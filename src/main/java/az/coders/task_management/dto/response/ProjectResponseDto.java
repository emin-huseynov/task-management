package az.coders.task_management.dto.response;

import az.coders.task_management.entity.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponseDto {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
