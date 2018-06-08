
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Academy extends Actor {

	private String			commercialName;
	private List<Tutorial>	tutorials;
	private List<Course>	courses;
	private List<Banner>	banners;


	//Getters
	@NotBlank
	public String getCommercialName() {
		return commercialName;
	}

	@NotNull
	@OneToMany
	public List<Tutorial> getTutorials() {
		return tutorials;
	}

	@NotNull
	@OneToMany
	public List<Course> getCourses() {
		return courses;
	}

	@NotNull
	@OneToMany
	public List<Banner> getBanners() {
		return banners;
	}

	//Setters
	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public void setTutorials(List<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

}
