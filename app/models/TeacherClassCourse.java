package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class TeacherClassCourse extends Model {
	@ManyToOne
	public Operator teacher;
	@ManyToOne
	public Classe classe;
	@ManyToOne
	public Course course;
	public String status;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
}
