package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Evaluation extends Model{
public String name;
public String description;
@ManyToOne
public Course course;
@OneToMany(mappedBy = "evaluation")
public List<Question> courses = new ArrayList<Question>();
@OneToMany(mappedBy = "evaluation")
public List<Assesment> assesments = new ArrayList<Assesment>();
public Date createdOn;
@ManyToOne
public Operator createdBy;


}
