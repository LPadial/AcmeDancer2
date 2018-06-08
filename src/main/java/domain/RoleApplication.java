
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class RoleApplication {

	//Attributes

	private final String	TEACHER	= "TEACHER", STUDENT = "STUDENT";
	private String			roleValue;


	//Getters

	@NotBlank
	@Pattern(regexp = "^" + TEACHER + "|" + STUDENT + "$")
	public String getRoleValue() {
		return roleValue;
	}

	//Setters

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

}
