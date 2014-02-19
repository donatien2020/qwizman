package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Assesment extends Model{
	public String assesmentNote;
	@ManyToOne
	public Evaluation evaluation;
	@ManyToOne
	public Operator attendant;
	public Date attendedOn;
	@OneToMany(mappedBy = "assesment")
	public List<AssesmentProcess> processes = new ArrayList<AssesmentProcess>();
	
}
