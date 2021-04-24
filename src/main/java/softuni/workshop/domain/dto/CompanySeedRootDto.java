package softuni.workshop.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedRootDto {

    @XmlElement(name = "company")
    private List<CompanySeedDto> companies;

    public CompanySeedRootDto() {
    }

    public List<CompanySeedDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanySeedDto> companies) {
        this.companies = companies;
    }
}
