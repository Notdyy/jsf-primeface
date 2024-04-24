package my.example.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    public void add(Employee employee) {
        if (employee == null) {
            log.log(Level.SEVERE, "Attempted to add null employee.");
            throw new IllegalArgumentException("Cannot add null employee.");
        }
        employeeMap.put(employee.getId(), employee);
    }
    
    public int update(Employee employee) {
        if (employee == null || !employeeMap.containsKey(employee.getId())) {
            log.log(Level.SEVERE, "Attempted to update non-existing or null employee.");
            return 0;
        }
        employeeMap.put(employee.getId(), employee);
        return 1;
    }

    public List<Employee> search(Employee employee) {
        List<Employee> employeeList = new ArrayList<>();
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            employeeList.add(entry.getValue());
        }
        return employeeList;
    }
    
    public int delete(String id) {
        if (id == null || !employeeMap.containsKey(id)) {
            log.log(Level.SEVERE, "Attempted to delete non-existing or null employee.");
            return 0;
        }
        employeeMap.remove(id);
        return 1;
    }
    
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
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

            add(new Employee("John", "Doe", formatter.parse("01/01/1990")));
            add(new Employee("Jane", "Doe", formatter.parse("15/06/1985")));
            add(new Employee("Michael", "Smith", formatter.parse("10/03/1982")));
            add(new Employee("Emily", "Johnson", formatter.parse("25/11/1995")));
            add(new Employee("Daniel", "Williams", formatter.parse("03/08/1987")));
            add(new Employee("Olivia", "Brown", formatter.parse("12/09/1993")));
            add(new Employee("James", "Miller", formatter.parse("19/05/1984")));
            add(new Employee("Emma", "Davis", formatter.parse("08/12/1990")));
            add(new Employee("Alexander", "Garcia", formatter.parse("30/04/1986")));
            add(new Employee("Sophia", "Rodriguez", formatter.parse("17/10/1992")));
            add(new Employee("William", "Martinez", formatter.parse("22/07/1989")));
            add(new Employee("Isabella", "Hernandez", formatter.parse("05/02/1998")));
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Error parsing date", e);
        }
        log.info("Mock data completed");
    }
}