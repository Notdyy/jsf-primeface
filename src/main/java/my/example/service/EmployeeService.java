package my.example.service;

import java.util.List;

import my.example.model.Employee;

public interface EmployeeService {
    void add(Employee employee);
    int update(Employee employee);
    List<Employee> search(Employee employee);
    int delete(String id);
    Employee getById(String id);
}
