package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.db.jpa.Model;
import play.modules.search.Indexed;
import utils.helpers.TeacherStatus;

@Indexed
@Entity
@Table(name = "school_teacher_class_course")
public class TeacherClassCourse extends Model {
	@ManyToOne
	@NotNull
	public Operator teacher;
	@ManyToOne
	@NotNull
	public Classe classe;
	@ManyToOne
	public Course course;
	@NotNull
	public String status;
	@ManyToOne
	@NotNull
	public AcademicYearDevision accademicYearDevision;
	@ManyToOne
	public AccademicYear accademicYear;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator lastUpdatedBy;
	public Date lastUpdateOn;

	public TeacherClassCourse() {
	}

	public TeacherClassCourse(Operator teacher, Classe classe, Course course,AccademicYear accademicYear,
			AcademicYearDevision accademicYearDevision, Operator creator) {
		this.teacher = teacher;
		this.classe = classe;
		this.course = course;
		this.accademicYear=accademicYear;
		this.accademicYearDevision = accademicYearDevision;
		this.creator = creator;
		this.status=TeacherStatus.PERMANANTRYACTIVE.getTeacherStatus();
		this.createdOn = new Date();
		this.lastUpdatedBy=creator;
		this.lastUpdateOn=new Date();

	}
}
