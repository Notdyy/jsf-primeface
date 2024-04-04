package my.example.model;

// model Employee

import java.io.Serializable;
import java.util.UUID;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String firstName;
    private String lastName;
    
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
}
