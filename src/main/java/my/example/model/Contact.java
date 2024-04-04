package my.example.model;

import java.io.Serializable;

// model Contact 

public class Contact implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String email;
    private String phone;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
