
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {
	
	private List<Derimek> derimeks;
	
	@NotNull
	@OneToMany
	public List<Derimek> getDerimeks() {
		return derimeks;
	}
	
	public void setDerimeks(List<Derimek> derimeks) {
		this.derimeks = derimeks;
	}



}
