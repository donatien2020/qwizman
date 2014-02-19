package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Course extends Model{
public String name;
public String descrition;
@OneToMany(mappedBy = "course")
public List<CourseMaterial> materials = new ArrayList<CourseMaterial>();
@OneToMany(mappedBy = "course")
public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
@OneToMany(mappedBy = "course")
public List<Evaluation> evaluations = new ArrayList<Evaluation>();
public Date createdOn;
@ManyToOne
public Operator creator;
}
