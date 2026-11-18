package az.coders.task_management.dto.request;

import az.coders.task_management.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDto {

    @NotBlank(message = "Project name is required")
    @Size(min = 3, max = 100,
            message = "Project name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000,
            message = "Description cannot exceed 1000 characters")
    private String description;

    private ProjectStatus status;
}
