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

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="proc_assesment")
public class Assesment extends GenericModel{
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	public String assesmentNote;
	@ManyToOne
	public Evaluation evaluation;
	@ManyToOne
	public Operator attendant;
	public BigDecimal obtainedMarks;
	public Date attendedOn;
	@OneToMany(mappedBy = "assesment")
	public List<AssesmentProcess> processes = new ArrayList<AssesmentProcess>();
	
}
