package my.example.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import my.example.model.Employee;
import my.example.service.EmployeeServiceMemory;

@ManagedBean
@ViewScoped
public class CrudBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mode;
    private Employee employeeCriteria;
    private Employee employeeEdit;
    private Employee selectedMember;
    private List<Employee> employeeList;

    private EmployeeServiceMemory service = new EmployeeServiceMemory();

    @PostConstruct
    public void init() {
        mode = "R";
        employeeCriteria = new Employee();
    }
    
    public String calculateAge(Date birthdate) {
        if (birthdate != null) {
            try {
                // Define formatter for parsing the date string
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                LocalDate birthdateDate = LocalDate.parse(formatter.format(birthdate), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Period period = Period.between(birthdateDate, LocalDate.now());
                
                // เพิ่มเงื่อนไขตรวจสอบว่าอายุมากกว่า 2 ปีหรือไม่
                if (period.getYears() >= 2) {
                    return period.getYears() + " years " + period.getMonths() + " months " + "and " + period.getDays() + " days";
                } else {
                    return "Age must be greater than 2 years";
                }
            } catch (DateTimeParseException e) {
                // Handle the DateTimeParseException here
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }

    public void resetBtnOnclick() {
    	employeeCriteria = new Employee();
        resetMode();
    }

    private void resetMode() {
        if (mode.equals("C")) {
            employeeEdit = new Employee();
        } else if (mode.equals("U")) {
            if (selectedMember != null) { // ตรวจสอบว่า selectedMember ไม่เป็น null ก่อนที่จะใช้
                employeeList = service.search(selectedMember);
            }
        }
    }


    
    public void searchBtnOnclick() {
        employeeList = service.search(employeeCriteria);
    }

    public void addBtnOnclick() {
        mode = "C";
        resetMode();
    }

    public void editBtnOnclick(Employee p) {
        mode = "U";
        employeeEdit = p;
    }

    public void saveBtnOnclick() {
        String ageMessage = calculateAge(employeeEdit.getBirthdate());
        if (ageMessage.startsWith("Age must be greater than 2 years")) {
            // อายุไม่ถึง 2 ปี ไม่สามารถเพิ่มข้อมูลได้
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Age must be greater than or equal to 2 years to add data.");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
        } else {
            service.add(employeeEdit);
            mode = "U";
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "บันทึกข้อมูลเรียบร้อย");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
        }
    }

    public void updateBtnOnclick() {
        String ageMessage = calculateAge(employeeEdit.getBirthdate());
        if (ageMessage.startsWith("Age must be greater than 2 years")) {
            // อายุไม่ถึง 2 ปี ไม่สามารถเพิ่มข้อมูลได้
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Age must be greater than or equal to 2 years to add data.");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
        } else {
            // อายุมากกว่าหรือเท่ากับ 2 ปี สามารถเพิ่มข้อมูลได้
            service.update(employeeEdit);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "แก้ไขข้อมูลเรียบร้อย");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
        }
    }




    public void deleteBtnOnclick() {
        service.delete(employeeEdit.getId());
    }

    public void backBtnOnclick() {
        mode = "R";
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
}