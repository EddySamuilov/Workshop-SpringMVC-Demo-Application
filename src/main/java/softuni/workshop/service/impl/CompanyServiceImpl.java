package softuni.workshop.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dto.CompanySeedRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.service.CompanyService;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidationUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;

import static softuni.workshop.constants.GlobalConstants.*;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.companyRepository = companyRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public void importCompanies() throws JAXBException {
        CompanySeedRootDto companySeedRootDto =
                this.xmlParser.importXMl(CompanySeedRootDto.class, COMPANY_PATH);

        companySeedRootDto.getCompanies()
                .stream()
                .map(c -> this.modelMapper.map(c, Company.class))
                .forEach(c -> {
                    if (this.validationUtil.isValid(c)){
                        this.companyRepository.save(c);
                    }else {
                        this.validationUtil.violations(c)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });

    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() {
        String xml = null;
        try {
            xml = fileUtil.readFile(COMPANY_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    @Override
    public Company findByName(String name) {
        return this.companyRepository.findByName(name);
    }
}
