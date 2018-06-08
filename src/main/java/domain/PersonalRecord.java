
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalRecord extends DomainEntity {

	private String	fullName;
	private String	email;
	private String	numWhatsapp;
	private String	linkedln;


	//Getters
	@NotBlank
	public String getFullName() {
		return fullName;
	}
	@Email
	public String getEmail() {
		return email;
	}

	@NotNull
	public String getNumWhatsapp() {
		return numWhatsapp;
	}

	@URL
	@NotNull
	public String getLinkedln() {
		return linkedln;
	}

	//Setter
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNumWhatsapp(String numWhatsapp) {
		this.numWhatsapp = numWhatsapp;
	}

	public void setLinkedln(String linkedln) {
		this.linkedln = linkedln;
	}

}
