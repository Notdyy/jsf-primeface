package my.example.service.impl;

import java.util.List;
import javax.faces.bean.ApplicationScoped;

import my.example.model.Employee;
import my.example.service.EmployeeService;
import my.example.service.qualifier.Repository;

@ApplicationScoped
@Repository(value = Repository.DATABASE)
public class EmployeeServiceDatabase implements EmployeeService{
	
	@Override
    public void add(Employee employee) {
        throw new UnsupportedOperationException("Database implementation not yet supported.");
    }

	@Override
	public int update(Employee employee) {
		throw new UnsupportedOperationException("Database implementation not yet supported.");
	}

	@Override
	public List<Employee> search(Employee employee) {
		throw new UnsupportedOperationException("Database implementation not yet supported.");
	}

	@Override
	public int delete(String id) {
		throw new UnsupportedOperationException("Database implementation not yet supported.");
	}

	@Override
	public Employee getById(String id) {
		throw new UnsupportedOperationException("Database implementation not yet supported.");
	}

	@Override
	public void mock() {
		throw new UnsupportedOperationException("Database implementation not yet supported.");
	}
	
}