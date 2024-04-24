package my.example.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import lombok.Data;
import my.example.model.Employee;
import my.example.service.EmployeeService;
import my.example.service.qualifier.Repository;

@Data
@Named
@ViewScoped
public class CrudBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mode;
    private Employee employeeCriteria;
    private Employee employeeEdit;
    private Employee selectedMember;
    private List<Employee> employeeList;

    private static final String ERROR_MESSAGE = "Error";
    private static final String MESSAGE_TARGET = "form:messages";

    @Inject
    @Repository(Repository.MEMORY)
    private EmployeeService service;

    @PostConstruct
    public void init() {
        mode = "R";
        employeeCriteria = new Employee();
        searchBtnOnclick();
    }

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

    public void searchBtnOnclick() {
        try {
            employeeList = service.search(employeeCriteria);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MESSAGE, "เกิดข้อผิดพลาดในการค้นหาข้อมูล");
            FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
        }
    }

    public void addBtnOnclick() {
        mode = "C";
        employeeEdit = new Employee();
    }

    public void editBtnOnclick(Employee p) {
        mode = "U";
        employeeEdit = p;
    }

    public void saveBtnOnclick() {
        if (!isValidEmployee(employeeEdit)) {
            return;
        }
        service.add(employeeEdit);
        mode = "U";
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "บันทึกข้อมูลเรียบร้อย.");
        FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
    }

    public void updateBtnOnclick() {
        if (!isValidEmployee(employeeEdit)) {
            return;
        }
        service.update(employeeEdit);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "แก้ไขข้อมูลเรียบร้อย.");
        FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
    }

    public void deleteBtnOnclick() {
        service.delete(employeeEdit.getId());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "ลบข้อมูลเรียบร้อย.");
        FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
    }

    public void backBtnOnclick() {
        mode = "R";
    }

    public void onRowSelect(SelectEvent<Employee> event) {
        selectedMember = event.getObject();
        mode = "U";
        employeeEdit = new Employee(selectedMember);
    }

    public void resetBtnOnclick() {
        if (mode.equals("R")) {
            resetForm();
            searchBtnOnclick();
        } else if (mode.equals("U")) {
            resetFormToSelectedEmployee();
        }
    }

    private void resetForm() {
        employeeCriteria = new Employee();
    }

    private void resetFormToSelectedEmployee() {
        if (selectedMember != null) {
            employeeEdit = new Employee(selectedMember);
        }
    }

    public String calculateAge(Date birthdate) {
        if (birthdate != null) {
            try {
                LocalDate birthdateDate = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(birthdateDate, LocalDate.now());

                if (period.getYears() >= 2) {
                    return period.getYears() + " years " + period.getMonths() + " months " + "and " + period.getDays() + " days.";
                } else {
                    return "อายุต้องมากกว่า 2 ปี";
                }
            } catch (DateTimeParseException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MESSAGE, "เกิดข้อผิดพลาดในการคำนวณอายุ.");
                FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
                return "";
            }
        } else {
            return "";
        }
    }

    private boolean isValidEmployee(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().isEmpty() ||
                employee.getLastName() == null || employee.getLastName().isEmpty() ||
                employee.getBirthdate() == null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MESSAGE, "กรุณากรอกข้อมูลในฟิลด์ที่จำเป็นทั้งหมด.");
            FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
            return false;
        }

        String ageMessage = calculateAge(employee.getBirthdate());
        if (ageMessage.startsWith("อายุต้องมากกว่า 2 ปี")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MESSAGE, "อายุต้องมากกว่า 2 ปีจึงจะเพิ่มข้อมูลได้.");
            FacesContext.getCurrentInstance().addMessage(MESSAGE_TARGET, message);
            return false;
        }

        return true;
    }
}
