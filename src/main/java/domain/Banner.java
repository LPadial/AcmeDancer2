package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	private String	url;
	private Academy	academy;


	//Getters
	@NotBlank
	@URL
	public String getUrl() {
		return url;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Academy getAcademy() {
		return academy;
	}

	//Setters
	public void setUrl(String url) {
		this.url = url;
	}

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}

}