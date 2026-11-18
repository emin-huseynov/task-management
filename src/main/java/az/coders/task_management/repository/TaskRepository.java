package az.coders.task_management.repository;

import az.coders.task_management.entity.TaskEntity;
import az.coders.task_management.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByProjectId(Long projectId);

    Optional<TaskEntity> findByIdAndProjectId(Long id, Long projectId);

    List<TaskEntity> findByProjectIdAndStatus(Long projectId, TaskStatus status);
}

