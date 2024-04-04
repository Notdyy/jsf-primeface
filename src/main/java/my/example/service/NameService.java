package my.example.service;

// Service

import my.example.model.Person;

public class NameService {

	public String display(Person person) {
		if (person != null) {
			String title = person.getTitle() != null ? person.getTitle() : "";
			String firstName = person.getFirstName() != null ? person.getFirstName() : "";
			String lastName = person.getLastName() != null ? person.getLastName() : "";
			return title.concat(" ").concat(firstName).concat(" ").concat(lastName);
		} else {
			return "";
		}
	}

}
