package softuni.workshop.web.models;

public class EmployeeViewModel {
    private String firstName;
    private String lastName;
    private int age;
    private String projectName;

    public EmployeeViewModel() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

         sb.append(String.format("Name: %s %s", this.getFirstName(), this.getLastName()))
                .append(System.lineSeparator())
                .append("\tAge: ").append(this.getAge())
                .append(System.lineSeparator())
                .append("\tProject Name: ").append(this.getProjectName())
                .append(System.lineSeparator());

        return sb.toString();
    }
}
