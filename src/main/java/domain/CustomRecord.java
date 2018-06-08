
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class CustomRecord extends DomainEntity {

	private String			title;
	private String			text;
	private List<String>	links;


	//Getters
	@NotBlank
	public String getTitle() {
		return title;
	}

	@NotBlank
	public String getText() {
		return text;
	}

	@NotNull
	@ElementCollection(targetClass = String.class)
	public List<String> getLinks() {
		return links;
	}

	//Setters

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

}
