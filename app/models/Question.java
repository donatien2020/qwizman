package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Question extends Model{
	public String name;
	public String description;
	public BigDecimal allowedOptions;
	public BigDecimal maxMarks;
	//synhtese
	public String type;
	@ManyToOne
	public Evaluation evaluation;
	@OneToMany(mappedBy = "question")
	public List<QuestionOption> options = new ArrayList<QuestionOption>();
	@OneToMany(mappedBy = "question")
	public List<AssesmentProcess> assesments = new ArrayList<AssesmentProcess>();
}
