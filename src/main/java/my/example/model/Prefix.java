package my.example.model;

//model Prefix

import java.io.Serializable;

public class Prefix implements Serializable {
    private static final long serialVersionUID = 1L;
    private String label;
    private String value;
   

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Prefix() {
    }

    public Prefix(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
