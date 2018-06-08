
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class StyleRecord extends DomainEntity {

	private Integer	numYear;
	private Style	style;


	//Geters

	@NotNull
	public Integer getNumYear() {
		return numYear;
	}

	@NotNull
	@OneToOne(optional = true)
	public Style getStyle() {
		return style;
	}

	public void setNumYear(Integer integer) {
		this.numYear = integer;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

}
