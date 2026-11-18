
package az.coders.task_management.service;

import az.coders.task_management.dto.request.TaskRequestDto;
import az.coders.task_management.dto.response.TaskResponseDto;
import az.coders.task_management.entity.ProjectEntity;
import az.coders.task_management.entity.TaskEntity;
import az.coders.task_management.exception.ResourceNotFoundException;
import az.coders.task_management.mapper.TaskMapper;
import az.coders.task_management.repository.ProjectRepository;
import az.coders.task_management.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponseDto createTask(
            Long projectId,
            Long userId,
            TaskRequestDto dto
    ) {

        ProjectEntity project = findOwnedProject(projectId, userId);

        TaskEntity task = taskMapper.toEntity(dto, project);

        TaskEntity savedTask = taskRepository.save(task);

        return taskMapper.toResponseDto(savedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllTasks(
            Long projectId,
            Long userId
    ) {

        findOwnedProject(projectId, userId);

        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(taskMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(
            Long projectId,
            Long taskId,
            Long userId
    ) {

        findOwnedProject(projectId, userId);

        TaskEntity task = taskRepository
                .findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        return taskMapper.toResponseDto(task);
    }

    @Transactional
    public TaskResponseDto updateTask(
            Long projectId,
            Long taskId,
            Long userId,
            TaskRequestDto dto
    ) {

        findOwnedProject(projectId, userId);

        TaskEntity task = taskRepository
                .findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        taskMapper.updateEntity(task, dto);

        TaskEntity updatedTask = taskRepository.save(task);

        return taskMapper.toResponseDto(updatedTask);
    }

    @Transactional
    public void deleteTask(
            Long projectId,
            Long taskId,
            Long userId
    ) {

        findOwnedProject(projectId, userId);

        TaskEntity task = taskRepository
                .findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
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
