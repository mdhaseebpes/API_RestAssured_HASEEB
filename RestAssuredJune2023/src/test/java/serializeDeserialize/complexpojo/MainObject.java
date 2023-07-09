package serializeDeserialize.complexpojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import serializeDeserialize.pojo.Employee;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainObject {

    private ArrayList<Employees> employees;
    private ArrayList<Department>  departments;

    public MainObject() {
    }

    public MainObject(ArrayList<Employees> employees, ArrayList<Department> departments) {
        this.employees = employees;
        this.departments = departments;
    }

    public ArrayList<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employees> employees) {
        this.employees = employees;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }
}
