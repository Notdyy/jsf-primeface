package my.example.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;

import my.example.model.Employee;
import my.example.service.EmployeeService;
import my.example.service.qualifier.Repository;

@ApplicationScoped
@Repository(value = Repository.MEMORY)
public class EmployeeServiceMemory implements EmployeeService{

    private static final Logger log = Logger.getLogger(EmployeeServiceMemory.class.getName());
    public static HashMap<String, Employee> employeeMap = new HashMap<String, Employee>();
    
    @Override
    public void add(Employee employee) {
    	employeeMap.put(employee.getId(), employee);
    }
    
    @Override
    public int update(Employee employee) {
        if (employeeMap.containsKey(employee.getId())) {
            employeeMap.put(employee.getId(), employee);
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public List<Employee> search(Employee employee) {
        List<Employee> employeeList = new ArrayList<Employee>();
        for (Map.Entry<String, Employee> entry : employeeMap.entrySet()) {
            employeeList.add(entry.getValue());
        }
        return employeeList;
    }
    
    @Override
    public int delete(String id) {
        if (employeeMap.containsKey(id)) {
            employeeMap.remove(id);
            return 1;
        } else {
            return 0;
        }
    }
    
    @Override
    public Employee getById(String id) {
        if (id != null) {
            return employeeMap.getOrDefault(id, null);
        }
        return null;
    }
    
    // ข้อมูลจำลอง
    @Override
    public void mock() {
        if (!employeeMap.isEmpty()) {
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            Employee employee1 = new Employee();
            employee1.setFirstName("John");
            employee1.setLastName("Doe");
            employee1.setBirthdate(dateFormat.parse("01/01/1990"));
            add(employee1);

            Employee employee2 = new Employee();
            employee2.setFirstName("Jane");
            employee2.setLastName("Doe");
            employee2.setBirthdate(dateFormat.parse("15/06/1985"));
            add(employee2);

            // เพิ่มพนักงานเพิ่มเติมตามต้องการ
            // Employee 3
            Employee employee3 = new Employee();
            employee3.setFirstName("Michael");
            employee3.setLastName("Smith");
            employee3.setBirthdate(dateFormat.parse("10/03/1982"));
            add(employee3);

            // Employee 4
            Employee employee4 = new Employee();
            employee4.setFirstName("Emily");
            employee4.setLastName("Johnson");
            employee4.setBirthdate(dateFormat.parse("25/11/1995"));
            add(employee4);

            // Employee 5
            Employee employee5 = new Employee();
            employee5.setFirstName("Daniel");
            employee5.setLastName("Williams");
            employee5.setBirthdate(dateFormat.parse("03/08/1987"));
            add(employee5);

            // Employee 6
            Employee employee6 = new Employee();
            employee6.setFirstName("Olivia");
            employee6.setLastName("Brown");
            employee6.setBirthdate(dateFormat.parse("12/09/1993"));
            add(employee6);

            // Employee 7
            Employee employee7 = new Employee();
            employee7.setFirstName("James");
            employee7.setLastName("Miller");
            employee7.setBirthdate(dateFormat.parse("19/05/1984"));
            add(employee7);

            // Employee 8
            Employee employee8 = new Employee();
            employee8.setFirstName("Emma");
            employee8.setLastName("Davis");
            employee8.setBirthdate(dateFormat.parse("08/12/1990"));
            add(employee8);

            // Employee 9
            Employee employee9 = new Employee();
            employee9.setFirstName("Alexander");
            employee9.setLastName("Garcia");
            employee9.setBirthdate(dateFormat.parse("30/04/1986"));
            add(employee9);

            // Employee 10
            Employee employee10 = new Employee();
            employee10.setFirstName("Sophia");
            employee10.setLastName("Rodriguez");
            employee10.setBirthdate(dateFormat.parse("17/10/1992"));
            add(employee10);

            // Employee 11
            Employee employee11 = new Employee();
            employee11.setFirstName("William");
            employee11.setLastName("Martinez");
            employee11.setBirthdate(dateFormat.parse("22/07/1989"));
            add(employee11);

            // Employee 12
            Employee employee12 = new Employee();
            employee12.setFirstName("Isabella");
            employee12.setLastName("Hernandez");
            employee12.setBirthdate(dateFormat.parse("05/02/1998"));
            add(employee12);
        } catch (ParseException e) {
            log.log(Level.SEVERE, "Error parsing date", e);
        }
    }
}
