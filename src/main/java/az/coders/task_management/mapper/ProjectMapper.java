package az.coders.task_management.mapper;

import az.coders.task_management.dto.request.ProjectRequestDto;
import az.coders.task_management.dto.response.ProjectResponseDto;
import az.coders.task_management.entity.ProjectEntity;
import az.coders.task_management.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectEntity toEntity(
            ProjectRequestDto dto,
            UserEntity owner
    ) {

        ProjectEntity project = new ProjectEntity();

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        project.setOwner(owner);

        return project;
    }

    public void updateEntity(
            ProjectEntity project,
            ProjectRequestDto dto
    ) {

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
    }

    public ProjectResponseDto toResponseDto(ProjectEntity project) {

        ProjectResponseDto dto = new ProjectResponseDto();

        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setStatus(project.getStatus());
        dto.setOwnerId(project.getOwner().getId());
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());

        return dto;
    }
}
