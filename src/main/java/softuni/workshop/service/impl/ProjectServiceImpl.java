package softuni.workshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constants.GlobalConstants;
import softuni.workshop.domain.dto.CompanySeedDto;
import softuni.workshop.domain.dto.ProjectRootSeedDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.service.CompanyService;
import softuni.workshop.service.ProjectService;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.web.models.ProjectViewModel;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepo;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CompanyService companyService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepo, FileUtil fileUtil, ModelMapper modelMapper, XmlParser xmlParser, CompanyService companyService, ValidationUtil validationUtil) {
        this.projectRepo = projectRepo;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.companyService = companyService;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importProjects() throws JAXBException {


        ProjectRootSeedDto projectRootSeedDto =
                this.xmlParser.importXMl(ProjectRootSeedDto.class, "src/main/resources/files/xmls/projects.xml");

        projectRootSeedDto.getProjects()
                .stream()
                .map(c -> {
                    Project project = this.modelMapper.map(c, Project.class);

                    CompanySeedDto companySeedDto = c.getCompanySeedDto();
                    Company company = this.companyService.findByName(companySeedDto.getName());

                    project.setCompany(company);

                    return project;
                })
                .forEach(p -> {
                    if (this.validationUtil.isValid(p)){
                        this.projectRepo.save(p);
                    }else {
                        this.validationUtil.violations(p)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public boolean areImported() {
        return this.projectRepo.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() {
        String xml = null;
        try {
            xml = fileUtil.readFile(GlobalConstants.PROJECTS_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder sb = new StringBuilder();

        findAllFinishedProjects()
                .forEach(p -> sb.append(p.toString()));

        return sb.toString();
    }

    @Override
    public Project findByName(String projectName) {
        return this.projectRepo.findByName(projectName);
    }

    @Override
    public List<ProjectViewModel> findAllFinishedProjects() {
        return this.projectRepo.findAllByFinishedTrue()
                .stream()
                .map(p -> this.modelMapper.map(p, ProjectViewModel.class))
                .collect(Collectors.toList());
    }
}
