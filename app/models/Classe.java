package models;

import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;
@Entity
public class Classe extends Model{
	@OneToOne(cascade = PERSIST)
	public Contact contact;
	@ManyToOne
	public School school;
	@ManyToOne
	public ClassLevel level;
	@OneToMany(mappedBy = "classe")
	public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	

}
