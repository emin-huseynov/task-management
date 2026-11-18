
package az.coders.task_management.controller;

import az.coders.task_management.dto.request.ProjectRequestDto;
import az.coders.task_management.dto.response.ProjectResponseDto;

import az.coders.task_management.exception.ResourceNotFoundException;
import az.coders.task_management.repository.UserRepository;
import az.coders.task_management.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(
            @Valid @RequestBody ProjectRequestDto dto,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        ProjectResponseDto response =
                projectService.createProject(dto, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects(
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                projectService.getAllProjects(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(
            @PathVariable Long id,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                projectService.getProjectById(id, userId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDto dto,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        return ResponseEntity.ok(
                projectService.updateProject(id, userId, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id,
            Principal principal
    ) {

        Long userId = getUserId(principal);

        projectService.deleteProject(id, userId);

        return ResponseEntity.noContent().build();
    }

    private Long getUserId(Principal principal) {

        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"))
                .getId();
    }
}
