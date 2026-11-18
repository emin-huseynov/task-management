
package az.coders.task_management.controller;

import az.coders.task_management.dto.request.TaskRequestDto;
import az.coders.task_management.dto.response.TaskResponseDto;

import az.coders.task_management.exception.ResourceNotFoundException;
import az.coders.task_management.repository.UserRepository;
import az.coders.task_management.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequestDto dto,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        TaskResponseDto response =
                taskService.createTask(projectId, userId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(
            @PathVariable Long projectId,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                taskService.getAllTasks(projectId, userId)
        );
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                taskService.getTaskById(projectId, taskId, userId)
        );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDto dto,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                taskService.updateTask(projectId, taskId, userId, dto)
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        taskService.deleteTask(projectId, taskId, userId);

        return ResponseEntity.noContent().build();
    }

    private Long getUserId(Principal principal) {

        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"))
                .getId();
    }
}
