package az.coders.task_management.dto.request;


import az.coders.task_management.entity.TaskPriority;
import az.coders.task_management.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequestDto {

    @NotBlank(message = "Task title is required")
    @Size(min = 3, max = 150,
            message = "Task title must be between 3 and 150 characters")
    private String title;

    @Size(max = 2000,
            message = "Description cannot exceed 2000 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;
}

