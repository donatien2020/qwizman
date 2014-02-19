package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class QuestionOption extends Model{
	public String content;
	// hidden before answered then shown after answered
	public BigDecimal marks;
	public Boolean chosen;
	@ManyToOne
	public Question question;
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	@OneToMany(mappedBy = "questionOption")
	public List<Answer> answers = new ArrayList<Answer>();
}
