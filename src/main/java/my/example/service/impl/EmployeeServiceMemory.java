package my.example.service.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import my.example.model.Employee;
import my.example.service.EmployeeService;
import my.example.service.qualifier.Repository;

@ApplicationScoped
@Repository(value = Repository.MEMORY)
public class EmployeeServiceMemory implements EmployeeService, Serializable{
	
	private static final long serialVersionUID = 1L; // สร้าง serialVersionUID เพื่อป้องกันปัญหา serialization
    private static final Logger log = Logger.getLogger(EmployeeServiceMemory.class.getName());
    private static Map<String, Employee> employeeMap = new HashMap<>();
    
    @Override
    public void add(Employee employee) {
        if (employee == null) {
            log.log(Level.SEVERE, "Attempted to add null employee.");
            throw new IllegalArgumentException("Cannot add null employee.");
        }
        employeeMap.put(employee.getId(), employee);
    }
    
    @Override
    public int update(Employee employee) {
        if (employee == null || !employeeMap.containsKey(employee.getId())) {
            log.log(Level.SEVERE, "Attempted to update non-existing or null employee.");
            return 0;
        }
        employeeMap.put(employee.getId(), employee);
        return 1;
    }

    @Override
    public List<Employee> search(Employee employee) {
        List<Employee> employeeList = new ArrayList<>();
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            employeeList.add(entry.getValue());
        }
        return employeeList;
    }
    
    @Override
    public int delete(String id) {
        if (id == null || !employeeMap.containsKey(id)) {
            log.log(Level.SEVERE, "Attempted to delete non-existing or null employee.");
            return 0;
        }
        employeeMap.remove(id);
        return 1;
    }
    
    @Override
    public Employee getById(String id) {
        if (id != null) {
            return employeeMap.getOrDefault(id, null);
        }
        return null;
    }
    
    // ข้อมูลจำลอง
    @PostConstruct
    public void mock() {
        if (!employeeMap.isEmpty()) {
            return;
        }

        try {
            add(new Employee("John", "Doe", LocalDate.of(1990, 1, 1)));
            add(new Employee("Jane", "Doe", LocalDate.of(1985, 6, 15)));
            add(new Employee("Michael", "Smith", LocalDate.of(1982, 3, 10)));
            add(new Employee("Emily", "Johnson", LocalDate.of(1995, 11, 25)));
            add(new Employee("Daniel", "Williams", LocalDate.of(1987, 8, 3)));
            add(new Employee("Olivia", "Brown", LocalDate.of(1993, 9, 12)));
            add(new Employee("James", "Miller", LocalDate.of(1984, 5, 19)));
            add(new Employee("Emma", "Davis", LocalDate.of(1990, 12, 8)));
            add(new Employee("Alexander", "Garcia", LocalDate.of(1986, 4, 30)));
            add(new Employee("Sophia", "Rodriguez", LocalDate.of(1992, 10, 17)));
            add(new Employee("William", "Martinez", LocalDate.of(1989, 7, 22)));
            add(new Employee("Isabella", "Hernandez", LocalDate.of(1998, 2, 5)));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error parsing date", e);
        }
        log.info("Mock data completed");
    }
}