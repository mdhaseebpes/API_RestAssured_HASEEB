package serializeDeserialize.complexpojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employees {

    private String id;
    private String name;

    private String department;
    private int salary ;

    private ArrayList<Projects> projects;

    public Employees() {
    }

    public Employees(String id, String name, String department, int salary, ArrayList<Projects> projects) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.projects = projects;
    }


    /**
     * get field
     *
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * set field
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get field
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * set field
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get field
     *
     * @return department
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * set field
     *
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * get field
     *
     * @return salary
     */
    public int getSalary() {
        return this.salary;
    }

    /**
     * set field
     *
     * @param salary
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * get field
     *
     * @return projects
     */
    public ArrayList<Projects> getProjects() {
        return this.projects;
    }

    /**
     * set field
     *
     * @param projects
     */
    public void setProjects(ArrayList<Projects> projects) {
        this.projects = projects;
    }
}
