package my.example.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import my.example.model.Person;
import my.example.service.NameService;

@Named("pfBean")
@ViewScoped
public class PrimefacesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Person person = new Person();

	private transient NameService nameService;

	private String fullName;

	// This method will be called when the bean is created  //
	public void init() {
		nameService = new NameService(); // Initialize nameService
	}

	public void submitButtonOnClick() {
		this.fullName = nameService.display(person);
	}

	// Getters and setters
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public NameService getNameService() {
		return nameService;
	}

	public void setNameService(NameService nameService) {
		this.nameService = nameService;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String personSexFull() {
		String sex = this.person.getSex();
		if (sex != null) {
			if (sex.equals("M")) {
				return "Male";
			} else if (sex.equals("F")) {
				return "Female";
			}
		}
		return "Unknown";
	}

	public void contactPreferenceChanged() {
		if ("phone".equals(this.person.getContactPreference())) {
			this.person.setEmail("");
		} else {
			this.person.setPhoneNumber("");
		}
	}

}
