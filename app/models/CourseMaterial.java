package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
@Entity
public class CourseMaterial extends Model{
public String name;
public String content;
@ManyToOne
public Course course;
public Date createdOn;
@ManyToOne
public Operator creator;

}
