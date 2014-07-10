package models;

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

import controllers.Operators;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "school_academicYear_devision")
/**
 * 
 * @author donatien 
 * They are like semester/trimesters and so on
 *
 */
public class AcademicYearDevision extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	public String description;
	public String devisionLabel;
	public Date startAt;
	public Date endAt;
	public Boolean devisionStatus;
	@ManyToOne
	@NotNull
	public AccademicYear accademicYear;
	@ManyToOne
	public Operator createdBy;
	public Date createdOn;
	@OneToMany(mappedBy = "accademicYearDevision")
	public List<TeacherClassCourse> teachers = new ArrayList<TeacherClassCourse>();
	@OneToMany(mappedBy = "accademicYearDevision")
	public List<StudentClasse> students = new ArrayList<StudentClasse>();
	@OneToMany(mappedBy = "accademicYearDevision")
	public List<Evaluation> evaluatios = new ArrayList<Evaluation>();
	@OneToMany(mappedBy = "accademicYearDevision")
	public List<SchoolReport> reports = new ArrayList<SchoolReport>();
	@OneToMany(mappedBy = "accademicYearDevision")
	public List<Chapter> chapters = new ArrayList<Chapter>();

	public AcademicYearDevision() {
	}

	public AcademicYearDevision(String description, String devisionLabel,
			Date startAt, Date endAt, AccademicYear accademicYear,
			Operator createdBy) {
		if (accademicYear != null) {
			this.description = description;
			this.devisionLabel = devisionLabel;
			this.startAt = startAt;
			this.endAt = endAt;
			this.accademicYear = accademicYear;
			this.createdBy = createdBy;
			this.devisionStatus = new Boolean(true);
			this.createdOn = new Date();
		}
	}
}
