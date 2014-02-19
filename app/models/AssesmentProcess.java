package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class AssesmentProcess extends Model{
	@ManyToOne
public Assesment assesment;
	@ManyToOne
public Question question;
	@OneToMany(mappedBy = "assesmentProcess")
	public List<Answer> answers = new ArrayList<Answer>();
public Date assecedOn;

}
