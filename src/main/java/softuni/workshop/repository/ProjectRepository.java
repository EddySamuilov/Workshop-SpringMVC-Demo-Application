package softuni.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.domain.entities.Project;

import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String projectName);

    Set<Project> findAllByFinishedTrue();
}
