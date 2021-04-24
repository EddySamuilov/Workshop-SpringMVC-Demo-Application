package softuni.workshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.constants.GlobalConstants;
import softuni.workshop.domain.dto.EmployeeSeedRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.service.EmployeeService;
import softuni.workshop.service.ProjectService;
import softuni.workshop.web.models.EmployeeViewModel;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepo;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final ProjectService projectService;
//    private final ValidatorUtil validatorUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepo, ModelMapper modelMapper, XmlParser xmlParser, FileUtil fileUtil, ProjectService projectService) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.projectService = projectService;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeSeedRootDto employeeSeedRootDto =
                this.xmlParser.importXMl(EmployeeSeedRootDto.class, GlobalConstants.EMPLOYEES_PATH);

        employeeSeedRootDto.getEmployeeSeedDtos()
                .stream()
                .map(e -> {
                    Employee employee = this.modelMapper.map(e, Employee.class);

                    String projectName = employee.getProject().getName();
                    Project project = projectService.findByName(projectName);

                    employee.setProject(project);

                    return employee;
                })
                .forEach(this.employeeRepo::save);
    }

    @Override
    public boolean areImported() {
        return this.employeeRepo.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        String result = null;
        try {
            result = this.fileUtil.readFile(GlobalConstants.EMPLOYEES_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<EmployeeViewModel> getEmployeesAbove25yrs() {
        int age = 25;

        List<EmployeeViewModel> employeeViewModels = this.employeeRepo.findAllByAgeGreaterThan(age)
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeViewModel.class))
                .collect(Collectors.toList());

        return employeeViewModels;
    }

    @Override
    public String exportEmployeesAbove25() {
        StringBuilder sb = new StringBuilder();

        getEmployeesAbove25yrs()
                .forEach(e ->  sb.append(e.toString()) );

        return sb.toString();
    }
}
