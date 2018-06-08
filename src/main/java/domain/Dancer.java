
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Dancer extends Actor {

	private List<Application>	applications;
	private List<Curricula>		curriculas;


	//Getters
	@NotNull
	@OneToMany
	public List<Application> getApplications() {
		return applications;
	}

	@NotNull
	@OneToMany
	public List<Curricula> getCurriculas() {
		return curriculas;
	}

	//Setters

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public void setCurriculas(List<Curricula> curriculas) {
		this.curriculas = curriculas;
	}

}
