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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
import utils.helpers.QuestionStatus;
@XmlRootElement(name = "question")
@XmlAccessorType (XmlAccessType.FIELD)
@Indexed
@Entity
@Table(name = "school_question")
public class Question extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@NotNull
	public String content;
	@NotNull
	public BigDecimal maxAllowedOptions;
	@NotNull
	public BigDecimal marks;
	public String qType;
	@NotNull
	public String qStatus;
	@ManyToOne
	public Operator creator;
	public Date createdOn;
	@ManyToOne
	@NotNull
	public Evaluation evaluation;
	@OneToMany(mappedBy = "question")
	public List<QuestionOption> options = new ArrayList<QuestionOption>();
	@OneToMany(mappedBy = "question")
	public List<AssesmentProcess> assesments = new ArrayList<AssesmentProcess>();
	public Question() {
	}
	public Question(String content, BigDecimal maxAllowedOptions,
			BigDecimal marks, Evaluation evaluation, Operator creator) {
		this.content = content;
		this.maxAllowedOptions = maxAllowedOptions;
		this.marks = marks;
		this.evaluation = evaluation;
		this.creator = creator;
		this.createdOn = new Date();
		this.qStatus =QuestionStatus.ACTIVE.getQuestionStatus();
	}
}
