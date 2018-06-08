package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Derimek extends DomainEntity{
	
	//Attributes
	private String ticker;
	private Date moment;
	private String title;
	private String description;
	private Integer score;
	private Boolean isCancelled;
	private String justification;
	
	//Getters
	
	@NotBlank
	@Pattern(regexp = "^\\d{2}-\\d{5}$")
	public String getTicker() {
		return ticker;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm:ss")
	public Date getMoment() {
		return moment;
	}
	
	@NotBlank
	@Length(min = 1, max = 20)
	public String getTitle() {
		return title;
	}
	
	@NotBlank
	@Length(min = 1, max = 100)
	public String getDescription() {
		return description;
	}
	
	@Range(min = -1,max = 1)
	public Integer getScore() {
		return score;
	}
	
	public Boolean getIsCancelled(){
		return isCancelled;
	}
	
	public String getJustification(){
		return justification;
	}
	
	
	//Setters
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public void setMoment(Date moment){
		this.moment = moment;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setScore(Integer score){
		this.score = score;
	}
	
	public void setIsCancelled(Boolean isCancelled){
		this.isCancelled = isCancelled;
	}
	
	public void setJustification(String justification){
		this.justification = justification;
	}
}