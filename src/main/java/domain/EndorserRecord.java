
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	private String	fullName;
	private String	contact;


	//Getters
	@NotBlank
	public String getFullName() {
		return fullName;
	}

	@NotBlank
	public String getContact() {
		return contact;
	}

	//Setters
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
