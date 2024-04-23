package my.example.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import my.example.model.Employee;
import my.example.service.impl.EmployeeServiceMemory;

@ManagedBean
@ViewScoped
public class CrudBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mode;
    private Employee employeeCriteria;
    private Employee employeeEdit;
    private Employee selectedMember;
    private List<Employee> employeeList;

    @Inject
    private EmployeeServiceMemory service = new EmployeeServiceMemory();
    
    // Constructor
    @PostConstruct
    public void init() {
        mode = "R";
        employeeCriteria = new Employee();
        service.mock();
    }

    // Getter and Setter methods

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

    // Action methods

    public void searchBtnOnclick() {
        try {
            employeeList = service.search(employeeCriteria);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "เกิดข้อผิดพลาดในการค้นหาข้อมูล");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
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
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
    }

    public void updateBtnOnclick() {
    	   if (!isValidEmployee(employeeEdit)) {
    	       return;
    	   }
            service.update(employeeEdit);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "แก้ไขข้อมูลเรียบร้อย.");
            FacesContext.getCurrentInstance().addMessage("form:messages", message);
    }

    public void deleteBtnOnclick() {
        service.delete(employeeEdit.getId());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "ลบข้อมูลเรียบร้อย.");
        FacesContext.getCurrentInstance().addMessage("form:messages", message);
    }

    public void backBtnOnclick() {
        mode = "R";
    }
    
    public void resetBtnOnclick() {
        if (mode.equals("R")) {
            resetForm();
            startSearch();
        } else if (mode.equals("U")) {
            resetFormToSelectedEmployee();
        }
    }
    
    private void resetForm() {
        // รีเซ็ตค่าที่ต้องการเรียกใช้งานทั้งหมดในโหมด R
        employeeCriteria = new Employee();
    }

    private void startSearch() {
        // ทำการค้นหาโดยใช้เงื่อนไขใน employeeCriteria และอัปเดต employeeList
        employeeList = service.search(employeeCriteria);
    }

    private void resetFormToSelectedEmployee() {
    	if (selectedMember != null) { // ตรวจสอบว่า selectedMember ไม่เป็น null
    		employeeEdit = new Employee(selectedMember); // Use copy constructor
        }
    }


    public String calculateAge(Date birthdate) {
    	   if (birthdate != null) {
    	       try {
    	           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	           LocalDate birthdateDate = LocalDate.parse(formatter.format(birthdate), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    	           Period period = Period.between(birthdateDate, LocalDate.now());
    	           
    	           if (period.getYears() >= 2) {
    	               return period.getYears() + " years " + period.getMonths() + " months " + "and " + period.getDays() + " days.";
    	           } else {
    	               return "อายุต้องมากกว่า 2 ปี";
    	           }
    	       } catch (DateTimeParseException e) {
    	           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "เกิดข้อผิดพลาดในการคำนวณอายุ.");
    	           FacesContext.getCurrentInstance().addMessage("form:messages", message);
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
    	       FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "กรุณากรอกข้อมูลในฟิลด์ที่จำเป็นทั้งหมด.");
    	       FacesContext.getCurrentInstance().addMessage("form:messages", message);
    	       return false;
    	   }
    	   
    	   String ageMessage = calculateAge(employee.getBirthdate());
    	   if (ageMessage.startsWith("อายุต้องมากกว่า 2 ปี")) {
    	       FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "อายุต้องมากกว่า 2 ปีจึงจะเพิ่มข้อมูลได้.");
    	       FacesContext.getCurrentInstance().addMessage("form:messages", message);
    	       return false;
    	   }
    	   
    	   return true;
    	}
}
