
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class StageCourse {

	//Attributes

	private final String	ORGANISING	= "ORGANISING", DELIVERING = "DELIVERING";
	private String			courseValue;


	//Getters

	@NotBlank
	@Pattern(regexp = "^" + ORGANISING + "|" + DELIVERING + "$")
	public String getCourseValue() {
		return courseValue;
	}

	//Setters

	public void setCourseValue(String courseValue) {
		this.courseValue = courseValue;
	}

}
