package softuni.workshop.service;

import javax.xml.bind.JAXBException;

public interface EmployeeService {

    void importEmployees() throws JAXBException;

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesAbove25();
}
