package softuni.workshop.web.models;

import java.math.BigDecimal;

public class ProjectViewModel {
    private String name;
    private String description;
    private BigDecimal payment;

    public ProjectViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {

        //sb.append(String.format("%s", p.getName()))
        //                                .append(System.lineSeparator())
        //                                .append("\tDescription: ").append(p.getDescription())
        //                                .append(System.lineSeparator())
        //                                .append(String.format("\tPayment: %.2f", p.getPayment()))
        //                                .append(System.lineSeparator())

        return "Project name: " + name + '\n' +
                "\tDescription: " + description + '\n' +
                "\tPayment: " + payment + "\n";
    }
}
