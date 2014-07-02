package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="school_teacher_class_course")
public class TeacherClassCourse extends Model{
	@ManyToOne
	public Operator teacher;
	@ManyToOne
	public Classe classe;
	@ManyToOne
	public Course course;
	public String status;
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator lastUpdatedBy;
	public Date lastUpdateOn;
}
