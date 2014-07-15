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

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "school_evaluation")
public class Evaluation extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@Required
	@NotNull
	public String name;
	@Required
	@NotNull
	public String description;
	@NotNull
	public BigDecimal totalMarks;
	@NotNull
	public BigDecimal duration;
	// Quiz,Excercise,Exam,General Quiz,
	@NotNull
	public String evalType;
	@ManyToOne
	@NotNull
	public Course course;
	@ManyToOne
	@NotNull
	public Classe classe;
	@ManyToOne
	@NotNull
	public AcademicYearDevision accademicYearDevision;
	@OneToMany(mappedBy = "evaluation")
	public List<Question> questions = new ArrayList<Question>();
	@OneToMany(mappedBy = "evaluation")
	public List<Assesment> assesments = new ArrayList<Assesment>();
	@NotNull
	public Date createdOn;
	@ManyToOne
	@NotNull
	public Operator createdBy;
	@ManyToOne
	@NotNull
	public School school;

	public Evaluation() {
	}

	public Evaluation(String name, String description, String evalType,
			BigDecimal totalMarks,BigDecimal duration, Course course,
			AcademicYearDevision accademicYearDevision, School school,Operator createdBy) {
		this.name = name;
		this.description = description;
		this.evalType = evalType;
		this.totalMarks = totalMarks;
		this.course = course;
		this.accademicYearDevision = accademicYearDevision;
		this.createdBy = createdBy;
		this.createdOn = new Date();
		this.school=school;
		this.duration=duration;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
