
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity {

	private PersonalRecord			personalRecord;
	private List<CustomRecord>		customRecord;
	private List<EndorserRecord>	endorserRecord;
	private List<StyleRecord>		styleRecord;


	//Getters
	@NotNull
	@OneToOne
	public PersonalRecord getPersonalRecord() {
		return personalRecord;
	}

	@NotNull
	@OneToMany
	public List<CustomRecord> getCustomRecord() {
		return customRecord;
	}

	@NotNull
	@OneToMany
	public List<EndorserRecord> getEndorserRecord() {
		return endorserRecord;
	}

	@NotNull
	@OneToMany
	public List<StyleRecord> getStyleRecord() {
		return styleRecord;
	}

	//Setters
	public void setPersonalRecord(PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	public void setCustomRecord(List<CustomRecord> customRecord) {
		this.customRecord = customRecord;
	}

	public void setEndorserRecord(List<EndorserRecord> endorserRecord) {
		this.endorserRecord = endorserRecord;
	}

	public void setStyleRecord(List<StyleRecord> styleRecord) {
		this.styleRecord = styleRecord;
	}

}
