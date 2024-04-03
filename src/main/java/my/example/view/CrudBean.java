package my.example.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
    private final EmployeeServiceMemory service = new EmployeeServiceMemory();
    
    @PostConstruct
    // init(): เมทอดที่ถูกเรียกเมื่อ Bean ถูกสร้างขึ้น ใช้ในการกำหนดค่าเริ่มต้น
    public void init() {
        mode = "R";
        employeeCriteria = new Employee(); 
    }
    // searchBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มค้นหา เพื่อทำการค้นหาข้อมูลพนักงาน
    public void searchBtnOnclick() {
        employeeList = service.search(employeeCriteria);
    }
    // addBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มเพิ่ม เพื่อทำการเพิ่มข้อมูลพนักงานใหม่
    public void addBtnOnclick() {
        mode = "C";
        employeeEdit = new Employee();
    }
    // editBtnOnclick(Employee p): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มแก้ไข เพื่อทำการแก้ไขข้อมูลพนักงานที่เลือก
    public void editBtnOnclick(Employee p) {
        mode = "U";
        employeeEdit = p;
    }
    // เมทอด saveBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มบันทึก เพื่อทำการบันทึกข้อมูลพนักงาน
    public void saveBtnOnclick() {
        service.add(employeeEdit);
        mode = "U";
    }
    // updateBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มอัปเดต เพื่อทำการอัปเดตข้อมูลพนักงาน
    public void updateBtnOnclick() {
        service.update(employeeEdit);
    }
    // deleteBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มลบ เพื่อทำการลบข้อมูลพนักงาน
    public void deleteBtnOnclick() {
        service.delete(employeeEdit.getId());
    }
    // backBtnOnclick(): เมทอดที่ถูกเรียกเมื่อผู้ใช้กดปุ่มย้อนกลับ เพื่อกลับไปยังหน้าค้นหาหรือหน้าหลัก
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
