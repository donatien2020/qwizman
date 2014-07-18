package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
import utils.helpers.AssesmentStatus;
@Indexed
@Entity
@Table(name = "proc_assesment")
public class Assesment extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	public String assesmentNote;
	@ManyToOne
	@NotNull
	public Evaluation evaluation;
	@ManyToOne
	@NotNull
	public Operator attendant;
	@NotNull
	public BigDecimal obtainedMarks;
	@NotNull
	public Date attendedOn;

	public Date terminetedOn;
	@Transient
	public BigDecimal elapsedTime;
	@OneToMany(mappedBy = "assesment")
	public List<AssesmentProcess> processes = new ArrayList<AssesmentProcess>();
	public Assesment() {}
	@NotNull
	public String aStatus;
	
	public Assesment(Evaluation evaluation,Operator attendant){
		this.evaluation=evaluation;
		this.attendant=attendant;
		this.obtainedMarks=new BigDecimal("0.0");
		this.attendedOn=new Date();
		this.assesmentNote="Start";
		this.aStatus=AssesmentStatus.STARTED.getAssesmentStatus();
	}

	

}
