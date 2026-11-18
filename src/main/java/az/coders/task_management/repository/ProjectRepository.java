package az.coders.task_management.repository;


import az.coders.task_management.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByOwnerId(Long ownerId);

    Optional<ProjectEntity> findByIdAndOwnerId(Long id, Long ownerId);
}


