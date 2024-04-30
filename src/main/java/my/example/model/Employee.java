package my.example.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "employees")
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id")
    private String id;
    
    @Column(name="firstName")
    private String firstName;
    
    @Column(name="lastName")
    private String lastName;
    
    @Column(name="birthdate")
    private LocalDate birthdate;

    public Employee() {
        id = UUID.randomUUID().toString();
    }

    public Employee(String firstName, String lastName, LocalDate birthdate) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    // Clone method
    public Employee(Employee other) {
        this.id = other.id;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.birthdate = other.birthdate;
    }
}