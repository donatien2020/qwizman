package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "school_question")
public class Question extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@Required
	@MinSize(8)
	@MaxSize(1000)
	public String content;
	@Required
	public BigDecimal maxAllowedOptions;
	@Required
	public BigDecimal maxMarks;
	public String qType;
	@ManyToOne
	public Evaluation evaluation;
	@OneToMany(mappedBy = "question")
	public List<QuestionOption> options = new ArrayList<QuestionOption>();
	@OneToMany(mappedBy = "question")
	public List<AssesmentProcess> assesments = new ArrayList<AssesmentProcess>();
}
