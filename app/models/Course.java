package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "scool_course")
/**
 * 
 * @author donatien
 *Each school will define its own courses
 */
public class Course extends Model {
	@Required
	@MaxSize(100)
	@Unique
	public String code;
	@Required
	@MaxSize(100)
	public String name;
	@Required
	@MaxSize(10000)
	public String content;
	@ManyToOne
	public School school;
	@OneToMany(mappedBy = "course")
	public List<TeacherClassCourse> teachers = new ArrayList<TeacherClassCourse>();
	@OneToMany(mappedBy = "course")
	public List<Evaluation> evaluations = new ArrayList<Evaluation>();
	@OneToMany(mappedBy = "course")
	public List<Chapter> chapters = new ArrayList<Chapter>();
	public Date createdOn;
	@ManyToOne
	public Operator creator;
}
