
package az.coders.task_management.service;

import az.coders.task_management.dto.request.ProjectRequestDto;
import az.coders.task_management.dto.response.ProjectResponseDto;

import az.coders.task_management.entity.ProjectEntity;
import az.coders.task_management.entity.UserEntity;
import az.coders.task_management.exception.ResourceNotFoundException;
import az.coders.task_management.mapper.ProjectMapper;
import az.coders.task_management.repository.ProjectRepository;
import az.coders.task_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponseDto createProject(
            ProjectRequestDto dto,
            Long userId
    ) {

        UserEntity owner = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        ProjectEntity project = projectMapper.toEntity(dto, owner);

        ProjectEntity savedProject = projectRepository.save(project);

        return projectMapper.toResponseDto(savedProject);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProjects(Long userId) {

        return projectRepository.findByOwnerId(userId)
                .stream()
                .map(projectMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(
            Long projectId,
            Long userId
    ) {

        ProjectEntity project = findOwnedProject(projectId, userId);

        return projectMapper.toResponseDto(project);
    }

    @Transactional
    public ProjectResponseDto updateProject(
            Long projectId,
            Long userId,
            ProjectRequestDto dto
    ) {

        ProjectEntity project = findOwnedProject(projectId, userId);

        projectMapper.updateEntity(project, dto);

        ProjectEntity updatedProject = projectRepository.save(project);

        return projectMapper.toResponseDto(updatedProject);
    }

    @Transactional
    public void deleteProject(Long projectId, Long userId) {

        ProjectEntity project = findOwnedProject(projectId, userId);

        projectRepository.delete(project);
    }

    private ProjectEntity findOwnedProject(
            Long projectId,
            Long userId
    ) {

        return projectRepository
                .findByIdAndOwnerId(projectId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found"));
    }
}
