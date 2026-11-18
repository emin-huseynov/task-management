package az.coders.task_management.mapper;

import az.coders.task_management.dto.request.TaskRequestDto;
import az.coders.task_management.dto.response.TaskResponseDto;

import az.coders.task_management.entity.ProjectEntity;
import az.coders.task_management.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskEntity toEntity(
            TaskRequestDto dto,
            ProjectEntity project
    ) {

        TaskEntity task = new TaskEntity();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        task.setProject(project);

        return task;
    }

    public void updateEntity(
            TaskEntity task,
            TaskRequestDto dto
    ) {

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
    }

    public TaskResponseDto toResponseDto(TaskEntity task) {

        TaskResponseDto dto = new TaskResponseDto();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setProjectId(task.getProject().getId());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        return dto;
    }
}

