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
@Table(name = "school_question_option")
public class QuestionOption extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	public String content;
	// hidden before answered then shown after answered
	public BigDecimal marks;
	@ManyToOne
	public Question question;
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	@OneToMany(mappedBy = "questionOption")
	public List<Answer> answers = new ArrayList<Answer>();
}
