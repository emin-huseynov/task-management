package az.coders.task_management.dto.response;


import az.coders.task_management.entity.TaskPriority;
import az.coders.task_management.entity.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private Long projectId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
