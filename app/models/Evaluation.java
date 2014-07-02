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
	@MaxSize(100)
	public String name;
	@Required
	@MaxSize(1000)
	public String description;
	public BigDecimal totalMarks;
	// Quiz,Excercise,Exam,General Quiz,
	public String evalType;
	@ManyToOne
	public Course course;
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;
	@OneToMany(mappedBy = "evaluation")
	public List<Question> questions = new ArrayList<Question>();
	@OneToMany(mappedBy = "evaluation")
	public List<Assesment> assesments = new ArrayList<Assesment>();
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;

}
