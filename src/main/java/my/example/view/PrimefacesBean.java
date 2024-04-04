package my.example.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import my.example.model.Person;
import my.example.service.NameService;

@ViewScoped
@ManagedBean(name = "pfBean")
public class PrimefacesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Person person = new Person();

	@ManagedProperty("#{nameService}") // inject NameService bean
	private NameService nameService;

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
			switch (sex) {
			case "M":
				return "Male";
			case "F":
				return "Female";
			}
		}
		return "Unknown";
	}

	public void contactPreferenceChanged() {
//		if (this.person.getContactPreference().equals("phone")) {
//			this.person.setEmail("");
//		} else {
//			this.person.setPhoneNumber("");
//		}
		this.person.setEmail("");
		this.person.setPhoneNumber("");
	}

}
