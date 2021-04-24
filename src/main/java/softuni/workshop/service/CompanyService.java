package softuni.workshop.service;

import softuni.workshop.domain.entities.Company;

import javax.xml.bind.JAXBException;

public interface CompanyService {
    void importCompanies() throws JAXBException;
    boolean areImported();
    String readCompaniesXmlFile();

    Company findByName(String name);
}
