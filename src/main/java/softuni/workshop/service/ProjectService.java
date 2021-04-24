package softuni.workshop.service;

import softuni.workshop.domain.entities.Project;
import softuni.workshop.web.models.ProjectViewModel;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface ProjectService {

    void importProjects() throws JAXBException;

    boolean areImported();

    String readProjectsXmlFile();

    String exportFinishedProjects();

    Project findByName(String projectName);

    List<ProjectViewModel> findAllFinishedProjects();
}
