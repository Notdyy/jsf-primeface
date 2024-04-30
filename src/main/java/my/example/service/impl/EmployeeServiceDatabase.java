package my.example.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import my.example.model.Employee;
import my.example.service.EmployeeService;
import my.example.service.qualifier.Repository;

@ApplicationScoped
@Repository(value = Repository.DATABASE)
public class EmployeeServiceDatabase implements EmployeeService {
	
	private static final Logger log = Logger.getLogger(EmployeeServiceDatabase.class.getName());

    private final EntityManager entityManager;
    
    public EmployeeServiceDatabase() {
        this.entityManager = null; // Or initialize to a proper value if needed
    }

    @Inject
    public EmployeeServiceDatabase(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    // เพิ่มเมทอดสำหรับตรวจสอบฐานข้อมูลว่ามีข้อมูลหรือไม่
    private boolean isDatabaseEmpty() {
        return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class)
                            .getSingleResult() == 0;
    }

    @PostConstruct
    public void mock() {
        if (!isDatabaseEmpty()) {
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

    @Override
    public void add(Employee employee) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(employee);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public int update(Employee employee) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(employee);
            transaction.commit();
            return 1;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Employee> search(Employee employee) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);

        Predicate predicate = criteriaBuilder.conjunction();

        if (employee != null) {
            if (employee.getFirstName() != null && !employee.getFirstName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("firstName"), employee.getFirstName()));
            }
            if (employee.getLastName() != null && !employee.getLastName().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("lastName"), employee.getLastName()));
            }
            if (employee.getBirthdate() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("birthdate"), employee.getBirthdate()));
            }
        }

        criteriaQuery.where(predicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public int delete(String id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.remove(employee);
                transaction.commit();
                return 1;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
        return 0;
    }

    @Override
    public Employee getById(String id) {
        return entityManager.find(Employee.class, id);
    }
}