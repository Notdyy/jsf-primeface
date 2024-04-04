package my.example.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

/**
 * Managed bean for CRUD operations on Employee entities.
 */
@ManagedBean
@ViewScoped
public class CrudBean implements Serializable {
    private static final long serialVersionUID = 1L;

    // Mode for CRUD operations
    private String mode;

    // Criteria for searching employees
    private Employee employeeCriteria;

    // Employee object for editing
    private Employee employeeEdit;

    // Selected member for editing or deletion
    private Employee selectedMember;

    // List of employees
    private List<Employee> employeeList;

    // Service for CRUD operations on employees
    private final EmployeeServiceMemory service = new EmployeeServiceMemory();

    /**
     * Initializes the managed bean after construction.
     */
    @PostConstruct
    public void init() {
        mode = "R"; // Set default mode to "Read"
        employeeCriteria = new Employee(); // Initialize criteria object.
    }

    /**
     * Action method for searching employees based on criteria.
     */
    public void searchBtnOnclick() {
        employeeList = service.search(employeeCriteria); // Perform search
    }

    /**
     * Action method for adding a new employee.
     */
    public void addBtnOnclick() {
        mode = "C"; // Set mode to "Create"
        employeeEdit = new Employee(); // Initialize new employee object for editing
    }

    /**
     * Action method for editing an employee.
     * 
     * @param p The employee to be edited
     */
    public void editBtnOnclick(Employee p) {
        mode = "U";
        employeeEdit = p;
    }
    /**
     * Action method for saving a new employee.
     */
    public void saveBtnOnclick() {
        if (!isDuplicate(employeeEdit)) { // Check for duplicate employees
            service.add(employeeEdit); // Add new employee
            mode = "U"; // Switch mode to "Update"
        } else {
            // Display error message for duplicate employee
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "This first name and last name already exist."));
        }
    }

    /**
     * Action method for updating an existing employee.
     */
    public void updateBtnOnclick() {
        service.update(employeeEdit);
    }

    /**
     * Action method for deleting an existing employee.
     */
    public void deleteBtnOnclick() {
        service.delete(employeeEdit.getId()); // Delete employee
    }

    /**
     * Action method for navigating back from editing.
     */
    public void backBtnOnclick() {
        mode = "R"; // Switch mode back to "Read"
    }

    // Getters and setters

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Employee getEmployeeCriteria() {
        return employeeCriteria;
    }

    public void setEmployeeCriteria(Employee employeeCriteria) {
        this.employeeCriteria = employeeCriteria;
    }

    public Employee getEmployeeEdit() {
        return employeeEdit;
    }

    public void setEmployeeEdit(Employee employeeEdit) {
        this.employeeEdit = employeeEdit;
    }

    public Employee getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Employee selectedMember) {
        this.selectedMember = selectedMember;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    /**
     * Checks if the given employee is a duplicate based on first name and last
     * name.
     * 
     * @param employee The employee to check
     * @return True if a duplicate is found, false otherwise
     */
    private boolean isDuplicate(Employee employee) {
        List<Employee> existingEmployees = service.search(employee); // Search for existing employees with same criteria
        // Check if any existing employee has same first name and last name
        return existingEmployees.stream().anyMatch(e -> e.getFirstName().equals(employee.getFirstName())
                && e.getLastName().equals(employee.getLastName()));
    }
}
