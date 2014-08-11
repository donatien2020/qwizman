package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
 * @author donatien
 *Each school will define its own courses
 */
public class Course extends Model {
	@Unique
	@NotNull
	public String code;
	@Required
	@NotNull
	public String name;
	public BigDecimal overTj;
	public BigDecimal overEx;
	@NotNull
	@Column(columnDefinition = "TEXT", name = "content_c")
	public String content;
	@ManyToOne
	@NotNull
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
	public Course() {
	}
	public Course(String code, String name, String content, School school,
			BigDecimal overTj,BigDecimal overEx,Operator creator) {
		this.code = code;
		this.name = name;
		this.content = content;
		this.school = school;
		this.overTj = overTj;
		this.overEx = overEx;
		this.creator = creator;
		this.createdOn = new Date();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
