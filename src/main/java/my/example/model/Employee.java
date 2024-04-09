package my.example.model;

import java.io.Serializable;
import java.util.Date; // เพิ่ม import
import java.util.UUID;


public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private Date birthdate; // เปลี่ยนชนิดของ birthdate เป็น Date

    public Employee() {
        id = UUID.randomUUID().toString();
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) { // เปลี่ยน signature ของเมทอด setBirthdate
        this.birthdate = birthdate;
    }
}
